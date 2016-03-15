var JL={};
JL.top_up = function(money){
	var json = {};
	json["PayMoney"] = money;
	json["WLDW01"] = userInfo.RYDM;
	json["BM01"] = userInfo.BMDM;
	
	window.open(pubJson.FormUrl+"/InstantTradePay/formatParamater.do?XmlData="+JSON.stringify(json));
}

JL.tip = function(message,config){
	config = JL.isNull(config)? {}: config;
	
	$(".jl_message p").html(message);
	//$("body,html").animate({scrollTop:140},1000);  
	$(".jl_message").slideDown();
	$(".jl_message i.fa-times").click(function(){
		$(".jl_message").slideUp();
	});
	
	var closeTime = JL.isNull(config["closeTime"])? 5: config["closeTime"];
	setTimeout(function(){
		$(".jl_message i.fa-times").click();
	},closeTime*1000);
}

JL.formatDate = function(addDay,time){ 
	var d=new Date();
	d.setDate(d.getDate()+addDay*1);//当前日期+几天
	var str=''; 
	var FullYear = d.getFullYear();
	var Month = d.getMonth()+1<10?'0'+(d.getMonth()+1):d.getMonth()+1;
	var Day = d.getDate()<10?'0'+d.getDate():d.getDate();
	var Hours = d.getHours()<10?'0'+d.getHours():d.getHours();
	var Minutes = d.getMinutes()<10?'0'+d.getMinutes():d.getMinutes();
	var Seconds = d.getSeconds()<10?'0'+d.getSeconds():d.getSeconds();
	str +=FullYear+'-'; //获取当前年份 
	str +=Month+'-'; //获取当前月份（0——11） 
	if(time==1){
		str +=Day;
	}else if(time==2){
		str +=Day+' '; 
		str +=Hours+':'; 
		str +=Minutes+':'; 
		str +=Seconds; 
	}
	return str; 
} 

JL.isNull = function(str){
	if(str == undefined || str == null || str === "" ||str === "undefined" || str === "null" || str === [] || str === {})
		return true;
	else
		return false;
}
JL.isString = function(str){
	if( typeof str == "string" )
		return true;
	else
		return false;
}

JL.changeClass = function(obj,o,n){
	$(obj).removeClass(o);
	$(obj).addClass(n);
}

JL.ajax = function(json){
	var contentType = JL.isNull(json.contentType) ? "" : json.contentType;
	var async = JL.isNull(json.async) ? false : true;
	var callback = JL.isNull(json.callback) ? null : json.callback;
	var type = JL.isNull(json.type) ? "POST" : "GET";
	var data = JL.isNull(json.data) ? {} : json.data;
	data["sid"]=Math.random();
	var src = JL.isNull(json.src) ? pubJson.FormUrl : json.src;
	var url = JL.isNull(json.url) ? "" : json.url;
	
	var ajaxJson = {};
	if(contentType!="")ajaxJson["contentType"]=contentType;
	ajaxJson["async"]=async;
	ajaxJson["type"]=type;
	ajaxJson["url"]=src+url;
	ajaxJson["data"]=data;
	ajaxJson["success"]=function(data) {
		try {
			returnData = JSON.parse(data);
			if(callback!=null && typeof callback == "function"){
				callback(returnData);
			}
		} catch (e) {
			returnData=data; 
		}
	};
	
	var returnData=null;
	$.ajax(ajaxJson);
	return returnData;
}

JL.download = function (url, data, method) {
	if (url && data) { // data 是 string 或者 array/object
		$("[data-download]").remove();
		var iframe = $("<iframe>");
		iframe.attr("data-download","true");
		iframe.css("display","none");
		iframe.appendTo("body");
		var form = $("<form>");
		form.attr("id", "downloadForm");
		form.attr("action", url);
		form.attr("method", (method || 'post'));
		form.appendTo(iframe.contents().find("body"));
		$.each(data, function (key,value) {
			var input = $("<input>");
			input.attr("type", "hidden");
			input.attr("name", key);
			input.val(value);
			input.appendTo(form);
	    });
		form.submit();
		//iframe.remove();
	};
}

JL.getFormURL = function(bdbh){
	var path = JL.ajax({"src":"form/getFormURL.do?bdbh="+bdbh}).data.string;
	path=path=="form/getFormURL"?"":path;
	return path;
}

JL.disabledClass = function(selector,disabled){
	if(disabled){
		$(selector).addClass("none");
		$(selector).attr("disabled","disabled");
	}else{
		$(selector).removeClass("none");
		$(selector).removeAttr("disabled");
	}
}

