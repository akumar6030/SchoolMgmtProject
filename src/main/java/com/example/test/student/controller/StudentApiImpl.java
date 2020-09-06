package com.example.test.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.student.StudentApi;
import com.example.test.student.model.Student;
import com.example.test.student.service.StudentService;

@RestController
public class StudentApiImpl implements StudentApi {

	@Autowired
	StudentService studentService;
	
	@Override
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> allStudents = studentService.getAllStudents();
		if(allStudents==null && allStudents.isEmpty()) {
			new RuntimeException("No Record Found");
		}
		return new ResponseEntity<List<Student>>(allStudents, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Student> getStudentById(@PathVariable("studentId") String studentId) {
		Student studentById = studentService.getStudentById(studentId);
		return new ResponseEntity<Student>(studentById, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> createStudent(@RequestBody Student student) {
		String createStudent = studentService.createStudent(student);
		return new ResponseEntity<>(createStudent, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> updateStudent(@PathVariable("studentId") String studentId, @RequestBody Student student) {
		Student studentById = studentService.getStudentById(studentId);
		if(studentById!=null) {
			String updateStudent = studentService.updateStudent(studentId, student);
			return new ResponseEntity<>(updateStudent, HttpStatus.OK);
		}
		return  new ResponseEntity<>("Record Not Found", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteStudent(@PathVariable("studentId") String studentId) {
		Student studentById = studentService.getStudentById(studentId);
		if(studentById!=null) {
			String updateStudent = studentService.deleteStudent(studentId);
			return new ResponseEntity<>(updateStudent, HttpStatus.OK);
		}
		return  new ResponseEntity<>("Record Not Found", HttpStatus.OK);
	}

}
