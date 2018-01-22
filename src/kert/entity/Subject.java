/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kert.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author c3071287
 */
@Entity
@Table(name="subject_k")
public class Subject implements Serializable {
	 public Subject(){
		 
	 }


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)   
    private Long id;
    private String name;
    @ManyToOne(fetch=FetchType.LAZY)
    private Experience experience;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Experience getExperience() {
		return experience;
	}
	public void setExperience(Experience experience) {
		this.experience = experience;
	}

   
    
}
