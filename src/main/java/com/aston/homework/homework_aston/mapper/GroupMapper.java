package com.aston.homework.homework_aston.mapper;

import com.aston.homework.homework_aston.dto.GroupDto;
import com.aston.homework.homework_aston.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDto entityToDto(GroupEntity entity);

    GroupEntity dtoToEntity(GroupDto dto);
}
