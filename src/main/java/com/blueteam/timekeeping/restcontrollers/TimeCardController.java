package com.blueteam.timekeeping.restcontrollers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import com.blueteam.timekeeping.repositories.TimeCardRepository;

@RestController
@RequestMapping("/api/v1/timecardcontroller")
public class TimeCardController {

	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private TimeCardRepository timeCardRepo;
	
	
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
	
}
