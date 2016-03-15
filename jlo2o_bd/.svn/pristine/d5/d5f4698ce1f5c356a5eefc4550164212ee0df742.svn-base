var rydm=$.getUrlParam("XTCZY01");
var xtczy02=$.getUrlParam("XTCZY02");
var zcxx01=$.getUrlParam("ZCXX01");
//验证发送时间/s
var count = 60;
//验证码失效时间/s
var outCount = 120;

$(document).ready(function(){
	//加载初始时间
	$("#endtime").html(count);
	$("#footer").load("/customer/sydjt/foot.html");
	//获取公司类型、用户名、密码
	$("#gslx").val($.getUrlParam("gslx"));
	$("#username").val($.getUrlParam("username"));
	$("#password").val($.getUrlParam("password"));
	refreshCaptchaImage();
})
var smsYzm=0;
var smsFlag=0;
function GetNumber(){
	$("#endtime").html(count);
	outCount--;
	if(outCount > 0&&count==0) {
		setTimeout(GetNumber, 1000);
	}else if(outCount==0){
		smsYzm=0;
	}
	if(count > 0) {
	    count--;
		setTimeout(GetNumber, 1000);
	}else{
		$("#endtime").html("0");
		$(".yzsj").attr("onclick","sendPhone()");
		$(".yzsj").attr("style","color: #000;");		
	}
}

function sendPhone(){
	//删除单击事件
    $(".yzsj").removeAttr("onclick");
	//验证发送时间/s
	count = 60;
	//验证码失效时间/s
	outCount = 120;
	//短信失效时间清0
    smsYzm=0;
	smsFlag=0;
    if(!checkNull()){
		$(".yzsj").attr("onclick","sendPhone()");
		return false;
	};
    //随机验证码
	var yzm1=parseInt(Math.random()*10);
	var yzm2=parseInt(Math.random()*10);
	var yzm3=parseInt(Math.random()*10);
	var yzm4=parseInt(Math.random()*10);
	var shjyzm = yzm1+""+yzm2+""+yzm3+""+yzm4;
	smsYzm=shjyzm;
	var json={};
	json.SJHM=$("#phone").val();	json.FSNR="您好，验证码为"+smsYzm+"（兴隆客服绝不会索要此验证码，切勿告知他人），请在页面中输入以完成验证,有效时间2分钟。"//有问题请致电400-6-6-5500转7
	json.DJLX=8;
	json.YWDH="";
	json.ZCXX01=zcxx01;
	//检查手机是否已注册
	var url="/Register/selectSms.action?XmlData="+JSON.stringify(json);
    var smscheck=visitService(url);
	if(smscheck.flag=="1003"){
		smsFlag=smscheck.flag;
		$("#phone").siblings(".tishi").empty();
		$("#phone").siblings(".tishi").append('<div class="tishi_jt"></div><div class="tishi_bq"></div><font color="red">此手机号已注册！</font>'); $("#phone").siblings(".tishi").css({"display":"block","border-color":"#e4e4e4","background-color":"#f5f5f5","color":"#666"}); 
		$("#phone").siblings(".tishi").children(".tishi_jt").css({"background-position":"-8px -511px"})
		$("#phone").siblings(".tishi").children(".tishi_bq").css({"background-position":"-202px -246px"})
		$(".yzsj").attr("onclick","sendPhone()");
		return false;
	}

	if(smsFlag==0){
		//$(".yzsj").removeAttr("onclick");
		$(".yzsj").attr("style","color: #999;");
		GetNumber();
	}else{
		$(".yzsj").attr("onclick","sendPhone()");
	}
	//var xmlData=[];
	if(smsYzm>0&&smsFlag==0){
	//xmlData.push(json);
    var url="/Register/insertSms.action?XmlData="+JSON.stringify(json);
    visitService(url);
  }
}

