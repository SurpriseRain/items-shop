//PCRM参数
var XMBH,GZLBH,BZBH,RZBH,JKDM,MSGID,INITFIELD,HIDEFIELD,CSCS,JPID,YWDH,XWBH,XWMC,GWBH,JPBJ;
//FORM参数
var BDBH,JLBH,GRIDARR,JLJP,JLHT;
var JPBJ;
//所有数据
var ALLDATA;
var RECORD;
var XWBH;
var msgid2;
var PI_USERNAME=$.getUrlParam("PI_USERNAME");
var PARENTID=$.getUrlParam("PARENTID");
var userLoginCookie = $.cookie("userLogin");
var userInfoCookie = $.cookie("userInfo");
var userInfo = JSON.parse(userInfoCookie);
var XWMC,XMMC,BZMC;

$(function() {
	if(userLoginCookie == null || userLoginCookie == "{}" || userLoginCookie == "{}" ){
		if(window.top==window.self){//不存在父页面
			window.location.href=pubJson.FormUrl+"/login.html";
		}else{
			parent.window.location.href=pubJson.FormUrl+"/login.html";
		}
	}
	if($.getUrlParam("bdbh")!=null&&$.getUrlParam("jlbh")!=null&&$.getUrlParam("INITFIELD")!=null&&$.getUrlParam("HIDEFIELD")!=null){ 
		BDBH=$.getUrlParam("bdbh");
		JLBH=$.getUrlParam("jlbh");
		INITFIELD=JSON.parse($.getUrlParam("INITFIELD"));
		HIDEFIELD=JSON.parse($.getUrlParam("HIDEFIELD"));
		var url4=pubJson.FormUrl+"/form/getRecord.do?bdbh="+BDBH+"&jlbh="+JLBH;
		RECORD=visitService(url4);
		if(RECORD.string=="form/getRecord"){
			RECORD = {};
		}
		init2(RECORD);
		init(RECORD);
		afterInit(RECORD);
		ALLDATA=RECORD; 
	}else if($.getUrlParam("XMBH")!=null&&$.getUrlParam("GZLBH")!=null&&$.getUrlParam("BZBH")!=null&&$.getUrlParam("RZBH")!=null){
	    GWBH=userInfo.GWFZBH;
		XMBH=$.getUrlParam("XMBH");//项目编号
		GZLBH=$.getUrlParam("GZLBH");//工作流编号
		BZBH=$.getUrlParam("BZBH");//步骤编号
		RZBH=$.getUrlParam("RZBH");//日志编号
		XWBH=$.getUrlParam("XWBH");//行为编号 
		JPID=$.getUrlParam("JPID");//加批ID 
		YWDH=$.getUrlParam("YWDH");//业务单号
		XWMC=$.getUrlParam("XWMC");//行为名称
		XMMC=$.getUrlParam("XMMC");//项目名称
		BZMC=$.getUrlParam("BZMC");//步骤名称
		
		JPBJ=$.getUrlParam("JPBJ");//步骤名称
		loadWorkFlow(XMBH,GZLBH,BZBH,RZBH,XWBH); 
	}
/*
	if(JL.isNull(JPBJ)){
		parent.button.loadButton();
		if(JLBH==0){
			$("[id^='czButton_']:not(:hidden)",parent.document).children(":first").before(parent.button["jlnew"]);
		}
		if(!JL.isNull(GZLBH)){
			if(GZLBH!='00'&&!(GZLBH.toString().startsWith("01")&&GZLBH.length!=2)){
				//parent.button.OAInit();
				if($("[jlid=JLHT]").length>0){
					$("[id^='czButton_']:not(:hidden)",parent.document).find("#jlcallback").show();
					$("[id^='czButton_']:not(:hidden)",parent.document).find("#jlcallbackone").show();
					//加批抄送回退
					$("[jlid=JLHT]").keyup(function(event){ 
						if(!JL.isNull(this.value)){
							//parent.button.OAShow("#jlcallback");
							parent.button.OAInit(parent.$("div.cz input#jlcallback,#jlcallbackone"));
							parent.$("div.cz input#jlsave,#jlsavedraft,#jlreply,#jlcopy").show();
						} else {
							parent.button.OAInit(parent.$("div.cz input#jlsave,#jlsavedraft"));
							parent.$("div.cz input#jlcallback,#jlcallbackone,#jlreply,#jlcopy").show();
						}
					}); 
				}
			}
			if($("[jlid=JLJP]").length>0){
				$("[id^='czButton_']:not(:hidden)",parent.document).find("#jlreply").show();
				$(":text[jlid=JLJP]:not(:disabled)").click(function(event){ //加批人员显示
					var JpcsUrl="queryPages/Srch_Ryxx.html?PI_USERNAME="+userInfo.RYDM+"&JPBJ=1&s="+Math.random()+
					"&XMBH="+XMBH+ "&RZBH="+RZBH+ "&BZBH="+BZBH+ "&XWBH="+XWBH+
					"&YWDH="+YWDH+ "&XMMC="+XMMC+ "&BZMC="+BZMC+ "&XWMC="+XWMC;
					var	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JpcsUrl+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
					parent.globalTinyBox.open(customerQuery_iframe);
				}); 
			}
			if($("[jlid=JLCS]").length>0){
				$("[id^='czButton_']:not(:hidden)",parent.document).find("#jlcopy").show();
				$(":text[jlid=JLCS]:not(:disabled)").click(function(event){ //抄送人员显示
					var JpcsUrl="queryPages/Srch_Ryxx.html?PI_USERNAME="+userInfo.RYDM+"&JPBJ=0&s="+Math.random()+
					"&XMBH="+XMBH+ "&RZBH="+RZBH+ "&BZBH="+BZBH+ "&XWBH="+XWBH+
					"&YWDH="+YWDH+ "&XMMC="+XMMC+ "&BZMC="+BZMC+ "&XWMC="+XWMC;
					var	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JpcsUrl+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
					parent.globalTinyBox.open(customerQuery_iframe);
				}); 
			}
		}
	}
	
	//个性化控制
    if(typeof(formInit)=='function'){
    	formInit();
	}  

	try{parent.window.JL.resizeIFrame(parent.$("#"+PARENTID));}catch(e){}*/
});

