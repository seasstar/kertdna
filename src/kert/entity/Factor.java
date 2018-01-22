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

@Entity
@Table(name="factor_k")
public class Factor implements Serializable {
	public Factor(){
		
	}
	public Factor(int id, Constraint constraint, Set<Subfactor> subfactors,
			 Function function, Condition condition) {
		super();
		this.id = id;
		this.constraint = constraint;
		this.subfactors = subfactors;
		this.function = function;
		this.condition = condition;
	}

	private static final long serialVersionUID = 6295820022829359657L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne(fetch=FetchType.LAZY)
	private Function function ;
	@ManyToOne(fetch=FetchType.LAZY)
	private Constraint constraint;
	@ManyToOne(fetch=FetchType.LAZY)
	private Condition condition;
	@OneToMany(mappedBy = "factor", cascade = CascadeType.ALL)
	private Set<Subfactor> subfactors = new HashSet<Subfactor>();
	
	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Set<Subfactor> getSubfactors() {
		return subfactors;
	}

	public void setSubfactors(Set<Subfactor> subfactors) {
		this.subfactors = subfactors;
	}


}
