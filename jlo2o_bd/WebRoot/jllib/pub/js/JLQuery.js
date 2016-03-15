var userInfoCookie = $.cookie("userInfo");
var userInfo = JSON.parse(userInfoCookie);
var version = Math.random();
var JLQuery = {
	"CustomQueryUrl":"queryPages/Srch_CustomQuery.html?version="+version+"&PI_USERNAME="+userInfo.RYDM+"&json=",
	//表单查询URL
	"FormQueryUrl":"queryPages/Srch_FormSelect.html?version="+version+"&PI_USERNAME="+userInfo.RYDM+"&json=",
	//搜索引擎URL
	"SearchEngineUrl":"queryPages/Srch_SearchEngine.html?version="+version+"&PI_USERNAME="+userInfo.RYDM+"&json=",
	//表单查询URL
	"InterfaceQueryUrl":"queryPages/Srch_InterfaceQuery.html?version="+version+"&PI_USERNAME="+userInfo.RYDM+"&json=",
	//地址
	"AddressQueryUrl":"queryPages/Srch_Address.html?version="+version+"&PI_USERNAME="+userInfo.RYDM+"&json="
};

fromElement = null;
fromElementG = null;
var CALLBACK,GRID,ISNEWLINE,BEFORECALLBACK,AFTERCALLBACK;

JLQuery.enterOpen = function(e,divID){
	var e = e || window.event; 
	if(e.keyCode == 13){ 
		JLQuery.open(divID);
	} 
}


JLQuery.config = {
	grid: "",
	callback: {}
}

JLQuery.gridShow = function(obj, json){
	var modalURL = "queryPages/Srch_CustomQuery_Grid.html";
	JLQuery["config"] = {};
	JLQuery["config"]["isOpen"] = false;
	JLQuery["config"]["grid"] = json["grid"];
	JLQuery["config"]["callback"] = json["callback"];
	JLQuery["config"]["aftercallback"] = json["aftercallback"];
	JLQuery["config"]["querybh"] = json["querybh"];
	JLQuery["config"]["querytype"] = json["querytype"];
	JLQuery["config"]["init"] = json["init"];
	JLQuery["config"]["autoquery"] = json["autoquery"];
	JLQuery["config"]["autocallback"] = json["autocallback"];
	
    var modalField = {};
    if(!JL.isNull(JLQuery["config"]["init"])){
    	$.each(JLQuery["config"]["init"], function(name,value){
    		if(name.indexOf(".")!=-1){
    			var grid = eval(name.split(".")[0]);
    			modalField[value] = grid.getStore().getAt(grid.getSelectionModel().lastActive).data[name.split(".")[1]];
    		}else{
    			modalField[value] = obj.closest("div.txt_main").find("[name='"+name+"']").val();
    		}
    	});
    	JLQuery["config"]["modalField"] = modalField;
    }

    obj.load(modalURL);
};
JLQuery.show = function(tab, json, obj){
	var modalURL = "queryPages/Srch_CustomQuery.html";
	
	JLQuery["config"] = {};
	
	JLQuery["config"]["isOpen"] = true;
	JLQuery["config"]["grid"] = JL.isNull(obj)? json["grid"]: $(obj).attr("grid");
	JLQuery["config"]["callback"] = JL.isNull(obj)? json["callback"]: JSON.parse($(obj).attr("callback"));
	JLQuery["config"]["aftercallback"] = JL.isNull(obj)? json["aftercallback"]: JSON.parse($(obj).attr("aftercallback"));
	JLQuery["config"]["querybh"] = JL.isNull(obj)? json["querybh"]: JSON.parse($(obj).attr("querybh"));
	JLQuery["config"]["querytype"] = JL.isNull(obj)? json["querytype"]: JSON.parse($(obj).attr("querytype"));
	JLQuery["config"]["init"] = JL.isNull(obj)? json["init"]: JSON.parse($(obj).attr("init"));
	JLQuery["config"]["autoquery"] = JL.isNull(obj)? json["autoquery"]: JSON.parse($(obj).attr("autoquery"));
	JLQuery["config"]["autocallback"] = JL.isNull(obj)? json["autocallback"]: JSON.parse($(obj).attr("autocallback"));
	var modalField = {};
	if(!JL.isNull(JLQuery["config"]["init"])){
		$.each(JLQuery["config"]["init"], function(name,value){
			if(name.indexOf(".")!=-1){
				var grid = eval(name.split(".")[0]);
				modalField[value] = grid.getStore().getAt(grid.getSelectionModel().lastActive).data[name.split(".")[1]];
			}else{
				modalField[value] = tab.find("[name='"+name+"']").val();
			}
		});
		JLQuery["config"]["modalField"] = modalField;
	}

	JL.globalTinyBox.open(modalURL,modalField,JLQuery["config"]["autocallback"]);
};
JLQuery.hide = function(jsonData){
	var config = JLQuery["config"];
	if(JL.isNull(config["grid"])){
		var rowData = JL.isNull(jsonData[0])? {}: jsonData[0];
		$.each(rowData, function(key,value){
			if(!JL.isNull(config["callback"][key])){
				JLQuery.write(config["callback"][key],value);
			}
		});
	}else{
		var grid = JLGrid.getGrid(config["grid"]);
		var count = grid.getDatas().length;
		var lastActive = grid.getSelectedIndex()[0];
		var line = grid.getDatas()[lastActive];
		
		for(var i=0;i<jsonData.length;i++){
			var rowData=jsonData[i];
			var pData={};
			
			$.each(rowData, function(key,value){
				if(config["callback"][key]) {
					pData[config["callback"][key]]=value;	
				}
			});

			if(!JL.isNull(lastActive) && i == 0){
				grid.setRow(pData,lastActive);
			}else{
				grid.addRow(pData);
			}
		}
	}
	
	if(!JL.isNull(JLQuery["config"]["isOpen"])){
		JL.globalTinyBox.close();
	}
	
	if(!JL.isNull(JLQuery["config"]["aftercallback"])){
		JLQuery["config"]["aftercallback"]();
	}
};

