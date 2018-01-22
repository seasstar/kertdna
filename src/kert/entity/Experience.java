/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kert.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * 
 * @author c3071287
 */
@Entity
@Table(name = "experience_k")
public class Experience extends BaseObject implements Serializable {

	public Experience() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private static SimpleDateFormat df = new SimpleDateFormat(
	"hh:mm:ss dd-MM-yyyy");
	private String experienceName;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Calendar edate = Calendar.getInstance();
	@Temporal(javax.persistence.TemporalType.TIME)
	private Calendar ehour = Calendar.getInstance();;
	
	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private Set<Variable> variables = new HashSet<Variable>();
	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private Set<Function> functions = new HashSet<Function>();
	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private Set<Rule> rules = new HashSet<Rule>();
	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private Set<Constraint> constraints = new HashSet<Constraint>();

	/**creation**/
	private String creationName;
	@OneToMany(mappedBy="experience",cascade = CascadeType.ALL)
	private Set<Creation> creations=new HashSet<Creation>();

	/****category*****/
	private String categoryName;
	private String area;
	private String subarea;
	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private Set<Subject> subjects = new HashSet<Subject>();
	/*********uncertainty**********************/
   private double truth;
   private double l_range;
   private double u_range;
   private double uncertaintyPriority;
	/**************preciseness********************/
   @Column(name = "preciseName")
   private double precision;
   private double precisePriority;
/************************************/
   public void setString2Calendar(String strDate,String strTime) {
	try {
		String temp=strTime+" "+strDate;
		if (temp != null) {
			Calendar cal = Calendar.getInstance();
			Date dat;

			dat = df.parse(temp);

			cal.setTime(dat);
			setEdate(cal);
			setEhour(cal);
		}
	} catch (ParseException e) {
		
		e.printStackTrace();
	}

}

   



	public Long getId() {
	return id;
}





public void setId(Long id) {
	this.id = id;
}





public String getExperienceName() {
	return experienceName;
}





public void setExperienceName(String experienceName) {
	this.experienceName = experienceName;
}





public Calendar getEdate() {
	return edate;
}





public void setEdate(Calendar edate) {
	this.edate = edate;
}





public Calendar getEhour() {
	return ehour;
}





public void setEhour(Calendar ehour) {
	this.ehour = ehour;
}





public Set<Variable> getVariables() {
	return variables;
}





public void setVariables(Set<Variable> variables) {
	this.variables = variables;
}





public Set<Function> getFunctions() {
	return functions;
}





public void setFunctions(Set<Function> functions) {
	this.functions = functions;
}





public Set<Rule> getRules() {
	return rules;
}





public void setRules(Set<Rule> rules) {
	this.rules = rules;
}





public Set<Constraint> getConstraints() {
	return constraints;
}





public void setConstraints(Set<Constraint> constraints) {
	this.constraints = constraints;
}





public String getCreationName() {
	return creationName;
}





public void setCreationName(String creationName) {
	this.creationName = creationName;
}





public Set<Creation> getCreations() {
	return creations;
}





public void setCreations(Set<Creation> creations) {
	this.creations = creations;
}





public String getCategoryName() {
	return categoryName;
}





public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}





public String getArea() {
	return area;
}





public void setArea(String area) {
	this.area = area;
}





public String getSubarea() {
	return subarea;
}





public void setSubarea(String subarea) {
	this.subarea = subarea;
}





public Set<Subject> getSubjects() {
	return subjects;
}





public void setSubjects(Set<Subject> subjects) {
	this.subjects = subjects;
}





public double getTruth() {
	return truth;
}





public void setTruth(double truth) {
	this.truth = truth;
}





public double getL_range() {
	return l_range;
}





public void setL_range(double l_range) {
	this.l_range = l_range;
}





public double getU_range() {
	return u_range;
}





public void setU_range(double u_range) {
	this.u_range = u_range;
}





public double getUncertaintyPriority() {
	return uncertaintyPriority;
}





public void setUncertaintyPriority(double uncertaintyPriority) {
	this.uncertaintyPriority = uncertaintyPriority;
}





public double getPrecision() {
	return precision;
}





public void setPrecision(double precision) {
	this.precision = precision;
}





public double getPrecisePriority() {
	return precisePriority;
}





public void setPrecisePriority(double precisePriority) {
	this.precisePriority = precisePriority;
}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((experienceName == null) ? 0 : experienceName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Experience other = (Experience) obj;
		if (experienceName == null) {
			if (other.experienceName != null)
				return false;
		} else if (!experienceName.equals(other.experienceName))
			return false;
		return true;
	}


	

	
}
