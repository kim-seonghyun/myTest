package com.nhnacademy.shoppingmall.shoppingCart.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import com.nhnacademy.shoppingmall.shoppingCart.repository.ShoppingCartRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @Override
    public int save(ShoppingCart shoppingCart) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into ShoppingCart(cartid, quantity, productid) VALUES (?, ?, ?)";

        if(productsRepository.findByProductId(shoppingCart.getProductId()).isEmpty()){
            throw new RuntimeException("물건이 존재하지 않습니다.");
        }

        if (countProductByCartIdAndProductId(shoppingCart.getCartId(), shoppingCart.getProductId()) > 0) {
            throw new RuntimeException("이미 장바구니에 상품이 존재합니다.");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, shoppingCart.getCartId());
            preparedStatement.setInt(2, shoppingCart.getQuantity());
            preparedStatement.setInt(3, shoppingCart.getProductId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countProductByCartIdAndProductId(String cartId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from ShoppingCart where ProductID = ? and CartID = ?";

        if(Objects.isNull(productsRepository.findByProductId(productId))){
            throw new RuntimeException("물건이 존재하지 않습니다.");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, cartId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int deleteProductByProductId(String cartId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from ShoppingCart where CartID= ? and ProductID = ?";

        if(Objects.isNull(productsRepository.findByProductId(productId))){
            throw new RuntimeException("물건이 존재하지 않습니다.");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cartId);
            preparedStatement.setInt(2, productId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
