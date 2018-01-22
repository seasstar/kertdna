package kert.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="term_k")
public class Term implements Serializable {
	public Term(){
		
	}
    public Term(int id, String oper, String coef, Variable variable,
			String poten, Subfactor subfactor) {
		super();
		this.id = id;
		this.oper = oper;
		this.coef = coef;
		this.variable = variable;
		this.poten = poten;
		this.subfactor = subfactor;
		
	}

	private static final long serialVersionUID = -8983932046548849316L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String oper;
    private String coef;
    @ManyToOne(fetch=FetchType.LAZY)
    private Variable variable ;
    private String poten;
    @ManyToOne(fetch=FetchType.LAZY)
    private Subfactor subfactor ;



    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(length = 1)
    public String getOper() {
        return oper;
    }
    
    public void setOper(String oper) {
        this.oper = oper;
    }
    
    @Column(length = 10)
    public String getCoef() {
        return coef;
    }
    
    public void setCoef(String coef) {
        this.coef = coef;
    }

    public Variable getVariable() {
        return variable;
    }
    
    public void setVariable(Variable variable) {
        this.variable = variable;
    }
    
    @Column(length = 2)
    public String getPoten() {
        return poten;
    }
    
    public void setPoten(String poten) {
        this.poten = poten;
    }

    public Subfactor getSubfactor() {
        return subfactor;
    }
    
    public void setSubfactor(Subfactor subfactor) {
        this.subfactor = subfactor;
    }


}
