package com.blueteam.timekeeping.controllers;

import java.awt.PageAttributes.MediaType;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@Controller
@CrossOrigin
public class LoginController {
	@Autowired
	private EmployeeRepository empRepo;


	
	@PostMapping(path="/login")
	public String Login( @RequestParam Map<String, String> user, Model model) {
		//get user 
		try {
		
		Employee existingEmployee = empRepo.findByUserName(user.get("myName"));
		//emp.setRecId(existingEmployee.getRecId());
		Employee emp = new Employee();
		String passedInPassword = user.get("password");
		emp.setPassword(passedInPassword);
		if (emp.comparePasswords(existingEmployee.getPassword())) {
			//here we need to add in the user session data to keep the session alive for logged in users
			
			
			if (existingEmployee.getIsSupervisor())
			{ 
				return "manager";
			}else {
				return "employee";
			}
		}else {
			model.addAttribute("error","Password is Invalid");
			return "index";
		}
		}catch(Exception e){
			model.addAttribute("error","User name not found. " + e.toString());
			return "index";
		}
	}
}