JLQuery.write = function(key,value){
	var currentTab = $(".txt_main:not(:hidden)");
	var page = "";
	if(!JL.isNull(currentTab.attr("page"))){
		page = eval(currentTab.attr("page"));
	}
	var plugins = page.getPlugin();
	currentTab = page.getTab();
	
	if(!JL.isNull(plugins[key]) && !JL.isNull(plugins[key]["jlid"])){
		var pluginInit = plugins[key];
		pluginInit["obj"] = currentTab.find("#d_"+key);
		pluginInit["zdid"] = key;
		var plugin = eval(pluginInit["jlid"]);
		try{
			plugin.setValue(pluginInit,value);
		}catch(e){
			console.info(e);
		}
	}else if(currentTab.find("select[name="+key+"]").length>0){
		value = JL.formatString(value); 
		currentTab.find("select[name="+key+"]").val(value);
	}else if(currentTab.find(":radio[name='"+key+"']").length > 0){
		value = JL.formatString(value); 
		currentTab.find(":radio[name='"+key+"'][value='"+value+"']").attr("checked",true);
	}else if(currentTab.find(":checkbox[name='"+key+"']").length > 0){
		if(typeof value == "string"){
			value = JSON.parse(value);
		}
		for(var i=0;i<value.length;i++){
			var checkbox_value = value[i];
			currentTab.find(":checkbox[name='"+key+"'][value='"+checkbox_value["key"]+"']").attr("checked",true);
		}
	}else{
		$("[name="+key+"]").val(value);
	}
}

