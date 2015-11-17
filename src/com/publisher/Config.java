package com.publisher;

import javax.servlet.ServletContext;

public class Config {
	
	private static ServletContext svctx = null;
	
	public static ServletContext getServletContext() {
		return svctx;
	}

	public static void setServletContext(ServletContext svctx) {
		Config.svctx = svctx;
	}

	public static String[] SEARCH_CLASS = {"pnr", "nsn", "para",
			"figure", "table", "step", "warning", "caution"};

}
