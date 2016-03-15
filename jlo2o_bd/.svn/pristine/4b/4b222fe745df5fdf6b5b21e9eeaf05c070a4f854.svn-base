var JLShowBMGW={};
var $thisInp;
var $showDivObj;
var hidname;
var $this;

//打开界面
JLShowBMGW.show = function(obj){
	$this=$(obj);
	$hisInp=$(obj);
	showDivId=$hisInp.attr("showDivId");
	hidname=$hisInp.attr("hidInpName");
	$showDivObj=$('#'+showDivId);
	//JLShowBMGW.showAdd(showDivId);
	JLShowBMGW.bmInit();
	JLShowBMGW.gwInit(null);
	$showDivObj.show();
	$('.JLShowBMGW').show();
}



//部门初始化
JLShowBMGW.bmInit=function(){
	var XmlData="";
	var url =pubJson.FormUrl+"/JLSoftInterface/selectAllBM.do";
	var returnData=visit(url,XmlData);
	JLShowBMGW.bmAdd(returnData);
}
//岗位初始化
JLShowBMGW.gwInit=function(xmlData){
	if(xmlData!=null){
		var obj=JSON.parse(xmlData);
		xmlData=obj.SJBM==undefined?null:"["+xmlData+"]";
	}
	var url = pubJson.PcrmUrl + "/OperatorAction/selectAllStationByBM.do?s=" + Math.random();
	var stationData = visit(url, xmlData);
	JLShowBMGW.gwAdd(stationData.list);
}

//拼接界面
JLShowBMGW.showAdd=function(showDivId){
	var str='<div class="JLShowBMGW" onClick=popClose();></div>'+
			'<div class="JLShowBMGW_main" id="'+showDivId+'">'+
				'<div class="title" style="background: #CCC;">请选择</div>'+
				'<div class="JLShowBMGW_FEN" cum="bm"/><ul class="bmgw_nav add"><div class="title" divlx="bm">添加部门</div></ul></div>'+
				'<div class="JLShowBMGW_FEN"><ul class="bmgw_nav add"><div class="title" divlx="gw">添加岗位<a class="chacha" onclick="JLShowBMGW.clearGW();">X</a></div></ul></div>'+
				'<div class="JLShowBMGW_FEN" style="width: 77px; border: 0;"><a class="zhuan" onclick="JLShowBMGW.bmgwRight();">→</a>'+
												  '<a class="zhuan" style="margin-top: 10px;" onclick="JLShowBMGW.bmgwLeft();" >←</a></div>'+
				'<div class="JLShowBMGW_FEN" style="width: 200px; float: right;"><ul class="bmgw_nav add">'+
				'<div class="title" divlx="yxz">已选择<a class="chacha" onclick="JLShowBMGW.clearYXZ();">X</a></div></ul></div>'+
				'<div class="title"><a class="bmgw_bot" style="margin-left: 300px;" onclick="JLShowBMGW.returnData();">确定</a><a class="bmgw_bot" onclick="popClose();">取消</a></div>'+
			'</div>'
	
	$(document.body).append(str);
	
}

//拼接部门
JLShowBMGW.bmAdd=function(json){
	$showDivObj.find("div[divlx='bm']").parent("ul").find("li").remove();
	var data=json.returnList;
	var len=data.length;
	bmJBList=[];
	for(var i=0;i<len;i++){
		var curData=data[i];
		var curBMJB = bmJBList[curData.BMJB];
		if(curBMJB==undefined){
			var array=[];
			array[0]=curData;
			bmJBList[curData.BMJB]=array;
		}else{
			curBMJB[curBMJB.length]=curData;
		}
	}
	
	var len= bmJBList.length;
	var strList=[];
	for(var i=len; i>=0 ;i-- ){
		var kongge="";
		for(var y=0; y<i ;y++){
			kongge+="&ensp;";
		}
		if(bmJBList[i]==null){
			continue;
		}else{
			strList[i]=[];
			for(var j = 0; j <bmJBList[i].length ;j++){
				if(strList[i].length==0){
					strList[i][0]={};
					strList[i][0].SJBM=bmJBList[i][j].SJBM;
					strList[i][0].STR="<li title='"+bmJBList[i][j].BMMC+"' ><a czlx='bm' bm='"+bmJBList[i][j].BMDM+"' json='"+JSON.stringify(bmJBList[i][j])+"' >"+kongge+bmJBList[i][j].BMMC+"</a></li>";
				}else{
					var bool=0;
					for(var x = 0; x <strList[i].length ;x++){
						if(strList[i][x].SJBM==bmJBList[i][j].SJBM){
							strList[i][x].STR+="<li title='"+bmJBList[i][j].BMMC+"'><a czlx='bm' bm='"+bmJBList[i][j].BMDM+"' json='"+JSON.stringify(bmJBList[i][j])+"' >"+kongge+bmJBList[i][j].BMMC+"</a></li>";
							bool=1;
							break;
						}
					}
					if(bool==0){
						var strlen=strList[i].length;
						strList[i][strlen]={};
						strList[i][strlen].SJBM=bmJBList[i][j].SJBM;
						strList[i][strlen].STR="<li title='"+bmJBList[i][j].BMMC+"'><a czlx='bm' bm='"+bmJBList[i][j].BMDM+"' json='"+JSON.stringify(bmJBList[i][j])+"' >"+kongge+bmJBList[i][j].BMMC+"</a></li>";
					}
				}
			}
		}
	}
	
	for(var i = 0 ; i < strList.length ; i++){
		if(strList[i]!=undefined){
			for(var j = 0 ; j < strList[i].length; j++){
				var str=strList[i][j].STR;
				if(strList[i][j].SJBM=='00'){
					$showDivObj.find("div[divlx='bm']").after($(str));
				}else{
					$("a[bm='"+strList[i][j].SJBM+"']").after("<span class='bmxl'>+</span>");
					$("a[bm='"+strList[i][j].SJBM+"']").parent("li").after("<ul class='fen'>"+str+"</ul>");
				}
				
			}
		}
		
	}
	
	$(".bmxl").click(function(){
		var label_zt = $(this).text();//children("span").
		if(label_zt == "-"){
			$(this).text("+");
			$(this).parent("li").next().slideUp(300);
		}else if(label_zt == "+"){
			$(this).text("-");
			$(this).parent("li").next().slideDown(300);
		}
	});
	
	$("a[czlx='bm']").click(function(){
		   var $xza=$(this);
		   if($xza.attr("class")=="daicaozuo"){
			   JLShowBMGW.clearBM();
			   JLShowBMGW.clearGW();
		   }else{
			   JLShowBMGW.clearBM();
			   JLShowBMGW.clearGW();
			   $xza.attr("class","daicaozuo");
		   }
		   JLShowBMGW.gwInit($xza.attr("json"));
	});
}


