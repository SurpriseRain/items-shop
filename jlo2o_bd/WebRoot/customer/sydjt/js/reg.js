$(document).ready(function(){
  $("input").focus(function(){
		  var input_text = $(this).val();
		  var uesrName_val = $("#uesrName").val(); 
		  $(this).siblings(".tishi").empty();
		  $(this).css({"border-color":"#ccc"});
		  $("#phone").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>请输入正确的手机号！')
		  $("#uesrName").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>请输入用户名！')
		  $("#uesrNameyx").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>请输入用户名或手机号或电子邮箱！')
		  $("#userPwd").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>密码为6-20个字符的数字和字母组合！')  
		  $("#userPwdwd").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>确认密码') 
		  $("#userYzm").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>验证码不区分大小写')
		  $("#email").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>邮箱可作为登录名和找回密码用')
		
		  $("input").siblings(".tishi").css({"display":"none"});
		  $(this).siblings(".tishi").css({"display":"block","border-color":"#e4e4e4","background-color":"#f5f5f5","color":"#666"}); 
		  $(this).siblings(".tishi").children(".tishi_jt").css({"background-position":"-8px -511px"})
		  $(this).siblings(".tishi").children(".tishi_bq").css({"background-position":"-202px -246px"})
	})
	/******************用户名判断*******************/
	$("#uesrName").blur(function(){
	var uesrName_val = $(this).val();
	$(this).siblings(".tishi").empty();
	if(uesrName_val == ""){
		 $(this).css({"border-color":"#ee4400"})
		 $(this).siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>用户名不能为空！')  
		 $(this).siblings(".tishi").css({"display":"block","border-color":"#ee4400","background-color":"#f5f5f5","color":"#ee4400"}); 
		 $(this).siblings(".tishi").children(".tishi_jt").css({"background-position":"-17px -511px"})
		 $(this).siblings(".tishi").children(".tishi_bq").css({"background-position":"-65px -77px"})
	}else{
		 $(this).siblings(".tishi").append('<div class="tishi_bq"></div>')  
		 $(this).siblings(".tishi").css({"display":"block","border-color":"#fff","background-color":"#fff","color":"#ccc"}); 
		 $(this).siblings(".tishi").children(".tishi_bq").css({"background-position":"-10px 0"})
	}
   })
	/******************密码判断*******************/

   	/******************用户名或邮箱判断*******************/
	$("#uesrNameyx").blur(function(){
	var uesrName_val = $(this).val();
	$(this).siblings(".tishi").empty();
	if(uesrName_val == ""){
		$(this).css({"border-color":"#ee4400"})
		$(this).siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div>用户名或手机号或电子邮箱不能为空！')  
	 $(this).siblings(".tishi").css({"display":"block","border-color":"#ee4400","background-color":"#f5f5f5","color":"#ee4400"}); 
	 $(this).siblings(".tishi").children(".tishi_jt").css({"background-position":"-17px -511px"})
	 $(this).siblings(".tishi").children(".tishi_bq").css({"background-position":"-65px -77px"})
	}else{
	  $(this).siblings(".tishi").append('<div class="tishi_bq"></div>')  
	 $(this).siblings(".tishi").css({"display":"block","border-color":"#fff","background-color":"#fff","color":"#ccc"}); 
	 $(this).siblings(".tishi").children(".tishi_bq").css({"background-position":"-10px 0"})
	}
   })
})