function showcJPCS(){ 
  //获得mgdb中信息 
  var url=pubJson.FormUrl+"/form/getRecord.do?bdbh="+BDBH+"&jlbh="+JLBH; 
  var returnValue=visit(url); 
  var str;
  if (returnValue.jphf != undefined){ 
    //显示内容 data[i].RYBH  
    $("body").append('<div class="jphf" style="float:left; width:100%;">'+
	                 '<span>加批详情：</span><div class="xq" id="jpxx"></div></div>');
    for(var i=0;i<returnValue.jphf.length;i++){  
  	  if (i==0){
	    str = '<div>'+JSON.stringify(returnValue.jphf[i][0])+'</div>';
	  }
	  else{
	    str = str + '<div>'+JSON.stringify(returnValue.jphf[i][0])+'</div>';
	  }
    }
    $("#jpxx").append(str);
  }
}

function loadWorkFlow(XMBH,GZLBH,BZBH,RZBH,XWBH){ 
	var XmlData={"XMBH":XMBH,"GZLBH":GZLBH,"BZBH":BZBH,"RZBH":RZBH,"PI_USERNAME":userInfo.RYDM};

	var XmlData={};
	XmlData["XMBH"] = XMBH,
    XmlData["GZLBH"] = GZLBH,
    XmlData["BZBH"] = BZBH,
    XmlData["RZBH"] = RZBH,
    XmlData["PI_USERNAME"] = userInfo.RYDM;
	if(!JL.isNull(XWBH)){
		XmlData["XWBH"]=XWBH;
	}
	var url=pubJson.PcrmUrl+"/document/viewWorkflowFirstStep.do";
	var resultData = JL.ajax({"src":url,"data":{"XmlData":JSON.stringify(XmlData)}});
	if(!JL.isNull(resultData)){
		var stepData=resultData.data.listWorkflowStepAction;
		for(var i=0;i<stepData.length;i++){
			if(stepData[i].jklx==5){
				loadForm(stepData[i]);
				return false;
			}
		}
	}
}

