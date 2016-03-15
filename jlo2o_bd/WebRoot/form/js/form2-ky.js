

//从PCRM里面获取initField,hidFile和msg_id 
/**
var userInfo=JSON.parse($.cookie("userInfo"));
var rydm = userInfo.CZY01;
*/
var rydm='00028990';
var XMBH;
var	GZLBH;
var BZBH;
var RZBH;
var CSCS;
var initfield=[];
var hidfield=[];
var GWBH;
var GWBH2;
var jlbh2;
var bdbh2;
var msgid2;
$(document).ready(function(){
	//获取岗位
       
	var url3=pubJson.PcrmUrl+"/streamDocument/getGw.do?PI_USERNAME="+rydm;
	x3 = createCORSRequest('POST', url3);
	x3.onload = function(){
		  GWBH=(JSON.parse(x3.responseText).data).GWFZBH;
		XMBH=$.getUrlParam("XMBH");//项目编号
		GZLBH=$.getUrlParam("GZLBH");//工作流编号
		BZBH=$.getUrlParam("BZBH");//步骤编号
		RZBH=$.getUrlParam("RZBH");//日志编号
		CSCS=$.getUrlParam("CSCS");//初始化字段传输

		loadWorkFlow(XMBH,GZLBH,BZBH,RZBH,GWBH);
	}
	 x3.onerror = function() {
        alert('岗位请求失败！');
    };
    x3.send();

});


function loadWorkFlow(XMBH,GZLBH,BZBH,RZBH,GWBH){

	var XmlData={"XMBH":XMBH,"GZLBH":GZLBH,"BZBH":BZBH,"RZBH":RZBH,"PI_USERNAME":rydm};
	var url=pubJson.PcrmUrl+"/document/viewWorkflowFirstStep.do?XmlData="+JSON.stringify(XmlData);
	 xhr2 = createCORSRequest('POST', url);
		//处理服务器端响应
		xhr2.onload = function(){
			appendStepAction(JSON.parse(xhr2.responseText),GWBH);
		};
		xhr2.onerror = function() {
			alert('跨源请求失败！');
		};
		xhr2.send();
}


function appendStepAction(proxyData,GWBH){
	var data=proxyData.data.listWorkflowStepAction;
	var new_element=document.createElement("ul"); 
	$(new_element).attr("class","list"); 
	$(new_element).attr("style","display:none"); 
	document.body.appendChild(new_element);
	for(var i=0;i<data.length;i++){
		$(".list").append("<li><div class='case'><a id='XWBH"+data[i].xwbh+"' href='javascript:loadForm("+JSON.stringify(data[i])+")'>"+(i+1)+"."+data[i].xwmc+"</a></div></li>");
		if(data[i].xwbh==1){
			if(data[0].jklx==5){
				loadForm(data[0],GWBH);
			}
		}
	}
}


