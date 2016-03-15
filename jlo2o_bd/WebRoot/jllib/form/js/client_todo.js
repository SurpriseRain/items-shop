$(document).ready(function(){
   $.cookie('PCRM_COOKIE', '', { expires: -1 }); // 删除 cookie	
   var height = $(window).height();
   $("#body").css({"min-height":height});
   $("#body > .sqzk").css({"height":height});
   //$("#body > .dbmain").css({"height":height});
   $("#body").animate({"right":"0px"},300);
   if(window.location.search.substr(1)==""){
	   showMsg("缺参数：PI_USERNAME(必填) rno(选填)");
	   return;
   }
   var RNO=$.getUrlParam("rno");
   PI_USERNAME=$.getUrlParam("PI_USERNAME");
   if(PI_USERNAME=="" || PI_USERNAME==null){
	   showMsg("无法获取用户名。");
	   return;
   }
   //SCM的相关权限（如部门仓库权限）过滤待办
   var PCRM_SCM_QX=$.getUrlParam("PCRM_SCM_QX");
   if(PCRM_SCM_QX=="" || PCRM_SCM_QX==null){
	   PCRM_SCM_QX = "";
   }
   var userInfoCookie = $.cookie(PI_USERNAME);
   if(userInfoCookie == undefined || userInfoCookie == null || userInfoCookie == ""){
	   login(PI_USERNAME);
   }else{
	   userInfo = JSON.parse(userInfoCookie);
   }
   
   var url="";
   if(RNO!=undefined && RNO!=""){
	 url=pubJson.PcrmUrl+"/document/getXMBHByRNO.do?XmlData={\"RNO\":\""+RNO+"\"}";
	 var XmlData={"RNO":RNO};
	 $.ajax({
		 type: "POST",
	     url: pubJson.PcrmUrl+"/document/getXMBHByRNO.do",
	     data: {sid:Math.random(),XmlData:JSON.stringify(XmlData)},
	     success: function(data){
	       if(data == ""){
	    	 //showMsg("菜单未配置流程，或流程出错，请联系系统管理员。");
	   		 setCookieTodoZero();
	   		 return;
	       }else{
	    	   data = JSON.parse(data).data.resultList;
	    	   if(data==undefined || data==""){
	  			 setCookieTodoZero();
	  			 return;
	    	   }else{
	  	   	  	    url=pubJson.PcrmUrl+"/document/getDBSY.do?XmlData={'PI_USERNAME':'"+PI_USERNAME+"','XMBH':["+data+"],'PCRM_SCM_QX':'"+PCRM_SCM_QX+"'}";
	  	   	  		buildDbsy(url);
	  			    window.setInterval(function(){
	  	   	  		buildDbsy(url);
	  			        },300000);
	    	   }
	       }
	     }
	 });  
	 
	 
	 
     /*x1 = createCORSRequest('POST', encodeURI(url));
	 x1.onload = function(){
		if(x1.responseText==""){
			//showMsg("菜单未配置流程，或流程出错，请联系系统管理员。");
			setCookieTodoZero();
			return;
		} 
		var returnStr=JSON.parse(x1.responseText);   
		returnStr=returnStr.data.resultList;
		if(returnStr==undefined || returnStr==""){
			 setCookieTodoZero();
			 return;
		}else{
	   	  	url=pubJson.PcrmUrl+"/document/getDBSY.do?XmlData={'PI_USERNAME':'"+PI_USERNAME+"','XMBH':["+returnStr+"],'PCRM_SCM_QX':'"+PCRM_SCM_QX+"'}";
	   	  		buildDbsy(url);
			    window.setInterval(function(){
	   	  		buildDbsy(url);
			        },300000);
		}
	 }
	 x1.send();*/
   }else{ 
		 url=pubJson.PcrmUrl+"/document/getDBSY.do?XmlData={'PI_USERNAME':'"+PI_USERNAME+"','PCRM_SCM_QX':'"+PCRM_SCM_QX+"'}";
	            buildDbsy(url); 
			    window.setInterval(function(){
			   	buildDbsy(url);
			        },300000);
   }
   $("h3 > b").click(function(){
	 $("#body").animate({"right":"-300px"},300);  
	 });  
   
   $("#jt-title").mouseover(function(){
	  if($("#show").length>0){
    	$("#show").remove();  
      }
	  $("h3 > a").removeClass("xuan");
	  $(this).addClass("xuan"); 
	  $(".dbmain").css({"display":"none"});
	  $("#jt-main").css({"display":"block"});
	 });
   $("#gz-title").mouseover(function(){
	  if($("#show").length>0){
    	$("#show").remove();  
      }
	  $("h3 > a").removeClass("xuan");
	  $(this).addClass("xuan"); 
	  $(".dbmain").css({"display":"none"});
	  $("#gz-main").css({"display":"block"});
	 });
	 showScroll();
});

