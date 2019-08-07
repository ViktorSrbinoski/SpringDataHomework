package com.endava.SpringDataHomework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subject {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"taughtSubjects", "mentoredStudent"})
    private Professor teachingProfessor;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"mentor", "subjects"})
    private Set<Student> attendingStudents = new HashSet<>();

    public Subject(String name, Professor teachingProfessor, Set<Student> attendingStudents) {
        this.name = name;
        this.teachingProfessor = teachingProfessor;
        this.attendingStudents = attendingStudents;
    }
}
