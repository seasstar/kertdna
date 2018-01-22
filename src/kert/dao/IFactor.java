package kert.dao;

import kert.entity.Factor;
import kert.entity.Function;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

public interface IFactor {
	


public void save(Factor factor,Subfactor af,Term term,Variable var);


public Function update(Factor factor,Subfactor af,Term term,Variable var);


}
