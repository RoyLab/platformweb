package com.publisher;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.publisher.xmlparsers.PMParser;

public class PublisherListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event) {
	 
		// 初始化解析xml，放在服务器的初始化函数中。
		Config.setServletContext(event.getServletContext());
		
		PMParser pm = new PMParser(Config.getServletContext().getInitParameter("pmLocation"));//规定xml的路径
		try {
			pm.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SearchEngine searchEngine = new SearchEngine();
			System.out.println(searchEngine.combSearch("机", "figure"));
			System.out.println("fulltext:\n"+searchEngine.fullTextSearch("发动机"));
			System.out.println((String)event.getServletContext().getAttribute("jdbcName"));
			searchEngine.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}