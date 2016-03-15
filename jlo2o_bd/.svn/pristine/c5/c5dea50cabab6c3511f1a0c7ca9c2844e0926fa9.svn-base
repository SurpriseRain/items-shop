//从PCRM里面获取initField,hidFile和msg_id 
/**
var userInfo=JSON.parse($.cookie("userInfo"));
var rydm = userInfo.CZY01;
*/
var rydm;
var XMBH;
var GZLBH;
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
var XWBH;
var curRecord;

var userInfo;
$(document).ready(function(){
	//获取cookies值
	var userInfoCookie = $.cookie("userInfo");
	if(userInfoCookie == null){
		return;
	}
	userInfo = JSON.parse(userInfoCookie);
	rydm = userInfo.XTCZY01;
	getParameters();
});

//获取流程相关值
function getParameters(){
	//获取岗位
	var url3=pubJson.PcrmUrl+"/streamDocument/getGw.do?PI_USERNAME="+rydm;
	var returnVal = visitService(url3);
	if(returnVal != "undefined"){
		GWBH = returnVal.GWFZBH;
		XMBH=$.getUrlParam("XMBH");
		GZLBH=$.getUrlParam("GZLBH");
		BZBH=$.getUrlParam("BZBH");
		RZBH=$.getUrlParam("RZBH");
		XWBH=$.getUrlParam("XWBH");
		loadWorkFlow(XMBH,GZLBH,BZBH,RZBH,GWBH,XWBH);
	}else{
		alert("岗位请求失败！");
		return;
	}
}

function loadWorkFlow(XMBH,GZLBH,BZBH,RZBH,GWBH,XWBH){
	var XmlData={"XMBH":XMBH,"GZLBH":GZLBH,"BZBH":BZBH,"XWBH":XWBH,"RZBH":RZBH,"PI_USERNAME":rydm};
	var url=pubJson.PcrmUrl+"/document/viewWorkflowFirstStep.do?XmlData="+JSON.stringify(XmlData);
	var returnVal = visitService(url);
	if(returnVal != "undefined"){
		appendStepAction(returnVal,GWBH);
	}else{
		alert("获取流程第一步数据");
		return;
	}
}

