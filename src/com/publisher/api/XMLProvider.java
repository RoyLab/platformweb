package com.publisher.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.publisher.Config;
import com.publisher.dbutils.DbUtil;

public class XMLProvider {

	private Connection con = null;
	private static String sqlContentQuery = "select xml from t_main where dmc=?;";
	
	public XMLProvider() throws Exception {
		con = DbUtil.getCon();
	}
	
	public void destroy() throws SQLException{
		if (con != null) con.close();
	}
	
	public String getXMLContent(String dmc) throws SQLException{
		PreparedStatement pstmt = con.prepareStatement(sqlContentQuery);
		pstmt.setString(1, dmc);
		ResultSet resultSet = pstmt.executeQuery();
		if (resultSet.next()){
			return resultSet.getString(1);
		}
		return "";
	}
	
	public String getXMLDirectoryData(){
		return (String) Config.getServletContext().getAttribute("dirObj");
	}
}
