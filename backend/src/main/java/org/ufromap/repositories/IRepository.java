package org.ufromap.repositories;

import org.ufromap.models.Edificio;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface IRepository {
    List<?> findAll();
    Object findById(int id);
    List<?> findByFilter(Map<String, Object> filters);
    void add(Object obj);
    void update(Object obj);

    void delete(int id);
    Object mapToObject(ResultSet resultSet);
}
