package com.publisher.genhtml;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

// TODO
public class ContentHtml {
	
	// 缓冲示例
//    String s = 
//    	      "<p>" +
//    	      "  <media type=\"audio\" id=\"au008093\" rights=\"wbowned\">" +
//    	      "    <title>Bee buzz</title>" +
//    	      "  " +
//    	      "  Most other kinds of bees live alone instead of in a colony." +
//    	      "  These bees make tunnels in wood or in the ground." +
//    	      "  The queen makes her own nest." +
//    	      "</p>";
//    	    InputStream is = new ByteArrayInputStream(s.getBytes());
//
//    	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//    	    DocumentBuilder db = dbf.newDocumentBuilder();
//    	    Document d = db.parse(is);
//
//    	    Node rootElement = d.getDocumentElement();
//    	    System.out.println(nodeToString(rootElement));
	
	public static void genHtml(JspWriter out, String xml){
		try {
			out.print("<div align=\"center\" class=\"dmodule_title\">"
					+ "	这是来自jsp的输出"
					+ "	</div>");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
