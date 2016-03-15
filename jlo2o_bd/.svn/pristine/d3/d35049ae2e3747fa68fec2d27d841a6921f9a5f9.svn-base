//汇通达中信中转服务器地址
var ZX_URL="http://172.16.1.89:8080/loan-zx";
//根目录
var rootPath="";
function setRootPath(ctx){
   this.rootPath = ctx;
}

/*通过id获取对象*/
function $$(obj){if(document.getElementById){return eval('document.getElementById("'+obj+'")')}else if(document.layers){return eval("document.layers['"+obj+"']")}else{return eval('document.all.'+obj)}}

// 将textarea中的SQL语句换行符\r\n替换为空格
String.prototype.replaceTextareaSQL = function(){
  var reg=new RegExp("\r\n","g");
  return this.replace(reg," ");
}

// 将textarea中的换行符\r\n替换为<br>
String.prototype.replaceTextarea = function(){
  var reg=new RegExp("\r\n","g");
  return this.replace(reg,"<br>");
}

// 将textarea中的<br>还原回换行符\r\n
String.prototype.restoreTextarea = function(){
  var reg=new RegExp("<br>","g");
  return this.replace(reg,"\r\n");
}

//将#替换为>
String.prototype.replaceText= function(){
  var reg=new RegExp("#","g");
  return this.replace(reg,">");
}

//将@替换为<
String.prototype.replaceTextStr= function(){
  var reg=new RegExp("@","g");
  return this.replace(reg,"<");
}

//还原特殊字符
String.prototype.decodeString = function(){
  return this.replace(/\&amp;/g, "&").replace(/\&lt;/g, "<").replace(/\&gt;/g, ">").replace(/\&apos;/g, "'");
}

//判断字符串是否为手机
String.prototype.isMobile = function(){
  if(/^13\d{9}$/g.test(this)||/^14\d{9}$/g.test(this)||/^15\d{9}$/g.test(this)||/^18\d{9}$/g.test(this))
    return true;
  else
    return false;
};

/**
 * 判断字符串是否为座机，中间要加-
 */
String.prototype.isPhone = function(){
  var reg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
  return reg.test(this);
};

//判断字符串是否s结尾
String.prototype.endsWith = function(s){
  if(this.length >= s.length){
    return this.substring(this.length - s.length, this.length) == s;
  }
  return false;
};

//判断字符串是否包含中文
String.prototype.checkChinese = function(){
  if (escape(this).indexOf('%u') != -1) {
    return true;
  }
  return false;
};

//判断字符串是否s开始
String.prototype.startsWith = function(s){
  if(this.length >= s.length){
    return this.substring(0, s.length) == s;
  }
  return false;
};

//去掉字符串左边的空格
String.prototype.ltrim = function(){
  return this.replace(/^\s*/,"");
};

//去掉字符串右边的空格
String.prototype.rtrim = function(){
  return this.replace(/\s*$/,"");
};

//去掉字符串左右2边的空格
String.prototype.trim = function(){
  return this.ltrim().rtrim();
};

String.prototype.noEnter = function(){
  return this.replace(/\r\n|\n/g,"");
};

//获取资产中时间年月日，格式：YYYY-MM-DD
function getDateFormat(str){
  return str.split(" ")[0];
}

//字符串的长度
String.prototype.len = function(){
  return this.replace(/[^\x00-\xff]/g, "aa").length;
};

//判断字符串是否为日期格式（yyyy-MM-dd）
String.prototype.isDate = function(){
  var m = this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/);
  if(null == m)
    return false;
  var d = new Date(m[1], m[3], m[4]);
  return ((d.getYear() == m[1]) && (d.getMonth() == m[3]) && (d.getDate() == m[4]));
};

//判断字符串是否为日期格式（yyyy-MM-dd hh:mm:ss）
String.prototype.isDateTime = function(){
  var m = this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
  if(null == m)
    return false;
  var d = new Date(m[1], m[3]-1, m[4], m[5], m[6], m[7]);
  return ((d.getFullYear() == m[1]) && (d.getMonth()+1 == m[3]) && (d.getDate() == m[4]) && (d.getHours() == m[5]) && (d.getMinutes() == m[6]) && (d.getSeconds() == m[7]));
};

//时间赋初值
function setDefaultDate(GMT, o){
  try{
    var date = new Date(parseInt(GMT));
    var y = date.getYear(), m = date.getMonth()+1, d = date.getDate();
    o.value = y + '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d));
  }
  catch(e){
    ;
  }
}

function setDefaultDateTime(GMT, o){
  try{
    var date = new Date(parseInt(GMT));
    var y = date.getYear(), m = date.getMonth()+1, d = date.getDate(), h=date.getHours(), mi=date.getMinutes(), s=date.getSeconds();
    o.value = y + '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d)) + ' ' + h + ':' + mi + ':' + s;
  }
  catch(e){
    ;
  }
}

/**
 * 判断字符串是否为英文或数字
 * author:陈鹏宇 2012.2.1
 */
String.prototype.isAlphaAndNumeric = function(){
  var re = /^[A-Za-z0-9]+$/;
  return re.test(this);
}

/**
 * 判断字符串是否为数字，可以有小数
 */
String.prototype.isNumeric = function(){
  var re = /^[0-9]+\.?[0-9]*$/;
  return re.test(this);
}

/**
 * 判断字符串是否全部为数字
 */
String.prototype.isCode = function(){
  return (/^(-){0,1}(\d)*$/.test(this));
};

/**
 * 判断一位小数
 */
String.prototype.isDecimalsOne = function(){
  return (/^\d*(.\d)?$/.test(this));
};

/**
 * 判断两位小数
 */
String.prototype.isDecimalsTwo = function(){
  return (/^\d*(.\d{2})?$/.test(this));
};

/**
 * 判断是否为数字，数字从0 - 9，不能有小数
 */
String.prototype.isNum = function(){
  return (/^[0-9]*[0-9][0-9]*$/.test(this));
}

/**
 * 判断是否为整数，数字从1 - 9，不能有小数
 */
String.prototype.isPositiveInteger = function(){
  return (/^[0-9]*[1-9][0-9]*$/.test(this));
};

/**
 * 判断字符串是否为数字，不能有小数，可以有正负数
 */
String.prototype.isInteger = function(){
  return (/^(-){0,1}(\d)*$/.test(this));
};

//通过列名获取索引
function getIndexByColumnName(columnName,frame)
{
  return frame.grid.getColumnModel().getIndexById(columnName);
}

/**
 * 判断字符串中是否包含2个字母（定义操作员用到）
 */
String.prototype.isContain2Char =function(){
  var re = /[A-Za-z]+/g;
  var arr,str="";
  while ((arr = re.exec(this)) != null){
    str+=arr;
  }
  return (str.length>1);
};

/**
 * 格式或金钱格式 （例如 1234。4 --> ￥1,234.5）
 */
String.prototype.formatMoney = function (){
  if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(this)){
    return '￥0.00';
  }
  var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
  var re = new RegExp().compile("(\\d)(\\d{3})(,|$)");
  while(re.test(b))
    b = b.replace(re, "$1,$2$3");
  return ('￥' + a + '' + b + '' + c);
}

/**
 * 展示日期
 */
function showDate(str){
   return formatDateValue(formatDate(str));
}

function formatDateValue(str){
  if(str){
   var d = new Date(str).toLocaleString();
   var a = d.split(" ");
   if(a[1] == "0:00:00") return a[0];
    return a[0] + " " + a[1];
  }
  return "&nbsp;";
}

function formatDate(str){
 if(!/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/.test(str) && !/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})(\d{1,2}):(\d{1,2}):(\d{1,2})(.)?(\d){0,}$/.test(str))
  return null;
  str = str.replace("-","/");
  str = str.replace("-","/");
  return str.split(" ")[0];
}

function getFormatDateTime(GMT){
  try{
    var date = new Date(parseInt(GMT));
    var y = date.getYear(), m = date.getMonth()+1, d = date.getDate(), hour=date.getHours(), minute=date.getMinutes(), second=date.getSeconds();
    return  y + '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d)) + " " + ((hour<10)?('0'+hour):(hour))+":" + ((minute<10)?('0'+minute):(minute)) + ":" +  ((second<10)?('0'+second):(second));
  }
  catch(e){
    ;
  }
}

/**
 * 将 年-月-日 类型的字符串转换成日期类型
 * author:陈鹏宇 2012.3.9
 */
function formatStrToDate(str){
  var standard_str = formatDate(str);
  var newDate = new Date(standard_str);
  return newDate;
}



/**
 * 将日期类型转为 年-月-日 类型的字符串
 * author:陈鹏宇 2012.3.9
 */