//生成首页右边的待办分类（时间：今天、更早），含条类条数 
function buildDbsy(url){
	$("#jt-main").empty();
	$("#gz-main").empty();
	$.ajax({
        type: "POST",
    	url: encodeURI(url),
    	data: {sid:Math.random()},
    	success: function(data){
    	  if(data==""){
    		  setCookieTodoZero();
    		  return;
    	  }else{
    		  var returnStr=JSON.parse(data);
    		   if(returnStr !=undefined && returnStr !=""){
    			 data=returnStr.data.resultList;
    			 if(data==undefined || data==""){
    				 setCookieTodoZero();
    				 return;
    			 }
    			var today=[],ago=[];
    			//按今日、更早分类待办
    			var date = new Date();
    			var y = date.getFullYear(), m = date.getMonth()+1, d = date.getDate();
    			var todayStr= y+ '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d));
    			for(var k=0;k<data.length;k++){
    				var TJRQ=(data[k].TJRQ).substring(0,(data[k].TJRQ).indexOf(" "));
    				if(todayStr==TJRQ){
    					today.push(data[k]);
    				}else{
    					ago.push(data[k]);
    				}
    			}
    			$("#jt-title").text("今天（"+today.length+"）");
    			$("#gz-title").text("更早（"+ago.length+"）");
    			spellDbsy(today,"jt-main");
    			spellDbsy(ago,"gz-main");
    			
    			$(".dbtitle").click(function(){
    			   if($("#show").length>0){
    		    	 $("#show").remove();  
    		       }
    			   if($(this).children(".dbzcd").css("display")=="none"){
    				   $(".dbtitle").children(".dbzcd").css({"display":"none"});
    				   $(this).children(".dbzcd").css({"display":"block"});
    			   }else{
    				   $(".dbtitle").children(".dbzcd").css({"display":"none"});
    			   }
    			   showScroll(); 
    			});
    			
    			//今天能待办，更早有，直接显示更早待办
    			if(today.length==0 && ago.length>0){
    				if($("#show").length>0){
    			    	$("#show").remove();  
    			      }
    				  $("h3 > a").removeClass("xuan");
    				  $("#gz-title").addClass("xuan"); 
    				  $(".dbmain").css({"display":"none"});
    				  $("#gz-main").css({"display":"block"});
    			}
    	    }else{
    			setCookieTodoZero();
    		    return;
    	    }
    	  }
    	}
  	});
	
	
	/*x2 = createCORSRequest('POST', encodeURI(url));
   	x2.onload = function(){
   	   if(x2.responseText==""){
   			 setCookieTodoZero();
   			 return;
   	   }
	   var returnStr=JSON.parse(x2.responseText);
	   if(returnStr !=undefined && returnStr !=""){
		 var data=returnStr.data.resultList;
		 if(data==undefined || data==""){
			 setCookieTodoZero();
			 return;
		 }
		var today=[],ago=[];
		//按今日、更早分类待办
		var date = new Date();
		var y = date.getFullYear(), m = date.getMonth()+1, d = date.getDate();
		var todayStr= y+ '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d));
		for(var k=0;k<data.length;k++){
			var TJRQ=(data[k].TJRQ).substring(0,(data[k].TJRQ).indexOf(" "));
			if(todayStr==TJRQ){
				today.push(data[k]);
			}else{
				ago.push(data[k]);
			}
		}
		$("#jt-title").text("今天（"+today.length+"）");
		$("#gz-title").text("更早（"+ago.length+"）");
		spellDbsy(today,"jt-main");
		spellDbsy(ago,"gz-main");
		
		$(".dbtitle").click(function(){
		   if($("#show").length>0){
	    	 $("#show").remove();  
	       }
		   if($(this).children(".dbzcd").css("display")=="none"){
			   $(".dbtitle").children(".dbzcd").css({"display":"none"});
			   $(this).children(".dbzcd").css({"display":"block"});
		   }else{
			   $(".dbtitle").children(".dbzcd").css({"display":"none"});
		   }
		   showScroll(); 
		});
		
		//今天能待办，更早有，直接显示更早待办
		if(today.length==0 && ago.length>0){
			if($("#show").length>0){
		    	$("#show").remove();  
		      }
			  $("h3 > a").removeClass("xuan");
			  $("#gz-title").addClass("xuan"); 
			  $(".dbmain").css({"display":"none"});
			  $("#gz-main").css({"display":"block"});
		}
    }else{
		setCookieTodoZero();
	    return;
    }
  };
  x2.send();*/
}

