package com.blueteam.timekeeping.models;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.blueteam.timekeeping.repositories.EmployeeRepository;
import com.blueteam.timekeeping.service.UserNameService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Employees")
@JsonIgnoreProperties({"hibernateLayInitializer", "handler", "images"})
public class Employee extends Person {

	//TODO: i need to get the service working or embed it in this class to take care of setting the username. right now the 
	//model only has name (not fname and lname)
	/* this if fighting me
	@Transient
	@Autowired
	private UserNameService userNameService;*/
	
	private String password;
	private String recId;
	
	private String userName;
	private boolean approved;
	
	@OneToMany
	@JoinColumn(name="id", nullable = false, insertable=false, updatable=false)
	private List<TimeCard> timeCards;
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		//this.userName = userNameService.createUserName(userName);
	}
	
	public List<TimeCard> getTimeCards() {
		return timeCards;
	}

	public void setTimeCards(List<TimeCard> timeCards) {
		this.timeCards = timeCards;
	}
	
	//methods to deal with the password
	public String getRecId() {
		return recId;
	}
	
	public void setRecId() {
		//this is the salt for the password
		if (recId == null) {
			Calendar now =  Calendar.getInstance();
			//get the second and concat a string to be salted into the password
			recId= now.get(Calendar.SECOND) + "";
			System.out.println("Rec Id for this user is: " + recId);
		}
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	
	public boolean comparePasswords(String password) {
		if (password.compareTo(this.password) == 0) {
			return true;
		}
		return false;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		if (this.recId == null) {
			this.setRecId();
		}
		this.password = recId + password + password.substring(0,4);
		/*try {
		 MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update((.getBytes());
		this.password = md.digest().toString();
		}
		catch(Exception ex) {
			System.out.println("MD5 hash failed. Employee.setPassword()");
		}*/
	}
	
	public Employee() {
		super();		
	}


}