function formatDateToStr(date){
  var y = date.getYear(), m = date.getMonth()+1, d = date.getDate();
  var str = y + '-' + ((m<10)?('0'+m):(m)) + '-' + ((d<10)?('0'+d):(d));
  return str;
}

//这个可以验证15位和18位的身份证，并且包含生日和校验位的验证。
function isIdCardNo(num) {
  num = num.toUpperCase();
  //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
  if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))){
    alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
    return false;
  }
  //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
  //下面分别分析出生日期和校验位
  var len, re;
  len = num.length;
  if (len == 15) {
    re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
    var arrSplit = num.match(re);
    //检查生日日期是否正确
    var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
    var bGoodDay;
    bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
    if (!bGoodDay) {
      alert('输入的身份证号里出生日期不对！');
      return false;
    } else {
      //将15位身份证转成18位
      //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
      var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
      var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
      var nTemp = 0, i;
      num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
      for(i = 0; i < 17; i ++){
        nTemp += num.substr(i, 1) * arrInt[i];
      }
      num += arrCh[nTemp % 11];
      return num;
    }
  }
  if (len == 18) {
    re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
    var arrSplit = num.match(re);
    //检查生日日期是否正确
    var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
    var bGoodDay;
    bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
    if (!bGoodDay) {
      alert(dtmBirth.getYear());
      alert(arrSplit[2]);
      alert('输入的身份证号里出生日期不对！');
      return false;
    }
  } else {
    //检验18位身份证的校验码是否正确。
    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
    var valnum;
    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
    var nTemp = 0, i;
    for(i = 0; i < 17; i ++) {
      nTemp += num.substr(i, 1) * arrInt[i];
      valnum = arrCh[nTemp % 11];
      if (valnum != num.substr(17, 1)) {
        alert('18位身份证的校验码不正确！应该为：' + valnum);
        return false;
      }
      return num;
    }
  }
  return true;
}

//获取鼠标对象距离页面上面的距离
function getTopPos(inputObj){
  var parObj = inputObj;
  var parFobj = parent;
  var parFobjName = parent.name;
  var top =  0;
  while(parObj){
    top += parObj.offsetTop;
    if (!parObj.offsetParent){
      top -=  parObj.ownerDocument.body.scrollTop;
    }
    if(!parObj.offsetParent && parObj.ownerDocument.parentWindow.frameElement)
    parObj = parObj.ownerDocument.parentWindow.frameElement;
    else
    parObj = parObj.offsetParent;
    //减去父父节点框架被卷去的高度
    if(parFobjName != ""){
      top -= parFobj.parent.document.getElementById(parFobjName).document.documentElement.scrollTop;
      parFobj = parFobj.parent;
      parFobjName = parFobj.name;
    }
    //减去父框架中滚动条的位置高度
    if(parent.document.frames[0]){
      top -= parent.document.frames[0].document.documentElement.scrollTop;
    }
  }
  return top;
}

//获取鼠标距离对象页面左边的距离
function getLeftPos(inputObj){
  var parObj = inputObj;
  var left = 0;
  while(parObj){
    left += parObj.offsetLeft;
    if (!parObj.offsetParent){
      left -= parObj.ownerDocument.body.scrollLeft;
    }
    if(!parObj.offsetParent && parObj.ownerDocument.parentWindow.frameElement)
    parObj = parObj.ownerDocument.parentWindow.frameElement;
    else
    parObj = parObj.offsetParent;
  }
  return left;
}

//获取元素当前操作页面距离上面距离
function getCurrentTopPos(inputObj){
  var returnValue = inputObj.offsetTop;
  while((inputObj = inputObj.offsetParent) != null){
    returnValue += inputObj.offsetTop;
  }
  return returnValue;
}

//获取元素当前超作页面距离左边距离
function getCurrentLeftPos(inputObj){
  var returnValue = inputObj.offsetLeft;
  while((inputObj = inputObj.offsetParent) != null)returnValue += inputObj.offsetLeft;
  return returnValue;
}

//获取选中radio的值
function getRadioValue(Obj){
 var radioValue=0;
 for(var i=0;i<Obj.length;i++){
   if(Obj[i].checked==true){
     radioValue = Obj[i].value;
     break;
   }
 }
 return radioValue;
}

//给radio付返回值
function setRadioChecked(keyValue,Obj){
  for(var i=0;i<Obj.length;i++){
    if(Obj[i].value == keyValue){
      Obj[i].checked = true;
      break;
    }
  }
}

/**
* 给radio付返回值(如果没有一个满足则一个都不选择,即使原来选择了也清空)
* author:陈鹏宇 2012.1.29
*/
function setRadioCheckedOrClean(keyValue,Obj){
  for(var i=0;i<Obj.length;i++){
    if(Obj[i].value == keyValue){
      Obj[i].checked = true;
    }else{
      Obj[i].checked = false;
    }
  }
}

/**
* 判断radio是否有一个选中
* author:陈鹏宇 2012.2.10
*/
function hasRadioChecked(Obj){
  var flag = false;
  for(var i=0;i<Obj.length;i++){
    if(Obj[i].checked == true){
      flag = true;
      break;
    }
  }
  return flag;
}

//设置checkbox是否选中 author:陈鹏宇 2012.1.29
function setCheckBoxChecked(keyValue,Obj){
  if(Obj.value == keyValue){
    Obj.checked = true;
  }else{
    Obj.checked = false;
  }
}

//获取选中checkbox的值 author:陈鹏宇 2012.1.29
function getCheckBoxValue(Obj){
  var boxValue = 0;
  if(Obj.checked == true){
    boxValue = 1;
  }
  return boxValue;
}

//清除页面输入值
function clearValue(){
  var inputs = $("input[type='text']");
  for(var i=0;i<inputs.length;i++){
    inputs[i].value = "";
  }
  var selects = $("input[type='select']");
  for(var i=0;i<selects.length;i++){
    selects[i].value = "";
  }
  var checkboxs = $("input[type='checkbox']");
  for(var i=0;i<checkboxs.length;i++){
    checkboxs[i].checked = false;
  }
  var radios = $("input[type='radio']");
  for(var i=0;i<radios.length;i++){
      if(radios[i].id=="GDZCLX")
      {
       $$("GDZCLX").checked = true;
      }else
      {
       radios[i].checked = false;
      }
   }
}

//获取radio值
function getValue(p){
  var v;
  for(var i=0;i<p.length;i++){
    if(p[i].checked==true){
      v=p[i].value;
    }
  }
  if (v>=0){
    return v;
  } else return -1;//如果未选择任何，返回-1 h.s.y add 201108
}

//删除td，t表示要删除td对象，该方法能自动合并删除td
function Del_Td(t){
   if(t.nextSibling){
    n=t.nextSibling.colSpan+t.colSpan;
    t.nextSibling.setAttribute("colSpan",n);
   }else if(t.previousSibling){
    n=t.previousSibling.colSpan+t.colSpan;
    t.previousSibling.setAttribute("colSpan",n);
   }else{
    if(!t.parentNode.nextSibling&&!t.parentNode.previousSibling){
     t.parentNode.parentNode.parentNode.parentNode.removeChild(t.parentNode.parentNode.parentNode);
     return;
    }
    t.parentNode.parentNode.removeChild(t.parentNode);
    return;
   }
   t.parentNode.removeChild(t);
}

//当滚动条滚动时触发
/**document.body.onscroll=setControlTrasiteTop;
var departmentId;

function setControlTrasiteTop(){
  var divTop = getTopPos(document.getElementById(departmentId))-47;
  window.top.controlTrasiteTop(divTop);
}*/

//parameter:查询SQL的ID;getMethodName:获取返回值方法;backStr:查询条件;cookieId当parameter不能确定唯一时传cookieId
/**function srchValueByLetter(p,parameter,getMethodName,backStr,cookieId){
  if(p.value == ""){
    departmentId = p.id;
    top.toptree = document.getElementById(departmentId);
    top.callBackFunc = getMethodName;
    var url = rootPath+"/ListTables.jsp?parameter="+parameter+"&backStr="+backStr+"&cookieId="+cookieId;
    var divLeft = getLeftPos(document.getElementById(departmentId))+1;
    var divTop = getTopPos(document.getElementById(departmentId))+23;
    window.top.controlTrasiteDepartment(encodeURI(encodeURI(url)),divLeft,divTop);
  }
}*/

//判断grid最下面一行某列值是否为空;flag:false空，true有值
function checkGridCellNull(gridObj,columnName){
  var flag = false;
  var rowNum = gridObj.grid.getStore().getCount();
  if(gridObj.getColumnValue(rowNum-1,columnName) != ""){
    flag = true;
  }
  return flag;
}

//获取grid选中的记录，返回数组
function getGridCheckedArr(gridObj){
  var Arr=new Array();
  if(gridObj.grid.getStore().getCount() > 0){
    var records=gridObj.grid.getSelectionModel().getSelections();
    for(var i=0;i<records.length;i++){
      Arr[i]=gridObj.store.indexOf(records[i]);
    }
  }
  return Arr;
}

