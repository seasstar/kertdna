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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.dom4j.Element;

@Entity
@Table(name = "variable_k")
@NamedQueries({ @NamedQuery(name = "findVariableByName", query = "select v from Variable v where v.var_name=:var_name") })
public class Variable extends BaseObject implements Serializable {
	public Variable() {

	}

	public Variable(Element node) {
		if (node.element("var_name") != null)
			this.var_name = node.element("var_name").getText();
		if (node.element("var_type") != null)
			this.var_type =node.element("var_type").getText();
		if (node.element("var_cvalue") != null)
			this.var_cvalue = node.element("var_cvalue").getText();
		if (node.element("var_evalue") != null)
			this.var_evalue = node.element("var_evalue").getText();
		if (node.element("unit") != null)
			this.unit = node.element("unit").getText();

		if (node.element("internal") != null
				|| "false".equals(node.element("internal").getText())) {
			this.internal = false;
		}
		if (node.element("weight") != null)
			this.weight = Float.parseFloat(node.element("weight").getText());
		if (node.element("l_range") != null)
			this.l_range =node.element("l_range").getText();
		if (node.element("u_range") != null)
			this.u_range = node.element("u_range").getText();
		if (node.element("priority") != null)
			this.priority = Float.parseFloat(node.element("priority")
					.getText());

	}

	public Variable(int id, String var_name, String var_type,
			String var_cvalue, String var_evalue, String unit,
			boolean internal, float weight, String l_range, String u_range,
			float priority, Set<VariableCategory> categories,
			Experience experience, Consequence consequence) {
		super();
		this.id = id;
		this.var_name = var_name;
		this.var_type = var_type;
		this.var_cvalue = var_cvalue;
		this.var_evalue = var_evalue;
		this.unit = unit;
		this.internal = internal;
		this.weight = weight;
		this.l_range = l_range;
		this.u_range = u_range;
		this.priority = priority;
		this.categories = categories;
		this.experience = experience;
		this.consequence = consequence;
	}

	private static final long serialVersionUID = -5821070653725824060L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String var_name;
	private String var_type;
	@Column(length=2000)
	private String var_cvalue;
	@Column(length=2000)
	private String var_evalue;
	private String unit;
	private boolean internal;
	private float weight;
	private String l_range;
	private String u_range;
	private float priority;
	@OneToMany(mappedBy = "variable", cascade = CascadeType.ALL)
	private Set<VariableCategory> categories = new HashSet<VariableCategory>();
	@ManyToOne(fetch = FetchType.LAZY)
	private Experience experience;
	@ManyToOne(fetch = FetchType.LAZY)
	private Consequence consequence;

	public Consequence getConsequence() {
		return consequence;
	}

	public void setConsequence(Consequence consequence) {
		this.consequence = consequence;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(length = 50)
	public String getVar_name() {
		return var_name;
	}

	public void setVar_name(String var_name) {
		this.var_name = var_name;
	}

	public String getVar_type() {
		return var_type;
	}

	public void setVar_type(String var_type) {
		this.var_type = var_type;
	}

	public String getVar_cvalue() {
		return var_cvalue;
	}

	public void setVar_cvalue(String var_cvalue) {
		this.var_cvalue = var_cvalue;
	}

	public String getVar_evalue() {
		return var_evalue;
	}

	public void setVar_evalue(String var_evalue) {
		this.var_evalue = var_evalue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getL_range() {
		return l_range;
	}

	public void setL_range(String l_range) {
		this.l_range = l_range;
	}

	public String getU_range() {
		return u_range;
	}

	public void setU_range(String u_range) {
		this.u_range = u_range;
	}

	public float getPriority() {
		return priority;
	}

	public void setPriority(float priority) {
		this.priority = priority;
	}

	public Set<VariableCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<VariableCategory> categories) {
		this.categories = categories;
	}

	/*
	 * {@inheritDoc}
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.var_name).toString();
	}

	/*
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Variable)) {
			return false;
		}
		final Variable v = (Variable) o;
		return !(var_name != null ? !var_name.equals(v.var_name)
				: v.var_name != null);
	}

	/*
	 * {@inheritDoc}
	 */
	public int hashCode() {

		return (var_name != null ? var_name.hashCode() : 0);
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
}
