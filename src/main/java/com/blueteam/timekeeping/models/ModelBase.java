package com.blueteam.timekeeping.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ModelBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	public int GetId() {
		return id;
	}
	public void SetId(int id) {
		this.id = id;
	}
	public ModelBase() {
		
	}
	
}
