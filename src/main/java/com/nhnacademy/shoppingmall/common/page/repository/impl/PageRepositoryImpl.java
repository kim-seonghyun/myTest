package com.nhnacademy.shoppingmall.common.page.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.page.repository.PageReopsitory;
import com.nhnacademy.shoppingmall.products.domain.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageRepositoryImpl implements PageReopsitory {
    @Override
    public int productTotalPage(int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from Products";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int totalRows = rs.getInt(1);
                return Math.round(totalRows / pageSize);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int productTotalPage(int pageSize, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from Products where CategoryID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int totalRows = rs.getInt(1);
                return Math.round(totalRows / pageSize);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Products> getProductContents(int pageNumber, int pageSize) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products limit ?, ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageNumber * pageSize);
            preparedStatement.setInt(2, pageSize);

            ResultSet rs = preparedStatement.executeQuery();
            List<Products> productsList = new ArrayList<>();
            while (rs.next()) {
                productsList.add(new Products(rs.getInt("ProductID"), rs.getInt("CategoryID"), rs.getString("ModelNumber"),
                        rs.getString("ModelName"), rs.getString("ProductImage"), rs.getInt("UnitCost"),
                        rs.getString("Description")));
            }
            return productsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Products> getProductContents(int pageNumber, int pageSize, int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where Products.CategoryID = ? limit ?, ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, pageNumber * pageSize);
            preparedStatement.setInt(3, pageSize);

            ResultSet rs = preparedStatement.executeQuery();
            List<Products> productsList = new ArrayList<>();
            while (rs.next()) {
                productsList.add(new Products(rs.getInt("ProductID"), rs.getInt("CategoryID"), rs.getString("ModelNumber"),
                        rs.getString("ModelName"), rs.getString("ProductImage"), rs.getInt("UnitCost"),
                        rs.getString("Description")));
            }
            return productsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
