package com.blueteam.timekeeping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@Controller
@CrossOrigin
public class LoginController {
	@Autowired
	private EmployeeRepository empRepo;


	
	@PostMapping("/login")
	public String Login(@RequestBody Employee  emp) {
		//get user 
		try {
		Employee existingEmployee = empRepo.findByUserName(emp.getUserName());
		emp.setRecId(existingEmployee.getRecId());
		String passedInPassword = emp.getPassword();
		emp.setPassword(passedInPassword);
		if (emp.comparePasswords(existingEmployee.getPassword())) {
			return "landingpage";
		}
		}catch(Exception e){
			return "userNotFound";
		}	
		//if something goes wrong return to index
		return "index";
	}


}