//获取重复记录的下标, 返回数组
function getGridRepeatArr(grid,cellValue,checkMC)
{
  var Arr=new Array();
  var j=0;
  for(var i=0;i<grid.getStore().getCount();i++)
  {
   if(cellValue==grid.getStore().getAt(i).get(checkMC))
    {
      Arr[j]=i;
      j++;
    }
  }
  return Arr;
}

//小窗口按钮控制
function smallWindowSetButton(gridObj){
  if (gridObj.grid.getSelectionModel().hasSelection()){
    document.getElementById("define_button").disabled = false;
    document.getElementById("define_button").src = rootPath+"/img/save.gif";
  }else{
    document.getElementById("define_button").disabled = true;
    document.getElementById("define_button").src = rootPath+"/img/nosave.gif";
  }
}

//拼供应商查询条件:yxbj有效标记，jxsx进销属性（0：供应商，1：客户），
function getWLDWBackStr(yxbj,jxsx,bCheck,iBMBJ){
  var backStr="";
  if(yxbj){
    backStr = backStr + " AND GDWLDW14=1";
  }
  if(jxsx>-1){
    backStr = backStr + " AND GDWLDW18="+jxsx;
  }
  if(iBMBJ==1){
    backStr = backStr + " AND GDWLDW01 IN ('000001','000003')";
  }
  if(iBMBJ==2){
    backStr = backStr + " AND GDWLDW19=0";
  }
  if(iBMBJ==3){
    backStr = backStr + " AND GDWLDW19=3";
  }

  return backStr;
}

//某部门下选择人员；   bmdm为要加入的查询条件 ；   @-TNT 2012.2.28 添加
function getRYXXBack(mjbz,bZZ,bmdm){
  var backStr="";
  if(mjbz){
    backStr = backStr + " AND B.BM04 = 1";
  }
  if(bZZ){
    backStr = backStr + " AND R.RYXX03 = 0";
  }
  backStr = backStr + " AND B.BM01 = '"+bmdm+"'";

  return backStr;
}

//某公司下选择人员 gsdm为要加入的查询条件
function getGSRYXXBack(mjbz,bZZ,gsdm){
  var backStr="";
  if(mjbz){
    backStr = backStr + " AND B.BM04 = 1";
  }
  if(bZZ){
    backStr = backStr + " AND R.RYXX03 = 0";
  }
  backStr = backStr + " AND R.GSXX01 = '"+gsdm+"'";

  return backStr;
}

//拼人员信息查询条件:mjbz部门末级标记，bZZ人员属性（RYXX03)
function getRYXXBackStr(gszt,gshb,mykh,gsid){
  var backStr="";
  if(gszt){
    backStr = backStr + "  AND EXISTS (SELECT 1 FROM W_GSLX G WHERE G.LX  = 42 AND G.GSID = W.ZCXX01)";
  }
  if(gshb){
    backStr = backStr + " AND EXISTS (SELECT 1 FROM W_GSLX G WHERE G.LX  = 21 AND G.GSID = W.ZCXX01)";
  }
  if(mykh){
	  backStr = backStr + " AND EXISTS (SELECT 1 FROM W_GSGX X WHERE X.HBID = W.ZCXX01 AND X.ZTID = '"+gsid+"')";
  }
  return backStr;
}

function getRYGXXXBackStr(myghs,mykh,gsid){
  var backStr="";
  if(myghs){
	  if(gsid!=''){
		  backStr = backStr + "  AND EXISTS (SELECT 1 FROM W_GSGX G WHERE G.HBID = '"+gsid+"' AND G.ZTID = W.ZCXX01)"; 
	  }else{
		  backStr = backStr + "  AND EXISTS (SELECT 1 FROM W_GSLX G WHERE G.LX  = 42 AND G.GSID = W.ZCXX01)";
	  }
  }
  if(mykh){
	  if(gsid!=''){
		  backStr = backStr + " AND EXISTS (SELECT 1 FROM W_GSGX X WHERE X.HBID = W.ZCXX01 AND X.ZTID = '"+gsid+"')";
	  }else{
		  backStr = backStr + " AND EXISTS (SELECT 1 FROM W_GSLX G WHERE G.LX  = 21 AND G.GSID = W.ZCXX01)";
	  }
  }
  return backStr;
}

//查询单点数型数据
function srchValueForSingleTree(id,backStr,obj,sqlId,last){
  departmentId = id;
  top.toptree = document.getElementById(id);
  backStr = (backStr == undefined)?"":backStr;
  var url = rootPath+"/singleSelectTree.jsp?name="+id+"&sqlId="+sqlId+"&backStr="+backStr+"&last="+last;
  var divLeft = getLeftPos(document.getElementById(departmentId))+1;
  var divTop = getTopPos(document.getElementById(departmentId))+23;
  window.top.controlTrasiteDepartment(encodeURI(encodeURI(url)),divLeft,divTop);
}

//树型结构数据多选，调用JS
function srchValueForMutiSelectTree(id,backStr,obj,sqlId){
  departmentId = id;
  top.toptree = document.getElementById(id);
  backStr = (backStr == undefined)?"":backStr;
  var url = rootPath+"/multiSelectTree.jsp?name="+id+"&sqlId="+sqlId+"&backStr="+backStr;
  var divLeft = getLeftPos(document.getElementById(departmentId))+1;
  var divTop = getTopPos(document.getElementById(departmentId))+23;
  window.top.controlTrasiteDepartment(encodeURI(encodeURI(url)),divLeft,divTop);
}

//将输入框变成可输入
function changeCanEnter(){
  var inputs = $("input[type='text']");
  for(var i=0;i<inputs.length;i++){
    if(inputs[i].className != "onlyWiew"){
      inputs[i].className = "";
      inputs[i].disabled = false;
    }
  }
  var radios = $("input[type='radio']");
  for(var i=0;i<radios.length;i++){
    radios[i].disabled = false;
  }
  var selects = $("select");
  for(var i=0;i<selects.length;i++){
     selects[i].disabled = false;
  }
}

//将输入框变成不可输入
function changeNotEnter(){
  var inputs = $("input[type='text']");
  for(var i=0;i<inputs.length;i++){
    if(inputs[i].className != "onlyWiew"){
      inputs[i].className = "notEnter";
      inputs[i].disabled = true;
    }
  }
  var radios = $("input[type='radio']");
  for(var i=0;i<radios.length;i++){
    radios[i].disabled = true;
  }
}

//验证信息是否重复
function checkXX(robj,checkMc,mc)
{
   var rows = robj.grid.getStore().getCount();
   var cellValueArr=new Array();
    for(var i=0;i<rows;i++){
      cellValueArr[i]=robj.getColumnValue(i,checkMc);
    }
    var objArr={};
    var isFlag=false;
    var flag=0;
    for(var i=0;i<cellValueArr.length;i++)
    {
      var j=cellValueArr[i];
      if(objArr[j]==null)
      {
        objArr[j]=1;
      }else
      {
        objArr[j]=objArr[j]+1;
        flag=1;
      }
      }

      if(flag==1)
      {
        var info=mc;
        for(obj in objArr)
        {
          var i=objArr[obj];
          if(i>1)
        {
          info=info+obj+",";
        }

      }
      isFlag=true;
      info=info.substring(0,info.length-1)+"重复!";
      alert(info);
    }
    return isFlag;
}

//禁用和可用 grid  isEdit:是否可以编辑(true:可编辑 false:不可编辑) obj:grid控件对象 arr:是否可以编辑的名称（数组对象）
function setColumnEnable(isEdit,obj,arr)
{
   if(obj.getStore().getCount()>0)
   {
     var indexZ=new Array();
     for(var i=0;i<arr.length;i++)
     {
       indexZ[i]=obj.getColumnModel().getIndexById(arr[i]);
     }

        if(isEdit)
        {
        for(var j=0;j<indexZ.length;j++)
       {
         obj.getColumnModel().setEditable(indexZ[j],true);
        }

      }else
     {
       for(var k=0;k<indexZ.length;k++)
       {
        obj.getColumnModel().setEditable(indexZ[k],false);
       }
    }
  }
}

//隐藏和显示 grid  isVisiable:是否隐藏(true:隐藏 false:显示) obj:grid控件对象 arr:是否隐藏的名称（数组对象）
function setColumnHidden(isHidden,obj,arr)
{
   if(obj.getStore().getCount()>0)
   {
     var indexZ=new Array();
     for(var i=0;i<arr.length;i++)
     {
       indexZ[i]=obj.getColumnModel().getIndexById(arr[i]);
     }

        if(isHidden)
        {
        for(var j=0;j<indexZ.length;j++)
       {
         obj.getColumnModel().setHidden(indexZ[j],true);
        }

      }else
     {
       for(var k=0;k<indexZ.length;k++)
       {
        obj.getColumnModel().setHidden(indexZ[k],false);
       }
    }
  }
}

