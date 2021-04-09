package com.blueteam.timekeeping.models;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person extends ModelBase {

	//fields
	private String firstName;
	private String lastName;
	private LocalDate dob;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getLastName() {
		return lastName;
	}
	
	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	//empty constructor
	public Person() {
		// TODO Auto-generated constructor stub
		super();
	}

}
