/**
 * rootName 根节点名称
 * tree_div 节点加载位置
 * autoScroll 自动滚动 true 滚动
 * mulsel 多选标记 true 多选
 */
function ReorderTree(rootName,tree_div,autoScroll,mulsel){
  this.rootName = rootName;
  this.tree_div = tree_div;
  this.autoScroll = autoScroll;
  this.mulsel = mulsel;
}

//rootId:根节点；ctxPath:根目录；pBackStr:查询数据条件；treeCxjb:查询级别
var sqlID;
var backStr;
ReorderTree.prototype.init = function(rootId,ctxPath,pSqlID,pBackStr,treeCxjb) {
backStr = pBackStr;
sqlID = pSqlID;
Ext.BLANK_IMAGE_URL ='images/default/s.gif';
alert(ctxPath+'/dataInfoAction/reorderTreeJson.do?sid='+Math.random()+'&XmlData={\"sqlID\":\"'+sqlID+'\",\"backStr\":\"'+backStr+'\",\"treeCxjb\":\"'+treeCxjb+'\",\"pid\":\"\"}');
var tree = new Ext.tree.TreePanel({
 id:'jltree',
 el:this.tree_div,
 autoScroll:true,
 loader: new
 /*第一次访问的时候只加载根节点下的数据*/
 Ext.tree.TreeLoader({dataUrl:'/dataInfoAction/reorderTreeJson.do?sid='+Math.random()+'&XmlData={\"sqlID\":\"'+sqlID+'\",\"backStr\":\"'+backStr+'\",\"treeCxjb\":\"'+treeCxjb+'\",\"pid\":\"\"}'})
});
/*多选*/
if(this.mulsel){
tree.loader.baseAttrs = {uiProvider:Ext.ux.TreeCheckNodeUI};
tree.checkModel  = 'cascade';
tree.onlyLeafCheckable = false;
/*
tree.on('expandnode',function(node){
	  var ui = node.getUI();
	  if (ui.checkbox.checked) {
	    Ext.each(node.childNodes,function(n){alert("12asd"+n.id);this.checkNode(n.id, true);}, this);
	  }
	});
*/
}
if(this.autoScroll != undefined){
tree.setAutoScroll(this.autoScroll);
}

/*增加click事件*/
tree.on('click',function(node){
    clickEvent(node);
});

/*根据点击的节点加载该节点下的数据,点击哪个节点就加载哪个节点的数据*/

tree.on('beforeload',function(node){
    if(node.id != rootId){
        //tree.loader.dataUrl = ctxPath+"/ReorderTreeJson?sid="+Math.random()+"&sqlID="+sqlID+"&backStr="+backStr+"&treeCxjb="+((node.id).length/2+1)+"&pid="+node.id;
    	tree.loader.dataUrl = "/dataInfoAction/ReorderTreeJson.do?sid="+Math.random()+"&XmlData={'sqlID':'"+sqlID+"','backStr':'"+backStr+"','treeCxjb':'"+((node.id).length/2+1)+"','pid':'"+node.id+"'}";
    }
});

var root = new Ext.tree.AsyncTreeNode({
    text: this.rootName,
    draggable:false,
     expanded : true,
    id:rootId
});
tree.setRootNode(root);
tree.render();
  if(this.mulsel){
    tree.expandAll();
  }
};

ReorderTree.prototype.checkNode = function(nodeId, checked) {
	  var node =  Ext.getCmp("jltree").getNodeById(nodeId);
	  if(node !=null){
		  node.getUI().toggleCheck(checked);
	  }
	}

//获取已勾选值
ReorderTree.prototype.getCheckValue = function (){
   var checkedNodesIds = "";
   var checkedNodes = Ext.getCmp("jltree").getChecked();
   var node = null;
   for (var i = checkedNodes.length - 1 ;i >= 0 ; i--) {
     node = checkedNodes[i];
     if (node.leaf) {/*去掉非叶子节点*/
    	 checkedNodesIds += ((checkedNodesIds == "")?"":",") + node.id;
     }
 }
 return checkedNodesIds;
};

ReorderTree.prototype.clearn = function (){
	   var checkedNodesIds = "";
	   var checkedNodes = Ext.getCmp("jltree").getChecked();
	   var node = null;
	   for (var i = checkedNodes.length - 1 ;i >= 0 ; i--) {
	     node = checkedNodes[i].id;
	     Ext.getCmp("jltree").getNodeById(node).getUI().toggleCheck(false);
	   }
	 }

//将以选的 置为选择

/*ReorderTree.prototype.setCheckValue = function (ids,bol){
  Allid = ids;
  alert(ids);
  var id = ids.split(",");
  var checkedNodes = Ext.getCmp("jltree").getChecked();
  alert(Allid);
  if(bol){
	  for (var i = checkedNodes.length - 1 ;i >= 0 ; i--) {
		  Ext.getCmp("jltree").getNodeById(checkedNodes[i].id).getUI().checkbox.checked=false;
	  }
  }
  
  for (var i =0;i< id.length; i++) {
	  var jsons = Ext.getCmp("jltree").getNodeById(id[i]);
	  if(jsons != null){
		  if(!jsons.getUI().checkbox.checked){
			  jsons.getUI().checkbox.checked =true;
		  }
	  }
  }
}*/
