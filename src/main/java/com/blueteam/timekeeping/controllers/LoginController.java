/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: this controller handles loggin into and out of the system 
*/
package com.blueteam.timekeeping.controllers;

import java.awt.PageAttributes.MediaType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
	//this is in the custom properties for this application
	//private String maxShift;	
	//long max =(long) Double.parseDouble(maxShift);
	//above is a failed attempt to read the application properties. need to revisit when I have time
	private long max = (long).05;

	
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
					//get the time now. get the time the ticket was opened. if the time is greater then 12 hours add a warning to the model to allow the user to go correct it
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime started = timeCards.get(i).getStartTime();
					Duration duration = Duration.between(now, started);
					System.out.println(duration.toHours());
					if (duration.toHours()<max) {
						model.addAttribute("href", "/clockout");
						model.addAttribute("btnText", "Clock Out");
							
					} else {
						//this should update the database to show that the ticket is completed, but it was completed by the system not the employee

						
						timeCards.get(i).setEndTime(started.plusHours(max));
						timeCards.get(i).setClosedBySystem();
						model.addAttribute("problemTicket", true);
						empRepo.save(existingEmployee);
					}
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
