package com.aston.homework.mapper;

import com.aston.homework.dto.GroupDto;
import com.aston.homework.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupDto entityToDto(GroupEntity entity);

    GroupEntity dtoToEntity(GroupDto dto);
}
