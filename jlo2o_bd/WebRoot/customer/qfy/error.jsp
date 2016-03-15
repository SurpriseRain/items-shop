<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="GBK"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
<html>
<head>
<style type="text/css">
#divimg{
width: 1500px; height:680px; border:1px solid ;
background-image: url("/customer/qfy/images/error.jpg");
}
#error{
	height: 37px;
	left: 574px;
	position: absolute;
	top: 640px;
	width: 355px;
}
#error a{
    display: block;
    float: left;
    height: 35px;
    width: 92px;
}
#main {
position: absolute;
    right: 0;
}
</style>
<title>火星撞汽车</title>
</head>
<body>
<div id="divimg">
</div>
<div id="error">
	<a href="javascript:history.go(-1)" title="回火星"></a>
	<a id="main" href="/customer/qfy/index.html" title="拯救系统"></a>
</div>
  <div style='display:none;'>
<p>抛出异常: <b> <%=exception.getClass()%>:<%=exception.getMessage()%></b></p>
<%
Enumeration<String> e = request.getHeaderNames();
String key;
while(e.hasMoreElements()){
  key = e.nextElement();
}
e = request.getAttributeNames();
while(e.hasMoreElements()){
  key = e.nextElement();
}
e = request.getParameterNames();
while(e.hasMoreElements()){
  key = e.nextElement();
}
%>
<%=request.getAttribute("javax.servlet.forward.request_uri") %><br>
<%=request.getAttribute("javax.servlet.forward.servlet_path") %>
<p>堆和栈进行跟踪:</p>
<pre>
<%
  exception.printStackTrace();
  ByteArrayOutputStream ostr = new ByteArrayOutputStream();
  exception.printStackTrace(new PrintStream(ostr));
  out.print(ostr);
%>
</pre>
  </div>
</body>
</html>