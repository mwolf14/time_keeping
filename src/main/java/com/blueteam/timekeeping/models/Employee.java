/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: POJO for the employee. controls the shape of the database table. Extends Person
 * Rev History: 
 * 
*/
package com.blueteam.timekeeping.models;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.blueteam.timekeeping.models.Person;

@Entity
@Table(name="Employees")
@JsonIgnoreProperties({"hibernateLayInitializer", "handler", "images"})
public class Employee extends Person {
	
	private String password;
	//private String recId;	
	private String userName;
	private boolean approved;
	private boolean supervisor;
	
	@OneToMany(cascade = {CascadeType.ALL})
	//@JoinColumn(name="timecard_id")
	private List<TimeCard> timeCards = new ArrayList<>();
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<TimeCard> getTimeCards() {
		return timeCards;
	}
	public void addTimeCard(TimeCard timecard) {
		this.timeCards.add(timecard);
	}

	public void setTimeCards(List<TimeCard> timeCards) {
		this.timeCards = timeCards;
	}
	public void setIsSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
	}
	public boolean getIsSupervisor() {
		return supervisor;
	}
	
	//methods to deal with the password
	/*public String getRecId() {
		return recId;
	}
	
	public void setRecId(String recId) {
		this.recId = recId;
	}*/
	
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
		String pepper = password.substring(0,4);
		this.password =  password + pepper;
		System.out.println(this.password);
	}
	
	public Employee() {
		super();		
	}
}
