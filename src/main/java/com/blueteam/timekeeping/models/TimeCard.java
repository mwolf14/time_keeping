/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: DOES NOT EXTEND MODELBASE!!!. This is the model for the timecard. Time cards appear in list held by employees.
 * Rev History: 
 * 
*/

package com.blueteam.timekeeping.models;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.blueteam.timekeeping.models.ModelBase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="TimeCard")
public class TimeCard  {
    @Id
    @Column(name="timecard_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int timecard_id;
    
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean isOpen;
	private boolean closedBySystem = false;
	
	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Employee employee;
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee emp) {
		this.employee= emp;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		this.isOpen = true;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		this.isOpen = false;
	}
	public int getTimeCardId() {
		return this.timecard_id;
	}
	public void setTimeCardId(int id) {
		this.timecard_id=id;
	}
	public boolean getIsOpen() {
		return this.isOpen;
	}

	public void setClosedBySystem() {
		this.closedBySystem = true;
	}
	public TimeCard() {
		// TODO Auto-generated constructor stub
	}

}
