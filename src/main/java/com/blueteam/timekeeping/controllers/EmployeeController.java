/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: this controller will handle creation of a new employee, and retrieving username 
*/
package com.blueteam.timekeeping.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

@Controller
@CrossOrigin
public class EmployeeController {

	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/createemployee")
	public String CreateEmployee() {
		return "createEmployee";
	}
	
	@PostMapping(value="/createemployee", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String CreatEmployee( @RequestParam Map<String, String> user, Model model, HttpServletRequest request){
		//this needs to work with map not the incoming model base..... fix me
		try {
		char firstInit = user.get("firstName").toString().charAt(0);
		String lastName = user.get("lastName").toString();
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
		Employee newEmp = new Employee();
		newEmp.setUserName((currentNum == 0)? userName : userName + currentNum);
		newEmp.setPassword(user.get("password").toString());
		newEmp.setFirstName(user.get("firstName").toString());
		newEmp.setLastName(user.get("lastName").toString());
		//this needs to be deleted for production
		empRepo.save(newEmp);
		empRepo.flush();
		model.addAttribute("userName",newEmp.getUserName());
		return "employeecreated";
		}catch(Exception ex) {
			return ex.toString();
		}
	}
	
	@GetMapping("/retrieveallusers")
	public String RetrieveAll(Model model, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		/*if ( msgs == null) {
			return "index";
		} else if (msgs.get(3) != "manager") {
			return "forbiden";
		}*/
		List<Employee> employees = empRepo.findAll();
		model.addAttribute("employees" , employees);
		return "retrieveallusers" ;
	}
	
	@PostMapping("/finduser")
	public String RetrieveUser( @RequestParam Map<String, String> user, Model model, HttpServletRequest request){
		//TODO find user by first name last name
		return "recoveremployee";
	}
	
	@PostMapping("/updatepassword")
	public String UpdatePassword( @RequestParam Map<String, String> user, Model model, HttpServletRequest request){
		//TODO take a user name and current password, then update to the new password
		return "recoveremployee";
	}
}