function checkNULL(value,type){
	var str;
	if(type=="string" || type == 0 || type == undefined){
		str = value==undefined || value==null || value=="null" ?"":value;	
	}else if(type=="number" || type == 3 ){
		str = value==undefined || value==null || value=="null" ?0:value;	
	}else if(type=="object" || type == 1 ){
		str = value==undefined || value==null || value=="null" ?{}:JSON.parse(value);	
	}else if(type=="array" || type == 2 ){
		str = value==undefined || value==null || value=="null" ?[]:JSON.parse(value);	
	}
	return str;
}

function loadForm(data){
	JKDM=JSON.parse(data.jkdm);
	BDBH=JKDM.name;
	JLBH=checkNULL(data.jlbh, 3);
	MSGID=checkNULL(data.msgid, 0);
	INITFIELD=checkNULL(data.initfield, 2);
	HIDEFIELD=checkNULL(data.hidfield, 2);
	YWDJLX=checkNULL(data.ywdjlx, 0);
	YWDJLX=YWDJLX==""?BDBH:YWDJLX;
	
	//获取业务数据
	YWSJ=checkNULL(data.ywsj, 1);
	try{YWSJ=YWSJ[YWDJLX];}catch(e){YWSJ={};}
	MXSJ=checkNULL(data.mxsj, 1);
	ALLDATA = {};
	ALLDATA = jQuery.extend({} ,YWSJ, MXSJ);
	
	if(data.cscs != null && data.cscs != ""){
		CSCS = getCSCS(JSON.parse(data.cscs),ALLDATA);
	}
	if(CSCS != null && CSCS != ""){
		ALLDATA = jQuery.extend({} ,ALLDATA, CSCS);
	} 
	
	if(JLBH!=0){
		//获取表单存储数据
		var url=pubJson.FormUrl+"/form/getRecord.do?bdbh="+BDBH+"&jlbh="+JLBH; 
		var Record=visitService(url);
		if(Record.string!="form/getRecord"&&Record!=undefined){
			ALLDATA = jQuery.extend({}, ALLDATA, Record);
		}
	}
	
	//初始化方法
	//个性化控制
    if(typeof(beforeInit)=='function'){
    	beforeInit();
	}
	init2(ALLDATA,INITFIELD);
	init(ALLDATA);
	afterInit(ALLDATA);
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
		if(INITFIELD.contains(divs_id)){
			disabled=false;
		}else if(JSON.stringify(INITFIELD).indexOf('"'+divs_id+'.')!=-1){
			disabled=false;
		}
		var JLID = eval($(divs[i]).attr('jlid'));
		if(!JL.isNull(JLID)){
			if(JL.isNull(JLID["version"])){
				eval($(divs[i]).attr('jlid') + ".init(" + $(divs[i]).attr('id') +",'"+divs_id+"',"+JSON.stringify(INITFIELD)+","+JSON.stringify(HIDEFIELD)+","+JSON.stringify(record)+ ","+disabled +");");
			}else if(JLID["version"] == 2){
				var json = {};
				json["obj"] = $(divs[i]);
				json["zdid"] = $(divs[i]).attr('id').split("d_")[1];
				JLID.init(json);
			}
		}
	}
}

function afterInit(recordVal){
	for(var i=0;i<$("select:not(:disabled)").length;i++){
		var input=$("select:not(:disabled)").eq(i);
		if(!input.is(":hidden")){
			if(input.attr("qx")!=undefined &&(input.val()==''||input.val()=='请选择')){
				input.val(eval(("userInfo."+input.attr("qx"))));
			}
		}
	}
	for(var i=0;i<$(":input[type='text']:not(:disabled)").length;i++){
		var input=$(":input[type='text']:not(:disabled)").eq(i);
		if(!input.is(":hidden")){
			if(input.attr("qx")!=undefined){
				input.val(eval(("userInfo."+input.attr("qx"))));
			}
		}
	}

	if($("#lsh").length==1 && JLBH!=0){
		$("#lsh").append("流水号:"+BDBH+"-"+JLBH);
	}
}

