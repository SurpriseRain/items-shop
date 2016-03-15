<%@ page contentType="text/html; charset=UTF-8" %>

<link type="text/css" rel="StyleSheet" href="/customer/qfy/css/motai.css"/>
<link type="text/css" rel="StyleSheet" href="/customer/qfy/css/readExcel.css"/>
<!--展示选择层开始-->
<div id="div_choose" style="position:absolute;Z-index:9999;display:none;">
  <div style="background-color:#FFFFFF;position:absolute;border: 1px solid #7F99B2;width:316px;"><!--360px-->
    <table id="choose" cellpadding="0" cellspacing="0" width="100%" border="0" style="padding:0px;">
      <tr>
       <!--<td align="left" onmousedown="start(event,'div_choose')" onmouseup="stop(event,'div_choose')" onmousemove="move(event,'div_choose')" style="FONT-SIZE: 13px;border:0px;background-color: #E8F2FF;cursor:hand;">&nbsp;&nbsp;请选择</td>-->
       <td align="left" style="FONT-SIZE: 13px;border:0px;background-color: #E8F2FF;cursor:pointer;">&nbsp;&nbsp;请选择</td>
       <td align="right" style="border:0px;background-color: #E8F2FF;">
         <b style="cursor:hand;color:#ffff" onclick="closeChoose()">×&nbsp;</b>
       </td>
      </tr>
      <tr>
        <td style="border-top:1px solid #7F99B2;vertical-align:top;" align="center" colspan="2">
          <iframe id="chooseIframe" name="chooseIframe" src="" style="height:206px;width:316px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
        </td>
      </tr>
    </table>
  </div>
</div>
<!--展示选择层结束-->
<!--弹出窗口展示层开始，放置资产查询页面等-->
<div id="div_windowOpen" style="position:absolute;top:50%;left:50%;height:420px;margin-top:-220px;width:560px;margin-left:-320px;Z-index:9999;display:none;">
  <div style="background-color:#FFFFFF;position:absolute;border: 1px solid #7F99B2;width:560px;">
    <table cellpadding="0" cellspacing="0" width="100%" border="0" style="padding:0px;">
      <tr>
        <td align="left" style="FONT-SIZE: 13px;border:0px;background-color: #E8F2FF;cursor:hand;">&nbsp;&nbsp;查询信息</td>
        <td align="right" style="border:0px;background-color: #E8F2FF;">
          <b style="cursor:hand;color:#ffff" onclick="closeWindowOpen()">×&nbsp;</b>
        </td>
      </tr>
      <tr>
        <td style="border-top:1px solid #7F99B2;vertical-align:top;" align="center" colspan="2">
          <iframe id="windowOpen" name="windowOpen" style="height:420px;width:560px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
        </td>
      </tr>
    </table>
  </div>
</div>
<!--弹出窗口展示层结束-->
<!--导入EXCEL开始-->
<div id="div_readExcel_Bg" style="background:#666;display:none"></div>
<table id="processingTable" style="display:none;border-spacing:0;border-collapse:collapse;border:0;width:100%;height:500px;margin: 0 auto;padding:0;text-align:center;position:absolute;top:0;left:0;z-index:11;">
  <tr>
    <td style="text-align:center;vertical-align: middle;padding:0;">
      <div>
        <img alt="正在处理,请稍候..." title="正在处理,请稍候..." src="<%=request.getContextPath()%>/img/processing.gif" />
          <br />
          正在处理,请稍候...
      </div>
    </td>
  </tr>
</table>

<table id="circlingTable" style="filter:alpha(opacity=80);-moz-opacity:0.6;opacity:0.7;display:none;border-spacing:0;border-collapse:collapse;border:0;zoom:1;width:180px;height:40px;margin: 0 auto;padding:-20;text-align:center;position:fixed!important;position:absolute;top:40%;left:40%;z-index:11;">
  <tr>
    <td style="border:0px solid #8CBEDA;color:#37a;font-size:12px;font-weight:bold;">
      <div style="width:180px;height:40px;line-height:40px;border:2px solid #D6E7F2;background:#fff;">
        <img style="margin:5px 10px;float:left;display:inline;" alt="" src="<%=request.getContextPath()%>/img/loading2.gif" />正在查询,请稍候...
      </div>
    </td>
  </tr>
</table>

