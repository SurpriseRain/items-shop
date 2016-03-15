<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@page session="true" import="com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils" import="java.util.Date" import="java.util.StringTokenizer"%>
<%@page import="com.jlsoft.utils.JlAppResources" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="/customer/qfy/css/public.css" />
<link rel="stylesheet" type="text/css" href="/customer/qfy/css/head.css" />
<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/js/commons.js"></script>
<script type="text/javascript" src="/js/json2.js"></script>
<script type="text/javascript" src="/js/jquery.cookie.js"></script>
<style>
#pay{ width:1200px; margin:20px auto; font-family:"microsoft yahei";}
.clear{ clear:both;}
#pay .pay_main{ float:left; width:50%; padding:20px 0 40px 0; margin-bottom:20px;margin-left: 30%;}
#pay .pay_main span{ float:left; width:40px; height:40px; border-radius:50%; background-color:#0C6; color:#fff; font-size:30px; line-height:40px; text-align:center;}
#pay .pay_main label{ width:40px; height:40px;line-height:40px;font-size:40px;}
#pay .pay_main .text{ float:left;  margin-left:20px;}
#pay .pay_main .text h4{width: 100%; float:left;  line-height:40px; margin:0px 0 10px 0; padding:0px; font-size:25px; color:#3c3c3c;}
#pay .pay_main .text label{width: 100%;margin-top: 10px; float:left; font-size:18px; color:#444; margin-right:20px; line-height:30px;}
</style>
</head>
<!-- 银联支付配置返回页面 -->
<%
	boolean ret;
	String strPKey=JlAppResources.getProperty("unionpay_PKey");
	//接受银联在线返回数据
	String OrderNo 	= request.getParameter("OrderNo");		//商户订单号
	String PayNo 	    = request.getParameter("PayNo");		//支付单号
	String PayAmount 	= request.getParameter("PayAmount");		//支付金额，格式：元.角分
	String CurrCode 	= request.getParameter("CurrCode");		//货币代码
	String SystemSSN 	= request.getParameter("SystemSSN");		//系统参考号
	String RespCode 	= request.getParameter("RespCode");		//响应码
	String SettDate 	= request.getParameter("SettDate");		//清算日期，格式：月月日日
	String Reserved01 	= request.getParameter("Reserved01");		//保留域1
	String Reserved02 	= request.getParameter("Reserved02");		//保留域2
	String SignMsg     = request.getParameter("SignMsg");             //时间戳签名
	
	String SourceMsg = OrderNo + PayNo + PayAmount + CurrCode + SystemSSN + RespCode + SettDate + Reserved01 + Reserved02;
	//解密数据
	PayUtils payUtils = new PayUtils();
	ret = payUtils.checkSign(SourceMsg,SignMsg,strPKey);
	
	if (ret == false) {
		//解密失败
		//out.println("验签失败");
	} else {
		//此部分操作由商户自行开发，建议将银联返回的原文、SignMsg同时写入数据库
%>
<body>
<script type="text/javascript">
<%
	//响应码为"00"表示交易成功，具体的响应码对照表请查阅《开放商户支付接口V34.doc》
	if(RespCode.equals("00")){
%>
	var JYDH = '<%=request.getParameter("JYDH").toString()%>';
	var OrderNo = "<%=OrderNo%>";
	var PayNo = "<%=PayNo%>";
	var SignMsg = "<%=SignMsg%>";
	var JYJE = "<%=PayAmount%>";
	//插入银联记录表
	var jsonData = {"JYDH":JYDH,"YLLS":OrderNo,"ZFDH":PayNo,"SJCQM":SignMsg,"JYJE":JYJE};
	url="/UnionpayOnline/insertW_XSDD_YLLS.action?json="+JSON.stringify(jsonData);
	visitService(url);
	window.location="/jlo2o_bd/customer/qfy/shopping/paysuccess.html";
<%}else{%>
	window.location="/jlo2o_bd/customer/qfy/shopping/payerror.html";
<%}}%>
</script>
<!-- head开始 -->
<div id="header"></div>
<!-- head结束 -->
<div id="banner_main">
	<div id="pay">
		<div class="pay_main">
  			<span>√</span>
  			<label><h4>支付成功</h4></label>
  			 
  			<div class="text">
    			<label>商户流水号：<b id="YLLS"></b></label>
    			<label>下&nbsp单&nbsp时&nbsp间：<b id="XDSJ"></b></label>
    			<label>支&nbsp付&nbsp金&nbsp额：<b id="ZTJE">￥</b></label>
  			</div>
		</div>
	</div>
</div>
<div class="clera"></div>
<!-- foot开始 -->
<div id="footer" class="clearfix mt20"></div>
<!-- foot结束 -->
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#header").load("/customer/qfy/head.jsp",function(reponse,status){
		if(status=="success"){
			$(".sy").css({"color":"#ff8800"});
		}
	});
	$("#footer").load("/customer/qfy/foot.html");
	
	//对页面进行赋值
	/**$("#YLLS").html(returnVal.OrderNo);
	var date = returnVal.ShoppingDate;
	$("#XDSJ").html(date.substr(0,4)+"-"+date.substr(4,2)+"-"+date.substr(6,2));
	$("#ZTJE").append(returnVal.OrderAmount);*/
})
</script>
</html>