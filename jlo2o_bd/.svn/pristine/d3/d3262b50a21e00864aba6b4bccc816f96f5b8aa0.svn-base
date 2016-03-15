JL.JLForm = function (json){
	return new JLForm(json)
} 

var JLForm = function(json){
	var initials = {
		obj: null,
		workflow: {},
		plugins: {},
		datas: {}	
	};
	$.extend(initials, json);

	this.define = function(json){
		var obj = this;
		$.each(json,function(key,value){
			obj[key] = value;
		})
	}
	
	this.define({
		"setUrlParam":function(key,val){
			var obj ={};
			obj[key]=val;
			initials["UrlParam"]=obj;
		},
		"getUrlParam":function(paramName){
			paramValue = null;  
			var url=initials.UrlParam==undefined?"":initials.UrlParam["url"];
			if (url.indexOf("?") > 0 && url.indexOf("=") > 0) {  
				arrSource = unescape(url).substring(url.indexOf("?")+1, url.length).split("&"), i = 0; 
					//alert(arrSource);
				while (i < arrSource.length)
					{
						if(arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase()){
							paramValue = arrSource[i].split("=")[1];
						}
						i++;
					}  
			}  
			return paramValue;  
		},
		"setTab": function(o){
			initials["obj"] = o;
		}, "getTab": function(){
			return $(initials["obj"]);
		}, "setData": function(json){
			$.extend(initials.datas, json);
		}, "getData": function(){
			delete initials["datas"]["_id"];
			return initials.datas;
		}, "setAfterInit": function(fn){
			initials["afterinit"] = fn;
		}, "getAfterInit": function(){
			return initials["afterinit"];
		}, "setPlugin": function(json){
			$.extend(initials.plugins, json);
		}, "getPlugin": function(){
			return initials.plugins;
		}, "reloadPlugin": function(key, value){
			try{
				$("#d_"+key).empty();
				var form = this;
				var JLID = eval(value["jlid"]);
				if(!JL.isNull(JLID)){
					if(JLID["version"] == 2){
						var json = {};
						json["obj"] = $("#d_"+key);
						json["zdid"] = key;
						json["value"] = form.getData()[key];
						json["config"] = form.getPlugin()[key];
						JLID.init(json);
					}
				}
			}catch(e){
				
			}
		}, "setEvents": function(fn){
			initials["Events"] = fn;
		}, "runEvents": function(fn){
			if(!JL.isNull(initials["Events"])){
				initials["Events"]();
			}
		}, "parseWorkflow": function(data){
			var BDBH = JSON.parse(data.jkdm).name;
			var JLBH = JL.isNull(data.jlbh)? 0:data.jlbh;
			var YWDJLX = JL.isNull(data.ywdjlx)? BDBH: data.ywdjlx;
			var YWSJ = JL.isNull(data.ywsj)? {}: JSON.parse(data.ywsj);
			YWSJ = JL.isNull(YWSJ[YWDJLX])? {}: YWSJ[YWDJLX];
			var MXSJ = JL.isNull(data.mxsj)? {}: JSON.parse(data.mxsj);
			YWSJ["bdbh"] = BDBH;
			YWSJ["jlbh"] = JLBH;
			this.setData($.extend({} ,YWSJ, MXSJ));
			
			if(JLBH!=0){
				var Record=JL.ajax({
					"url":"/form/getRecord.do",
					"data":{"bdbh":BDBH,"jlbh":JLBH}
				});
				if(Record.string!="form/getRecord"&&Record!=undefined){
					this.setData($.extend({}, this.getData(), Record["data"]));
				}
			}
		}, "setWorkflow": function(json){
			var XmlData={ "PI_USERNAME":userInfo.RYDM };
			$.extend(XmlData, json);

			var resultData = JL.ajax({
				"src":pubJson.PcrmUrl+"/document/viewWorkflowFirstStep.do",
				"data":{"XmlData":JSON.stringify(XmlData)}
			});

			if(!JL.isNull(resultData)){
				var stepData=resultData.data.listWorkflowStepAction;
				for(var i=0;i<stepData.length;i++){
					if(stepData[i].jklx==5){
						this.parseWorkflow(stepData[i]);
						initials["workflow"] = stepData[i];
						return false;
					}
				}
			}
		}, "getWorkflow": function(){
			return initials["workflow"];
		}, "initValue": function(data){
			$.each(data,function(key,value){
				var jl = $("#d_"+key);
				var html = $("[name='"+key+"']");
				if( jl.length > 0){
					
				}else if( html.length > 0){
					if(html[0].type == "radio"){
						value = JL.isString(value) ? value: value.key;
						html.filter("[value='"+value+"']").attr("checked",true);
					}else if(html[0].type == "checkbox"){
						var array = JL.isString(value) ? value.split("|"): value; 
						for(var i=0;i<array.length;i++){
							var row = array[i];
							row = JL.isString(row) ? row: row.key;
							html.filter("[value='"+row+"']").attr("checked",true);
						}
					}else{
						if(html[0].type == "select-one"){
							value = JL.isString(value) ? value: value.key;
						}
						html.val(value);
					}
				}
			});
			
			var plugin = this.getPlugin();
			$.each(plugin,function(key,value){
				if(!$("[name='"+key+"']").is(":disabled") && !JL.isNull(value["qx"])){
					var qx = JL.isNull(userInfo[value["qx"]])? "": userInfo[value["qx"]];
					$("[name='"+key+"']").val(qx);
				}
			});
		}, "initPlugIn": function(jsonData){
			var form = this;
			var plugin = form.getPlugin();
			$.each(plugin, function(key, value){
				try{
					var JLID = eval(value["jlid"]);
					if(!JL.isNull(JLID)){
						if(JLID["version"] == 2){
							var json = {};
							json["obj"] = form.getTab().find("#d_"+key);
							json["zdid"] = key;
							json["value"] = form.getData()[key];
							json["tab"] = form.getTab();
							json["config"] = form.getPlugin()[key];
							var INITFIELD = JL.isNull(jsonData.initfield)? []: JSON.parse(jsonData.initfield);
							var HIDEFIELD = JL.isNull(jsonData.hidfield)? []: JSON.parse(jsonData.hidfield);
							json["INITFIELD"] = INITFIELD;
							json["HIDEFIELD"] = HIDEFIELD;
							JLID.init(json);
						}
					}
				}catch(e){
					console.info(e.message);	
				}
			});
			
			var plugin = this.getPlugin();
			$.each(plugin,function(key,value){
				if(!$("[name='"+key+"']").is(":disabled") && !JL.isNull(value["qx"])){
					var qx = JL.isNull(userInfo[value["qx"]])? "": userInfo[value["qx"]];
					$("[name='"+key+"']").val(qx);
				}
			});
			
		}, "initDisabled": function(json){
			this.getTab().find("input:not(:button),select,textarea").attr("disabled",true);
			for(var i=0;i<json.INITFIELD.length;i++){
				var key = json.INITFIELD[i];
				this.getTab().find("[name='"+key+"']").attr("disabled",false);
			}
		}, "initHidden": function(json){
			for(var i=0;i<json.HIDEFIELD.length;i++){
				var key = json.HIDEFIELD[i];
				this.getTab().find("[name='"+key+"']").hide();
			}
		}, "onceQuery": function(queryForm, obj){
			var pluginName = obj.attr("name");
			var plugin = queryForm.getPlugin()[pluginName];
			plugin["autocallback"] = true;
			queryForm.setPlugin({pluginName : plugin});
			obj.click();
			plugin = queryForm.getPlugin()[pluginName];
			delete plugin["autocallback"];
			queryForm.setPlugin({pluginName:plugin});
		}, "lastInit": function(){
			var afterInit = this.getAfterInit();
			if(!JL.isNull(afterInit)){
				afterInit();
			}
		}, "initForm": function(json){
			JL.loading(true);
			if(json["save"] == true){
				this.loadButton(["new","save","savedraft"]);
			}
			var HIDEFIELD = JL.isNull(json["HIDEFIELD"])? []: json["HIDEFIELD"];
			this.initHidden({"HIDEFIELD":HIDEFIELD});
			this.initPlugIn(json);
			this.runEvents();
			this.lastInit();
			setTimeout(function(){JL.loading(false);},1000);
		}, "initFormByWorkflow": function(json){
			JL.loading(true);
			if(json.BZBH == 1){
				this.loadButton(["new","save","savedraft"]);
			}else{
				this.loadButton(["save","savedraft"]);
			}
			this.setWorkflow(json);
			var workflow = this.getWorkflow();
			this.initValue(this.getData());
			var INITFIELD = JL.isNull(workflow.initfield)? []: JSON.parse(workflow.initfield);
			this.initDisabled({"INITFIELD":INITFIELD});
			var HIDEFIELD = JL.isNull(workflow.hidfield)? []: JSON.parse(workflow.hidfield);
			this.initHidden({"HIDEFIELD":HIDEFIELD});
			this.initPlugIn(workflow);
			this.runEvents();
			this.lastInit();
			setTimeout(function(){JL.loading(false);},1000);
		}, "submit": function(json){
			var workflow = this.getWorkflow();
			var MESSAGE_ID = JL.isNull(workflow.msgid)? "": workflow.msgid;
			var INITFIELD = JL.isNull(workflow.initfield)? []: JSON.parse(workflow.initfield);
			if($.inArray("jlbh", INITFIELD)==-1){
				INITFIELD.push("jlbh");
			}
			
			if($.inArray("bdbh", INITFIELD)==-1){
				INITFIELD.push("bdbh");
			}

			var resultData = JL.ajax({
				"contentType":"application/x-www-form-urlencoded;charset=utf-8",
				"data":{"PI_USERNAME":userInfo.RYDM,"MESSAGE_ID":MESSAGE_ID,"initField":JSON.stringify(INITFIELD),"json":JSON.stringify(this.getData())},
				"url":"/form/saveRecord.do"
			});
			if(resultData!=null){
				resultData=typeof resultData=="string"?resultData:resultData.data;
				if(typeof resultData=="string"&&resultData.indexOf("Exception") != -1){
					resultData=resultData.replace(/java.lang.Exception: /gm,'');
					resultData=resultData.replace(/Exception: /gm,'');
					JL.tip("保存失败："+resultData);
					JL.disabledClass(obj,false);
				}else if(resultData.TODOLIST != undefined){
					var TODOLIST = resultData.TODOLIST;
					if(TODOLIST.length>0){
						if(confirm("保存成功[流水号:"+this.getData()["bdbh"]+"-"+resultData.jlbh+"],是否立即进入下一步?")){
							var srcUrl=$("#IFRAME_GZLBH_"+GZLBH)[0].contentWindow.location.href.split("?")[0]+"?XMBH="+TODOLIST[0].XMBH+"&GZLBH="+TODOLIST[0].GZLBH+"&BZBH="+TODOLIST[0].BZBH+"&RZBH="+TODOLIST[0].RZBH;
							$("#IFRAME_GZLBH_"+GZLBH).attr("src",srcUrl);
						}
					}
				}else if(resultData.jlbh!=undefined){
					JL.tip("保存成功[流水号:"+this.getData()["bdbh"]+"-"+resultData.jlbh+"]");
					this.getTab().find("input,select,textarea").attr("disabled",true);
				}else if(resultData==""){
					JL.tip("保存失败");
					JL.disabledClass(obj,false);
				}
				main.loadHead();
			}else{
				JL.disabledClass(obj,false);
			}
		}, "readData": function(){
			var json={};
			this.getTab().find("input:not(:disabled):not(:button)").each(function(){
				var key = $(this).attr('name');
				if(!JL.isNull(key)){
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
				}
			});
			
			this.getTab().find("select:not(:disabled) option:selected").each(function(){
				var key=$(this).parent().attr('name');
				var json2={};
				json2['key']=$(this).val();
				json2['value']=$(this).text();
				json[key]=json2;
			});
			
			this.getTab().find("textarea").each(function(){
				var key=$(this).attr('name');
				json[key]=$(this).val();
			});
			
			$.each(this.getPlugin(),function(key,value){
				if(value["jlid"] == "JLGrid"){
					var gridData = JLGrid.getGrid(key).getDatas();
					gridData = $.map(gridData,function(value){
						if(!JL.isNull(value)){
							return value;
						}
		            });
					json[key] = gridData;
				}
			});

			delete json["undefined"];
			this.setData(json);
		}, "loadButton": function(json){
			var obj = this;
			var buttons = {
				"new":"<input id='jlnew' type='button' class='jl_btn btn_blue btn_maigin_right display_none' value='新建'>",
				"save":"<input id='jlsave' type='button' class='jl_btn btn_blue btn_maigin_right' value='提交'>",
				"savedraft":"<input id='jlsavedraft' type='button' class='jl_btn btn_blue btn_maigin_right display_none' value='保存草稿'>"
			};
			var html = null;
			if(obj.getTab().find(".jl_operation").length > 0){
				html = obj.getTab().find(".jl_operation");
			}else{
				html = $("<div class='jl_operation' style='bottom: 0px; margin-bottom: 0px;'><div class='oper_main'><div class='width_90'></div></div></div>");
				html.appendTo(this.getTab());
			}
			for(var i=0;i<json.length;i++){
				html.find(".oper_main > div").append(buttons[json[i]]);
			}
			obj.getTab().find(".jl_operation > .oper_main > div > #jlsave").click(function(){
				obj.readData();
				obj.submit();
			});
		}
	});
}

