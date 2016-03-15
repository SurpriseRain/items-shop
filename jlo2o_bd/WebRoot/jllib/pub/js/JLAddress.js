var JLTree = {};
JLTree.TREEDATA = {};
JLTree.init = function(o){
	JLTree.url = $(o).attr("url");
//	JLTree.noText = JL.isNull($(o).attr("noText")) ? true : false;
	JLTree.noText = $(o).attr("linenum")*1 != 1 ? false : true;
	JLTree.radio = JL.isNull($(o).attr("radio")) ? false : true;
	if(!JL.isNull(JLTree.url)){
		JLTree.write();
	}	
}
///JLSoftInterface/getDQXX.do
JLTree.write = function(){
	if(JL.isNull(JLTree.TREEDATA[1])){
		var resultData = JL.ajax({"url":JLTree.url+"?XmlData={}"});
		JLTree.TREEDATA[1] = resultData.data.returnList;
	}
	var head="<div class='add_jg'><span>您当前选择：</span></div>";
	var first="<ul id='add_main_01'><li class='title'>请选择</li>"+loadData(1)+"</ul>";
	var html = head+first;
	if(JLTree.noText)
		html += "<input placeholder='请输入您的详细地址' id='detailText' /><a class='bot' onclick='backFill()'>确 定</a>";
	$("#add").html(html);
}

var loadChildNodes = function(obj,pData){
	if(pData.MJBJ == 1 && JLTree.radio){
		return false;
	}
	if($("#add_jg_0"+pData.LEVELS).length==0){
		$(".add_jg").append("<label id='add_jg_0"+pData.LEVELS+"' title='"+pData.VALUE+"' cdata='"+JSON.stringify(pData)+"'><span><font>"+pData.VALUE+"</font></span><a title='删除选择' onclick='deleteNode(this,"+JSON.stringify(pData)+")'>×</a></label>");
	}else{
		$("#add_jg_0"+pData.LEVELS+" > span > font").empty();  
		$("#add_jg_0"+pData.LEVELS+" > span > font").text(pData.VALUE);
		$("#add_jg_0"+pData.LEVELS).attr("title",pData.VALUE);
	}
	$("#add_jg_0"+pData.LEVELS).show();
	
	if( pData.MJBJ==0 ){
		var childnodes = "<li class='title'>请选择</li>"+loadData(pData.LEVELS+1,pData.KEY.toString());
		
		if($("#add_main_0"+(pData.LEVELS+1)).length==0){
			$("#add_main_0"+pData.LEVELS).after("<ul id='add_main_0"+(pData.LEVELS+1)+"'>"+childnodes+"</ul>");
		}else{
			$("#add_main_0"+(pData.LEVELS+1)).html(childnodes);
		}
		$("#add_main_0"+(pData.LEVELS+1)).show();
		$("#add_main_0"+pData.LEVELS).hide();
	}else if(!JLTree.noText && !JLTree.radio){
		backFill();
	}
	
	if(typeof(afterClick)=='function'){
		if(afterClick()){
			return;
		}
	}
}

var loadData = function(levels,prev){
	var details ="";
	var currDatas = [];//JLTree.TREEDATA[levels];
	if(!JL.isNull(prev)){
		if(!JL.isNull(currDatas)&&!JL.isNull(currDatas[prev])){
			currDatas = currDatas[prev];
		}else{
			if(JL.isNull(currDatas)) JLTree.TREEDATA[levels]={};
			var resultData = JL.ajax({"url":JLTree.url+"?XmlData={\"LAST\":\""+prev+"\"}"});
			resultData = resultData.data.returnList;
			if(!JL.isNull(resultData)){
				JLTree.TREEDATA[levels][prev]=resultData;
				currDatas = resultData;
			}
		}
	}
	for(var i=0;i<currDatas.length;i++){
		var currData = currDatas[i];
		details+="<li class='lileft' id='add_li_0"+currData.KEY+"' onclick='loadChildNodes(this,"+JSON.stringify(currData)+")' title='"+currData.VALUE+"' cdata='"+JSON.stringify(currData)+"'>"+currData.VALUE+"</li>";
		if(JLTree.radio) {
			details+="<li class='liright' onclick='backFillRadio(this);'>选择</li>";	
		}
	}
	return details;
}

var deleteNode = function(obj,pData){
	var label = $(obj).parent("label");
	$(label).nextAll("label").remove();
	$(label).remove();
	if(pData.LEVELS==1){
		JLTree.write();
	}else{
		$("li#add_li_0"+pData.LAST).click();
		$("#add_main_0"+(pData.LEVELS+1)).hide();
	}
}


var backFillRadio = function(obj){
	var row = JSON.parse($(obj).prev().attr("cdata"));
	var all = {};
	all["KEY"] = row.KEY;
	all["VALUE"] = row.VALUE;
	JLTree.CALLBACK = [all];
	if(typeof(treeBackFill)=='function'){
		if(treeBackFill()){
			return;
		}
	}
}

var backFill = function(){
	var label = $(".add_jg label");
	var all = {};
	for(var i=0;i<label.length;i++){
		var row = JSON.parse($(label).eq(i).attr("cdata"));
		all["KEY0"+row.LEVELS] = row.KEY;
		all["VALUE0"+row.LEVELS] = row.VALUE;
	}
	if($("#detailText").length>0){
		all["TEXT"] = $("#detailText").val();
	}
	
	JLTree.CALLBACK = [all];
	if(typeof(treeBackFill)=='function'){
		if(treeBackFill()){
			return;
		}
	}
}