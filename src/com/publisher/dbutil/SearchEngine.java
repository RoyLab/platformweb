package com.publisher.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchEngine {
	
	private Connection con = null;
	private static String sqlFullTextSearch = "select dmc, content from t_main where content like ?;";
	
	public SearchEngine() throws Exception {
		con = DbUtil.getCon();
	}
	
	public String fullTextSearch(String keyword) throws SQLException{
		PreparedStatement pstmt = con.prepareStatement(sqlFullTextSearch);
		pstmt.setString(1, "%"+keyword+"%");
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) return rs.getString(1)+" 内容摘要："+rs.getString(2).substring(0, 30)+"......";
		return "没有查找到内容。";
	}
	
	public void destroy() throws SQLException{
		if (con != null) con.close();
	}
}