JLQuery.open = function(divID,cloumnID){
	var o=$("#"+divID);
	var jsonData={};
	
	var cloumn = {};
	if(!JL.isNull($(o).attr('cloumn')))
		cloumn = JSON.parse($(o).attr('cloumn'))[0][cloumnID];
	
	jsonData["querybh"] = JL.isNull(cloumnID) ? $(o).attr('querybh') : cloumn.querybh;
	jsonData["queryModel"] = JL.isNull(cloumnID) ? $(o).attr('querymodel') : cloumn.querymodel;
	jsonData["isNewLine"] = JL.isNull(cloumnID) ? true : false;
	
	jsonData["type"] = JL.isNull(cloumnID) ? $(o).attr('querytype') : cloumn.querytype;
	jsonData["type"] = JL.isNull(jsonData["type"]) ? "define" : jsonData["type"];

	jsonData["init"] = JL.isNull(cloumnID) ? $(o).attr('init') : cloumn.init;
	jsonData["init"] = JL.isNull(jsonData["init"]) ? "{}" : jsonData["init"];

	jsonData["grid"] = JL.isNull(cloumnID) ? $(o).attr('grid') : cloumn.grid;
	jsonData["grid"] = JL.isNull(jsonData["grid"]) ? "{}" : jsonData["grid"];
	
	jsonData["lineNum"] = JL.isNull(cloumnID) ? $(o).attr('linenum') : cloumn.linenum;
	jsonData["lineNum"] = JL.isNull(jsonData["lineNum"]) ? -1 : jsonData["lineNum"];

	jsonData["gridwidth"] = JL.isNull(cloumnID) ? $(o).attr('gridwidth') : cloumn.gridwidth;
	jsonData["gridwidth"] = JL.isNull(jsonData["gridwidth"]) ? {} : JSON.parse(jsonData["gridwidth"]);

	jsonData["gridhidden"] = JL.isNull(cloumnID) ? $(o).attr('gridhidden') : cloumn.gridhidden;
	jsonData["gridhidden"] = JL.isNull(jsonData["gridhidden"]) ? {} : JSON.parse(jsonData["gridhidden"]);

	jsonData["callBack"] = JL.isNull(cloumnID) ? $(o).attr('callback') : cloumn.callback;
	jsonData["callBack"] = JL.isNull(jsonData["callBack"]) ? "{}" : jsonData["callBack"];
	
	jsonData["autoQuery"] = JL.isNull(cloumnID) ? $(o).attr('autoquery') : cloumn.autoquery;
	if(JL.isNull(jsonData["autoQuery"])) delete jsonData["autoQuery"]; 

	jsonData["beforeCallBack"] = JL.isNull(cloumnID) ? $(o).attr('beforecallback') : cloumn.beforecallback;
	if(JL.isNull(jsonData["beforeCallBack"])) delete jsonData["beforeCallBack"]; 

	jsonData["afterCallBack"] = JL.isNull(cloumnID) ? $(o).attr('aftercallback') : cloumn.aftercallback;
	if(JL.isNull(jsonData["afterCallBack"])) delete jsonData["afterCallBack"]; 
	
	jsonData["beforeQuery"] = JL.isNull(cloumnID) ? $(o).attr('beforequery') : cloumn.beforequery;
	if(JL.isNull(jsonData["beforeQuery"])) delete jsonData["beforeQuery"]; 
	if(jsonData["beforeQuery"]!=null){
		if(!eval(jsonData["beforeQuery"])){
			return false;
		}
	}

	var json={};
	if(!JL.isNull(jsonData["autoQuery"])) json['autoQuery'] = jsonData.autoQuery;
	if(!JL.isNull(jsonData["gridwidth"])) json['gridwidth'] = jsonData.gridwidth;
	if(!JL.isNull(jsonData["gridhidden"])) json['gridhidden'] = jsonData.gridhidden;
	if(!JL.isNull(jsonData["lineNum"])) json['lineNum'] = jsonData.lineNum;
	if(!JL.isNull(jsonData["queryModel"])) json['queryModel'] = jsonData.queryModel;
	json['querybh'] = jsonData.querybh;
    if(!JL.isNull(jsonData.init)){
    	$.each(JSON.parse(jsonData.init), function(name,value){
    		if(name.indexOf(".")!=-1){
    			var grid = eval(name.split(".")[0]);
    			json[value] = grid.getStore().getAt(grid.getSelectionModel().lastActive).data[name.split(".")[1]];
    		}else{
    			json[value] = document.getElementsByName(name)[0].value;
    		}
    	});
    }
    
    var customerQuery_iframe = "";
    if(jsonData.type=="form"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.FormQueryUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(jsonData.type=="define"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.CustomQueryUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(jsonData.type=="engine"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.SearchEngineUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(jsonData.type=="address"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.AddressQueryUrl+JSON.stringify(json)+"' width='940px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(jsonData.type=="interface"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.InterfaceQueryUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }
    
    CALLBACK = JL.isNull(jsonData["callBack"]) ? {} : JSON.parse(jsonData["callBack"]);
    GRID = jsonData["grid"];
    ISNEWLINE = jsonData["isNewLine"];
    LINENUM = jsonData["lineNum"];
    BEFORECALLBACK = JL.isNull(jsonData["beforeCallBack"]) ? null : jsonData["beforeCallBack"];
    AFTERCALLBACK = JL.isNull(jsonData["afterCallBack"]) ? null : jsonData["afterCallBack"]; 
    
    var e2 = document.createElement('span');
	$.each(jsonData, function(key,value){
		if(typeof(values) == 'object')
			e2.setAttribute(key,  JSON.stringify(value));
		else
			e2.setAttribute(key,  value);
	});
	e2.setAttribute("l",  e2.getAttribute("callBack"));
	e2.setAttribute("g",  e2.getAttribute("grid"));
	var e = new Object();
	e.srcElement = e2;
    fromElement = e.srcElement;
    
	//JL.globalTinyBox.open(customerQuery_iframe);
	var modalURL = "queryPages/Srch_CustomQuery.html";
    sessionStorage["querybh"] = jsonData["querybh"];
	JL.globalTinyBox.open(modalURL);
}

JLQuery.callBack = function(jsonData){
	
	if(BEFORECALLBACK!=null){
		eval(BEFORECALLBACK+"(jsonData,fromElement,fromElementG)");
		return false;
	}
	if(JL.isNull(GRID) || GRID=="{}"){
		for(var i=0;i<jsonData.length;i++){
			var rowData=jsonData[i];
			$.each(rowData, function(key,value){
				if(!JL.isNull(CALLBACK[key])){
					if($("#d_"+CALLBACK[key]+"[jlid=extGrid]").length>0){
						var grid = eval(CALLBACK[key]);
						grid.getStore().loadData(value);
					}else if($("#d_"+CALLBACK[key]+"[jlid=uploadFile]").length>0){
						value = value == "" ? [] : value;
						value = typeof(value) == 'string' ? JSON.parse(value) : value;
						$("#file_"+CALLBACK[key]).parent().find("span").remove();
						for(var i=0;i<value.length;i++){
							var down ="<span class='file_upload_span'>";
							down +="<a class='file_upload_a' id='A_file_"+CALLBACK[key]+"'  onclick=\"form_downloadfile('"+value[i].fileUrl+"',"+$("input[name="+CALLBACK[key]+"]").attr('file')+")\">"+value[i].fileName+"</a>";
							if($.inArray(CALLBACK[key], INITFIELD)!=-1){
								//删除附件
								down +="<img class='file_upload_img' src='../images/delete.gif' onclick=\"delectFile('"+value[i].fileName+"','"+value[i].fileUrl+"','"+CALLBACK[key]+"',this)\"/>"
							}
							down +="</span>&nbsp";
							$("input[name="+CALLBACK[key]+"]").val(JSON.stringify(value));
							$("#file_"+CALLBACK[key]).parent().append(down);
						}
					}else if($(":radio[name="+CALLBACK[key]+"]").length>0){
						var radiokey= JL.isNull(value.key) ? value : value.key;
						$(":radio[name="+CALLBACK[key]+"][value="+radiokey+"]").attr("checked",true);
					}else if($(":checkbox[name="+CALLBACK[key]+"]").length>0){
						for(var i=0;i<value.length;i++){
							var tempvalue = value[i];
							var checkboxkey= JL.isNull(tempvalue.key) ? tempvalue : tempvalue.key;
							$(":checkbox[name="+CALLBACK[key]+"][value="+checkboxkey+"]").attr("checked",true);
							
						}
					}else if($("select[name="+CALLBACK[key]+"]").length>0){
						var selectkey=JL.isNull(value.key) ? value : value.key;
						$("select[name="+CALLBACK[key]+"]").val(selectkey);
					}else if($("#"+CALLBACK[key]).length>0){
						$("#"+CALLBACK[key]).val(value);
					}else{
						$("[name="+CALLBACK[key]+"]").val(value);
					}
				}
			});
		}
	}else if(!JL.isNull(GRID)){
		var grid = eval(GRID);
		var count = grid.getStore().getCount() == 0 ? 0 : grid.getStore().getCount();
		var lastActive = grid.getSelectionModel().lastActive;
		lastActive = ISNEWLINE ? count : lastActive;
		var line = grid.getStore().getAt(lastActive);
		for(var i=0;i<jsonData.length;i++){
			var rowData=jsonData[i];
			var p={};
			$.each(rowData, function(key,value){
				if(CALLBACK[key]) p[CALLBACK[key]]=value;
				if(i==0 && !ISNEWLINE){
					line.set(CALLBACK[key],value);
				}
			});
			if(i>0 || (i==0 && ISNEWLINE)){
				var recode = grid.getStore().recordType;
				grid.stopEditing();
				var p1 = JL.isNull(line) ? p : $.extend({} , line.data, p);
				var p2 = new recode(p1);
				grid.getStore().insert(lastActive+i,p2);
				
				var groupField=grid.store.getGroupState();
				if(!JL.isNull(groupField)){
					grid.store.groupBy(groupField,true);
				}
				grid.getView().refresh();
				JL.globalTinyBox.close();
				
				if(((i+1)==LINENUM && LINENUM>0) || LINENUM==0){
					return false;
				}
			}
		}
	}			
	
	if(AFTERCALLBACK!=null){
		eval(AFTERCALLBACK);
//		return false;
	}
	
	JL.globalTinyBox.close();
}

function call_customerQuery(e){
	//alert(e.srcElement.getAttribute('p'));
	fromElement = e.srcElement;
	var fieldObj = JSON.parse(e.srcElement.getAttribute('p'));
	var json={};
	json['querybh'] = e.srcElement.getAttribute('bh');
	//json['s'] = e.srcElement.getAttribute('s');
    if(fieldObj!=null){
    	$.each(fieldObj, function(name,value){
    		if(name.indexOf(".")!=-1){
    		}else{
    			json[value] = document.getElementsByName(name)[0].value;
    		}
    	});
    }
	//alert(JSON.stringify(json));
    var customerQuery_iframe = "";
    
    var url=e.srcElement.getAttribute('url')==null?"define":e.srcElement.getAttribute('url');
    if(url=="form"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.FormQueryUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(url=="define"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.CustomQueryUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(url=="engine"){
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.SearchEngineUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }else if(url=="interface"){
    
    	customerQuery_iframe = "<iframe id='tinyBoxIrame' name='tinyBoxIrame' src='"+JLQuery.InterfaceQueryUrl+JSON.stringify(json)+"' width='1000px' onload='JL.resizeIFrame(this)' frameborder='0' marginheight='0' marginwidth='0' style='display:black; border:0px solid #F0F0F0' scrolling='no'></iframe>";
    }
	JL.globalTinyBox.open(customerQuery_iframe);
}

function form_grid_cell_onclick(g,key){
	var e2 = document.createElement('span');
	$.each(g.jlq, function(i,val){
		$.each(val, function(name,value){
			if(name==key){
				$.each(value, function(key,values){
					if(typeof(values) == 'object')
						e2.setAttribute(key,  JSON.stringify(values));
					else
						e2.setAttribute(key,  values);
				});
				e2.setAttribute("r", g.getSelectionModel().lastActive);
				return false; 
			}
		});
	});
	fromElementG=g;
	var e = new Object();
	e.srcElement = e2;
	call_customerQuery(e);
        //callBack_customerQuery('[{"DWQT2":"43323","DWQT3":"wefs"},{"DWQT2":"3456356","DWQT3":"hnfgnd"}]');
}

function form_jl1_onclick(event){
	fromElementG=null;
	var e = e || window.event; 
	call_customerQuery(e);
        //callBack_customerQuery('[{"DWQT2":"43323","DWQT3":"wefs"},{"DWQT2":"3456356","DWQT3":"hnfgnd"}]');
}


function callBack_customerQuery(s){
	if(fromElement.getAttribute('bcb')!=null){
		eval(fromElement.getAttribute('bcb')+"(s,fromElement,fromElementG)");
		return false;
	}
	var json = JSON.parse(s);
	
	var l = JSON.parse(fromElement.getAttribute('l'));
	if(typeof (fromElement.getAttribute('g'))=="undefined" || (fromElement.getAttribute('g')==null&&fromElementG==null)){
		$.each(json, function(i,val){      
			$.each(val, function(name,value){  
				if(l[name]!=undefined){
					var tmpG = undefined;
					if($("#d_"+l[name]).attr("jlid")=="extGrid")
						tmpG = eval(l[name]);
					if(tmpG!=undefined && tmpG.id!=undefined && tmpG.id.indexOf("ext-comp-")==0){//粗略判断ext-grid
						var tmpR = tmpG.getStore().recordType;
						for(var i=0;i<value.length;i++){
							tmpG.getStore().add(new tmpR(value[i]));
						}
					}else{
						if($("[name="+l[name]+"]").attr("type")=="radio"){
							if(value.value==undefined)//判断是否为key-value形式
							  $("input:radio[value="+value+"]").attr('checked',true);
							else
							  $("input:radio[value="+value.value+"]").attr('checked',true);
						}else if($("[name="+l[name]+"]").attr("type")=="checkbox"){
							if(value.length==undefined)
							  $("input:checkbox[value="+value+"]").attr('checked',true);
							else{
							  for(var i=0;i<value.length;i++){
							    $("input:checkbox[value="+value[i].value+"]").attr('checked',true);
							  }
							}
						}else{
							if(value.value==undefined)
							  $("[name="+l[name]+"]").val(value);
							else
							  $("[name="+l[name]+"]").val(value.value);
							if($("[name="+l[name]+"]").attr("file")!=undefined && $("[name="+l[name]+"]").attr("file")==2 && value!=""){
								var jsonArr_file = eval(value);
								for(var n=0;n<jsonArr_file.length;n++){
									var json_file =jsonArr_file[n];
									$("[name="+l[name]+"]").parent().append("&nbsp;<a id='A_file_"+l[name]+
										"' onclick=\"form_downloadfile('"+json_file.fileUrl+"',"+$("[name="+id+"]").attr("file")+
										");\" style='color: blue'>"+json_file.fileName+"</a>");
								}
						    }
						}
				  }
				}
			});
		});
	}else{
		if(fromElementG!=null){
			var grid=fromElementG;
			var ul=fromElement.getAttribute('ul')==null?0:Number(fromElement.getAttribute('ul'));//修改行数
			var ireturn=fromElement.getAttribute('r')==null?grid.getStore().getCount():fromElement.getAttribute('r');
			var line=grid.getStore().getAt(ireturn);
			$.each(json, function(i,val){
				var p={};
				$.each(val, function(name,value){
					if(l[name])
						p[l[name]]=value;
				});
				var recode = grid.getStore().recordType;
				grid.stopEditing();
				if(i==0){
					grid.getStore().remove(line); 
				}
				var p1 = jQuery.extend({} ,line.data,p);
				var p2 = new recode(p1);
				grid.getStore().insert(ireturn,p2);
				if(ul==0){
					return false;
				}
				if((i+1)==ul&&ul>0){
					return false;
				}
			});
		}else{
			var grid=eval(fromElement.getAttribute('g'));
			var keys=grid.getStore().fields.keys;
			$.each(json, function(i,val){      
				var p={};
				$.each(keys, function(j,k){      
					p[k]='';
				}); 
				$.each(val, function(name,value){  
					if(l[name])
						p[l[name]]=value;
				});
				//alert(JSON.stringify(p));
				var recode = grid.getStore().recordType;
		    var p2 = new recode(p);
			grid.stopEditing();
			grid.getStore().insert(grid.getStore().getCount(),p2);
		  });
		}
		var groupField=grid.store.getGroupState();
		if(groupField!=undefined){
			grid.store.groupBy(groupField,true);
		}
		grid.getView().refresh();
	}

	JL.globalTinyBox.close();

	var p2 = fromElement.getAttribute('p2');
	
	fromElement = null;
	fromElementG = null;

	if(p2!=null) eval(p2);
	//个性化操作	回填后执行
	if(typeof(afterBackFill)=='function'){
		afterBackFill();
	}
}