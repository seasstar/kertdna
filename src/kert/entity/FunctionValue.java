/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author c3071287
 */
@Entity
@Table(name="functionvalue_k")
public class FunctionValue implements Serializable {
	 public FunctionValue(){
		 
	 }
    public FunctionValue(int id, String funValue, Function function) {
		super();
		this.id = id;
		this.funValue = funValue;
		this.function = function;
	}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    @Column(length = 100)
    private String funValue;
    @ManyToOne(fetch=FetchType.LAZY)
    private Function function;
    

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

   
    public String getFunValue() {
        return funValue;
    }

    public void setFunValue(String funValue) {
        this.funValue = funValue;
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*
     * {@inheritDoc}
     * 
     */
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.funValue).toString();
    }


}
