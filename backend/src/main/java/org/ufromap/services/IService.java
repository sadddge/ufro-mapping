package org.ufromap.services;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Map;

public interface IService<T> {
    List<T> findAll();

    T findById(int id) throws EntityNotFoundException;

    List<T> findByFilter(Map<String, Object> filters) throws EntityNotFoundException;

    T add(T entity) throws BadRequestException;

    T update(T entity) throws EntityNotFoundException;

    void delete(int id) throws EntityNotFoundException;

}
