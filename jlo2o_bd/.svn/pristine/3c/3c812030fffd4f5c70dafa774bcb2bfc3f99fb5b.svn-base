var JLDate = {};
JLDate["version"] = 2;
JLDate.config = {};
JLDate.setValue = function(json,value){
	var text = $(json["obj"]).find("[name='"+json["zdid"]+"']");
	text.val(value);
}

JLDate.init = function(json){
	JLDate.config = JL.isNull(json["config"]) ? {} : json["config"];
	if(!JL.isNull(json["INITFIELD"]) && $.inArray(json["zdid"], json["INITFIELD"]) == -1){
		json["disabled"] = true;
	}else{
		json["disabled"] = false;
	}
	
	JLDate.write(json);
}

JLDate.write = function(json){
	var html = $("<input id='time_"+json.zdid+"' name='"+json.zdid+"' type='text' value=''/>");
	html.appendTo($(json.obj));
	
	if(!JL.isNull(json["value"])){
		html.val(json["value"]);
	}
	
	if(json["disabled"]){
		html.attr("disabled","disabled");
	}else{
		var config = {};
		config["applyTo"] = "time_"+json.zdid;
		config["xtype"] = "datefield";
		config["xtype"] = "datefield";
		config["timePicker"] = true;
		config["format"] = JLDate.config["extformat"];
		
		if(!JL.isNull(JLDate.config["extreadOnly"])){
			config["readOnly"] = JLDate.config["extreadOnly"];
		}
		if(JLDate.config["minValue"] == 'sysdate'){
			config["minValue"] = JL.formatDate(0,1);
		}else{
			config["minValue"] = "";
		}
		if(JL.isNull(json["value"])){
			if(JLDate.config["mrValue"] == 0){
				config["value"] = "";
			}else if(JLDate.config["mrValue"] == 1){
				var time = JLDate.config["extformat"] == 'Y-m-d H:i:s'?2:1;
				var addDays= JL.isNull(JLDate.config["addDays"])? 0: JLDate.config["addDays"];
				config["value"] = JL.formatDate(addDays,time);
			}
		}
		if(JLDate.config["extformat"] == "Y-m-d H:i:s"){
			config["menu"] = new DatetimeMenu();
		}
		new Ext.form.DateField(config);
	}
	
}
