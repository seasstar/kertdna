package kert.test;

import kert.dao.IConstraint;
import kert.dao.IExperience;
import kert.dao.IFunction;
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

import org.dom4j.Document;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExperienceTest {
	IExperience eDao;
	IFunction fdao;
	IConstraint cdao;
	final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	@Before
	public void pathTest() {
    //
		IExperienceService eService = (IExperienceService) context
				.getBean("experienceService");
		eService.xml2Data("//xml");
	}
	// ServletContext sc=super.getServletContext();
	// ApplicationContext context =
	// WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
	// private ApplicationContext applicationContext;
	@Test
	public void generateXML() {
		IExperienceService eService = (IExperienceService) context
				.getBean("experienceService");
		String path = "//xml";
		eService.allDataIntoXml(path);
	}

	

	@Test
	public void findByNameTest() {
		IExperienceService eService = (IExperienceService) context
				.getBean("experienceService");
		
		String path = "\\xml\\A Clockwork Orange (1971) .xml";
		String[] fn=path.split("\\\\");
		path="";
		for(int i=0;i<fn.length-1;i++){
			if(i<fn.length-2){
			path=path +fn[i]+"\\";
			}else{
				path=path+fn[i];
			}
		}
		System.out.println("fn=="+fn[fn.length-1]  +"  path=="+path );
		Document e = eService.data2xmlbyName(fn[fn.length-1], path);
		System.out.println("e.experienceName==" + e);
	}

	public String getfilenameByPath(String path) {
//		StringTokenizer st = new StringTokenizer(path, "\\\\");
//		String name = "";
//		while (st.hasMoreElements()) {
//			
//			name = st.nextToken();
//		}
//		return name;
		String[] fn=path.split("\\\\");
		for(int i=0;i<fn.length-1;i++){
			if(i<fn.length-2){
			path=path +fn[i]+"\\";
			}else{
				path=path+fn[i];
			}
		}
		return fn[fn.length-1];
	}

	@Test
	public void functionSaveTest() {
		fdao = (IFunction) context.getBean("functionDao");
		eDao = (IExperience) context.getBean("experienceDao");
		IExperienceService eService = (IExperienceService) context
				.getBean("experienceService");

		Function f = new Function();
		Factor factor = new Factor();
		Subfactor af = new Subfactor();

		Term term = new Term();
		Variable var = new Variable();
		var.setVar_cvalue("function variable");

		FunctionValue fv = new FunctionValue();
		Experience experience = new Experience();
		experience.setExperienceName("experienceName");
		Creation creation = new Creation();

		Subject subject = new Subject();

		Constraint constraint = new Constraint();

		Factor constraintFactor = new Factor();
		Subfactor constraintAf = new Subfactor();

		Term constraintTerm = new Term();
		Variable constraintVar = new Variable();
		constraintVar.setVar_cvalue("constraint Var");

		Rule rule = new Rule();
		Consequence consequence = new Consequence();
		Variable consequenceVar = new Variable();

		Joint joint = new Joint();
		Condition condition = new Condition();
		Factor ruleFactor = new Factor();
		Subfactor ruleAf = new Subfactor();

		Term ruleTerm = new Term();
		Variable ruleVar = new Variable();
		ruleVar.setVar_cvalue("Rule Joint Var");

		eService.convertXmlByFolder(experience, creation, subject, f, factor,
				fv, af, term, var, constraint, constraintFactor, constraintAf,
				constraintTerm, constraintVar, rule, consequence,
				consequenceVar, joint, condition, ruleFactor, ruleAf, ruleTerm,
				ruleVar);
	}
}
