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
 * @author c3071287
 */
@Entity
@Table(name="constraint_k")
public class Constraint implements Serializable {

	public Constraint(){
		
	}
	
    public Constraint(Long id, String sym, String value, double weight,
			Experience experience, Set<Factor> factors) {
		super();
		this.id = id;
		this.sym = sym;
		this.value = value;
		this.weight = weight;
		this.experience = experience;
		this.factors = factors;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sym;
    private String value;
    private double weight;
	@ManyToOne(fetch=FetchType.LAZY)
    private Experience experience;
    @OneToMany(mappedBy = "constraint", cascade = CascadeType.ALL)
    private Set<Factor> factors = new HashSet<Factor>();


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Factor> getFactors() {
        return factors;
    }

    public void setFactors(Set<Factor> factors) {
        this.factors = factors;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    @Column(name = "cValue")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
    
}
