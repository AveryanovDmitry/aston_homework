package com.aston.homework.homework_aston.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GroupEntity {
    private Long id;
    private String name;
    private Set<String> teachers;
    private Set<String> students;

    public GroupEntity(){
        teachers = new HashSet<>();
        students = new HashSet<>();
    }
}
