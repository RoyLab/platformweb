package com.publisher.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.publisher.XMLProvider;
import com.publisher.genhtml.ContentHtml;

// TODO
public class Content extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	response.setContentType("text/html; charset=utf-8");
    	PrintWriter out = response.getWriter();
    	XMLProvider xmlProvider;
    	String xml = "";
		try {
			xmlProvider = new XMLProvider();
			xml = xmlProvider.getXMLContent((String)request.getAttribute("dmc"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ContentHtml.genHtml(out, xml);
//    	out.print(" <link rel=\"STYLESHEET\" type=\"text/css\" href=\"../manual-resources/css/style.css\"/><h1>ahhaahaha</h1>");
    	
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    }


    @Override
    public String getServletInfo() {
        return "Content Servlet";
    }

}
