/* Author: Matt Wolf
 * Date: 5/2/21
 * Desc: this controller will handle creation of a new employee, and retrieving username
 * Rev History:
 * 4/17/21 - initial creation
 * 4/27/21 - stubbed methods. Changed signatures to use ajax for some calls
 * 5/2/21 - refactored functionality into methods 
*/
package com.blueteam.timekeeping.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import org.springframework.ui.Model;


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
	@GetMapping("/retrieveallemployees")
	public ResponseEntity<String> RetrieveAll(Model model, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity("Please log in", HttpStatus.FORBIDDEN);
		}			
		List<Employee> employees = empRepo.findAll();
		model.addAttribute("employees" , employees);
		return new ResponseEntity(employees, HttpStatus.OK);
	}
	@PostMapping("/findemployee")
	public ResponseEntity<String> RetrieveEmployee( @RequestParam Map<String, String> user, Model model, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity("Please log in", HttpStatus.FORBIDDEN);
		}
		List<Employee> empList = empRepo.getAllByLastName(user.get("lastname"));
		if (empList.size() != 1) {
			return new ResponseEntity<String>("More then one result", HttpStatus.CONFLICT); 
		}
		return	new ResponseEntity<String>(empList.get(0).getUserName(), HttpStatus.OK);	
	}
	@PostMapping("/updatepassword")
	public ResponseEntity<String> UpdatePassword( @RequestParam Map<String, String> user, Model model, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity("Please log in", HttpStatus.FORBIDDEN);
		}
		try{
			Employee emp = empRepo.getOne(Integer.parseInt(user.get("id")));
			if (emp.getPassword()== user.get("oldpassword"))
				{
				emp.setPassword(user.get("newpassword"));
				return new ResponseEntity("success", HttpStatus.OK);
				}
			else {
				return new ResponseEntity("Old password invalid", HttpStatus.CONFLICT);
			}
			
		} catch (Exception ex) {
				return new ResponseEntity("Something went wrong", HttpStatus.NOT_FOUND);
		}
		
	}
	@GetMapping("/approveemployee/{id}")
	public ResponseEntity<String> ApproveEmployee(@PathVariable("id") int id, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity("Please log in", HttpStatus.FORBIDDEN);
		}
		try{
		Employee emp = empRepo.getOne(id);
		emp.setApproved(true);
		emp.setActive(true);
		return new ResponseEntity("success", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity("Something went wrong", HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/deactivateuser/{id}")
	public ResponseEntity<String> DeactivateEmployee(@PathVariable("id") int id, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity("Please log in", HttpStatus.FORBIDDEN);
		}
		try{
			Employee emp = empRepo.getOne(id);
			emp.setActive(false);
			return new ResponseEntity("success", HttpStatus.OK);
			} catch (Exception ex) {
				return new ResponseEntity("Something went wrong", HttpStatus.NOT_FOUND);
			}
	}
}
