<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@page session="true" import="com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils,java.text.SimpleDateFormat,java.util.Date"%>
<%@page import="com.jlsoft.utils.JlAppResources,com.jlsoft.o2o.interfacepackage.alipay.unionpay.GnetePayUtils" %>
<html>
<head>
<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/js/commons.js"></script>
</head>
<body>
<!-- 银联支付发送页面 -->
<!-- 弹出遮罩层 -->
<div id="header"></div>
	<%
		//定义变量
		String MerId, OrderNo, OrderAmount, CurrCode, BankCode,OrderType, nOrderType, CallBackUrl, ResultMode, Reserved01, Reserved02, SourceText, PKey, SignedMsg, EntryMode;
		boolean ret;
        //交易密钥
		PKey = JlAppResources.getProperty("unionpay_PKey");
		//商户ID参数
		MerId = JlAppResources.getProperty("unionpay_MerId");
		//商户订单号
		Date myDate = new Date();
		OrderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
		//订单金额，格式：元.角分
		OrderAmount = request.getParameter("OrderAmount");
		//货币代码，值为：CNY
		CurrCode = "CNY"; 
		//支付结果接收URL
		System.out.println("JYDH:"+request.getParameter("JYDH")+"   ##########################");
		CallBackUrl = JlAppResources.getProperty("unionpay_returnUrl")+"?JYDH="+request.getParameter("JYDH");
		//支付方式
		EntryMode = request.getParameter("EntryMode") == null ? "": request.getParameter("EntryMode"); 
		//银行代码，直通车用
		BankCode = request.getParameter("nBankCode") == null ? "" : request.getParameter("nBankCode");
	    //支付结果返回方式(0-成功和失败支付结果均返回；1-仅返回成功支付结果)
		ResultMode = "0";
		Reserved01 = "just"; //保留域1
		Reserved02 = "atest"; //保留域2
		//订单类型
		OrderType = "B2B";
		//签名数据
		SignedMsg = "";
		SourceText = MerId + OrderNo + OrderAmount + CurrCode + OrderType + CallBackUrl+ ResultMode + BankCode + EntryMode + Reserved01+ Reserved02;
		PayUtils payUtils = new PayUtils();
		SignedMsg = payUtils.md5(SourceText + payUtils.md5(PKey));
        //验证签名
		ret = payUtils.checkSign(SourceText,SignedMsg,PKey);
	%>
	<form method="post" name="SendOrderForm" action="<%=JlAppResources.getProperty("unionpay_paySend")%>">
		<input type='hidden' name='MerId' value='<%=MerId%>'> 
		<input type='hidden' name='OrderNo' value='<%=OrderNo%>'> 
		<input type='hidden' name='OrderAmount' value='<%=OrderAmount%>'> 
		<input type='hidden' name='CurrCode' value='<%=CurrCode%>'> 
		<input type='hidden' name='CallBackUrl' value='<%=CallBackUrl%>'> 
		<input type='hidden' name='ResultMode' value='<%=ResultMode%>'>
		<input type='hidden' name='OrderType' value='<%=OrderType%>'>
		<input type='hidden' name='BankCode' value='<%=BankCode%>'> 
		<input type='hidden' name='EntryMode' value='<%=EntryMode%>'>
		<input type='hidden' name='Reserved01' value='<%=Reserved01%>'> 
		<input type='hidden' name='Reserved02' value='<%=Reserved02%>'> 
		<input type='hidden' name='SignMsg' value='<%=SignedMsg%>'>

		<div align="center"><input type="submit" name="Submit" value="提交"></div>
	</form>
</body>
<script type="text/javascript">
window.onload = function(){
	openWait();
	//插入银联记录表
	var JYDH="<%=request.getParameter("JYDH").toString()%>";
	var OrderNo = "<%=OrderNo%>";
	var PayNo = "";
	var SignMsg = "银联支付开始";
	var JYJE = "<%=OrderAmount%>";
	var jsonData = {"JYDH":JYDH,"YLLS":OrderNo,"ZFDH":PayNo,"SJCQM":SignMsg,"JYJE":JYJE};
	url="/UnionpayOnline/insertW_XSDD_YLLS.action?json="+JSON.stringify(jsonData);
	var returnVal = visitService(url);
	if(returnVal.STATE == "success"){
		SendOrderForm.submit();
	}else{
		alert("支付失败");
	}
}
</script>
</html>