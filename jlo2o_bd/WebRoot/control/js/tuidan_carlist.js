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

//获取公用默认图片路径
var imgUrl=pubJson.path_sptp+"/sptp/";

//查看明细连接
function select_goods(obj,i){
	location.href="/customer/qfy/back/thgl/th-shenhe.html?THDH="+obj+"&TYPE="+i;
}
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
	var s = '<div class="mainbox mt10" id="clecls" style="background-color:red;"><h2 ><span class="fl" style="padding-left:230px; font-size:16px;">商品信息</span>';
			s +='<span class="fr" style="padding-left:230px; font-size:16px;">金额</span>';
			s +='<span class="fr" style="padding-left:130px; font-size:16px;">商品操作</span></h2></div>';
		    s +='<div class="car_body">';
		     
	  $.each(DefStaPer.data, function(i, val) {
		    var result = val.SPLIST;
			s += '<div class="car_top">';
				s += '<div class="car_checkbox"></div>';
				s += '<div class="car_time">退货时间：';
				s += val.ZDSJ;
				s += '</div>';
				s += '<div class="car_ddh order_styles">';
				s += '退单号：'+val.THDH;
				s += '</div>';
				s += '<div class="car_ddh order_styles">';
				s += '订单号：'+val.XSDD01;
				s += '</div>';
				s += '<div class="car_dp order_styles" style="float:right; margin-right:10px;">';
				if(TYPE=="2"){
					s +='买家名称：'+val.ZCXX02;;
				}else{
					s += '卖家名称：'+val.ZCXX02;;
				}
				s += '</div>';
			s += '</div>';
			
		    s += '<div class="car_item">';
				s += '<div class="goodz goodz_name">';
				for(var i=0; i<result.length;i++){
					s += '<div><img title="'+result[i].SPXX04+'" class="imgsize" src="'+imgUrl+val.ZCXX01+'/'+result[i].SPXX02+'/images/'+result[i].SPXX02+'_1_small.'+result[i].SPTP02+'" /></div>';
				}
				s += '</div>';

				s += '<div class="goodz jg" style="width:140px;">';
				
				s += '<div style="font-size:15px;">'+eval("changeColumns.THZT_"+val.THZT)+'<font class="scx"></font></div>';
				s += '<div><font>'+accounting.formatMoney(val.THJE)+'</font></div>';
				s += '</div>';

				s += '<div class="goodz sl goods_style" style="padding-left:40px;">';
				s += '<div><div class="thstyle" style="text-align:center;"><a onclick="select_goods(\''+val.THDH+'\',\''+TYPE+'\')" style="display:block;">查看详情</a></div></div>';
				
				s += '</div>';
			s += '</div>';
	  }); 
	  s += '</div>';
	  	

	    $(o).html(s);
	    $("img").error(function() {
        	$(this).attr("src", pubJson.defaultImgUrl);
        });
	   
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
