package com.aston.homework.homework_aston.entity;

import lombok.*;

import java.util.HashSet;
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
}
