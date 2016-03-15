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
			$(".shop-left-nav").append("<li id='cd-"+menuArr[i].CODE+"'><h3><span>-</span><b>"+menuArr[i].NAME+""+
													  "</b></h3></li>");
		}else if(menuArr[i].LEVELS==2){
			var tid = menuArr[i].CODE.getpid();
			var url =menuArr[i].URL;
			url = ((menuArr[i].URL).indexOf("?")>-1)?(url+"&menuId="+menuArr[i].CODE):(url+"?menuId="+menuArr[i].CODE);
 			$("#cd-"+tid).append("<a id='"+menuArr[i].CODE+"' class='nav' href='"+url+"' target=\"centerFrame\" onclick=\"changeIframeSize()\">"+
 											  ""+menuArr[i].NAME+"</a>");
		}
	}
}

//输出特有菜单
menu.writeProperMenu = function(){
	var str = "<li><h3><span>-</span><b>账户管理</b></h3>" +
					"<a class=\"nav\" href=\"/customer/qfy/back/yhgl/shop_Basinfo.html\"  target=\"centerFrame\" onclick=\"changeIframeSize()\">账号管理</a>" +
					"</li>";
	$(".shop-left-nav").append(str);
}