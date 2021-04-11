package com.blueteam.timekeeping.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blueteam.timekeeping.repositories.TimeCardRepository;

@Controller
public class TimeCardController {
	@Autowired
	private TimeCardRepository timeCardRepo;
		
	@GetMapping("/gettimecards")
	public String getTimeCards( @RequestParam Map<String, String> timeSpan, Model model, HttpServletRequest request) {
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
	
}
