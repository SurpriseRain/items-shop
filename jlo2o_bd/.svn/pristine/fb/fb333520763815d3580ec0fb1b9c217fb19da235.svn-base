<%@page language="java" contentType="text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<!-- %@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"% -->
<!-- %@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%-->
<!--%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%-->
<!--%@taglib uri="/WEB-INF/cells-bean.tld" prefix="out"%-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>
<logic:notEmpty name="ResultMapName">
  <out:resour name="ResultMapName"/>
</logic:notEmpty>
</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ext-all.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.2.6.pack.js"></script>
<script src="<%=request.getContextPath()%>/js/ext-base.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/ext-all.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/ext-jl.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/ext-jsgl.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commons.js"></script>
<style type= "text/css" >
.x-selectable, .x-selectable * {
-moz-user-select: text! important;
-khtml-user-select: text! important; }
</style>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<base target="_self"/>
<body topMargin="0px" leftMargin="0px">
<form name="ActionForm" action="<bean:write name="location"/>.do" method="post">
<input type="hidden" id="totalCount" name="totalCount" value="<bean:write name="actionForm" property="totalCount"/>"/>
<input type="hidden" id="totalPage" name="totalPage" value="<bean:write name="actionForm" property="totalPage"/>"/>
<input type="hidden" id="currPage" name="currPage" value="<bean:write name="actionForm" property="currPage"/>"/>
<input type="hidden" id="sqlMap" name="sqlMap" value="<bean:write name="actionForm" property="sqlMap"/>"/>
<input type="hidden" id="ds" name="ds" value="<bean:write name="actionForm" property="ds"/>"/>
<input type="hidden" id="token" name="token" value="<bean:write name="actionForm" property="token"/>"/>
<input type="hidden" id="strategy" name="strategy" value="<logic:notEmpty name="strategy"><bean:write name="strategy"/></logic:notEmpty>"/>
</form>
<div id="content" style='width:100%;height: 100%;'>
  <div id="ExtGrid">  </div>
  <div id="rightClickCont">  </div>
</div>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/s.gif';
var contextPath = "<%=request.getContextPath()%>";

var data = [];var  fields = [] ;var columns = [] ; var sumvalue = [] ;var sumcol = [] ;
var menuaction = [] ;var  menuparas = [];var  menutarget = []; var openTargets = [];

//checkbox字段特有变量
var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
var cm = new Ext.grid.ColumnModel(columns);

var gridwidth = document.body.clientWidth,gridheight = document.body.clientHeight;
<logic:notEmpty name="columnList">
 <logic:iterate id="column" name="columnList" indexId="rowsIndex">

 //获取列字段(checkbox)
<logic:equal name="column" property="property" value="CHECKBOX">
fields[<bean:write name="rowsIndex"/>] = {name: '<bean:write name="column" property="property"/>'};
columns[<bean:write name="rowsIndex"/>] = sm;
</logic:equal>

//获取列字段(非checkbox)
<logic:notEqual name="column" property="property" value="CHECKBOX">
fields[<bean:write name="rowsIndex"/>] = {name: '<bean:write name="column" property="property"/>'};
columns[<bean:write name="rowsIndex"/>] = {header: '<logic:equal name="column" property="decode" value="0"><bean:write name="column" property="text"/></logic:equal><logic:notEqual name="column" property="decode" value="0"><out:resour name="column" key="text"/></logic:notEqual>', width: <bean:write name="column" property="width"/>, sortable: true <logic:notEmpty name="column" property="renderer">,renderer:<bean:write name="column" property="renderer"/></logic:notEmpty> , dataIndex: '<bean:write name="column" property="property"/>'<logic:equal name="column" property="type" value="money">, renderer:formatMoney</logic:equal><logic:equal name="column" property="show" value="0">,hidden:true</logic:equal>};
</logic:notEqual>

//获取列字段(合计字段)
<logic:equal name="column" property="sum" value="1">
sumcol[sumcol.length] = '<bean:write name="column" property="property"/>';
sumvalue[sumvalue.length] = 0;
</logic:equal>

