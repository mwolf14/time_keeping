package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blueteam.timekeeping.repositories.EmployeeRepository;
import com.blueteam.timekeeping.repositories.TimeCardRepository;

@Service
public class TimeCardService {
	@Autowired
	private TimeCardRepository timeCardRepo;
	
	// not sure if i need this or not..... leaving it for now
	
	
	//empty constructor to create bean
	public TimeCardService() {
		
	}
}
