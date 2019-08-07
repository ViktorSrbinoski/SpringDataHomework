package com.endava.SpringDataHomework;

import com.endava.SpringDataHomework.model.Professor;
import com.endava.SpringDataHomework.model.Student;
import com.endava.SpringDataHomework.model.Subject;
import com.endava.SpringDataHomework.repository.ProfessorRepository;
import com.endava.SpringDataHomework.repository.StudentRepository;
import com.endava.SpringDataHomework.repository.SubjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringDataHomeworkApplication implements CommandLineRunner {

	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataHomeworkApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Professor vesna = new Professor();
		Professor daniel = new Professor();

		Student viktor = new Student();
		Student nikola = new Student();
		Student aleksandar = new Student();

		Subject math = new Subject();
		Subject android = new Subject();
		Subject networking = new Subject();

		vesna.setName("Vesna Andova");
		daniel.setName("Daniel Denkovski");
		viktor.setName("Viktor Srbinoski");
		nikola.setName("Nikola Mladenovski");
		aleksandar.setName("Aleksandar Dimov");
		math.setName("Math");
		android.setName("Andorid");
		networking.setName("Networking");

		viktor.setMentor(vesna);
		nikola.setMentor(daniel);

		math.setTeachingProfessor(vesna);
		networking.setTeachingProfessor(daniel);
		android.setTeachingProfessor(daniel);

		viktor.getSubjects().add(math);
		viktor.getSubjects().add(networking);
		viktor.getSubjects().add(android);
		nikola.getSubjects().add(android);
		aleksandar.getSubjects().add(networking);


		vesna.setMentoredStudent(viktor);
		daniel.setMentoredStudent(nikola);

		vesna.getTaughtSubjects().add(math);
		daniel.getTaughtSubjects().add(android);
		daniel.getTaughtSubjects().add(networking);

		math.getAttendingStudents().add(viktor);
		networking.getAttendingStudents().add(viktor);
		networking.getAttendingStudents().add(aleksandar);
		android.getAttendingStudents().add(viktor);
		android.getAttendingStudents().add(nikola);


		studentRepository.save(viktor);
		studentRepository.save(nikola);
		studentRepository.save(aleksandar);

		professorRepository.save(vesna);
		professorRepository.save(daniel);

		subjectRepository.save(math);
		subjectRepository.save(android);
		subjectRepository.save(networking);

		List<Subject> allSubjects = subjectRepository.findAll();
		List<Professor> allProfessors = professorRepository.findAll();
		List<Student> allStudents = studentRepository.findAll();

		// Printing everything
		System.out.println("\nStudents: ");
		allStudents.forEach(student -> {
			try {
				System.out.println(new ObjectMapper().writeValueAsString(student));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		System.out.println();

		System.out.println("Professors: ");
		allProfessors.forEach(professor -> {
			try {
				System.out.println(new ObjectMapper().writeValueAsString(professor));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		System.out.println();

		System.out.println("Subjects: ");
		allSubjects.forEach(subject -> {
			try {
				System.out.println(new ObjectMapper().writeValueAsString(subject));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		System.out.println();


		// Try out the relationships:

		// One to one
		System.out.println("All mentored students:");
		allProfessors.forEach(professor -> System.out.println("Student "+professor.getMentoredStudent().getName()+" has mentor "+professor.getName()));
		System.out.println();

		// One to many
		System.out.println("All subjects per professor:");
		allProfessors.forEach(professor -> {
			System.out.println("Professor's name: "+professor.getName());
			System.out.println("Subjects: ");
			professor.getTaughtSubjects().forEach(subject -> System.out.println(subject.getName()));
			System.out.println();
		});

		// Many to many
		System.out.println("All subjects per student:");
		allStudents.forEach(student -> {
			System.out.println("Student's name: "+student.getName());
			System.out.println("Subjects: ");
			student.getSubjects().forEach(subject -> System.out.println(subject.getName()));
			System.out.println();
		});
	}
}
