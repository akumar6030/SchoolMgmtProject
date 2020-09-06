package com.example.test.student.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.test.student.model.Address;
import com.example.test.student.model.Student;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("namedTemplate")
	NamedParameterJdbcTemplate parameterJdbcTemplate;
	
	@Override
	public List<Student> getAllStudents() {
		String getAllStudentQuery = "Select s.Id, s.Name, s.FathersName, s.MothersName, a.AddressLine1, a.AddressLine2, a.City, a.State, a.Country, a.Pincode from student_details s, address a Where s.id=a.id";
		List<Student> list = null;
		try {
			list = parameterJdbcTemplate.query(getAllStudentQuery, new RowMapper<Student>() {

				@Override
				public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
					Student s = new Student();
					Address a = new Address();
					a.setAddressLine1(rs.getString("AddressLine1"));
					a.setAddressLine2(rs.getString("AddressLine2"));
					a.setCity(rs.getString("City"));
					a.setState(rs.getString("State"));
					a.setCountry(rs.getString("Country"));
					a.setPincode(rs.getInt("Pincode"));
					
					s.setStudentId(rs.getString("Id"));
					s.setStudentName(rs.getString("Name"));
					s.setFathersName(rs.getString("FathersName"));
					s.setMothersName(rs.getString("MothersName"));
					s.setAddress(a);
					return s;
				}
			
			});
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Something went wrong : Unable to fetch the Records");
		}
		return list;
	}

	@Override
	public Student getStudentById(String studentId) {
		String getAllStudentQuery = "Select s.Id, s.Name, s.FathersName, s.MothersName, a.AddressLine1, a.AddressLine2, a.City, a.State, a.Country, a.Pincode from student_details s, address a Where a.id=:Sid";
		Student list = null;
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("Sid", studentId);
			list = this.parameterJdbcTemplate.queryForObject(getAllStudentQuery, param, new RowMapper<Student>() {

				@Override
				public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
					Student s = new Student();
					Address a = new Address();
					a.setAddressLine1(rs.getString("AddressLine1"));
					a.setAddressLine2(rs.getString("AddressLine2"));
					a.setCity(rs.getString("City"));
					a.setState(rs.getString("State"));
					a.setCountry(rs.getString("Country"));
					a.setPincode(rs.getInt("Pincode"));
					
					s.setStudentId(rs.getString("Id"));
					s.setStudentName(rs.getString("Name"));
					s.setFathersName(rs.getString("FathersName"));
					s.setMothersName(rs.getString("MothersName"));
					s.setAddress(a);
					return s;
				}
			
			});
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Something went wrong : Unable to fetch the Record for student id="+studentId);
		}
		return list;
	}

	@Override
	public void createStudent(Student student) {
		String createStudentQuery = "Insert into student_details(Id, Name, FathersName, MothersName) values(?,?,?,?)";
		String createAddressQuery = "Insert into address(AddressLine1, AddressLine2, City, State, Country, Pincode, Id) values(?,?,?,?,?,?,?)";
		try {
			Boolean execute = jdbcTemplate.execute(createStudentQuery, new PreparedStatementCallback<Boolean>(){

				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, student.getStudentId());
					ps.setString(2, student.getStudentName());
					ps.setString(3, student.getFathersName());
					ps.setString(4, student.getMothersName());
					return ps.execute();
				}
				
			});
			if(!execute) {
				log.info("Data inserted into student_details table successfully.");
			}
			Address address = student.getAddress();
			Boolean execute1 = jdbcTemplate.execute(createAddressQuery, new PreparedStatementCallback<Boolean>(){

				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, address.getAddressLine1());
					ps.setString(2, address.getAddressLine2());
					ps.setString(3, address.getCity());
					ps.setString(4, address.getState());
					ps.setString(5, address.getCountry());
					ps.setObject(6, address.getPincode());
					ps.setString(7, student.getStudentId());
					return ps.execute();
				}
				
			});
			if(!execute1) {
				log.info("Data inserted into address table successfully.");
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Something went wrong : Unable to create record");
		}
	}

	@Override
	public void updateStudent(String studentId, Student student) {
		String updateQuery = "Update student_details set Name=:name, FathersName = :Fname, MothersName= :Mname where Id= :Sid";
		String updateAddressQuery = "Update address set AddressLine1= :Al1, AddressLine2= :Al2, City= :City, State= :State, Country= :Country, Pincode=:Pin where Id= :Sid";
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("Sid", studentId);
			param.put("name", student.getStudentName());
			param.put("Fname", student.getFathersName());
			param.put("Mname", student.getMothersName());
			param.put("Al1", student.getAddress().getAddressLine1());
			param.put("Al2", student.getAddress().getAddressLine2());
			param.put("City", student.getAddress().getCity());
			param.put("State", student.getAddress().getState());
			param.put("Country", student.getAddress().getCountry());
			param.put("Pin", student.getAddress().getPincode());
			int updatedRow = this.parameterJdbcTemplate.update(updateQuery, param);
			int updatedRow1 = this.parameterJdbcTemplate.update(updateAddressQuery, param);
			if(updatedRow>0) {
				log.info("StudentDetails updated Successfully for student id = "+studentId);
			}
			if(updatedRow1>0) {
				log.info("Address updated Successfully for student id = "+studentId);
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Something went wrong : Unable to delete record");
		}
	}

	@Override
	public void deleteStudent(String studentId) {
		String deleteQuery = "Delete From student_details where id=:StudentId";
		try {
			Map<String, String> param = new HashMap<>();
			param.put("StudentId", studentId);
			int updatedRow = this.parameterJdbcTemplate.update(deleteQuery, param);
			if(updatedRow>0) {
				log.info("Record Deleted Successfully for student id = "+studentId);
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException("Something went wrong : Unable to delete record");
		}
	}

}
