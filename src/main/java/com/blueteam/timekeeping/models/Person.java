package com.blueteam.timekeeping.models;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person extends ModelBase {

	//fields
	private String name;
	private LocalDate dob;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
