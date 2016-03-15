var ContextPath;
function setCtx(ctx){
   this.ContextPath = ctx;
}

if (!Ext.grid.GridView.prototype.templates) {
    Ext.grid.GridView.prototype.templates = {};
  }
  Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
  '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,
  '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,
  '</td>'
  );

if(Ext.grid.GridView){
   Ext.apply(Ext.grid.GridView.prototype, {
      sortAscText  : "正序",
      sortDescText : "逆序",
      lockText     : "锁列",
      unlockText   : "解锁列",
      columnsText  : "列"
   });
}

//点击图片弹出新窗口
/**function windowFillBack(val){
   val = (val=="")?"&nbsp;":val;
   return '<div style="POSITION:relative;">'+val+'<div style="POSITION: absolute;right:0px;top:0px;cursor:hand;"><img src="/img/openWindow.gif" onclick="windowOpen('+arguments[3]+','+arguments[4]+')"/></div></div>';
}

function windowOpen(rowNum,cellNum){
  if(typeof(parent.windowOpen)=="function"){
     var cellName = grid.getColumnModel().getColumnHeader(cellNum);
     parent.windowOpen(rowNum,cellName);
  }
}*/

//点击图片弹出新窗口
//function gridWindowOpen(val){
//   val = (val=="" || val==null)?"&nbsp;":val;
//   return '<div style="POSITION:relative;">'+val+'</div><div style="POSITION: absolute;height:25px;right:0px;top:5px;cursor:hand;"  onclick="windowOpen('+arguments[3]+','+arguments[4]+')"><img src="'+ContextPath+'/img/openWindow.gif"/></div>';
//}
function gridWindowOpen(val){
  val = (val=="" || val==null)?"&nbsp;":val;
  return '<div style="position:static!important;POSITION:relative;float:left;">'+val+'</div><div style="position:static!important;POSITION: absolute;height:5px!important;height:25px;right:0px;top:5px;cursor:pointer;float:right;"  onclick="windowOpen('+arguments[3]+','+arguments[4]+')"><img src="/img/openWindow.gif"/></div>';
}

function windowOpen(rowNum,cellNum){
  if(typeof(parent.windowOpen)=="function"){
     var cellName = grid.getColumnModel().getColumnHeader(cellNum);
     parent.windowOpen(rowNum,cellName,grid);
  }
}

function openMenu(grid,rowindex,menuindex,menuName,openTarget){
  var url = ContextPath+menuaction[menuindex] + "?s=" + Math.random(), tmp="";
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

//时间格式
function formatMoney(value){
  if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(value)){
    return '￥0.00';
  }
  var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
  var re = new RegExp().compile("(\\d)(\\d{3})(,|$)");
  while(re.test(b))
  b = b.replace(re, "$1,$2$3");
  return ('￥' + a + '' + b + '' + c);
}

function jldateRenderer(value){
  if(value instanceof Date || value.toString().indexOf("UTC")>0){
    return new Date(value).format("Y-m-d");
  }else{
    return value;
  }
}

function dbdecode(A){var J=/^(a|n|d|b|s|o)\:(.*)$/;var C=J.exec(unescape(A));if(!C||!C[1]){return }var F=C[1];var H=C[2];switch(F){case"n":return parseFloat(H);case"d":return new Date(Date.parse(H));case"b":return(H=="1");case"a":var G=[];var I=H.split("^");for(var B=0,D=I.length;B<D;B++){G.push(dbdecode(I[B]))}return G;case"o":var G={};var I=H.split("^");for(var B=0,D=I.length;B<D;B++){var E=I[B].split("=");G[E[0]]=dbdecode(E[1])}return G;default:return H}}
Ext.state.DataBaseProvider=function(A){
Ext.state.DataBaseProvider.superclass.constructor.call(this);
this.path="/";
this.expires=new Date(new Date().getTime()+(1000*60*60*24*7));
this.domain=null;
this.secure=false;
Ext.apply(this,A);
this.state=this.readCookies()
};
Ext.extend(Ext.state.DataBaseProvider,Ext.state.Provider,
{set:function(A,B){
if(typeof B=="undefined"||B===null){
this.clear(A);
return }
this.setCookie(A,B);
Ext.state.DataBaseProvider.superclass.set.call(this,A,B)
},
clear:function(A){
  this.clearCookie(A);
Ext.state.DataBaseProvider.superclass.clear.call(this,A)},
readCookies:function(){var C={};
$.ajax({
      type: "POST",
      url: "/workflow/gridProperty.jspa?ParamID=queryProperty",
      data: {sid:Math.random()}, dataType:"xml",async: false ,
      success: function(data){if (data != null){var arrs=data.selectNodes("/response/grid");
        for(var i=0;i<arrs.length;i++){
            C[arrs[i].selectSingleNode("gridid").text] = dbdecode(arrs[i].selectSingleNode("colproperty").text);
        }
      }}
    });
return C},
setCookie:function(A,B){
   $.ajax({
      type: "POST",
      url: "/workflow/gridProperty.jspa?ParamID=saveProperty",
      data: {sid:Math.random(),gridPkId:A,colproperty:this.encodeValue(B)},
      success: function(data){
      }
    });
},
clearCookie:function(A){
$.ajax(
    {
      type: "POST",
      url: "/workflow/gridProperty.jspa?ParamID=clearProperty",
      data: {sid:Math.random(),gridPkId:A},
      success: function(data){
      }
    });
}
});

