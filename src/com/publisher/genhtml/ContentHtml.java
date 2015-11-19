package com.publisher.genhtml;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

// TODO
public class ContentHtml {
	
	public static void genHtml(JspWriter out, String xml){
		try {
			out.print("this is jsp writer");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
