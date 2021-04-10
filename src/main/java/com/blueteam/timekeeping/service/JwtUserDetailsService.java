package com.blueteam.timekeeping.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//
		Employee employee = empRepo.findByUserName(username);
		if (employee == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(employee.getUserName(), employee.getPassword(),
				new ArrayList<>());
	}
	
	public Employee save(Employee user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return empRepo.save(user);
	}
}
