package kert.dao;

import java.util.ArrayList;
import java.util.List;

import kert.entity.Creation;
import kert.entity.Experience;
import kert.entity.Subject;
import kert.entity.Variable;

public interface IExperience {
	
public void save(Experience experience);

public void save(Experience experience,Creation creation);
public void save(Experience experience,Creation creation,Subject subject);

public void delete(Experience experience);
public Experience update(Experience experience);
public Experience update(Experience experience,Creation creation);

public Experience findById(long id);
public ArrayList<Experience> findByProperty(String PropertyName,final Object Value);
public List<Experience> findAll();
public Variable findVariableByName(String name);
public Experience findByName(String name);
}