//加载地址信息
function showDQXX(id){
  $("#province"+id).parent().children("select").each(function(index,e){
  	if(index==0){
  	  var num=0;
  	  var dqjb=1;
    	var xmlData=[];
      	var json={};
        json.qydm=num;
    	json.dqjb=dqjb;
      	xmlData.push(json);
      	var url="/oper_qydz/qryDQ.action?XmlData="+JSON.stringify(xmlData);
      	ajaxQ({
      		"url":url,
      		"callback":function(data){
      			var dqxx = data.mapList;
      			 $(dqxx).each(function(i,dqxxjson){
      		  		$("#province"+id).append("<option>"+dqxxjson.DQXX02+"</option>");
      		  		$("#province"+id).find("option").eq(i+1).val(dqxxjson.DQXX01);
      		  	  });		 
      		  	  $(e).bind("change",function(inx){
      		  		setVal(e,"#city"+id,2);
      		  		var con="<option value='0'>请选择</option>";
      		  		$("#countryQY").html(con).val(0);
      		  		var width = $("#main").width()-$("#provinceQY").width()-$("#cityQY").width()-$("#countryQY").width()-260;
      		  		$("#QYdzdetail").css("width", 200+"px");
      		  	  });	
      		  	}  			
      		});
      	}
  	else{
	  	  $(e).bind("change",function(inx){
	  		setVal(this,"#country"+id,3);
	  		var width = $("#main").width()-$("#provinceQY").width()-$("#cityQY").width()-$("#countryQY").width()-260;
	  		$("#QYdzdetail").css("width", 200+"px"); 
	  	  });	
	  	}	
   });
}
//设置地址信息
function setVal(objnow,objaft,dqjb){
  	var num=$(objnow).find("option:selected").val();  	
  	var xmlData=[];
  	var json={};
    json.qydm=num;
	json.dqjb=dqjb;
  	xmlData.push(json);
  	var url="/oper_qydz/qryDQ.action?XmlData="+JSON.stringify(xmlData);
  	ajaxQ({
  		"url":url,
  		"callback":function(data){
  			var list = data.mapList;
  			$(list).each(function(i,json){
  		  		if(i==0){
  		  	 		$(objaft).empty();
  		  	  		$(objaft).append("<option value='0'>请选择</option>");
  		    	}
  		  		$(objaft).append("<option>"+json.DQXX02+"</option>");
  		  		$(objaft).find("option").eq(i+1).val(json.DQXX01);
  		  	});
  		}
  	});
}

function refreshCaptchaImage(){
	$("#realCaptcha").val("");
	$("#yzmimg").attr("src","/servlet/CaptchaOutput?font=Helvetica&&fontsize=22&&min-width=150&&padding-x=5&&padding-y=5&&random="+Math.random());
}
function setCaptchaText(){
	var data = visitService("/UserLogin/searchCaptcha.action");
	var captchaText=data.captchaText;  
	$("#realCaptcha").val(captchaText);
}

//判断输入项是否合法
function checkNull(){
	if($("#phone").val().length == 0){
		alert("请输入手机号码");
		return false;
	}else{
		if(!checkPhone()){
			return false;
		}
	}
	return true;
}
function checkPhone(){
	var isMobile=/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
	if(!isMobile.test($("#phone").val())){
		alert("请正确填写手机号码,例如:13415764179");
		return false;
	}else{
		return true;
	}
}
function checkTel(){
	var isPhone=/^((0\d{2,3}))?(\d{7,8})(-(\d{3,}))?$/;   //座机验证规则
	if(!isPhone.test($("#lxdh").val())){
		alert("请正确填写联系电话,例如:0104816048");
		return false;
	}else{
		return true;
	}
}

