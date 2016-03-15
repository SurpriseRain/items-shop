var JLMultiSelect = {};
JLMultiSelect["version"] = 2;
JLMultiSelect.config = {};
JLMultiSelect.data = [];

JLMultiSelect.setValue = function(json,value){
	value = JL.formatObject(value);
	var text_hidden = $(json["obj"]).find("[name='"+json["zdid"]+"']");
	var text = $(json["obj"]).find("[name='TEXT_"+json["zdid"]+"']");
	
	var str = "";
	var html = "";
	for(var i=0;i<value.length;i++){
		var row = value[i];
		html += row["VALUE"];
		if(i < value.length-1 ){
			html += ",";
		}
	}
	
	text_hidden.val(JSON.stringify(value));
	text.val(html);
}

JLMultiSelect.init = function(json){
	JLMultiSelect.config = JL.isNull(json.config) ? {} : json.config;
	if(!JL.isNull(json["INITFIELD"]) && $.inArray(json["zdid"], json["INITFIELD"]) == -1){
		json["disabled"] = true;
	}else{
		json["disabled"] = false;
		JLMultiSelect.load(json);
	}
	
	JLMultiSelect.write(json);
}

JLMultiSelect.load = function(json){
	var XmlData = [];
	var queryField={};
	queryField["dataType"] = "Json";
	queryField["QryType"] = "Bill";
	queryField["sqlid"] = JLMultiSelect.config["sqlid"];
	queryField["DataBaseType"] = JLMultiSelect.config["resource"];
	queryField=$.extend({}, queryField, userInfo);
	var param= JL.isNull(JLMultiSelect.config["param"]) ? {} : JLMultiSelect.config["param"];
	queryField=$.extend({}, queryField, param);
	XmlData.push(queryField);

	if( !JL.isNull(JLMultiSelect.config["sqlid"]) && !JL.isNull(JLMultiSelect.config["resource"]) ){
		var ajaxJson = {};
		ajaxJson["url"] = "/jlquery/select.do";
		ajaxJson["data"] = {"json":JSON.stringify(XmlData)};
		var resultData = JL.ajax(ajaxJson);
		if(!JL.isNull(resultData)){
			resultData = resultData.data;
			JLMultiSelect.data = resultData;
		}
	}else if( !JL.isNull(JLMultiSelect.config["url"]) ){
		var type = JLMultiSelect.config["resource"] == "outer" ? "src" : "url";
		
		var ajaxJson = {};
		ajaxJson[type] = JLMultiSelect.config["url"];
		ajaxJson["data"] = {"json":JSON.stringify(queryField)};
		var resultData = JL.ajax(ajaxJson);
		if(!JL.isNull(resultData)){
			resultData = resultData.data.returnList;
			JLMultiSelect.data = resultData;
		}
	}
}

JLMultiSelect.write = function(json){
	$(json.obj).append("<input type='text' name='"+json.zdid+"' style='display:none;'/>");
	$(json.obj).append("<input type='text' name='TEXT_"+json.zdid+"' readonly='readonly'/>");
	
	if(!JL.isNull(json["value"])){
		var value = "";
		var values = JSON.parse(json["value"]);
		for(var i=0;i<values.length;i++){
			value += values[i]["VALUE"];
			if(i < values.length-1){
				value += ",";
			}
		}
		$(json.obj).find("[name='"+json.zdid+"']").val(json["value"])
		$(json.obj).find("[name='TEXT_"+json.zdid+"']").val(value);
	}
	
	if(json["disabled"] == false){
		$(json.obj).append("<div class='jl_poplayer'></div>");
		var jl_poplayer = $(json.obj).find(".jl_poplayer");
		$(jl_poplayer).append("<div class='add_jg'><input type='text' placeholder='请输入关键字' style='width: 100% !important;'></div>");
		$(jl_poplayer).append("<ul class='jl_multiselect'></ul>");
		$(jl_poplayer).append("<input id='select' type='button' class='jl_btn btn_red' style='width: 21% !important;' value='全选'>");
		$(jl_poplayer).append("<input id='unselect' type='button' class='jl_btn btn_red' style='width: 21% !important;margin-right:8%;' value='反选'>");
		$(jl_poplayer).append("<input id='ok' type='button' class='jl_btn btn_red btn_medium' value='确定'>");
		
		var jl_multiselect = $(json.obj).find(".jl_multiselect");
		for(var i=0;i<JLMultiSelect.data.length;i++){
			var row = JLMultiSelect.data[i];
			$(jl_multiselect).append("<li class='case_list'><input type='checkbox' value='"+JSON.stringify(row)+"'><label class='checkbox'>"+row.VALUE+"</label><span>"+row.KEY+"</span></li>");
		}
		
		//更改勾选样式
		$(jl_multiselect).find(":checkbox").change(function(){
			if($(this).is(":checked")){
				$(this).closest("li").addClass("case_xuan");
			}else{
				$(this).closest("li").removeClass("case_xuan");
			}
		});
	
		//模糊查询
		$(jl_poplayer).find(".add_jg :text").keyup(function(){
			if(this.value == ""){
				$(jl_poplayer).find(".add_jg #srch").click();
			}
			//srch(this);
			var obj = $(jl_poplayer).find(".add_jg :text")[0];
			var lis = $(jl_multiselect).find("li");
			for(var i=0;i<lis.length;i++){
				var li = lis[i];
				var value = $(li).find("label").html();
				if(value.indexOf(obj.value) == -1){
					$(li).hide();
				}else{
					$(li).fadeIn(500);
				}
			}
		});
		//全选
		$(jl_poplayer).find("#select").click(function(){
			$(jl_multiselect).find("li :checkbox:checked").click();
			$(jl_multiselect).find("li:not(:hidden) :checkbox:not(:checked)").click();
		});
		//反选
		$(jl_poplayer).find("#unselect").click(function(){
			$(jl_multiselect).find("li :checkbox:checked").click();
		});
		
		//确定按钮回填事件
		$(jl_poplayer).find("#ok").click(function(){
			var checked = $(jl_multiselect).find(":checkbox:checked");
			var str = "";
			var value = "";
			for(var i=0;i<checked.length;i++){
				var row = $(checked).eq(i);
				str += $(row).val();
				value += $(row).next("label").html();
				if(i<checked.length-1){
					str += ",";
					value += ",";
				}
			}
			$("[name='"+json.zdid+"']").val("["+str+"]");
			$("[name='TEXT_"+json.zdid+"']").val(value);
			$(jl_poplayer).parent().attr("title",value);
			$(jl_poplayer).fadeOut();
		});
		
		//弹出层
		$("[name='TEXT_"+json.zdid+"']").click(function(){
			$(this).next(".jl_poplayer").fadeIn();
		});
		
		$("body").mousemove(function(){
			if(!$('#d_'+json.zdid)[0].contains(window.event.srcElement) && !$(jl_poplayer).is(":hidden")){
				$(jl_poplayer).fadeOut();
			}
		});
	}else{
		$(json.obj).find(":text").attr("disabled","disabled");
		$(json.obj).attr("title",$(json.obj).find("[name='TEXT_"+json.zdid+"']").val());
	}
}