package kert.dao;

import java.util.ArrayList;

import kert.entity.Condition;
import kert.entity.Consequence;
import kert.entity.Constraint;
import kert.entity.Creation;
import kert.entity.Experience;
import kert.entity.Factor;
import kert.entity.Joint;
import kert.entity.Rule;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

public interface IRule {

	public void save(Rule rule, Experience experience, Consequence consequence,
			Variable conVar, Joint joint, Condition condition, Factor factor,
			Subfactor af, Term term, Variable factorVar);

	public Creation update(Constraint constraint, Experience experience,
			Factor factor, Subfactor af, Term term, Variable var);

	public void delete(Creation Creation);

	public Creation findById(long id);

	public ArrayList<Creation> findByProperty(String PropertyName,
			final Object Value);

	public ArrayList<Creation> findAll();

}
