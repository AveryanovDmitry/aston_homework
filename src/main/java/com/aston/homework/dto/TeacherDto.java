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
public class TeacherDto {
    private Long id;
    private String name;
    private Set<Long> idsGroups;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto that = (TeacherDto) o;
        return Objects.equals(name, that.name) && Objects.equals(idsGroups, that.idsGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, idsGroups);
    }
}
