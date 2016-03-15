$(document).ready(function(){
	/************首页登录/快速注册切换************/
  $(".login_pinpai > .login > h3").mouseover(function(){
	var h3_text = $(this).text();

	if(h3_text == "登 录"){
         $(this).attr("class","login_bot_xuan");
		 $(this).siblings(".login_main").css({"display":"block"});
         $(this).next("h3").attr("class","reg_bot");
		 $(this).siblings(".reg_main").css({"display":"none"});
	   }else if(h3_text == "快速注册"){
         $(this).attr("class","reg_bot_xuan");
		 $(this).siblings(".reg_main").css({"display":"block"});
         $(this).prev("h3").attr("class","login_bot");
		 $(this).siblings(".login_main").css({"display":"none"});
	   }   
	})
    $(".reg_main > .reg_info > li > .checkbox").click(function(){
	  var checkbox = $(this).attr("checked");
	  if(checkbox == "checked"){
		$(this).parent("li").next().children("a").attr("class","reg_bot_yes");  
	   }else{
		$(this).parent("li").next().children("a").attr("class","reg_bot_no");    
	   }
	})
	
	/************登录判断************/
	$(".login_bot").click(function(){
		var login_userName = $("#login_userName").val();
		if(login_userName == ""){
		  $(this).parent("li").siblings(".ts").css({"display":"block"});
		  $(this).parent("li").siblings(".ts").children("label").empty();
		  $(this).parent("li").siblings(".ts").children("label").append("用户名不能为空！")
	   }
	})
	$(".login_main > li > input").focus(function(){
		  $(this).parent("li").siblings(".ts").css({"display":"none"});
    })
	
	/************快速注册判断************/
	$(".reg_bot_yes").click(function(){
		var reg_userName = $("#reg_userName").val();
		if(reg_userName == ""){
		  $(this).parent("li").siblings(".ts").css({"display":"block"});
		  $(this).parent("li").siblings(".ts").children("label").empty();
		  $(this).parent("li").siblings(".ts").children("label").append("用户名不能为空！")
	   }
	})
	$(".reg_info > li > input").focus(function(){
		  $(this).parent("li").siblings(".ts").css({"display":"none"});
    })
	
})