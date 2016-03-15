var JLDatalist = {};
JLDatalist.data = [];
JLDatalist.CACHE = [];

JLDatalist.init = function(o,cID,initField,hidefield,record,disabled){
  if(!disabled){
	  JLDatalist.load(o); 
  }
  JLDatalist.write(o,cID,record,disabled); 
}

JLDatalist.load = function(o){
	var Xmldata=[]; 
	var data={};
	data["sqlid"]=$(o).attr("sqlid");
	data["DataBaseType"]=$(o).attr("resource");
	data["dataType"]="Json";
	data["QryType"]="Report";
	data=$.extend({} ,data,userInfo);
	data["FORM_USERID"]=userInfo.RYDM;
	data["FORM_BMID"]=userInfo.BMDM;
	Xmldata.push(data);
	if($(o).attr("resource")!=undefined&&$(o).attr("sqlid")!=undefined){
		var resultData = JL.ajax({"url":"/formQuery/select.do","data":{"json":JSON.stringify(data)}});
		if(!JL.isNull(resultData)){
			JLDatalist.CACHE = resultData.data.hashMapList;
			JLDatalist.CACHE.sqlid=$(o).attr("sqlid");
			JLDatalist.data.push(JLDatalist.CACHE);
		}
	}else if($(o).attr("resource")!=undefined&&$(o).attr("url")!=undefined){
		var resultData = JL.ajax({"url":$(o).attr("url"),"data":{"json":JSON.stringify(data)}});
		if(!JL.isNull(resultData)){
			JLDatalist.CACHE = resultData.data.hashMapList;
			JLDatalist.CACHE.sqlid=$(o).attr("url");
			JLDatalist.data.push(JLDatalist.CACHE);
		}
	}
}

JLDatalist.write = function(o,cID,record,disabled){
	var html="";
	var placeholder = JL.isNull($(o).attr("placeholder"))? "":$(o).attr("placeholder");
	var noEdit = JL.isNull($(o).attr("noEdit"));
	$(o).append("<input type='text' id='"+cID+"' name='"+cID+"' placeholder='"+placeholder+"' list='datalist_"+cID+"' />");
	if(!JL.isNull(record[cID])){
		$("#"+cID).val(record[cID]);
	}
	if(disabled){
		$("#"+cID).attr("disabled", "disabled");
	}else{
		$(o).append("<datalist id='datalist_"+cID+"' ></datalist>");
		for(var i=0;i<JLDatalist.CACHE.length;i++){
			var row = JLDatalist.CACHE[i];
			$("#datalist_"+cID).append("<option value='"+row.VALUE+"'></option>")
		}
		
		$("#"+cID).blur(function(){
			if($("#datalist_"+cID+" option[value='"+this.value+"']").length == 0 && noEdit ){
				$("#"+cID).val("");
				JL.tip("请勿修改");
			}
		});
	}
}
  
