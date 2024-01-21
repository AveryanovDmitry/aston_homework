package com.aston.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TeacherEntity {
    private Long id;
    private String name;
    private Set<Long> idsGroups;
    public TeacherEntity(){
        idsGroups = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherEntity that = (TeacherEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(idsGroups, that.idsGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, idsGroups);
    }
}
