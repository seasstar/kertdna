/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kert.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Peng Wang
 */
@Entity
@Table(name="joint_k")
public class Joint implements Serializable {
	public Joint(){
		
	}
    public Joint(Long id, String jnt, Set<Condition> conditions, Rule rule) {
		super();
		this.id = id;
		this.jnt = jnt;
		this.conditions = conditions;
		this.rule = rule;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String jnt;
    @OneToMany(mappedBy = "joint", cascade = CascadeType.ALL)
    private Set<Condition> conditions = new HashSet<Condition>();
    @ManyToOne(fetch=FetchType.LAZY)
    private Rule rule;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJnt() {
		return jnt;
	}
	public void setJnt(String jnt) {
		this.jnt = jnt;
	}

	public Set<Condition> getConditions() {
		return conditions;
	}
	public void setConditions(Set<Condition> conditions) {
		this.conditions = conditions;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
   
}
