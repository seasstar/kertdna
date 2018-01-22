package kert.dao.impl;

import kert.entity.Condition;
import kert.entity.Consequence;
import kert.entity.Factor;
import kert.entity.Joint;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class BaseDao extends JpaDaoSupport {

	public void saveFactor(Factor factor, Subfactor af,
			Term term, Variable var) {
		logger.info("saving Base instance");
		try {

			if (factor != null) {

				getJpaTemplate().persist(factor);
				if (af != null) {
					factor.getSubfactors().add(af);
					getJpaTemplate().persist(af);
					if (term != null) {
						af.getTerms().add(term);
						getJpaTemplate().persist(term);
						if (var != null) {
							term.setVariable(var);
						}
					}
					// getJpaTemplate().merge(function);
				}
		
			}

			// getJpaTemplate().persist(function);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.info("save failed" + re);
			throw re;
		}

	}

	public void saveJoint(Joint joint, Condition condition, Factor factor,
			Subfactor af, Term term, Variable var) {
		logger.info("saving joint instance");
		try {
			if (joint != null) {
				getJpaTemplate().persist(joint);
				if (condition != null) {
					getJpaTemplate().persist(condition);
					if (factor != null) {

						saveFactor(factor, af, term, var);

					}

				}
			}
			// getJpaTemplate().persist(function);
			logger.info("save joint successful");
		} catch (RuntimeException re) {
			logger.info("save joint failed" + re);
			throw re;
		}
	}

	public void saveConsequence(Consequence consequence, Variable var) {
		logger.info("saving consequence instance");
		try {

			if (consequence != null) {
				getJpaTemplate().persist(consequence);
				if (var != null) {
					getJpaTemplate().persist(var);

				}

			}
			logger.info("save consequence successful");
		} catch (RuntimeException re) {
			logger.info("save consequence failed" + re);
			throw re;
		}
	}
}
