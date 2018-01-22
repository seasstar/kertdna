package crawler.impl;

/**
 * 
 * 
 * @author Peng Wang
 * @Creation Mar 26, 2012
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.HasSiblingFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import crawler.Crawler;

public class ImdbCrawlerDao extends JpaDaoSupport implements Crawler {
	/**
	 * 
	 */
	public static final String VAR_TITLE = "title";
	public static final String VAR_MEMORY = "Memory";
	public static final String VAR_SPEED = "Processor Speed";
	public static final String VAR_TYPE = "Processor Type";
	public static final String VAR_HD = "Hard Drive Capacity";
	public static final String VAR_PRICE = "price";
	public static final String VAR_BRAND = "Brand";
	public static final String VAR_BUNDLED_ITEMS = "Bundled Items";
	public static final String VAR_TYPE_CONTENT = "AMD|Intel Celeron|Intel Core 2|Intel Core i3|Intel Core i5|Intel Core i7";

	public StringBuffer readURL(String url) {
		StringBuffer sb = new StringBuffer();
		BufferedReader r = null;
		try {

			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			conn.setDoInput(true);
			InputStream in = u.openStream();
			r = new BufferedReader(new InputStreamReader(in));
			String tempString = null;
			while ((tempString = r.readLine()) != null) {
				sb.append(tempString.trim());
				sb.append("\n");

			}
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb;
	}

	public void extractAllIndexPages() {

		for (int i = 0; i < 64; i++) {
			int j = i + 1;
			String url = "http://www.ebay.com.au/sch/Desktop-PCs-/179/i.html?LH_BIN=1&LH_ItemCondition=1000&Processor%2520Speed=2%252E5%2520GHz%2520or%2520more&_trkparms=65%253A2%257C66%253A2%257C39%253A1&rt=nc&_dmpt=AU_comp_dekstop&_trksid=p3286.c0.m14.l1513&_pgn=1";
			url = url + j;
			logger.info("index url==" + url);
			getSubURLbyIndex(readURL(url), i + 1);
		}

	}

	public void getSubURLbyIndex(StringBuffer index, int pageNo) {
		String url = "";

		NodeList nl = null;
		try {
			Parser p = new Parser(index.toString());
			// logger.info("print index content======" +index.toString());

			NodeFilter f=new HasParentFilter(new HasParentFilter(new HasSiblingFilter(new HasChildFilter(new HasAttributeFilter("title","Buy It Now")))));
			
			NodeList nl1 = p.extractAllNodesThatMatch(new AndFilter(f,new HasAttributeFilter(
					"class", "vip")));
			
			logger.info("the number of urls extracted==" + nl1.size());
			// System.out.print(nl.size());
			for (int i = 0; i < nl1.size(); i++) {
				LinkTag link = (LinkTag) nl1.elementAt(i);
				logger.info("url links==" + link.getLink());
				// System.out.println(link.getLink());
				StringBuffer sb = readURL(link.getLink());

				String si = i + "";
				String fileName = String.format("%1$02d", pageNo)
						+ String.format("%1$02d", i);

				logger.info("fileName==" + fileName);
				convertWebPageToDNA(sb, fileName);
			}

		} catch (ParserException e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	public void convertWebPageToDNA(StringBuffer sb, String fileName) {
		try {
			Document doc = DocumentHelper.createDocument();
			// System.out.println("doc=="+doc.asXML());
			Element root = doc.addElement("set_of_experience");
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String str1 = sdf.format(cl.getTime());
			root.addElement("date").addText(str1);
			sdf = new SimpleDateFormat("ss:mm:hh");
			str1 = sdf.format(cl.getTime());
			root.addElement("hour").addText(str1);
			root.addElement("creation").addText("EBAY");
			// ***************category**************
			Element bcategory = root.addElement("category");
			bcategory.addElement("area").addText("COMPUTER SHOPPING");
			bcategory.addElement("subarea").addText("PRODUCTION PREDICTION");
			bcategory.addElement("subject").addText("DESKTOP PCs");
			// ****************category******************
			Element vars = root.addElement("set_of_variables");
			Element funs = root.addElement("set_of_functions");
			root.addElement("set_of_constraints");
			root.addElement("set_of_rules");
			Element categories = null;
			Element var = null;// vars.addElement("variable");
			String tit = addTitleVar(sb, VAR_TITLE, "categorical", vars,
					categories);
			// addConditionVar(sb, "condition", "categorical", vars,
			// categories);
			addPriceVar(sb, VAR_PRICE, "numerical", vars, categories);
			addVariables(sb, VAR_TYPE, "categorical", vars, categories, tit);
			addVariables(sb, VAR_SPEED, "numerical", vars, categories, tit);
			addVariables(sb, VAR_BRAND, "categorical", vars, categories, tit);
			addVariables(sb, VAR_MEMORY, "numerical", vars, categories, tit);
			addVariables(sb, VAR_BUNDLED_ITEMS, "categorical", vars,
					categories, tit);
			addVariables(sb, VAR_HD, "numerical", vars, categories, tit);

			// appendDataToArff(vars.element("variable").element(arg0));
			String title = null, condition = null, price = null, type = null, speed = null, brand = null, memory = null, hardDrive = null, bundledItem = null;
			for (Iterator<Element> i = vars.elementIterator(); i.hasNext();) {
				Element e = (Element) i.next();

				if (VAR_TITLE.equals(e.elementText("var_name"))) {
					title = e.element("var_cvalue").getStringValue();

				}
				// if ("condition".equals(e.elementText("var_name"))) {
				// condition = e.element("var_cvalue").getStringValue();
				//
				// }
				if (VAR_PRICE.equals(e.elementText("var_name"))) {
					price = e.element("var_cvalue").getStringValue();

				}
				if (VAR_TYPE.equals(e.elementText("var_name"))) {
					type = e.element("var_cvalue").getStringValue();

				}
				if (VAR_SPEED.equals(e.elementText("var_name"))) {
					speed = e.element("var_cvalue").getStringValue();

				}
				if (VAR_BRAND.toLowerCase().equals(
						e.elementText("var_name").toLowerCase())) {
					brand = e.element("var_cvalue").getStringValue();
				}
				if (VAR_MEMORY.equals(e.elementText("var_name"))) {
					memory = e.element("var_cvalue").getStringValue();

				}
				if (VAR_HD.equals(e.elementText("var_name"))) {
					hardDrive = e.element("var_cvalue").getStringValue();

				}
				if (VAR_BUNDLED_ITEMS.equals(e.elementText("var_name"))) {
					bundledItem = e.element("var_cvalue").getStringValue();

				}

			}
			appendDataToArff(title, condition, price, type, speed, brand,
					memory, hardDrive, bundledItem);
			if (vars.elements().size() > 0) {
				String classPath = System.getProperty("user.dir") + "/xml/";
				classPath = classPath + fileName + ".xml";
				write(doc, classPath);

			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//
	public String correctString(String content) {

		if (content == null || content == "") {
			content = "?";
		} else {

			Pattern p = Pattern.compile("[A-Za-z0-9]+");
			Matcher m = p.matcher(content);
			if (m.find()) {
				content = content.replaceAll("'", " ");
				content = content.trim();
			} else
				content = "?";
		}
		return content;
	}

	public String correctStrNum(String content) {

		if (content == null || content == "") {
			content = "?";
		} else {

			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(content);
			if (m.find()) {
				content = m.group();
			} else
				content = "?";
		}
		return content;
	}

	/**
	 * 
	 * @param sb
	 *            a stringBuffer of a web page content
	 * @param variableName
	 *            of variable
	 * @param kinds
	 *            contains Categories, Numberical,
	 * @param unit
	 *            it is the unit when the type is numberical
	 * @param vars
	 *            it is object in DOM4J, it contains all the variable together.
	 * @param categories
	 * @throws ParserException
	 */
	private void addVariables(StringBuffer sb, String variableName,
			String kinds, Element vars, Element categories, String title)
			throws ParserException {

		Parser parser;
		parser = new Parser(sb.toString());

		String variable = null, unit = "";

		NodeFilter filter = new AndFilter(new TagNameFilter("th"),
				new HasChildFilter(new StringFilter(variableName.trim() + ":")));
		NodeList nl = parser.parse(filter);

		if (nl != null && nl.size() > 0) {
			Tag tag = (Tag) nl.elementAt(0);

			// logger.info("nl "+variableName+" size===" + nl.size());
			// logger.info("id attribute value==" + tag.getAttribute("id"));
			// only extract table th id equal than td headers values
			String idAttr = tag.getAttribute("id");
			NodeFilter f1 = new HasAttributeFilter("headers", idAttr);
			// NodeList nl1=parser.parse(new AndFilter(new
			// TagNameFilter("td"),f1));
			parser = new Parser(sb.toString());
			NodeList nl1 = parser.extractAllNodesThatMatch(f1);
			if (nl1 != null && nl1.size() != 0) {
				// logger.info("nl "+variableName+"==="+
				// nl1.elementAt(0).getChildren().elementAt(0) .getText());
				variable = nl1.elementAt(0).getChildren().elementAt(0)
						.getText();
				if (VAR_TYPE.equals(variableName)) {
					variable = changeType(variable);
				}
				if (VAR_SPEED.equals(variableName)) {
					variable = changeSpeed(variable);
					unit = "GHz";
				}
				if (VAR_BRAND.equals(variableName)) {
					variable = changeBrand(variable);
				}
				String temp = null;
				if ("numerical".equals(kinds)) {

					Pattern p = Pattern.compile("\\d+");
					Matcher m = p.matcher(variable);
					if (m.find()) {
						temp = m.group();
						if (temp != null && temp.trim() != "") {
							unit = variable.substring(variable.indexOf(temp));
							variable = temp;
							if (VAR_HD.equals(variableName)) {
								if (Integer.parseInt(variable) < 50) {
									variable = Integer.parseInt(variable)
											* 1000 + "";
									unit = "GB";
								}
							}
							if (VAR_MEMORY.equals(variableName)) {
								if (Integer.parseInt(variable) > 50) {
									variable = Integer.parseInt(variable)
											/ 1000 + "";
									unit = "GB";
								}
							}
						}
					}
				}
			}
		}
		if (VAR_MEMORY.equals(variableName)
				&& (variable == null || variable == "")) {
			variable = memoryBySplitTitle(title);
			unit = "GB";
		}
		if (VAR_HD.equals(variableName) && (variable == null || variable == "")) {
			variable = hdBySplitTitle(title);
			unit = "GB";
		}

		if (VAR_SPEED.equals(variableName)
				&& (variable == null || variable == "")) {
			variable = changeSpeed(title);
			unit = "GHz";
		}
		if (VAR_TYPE.equals(variableName)
				&& (variable == null || variable == "")) {

			variable = changeType(title);
			unit = "GHz";
		}
		if (VAR_BRAND.equals(variableName)
				&& (variable == null || variable == "")) {

			variable = changeBrand(title);
			unit = "GHz";
		}
		Element var = null;
		var = vars.addElement("variable");

		variable = correctString(variable);
		var.addElement("var_name").addText(variableName);
		var.addElement("var_type").addText(kinds);
		var.addElement("var_cvalue").addText(variable);
		var.addElement("var_evalue").addText(variable);
		var.addElement("unit").addText(unit);
		var.addElement("internal").addText("false");
		var.addElement("weight").addText("1");
		var.addElement("l_range").addText("0.0");
		var.addElement("u_range").addText("0.0");
		categories = var.addElement("categories");
		categories.addElement("category").addText("");
		var.addElement("priority").addText("0.0");

	}

	private String addTitleVar(StringBuffer sb, String variableName,
			String kinds, Element vars, Element categories)
			throws ParserException {

		Parser parser;
		parser = new Parser(sb.toString());

		// <h1 class="vi-is1-titleH1">
		NodeFilter fl = new HasParentFilter(new AndFilter(new TagNameFilter(
				"h1"), new HasAttributeFilter("class", "vi-is1-titleH1")));

		NodeList nl1 = parser.parse(fl);
		String title = null;
		if (nl1 != null && nl1.size() > 0) {
			title = nl1.elementAt(0).getText();

		}
		title = correctString(title);
		Element var = null;
		var = vars.addElement("variable");
		var.addElement("var_name").addText(VAR_TITLE);
		var.addElement("var_type").addText(kinds);
		var.addElement("var_cvalue").addText(title);
		var.addElement("var_evalue").addText(title);
		var.addElement("unit").addText("");
		var.addElement("internal").addText("false");
		var.addElement("weight").addText("1");
		var.addElement("l_range").addText("0.0");
		var.addElement("u_range").addText("0.0");
		categories = var.addElement("categories");
		categories.addElement("category").addText("");
		var.addElement("priority").addText("0.0");
		return title;
	}

	/*
	 * private void addConditionVar(StringBuffer sb, String conName, String
	 * kinds, Element vars, Element categories) throws ParserException {
	 * 
	 * Parser parser; parser = new Parser(sb.toString());
	 * 
	 * // // <span class="vi-is1-condText"> NodeFilter fl = new
	 * HasParentFilter(new AndFilter(new TagNameFilter( "span"), new
	 * HasAttributeFilter("class", "vi-is1-condText")));
	 * 
	 * NodeList nl1 = parser.parse(fl); String condition = null; if (nl1 != null
	 * && nl1.size() > 0) { condition = nl1.elementAt(0).getText();
	 * 
	 * } condition = changeCondition(condition); Element var = null; var =
	 * vars.addElement("variable");
	 * var.addElement("var_name").addText("condition");
	 * var.addElement("var_type").addText(kinds);
	 * var.addElement("var_cvalue").addText(condition);
	 * var.addElement("var_evalue").addText(condition);
	 * var.addElement("unit").addText("");
	 * var.addElement("internal").addText("false");
	 * var.addElement("weight").addText("1");
	 * var.addElement("l_range").addText("0.0");
	 * var.addElement("u_range").addText("0.0"); categories =
	 * var.addElement("categories");
	 * categories.addElement("category").addText("");
	 * var.addElement("priority").addText("0.0"); }
	 */
	private void addPriceVar(StringBuffer sb, String priceName, String kinds,
			Element vars, Element categories) throws ParserException {

		Parser parser;
		parser = new Parser(sb.toString());

		// <span class="vi-is1-prcp" id="v4-27">
		NodeFilter fl = new HasParentFilter(new AndFilter(new TagNameFilter(
				"span"), new HasAttributeFilter("class", "vi-is1-prcp")));

		NodeList nl1 = parser.parse(fl);
		String price = null;
		if (nl1 != null && nl1.size() > 0) {
			price = nl1.elementAt(0).getText();

		}
		if (price != null && price != "") {
			String temp = null;
			if ("numerical".equals(kinds)) {
				price = price.replace(",", "");
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(price);
				if (m.find()) {
					temp = m.group();

					price = temp;

				}
			}
		}
		if (price == null)
			price = "";
		Element var = null;
		var = vars.addElement("variable");
		var.addElement("var_name").addText("price");
		var.addElement("var_type").addText(kinds);
		var.addElement("var_cvalue").addText(price);
		var.addElement("var_evalue").addText(price);
		var.addElement("unit").addText("AU $");
		var.addElement("internal").addText("false");
		var.addElement("weight").addText("1");
		var.addElement("l_range").addText("0.0");
		var.addElement("u_range").addText("0.0");
		categories = var.addElement("categories");
		categories.addElement("category").addText("");
		var.addElement("priority").addText("0.0");

	}

	public Document parse(String path) {

		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(path));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	public void createArff() {

		StringBuffer content = new StringBuffer();
		content.append("@relation 'computers'\n\n");
		content.append("@attribute title string \n");
		// content
		// .append("@attribute condition {'brand new',used,'manufacturer refurbished','seller refurbished'}\n");
		content.append("@attribute price real\n");
		content
				.append("@attribute processorType {amd,'intel celeron','intel core 2','intel core i3','intel core i5','intel core i7'}\n");
		content.append("@attribute processorSpeed {3,2.5,2,1.5,1}\n");
		content
				.append("@attribute brand {acer,antec,compag,dell,hewlett-packard,lenove,tower,shuttle,ibm,div,others}\n");
		content.append("@attribute memory real\n");
		content.append("@attribute hardDriveCapacity real\n");
		content.append("@attribute BundledItems string\n");
		content.append("\n\n @data \n\n");
		appendToFile(content, false);

	}

	private String changeBrand(String brand) {
		String temp = null;
		Pattern p = Pattern
				.compile(
						"acer|antec|compag|dell|hewlett-packard|lenove|tower|shuttle|ibm|div",
						Pattern.CASE_INSENSITIVE);
		if (brand != null && brand != "") {
			Matcher m = p.matcher(brand);
			if (m.find()) {
				temp = m.group().toLowerCase();
			} else {
				temp = "others";
			}
		} else {
			temp = "others";
		}

		return temp;
	}

	private String memoryBySplitTitle(String title) {
		String temp = null;

		Pattern p = Pattern.compile("\\d+");
		Matcher m = null;
		if (title != null && title != "") {
			temp = title;

			String[] strs = title.split("GB");
			if (strs != null && strs.length > 0) {
				for (int i = 0; i < strs.length; i++) {
					String[] subs = strs[i].split("[^0-9]+");
					if (subs != null && subs.length > 0) {
						temp = subs[subs.length - 1];
						if (Integer.parseInt(temp) < 50) {
							return temp;
						}
					}
				}
			}

		}

		return null;
	}

	public String hdBySplitTitle(String title) {
		String temp = null;

		Pattern p = Pattern.compile("\\d+");
		Matcher m = null;
		if (title != null && title != "") {
			temp = title;
			while (true) {

				String[] strs = title.split("GB");
				if (strs != null && strs.length > 0) {
					for (int i = 0; i < strs.length; i++) {
						String[] subs = strs[i].split("[^0-9]+");
						if (subs != null && subs.length > 0) {
							temp = subs[subs.length - 1];
							if (Integer.parseInt(temp) > 50) {
								return temp;
							}
						}
					}
				}
				strs = title.split("TB");
				if (strs != null && strs.length > 0) {
					for (int i = 0; i < strs.length; i++) {
						String[] subs = strs[i].split("[^0-9]+");
						if (subs != null && subs.length > 0) {
							temp = subs[subs.length - 1];
							int t = Integer.parseInt(temp) * 1000;
							return t + "";

						}
					}
				}
			}
		}
		return null;
	}

	private String changeCondition(String condition) {
		String temp = null;
		Pattern p = Pattern.compile(
				"brand new|used|manufacturer refurbished|seller refurbished",
				Pattern.CASE_INSENSITIVE);

		if (condition != null && condition != "") {
			Matcher m = p.matcher(condition);
			if (m.find()) {
				temp = "'" + m.group().toLowerCase() + "'";
			} else {
				temp = "?";
			}
		} else {
			temp = "?";
		}

		return temp;
	}

	private String changeType(String type) {
		String temp = null;
		Pattern p = Pattern.compile(VAR_TYPE_CONTENT, Pattern.CASE_INSENSITIVE);

		if (type != null && type != "") {
			Matcher m = p.matcher(type);
			if (m.find()) {
				temp = "'" + m.group().toLowerCase() + "'";
			} else {
				temp = "?";
			}
		} else {
			temp = "?";
		}

		return temp;
	}

	private String changeSpeed(String speed) {
		String temp = null;
		double f = 0;
		// 3,2.5,2,1.5,1
		Pattern p = Pattern.compile("\\d+|^(\\d+(\\.\\d*)?|\\.\\d+)$");
		if (speed != null && speed != "") {
			Matcher m = p.matcher(speed);
			if (m.find()) {
				f = Float.parseFloat(m.group());
				if (1000 < f)
					f = f / 1000;

				if (f <= 1.25) {
					temp = "1";
				} else if (f > 1.25 && f <= 1.75) {
					temp = "1.5";
				} else if (f > 1.75 && f <= 2.25) {
					temp = "2";
				} else if (f > 2.25 && f <= 2.75) {
					temp = "2.5";
				} else if (f > 2.75) {
					temp = "3";
				}
			} else {
				temp = "?";
			}
		} else {
			temp = "?";
		}

		return temp;
	}

	public void appendDataToArff(String title, String condition, String price,
			String type, String speed, String brand, String memory,
			String hardDrive, String bundledItem) {
		// title = correctString(title);
		// // condition = changeCondition(condition);
		// price = correctStrNum(price);
		// type = correctString(type);
		// speed = changeSpeed(speed);
		// brand = changeBrand(brand);
		// memory = correctStrNum(memory);
		// hardDrive = correctStrNum(hardDrive);
		// bundledItem = correctString(bundledItem);
		StringBuffer content = new StringBuffer();
		if (!"?".equals(title)) {
			title = "'" + title + "'";
		}

		if (!"?".equals(type)) {
			type = "'" + type + "'";
		}

		if (!"?".equals(bundledItem)) {
			bundledItem = "'" + bundledItem + "'";
		}
		content.append(title + ",");
		// content.append(condition + ",");
		content.append(price + ",");
		content.append(type + ",");
		content.append(speed + ",");
		content.append(brand + ",");
		content.append(memory + ",");
		content.append(hardDrive + ",");
		content.append(bundledItem + "\n");
		appendToFile(content, true);

	}

	public void appendToFile(StringBuffer content, boolean begins) {
		try {
			logger.info("content== ==" + content.toString());
			String classPath = System.getProperty("user.dir") + "/xml/";
			classPath = classPath + "EbayComputers.arff";
			FileWriter writer = new FileWriter(classPath, begins);
			writer.write(content.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(Document document, String path) {

		// lets write to a file
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter(path));

			writer.write(document);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ParameterVisitor extends NodeVisitor {
		Map paramsMap = new HashMap();
		String lastKeyVisited;

		public String getValue(String key) {
			return (String) paramsMap.get(key);

		}

		public void visitStringNode(Text stringNode) {
			paramsMap.put(lastKeyVisited, stringNode.getText());

		}

		public void visitTag(Tag tag) {
			if (tag.getTagName().equals("PARAM")) {
				lastKeyVisited = tag.getAttribute("NAME");
			}
		}
	}

}
