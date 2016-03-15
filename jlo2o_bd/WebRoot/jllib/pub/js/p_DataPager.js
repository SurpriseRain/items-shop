/**
 * 清空方法放进append
 * div中currMaxPage属性,currPageId属性需要自行初始化
 * 调用DataPagerInit进行插件的初始化
 */
var $DataPagerDiv;// 当前分页div对象
var showName;// 当前页显示数据的方法名
var currPageId;// 当前页input框id,前台传入,自定义
var fileName;// 缓存文件名
var currMaxPage ;// 当前页面数据的最大页数


// DataPager


// 分页初始化,divid,自定义输入框id
DataPagerInit = function(divId, inpPageId) {
	$('#jl_page').remove();
	$DataPagerDiv = $('#' + divId);
	fileName = $DataPagerDiv.attr('filename');
	showName = $DataPagerDiv.attr('showname');
	currMaxPage = $DataPagerDiv.attr('currmaxpage');
	currPageId = inpPageId;
	
	var fFloor = $("<div class='fy' id='jl_page'></div>");
	var sFloorStr = '<a href="#" onclick="goPrevPage()"><< 上一页</a>';
	for ( var i = 0; i < (currMaxPage >= 5 ? 5 : currMaxPage); i++) {
		sFloorStr += ('<a href="#" id="'+(currPageId+i)+'" onclick="goFreePage(' + (i + 1) + ')">' + (i + 1) + '</a>');
	}
	sFloorStr += ('<a href="#" onclick="goNextPage()">下一页 >></a>' + 
				  '<span>共'+currMaxPage+'页，到第</span><input id="' + 
				  currPageId + '" type="text" value="1"/><input id="hid' + 
				  currPageId + '" type="hidden" value="1"/><span class="tb">页</span>' +
				  '<a href="#" class="botton" onclick="goFreePage()">跳转</a>');
	
	var sFloor = $(sFloorStr);

	fFloor.append(sFloor);
	$DataPagerDiv.append(fFloor);

}
// 上一页
function goPrevPage() {
	var currPage=$('#hid' + currPageId).val();
	var pageSize = --currPage;
	if (pageSize <= 0) {
		alert("已经是第一页了");
	} else {
		pageOver(pageSize);
	}
}
// 下一页
function goNextPage() {
	var currPage=$('#hid' + currPageId).val();
	var pageSize = ++currPage;
	if (pageSize > currMaxPage) {
		alert("已经是最后一页了");
	} else {
		pageOver(pageSize);
	}
}
//跳页
function goFreePage(page) {
	var pageSize ;
	if(page!=null&&page!=''){
		pageSize=page;
	}else{
		pageSize =$("#" + currPageId).val();
	}
	if (pageSize > currMaxPage) {
		alert("请输入小于最大页的页码!");
	} else if(pageSize <=0){
		alert("请输入大于最小页的页码!");
	}else{
		pageOver(pageSize);
	}
}

// 翻页方法
function pageOver(pageSize) {
	var Url = pubJson.PcrmUrl + "/GridPageDataServlet?pages=" + pageSize
			+ "&fileName=" + fileName;
	var pageData = visitServiceGrid(Url);
	$("#" + currPageId).val(pageSize);
	$('#hid' + currPageId).val(pageSize);
	var maxPageNum=(currMaxPage >= 5 ? 5 : currMaxPage);
	var num=0;
	
	if(pageSize>3&&pageSize<=currMaxPage-2){
		num=pageSize-2;
	}else if(pageSize>currMaxPage-2&&pageSize<=currMaxPage){
		num=currMaxPage-maxPageNum+1;
	}else if(pageSize<=3){
		num=1;
	}
	
	if(num!=0){
		for(var i=0;i<maxPageNum;i++){
			var $obj=$('#'+currPageId+i);
			$obj.html(num+i);
			$obj.attr('onclick','goFreePage('+(num+i)+')');
		}
	}
	
	eval(showName + "(pageData)");
}

// 跟数据库交互获取展示数据
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
			alert("获取数据失败，状态是：" + textStatus + "+" + XMLHttpRequest + "+"
					+ errorThrown);
		}
	});
	return returnValue;
}