function loadForm(data,GWBH){

	GWBH2=GWBH;
	var jkdm=JSON.parse(data.jkdm);
	var bdbh=data.bdbh;
	if(data.bdbh==null||data.bdbh=="null"||data.bdbh=="{}"){
		bdbh=jkdm.name;
	}
	bdbh2=bdbh;
	var jlbh=data.jlbh;
	if(data.jlbh==null||data.jlbh=="null"||data.jlbh=="{}"){
		jlbh=0;
	}
	jlbh2=jlbh;
	var msgid=data.msgid;
	if(data.msgid==null||data.msgid=="null"||data.msgid=="{}"){
		msgid="";
	}
	msgid2=msgid;
	initfield=data.initfield;
	if(initfield==null||initfield=="null"||initfield=="{}"){
		initfield="[]";
	}
	hidefield=data.hidefield;
	if(hidefield==null||hidefield=="null"||hidefield=="{}"){
		hidefield="[]";
	}
	var preRecord="";
	var YWSJ = {};//业务数据
	var MXSJ = {};//明细数据
	var tempPrerecord = {};
	if(data.ywsj==null&&data.mxsj==null){
		preRecord="";
		//alert(1);
	}else{
		//alert(2);
		if(data.ywsj!=null){
			$.each(JSON.parse(data.ywsj),function(key,value){
		        //其中key相当于是JAVA中MAP中的KEY，VALUE就是KEY相对应的值
		        YWSJ=value;
		 	});
		}
		if(data.mxsj!=null){
		 	MXSJ = JSON.parse(data.mxsj);		
		}
		tempPrerecord=jQuery.extend({} ,YWSJ, MXSJ);
	}
	if(data.cscs != null && data.cscs != ""){
		CSCS = getCSCS(JSON.parse(data.cscs));
	}
	if(CSCS != null && CSCS != ""){
		tempPrerecord = jQuery.extend({} ,tempPrerecord, CSCS);
	} 

/**
//保存initFiled和hidFiled
	var new_input1=document.createElement("input"); 
				$(new_input1).attr("type","hidden"); 
				$(new_input1).attr("value",initfield); 
				document.body.appendChild(new_input1);
var new_input2=document.createElement("input"); 
				$(new_input2).attr("type","hidden"); 
				$(new_input2).attr("value",hidefield); 
				document.body.appendChild(new_input2);

*/
	preRecord="&preRecord="+JSON.stringify(tempPrerecord);
//获取/getRecord.do
	var url4=pubJson.FormUrl+"/form/getRecord.do?bdbh="+bdbh+"&jlbh="+jlbh+preRecord;
	x4 = createCORSRequest('POST', url4);
		x4.onload = function(){
			var record=(JSON.parse(x4.responseText).data.Map);
			
		}
		 x4.onerror = function() {
			alert('Record请求失败！');
		};
		x4.send();


			//init
/**
				var new_element=document.createElement("script"); 
				$(new_element).attr("type","text/javascript"); 
				$(new_element).attr("src","/v9_new/js/init.js"); 
				document.body.appendChild(new_element); */


}
function save(g){

	var json={};
	var n ;
	
	$(":text").each(function(){
		n=$(this).attr('name');
		json[n]=$(this).prop('value');
	});
	
	$("input:checked").each(function(){
		if(!$(this).attr('disabled')){
			n=$(this).attr('name');
			if($(this).attr('type')=='radio'){
				var json2={};
				json2['key']=$(this).attr('value');
		    	json2['value']=$(this).parent().text();
		    	json[n]=json2;
			}
			if($(this).attr('type')=='checkbox'){
				if (typeof(json[n]) == "undefined") json[n] = [];
				var json2={};
			    json2['key']=$(this).attr('value');
				json2['value']=$(this).parent().text();
				json[n].push(json2);
			}
		}
	});
	
	$("select option:selected").each(function(){
	  if(!$(this).parent().attr('disabled')){
		n=$(this).parent().attr('name');
		var json2={};
		json2['key']=$(this).attr('value');
		json2['value']=$(this).text();
		json[n]=json2;
	  }
	});
	
	$("textarea").each(function(){
		n=$(this).attr('name');
		json[n]=$(this).prop('value');
	});
/**
	for(var j=0;j<g.length;j++){
		var grid=eval(g[j]);
		n=g[j];
		json[n]=[];
		for (var i = 0; i < grid.getStore().getCount(); i++) {
			alert("llll");
		    var record = grid.getStore().getAt(i);
			var row = [];var nullsize=0;var size=0;
			for(var key in record.data){
				row.push(record.data[key]);
				size++;	
				if(record.data[key]==""){
					nullsize++;
				}
			}
			if(nullsize!=size){
				json[n].push(record.data);
			}
		}
	}
*/
	if ($('#msg_id').length == 0)
	v_msg_id='';
	else
	json['msg_id']=msgid2;
	json['bdbh']=bdbh2;		
	json['jlbh']=jlbh2;
	json['gwbh']=GWBH2;
	var result=json;//$.extend({},curRecord,json);
	
    //个性化控制
    if(typeof(formCommonPlugin)=='function'){
		if(formCommonPlugin(result,initfield)){
			return;
		}
	}
//var i2=initfield.substring(1); 
//var i3=i2.substring(0,(i2.length-1));


	/**
	if(!(initfield.contains("jlbh"))){
		initfield.push("jlbh");
	}
	if(!(initfield.contains("bdbh"))){
		initfield.push("bdbh");
	}
*/
	initfield=["SPXX04","SPXX07","SPXX10","SPXX25", "SPXX11", "GSID", "SPXX12", "SPXX16","SCTP","bdbh","jlbh"];

	u=pubJson.FormUrl+"/form/saveRecord.do?MESSAGE_ID="+msgid2+"&initField="+JSON.stringify(initfield)+"&PI_USERNAME="+rydm;
	alert(u);
	//u="http://192.13.4.97:8088/v9_new/form/saveRecord.do?MESSAGE_ID="+v_msg_id+"&initField="+JSON.stringify(initfield)+"&PI_USERNAME=00028990";
	$("#jlsave").attr("disabled","disabled");
    $.ajax({
	    type: "POST",  //请求方式
		contentType: "application/x-www-form-urlencoded;charset=utf-8",//application/json
	    //async: false,   //true表示异步 false表示同步
	    url:u,//"http://192.13.4.115:8080/v9_new/form/saveRecord.do",
		//data:{'msg_id':v_msg_id,'bdbh':v_bdbh,'jlbh':v_jlbh,'json':JSON.stringify(json)},
		data:{json:JSON.stringify(result)},//"json="+JSON.stringify(result),
			//dataType: "json",
        //获取错误信息
    	error:function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
			$("#jlsave").removeAttr("disabled");
        },
		success: function(data){
			if(data.indexOf("Exception") != -1){
				alert("保存失败："+data);
				$("#jlsave").removeAttr("disabled");
			}else if(data.indexOf("dataType") != -1){
				var resultData=JSON.parse(data).data;
				if(JSON.stringify(resultData)=="{}"){
					alert("保存失败：未返回流水号");
					$("#jlsave").removeAttr("disabled");
				}else{
					var bdbh=$("#bdbh").val();
					bdbh=bdbh*1<10?"0"+bdbh:bdbh;
					alert("保存成功[流水号:"+bdbh+"-"+resultData.jlbh+"]");//
				}
			}else{
				alert("保存失败："+data);
				$("#jlsave").removeAttr("disabled");
			}
		}
	  });
}

