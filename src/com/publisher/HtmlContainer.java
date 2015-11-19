package com.publisher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspWriter;

import com.publisher.utils.DbUtil;

// TODO
public class HtmlContainer {
	
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
	
	public void getHtml(JspWriter out, String dmc){
		try {
			try {
				out.print("<div align=\"center\" class=\"dmodule_title\">"
						+ "	这是来自jsp的输出"
						+ "	</div>"+getHTMLContent(dmc));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private static String sqlContentQuery = "select html from t_main where dmc=?;";
	private Connection con = null;
	
	public HtmlContainer() throws Exception {
		con = DbUtil.getCon();
	}
	
	public void destroy() throws SQLException{
		if (con != null) con.close();
	}
	
	public String getHTMLContent(String dmc) throws SQLException{
		PreparedStatement pstmt = con.prepareStatement(sqlContentQuery);
		pstmt.setString(1, dmc);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()){
			return resultSet.getString(1);
		}
		return "";
	}
}
