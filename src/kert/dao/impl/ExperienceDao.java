package kert.dao.impl;
/*
 * @author Peng Wang
 */
import java.util.ArrayList;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import kert.dao.IExperience;
import kert.entity.Creation;
import kert.entity.Experience;
import kert.entity.Subject;
import kert.entity.Variable;


import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class ExperienceDao extends JpaDaoSupport implements IExperience {
	
	/*
	 * @see kert.dao.IExperience#save(kert.entity.Experience)
	 */
	@Override
	public void save(Experience experience) {
		logger.info("saving Experience instance");
		try{
			getJpaTemplate().persist(experience);
			logger.info("save successful");
		}catch(RuntimeException re){
			logger.info("save failed"+re);
			throw re;
		}

	}

	@Override
	public void delete(Experience experience) {
		logger.info("deleting Experience instance");
		try{
			getJpaTemplate().remove(experience);
			logger.info("delete successful");
		}catch(RuntimeException re){
			logger.info("delete failed");
			throw re;
		}

	}

	@Override
	public Experience update(Experience experience) {
		logger.info("updating Experience instance");
		try{
			Experience result=getJpaTemplate().merge(experience);
			logger.info("update successful");
			return result;
		}catch(RuntimeException re){
			logger.info("update failed");
			throw re;
		}
		
	}

	@Override
	public Experience findById(long id) {
		logger.info("finding Experience instance with id:"+id);
		try{
			Experience result=getJpaTemplate().find(Experience.class,id);
			
			return result;
		}catch(RuntimeException re){
			logger.info("find failed");
			throw re;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<Experience> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding Experience instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select e from Experience e where e."
					+ propertyName + "= :propertyValue";
			return (ArrayList<Experience>) getJpaTemplate().executeFind(new JpaCallback() {
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
	@Override
	public ArrayList<Experience> findAll() {
		logger.info("finding all Experience instances");
		try {
			final String queryString = "select e from Experience e";
			return (ArrayList<Experience>) getJpaTemplate().executeFind(new JpaCallback() {
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

	public static ExperienceDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (ExperienceDao) ctx.getBean("ExperienceDao");
	}

	@Override
	public Experience findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable findVariableByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Experience experience, Creation creation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Experience experience, Creation creation, Subject subject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Experience update(Experience experience, Creation creation) {
		// TODO Auto-generated method stub
		return null;
	}


}
