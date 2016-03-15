<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@page session="true" import="com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils" import="java.util.Date" import="java.util.StringTokenizer"%>
<%@page import="com.jlsoft.utils.JlAppResources" %>
<html>
<head>
<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/js/commons.js"></script>
<script type="text/javascript" src="/js/json2.js"></script>
</head>
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
%>
<body>
<script type="text/javascript">
//判断该流水号是否支付成功，成功返回OK，未成功进行单据查询
var OrderNo = "<%=OrderNo%>";
var jsonData = {"YLLS":OrderNo};
var url = "/UnionpayOnline/selectW_XSDD_YLLS.action?json="+JSON.stringify(jsonData);
var returnVal = visitService(url);
jsonData["JYDH"]=returnVal.JYDH;
<%
	if(RespCode.equals("00")){
%>
		if(returnVal.CGBJ == 1){
			response.getWriter().write("OK");
		}else{
			//从银联获取数据
			url="/UnionpayOnline/callUnionpaySucess.action?json="+JSON.stringify(jsonData);
			visitService(url);
		}
<% 
		response.getWriter().write("OK");
	}else{
%>
	   	//失败后更新状态为待支付状态
	   	jsonData["DJZT"]="3";
	   	url="/UnionpayOnline/updateOrderState.action?json="+JSON.stringify(jsonData);
		visitService(url);
<%		
		System.out.println("【支付失败!响应码为："+RespCode+"】");
		response.getWriter().write("ERROR");
	}
	response.getWriter().flush();
	response.getWriter().close();	
}%>
</script>
</body>
</html>