package com.example.test.student;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.test.student.model.Student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Students")
public interface StudentApi {

	@ApiOperation(value = "Get All Students")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class) })
	@GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Student>> getAllStudents();
	
	@ApiOperation(value = "Get Student By Id")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "studentId", required = false, dataType = "string", paramType = "path")
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class) })
	@GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> getStudentById(@PathVariable("studentId") String studentId);
	
	@ApiOperation(value = "Create A New Student")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class) })
	@PostMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createStudent(@RequestBody Student student);
	
	@ApiOperation(value = "Update Student Details")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "studentId", required = false, dataType = "string", paramType = "path")
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class) })
	@PutMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateStudent(@PathVariable("studentId") String studentId, @RequestBody Student student);
	
	@ApiOperation(value = "Update Student Details")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "studentId", required = false, dataType = "string", paramType = "path")
	})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseEntity.class) })
	@DeleteMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteStudent(@PathVariable("studentId") String studentId);
}
