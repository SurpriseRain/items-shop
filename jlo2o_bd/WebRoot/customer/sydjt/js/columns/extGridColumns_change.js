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
	  "W_DJZX02_14":"付款完成",
	  "W_DJZX02_15":"待ERP发货",
	  "W_DJZX02_16":"申请退货",
	  "W_DJZX02_17":"商家同意退货",
	  "W_DJZX02_18":"退货完成",
	  
	  "ZCGS01_2":"待完善",
	  "ZCGS01_3":"待审核",
	  "ZCGS01_4":"已审核",
	  "ZCGS01_5":"审核不通过",
	  
	  "ZCXX56_0":"PC注册",
	  "ZCXX56_1":"APP注册",
	  "ZCXX56_2":"初始导入",
	  
	  "THZT_0":"申请退换货",
	  "THZT_1":"取消申请",
	  "THZT_2":"同意申请",
	  "THZT_3":"不同意申请",
	  "THZT_4":"同意退款",
	  "THZT_5":"不同意退款",
	  "THZT_6":"财务退款",
	  "THZT_7":"财务不退款",
	  "THZT_8":"退款处理中",
	  "THZT_9":"退款失败",
	  "THZT_10":"退货处理中",
	  "THZT_11":"退货完成",
	  "THZT_12":"换货处理中",
	  "THZT_13":"换货完成",
	  
	  
	  "GSYHK11_0":"禁用",
	  "GSYHK11_1":"启用",
	  
	  "DJLX_0":"出库单",
	  "DJLX_1":"入库单",
	  "DJLX_2":"销售单",
	  "DJLX_3":"销售超时单",
	  "DJLX_4":"退货单",
	  "DJLX_5":"注册",
	  "DJLX_6":"定义商品",
	  
	  "FPLX_1":"",
	  "FPLX_2":"普通发票",
	  "FPLX_3":"增值税发票"
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