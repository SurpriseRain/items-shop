/**
 * name:查询我的订单
 * author:ly
 * date:2015-03-26
 */

var DefStaPer = {};
var rowNum = 0; //结果总数
var fileName = ""; //分页生成文件名
var obj=""; //操作对象ID
var changeColumnObj=""
var parasData=[]; //传参结果集
DefStaPer.multiPage;//分页参数
DefStaPer.newclos;//换行参数
DefStaPer.rowClick;//行点击事件
DefStaPer.data;//数据展示
DefStaPer.columnsArr;//列头
DefStaPer.afterUrl;//明细url

//退货/退款连接
function select_goods(obj){
	location.href="/customer/qfy/back/thgl/returnapply.html?XSDD01="+obj;
}
//订单明细
function goodslist(XSDD04,XSDD01,DDZT,ZCXX01){
	location.href="/customer/qfy/back/thgl/thmx.html?XSDD04="+XSDD04+"&XSDD01="+XSDD01+"&DDZT="+DDZT+"&ZCXX01="+ZCXX01+"#spitem";
}
//确认收货
function updata_6(obj){
	//----2015.10.13. add 确认收货增加确认框
	if(confirm('此订单货物已收到?')){
	var XmlData=[{"XSDD01":obj}];
	var url="/OrderFlowManage/OkMyOrder.action?XmlData="+JSON.stringify(XmlData);
	var rData=visitService(url);
	if(rData.STATE=="1"){
		alert("操作成功!");
		location.href="/customer/qfy/back/ddgl/myorder.html";
		}else{
			alert("操作失败!");
			}
	}
}
//获取公用默认图片路径
var imgUrl=pubJson.path_sptp+"/sptp/";

/****************************列头展示开始*******************************************/
//大数据量查询条件GRID查询时
function visitGrid(val,XmlData) {
	parasData=jQuery.parseJSON(XmlData);
	var returnValue;
	$.ajax( {
		type : "POST", // 请求方式
		async : false, // true表示异步 false表示同步
		url : encodeURI(val),
		data : {XmlData:XmlData},
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
			alert("获取数据失败，状态是：" + textStatus + "+" + XMLHttpRequest + "+" + errorThrown);
		}
	});
	return returnValue;
}

//加载初始值、文件名、总数量
DefStaPer.load = function(returnData){
    DefStaPer.data=returnData.data;
    //给文件名赋值
    fileName = returnData.fileName;
    //查询总数
    var sumXmlData = parasData[0];
    sumXmlData["sqlid"]=sumXmlData.sqlid + "_sum";
    sumXmlData["QryType"]="";
    var returnVal = visit("/jlquery/selecto2o.action",JSON.stringify(parasData));
    rowNum = returnVal[0].COUNT;
}

$(".dyryqx").remove();
//打印页面数据
DefStaPer.write = function(o,columnName){
	DefStaPer.writeData(o,columnName);
    //判断是否分页
    if(DefStaPer.multiPage=="true"){
	    /****************************分页展示开始*******************************************/
	    $(".Pagination").remove();
		$('.center').kkPages({ 
	 	   PagesClass:'tr.jl', //需要分页的元素
	 	   RowNum:rowNum,
	 	   FileName:fileName,
	 	   PagesMth:pubJson.PagesMth, //每页显示个数 
	 	   PagesNavMth:pubJson.PagesNavMth //显示导航个数
	 	});
		/****************************分页展示结束*******************************************/
    }
}