JL.Grid = function(json){
	var initials = {
		id:"",
		header:[]
	};
	$.extend(initials, json);
	
	$("#"+initials.id).html("<div class='jl_list'><div class='list_01'></div></div>");
	var html="";
	html+="<dl class='list title font_bold'>"
	html+="<dt style='width:50px'><input type='checkbox'></dt>";
	$.each(initials.header,function(key, value){
		html+="<dd style='width:80px'>"+value.name+"</dd>";
	});
	html+="</dl>";
	$("#"+initials.id+" > .jl_list > .list_01").html(html);
	
	this.loadData = function(data){
		var html = "";
		for(var i=0;i<data.length;i++){
			var row = data[i];
			html+="<dl class='list'>";
			html+="<dt class='width_05'><input type='checkbox'></dt>";
			$.each(row,function(key, value){
				html+="<dd  style='width:80px !important;text-overflow: ellipsis;overflow:hidden;word-break:keep-all;white-space:nowrap;' class='width_10'>"+value+"</dd>";
			})

			html+="</dl>";
		}
		
		$("#"+initials.id+" > .jl_list > .list_01").append("<div style='width:4000px;'></div>");
		$("#"+initials.id+" > .jl_list > .list_01 div").append(html);
	}
	this.loadData = function(data){
		var html = "";
		for(var i=0;i<data.length;i++){
			var row = data[i];
			html+="<dl class='list'>"
				html+="<dt class='width_05'><input type='checkbox'></dt>";
			$.each(row,function(key, value){
				html+="<dd  style='width:80px !important;' class='width_10'>"+value+"</dd>";
			})
			
			html+="</dl>";
		}
		
		$("#"+initials.id+" > .jl_list > .list_01").append(html);
	}
}

JL.param = {};

//传递url参数
JL.setDivUrl=function(key,val){
	JL.param[key]=val;
}

//取url参数
JL.getParam=function(paramName) {  
paramValue = null;  
	var url=JL.param["url"]
if (url.indexOf("?") > 0 && url.indexOf("=") > 0) {  
    arrSource = unescape(url).substring(url.indexOf("?")+1, url.length).split("&"), i = 0; 
		//alert(arrSource);
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