/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: DOES NOT EXTEND MODELBASE!!!. 
 * 	This is the model for the timecard. Time cards appear in list held by employees. 
 * 	Hibernate was having a problem with mapping of ID's if they had the same "id" name as everything else..
 * Revision History: 
 * 	V0.2.0
*/

package com.blueteam.timekeeping.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@Table(name="TimeCards")
public class TimeCard  {
    @Id
    @Column(name="timecard_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int timecard_id;
    
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean isOpen = true;
	private boolean closedBySystem = false;
	private boolean needsApproved = false;
	private int approvedBy;
	
	/*
	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Employee employee;
	
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee emp) {
		this.employee= emp;
	}
	*/
/************************************************************************************************************
*Public Methods Getters and Setters
************************************************************************************************************/
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public String getStartTimeDisplayString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return startTime.format(formatter);
	}

	
	public void setStartTime(LocalDateTime startTime) {
		this.startTime =  CorrectTimeEntryFormattingForBusinessRules(startTime);
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}
	public String getEndTimeDisplayString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return endTime.format(formatter);
	}

	public void setEndTime(LocalDateTime endTime) {
		LocalDateTime corrected = CorrectTimeEntryFormattingForBusinessRules(endTime);
		if (this.isOpen) {
		this.endTime = corrected;
		this.isOpen = false;
		} else {
			//this branch will only be reached on an edit of the endtime
			this.endTime =corrected;
			this.needsApproved = true;
		}
	}
	
	public boolean getNeedsApproved() {
		return this.needsApproved;
	}
	
	public void needsApproved() {
		this.needsApproved = true;
	}
	public void Approve() {
		this.needsApproved = false;
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
		// Constructor must be here to generate a bean
	}

	public boolean isClosedBySystem() {
		return closedBySystem;
	}

	public void setClosedBySystem(boolean closedBySystem) {
		this.closedBySystem = closedBySystem;
	}

	public int getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
/************************************************************************************************************
*Private methods
************************************************************************************************************/	
	
	private LocalDateTime CorrectTimeEntryFormattingForBusinessRules(LocalDateTime inputTime) {
		LocalDateTime minCorrect = CorrectMinutes(inputTime);
		LocalDateTime clearSeconds = ClearSeconds(minCorrect);
		return ClearMilliSeconds(clearSeconds);
	}
	private LocalDateTime ClearMilliSeconds(LocalDateTime clearSeconds) {
		
		return clearSeconds.minusNanos(clearSeconds.getNano());
	}

	private LocalDateTime ClearSeconds(LocalDateTime minCorrect) {
		return minCorrect.minusSeconds(minCorrect.getSecond());
	}

	private LocalDateTime CorrectMinutes(LocalDateTime inputTime) {
		if(inputTime.getMinute()%15 < 8) {
			LocalDateTime newTime = inputTime.minusMinutes(inputTime.getMinute()%15);
			return newTime;
		} else {
			LocalDateTime newTime = inputTime.plusMinutes(15-(inputTime.getMinute()%15));
			return newTime;
		}
	}

}
