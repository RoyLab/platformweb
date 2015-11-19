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
	<div align="center" class="dmodule_title">
	N1 SET 外圈旋钮 － 控制与指示
	</div>
	<%
	HtmlContainer ch = new HtmlContainer();
	ch.getHtml(out, "SAMPLEA00000000A00CAA");// (String)request.getAttribute("dmc"));
	ch.destroy();
	%>	
</div>
<hr class="DmEnding" width="98%" align="center">
<div class="DmEnding" align="center" style="color:#6C6C6C;font-family:黑体; font-weight:bold;">数据模块结束</div>

</body>
</html>