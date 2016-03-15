var changeColumns = {
	  "W_DJZX02_1":"待审核中",
	  "W_DJZX02_2":"审核未通过",
	  "W_DJZX02_3":"待支付",
	  "W_DJZX02_4":"待发货",
	  "W_DJZX02_5":"待确认收货",
	  "W_DJZX02_6":"已完成",
	  "W_DJZX02_7":"已冲红",
	  "W_DJZX02_8":"V7已开单",
	  "W_DJZX02_9":"异常订单",
	  "W_DJZX02_10":"已评价",
	  "W_DJZX02_11":"已终止",
	  "W_DJZX02_12":"已删除",
	  "W_DJZX02_13":"付款处理中",
	  "W_DJZX02_14":"待ERP生成订单",
	  "W_DJZX02_15":"待物流打包",
	  "W_DJZX02_16":"待物流发货",
	  "W_DJZX02_17":"线下汇款待审核",
	  
	  "ZCGS01_2":"待完善",
	  "ZCGS01_3":"待审核",
	  "ZCGS01_4":"审核完成",
	  "ZCGS01_5":"业务审核不通过",
	  "ZCGS01_6":"待业务审核",
	  "ZCGS01_7":"财务审核不通过",
	  
	  "ZCXX56_0":"PC注册",
	  "ZCXX56_1":"APP注册",
	  "ZCXX56_2":"初始导入",
	  
	  "THZT_0":"申请退货", 
	  "THZT_1":"取消申请",
	  "THZT_10":"同意申请",
	  "THZT_3":"不同意申请",
	  "THZT_11":"待完善快递信息",
	  "THZT_2":"入库完成",
	  "THZT_4":"同意退款",
	  "THZT_5":"不同意退款",
	  "THZT_6":"财务退款",
	  "THZT_7":"财务不退款",
	  "THZT_8":"退款处理中",
	  "THZT_9":"退款失败",
	  
	  "GSYHK11_0":"禁用",
	  "GSYHK11_1":"启用",
	  
	  "DJLX_0":"出库单",
	  "DJLX_1":"入库单",
	  "DJLX_2":"销售单",
	  "DJLX_3":"销售超时单",
	  "DJLX_4":"退货单",
	  "DJLX_5":"注册",
	  "DJLX_6":"定义商品",
	  "DJLX_7":"退货单退款",
	  "DJLX_8":"分销单提单发货",
	  
	  "FPLX_1":"",
	  "FPLX_2":"普通发票",
	  "FPLX_3":"增值税发票",
	  
	  "INVOICETYPE_1":"普通发票",
	  "INVOICETYPE_2":"增值税发票",
	  
	  "INVOICESTATE_0":"未申请",
	  "INVOICESTATE_1":"已申请",
	  "INVOICESTATE_2":"已审核",
	  "INVOICESTATE_3":"已驳回",
	  
	  "FPZT_1":"未审核",
	  "FPZT_2":"已审核",
	  "FPZT_3":"已驳回",
	  
	  "ACTIVITYTYPEID_1":"进行中",
	  
	  "ACTIVITYSTATE_0":"申请",
	  "ACTIVITYSTATE_1":"审核通过",
	  "ACTIVITYSTATE_2":"审核未通过",
	  "ACTIVITYSTATE_3":"手动停止",
	  
	  "PRODUCTSTATE_0":"申请",
	  "PRODUCTSTATE_1":"审核",
	  "PRODUCTSTATE_2":"终止",
	  "PRODUCTSTATE_3":"退出",
	  
	  "PRODUCTSTATEN_0":"申请中",
	  "PRODUCTSTATEN_1":"审核通过",
	  "PRODUCTSTATEN_2":"审核驳回",
		  
	 "XQZT_0":"发布",
	 "XQZT_1":"受理",
	 "XQZT_2":"已服务",
	 "XQZT_3":"无效",
	 
	 "JDZT_0":"未服务",
	 "JDZT_1":"已服务",
	 "JDZT_2":"未付款",
	 "JDZT_3":"已付款",
	 
	 "SKFS_6":"支付宝",
	 "SKFS_8":"银联",
	 "SKFS_9":"微信",
	 "SKFS_10":"线下汇款",
	 "SKFS_11":"钱包支付",
	
	 "HDSTATE_1":"进行中",
	 "HDSTATE_2":"即将开始",
	 "HDSTATE_3":"已过期",
	 
	 "PPQXZT_0":"待审核",
	 "PPQXZT_1":"通过",
	 "PPQXZT_2":"未通过",
	 "PPQXZT_3":"停用",
	 "PPQXZT_4":"已过期",
	 
	 "FWSZT_0":"待确认",
	 "FWSZT_1":"服务中",
	 "FWSZT_2":"已拒绝",
	 "FWSZT_3":"终止服务",
	 "FWSZT_4":"已过期",
}

//打印状态下拉HTML，当FLAG=1时表示新增编辑
function showStateHtml(columnKey,selectId,flag){
	//获取退货状态KEY
	var keyArr = [];
	for(var key in changeColumns){
		if(key.indexOf(columnKey+"_")==0){
			keyArr.push(key);
		}
	}
	//打印下拉选择框
	var stateHtml = "<select id=\""+selectId+"\" name=\""+selectId+"\">";
	if(flag == 1){
		stateHtml = stateHtml + "<option value=\"\">请选择</option>";
	}else{
		stateHtml = stateHtml + "<option value=\"\">全部</option>";
	}
	for(var i=0;i<keyArr.length;i++){
		var key = keyArr[i];
		stateHtml = stateHtml + "<option value=\""+key.split("_")[1]+"\">"+eval("changeColumns."+key)+"</option>";
	}
	stateHtml = stateHtml + "</select>";
	return stateHtml;
}

function showTHZT(){
  //获取退货状态KEY
  var thztKeyArr = [];
  for(var key in changeColumns){
    if(key.indexOf("THZT_")==0){
      thztKeyArr.push(key);
	}
  }
  //打印下拉选择框
  var thztHTML = "<select id=\"thzt\" name=\"thzt\">";
  for(var i=0;i<thztKeyArr.length;i++){
    var key = thztKeyArr[i];
    thztHTML = thztHTML + "<option value=\""+key.split("_")[1]+"\">"+eval("changeColumns."+key)+"</option>";
  }
  thztHTML = thztHTML + "</select>";
  return thztHTML;
}

//展示单据状态中文
function showDJZT(val){
	var str="";
	if(val == 1){
		str="待审核";
	}else if(val == 2){
		str="审核未通过";
	}else if(val == 3){
		str="待支付";
	}else if(val == 4){
		str="待发货";
	}else if(val == 5){
		str="待确认收货";
	}else if(val == 6){
		str="已完成";
	}else if(val == 7){
		str="已取消";
	}else if(val == 8){
		str="v7已开单";
	}else if(val == 9){
		str="问题订单";
	}else if(val == 10){
		str="已评价";
	}else if(val == 11){
		str="终止";
	}else if(val == 12){
		str="已删除";
	}
	return str;
}

function showSKFS(val){
	var str="";
	if(val == 6){
		str="支付宝";
	}else if(val == 8){
		str="银联";
	}else if(val == 9){
		str="微信";
	}else if(val == 10){
		str="线下汇款";
	}else if(val == 11){
		str="钱包支付";
	}  
	return str;
}