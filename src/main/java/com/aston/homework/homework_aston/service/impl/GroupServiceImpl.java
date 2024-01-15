package com.aston.homework.homework_aston.service.impl;

import com.aston.homework.homework_aston.dao.GroupRepository;
import com.aston.homework.homework_aston.dao.implementation.GroupRepositoryImpl;
import com.aston.homework.homework_aston.dto.GroupDto;
import com.aston.homework.homework_aston.exception.IncorrectParameters;
import com.aston.homework.homework_aston.mapper.GroupMapper;
import com.aston.homework.homework_aston.service.GroupService;

public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    public GroupServiceImpl() {
        this.groupRepository = new GroupRepositoryImpl();
    }

    @Override
    public GroupDto read(long id) {
        return GroupMapper.INSTANCE.entityToDto(groupRepository.getById(id));
    }

    public GroupDto create(GroupDto performance) {
        return GroupMapper.INSTANCE.entityToDto(
                groupRepository.create(GroupMapper.INSTANCE.dtoToEntity(performance)));
    }

    @Override
    public void delete(Long id) {
        groupRepository.delete(id);
    }

    @Override
    public GroupDto update(GroupDto performance) {
        if (performance.getId() == null) {
            throw new IncorrectParameters("you need to specify an id to update");
        }
        return GroupMapper.INSTANCE.entityToDto(
                groupRepository.update(GroupMapper.INSTANCE.dtoToEntity(performance), performance.getId()));
    }
}
