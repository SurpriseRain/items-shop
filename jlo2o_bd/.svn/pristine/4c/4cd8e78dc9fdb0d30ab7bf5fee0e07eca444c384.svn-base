var JLSelectTree = {};
JLSelectTree.zNodes = [];	//需要加载的数据

var setting = {
	view: {	dblClickExpand: false,
		nameIsHTML: true,
		selectedMulti: false
	},
	data: {	simpleData: {enable: true}	},
	callback: {
		onClick: onTreeClick
	}
};

function onTreeClick(e, treeId, treeNode) {
	var cID = treeId.split("_")[2];

	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var nodes = zTree.getSelectedNodes();

	var callback = $("#d_"+cID).attr("callback");
	callback = JL.isNull(callback) ? {} : JSON.parse(callback);

	for (var i=0 ; i<nodes.length ; i++) {
		var node = nodes[i];
		$.each(callback, function(key,value){
			if($("#"+key).length>0){
				$("#"+key).val(node[value]);
			}else if($("[name="+key+"]").length>0){
				if(node['level']==0&&value=='pId'){
					$("[name="+key+"]").val(node['id']);
				}else{
					$("[name="+key+"]").val(node[value]);
				}
			}
		});
	}
}

//JLSelectTree.init = function(o,cID,disabled) {
JLSelectTree.init = function(o,cID,initField,hidefield,record,disabled){
    JLSelectTree.load(o,cID,disabled);
    JLSelectTree.write(o,cID,record,disabled);
}

 JLSelectTree.load = function(o,cID,disabled){
    var json = {};
    var src = $(o).attr("src");
    if(!JL.isNull(src)){
    	json["src"]=src;
    }
    var url = $(o).attr("url");
	if(!JL.isNull(url)){
		json["url"]=url;
	}
	var XmlData = {};
	XmlData = $.extend({} ,XmlData,userInfo);
    json["data"]={"XmlData":JSON.stringify(XmlData)}
    var resultData = JL.ajax(json); 
    resultData = resultData.data.returnList;
    JLSelectTree.zNodes = [];
    for(var i=0;i<resultData.length;i++){
    	var rowData = {};
    	rowData["id"] = resultData[i].KEY;
    	rowData["pId"] = resultData[i].PARENT;
    	rowData["name"] = resultData[i].VALUE;
    	JLSelectTree.zNodes.push(rowData);
    }
//    
//	JLSelectTree.zNodes =[
//	{id:1, pId:0, name:"北京"},
//	{id:2, pId:0, name:"天津"},
//	{id:3, pId:0, name:"上海"},
//	{id:6, pId:0, name:"重庆"},
//	{id:6, pId:0, name:"重庆"},
//	{id:4, pId:0, name:"河北省"},
//	{id:41, pId:4, name:"石家庄"},
//	{id:42, pId:4, name:"保定"},
//	{id:43, pId:4, name:"邯郸"},
//	{id:44, pId:4, name:"承德"},
//	{id:5, pId:0, name:"广东省"},
//	{id:51, pId:5, name:"广州"},
//	{id:52, pId:5, name:"深圳"},
//	{id:53, pId:5, name:"东莞"},
//	{id:54, pId:5, name:"佛山"},
//	{id:6, pId:0, name:"福建省"},// open:true 默认打开    isHidden:true  隐藏节点
//	{id:61, pId:6, name:"福州"},
//	{id:62, pId:6, name:"厦门"},
//	{id:63, pId:6, name:"泉州"},
//	{id:64, pId:6, name:"三明"},
//	 ];
}

JLSelectTree.write = function(o,cID,record,disabled) {
	var value = JL.isNull(record[cID]) ? "" : record[cID];
	var str = '<input id="'+cID+'" name="'+cID+'" value="'+value+'" type="text" readonly ';
	if(disabled){
		str += ' disabled="disabled" ';
	}
	str += '/> '+
			  '<div id="d_Tree_'+cID+'" class="menuContent"  style="display:none; position: absolute;margin: 38px 0px 0px 123px;z-index:10000;"> '+
				  '	<ul id="u_Tree_'+cID+'" class="ztree" style="margin-top:0;width:220px;"></ul> '+
			  '</div>';
	$(o).append(str);
	$.fn.zTree.init($("#u_Tree_"+cID), setting, JLSelectTree.zNodes);

	if(!disabled){
		$("#"+cID).click(function(){
			$("#d_Tree_"+cID).slideToggle();
		});
		
		$("#d_Tree_"+cID).mouseleave(function(){
			$("#d_Tree_"+cID).fadeOut("fast");
		});
	}
}
