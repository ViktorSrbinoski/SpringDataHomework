package com.endava.SpringDataHomework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY) // In a world where a professor can be a mentor to only one student
    @JsonIgnoreProperties({"mentoredStudent", "taughtSubjects"})
    private Professor mentor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_subject",
            joinColumns = {@JoinColumn(name = "student")},
            inverseJoinColumns = {@JoinColumn(name = "subject")}
    )
    @JsonIgnoreProperties({"teachingProfessor", "attendingStudents"})
    private Set<Subject> subjects = new HashSet<>();


    public Student(String name, Professor mentor, Set<Subject> subjects) {
        this.name = name;
        this.mentor = mentor;
        this.subjects = subjects;
    }
}
