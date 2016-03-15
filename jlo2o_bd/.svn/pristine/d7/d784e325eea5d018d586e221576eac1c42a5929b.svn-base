<%@ page language="java" import="java.util.*,net.sf.json.JSONArray,com.jlsoft.utils.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <% 
  	String fileUrl = request.getParameter("fileUrl");
  	String gridId = request.getParameter("gridId");
  	String XmlData = request.getParameter("XmlData");
  	String DataBaseType=request.getParameter("DataBaseType");
  	String selecturl=request.getParameter("selecturl");
  	String hiddenid=request.getParameter("hiddenid");
  	String hiddenname=request.getParameter("hiddenname");
  	//字串变成JSON，取QryType值
  	JSONArray list = JSONArray.fromObject(XmlData);
  	if(DataBaseType==null){
  	 DataBaseType = JLTools.getDataBaseType((String)((Map)list.get(0)).get("DataBaseType"));
  	}
  %>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  	<title></title>
  	<link rel="StyleSheet" href="<%=request.getContextPath()%>/css/ext-all.css" type="text/css" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/customer/qfy/css/grid.css" type="text/css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-jl.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commons.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/customer/qfy/js/queryExtGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/customer/qfy/js/LenoveCombo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/customer/qfy/js/columns/extGridColumns_change.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/customer/qfy/js/columns/extGridColumns_<%=fileUrl%>.js"></script>
  </head>
<body topMargin="0px" leftMargin="0px">
	<div id="grid"></div>
	<!-- 展示翻页内容 -->
	<%if(((Map)list.get(0)).get("QryType") == null){%>	
	<div id="changePage" style="font-size:13px;padding:2px;margin:1px;">
	 <form action="" id="formDate" name="formDate" method="post">
	    <input type="hidden" id="itemData" name="itemData" value="">
	    <input type="hidden" id="jsonarr" name="jsonarr" value="">
	    <input type="hidden" id="titleRep" name="titleRep" value="">
	    <input type="hidden" id="checkBox" name="checkBox" value="">
		当前页<input type="text" id="currPage" name="currPage" readOnly class="text" size="2" value="1"/>&nbsp;
		<!-- <img src="<%=request.getContextPath()%>/img/nofirst.gif" title="第一页" alt="第一页" onclick="gofirstpage()" style="cursor: hand;height:16px;width:16px;"/>&nbsp;&nbsp; -->
		<img src="<%=request.getContextPath()%>/img/noprefix.gif" title="上一页" alt="上一页" onclick="goprevpage()" style="cursor: hand;height:16px;width:16px;"/>&nbsp;&nbsp;
		<img src="<%=request.getContextPath()%>/img/nopostfix.gif" title="下一页" alt="下一页" onclick="gonextpage()" style="cursor: hand;height:16px;width:16px;"/>&nbsp;&nbsp;
		<img src="<%=request.getContextPath()%>/img/excel.gif" title="数据另存为Excel" alt="数据另存为Excel" onclick="exportExcel()" style="cursor: pointer;height:16px;width:16px;"/>&nbsp;&nbsp;
	</form>
	</div>
	<%}%>
	<!-- 展示翻页内容结束 -->
	<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = "<%=request.getContextPath()%>/images/s.gif";
	var o = document.forms[0];
	var returnValue; //数据库交互返回结果参数
	var  fields = [] , columns = [], sumcol = [] ,sumvalue = [] ;  
	var gridwidth = document.body.clientWidth;
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
	var i = getExtGridId("<%=gridId%>");
	var columnsArr = json[i].columns;