//今天、更早待办数组拼接html
function spellDbsy(data,lx){
	var arr=[];var m=0;
	for(var i=0;i<data.length;i++){
		var GZLMC=data[i].GZLMC;
	    if(!arr.contains(GZLMC)){
			var n=0;
			var tempMid="";
			//获取同工作流待办条数
			for(var j=i;j<data.length;j++){
				if(GZLMC==data[j].GZLMC){
				   //拼接显示关键字段html
				   tempMid=tempMid+"<li id='item"+lx+j+"' class='item'><span class='BZMC'>"+GZLMC+'-'+data[j].BZMC+"</span>"+
				   "<input type='hidden' id='LOGDATA"+lx+j+"' value='"+JSON.stringify(data[j])+"'></li>";
				n++;	
				}
			}
			$("#"+lx).append("<li class='dbtitle'><h4>"+GZLMC+"（"+n+"）条</h4><ul class='dbzcd'>"+tempMid
				+"</ul></li>");//<div class='clear'></div>
			arr.push(GZLMC);
	  }
  }
						  
  for(var j=0;j<data.length;j++){
	 if($("#item"+lx+j).length>0){
		//点击明细事件，按scm要求格式传值
		$("#item"+lx+j).mouseover(function(){
			var id=$(this).attr("id").substring(4);
			if($("li[name='stepAction"+id+"']").length==0 && $("li[name='TODO_PROFILE"+id+"']").length==0){
				spellTodoItem(id);
			}
			$(".TODO_PROFILE").css({"display":"none"});
			$(".stepAction").css({"display":"none"});
			$("li[name='TODO_PROFILE"+id+"']").css({"display":"block"});
			$("li[name='stepAction"+id+"']").css({"display":"block"});
			showScroll();
		});
		$("#item"+lx+j).click(function(){
			showScroll();
			if($("#show").length>0){
	    		$("#show").remove();  
	    	}
			$(this).children().css({"display":"block"});
			var id=$(this).attr("id").substring(4);
			$("li[name='TODO_PROFILE"+id+"']").css({"display":"block"});
			$("li[name='stepAction"+id+"']").css({"display":"block"});
			var LOGDATA=jQuery.parseJSON($("#LOGDATA"+id).val());  
			var ywdj=LOGDATA.YWSJ;
			var ywdjlx="";
			var jklx="";
			
			if(LOGDATA.LOGSTEP.length == 1){
				var LOGSTEP = LOGDATA.LOGSTEP[0];
				var GZLBH=LOGDATA.GZLBH;
				var jkdm = LOGSTEP.JKDM;
				var BZBH=LOGSTEP.BZBH;
				var XMBH=LOGSTEP.XMBH;
				var RZBH=LOGSTEP.RZBH;
				var XWBH=LOGDATA.XWBH;
				var COMPAREBJ=LOGDATA.COMPAREBJ;
				ywdjlx=LOGSTEP.YWDJLX;
				jklx=LOGSTEP.JKLX;
				var jlbh = 0;
				
				if(jkdm != undefined && jklx == 4){
					//穿透SCM接口，则传cookie
					jlbh=(jQuery.parseJSON(ywdj))[ywdjlx].DJBH;
					var jsonVal={"SENDER":"PCRM.TODO","MSGID":"ToDoListItemClick"};
					jkdm = JSON.parse(jkdm);
					if(jkdm.ACTION != undefined && jkdm.ACTION!="A"){
						jkdm.ACTION = "";
					}
					jsonVal = jQuery.extend({} ,jsonVal, jkdm);
					jsonVal = jQuery.extend({} ,jsonVal, {"BILLNO":jlbh});
					setCookie(jsonVal);
				}else if(parent.window.$("#main00").length>0 && jklx==5){
					//非穿透接口则刷新主页面操作表单
					url=pubJson.PcrmUrl+'/document/getListLogger.do?XmlData={"LX":"FORM","XMBH":'+XMBH+',"GZLBH":'+GZLBH+',"RZBH":'+RZBH+'}';
					parent.setFlow(url);
					
					url=pubJson.FormUrl+"/stepAction_dm.html?PI_USERNAME="+PI_USERNAME+"&XMBH="+XMBH+"&GZLBH="+GZLBH+"&BZBH="+BZBH+"&RZBH="+RZBH+"&XWBH="+XWBH+"&CSCS="+LOGSTEP.CSCS+"&sid=" + Math.random();
					parent.window.$("#main00").attr("src",url);
					parent.window.$("#flowSave").removeAttr("disabled");
					parent.window.$("#main00")[0].contentWindow.IFrameReSizeWidth();
					if(COMPAREBJ == 1){
						showCompareData(XMBH,GZLBH,0,RZBH,ywdjlx);
					}
					
				}else if(parent.window.$("#main00").length==0 && jklx==5){//指定穿透环境
				    var bdJkdm = JSON.parse(jkdm);
					url=pubJson.FormUrl+"/scm/client_main.html?rno="+bdJkdm.RNO+"&PI_USERNAME="+$.getUrlParam("PI_USERNAME")+"&BZBH="+BZBH+"&RZBH="+RZBH+"&XMBH="+XMBH+"&GZLBH="+GZLBH+"&COMPAREBJ="+COMPAREBJ+"&YWDJLX="+ywdjlx+"&sid=" + Math.random();
					var jsonVal={"SENDER":"PCRM.TODO","FORMTYPE":"Web","MSGID":"ToDoListItemClick","RSYS":"DPCRM","RFORM":"TFrmCustomForm"};
					//必填参数，为要指向的url的菜单参数（需改在oracle配置）
					if(bdJkdm.ACTION != undefined && bdJkdm.ACTION != "A"){
						bdJkdm.ACTION = "";
					}
					jsonVal = jQuery.extend({} ,jsonVal, bdJkdm);
					jsonVal = jQuery.extend({} ,jsonVal, {"URL":url});
					setCookie(jsonVal);
				}
			}else{
				//如果是供应链流程，则依赖供应链的按钮来匹配对应的行为
				var LOGSTEP = LOGDATA.LOGSTEP[0];
				var jkdm = JSON.parse(LOGSTEP.JKDM);
				ywdjlx=LOGSTEP.YWDJLX;
				jklx=LOGSTEP.JKLX;
				if(jkdm != undefined && jklx == 4){
					//穿透SCM接口，则传cookie
					jlbh=(jQuery.parseJSON(ywdj))[ywdjlx].DJBH;
					var jsonVal={"SENDER":"PCRM.TODO","MSGID":"ToDoListItemClick"};
					if(jkdm.ACTION != undefined && jkdm.ACTION != "A"){
						jkdm.ACTION = "";
					}
					jsonVal = jQuery.extend({} ,jsonVal, jkdm);
					jsonVal = jQuery.extend({} ,jsonVal, {"BILLNO":jlbh});
					setCookie(jsonVal);
				}
			}
		})
	 }
  }		
}
//生成明细数据字段摘要及行为（大于1）
function spellTodoItem(id){
   var tempMid='';
   var tempProfile='';
   var LOGDATA=jQuery.parseJSON($("#LOGDATA"+id).val());
   var YWSJ=LOGDATA.YWSJ;
   var YWDJLX=LOGDATA.YWDJLX;
   var YWSJJson = jQuery.parseJSON(YWSJ);
   var YWDJLX=LOGDATA[key];
   for(var key in YWSJJson){
     YWDJLX = key;
	 break;
   }
   //拼接可选行为html  
   if(LOGDATA.LOGSTEP.length == 0 && (YWSJ==undefined || YWSJ=="" || YWDJLX==undefined || YWDJLX=="")){
	   tempMid = tempMid+"<li name='stepAction"+id+"'><span>该单据存在错误</span></li>";
   }else{
	   YWSJ=jQuery.parseJSON(LOGDATA.YWSJ);
	   var ZY=(YWSJ)[YWDJLX];
   	   var TODO_PROFILE=LOGDATA.TODO_PROFILE;
       //拼接摘要字段
	   if(ZY!=undefined && ZY!="" && TODO_PROFILE!=undefined && TODO_PROFILE!=""){
			TODO_PROFILE=jQuery.parseJSON(TODO_PROFILE);
			$.each(TODO_PROFILE,function(m){
	            $.each(ZY,function(n){
	                if(m==n && ZY[n] !=''){
						tempProfile=tempProfile+"<li style='display:none;color:blue;' class='TODO_PROFILE' name='TODO_PROFILE"+id+"'><span>&nbsp;*&nbsp;"+
									TODO_PROFILE[m]+":"+ZY[n]+"</span></li>";
					}
	        	});
	        });
	   }
					   
	   if(tempProfile !='' && tempProfile !=undefined){
	       tempMid=tempMid+tempProfile;
	   }
   }
   if(LOGDATA.LOGSTEP.length >= 1){
	   m=0;
	   tempMid = tempMid+"<li style='cursor:hand;display:none;' class='stepAction' name='stepAction"+id+"'><span style='float:left;'>&nbsp;»&nbsp;</span>";
	   for(var p=0;p<LOGDATA.LOGSTEP.length;p++){
		   tempMid=tempMid+"<button style='padding:1px 0.5px 1px 0.5px;margin:0px 1px 0px 1px;width:auto;height:32px;' class='action' onclick='showStepAction("+JSON.stringify(LOGDATA.LOGSTEP[p])+","+JSON.stringify(LOGDATA)+")'>"+LOGDATA.LOGSTEP[p].XWMC+"</button>";
		   /**if(LOGDATA.LOGSTEP[p].JKLX == 5){
			   tempMid=tempMid+"<button style='padding:1px 0.5px 1px 0.5px;margin:0px 1px 0px 1px;width:auto;height:32px;' class='action' onclick='showStepAction("+JSON.stringify(LOGDATA.LOGSTEP[p])+","+JSON.stringify(LOGDATA)+")'>"+LOGDATA.LOGSTEP[p].XWMC+"</button>";
		   }else{
			   if(LOGDATA.LOGSTEP[p].JKLX == 0 || LOGDATA.LOGSTEP[p].JKDM == undefined || LOGDATA.LOGSTEP[p].JKDM.length == 0){
				   tempMid=tempMid+"<button style='padding:1px 0.5px 1px 0.5px;margin:0px 1px 0px 1px;width:auto;height:32px;' class='action' onclick='showStepAction("+JSON.stringify(LOGDATA.LOGSTEP[p])+","+JSON.stringify(LOGDATA)+")'>"+LOGDATA.LOGSTEP[p].XWMC+"</button>";
			   }else{
				   if(m == 0){
					   tempMid=tempMid+"<input type='hidden' id='JKDM"+id+"' value='"+LOGDATA.LOGSTEP[p].JKDM+"'>";
				   }
				   m++;
			   }
		   }*/
	   }
	  tempMid = tempMid+ "</li>";
   }
   $("#item"+id).after(tempMid);
   //一个行为，默认，不需要显示
   if($("li[name='stepAction"+id+"']").children("button").length==0 && $("li[name='stepAction"+id+"']").length==1){
	   $("li[name='stepAction"+id+"']").remove();
   }
}

