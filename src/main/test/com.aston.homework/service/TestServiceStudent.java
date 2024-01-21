package com.aston.homework.service;

import com.aston.homework.dto.StudentDto;
import com.aston.homework.entity.StudentEntity;
import com.aston.homework.repository.implementation.StudentRepositoryImpl;
import com.aston.homework.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceStudent {
    @Mock
    private StudentRepositoryImpl repository;

    private StudentServiceImpl service = new StudentServiceImpl();

    private StudentDto dto = new StudentDto();

    private StudentEntity entity = new StudentEntity();

    @BeforeEach
    void init() {
        dto.setName("aaa");
        dto.setGroupId(1L);

        entity.setId(1L);
        entity.setName("aaa");
        entity.setGroupId(1L);
    }

    @Test
    void testAdd() {
        System.out.println(entity.getGroupId());
        System.out.println(dto.getGroupId());
        when(repository.create(entity)).thenReturn(entity);
        StudentDto dto1 = service.create(dto);
        assertEquals(dto.getName(), dto1.getName());
        assertEquals(dto.getGroupId(), dto1.getGroupId());
    }
//
//    @Test
//    void testGetAll() {
//        when(repository.findAll()).thenReturn(List.of(PRODUCT));
//        assertEquals(List.of(PRODUCT_DTO), service.getAll());
//    }
//
//    @Test
//    void testGetById() {
//        when(repository.findById(anyLong())).thenReturn(PRODUCT);
//        assertEquals(PRODUCT_DTO, service.get(1L));
//    }
//
//    @Test
//    void testUpdate() throws SQLException {
//        service.update(PRODUCT_DTO);
//        verify(repository).update(PRODUCT);
//    }
//
//    @Test
//    void testDelete() throws SQLException {
//        service.remove(1L);
//        verify(repository).delete(anyLong());
//    }
}