package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public Optional<Address> findById(String address_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from address where address_id = ?";

        ResultSet rs = null;
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, address_id);
            rs = psmt.executeQuery();


            if (rs.next()) {
                log.debug("찾음");
                Address address = new Address(rs.getString("address_id"),rs.getString("address_line1"), rs.getString("address_line2"),
                        rs.getString("city"), rs.getString("sido"), rs.getString("postal_code"));
                return Optional.of(address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into address(address_id,address_line1, address_line2, city, sido, postal_code) values(?,?,?,?,?,?) ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address.getAddress_id());
            ps.setString(2, address.getAddress_line1());
            ps.setString(3, address.getAddress_line2());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getSido());
            ps.setString(6, address.getPostal_code());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByAddressId(String address_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from address where address_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address_id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update address set address_line1 = ?, address_line2 = ?, city = ?, sido = ?, postal_code = ? where address_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address.getAddress_line1());
            ps.setString(2, address.getAddress_line2());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getSido());
            ps.setString(5, address.getPostal_code());
            ps.setString(6, address.getAddress_id());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateModifiedDate(String address_id, Timestamp modified_date) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update address set modified_date = ? where address_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, modified_date);
            ps.setString(2, address_id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByAddressId(String address_id) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from address where address_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, address_id);
            return ps.executeQuery().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
