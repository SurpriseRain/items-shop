/**
 * rootName 根节点名称
 * tree_div 节点加载位置
 * autoScroll 自动滚动
 */
function ReorderTree(rootName,tree_div,autoScroll){
  this.rootName = rootName;
  this.tree_div = tree_div;
  this.autoScroll = autoScroll;
}

//rootId:根节点；ctxPath:根目录；pBackStr:查询数据条件；treeCxjb:查询级别
var sqlID;
var backStr;
ReorderTree.prototype.init = function(rootId,ctxPath,pSqlID,pBackStr,treeCxjb) {
backStr = pBackStr;
sqlID = pSqlID;
Ext.BLANK_IMAGE_URL = ctxPath+'/images/default/s.gif';
var tree = new Ext.tree.TreePanel({
 id:'jltree',
 el:this.tree_div,
 autoScroll:true,
 loader: new Ext.tree.TreeLoader({dataUrl:ctxPath+"/TreeDate.jsp?sid="+Math.random()+"&sqlID="+sqlID+"&backStr="+backStr+"&treeCxjb="+treeCxjb})
 
/*第一次访问的时候只加载根节点下的数据*/
//Ext.tree.TreeLoader({dataUrl:ctxPath+'/ReorderTreeJson?sid='+Math.random()+'&sqlID='+sqlID+'&backStr='+backStr+'&treeCxjb='+treeCxjb+"&pid="})
});

if(this.autoScroll != undefined){
tree.setAutoScroll(this.autoScroll);
}

/*增加click事件*/
tree.on('click',function(node){
    clickEvent(node,sqlID);
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

var root = new Ext.tree.AsyncTreeNode({
    text: this.rootName,
    draggable:false,
     expanded : true,
    id:rootId
});
tree.setRootNode(root);
tree.render();
}
