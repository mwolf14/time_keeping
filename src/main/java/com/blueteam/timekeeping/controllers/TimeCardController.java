package com.blueteam.timekeeping.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blueteam.timekeeping.repositories.TimeCardRepository;

@Controller
public class TimeCardController {
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
		
		return "editrecord";
	}
}
