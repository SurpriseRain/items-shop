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
#pay .pay_main .text{ float:left;  margin-left:-70px;}
#pay .pay_main .text h4{width: 100%; float:left;  line-height:40px; margin:0px 0 10px 0; padding:0px; font-size:25px; color:#3c3c3c;}
#pay .pay_main .text span img{margin:23px 0 10px 0; padding:0px; }
#pay .pay_main .text label{width: 100%;margin-top: 10px; float:left; font-size:18px; color:#444; margin-right:20px; line-height:30px;}
</style>
</head>
<body>
<!-- head开始 -->
<div id="header"></div>
<!-- head结束 -->
<div id="banner_main">
	<div id="pay">
		<div class="pay_main">
  			<div class="text">
  			   <span><img src="/customer/qfy/images/gwc.png"></img></span>
    			<h4>购物车空空的哦~，去看看心仪的商品吧~ </h4>
    			<label><a href="/customer/qfy/index.html">[ 去购物 ]</a></label>
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
	$("#header").load("/customer/qfy/head-08.jsp",function(reponse,status){
		if(status=="success"){
			$(".sy").css({"color":"#ff8800"});
		}
	});
	$("#footer").load("/customer/qfy/foot-08.html");
});
</script>
</html>