function getCSCS(obj){
	var tempCscs = {};
	for(var item in obj){
		if(obj[item].key == "date"){
			var myDate = new Date();
			tempCscs[item]=myDate.toLocaleDateString();
		}else if(obj[item].key == "rymc"){
			if(userInfoCookie == undefined || userInfoCookie == null || userInfoCookie == ""){
	   			tempCscs[item]="";
   			}else{
   				tempCscs[item]=userInfo.RYMC;
   			}
		}else if(obj[item].key == "rydm"){
			if(userInfoCookie == undefined || userInfoCookie == null || userInfoCookie == ""){
	   			tempCscs[item]="";
   			}else{
   				tempCscs[item]=userInfo.RYDM;
   			}
		}else if(obj[item].key == "bmdm"){
			if(userInfoCookie == undefined || userInfoCookie == null || userInfoCookie == ""){
	   			tempCscs[item]="";
   			}else{
   				tempCscs[item]=userInfo.BM01;
   			}
		}else if(obj[item].key == "bmmc"){
			if(userInfoCookie == undefined || userInfoCookie == null || userInfoCookie == ""){
	   			tempCscs[item]="";
   			}else{
   				tempCscs[item]=userInfo.BM02;
   			}
		}else{
			tempCscs[item]=obj[item].key;
		}
	}
	return tempCscs;
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

