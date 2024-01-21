package com.aston.homework.mapper;

import com.aston.homework.dto.StudentDto;
import com.aston.homework.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    StudentDto entityToDto(StudentEntity user);
    StudentEntity dtoToEntity(StudentDto user);
}