//to execl begin
	var editVal = json[i].edit;
    var repName = json[i].name;
    var jschangeName = json[i].change;
    //to execl end
	var checkboxVal = json[i].checkbox;
	var editVal = json[i].edit;
	var aa = "<%=selecturl%>";
	//alert(aa);
	if(aa!="null" && aa!='' && aa!="undefined") {
		var myData = visitService(aa);
	}
	//获取字段变换展示列，存放到arr中
	var columnsChangeArr;
	if(json[i].change != undefined){
		columnsChangeArr = ( json[i].change).split(",");
	}

	var k=0;  //显示字段列数
	//循环JS配置文件，获取GRID抬头
	for(var j=0;j<columnsArr.length;j++){
		//判断列是否是checkbox，当是checkbox时
		if(columnsArr[j].property == "CHECKBOX"){
			fields[k] = {name: ""+columnsArr[j].property+""};
			columns[k] =sm;
			k++;
		}else{
			//当字段不是checkbox时
			fields[k] = {name: ""+columnsArr[j].property+""};
			columns[k] = {id:""+columnsArr[j].property+"",header:""+columnsArr[j].explain+"" ,sortable: true, dataIndex: ""+columnsArr[j].property+""};
			//控制列宽
			if(columnsArr[j].width == undefined){
				columns[k]["width"] = 80;
			}else{
				columns[k]["width"] = eval(columnsArr[j].width);
			}
			//控制是否显示或隐藏
			if(columnsArr[j].show == 1){
				columns[k]["hidden"] = false;
			}else{
				columns[k]["hidden"] = true;
			}
			//非联想查询弹框
			if(columnsArr[j].renderer != undefined){
	            columns[k]["renderer"] = eval(columnsArr[j].renderer);
	        }
			//控制编辑属性
			var editStr ="",parameter="";
			editStr = columnsArr[j].editor;
			if(editStr != undefined){
				if(editStr == 'combo') {
					var usage = new Ext.data.Store({
			               proxy: new Ext.data.MemoryProxy(myData),
			               reader: new Ext.data.JsonReader({
			                   root:"list"
			                   },
			                   [{name:"<%=hiddenid%>"},{name:"<%=hiddenname%>"}]
			               ),
			               toValue:function (v) {
			               		if(v) {
						        	var rec = usage.find('<%=hiddenid%>',v,0,true,true,false);
			                        if (rec > -1) {
			                            return usage.getAt(rec).get('<%=hiddenname%>');
			                        } else {
			                            return "请选择";
			                        }
				               	}
		                    }
			           });
			        usage.load();
			        /*store请求后台，前台未获取数据
					var usage = new Ext.data.Store({
			               proxy: new Ext.data.HttpProxy({
			                   url: '/QFY/showkhlx.action',
			                   method: 'POST',
			                   sync : false
			               }),
			               reader: new Ext.data.JsonReader({
			                   root:"list"
			                   },
			                   [{name:'KHLX01',mapping:'KHLX01'},{name:'KHLX02',mapping:'KHLX02'}]
			               )
			           });
			        usage.load();
			        */
					editStr=new Ext.form.ComboBox({
			               typeAhead: true,
			               hiddenName: "<%=hiddenid%>",
			               triggerAction: 'all',
			               mode: 'local',
		                  store: usage,
		                  displayField: "<%=hiddenname%>",
		                  valueField: "<%=hiddenid%>"
		            });
			        columns[k]["renderer"] = usage.toValue;
				} else {
					
					if(editStr.indexOf("#") > -1){
						parameter = editStr.substring(editStr.indexOf("#")+1,editStr.lastIndexOf("#"));
						//当编辑框属性是联想查询类型时
						if(parameter.split("@")[0] == "LenoveCombo"){
							editStr = "new Ext.form.SkyCombo({triggerAction: 'all', store: LenoveXX,displayField:'mc',typeAhead: false,pageSize:0,minChars:1,listWidth:200,";
							editStr = editStr + "tpl: new Ext.XTemplate('<tpl for=\".\"><div class=\"search-item\">','{mc}','</div></tpl>'),itemSelector: 'div.search-item',";
							editStr = editStr + "onTriggerClick : function(){if(typeof(parent.windowOpen)=='function')parent.windowOpen(grid.getSelectionModel().getSelectedCell()[0],'"+columnsArr[j].property+"',grid,event.keyCode);},";
							editStr = editStr + "onSelect: function(record){if(typeof(parent.setGridFieldValue)=='function')parent.setGridFieldValue(record.data,grid.getSelectionModel().getSelectedCell()[0],'"+columnsArr[j].property+"',grid);this.setValue(record.data.mc);this.collapse();}";
							if(parameter.split("@")[1] == "delRowsHandleKeyDown"){
								editStr = editStr + ",listeners:{render:function(c){c.getEl().on({keydown:function(e){delRowsHandleKeyDown(e)},scope:c});}}";
							}
							editStr = editStr + "})";
						}
						//联想编辑框结束
					}
				}
				columns[k]["editor"] = eval(editStr);
			}
			//增加列数
			k++;
			//判断列是否在change中存在，如果存在，增加一列；开始
			if(checkColumnExistChange(columnsArr[j].property)){
				fields[k] = {name: ""+columnsArr[j].property+"_NAME"};
				columns[k] = {id:""+columnsArr[j].property+"_NAME",header:""+columnsArr[j].explain+"" ,sortable: true, hidden:false,dataIndex: ""+columnsArr[j].property+"_NAME"};
				//控制列宽
				if(columnsArr[j].width == undefined){
					columns[k]["width"] = 80;
				}else{
					columns[k]["width"] = eval(columnsArr[j].width);
				}
				//增加列数
				k++;
			}
			//判断是否在change中存在结束
		}
	}

	var cm = new Ext.grid.ColumnModel(columns);
	var store = new Ext.data.SimpleStore({fields:fields});
    //visitService获取查询数据
    var url = '<%=request.getContextPath()%>/jlquery/select<%=DataBaseType%>.action';//?XmlData=<%=XmlData%>';

    //toexecl begin
    var paras = '<%=XmlData%>';
    var jsonarray = eval('('+paras+')'); 
    jsonarray[0]["QryType"] = "Bill";
    
    var downurl = '<%=request.getContextPath()%>/DownExcelAction/update.action?XmlData=<%=XmlData%>';
    XmlDataUrl = JSON.stringify(jsonarray);
    var noPageUrl = '<%=request.getContextPath()%>/jlquery/select<%=DataBaseType%>.action';
    document.getElementById("itemData").value=JSON.stringify(columnsArr);
    //to execl end
    visitServiceGrid(url,'<%=XmlData%>');
    var allData = returnValue.data;
	var data = [];
	var keyValue = "";
	var columnKeyValue = "";
	var changeName = "";
	var changeNameArr = "";
	//初始data数据
	initData();
	//var data = [['002003','官方','1'],['002003','拉开','0']];
	store.loadData(data);
	
	//grid属性开始，判断是否可编辑，是否有checkbox等
	var grid;
	if(editVal == "1"){
		grid = "grid = new Ext.grid.EditorGridPanel({store: store,";
	}else{
		grid = "grid = new Ext.grid.GridPanel({store: store,";
	}
	if(checkboxVal == "1"){
		grid = grid + "cm:cm,sm:sm,";
	}else{
		grid = grid + "columns:columns,";
	}
	grid = grid + "stripeRows: true,clicksToEdit: 1,height:document.body.clientHeight-55,width:gridwidth});";
    eval(grid);
    //grid属性结束
	
	grid.render('grid');
	setCtx("<%=request.getContextPath()%>");
	//根据唯一ID获取GRID列值
	 function getExtGridId(id){
	    var index = 0;
	    for(var i=0;i<json.length;i++){
		  if(json[i].id == id){
		    index = i;
			break;
		  }	  
	    }
		return index;
	  }

	  //判断所在列是否在change中存在
	  function checkColumnExistChange(columnName){
		  var flag = false;
		  if(columnsChangeArr !=undefined &&columnsChangeArr != "" && columnsChangeArr.length > 0){
			  for(var i=0;i<columnsChangeArr.length;i++){
				  if(columnsChangeArr[i] == columnName){
					  flag = true;
					  break;
				  }
			  }
		  }
		  return flag;
	  }

      //初始data数据
      function initData(){
    	  data.length = 0;
    	//根据数据和抬头匹配数据
    	for(var i=0;i<allData.length;i++){
    		for(var j=0;j<fields.length;j++){
    			//判断当前字段名在change中是否存在
    			changeNameArr = (fields[j].name).split("_");
    			if(checkColumnExistChange(changeNameArr[0])==true && changeNameArr[1]=="NAME"){
    				changeName = changeNameArr[0]+"_"+eval("allData[i]."+changeNameArr[0]);
    				keyValue = keyValue + eval("changeColumns."+changeName)+",";
    			}else{
    				//对值进行判断，当值为undefined时，直接赋空
    				columnKeyValue = eval("allData[i]."+fields[j].name);
    				if(columnKeyValue == undefined)columnKeyValue="";
    				keyValue = keyValue + columnKeyValue + "@@@";
    			}
    		}
    		data[i]=keyValue.split("@@@");
    		keyValue = "";
    	 }
       }
	
	  //跟数据库交互获取展示数据
	  function visitServiceGrid(val,xmldata){
		    $.ajax({
			    type: "POST",  //请求方式
			    async: false,   //true表示异步 false表示同步
			    url:encodeURI(encodeURI(val)),
			    data:{XmlData:xmldata},
			    success: function(data){
			      if (data != null){
			        try{
			          var json = jQuery.parseJSON(data);
			          returnValue = json;
			        }catch(e){
			          return;
			        }
			      }
			    },
		        //获取错误信息
		    	error:function(XMLHttpRequest, textStatus, errorThrown) {
			    	alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
		        }
			  });
		    return returnValue;
		}

		//控制翻页脚本
		function goPage(p){
			if((p!= getCurrPage()) && (p>=1)){
				var val="<%=request.getContextPath()%>/GridPageDataServlet?pages="+p+"&fileName="+returnValue.fileName;
				$.ajax({
			    	type: "POST",  //请求方式
			    	async: false,   //true表示异步 false表示同步
			    	url:encodeURI(val),
			    	data:{},
			   		success: function(jsonData){
			      	if (jsonData != null){
			        	try{
			        	document.getElementById("currPage").value = p;
				      	allData = jQuery.parseJSON(jsonData);
			          	initData();
			          	store.loadData(data);
			          	grid.render("grid");
			       	 }catch(e){
			       		document.getElementById("currPage").value = p-1;
			          	return;
			       	 }
			   	   }
			   	 },
		      	  //获取错误信息
		    		error:function(XMLHttpRequest, textStatus, errorThrown) {
			    		return;
			    		//alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
		       	 }
			  });
			}
		}
//Excel导出
		function exportExcel(){
		  var jsonData = visit(noPageUrl,XmlDataUrl);
		  if(jsonData==undefined || jsonData.length==0){
		    alert("无数据可导！");
		    return;
		  }
		  var jsonarrValue = JSON.stringify(jsonData);
		  var jsonarray = eval('('+jsonarrValue+')'); 
		  if(jschangeName!=undefined){
		  for(var i=0;i<jsonarray.length;i++){
		    var changeValueArr = [];
		    changeValueArr = jschangeName.split("@#$");
		    for(var j=0;j<changeValueArr.length;j++){
		      var addColName = changeValueArr[j] + "_"+jsonarray[i][changeValueArr[j]];
		      var addColVal = eval("changeColumns."+addColName);
		      jsonarray[i][addColName] = addColVal;
		    }
		  }
		 }
		  o.jsonarr.value=JSON.stringify(jsonarray);
		  o.titleRep.value=repName;
		  o.checkBox.value=checkboxVal==undefined?"0":checkboxVal;
		 // alert(downurl);
		  o.action=downurl;
		  o.submit();
		}
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/customer/qfy/js/extGrid2.js"></script>
  </body>
</html>