/**
 * name:公共控件
 * author:jhj
 * date:2015-03-09
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

DefStaPer["version"] = 2;
DefStaPer.config = {};
DefStaPer.options = [];
DefStaPer.configs = {};
DefStaPer.values = {};

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
    var count = returnVal[0];
    count = typeof count["COUNT"] === "undefined" ? (typeof count["count"] === "undefined" ? 0 : count["count"]) : count["COUNT"]; 
    rowNum = count;
}

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
	 	   PagesNavMth:pubJson.PagesNavMth, //显示导航个数
	 	   columnsArr:DefStaPer.columnsArr,
	 	   data:DefStaPer.data
	 	});
		/****************************分页展示结束*******************************************/
    }
}


//值打印列表数据，不包括导航上下翻页内容
DefStaPer.writeData = function(o,columnName){
	obj=o;
	changeColumnObj=columnName;
	/****************************列头展示开始*******************************************/
	$(".dyryqx").remove();
	var s = '';
	s += '<table class="list_table">';
	s += '<tr class="title">';
	$.each(DefStaPer.columnsArr,function(i,val){
		if(val.checkbox==1){
			s += '<td class="no1"><input type="checkbox" class="checkbox all" /></td>';
		}else if(val.type=="hidden"){
			s += '<td style="display:none">'+val.header+'</td>';
		}else{
			s += '<td class="no2" style="width:'+val.width+';">'+val.header+'</td>';
		}
	});
	s += '</tr>';
	/****************************列头展示结束*******************************************/

	/****************************数据展示开始*******************************************/
	$.each(DefStaPer.data,  function(i, val) {
					//判断是否有行点击事件
		            if(DefStaPer.rowClick == "false"){
		            	s += '<tr class="jl">';
		            }else{
		            	s += "<tr id='tr_"+i+"' class='jl' onclick='rowClick("+i+")'>";
		            }
				    
					 $.each(DefStaPer.columnsArr,function(index,temp){
						    var fieldValue = eval("val."+temp.property);
						    if(fieldValue==undefined || fieldValue=="undefined")fieldValue="";
							if(temp.checkbox==1){
								s += '<td><input type="checkbox" name='+temp.property+'/>'+fieldValue+'</td>';
							}
							if(temp.type=="hidden"){
								s += '<td style="display:none;"><input id="'+temp.property+'" name="'+temp.property+'" value="'+fieldValue+'"/></td>';
							}
							if(temp.type=="text"){
								s += '<td><input name="'+temp.property+'" style="width:100px;"/>'+fieldValue+'</td>';
							}
							if(temp.type=="lable"){
								s += '<td class="case2"><input id="'+temp.property+'" name="'+temp.property+'"  value="'+fieldValue+'" title="'+fieldValue+'" class="span" style="width:'+temp.width+';" readonly="readonly;"/></td>';
							}
							if(temp.type=="img"){
								var zcxx = eval("val.ZCXX01");
								var SPTP02 = eval("val.SPTP02");
								s += '<td class="case2"><img src="'+pubJson.path_sptp+'/sptp/'+zcxx+'/'+fieldValue+'/images/'+fieldValue+'_1_small.'+SPTP02+'" onerror=this.onerror=null;this.src="/customer/qfy/images/msimg.jpg" name="'+temp.property+'"  title="'+fieldValue+'" class="span" style="width:'+temp.width+'; height:'+temp.width+';" readonly="readonly;"/></td>';
							}
							/*单独的查询展示，没有点击事件*/
							if(temp.type=="srch"){
								s += '<td class="srch" ><input class="srch span" id="'+temp.property+'" name="'+temp.property+'" value="'+fieldValue+'" style="width:'+temp.width+';" readonly="readonly;"></td>';
							}
							if(temp.type=="number"){
								djzt = temp.property+"_"+fieldValue;
								var valueTxt = eval("columnName."+djzt);
								s += '<td class="case2"><input type="text" id="'+temp.property+'_code" value="'+fieldValue+'" style="display:none;"/><a class="gdz" id="'+temp.property+'" name="'+temp.property+'" style="width:'+temp.width+'; text-align:center;">'+valueTxt+'</a></td>';
							}
							if(temp.type=="select"){
								s += '<td>';
							    s += '<select>';
								$.each(eval("val."+temp.property),function(key,value){
									s += '<option value="'+key+'">'+value+'</option>';
								});
								s += '</select>';
								s += '</td>';
							}
							if(temp.type=="css1"){
								s += '<td class="no4">';
								s += '<div class="get"><span >请选择</span></div>';
								s += '<div class="cdqx_zcd">';
								$.each(fieldValue,function(key,value) {
								    s += '<input id="id_'+key+'" type="checkbox" value="'+value+' " class="checkbox"  name="checkbox"/><span id="id_'+key+'">'+value+'</span>';
								});
								s += '</div>';
								s += '</td>';
							}
							if(temp.type=="css2"){
								s += '<td>';
								s += '<span>请选择</span>';
								s += '<ul class="cd_qx">';
								$.each(fieldValue,function(key, value){
								    s += '<li><input type="radio" name="jkqx" value="'+value+'" /><font>'+value+'</font></li>';
								});
								s += '</ul>';
								s += '</td>';
							}
							if(temp.type=="radio"){
								s += '<td>';
								$.each(fieldValue,function(key, value){
								    s += '<input type="radio"  name="yes" value='+key+'/><label>'+value+'</label>';
								});
								s += '</td>';
							}
							//商品操作
							if(temp.type=="spcz"){
								var djztValue = eval("val."+temp.property);
								var spxx01 = eval("val."+DefStaPer.columnsArr[0].property);
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+spxx01+'")>编辑</a>'
								if(djztValue=="0")
								{
									s += '<a href="javascript:void(0);" class="sj_ operation"  name="sj" onClick=sj("'+spxx01+'")>上架</a>';
								}else
								{
									s += '<a href="javascript:void(0);" class="xj_ operation"  name="xj" onClick=sj("'+spxx01+'")>下架</a>';
								}
								//s += '<a href="javascript:void(0);" class="del_ operation"  name="del" onClick=del("'+spxx01+'")>删除</a></td>';
								s += '<a href="javascript:void(0);" class=" operation" name="" onClick=glsp("'+spxx01+'")>关联商品</a></td>';
							}
							//服务操作
							if(temp.type=="fwcz"){
								var djztValue = eval("val."+temp.property);
								var id = eval("val."+DefStaPer.columnsArr[0].property);
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+id+'")>编辑</a>';
								if(djztValue=="0")
								{
									s += '<a href="javascript:void(0);" class="sj_ operation"  name="sj" onClick=sj("'+id+'")>上架</a></td>';
								}else
								{
									s += '<a href="javascript:void(0);" class="xj_ operation"  name="xj" onClick=sj("'+id+'")>下架</a></td>';
								}
							}
							// NineDragon
							// 权限菜单：编辑 
							if(temp.type=="qxgl"){
								var djztValue = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+djztValue+'")>编辑</a></td>';
							}
							//财务线下交易信息审核
							if(temp.type=="cwsh"){
								var THZT = eval("val.JYLSH");
								s += '<td><a href="javascript:void(0);" class="operation" " onClick=select_THD("'+THZT+'")>详情</a></td>';
							}
							//Qi发票订单 查看
							if(temp.type=="fpxq"){
								var djztValue = eval("val.fpbh");
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=select_THD("'+djztValue+'")>详情</a></td>';
							}
							if(temp.type=="thcz"){
								var gmsl = eval("val.XSDDI05");
								var thsl = eval("val.THSL");
								var djztValue = eval("val."+temp.property);
								if(thsl<gmsl){
									s+='<td><a href="javascript:void(0);" class="sq buttons"  name=""onClick=edit("'+djztValue+'","'+gmsl+'","'+thsl+'")>申请退换货</a></td>';
								}else{
									s+='<td><a href="javascript:void(0);" class="sq buttons" id="cx" name="">申请退货</a></td>';
								}
							}
							//用户咨询查看
							if(temp.type=="zxcka"){
								var id = eval("val.id");
								var SendUserId = eval("val.SendUserId");
								var ReceiveUserId = eval("val.ReceiveUserId");
								var AssociateProduceId = eval("val.AssociateProduceId");
									s+='<td><a href="javascript:void(0);" class="sq buttons"  name="" onClick=updata("'+id+'","'+SendUserId+'","'+ReceiveUserId+'","'+AssociateProduceId+'")>查看</a></td>';
							}
							
							//关联商品
							if(temp.type=="glsp"){
								var spxx01 = eval("val."+DefStaPer.columnsArr[0].property);
								s += '<td><a href="javascript:void(0);" class=" operation" name="" onClick=glspEdit("'+spxx01+'")>关联</a></td>';
							}
							//项目维护操作
							if(temp.type=="xmcz"){
								var djztValue = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+djztValue+'")>编辑</a>'
								s += '<a href="javascript:void(0);" class="del_ operation"  name="del" onClick=del("'+djztValue+'")>删除</a></td>';
								//s += '<td><a href="javascript:void(0);" class="rpl operation" id="check" name="sh">查看</a></td>';
							}
							//订单审核
							if(temp.type=="ddsh"){
								var djztValue = fieldValue;
								s += '<td><a href="javascript:void(0);" style="text-align:center;" class="rpl operation" id="check" name="sh">查看</a></td>';
							}
							//点击删除
							if(temp.type=="delete"){
								
								s += '<td><a href="javascript:void(0);" class="del operation"  name="del">删除</a></td>';
							}
							//买家退单入库
							if(temp.type=="mjtd"){
								s += '<td><a href="javascript:void(0);"  style="text-align:center;" class="operation" id="check" name="sh"  onClick=select_xsdd("'+fieldValue+'")>扫码/导入</a></td>';
							}
							//买家退单入库
							if(temp.type=="mjtdd"){
								s += '<td><a href="javascript:void(0);"  style="text-align:center;" class="operation" id="check" name="sh"  onClick=select_xsddd("'+fieldValue+'")>出库</a></td>';
							}
							//买家退单入库货架 
							if(temp.type=="eidt_rkdh"){
								var SHCK = eval("val.SHCK");
								s += '<td><a href="javascript:void(0);"  style="text-align:center;" class="operation" id="check" name="sh"  onClick=select_thxx("'+fieldValue+'","'+SHCK+'")>查看详情</a></td>';
							}
							//扫码发货
							if(temp.type=="mjtdd_2"){
								var lx = eval("val.LX");
								var djztValue = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);"  style="text-align:center;" class="operation" id="check" name="sh"  onClick=select_xsddd("'+djztValue+'","'+lx+'")>出库</a></td>';
							}
							//点击操作
							if(temp.type=="ttttt"){
								var THZT = eval("val.THZT");
								var djztValue = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);" class="operation" " onClick=select_THD("'+djztValue+'","'+THZT+'")>详情</a></td>';
							}
							//店铺审核
							if(temp.type=="dpsh"){
	                            var djztValue = fieldValue;
								if(djztValue==1){
									s += '<td>已审核</td>';
								}else if(djztValue==0){
								    s += '<td>未审核</td>';
								}
							}
							//服务需求状态
							if(temp.type=="fwzt"){
								djzt = temp.property+"_"+fieldValue;
								var valueTxt = eval("columnName."+djzt);
								s += '<td class="case2">'+valueTxt+'</td>';
							}
							if(temp.type=="jdzt"){
								djzt = temp.property+"_"+fieldValue;
								var valueTxt = eval("columnName."+djzt);
								s += '<td class="case2">'+valueTxt+'</td>';
							}
							//操作需求
							if(temp.type=="edit"){
								var djztValue = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+djztValue+'")> 查看</a>';
							}
							//用户审核
							if(temp.type=="user_sh"){
	                            var djztValue = eval("val."+temp.property);
	                            s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=edit("'+djztValue+'")> 查看</a>';
							}
							if(temp.type=="fpxz"){
								var isShowCB = (val["INVOICESTATE"] === 0 || val["INVOICESTATE"] === 3)  ? true : false;
								var xsdd = eval("val."+temp.property);
								if(isShowCB){									
									s += '<td><input type="checkbox" class="check" id="' + xsdd + '" style="opacity:1;border:1px solid #000;" name="check"  onClick=check_fp("' + xsdd + '") /></td>';
								}else{
									s += '<td><input type="checkbox" class="check1" id="' + xsdd + '" name="check" disabled  style="opacity:0.3;border:1px dotted #000;"/></td>';
								}
							}
							//银行卡启用
							/*if(temp.type=="yhkbj"){
	                            var djztValue = eval("val."+temp.property);
								if(djztValue==1){
									s += '<td><a href="javascript:void(0);" class="operation" id="check" name="sh">已启用</a></td>';
								}else if(djztValue==0){
								    s += '<td><a class="start operation" id="check" name="sh" style="color:#f40;">启用 </a><a href="javascript:void(0);" class="del operation"  name="del">删除</a></td>';
								}
							}*/
					
					 });
					 s += '</tr>';
				});
				s += '</table>';
				if(DefStaPer.data.length==0){
					s += '<div style="height:200px;color:rgb(92, 92, 92);font-size:16px;text-align: center;line-height:100px;letter-spacing:5px;">没有找到相关的数据！</div>';
				}
		$(o).html(s);
		
	    /****************************数据展示结束*******************************************/
	    //点击审核按钮
	     $(".rpl").click(function(){
	        var xsdd01=$(this).parent("td").siblings(".case2:first").children("input").attr("value");
	    	parent.ajaxGrid(DefStaPer.afterUrl+xsdd01);
	     })
	     //点击删除
	    $(".del").click(function(){
	    	 var xsdd01=$(this).parent("td").siblings(".srch:first").children("input").attr("value");
	         if(xsdd01==undefined){
	         	xsdd01=$(this).parent("td").siblings(".case2:first").children("input").attr("value");
	         }
	         del(xsdd01);
	    })

	    //银行卡点击启用
	    $(".start").click(function(){
	        var xsdd01=$(this).parent("td").siblings(".case2:first").children("input").attr("value");
	        update(xsdd01);
	    })
}


