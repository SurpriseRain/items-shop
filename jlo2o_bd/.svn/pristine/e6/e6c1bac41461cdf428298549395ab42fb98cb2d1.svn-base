var uploadFile = {};

uploadFile.bdxx = {};
var record = {};
uploadFile.load = function(url, data){
record = data;
}
uploadFile.write = function(o,disabled){
uploadFile.bdxx = JSON.parse($(o).attr("field"));

$.each(uploadFile.bdxx,function(i,bdxx){
	var p =  document.createElement("p");
	o.appendChild(p);
	var zdid = bdxx.zdid;
		//附件上传
			var eles = document.createElement("input");
			$(eles).attr("name",zdid);
			$(eles).attr("file",bdxx.file);//1.任意格式的文件  2.只能是图片
			$(eles).attr("type",bdxx.type);//text
			$(eles).attr("style","display:none");
			p.appendChild(eles);
			if(record==null||record[zdid]==null){
				$(eles).attr("value",$(eles).attr("value"));
			}else{
				$(eles).attr("value",JSON.stringify(record[zdid]));
			}
				
						if($(eles).attr("file") != null){
							var eles2 = document.createElement("input");
							$(eles2).attr("id","file_"+bdxx.zdid);
							$(eles2).attr("name","file_"+bdxx.zdid);
							if(disabled==false){
								$(eles2).attr("style","width: 150px; height: 24px");
							}else{
								$(eles2).attr("style","width: 150px; height: 24px;display:none");
							}
							$(eles2).attr("type","file");
							p.appendChild(eles2);
							var eles3 = document.createElement("input");
							$(eles3).attr("id","upload"+zdid);
							$(eles3).attr("onclick","form_uploadfile(this)");
							$(eles3).attr("value","上传");
							$(eles3).attr("type","button");
							if(disabled==true){
								$(eles3).attr("style","display:none");
							}
							p.appendChild(eles3);
						}
					//附件下载
								var fileArray = {};
								if(record==null||record==undefined||record[zdid]==null||record[zdid] == undefined){
										fileArray = {};
								}else{
										fileArray = record[zdid];
								}
								for(var j=0;j<fileArray.length;j++){
								var  tmpS = document.createElement("a");
								var fileUrl=fileArray[j].fileUrl;
								var fileName=fileArray[j].fileName;
								var down ="<span style='border:1px solid black;cursor:pointer'>";
								    down +="<a id='A_file_"+zdid+"'  onclick=\"form_downloadfile('"+fileUrl+"',"+$(eles).attr('file')+")\" style='color: blue'>"+fileName+"</a>";
									if(disabled==false){
										//删除附件
									down +="<img src='delete123.gif' onclick=\"delectFile('"+fileName+"','"+fileUrl+"','"+zdid+"',this)\"/>"
									}
									down +="</span>&nbsp";
								$("#file_"+zdid).parent().append(down);
								}
				 o.appendChild(p);
		});
}

uploadFile.init = function(o,record,disabled) {
    uploadFile.load($(o).attr('url'),record);
    uploadFile.write(o,disabled);

}

//点击删除
function delectFile(fileName,fileUrl,zdid,obj){
	var fileArray = [];
	$(obj).parent().remove();
	var result = eval('('+$("input[name='"+zdid+"']").val()+')');
	for(var i=0;i<result.length;i++){
			if(fileName == result[i].fileName && fileUrl == result[i].fileUrl){
			
			}else{
				var fileObject ={"fileName":result[i].fileName,"fileUrl":result[i].fileUrl};
				fileArray.push(fileObject);
			}
	}
	$("input[name='"+zdid+"']").val(JSON.stringify(fileArray));

};
//点击上传
function form_uploadfile(obj){
	var fid=$(obj).attr("id");
	var bid=fid.substr(7,fid.length);
	if($("[name="+bid+"]").attr("file")==2){
		alert("aaaa");
		var imageType = "jpg bmp gif png jpeg";
		var fileType = $("#file_"+bid).val().substr($("#file_"+bid).val().lastIndexOf(".")+1,$("#file_"+bid).val().length);
		if(imageType.indexOf(fileType)==-1){
			alert("请上传图片文件(\".jpg\", \".bmp\", \".gif\", \".png\",\".jpeg\")");
			return false;
		}
		var fileSize=$("#file_"+bid)[0].files[0].size;
		fileSize=Math.ceil(fileSize / 1024);
		if(fileSize>500){
			alert("图片大小("+fileSize+"KB)超出限制500KB");
			return false;
		}
	}
	
	var fileArray=[];
	fileArray.push("file_"+bid);
	var url=pubJson.FormUrl+"/FormUpload/upload.do";
	//alert(url);
	if(confirm("确认上传?")){
		$.ajaxFileUpload({
			type:"POST",
			secureuri:false,
			fileElementId:fileArray,
			url:encodeURI(url),//encodeURI避免中文乱码
			data:{},
			dataType:"text",
			success: function(data) { 
				var json = jQuery.parseJSON(data);
				var jsondata = json.data;
				var resultData=jsondata.resultData;
				var errorFileSize=0;
				for(var i=0;i<resultData.length;i++){
					if(resultData[i].STATE==1){
						//alert("保存成功,等待管理员审核!");
						var FILEID=resultData[i].FILEID;
						var id=FILEID.substr(5,FILEID.length);
						
//						$("#"+FILEID).hide();$("#upload_"+id).hide();$("#reupload_z10").show();
						var fileArray=$("[name="+id+"]").val()==""?[]:JSON.parse($("[name="+id+"]").val());
						var fileObject={"fileUrl":resultData[i].FILE_URL,"fileName":resultData[i].FILE_DESC};
						fileArray.push(fileObject);
						if(initField.contains((id+".2"))){
							if(initField.contains((id+".3"))){
								$("#"+FILEID).parent().append("<span style='border:1px solid black;cursor:pointer'><a id='A_"+FILEID+"' onclick=\"form_downloadfile('"+resultData[i].FILE_URL+"',"+$("[name="+id+"]").attr("file")+");\" style='color: blue'>"+resultData[i].FILE_DESC+"</a><img src='delete123.gif' onclick=\"delectFile('"+fileName+"','"+fileUrl+"','"+zdid+"',this)\"/></span>");
							}else{
								$("#"+FILEID).parent().append("<span style='border:1px solid black;cursor:pointer'><a id='A_"+FILEID+"' onclick=\"form_downloadfile('"+resultData[i].FILE_URL+"',"+$("[name="+id+"]").attr("file")+");\" style='color: blue'>"+resultData[i].FILE_DESC+"</a></span>");
							}
						}
						$("input[name="+id+"]").val(JSON.stringify(fileArray));
					}else if(resultData[i].STATE==0){
						errorFileSize++;
					}
				} 
				if(errorFileSize==0){
					alert("保存成功!");
				}else{
					alert("保存失败!");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("保存失败!"+textStatus);
		    }
		});
	}
}

//点击浏览下载
function form_downloadfile(url,fileType){
	if(fileType==2){
    	var imgUrl="<iframe id=customerQuery_iframe name=customerQuery_iframe src=/v9_new/showImages.html?src=";
    	imgUrl += url;
    	imgUrl += " width=800px height=300px frameborder=0 marginheight=0 marginwidth=0 style=display:black; border:0px solid #F0F0F0 scrolling=false></iframe>";
    	TINY.box.show(imgUrl,0,0,0,1);
	}else{
		//var fileUrl=pubJson.FormUrl+"/FormUpload/download.do?XmlData={\"url\":\""+url+"\"}";
		window.open(url);
	}
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
  