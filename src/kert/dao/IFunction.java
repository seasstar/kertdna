package kert.dao;

import java.util.ArrayList;

import kert.entity.Experience;
import kert.entity.Factor;
import kert.entity.Function;
import kert.entity.FunctionValue;
import kert.entity.Subfactor;
import kert.entity.Term;
import kert.entity.Variable;

public interface IFunction {
	
public void save(Function function);

public void save(Function function,Experience experience,Factor factor,FunctionValue fv,Subfactor af,Term term,Variable var);
public Function update(Function function);

public Function update(Function function,Experience experience,Factor factor,FunctionValue fv,Subfactor af,Term term,Variable var);
public void delete(Function Function);

public Function findById(long id);
public ArrayList<Function> findByProperty(String PropertyName,final Object Value);
public ArrayList<Function> findAll();

}
