package com.aston.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private Long groupId;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, name);
    }
}
