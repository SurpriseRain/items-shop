var JLSelect = {};
JLSelect["version"] = 2;
JLSelect.data = {};
JLSelect.config = {};
JLSelect.options = [];
JLSelect.configs = {};
JLSelect.values = {};

JLSelect.setValue = function(json,value){
	value = JL.formatObject(value);
	var select = $(json["obj"]).find("[name='"+json["zdid"]+"']");
	var option = select.find("option[value='"+value["key"]+"']");
	if(option.length == 0){
		select.append("<option value='"+value["key"]+"'>"+value["value"]+"</option>")
	}else{
		select.val(value["key"]);
	}
	
	if(!JL.isNull(json["edit"]) && json["edit"]){
		$(json["obj"]).find(":text").val(value["value"]);
	}
}

JLSelect.init = function(json){
	JLSelect.config = JL.isNull(json.config) ? {} : json.config;
	JLSelect.configs[json.zdid] = JLSelect.config;
	if(!JL.isNull(json["INITFIELD"]) && $.inArray(json["zdid"], json["INITFIELD"]) == -1){
		json["disabled"] = true;
	}else{
		json["disabled"] = false;
	}
	
	JLSelect["options"] = [];
	
	//初始化数据
	if(json["disabled"] == false
			&& JL.isNull(JLSelect.config["edit"])){
		JLSelect.load(json);
	} 

	if(!JL.isNull(json["value"])){
		JLSelect["values"] = {"KEY":json["value"]["key"],"VALUE":json["value"]["value"]};
		
		if(JLSelect["options"].length == 0){
			JLSelect["options"].push(JLSelect["values"]);
		}else{	
			var noOption = true;
			$.each(JLSelect["options"],function(i,arr){
				if(arr["KEY"] == JLSelect["values"]["KEY"]
					&& arr["VALUE"] == JLSelect["values"]["VALUE"]){
					noOption = false;
				}
			});
			if(noOption){
				JLSelect["options"].push(JLSelect["values"]);
			}
		}
	}
	
	//控件渲染
	JLSelect.write(json); 
	
	if($.inArray(json["zdid"], json["INITFIELD"]) != -1){
		$(json["obj"]).find("input,select").attr("disabled");
	}
}

JLSelect.load = function(json){
	JLSelect.options = [];
	
	var XmlData = [];
	var queryField={};
	queryField["dataType"] = "Json";
	queryField["QryType"] = "Bill";
	queryField["sqlid"] = JLSelect.config["sqlid"];
	queryField["DataBaseType"] = JLSelect.config["resource"];
	queryField=$.extend({}, queryField, userInfo);
	var param= JL.isNull(JLSelect.config["param"]) ? {} : JLSelect.config["param"];
	queryField=$.extend({}, queryField, param);
	XmlData.push(queryField);
	
	if( !JL.isNull(JLSelect.config["sqlid"]) && !JL.isNull(JLSelect.config["resource"]) ){
		var ajaxJson = {};
		ajaxJson["url"] = "/jlquery/select.do";
		ajaxJson["data"] = {"json":JSON.stringify(XmlData)};
		var resultData = JL.ajax(ajaxJson);
		if(!JL.isNull(resultData)){
			resultData = resultData.data;
			JLSelect.options = resultData;
		}
	}else if( !JL.isNull(JLSelect.config["url"]) ){
		var type = JLSelect.config["resource"] == "outer" ? "src" : "url";
		
		var ajaxJson = {};
		ajaxJson[type] = JLSelect.config["url"];
		ajaxJson["data"] = {"json":JSON.stringify(queryField)};
		var resultData = JL.ajax(ajaxJson);
		if(!JL.isNull(resultData)){
			resultData = resultData.data.returnList;
			JLSelect.options = resultData;
		}
	}
}

