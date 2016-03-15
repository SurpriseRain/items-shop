$(document).ready(function(){
	$(".menu").mouseover(function(){
		$(".menu-lest").css({"display":"block"})						  
	})
	$(".menu-lest").mouseleave(function(){
		$(this).css({"display":"none"})						  
	})
})

String.prototype.getpid = function(){
  if(this.length < 2) return -1;
  else if(this.length == 2){
    return  '0';
  }
  return this.substring(0, this.length - 2);
};
//展示菜单界面
var menu = {};
menu.data = {};
menu.dbData = {};


menu.init = function(o) {
	menu.load(o);
	menu.write(o);
		//alert(10010);
}

//获取数据
menu.load = function(o){
	var XmlData=[{"PERSON_ID":o}];
	var url="/UserInfo/selectUserMenu.action?XmlData="+JSON.stringify(XmlData);
	menu.data=visitService(url);
	//查询代办菜单
	XmlData={};
	//url = pubJson.PcrmUrl+"/workflowAction/workflowList.do?PI_USERNAME="+o+"&xmlData="+JSON.stringify(XmlData);
	//menu.dbData = visitService(url);
}


//输出结果
menu.write = function(o){
	if(o=="jladmin"){
		//展示代办事宜菜单
		/**
		$(".shop-left-nav").append("<li  id='db' title='待办事宜' ><h3><span>-</span><a href='/form/currentLog.html' target = 'centerFrame'><b>待办事宜</b></h3></a></li>");
		var data=menu.dbData.resultList;*/
		/**for(var i=0;i<data.length;i++){
		   var title = data[i].GZLMC;
		   var url=pubJson.O2OUrl+"/form/stepAction.html?XMBH="+data[i].XMBH+"&GZLBH="+data[i].GZLBH+"&BZBH=1&RZBH=0";
		   var frameId="";
		   $("#db").after('<li>'+'<a href="'+url+'" class="nav" target="centerFrame" onclick="showLC('+data[i].XMBH+','+data[i].GZLBH+',1,0)">'+title+'</a></li>');	   
		}*/
	}
	
	//固定菜单展示开始
	var menuArr=menu.data.menu;
	for(var i=0;i<menuArr.length;i++){
		if(menuArr[i].LEVELS==1){
			//$(".shop-left-nav").append("<li id='cd-"+menuArr[i].CODE+"'><h3><span>-</span><b>"+menuArr[i].NAME+""+
			//	.userMain 									  "</b></h3></li>");
			$(".user_nav").append("<dl id='cd-"+menuArr[i].CODE+"' class='w12'><dt class='w12'>"+menuArr[i].NAME+"</dt></dl>");
		}else if(menuArr[i].LEVELS==2){
			var tid = menuArr[i].CODE.getpid();
			var url =menuArr[i].URL;
			url = ((menuArr[i].URL).indexOf("?")>-1)?(url+"&menuId="+menuArr[i].CODE):(url+"?menuId="+menuArr[i].CODE);
 			
			$("#cd-"+tid).append("<dd class='w12'><a id='"+menuArr[i].CODE+"'  onclick=\"changeIframeSize('"+url+"','#centerFrame')\" >"+menuArr[i].NAME+"</a></dd>");
		}
	}
}



function changeIframeSize(url,main,num,obj,index){
	$("#centerFrame").show();
	JL.setDivUrl("url",url);
	num=num==undefined?null:num;
	if(obj!=undefined){
		$(main).load(url,function(){
			$(".user_page_tab .w09").find("li").eq(index).prevAll().removeClass("xuan");
			$(".user_page_tab .w09").find("li").eq(index).addClass("xuan");
			var name = url.split("/");
			name = name[name.length-1];
			name = name.split(".html")[0];
			$(this).attr("page",name);
			try{
				var jlform = eval(name);
				jlform.setUrlParam("url",url);
				jlform.setTab(this); 
				var json = {};
				json["initfield"] = "{}";
				json["save"] = false;
				jlform.setData({});
				jlform.initForm(json);
			}catch(e){}
		});
	}else{
		if(JL.getParam("iframe")=="true"){
			//$(main).append('<iframe id="centerFrame_ifram" name="centerFrame_ifram" src="'+url+'" frameborder="0" marginheight="0" class="right-main" scrolling="auto" style="border:0px; height:auto;margin-left:10px;"></iframe>');
			$("#centerFrame_ifram").attr("src",url,function(){
				var name = url.split("/");
				name = name[name.length-1];
				name = name.split(".html")[0];
				$(this).attr("page",name);
				try{
					var jlform = eval(name);
					jlform.setUrlParam("url",url);
					jlform.setTab(this); 
					var json = {};
					json["initfield"] = "{}";
					json["save"] = false;
					jlform.setData({});
					jlform.initForm(json);
				}catch(e){}
			});
			$("#centerFrame").empty();
			$("#centerFrame").hide();
			$("#centerFrame_ifram").show();//css("display","block");
		}else{
			$("#centerFrame_ifram").empty();
			$("#centerFrame_ifram").hide();
			$(main).load(url,function(){
				var name = url.split("/");
				name = name[name.length-1];
				name = name.split(".html")[0];
				$(this).attr("page",name);
				try{
					var jlform = eval(name);
					jlform.setUrlParam("url",url);
					jlform.setTab(this); 
					var json = {};
					json["initfield"] = "{}";
					json["save"] = false;
					jlform.setData({});
					jlform.initForm(json);
				}catch(e){}
			});
		}
			
	}
}



