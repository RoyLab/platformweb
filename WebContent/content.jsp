<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@page import="com.publisher.*" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="PRAGMA" content="no-cache">
    <link rel="STYLESHEET" type="text/css" href="<%=getServletContext().getInitParameter("cssSrc")%>">
	<title>Content</title>
</head>
<body>
<div style="height:80%" id="dmContent">
	<%
	HtmlContainer ch = new HtmlContainer();
	//request这个页面的时候应该同时提交dmc参数，现在为了测试用的是固定值。
	ch.writeHtml(out, "SAMPLEA00000000A018AA");// (String)request.getAttribute("dmc"));
	ch.destroy();
	%>	
</div>
<hr class="DmEnding" width="98%" align="center">
<div class="DmEnding" align="center" style="color:#6C6C6C;font-family:黑体; font-weight:bold;">数据模块结束</div>

</body>
</html>