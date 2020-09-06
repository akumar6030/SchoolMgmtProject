package com.example.test.student.service;

import java.util.List;

import com.example.test.student.model.Student;

public interface StudentService {
	
	public List<Student> getAllStudents() ;

	
	public Student getStudentById(String studentId);

	
	public String createStudent(Student student);

	
	public String updateStudent(String studentId, Student student);

	
	public String deleteStudent(String studentId);


}
