package com.aston.homework.homework_aston.dao;

public interface CrudRepository<T> {
    T getById(Long id);
    T create(T user);
    void delete(Long id);

    T update(T user, long id);
}
