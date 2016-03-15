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

	var str = '';//alert(JSON.stringify(DefStaPer.data))
	  $.each(DefStaPer.data, function(i, obj) {//alert(obj.SPLIST)
		  if(obj.SPLIST != undefined){
		  $(obj.SPLIST).each(function(index,spObj){
			  str+='<dl class="w12 public_table_main" style="margin-bottom: 0px;">';
				str +='<dt class="w01">';
			      str +='<img src="'+imgUrl+obj.ZCXX01+'/'+spObj.SPXX02+'/images/'+spObj.SPXX02+'_1_small.'+spObj.SPTP02+'" /></dt>';
			      //str +='<div class="w10">';
			      str +='<dd class="w07"><a href="/customer/sydjt/showProduct/product.html?spxx01='+spObj.SPXX01+'&zcxx01='+obj.ZCXX01+'&gsid='+obj.ZCXX01+'&gsmc='+escape(obj.ZCXX02)+'" target="_blank">'+spObj.SPXX04+'</a>';
			      //str +='</div>';
			      str +='<label class="w12">购买时间：'+obj.XSDD04+'</label>';
			    str +='</dd>';
			    if(obj.DDZT==6){
			    str +='<dd class="w04"><input type="button" value="点击评价" class="jl_btn btn_red new" onclick="pjsd.DJPJ(\''+obj.XSDD01+'\',\''+spObj.SPXX01+'\',\'0\')"/></dd>';
			    }
			    if(obj.DDZT==10){
				    str +='<dd class="w04"><input type="button" value="查看评价" class="jl_btn btn_red new" onclick="pjsd.DJPJ(\''+obj.XSDD01+'\',\''+spObj.SPXX01+'\',\'1\')"/></dd>';
				    }
			    str +='</dl>';
			    str +='<div  class="w12 detaInfo detaInfo_none" style="display: none;" id="zhankai_'+obj.XSDD01+'">';
			    				  str +='<div class="jt"></div>';
			    				  str +='<dl class="w12 public_form">';
			    				  str +='<dt class="w01">评分：</dt><dd class="w11"></dd>';
			    				  str +='</dl>';
			    				  str +='<dl class="w12 public_form">';
			    				  str +='<dt class="w01">心得：</dt>';
			    				  str +='<dd class="w11"><textarea class="w09" id="textarea_'+obj.XSDD01+'" placeholder="商品是否给力？快分享你的购买心得吧~"></textarea></dd>';
			    				  str +='</dl>';
			    				  
			    				  str +='<div class="w12 pj_list">';
			    				  str +='<dl class="w12 public_form">';
			    				  str +='<dt class="w01">晒单：</dt>';
			    				  str +='<dd class="w11">';
			    				str +='<div class="up_img"><i class="w12 fa fa-cloud-upload"></i><img src="/customer/sydjt/images/sccg.png" style="display: none;" /><input id="file01" name="file01" type="file" title="上传图片" onChange="pjsd.Upload(\'file01\')" /></div>';
			    				str +='<div class="up_img"><i class="w12 fa fa-cloud-upload"></i><img src="/customer/sydjt/images/sccg.png" style="display: none;" /><input id="file02" name="file02" type="file" title="上传图片" onChange="pjsd.Upload(\'file02\')" /></div>';
			    				str +='<div class="up_img"><i class="w12 fa fa-cloud-upload"></i><img src="/customer/sydjt/images/sccg.png" style="display: none;" /><input id="file03" name="file03" type="file" title="上传图片" onChange="pjsd.Upload(\'file03\')" /></div>';
			    				str +='<div class="up_img"><i class="w12 fa fa-cloud-upload"></i><img src="/customer/sydjt/images/sccg.png" style="display: none;" /><input id="file04" name="file04" type="file" title="上传图片" onChange="pjsd.Upload(\'file04\')" /></div>';
			    				str +='<div class="up_img"><i class="w12 fa fa-cloud-upload"></i><img src="/customer/sydjt/images/sccg.png" style="display: none;" /><input id="file05" name="file05" type="file" title="上传图片" onChange="pjsd.Upload(\'file05\')" /></div>';
			    				str +='</dd>';
			    				str +='</dl>';
			    				str +='<dl class="w12 public_form">';
			    				str +='<dt class="w01">&nbsp;</dt>';
			    				str +='<dd class="w11"><input type="button" class="jl_btn btn_red" onclick="pjsd.FBPJ(\''+obj.XSDD01+'\',\''+spObj.SPXX01+'\',\''+obj.XSDD04+'\',\''+obj.ZCXX01+'\')" value="发表评论"></dd>';
			    				str +='</dl>';
			    				str +='</div>';
			    				str +='</div>';
		  });
		  }else{//评价管理
			  str +='<dl class="w12 public_table_main">';
				str +='<dd class="w05">';
				str +='<label class="w12">'+obj.SPPJ07+'</label>'; 
				str +='<label class="w12">['+obj.SPPJ08+']</label>';
				str +='</dd>';
				str +='<dd class="w02"><a href="#">买家：'+obj.ZCXX02+'</a></dd>';
				str +='<dd class="w03">';
				str +='<a href="#" class="w11">'+obj.spxx04+'</a>';
				str +='</dd>';
				str +='<dd class="w02">';
				str +='<input value="回复" onclick="HFPJ(\''+obj.XSDD01+'\',\''+obj.SPXX01+'\',\'1\')" type="button" class="jl_btn btn_red btn_medium" />';
				str +='</dd>';
				str +='</dl>';
				
				str +='<div class="w12 detaInfo detaInfo_none" style="display: none;" id="zhankai_'+obj.XSDD01+'">';
				str +='<div class="jt"></div>';
				str +='<dl class="w12 public_form">';
				str +='<dt class="w01">心得：</dt>';
				str +='<dd class="w11"><textarea class="w09" id="textarea_'+obj.XSDD01+'" placeholder="在此处可回复买家的当前评论哦~" disabled="disabled"></textarea></dd>';
				str +='</dl>';
				str +='</div>';
		  }
	  }); 
		if(DefStaPer.data.length==0){
			str += '<div style="height:200px;color:rgb(92, 92, 92);font-size:16px;text-align: center;line-height:100px;letter-spacing:5px;">没有找到相关的数据！</div>';
		}
	    $(o).html(str);
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