<table id="loginTable" style="filter:alpha(opacity=80);-moz-opacity:0.6;opacity:0.7;display:none;border-spacing:0;border-collapse:collapse;border:0px;zoom:1;width:175px;height:40px;margin: 0 auto;padding:-20;text-align:center;position:fixed!important;position:absolute;top:50%;left:45%;z-index:11;">
  <tr>
    <td style="border:0px solid #8CBEDA;color:#37a;font-size:12px;font-weight:bold;">
      <div style="width:175px;height:40px;line-height:40px;border:2px solid #D6E7F2;background:#fff;">
        <img style="margin:5px 10px;float:left;display:inline;" alt="" src="<%=request.getContextPath()%>/img/loading2.gif" />正在登录,请稍候...
      </div>
    </td>
  </tr>
</table>

<table id="readExcelStyle" style="display:none">
  <tr>
    <td>
      <div class="mwindow" style="padding-bottom:0px">
        <div class="mwindowstitle" id="mt_win_title" style="padding:2px 0 2px 2px;">
          <table width="100%" style="border:0px;">
            <tr>
              <td width="90%" align="left" style="border:0px;text-align:left;">导入EXCEL</td>
              <td width="10%" style="border:0px;cursor:pointer;" onclick="closeReadExcel()">×&nbsp;</td>
            </tr>
          </table>
        </div>
        <div class="mwindowsmsg" id="mt_win_msg" style="padding:0px 0 0px 0px;">
          <table width="100%">
            <tr>
              <td width="30%" style="text-align:right;padding-right:2px;height:30px;">导入EXCEL</td>
              <td width="70%" style="text-align:left;padding-left:2px;height:30px;">
                <input type="file" id="FileUpload" name="FileUpload" value=""/>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <img style="cursor:hand;" alt="保存" src="<%=request.getContextPath()%>/img/save.gif" onclick="importExcel()"/>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </td>
  </tr>
</table>

<table id="blankTable" style="filter:alpha(opacity=50);-moz-opacity:0.5;opacity:0.5;display:none;border-spacing:0;border-collapse:collapse;border:0;width:100%;height:600px;margin: 0 auto;padding:-20;text-align:center;position:absolute;top:0;left:0;z-index:11;">
  <tr>
    <td style="text-align:center;vertical-align: middle;padding:0;">
      <div style="border:solid 2px #86A5AD;width:200px">
        <img alt="请选择..." title="请选择..." src="<%=request.getContextPath()%>/img/blank2.gif" />
      </div>
    </td>
  </tr>
</table>

<!--导入EXCEL结束-->

<script type="text/javascript">
//对选择层进行控制
var toptree = null;
var s_toptree = null;
var topWindow = null;
var callBackFunc = null;

//判断浏览器类型
var userAgent = navigator.userAgent.toLowerCase();
var isSafari = userAgent.indexOf("Safari")>=0;
var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
var ua_match = /(trident)(?:.*rv:([\w.]+))?/.exec(userAgent) || /(msie) ([\w.]+)/.exec(userAgent);
var is_ie = ua_match && (ua_match[1] == 'trident' || ua_match[1] == 'msie') ? true : false;

function controlTrasiteDepartment(url,left,top){
  var div_Tr = document.getElementById("div_choose");
  if(div_Tr.style.display == "none"){
    div_Tr.style.display = "block";
    document.all.chooseIframe.src = url;
    document.getElementById("div_choose").style.left = left;
    document.getElementById("div_choose").style.top = top;
  }else{
    div_Tr.style.display = "none";
  }
}

//parameter:查询SQL的ID;getMethodName:获取返回值方法;backStr:查询条件;cookieId当parameter不能确定唯一时传cookieId
function srchValueByLetter(p,parameter,getMethodName,backStr,cookieId){
  if(p.value == ""){
    departmentId = p.id;
    toptree = document.getElementById(departmentId);
    callBackFunc = getMethodName;
    var url = rootPath+"/ListTables.jsp?parameter="+parameter+"&backStr="+backStr+"&cookieId="+cookieId;
    var divLeft = getLeftPos(document.getElementById(departmentId));
    var divTop = getTopPos(document.getElementById(departmentId))+22;
    controlTrasiteDepartment(url,divLeft,divTop);
  }
}

//查询单点数型数据 id:字段ID；getMethodName:获取返回值方法；backStr:查询条件；treeCxjb查询级别
function srchValueForSingleTree(id,getMethodName,backStr,treeCxjb){
  departmentId = id;
  toptree = document.getElementById(id);
  callBackFunc = getMethodName;
  backStr = (backStr == undefined)?"":backStr;
  var url = rootPath+"/Tree.jsp?sqlID="+id+"&backStr="+backStr+"&treeCxjb="+treeCxjb;
  var divLeft = getLeftPos(document.getElementById(departmentId))+1;
  var divTop = getTopPos(document.getElementById(departmentId))+22;
  //控制弹出框的位置，当超出屏幕大小时，位于text的上面而不是下面 edit by luffy.tian 20140102
  if(divTop+206>$(document).height()){
	  divTop = getTopPos(document.getElementById(departmentId))-206-22;
  }
  controlTrasiteDepartment(encodeURI(encodeURI(url)),divLeft,divTop);
}