function appendStepAction(proxyData,GWBH){
	var data=proxyData.listWorkflowStepAction;
	if(data.length==1){
		if(data[0].jklx==5){
			loadForm(data[0],GWBH);
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
		jlbh="0";
	}
	jlbh2=jlbh;
	var msgid=data.msgid;
	if(data.msgid==null||data.msgid=="null"||data.msgid=="{}"){
		msgid="";
	}
	msgid2=msgid;
	initfield=data.initfield;
	if(initfield==null||initfield=="null"||initfield=="{}"){
		initfield=[];
	}else{
		initfield=JSON.parse(initfield);
	}
	hidefield=data.hidefield;
	if(hidefield==null||hidefield=="null"||hidefield=="{}"){
		hidefield=[];
	}else{
		hidefield=JSON.parse(hidefield);
	}
	var preRecord="";
	var YWSJ = {};//业务数据
	var MXSJ = {};//明细数据
	var tempPrerecord = {};
	if(data.ywsj==null&&data.mxsj==null){
		preRecord="";
	}else{
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
	preRecord="&preRecord="+JSON.stringify(tempPrerecord);
	//获取/getRecord.do
	var url4=pubJson.FormUrl+"/form/getRecord.do?bdbh="+bdbh+"&jlbh="+jlbh+preRecord;
	var returnVal = visitService(url4);
	if(returnVal != "undefined"){
		var Record=returnVal.string;
		if(Record=="form/getRecord"){
			   Record = tempPrerecord;
		}
		init2(Record);
		init(Record);
		curRecord=Record;
	}else{
		alert("获取record失败");
		return;
	}
}

//初始化控件的方法
function init(recordVal) {
	var divs = $("div[jl]");
	for (var i = 0; i < divs.length; i++) {
		var divs_id=($(divs[i]).attr('id').split("_"))[1];
		var disabled = true;
		var record={};
		if(!(recordVal[divs_id] == null || recordVal[divs_id] == "" || recordVal[divs_id] == undefined )){
			record[divs_id] = recordVal[divs_id];
		}
		if(initfield.contains(divs_id)){
			disabled=false;
		}else if(JSON.stringify(initfield).indexOf('"'+divs_id+'.')!=-1){
			disabled=false;
		}
		eval($(divs[i]).attr('jlid') + ".init(" + $(divs[i]).attr('id') +",'"+divs_id+"',"+JSON.stringify(initfield)+","+JSON.stringify(hidefield)+","+JSON.stringify(record)+ ","+disabled +");");
	}
}

//初始化方法，初始非控件方法
function init2(recordVal){
	//alert(JSON.stringify(recordVal));
	$(":text").each(function(){
		var note1 = $(this).attr('name');
		if(recordVal[note1] == null || recordVal[note1] == "" || recordVal[note1] == "undefinde" ){
			
		}else{
			$(this).attr('value',recordVal[note1]);
			if(!(initfield.contains(note1))){
				$(this).attr('disabled','disabled');
			}
		}
	});
	$("input").each(function(){
		var note2 = $(this).attr('name');
		if(recordVal[note2] == null || recordVal[note2] == "" || recordVal[note2] == "undefinde" ){
		}else{
				if(!(initfield.contains(note2))){
					$(this).attr('disabled','disabled');
				}
				if($(this).attr('type')=='radio'){
					if($(this).val() == recordVal[note2]){
						$(this).attr("checked","checked");
					}
					//$(this).attr('value',(recordVal[note2]).key);
					//$(this).parent().html((recordVal[note2]).value);
				}
				if($(this).attr('type')=='checkbox'){
					$(this).attr('value',(recordVal[note2]).key);
					$(this).parent().html((recordVal[note2]).value);
				}
			}
	});
	
	$("select").each(function(){
		var note = $(this).attr('name');
		if(recordVal[note] == null || recordVal[note] == "" || recordVal[note] == "undefinde" ){
		}else{
				if(!(initfield.contains(note))){
					$(this).attr('disabled','disabled');
				}
				var record = recordVal[note] ;
				for(var i2=0;i2<record.length;i2++){
					var eles = document.createElement("opption");
					$(eles).attr('value',record.key);
					$(eles).html(record.value);
					this.appendChild(eles);
				}
			}
	});
	
	$("textarea").each(function(){
		var note3 = $(this).attr('name');
		if(recordVal[note3] == null || recordVal[note3] == "" || recordVal[note3] == "undefinde" ){
		}else{
			if(!(jQuery.parseJSON(initfield).contains(note3))){
				$(this).attr('disabled','disabled');
			}
			
			$(this).attr('value',recordVal[note3]);
		}
	});
}

function save(g){
	//获取cookies和流程相关参数
	if(rydm == undefined){
		var userInfoCookie = $.cookie("userInfo");
		userInfo = JSON.parse(userInfoCookie);
		rydm = userInfo.XTCZY01;
		getParameters();
	}
	
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
				//var json2={};
				//json2['key']=$(this).attr('value');
		    	//json2['value']=$(this).parent().text();
		    	//json[n]=json2;
				json[n]=$(this).attr('value');
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
	if ($('#msg_id').length == 0)
	v_msg_id='';
	else
	json['msg_id']=msgid2;
	json['bdbh']=bdbh2;		
	json['jlbh']=jlbh2;
	json['gwbh']=GWBH2;
	//var result=json;
	var result = $.extend({},curRecord,json);
    //个性化控制
    if(typeof(formCommonPlugin)=='function'){
		if(formCommonPlugin(result,initfield)){
			return;
		}
	}
    
    var initfield2=initfield;
    initfield2.push("jlbh");
    initfield2.push("bdbh");
	u=pubJson.FormUrl+"/form/saveRecord.do?MESSAGE_ID="+msgid2+"&initField="+JSON.stringify(initfield2)+"&PI_USERNAME="+rydm;
	$("#jlsave").attr("disabled","disabled");
    $.ajax({
	    type: "POST",  //请求方式
		contentType: "application/x-www-form-urlencoded;charset=utf-8",//application/json
	    //async: false,   //true表示异步 false表示同步
	    url:u,
		data:{json:JSON.stringify(result)},
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
					//alert("保存成功[流水号:"+bdbh2+"-"+resultData.jlbh+"]");
					//返回当前页面
					if(typeof(formSaveSuccess)=="function"){
						formSaveSuccess(rydm);
					}
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
			//tempCscs[item]=obj[item];
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

