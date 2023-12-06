package com.nhnacademy.shoppingmall.categories.repository.impl;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoriesRepositoryImpl implements CategoriesRepository {

    @Override
    public int save(Categories categories) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Categories(CategoryName) values (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, categories.getCategoryName());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Categories categories) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Categories set CategoryName = ? where CategoryId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categories.getCategoryName());
            preparedStatement.setInt(2, categories.getCategoryID());
            return preparedStatement.executeUpdate();
        } catch (
                SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public int updateByCategoriesID(Categories categories) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Categories set CategoryName = ? where CategoryID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categories.getCategoryName());
            preparedStatement.setInt(2, categories.getCategoryID());
            return preparedStatement.executeUpdate();
        } catch (
                SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public int deleteByCategoriesId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Categories where CategoryID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByCategoriesName(String categoryName) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Categories where CategoryName = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCategoriesName(String categoryName) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select count(*) from Categories where CategoryName = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);

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
    public Optional<Categories> findByCategoriesName(String categoryName) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * from Categories where CategoryName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                log.debug(String.valueOf(rs.getInt("CategoryID")));
                log.debug(rs.getString("CategoryName"));
                return Optional.of(new Categories(rs.getInt("CategoryID"), rs.getString("CategoryName")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Categories> findByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select * from Categories where CategoryID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                log.debug(String.valueOf(rs.getInt("CategoryID")));
                log.debug(rs.getString("CategoryName"));
                return Optional.of(new Categories(rs.getInt("CategoryID"), rs.getString("CategoryName")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
