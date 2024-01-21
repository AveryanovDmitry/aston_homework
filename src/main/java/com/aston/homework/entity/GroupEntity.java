package com.aston.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
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

    public GroupEntity() {
        teachers = new HashSet<>();
        students = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(name, that.name)
                && Objects.equals(teachers, that.teachers) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teachers, students);
    }
}
