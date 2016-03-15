/**
 * rootName 根节点名称
 * tree_div 节点加载位置
 * autoScroll 自动滚动
 */
function MulReorderTree(rootName,tree_div,autoScroll){
  this.rootName = rootName;
  this.tree_div = tree_div;
  this.autoScroll = autoScroll;
}

//rootId:根节点；ctxPath:根目录；pBackStr:查询数据条件；treeCxjb:查询级别
var sqlID;
var backStr;
MulReorderTree.prototype.init = function(rootId,ctxPath,pSqlID,pBackStr,treeCxjb) {
backStr = pBackStr;
sqlID = pSqlID;
Ext.BLANK_IMAGE_URL = ctxPath+'/images/default/s.gif';
var tree = new Ext.tree.TreePanel({
	id:'jltree',
	el:this.tree_div,
	autoScroll:true,
	checkModel:'cascade',
	onlyLeafCheckable:false,
	plugins:[new Ext.ux.TreePanelCheck()],
	/*第一次访问的时候只加载根节点下的数据*/
	loader: new Ext.tree.TreeLoader({baseAttrs:{uiProvider:Ext.ux.TreeCheckNodeUI},dataUrl:ctxPath+"/TreeDate.jsp?sid="+Math.random()+"&sqlID="+sqlID+"&backStr="+backStr+"&treeCxjb="+treeCxjb})
});

if(this.autoScroll != undefined){
	tree.setAutoScroll(this.autoScroll);
}

/*增加click事件*/
tree.on('click',function(node){
    //alert(node.id+"+"+node.text);
});

/*根据点击的节点加载该节点下的数据,点击哪个节点就加载哪个节点的数据*/
tree.on('beforeload',function(node){
    if(node.id != rootId){
    	tree.loader.dataUrl = ctxPath+"/TreeDate.jsp?sid="+Math.random()+"&sqlID="+sqlID+"&backStr="+backStr+"&treeCxjb="+((node.id).length/2+1)+"&pid="+node.id;
    }
    if(node.id == rootId && node.leaf != undefined){
    	tree.loader.dataUrl = ctxPath+"/TreeDate.jsp?sid="+Math.random()+"&sqlID="+sqlID+"&backStr="+backStr+"&treeCxjb="+((node.id).length/2+1)+"&pid="+node.id;
    }
});


/*tree.on('expandnode',function(node){
  var ui = node.getUI();
  if (ui.checkbox.checked) {
    Ext.each(node.childNodes,function(n){this.checkNode(n, true);}, this);
  }
});*/

var root = new Ext.tree.AsyncTreeNode({
    text: this.rootName,
    draggable:false,
     expanded : true,
    id:rootId
});
tree.setRootNode(root);
tree.render();
}

MulReorderTree.prototype.expandAll = function(){
	Ext.getCmp("jltree").expandAll();
}

function checkNodeExist(nodeId){
	return Ext.getCmp("jltree").getNodeById(nodeId);
}

MulReorderTree.prototype.checkNode = function(nodeId, checked) {
  var tmp_tree =  Ext.getCmp("jltree");
   if (Ext.isArray(nodeId)) {
       Ext.each(nodeId, function(n) {
    	   if (n.length >1) {
    		   n = tmp_tree.getNodeById(n);
               n.getUI().toggleCheck(checked);
    	   }
       });
   } else {
	   var node = tmp_tree.getNodeById(nodeId);
	   if(node){
		   node.getUI().toggleCheck(checked);
	   }
   }
}

MulReorderTree.prototype.getCheckValue = function (){
   var checkedNodesIds = "";
   var checkedNodes = Ext.getCmp("jltree").getChecked();
   var node = null;
   for (var i = checkedNodes.length - 1 ;i >= 0 ; i--) {
     node = checkedNodes[i];
     if(!node.getUI().checkbox.indeterminate){
        checkedNodesIds += ((checkedNodesIds == "")?"":",") + node.id;
     }
     /*if (node.leaf) {去掉非叶子节点*/
     //checkedNodesIds += ((checkedNodesIds == "")?"":",") + node.id;
   /*}*/
 }
 return checkedNodesIds;
}

