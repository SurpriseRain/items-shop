
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.alipayTimely.util.*"%>
<%@ page import="com.jlsoft.o2o.interfacepackage.alipayTimely.config.*"%>

<html>
  <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>微信支付页面跳转同步通知页面</title>
		<link rel="stylesheet" type="text/css" href="/customer/qfy/css/shop-Public.css" />
		<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="/js/commons.js"></script>
		<script type="text/javascript" src="/js/json2.js"></script>
		<script type="text/javascript" src="/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="/customer/qfy/js/pubJson.js"></script>
		<script type="text/javascript">

	//获取公用默认图片路径
	var ImgUrl=pubJson.path_sptp;//图片默认路径
$(document).ready(function(){
	$("#ul0").hide();
	$(".pro").hide();
	//加载头和尾 
	$("#header").load("/customer/qfy/head-08.jsp?ss="+Math.random(),function(reponse,status){
		if(status=="success"){
		$(".sptms").css({"color":"#ff8800"});
		}
	});
	$("#footer").load("/customer/qfy/foot-08.html?ss="+Math.random());	
});

	/* /* $(document).ready(function(){
		//加载头和尾 
		$("#header").load("Head.html", function() { */
			 /*  $("#menu-lest").remove();
			  $("#header").find("a[id='qbsp']").attr("class","bian");
		      $("#header").find("a[id='sy']").attr("class","");
			});
		$("#footer").load("/customer/qfy/foot-08.html");
	}); */ 

	function ckdd(){
		window.location.href="/customer/qfy/back/shop-main.html";
	}
	</script>
	<style>
		.zfContent{margin:0 auto;width:1200px;height:auto;}
		.zfContent{display:block;width:1200px;height:400px;}
		.zfbg{width:100%;height:500px;margin:0 auto;background:url(/customer/qfy/images/zfbg_1.jpg);margin:10px 0;}
		.zfbg h4{font-size:35px;color:#fff;width:100%;text-align:center;line-height:200px;font-weight:400;padding-left:20px;}
		a.qd{width:200px;height:50px;line-height:50px;background:#fff;color:#515151;font-size:25px;border:1px solid #c2c2c2;}
		a.qd:hover{background:none;}
	</style>
  </head>
  <body>
	<!-- head开始 -->
	<div id="header"></div>
	<!-- head结束 -->
	<!-- 中间开始 -->
	<div class="zfContent"><!-- id="second" -->
		<div class="zfbg">
			<h4>您已成功支付，等待发货&nbsp;.&nbsp;.&nbsp;.</h4>
		</div>
	</div>
	<!-- 中间结束 -->
	
	<a class="qd" onclick="ckdd();">确定</a>
	<!-- footer开始 -->
	<div id="footer" class="clearfix mt20"> </div>
	<!-- footer结束 -->
	
  </body>
</html>