package com.blueteam.timekeeping.controllers.REST;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;
import com.blueteam.timekeeping.repositories.EmployeeRepository;
import com.blueteam.timekeeping.repositories.TimeCardRepository;

@RestController
public class ClockController {
	private TimeCardRepository tcRepo;
	private EmployeeRepository empRepo;
	
	//right now all returns are set to void, return will probably need to be json 
	// with the correct time card, other then clock in clock out, which should return 200 or 50x
	@PostMapping(path="/edittime")
	@ResponseStatus(HttpStatus.OK)
	public void EditTime(HttpServletRequest request) {
		
		
	}
	
	@GetMapping(path="/api/clockin")
	public HttpStatus ClockIn(HttpServletRequest request) {
		//wrap in try catch
		@SuppressWarnings("unchecked")
		List<String> msgs = (List<String>) request.getSession().getAttribute("Session_Info");
		if (msgs == null) {
			return HttpStatus.FORBIDDEN;
		}
		Employee emp = empRepo.getOne(Integer.parseInt(msgs.get(0)));
		TimeCard timecard = new TimeCard();
		timecard.setEmployee(emp);
		timecard.setStartTime(LocalDateTime.now());
		tcRepo.saveAndFlush(timecard);
		return HttpStatus.OK;
		
	}
	
	@PostMapping(path="/clockout")
	@ResponseStatus(HttpStatus.OK)
	public void ClockOut(HttpServletRequest request) {
		
	}
}
