<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@page session="true" import="com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils" import="java.util.Date" import="java.util.StringTokenizer"%>
<%@page import="com.jlsoft.utils.JlAppResources" %>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.URL"%>
<html>
<!-- 银联支付配置后台异步通知交易结果处理界面 -->
<%
	boolean ret;
	//接受银联在线返回数据
	String strPKey=JlAppResources.getProperty("unionpay_PKey");
	String OrderNo=request.getParameter("OrderNo");		//商户订单号
	String PayNo=request.getParameter("PayNo");		//支付单号
	String PayAmount=request.getParameter("PayAmount");		//支付金额，格式：元.角分
	String CurrCode=request.getParameter("CurrCode");		//货币代码
	String SystemSSN=request.getParameter("SystemSSN");		//系统参考号
	String RespCode=request.getParameter("RespCode");		//响应码
	String SettDate=request.getParameter("SettDate");		//清算日期，格式：月月日日
	String Reserved01=request.getParameter("Reserved01");		//保留域1
	String Reserved02=request.getParameter("Reserved02");		//保留域2
	String SignMsg=request.getParameter("SignMsg");             //签名数据，32位MD5加密
	//解密数据
	String SourceMsg = OrderNo + PayNo + PayAmount + CurrCode + SystemSSN + RespCode + SettDate + Reserved01 + Reserved02;
	//对返回的信息进行加密签名
	PayUtils payUtils = new PayUtils();
	ret = payUtils.checkSign(SourceMsg,SignMsg,strPKey);
	if (ret == false) {
		//解密失败
		//out.println("ERROR");
		response.getWriter().write("ERROR");
	} else {
		System.out.println("ret start:"+ret);
		String urlvalue=JlAppResources.getProperty("timely_header_url")+"/UnionpayOnline/auditUnionpayReceiveBack.action?RespCode="+RespCode+"&YLLS="+OrderNo;
		URL url = new URL(urlvalue);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine = in.readLine().toString();
        System.out.println(inputLine+"  @@@@@@@@@@@");
        response.getWriter().write("OK");
	}
	response.getWriter().flush();
	response.getWriter().close();
%>
</html>