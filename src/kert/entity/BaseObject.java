package kert.entity;

import java.io.Serializable;

public abstract class BaseObject implements Serializable{

	private static final long serialVersionUID = 1L;

/*
	
        @//<editor-fold defaultstate="collapsed" desc="Returns a multi-line String with key=value pairs">
                Override
        //</editor-fold>
public abstract String toString();
/*
 * Compares object equality. When using Hibernate, the primary key should not be part of this comparison
 * @param o object to compare to
 * @return true/false based on equality tests
 * @see java.lang.Object#equals(java.lang.Object)
 */
    @Override
public abstract boolean equals(Object o);
/*
 * When you override equals, you should override hashcode. See "why are 
 * equals() and hashCode() importation" for more information:
 * http://www.hibernate.org/109.html
 * @return hashCode
 */
    @Override
public abstract int hashCode();
}
