package kert.dao.impl;

import java.util.ArrayList;

import kert.dao.IRule;
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

import org.springframework.stereotype.Repository;
@Repository
public class RuleDao extends BaseDao implements IRule {

	public void save(Rule rule, Experience experience, Consequence consequence,
			Variable conVar, Joint joint, Condition condition, Factor factor,
			Subfactor af,  Term term, Variable factorVar) {
		logger.info("saving rule instance");

		try {
			if (rule != null) {
				getJpaTemplate().persist(rule);
				if (experience != null) {
					rule.setExperience(experience);
					experience.getRules().add(rule);
				}
				saveConsequence(consequence, conVar);
				saveJoint(joint, condition, factor, af,  term, factorVar);

			}

			// getJpaTemplate().persist(function);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.info("save failed" + re);
			throw re;
		}

	}

	@Override
	public Creation update(Constraint constraint, Experience experience,
			Factor factor, Subfactor af,  Term term, Variable var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Creation Creation) {
		// TODO Auto-generated method stub

	}

	@Override
	public Creation findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Creation> findByProperty(String PropertyName, Object Value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Creation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
