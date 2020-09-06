package com.example.test.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.student.model.Student;
import com.example.test.student.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public List<Student> getAllStudents() {
		List<Student> allStudents = studentRepository.getAllStudents();
		return allStudents;
	}

	@Override
	public Student getStudentById(String studentId) {
		Student studentById = studentRepository.getStudentById(studentId);
		return studentById;
	}

	@Override
	public String createStudent(Student student) {
		studentRepository.createStudent(student);
		return "Record Created Successfully";
	}

	@Override
	public String updateStudent(String studentId, Student student) {
		studentRepository.updateStudent(studentId, student);
		return "Record Updated Successfully";
	}

	@Override
	public String deleteStudent(String studentId) {
		studentRepository.deleteStudent(studentId);
		return "Record Deleted Successfully";
	}

}
