/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: Base for all human's in this project. Extends ModelBase
 * Rev History: 
 * 
*/
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
