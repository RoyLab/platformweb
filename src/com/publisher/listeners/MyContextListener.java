package com.publisher.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.publisher.Config;
import com.publisher.xmlparsers.PMParser;

public class MyContextListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event) {
	 
		// 初始化解析xml，放在服务器的初始化函数中。
		Config.setServletContext(event.getServletContext());
		
		PMParser pm = new PMParser("D:\\eclipse\\jdbctest2\\xml");//规定xml的路径
		try {
			pm.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
	}
}