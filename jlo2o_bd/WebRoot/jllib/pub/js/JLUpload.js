var JLUpload = {};
JLUpload["version"] = 2;
JLUpload.config = {};

JLUpload.setValue = function(json,value){
	value = JL.formatObject(value);
	json["disabled"] = false;
	JLUpload.preview(json,value);
}


JLUpload.init = function(json){
	JLUpload.config = JL.isNull(json.config) ? {} : json.config;
	if(!JL.isNull(json["INITFIELD"]) && $.inArray(json["zdid"], json["INITFIELD"]) == -1){
		json["disabled"] = true;
	}else{
		json["disabled"] = false;
	}
	JLUpload.write(json);
}

JLUpload.write = function(json){
	var file_box = $("<div class='file_box'></div>").appendTo($(json.obj));
	var file = $("<input type='file' id='file_"+json.zdid+"' name='file_"+json.zdid+"' class='file display_none' multiple='true'>").appendTo(file_box);
	var text_save = $("<input type='text' id='"+json.zdid+"' name='"+json.zdid+"' class='display_none' >").appendTo(file_box);
	var text_show = $("<input type='text' id='text_"+json.zdid+"' readonly>").appendTo(file_box);
	var button = $("<input type='button' id='button_"+json.zdid+"' class='btn' value='浏览...'>").appendTo(file_box);
	var submit = $("<input type='button' id='submit_"+json.zdid+"' name='submit' class='btn' value='上传'>").appendTo(file_box);
	
	if(!JL.isNull(JLUpload.config["max"]) && JLUpload.config["max"] == 1){
		file.removeAttr("multiple");
	}
	if(!JL.isNull(JLUpload.config["auto"])){
		text_show.attr("style","width: 85% !important;");
		submit.hide();
	}
	if(!JL.isNull(JLUpload.config["value"])){
		var value = JSON.parse(json["value"])
		JLUpload.preview(json,value);
	}


	if(json["disabled"] == false){
		file.change(function(){
			var fileName = "";
			var files = $(this).prop("files");
			$.each(files,function(i,row){
				fileName+=row['name'];
				if(i<files.length-1){
					fileName+=" | ";
				}
			});
			text_show.val(fileName);
			if(!JL.isNull(JLUpload.config["auto"])){
				submit.click();
			}
		});
		button.click(function(){
			file.click();
		});
		submit.click(function(){
			JLUpload.upload(json);
		});
	}else{
		$(json.obj).hide();
	}
		
	if(!JL.isNull(json["value"])&&!JL.isNull(json["value"])){
		var files = JSON.parse(json["value"]);
		for(var i=0;i<files.length;i++){
			var file = files[i];
			JLUpload.show = (resultData, 0);
		}
	}
}
JLUpload.preview = function(json,fileArray){
	$(json["obj"]).find("[name="+json["zdid"]+"]").val(JSON.stringify(fileArray));
	for(var index=0;index<fileArray.length;index++){
		var FILE_DESC = fileArray[index]["FILE_DESC"];
		var dd = $(json["obj"]).parent();
		var span = $("<span data-index='"+index+"' title='"+FILE_DESC+"' class='file_span'></span>").appendTo(dd);
		var a = $("<a class='fileLink'>"+FILE_DESC+"</a>").appendTo(span);
		
		span.click(function(){
			var index = $(this).attr("data-index");
			var text = $(this).parent().find("div :text[name]");
			var fileArray = JSON.parse(text.val());
			var FILE_URL = fileArray[index]["FILE_URL"];
			var FILE_DESC = fileArray[index]["FILE_DESC"];
			
			if(JLUpload["config"]["filetype"]==2){
				var showImage = $(".jl_showImage");
				showImage.show(); 
				var image_main = showImage.find(".image_main");
				image_main.show();
				image_main.empty();
				var img = $("<img>").appendTo(image_main); 
				FILE_URL = "proIHAIER/images/zhifubao.png";
				img.attr("src",FILE_URL);
				var imgHeight = img.height();
				var imgWidth = img.width();
				imgHeight = imgHeight/2*-1;
				imgWidth = imgWidth/2*-1;
				image_main.css({"margin-top":imgHeight+"px","margin-left":imgWidth+"px"});
			}else{
				var url=pubJson.FormUrl+"/FormUpload/download.do";//?XmlData="+JSON.stringify({"filename":filename,"keys":keys});
				var XmlData={};
				XmlData["filename"] = FILE_DESC;
				XmlData["url"] = FILE_URL;
				JL.download(url,{"XmlData":JSON.stringify(XmlData)});
			}
		});
		
		if(json["disabled"] == false){
			var i = $("<i class='fa fa-remove font_red'></i>").appendTo(span);
			i.click(function(){
				var span = $(this).parent();
				
				var text = span.parent().find("div :text[name]");
				var fileArray = JSON.parse(text.val());
				fileArray.splice(span.attr("data-index"),1);
				text.val(JSON.stringify(fileArray));
				
				span.siblings(":not(div)").andSelf().remove();
				JLUpload.preview(json,fileArray);
			});
		}
	}
	
}

