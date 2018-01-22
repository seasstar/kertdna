package kert.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import kert.dao.IConstraint;
import kert.dao.IExperience;
import kert.dao.IFunction;
import kert.dao.IRule;
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
import kert.service.IExperienceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExperienceService implements IExperienceService {
	
	protected final Log log =LogFactory.getLog(getClass());
	private static final String APPLICATION = "application";

	private static final String AREA = "area";

	private static final String ASSOFACTOR = "assofactor";

	private static final String CATEGORY = "category";

	private static final String COEF = "coef";

	private static final String COMMENT = "comment";

	private static final String CONDITION = "condition";

	private static final String CONFIDENCE = "confidence";

	private static final String CONSEQUENCE = "consequence";

	private static final String CONSTRAINT = "constraint";

	private static final String CREATION = "creation";

	private static final String DATE = "date";

	private static final String FACTOR = "factor";

	private static final String FILENAME = "filename";

	private static final String FN_NAME = "fn_name";

	private static final String HOUR = "hour";

	private static final String JNT = "jnt";

	private static final String JOINT = "joint";

	private static final String L_RANGE = "l_range";

	private static final String LPAR = "lpar";

	private static final String OBJ = "obj";

	private static final String OPER = "oper";

	private static final String POTEN = "poten";

	private static final String PRECISENESS = "preciseness";

	private static final String PRECISION = "precision";

	private static final String PRIORITY = "priority";

	private static final String RPAR = "rpar";

	private static final String RULE = "rule";

	private static final String SET_OF_VARIABLES = "set_of_variables";

	private static final String SIMFACTOR = "simfactor";

	private static final String SUBAREA = "subarea";

	private static final String SUBJECT = "subject";

	private static final String SYM = "sym";

	private static final String TERM = "term";

	private static final String TRUTH = "truth";

	private static final String U_RANGE = "u_range";

	private static final String UNCERTAINTY = "uncertainty";

	private static final String UNIT = "unit";

	private static final String VALUE = "value";

	private static final String VARIABLE = "variable";

	private static final String WEIGHT = "weight";

	private IConstraint constraintDao;

	private IExperience edao;

	private IFunction funDao;

	private IRule ruleDao;

	private void constraintData2xml(Element elements, Experience exp) {
		if (exp.getConstraints().size() > 0) {
			for (Constraint constraint : exp.getConstraints()) {
				Element conses = elements.addElement("set_of_constraints");
				Element conse = conses.addElement(CONSTRAINT);
				if (constraint.getSym() != null) {
					conse.addElement(SYM).addText(constraint.getSym());
				}
				if (constraint.getValue() != null) {
					conse.addElement(VALUE).addText(constraint.getValue());
				}
				if (constraint.getWeight() > 0) {
					conse.addElement(WEIGHT).addText(
							Double.toHexString(constraint.getWeight()));
				}
				factorData2Xml(conse, null, constraint, null);

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kert.service.impl.IExperienceService#converting(org.dom4j.Element,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void converting(Element root, String fileName) {
		Experience exp = new Experience();
		exp.setExperienceName(fileName);
		
		log.info("fileName== =="+fileName);

		exp.setString2Calendar(root.element(DATE).getText(), root.element(HOUR)
				.getText());

		Element nodeCre = root.element(CREATION);
		Creation cre = new Creation();
		if (nodeCre.getText() != null)
			exp.setCreationName(nodeCre.getText());
		if (nodeCre.elements(APPLICATION).size() > 0) {

			List<Element> es = nodeCre.elements(APPLICATION);
			for (Element e : es) {

				cre.setAppName(e.getText());
			}
		}
		if (nodeCre.elements(COMMENT).size() > 0) {

			List<Element> es = nodeCre.elements(COMMENT);
			for (Element e : es) {

				cre.setCommentName(e.getText());
			}
		}
		if (nodeCre.elements(FILENAME).size() > 0) {

			List<Element> es = nodeCre.elements(FILENAME);
			for (Element e : es) {
				cre.setFileName(e.getText());
			}
		}

		Element nodeCate = root.element(CATEGORY);

		if (nodeCate != null) {
			if (nodeCate.getText() != null) {
				exp.setCategoryName(nodeCate.getText());
			}
			if (nodeCate.element(AREA) != null) {
				exp.setArea(nodeCate.elementText(AREA));
			}
			if (nodeCate.element(SUBAREA) != null) {
				exp.setSubarea(nodeCate.elementText(SUBAREA));
			}
			if (nodeCate.elements(SUBJECT).size() > 0) {
				List<Element> nodes = nodeCate.elements(SUBJECT);

				for (Element node : nodes) {
					Subject sub = new Subject();
					sub.setName(node.getText());
					sub.setExperience(exp);
					exp.getSubjects().add(sub);
				}
			}

		}

		Element nodePre = root.element(PRECISENESS);
		if (nodePre != null) {
			if (nodePre != null) {
				if (nodePre.elements(PRECISION) != null)
					exp.setPrecision(Double.parseDouble(nodePre.element(
							PRECISION).getText()));
				if (nodePre.element(PRIORITY) != null)
					exp.setPrecisePriority(Double.parseDouble(nodePre.element(
							PRIORITY).getText()));

			}

		}

		Element node = root.element(UNCERTAINTY);
		if (node != null) {
			if (node != null) {
				if (node.element(TRUTH) != null)
					exp.setTruth(Double.parseDouble(nodePre.element(TRUTH)
							.getText()));
				if (node.element(L_RANGE) != null)
					exp.setL_range(Double.parseDouble(nodePre.element(L_RANGE)
							.getText()));
				if (node.element(U_RANGE) != null)
					exp.setU_range(Double.parseDouble(nodePre.element(U_RANGE)
							.getText()));
				if (node.element(PRIORITY) != null)
					exp.setUncertaintyPriority(Double.parseDouble(nodePre
							.element(PRIORITY).getText()));

			}

		}
		Element variables = root.element(SET_OF_VARIABLES);
		List<Element> nodes = variables.elements(VARIABLE);
		for (Element n : nodes) {
			Variable variable = new Variable(n);
			variable.setExperience(exp);
			exp.getVariables().add(variable);
		}

		Element elements = root.element("set_of_functions");
		setFunctions(elements, exp);
		elements = root.element("set_of_constraints");
		setConstraint(elements, exp);
		elements = root.element("set_of_rules");
		setRule(elements, exp);

		edao.save(exp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kert.service.impl.IExperienceService#convertXmlByFolder(kert.entity.
	 * Experience, kert.entity.Creation, kert.entity.Subject,
	 * kert.entity.Function, kert.entity.Factor, kert.entity.FunctionValue,
	 * kert.entity.Subfactor, kert.entity.Term, kert.entity.Variable,
	 * kert.entity.Constraint, kert.entity.Factor, kert.entity.Subfactor,
	 * kert.entity.Term, kert.entity.Variable, kert.entity.Rule,
	 * kert.entity.Consequence, kert.entity.Variable, kert.entity.Joint,
	 * kert.entity.Condition, kert.entity.Factor, kert.entity.Subfactor,
	 * kert.entity.Term, kert.entity.Variable)
	 */
	
	public void convertXmlByFolder(Experience experience, Creation creation,
			Subject subject, Function function, Factor functionFactor,
			FunctionValue fv, Subfactor functionAf, Term functionTerm,
			Variable functionVar, Constraint constraint, Factor constFactor,
			Subfactor constAf, Term constTerm, Variable constVar, Rule rule,
			Consequence consequence, Variable conseqVar, Joint ruleJoint,
			Condition condition, Factor ruleFactor, Subfactor ruleAf,
			Term ruleTerm, Variable ruleFactorVar) {
		edao.save(experience, creation, subject);
		funDao.save(function, experience, functionFactor, fv, functionAf,
				functionTerm, functionVar);
		constraintDao.save(constraint, experience, constFactor, constAf,
				constTerm, constVar);
		ruleDao.save(rule, experience, consequence, conseqVar, ruleJoint,
				condition, ruleFactor, ruleAf, ruleTerm, ruleFactorVar);
	}

	@Transactional
	public void allDataIntoXml(String path) {
		List<Experience> expList = edao.findAll();
		for (Experience exp : expList) {
			dataIntoXml(exp, path);
		}
	}
	@Transactional
	public Document data2xmlbyName(String fileName, String path) {
		Experience e = edao.findByName(fileName);
		log.info("experience=="+e);
		Document doc = dataIntoXml(e, path);
		return doc;
	}

	
	public Document dataIntoXml(Experience exp, String path) {
		try {
			log.info("path== =="+path);
			
			Document doc = DocumentHelper.createDocument();
			// System.out.println("doc=="+doc.asXML());
			Element root = doc.addElement("set_of_experience");

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String str1 = sdf.format(exp.getEdate().getTime());
			root.addElement(DATE).addText(str1);
			sdf = new SimpleDateFormat("ss:mm:hh");
			str1 = sdf.format(exp.getEhour().getTime());
			root.addElement(HOUR).addText(str1);
			// ***************category**************
			Element cre = root.addElement(CREATION);
			if (exp.getCreationName() != null) {
				cre.addText(exp.getCreationName());
			}
			for (Creation creation : exp.getCreations()) {
				if (creation.getAppName() != null) {
					Element e = cre.addElement(APPLICATION);
					e.addText(creation.getAppName());
				}
			}
			for (Creation creation : exp.getCreations()) {
				if (creation.getFileName() != null) {
					Element e = cre.addElement(FILENAME);
					e.addText(creation.getFileName());
				}
			}
			for (Creation creation : exp.getCreations()) {
				if (creation.getCommentName() != null) {
					Element e = cre.addElement(COMMENT);
					e.addText(creation.getCommentName());
				}
			}

			Element nodeCate = root.addElement(CATEGORY);
			if (exp.getCategoryName() != null) {
				nodeCate.addText(exp.getCategoryName());
			}
			if (exp.getArea() != null) {
				nodeCate.addText(exp.getArea());
			}
			if (exp.getSubarea() != null) {
				nodeCate.addText(exp.getSubarea());
			}

			for (Subject sub : exp.getSubjects()) {
				Element subE = nodeCate.addElement(SUBJECT);
				subE.addText(sub.getName());

			}

			Element nodePre = root.addElement(PRECISENESS);
			if (exp.getPrecision() != 0) {

				nodePre.addText(Double.toString(exp.getPrecision()));
			}
			if (exp.getPrecisePriority() != 0) {
				Element temp = nodePre.addElement(PRIORITY);
				temp.addText(Double.toString(exp.getPrecisePriority()));
			}

			Element uncer = root.element(UNCERTAINTY);
			if (exp.getTruth() != 0) {
				Element temp = uncer.addElement(TRUTH);
				temp.addText(Double.toString(exp.getTruth()));
			}
			if (exp.getL_range() != 0) {
				Element temp = uncer.addElement(L_RANGE);
				temp.addText(Double.toString(exp.getL_range()));
			}
			if (exp.getU_range() != 0) {
				Element temp = uncer.addElement(U_RANGE);
				temp.addText(Double.toString(exp.getU_range()));
			}
			if (exp.getUncertaintyPriority() != 0) {
				Element temp = uncer.addElement(PRIORITY);
				temp.addText(Double.toString(exp.getUncertaintyPriority()));
			}
			funData2Xml(root, exp);
			constraintData2xml(root, exp);
			ruleData2Xml(root, exp);

			write(doc, exp.getExperienceName(), path);
			return doc;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kert.service.impl.IExperienceService#dataIntoXml()
	 */

	private void factorData2Xml(Element parentFactor, Function function,
			Constraint constraint, Condition condition) {
		if (parentFactor != null) {
			Element factorlist = parentFactor.addElement(FACTOR);

			for (Factor factor : function.getFactors()) {

				for (Subfactor subf : factor.getSubfactors()) {
					if (Subfactor.TYPE_ASSOFACTOR.equals(subf.getType())) {
						Element assf = factorlist.addElement(ASSOFACTOR);
						if (subf.getLpar() != null) {
							Element temp = assf.addElement(LPAR);
							temp.addText(subf.getLpar());
						}
						if (subf.getOper() != null) {
							Element temp = assf.addElement(OPER);
							temp.addText(subf.getOper());
						}
						if (subf.getPoten() != null) {
							Element temp = assf.addElement(POTEN);
							temp.addText(subf.getPoten());
						}
						if (subf.getRpar() != null) {
							Element temp = assf.addElement(RPAR);
							temp.addText(subf.getRpar());
						}
						if (subf.getTerms().size() > 0) {

							for (Term term : subf.getTerms()) {
								Element terme = assf.addElement(TERM);
								if (term.getCoef() != null) {
									Element temp = terme.addElement(COEF);
									temp.addText(term.getCoef());
								}
								if (term.getOper() != null) {
									Element temp = terme.addElement(OPER);
									temp.addText(term.getOper());
								}
								if (term.getPoten() != null) {
									Element temp = terme.addElement(POTEN);
									temp.addText(term.getPoten());
								}
								if (term.getVariable() != null) {
									Element temp = terme.addElement(VARIABLE);
									temp.addText(term.getVariable()
											.getVar_name());
								}
							}
						}
					}
					if (Subfactor.TYPE_SIMFACTOR.equals(subf.getType())) {
						Element assf = factorlist.addElement(SIMFACTOR);

						if (subf.getTerms().size() > 0) {

							for (Term term : subf.getTerms()) {
								Element terme = assf.addElement(TERM);
								if (term.getCoef() != null) {
									Element temp = terme.addElement(COEF);
									temp.addText(term.getCoef());
								}
								if (term.getOper() != null) {
									Element temp = terme.addElement(OPER);
									temp.addText(term.getOper());
								}
								if (term.getPoten() != null) {
									Element temp = terme.addElement(POTEN);
									temp.addText(term.getPoten());
								}
								if (term.getVariable() != null) {
									Element temp = terme.addElement(VARIABLE);
									temp.addText(term.getVariable()
											.getVar_name());
								}
							}
						}
					}
				}
			}
		}
	}

	private void funData2Xml(Element elements, Experience exp) {
		if (elements != null) {

			Element functions = elements.addElement("set_of_function");
			Element fune = functions.addElement("function");
			for (Function f : exp.getFunctions()) {
				if (f.getFn_name() != null) {
					Element fn_name = fune.addElement(FN_NAME);
					fn_name.addText(f.getFn_name());
				}
				if (f.getSym() != null) {
					Element temp = fune.addElement(SYM);
					temp.addText(f.getSym());
				}
				if (f.getObj() != null) {
					Element temp = fune.addElement(OBJ);
					temp.addText(f.getObj());
				}
				if (f.getUnit() != null) {
					Element temp = fune.addElement(UNIT);
					temp.addText(f.getUnit());
				}
				if (f.getWeight() != null) {
					Element temp = fune.addElement(WEIGHT);
					temp.addText(f.getWeight());
				}
				if (f.getFactors().size() > 0) {
					factorData2Xml(fune, f, null, null);
				}
			}
		}
	}

	@Transactional
	public void xml2Data(String path) {

		// Directory path here

		// path = "D:\\source\\heritrixCrawler\\xml";
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".xml") || files.endsWith(".xml")) {
					readXml(listOfFiles[i]);
					log.info("fileName===" + listOfFiles[i].getName());
				}
			}
		}
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see kert.service.impl.IExperienceService#readXml(java.io.File)
	 */
	@Override
	public void readXml(File file) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
			Element root = doc.getRootElement();

			converting(root, file.getName());
		} catch (DocumentException e) {

			e.printStackTrace();
		}
	}

	public void write(Document document, String fileName, String path)
			throws IOException {

		// lets write to a file
		// System.out.println("path==   =="+path+"\\"+fileName);
		// System.out.println("document==="+document);
		if (path.lastIndexOf("\\") != -1) {
			path = path + "\\";
		}
		XMLWriter writer = new XMLWriter(new FileWriter(new File(path
				+ fileName)));
		writer.write(document);
		writer.close();
	}

	private void ruleData2Xml(Element root, Experience exp) {
		if (exp.getRules().size() > 0) {

			Element rulesl = root.addElement("set_of_rules");
			Element rulese = rulesl.addElement(RULE);
			for (Rule rule : exp.getRules()) {

				if (rule.getConfidence() > 0) {
					rulese.addElement(CONFIDENCE).addText(
							Integer.toString(rule.getConfidence()));
				}
				if (rule.getWeight() > 0) {
					rulese.addElement(WEIGHT).addText(
							Double.toString(rule.getWeight()));
				}
				if (rule.getConsequence().size() > 0) {
					Element conseqs = rulese.addElement(CONSEQUENCE);
					for (Consequence conseq : rule.getConsequence()) {
						if (conseq.getSym() != null) {
							conseqs.addElement(SYM).addText(conseq.getSym());
						}
						if (conseq.getValue() != null) {
							conseqs.addElement(VALUE)
									.addText(conseq.getValue());
						}
						if (conseq.getVariable().size() > 0) {

						}
					}
				}
				if (rule.getJoint().size() > 0) {
					Element joints = rulese.addElement(JOINT);
					for (Joint joint : rule.getJoint()) {
						if (joint.getJnt() != null) {
							joints.addElement(JNT).addText(joint.getJnt());
						}
						if (joint.getConditions().size() > 0) {
							Element conditions = rulese.addElement(CONDITION);
							for (Condition cond : joint.getConditions()) {
								if (cond.getSym() != null) {
									conditions.addElement(SYM).addText(
											cond.getSym());
								}
								if (cond.getValue() != null) {
									conditions.addElement(VALUE).addText(
											cond.getValue());
								}
								if (cond.getWeight() != null) {
									conditions.addElement(WEIGHT).addText(
											cond.getWeight());

								}
								if (cond.getVariable() != null) {
									conditions.addElement(VARIABLE).addText(
											cond.getVariable().getVar_name());
								}
								factorData2Xml(conditions, null, null, cond);
							}
						}
					}
				}
			}

		}
	}

	private void setConstraint(Element elements, Experience exp) {
		if (elements != null) {
			@SuppressWarnings("unchecked")
			List<Element> elementList = elements.elements(CONSTRAINT);
			for (Element e : elementList) {
				Constraint cons = new Constraint();

				cons.setValue(e.elementText(VALUE));
				cons.setSym(e.elementText(SYM));
				double weight = Double.parseDouble(e.elementText(WEIGHT));
				cons.setWeight(weight);

				setFactors(e, cons);
				cons.setExperience(exp);
				exp.getConstraints().add(cons);
			}
		}
	}

	public void setConstraintDao(IConstraint constraintDao) {
		this.constraintDao = constraintDao;
	}

	public void setEdao(IExperience edao) {
		this.edao = edao;
	}

	private void setFactors(Element es, Condition cond) {
		setFactors(es, null, null, cond);
	}

	private void setFactors(Element es, Constraint contr) {
		setFactors(es, null, contr, null);
	}

	private void setFactors(Element es, Function f) {
		setFactors(es, f, null, null);
	}

	@SuppressWarnings("unchecked")
	private void setFactors(Element es, Function function,
			Constraint constraint, Condition condition) {
		if (es != null) {
			List<Element> factorlist = es.elements(FACTOR);
			if (factorlist != null) {
				for (Element e : factorlist) {
					if (e != null) {
						Factor factor = new Factor();
						List<Element> assflist = e.elements(ASSOFACTOR);
						if (assflist != null) {
							for (Element assfElement : assflist) {

								Subfactor assf = new Subfactor();
								assf.setType(Subfactor.TYPE_ASSOFACTOR);
								assf.setLpar(assfElement.elementText(LPAR));
								assf.setOper(assfElement.elementText(OPER));
								assf.setPoten(assfElement.elementText("opten"));
								assf.setRpar(assfElement.elementText(RPAR));
								List<Element> ts = assfElement.elements(TERM);
								for (Element et : ts) {
									Term term = new Term();
									term.setCoef(et.elementText(COEF));
									term.setOper(et.elementText(OPER));
									term.setPoten(et.elementText(POTEN));
									String name = et.elementText(VARIABLE);
									Variable v = edao.findVariableByName(name);
									term.setVariable(v);

									assf.getTerms().add(term);
									term.setSubfactor(assf);
								}

								assf.setFactor(factor);
								factor.getSubfactors().add(assf);
							}
						}

						List<Element> simfList = e.elements(SIMFACTOR);
						if (simfList != null) {
							for (Element assfElement : simfList) {

								Subfactor simf = new Subfactor();
								simf.setType(Subfactor.TYPE_SIMFACTOR);
								List<Element> ts = assfElement.elements(TERM);
								for (Element et : ts) {
									Term term = new Term();
									term.setCoef(et.elementText(COEF));
									term.setOper(et.elementText(OPER));
									term.setPoten(et.elementText(POTEN));
									String name = et.elementText(VARIABLE);
									Variable v = edao.findVariableByName(name);
									term.setVariable(v);
									term.setSubfactor(simf);
									simf.getTerms().add(term);

								}
								simf.setFactor(factor);
								factor.getSubfactors().add(simf);
							}
						}
						if (function != null) {
							factor.setFunction(function);
							function.getFactors().add(factor);
						}
						if (constraint != null) {
							factor.setConstraint(constraint);
							constraint.getFactors().add(factor);
						}
						if (condition != null) {
							factor.setCondition(condition);
							condition.getFactors().add(factor);
						}
					}

				}
			}

		}
	}

	private void setFunctions(Element elements, Experience exp) {
		if (elements != null) {
			@SuppressWarnings("unchecked")
			List<Element> elementList = elements.elements("function");
			for (Element e : elementList) {
				Function f = new Function();
				f.setFn_name(e.elementText(FN_NAME));
				f.setSym(e.elementText(SYM));
				f.setObj(e.elementText(OBJ));
				f.setUnit(e.elementText(UNIT));
				f.setWeight(e.elementText(WEIGHT));
				setFactors(e, f);
				f.setExperience(exp);
				exp.getFunctions().add(f);
			}
		}
	}

	public void setFunDao(IFunction funDao) {
		this.funDao = funDao;
	}

	@SuppressWarnings({ "unchecked" })
	private void setRule(Element elements, Experience exp) {
		if (elements != null) {

			List<Element> elementList = elements.elements(RULE);
			for (Element e : elementList) {
				Rule rule = new Rule();
				if (e.elementText(CONFIDENCE) != null) {
					rule.setConfidence(Integer.parseInt(e
							.elementText(CONFIDENCE)));
				}
				if (e.elementText(WEIGHT) != null) {
					rule.setWeight(Double.parseDouble(e.elementText(WEIGHT)));
				}
				List<Element> es = e.elements(JOINT);
				for (Element eJoint : es) {
					Joint joint = new Joint();
					joint.setJnt(eJoint.elementText(JNT));
					List<Element> eConds = eJoint.elements(CONDITION);
					for (Element eCondition : eConds) {
						Condition cond = new Condition();
						cond.setSym(e.elementText(SYM));
						cond.setValue(e.elementText(VALUE));
						cond.setWeight(e.elementText(WEIGHT));

						setFactors(eCondition, cond);
						joint.getConditions().add(cond);
					}
					rule.getJoint().add(joint);

				}
				es = e.elements(CONSEQUENCE);
				for (Element element : es) {
					Consequence conseq = new Consequence();
					conseq.setSym(element.elementText(SYM));
					conseq.setValue(element.elementText(VALUE));
					List<Element> evs = element.elements(VARIABLE);
					for (Element ev : evs) {
						Variable v = edao.findVariableByName(ev
								.elementText(VARIABLE));
						conseq.getVariable().add(v);

					}
					rule.getConsequence().add(conseq);

				}
				rule.setExperience(exp);
				exp.getRules().add(rule);
			}
		}
	}

	public void setRuleDao(IRule ruleDao) {
		this.ruleDao = ruleDao;
	}

}
