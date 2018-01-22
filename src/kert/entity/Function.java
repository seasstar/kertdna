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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "function_k")
public class Function  implements Serializable {
	public Function(){
		
	}
    public Function(int id, Experience experience, String obj, String fn_name,
			String sym, Set<FunctionValue> funValue, String unit,
			String weight, Set<Factor> factors) {
		super();
		this.id = id;
		this.experience = experience;
		this.obj = obj;
		this.fn_name = fn_name;
		this.sym = sym;
		this.funValue = funValue;
		this.unit = unit;
		this.weight = weight;
		this.factors = factors;
	}

	private static final long serialVersionUID = 7925165889767033191L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	private String obj;
	private String fn_name;
	private String sym;
	private String unit;
	private String weight;
	@ManyToOne(fetch=FetchType.LAZY)
	private Experience experience;

	@OneToMany(mappedBy = "function", cascade = CascadeType.ALL)
	private Set<FunctionValue> funValue = new HashSet<FunctionValue>();

	@OneToMany(mappedBy = "function", cascade = CascadeType.ALL)
	private Set<Factor> factors = new HashSet<Factor>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getFn_name() {
        return fn_name;
    }

    public void setFn_name(String fnName) {
        fn_name = fnName;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public Set<FunctionValue> getFunValue() {
        return funValue;
    }

    public void setFunValue(Set<FunctionValue> funValue) {
        this.funValue = funValue;
    }


    public Set<Factor> getFactors() {
        return factors;
    }

    public void setFactors(Set<Factor> factors) {
        this.factors = factors;
    }

   
    public Experience getExperience() {
	    return experience;
	}

	public void setExperience(Experience experience) {
	    this.experience = experience;
	}


    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.fn_name).toString();
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fn_name == null) ? 0 : fn_name.hashCode());
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
		Function other = (Function) obj;
		if (fn_name == null) {
			if (other.fn_name != null)
				return false;
		} else if (!fn_name.equals(other.fn_name))
			return false;
		return true;
	}

  
}
