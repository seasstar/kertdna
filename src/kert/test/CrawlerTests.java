package kert.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kert.dao.impl.ExperienceDao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import crawler.impl.CrawlerDao;

public class CrawlerTests {
	ExperienceDao eDao;
	final ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private CrawlerDao crawlerDao;

	@Test
	public void getSubURLByIndexTest() {
		try {
			crawlerDao = (CrawlerDao) context.getBean("crawlerDao");
			String url = "http://www.ebay.com.au/itm/SUPER-FAST-PC-w-XP-OFFICE-A-VIRUS-12-MONTH-WARRANTY-/180631583179?pt=AU_comp_dekstop&hash=item2a0e7b39cb";
			crawlerDao.convertWebPageToDNA(crawlerDao.readURL(url),"");
			//

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void extractAllIndexPagesTest() {
		try {
			crawlerDao = (CrawlerDao) context.getBean("crawlerDao");
			crawlerDao.createArff();
			crawlerDao.extractAllIndexPages();
			//

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void correctString() {
		try {
			//crawlerDao = (CrawlerDao) context.getBean("crawlerDao");
			String temp = null;

			Pattern p=Pattern.compile("\\d+");
			

			String str="1170.00";
			str=str.replace(",", "");
			
			//String a=crawlerDao.hdBySplitTitle(str);
			System.out.println("value== "+str);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void swichBrandTest() {
		/**
		 * Acer,antec, ASUS,Dell,X2,lenove,div,Tower
		 * 
		 */
		Pattern p = Pattern.compile(
				"'Brand NEW|Used|manufacturer refurbished|seller refurbished",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher("edfdsaAcsmanufacturer refurbishedERasdivdf");

		System.out.println("m== " + m.find() + " " + m.group());
	}

	@Test
	public void convertWebPageToDNATest() {
		try {
			crawlerDao = (CrawlerDao) context.getBean("crawlerDao");
			String url = "http://www.ebay.com.au/sch/Desktop-PCs-/179/i.html?_trksid=p3910.c0.m485";
			StringBuffer sb = crawlerDao.readURL(url);
			// System.out.println(sb.toString());
			crawlerDao.convertWebPageToDNA(sb, "1");
			// xml();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void appendTofileTest() {
		try {
			crawlerDao = (CrawlerDao) context.getBean("crawlerDao");
			crawlerDao.createArff();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
