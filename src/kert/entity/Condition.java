/*
 * @author Peng Wang
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

@Entity
@Table(name="condition_k")
public class Condition implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8140341702150174084L;
	public Condition(){
		 
	 }
    public Condition(long id, String sym, String value, String weight,
			Joint joint, Set<Factor> factors, Variable variable) {
		super();
		this.id = id;
		this.sym = sym;
		this.value = value;
		this.weight = weight;
		this.joint = joint;
		this.factors = factors;
		this.variable = variable;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String sym;
    @Column(name = "cValue")
    private String value;
    private String weight;
    @ManyToOne(fetch=FetchType.LAZY)
    private Joint joint;
    @OneToMany(mappedBy = "condition", cascade = CascadeType.ALL)
    private Set<Factor> factors = new HashSet<Factor>();
    @ManyToOne(fetch=FetchType.LAZY)
    private Variable variable ;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Joint getJoint() {
		return joint;
	}
	public void setJoint(Joint joint) {
		this.joint = joint;
	}
	public Set<Factor> getFactors() {
		return factors;
	}
	public void setFactors(Set<Factor> factors) {
		this.factors = factors;
	}
	public Variable getVariable() {
		return variable;
	}
	public void setVariable(Variable variable) {
		this.variable = variable;
	}

  
    
}
