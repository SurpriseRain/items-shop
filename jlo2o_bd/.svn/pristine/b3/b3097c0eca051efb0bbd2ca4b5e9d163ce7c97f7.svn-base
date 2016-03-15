var userLoginCookie = $.cookie("userLogin");
var userInfoCookie = $.cookie("userInfo");
var userInfo = JSON.parse(userInfoCookie);
var XMBH;
var GZLBH;
var BZBH;
var RZBH;
var CSCS;
//登录对象
var login = {};
$(document).ready(function(){
   PI_USERNAME=$.getUrlParam("PI_USERNAME"); 
   if(PI_USERNAME=="" || PI_USERNAME==null){
	   alert("无法获取用户名。");
	   return;
   } 
   var userInfoCookie = $.cookie(PI_USERNAME);
   if(userInfoCookie!=undefined && userInfoCookie!=""){
	   client_main.loadCZY();
	   //client_main.loadLeft();
	   client_main.loadHead();
	   freshIframes(); 
   }else{
	  login.setRYDM(PI_USERNAME);
	  login.getGW(PI_USERNAME); 
   }
	
});

function freshIframes(){
	if(window.location.search.substr(1)==""){
	   alert("缺参数：PI_USERNAME(必填) rno(必填)");
	   return;
    }
   var RNO=$.getUrlParam("rno");
   var BZBHCS=$.getUrlParam("BZBH");//步骤编号
   var RZBHCS=$.getUrlParam("RZBH");//日志编号
   if(BZBHCS==null || BZBHCS==""){
	   BZBHCS = 1;
   }
   if(RZBHCS==null || RZBHCS==""){
	   RZBHCS = 0;
   }
    XMBH=$.getUrlParam("XMBH");
    GZLBH=$.getUrlParam("GZLBH"); 
   if(RNO!=undefined && RNO!="" && (XMBH==undefined || XMBH=="" || GZLBH==undefined || GZLBH=="")){ 
	 var XmlData={"RNO":RNO};
	 $.ajax({
		 type: "POST",
	     url: pubJson.PcrmUrl+"/document/queryWorkflowByRno.do",
	     data: {sid:Math.random(),XmlData:JSON.stringify(XmlData)},
	     success: function(data){
	       if(data == ""){
	    	   alert("菜单未配置流程，或流程出错，请联系系统管理员。");
			   return;
	       }else{
	    	   var returnStr=JSON.parse(data).data;
			   if(returnStr!=undefined && returnStr!=""){
					XMBH=returnStr.XMBH;
					GZLBH=returnStr.GZLBH;
					CSCS=returnStr.CSCS;
					var url=pubJson.FormUrl+"/scm/client_todo.html?PI_USERNAME="+PI_USERNAME+"&RNO="+RNO+"&sid=" + Math.random();
   					$("#dbsyFrame").attr("src",url);
					loadWorkFlow(XMBH,GZLBH,BZBHCS,RZBHCS,CSCS);
			    }
	       }
	     }
	  });
	 
	 /*x1 = createCORSRequest('POST', encodeURI(url));
	 x1.onload = function(){
		 if(x1.responseText==""){
			 alert("菜单未配置流程，或流程出错，请联系系统管理员。");
			 return;
		 }
		 var returnStr=JSON.parse(x1.responseText);
			 if(returnStr!=undefined && returnStr!=""){
				returnStr=returnStr.data;
				XMBH=returnStr.XMBH;
				GZLBH=returnStr.GZLBH;
				var CSCS=returnStr.CSCS;
				url=pubJson.FormUrl+"/stepAction_dm.html?PI_USERNAME="+PI_USERNAME+"&XMBH="+XMBH+"&GZLBH="+GZLBH+"&BZBH="+BZBHCS+"&RZBH="+RZBHCS+"&CSCS="+CSCS+"&sid=" + Math.random();
			   	$("#main00").attr("src",url);
				url=pubJson.FormUrl+"/scm/client_todo.html?PI_USERNAME="+PI_USERNAME+"&RNO="+RNO+"&sid=" + Math.random();
	   	  		$("#dbsyFrame").attr("src",url);
			}
     };
     x1.send();*/
  }else{ 
	  url=pubJson.FormUrl+"/scm/client_todo.html?PI_USERNAME="+PI_USERNAME+"&RNO="+RNO+"&sid=" + Math.random();
   	  $("#dbsyFrame").attr("src",url);
	  loadWorkFlow(XMBH,GZLBH,BZBHCS,RZBHCS,CSCS);
  }
   
  if(XMBH!=undefined && XMBH!="undefined" && XMBH!="" && GZLBH!=undefined && GZLBH!="undefined" && GZLBH!="" 
	  && $.getUrlParam("RZBH")!=undefined && $.getUrlParam("RZBH")!="undefined" && $.getUrlParam("RZBH")!="" 
	  && $.getUrlParam("YWDJLX")!=undefined && $.getUrlParam("YWDJLX")!='undefined' && $.getUrlParam("YWDJLX")!=""
	  && $.getUrlParam("COMPAREBJ")=="1" ){
	  //document.getElementById("dbsyFrame").contentWindow.showCompareData(XMBH,GZLBH,0,$.getUrlParam("RZBH"),$.getUrlParam("YWDJLX"));
	  //window.frames["dbsyFrame"].window.showCompareData(XMBH,GZLBH,0,$.getUrlParam("RZBH"),$.getUrlParam("YWDJLX"));
	  //$("#dbsyFrame")[0].contentWindow.showCompareData(XMBH,GZLBH,0,$.getUrlParam("RZBH"),$.getUrlParam("YWDJLX"));
  }
}

