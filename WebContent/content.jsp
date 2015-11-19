<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@page import="com.publisher.XMLProvider" %>
    <%@page import="com.publisher.genhtml.*" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="PRAGMA" content="no-cache">
    <link rel="STYLESHEET" type="text/css" href="<%=getServletContext().getInitParameter("cssSrc")%>">
	<title>Content</title>
</head>
<body>
<%
XMLProvider xmlprov = new XMLProvider(); 
String xml = xmlprov.getXMLContent((String)request.getAttribute("dmc"));
ContentHtml.genHtml(out, xml);
%>
<hr class="DmEnding" width="98%" align="center">
<div class="DmEnding" align="center" style="color:#6C6C6C;font-family:黑体; font-weight:bold;">数据模块结束</div>
</body>
</html>