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
			 if(val.header == "全选"){
			   s += '<td style="width:'+val.width+';"><input id="selAll" style="vertical-align:middle;margin-top:0;" type="checkbox" />'+val.header+'</td>';
			  }else if(val.checkbox==1){
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
								// 齐俊宇 优化 动态显示小图片
								var zcxx = eval("val.ZCXX01");
								var SPTP02 = eval("val.SPTP02");
								var SPTP01 = eval("val.SPTP01");
								if(SPTP01 == null || SPTP01 == undefined){
									s += '<td class="case2"><img src="/customer/qfy/images/msimg.jpg" name="'+temp.property+'"  title="'+fieldValue+'" class="span" style="width:'+temp.width+'; height:'+temp.width+';" readonly="readonly;"/></td>';
								} else {
									s += '<td class="case2"><img src="'+pubJson.path_sptp+'/sptp/'+zcxx+'/'+fieldValue+'/images/'+fieldValue+'_' + SPTP01 + '_small.'+SPTP02+'" onerror=this.onerror=null;this.src="/customer/qfy/images/msimg.jpg" name="'+temp.property+'"  title="'+fieldValue+'" class="span" style="width:'+temp.width+'; height:'+temp.width+';" readonly="readonly;"/></td>';
								}
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
									// 增加搜索引擎需要的内容
									s += '<a href="javascript:void(0);" class="sj_ operation"  name="sj" onClick=sj("'+spxx01+'","1")>上架</a>';
								}else
								{
									// 增加搜索引擎需要的内容
									s += '<a href="javascript:void(0);" class="xj_ operation"  name="xj" onClick=sj("'+spxx01+'","0")>下架</a>';
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
							// NineDragon
							//商家参加的活动
							if(temp.type=="wdhds"){
								var ActivityShopId = eval("val."+temp.property);
								s += '<td><input type="checkbox" id="'+ActivityShopId+'" class="check" style="opacity:1;border:1px solid #000;" name="check" /></td>';
							}
							//NineDragon
							//活动-商品名跳转
							if(temp.type=="hdtz"){
								var djztValue = eval("val."+temp.property);
								var SPXX01 = eval("val.SPXX01");
								var ZCXX01 = eval("val.ZCXX01");
								var GSMC = escape(djztValue);
								s += '<td class="case2"><a href="/customer/qfy/showProduct/product.html?spxx01='+SPXX01+'&zcxx01='+ZCXX01+'&gsid='+ZCXX01+'&gsmc='+GSMC+'"    target="_blank"  class="edit_ operation"  title="'+djztValue+'" name="edit"   style="color:#000000;font-weight:400;">'+djztValue+'</a></td>';
							}
							//NineDragon 是否包邮--是
							if(temp.type=="wdhdss"){
								s += '<td class="case2"><input   value="是"  class="span" style="width:'+temp.width+';" readonly="readonly;"/></td>';
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
									s+='<td><a href="javascript:void(0);" class="sq buttons"  name=""onClick=edit("'+djztValue+'","'+gmsl+'","'+thsl+'")>申请退货</a></td>';
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
							
							//我的活动
						/*	if(temp.type=="cjsp"){
								var djztValue = eval("val."+temp.property);
								var id = eval("val."+DefStaPer.columnsArr[0].property);
								s += '<td><a href="javascript:void(0);" class="edit_ operation"  name="edit"  onClick=list("'+id+'")>列表</a>';
							}*/
							if(temp.type=="MyActivityCjsp"){
								var djztValue = eval("val.ACTIVITYID");
								s += '<td><a href="javascript:void(0);" class="cjsp_ operation"  name="cjsp"  onClick=MyActivityCjsp("'+djztValue+'")>列表</a></td>';
							}
							


							////////////商家参加的活动
							if(temp.type=="wdhd"){
								var hdmc = eval("val."+temp.property);
								s += '<td><input type="checkbox" id="'+hdmc+'" class="check" style="opacity:1;border:1px solid #000;" name="check" /></td>';
							}

							/* 20151110 齐俊宇 活动商品列表和关闭按钮 BEGIN*/
							if(temp.type=="cjsp"){
								var cjspValue = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);" class="cjsp_ operation"  name="cjsp"  onClick=cjsp("' + cjspValue + '")>列表</a></td>';
							}
							if(temp.type=="hdgb"){
								var hdgbState = val["ACTIVITYSTATE"] === 2 ? true : false;
								var hdgbValue = eval("val."+temp.property);
								if(hdgbState){
									s += '<td><a href="javascript:void(0);" class="hdgb_ operation"  name="hdgb"  >已关闭</a></td>';
								} else {
									s += '<td><a href="javascript:void(0);" class="hdgb_ operation"  name="hdgb"  onClick=hdgb("' + hdgbValue + '")>关闭</a></td>';
								}
							}
							/* 20151110 齐俊宇 活动商品列表和关闭按钮 END*/
							
							// 2015.11.13.活动报名商品列表点击删除
							if(temp.type=="deleteShow"){
								var productId = eval("val."+temp.property);
								s += '<td><a href="javascript:void(0);" id="'+productId+'" class="operation" style="margin:0;padding:0;border:0"  name="delShow" onclick="delShow(\'tr_'+i+'\')">删除</a></td>';
							}
							// 2015.11.13.活动报名商品选择
							if(temp.type=="selectProduct"){
								var hdmc = eval("val."+temp.property);
								s += '<td><input type="checkbox" id="'+hdmc+'" class="check" style="opacity:1;border:1px solid #000;" name="check"  onClick="mergeProductIds();" /></td>';
							}
							// 2015.11.13.活动报名商品数量输入
							if(temp.type=="inputNum"){
								var inputNum = eval("val."+temp.property);
								//s += '<td class="case2"><input id="'+temp.property+'" name="'+temp.property+'"  value="'+fieldValue+'" title="'+fieldValue+'" class="span" style="width:'+temp.width+';" onkeyup="checkMaxNum(this,'+fieldValue+');" /></td>';
								s += '<td class="case2"><input id="'+temp.property+'" name="'+temp.property+'" onkeyup="value=value.replace(\/[^\\d]\/g,\'\')" style="border: 1px solid #ccc;width: 80px;text-align: center;"  value="' + inputNum + '" onblur="checkMaxNum(this,\'' + fieldValue + '\')" title="'+fieldValue+'"  /></td>';
							}	
							// 20151201 齐俊宇 BEGIN
							// 品牌管理操作
							if(temp.type=="ppcz"){
								var ppbhValue = eval("val." + temp.property);
								var ppztState = val["PPQXZT"];// === 3 ? true : false;
								s += '<td><a href="javascript:void(0);" class="check_ operation"  name="check"  onClick=check("' + ppbhValue + '","' + val["PPQXZT"] + '")>查看</a>';
								if(ppztState == 3){
									s += '<a href="javascript:void(0);" class="start_ operation"  name="start" onClick=changeState("' + ppbhValue + '","0")>启用</a></td>';
								} else if(ppztState == 1 || ppztState == 4){
									s += '<a href="javascript:void(0);" class="stop_ operation"  name="stop" onClick=changeState("' + ppbhValue + '","3")>停用</a></td>';
								}else {
									s += '<a href="javascript:void(0);" class="stop_ operation" style="color: #ccc;" name="stopDis" >停用</a></td>';
								}
							}
							// 20151201 齐俊宇 END
							
							// 20151209 齐俊宇 服务商状态判断是启用还是终止服务 BEGIN 
							if(temp.type=="fwscz"){
								var hbidValue = eval("val." + temp.property);
								var fwsState = val["FWSZT"];
								s += '<td><a href="javascript:void(0);" class="check_ operation"  name="check"  onClick=check("' + hbidValue + '","' + val["FWSZT"] + '")>查看</a>';
								if(fwsState == 3){
									s += '<a href="javascript:void(0);" class="start_ operation"  name="start" onClick=changeState("' + hbidValue + '","0")>启用</a></td>';
								} else if(fwsState == 1 || fwsState == 4) {
									s += '<a href="javascript:void(0);" class="stop_ operation"  name="stop" onClick=changeState("' + hbidValue + '","3")>终止</a></td>';
								} else {
									s += '<a href="javascript:void(0);" class="stop_ operation" style="color: #ccc;"  name="stopDis" >终止</a></td>';
								}
							}
							// 20151209 齐俊宇 服务商状态判断是启用还是终止服务 END
							
							// 20151212 hcl 删除包装码
							if(temp.type=="bzm"){
								s += '<td><a href="javascript:void(0);" class="check_ operation"  name="check"  onClick=check(\'tr_'+i+'\')>查看</a>'
								s += '<a href="javascript:void(0);" class="del_ operation"  name="del" onClick=del(\'tr_'+i+'\')>删除</a></td>';
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
							//管理员品牌查看
							if(temp.type=="ppck"){
								var PPBH = eval("val.PPBH");
								var dd = eval("val.id");
								s += '<td><a href="javascript:void(0);" class="operation" " onClick=select_PPBH("'+PPBH+'","'+dd+'")>详情</a></td>';
							}
							
							//2015.12.9 叶栓 服务企业管理查看审核操作
							// 20151209 齐俊宇 服务商状态判断是启用还是终止服务 BEGIN 
							if(temp.type=="fwqy"){
								var ztid = eval("val." + temp.property);
								var fwqyState = val["FWSZT"];
								if(fwqyState == 0){
									s += '<td><a href="javascript:void(0);" class="start_ operation"  name="start" onClick=checked("' + ztid + '","' + val["FWSZT"] + '")>审核</a></td>';
								}  else {
									s += '<td><a href="javascript:void(0);" class="check_ operation"  name="check"  onClick=checked("' + ztid + '","' + val["FWSZT"] + '")>查看</a>';
								}
							}
					
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
	    //全选
	    $('#selAll').click(function(){
	        var is_checked=$('#selAll').attr('checked');
	        if(is_checked=="checked"){
	            $('input[name=check]').attr('checked',true);
	        }
	        else{
	        	$('input[name=check]').attr('checked',false); 
	        	// 回调 活动创建选择商品页面使用
	        	$("input[name='check']:disabled").attr("checked", true);
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
	// 翻页后具有勾选功能的方法 pageGridChangeFunc() 齐俊宇 20151116
	if(typeof(pageGridChangeFunc)=="function"){
		pageGridChangeFunc();
	}
}