package com.nhnacademy.shoppingmall.shoppingCart.service.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import com.nhnacademy.shoppingmall.shoppingCart.repository.ShoppingCartRepository;
import com.nhnacademy.shoppingmall.shoppingCart.repository.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepositoryImpl();
    ProductsRepository productsRepository = new ProductRepositoryImpl();

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<Products> findAllProductsByCartId(String cartId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select ProductID from ShoppingCart where  CartID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cartId);

            ResultSet rs = preparedStatement.executeQuery();
            List<Products> productsList = new ArrayList<>();
            while (rs.next()) {
                if (productsRepository.findByProductId(rs.getInt(1)).isPresent()) {
                    productsList.add(productsRepository.findByProductId(rs.getInt(1)).get());
                }
            }
            return productsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShoppingCart> findAllShoppingCartByCartId(String cartId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from ShoppingCart where CartID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cartId);

            ResultSet rs = preparedStatement.executeQuery();
            List<ShoppingCart> shoppingCartList = new ArrayList<>();

            while (rs.next()) {
                if (Objects.nonNull(productsRepository.findByProductId(rs.getInt("ProductId")))) {
                    shoppingCartList.add(
                            new ShoppingCart(rs.getString("CartID"), rs.getInt("Quantity"), rs.getInt("ProductID")));
                }else{
                    throw new RuntimeException("상품이 존재하지 않습니다.");
                }


            }
            return shoppingCartList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int save(String cartId, int productId) {
        return shoppingCartRepository.save(new ShoppingCart(cartId, 1, productId));
    }

    @Override
    public int save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public int delete(String cartId, int productId) {
        return shoppingCartRepository.deleteProductByProductId(cartId, productId);
    }

    @Override
    public int updateQuentity(ShoppingCart shoppingCart) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update ShoppingCart set Quantity = ? where CartID = ? and ProductID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, shoppingCart.getQuantity());
            preparedStatement.setString(2, shoppingCart.getCartId());
            preparedStatement.setInt(3, shoppingCart.getProductId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