//alqx改菜单可以操作的按钮权限，curButton当期按钮，A：新建,B：修改,C：删除,D：列设置,E：保存,F：取消,G：显示图形,P：打印,Q：查询或刷新
function getButtonQX(alqx,curButton){
  var p = alqx.indexOf(curButton);
  return (p==-1)?false:true;
}

//获取固定资产类型
function getGDZXLX(){
  var Result=-1;
  if(getButtonQX(alqx,"0")){
    Result=0;
  }else if(getButtonQX(alqx,"1")){
    Result=1;
  }else if(getButtonQX(alqx,"2")){
    Result=2;
  }else if(getButtonQX(alqx,"3")){
    Result=3;
  }else if(getButtonQX(alqx,"4")){
    Result=4;
  }else if(getButtonQX(alqx,"5")){
    Result=5;
  }
  return Result;
}

//获取**
function getIBMBJ(){
  var iBMBJ=2;
  if(getButtonQX(alqx,"6")){
    iBMBJ=0;
  }else if(getButtonQX(alqx,"7")){
    iBMBJ=1;
  }else if(getButtonQX(alqx,"8")){
    iBMBJ=3;//商品转入入库
  }
   return iBMBJ;
}

/**
 * 获取操作权限
 * qxString:权限字符串；fieldName:字段名称（BM01、CK01）
 */
function getQxItem(qxString,fieldName){
  var str = "";
  if(qxString.length > 0 && qxString != "null"){
    var qxArr = qxString.split(";");
    str = str + ("(");
    for(var i=0;i<qxArr.length;i++){
      if(i==0){
        str = str + fieldName + " LIKE '" + qxArr[i] + "%'";
      }else{
        str = str + " OR " + fieldName + " LIKE '" + qxArr[i] + "%'";
      }
    }
    str = str + (")");
  }
  return str;
}

//bmlb:部门类别1总部,2分公司,3办事处；bmdm:部门代码；fieldName:字段名
function getRYXXBMQX(bmlb,bmdm,fieldName){
  var backStr = "";
  if(bmlb != 1)bmdm = bmdm.substring(0,bmdm.length-2);
  backStr = backStr + " AND "+fieldName+" LIKE '"+bmdm+"%'";
  return backStr;
}

//获取部门树形联想输入字符
function getLenovoTreeBM(obj){
 var backStr = "";
 if(obj.value != ""){
   backStr = " AND (BM01 LIKE '%"+obj.value+"%' OR BM02 LIKE '%"+obj.value+"%' OR BM11 LIKE '%'||Upper('"+obj.value+"')||'%')";
 }
 return backStr;
}

//获取仓库树形联想输入字符
function getLenovoTreeCK(obj){
 var backStr = "";
 if(obj.value != ""){
   backStr = " AND (CK01 LIKE '%"+obj.value+"%' OR CK02 LIKE '%"+obj.value+"%' OR CK11 LIKE '%'||Upper('"+obj.value+"')||'%')";
 }
 return backStr;
}

/**
 * 获取查询级别
 * gsxx01:公司代码；level:查询级别；fieldName:字段名称
 * level(0:集团；1:平台；2:公司)
 */
function getQueryLevel(gsxx01,level,fieldName){
  var result = "";
  result = gsxx01.substring(0,level*2);
  if(result.length > 0)
  result = "(" + fieldName + " LIKE '"+result+"%')";
  return result;
}

/**
 * 获取权限查询条件
 * bmQx:部门权限；ckQx:仓库权限；gsQx:公司权限；levelQx:级别权限
 */
function getQxBackStr(bmQx,ckQx,gsQx,levelQx){
  var str = "";
  if(bmQx != undefined && bmQx.length > 0)
  {
  str = str + " AND " + bmQx;
  }
  if(ckQx != undefined && ckQx.length > 0)
  str = str + " AND " + ckQx;
  if(gsQx != undefined && gsQx.length > 0)
  str = str + " AND " + gsQx;
  if(levelQx != undefined && levelQx.length > 0)
  str = str + " AND " + levelQx;
  return str;
}

//对穿透标记赋值
function set_workflow_control(){
  var ctbj = $$("CTBJ").value;
  if(ctbj=="" || ctbj==0){
    $$("workflow_control").value = false;
  }else{
    $$("workflow_control").value = true;
  }
}

/**
* 为公司代码拼接SQL
* author:陈鹏宇 2012.2.28
*/
function MakeSQLForGSID(tblAlias,sGSID,sGSCode){
  if(sGSCode == null){
    sGSCode = "";
  }

  var result = "";
  if((sGSID.trim() == "" || sGSID.trim() == "00" ) && sGSCode.trim() == ""){
    result = ""; //总部查询单据，示例：总部在点查找之后在查找窗体里出现的结果，应该是所有分公司都可以看到
  }
  else if(tblAlias != ""){
    if(sGSCode.trim() != ""){
      result = " AND "  + tblAlias + ".GSXX01 = '" + sGSCode.trim() + "'";
    }else{
      result = " AND "  + tblAlias + ".GSXX01 = '" + sGSID.trim() + "'";
    }
  }else{
    if(sGSCode.trim() != ""){
      result = " AND GSXX01 = '" + sGSCode.trim() + "'";
    }else{
      result = " AND GSXX01 = '" + sGSID.trim() + "'";
    }
  }
  return result;
}

var WLDWRec = {
  sGSID : "",//公司代码
  sCode : "",//供应商代码
  sCName : "",//供应商名称
  sSpell : "", //拼音码
  sShort : "", //简称
  sCAddr : "", //中文地址
  sEAddr : "", //英文地址
  sPost : "", //邮政编码
  sFax : "", //传真号码
  sPhone : "", //联系电话
  sBank : "", //开户银行
  sAccounts : "", //帐号
  sTax : "", //税号
  sPassword : "", //单位密码
  bAvail : 0, //有效标记
  sBooker : "", //登记人
  dDate : "", //登记日期
  sEName : "",//英文名称
  JXSX : 0, //进销属性（0：供应商，1：客户）
  iType : -1, //单位属性(0一般往来 1内部往来(分部) 2内部往来(直营) 3集采  4 IT配发,5非商品转入)
  sDFGSID : "", //对方公司代码
  sDFGSMC : "", //对方公司名称
  sDFBMDM : 0, //对方部门代码
  sDFBMMC : 0,//对方部门名称
  SFJJ : 0 //是否加价
};

/**
* 查询供应商信息
* author:刘项 2012.3.31
* sCode供应商代码  bEffi有效标记
* iAttr进销属性（0：供应商，1：客户）
* iDWSX单位属性(0一般往来 1内部往来(分部) 2内部往来(直营) 3集采  4 IT配发)
*/
function GetPartner(sCode,bEffi,iAttr,iDWSX){
  var backStr="";
  var Result=false;
  if(sCode=="")
  {
    Result=false;
    return;
  }
  backStr=backStr+" GDWLDW01='"+sCode+"'";
  if(bEffi)
  {
   backStr=backStr+" AND GDWLDW14 = 1";//有效标记
  }
  if(iAttr==-1)
  {
   backStr=backStr+" AND GDWLDW18 IN (1,2)";
  }
  if(iAttr==3)
  {
   backStr=backStr+" AND (GDWLDW18 = 1 OR GDWLDW18 = 2)";
  }else if(iAttr>-1)
  {
   backStr=backStr+" AND GDWLDW18="+iAttr;
  }
  if(iDWSX>-1)
  {
   backStr=backStr+" AND GDWLDW19="+iDWSX;
  }
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+"/dataInfoAction.do?method=getGYSXX&sid=" + Math.random(),
    data: {backStr:backStr},
    success: function(data){
      if (data != null)
      {
        var gysxxs=data.selectSingleNode("/response/gysxxs");
        try{
            if(gysxxs!=null)
            {
            WLDWRec.sGSID=gysxxs.selectSingleNode("sGSID").text;
            WLDWRec.sCode=gysxxs.selectSingleNode("sCode").text;
            WLDWRec.sCName=gysxxs.selectSingleNode("sCName").text;
            WLDWRec.sSpell=gysxxs.selectSingleNode("sSpell").text;
            WLDWRec.sShort=gysxxs.selectSingleNode("sShort").text;
            WLDWRec.sCAddr=gysxxs.selectSingleNode("sCAddr").text;
            WLDWRec.sEAddr=gysxxs.selectSingleNode("sEAddr").text;
            WLDWRec.sPost=gysxxs.selectSingleNode("sPost").text;
            WLDWRec.sFax=gysxxs.selectSingleNode("sFax").text;
            WLDWRec.sPhone=gysxxs.selectSingleNode("sPhone").text;
            WLDWRec.sBank=gysxxs.selectSingleNode("sBank").text;
            WLDWRec.sAccounts=gysxxs.selectSingleNode("sAccounts").text;
            WLDWRec.sTax=gysxxs.selectSingleNode("sTax").text;
            WLDWRec.sPassword=gysxxs.selectSingleNode("sPassword").text;
            WLDWRec.bAvail=gysxxs.selectSingleNode("bAvail").text;
            WLDWRec.sBooker=gysxxs.selectSingleNode("sBooker").text;
            WLDWRec.dDate=gysxxs.selectSingleNode("dDate").text;
            WLDWRec.sEName=gysxxs.selectSingleNode("sEName").text;
            WLDWRec.JXSX=gysxxs.selectSingleNode("JXSX").text;
            WLDWRec.iType=gysxxs.selectSingleNode("iType").text;
            WLDWRec.sDFGSID=gysxxs.selectSingleNode("sDFGSID").text;
            WLDWRec.sDFGSMC=gysxxs.selectSingleNode("sDFGSMC").text;
            WLDWRec.sDFBMDM=gysxxs.selectSingleNode("sDFBMDM").text;
            WLDWRec.sDFBMMC=gysxxs.selectSingleNode("sDFBMMC").text;
            WLDWRec.SFJJ=gysxxs.selectSingleNode("SFJJ").text;
            Result=true;
            }
          }catch(e){
            Result=false;
            alert("查询失败！");
          }
       }
    }
  });
  return Result;
}