//初始化方法
function init2(recordVal){
	$("input:not(:button)").each(function(){
		var note = $(this).attr('name');
		if(!(recordVal[note] == null || recordVal[note] == "" || recordVal[note] == undefined )){
			if($(this).attr('type')=='text'){
				$(this).attr('value',recordVal[note]);
			}
			if($(this).attr('type')=='radio'){
				$(":radio[name="+note+"][value='"+recordVal[note].key+"']").click();
			}
			if($(this).attr('type')=='checkbox'){
				for(var i=0;i<recordVal[note].length;i++){
					var noteKey=recordVal[note][i].key;
					if(noteKey==$(this).attr('value')&&$(":checkbox[name="+note+"][value='"+noteKey+"']:checked").length==0){
						$(":checkbox[name="+note+"][value='"+noteKey+"']").click();
					}
				}
			}
		}
	});
	
	$("select").each(function(){
		var note = $(this).attr('name');
		if(!(recordVal[note] == null || recordVal[note] == "" || recordVal[note] == undefined )){
			var record = recordVal[note] ;
			$(this).val(record.key);
		}
	});
	
	$("textarea").each(function(){
		var note = $(this).attr('name');
		if(!(recordVal[note] == null || recordVal[note] == "" || recordVal[note] == undefined )){
			$(this).val(recordVal[note]);
		}
	});
	
	$("input:not(:button):not([type=range]),select,textarea").each(function(){
		var note = $(this).attr('name');
		if(!(INITFIELD.contains(note))){
			$(this).attr('disabled','disabled');
		}
	});
	
	var grid="";
	$.each(INITFIELD, function(i, val) {
		if(val.indexOf(".")!=-1&&grid.indexOf(val.split(".")[0])==-1){
			grid+="\""+val.split(".")[0]+"\",";
		}
    });
	GRIDARR=eval('['+grid.substring(0,grid.length-1)+']');
	
	//添加主表上面需要隐藏的部分
	$(HIDEFIELD).each(function(i,value){
		if( value.indexOf(".") == -1 ){
			$("[name="+value+"]").hide();
		}
	});
}

//检查输入
function checkEnter(){
	//必填项通用控制	
	var inputs = $("input[filter]:not(:button):not(:disabled),select[filter]:not(:disabled),textarea[filter]:not(:disabled)");
	for(var i=0;i<inputs.length;i++){
		var input=inputs[i];
		var value=$(input).val();
		var filter=JSON.parse($(input).attr("filter"));

		if(filter["1"]!=null&&filter["1"]!=undefined){
			if($(input).attr("type")=="radio"||$(input).attr("type")=="checkbox"){
				if($("input[name='"+$(input).attr('name')+"']:checked").length==0){
					alert(filter["1"]);
					$(input).focus();
					return true;
				}
			}else{
				if(value==""){ 
					alert(filter["1"]);
					$(input).focus();
					return true;
				}
			}
	    }else if(filter["2"]!=null&&filter["2"]!=undefined){
			if(isNaN(value)){ 
				alert(filter["2"]);
				$(input).focus();
		    	return true;
		    }
	    }else if(filter["3"]!=null&&filter["3"]!=undefined){
	    	var fileMin=$(input).attr("filemin")==""?[]:JSON.parse($(input).attr("filemin"));
	    	var fileArray=value==""?[]:JSON.parse(value);
			if(fileArray<fileMin){ 
				alert(filter["3"]);
				$(input).focus();
		    	return true;
		    }
	    }
	}	
	
	//个性化控制
	if(typeof(checkMustEnter)=='function'){
		if(checkMustEnter()){
			return true; 
		}
	}
	
	//必填项通用控制	
	var inputs = $(".mustEnter:input[type='text']:not(:disabled)");
	for(var i=0;i<inputs.length;i++){
		var className=$(inputs[i]).attr('class');
		if(className !=null && className.indexOf("mustEnter") !=-1 && $(inputs[i]).val()==""&& !$(inputs[i]).is(":hidden")){
			alert("填写内容不正确或必填未填写。"); 
			inputs[i].focus();
			return true;
		}
	}
  
  var inputs = $(".numBer:input[type='text']");   
  for(var n=0;n<inputs.length;n++){
	var val=$(inputs[n]).val();
	var className=$(inputs[n]).attr('class');
    if(isNaN(val)){
      alert("请输入数字！");
      return true;
    }
  }
  //必填项通用控制	
  var checkbox = $(".mustEnter:checkbox:not(:disabled)");
  for(var i=0;i<checkbox.length;i++){ 
	if($(checkbox[i]).attr('name')!=$(checkbox[i-1]).attr('name')){
		var checkbox2=$("input[name='"+$(checkbox[i]).attr('name')+"']:checked");
		if(checkbox2.length==0){
			alert("填写内容不正确或必填未填写。"); 
			$("input[name='"+$(checkbox[i]).attr('name')+"']:first").focus();
			return true;
		}
	}
  }
  
  var radio = $(".mustEnter:radio:not(:disabled)");
  for(var i=0;i<radio.length;i++){ 
	if($(radio[i]).attr('name')!=$(radio[i-1]).attr('name')){
		var radio2=$("input[name='"+$(radio[i]).attr('name')+"']:checked");
		if(radio2.length==0){
			alert("填写内容不正确或必填未填写。"); 
			$("input[name='"+$(radio[i]).attr('name')+"']:first").focus();
			return true;
		}
	}
  }  
  
  //button控制
  var buttons = $("select:not(:disabled)");
  for(var i=0;i<buttons.length;i++){
	var className=$(buttons[i]).attr('class');
    if(className !=null && className.indexOf("mustEnter") !=-1 && ($(buttons[i]).val()=="请选择"||$(buttons[i]).val()=="")){
    	alert("填写内容不正确或必填未填写。"); 
    	buttons[i].focus();
    	return true;
    }
  }
  
  return false;	  
}

