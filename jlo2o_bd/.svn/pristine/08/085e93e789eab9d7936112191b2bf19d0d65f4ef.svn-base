<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" errorPage="errorJSP.jsp"%>
<html>
<head>
<title>Tree</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ext-all.css"/>
<script src="<%=request.getContextPath()%>/js/ext-base.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/ext-all.js" type="text/javascript"></script>
<script src="/js/ext-multree-jl.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/TreeCheckNodeUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/TreeCheckNodePlug.js" type="text/javascript"></script>
<script src="/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commons.js"></script>
<%
  String sqlID = request.getParameter("sqlID");
  String backStr = request.getParameter("backStr");
  String treeCxjb = request.getParameter("treeCxjb");
%>
<body>
<div id="jltree" style="overflow:auto; height:100%;width:100%;border:0px solid #c3daf9;"></div>
<script type="text/javascript">
var contextPath = '<%=request.getContextPath()%>';
var tree = new MulReorderTree('全部','jltree',true);
tree.init('00',contextPath,"<%=sqlID%>","<%=backStr%>",<%=treeCxjb%>);

//加载全部树节点
function expandAll(){
	tree.expandAll();
}

//树节点赋值
function checkNode(nodeId){
	tree.checkNode(nodeId,true);
}

//树点击回填事件
function clickEvent(){
	var level = $.getUrlParam("level");
	var value = "";
	if(level!=""){
		if(level==3){
			value=tree.getAllCheckValueLevelThree();
		}else if(level==4) {
			value=tree.getAllCheckValueLevelFour();
		}else if(level==5) {
			value=tree.getAllCheckValueLevelFive();
		}else{
			value=tree.getAllCheckValue();
		}
	}else{
		value=tree.getAllCheckValue();
	}
	
	return value;
}
</script>
</body>
</html>