function checkEmail(){
		var email=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		if(!email.test($("#email").val())){
			alert("邮箱地址不合法！");
			return false;
		}else{
			return true;
		}
}
//注册完善方法
function perfectSave(){
    $(".reg_bot").removeAttr("onclick");
	var realCaptcha= $("#realCaptcha").val().toLowerCase();
	var userYzm= $("#userYzm").val().toLowerCase();
	if($("#msgYzm").val()==""){
		alert("短信验证码不能为空");
		$(".reg_bot").attr("onclick","perfectSave()");
		return false;
	}
	if($("#email").val().length == 0){
		alert("请输入邮箱地址");
		return false;
	}else{
		if(!checkEmail()){
			$(".reg_bot").attr("onclick","perfectSave()");
			return false;
		}
	}
	if(userYzm==""||realCaptcha==""){
		alert("验证码不能为空");
		$(".reg_bot").attr("onclick","perfectSave()");
		return false;
	}
	if(!$(".checkbox").is(":checked")){
		alert("请阅读并勾选电器服务云协议！");
		$(".reg_bot").attr("onclick","perfectSave()");
		return false;
	}
	if(smsYzm!=$("#msgYzm").val()){
		alert("短信验证码错误");
		$(".reg_bot").attr("onclick","perfectSave()");
		return false;
	}
	if(userYzm!=realCaptcha){
		alert("验证码输入不正确");
		$(".reg_bot").attr("onclick","perfectSave()");
		return false;
	}
	var flag = checkNull();
	if(flag){
		//弹出遮罩层
		openWait();
		//修改注册信息
	 	var XmlData = {};
	 	XmlData["SJLY"] = "0";
	 	XmlData["ZCXX06"]=$("#phone").val();
	 	XmlData["ZCXX55"]=$("#lxdh").val();
	 	XmlData["ZCXX09"]=$("#email").val();
	 	XmlData["XTCZY01"]=rydm;
	 	XmlData["ZCXX01"]=zcxx01;
	 	//拼接图片
	   	var fileArray=[];
	   	var url="/Register/updateSytjtRegister.action?jmbj=1";
	   	$.ajaxFileUpload({
				type:"POST",
				secureuri:false,
				fileElementId:fileArray,
				url:encodeURI(url),
				data:{"XmlData":escape(JSON.stringify(XmlData))},
				dataType:"text",
				success: function(data) { 
					//关闭遮罩层
					top.closeWait();
					
					var json = jQuery.parseJSON(data);
					var jsondata = json.data;
					var data1=jsondata.state;
					if(data1=="success"){
					    var username = $.trim($("#XTCZY01").val());
					var password = $("#XTCZY02").val();
					var arr = [];
					var o = {};
					o["XTCZY01"] = rydm;
					o["XTCZY02"] = xtczy02;
					arr.push(o);
					var users=arr;// modify by liuwx jhj  2014年8月5日10:53:39 初始化users
					var data = visitService("/UserLogin/login.action?XmlData="
							+ JSON.stringify(arr));
					var loginState = data.state;
					if (loginState == 1) {
						alert("用户名有误!");
						$("#XTCZY01").focus();
					} else if (loginState == 2) {
						alert("密码错误!");
						$("#XTCZY02").focus();
					} else if (loginState == 3) {
							if (users != null) {
									o.userName = rydm;
									o.passWord = xtczy02;
									users.push(o);
							}
							$.cookie("users", null, {
								path : '/'
							});
							$.cookie("users", JSON.stringify(users), {
								expires : 90,
								path : '/'
							});
						}
						else// modify by liuwx jhj  2014年8月5日10:53:39  不记录密码时候 清空 users
						{
							$.cookie("users", null, {
								path : '/'
							});
						};
						var userInfo = data.userInfo;
						var updateDate = new Date();
						updateDate.setTime(updateDate.getTime() + 1 * 24 * 60 * 60
								* 1000);//超过1天,cookie需要重新更新
						userInfo.UPDATETIME = updateDate.getTime();
						$.cookie("userInfo", null, {
							path : '/'
						});
						$.cookie("userInfo", JSON.stringify(userInfo), {
							expires : 30,
							path : '/'
						});
	 					//跳转到成功页面
						url = pubJson.O2OUrl+"/customer/sydjt/register/registerPerfect3.html?ZCXX02="+escape(rydm);
						window.location.href=url;
					}else{
						alert("保存失败!");
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					//关闭遮罩层
					top.closeWait();
					alert(textStatus);
			    }
			});
		//与数据库交互完毕
	}else{
		$(".reg_bot").attr("onclick","perfectSave()");
	}
}