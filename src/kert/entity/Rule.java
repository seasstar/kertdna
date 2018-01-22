package kert.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @date 14/09/2011
 * @author Peng Wang
 */
@Entity
@Table(name="rule_k")
public class Rule implements Serializable {
	 public Rule(){
		 
	 }
    public Rule(Long id, int confidence, double weight,
			Set<Consequence> consequence, Set<Joint> joint,
			Experience experience) {
		super();
		this.id = id;
		this.confidence = confidence;
		this.weight = weight;
		this.consequence = consequence;
		this.joint = joint;
		this.experience = experience;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int confidence;
    private double weight;
	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    private Set<Consequence> consequence = new HashSet<Consequence>();
	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    private Set<Joint> joint = new HashSet<Joint>();
	@ManyToOne(fetch=FetchType.LAZY)
    private Experience experience;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getConfidence() {
		return confidence;
	}
	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Set<Consequence> getConsequence() {
		return consequence;
	}
	public void setConsequence(Set<Consequence> consequence) {
		this.consequence = consequence;
	}
	public Set<Joint> getJoint() {
		return joint;
	}
	public void setJoint(Set<Joint> joint) {
		this.joint = joint;
	}
	public Experience getExperience() {
		return experience;
	}
	public void setExperience(Experience experience) {
		this.experience = experience;
	}


}
