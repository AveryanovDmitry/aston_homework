package com.aston.homework.homework_aston.service.impl;

import com.aston.homework.homework_aston.dao.TeacherRepository;
import com.aston.homework.homework_aston.dao.implementation.TeacherRepositoryImpl;
import com.aston.homework.homework_aston.dto.TeacherDto;
import com.aston.homework.homework_aston.exception.IncorrectParameters;
import com.aston.homework.homework_aston.mapper.TeacherMapper;

import com.aston.homework.homework_aston.service.TeacherService;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl( ) {
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