//将人员代码 保存到Cookie中
login.setRYDM =function(rydm){
	var userData = {"RYDM":rydm};
    $.cookie("userLogin", null, {path:'/'});
	$.cookie("userLogin", JSON.stringify(userData), {expires:30,path:'/'});//expires:30天后cookies过期失效
}

//获取岗位权限
login.getGW = function(RYDM){
	if(RYDM!=null){
		$.ajax({
			async: false,
	        type: "POST",
	    	url: pubJson.PcrmUrl+"/streamDocument/getGw.do?PI_USERNAME="+RYDM,
	    	data: {sid:Math.random()},
	    	success: function(data){
	    		var userDataInfo=JSON.parse(data).data;
	    		if(data == "" || userDataInfo=="" || userDataInfo==undefined){
		    	  alert('登录失败！帐号或密码错误');
		    	  return;
		    	}else{
		    		login.saveCookies(userDataInfo);
		    	}
	    	},
	    	error:function(XMLHttpRequest, textStatus, errorThrown) {
		    	alert("登录失败,网络连接异常,请重试");
	        }
		});
	}
}

window.onscroll=function(){ 
	var oDiv=$(".contontList"); 
	if(document.body.scrollTop<99){
		$(".contontList").css({"top":"0","position":"static","z-index":"100"});
	}else if(document.body.scrollTop>100){
		$(".contontList").css({"top":"0","position":"fixed","z-index":"100"});
	}
}
var client_main={};

//首页加载操作员方法
client_main.loadCZY = function(){
	$(".userinfo").find("b").empty();
	var hour = new Date().getHours();
	if(hour<12){
		$(".userinfo").find("b").text("早上好,"+userInfo.RYMC); 
	}else if(hour>=13){
		$(".userinfo").find("b").text("下午好,"+userInfo.RYMC);
	}else{
		$(".userinfo").find("b").text("中午好,"+userInfo.RYMC);
	}
}

