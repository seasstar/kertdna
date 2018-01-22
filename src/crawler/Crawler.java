package crawler;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
/**
 * converting webpages into ddna xml
 * 
 * @author Peng Wang
 * @Creation Mar 26, 2012
 */
public interface Crawler  {
	/**
	 * 
	 * @param url
	 * @return StringBuffer of webpage content
	 */
	public  StringBuffer readURL(String url) ;
		

	/**
	 * get webpage from index page content and then invoke convertWebPageToDNA method
	 * 
	 * @param index
	 * 
	 */
	public  void getSubURLbyIndex(StringBuffer index,int pageNo)  ;
	/**
	 * 
	 * @param sb
	 * @param fileName
	 */
	public  void convertWebPageToDNA(StringBuffer sb,String fileName);
	/**
	 * 
	 * @param path
	 * @return
	 */
	public Document parse(String path) ; 
	/**
	 * 
	 * @param document
	 * @param path
	 */
	public void write(Document document, String path)  ;

}
