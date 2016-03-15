<%
/* *
 功能：支付宝服务器异步通知页面
 版本：3.3
 日期：2012-08-17
 说明：
 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 //***********页面功能说明***********
 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
 该页面调试工具请使用写文本函数logResult，该函数在com.alipay.util文件夹的AlipayNotify.java类文件中
 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
 //********************************
 * */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.alipayTimely.util.*"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.alipayTimely.config.*"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="com.jlsoft.utils.JlAppResources"%>
<%
//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			String ss=new String(values[i].getBytes("ISO-8859-1"),"UTF-8");
			valueStr = (i == values.length - 1) ? valueStr + ss
					: valueStr + ss + ",";
		}
		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
		
		params.put(name, valueStr);
	}
	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
	//退款批次号
	String batch_no = new String(params.get("batch_no"));
	//必填


	//退款成功总数
	String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");
	//必填，0<= success_num<= batch_num


	//处理结果详情
	String result_details = new String(params.get("result_details"));
	//必填，详见“6.3 处理结果详情说明”


	//解冻结果明细
	//String unfreezed_deta = new String(request.getParameter("unfreezed_deta").getBytes("ISO-8859-1"),"UTF-8");
	//格式：解冻结订单号^冻结订单号^解冻结金额^交易号^处理时间^状态^描述码

	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)/
	boolean isVerifySuccess=AlipayNotify.verify(params);
	if(isVerifySuccess){//验证成功
		//////////////////////////////////////////////////////////////////////////////////////////
		//请在这里加上商户的业务逻辑程序代码
		  String urlvalue=JlAppResources.getProperty("timely_header_url")+"/AlipayOnline/update_thd_notify_url.action?batch_no="+batch_no+"&status=1";
		  URL url = new URL(urlvalue);
          HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
          BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
              .getInputStream()));
          String inputLine = in.readLine().toString();
		  if("{\"dataType\":\"xml\",\"data\":{\"is_update_success\":\"1\"}}".equals(inputLine)){
		  }else{
		  }   
		//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
		
		//判断是否在商户网站中已经做过了这次通知返回的处理
			//如果没有做过处理，那么执行商户的业务程序
			//如果有做过处理，那么不执行商户的业务程序
			
		out.println("success");	//请不要修改或删除

		//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

		//////////////////////////////////////////////////////////////////////////////////////////
	}else{//验证失败
		  String urlvalue=JlAppResources.getProperty("timely_header_url")+"/AlipayOnline/update_thd_notify_url.action?batch_no="+batch_no+"&status=0&result_details="+result_details;
		  URL url = new URL(urlvalue);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
            .getInputStream()));
        String inputLine = in.readLine().toString();
		  if("{\"dataType\":\"xml\",\"data\":{\"is_update_success\":\"1\"}}".equals(inputLine)){
		  }else{
		  }   
		out.println("fail");
	}
%>