//点击上传
JLUpload.upload = function(json){
	var files = $(json["obj"]).find(":file").prop("files");
	if(files.length == 0){
		return false;
	}
	
	if(!JL.isNull(JLUpload.config["max"]) && JLUpload.config["max"]<files.length){
		alert("上传文件数量不能超过"+JLUpload.config["max"]);
		return false;
	}
	var fileSize = 0;
	$.each(files,function(i,row){
		var fileName = row['name'];
		var split = fileName.split(".");
		var fileType = split[split.length-1].toLocaleLowerCase();

		if(!JL.isNull(JLUpload.config["type"])){
			if(JLUpload.config["type"] == 2){
				var imageType = ["jpg","bmp","gif","png","jpeg"];
				if(!imageType.contains(fileType)){
					alert("请上传图片文件(\".jpg\", \".bmp\", \".gif\", \".png\",\".jpeg\")");
					return false;
				}
			}else{
				if(!JLUpload.config["type"].contains(fileType)){
					alert("请上传图片文件("+JSON.stringify(JLUpload.config["type"])+")");
					return false;
				}
			}
		}
		
		fileSize+=row['size'];
	});
	
	if(fileSize >= 5242880){
		JL.tip("单次上传文件大小不能大于5M");
		return false;
	}
		
	if( JL.isNull(JLUpload.config["isConfirm"]) || JLUpload.config["isConfirm"] != false){
		if( !confirm("确认上传?") ){
			return false;
		}
	}

    var url = pubJson.FormUrl+"/FormUpload/multiUpload.do";
	$.ajaxFileUpload({
		type:"POST",
		secureuri:false,
		fileElementId:["file_"+json.zdid],
		url:encodeURI(url),//encodeURI避免中文乱码
		data:{},
		dataType:"text",
		success: function(data) { 
			$(json.obj).find("#text_"+json.zdid).val("");
			if(data.indexOf("Exception: java.lang.Exception:")!=-1){
				JL.tip("保存失败,"+data.replace("Exception: java.lang.Exception:",""));
			}else{
				var jsonData = JSON.parse(data).data;
				if(!JL.isNull(jsonData.resultData)){
					var resultData = jsonData.resultData;
					var fileinput = $(json["obj"]).find("[name="+json["zdid"]+"]");
					var fileArray = JL.isNull(fileinput.val())? []: JSON.parse(fileinput.val());
					for(var i=0;i<resultData.length;i++){
						if(resultData[i].STATE==1){
							var fileObject={};
							fileObject["FILE_URL"] = resultData[i].FILE_URL;
							fileObject["FILE_DESC"] = resultData[i].FILE_DESC;
							fileArray.push(fileObject);
						}
					}
					fileinput.val(JSON.stringify(fileArray));
					JLUpload.preview(json,fileArray);
				}
			}
			
			if(!JL.isNull(JLUpload["config"]["afterupload"]) && typeof JLUpload["config"]["afterupload"] == "function"){
				JLUpload["config"]["afterupload"]();
			}
		}
	});
}