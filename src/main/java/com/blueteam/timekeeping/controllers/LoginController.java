package com.blueteam.timekeeping.controllers;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@Controller
@CrossOrigin
public class LoginController {
	@Autowired
	private EmployeeRepository empRepo;


	
	@PostMapping(path="/login")
	public String Login( @RequestParam Map<String, String> user, Model model, HttpServletRequest request) {
		//get user 
		try {
		Employee existingEmployee = empRepo.findByUserName(user.get("myName"));
		//emp.setRecId(existingEmployee.getRecId());
		List<TimeCard> timeCards = existingEmployee.getTimeCards();
		Employee emp = new Employee();
		String passedInPassword = user.get("password");
		emp.setPassword(passedInPassword);
		if (emp.comparePasswords(existingEmployee.getPassword())) {
			//here we need to add in the user session data to keep the session alive for logged in users
			@SuppressWarnings("unchecked")
			List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
			if ( msgs == null) {
				 msgs = new ArrayList<>();
				 msgs.add(0, existingEmployee.getId() + "");
				 msgs.add(1, existingEmployee.getUserName());
				 msgs.add(2, existingEmployee.getIsSupervisor()+"");
				request.getSession().setAttribute("Session_Info", msgs);
			}
			for (int i = 0; i<timeCards.size(); i++) {
				if (timeCards.get(i).getIsOpen()) {
					model.addAttribute("href", "/clockout");
					model.addAttribute("btnText", "Clock Out");
				}
			}
			if(!model.containsAttribute("href")) {
				model.addAttribute("href", "/clockin");
				model.addAttribute("btnText", "Clock In");
			}
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
			model.addAttribute("error","User name not found.");
			return "index";
		}
	}
	
	@GetMapping(path="/logout")
	public String Login(Model model, HttpServletRequest request) {
		request.getSession().invalidate();
		return "index";
	}
}
