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
	private boolean isOpen;
	private boolean closedBySystem = false;
	private boolean needsApproved = false;
	private int approvedBy;
	
	/*@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Employee employee;
	
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee emp) {
		this.employee= emp;
	}
	*/
	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		if (this.isOpen) {
			this.startTime = startTime;
			this.isOpen = true;
		} else {
			this.startTime = startTime;
			this.needsApproved = true;
		}
		
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		if (this.isOpen) {
		this.endTime = endTime;
		this.isOpen = false;
		} else {
			//this branch will only be reached on an edit of the endtime
			this.endTime =endTime;
			this.needsApproved = true;
		}
	}
	
	public boolean getNeedsApproved() {
		return this.needsApproved;
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
		// TODO Auto-generated constructor stub
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

}
