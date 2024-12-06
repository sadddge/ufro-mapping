package org.ufromap.core.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.logging.Level;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.ufromap.core.config.DatabaseConnection;
import org.ufromap.core.exceptions.InternalErrorException;

@Log
@AllArgsConstructor
public abstract class BaseRepository<T> implements IRepository<T> {
    protected final Connection connection;

    public BaseRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

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
    public Optional<T> findById(int id) {
        Optional<T> result = Optional.empty();
        String query = "SELECT " + getColumns() + " FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(mapToObject(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute get operation");
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParametersForInsert(statement, obj);
            statement.executeUpdate();

            return findById(getLastInsertId()).orElse(null);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute insert operation");
        }
    }

    @Override
    public T update(T obj) {
        String query = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParametersForUpdate(statement, obj);
            statement.executeUpdate();
            return obj;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute set operation");
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute delete operation");
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
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                results.add(mapToObject(resultSet));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Failed to execute selec operation");
        }
        return results;
    }

    private int getLastInsertId() {
        int lastId = -1;
        try {
            String query = "";

            if (connection.getMetaData().getURL().contains("mysql")) {
                query = "SELECT LAST_INSERT_ID()"; // MySQL
            } else if (connection.getMetaData().getURL().contains("h2")) {
                query = "SELECT MAX(id) FROM clase"; // H2
            }

            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lastId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            log.severe("Error: " + e.getMessage());
            throw new InternalErrorException("Failed to execute select last_insert_id operation");
        }
        return lastId;
    }

}