//IFrame重新加载高度
JL.resizeIFrame = function(obj){
	var iframe= $(obj);
	var iframeName= $(obj).attr("name");
    var innerHTML = eval("document."+iframeName) ? eval("document."+iframeName).document : iframe.contentDocument;
    if(iframe != null && innerHTML != null) {
    	$(obj).height(innerHTML.body.scrollHeight);
	}
}

JL.formatString = function(value){
	if( typeof value == "string" 
		&& value.indexOf("key") != -1  
		&& value.indexOf("value") != -1 ){
		value = JSON.parse(value)["key"];
	}else if( typeof value == "object" ){
		value = value["key"];
	}
	return value;
}
JL.formatObject = function(value){
	if( typeof value == "string"){ 
		if( value.indexOf("key") != -1 && value.indexOf("value") != -1 ){
			value = JSON.parse(value);
		}else if( value.indexOf("KEY") != -1 && value.indexOf("VALUE") != -1 ){
			value = JSON.parse(value);
		}else if( value.indexOf("key") == -1 && value.indexOf("value") == -1 ){
			value = {"key":value,"value":value};
		}
	}
	return value;
}

JL.loading = function(state){
	if(state){
		$(".jl_loading").show();
	}else{
		$(".jl_loading").fadeOut();
	}
}

JL.light = function(obj){
	$(obj).addClass("box_shadow_show");  
	var top = $(obj).offset().top - 200;
	$("body,html").animate({scrollTop:top},1000);  
	setTimeout(function(){
		$(obj).removeClass("box_shadow_show");  
	},3000);  
}
JL.isEmail = function(obj,value){
	var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/; //,//邮箱
	if(!regEmail.test(value) && !JL.isNull(value)){
		JL.tip("Email格式错误！");
		JL.light(obj); 
		return false;
	}
	return true;
}
JL.isPhone = function(obj,value){
	var regMobile = /^0?1[3|4|5|7|8][0-9]\d{8}$/; //手机
	if(!regMobile.test(value) && !JL.isNull(value)){
		JL.tip("手机号码格式错误！");
		JL.light(obj); 
		return false;
	}
	return true;
}
JL.isNumber = function(obj,value){
    if(isNaN(value)){
        JL.tip("请输入正确的数字");
		JL.light(obj); 
		return false;
    }
	return true;
}

JL.globalTinyBox = {
	open: function(modalURL,modalField,show){
		$(".jl_modal > .modal_main").empty();
	    $(".jl_modal > .modal_main").load(modalURL);
	    if(!JL.isNull(show) && show){
	    	JL.changeClass($(".jl_modal"), "opacity_1", "opacity_0");
	    }
    	$(".jl_modal").fadeIn();
	},close: function(){
		JL.changeClass($(".jl_modal"), "opacity_0", "opacity_1");
		$(".jl_modal").hide();
	}
}

JL.changePrintTmp = function(obj,dymb,bdbh,jlbh,XmlData){
	var dybh=$(obj).val();
	var url=pubJson.FormUrl+"/formPrint/findFormPrint.do?dybh="+dybh+"&dymb="+JSON.stringify(dymb)+"&jlbh="+jlbh+"&bdbh="+bdbh+"&XmlData="+JSON.stringify(XmlData).replace(/%/gm,"%25").replace(/&/gm,"%26");
	location.href=url;
}
JL.print = function(dybh,data){
	sessionStorage["PRINT_DATAS"] = JSON.stringify(data);
	window.open("print.html?dybh="+dybh);
}

//页面间通过URL传值 
$.getUrlParam = function(name)
	{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) 
		return decodeURI(r[2]); 
		return null;
}

//页面存url
JL.setUrlParam=function(val){
	var obj ={};
	obj["url"]=val;
	JL["UrlParam"]=obj;
}

//页面间通过URL传值 
JL.getUrlParam=function(paramName){
	var paramValue = null;  
	var url=JL.UrlParam==undefined?"":JL.UrlParam["url"];
	if (url.indexOf("?") > 0 && url.indexOf("=") > 0) {  
		arrSource = unescape(url).substring(url.indexOf("?")+1, url.length).split("&"), i = 0; 
		while (i < arrSource.length)
			{
				if(arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase()){
					paramValue = arrSource[i].split("=")[1];
				}
				i++;
			}  
	}  
	return paramValue;  
}