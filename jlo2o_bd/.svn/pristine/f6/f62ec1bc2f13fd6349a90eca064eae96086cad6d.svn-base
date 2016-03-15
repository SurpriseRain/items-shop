//手机验证码获取
//验证发送时间/s
var count = 60;
//验证码失效时间/s
var outCount = 120;
// setTimeOut的ID
var t;
//$("#endtime").html(count); phone：手机号;obj：需要选手倒计时的对象;flag：true没有手机号发送短信 、false有手机号是发送短信
function sendPhone(phone,obj,flag,th){
	// 移除setTimeOut方法
	clearTimeout(t);
    //$(this).removeAttr("onclick");
	//验证发送时间/s
	count = 60;
	//验证码失效时间/s
	outCount = 120;
	//短信失效时间清0
    smsYzm=0;
	smsFlag=0;
    if(!checkNulls()){
		//$(th).attr("onclick","sendPhone('"+phone+"',$('#endtime'),'"+flag+"',this)");
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
	json.SJHM=phone;
	json.FSNR="您好，验证码为"+smsYzm+"（客服绝不会索要此验证码，切勿告知他人），请在页面中输入以完成验证,有效时间2分钟。";
	json.DJLX=5;
	json.YWDH="";
	//检查手机是否已注册
	var url="/Register/selectSms.action?XmlData="+JSON.stringify(json);
    var smscheck=visitService(url);
	if(smscheck.flag=="1003"&&flag){
		$('.ts').css({'display':'block'}).children('label').text("该手机号已经注册！");
		return false;
	}else if(smscheck.flag=="1000"&&!flag)
	{
		$('.ts').css({'display':'block'}).children('label').text("该手机号未注册！");
		return false;
	}else{
		$('.ts').css({'display':'none'});
	}
	//删除单击事件
	$(th).removeAttr("onclick");
	if(smsFlag==0){
		GetNumber($(th),phone,obj,flag);
	}else{
		$(th).attr("onclick","sendPhone($('#phone').val(),$('#endtime'),'"+flag+"',this)");
	}
	if(smsYzm>0&&smsFlag==0){
	    var url="/Register/insertRegisterCode.action?XmlData="+JSON.stringify(json);
	    visitService(url);
  	}
}

//验证倒计时
var smsYzm=0;
var smsFlag=0;
function GetNumber(id,phone,obj,flag){
	
	obj.html(count);
	outCount--;
	if(outCount > 0 && count == 0) {
		t = setTimeout(function(){GetNumber(id,phone,obj,flag);}, 1000);
	} else if(outCount==0){
		smsYzm=0;
	}
	
	if(count > 0) {
		count--;
	    t = setTimeout(function(){GetNumber(id,phone,obj,flag);}, 1000);
	}else{
		obj.html("0");
		id.attr("onclick","sendPhone($('#phone').val(),$('#endtime'),'"+flag+"',this)");
	}
}

//判断输入项是否合法
function checkNulls(){
	if($("#phone").val().length == 0){
		$('.ts').css({'display':'block'}).children('label').text("请输入手机号码");
		//alert("请输入手机号码");
		return false;
	}else{
		if(!checkPhone()){
			return false;
		}
	}
	return true;
}

function checkPhone(){
	var isMobile= /(^1[3|4|5|7|8][0-9]{9}$)/; //手机号码验证规则
	if(!isMobile.test($("#phone").val())){
		$('.ts').css({'display':'block'}).children('label').text("请正确填写手机号码");
		//alert("请正确填写手机号码,例如:13415764179");
		return false;
	}else{
		$('.ts').css({'display':'none'});
		return true;
	}
}