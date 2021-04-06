package com.blueteam.timekeeping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.blueteam.timekeeping.models.*;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@Service
public class DatabaseSeeder {

	@Autowired
	private EmployeeRepository empRepo;
	
	private List<Employee> employees = new ArrayList<Employee>();
	private List<TimeCard> timecards;
	
	//setting for how many records are created
	private int numEmployees = 1000;
	private int numTimeCards = 10000;
	private int numAudits = 100;
	
	
	public DatabaseSeeder() {
		System.out.println("In the seeder Const");
		CreateEmployeeList();
		//CreateTimeCardList();
	}
	private void CreateTimeCardList() {
		// TODO Auto-generated method stub
		
	}
	private void CreateEmployeeList() {
		String[] fname = {"FName1","FName2","FName3","FName4","FName5","FName6","FName7"}; 
		String[] lname = {"LName1","LName2", "LName3","LName4","LName5","LName6","LName7"};
		String[] pword= {"password1","password2","password3","password4","password5","password6","password7"};
		//number set higher the base (1000) to show recId is working correctly
		for(int i=0; i<numEmployees; i++) {
			int f = (int) (Math.random()*7);
			int l = (int) (Math.random()*7);
			int p = (int) (Math.random()*7);
			System.out.println("first: " +f + " last: " + l);
			Employee employee = new Employee();
			employee.setName(fname[f] +" "+ lname[l]);
			employee.setPassword(pword[p]);
			employees.add(employee);	
		}
		//this employees is added everytime for testing the password hashing algorithm
		Employee testEmployee = new Employee();
		testEmployee.setName("john doe");
		testEmployee.setPassword("knownPassword");
		employees.add(testEmployee);
	}
	public  void Seed() {
		SeedEmployees();
		SeedTimeCards();
	}
	
	@EventListener
	public void seed(ContextRefreshedEvent event) {
		SeedEmployees();
	}
	
	private  void SeedTimeCards() {
		// TODO Auto-generated method stub
		
	}
	private void SeedEmployees() {
		empRepo.saveAll(employees);
		empRepo.flush();
	}

}
