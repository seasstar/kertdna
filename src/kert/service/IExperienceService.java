package kert.service;

import java.io.File;

import kert.entity.Condition;
import kert.entity.Consequence;
import kert.entity.Constraint;
import kert.entity.Creation;
import kert.entity.Experience;
import kert.entity.Factor;
import kert.entity.Function;
import kert.entity.FunctionValue;
import kert.entity.Joint;
import kert.entity.Rule;
import kert.entity.Subfactor;
import kert.entity.Subject;
import kert.entity.Term;
import kert.entity.Variable;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author c3071287
 *
 */
/**
 * @author c3071287
 * 
 */
public interface IExperienceService {
	/**
	 * convert a dom4j element with file name into database
	 * 
	 * @param root
	 * @param fileName
	 */
	public void converting(Element root, String fileName);

	/**
	 * 
	 * @param experience
	 * @param creation
	 * @param subject
	 * @param function
	 * @param functionFactor
	 * @param fv
	 * @param functionAf
	 * @param functionTerm
	 * @param functionVar
	 * @param constraint
	 * @param constFactor
	 * @param constAf
	 * @param constTerm
	 * @param constVar
	 * @param rule
	 * @param consequence
	 * @param conseqVar
	 * @param ruleJoint
	 * @param condition
	 * @param ruleFactor
	 * @param ruleAf
	 * @param ruleTerm
	 * @param ruleFactorVar
	 */
	public void convertXmlByFolder(Experience experience, Creation creation,
			Subject subject, Function function, Factor functionFactor,
			FunctionValue fv, Subfactor functionAf, Term functionTerm,
			Variable functionVar, Constraint constraint, Factor constFactor,
			Subfactor constAf, Term constTerm, Variable constVar, Rule rule,
			Consequence consequence, Variable conseqVar, Joint ruleJoint,
			Condition condition, Factor ruleFactor, Subfactor ruleAf,
			Term ruleTerm, Variable ruleFactorVar);

	/**
	 * converting xml to database By forlder path
	 * 
	 * @param path
	 */
	public void xml2Data(String path);

	/**
	 * convert file into database with DDNA structure
	 * 
	 * @param file
	 */
	public void readXml(File file);


	/**
	 * convert all data to xml files from database to a folder
	 * 
	 * @param path
	 */
	void allDataIntoXml(String path);

	/**
	 * convert a data named fileName to xml file at a folder
	 * 
	 * @param fileName
	 * @param path
	 * @return
	 */
	public Document data2xmlbyName(String fileName, String path);

}