</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="menuActions">
<logic:iterate id="item" name="menuActions" indexId="rowsIndex">
menuaction[<bean:write name="rowsIndex" />] = '<bean:write name="item" />';
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="menutargetFields">
<logic:iterate id="item" name="menutargetFields" indexId="rowsIndex">
menutarget[<bean:write name="rowsIndex" />] = <bean:write name="item" filter="false"/>;
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="menuParas">
<logic:iterate id="item" name="menuParas" indexId="rowsIndex">
menuparas[<bean:write name="rowsIndex" />] = [<logic:iterate id="p" name="item">'<bean:write name="p" filter="false"/>', </logic:iterate>''];
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="openTargets">
<logic:iterate id="item" name="openTargets" indexId="rowsIndex">
openTargets[<bean:write name="rowsIndex" />] = '<bean:write name="item" filter="false"/>';
</logic:iterate>
</logic:notEmpty>


function vp(p){ return ((!!p)&&(/\w/.test(p.replace(/\w*=/, '')))); }
function mp(s, rowIndex,grid){
  var record = grid.getStore().getAt(rowIndex);
   s = s.substring(0,s.length-1);
return (record.data[s] == undefined) ? null:record.data[s];
}

function getTargetField(i,key){	return (menutarget[i][key] == undefined)? key.toLowerCase(): menutarget[i][key] ;}

function checkout(){
  var tmp= ActionForm.action;
  //ActionForm.action="<%=request.getContextPath()%>/downLoadAction.do";
  ActionForm.action="<%=request.getContextPath()%>/downLoadExcelAction.do";
  ActionForm.submit();
  ActionForm.action=tmp;
  document.getElementById("save").disabled=true;
}

function openMenu(grid,rowindex,menuindex,menuName,openTarget){
  var url = contextPath+menuaction[menuindex] + "?s=" + Math.random(), tmp="";
  var arr = menuparas[menuindex];
  for(var i=0; i<arr.length-1; i++){
    tmp = arr[i];
    if(vp(tmp)){
      url += "&" + tmp;
    } else{
      tmp = tmp.toLowerCase();
      url += "&" +  getTargetField(menuindex,tmp.substring(0,tmp.length-1)) +"=" + mp(arr[i], rowindex,grid);
    }
  }
  if(openTarget=="_blank")
    window.top.fOpenTab(encodeURI(encodeURI(url)),menuName);
  else
    window.open(encodeURI(encodeURI(url)), openTarget, "height=540,width=780,status=yes,toolbar=no,location=no,scrollbars=yes,resizable=yes");
}

function GridSum(grid){
  grid.store.each(function(record){
    for(var i=0;i<sumcol.length;i++){
      sumvalue[i] += Number(record.data[sumcol[i]]);
    }
  });

  var n = grid.getStore().getCount();// 获得总行数
  var jsonr = {};
  for(var i=0;i<sumcol.length;i++){
    jsonr[sumcol[i]] = sumvalue[i];
  }
  var p = new Ext.data.Record(jsonr);
  var firstfield = grid.getColumnModel().getDataIndex(0);
  p.data[firstfield] = (p.data[firstfield] == undefined) ? "页计":"页计:"+p.data[firstfield];
  grid.store.insert(n, p);// 插入到最后一行
  grid.getView().getRow(n).style.backgroundColor="#F0F8FF";
}

<logic:notEmpty name="pageList">
<logic:iterate id="pagedata" name="pageList" indexId="rowsIndex">
data[<bean:write name="rowsIndex"/>] = [<logic:iterate id="column" name="columnList"> <logic:equal name="column" property="property" value="CHECKBOX">'',</logic:equal><logic:notEqual name="column" property="property" value="CHECKBOX">'<out:write name="pagedata" collection="column" key="property"/>',</logic:notEqual></logic:iterate>''];
</logic:iterate>
</logic:notEmpty>

function getJsonLength(jsonData){
  var jsonLength = 0;
  for(var item in jsonData){
    jsonLength++;
  }
  return jsonLength;
}


