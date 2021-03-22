package com.blueteam.timekeeping.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Employees")
@JsonIgnoreProperties({"hibernateLayInitializer", "handler", "images"})
public class Employee extends Person {

	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
		super();
	}

}
