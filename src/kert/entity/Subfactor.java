package kert.entity;

import java.io.Serializable;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="subfactor_k")
public class Subfactor implements Serializable {
	public Subfactor(){
		
	}
    public Subfactor(int id, String oper, String lpar, Set<Term> terms,
			String rpar, String poten, Factor factor) {
		super();
		this.id = id;
		this.oper = oper;
		this.lpar = lpar;
		this.terms = terms;
		this.rpar = rpar;
		this.poten = poten;
		this.factor = factor;
	}
    public final static String TYPE_ASSOFACTOR="assofactor";
    public final static String TYPE_SIMFACTOR="simfactor";
	private static final long serialVersionUID = 4382963199089499519L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String Type;
    private String oper;
    private String lpar;
    @OneToMany(mappedBy = "subfactor", cascade = CascadeType.ALL)
    private Set<Term> terms = new HashSet<Term>();
    private String rpar;
    private String poten;
    @ManyToOne(fetch=FetchType.LAZY)
    private Factor factor;

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(Factor factor) {
        this.factor = factor;
    }

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

    @Column(length = 1)
    public String getLpar() {
        return lpar;
    }

    public void setLpar(String lpar) {
        this.lpar = lpar;
    }


    public Set<Term> getTerms() {
        return terms;
    }

    public void setTerms(Set<Term> terms) {
        this.terms = terms;
    }

    public String getRpar() {
        return rpar;
    }

    public void setRpar(String rpar) {
        this.rpar = rpar;
    }

    public String getPoten() {
        return poten;
    }

    public void setPoten(String poten) {
        this.poten = poten;
    }
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}

}
