var JLRadioTree = {};
JLRadioTree["version"] = 2;
JLRadioTree.data = [];
JLRadioTree.config = {};

JLRadioTree.setValue = function(json,value){
	value = JL.formatObject(value);
	var text_hidden = $(json["obj"]).find("[name='"+json["zdid"]+"']");
	var text = $(json["obj"]).find("#"+json["zdid"]);
	
	var str = "";
	var html = "";
	for(var i=0;i<value.length;i++){
		var row = value[i];
		html += row["value"];
		if(i < value.length-1 ){
			html += ",";
		}
	}
	
	text_hidden.val(JSON.stringify(value));
	text.val(html);
}

JLRadioTree.init = function(json){
	JLRadioTree.config = JL.isNull(json.config) ? {} : json.config;
	if(!JL.isNull(json["INITFIELD"]) && $.inArray(json["zdid"], json["INITFIELD"]) == -1){
		json["disabled"] = true;
	}else{
		json["disabled"] = false;
	}
	
    JLRadioTree.write(json);
}

JLRadioTree.write = function(json) {
	var text_KEY = $("<input type='text' id='"+json.zdid+"' readonly='readonly'/>").appendTo(json.obj);
	var text = $("<input type='text' name='"+json.zdid+"' class='display_none'/>").appendTo(json.obj);
	var head = $("<div class='add_jg'><span>您当前选择：</span></div>");
	var first = $("<ul class='jl_tree_01' data-levels='1'><li class='title'>请选择</li>"+loadData(1)+"</ul>");
	var button = $("<input type='button' class='jl_btn btn_red btn_long' value='确定'>");
	var jl_poplayer = $("<div class='jl_poplayer'></div>").appendTo(json.obj);
	jl_poplayer.append(head);
	jl_poplayer.append(first);
	if(JLRadioTree.config["TEXT"] == true){
		var editText = $("<input type='text' placeholder='请输入您的详细地址' style='display: block;'>");
		jl_poplayer.append(editText);
	}
	jl_poplayer.append(button);
	
	if(!JL.isNull(json["value"])){
		text.val(json["value"]);
		var jsonArray = JSON.parse(json["value"]);
		var Text = "";
		for(var i=0;i<jsonArray.length;i++){
			Text += jsonArray[i]["value"];
			if(i<jsonArray.length-1){
				Text += ",";
			}
		}
		text_KEY.val(Text);
	}
	if(json["disabled"]==true){
		text.attr("disabled","disabled");
	}

	var jl_poplayer = $(json.obj).find(".jl_poplayer");
	$("#"+json.zdid).click(function(){
		$(jl_poplayer).show();
	});

	$(jl_poplayer).find(".jl_btn").click(function(){
		var labels = $(jl_poplayer).find(".add_jg label");
		var jsonArray = [];
		var labelText = "";
		for(var i=0;i<labels.length;i++){
			var data_label = JSON.parse(labels.eq(i).attr("data-label"));
			var jsonObject = {};
			jsonObject["key"] = data_label["KEY"];
			jsonObject["value"] = data_label["VALUE"];
			jsonArray.push(jsonObject);
			labelText += data_label["VALUE"]+",";
		}
		
		if(JLRadioTree.config["TEXT"] == true){
			var text = $(jl_poplayer).find(":text").val();
			if(JL.isNull(text)){
				JL.tip("请输入详细地址");
				return false;
			}
			jsonArray.push({"key":"","value":text});
			labelText += text;
		}else{
			labelText = labelText.substring(0,labelText.length-1);
		}
		
		$("[name='"+json.zdid+"']").val(JSON.stringify(jsonArray));
		$("#"+json.zdid).val(labelText);
		$(jl_poplayer).hide();
	});
	$("#"+json.zdid).click(function(){
		$(jl_poplayer).show();
	});
	
	$("body").on("click", "#d_"+json.zdid+" .jl_poplayer .add_jg label [del]", function(){
		var label = $(this).parent("label");
		$(label).nextAll("label").andSelf().remove();
		var labelData = JSON.parse($(label).attr("data-label"));
		
		$(jl_poplayer).find("ul").hide();
		$(jl_poplayer).find("ul[data-levels='"+labelData.LEVELS+"']").show();
	});
	$("body").on("click", "#d_"+json.zdid+" .jl_poplayer ul .case_list", function(){ 
		var liData = JSON.parse($(this).attr("data-li"));
		
		$(this).parent("ul").hide();
		var add_jg = $(jl_poplayer).find(".add_jg")
		var label = "<label data-label='"+JSON.stringify(liData)+"' data-levels='"+liData.LEVELS+"' title='"+liData.VALUE+"'>"
				  + "	<font>"+liData.VALUE+"</font>"
				  +	"	<a del title='删除选择'>×</a>"
				  + "</label>";
		if($(add_jg).find("label[data-levels='"+liData.LEVELS+"']").length > 0){
			$(add_jg).find("label[data-levels='"+liData.LEVELS+"']").replaceWith(label);
		}else{
			$(add_jg).append(label);
		}

		
		if(liData.MJBJ == 1){
			return false;
		}else{
			var lis = loadData(liData.LEVELS+1,liData);
			var ul="<ul class='jl_tree_01' data-levels='"+(liData.LEVELS+1)+"'><li class='title'>请选择</li>"+lis+"</ul>";
			if($(jl_poplayer).find("ul[data-levels='"+(liData.LEVELS+1)+"']").length > 0){
				$(this).closest("ul").next("ul").replaceWith(ul);
			}else{
				$(this).closest("ul").after(ul);
			}
		}
		
	});
	
	$('body').mousemove(function(){
		if($('#d_'+json.zdid).length > 0){
			if(!$('#d_'+json.zdid)[0].contains(window.event.srcElement) && !$(jl_poplayer).is(":hidden")){
				$(jl_poplayer).fadeOut();
			}
		}
	});
}

var loadData = function(level,jsonData){
	var details ="";
	var currDatas = [] ;//JLTree.TREEDATA[levels];
	var XmlData = {}
	if(!JL.isNull(jsonData)){
		XmlData = {"LAST":jsonData["KEY"]};
	}
	var url = JLRadioTree.config["url"]+"?XmlData="+JSON.stringify(XmlData);
	var resultData = JL.ajax({"url":url});
	resultData = resultData.data.returnList;
	for(var i=0;i<resultData.length;i++){
		var currData = resultData[i];
		details+="<li class='case_list' data-li='"+JSON.stringify(currData)+"' title='"+currData.VALUE+"'>"+currData.VALUE+"</li>";
	}
	return details;
}