function srchValueForSingleTreeNoClose(id,getMethodName,backStr,treeCxjb){
  departmentId = id;
  toptree = document.getElementById(id);
  callBackFunc = getMethodName;
  backStr = (backStr == undefined)?"":backStr;
  var url = rootPath+"/singleViewSelectTree.jsp?sqlID="+id+"&backStr="+backStr+"&treeCxjb="+treeCxjb;
  var divLeft = getLeftPos(document.getElementById(departmentId))+1;
  var divTop = getTopPos(document.getElementById(departmentId))+22;
  controlTrasiteDepartment(encodeURI(encodeURI(url)),divLeft,divTop);
}

//查询单点数型数据 id:字段ID；getMethodName:获取返回值方法；backStr:查询条件；treeCxjb查询级别
function srchValueForCheckboxTree(id,getMethodName,backStr,treeCxjb){
  departmentId = id;
  toptree = document.getElementById(id);
  callBackFunc = getMethodName;
  backStr = (backStr == undefined)?"":backStr;
  var url = rootPath+"/TreeCheckbox.jsp?sqlID="+id+"&backStr="+backStr+"&treeCxjb="+treeCxjb;
  var divLeft = getLeftPos(document.getElementById(departmentId))- 50;
  var divTop = getTopPos(document.getElementById(departmentId))+22;
  controlTrasiteDepartment(encodeURI(encodeURI(url)),divLeft,divTop);
}

//关闭部门选择窗口
function closeChoose(){
  var div_Tr = document.getElementById("div_choose");
  div_Tr.style.display = "none";
}

//弹出窗口 parentObj父页面窗口对象
var parentWindow = null;
var parentWindowFunc = null;
var parentWindowFunc1 = null;
function controlWindowOpen(url){
  var div_Tr = document.getElementById("div_windowOpen");
  if(div_Tr.style.display == "none"){
    var loc_y=loc_x=200;
    var width=640,height=480;
    if(is_ie){
      window.showModalDialog(url,window,"edge:raised;scroll:0;status:0;help:0;resizable:1;dialogWidth:"+width+"px;dialogHeight:"+height+"px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px",true);
    }else{
      window.open(url,parent,"height="+height+",width="+width+",status=0,toolbar=no,menubar=no,location=no,scrollbars=no,top="+loc_y+",left="+loc_x+",resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no",true);
    }
  }else{
    div_Tr.style.display = "none";
  }
}

//关闭弹出窗口层
function closeWindowOpen(){
  var div_Tr = document.getElementById("div_windowOpen");
  div_Tr.style.display = "none";
}

//对层进行拖动
var _x,_y;
var _move = false;
function start(evt,moveID){
  evt = evt ? evt : (window.event ? window.event : null);
  var which = navigator.userAgent.indexOf('MSIE') > 1 ? event.button == 1 ? 1 : 0 : event.which == 1 ? 1 : 0 ;
  if(which) {
    _move = true;
    _x = evt.clientX - parseInt(document.getElementById(moveID).style.left);
    _y = evt.clientY - parseInt(document.getElementById(moveID).style.top);
  }
}

//拖动
function move(evt,moveID){
  evt = evt ? event : (window.event ? window.event : null);
  if(_move) {
    var x = evt.clientX - _x;
    var y = evt.clientY - _y;
    document.getElementById(moveID).style.left = x+'px';
    document.getElementById(moveID).style.top = y+'px';
  }
}

//拖动停止
function stop(){
  _move = false;
}

//获取元素的纵坐标
function getTopPos(e){
  var offset=e.offsetTop;
  if(e.offsetParent!=null) offset+=getTopPos(e.offsetParent);
  return offset;
}

//获取元素的横坐标
function getLeftPos(e){
  var offset=e.offsetLeft;
  if(e.offsetParent!=null) offset+=getLeftPos(e.offsetParent);
  return offset;
}

//显示导航条
function showProcessingTable(){
  document.getElementById("div_readExcel_Bg").style.display = "";
  document.getElementById("processingTable").style.display = "";
}

//显示半透明的背景
function showBlankTable(){
  document.getElementById("div_readExcel_Bg").style.display = "block";
  //document.getElementById("blankTable").style.display = "block";
}

