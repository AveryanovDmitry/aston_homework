package com.aston.homework.mapper;

import com.aston.homework.entity.TeacherEntity;
import com.aston.homework.dto.TeacherDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherEntity dtoToEntity(TeacherDto dto);

    TeacherDto entityToDto(TeacherEntity entity);
}