MulReorderTree.prototype.getAllCheckValue = function (){
	var checkedNodesIds = "";
	var nodes = Ext.getCmp("jltree").getRootNode().childNodes;
	for (var j = 0; j < nodes.length; j++) {
	     var node = Ext.getCmp("jltree").getRootNode().childNodes[j];
	     if (node.hasChildNodes()) {
	         for (var i = 0; i < node.childNodes.length; i++) {
	             if (node.childNodes[i].getUI().checkbox.checked) {
	              	 checkedNodesIds+= ((checkedNodesIds == "")?"":",") + node.childNodes[i].id;
	             }
	          }
	     }
	}
	return checkedNodesIds;
}

MulReorderTree.prototype.getAllCheckValueLevelThree = function (){
	var checkedNodesIds = "";
	var nodes = Ext.getCmp("jltree").getRootNode().childNodes;
	for (var j = 0; j < nodes.length; j++) {
	     var node = Ext.getCmp("jltree").getRootNode().childNodes[j];
	     if (node.hasChildNodes()) {
	         for (var i = 0; i < node.childNodes.length; i++) {
	        	 var nodee = node.childNodes[i];
	    	     if (nodee.hasChildNodes()) {
	    	         for (var k = 0; k < nodee.childNodes.length; k++) {
	    	             if (nodee.childNodes[k].getUI().checkbox.checked) {
	    	              	 checkedNodesIds+= ((checkedNodesIds == "")?"":",") + nodee.childNodes[k].id;
	    	             }
	    	          }
	    	     }
	          }
	     }
	}
	return checkedNodesIds;
}

MulReorderTree.prototype.getAllCheckValueLevelFour = function (){
	var checkedNodesIds = "";
	var nodes = Ext.getCmp("jltree").getRootNode().childNodes;
	for (var j = 0; j < nodes.length; j++) {
		var node = Ext.getCmp("jltree").getRootNode().childNodes[j];
		if (node.hasChildNodes()) {
			for (var i = 0; i < node.childNodes.length; i++) {
				var nodee = node.childNodes[i];
				if (nodee.hasChildNodes()) {
					for (var k = 0; k < nodee.childNodes.length; k++) {
						var nodeee = nodee.childNodes[k];
						if (nodeee.hasChildNodes()) {
							for (var m = 0; m < nodeee.childNodes.length; m++) {
								if (nodeee.childNodes[m].getUI().checkbox.checked) {
									checkedNodesIds+= ((checkedNodesIds == "")?"":",") + nodeee.childNodes[m].id;
								}
							}
						}
					}
				}
			}
		}
	}
	return checkedNodesIds;
}
MulReorderTree.prototype.getAllCheckValueLevelFive = function (){
	var checkedNodesIds = "";
	var nodes = Ext.getCmp("jltree").getRootNode().childNodes;
	for (var j = 0; j < nodes.length; j++) {
		var node = Ext.getCmp("jltree").getRootNode().childNodes[j];
		if (node.hasChildNodes()) {
			for (var i = 0; i < node.childNodes.length; i++) {
				var nodee = node.childNodes[i];
				if (nodee.hasChildNodes()) {
					for (var k = 0; k < nodee.childNodes.length; k++) {
						var nodeee = nodee.childNodes[k];
						if (nodeee.hasChildNodes()) {
							for (var m = 0; m < nodeee.childNodes.length; m++) {
								var node5 = nodeee.childNodes[m];
								if (node5.hasChildNodes()) {
									for (var n = 0; n < node5.childNodes.length; n++) {
										var node6 = node5.childNodes[n];
										if (node6.hasChildNodes()) {
											for (var x = 0; x < node6.childNodes.length; x++) {
												if (node6.childNodes[x].getUI().checkbox.checked) {
													checkedNodesIds+= ((checkedNodesIds == "")?"":",") + node6.childNodes[x].id;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	return checkedNodesIds;
}

MulReorderTree.prototype.getCheckName = function (){
   var checkedNodesIds = "";
   var checkedNodes = Ext.getCmp("jltree").getChecked();
   var node = null;
   for (var i = checkedNodes.length - 1 ;i >= 0 ; i--) {
     node = checkedNodes[i];
     if(!node.getUI().checkbox.indeterminate){
        checkedNodesIds += ((checkedNodesIds == "")?"":",") + node.text;
     }
  }
 return checkedNodesIds;
}
