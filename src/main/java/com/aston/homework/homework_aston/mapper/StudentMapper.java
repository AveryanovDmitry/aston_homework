package com.aston.homework.homework_aston.mapper;

import com.aston.homework.homework_aston.dto.StudentDto;
import com.aston.homework.homework_aston.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    StudentDto entityToDto(StudentEntity user);
    StudentEntity dtoToEntity(StudentDto user);
}