DefStaPer.init = function(json) {
	DefStaPer.config = JL.isNull(json.config) ? {} : json.config;
	var columnName = changeColumns;
	var o = json["obj"];
	if (DefStaPer.config["newclos"] != 'undefine')DefStaPer.newclos=DefStaPer.config["newclos"];
	if (DefStaPer.config["data"] != 'undefine')DefStaPer.data=DefStaPer.config["data"];
	if (DefStaPer.config["multiPage"] != 'undefine')DefStaPer.multiPage=DefStaPer.config["multiPage"];
	if (DefStaPer.config["afterUrl"] != 'undefine')DefStaPer.afterUrl=DefStaPer.config["afterUrl"];
	if (DefStaPer.config["columnsArr"] != 'undefine')DefStaPer.columnsArr=DefStaPer.config["columnsArr"];
	if(DefStaPer.config["rowClick"] != 'undefine'){
		DefStaPer.rowClick=DefStaPer.config["rowClick"];
	}else{
		DefStaPer.rowClick="false";
	}
	if(DefStaPer.config["height"] != 'undefine'){
		DefStaPer.height=DefStaPer.config["height"];
	}else{
		DefStaPer.height="2000";
	}
	/*
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
	}*/
	
	var XmlData = [];
	var queryField={};
	queryField["dataType"] = "Json";
	queryField["QryType"] = "Report";
	queryField["sqlid"] = DefStaPer.config["sqlid"];
	queryField=$.extend({}, queryField);
	var param= JL.isNull(DefStaPer.config["param"]) ? {} : DefStaPer.config["param"];
	queryField=$.extend({}, queryField, param);
	XmlData.push(queryField);
	var ajaxJson = {};
	ajaxJson["url"] = DefStaPer.config["url"];
	ajaxJson["data"] = {"XmlData":JSON.stringify(XmlData)};
	var resultData = JL.ajax(ajaxJson);
	//DefStaPer.load(initData());
	parasData = XmlData;
	DefStaPer.load(resultData);
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