/**
* 查询供应商信息
* author:刘项 2012.4.1
* sCode供应商代码  bEffi有效标记
* iAttr进销属性（0：供应商，1：客户）
* iBMBJ 部门标记
* uGSID 公司代码
*/
function GetGDPartner(sCode,bEffi,iAttr,iBMBJ,uGSID){
  var backStr="";
  var Result=false;
  if(sCode=="")
  {
    Result=false;
    return;
  }
  backStr=backStr+" GDWLDW01='"+sCode+"'";
  if(bEffi)
  {
   backStr=backStr+" AND GDWLDW14 = 1";//有效标记
  }
  if(iAttr>-1)
  {
   backStr=backStr+" AND GDWLDW18="+iAttr;
  }
  if(iBMBJ==1)
  {
   //物流只允许IT配发的供应商
   backStr=backStr+" AND GDWLDW19=4";
  }
  if(iBMBJ==3)
  {
   backStr=backStr+" AND GDWLDW19=5 AND GDWLDW20='"+uGSID+"'";
  }else
  {
   //不包括 商品转入供应商
   backStr=backStr+" AND GDWLDW19<>5";
  }
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+"/dataInfoAction.do?method=getGYSXX&sid=" + Math.random(),
    data: {backStr:backStr},
    success: function(data){
      if (data != null)
      {
        var gysxxs=data.selectSingleNode("/response/gysxxs");
        try{
            if(gysxxs!=null)
            {
            WLDWRec.sGSID=gysxxs.selectSingleNode("sGSID").text;
            WLDWRec.sCode=gysxxs.selectSingleNode("sCode").text;
            WLDWRec.sCName=gysxxs.selectSingleNode("sCName").text;
            WLDWRec.sSpell=gysxxs.selectSingleNode("sSpell").text;
            WLDWRec.sShort=gysxxs.selectSingleNode("sShort").text;
            WLDWRec.sCAddr=gysxxs.selectSingleNode("sCAddr").text;
            WLDWRec.sEAddr=gysxxs.selectSingleNode("sEAddr").text;
            WLDWRec.sPost=gysxxs.selectSingleNode("sPost").text;
            WLDWRec.sFax=gysxxs.selectSingleNode("sFax").text;
            WLDWRec.sPhone=gysxxs.selectSingleNode("sPhone").text;
            WLDWRec.sBank=gysxxs.selectSingleNode("sBank").text;
            WLDWRec.sAccounts=gysxxs.selectSingleNode("sAccounts").text;
            WLDWRec.sTax=gysxxs.selectSingleNode("sTax").text;
            WLDWRec.sPassword=gysxxs.selectSingleNode("sPassword").text;
            WLDWRec.bAvail=gysxxs.selectSingleNode("bAvail").text;
            WLDWRec.sBooker=gysxxs.selectSingleNode("sBooker").text;
            WLDWRec.dDate=gysxxs.selectSingleNode("dDate").text;
            WLDWRec.sEName=gysxxs.selectSingleNode("sEName").text;
            WLDWRec.JXSX=gysxxs.selectSingleNode("JXSX").text;
            WLDWRec.iType=gysxxs.selectSingleNode("iType").text;
            WLDWRec.sDFGSID=gysxxs.selectSingleNode("sDFGSID").text;
            WLDWRec.sDFGSMC=gysxxs.selectSingleNode("sDFGSMC").text;
            WLDWRec.sDFBMDM=gysxxs.selectSingleNode("sDFBMDM").text;
            WLDWRec.sDFBMMC=gysxxs.selectSingleNode("sDFBMMC").text;
            WLDWRec.SFJJ=gysxxs.selectSingleNode("SFJJ").text;
            Result=true;
            }
          }catch(e){
            Result=false;
            alert("查询失败！");
          }
       }
    }
  });
  return Result;
}

var CKKind = -1; //当前仓库类型
var DeptKind = -1; //当前采购部门类型

//仓库类型
 CKKind = {
  ckDeptAll : 0,
  dkXZ : 1,//行政
  dkLP : 2,//礼品
  dkWL : 3,//物流
  dkXZ_LP : 4 //行政礼品
};

//采购部门类型
 DeptKind = {
  dkAll : 0,
  dkCompany : 1,//公司
  dkBuy : 2,//采购部门
  dkSale : 3,//销售部门
  dkOther : 4,//其他
  dkWLBuy : 5,//物流部门
  dkGF : 6 //股份直营部门
};

//初始仓库和部门值
function setObjectforCKandBM(){
  //仓库类型
   CKKind = {
    ckDeptAll : 0,
    dkXZ : 1,//行政
    dkLP : 2,//礼品
    dkWL : 3,//物流
    dkXZ_LP : 4 //行政礼品
  };

  //采购部门类型
   DeptKind = {
    dkAll : 0,
    dkCompany : 1,//公司
    dkBuy : 2,//采购部门
    dkSale : 3,//销售部门
    dkOther : 4,//其他
    dkWLBuy : 5,//物流部门
    dkGF : 6 //股份直营部门
  };
}

//获取仓库条件
function getBackCKStr()
{
 var backStr="";
 switch(CKKind){
    case 0:
      backStr = " 1=1";
      break;
    case 1:
      backStr = " CK15 <> 0 AND (CK15 = 1)";
      break;
    case 2:
      backStr = " CK15 <> 0 AND (CK15 = 2)";
      break;
    case 3:
      backStr = " CK15 <> 0 AND (CK15 = 3)";
      break;
    case 4:
      backStr = " CK15 <> 0 AND (CK15 = 3 OR CK15=2)";
      break;
    default:
      backStr = " 1=1";
  }
  return backStr;
}

//获取仓库条件
function getBackTCKStr()
{
  var backStr="";
 switch(CKKind){
    case 0:
      backStr = " 1=1";
      break;
    case 1:
      backStr = "  CK04 <> 0  AND (CK06 = 0)'";
      break;
    case 2:
      backStr = "  CK04 <> 0  AND (CK06 = 1)'";
      break;
    case 3:
      backStr = "  CK04 <> 0  AND ((CK06 = 1) OR (CK06=0))";
      break;
    default:
      backStr = " 1=1";
  }
  return backStr;
}

//获取部门条件
function getBackBMStr()
{
  var backStr = "";
  switch(DeptKind){
    case 0:
      backStr = " 1=1";
      break;
    case 1:
      backStr = " BM04 <> 0 AND (BM06 = 0)";
      break;
    case 2:
      backStr = " BM04 <> 0 AND (BM06 = 2)";
      break;
    case 3:
      backStr = " BM04 <> 0 AND (BM06 = 1 OR BM06 = 3)";
      break;
    case 4:
      backStr = " BM04 <> 0 AND (BM06 > 3)";
      break;
    case 5:
      backStr = " BM04 <> 0 AND (BM06 = 5)";
      break;
    default:
      backStr = " 1=1";
  }
  return backStr;
}

var YEAR,MONTH,DAY;
//获取时间的年月日
function DecodeDate(strTime)
{
  var date=formatStrToDate(strTime);
  YEAR=date.getYear();
  MONTH=date.getMonth()+1;
  DAY=date.getDate();
}

