package kert.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import kert.dao.IConstraint;
import kert.entity.Constraint;
import kert.entity.Creation;
import kert.entity.Experience;
import kert.entity.Factor;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

@Repository
public class ConstraintDao extends BaseDao implements IConstraint {

	public void save(Constraint constraint, Experience experience,
			Factor factor, Subfactor af, Term term, Variable var) {
		logger.info("saving constraint instance");
		try {
			if (constraint != null) {
				getJpaTemplate().persist(constraint);
				if (experience != null) {
					constraint.setExperience(experience);
					experience.getConstraints().add(constraint);
				}

				saveFactor(factor, af, term, var);

			}

			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.info("save failed" + re);
			throw re;
		}

	}

	public void delete(Creation creation) {
		logger.info("deleting Creation instance");
		try {
			getJpaTemplate().remove(creation);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.info("delete failed");
			throw re;
		}

	}

	public Creation update(Creation creation) {
		logger.info("updating Experience instance");
		try {
			Creation result = getJpaTemplate().merge(creation);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.info("update failed");
			throw re;
		}

	}

	public Creation update(Creation creation, Experience experience) {
		logger.info("updating Experience instance");
		try {
			Creation result = getJpaTemplate().merge(creation);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.info("update failed");
			throw re;
		}

	}

	public Creation findById(long id) {
		logger.info("finding Experience instance with id:" + id);
		try {
			Creation result = getJpaTemplate().find(Creation.class, id);

			return result;
		} catch (RuntimeException re) {
			logger.info("find failed");
			throw re;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Creation> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding Experience instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select c from Creation c where c."
					+ propertyName + "= :propertyValue";
			return (ArrayList<Creation>) getJpaTemplate().executeFind(
					new JpaCallback() {
						public Object doInJpa(EntityManager em)
								throws PersistenceException {
							Query query = em.createQuery(queryString);
							query.setParameter("propertyValue", value);
							return query.getResultList();
						}
					});
		} catch (RuntimeException re) {
			logger.info("find failed");
			throw re;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Creation> findAll() {
		logger.info("finding all Creation instances");
		try {
			final String queryString = "select e from Creation e";
			return (ArrayList<Creation>) getJpaTemplate().executeFind(
					new JpaCallback() {
						public Object doInJpa(EntityManager em)
								throws PersistenceException {
							Query query = em.createQuery(queryString);
							return query.getResultList();
						}
					});
		} catch (RuntimeException re) {
			logger.info("find all failed");
			throw re;
		}
	}

	public static ConstraintDao getFromApplicationContext(ApplicationContext ctx) {
		return (ConstraintDao) ctx.getBean("ExperienceDao");
	}

	@Override
	public Creation update(Constraint constraint, Experience experience,
			Factor factor, Subfactor af, Term term, Variable var) {
		// TODO Auto-generated method stub
		return null;
	}

}
