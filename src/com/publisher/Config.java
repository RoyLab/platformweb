package com.publisher;

import javax.servlet.ServletContext;

public class Config {
	
	public static String[] SEARCH_CLASS = {"pnr", "nsn", "para",
			"figure", "table", "step", "warning", "caution"};
	
	private ServletContext servletContext = null;
	
	public Config(ServletContext svctx){
		
		setServletContext(svctx);
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext svctx) {
		servletContext = svctx;
	}

	
	

}