//讲GRID中的时间格式进行转换
function changeExtGridDate(value){
  if(value instanceof Date || value.toString().indexOf("UTC")>0){
    var date = new Date(value);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
  }else{
    return value;
  }
}

//ajax查询主键是否重复（table表名，key主键，value值）
function selectKeyNum(table,key,value,backStr){
  var isRepeat = false;
  if(backStr=="" || backStr==undefined){
      backStr=" AND 1=1 ";
  }else{
      backStr=backStr;
  }
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+"/dataInfoAction.do?method=getNumber&sid=" + Math.random(),
    data: {"table":table,"key":key,"value":value,"backStr":backStr},
    success: function(data){
      if (data != null)
      {
        try{
          var Num = data.selectSingleNode("/response/Num").text;
          var num = parseInt(Num);
          if(num >0 ){
           isRepeat = true;
          }
        }catch(e){
          alert(e);
        }
      }
    }
  });
  return isRepeat;
}

//ajax加载部门所属帐套
function getBMDYZT(bmdm){
  var backStr = "";
   if(bmdm == "" || bmdm == undefined || bmdm==null){
      alert("部门代码不明确，请确认！");
      return;
  }else{
      backStr=backStr+" AND BM01='"+bmdm+"'";
  }
  //<!--reset用于返回结果集-->
  var reset = new Array();
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+"/dataInfoAction.do?method=getBMDYZT",
    data: {"backStr":backStr},
    success: function(data){
      if (data != null)
      {
        var bmzts = data.selectNodes("/response/bmzts/bmzt");
        if(bmzts.length < 1){
          alert("对不起!部门："+bmdm+"没有对应帐套！");
        }
        for(var i=0;i<bmzts.length;i++){
          try{
            var result = "";
            result = result + bmzts[i].selectSingleNode("bmdm").text;
            result = result + ";";
            result = result + bmzts[i].selectSingleNode("ztdm").text;
            result = result + ";";
            result = result + bmzts[i].selectSingleNode("mrbj").text;
            result = result + ";";
            result = result + bmzts[i].selectSingleNode("sfyx").text;
            reset.push(result);
          }catch(e){
            alert(e);
          }
        }
      }
    }
  });
  return reset;
}

//ajax加载部门对应仓库
function getBMDYCK(bmdm,Str){
  var backStr = "";
  if(bmdm == "" || bmdm == undefined || bmdm==null){
      alert("部门代码不明确，请确认！");
      return;
  }else{
      backStr = backStr + " AND BM01='" + bmdm + "'";
  }

  if(Str == "" || Str == undefined || Str==null){
      alert("仓库权限不明确，请确认！");
      return;
  }else{
      backStr = backStr + Str;
  }
  //<!--reset用于返回结果集-->
  var reset = new Array();
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+ "/dataInfoAction.do?method=getBMDYCK",
    data: {"backStr":backStr},
    success: function(data){
      if (data != null)
      {
        var bmcks = data.selectNodes("/response/bmcks/bmck");
        if(bmcks.length < 1 ){
          alert("对不起！部门："+bmdm+" 还没有对应的仓库，或没有对应仓库的权限！");
          return;
        }

        for(var i=0;i<bmcks.length;i++){
          try{
            var result = "";
            result = result + bmcks[i].selectSingleNode("bmdm").text;
            result = result + ";";
            result = result + bmcks[i].selectSingleNode("bmmc").text;
            result = result + ";";
            result = result + bmcks[i].selectSingleNode("ckdm").text;
            result = result + ";";
            result = result + bmcks[i].selectSingleNode("ckmc").text;
            result = result + ";";
            reset.push(result);
          }catch(e){
            alert(e);
          }
        }
      }
    }
  });
  return reset;
}

//ajax查询默认供应商
function getMRWLDW(){
  var backStr = "";
  backStr=backStr+" AND GDWLDW01='A0000A'";

  //<!--reset用于返回结果集-->
  var reset = new Array();
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+ "/dataInfoAction.do?method=getMRGDWLDW",
    data: {"backStr":backStr},
    success: function(data){
      if (data != null)
      {
        var mrgyss = data.selectNodes("/response/mrgyss/mrgys");
        if(mrgyss.length > 1 ){
          alert("对不起！有多个默认供应商！");
          return;
        }

        if(mrgyss.length < 1 ){
          alert("对不起！没有查询到默认供应商！");
          return;
        }

        for(var i=0;i<mrgyss.length;i++){
          try{
            var result = "";
            result = result + mrgyss[i].selectSingleNode("wldwdm").text;
            result = result + ";";
            result = result + mrgyss[i].selectSingleNode("wldwmc").text;
            reset.push(result);
          }catch(e){
            alert(e);
          }
        }
      }
    }
  });
  return reset;
}


//限制EXT点击backspace时，页面回退
if(document.addEventListener){
    document.addEventListener("keydown",maskBackspace, true);
}else{
    document.attachEvent("onkeydown",maskBackspace);
}

function maskBackspace(event){
  var event = event || window.event; //标准化事件对象
  var obj = event.target || event.srcElement;
  var keyCode = event.keyCode ? event.keyCode : event.which ?
  event.which : event.charCode;
  if(keyCode == 8){
    if(obj!=null && obj.tagName!=null && (obj.tagName.toLowerCase() == "input" || obj.tagName.toLowerCase() == "textarea")){
      if(obj.readOnly){
	event.returnValue = false ;
      }else{
	event.returnValue = true ;
      }
      if(document.Ext){
        if(Ext.getCmp(obj.id)){
          if(Ext.getCmp(obj.id).readOnly) {
            if(window.event)
            event.returnValue = false ; //or event.keyCode=0
            else
            event.preventDefault();   //for ff[/b]
          }
        }
      }
    }else{
      if(window.event)
      event.returnValue = false ;   // or event.keyCode=0
      else
      event.preventDefault();
    }
  }
}

//获取部门类别
function getBMLB(bmdm){
  var bmlb=0;
  $.ajax(
  {
    type: "POST",  //请求方式
    async: false, //同步
    url: rootPath+ "<%=request.getContextPath()%>/dataInfoAction.do?method=getBMDetail&sid=" + Math.random(),
    data: {code:bmdm},
    success: function(data){
      if (data != null)
      {
        try{
          var bm = data.selectSingleNode("/response/bm");
          bmlb = bm.selectSingleNode("bmlb").text;
        }catch(e){
          alert(e);
        }
      }
    }
  });
  return bmlb;
}


//ajax 访问服务器端,同步，获取JSON返回结果，val是连接地址
function visitService(url){
	var returnValue;
    $.ajax({
	    type: "GET",  //请求方式
	   // dataType: "JSON",
	    async: false,   //true表示异步 false表示同步
	    url:encodeURI(url),
	    data:{},
	    success: function(data){
	      if (data != null){
	        try{
	          var json = jQuery.parseJSON(data);
	          //JSON为map值直接获取:json.*
	          //returnValue = JSON.stringify(json.data);
	          returnValue = json.data;
	        }catch(e){
	          return;
	        }
	      }
	    },
        //获取错误信息
    	error:function(XMLHttpRequest, textStatus, errorThrown) {
	    	alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest.responseText+"+"+errorThrown);
        }
	  });
    return returnValue;
}

