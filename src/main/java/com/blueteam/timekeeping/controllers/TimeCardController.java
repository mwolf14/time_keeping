/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: get timecards for all employees, single employees, or create/complete a time card 
*/
package com.blueteam.timekeeping.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import com.blueteam.timekeeping.repositories.TimeCardRepository;

import services.TimeCardService;

@Controller
public class TimeCardController {
	/*
	@Autowired
	private TimeCardService tcService;
	*/
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private TimeCardRepository timeCardRepo;
	
	@GetMapping("/gettimecards")
	public String getTimeCards(Model model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		//if you dont have a session back to index
		if (msgs == null) {
			return "index";
		}
		model.addAttribute("permissions", (msgs.get(2).toString().compareTo("true") == 0) ? "supervisor" : "employee");
		//System.out.println("msgs.get(2): " + msgs.get(2));
		//System.out.println("(msgs.get(2) == (true + \"\") evaluates to : " + msgs.get(2).toString().compareTo("true"));
		return "gettimecards";
	}
	
	@PostMapping(path="/gettimecards")
	public String getTimeCards( @RequestParam Map<String, String> timeSpan, Model model, HttpServletRequest request) {
		
		//this needs to be refactored. it is used in multiple spots. probably a service
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		//if you dont have a session back to index
		if (msgs == null) {
			return "index";
		}
		if(msgs.get(2) == "true") {
			//need to talk about what the data for supervisor request will look like 
			
		} else {
			//this will be a standard employee based on timespan
			
		}
		return "timecards";
	}
	
	@PostMapping(path="/editrecord")
	public String editRecord( @RequestParam Map<String, String> timeSpan, Model model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		//if you dont have a session back to index
		if (msgs == null) {
			return "index";
		}
		/*param should boil down to:
		 * 
		 */
		
		Optional<Employee> emp = empRepo.findById(Integer.parseInt(msgs.get(0)));
		if (emp != null) {
			Employee employee = emp.get();
		
		}
		return "editrecord";
	}
	
	@GetMapping(path="/clockin")
	public String ClockIn(Model model, HttpServletRequest request) {
		String destinationPage;
		//wrap in try catch
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			model.addAttribute("error", "Please Log into the system");
			return "index";
		} 
		destinationPage = (msgs.get(2) == "true")? "manager": "employee";
		int test = Integer.parseInt(msgs.get(0));		
		Optional<Employee> emp = empRepo.findById(test);
		if (emp != null) {
			Employee employee = emp.get();
			TimeCard timecard = new TimeCard();
			//timecard.setEmployee(employee);
			timecard.setStartTime(LocalDateTime.now());
			employee.addTimeCard(timecard);
			empRepo.saveAndFlush(employee);
			//timeCardRepo.saveAndFlush(timecard);
			model.addAttribute("href", "/clockout");
			model.addAttribute("btnText", "Clock Out");
		}
		return destinationPage;	
	}
	
	@GetMapping(path="/clockout")
	public String ClockOut(Model model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			model.addAttribute("error", "Please Log into the system");
			return "index";
		}
		int test = Integer.parseInt(msgs.get(0));		
		Optional<Employee> emp = empRepo.findById(test);
		if (emp != null) {
			Employee employee = emp.get();
			List<TimeCard> timeCards = employee.getTimeCards();
			for(int i = 0; i< timeCards.size(); i++){
				if (timeCards.get(i).getIsOpen()) {
					timeCards.get(i).setEndTime(LocalDateTime.now());
					empRepo.saveAndFlush(employee);
				}
			}
			model.addAttribute("href", "/clockin");
			model.addAttribute("btnText", "Clock In");
		}
		String destinationPage = (msgs.get(2) == "true")? "manager": "employee";
		return destinationPage;
	}
}