//首页加载左边菜单方法
client_main.loadLeft = function(){
	var queryField={"sid":Math.random(),"PI_USERNAME":userInfo.RYDM,"xmlData":"{}"};
	$.ajax({
		async: false,
		type: "POST",
		url: pubJson.PcrmUrl+"/workflowAction/workflowList.do",
		data: queryField,
		success: function(data){
			var resultData=JSON.parse(data).data;
			if(!(resultData=="" || resultData==undefined)){
				var resultList=resultData.resultList;
				for(var i=0;i<resultList.length;i++){
				   var GZLMC = resultList[i].GZLMC;
				   var GZLBH = resultList[i].GZLBH;
				   var LXDM = resultList[i].LXDM;
				   var LXMC = resultList[i].LXMC;
				   var XMBH = resultList[i].XMBH;
				   var XMMC = resultList[i].XMMC;
				   var BDBH = JSON.parse(resultList[i].JKDM).name;
				   
				   if($("#MENU_LXDM_"+LXDM).length>0){
					   $(".left_nav").find("#MENU_LXDM_"+LXDM).append("<li id='MENU_GZLBH_"+GZLBH+"' onclick=\"client_main.loadRight('"+GZLBH+"','"+GZLMC+"','"+XMBH+"',true,'"+BDBH+"')\"><i></i><a>"+GZLMC+"</a></li>");
				   }else{
					   $(".left_nav").append("<li><a>"+LXMC+"</a><span>+</span></li>"+
											 "<ul id='MENU_LXDM_"+LXDM+"' class='left_nav_last'>"+
											 	"<li id='MENU_GZLBH_"+GZLBH+"' onclick=\"client_main.loadRight('"+GZLBH+"','"+GZLMC+"','"+XMBH+"',true,'"+BDBH+"')\"><i></i><a>"+GZLMC+"</a></li>"+
											 "</ul>");
				   }
				}
			}
		}
	});
}

//首页加载右边页面方法
client_main.loadRight = function(GZLBH,GZLMC,XMBH,XWBH,REFRESH,BDBH){
	if($("#TAB_GZLBH_"+GZLBH).length>0){
		/**/$(".contontList > li").removeClass("xuan");
		$(".contontList").find("#TAB_GZLBH_"+GZLBH).addClass("xuan");
		$(".txt_main").hide();
		$(".contont").find("#IFRAME_GZLBH_"+GZLBH).show();
		if(REFRESH){
			var url=$(".contont").find("#IFRAME_GZLBH_"+GZLBH).attr("src");
			$(".contont").find("#IFRAME_GZLBH_"+GZLBH).attr("src",url);
		}
	}else{
		/**/$(".contontList > li").removeClass("xuan");
		if($(".contontList > li").length==10){
			$(".contontList > li").eq(1).remove();
			var first = $(".contontList > li").eq(1).attr("id").split("_")[2];
			$(".contont").find("#IFRAME_GZLBH_"+first).remove();
		}
		$(".contontList").append("<li id='TAB_GZLBH_"+GZLBH+"' class='xuan'><a title='"+GZLMC+"' onclick=\"client_main.loadRight('"+GZLBH+"','"+GZLMC+"','"+XMBH+"',false)\">"+GZLMC+"</a><span title='关闭选项卡' onclick='closeTab(\""+GZLBH+"\")'>×</span></li>");
		
		var pathUrl=client_main.getFormURL(BDBH);
		var client_main_url=pubJson.FormUrl+pathUrl+"?XMBH="+XMBH+"&XWBH="+XWBH+"&GZLBH="+GZLBH+"&BZBH="+1+"&RZBH="+0;
		
		$(".txt_main").hide();
		$(".contont").append("<iframe id='IFRAME_GZLBH_"+GZLBH+"' name='IFRAME_GZLBH_"+GZLBH+"' onload='resizeIFrame(this)'  src='"+client_main_url+"&PARENTID=IFRAME_GZLBH_"+GZLBH+"' class='txt_main' style='height: 100%; overflow: scroll;' width='100%' frameborder='0' scrolling='no'></iframe>");
	}
}

