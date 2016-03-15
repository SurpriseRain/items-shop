var saveArray = [];
var XMBH;
var YWDH;
var RZBH; 
var JPBJ; 
var XMMC;
var BZBH;
var BZMC;
var XWBH;
var XWMC;
var RZBH;
var userInfoCookie = $.cookie("userInfo");
var userInfo;


window.onload = function(){
if(userInfoCookie == null || userInfoCookie == "{}"){
	  if(RYDM==null){
		  window.location.href=pubJson.FormUrl+"/login.html";
	  }
   }else{
	  userInfo = JSON.parse(userInfoCookie);
   } 

   var XmlData = [];
   var json = {}; 
   json["sqlid"]="com.jlsoft.pcrm.dataInfo.sql.DefineInfo.viewOperatorByBm-DefineInfo";
   json["QryType"] = "Report";
   XmlData.push(json);  

   XMBH = $.getUrlParam("XMBH");
   YWDH = $.getUrlParam("YWDH");
   RZBH = $.getUrlParam("RZBH"); 
   if ($.getUrlParam("JPBJ") == 'undefined')
   {
	 JPBJ = 0;
   }else {
     JPBJ = $.getUrlParam("JPBJ");
   }  
   XMMC = $.getUrlParam("XMMC");
   BZBH = $.getUrlParam("BZBH");
   BZMC = $.getUrlParam("BZMC");
   XWBH = $.getUrlParam("XWBH");
   XWMC = $.getUrlParam("XWMC");
   RZBH = $.getUrlParam("RZBH");

   var data = selectRYInfomation(JSON.stringify(XmlData));  
   showRYXX(data);
}

   
function selectRYInfomation(XmlData){      
  var returnValue= visitServicePage(pubJson.PcrmUrl+"/jlquery/selectWorkflow.do?XmlData="+XmlData); 
  var allData=returnValue.data; 
  
  var url=pubJson.PcrmUrl+"/OperatorAction/countOperatorByBm.do?"+"s="+Math.random();
  var returnData=visit(url,XmlData);
  var count=returnData.count;
  
  //分页总页数赋值，需要放在DataPagerInit方法的上面			
  $("#pro-fy").attr("currmaxpage",count);  	
  $("#pro-fy").attr("fileName",returnValue.fileName);  
  DataPagerInit("pro-fy","currPage");	  

  //调用数据方法之前，将界面table中的数据清空  使用 remove() 将整个 <tr> 干掉
  //$("#tableShow tr:not(:first)").remove();
  if((returnValue.data).length == 0){   
	alert("未查找到符合条件的人员!");
  }

  return allData;
}

//展示岗位
function showRYXX(data){   
	//===>吴显浩
	  //调用数据方法之前，将界面table中的数据清空  使用 remove() 将整个 <tr> 干掉
	  $("#tableShow tr:not(:first)").remove();
	//把查询的清空放到append之前
	
  for(var i=0;i<data.length;i++){ 
    $("#tableShow").append('<tr class="fuxuan">' +
                           '<td><input id="ry_'+data[i].RYBH+'_'+data[i].BMDM+'" name="ryxx_'+data[i].RYBH+'" type="checkbox" '+
		                   'class="checkbox" onclick="checkRY(\'' + data[i].RYBH + '\',\''+ data[i].BMDM +'\')"/></td>' +
						   '<td><span id="rymc_'+data[i].RYBH+'">'+data[i].RYMC+'</span></td>' +
					   	   '<td title="'+data[i].BMMC+'"><span id="bmmc_'+data[i].RYBH+'">'+data[i].BMMC+'</span></td>' +
						   '<td><span>'+data[i].GWFZMC+'</span></td></tr>');     
  }
}

function checkRY(ry,bm) {
	var saveo = {};  
	saveo["rybh"] = ry;
	saveo["bmdm"] = bm;
	saveo['bmmc'] = $("#bmmc_" + ry).html(); 
	saveo["rymc"] = $("#rymc_" + ry).html(); 
	if ($("#ry_"+ry+'_'+bm).prop("checked") == true) {  
	  saveArray.push(saveo);
	} else {
		len = saveArray.length;
		for ( var i = 0; i < len; i++) {
			if (saveArray[i]['rybh'] == ry) {
				saveArray[i]['rybh'] = null;
				saveArray[i]['bmdm'] = null;
				saveArray[i]['bmmc'] = null;
				saveArray[i]["rymc"] = null;
			}
		}
	}
	showry(); 
}

function showry() { 
	var span_text = '';
	len = saveArray.length;
	$(".bm").empty();
	for ( var i = 0; i < len; i++) {
		if (saveArray[i]["rybh"] != null) {
			if (span_text == null) {
				span_text = '<a>' + saveArray[i]['bmmc'] + '-' + saveArray[i]["rymc"]
						+ '<label title="删除" onclick="del_checkry(\''
						+ saveArray[i]["rybh"] + '\',\''+ saveArray[i]["bmdm"] +'\')">×</label></a>';
			} else {
				span_text = span_text + '<a>' + saveArray[i]['bmmc'] + '-' + saveArray[i]["rymc"]
						+ '<label title="删除" onclick="del_checkry(\''
						+ saveArray[i]["rybh"] + '\',\''+ saveArray[i]["bmdm"] +'\')">×</label></a>';
			}
		}
	}
	if (span_text != null) {
	  $(".bm").append(span_text);
	}
}

function del_checkry(ry,bm) {
  var span_text = '';
  var deleteSize=0;
  for ( var i = 0; i < saveArray.length; i++) {
	if (saveArray[i]['rybh'] == ry && saveArray[i]['bmdm'] == bm) {
//    	saveArray[i]['rybh'] = null;
//		saveArray[i]['bmdm'] = null;
//		saveArray[i]['bmmc'] = null;
//		saveArray[i]["rymc"] = null;
		delete saveArray[i];
		deleteSize++;
	}
  }
  saveArray.sort();
  saveArray.length-=deleteSize;
//  for(var i in saveArray){
//	  saveArray[i]=saveArray[i]
//  }
  document.getElementById("ry_"+ry+'_'+bm).checked = false;
  showry(); 
}

function cx_rymc() {
  var XmlData = [];
  var json = {};
  json.sqlid="com.jlsoft.pcrm.dataInfo.sql.DefineInfo.viewOperatorByBm-DefineInfo";
  json.rymc = $("#rymc").val();
  json.bmdm = $("#department").val();
  json.QryType = "Report";
  XmlData.push(json);  
  var data = selectRYInfomation(JSON.stringify(XmlData));  
  showRYXX(data);
} 

//确定按钮
function return_ry() { 
	var iframe = parent.$(".contont iframe:not(:hidden)").contents();
	var ryText="";
	for(var i=0;i<saveArray.length;i++){
		if(i==saveArray.length-1)
			ryText+=saveArray[i].rymc;
		else
			ryText+=saveArray[i].rymc+",";
	}
	
	var ID="",BID="";
	if(JPBJ==0){
		ID="JLCS"; BID="#jlcopy";
	}else{
		ID="JLJP"; BID="#jlreply";
	}
	$(iframe).find(":text[jlid="+ID+"]:not(:disabled)").val(ryText);
	$(iframe).find(":hidden[jlid="+ID+"]:not(:disabled)").val(JSON.stringify(saveArray));
	
	parent.globalTinyBox.close();
	if(!JL.isNull(ryText)){
		parent.button.OAShow(BID);
	} else {
		parent.button.OAInit(parent.$("div.cz input#jlsave,#jlsavedraft"));
		parent.$("div.cz input#jlcallback,#jlcallbackone,#jlreply,#jlcopy").show();
	}
}