package com.endava.SpringDataHomework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "mentor", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"mentor", "subjects"})
    private Student mentoredStudent;

    @OneToMany(mappedBy = "teachingProfessor", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"attendingStudents", "teachingProfessor"})
    private List<Subject> taughtSubjects = new ArrayList<>();

    public Professor(String name, Student mentoredStudent, List<Subject> taughtSubjects) {
        this.name = name;
        this.mentoredStudent = mentoredStudent;
        this.taughtSubjects = taughtSubjects;
    }
}
