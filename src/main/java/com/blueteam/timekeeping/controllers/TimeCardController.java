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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import com.blueteam.timekeeping.repositories.TimeCardRepository;

import services.TimeCardService;

@RestController("/timecardcontroller")
public class TimeCardController {
/*Autowired fields that are injected through the DI used in Spring */
	/*
	@Autowired
	private TimeCardService tcService;
	*/
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private TimeCardRepository timeCardRepo;
/************************************************************************************************************
*Public Methods (can be called via web request) baseurl/value found in the mapping anotation
************************************************************************************************************/	
	@PostMapping("/gettimecardsbyid?{id}&{startdate}&{enddate}")
	public ResponseEntity getTimeCardsById(@PathVariable int id,@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate, HttpServletRequest request) {
		//this should work
		/*
		 * get the session info
		 * if the session is empty send back a 404
		 * get the employee
		 * get the timecards and then return them with a 200
		 */
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity("Please log in", HttpStatus.FORBIDDEN);
		}
		Employee employee = empRepo.getOne(Integer.parseInt(msgs.get(0)));
			return new ResponseEntity(employee.getTimeCards(), HttpStatus.OK);
	}
	
	@PostMapping(path="/gettimecardsbyids")
	public ResponseEntity getTimeCardsByIds( @RequestParam Map<String, String> timeSpan, Model model, HttpServletRequest request) {		

		return new ResponseEntity("", HttpStatus.OK);
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
	public ResponseEntity<String> ClockIn(HttpServletRequest request) {
		
		//wrap in try catch
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity<String>("Must log in", HttpStatus.FORBIDDEN);
		} 
		int test = Integer.parseInt(msgs.get(0));		
		Optional<Employee> emp = empRepo.findById(test);
		if (emp != null) {
			Employee employee = emp.get();
			TimeCard timecard = new TimeCard();
			timecard.setStartTime(LocalDateTime.now());
			employee.addTimeCard(timecard);
			empRepo.saveAndFlush(employee);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
			
	}
	
	@GetMapping(path="/clockout")
	public ResponseEntity<String> ClockOut(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return new ResponseEntity<String>("Must log in", HttpStatus.FORBIDDEN);
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
					return new ResponseEntity<String>(HttpStatus.OK);
				}
			}
		}
		
		return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path="/correcttimeticket")
	public String CorrectTimeTicket(Model model, HttpServletRequest request) {
			
		return "editpage";
	}
}