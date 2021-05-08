package com.blueteam.timekeeping.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@RestController
public class SeederController {
// TODO: DELETE for dev only needs deleted
	@Autowired
	private EmployeeRepository empRepo;
	
	public ResponseEntity<String> seedEmployees(){
		List<Employee> empList = new ArrayList<Employee>();
		for (int i = 0; i <100; i++) {
			Employee emp = new Employee();
			emp.setFirstName("Test");
			emp.setLastName("Employee");
			emp.setUserName("TEmployee" + i);
			emp.setApproved(true);
			emp.setIsSupervisor(false);
			emp.setPassword("password");
			empList.add(emp);
		}
		try {
		empRepo.saveAll(empList);
		empRepo.flush();
		return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.toString(),  HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
	}

}
