package com.aston.homework.repository;

public interface CrudRepository<T> {
    T getById(Long id);

    T create(T obj);

    void delete(Long id);

    T update(T obj, long id);
}
