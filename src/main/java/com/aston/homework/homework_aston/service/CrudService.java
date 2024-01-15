package com.aston.homework.homework_aston.service;

public interface CrudService<T> {
    T read(long id);

    T create(T obj);

    void delete(Long id);

    T update(T obj);
}
