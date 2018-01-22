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
 * @date 15/09/2011
 * @author Peng Wang
 */
@Entity
@Table(name="consequence_k")
public class Consequence implements Serializable {
	public Consequence(){
		
	}
	
    public Consequence(Long id, Set<Variable> variable, String sym,
			String value, Rule rule) {
		super();
		this.id = id;
		this.variable = variable;
		this.sym = sym;
		this.value = value;
		this.rule = rule;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@OneToMany(mappedBy = "consequence", cascade = CascadeType.ALL)
    private Set<Variable> variable = new HashSet<Variable>();
    private String sym;
    @Column(name = "cValue")
    private String value;
    @ManyToOne(fetch=FetchType.LAZY)
    private Rule rule;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<Variable> getVariable() {
		return variable;
	}
	public void setVariable(Set<Variable> variable) {
		this.variable = variable;
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
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}

  
}
