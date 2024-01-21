package com.aston.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    private Long id;
    private Long groupID;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return Objects.equals(groupID, that.groupID) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, name);
    }
}
