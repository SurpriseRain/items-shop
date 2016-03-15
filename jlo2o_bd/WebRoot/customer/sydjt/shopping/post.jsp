<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" import=" com.allinpay.ets.client.*,com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils,com.jlsoft.utils.JLTools,java.text.SimpleDateFormat,java.util.Date,com.jlsoft.utils.JlAppResources" errorPage="" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>通联网上支付平台-商户接口范例-支付请求信息签名</title>
		<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/js/commons.js"></script>
	</head>
	<body>
<!-- 银联支付发送页面 -->
<!-- 弹出遮罩层 -->
<div id="header"></div>
	<%	
	//页面编码要与参数inputCharset一致，否则服务器收到参数值中的汉字为乱码而导致验证签名失败。
	request.setCharacterEncoding("UTF-8"); 
    System.out.println(request.getParameter("productNum")+"##################################");
	String serverUrl = JlAppResources.getProperty("unionpay_serverUrl");
	String key = JlAppResources.getProperty("key");
	//网关接收支付请求接口版本/Y
	String version = JlAppResources.getProperty("version");
	//网关页面显示语言种类/Y
	String language = JlAppResources.getProperty("language");
	//字符集/Y
	String inputCharset= JlAppResources.getProperty("inputCharset");
	//商户号/Y
	String merchantId = JlAppResources.getProperty("unionpay_merchantId");
	//取货地址/Y
	String pickupUrl = JlAppResources.getProperty("pickupUrl");
	//商户通知地址/Y
	String receiveUrl=JlAppResources.getProperty("unionpay_receiveUrl")+"?person_id="+request.getParameter("person_id");
	//0代表未指定支付方式，即显示该商户开通的所有支付方式
	//1个人储蓄卡网银支付
	//4企业网银支付
	//11个人信用卡网银支付
	//23外卡支付/Y
	String payType=JlAppResources.getProperty("payType");
	//签名类型0表示订单上送和交易结果通知都使用MD5进行签名;1表示商户用使用MD5算法验签上送订单，通联交易结果通知使用证书签名/Y
	String signType=JlAppResources.getProperty("signType");
	//商户系统订单号/Y
	String orderNo=request.getParameter("orderNo");
	//订单金额/Y
	String orderAmount=request.getParameter("orderAmount");
	//商户的订单提交时间/YyyyyMMddHHmmss
	String orderDatetime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	//默认填0
    //0和156代表人民币、840代表美元、344代表港币，跨境支付商户不建议使用0/y
	String orderCurrency=JlAppResources.getProperty("orderCurrency");
	//订单过期时间:
	String orderExpireDatetime=JlAppResources.getProperty("orderExpireDatetime");
	//付款人电话联系方式
	String payerTelephone=request.getParameter("payerTelephone")==null?"":request.getParameter("payerTelephone");
	//付款人邮件联系方式
	String payerEmail=request.getParameter("payerEmail")==null?"":request.getParameter("payerEmail");
	//付款人姓名
	String payerName=request.getParameter("payerName")==null?"":request.getParameter("payerName");
	//付款人类型及证件号
	String payerIDCard=request.getParameter("payerIDCard")==null?"":request.getParameter("payerIDCard");
	//合作伙伴的商户号
	String pid=request.getParameter("pid")==null?"":request.getParameter("pid");
	//商品名称
	String productName=request.getParameter("productName")==null?"":request.getParameter("productName");
	//商品标识
	String productId=request.getParameter("productId")==null?"":request.getParameter("productId");
	//商品数量
	String productNum=request.getParameter("productNum")==null?"":request.getParameter("productNum");
	//商品单价
	String productPrice=request.getParameter("productPrice")==null?"":request.getParameter("productPrice");
	//商品描述
	String productDesc=request.getParameter("productDesc")==null?"":request.getParameter("productDesc");
	String ext1=request.getParameter("ext1")==null?"":request.getParameter("ext1");
	String ext2=request.getParameter("ext2")==null?"":request.getParameter("ext2");
	String extTL=request.getParameter("extTL");//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	//银行或预付卡发卡机构代码，用于指定支付使用的付款方机构。接入手机网关时，该值留空payType为0时，issuerId必须为空——显示该商户支持的所有支付类型和各支付类型下支持的全部发卡机构
	String issuerId=request.getParameter("issuerId")==null?"":request.getParameter("issuerId");
	String pan=request.getParameter("pan")==null?"":request.getParameter("pan");
	String tradeNature = request.getParameter("tradeNature")==null?"":request.getParameter("tradeNature");
	String sign="";
	//若直连telpshx渠道，payerTelephone、payerName、payerIDCard、pan四个字段不可为空
	//其中payerIDCard、pan需使用公钥加密（PKCS1格式）后进行Base64编码
	if(null!=payerIDCard&&!"".equals(payerIDCard)){
		try{
			//payerIDCard = SecurityUtil.encryptByPublicKey("C:\\TLCert.cer", payerIDCard);
			payerIDCard = SecurityUtil.encryptByPublicKey("/cert/TLCert.cer", payerIDCard);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	if(null!=pan&&!"".equals(pan)){
		try{
			pan = SecurityUtil.encryptByPublicKey("/cert/TLCert.cer", pan);
			//pan = SecurityUtil.encryptByPublicKey("C:\\TLCert.cer", pan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//构造订单请求对象，生成signMsg。
	com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
	if(null!=inputCharset&&!"".equals(inputCharset)){
		requestOrder.setInputCharset(Integer.parseInt(inputCharset));
	}
	requestOrder.setPickupUrl(pickupUrl);
	requestOrder.setReceiveUrl(receiveUrl);
	requestOrder.setVersion(version);
	if(null!=language&&!"".equals(language)){
		requestOrder.setLanguage(Integer.parseInt(language));
	}
	requestOrder.setSignType(Integer.parseInt(signType));
	requestOrder.setPayType(Integer.parseInt(payType));
	requestOrder.setIssuerId(issuerId);
	requestOrder.setMerchantId(merchantId);
	requestOrder.setPayerName(payerName);
	requestOrder.setPayerEmail(payerEmail);
	requestOrder.setPayerTelephone(payerTelephone);
	requestOrder.setPayerIDCard(payerIDCard);
	requestOrder.setPid(pid);
	requestOrder.setOrderNo(orderNo);
	requestOrder.setOrderAmount(Long.parseLong(orderAmount));

	requestOrder.setOrderCurrency(orderCurrency);
	requestOrder.setOrderDatetime(orderDatetime);
	requestOrder.setOrderExpireDatetime(orderExpireDatetime);
	requestOrder.setProductName(productName);
	if(null!=productPrice&&!"".equals(productPrice)){
		requestOrder.setProductPrice(Long.parseLong(productPrice));
	}
	if(null!=productNum&&!"".equals(productNum)){
		requestOrder.setProductNum(Integer.parseInt(productNum));
	}	
	requestOrder.setProductId(productId);
	requestOrder.setProductDesc(productDesc);
	requestOrder.setExt1(ext1);
	requestOrder.setExt2(ext2);
	requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	requestOrder.setPan(pan);
	requestOrder.setTradeNature(tradeNature);
	requestOrder.setKey(key); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

	String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
	String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
	System.out.println(strSrcMsg+"-----------"+strSignMsg);
	%>
	
	<!--
		1、订单可以通过post方式或get方式提交，建议使用post方式；
		   提交支付请求可以使用http或https方式，建议使用https方式。
		2、通联支付网关地址、商户号及key值，在接入测试时由通联提供；
		   通联支付网关地址、商户号，在接入生产时由通联提供，key值在通联支付网关会员服务网站上设置。
	-->
	<center>
	<!--================= post 方式提交支付请求 start =====================-->
    <!--================= 测试地址为 http://ceshi.allinpay.com/gateway/index.do =====================-->
	<!--================= 生产地址请在测试环境下通过后从业务人员获取 (https://service.allinpay.com/gateway/index.do )=====================-->
	<form name="SendOrderForm" action="<%=serverUrl %>" method="post">
		<input type="hidden" name="inputCharset" value="<%=inputCharset%>"/>
		<input type="hidden" name="pickupUrl" value="<%=pickupUrl%>"/>
		<input type="hidden" name="receiveUrl" value="<%=receiveUrl%>" />
		<input type="hidden" name="version" value="<%=version %>"/>
		<input type="hidden" name="language" value="<%=language %>" />
		<input type="hidden" name="signType" value="<%=signType%>"/>
		<input type="hidden" name="merchantId" value="<%=merchantId%>" />
		<input type="hidden" name="payerName" value="<%=payerName%>"/>
		<input type="hidden" name="payerEmail" value="<%=payerEmail%>" />
		<input type="hidden" name="payerTelephone" value="<%=payerTelephone %>" />
		<input type="hidden" name="payerIDCard" value="<%=payerIDCard %>" />
		<input type="hidden" name="pid" value="<%=pid%>"/>
		<input type="hidden" name="orderNo" value="<%=orderNo%>" />
		<input type="hidden" name="orderAmount" value="<%=orderAmount %>"/>
		<input type="hidden" name="orderCurrency" value="<%=orderCurrency%>" />
		<input type="hidden" name="orderDatetime" value="<%=orderDatetime%>" />
		<input type="hidden" name="orderExpireDatetime" value="<%=orderExpireDatetime %>"/>
		<input type="hidden" name="productName" value="<%=productName%>" />
		<input type="hidden" name="productPrice" value="<%=productPrice%>" />
		<input type="hidden" name="productNum" value="<%=productNum %>"/>
		<input type="hidden" name="productId" value="<%=productId%>" />
		<input type="hidden" name="productDesc" value="<%=productDesc%>" />
		<input type="hidden" name="ext1" value="<%=ext1%>" />
		<input type="hidden" name="ext2" value="<%=ext2%>" />
		<input type="hidden" name="payType" value="<%=payType%>" />
		<input type="hidden" name="issuerId" value="<%=issuerId%>" />
		<input type="hidden" name="pan" value="<%=pan %>" />
		<input type="hidden" name="tradeNature" value="<%=tradeNature %>" />
		<input type="hidden" name="signMsg" value="<%=strSignMsg %>" />
	    <input type="submit" value="提交"/>
	 </form>
	<!--================= post 方式提交支付请求 end =====================-->
	</center>
	<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
	<table border="1" cellpadding="1" cellspacing="1" align="center">
			<!-- 报文参数-->
			<tr>
				<td width="40%">1. 编码:</td>
				<td> <%=inputCharset%></td>
			</tr>
			<tr>
				<td>2. 取货地址:</td>
				<td> <%=pickupUrl%></td>
			</tr>
			<tr>
				<td>3. 商户系统通知地址:</td>
				<td> <%=receiveUrl%></td>
			</tr>
			<tr>
				<td>4. 报文版本号:</td>
				<td> <%=version%></td>
			</tr>
			<tr>
				<td>5. 语言:</td>
				<td><%=language %></td>
			</tr>
			<tr>
				<td>6. 签名方式:</td>
				<td><%=signType%></td>
			</tr>
			<!-- 买卖双方信息参数 -->
			<tr>
				<td>7. 商户号:</td>
				<td> <%=merchantId%></td>
			</tr>
			<tr>
				<td>8. 付款人名称:</td>
				<td> <%=payerName%></td>
			</tr>
			<tr>
				<td>9. 付款人联系email:</td>
				<td> <%=payerEmail%></td>
			</tr>
			<tr>
				<td>10. 付款人电话:</td>
				<td> <%=payerTelephone%></td>
			</tr>
			<tr>
				<td>11. 付款人证件号:</td>
				<td><textarea cols="60" rows="1"><%=payerIDCard %></textarea></td>
			</tr>
			<tr>
				<td>12. 合作伙伴的商户号:</td>
				<td> <%=pid%></td>
			</tr>
			<!-- 业务参数 -->
			<tr>
				<td>13. 商户系统订单号:</td>
				<td> <%=orderNo%></td>
			</tr>
			<tr>
				<td>14. 订单金额(单位分):</td>
				<td> <%=orderAmount%></td>
			</tr>
			<tr>
				<td>15. 金额币种:</td>
				<td> <%=orderCurrency %></td>
			</tr>
			<tr>
				<td>16. 商户的订单创建时间:</td>
				<td> <%=orderDatetime%></td>
			</tr>
			<tr>
				<td>17. 订单过期时间:</td>
				<td> <%=orderExpireDatetime%></td>
			</tr>
			<tr>
				<td>18. 商品名称:</td>
				<td> <%=productName%></td>
			</tr>
			<tr>
				<td>19. 商品单价:</td>
				<td> <%=productPrice%></td>
			</tr>
			<tr>
				<td>20. 商品数量:</td>
				<td> <%=productNum %></td>
			</tr>
			<tr>
				<td>21. 商品标识:</td>
				<td> <%=productId%></td>
			</tr>
			<tr>
				<td>22. 商品描述:</td>
				<td> <%=productDesc%></td>
			</tr>
			<tr>
				<td>23. 附加参数1:</td>
				<td> <%=ext1 %></td>
			</tr>
			<tr>
				<td>24. 附加参数2:</td>
				<td> <%=ext2 %></td>
			</tr>			
			<tr>
				<td>25. 支付方式:</td>
				<td> <%=payType%></td>
			</tr>
			<tr>
				<td>26. 发卡行机构号:</td>
				<td> <%=issuerId%></td>
			</tr>
			<tr>
				<td>27. 付款人支付卡号:</td>
				<td> <textarea cols="60" rows="1"><%=pan %></textarea></td>
			</tr>
			<tr>
				<td>28. 贸易类型:</td>
				<td><%=tradeNature %></td>
			</tr>
			<tr>
				<td>29. 报文签名后内容:</td>
				<td> <%=strSignMsg %></td>
			</tr>
	</table>
	<center>

	<div>&nbsp;</div>
	<div>组成签名信息的源串: <textarea cols="100" rows="4"><%=strSrcMsg %></textarea></div>
	</center>
	</body>
	<script type="text/javascript">
	window.onload = function(){
	//openWait();
	SendOrderForm.submit();
	}
</script>
	</html>
	
