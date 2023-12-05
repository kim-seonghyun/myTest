package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.UsersAddress;
import com.nhnacademy.shoppingmall.user.exception.UsersAddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.UserAddressRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserAddressRepositoryImpl implements UserAddressRepository {
    @Override
    public int save(UsersAddress usersAddress) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into users_address(user_id, address_id) values (?,?)";
        if (countByUserIdAndAddressId(usersAddress.getUserId(), usersAddress.getAddressId()) > 0) {
            throw new UsersAddressAlreadyExistsException();

        }

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, usersAddress.getUserId());
            psmt.setString(2, usersAddress.getAddressId());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new UsersAddressAlreadyExistsException();
        }
    }

    @Override
    public int deleteByUserIdAndAddressId(String user_id, String address_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from users_address where user_id = ? and address_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, address_id);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<UsersAddress> findUsersAdressByUsersIdAndAddressId(String user_id, String address_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from users_address where user_id = ? and address_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, address_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(new UsersAddress(rs.getString("user_id"), rs.getString("address_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int countByUserIdAndAddressId(String userId, String addressId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from users_address where user_id = ? and address_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, addressId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