client_main.getFormURL = function(bdbh){
	var pathUrl="";
	$.ajax({
		async: false,
        type: "POST",
    	url: pubJson.FormUrl+"/form/getFormURL.do?bdbh="+bdbh,
    	data: {sid:Math.random()},
    	success: function(data){
    		var json = jQuery.parseJSON(data);
    		pathUrl=json.data.string=="form/getFormURL"?"":json.data.string;
    	}
	});
	return pathUrl;
}


var globalTinyBox = {};

globalTinyBox.open = function(tinyURL){
	TINY.box.show(tinyURL,0,0,0,0,0);
}

globalTinyBox.close = function(){
	TINY.box.hide();
}

var loadWorkFlow = function(XMBH,GZLBH,BZBH,RZBH,CSCS){
	var XmlData={"XMBH":XMBH,"GZLBH":GZLBH,"BZBH":BZBH,"RZBH":RZBH,"PI_USERNAME":userInfo.RYDM};
	var url=pubJson.PcrmUrl+"/document/viewWorkflowFirstStep.do?XmlData="+JSON.stringify(XmlData);
	
	$.ajax({
        type: "POST",
    	url: pubJson.PcrmUrl+"/document/viewWorkflowFirstStep.do",
    	data: {sid:Math.random(),XmlData:JSON.stringify(XmlData)},
    	success: function(data){
    	  appendStepAction(JSON.parse(data));
    	}
  	});
}

function appendStepAction(proxyData){
	
	var data=proxyData.data.listWorkflowStepAction;
	for(var i=0;i<data.length;i++){
		if(data[i].xwbh==1){
			if(data[0].jklx==5){
				loadForm(data[0]);
			}
		}
	}
}


function loadForm(data){
	var jkdm=JSON.parse(data.hdjkdm);
	var bdbh=data.bdbh;
	if(data.bdbh==null||data.bdbh=="null"||data.bdbh=="{}"){
		bdbh=jkdm.name;
	}
	client_main.loadRight(GZLBH,"",XMBH,data.xwbh,"true",bdbh);
}

//IFrame重新加载高度
var resizeIFrame = function(obj){
	var iframe= $(obj);
	var iframeName= $(obj).attr("name");
    var innerHTML = eval("document."+iframeName) ? eval("document."+iframeName).document : iframe.contentDocument;
    if(iframe != null && innerHTML != null) {
    	$(obj).height(innerHTML.body.scrollHeight);
    }
}

var closeTab = function(GZLBH){
	$(".contontList").find("#TAB_GZLBH_"+GZLBH).remove();
	$(".contont").find("#IFRAME_GZLBH_"+GZLBH).remove();
	if($(".contont").find(".txt_main").length>0){
		$(".contont").find(".txt_main").eq(0).show();
		var first = $(".contont").find(".txt_main").eq(0).attr("id").split("_")[2];
		$(".contontList").find("#TAB_GZLBH_"+first).show();
	}
}

client_main.loadHead = function(){
	var XmlData={"PI_USERNAME":userInfo.RYDM,"interval":5};
	$.ajax({
        type: "POST",
    	url: pubJson.PcrmUrl+"/document/queryNewLog.do",
    	data: {sid:Math.random(),XmlData:JSON.stringify(XmlData)},
    	success: function(data){
    		var resultData=JSON.parse(data).data;
    		var resultList=resultData.returnList;
    		if(resultList.length>0){
	    		$(".doList").empty();
	    		if(resultList.length>5){
	    			$(".doList").append("<li class='more' title='更多待办' onmou></li><ul class='more_doList'></ul>");
	    		}
	    		for(var i=0;i<resultList.length;i++){
	    			var RZSJ=resultList[i];
    				if(i>4){
    					$(".more_doList").append("<li id='HEAD_GZLBH_"+RZSJ.GZLBH+"' onclick=\"client_main.loadRight('01"+RZSJ.GZLBH+"','"+RZSJ.GZLMC+"待办','01"+RZSJ.XMBH+"',true)\"><a>"+RZSJ.GZLMC+"<label>"+RZSJ.NUM+"</label></a></li>");
    				}else{
    					$(".doList").append("<li id='HEAD_GZLBH_"+RZSJ.GZLBH+"' onclick=\"client_main.loadRight('01"+RZSJ.GZLBH+"','"+RZSJ.GZLMC+"待办','01"+RZSJ.XMBH+"',true)\"><a>"+RZSJ.GZLMC+"<label>"+RZSJ.NUM+"</label></a></li>");
    				}
	    		}
	    		
	    		$(".more").mouseover(function(){
	    			$(".more_doList").css({"display":"block"})
	    	    })
	    		$(".more_doList").mouseleave(function(){
	    			$(".more_doList").css({"display":"none"})
	    	    })
    		}
    	},
    	error:function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("网络连接异常,请重试");
        }
	});
}

