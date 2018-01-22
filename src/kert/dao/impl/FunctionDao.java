package kert.dao.impl;

import java.util.ArrayList;

import kert.dao.IFunction;
import kert.entity.Experience;
import kert.entity.Factor;
import kert.entity.Function;
import kert.entity.FunctionValue;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionDao extends BaseDao implements IFunction {

	public void save(Function function) {
		save(function, null, null, null, null, null, null);

	}

	public void save(Function function, Experience experience, Factor factor,
			FunctionValue fv, Subfactor af, Term term, Variable var) {

		logger.info("saving function instance");

		try {
			if (function != null) {
				getJpaTemplate().persist(function);
				if (experience != null) {
					function.setExperience(experience);
					experience.getFunctions().add(function);
				}

				saveFactor(factor, af,  term, var);

				if (fv != null) {
					function.getFunValue().add(fv);
					fv.setFunction(function);
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

	@Override
	public Function update(Function function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function update(Function function, Experience experience,
			Factor factor, FunctionValue fv, Subfactor af, Term term,
			Variable var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Function Function) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Function> findByProperty(String PropertyName, Object Value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Function> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public static FunctionDao getFromApplicationContext(ApplicationContext ctx) {
		return (FunctionDao) ctx.getBean("functionDao");
	}

}
