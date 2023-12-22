package com.nhnacademy.shoppingmall.order.services.impl;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.exceptions.InsufficientBalanceException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.services.OrderServices;
import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.orderdetail.repository.impl.OrderDetailRepositoryImpl;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import com.nhnacademy.shoppingmall.shoppingCart.repository.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.shoppingCart.service.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 에러 발생 시 rollback 테스트 주문 성공 - 포인트 변동, 주문 상세 내역
 * <p>
 * 주문 실패 - 에러 발생, 포인트 변동 없을것 잔액 부족
 * <p>
 * 주문 일부만 성공한경우 -
 * <p>
 * 포인트 변경 있으나 주문상세 저장 안됨
 * <p>
 * 주문상세 저장됐으나 포인트 변경없음.
 * <p>
 * order 저장안됨.
 */
@Slf4j
class OrderServicesImplTest {
    OrderRepository orderRepository = new OrderRepositoryImpl(new UserRepositoryImpl());
    OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl(new ProductRepositoryImpl(),
            new OrderRepositoryImpl(new UserRepositoryImpl()));

    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    UserService userService = new UserServiceImpl(userRepository);
    OrderServices orderServices = new OrderServicesImpl(orderRepository, orderDetailRepository, shoppingCartService,
            productsRepository, userService);

    Products testProduct;

    Categories categories;
    OrderDetails testOrderDetails;
    User testUser;

    Orders testOrder;
    int orderId;
    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testUser = new User("nhnacademy-test-user", "nhn아카데미", "nhnacademy-test-password", "19900505",
                User.Auth.ROLE_USER, 100_0000,
                LocalDateTime.now(), null);
        userRepository.save(testUser);

        testOrder = new Orders(testUser.getUserId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        categories = new Categories("category1");
        categoriesRepository.save(categories);
        categories = categoriesRepository.findByCategoriesName("category1").get();

        testProduct = new Products(categories.getCategoryID(), "model1", "name1", "image1", 1000, "description1");
        productsRepository.save(testProduct);
        testProduct = productsRepository.findByModelNumber(testProduct.getModelNumber()).get();
        log.debug("set UP에서 Product value" + String.valueOf(testProduct.getProductId()));

        shoppingCart = new ShoppingCart(testUser.getUserId(), 3, testProduct.getProductId());
        shoppingCartService.save(shoppingCart);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("주문 성공")
    void orderSuccess() {
        int userPoint = testUser.getUserPoint();
        int unitCost = testProduct.getUnitCost();
        int quantity = shoppingCart.getQuantity();
        int totalCost = unitCost * quantity;
        orderServices.order(testOrder);
        Assertions.assertEquals(userPoint - totalCost, userService.getUser(testUser.getUserId()).getUserPoint());
    }

    @Test
    @DisplayName("주문 후 장바구니 삭제 여부")
    void orderSuccessCheckShoppingCartDelete() {
        orderServices.order(testOrder);
        Assertions.assertTrue(shoppingCartService.findAllProductsByCartId(testUser.getUserId()).isEmpty());
    }

    @Test
    @DisplayName("포인트 부족으로 주문 실패")
    void orderFailbyInsufficientPoint() {
        final int INSUFFICIENT_POINT = 2000;
        userService.savePoint(testUser.getUserId(), INSUFFICIENT_POINT);
        Assertions.assertAll(
                ()->{Assertions.assertThrows(InsufficientBalanceException.class, ()->{
                    orderServices.order(testOrder);
                });},
                () ->{Assertions.assertEquals(INSUFFICIENT_POINT, userService.getUser(testOrder.getUserId()).getUserPoint());}
        );
    }


    @Test
    @DisplayName("주문 일부 성공하는지 확인, 장바구니 상품은 삭제, 포인트 부족으로 포인트 갱신안됨.")
    void notAtomicityOrder() {
        int userPoint = testUser.getUserPoint();
        int unitCost = testProduct.getUnitCost();
        int quantity = shoppingCart.getQuantity();
        int totalCost = unitCost * quantity;
        orderServices.order(testOrder);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(userPoint - totalCost,
                        userService.getUser(testUser.getUserId()).getUserPoint()),
                () -> Assertions.assertTrue(shoppingCartService.findAllProductsByCartId(testUser.getUserId()).isEmpty())
        );
    }

}