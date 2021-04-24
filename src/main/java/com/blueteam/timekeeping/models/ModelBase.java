/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: Base for the base for models created with id
 * Rev History: 
 *  V0.1.0
*/
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ModelBase() {
		
	}
	
}