function showGczt_ZHGL(val){
var arr = ["制单","审核","终止"];
var arg = arguments[2];
  var record = arg['data'];
  var result = arr[val];
  if(record["ygcbh"] != '' && val == '0'){
  result = "工程变更";
  }
return result;
}

function show_kms_xmgl(val){
var arg = arguments[2];
  var record = arg['data'];
  var result = "";
  if(record["xs01"] == '1'){
  result = val;
  }else{
  if(val < 1){
  result = "无货";
  }else if(val > 200){
  result = "货物充足";
  }else{
  result = "有货";
  }
  }
  return result;
}
//展示单据类型中文字符 JSDJD_JH
function showDJLX_JSDJD_JH(val){
  var sxName = "";
  if(val == "1"){
    sxName = "入库";
  }
  else if(val == "3"){
    sxName = "返厂";
  }
  else if(val == "6"){
    sxName = "变价";
  }
  return sxName;
}


function showHwzt_PFDITEM(val){
var arr = ["在库","在途"];
return arr[val];
}

//展示单据类型中文字符 JSDJD_XS 结算登记单(销售)
function showDJLX_JSDJD_XS(val){
  var sxName = "";
  if(val == "0"){
    sxName = "销售";
  }
  else if(val == "3"){
    sxName = "返厂";
  }
  else if(val == "4"){
    sxName = "损溢";
  }
  else if(val == "6"){
    sxName = "变价";
  }
  return sxName;
}

//展示厂家勾对通过标记
function showCJGDBJ_Pub_GCHT(val){
  var sxName = "";
  if(val == "0"){
    sxName = "未勾对";
  }
  else if(val == "1"){
    sxName = "部分勾对";
  }
  else if(val == "2"){
    sxName = "已勾对";
  }
  return sxName;
}

//展示工程队勾对通过标记
function showGCDGDBJ_Pub_GCHT(val){
  var sxName = "";
  if(val == "0"){
    sxName = "未勾对";
  }
  else if(val == "1"){
    sxName = "部分勾对";
  }
  else if(val == "2"){
    sxName = "已勾对";
  }
  return sxName;
}

//展示合同性质
function showHTXZ_Pub_GCHT(val){
  var sxName = "";
  if(val == "0" || val == ""){
    sxName = "正常";
  }
  else if(val == "1"){
    sxName = "要货";
  }
  else if(val == "2"){
    sxName = "政府";
  }
  else if(val == "3"){
    sxName = "重提";
  }
  return sxName;
}

//展示跟进状态
function showGJZT_GCXX18_GCXX(val){
  var sxName = "";
  if(val == "1"){
    sxName = "跟进";
  }
  else if(val == "2"){
    sxName = "签单";
  }
  else if(val == "3"){
    sxName = "丢单";
  }
  else if(val == "4"){
    sxName = "其它单位已做";
  }else if(val == "5"){
    sxName = "终止";
  }
  return sxName;
}


function showGCLX_GCXX(val){
  var sxName = "";
  if(val == "0"){
    sxName = "商用(工装)";
  }
  else if(val == "1"){
    sxName = "家用";
  }
  else if(val == "2"){
    sxName = "商用(家装)";
  }
  return sxName;
}
