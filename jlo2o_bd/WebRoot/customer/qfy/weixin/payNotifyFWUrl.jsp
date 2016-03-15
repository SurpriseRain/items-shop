<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.weixin.util.MD5Util"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="com.jlsoft.utils.JlAppResources"%>
<%@ page import="java.util.*"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.weixin.util.XMLUtil"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.weixin.util.WeixinUtil"%>


<%
	//---------------------------------------------------------
	//微信支付支付通知（后台通知）示例，商户按照此文档进行开发即可
	//---------------------------------------------------------

	//创建支付应答对象
	String partnerKey = JlAppResources
			.getProperty("WEIXIN_PARTNER_KEY");
	Map parameters = XMLUtil.doXMLParse(WeixinUtil
			.getPostString(request));
	Map newparameters = new HashMap();
	String ValidSign = "";
	Set set = parameters.keySet();
	Iterator it = set.iterator();
	while (it.hasNext()) {
		String k = (String) it.next();
		String name = (String) parameters.get(k);
		String v = name;
		if (!"sign".equals(k) && null != v && !"".equals(v)) {
			newparameters.put(k, v);
		}
		if ("sign".equals(k)) {
			ValidSign = v;
		}
	}
	//商户订单号
	String out_trade_no = parameters.get("out_trade_no").toString();
	//财付通订单号
	String transaction_id = parameters.get("transaction_id").toString();
	//创建请求对象
	String newsign=WeixinUtil.getsign2(newparameters, partnerKey);


	if (ValidSign.equals(newsign) ) {

		//金额,以分为单位
		String total_fee = parameters.get("total_fee").toString();
		//支付结果
		String return_code = parameters.get("return_code").toString();
		//判断签名及结果
		if ("SUCCESS".equals(return_code)) {
			//------------------------------
			//即时到账处理业务开始
			//------------------------------
		 	String urlvalue=JlAppResources.getProperty("WEIXIN_HEADER")+"/WeixinController_FW/update_notify_fw_url.action?out_trade_no="+out_trade_no+"&trade_no="+transaction_id+"&status=1";
		    URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                .getInputStream()));
            String inputLine = in.readLine().toString();
            //System.out.println("inputLine=="+inputLine);
            //此处以后记录日志用
			if("{\"dataType\":\"xml\",\"data\":{\"is_update_success\":\"1\"}}".equals(inputLine)){
				
			}else{
				
			}  
			//处理数据库逻辑
			//注意交易单不要重复处理
			//注意判断返回金额

			//------------------------------
			//即时到账处理业务完毕
			//------------------------------

			//System.out.println("success 后台通知成功");	
			//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
			out.println("success");
			
		} else {
			//System.out.println("fail 支付失败");	
			out.println("fail");
		}
		
	} else {//sha1签名失败
		
	 	 String urlvalue=JlAppResources.getProperty("WEIXIN_HEADER")+"/WeixinController_FW/update_notify_fw_url.action?out_trade_no="+out_trade_no+"&trade_no="+transaction_id+"&status=0";
		 URL url = new URL(urlvalue);
         HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
         String inputLine = in.readLine().toString();
         //此处以后记录日志用
		if("{\"dataType\":\"xml\",\"data\":{\"is_update_success\":\"1\"}}".equals(inputLine)){
		
		}else{
		
		}  
		//System.out.println("fail -SHA1 failed");
		out.println("fail");
	}
%>