JLSelect.write = function(json){
	$(json.obj).empty();
	if(!JL.isNull(JLSelect.config["edit"]) && JLSelect.config["edit"]){
		var select = $("<select name='"+json.zdid+"' class='display_none'></select>").appendTo(json.obj);
		var placeholder = JL.isNull(JLSelect["config"]["placeholder"])? "请输入关键字": JLSelect["config"]["placeholder"];
		var text = $("<input placeholder='"+placeholder+"' type='text' id='"+json.zdid+"'/>").appendTo(json.obj);
		var div = $("<div class='jl_tree_02 display_none' style='position: absolute; width: 91%; top: 40px;  left: 0px;z-index: 2;background-color: #FFF;'></div>").appendTo(json.obj);
		var ul = $("<ul style='width: 100%;margin-top: -1px;'></ul>").appendTo(div);
		JLSelect.options=[];
		
		if(!JL.isNull(json["value"])&&!JL.isNull(json["value"]["key"])){
			text.val(json["value"]["value"]);
			$("<option selected='selected' value='"+json["value"]["key"]+"'>"+json["value"]["value"]+"</option>").appendTo(select);
		}
		if(json["disabled"]){
			text.attr("disabled","disabled");
		}else{
			text.keyup(function(){
				div.fadeIn();
				select.empty();
				JLSelect.config = JLSelect.configs[this.id];
				JLSelect.config["param"][this.id] = this.value;
				JLSelect.load(json);
				ul.empty();
				$.each(JLSelect.options, function(i, option) {
					var li = $("<li data-value='"+JSON.stringify(option)+"'><span>"+option["VALUE"]+"</span></li>").appendTo(ul);
					
					li.click(function(){
						$(this).siblings().remove();
						select.empty();
						var data_value = JSON.parse($(this).attr("data-value"));
						$("<option selected='selected' value='"+data_value["KEY"]+"'>"+data_value["VALUE"]+"</option>").appendTo(select);
						text.val(data_value["VALUE"]);
						div.fadeOut();
					});
				});
			});
			text.blur(function(event){
				if(select.find("option:selected").length <= 0){
					this.value="";
				}
				div.fadeOut();
			});
			text.focus(function(){
				JLSelect.config = JLSelect.configs[this.id];
				JLSelect.config["param"][this.id] = this.value;
				JLSelect.load(json);
				div.fadeIn();
				ul.empty();
				$.each(JLSelect.options, function(i, option) {
					var li = $("<li data-value='"+JSON.stringify(option)+"'><span>"+option["VALUE"]+"</span></li>").appendTo(ul);
					
					li.click(function(){
						select.empty();
						var data_value = JSON.parse($(this).attr("data-value"));
						$("<option selected='selected' value='"+data_value["KEY"]+"'>"+data_value["VALUE"]+"</option>").appendTo(select);
						text.val(data_value["VALUE"]);
						div.fadeOut();
					});
				});
			});
		}
		
	}else{
		$(json.obj).append('<select name="'+json.zdid+'"><option value="">请选择</option></select>');
		
		if(!JL.isNull(JLSelect.config["attr"])){
			var attr = JLSelect.config["attr"] ;
			$.each(attr ,function(type, arr) {
				$.each(arr ,function(key, value) {
					var selector = type == "text" ? json.zdid + "_TEXT" : json.zdid + "";
					$('[name="'+selector+'"]').attr(key,value);
				});
			});
		}
		
		if(json.disabled){
			if(!JL.isNull(json.value)&&!JL.isNull(json.value.key)){
				$('[name="'+json.zdid+'"]').append('<option value="'+json.value.key+'" selected="selected">'+json.value.value+'</option>');
			}
			$('[name="'+json.zdid+'"]').attr("disabled","disabled");
		}else{
			$.each(JLSelect.options, function(i, option) {
				$(json.obj).find('[name="'+json.zdid+'"]').append('<option value="'+option.KEY+'">'+option.VALUE+'</option>');
				if(!JL.isNull(JLSelect["values"]) && !JL.isNull(JLSelect["values"]["KEY"])  && JLSelect["values"]["KEY"] == option["KEY"]){
					$(json.obj).find('[name="'+json.zdid+'"]').val(JLSelect["values"]["KEY"]);
				}
				
				if(!JL.isNull(option.FILTER)){
					$(json.obj).find('[name="'+json.zdid+'"] option[value="'+option.KEY+'"]').attr("filter",option.FILTER);
				}
			});
		}
	}
}
