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
function select_goods(obj,main){
	//location.href="/customer/sydjt/back/thgl/returnapply.html?XSDD01="+obj;
	$(main).load("/customer/sydjt/back/thgl/returnapply.html?XSDD01="+obj);
	JL.setDivUrl("url","/customer/sydjt/back/thgl/returnapply.html?XSDD01="+obj);
}
//订单明细
function goodslist(XSDD04,XSDD01,DDZT,ZCXX01,main){
	//location.href="/customer/sydjt/back/thgl/thmx.html?XSDD04="+XSDD04+"&XSDD01="+XSDD01+"&DDZT="+DDZT+"&ZCXX01="+ZCXX01+"#spitem";
	var url="/customer/sydjt/back/ddgl/my_order_infor.html?XSDD04="+escape(XSDD04)+"&XSDD01="+XSDD01+"&DDZT="+DDZT+"&ZCXX01="+ZCXX01+"#spitem";//+"#spitem";
	JL.setDivUrl("url",url);
	$(main).load("/customer/sydjt/back/ddgl/my_order_infor.html?XSDD04="+escape(XSDD04)+"&XSDD01="+escape(XSDD01)+"&DDZT="+escape(DDZT)+"&ZCXX01="+escape(ZCXX01)+"#spitem");

}
//确认收货
function updata_6(obj,main){
	var XmlData=[{"XSDD01":obj}];
	var url="/OrderFlowManage/OkMyOrder.action?XmlData="+JSON.stringify(XmlData);
	var rData=visitService(url);
	if(rData.STATE=="1"){
		alert("操作成功!");
		//location.href="/customer/sydjt/back/ddgl/myorder.html";
		JL.setDivUrl("url","/customer/sydjt/back/ddgl/myorder.html");
		$(main).load("/customer/sydjt/back/ddgl/myorder.html");
		}else{
			alert("操作失败!");
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
	  $.each(DefStaPer.data, function(i, val) {
		    var result = val.SPLIST;
			s += '<dl class="w12 public_list">';
			s += '<dt class="w12"><span>';
			s +=column_header["carlist_shr"]+'：'+userCookie.XTCZY01;
			s +='</span><span>'+val.XSDD04+'</span><span>';
			s += column_header["carlist_orderno"]+'：'+val.XSDD01;
			s += '</span><span><a href="/customer/sydjt/showDpxx/shopAllGoods.html?gsid='+val.ZCXX01+'&gsmc='+escape(val.ZCXX02)+'"  target="_blank" >';
			s += column_header["carlist_wldw"]+'：'+val.ZCXX02;
			s += '</a></span><i class="fa fa-trash-o" title="删除订单"></i></dt>';
		    s += '<dd class="w06">';
	       
				for(var i=0; i<result.length;i++){
					s += '<div class="w02"><a href="/customer/sydjt/showProduct/product.html?spxx01=='+result[i].SPXX01+'&zcxx01='+val.ZCXX01+'&gsid='+val.ZCXX01+'&gsmc='+escape(val.ZCXX02)+'" target="_blank"><img title="'+result[i].SPXX04+'" src="'+imgUrl+val.ZCXX01+'/'+result[i].SPXX02+'/images/'+result[i].SPXX02+'_1_small.'+result[i].SPTP02+'"></a></div>';
					s += '<div class="w08"><a href="/customer/sydjt/showProduct/product.html?spxx01=='+result[i].SPXX01+'&zcxx01='+val.ZCXX01+'&gsid='+val.ZCXX01+'&gsmc='+escape(val.ZCXX02)+'">'+result[i].SPXX04+'</a></div>';
					s += '<div class="w02"><span class="w12">×'+result[i].XSDDI05+'</span></div>';
				}
				s += '</dd>';

				s += '<dd class="w02">';
				s += '<b class="w12">'+accounting.formatMoney(val.XSDD02)+'</b>';
				s += '<span class="w12">在线支付</span>';
				s += '</dd>';
				
				s += '<dd class="w02"><b class="w12">'+eval("changeColumns.W_DJZX02_"+val.DDZT)+'</b><span class="w12 detailss"><a onclick="goodslist(\''+val.XSDD04+'\',\''+val.XSDD01+'\',\''+val.DDZT+'\',\''+val.ZCXX01+'\',\'#centerFrame\')"> 订单详细</a></span></dd>';
			      

				if(val.DDZT=="3"){
					s += '<dd class="w02"><input type="button" value="立即付款" class="jl_btn btn_red" onclick="go_account(\''+val.XSDD01+'\',\''+val.XSDD02+'\')"></dd>';
				}
				if(val.DDZT=="4"){//待发货
					s += '<dd class="w02"><input type="button" value="退货/退款" class="jl_btn btn_red" onclick="select_goods(\''+val.XSDD01+'\',\'#centerFrame\')"></dd>';
				}
				if(val.DDZT=="6"){//已完成
					s += '<dd class="w02"><input type="button" value="评价/晒单" class="jl_btn btn_red" onclick="goodslist(\''+val.XSDD04+'\',\''+val.XSDD01+'\',\''+val.DDZT+'\',\''+val.ZCXX01+'\',\'#centerFrame\')">';
					s += '<input type="button" value="退货/退款" class="jl_btn btn_red" onclick="select_goods(\''+val.XSDD01+'\',\'#centerFrame\')"></dd>';
				}
				if(val.DDZT=="5"){
					s += '<dd class="w02"><input type="button" value="确认收货" class="jl_btn btn_red" onclick="updata_6(\''+val.XSDD01+'\',\'#centerFrame\')"></dd>';
				}
				s += '</dl>';
	  }); 

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
	    
	    $("#accout").on("click",function(){
			var cks=$("input[name='DDBH']");
			if (cks.length == 0 ) {
				alert("没有待支付订单");
				return false;
			}  
			var ddbhlist ="";
			var money =[];
			if(cks.length==0){
				return false;
			}
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
	    		window.parent.location.href="/customer/sydjt/shopping/cartSuccess.html?xsdd01="+rData.DDBHLIST+"&zfje="+rData.ZFJE+"&JYDH="+rData.JYDH;
    		}else{
    			alert("操作失败!");
    		}
	    });
}

function go_account(obj,objs){
  	var XmlData=[{"HBID":usercookie.ZCXX01,"DDBHLIST":obj,"ZFJE":objs}];
	var url="/OrderFlowManage/ConsolidatedPayment.action?XmlData="+JSON.stringify(XmlData);
	var rData=visitService(url);
	if(rData.STATE=="1"){
		window.parent.location.href="/customer/sydjt/shopping/cartSuccess.html?xsdd01="+rData.DDBHLIST+"&zfje="+rData.ZFJE+"&JYDH="+rData.JYDH;
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
