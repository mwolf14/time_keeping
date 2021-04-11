package com.blueteam.timekeeping.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.blueteam.timekeeping.models.ModelBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="TimeCard")
public class TimeCard extends ModelBase {
	@ManyToOne
	@JoinColumn(name="id", nullable = false, insertable=false, updatable=false)
	private Employee employee;
	private Date startTime;
	private Date endTime;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public TimeCard() {
		// TODO Auto-generated constructor stub
	}

}
