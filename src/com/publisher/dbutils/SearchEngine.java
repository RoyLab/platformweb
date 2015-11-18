package com.publisher.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchEngine {
	
	private Connection con = null;
	private static String sqlFullTextSearch = "select dmc from t_main where content like ?;";
	
	public SearchEngine() throws Exception {
		con = DbUtil.getCon();
	}
	
	public String fullTextSearch(String keyword) throws SQLException{
		PreparedStatement pstmt = con.prepareStatement(sqlFullTextSearch);
		pstmt.setString(1, "%"+keyword+"%");
		ResultSet rs = pstmt.executeQuery();
		String result = "";
		while (rs.next()){
			result += rs.getString(1);
			result += ",\n";
		}
		return result;
	}
	
	public void destroy() throws SQLException{
		if (con != null) con.close();
	}
	
}
