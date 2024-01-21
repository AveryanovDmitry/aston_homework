package com.aston.homework.service.impl;

import com.aston.homework.repository.implementation.StudentRepositoryImpl;
import com.aston.homework.dto.StudentDto;
import com.aston.homework.repository.StudentRepository;
import com.aston.homework.exception.IncorrectParameters;
import com.aston.homework.mapper.StudentMapper;
import com.aston.homework.service.StudentService;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl() {
        this.studentRepository = new StudentRepositoryImpl();
    }

    @Override
    public StudentDto read(long id) {
        return StudentMapper.INSTANCE.entityToDto(studentRepository.getById(id));
    }

    public StudentDto create(StudentDto user) {
        return StudentMapper.INSTANCE.entityToDto(
                studentRepository.create(StudentMapper.INSTANCE.dtoToEntity(user)));
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public StudentDto update(StudentDto actor) {
        if (actor.getId() == null) {
            throw new IncorrectParameters("you need to specify an id to update");
        }
        return StudentMapper.INSTANCE.entityToDto(
                studentRepository.update(StudentMapper.INSTANCE.dtoToEntity(actor), actor.getId()));
    }
}