function setCookie(jsonVal){
	//当客户端独立引用时，写cookie
	$.cookie('PCRM_TODO_MSG', '', { expires: -1 }); // 删除 cookie
	$.cookie('PCRM_TODO_MSG',JSON.stringify(jsonVal));
    top.document.title = "{\"PCRM_COOKIE\":\"PCRM_TODO_MSG\"}";
}

function setCookieTodoZero(){
	var jsonVal = {"SENDER":"PCRM.TODO" ,"MSGID":"ToDoListLoad","ToDoListCount":"0"};
	setCookie(jsonVal);
    showMsg("我的待办都处理了。");
}

//数组是否含元素
Array.prototype.contains = function(obj) {
	var i = this.length - 1;
	while (i >= 0) {
		if (this[i] === obj) {
			return true;
		}
		i--;
	}
	return false;
}

function showMsg(msg){
	if($("#show").length>0){
    	$("#show").remove();  
    }
    $(".dbmain").append("<div id='show'><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>"+msg+"</div>");
    $(".dbmain").css({"color":"#00f","background":"none","float":"center","font-size":"15px","text-align":"center","vertical-align":"center"});
}

function showScroll(){
    var height=$("#jt-main").height()> $("#gz-main").height() ?$("#jt-main").height():$("#gz-main").height() ;
    //var height=$(obj).height() < $(window).height()-190 ?$(window).height()-190 :$(obj).height() ;
    var height2=$(window).height()-$("#dbsy-title").height();
    if(height>height2){
    	$("#body > .dbmain").css({"height":height});
    	$("#body").css({"height":height});
    	$("#body").css({"overflow-y":"scroll"});
    }else{
    	$("#body").css({"overflow-y":"hidden"});
    }
}