var form={};
form.spellDATA= function(){
	var json={};
	$("input:not(:disabled):not(:button)").each(function(){
		var key = $(this).attr('name');
		var type = $(this).attr('type');
		var value = $(this).val();
		if(type=='text' || type=='hidden'){
			json[key]=value;
		}else if((type=='radio' || type=='checkbox') && $(this).prop('checked')){
			if((typeof(json[key]) == "undefined" || json[key]==""))json[key] = [];
			var json2={};
			json2['key']=value;
			json2['value']=$(this).next("label").text();
			if(type=='checkbox'){ 
				json[key].push(json2);
			}else{
				json[key]=json2;
			}
		}
		
		if(json[key]==undefined || json[key]==null){
			json[key]="";
		}
	});
	
	$("select:not(:disabled) option:selected").each(function(){
		var key=$(this).parent().attr('name');
		var json2={};
		json2['key']=$(this).val();
		json2['value']=$(this).text();
		json[key]=json2;
	});
	
	$("textarea").each(function(){
		var key=$(this).attr('name');
		json[key]=$(this).val();
	});

	for(var j=0;j<GRIDARR.length;j++){
		var grid=eval(GRIDARR[j]);
		var key=GRIDARR[j];
		json[key]=[];
		for (var i = 0; i < grid.getStore().getCount(); i++) {
		    var recordData = grid.getStore().getAt(i).data;
			$.each(recordData,function(name,value){
				if(recordData[name]==null)
					recordData[name]=="";
			})
			json[key].push(recordData);
		}
	}
	//alert(JSON.stringify(json));
	return json;
}

function save(buttonID){
	if(buttonID!="jlsavedraft"){
		if(checkEnter()){
			return;
		}
	}

	var json=form.spellDATA();

	if(json['jlbh']==undefined || json['jlbh']=="" ) {
		json['jlbh']=JLBH;
	}
	json['bdbh']=BDBH;
	json['SYS_GSXX01']=userInfo.BMDM.substring(0,4);
	json['Order_Code']=BDBH+"-"+JLBH;
	if(!JL.isNull(GWBH)) {
		json['gwbh']=GWBH;
	}
	if(!JL.isNull(BZBH)) {
		json['SYS_FORM_BZBH']=BZBH;
	}	
	//个性化控制
	
	if(typeof(saveBefore)=='function'){
			if(saveBefore(json)){
				return;
			}
	}
	

	var result=$.extend({},ALLDATA,json);
	delete result["_id"];
    //个性化控制
	if(buttonID!="jlsavedraft"){
	    if(typeof(formCommonPlugin)=='function'){
			if(formCommonPlugin(result,INITFIELD)){
				return;
			}
		}
	}
	
	$.each(userInfo , function(key,value){
		result["SYS_FORM_"+key] = value;
	});
	
	if($.inArray("jlbh", INITFIELD)==-1){
		INITFIELD.push("jlbh");
	}
	
	if($.inArray("bdbh", INITFIELD)==-1){
		INITFIELD.push("bdbh");
	}
	var submitURL=pubJson.FormUrl+"/form/saveRecord.do?MESSAGE_ID="+MSGID+"&initField="+JSON.stringify(INITFIELD)+"&PI_USERNAME="+userInfo.RYDM;
	var returnData={};
	returnData.result=result;
	returnData.url=submitURL;
	return returnData;
}