var button = {};
button.submit = function(obj){
	var buttonID=$(obj).attr("id");
	var GZLBH=$(".contontList .xuan").attr("id").split("_")[2];
	var submitData = $("#IFRAME_GZLBH_"+GZLBH)[0].contentWindow.save(buttonID,[]);
	if(submitData != undefined){
		var url=submitData.url;
		var result=submitData.result;
		if(buttonID=="jlsavedraft"){
			if(result.jlbh!=0)
				url+="&draft=true";
			else if(result.jlbh!=0)
				alert("创建单据时不允许保存草稿");
			if(parent.$("#jlsave").prop("disabled")){
				alert("已保存并流转至下一步,不允许继续保存草稿");
				return;
			}
		}else if(buttonID=="jlsave"){
			parent.$("#jlsave").attr("disabled","disabled");
		}
		if(pubJson.nestedScm=="1"){
			$.ajax({
			    type: "POST",  //请求方式
				contentType: "application/x-www-form-urlencoded;charset=utf-8",//application/json
			    async: false,   //true表示异步 false表示同步
			    url:url,//"http://192.13.4.115:8080/v9_new/form/saveRecord.do",
				data:{json:JSON.stringify(result)},
				//dataType: "json",
		        //获取错误信息
		    	error:function(XMLHttpRequest, textStatus, errorThrown) {
			    	alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
					$("#jlsave").removeAttr("disabled");
		        },
				success: function(data){
					if(data.indexOf("Exception") != -1){
						data=data.replace(/java.lang.Exception: /gm,'');
						data=data.replace(/Exception: /gm,'');
						alert("保存失败："+data);
						$("#jlsave").removeAttr("disabled");
					}else if(data.indexOf("TODOLIST") != -1){
						var resultData=JSON.parse(data).data;
						var TODOLIST = resultData.TODOLIST;
						if(TODOLIST.length>0){
							if(confirm("保存成功[流水号:"+result.bdbh+"-"+resultData.jlbh+"],是否立即进入下一步?")){
								//window.location.href=pubJson.FormUrl+"/stepAction.html?XMBH="+TODOLIST[0].XMBH+"&GZLBH="+TODOLIST[0].GZLBH+"&BZBH="+TODOLIST[0].BZBH+"&RZBH="+TODOLIST[0].RZBH;
								var todoUrl=NestUrl+"?XMBH="+TODOLIST[0].XMBH+"&GZLBH="+TODOLIST[0].GZLBH+"&BZBH="+TODOLIST[0].BZBH+"&RZBH="+TODOLIST[0].RZBH;
								parent.window.location.href=todoUrl;
							}else{
								
							}
						}
					}else if(data.indexOf("dataType") != -1){
						var resultData=JSON.parse(data).data;
						if(JSON.stringify(resultData)=="{}"){
							alert("保存失败：未返回流水号");
							$("#jlsave").removeAttr("disabled");
						}else{
							alert("保存成功[流水号:"+result.bdbh+"-"+resultData.jlbh+"]");//
						}
					}else{
						alert("保存失败："+data);
						$("#jlsave").removeAttr("disabled");
					}
				}
			});
		}else if(pubJson.nestedScm=="0"){
		if(confirm("确认保存?")){
			$.ajax({
			    type: "POST",  //请求方式
				contentType: "application/x-www-form-urlencoded;charset=utf-8",//application/json
			    async: false,   //true表示异步 false表示同步
			    url:url,//"http://192.13.4.115:8080/v9_new/form/saveRecord.do",
				data:{json:JSON.stringify(result)},
				//dataType: "json",
		        //获取错误信息
		    	error:function(XMLHttpRequest, textStatus, errorThrown) {
			    	alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
					$("#jlsave").removeAttr("disabled");
		        },
				success: function(data){
					if(data.indexOf("Exception") != -1){
						data=data.replace(/java.lang.Exception: /gm,'');
						data=data.replace(/Exception: /gm,'');
						alert("保存失败："+data);
						$("#jlsave").removeAttr("disabled");
					}else if(data.indexOf("TODOLIST") != -1){
						var resultData=JSON.parse(data).data;
						var TODOLIST = resultData.TODOLIST;
						if(TODOLIST.length>0){
							if(confirm("保存成功[流水号:"+result.bdbh+"-"+resultData.jlbh+"],是否立即进入下一步?")){
								//window.location.href=pubJson.FormUrl+"/stepAction.html?XMBH="+TODOLIST[0].XMBH+"&GZLBH="+TODOLIST[0].GZLBH+"&BZBH="+TODOLIST[0].BZBH+"&RZBH="+TODOLIST[0].RZBH;
								var todoUrl=NestUrl+"?XMBH="+TODOLIST[0].XMBH+"&GZLBH="+TODOLIST[0].GZLBH+"&BZBH="+TODOLIST[0].BZBH+"&RZBH="+TODOLIST[0].RZBH;
								parent.window.location.href=todoUrl;
							}else{
								
							}
						}
					}else if(data.indexOf("dataType") != -1){
						var resultData=JSON.parse(data).data;
						if(JSON.stringify(resultData)=="{}"){
							alert("保存失败：未返回流水号");
							$("#jlsave").removeAttr("disabled");
						}else{
							alert("保存成功[流水号:"+result.bdbh+"-"+resultData.jlbh+"]");//
						}
					}else{
						alert("保存失败："+data);
						$("#jlsave").removeAttr("disabled");
					}
				}
			});
		}
	  }
	}
}