function showStepAction(val,logdata){
	val = JSON.parse(JSON.stringify(val));
	logdata = JSON.parse(JSON.stringify(logdata));
	var ywsjlx = val.YWDJLX;
	var ywsj = logdata.YWSJ;
	var msgid = logdata.MSGID;
    var compareBj = logdata.COMPAREBJ;
	if(val.JKLX == 5 && val.JKDM != undefined && val.JKDM != ""){
		var url="";
		if(parent.window.$("#main00").length>0){
			url=pubJson.PcrmUrl+'/document/getListLogger.do?XmlData={"LX":"FORM","XMBH":'+val.XMBH+',"GZLBH":'+val.GZLBH+',"RZBH":'+val.RZBH+'}';
			parent.setFlow(url);
			url=pubJson.FormUrl+"/stepAction_dm.html?PI_USERNAME="+PI_USERNAME+"&XMBH="+val.XMBH+"&GZLBH="+val.GZLBH+"&BZBH="+val.BZBH+"&RZBH="+val.RZBH+"&XWBH="+val.XWBH+"&CSCS="+val.CSCS+"&sid=" + Math.random();
			
			parent.window.$("#main00").attr("src",url);
			parent.window.$("#flowSave").removeAttr("disabled");
			if(compareBj == 1){
		      showCompareData(val.XMBH,val.GZLBH,val.YWDH,val.RZBH,ywsjlx);
		    }
		}else if(parent.window.$("#main00").length==0){//指定穿透环境
			var bdJkdm = JSON.parse(val.JKDM);
			url=pubJson.FormUrl+"/scm/client_main.html?rno="+bdJkdm.RNO+"&PI_USERNAME="+$.getUrlParam("PI_USERNAME")+"&BZBH="+val.BZBH+"&RZBH="+val.RZBH+"&COMPAREBJ="+compareBj+"&YWDJLX="+ywsjlx+"&sid=" + Math.random();
			var jsonVal={"SENDER":"PCRM.TODO","FORMTYPE":"Web","MSGID":"ToDoListItemClick","RSYS":"DPCRM","RFORM":"TFrmCustomForm"};
			//必填参数，为要指向的url的菜单参数（需改在oracle配置）
			if(bdJkdm.ACTION != undefined && bdJkdm.ACTION != "A"){
				bdJkdm.ACTION = "";
			}
			jsonVal = jQuery.extend({} ,jsonVal, bdJkdm);
			jsonVal = jQuery.extend({} ,jsonVal, {"URL":url});
			setCookie(jsonVal);
		}
	}else if(val.JKLX == 4 && val.JKDM != undefined && val.JKDM != ""){
		//如果是供应链流程，则依赖供应链的按钮来匹配对应的行为
		var jkdm = jQuery.parseJSON(val.JKDM);
		//穿透SCM接口，则传cookie
		jlbh=(jQuery.parseJSON(ywsj))[ywsjlx].DJBH;
		var jsonVal={"SENDER":"PCRM.TODO","MSGID":"ToDoListItemClick"};
		if(jkdm.ACTION != undefined && jkdm.ACTION != "A"){
			jkdm.ACTION = "";
		}
		jsonVal = jQuery.extend({} ,jsonVal, jkdm);
		jsonVal = jQuery.extend({} ,jsonVal, {"BILLNO":jlbh});
		setCookie(jsonVal);
	}else if(val.JKLX == 0 && (val.JKDM == undefined || val.JKDM == "")){
		var tempData = {"xmbh":val.XMBH,"gzlbh":val.GZLBH,"ywdh":val.YWDH,"bzbh":val.BZBH,"xwbh":val.XWBH,"PI_USERNAME":userInfo.RYDM};
    	url=pubJson.PcrmUrl+"/document/interfaceTsransiteDocument.do?strData="+JSON.stringify(tempData);
    	
    	$.ajax({
            type: "POST",
        	url: pubJson.PcrmUrl+"/document/interfaceTsransiteDocument.do",
        	data: {sid:Math.random(),strData:JSON.stringify(tempData)},
        	success: function(data){
        	  if(data!=undefined && data!=""){
        		  showInnerTrans(data);
        	  }else{
        		  alert('步骤请求失败！');
        		  return;
        	  }
        	}
      	});
    	
    	//visitService(url);
    	/*xhr3 = createCORSRequest('POST', url);
    	//处理服务器端响应
    	xhr3.onload = function(){
        	showInnerTrans(xhr3.responseText);
    	};
    	xhr3.onerror = function() {
        	alert('步骤请求失败！');
    	};
    	xhr3.send();*/
	}
}