function noCheckSave(){
	var json=form.spellDATA();

	if(json['jlbh']==undefined || json['jlbh']=="" ) {
		json['jlbh']=JLBH;
	}
	json['bdbh']=BDBH;
	json['SYS_GSXX01']=userInfo.BMDM.substring(0,4);
	json['Order_Code']=BDBH+"-"+JLBH;
	if(!JL.isNull(GWBH)) {
		json['gwbh']=GWBH;
	}
	if(!JL.isNull(BZBH)) {
		json['SYS_FORM_BZBH']=BZBH;
	}	
	//个性化控制
	if(typeof(saveBefore)=='function'){
		if(saveBefore(json)){
			return;
		}
	}

	var result=$.extend({},ALLDATA,json);
	delete result["_id"];
	
	$.each(userInfo , function(key,value){
		result["SYS_FORM_"+key] = value;
	});
	
	if($.inArray("jlbh", INITFIELD)==-1){
		INITFIELD.push("jlbh");
	}
	if($.inArray("bdbh", INITFIELD)==-1){
		INITFIELD.push("bdbh");
	}
	
	JL.disabledClass(parent.$("#jlsave"),true);
	JL.disabledClass(parent.$("#jlsavedraft"),true);
	JL.disabledClass(parent.$("#jlstop"),true);
	var submitURL="/form/saveRecord.do?MESSAGE_ID="+MSGID+"&initField="+JSON.stringify(INITFIELD)+"&PI_USERNAME="+userInfo.RYDM;
	var resultData = JL.ajax({"url":submitURL,"data":{"json":JSON.stringify(result)},"contentType":"application/x-www-form-urlencoded;charset=utf-8"});
	if(resultData!=null){
		resultData=typeof resultData=="string"?resultData:resultData.data;
		if(typeof resultData=="string"&&resultData.indexOf("Exception") != -1){
			resultData=resultData.replace(/java.lang.Exception: /gm,'');
			resultData=resultData.replace(/Exception: /gm,'');
			JL.tip("保存失败："+resultData);
			JL.disabledClass(parent.$("#jlsave"),false);
			JL.disabledClass(parent.$("#jlsavedraft"),false);
			JL.disabledClass(parent.$("#jlstop"),false);
		}else if(resultData.jlbh!=undefined){
			JL.tip("保存成功[流水号:"+result.bdbh+"-"+resultData.jlbh+"]");
			$("input,select,textarea").attr("disabled",true);
		}else if(resultData==""){
			JL.tip("保存失败");
			JL.disabledClass(parent.$("#jlsave"),false);
			JL.disabledClass(parent.$("#jlsavedraft"),false);
			JL.disabledClass(parent.$("#jlstop"),false);
		}
		parent.main.loadHead();
	}else{
		JL.disabledClass(parent.$("#jlsave"),false);
		JL.disabledClass(parent.$("#jlsavedraft"),false);
		JL.disabledClass(parent.$("#jlstop"),false);
	}
}

function getCSCS(obj,preRecord){
	var tempCscs = {};
	for(var item in obj){
		if(obj[item].key == "date"){
			var myDate = new Date();
			var date=myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
			tempCscs[item]=date//myDate.toLocaleDateString();
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
		}else if(obj[item].QZBJ != undefined){
			if(obj[item].QZBJ == 0){
				tempCscs[item]=preRecord[obj[item].key];
			}
		}else{
			tempCscs[item]=obj[item];
		}
	}
	return tempCscs;
}


function afterEdit(e) {
	if(typeof(gridAfterEdit)=='function'){
		gridAfterEdit(e);
	}
}

function getPrintData(dymb,bdbh,jlbh,data){
	var url=pubJson.FormUrl+"/formPrint/findFormPrint.do?dymb="+JSON.stringify(dymb)+"&jlbh="+jlbh+"&bdbh="+bdbh+"&XmlData="+JSON.stringify(data).replace(/%/gm,"%25").replace(/&/gm,"%26");
	window.open(url);
}