button.logout = function(){
	$.cookie("userLogin", null, {path:'/'});
	$.cookie("userInfo", null, {path:'/'});
	location.href=pubJson.FormUrl;
}

login.saveCookies = function(userDataInfo){
	var userData = {"RYBH":userDataInfo.RYBH,"RYDM":userDataInfo.RYDM,"RYMC":userDataInfo.RYMC,
			        "GWFZBH":userDataInfo.GWFZBH,"GWFZMC":userDataInfo.GWFZMC,
			        "GWBH":userDataInfo.GWFZBH,"GWMC":userDataInfo.GWFZMC,
			        "BMDM":userDataInfo.BMDM,"BMMC":userDataInfo.BMMC};

	var updateDate = new Date();
	updateDate.setTime(updateDate.getTime()+1*24*60*60*1000);//超过1天,cookie需要重新更新
	userData.UPDATETIME= updateDate.getTime();
	$.cookie(userDataInfo.RYDM, null, {path:'/'});
	$.cookie(userDataInfo.RYDM, JSON.stringify(userData), {expires:30,path:'/'});//expires:30天后cookies过期失效
	$.cookie("userInfo", null, {path:'/'});
	$.cookie("userInfo", JSON.stringify(userData), {expires:30,path:'/'});//expires:30天后cookies过期失效
	userInfo = JSON.parse($.cookie("userInfo"));
	client_main.loadCZY();
	//client_main.loadLeft();
	client_main.loadHead();
	freshIframes();
}