function showInnerTrans(data){
	alert("该功能尚未开通，敬请期待");
}

function login(userId){
	//获取岗位权限
	$.ajax({
        type: "POST",
    	url: pubJson.PcrmUrl+"/streamDocument/getGw.do",
    	data: {sid:Math.random(),PI_USERNAME:userId},
    	success: function(data){
    		var gw=JSON.parse(data);
    		if(data == "" || gw=="" || gw==undefined){
	    	  alert('岗位请求失败！');
	    	  return;
	    	}
    		$.ajax({
    	        type: "POST",
    	    	url: pubJson.ScmUrl+"/jlquery/selectSCM.do?XmlData={\"sqlid\":\"Login.Login_GetUrInfo\",\"RYXX01\":\""+userId+"\",\"dataType\":\"Json\"}",
    	    	data: {sid:Math.random()},
    	    	success: function(databm){
    	    		var bm=JSON.parse(databm);
    	    		if(databm == "" || bm=="" || bm==undefined){
    	  	    	  alert('部门请求失败！');
    	  	    	  return;
    	  	    	}
    	    		saveCookies(gw,bm,userId);
    	    	}
    		});
    	}
	});
	/*var url3=pubJson.PcrmUrl+"/streamDocument/getGw.do?PI_USERNAME="+userId;
	x3 = createCORSRequest('POST', url3);
	x3.onload = function(){
		var gw=JSON.parse(x3.responseText);
		var url4=pubJson.ScmUrl+"/jlquery/selectSCM.do?XmlData={\"sqlid\":\"Login.Login_GetUrInfo\",\"RYXX01\":\""+userId+"\",\"dataType\":\"Json\"}";
		x33 = createCORSRequest('POST', url4);
		x33.onload = function(){
			var bm=JSON.parse(x33.responseText);
			saveCookies(gw,bm,userId);
	    };
	    x33.onerror = function() {
	        alert('部门请求失败！');
	    };
	    x33.send();
    };
    x3.onerror = function() {
        alert('岗位请求失败！');
    };
    x3.send();*/
}