DefStaPer.writeData = function(o,columnName){
	obj=o;
	changeColumnObj=columnName;

	var s = '';
		s = '<div class="mainbox mt10" id="clecls"><h2 ><span class="fl" style="padding-left:200px; font-size:16px;">商品信息</span>';
		s +='<span class="fr" style="padding-left:270px; font-size:16px;">金额</span>';
		s +='<span class="fr" style="padding-left:120px; font-size:16px;">商品操作</span></h2></div>';
	    s +='<div class="car_body">';
	  $.each(DefStaPer.data, function(i, val) {
		    var result = val.SPLIST;
			s += '<div class="car_top">';
				s += '<div class="car_checkbox">';
				if(val.DDZT == "3"){
					s +='<input  class="checkboxs" type="checkbox" id="'+val.XSDD01+'" code="'+val.XSDD02+'" code1="'+val.DDZT+'" name="DDBH"  value="'+val.XSDD01+'"/>';
				}else{
					s +='&nbsp;&nbsp;&nbsp;&nbsp;';
				}
				s += '</div><div class="car_time">订货时间：';
				s += val.XSDD04;
				s += '</div>';
				s += '<div class="car_ddh order_style">';
				s += column_header["carlist_orderno"]+'：'+val.XSDD01;
				s += '</div>';
				s += '<div class="car_ddh order_style">';
				s += '支付方式'+'：';
				if(val.XSDD50 == '0'){
					s+= '在线支付';
				}
				else if(val.XSDD50 == '1'){
					s+= '线下支付';
				}
				s += '</div>';
				s += '<div class="car_dp order_style" style="float:right; margin-right:20px; text-decoration:none;">';
				s += '<a href="/customer/qfy/showDpxx/shopShow.html?gsid='+val.ZCXX01+'&gsmc='+escape(val.ZCXX02)+'"  target="_blank" >';
				s += column_header["carlist_wldw"]+'：'+val.ZCXX02;
				s += '</a>';
				s += '</div>';
			s += '</div>';
			
		    s += '<div class="car_item">';
				s += '<div class="goodz goodz_name">';
				for(var i=0; i<result.length;i++){
					s += '<div><a href="/customer/qfy/showProduct/product.html?spxx01='+result[i].SPXX01+'&zcxx01='+val.ZCXX01+'&gsid='+val.ZCXX01+'&gsmc='+escape(val.ZCXX02)+'" target="_blank"><img title="'+result[i].SPXX04+'" class="imgsize" src="'+imgUrl+val.ZCXX01+'/'+result[i].SPXX02+'/images/'+result[i].SPXX02+'_1_small.'+result[i].SPTP02+'" /></a></div>';
				}
				s += '</div>';

				s += '<div class="goodz jg">';
				s += '<div style="font-size:15px;width:113px;';
				if(val.DDZT=="3"){
					s+='color:red;';
				}
				s +='">'+eval("changeColumns.W_DJZX02_"+val.DDZT)+'<font class="scx"></font></div>';
				s += '<div><font>'+accounting.formatMoney(val.XSDD02)+'</font></div>';
				s += '</div>';

				s += '<div class="goodz sl goods_style">';
				if(val.DDZT=="3"){
					s += '<div class="detailss"><a  onclick="go_account(\''+val.XSDD01+'\',\''+val.XSDD02+'\')">去结算</a></div>';
				}
				s += '<div class="detailss"><a onclick="goodslist(\''+val.XSDD04+'\',\''+val.XSDD01+'\',\''+val.DDZT+'\',\''+val.ZCXX01+'\')"> 查看详情</a></div>';
				if(val.DDZT=="6"){
					s += '<div class="detailss"><a>物流信息</a></div>';
					s += '<div class="detailss"><a onclick="goodslist(\''+val.XSDD04+'\',\''+val.XSDD01+'\',\''+val.DDZT+'\',\''+val.ZCXX01+'\')"> 评价/晒单</a></div>';
					s += '<div class="detailss"><a onclick="select_goods(\''+val.XSDD01+'\')">退货/退款</a></div>';
				}
				if(val.DDZT=="5"){
					s += '<div class="detailss"><a href="#top" onclick="updata_6(\''+val.XSDD01+'\')">确认收货</a></div>';
				}
				s += '</div>';
			s += '</div>';
	  }); 
	 
	  s += '</div>';

	    $(o).html(s);
	    $("img").error(function() {
        	$(this).attr("src", pubJson.defaultImgUrl);
        });
	   
	    $(".selectall").on("click",function(){
	    	 var obj = $("input[class='selectall']"); 
	      	 var cks=$("input[name='DDBH']");
	      	 for(var i=0;i<cks.length;i++) {
	      		 if(document.getElementById(cks[i].value).getAttribute("code1")=="3"){
	      			 cks[i].checked = obj[0].checked;
	      		 }
	      	 }
	    });
	    $(".checkboxs").on("click",function(){
	    	 var cks=$("input[name='DDBH']");
	    	 var obj = $("input[class='selectall']"); 
	      	 for(var i=0;i<cks.length;i++) {
	      		 if(!cks[i].checked){
	      			 obj[0].checked = false;
	      			 break;
	      		 }else{
	      			 obj[0].checked = cks[i].checked;
	      		 }
	      	 }
	    });
	    
	    //我的订单合并支付方法
	    $("#accout").on("click",function(){
			var cks=$("input[name='DDBH']");
			if (cks.length == 0 ) {
				alert("没有待支付订单");
				return false;
			}  
			var ddbhlist ="";
			var money =[];
			for(var i=0;i<cks.length;i++) {
				 /*if(cks[i].checked){
					var pid = document.getElementById(cks[i].value).getAttribute("code");
					money.push(parseInt(pid));
					ddbhlist += cks[i].value+",";
				 }*/
				 var ddbhnum=$("input[name='DDBH']").is(":checked");
				 if(ddbhnum==true){
					 if(cks[i].checked){
							var pid = document.getElementById(cks[i].value).getAttribute("code");
							money.push(parseFloat(pid));
							ddbhlist += cks[i].value+",";
						 }
				 }else{
					 alert("没有选中的订单！");
					 return false;
				 }
			}
			var zfje=0;
			for(var i=0;i<money.length;i++){
				zfje += money[i];
			}
	      	var DDBHLIST =ddbhlist.substring(0, ddbhlist.length-1);
	      	var XmlData=[{"HBID":usercookie.ZCXX01,"DDBHLIST":DDBHLIST,"ZFJE":zfje}];
	    	var url="/OrderFlowManage/ConsolidatedPayment.action?XmlData="+JSON.stringify(XmlData);
	    	var rData=visitService(url);
	    	if(rData.STATE=="1"){
	    		window.parent.location.href="/customer/qfy/shopping/cartSuccess.html?xsdd01="+rData.DDBHLIST+"&zfje="+rData.ZFJE+"&JYDH="+rData.JYDH+"&type="+rData.type;
    		}
	    	else if(rData.STATE=="2"){
	    		alert("线上支付与线下支付不能合并付款!");
	    	}
	    	else{
    			alert("操作失败!");
    		}
	    });
}

