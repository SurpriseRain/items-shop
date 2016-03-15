//根目录
var rootPath="";
function setRootPath(ctx){
   this.rootPath = ctx;
}

//脚本获取根路劲
function getBasePath(){ 
   var obj=window.location; 
   var contextPath=obj.pathname.split("/")[1]; 
   return "/"+contextPath;
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

//(新)ajax 访问服务器端,异步访问服务器端，获取JSON返回结果，val是连接地址
function ajaxQ(obj){
    var opt = {
        "type" : obj.type || "get",
        "url" : encodeURI(__formatUrl(obj.url)),
        "data" : obj.data || {},
        "dataType" : obj.dataType || "JSON",
        "callback" : obj.callback || function(){ alert("操作成功"); },
        "error":function(msg){
            if(msg && msg === ""){
                //alert("操作失败");
                return;
            }
        }
    };

    $.ajax({
        type: opt.type,  //请求方式
        dataType: opt.dataType,
        url:opt.url,
        data:opt.data,
        contentType:"application/x-www-form-urlencoded; charset=UTF-8",
        timeout : 60000,
        success: function(data){
            if (data != null){
                try{
                    var json = JSON.parse(JSON.stringify(data));
                    //var json = $.parseJSON(data);
                    opt.callback(json.data);
                }catch(e){
                    //opt.error("请求异常");
                }
            }
        },
        //获取错误信息
        error:function(data,XMLHttpRequest, textStatus, errorThrown) {
            
        }
    });
}

function __formatUrl(val){
    //var url = "";

    if(val.lastIndexOf("?") != -1){
        if(val.lastIndexOf("?") !== val.length - 1){
            val+="&r="+Math.random();
        }else{
            val+="r="+Math.random();
        }
    }else{
        val+="?r="+Math.random();
    }

    return val;
}


function visitSvcAsync(val,callback){
	if(!val){
		return "";
	}

    if(!callback || typeof callback !== "function"){
        return "";
    }

	var returnValue;
	var url = "";
	
	if(val.lastIndexOf("?")!=-1){
		if(val.lastIndexOf("?")<val.length){
			val+="&r="+Math.random();
		}else{
			val+="r="+Math.random();
		}		
	}else{
		val+="?r="+Math.random();
	}
	
	url = val;
	
	if(val.indexOf("?")==-1){
		url=val+"?r="+Math.random();
	}else{
		url=val+"&r="+Math.random();
	}
	
    $.ajax({
	    type: "GET",  //请求方式
	    dataType: "JSON",
	    url:encodeURI(url),
	    //data:{},
	    success: function(data){
	      if (data != null){
	    	  //alert(JSON.stringify(data));
	        try{
	          var json = JSON.parse(JSON.stringify(data)); //jQuery.parseJSON(data);
	          //JSON为map值直接获取:json.*
	          //returnValue = JSON.stringify(json.data);
	          //returnValue = json.data;
			  callback(json.data);
	        }catch(e){
	          return;
	        }
	      }
	    },
        //获取错误信息
    	error:function(data,XMLHttpRequest, textStatus, errorThrown) {
	    	//alert(data.responseText+"获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
        }
	  });
}

//ajax 访问服务器端,同步，获取JSON返回结果，val是连接地址
function visitService(val){
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
	/**if(val.indexOf("?")==-1){
		url=val+"?r="+Math.random();
	}else{
		url=val+"&r="+Math.random();
	}*/
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
    	error:function(data,XMLHttpRequest, textStatus, errorThrown) {
	    	//alert(data.responseText+"获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
        }
	  });
    return returnValue;
}

//ajax 访问服务器端,异步，获取JSON返回结果，val是连接地址
function visitServiceAsync(val){
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
	    	//alert(data.responseText+"获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
        }
	  });
    return returnValue;
}

//跟数据库交互获取展示数据
function visitServiceGrid(val) {
	var returnValue;
	$.ajax( {
		type : "POST", // 请求方式
		async : false, // true表示异步 false表示同步
		url : encodeURI(val),
		data : {},
		success : function(data) {
			if (data != null) {
				try {
					var json = jQuery.parseJSON(data);
					returnValue = json;
				} catch (e) {
					return;
				}
			}
		},// 获取错误信息
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("获取数据失败，状态是：" + textStatus + "+" + XMLHttpRequest + "+" + errorThrown);
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
		    alert(data.responseText+textStatus);
		}
	});
	return returnVal;
}

//用变量创建新方法然后调用
function loadDATA(returnValue,functionName){
    this.func = new Function(functionName+"();"); 
    returnData = returnValue;
	func();
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
		return unescape(r[2]); 
		return null;
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

//ajax 异步访问服务器端，获取JSON返回结果，val是连接地址
function visitService1(val,functionName){
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
					loadDATA(returnValue,functionName);
				}catch(e){
					return;
				}
			}
		},
		//获取错误信息
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("获取数据失败，状态是："+textStatus+"+"+XMLHttpRequest+"+"+errorThrown);
		}
	});
	return returnValue;
}

//弹出遮罩等待层
function openWait(){
    $("#header").append('<div id="index-waiting" style="position:fixed; width:100%; height:2000px; background-color:rgba(1,1,1,0.2); z-index:9999999; top:0px;"><div style="width:364px; height:84px; background-color:rgba(1,1,1,0.3); position:absolute; left:50%; margin-left:-184px; top:240px; border:1px solid #2f80eb;"><div style="width:350px; height:70px; margin:7px; background-color:#fff;"><img src="/img/load.gif" style="width:30px; margin:20px; float:left;" /><span style=" float:left; font-family:microsoft yahei; line-height:30px; margin:20px 0; font-size:20px; color:#ccc;">正在努力加载中，请稍等...</span></div></div></div>')
}

//隐藏遮罩等待层
function closeWait(){
	$("#index-waiting").remove();
}

//页面定位
function scrollOffset(scroll_offset) {
    $("body,html").animate({
      scrollTop: scroll_offset.top
    }, 0);
}
// 提示框
function dlgShow(option){
	easyDialog.open({
        container : {
            header : option["title"] || "友情提示",
            content : option["content"],
            yesFn : option["yesFn"] || null,
			noFn :  option["noFn"] || null,
			yesText : option["yesText"] || '确定',
			noText : option["noText"] || '取消'
        },
        drag : option["drag"] || true,
        autoClose : option["autoClose"] || 0,
        lock : true,
        callback : option["callback"] || null,
    });
}

//判断是否是图片
function checkPic(id){
		//filepath=$("#"+id).val();
		filepath = document.getElementById(id).value;
		var extStart=filepath.lastIndexOf(".");
		var ext=filepath.substring(extStart,filepath.length).toUpperCase();
		if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"&&ext!=".SVG"){
			//alert("图片限于png,gif,jpeg,jpg格式");
		}else{
			return true;
		}
}

//获取图片路径
function getObjectURL(file) {
	var url = null ; 
	if (window.createObjectURL!=undefined) {
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) {
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) {
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
}

//增加页面的统计
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?e68ecf8aab7ee68a3adb85faa3ac997c";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();