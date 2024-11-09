package org.ufromap.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ufromap.config.DatabaseConnection;

public abstract class BaseRepository<T> implements IRepository<T> {
    private static final Logger logger = Logger.getLogger(BaseRepository.class.getName());

    protected abstract String getTableName();

    protected abstract String getColumns();

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract T mapToObject(ResultSet resultSet);

    protected abstract void setParametersForInsert(PreparedStatement statement, T obj) throws SQLException;

    protected abstract void setParametersForUpdate(PreparedStatement statement, T obj) throws SQLException;

    @Override
    public List<T> findAll() {
        List<T> results = new ArrayList<>();
        String query = "SELECT " + getColumns() + " FROM " + getTableName();
        return execListQuery(results, query);
    }

    @Override
    public T findById(int id) {
        T result = null;
        String query = "SELECT " + getColumns() + " FROM " + getTableName() + " WHERE id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = mapToObject(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query: " + query, e);
        }
        return result;
    }

    @Override
    public List<T> findByFilter(Map<String, Object> filters) {
        List<T> results = new ArrayList<>();
        String query = getQueryByFilters(filters);
        return execListQuery(results, query);
    }

    @Override
    public T add(T obj) {
        String query = getInsertQuery();
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParametersForInsert(statement, obj);
            statement.executeUpdate();
            obj = findById(DatabaseConnection.getLastInsertId());
            return obj;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query: " + query, e);
            return null;
        }
    }

    @Override
    public T update(T obj) {
        String query = getUpdateQuery();
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParametersForUpdate(statement, obj);
            statement.executeUpdate();
            return obj;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query: " + query, e);
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query: " + query, e);
            return false;
        }
    }

    private String getQueryByFilters(Map<String, Object> filters) {
        StringBuilder query = new StringBuilder("SELECT " + getColumns() + " FROM " + getTableName() + " WHERE 1 = 1");
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query.append(" AND ").append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
        }
        return query.toString();
    }

    private List<T> execListQuery(List<T> results, String query) {
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                results.add(mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query: " + query, e);
        }
        return results;
    }

}
