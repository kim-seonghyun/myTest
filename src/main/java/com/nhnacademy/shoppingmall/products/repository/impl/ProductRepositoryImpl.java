package com.nhnacademy.shoppingmall.products.repository.impl;

import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductRepositoryImpl implements ProductsRepository {

    @Override
    public List<Products> findProductAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Products> productsList = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                log.debug(rs.getString("ProductID"));
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
    public int save(Products products) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Products(categoryid, modelnumber, modelname, productimage, unitcost, description) values (?,?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, products.getCategoryId());
            ps.setString(2, products.getModelNumber());
            ps.setString(3, products.getModelName());
            ps.setString(4, products.getProductImage());
            ps.setInt(5, products.getUnitCost());
            ps.setString(6, products.getDescription());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Products where ProductID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Products> findByProductId(int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where ProductID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new Products(rs.getInt("ProductID"), rs.getInt("CategoryID"), rs.getString("ModelNumber"),
                                rs.getString("ModelName"), rs.getString("ProductImage"), rs.getInt("UnitCost"),
                                rs.getString("Description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            return Optional.empty();
    }

    @Override
    public List<Products> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products";
        List<Products>  productsList=new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
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
    public int update(Products products) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Products set CategoryID = ?, ModelNumber = ?, ModelName = ?, ProductImage = ?, UnitCost = ?, Description = ? where ProductID = ? ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, products.getCategoryId());
            ps.setString(2, products.getModelNumber());
            ps.setString(3, products.getModelName());
            ps.setString(4, products.getProductImage());
            ps.setInt(5, products.getUnitCost());
            ps.setString(6, products.getDescription());
            ps.setInt(7, products.getProductId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByModelNumber(String modelNumber, String categoryName) {
        return 0;
    }

    @Override
    public Optional<Products> findByModelNumber(String modelNumber) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where ModelNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, modelNumber);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new Products(rs.getInt("ProductID"), rs.getInt("CategoryID"), rs.getString("ModelNumber"),
                                rs.getString("ModelName"), rs.getString("ProductImage"), rs.getInt("UnitCost"),
                                rs.getString("Description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Products> findProductsByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Products where CategoryID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);

            ResultSet rs = preparedStatement.executeQuery();
            List<Products> products = new ArrayList<>();
            while (rs.next()) {
                products.add(new Products(rs.getInt("ProductID"), rs.getInt("CategoryID"), rs.getString("ModelNumber"),
                        rs.getString("ModelName"), rs.getString("ProductImage"), rs.getInt("UnitCost"),
                        rs.getString("Description")));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
