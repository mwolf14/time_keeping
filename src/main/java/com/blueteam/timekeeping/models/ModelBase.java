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
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

@MappedSuperclass
@SequenceGenerator(initialValue=1000, name = "seq")
public abstract class ModelBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
