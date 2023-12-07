package com.nhnacademy.shoppingmall.shoppingCart.repository.impl;


import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
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
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();

    ShoppingCart shoppingCart;
    Products testProduct;
    Categories categories;
    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        categories = new Categories("category1");
        categoriesRepository.save(categories);
        categories = categoriesRepository.findByCategoriesName("category1").get();
        testProduct = new Products( categories.getCategoryID(), "model1", "name1", "image1", 100, "description1");
        productsRepository.save(testProduct);
        testProduct = productsRepository.findByModelNumber(testProduct.getModelNumber()).get();
        shoppingCart = new ShoppingCart("myCart", 3, testProduct.getProductId());
        shoppingCartRepository.save(shoppingCart);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }


    @Test
    void save() {
        ShoppingCart shoppingCart1 = new ShoppingCart("yourCart", 3, testProduct.getProductId());
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