//拼接岗位
JLShowBMGW.gwAdd=function(gwJBList){
	var str="";
	$showDivObj.find("div[divlx='gw']").parent("ul").find("li").remove();
	for(var i=0;i<gwJBList.length;i++){
		str+="<li title='"+gwJBList[i].GWFZMC+"'><a czlx='gw' json='"+JSON.stringify(gwJBList[i])+"' >"+gwJBList[i].GWFZMC+"</a></li>"
	}
	if(str!=""){
		$showDivObj.find("div[divlx='gw']").after(str);
	}else{
		$showDivObj.find("div[divlx='gw']").after("<li><a czlx='xx'>当前部门下没有岗位!</a></li>");
	}
	$("a[czlx='gw']").click(function(){
		   var $xza=$(this);
		   if($xza.attr("class")=="daicaozuo"){
			   JLShowBMGW.clearBM();
			   $xza.attr("class","");
		   }else{
			   JLShowBMGW.clearBM();
			   $xza.attr("class","daicaozuo");
		   }
	});
}

//添加至输出框
JLShowBMGW.bmgwRight=function(){
	$objList=$("a[class='daicaozuo']");
	var $json=$("a[czlx='dsc']");
	var str="";
	for(var i=0; i<$objList.length;i++){
		$obj=$($objList[i]);
		var json = JSON.parse($obj.attr("json"));
		var text="";
		var xzbj="";
		if($obj.attr("czlx")=='bm'){
			text=json.BMMC;
			xzbj="bm";
		}else if($obj.attr("czlx")=='gw'){
			var gwbmmc=json.BMMC==undefined?"":(json.BMMC+"-");
			text=gwbmmc+json.GWFZMC;
			xzbj="gw";
		}else{
			continue;
		}
		var ishave=0;
		for(var j=0; j<$json.length;j++){
			var curJson=JSON.parse($($json[j]).attr("json"));
			if($($json[j]).attr("xzbj")=="bm"&&curJson.BMDM==json.BMDM){
				ishave=1;
			}else if($($json[j]).attr("xzbj")=="gw"&&curJson.GWFZBH==json.GWFZBH&&curJson.BMDM==json.BMDM){
				ishave=1;
			}
		}
		if(ishave==0){
			str+="<li title='"+text+"' ><a czlx='dsc' xzbj='"+xzbj+"' json='"+JSON.stringify(json)+"' >"+text+"</a></li>"
		}
	}
	$showDivObj.find("div[divlx='yxz']").after(str);
	$("a[czlx='dsc']").unbind("click");
	$("a[czlx='dsc']").click(function(){
		var $xza=$(this);
		if($xza.attr("class")=="dsc"){
			$xza.attr("class","");
		}else{
			$xza.attr("class","dsc");
		}
	});
	
}
//从输出框移除
JLShowBMGW.bmgwLeft=function(){
	$("a[class='dsc']").parent("li").remove();
}


//清空选择的部门
JLShowBMGW.clearBM=function(){
	$("a[czlx='bm']").attr("class","");
}
//清空选择的岗位
JLShowBMGW.clearGW=function(){
	$("a[czlx='gw']").attr("class","");
	
}
//清空已选
JLShowBMGW.clearYXZ=function(){
	$("a[czlx='dsc']").attr("class","dsc");
	
}


//确定
JLShowBMGW.returnData=function(){
	var $json=$("a[czlx='dsc']");
	var json=[];
	var text="";
	for(var i=0; i<$json.length;i++){
		json[i]=JSON.parse($($json[i]).attr("json"));
		text+=$($json[i]).parent("li").attr("title");
		if(i!=$json.length-1){
			text+=",";
		}
	}
	var jjson = {};
	jjson.JSON=json;
	$("input[name='"+hidname+"']").val(JSON.stringify(jjson));
	$this.val(text);
	JLShowBMGW.popClose();
}



//关闭查询层
JLShowBMGW.popClose=function(){ 
	$('.JLShowBMGW').hide();
	$('.JLShowBMGW_main').hide();
}
