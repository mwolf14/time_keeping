package com.blueteam.timekeeping.models;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Employees")
@JsonIgnoreProperties({"hibernateLayInitializer", "handler", "images"})
public class Employee extends Person {

	private String password;
	private String recId;
	@OneToMany
	@JoinColumn(name="id", nullable = false, insertable=false, updatable=false)
	private List<TimeCard> timeCards;
	
	public List<TimeCard> getTimeCards() {
		return timeCards;
	}

	public void setTimeCards(List<TimeCard> timeCards) {
		this.timeCards = timeCards;
	}
	
	//methods to deal with the password
	private String getRecId() {
		return recId;
	}
	
	private void setRecId() {
		//this is the salt for the password
		if (recId == null) {
			Calendar now =  Calendar.getInstance();
			//get the second and concat a string to be salted into the password
			recId= now.get(Calendar.SECOND) + "";
			System.out.println("Rec Id for this user is: " + recId);
		}
	}
	
	public boolean comparePasswords(String password) {
		String hashedPassword= "";
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update((this.recId + password + password.substring(0,4)).getBytes());
		 hashedPassword = md.digest().toString();
		} catch(Exception ex) {
			System.out.println("MD5 hash failed. Employee.comparePassword");
		}
		if (hashedPassword.toString() == this.password) {
			return true;
		}
		return false;
	}
	
	public void setPassword(String password) {
		try {
		 MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update((recId + password + password.substring(0,4)).getBytes());
		this.password = md.digest().toString();
		}
		catch(Exception ex) {
			System.out.println("MD5 hash failed. Employee.setPassword()");
		}
	}
	
	public Employee() {
		super();
		setRecId();
	}
}