function go_account(obj,objs){
  	var XmlData=[{"HBID":usercookie.ZCXX01,"DDBHLIST":obj,"ZFJE":objs}];
	var url="/OrderFlowManage/ConsolidatedPayment.action?XmlData="+JSON.stringify(XmlData);
	var rData=visitService(url);
	if(rData.STATE=="1"){
		window.parent.location.href="/customer/qfy/shopping/cartSuccess.html?xsdd01="+rData.DDBHLIST+"&zfje="+rData.ZFJE+"&JYDH="+rData.JYDH+"&type="+rData.type;
	}else{
		alert("操作失败!");
	}
}


DefStaPer.init = function(o) {
	var columnName = changeColumns;
	if ($(o).attr('newclos') != 'undefine')DefStaPer.newclos=$(o).attr('newclos');
	if ($(o).attr('data') != 'undefine')DefStaPer.data=eval($(o).attr('data'));
	if ($(o).attr('multiPage') != 'undefine')DefStaPer.multiPage=$(o).attr('multiPage');
	if ($(o).attr('afterUrl') != 'undefine')DefStaPer.afterUrl=$(o).attr('afterUrl');
	if ($(o).attr('columnsArr') != 'undefine')DefStaPer.columnsArr=eval($(o).attr('columnsArr'));
	if($(o).attr('rowClick') != 'undefine'){
		DefStaPer.rowClick=$(o).attr('rowClick');
	}else{
		DefStaPer.rowClick="false";
	}
	if($(o).attr('height') != 'undefine'){
		DefStaPer.height=$(o).attr('height');
	}else{
		DefStaPer.height="2000";
	}
	DefStaPer.load(initData());
	if(columnName!=undefined){
	    DefStaPer.write(o,columnName);
	}else{
		DefStaPer.write(o);
	}
}

//行点击事件，弹出明细窗口
function rowClick(rowIndex){
	var rowId="tr_"+rowIndex;
	var rowObj = $("#"+rowId+"");
	if(DefStaPer.afterUrl != undefined && DefStaPer.afterUrl != ""&&DefStaPer.rowClick=="open"){
		parent.ajaxGrid(DefStaPer.afterUrl,DefStaPer.height);
		parent.$("#alertDIV").load(function() {
			if(typeof(parent.$("#alertDIV")[0].contentWindow.openUrlInit)=='function'){
				parent.$("#alertDIV")[0].contentWindow.openUrlInit(rowObj);
			}
		})
	}else if(DefStaPer.rowClick=="backFill"){
		backFun(rowObj);
	}
}

//翻页重新输出
function pageTurnWrite(data){
	DefStaPer.data = data;
	DefStaPer.writeData(obj,changeColumnObj);
}
