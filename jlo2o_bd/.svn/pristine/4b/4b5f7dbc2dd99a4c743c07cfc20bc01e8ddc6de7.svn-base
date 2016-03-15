<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.ebc.DSDES"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="com.jlsoft.utils.JlAppResources"%>
<%
	StringBuilder sb = new StringBuilder();
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	System.out.println("------------接受参数开始------------");
	Enumeration enums = request.getParameterNames();
	
	while(enums.hasMoreElements()){
		String key = (String)enums.nextElement();
		String value = request.getParameter(key);
		sb.append("key:"+key+",value:"+value);
		sb.append("\n");
	}
	System.out.println("-->:"+sb.toString());
	System.out.println("------------接受参数完毕------------");
	
	String dstbdatasign = request.getParameter("dstbdatasign");
	String dstbdata = request.getParameter("dstbdata");
	
	if(DSDES.verify(dstbdatasign, dstbdata)){
		//更新订单状态
		 String urlvalue=JlAppResources.getProperty("timely_header_url")+"/EbcController/update_notify_url.action?"+dstbdata;
		URL url = new URL(urlvalue);//
         HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
             .getInputStream()));
         String inputLine = in.readLine().toString();
         System.out.println("inputLine--------->:"+inputLine);
         System.out.println("Outer--->True|Flase----->:"+"{\"dataType\":\"xml\",\"data\":{\"is_update_success\":\"1\"}}".equals(inputLine));
		if("{\"dataType\":\"xml\",\"data\":{\"is_update_success\":\"1\"}}".equals(inputLine)){
			//通知银盈通，支付成功
			response.getWriter().write("00");
			System.out.println("OK");
		}else{
		}  
	}
%>