//new Ext.Toolbar.TextItem('<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.prefix"/>" name="prev" title="<bean:message key="page.prevpage"/>" alt="<bean:message key="page.prevpage"/>"  onclick="goprevpage()"  style="cursor: hand;height:16px;width:16px;"/>&nbsp;&nbsp;');
 //Ext.onReady(function(){
   Ext.QuickTips.init();
    Ext.state.Manager.setProvider(new Ext.state.DataBaseProvider());

    var store = new Ext.data.SimpleStore({fields:fields});

     var linkexcel1 = new Ext.Toolbar.TextItem('<bean:message key="page.totalcount"/><input type="text" size="2" readOnly class="text" value="<bean:write name="actionForm" property="totalCount"/>"/>');
     var linkexcel2 = new Ext.Toolbar.TextItem('<bean:message key="page.totalpage"/><input type="text"  size="2" readOnly class="text" value="<bean:write name="actionForm" property="totalPage"/>"/>');
     var linkexcel3 = new Ext.Toolbar.TextItem('<bean:message key="page.currpage"/><input type="text" size="2" readonly class="text" value="<bean:write name="actionForm" property="currPage"/>"/>');

     var linkexcel4 = new Ext.Toolbar.Button({
     text:'<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.extfirst"/>" name="first" title="<bean:message key="page.firstpage"/>" alt="<bean:message key="page.firstpage"/>"  style="cursor: hand;height:16px;width:16px;"/>',
     handler:function(){gofirstpage();}}) ;

     var linkexcel5 = new Ext.Toolbar.Button({
     text:'<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.extprefix"/>" name="prev" title="<bean:message key="page.prevpage"/>" alt="<bean:message key="page.prevpage"/>"  style="cursor: hand;height:16px;width:16px;"/>',
     handler:function(){goprevpage();}}) ;

     var linkexcel6 = new Ext.Toolbar.Button({
     text:'<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.extpostfix"/>" name="next1" title="<bean:message key="page.nextpage"/>" alt="<bean:message key="page.nextpage"/>" style="cursor: hand;height:16px;width:16px;"/>',
     handler:function(){gonextpage();}}) ;

     var linkexcel7 = new Ext.Toolbar.Button({
     text:'<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.extlast"/>" name="last" title="<bean:message key="page.lastpage"/>" alt="<bean:message key="page.lastpage"/>" style="cursor: hand;height:16px;width:16px;"/>',
     handler:function(){golastpage();}}) ;

     var linkexcel8 = new Ext.Toolbar.Button({
     text:'<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.formula"/>" id="formula" name="formula" title="<bean:message key="page.formula"/>"  alt="<bean:message key="page.formula"/>" style="cursor: hand;height:16px;width:16px;"/>',
     handler:function(){openFormula();}}) ;

     var linkexcel9 = new Ext.Toolbar.TextItem('<img src="<%=request.getContextPath()%>/img/<bean:message key="button.img.word"/>" id="save" name="save" title="<bean:message key="page.worddata"/>"  alt="<bean:message key="page.worddata"/>"  onclick="automateWord()"   style="cursor: hand;height:16px;width:16px;"/>');

     var button1 =
     new Ext.Toolbar.Button({
       text:'<img src="<%=request.getContextPath()%>/img/extexcel.gif" id="save" name="save" title="<bean:message key="page.savedata"/>"  alt="<bean:message key="page.savedata"/>"  style="cursor: hand;height:16px;width:16px;"/>',
       handler:function(){
         if(<bean:write name="actionForm" property="totalCount"/>>0){
           checkout();
         }else{

        try{
          var oXL = new ActiveXObject("Excel.Application");
          var oWB = oXL.Workbooks.Add();
          var oSheet = oWB.ActiveSheet;
          var cm = grid.getColumnModel();
          var col_arr = [];
          for(i=0;i<cm.getColumnCount();i++){
            if(cm.isHidden(i) != true){
              col_arr.push(i);
            }
          }

          for(i=1;i<=col_arr.length;i++){
            oSheet.Cells(1,i).Value = cm.getColumnHeader(col_arr[i - 1]);
          }
          var store = grid.getStore();
          var view = grid.getView();
          for(i=1;i<=store.getCount();i++){
            for(j=1;j<=col_arr.length;j++){
              oSheet.Cells(i + 1,j).Value = view.getCell(i - 1,col_arr[j - 1]).innerText;
            }
          }
          oSheet.Columns.AutoFit;
          oXL.Visible = true;
          oXL.UserControl = true;
        } catch(e){
          alert('总记录数为0,不能导出!');
        }}
     }
     }) ;

    store.loadData(data);

    // create the Grid       title:'<logic:notEmpty name="ResultMapName"><out:resour name="ResultMapName"/></logic:notEmpty>',
    <logic:notEmpty name="resultMap">
    <logic:notEqual name="resultMap" property="checkbox" value="1">
    var grid = new Ext.grid.GridPanel({
       id:'jlgrid',
      store: store,
      columns:columns,
      stripeRows: true,
      height:gridheight,
      width:gridwidth,
      bbar: [linkexcel1,linkexcel2,linkexcel3,linkexcel4,linkexcel5,linkexcel6,linkexcel7,button1],
      stateful:true,
      stateId:'cookie<bean:write name="actionForm" property="gridPkId"/>'
    });
    </logic:notEqual>
    <logic:equal name="resultMap" property="checkbox" value="1">
    var grid = new Ext.grid.GridPanel({
        id:'jlgrid',
       store: store,
       cm:cm,
       sm:new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}),
       stripeRows: true,
       height:gridheight,
       width:gridwidth,
       bbar: [linkexcel1,linkexcel2,linkexcel3,linkexcel4,linkexcel5,linkexcel6,linkexcel7,button1],
       stateful:true,
       stateId:'cookie<bean:write name="actionForm" property="gridPkId"/>'
     });
    </logic:equal>
    </logic:notEmpty>
    
    grid.render('ExtGrid');
    //计算页计
    if(sumcol.length >0){
      GridSum(grid);
    }
    var hj_json = {};
    <logic:notEmpty name="actionForm" property="amount">
    <logic:iterate id="item" name="actionForm" property="amount">
    <logic:iterate id="mm" name="item">
    hj_json['<bean:write name="mm" property="key"/>']  = '<bean:write name="mm" property="value"/>';
    </logic:iterate>
    </logic:iterate>
    </logic:notEmpty>
    // 总合计
    if(getJsonLength(hj_json) > 0){
      var n = grid.getStore().getCount();
      var p = new Ext.data.Record(hj_json);
      var firstfield = grid.getColumnModel().getDataIndex(0);
      p.data[firstfield] = (p.data[firstfield] == undefined) ? "总计":"总计 :"+p.data[firstfield];
      grid.store.insert(n, p);
      grid.getView().getRow(n).style.backgroundColor="#D3E1F1";
      //grid.getView().addRowClass(r,css);
    }

    grid.addListener('rowcontextmenu', rightClickFn);
    function rightClickFn(grid,rowindex,e){
      e.preventDefault();
      var rightClick = new Ext.menu.Menu({id:'rightClickCont', items: [
      <logic:notEmpty name="menuNames">
      <logic:iterate id="item" name="menuNames" indexId="rowsIndex">
      <logic:greaterThan value="0" name="rowsIndex">,</logic:greaterThan>
      {id:'menuitem<bean:write name="rowsIndex" />',text:'<out:resour name="item" bundle="bundle"/>',handler:function () {openMenu(grid,rowindex,<bean:write name="rowsIndex"/>,'<out:resour name="item" bundle="bundle"/>',openTargets[<bean:write name="rowsIndex"/>]);}}
      </logic:iterate>
      </logic:notEmpty>
      ]});
      rightClick.showAt(e.getXY());
    }

    <logic:equal value="true" name="actionForm" property="openFirstMenu">
      <logic:notEmpty name="menuNames">
        <logic:iterate id="item" name="menuNames" indexId="rowsIndex" length="1">
          openMenu(grid,0,0,'<bean:write name="item"/>',openTargets[0]);
        </logic:iterate>
      </logic:notEmpty>
    </logic:equal>

    Ext.EventManager.onWindowResize(function(){
      grid.setWidth(0);
      grid.setHeight(0);
      grid.setWidth(Ext.get("content").getWidth());
      grid.setHeight(Ext.get("content").getHeight());
    })

  //});

function modifydoc(url,target){
  var jlgrid = Ext.getCmp("jlgrid");
  var row = jlgrid.getSelectionModel().getSelections();
  var data_json = null;
  if(row.length==0){
    data_json = jlgrid.store.getAt(0).data;
  }else{
    data_json = row[0].data;
  }
  window.open(url+"?gsxx01="+data_json["gsxx01"]+"&jlbh="+data_json["gcxx01"], target);
}
if (!Ext.grid.GridView.prototype.templates) {
    Ext.grid.GridView.prototype.templates = {};
  }
  Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
  '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,
  '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,
  '</td>'
  );

function getCurrPage(){
  return parseInt(document.getElementById("currPage").value);
}

function getTotalPage(){
  return parseInt(document.getElementById("totalPage").value);
}

function goPage(i){
  if((i!= getCurrPage()) && (i<=getTotalPage()) && (i>=1)){
    document.getElementById('currPage').value = i;
    document.forms[0].submit();
  }
}

function gofirstpage(){
  goPage(1);
}

function goprevpage(){
  goPage(getCurrPage()-1);
}

function gonextpage(){
  goPage(getCurrPage()+1);
}

function golastpage(){
  goPage(getTotalPage());
}
</script>
</body>
</html>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/extGrid.js'></script>
