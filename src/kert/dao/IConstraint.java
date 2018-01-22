package kert.dao;

import java.util.ArrayList;

import kert.entity.Constraint;
import kert.entity.Creation;
import kert.entity.Experience;
import kert.entity.Factor;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

public interface IConstraint {

	public void save(Constraint constraint, Experience experience,
			Factor factor, Subfactor af,  Term term, Variable var);

	public Creation update(Constraint constraint, Experience experience,
			Factor factor, Subfactor af, Term term, Variable var);

	public void delete(Creation Creation);

	public Creation findById(long id);

	public ArrayList<Creation> findByProperty(String PropertyName,
			final Object Value);

	public ArrayList<Creation> findAll();

}
