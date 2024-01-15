package com.aston.homework.homework_aston.mapper;

import com.aston.homework.homework_aston.entity.TeacherEntity;
import com.aston.homework.homework_aston.dto.TeacherDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherEntity dtoToEntity(TeacherDto dto);

    TeacherDto entityToDto(TeacherEntity entity);
}
