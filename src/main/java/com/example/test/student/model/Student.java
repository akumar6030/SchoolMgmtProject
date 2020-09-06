package com.example.test.student.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Student {

	private String studentId;
	private String studentName;
	private String fathersName;
	private String mothersName;
	private Address address;
	
}
