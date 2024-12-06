package org.ufromap.core.base;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IRepository<T> {
    List<T> findAll();

    Optional<T> findById(int id);

    List<T> findByFilter(Map<String, Object> filters);

    T add(T obj);

    T update(T obj);

    boolean delete(int id);
}
