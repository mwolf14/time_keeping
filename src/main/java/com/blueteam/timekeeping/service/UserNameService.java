package com.blueteam.timekeeping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@Service
public class UserNameService {

	@Autowired
	private EmployeeRepository empRepo;
	
	public String createUserName(String name) {
		String firstInit = name.substring(0, 1);
		String[] lastName = name.split(" ");
		String userName = firstInit + lastName[1];
		List<Employee> employees = empRepo.findAll();
		int currentNum = 0;
		for (Employee emp : employees) {
			if (emp.getUserName().contains(userName)) {
				int userNum = Integer.parseInt(emp.getUserName().substring(userName.length()));
				if (userNum > currentNum) {
					currentNum = userNum;
				}
			}
		}
		
		return (currentNum == 0)? userName : userName + currentNum;
	}

}
