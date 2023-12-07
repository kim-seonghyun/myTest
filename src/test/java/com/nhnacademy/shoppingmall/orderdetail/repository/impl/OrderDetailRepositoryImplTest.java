package com.nhnacademy.shoppingmall.orderdetail.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * order, product
 */
class OrderDetailRepositoryImplTest {


    OrderDetailRepository orderDetailRepository = new OrderDetailRepositoryImpl(new ProductRepositoryImpl(),
            new OrderRepositoryImpl(new UserRepositoryImpl()));
    UserRepository userRepository = new UserRepositoryImpl();
    OrderRepository orderRepository = new OrderRepositoryImpl(userRepository);

    User testUser;

    Orders testOrder;
    int orderId;
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();

    Products testProduct;

    Categories categories;
    OrderDetails testOrderDetails;
    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testUser = new User("nhnacademy-test-user", "nhn아카데미", "nhnacademy-test-password", "19900505",
                User.Auth.ROLE_USER, 100_0000,
                LocalDateTime.now(), null);
        userRepository.save(testUser);
        testOrder = new Orders(testUser.getUserId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        orderId = orderRepository.save(testOrder);
        categories = new Categories("category1");
        categoriesRepository.save(categories);
        categories = categoriesRepository.findByCategoriesName("category1").get();
        testProduct = new Products( categories.getCategoryID(), "model1", "name1", "image1", 100, "description1");
        productsRepository.save(testProduct);
        testProduct = productsRepository.findByModelNumber(testProduct.getModelNumber()).get();
        testOrderDetails = new OrderDetails(orderId, testProduct.getProductId(), 3, testProduct.getUnitCost());
        orderDetailRepository.save(testOrderDetails);
    }
    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    void save() {
        Products testProduct2 = new Products( categories.getCategoryID(), "mode22l1", "n22ame1", "image1", 100, "description1");
        productsRepository.save(testProduct2);
        testProduct2 = productsRepository.findByModelNumber(testProduct2.getModelNumber()).get();

        OrderDetails newOrderDetails = new OrderDetails(orderId, testProduct2.getProductId(), 10,
                testProduct.getUnitCost());
        int result = orderDetailRepository.save(newOrderDetails);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newOrderDetails, orderDetailRepository.findOrderDetailByOrderIdAndProductId(
                        newOrderDetails.getOrderId(), newOrderDetails.getProductId()).get())

        );
    }

    @Test
    void findOrderDetailByOrderIdAndProductID() {
        Assertions.assertEquals(testOrderDetails, orderDetailRepository.findOrderDetailByOrderIdAndProductId(
                testOrderDetails.getOrderId(),testOrderDetails.getProductId()).get() );
    }

    @Test
    void findOrderDetailByOrderId() {
        Assertions.assertEquals(testOrderDetails, orderDetailRepository.findOrderDetailByOrderId(
                testOrderDetails.getOrderId()).get(0) );
    }

    @Test
    void update() {
        testOrderDetails.setQuantity(10);
        int result = orderDetailRepository.update(testOrderDetails);
        Assertions.assertAll(
                () -> Assertions.assertEquals(result, 1),
                () -> Assertions.assertEquals(testOrderDetails,
                        orderDetailRepository.findOrderDetailByOrderIdAndProductId(testOrderDetails.getOrderId(),
                                testOrderDetails.getProductId()).get())
        );
    }

    @Test
    void deleteOrderDetail() {
        int result = orderDetailRepository.deleteOrderDetail(testOrderDetails.getOrderId(), testOrderDetails.getProductId());
        Assertions.assertEquals(1, result);
    }
}