//ajax 访问服务器端,异步，获取JSON返回结果，val是连接地址
function visitServAsync(val){

	if(val.lastIndexOf("?")!=-1){
		if(val.lastIndexOf("?")<val.length){
			val+="&r="+Math.random();
		}else{
			val+="r="+Math.random();
		}		
	}else{
		val+="?r="+Math.random();
	}
	var returnValue;
	var url=val;
	if(val.indexOf("?")==-1){
		url=val+"?r="+Math.random();
	}else{
		url=val+"&r="+Math.random();
	}
    $.ajax({
	    type: "GET",  //请求方式
	   // dataType: "JSON",
	    async: true,   //true表示异步 false表示同步
	    url:encodeURI(url),
	    data:{},
	    success: function(data){
	      if (data != null){
	        try{
	          var json = jQuery.parseJSON(data);
	          //JSON为map值直接获取:json.*
	          //returnValue = JSON.stringify(json.data);
	          returnValue = json.data;
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

/**提交大量数据
 * @author jhj
 * @date 2014-07-31
 */
function visit(url,XmlData){
	var returnVal;
	$.ajax({
		 type:"POST",
		   async:false,
		   url:encodeURI(url),
		   data:{XmlData:XmlData},
		   success: function(data) {
			   		var json = jQuery.parseJSON(data);
					returnVal = json.data;
					
				},
		   error: function(XMLHttpRequest, textStatus, errorThrown) {
				       alert(textStatus);
				}
		});
	return returnVal;
}


function visitServicePage(val){
	var returnValue;
    $.ajax({
	    type: "POST",  //请求方式
	   // dataType: "JSON",
	    async: false,   //true表示异步 false表示同步
	    url:encodeURI(val),
	    data:{},
	    success: function(data){
	      if (data != null){
	        try{
	          var json = jQuery.parseJSON(data);
	          //JSON为map值直接获取:json.*
	          //returnValue = JSON.stringify(json.data);
	          //alert("ALLDATE:"+JSON.stringify(json));
	          returnValue = json;			  
	        }catch(e){
	          return;
	        }
	      }
	    },
        //获取错误信息
	    error: function(data,XMLHttpRequest, textStatus, errorThrown) {
	    	return;
		}
	  });
    return returnValue;
}

//form中选取的字段变成JSON
$.fn.formToJson = function( names){
	var arr=[];
	var serializeObj={};
	var array=this.serializeArray();
    var $form = $(this);
	$(array).each(function(){
		if(names==null){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					if($form.find("[name='"+this.name+"']").eq(0).attr("title")=="number"){
						serializeObj[this.name].push(Number(this.value.trim()));
					}else{
						serializeObj[this.name].push(this.value.trim());
					}
				}else{
					if($form.find("[name='"+this.name+"']").eq(0).attr("title")=="number"){
						serializeObj[this.name]=[serializeObj[this.name],Number(this.value.trim())];
					}else{
						serializeObj[this.name]=[serializeObj[this.name],this.value.trim()];
					}
				}
			}else{
				if($form.find("[name='"+this.name+"']").eq(0).attr("title")=="number"){
						serializeObj[this.name]=Number(this.value.trim());
				}else{
						serializeObj[this.name]=this.value.trim();
				}
			}
		}else{
			if($.inArray(this.name,names)>=0){
				if(serializeObj[this.name]){
					if($.isArray(serializeObj[this.name])){
						if($form.find("[name='"+this.name+"']").eq(0).attr("title")=="number"){
							serializeObj[this.name].push(Number(this.value.trim()));
						}else{
							serializeObj[this.name].push(this.value.trim());
						}
					}else{
						if($form.find("[name='"+this.name+"']").eq(0).attr("title")=="number"){	
							serializeObj[this.name]=[serializeObj[this.name],Number(this.value.trim())];
						}else{
							serializeObj[this.name]=[serializeObj[this.name],this.value.trim()];
						}
					}
				}else{
					if($form.find("[name='"+this.name+"']").eq(0).attr("title")=="number"){
							serializeObj[this.name]=Number(this.value.trim());
					}else{
							serializeObj[this.name]=this.value.trim();
					}
				}
			}
		}
	});
	arr.push(serializeObj);
	return arr;
}

//GRID中选取列变成JSON
$.gridToJson = function(grid,cols){
	var arr = [];
	var store = grid.getStore();
   	if(store.getCount()>0){
		for(var i=0;i<store.getCount();i++){
			var o={};
			var record = store.getAt(i);
			for(var j=0;j<cols.length;j++){
				o[cols[j]]=record.get(cols[j]);
			}
			arr.push(o);
			
		}
    }
    
   return arr;
}

$.multiGridToJson= function(grid,cols){
	var arr = [];
	var selections = grid.getSelectionModel().getSelections();
   	if(selections.length>0){
		for(var i=0;i<selections.length;i++){
			var o={};
			var record = selections[i];
			for(var j=0;j<cols.length;j++){
				o[cols[j]]=record.get(cols[j]);
			}
			arr.push(o);
			
		}
    }
    
   return arr;
}


//names,grid1,cols1,layer1,aName1,grid2,cols2,layer2,aName2
//将FORM和GRID整合成一个JSON
$.fn.gridFormToJson = function(opt){
	item1 =$.gridToJson(opt.grid1,opt.cols1) ;
	arr = $(this).formToJson(opt.names);
	if("undefined" != typeof(opt.layer1)){
		arr[opt.layer1][opt.aName1]=item1;
	}else{
		arr[0][opt.aName1]=item1;
	}
	if("undefined" !=typeof(opt.grid2)&&"undefined" !=typeof(opt.cols2)&&
	"undefined" !=typeof(opt.aName2)){
		item2 = $.gridToJson(opt.grid2,opt.cols2);
		if("undefined" != typeof(opt.layer1)&&"undefined" != typeof(opt.layer2)){
			arr[opt.layer1][opt.aName1][opt.layer2][opt.aName2]=item2;
		}else if("undefined" != typeof(opt.layer2)&&"undefined" == typeof(opt.layer1)){
			arr[0][opt.aName1][opt.layer2][opt.aName2]=item2;
		}else if("undefined" != typeof(opt.layer1)&&"undefined" == typeof(opt.layer2)){
			arr[opt.layer1][opt.aName1][0][opt.aName2]=item2;
		}else{
			arr[0][opt.aName1][0][opt.aName2]=item2;
		}
	}
	
	return arr;
}

//jsonSource,json,layer0,aName0,layer1,aName1,layer2,aName2
//选定特定行记录增加明细记录
$.updateJsonItem = function(opt ){
	if("undefined" !=typeof(opt.aName1)){
		if("undefined" !=typeof(opt.layer1)){
			opt.jsonSource[opt.layer1][opt.aName1]=opt.json;
			if("undefined" !=typeof(opt.layer2)&&"undefined" !=typeof(opt.aName2)){
				opt.jsonSource[opt.layer1][opt.aName1][opt.layer2][opt.name2]=json;
				if("undefined" !=typeof(opt.layer3)&&"undefined" !=typeof(opt.aName3)){
					opt.jsonSource[opt.layer1][opt.aName1][opt.laye2][opt.aName2][opt.layer3][opt.aName3]=json;
				}
			}
		}else{
			opt.jsonSource[0][opt.aName1]=opt.json;
			if("undefined" !=typeof(opt.layer2)&&"undefined" !=typeof(opt.aName2)){
				opt.jsonSource[0][opt.aName1][opt.layer2][opt.name2]=json;
				if("undefined" !=typeof(opt.layer3)&&"undefined" !=typeof(opt.aName3)){
					opt.jsonSource[0][opt.aName1][opt.laye2][opt.aName2][opt.layer3][opt.aName3]=json;
				}
			}
		}
	}
	return opt.jsonSource;
}

//页面间通过URL传值 
$.getUrlParam = function(name)
	{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) 
		return decodeURI(r[2]); 
		return null;
}


//定时更新cookie, users的cookie更新后存放90天,userInfo的cookie更新后存放30
function UpdateCookieInfo(){
	var uInfo = JSON.parse($.cookie("userInfo"));
	var users = JSON.parse($.cookie("users"));
	if(uInfo!=null){//cookie不等于null
		var cDate = new Date();
		var uTime = uInfo.UPDATETIME;
		if(cDate.getTime()>uTime){
			var username= uInfo.XTCZY01;
			var password= uInfo.XTCZY02;
			var arr=[];
			var o={};
			o["XTCZY01"]=username;
			o["XTCZY02"]=password;
			arr.push(o);
			var data = visitService("/UserLogin/login.action?XmlData="+JSON.stringify(arr));
				var loginState = data.test;
				if(loginState==1||loginState==2){
					$.cookie("userInfo", null, {path:'/'});
					if(users!=null){
						for(var i=0;i<users.length;i++){
							if(users[i].userName==uInfo.XTCZY01){
								users.splice(i,1);
							}
						}
						if(users.legnght==0){
							$.cookie("users", null, {path:'/'});
						}else{
							$.cookie("users", null, {path:'/'});
							$.cookie("users", JSON.stringify(users), {expires:90,path:'/'});
						}
						
					}
					alert("用户基础信息已做修改,请重新登陆!");
					window.location.href="/gui/fore/login.html";
				}else if(loginState==3){
					var userInfo = data.userInfo;
					var updateDate = new Date();
					updateDate.setTime(updateDate.getTime()+1*24*60*60*1000);//超过1天,cookie需要重新更新
					userInfo.UPDATETIME= updateDate.getTime();
					$.cookie("userInfo", null, {path:'/'});
					$.cookie("userInfo", JSON.stringify(userInfo), {expires:30,path:'/'});
				
				}
		}
	
	}
	
}

//判断浏览器类型
var userAgent = navigator.userAgent.toLowerCase();
var isSafari = userAgent.indexOf("Safari")>=0;
var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
var ua_match = /(trident)(?:.*rv:([\w.]+))?/.exec(userAgent) || /(msie) ([\w.]+)/.exec(userAgent);
var is_ie = ua_match && (ua_match[1] == 'trident' || ua_match[1] == 'msie') ? true : false;


/**
 * ctx 代表上下文根
 * url 默认不传 （新开页面的地址）一般为查询单据号码时用到
 */
function srchdataInfo(ctx,url){
   var e = window.event?window.event:arguments.callee.caller.arguments[0];
   var obj = e.srcElement?e.srcElement:e.target;
   var tr = obj.parentNode.children;
   var td ;
   for(var i=0;i<tr.length;i++){
	   if(tr[i].tagName && tr[i].tagName.toUpperCase() == "INPUT"){
		   td = tr[i];
		   break;
	   }
   }
   if(td){
	   if(is_ie){//IE

         window.showModalDialog(ctx+"/"+((url == null || url == "")?"Srch_DataInfo.jsp":url)+"?tmp=" + Math.random()+"&id=" + td.name , window, "dialogHeight:400px; dialogWidth:500px; dialogTop:200px; dialogLeft:400px; location:no; status:no; help:no; menubar:no; resizable:yes;",true);
	   }else{//非IE
         window.open(ctx+"/"+((url == null || url == "")?"Srch_DataInfo.jsp":url)+"?tmp=" + Math.random()+"&id=" + td.name , window, "height="+450+",width="+550+",status=0,toolbar=no,menubar=no,location=no,scrollbars=no,top="+200+",left="+430+",resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no",true);
	   }
   }
}

/**
 * GRID
 * @param ctx 代表上下文根
 * @param id 代表找XML哪条语句
 */
function srchGridInfo(ctx,id){
  if(is_ie){//IE
    window.showModalDialog(ctx+"/"+"Srch_GridInfo.jsp"+"?tmp=" + Math.random()+"&id=" + id , window, "dialogHeight:400px; dialogWidth:500px; dialogTop:200px; dialogLeft:400px; status:no; help:no; location:no; menubar:no; resizable:yes;");
  }else{//非IE
    window.open(ctx+"/"+"Srch_GridInfo.jsp"+"?tmp=" + Math.random()+"&id=" + id , window, "height="+420+",width="+500+",status=0,toolbar=no,menubar=no,location=no,scrollbars=no,top="+200+",left="+430+",resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no",true);
  }
}

//清空Grid
function clearGrid(result){
  if(result == null || result.grid == null){
    return;
  }
  if(result.grid.activeEditor != null){
	result.grid.activeEditor.completeEdit();//编辑区失去焦点
  }
  var grid = result.grid;
  var rows = grid.getStore().getCount();
  for(var i=rows-1;i>=0;i--){
    result.delRows(i);
  }
}

//显示DIV信息
function showXX(id){
  $("#"+id).attr("title",$("#"+id).val());
}

//清除DIV信息
function clearHtml(id1,id2){
  if((window.event ? event.keyCode:event.which) == 8){
    $("#"+id2).val("");
  }
}

//创建XHR对象
function createCORSRequest(method, url) {
	url = url+"&sid=" + Math.random();
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        //Chrome/Safari/Firefox浏览器中的XHR对象
        xhr.open(method, url, true);
    } 
    else if (typeof XDomainRequest != "undefined") {
        //在IE浏览器中创建XDomainRequest对象
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } 
    else {
        //CORS不被支持.
        xhr = null;
    }
    return xhr;
}
//var proxyData=null;
var json={"aa":""};
//创建CORS请求
function makeCorsRequest(url, json, proxyData) {
    // bibliographica.org支持CORS请求
    //var url = 'http://192.13.4.115:8080/v9_new/form/createForm.do';
    xhr2 = createCORSRequest('POST', url);
    if (!xhr2) {
        alert('您的浏览器不支持跨源请求');
        return;
    }
    //处理服务器端响应
    xhr2.onload = function(){
    	//proxy=xhr2.responseText;
        proxyData = JSON.parse(xhr2.responseText);
        alert(xhr2.responseText);
    };
    xhr2.onerror = function() {
        alert('跨源请求失败！');
    };
	//alert(json)

    xhr2.send();
}

/**
 * function: jQuery限制input、textarea中可输入的最大字符数
 * @param {} max
 * @return {}
 * 调用示例：$('#test").maxLength(3000); 
 * author jhj add 2014-07-19
 */
jQuery.fn.maxLength = function(max){ 
  return this.each(function(){
    var type = this.tagName.toLowerCase(); 
    var inputType = this.type? this.type.toLowerCase() : null; 
    if(type == "input" && inputType == "text" || inputType == "password"){ 
      this.maxLength = max; 
    } else if(type == "textarea"){
      this.onkeypress = function(e){ 
        var ob = e || event; 
        var keyCode = ob.keyCode; 
        var hasSelection = document.selection? document.selection.createRange().text.length > 0 :this.selectionStart != this.selectionEnd; 
          return !(this.value.replace(/[^\u0000-\u00ff]/g,"aa").length >= max && (keyCode > 50 || keyCode == 32 || keyCode == 0 || keyCode == 13) &&!ob.ctrlKey && !ob.altKey && !hasSelection); 
        }; 
        this.onkeyup = function(){ 
          if(this.value.replace(/[^\u0000-\u00ff]/g,"aa").length > max){ 
            this.value = this.value.substring(0,max); 
          } 
        };
      }
   });
};

//脚本获取根路劲
function getBasePath(){ 
   var obj=window.location; 
   var contextPath=obj.pathname.split("/")[1]; 
   //var basePath=obj.protocol+"//"+obj.host+"/"+contextPath; 
   return "/"+contextPath;
} 

//获取浏览器请求的某个参数的值
function getParam(paramName){
  var paramValue = "";
  var isFound = false;
  var arrSource = new Array();
  if(this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
    arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&");
    i = 0;
    while(i < arrSource.length && !isFound){
      if(arrSource[i].indexOf("=") > 0){
        if(arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase()){
          paramValue = arrSource[i].split("=")[1];
		  isFound = true;
		}
	  }
      i++;
	}
  }
  return paramValue;
}


function moveDiv(alpha,title,m_div){
	var fx; var fy;   
    var flag = false; 
    m_div.mousemove(function(event){ 
      if(flag) {   
        var myEvent = event || window.event;    
        changeXY(m_div,myEvent); 
      }  
    });  

    m_div.mouseup(function() { 
      title.unbind("mousemove"); 
      flag = false; 
    });  

    alpha.css("width",$(window).width()); 
    alpha.css("height",$(window).height()); 

    alpha.mousemove(function(event){ 
      if(flag) {   
        var myEvent = event || window.event; 
        changeXY(m_div,myEvent); 
      } 
    });  

    alpha.mouseup(function() { 
      title.unbind("mousemove"); 
      flag = false;  
    });  

    title.mousedown(function(event) { 
      var t = $(this);   
      var myEvent = event || window.event; 
      fx = myEvent.clientX; 
      fy = myEvent.clientY; 

      t.mousemove(function(event){  
	    var myEvent = event || window.event;  
        changeXY(m_div,myEvent); 
        flag = true; 
      }); 
    });

    function changeXY(m_div,myEvent) { 
      if(fx > myEvent.clientX) {  
        var change_top_x = parseInt(m_div.css("left"))-(fx-myEvent.clientX); 
        //测试是否超出左边窗口
        if(change_top_x < 5) { 
          change_top_x = 5; 
          //flag = false;  
          // title.unbind("mousemove"); 
        }  
        m_div.css("left",change_top_x+"px"); 
        fx = myEvent.clientX; 
      }  
      if(fx < myEvent.clientX) {
        var change_top_x = parseInt(m_div.css("left"))+(myEvent.clientX-fx); 
        //测试是否超出窗口右边
        if(change_top_x > ($(window).width() - parseInt(m_div.css("width"))-5)) {  
          change_top_x = ($(window).width() - parseInt(m_div.css("width"))-5); 
          //flag = false;  
          //title.unbind("mousemove"); 
        }   
        m_div.css("left",change_top_x+"px"); 
        fx = myEvent.clientX; 
      }  

      if(fy > myEvent.clientY) {  
        var change_top_y = parseInt(m_div.css("top"))-(fy-myEvent.clientY);
        //测试是否超出窗口顶部
        if(change_top_y < 5) { 
          change_top_y = 5; 
          // flag = false;  
          // title.unbind("mousemove"); 
        }  
        m_div.css("top",change_top_y+"px"); 
        fy = myEvent.clientY; 
      }  
      if(fy < myEvent.clientY) {  
        var change_top_y = parseInt(m_div.css("top"))+(myEvent.clientY-fy); 
        //测试是否超出窗口底部
        if(change_top_y > ($(window).height() - parseInt(m_div.css("height"))-5)) {  
          change_top_y = ($(window).height() - parseInt(m_div.css("height"))-5); 
          //flag = false;  
          //title.unbind("mousemove"); 
        }  
        m_div.css("top",change_top_y+"px"); 
        fy = myEvent.clientY; 
      } 
    }  
    title.mouseup(function(){ 
      title.unbind("mousemove"); 
      flag = false; 
    });
    title.mouseout(function(){  
      title.unbind("mousemove"); 
    });
}