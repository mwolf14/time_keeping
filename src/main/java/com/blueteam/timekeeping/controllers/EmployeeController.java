package com.blueteam.timekeeping.controllers;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import org.springframework.ui.Model;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/createemployee")
	public String CreateEmployee() {
		return "createEmployee";
	}
	
	@PostMapping("/createemployee")
	public String CreatEmployee(@RequestBody Employee emp){
		Employee newEmployee = new Employee();
		String firstInit = emp.getFirstName().substring(0, 1);
		String lastName = emp.getLastName();
		String userName = firstInit + lastName;
		List<Employee> employees = empRepo.findAll();
		int currentNum = 0;
		for (Employee existingEmployee : employees) {
			if (existingEmployee.getUserName() != null) {
				if (existingEmployee.getUserName().contains(userName)) {
					if (existingEmployee.getUserName().length() == userName.length()) {
						currentNum =1;
					}
					else {
						int userNum = Integer.parseInt(existingEmployee.getUserName().substring(userName.length()));
						if (userNum >= currentNum) {
							currentNum = userNum+1;
						}
					}
				}
			}
		}
		emp.setUserName((currentNum == 0)? userName : userName + currentNum);
		newEmployee.setFirstName(emp.getFirstName());
		newEmployee.setLastName(emp.getLastName());
		newEmployee.setPassword(emp.getPassword());
		empRepo.save(newEmployee);
		empRepo.flush();
		return "employeecreated";
	}
	@GetMapping("/retrieveallusers")
	public String RetrieveAll(Model model){
		List<Employee> employees = empRepo.findAll();
		model.addAttribute("employees" , employees);
		return "retrieveall" ;
	}
	
}
