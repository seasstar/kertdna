package kert.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="VariableCategory_k")
public class VariableCategory extends BaseObject implements Serializable {
	public VariableCategory(){
		
	}
    public VariableCategory(int id, Variable variable, String categoryName) {
		super();
		this.id = id;
		this.variable = variable;
		this.categoryName = categoryName;
	}

	int id;
    private Variable variable ;
    String categoryName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    @Column(length = 30)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    /*
     * {@inheritDoc}
     * 
     */
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.categoryName).toString();
    }

    /*
     * {@inheritDoc}
     * 
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableCategory)) {
            return false;
        }
        final VariableCategory v = (VariableCategory) o;
        return !(categoryName != null ? !categoryName.equals(v.categoryName) : v.categoryName != null);
    }

    /*
     * {@inheritDoc}
     * 
     */
    public int hashCode() {

        return (categoryName != null ? categoryName.hashCode() : 0);
    }
}
