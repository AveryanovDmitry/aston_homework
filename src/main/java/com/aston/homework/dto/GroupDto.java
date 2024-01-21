package com.aston.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private Set<String> teachers;
    private Set<String> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDto groupDto = (GroupDto) o;
        return Objects.equals(name, groupDto.name)
                && Objects.equals(teachers, groupDto.teachers) && Objects.equals(students, groupDto.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teachers, students);
    }
}
