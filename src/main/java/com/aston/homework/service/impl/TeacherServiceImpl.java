package com.aston.homework.service.impl;

import com.aston.homework.repository.TeacherRepository;
import com.aston.homework.repository.implementation.TeacherRepositoryImpl;
import com.aston.homework.dto.TeacherDto;
import com.aston.homework.exception.IncorrectParameters;
import com.aston.homework.mapper.TeacherMapper;

import com.aston.homework.service.TeacherService;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl() {
        this.teacherRepository = new TeacherRepositoryImpl();
    }

    @Override
    public TeacherDto read(long id) {
        return TeacherMapper.INSTANCE.entityToDto(teacherRepository.getById(id));
    }

    public TeacherDto create(TeacherDto teacher) {
        return TeacherMapper.INSTANCE.entityToDto(teacherRepository.create(TeacherMapper.INSTANCE.dtoToEntity(teacher)));
    }

    @Override
    public void delete(Long id) {
        teacherRepository.delete(id);
    }

    @Override
    public TeacherDto update(TeacherDto teacher) {
        if (teacher.getId() == null) {
            throw new IncorrectParameters("you need to specify an id to update");
        }
        return TeacherMapper.INSTANCE.entityToDto(teacherRepository
                .update(TeacherMapper.INSTANCE.dtoToEntity(teacher), teacher.getId()));
    }
}