function saveCookies(gw,bm,userId){
	var gwData = gw.data;
	var bmJson = bm.data;
	var bmData = {"BM01":bmJson[0].BM01,"BM02":bmJson[0].BM02};
	
	var qxInfo=jQuery.extend({} ,gwData, bmData);
	var updateDate = new Date();
	updateDate.setTime(updateDate.getTime()+1*24*60*60*1000);//超过1天,cookie需要重新更新
	qxInfo.UPDATETIME= updateDate.getTime();
	userInfo = qxInfo;
	$.cookie(userId, null, {path:'/'});
	$.cookie(userId, JSON.stringify(qxInfo), {expires:30,path:'/'});//expires:30天后cookies过期失效
}

function showCompareData(xmbh,gzlbh,ywdh,rzdh,ywdjlx){
	if(xmbh != undefined && xmbh != 0 && gzlbh != undefined && gzlbh != 0){
	  var jsonData = {"XMBH":xmbh,"GZLBH":gzlbh};
	  if(ywdh != undefined && ywdh != 0){
		  jsonData = jQuery.extend({} ,jsonData,{"YWDH":ywdh});
	  }
	  if(rzdh != undefined && rzdh != 0){
		  jsonData = jQuery.extend({} ,jsonData,{"RZBH":rzdh});
	  }
	  var url =pubJson.PcrmUrl+"/interfaceAction/queryCompareLog.do?jsonData="+JSON.stringify(jsonData);
	  $.ajax({
		  type: "POST",
	      url: encodeURI(url),
	      data: {sid:Math.random()},
	      success: function(data){
	    	  var json=JSON.parse(data);
	    	  var returnValue = json.data.logList;
	  	      var jsonData = new Array();
	  	      for(var m = 0;m<returnValue.length;m++){
	  	    	jsonData[m] = {"log":{"xmbh":returnValue[m].XMBH,"gzlbh":returnValue[m].GZLBH,"ywdh":returnValue[m].YWDH,"rzbh":returnValue[m].RZBH}};
	  	      }
	  	      loadCompareRZSJ(JSON.stringify(jsonData),ywdjlx);
	      },
	      error: function(XMLHttpRequest, textStatus, errorThrown) {
		       alert("对比失败");
		  }
	  });
	  
	  
	  /*x0 = createCORSRequest('POST', url);
	  x0.onload = function(){
	    var json=JSON.parse(x0.responseText);
	    var returnValue = json.data.logList;
	    var jsonData = new Array();
	    for(var m = 0;m<returnValue.length;m++){
	    	jsonData[m] = {"log":{"xmbh":returnValue[m].XMBH,"gzlbh":returnValue[m].GZLBH,"ywdh":returnValue[m].YWDH,"rzbh":returnValue[m].RZBH}};
	    }
	    loadCompareRZSJ(JSON.stringify(jsonData),ywdjlx);
      };
      x0.onerror = function() {
        alert('对比失败！');
      };
      x0.send();*/
	}
}