//隐藏半透明的背景
function closeBlankTable(){
  document.getElementById("div_readExcel_Bg").style.display = "none";
  //document.getElementById("blankTable").style.display = "none";
}

//显示login
function showloginTable(){
  document.getElementById("loginTable").style.display = "";
}

//显示
function showCirclingTable(){
  document.getElementById("div_readExcel_Bg").style.display = "";
  document.getElementById("circlingTable").style.display = "";
}

function closeCirclingTable(){
  document.getElementById("div_readExcel_Bg").style.display = "none";
  document.getElementById("circlingTable").style.display = "none";
}

//显示导入EXCEL内容
function showReadExcel(){
  document.getElementById("div_readExcel_Bg").style.display = "block";
  document.getElementById("readExcelStyle").style.display = "block";
}

//隐藏导入EXCEL内容
function closeReadExcel(){
  document.getElementById("processingTable").style.display = "none";
  document.getElementById("div_readExcel_Bg").style.display = "none";
  document.getElementById("readExcelStyle").style.display = "none";
}

function reverse(val)
{
  var str="";
  var a=val.split('');
  var result=new Array();
  while(a.length)
  {
    result.push(a.pop());
}
  str=result.join('');
  return str;
}

//读取导入EXCEL
function importExcel(){
  var excelApp;
  var excelWorkBook;
  var excelSheet;
  var file = document.getElementById("FileUpload").value;
  if (file == "") {
    alert("请选择导入EXCEL文件");
    return false;
  }else{
    var stuff = reverse(file).split(".")[0];
    if (stuff != "slx" && stuff != "xslx"){
      alert("请选择正确的EXCEL文件导入");
      return false;
    }
    else {
      try{
        //开始读取EXCEL
        excelApp = new ActiveXObject("Excel.Application");
        excelWorkBook = excelApp.Workbooks.open(file);
        excelSheet = excelWorkBook.ActiveSheet;
        readLocaleExcel(excelWorkBook,excelSheet,file);
        //关闭EXCEL
        excelSheet=null;
        excelWorkBook.close();
        excelApp.Application.Quit();
        excelApp=null;
      }catch(e){
        //关闭EXCEL
        if(excelSheet !=null || excelSheet!=undefined){
          excelSheet =null;
        }
        if(excelWorkBook != null || excelWorkBook!=undefined){
          excelWorkBook.close();
        }
        if(excelApp != null || excelApp!=undefined){
          excelApp.Application.Quit();
          excelApp=null;
        }
        alert("IE工具->Internet选项->安全->自定义级别->启用所有ActiveX控制和插件和启动其它选项中文件上传到服务器时包含本地目录文件!");
        return false;
      }
    }
  }
}

drag("div_choose");
//实现div拖动addd
function drag(o,s)
{
  if (typeof o == "string") o = document.getElementById(o);
  o.orig_x = parseInt(o.style.left) - document.body.scrollLeft;
  o.orig_y = parseInt(o.style.top) - document.body.scrollTop;
  o.orig_index = o.style.zIndex;

  o.onmousedown = function(a)
  {
    this.style.cursor = "move";
    this.style.zIndex = 10000;
    var d=document;
    if(!a)a=window.event;
    var x = a.clientX+d.body.scrollLeft-o.offsetLeft;
    var y = a.clientY+d.body.scrollTop-o.offsetTop;
    d.ondragstart = "return false;"
    d.onselectstart = "return false;"
    d.onselect = "document.selection.empty();"

    if(o.setCapture)
    o.setCapture();
    else if(window.captureEvents)
    window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);

    d.onmousemove = function(a)
    {
      if(!a)a=window.event;
      o.style.left = a.clientX+document.body.scrollLeft-x;
      o.style.top = a.clientY+document.body.scrollTop-y;
      o.orig_x = parseInt(o.style.left) - document.body.scrollLeft;
      o.orig_y = parseInt(o.style.top) - document.body.scrollTop;
    }

    d.onmouseup = function()
    {
      if(o.releaseCapture)
      o.releaseCapture();
      else if(window.captureEvents)
      window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
      d.onmousemove = null;
      d.onmouseup = null;
      d.ondragstart = null;
      d.onselectstart = null;
      d.onselect = null;
      o.style.cursor = "normal";
      o.style.zIndex = o.orig_index;
    }
  }

  if (s)
  {
    var orig_scroll = window.onscroll?window.onscroll:function (){};
    window.onscroll = function ()
    {
      orig_scroll();
      o.style.left = o.orig_x + document.body.scrollLeft;
      o.style.top = o.orig_y + document.body.scrollTop;
    }
  }
}
</script>
