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

	var str = '';
	  $.each(DefStaPer.data, function(i, obj) {//alert(JSON.stringify(DefStaPer.data))
			 str+='<dl class="w12 public_table_main">';
	  		 str +='<dt class="w01"><a href="/customer/sydjt/showProduct/product.html?spxx01='+obj.SPXX01+'&zcxx01='+obj.ZCXX01+'&gsid='+obj.ZCXX01+'&gsmc='+escape(obj.ZCXX02)+'" target="_blank"><img src="'+imgUrl+obj.ZCXX01+'/'+obj.SPXX02+'/images/'+obj.SPXX02+'_1_small.'+obj.SPTP02+'" /></a></dt>';
	  		 str +='<dd class="w05"><a class="w10" href="/customer/sydjt/showProduct/product.html?spxx01='+obj.SPXX01+'&zcxx01='+obj.ZCXX01+'&gsid='+obj.ZCXX01+'&gsmc='+escape(obj.ZCXX02)+'" target="_blank">'+obj.SPXX04+'</a></dd>';
	  		 str +='<dd class="w01"><span>'+obj.CKSP04+'</span></dd>';
	  		 str +='<dd class="w01"><span>'+obj.ZDKC+'</span></dd>';
	  		 str +='<dd class="w02"><b class="pic">￥'+obj.SPJGB02+'</b></dd>';
	  		 if(obj.SPGL12 == "0"){
	  			str +='<dd class="w02"><a class="w04 ml_w01 cz" onClick=glsp("'+obj.SPXX01+'")>关联商品</a><a class="w03 cz" onClick=edit("'+obj.SPXX01+'")>编辑</a><a class="w03 cz" onClick=del("'+obj.SPXX01+'")>删除</a><input type="button" class="jl_btn btn_red top" value="上架" onClick=sj("'+obj.SPXX01+'") /></dd>';
	  		 }else{
	  			str +='<dd class="w02"><a class="w04 ml_w02 cz" onClick=glsp("'+obj.SPXX01+'")>关联商品</a><a class="w04 cz" onClick=edit("'+obj.SPXX01+'")>编辑</a><input type="button" class="jl_btn btn_red top" value="下架" onClick=sj("'+obj.SPXX01+'") /></dd>';
	  		 }str +='</dl>';
				
	  }); 

	    $(o).html(str);
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
