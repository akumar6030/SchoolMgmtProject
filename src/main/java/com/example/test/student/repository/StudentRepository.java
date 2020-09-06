package com.example.test.student.repository;

import java.util.List;

import com.example.test.student.model.Student;

public interface StudentRepository {

	public List<Student> getAllStudents() ;

	
	public Student getStudentById(String studentId);

	
	public void createStudent(Student student);

	
	public void updateStudent(String studentId, Student student);

	
	public void deleteStudent(String studentId);



}