function loadCompareRZSJ(jsonData,bdbh){
  if(bdbh == undefined ||  bdbh=="" || !bdbh.isNumeric()){
   	compareRZSJ(jsonData);
  }else{
    var url = pubJson.FormUrl+"/form/findField.do?bdbh="+bdbh+"&s="+Math.random();
    $.ajax(
    {
      type: "POST",  //请求方式
      url: url,
	  data: {"json":jsonData},
      success: function(data){
	      var data = jQuery.parseJSON(data);
	      var fieldList = data.data.basicDBObjectList;
	  	  var fieldObj={};
	      var fieldArr=[];
	      var fieldMap={};  //存放所有表单字段值
	      for(var i=0;i<fieldList.length;i++){
	   	    fieldObj=fieldList[i];
	  	    if(fieldObj.zdmc != null && fieldObj.zdmc != ""){
	          fieldMap[fieldObj.zdid]=fieldObj.zdmc;
	        }
	      }
	      //对jsonData值进行处理
	      var jsonList = jQuery.parseJSON(jsonData);
	      var jsonObj = jsonList[0];
	      jsonObj["bdzdmc"]=fieldMap;
	      compareRZSJ(JSON.stringify(jsonList),bdbh);
        }
    });
  }
}

//加载比较内容
function compareRZSJ(jsonData,bdbh){
  	var url =pubJson.PcrmUrl+"/interfaceAction/compareRZSJ.do?json="+jsonData;
  	
  	$.ajax({
  		type: "POST",  //请求方式
  		url: pubJson.PcrmUrl+"/interfaceAction/compareRZSJ.do",
  		data: {"json":jsonData},
  		success: function(data){
  		  var json=JSON.parse(data);
  		  var returnValue = json.data;
  		  showDiffData(returnValue.compareData,bdbh);
  		}
  	});
  	
  	/*x0 = createCORSRequest('POST', encodeURI(url));
	x0.onload = function(){
	  var json=JSON.parse(x0.responseText);
	  var returnValue = json.data;
	  showDiffData(returnValue.compareData,bdbh);
    };
    x0.onerror = function() {
        alert('对比失败！');
    };
    x0.send();*/
}

String.prototype.endWith=function(str){     
  var reg=new RegExp(str+"$");     
  return reg.test(this);        
}; 

function showDiffData(jsonData,bdbh){
	var str = "",resultStr="";
	var valList=[];
	var num=0;
	for(var i=0;i<jsonData.length;i++){
	   var fieldObj=jsonData[i];
	   jQuery.each(fieldObj, function(key, val){
		  if(key != bdbh && key != "bdbh" && key != "jlbh"){
			str = "<tr><td align='left' width='18%'>" + key+"：</td><td>";
	        var flag=false;
	        var tdVal = "";
		    for(var j=0;j<val.length;j++){
			  if(j == 0){
				 tdVal=val[j];
			  }else{
				  if(tdVal != val[j]){
					  flag=true;
					  str = str + "由【"+tdVal+"】变成【"+val[j]+"】.&nbsp;&nbsp;";
					  tdVal = val[j];
				  }
			  }
		    }
		    if(flag){
		      str = str + "</td></tr>";
			  resultStr = resultStr + str;
		    }
		    str = "";
		  }
	   });
	}
	parent.window.$("#compareBtn").css({"disabled":"disabled"});
	parent.window.$("#showCompareData_div_cont").html("");
	if(resultStr != ""){
		parent.window.$("#compareBtn").removeAttr("disabled");
		resultStr = "<table>" + resultStr + "</table>";
		parent.window.$("#showCompareData_div_cont").html(resultStr);
	    parent.window.$("#compareBtn").click();
	}
}