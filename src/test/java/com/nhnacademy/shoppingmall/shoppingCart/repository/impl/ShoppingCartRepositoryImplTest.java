package com.nhnacademy.shoppingmall.shoppingCart.repository.impl;


import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import com.nhnacademy.shoppingmall.shoppingCart.repository.ShoppingCartRepository;
import com.oracle.wls.shaded.org.apache.regexp.RE;
import java.sql.SQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * product를 미리 생성한 뒤, 직접 조회하여 productID를 가져왔습니다.
 */
class ShoppingCartRepositoryImplTest {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepositoryImpl();

    ShoppingCart shoppingCart;
    Products testProduct;
    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        //18번은 Products Table에 존재하는 productId값입니다.
        shoppingCart = new ShoppingCart("myCart", 3, 18);
        shoppingCartRepository.save(shoppingCart);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }


    @Test
    void save() {
        ShoppingCart shoppingCart1 = new ShoppingCart("yourCart", 3, 20);
        int result = shoppingCartRepository.save(shoppingCart1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                ()-> Assertions.assertEquals(1, shoppingCartRepository.countProductByCartIdAndProductId(shoppingCart1.getCartId(),
                        shoppingCart1.getProductId()))
        );
    }

    @Test
    void duplicate_save(){
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () ->{
            shoppingCartRepository.save(shoppingCart);
        });
        Assertions.assertTrue(throwable.getMessage().contains("이미 장바구니에 상품이 존재합니다."));
    }

    @Test
    void product_not_exist(){
        ShoppingCart shoppingCart2 = new ShoppingCart("ourCart", 10, 210003030);
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () ->{
            shoppingCartRepository.save(shoppingCart2);
        });
        Assertions.assertTrue(throwable.getMessage().contains("물건이 존재하지 않습니다."));
    }


    @Test
    void countProductByCartIdAndProductId() {
        int count = shoppingCartRepository.countProductByCartIdAndProductId(shoppingCart.getCartId(),
                shoppingCart.getProductId());
        Assertions.assertEquals(1, count);
    }

    @Test
    void deleteProductByProductId() {
        int result = shoppingCartRepository.deleteProductByProductId(shoppingCart.getCartId(), shoppingCart.getProductId());
        Assertions.assertEquals(1, result);
    }
}