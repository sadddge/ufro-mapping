package org.ufromap.repositories;

import java.util.List;
import java.util.Map;

public interface IRepository<T> {
    List<T> findAll();
    T findById(int id);
    List<T> findByFilter(Map<String, Object> filters);
    T add(T obj);
    T update(T obj);
    boolean delete(int id);
}
