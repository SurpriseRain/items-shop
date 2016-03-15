package com.jlsoft.o2o.interfacepackage.alipay;

import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V9.V9THD;
import com.jlsoft.o2o.interfacepackage.alipayTimely.util.AlipaySubmit;
import com.jlsoft.o2o.interfacepackage.loop.ErpXSDD;
import com.jlsoft.o2o.order.service.OrderFlowManage;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

@Controller
@RequestMapping("/AlipayOnline")
public class AlipayOnline extends JLBill{
	private OrderFlowManage ofm;
	@Autowired
	private SmsService smsService;
	@Autowired
	private KmsService kmsService;
	@Autowired
	private ErpXSDD erpXSDD;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9THD v9THD;
	@Autowired
	private ManageLogs manageLogs;
	@Autowired
	private OrderFlowManage orderFlowManage;
	
	@Autowired
	public void setOfm(OrderFlowManage ofm) {
		this.ofm = ofm;
	}
	
	//给运营发送支付成功短信,运营电话号码
	String yyPhone = JlAppResources.getProperty("notice_Phone");
		
	@RequestMapping("/payOnline.action")
	public Map<String, Object> payOnline(String XmlData,String skfsArr,HttpServletResponse response) throws Exception {
		cds = new DataSet(XmlData);
		String payment = cds.getField("payment", 0);
		Map<String, Object> hm = new HashMap<String, Object>();
		String returnString="";
		// 1中行网银 2
		if ("1".equals(payment) || "2".equals(payment)) {
			
		} else if ("3".equals(payment)) {			
			return hm;
		}else if("6".equals(payment)){
			//写入收款方式
			orderFlowManage.insertOrderPayway(skfsArr);
			//支付宝
			returnString = alipayTimely(XmlData);
		}
		
		Writer pw= response.getWriter();
		pw.write(returnString);
		pw.flush();
		pw.close();
		Map map =new HashMap();
		return map;
	}
	/*
	 * 支付宝及时到账交易请求接口
	 */
	public String alipayTimely(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = JlAppResources.getProperty("timely_notify_url");
		//需http://格式的完整路径，不能加?id=123这类自定义参数
		//页面跳转同步通知页面路径
		String return_url = JlAppResources.getProperty("timely_return_url");
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		//商户订单号
		String out_trade_no = cds.getField("JYDH", 0);
		//商户网站订单系统中唯一订单号，必填
		//订单名称
		String subject = "";
		String sqlzt=" SELECT (select sum(t3.xsddi02*t3.xsddi05) from w_jyd t1,w_xsdd t2,w_xsdditem t3  where t1.jydh=t2.jydh and t2.xsdd01=t3.xsdd01 and t2.JYDH='"+out_trade_no+"') jyje,GROUP_CONCAT((SELECT SPXX04 FROM W_SPXX WHERE SPXX01=A.SPXX01)) DDMC " +
							     " FROM W_XSDDITEM A,W_XSDD B WHERE A.XSDD01=B.XSDD01 and B.JYDH='"+out_trade_no+"'";
		Map map=queryForMap(o2o, sqlzt);

		if(map!=null || !map.get("DDMC").equals("")){
			subject = (String) map.get("DDMC");
		}
		String amount=map.get("jyje").toString();
		//付款金额
		DecimalFormat df = new DecimalFormat("0.00");
		String total_fee = df.format(Double.parseDouble((amount)));
		//必填
		//订单描述
		String body = subject;
		//商品展示地址
		String show_url = JlAppResources.getProperty("timely_show_url");
		//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html
		//防钓鱼时间戳
		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数
		//客户端的IP地址
		String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1

		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", JlAppResources.getProperty("timely_alipayPID"));
        sParaTemp.put("seller_email", JlAppResources.getProperty("timely_seller_email"));
        sParaTemp.put("_input_charset", JlAppResources.getProperty("timely_input_charset"));
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		//String updateTHBStatussql="UPDATE W_DJZX t1 SET t1.w_djzx02='13' WHERE exists( select 1 from  W_XSDD t2 where t1.w_djzx01=t2.xsdd01 and  t2.JYDH='"+out_trade_no+"')";
		//execSQL(o2o,updateTHBStatussql,new HashMap());
		return sHtmlText;
	}
	
	//支付及时到账异步接口
	@RequestMapping("/update_notify_url.action")
	public Map<String, Object> update_notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> hm = new HashMap<String, Object>();
		String statusString=request.getParameter("status");
		String JYDH = request.getParameter("out_trade_no");
		//System.out.println("--------------------------ceshi------------------------------");
		//System.out.println("--------------------------支付宝回调接口------------------------------");
		//System.out.println("--------------------------statusString=="+statusString+"------------------------------");
		//System.out.println("-------s1===="+s1+"--------");
		try{
			if("1".equals(statusString)){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
			
				//更新交易单状态，为支付完成
				String jySQL = "UPDATE W_JYD SET ZT=2 WHERE JYDH='"+JYDH+"'";
				//查询结果成功，更新支付成功标记
				execSQL(o2o, jySQL, new HashMap());
				//更新订单状态
				String sql = "SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"'";
				List list = queryForList(o2o,sql);
				if(list != null && list.size()>0){
					Map map;
					for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						Map ddztMap = new HashMap();
						ddztMap.put("XSDD01",map.get("XSDD01").toString());
						ddztMap.put("trade_no", request.getParameter("trade_no"));
						JSONArray ddztJson = JSONArray.fromObject(ddztMap);
						ofm.updateOrderInfo(ddztJson.toString());
						try{
							//与erp对接
							String queryzidsql = "SELECT ztid from w_xsdd where xsdd01 ='"+map.get("XSDD01").toString()+"'";
							Map ztidMap = queryForMap(o2o, queryzidsql);
							Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
							if(erpMap.get("DJLX") != null){
								if(erpMap.get("DJLX").equals("V9")){
									erpXSDD.paySucessV9XSDD(map.get("XSDD01").toString());
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				try{
					//发送短信
					if(JLTools.getCurConf(2) == 1){
						String smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.HBID,A.XSDD01,B.ZCXX02 "+
						                           "FROM W_XSDD A,W_ZCGS B WHERE A.HBID=B.ZCXX01 AND A.JYDH='"+JYDH+"'";
						List mjList = queryForList(o2o,smsSQL);
						//跟买家发送短信
						String bhid="";
						String sjhm = "";
			            String xsddStr = "";
			            String content = "";
						Map mjMap;
						for(int i=0;i<mjList.size();i++){
							mjMap=(Map)mjList.get(i);
							bhid=mjMap.get("HBID").toString();
							sjhm=mjMap.get("SJHM").toString();
							xsddStr = xsddStr + mjMap.get("XSDD01").toString() + ",";
						}
						xsddStr=xsddStr.substring(0, xsddStr.length()-1);
						content = "尊敬客户您好，您的订单，单号："+xsddStr+" 已付款！感谢您的购买！";
						smsService.sendSms(2, JYDH, sjhm, content, bhid);
						//给运营发送订单付款通知短信
						content = "订单提醒,单号："+xsddStr+" 已付款！请跟踪处理";
						smsService.sendSms(2, JYDH, yyPhone, content, bhid);
						//跟卖家发送短信
						smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.ZTID,A.XSDD01,B.ZCXX02 FROM W_XSDD A,W_ZCGS B WHERE A.ZTID=B.ZCXX01 AND A.JYDH='"+JYDH+"'";
						List dpList = queryForList(o2o,smsSQL);
						Map smsMap;
						for(int i=0;i<dpList.size();i++){
							smsMap=(Map)dpList.get(i);
							content = "尊敬的"+smsMap.get("ZCXX02").toString()+"您好，您的店铺有新增订单，单号："+smsMap.get("XSDD01").toString()+"，请您尽快发货！";
							smsService.sendSms(2, JYDH, smsMap.get("SJHM").toString(), content, smsMap.get("ZTID").toString());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				//更新状态为已完成
				//String updateTHBStatussql="UPDATE W_DJZX t1 SET t1.w_djzx02='14' WHERE exists( select 1 from  W_XSDD t2 where t1.w_djzx01=t2.xsdd01 and  t2.JYDH='"+JYDH+"')";
				//execSQL(o2o,updateTHBStatussql,new HashMap());
				//System.out.println("-----支付成功，更新状态陈宫----------");
			}else{
				String updateTHBStatussql="UPDATE W_DJZX t1 SET t1.w_djzx02='3' WHERE exists( select 1 from  W_XSDD t2 where t1.w_djzx01=t2.xsdd01 and  t2.JYDH='"+JYDH+"')";
				execSQL(o2o,updateTHBStatussql,new HashMap());
			}
			
			hm.put("is_update_success", "1");
		}catch(Exception e){
			//System.out.println("-----支付成功，更新状态失败----------");
			e.printStackTrace();
			hm.put("is_update_success", "0");
			//更新状态为待支付
			String updateTHBStatussql="UPDATE W_DJZX t1 SET t1.w_djzx02='3' WHERE exists( select 1 from  W_XSDD t2 where t1.w_djzx01=t2.xsdd01 and  t2.JYDH='"+JYDH+"')";
			execSQL(o2o,updateTHBStatussql,new HashMap());
			throw new Exception(e.getMessage());
		}
		return hm;
	}
	
	/*
	 * 支付宝退款异步请求接口
	 */
	@RequestMapping("/THTBline.action")
	public Map<String, Object> THTBline(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String thdh=request.getParameter("thdh");
		String shyj=request.getParameter("shyj");
		String returnString="";
		String queryTHDH="SELECT  t1.THJE total_money,t3.trade_no from w_thd t1 ,w_xsdd t2,W_XSDDDZ t3 where t1.xsdd01=t2.XSDD01 and t1.XSDD01=t3.XSDD01"+
							" and  t1.thdh='"+thdh+"' ";
		List mjList = queryForList(o2o,queryTHDH);
		Map resultMap=(Map)mjList.get(0);
		String trade_no=resultMap.get("trade_no").toString();
		String total_money=resultMap.get("total_money").toString();
		returnString = THTBTimely(thdh,trade_no,total_money,shyj);
		//Writer pw= response.getWriter();
		//pw.write(returnString);
		//pw.flush();
		//pw.close();
		Map map =new HashMap();
		if(returnString.contains("<is_success>T</is_success>")){
			map.put("is_success", "1");
		}else{
			map.put("is_success", "0");
		}
		return map;
	}
	
	/*
	 * 支付宝退款逻辑处理
	 */
	public String THTBTimely(String thdh,String trade_no,String total_money,String shyj) throws Exception {
		String notify_url = JlAppResources.getProperty("timely_th_notify_path");
		//必填
		//退款当天日期
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String refund_date = format.format(date);
		//必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13

		//批次号
		format=new SimpleDateFormat("yyyyMMdd");
		
		String batch_no = format.format(date)+thdh;
		//必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001

		//退款笔数
		String batch_num = "1";
		//必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
		 DecimalFormat df = new DecimalFormat("0.00");
		//退款详细数据
		String detail_data = trade_no+"^"+df.format(Double.parseDouble(total_money))+"^协商退款";
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", JlAppResources.getProperty("timely_alipayPID"));
        sParaTemp.put("_input_charset", JlAppResources.getProperty("timely_input_charset"));
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_num", batch_num);
		sParaTemp.put("detail_data", detail_data);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		//更新退单状态为处理中
		if(sHtmlText.contains("<is_success>T</is_success>")){
			String updateTHBStatussql="UPDATE W_THD SET THZT='8',shyj='"+shyj+"' WHERE THDH='"+thdh+"'";
			execSQL(o2o,updateTHBStatussql,new HashMap());
		}
		return sHtmlText;
	}
	
	//支付及时到账退货异步接口
	@RequestMapping("/update_thd_notify_url.action")
	public Map<String, Object> update_thd_notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println("退款异步回调 start");
		Map<String, Object> hm = new HashMap<String, Object>();
		String batch_no=request.getParameter("batch_no");
		String thdh=batch_no.substring(8);

		try{
		String statusString=request.getParameter("status");
		if("1".equals(statusString)){
			//库存管理
			/**
			String queryString="select  t1.ztid,t2.SPXX01,t3.ck01,t1.XSDD01,t2.THSL from   w_thd  t1,w_thditem t2,w_xsdditem t3  where  t1.thdh=t2.thdh  and t1.xsdd01=t3.xsdd01 AND t2.spxx01=t3.spxx01 and t1.thdh='"+thdh+"'";
			Map infoMap = queryForMap(o2o, queryString);
			kmsService.insertGwcSpxx(infoMap.get("ztid").toString(), Double.parseDouble(infoMap.get("SPXX01").toString()), "0", 0, 0, 0, infoMap.get("ck01").toString(), "0",
					4, thdh, Integer.parseInt(infoMap.get("THSL").toString()),0 );
			*/
			//财务同意退款后短信通知客户
			if(JLTools.getCurConf(2) == 1){
				String smsSQL = "SELECT ifnull(B.XSDD21,'') SJHM,B.HBID FROM W_THD A,W_XSDD B WHERE A.XSDD01=B.XSDD01 AND A.THDH='"+thdh+"'";
				Map smsMap = queryForMap(o2o,smsSQL);
				String   context = "您的退/换货订单商家已审核通过，单号："+thdh+"，我们将在7个工作日内将退货款项返回到您的支付账号，请注意查收！";

				//发送短信
				smsService.sendSms(4, thdh, smsMap.get("SJHM").toString(), context, smsMap.get("HBID").toString());
			}
			//ERP交互
			String ztSQL = "SELECT ztid,PFD01,XSDD01 from w_thd where thdh ='"+thdh+"'";
			Map ztidMap = queryForMap(o2o, ztSQL);
			Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
			if(erpMap.get("DJLX") != null){
				erpMap.put("THDH",thdh);
				erpMap.put("FXDH",ztidMap.get("PFD01"));
				erpMap.put("XSDD01", ztidMap.get("XSDD01"));
				if(erpMap.get("DJLX").equals("V9")){
					String returnStr = v9THD.createFXDTK(erpMap);
					System.out.println("分销单退款制单："+returnStr);
					JSONObject jsonObject = JSONObject.fromObject(returnStr);
					JSONObject returnData =(JSONObject) jsonObject.get("data");
					if(!returnData.getString("JL_State").equals("1")){
						//写入W_WTDJ
						pubService.insertW_WTDJ(7,thdh,0,0);
						//写入错误日志
						manageLogs.writeLogs(7,thdh,"","车福",0,"生成退款单ERP失败"+returnData.get("JL_ERR"));
					}
					System.out.println(returnStr + "   @@@@@@@@@@##########");
				}
			}
			
			String sql="UPDATE W_THD SET THZT='6',TKDZH ='"+batch_no+"' WHERE THDH='"+thdh+"'";
			execSQL(o2o,sql,new HashMap());	
			hm.put("is_update_success", "1");
		}else{
			hm.put("is_update_success", "0");
			String result_details = request.getParameter("result_details");
			String sql="UPDATE W_THD SET THZT='9',TKDZH ='"+batch_no+"',ERROR='"+result_details+"' WHERE THDH='"+thdh+"'";
			execSQL(o2o,sql,new HashMap());
		}
		
		}catch(Exception e){
			hm.put("is_update_success", "0");
			throw new Exception(e.getMessage());
		}
	
		return hm;
	}
	/**
	 * @todo 更新银联表支付成功标记
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateW_XSDD_YLLS(String ylls) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String sql="UPDATE W_XSDD_YLLS SET CGBJ=1 WHERE YLLS='"+ylls+"'";
			execSQL(o2o,sql,resultMap);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
/*	*//**
	 * 支付宝-纯担保交易接口
	 *//*
	@RequestMapping("/alipayDBJY.action")
	public String alipayDBJY(String XmlData) {
		Map<String, Object> hm = new HashMap<String, Object>();
		String returnString="";
		try {
			cds = new DataSet(XmlData);
			//支付类型
			String payment_type = "1";
			//必填，不能修改
			//服务器异步通知页面路径
			String notify_url = JlAppResources.getProperty("notify_url");
			//需http://格式的完整路径，不能加?id=123这类自定义参数		
			//页面跳转同步通知页面路径
			String return_url = JlAppResources.getProperty("return_url");
			//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/		
			//卖家支付宝帐户
			String seller_email = new String(JlAppResources.getProperty("WIDseller_email").getBytes("ISO-8859-1"),"UTF-8");
			//必填		
			//商户订单号
			String out_trade_no = new String(cds.getField("XSDD01", 0).getBytes("ISO-8859-1"),"UTF-8");
			//商户网站订单系统中唯一订单号，必填		
			//订单名称
			String order_name = "";
			String sqlzt=" SELECT GROUP_CONCAT((SELECT SPXX04 FROM W_SPXX WHERE SPXX01=A.SPXX01)) DDMC " +
					     " FROM W_XSDDITEM A WHERE A.XSDD01='"+out_trade_no+"'";
	        Map map=queryForMap(o2o, sqlzt);
	        
	        if(map!=null || !map.get("DDMC").equals("")){
	        	order_name = (String) map.get("DDMC");
	        }
			 
			String subject = order_name;//new String(order_name.getBytes("ISO-8859-1"),"UTF-8");
			//必填		
			//付款金额
			DecimalFormat df = new DecimalFormat("0.00");
			String orderAmount = df.format(Double.parseDouble((cds.getField("AMOUNT", 0))));
			String price = new String(orderAmount.getBytes("ISO-8859-1"),"UTF-8");
			//商品数量
			String quantity = "1";
			//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
			//物流费用
			String logistics_fee = df.format(Double.parseDouble(cds.getField("logistics_fee", 0)));//"0.00";
			//必填，即运费
			//物流类型
			String logistics_type = cds.getField("logistics_type", 0);//"EXPRESS";
			//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
			//物流支付方式
			String logistics_payment = cds.getField("logistics_payment", 0);//"SELLER_PAY";
			//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
					
			//订单描述	
			String order_detail = "";
			String body = new String(order_detail.getBytes("ISO-8859-1"),"UTF-8");
			//商品展示地址
			String sp_url = "";
			String show_url = new String(sp_url.getBytes("ISO-8859-1"),"UTF-8");
			
			//收货人姓名
			String receive_name = cds.getField("WIDreceive_name", 0);//new String(cds.getField("WIDreceive_name", 0).getBytes("ISO-8859-1"),"UTF-8");
			//如：张三

			//收货人地址
			String receive_address = cds.getField("WIDreceive_address", 0);//new String(cds.getField("WIDreceive_address", 0).getBytes("ISO-8859-1"),"UTF-8");
			//如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号

			//收货人邮编
			String receive_zip = new String(cds.getField("WIDreceive_zip",0).getBytes("ISO-8859-1"),"UTF-8");
			//如：123456

			//收货人电话号码
			String receive_phone = new String(cds.getField("WIDreceive_phone",0).getBytes("ISO-8859-1"),"UTF-8");
			//如：0571-88158090

			//收货人手机号码
			String receive_mobile = new String(cds.getField("WIDreceive_mobile",0).getBytes("ISO-8859-1"),"UTF-8");
			//如：13312341234
			//////////////////////////////////////////////////////////////////////////////////
			
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "create_partner_trade_by_buyer");
	        sParaTemp.put("partner", JlAppResources.getProperty("alipayPID"));
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("seller_email", seller_email);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			sParaTemp.put("price", price);
			sParaTemp.put("quantity", quantity);
			sParaTemp.put("logistics_fee", logistics_fee);
			sParaTemp.put("logistics_type", logistics_type);
			sParaTemp.put("logistics_payment", logistics_payment);
			sParaTemp.put("body", body);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("receive_name", receive_name);
			sParaTemp.put("receive_address", receive_address);
			sParaTemp.put("receive_zip", receive_zip);
			sParaTemp.put("receive_phone", receive_phone);
			sParaTemp.put("receive_mobile", receive_mobile);
			
			//建立请求
		    returnString = AlipaySubmit.buildRequest(sParaTemp,"get","提交支付");
		    
		    if(returnString!=null){
		    	//deleteCZZD(out_trade_no);
				//insertYH(XmlData);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
			return returnString;
	}
	
	//支付宝服务器异步通知(获取支付宝POST过来反馈信息)
	@RequestMapping("/notify_url.action")
	public Map<String, Object> notify_url(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> hm = new HashMap<String, Object>();
		try
	    {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        
		System.out.println(out_trade_no+":"+trade_no+":"+trade_status);
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if(AlipayNotify.verify(params)){//验证成功
			System.out.println("--------验签成功-----------");
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//判断该笔订单是否在商户网站中已经做过处理
			//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
			//如果有做过处理，不执行商户的业务程序
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("WAIT_BUYER_PAY")){//该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
				
				System.out.println("--------产生了交易...没有付款...-----------");
				
			}else if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){//该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
				
				System.out.println("--------付款成功...没有发货...-----------");
				
			}else if(trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")){//该判断表示卖家已经发了货，但买家还没有做确认收货的操作
				
				System.out.println("--------已经发了货...开始调用发货接口...-----------");
				
			}else if(trade_status.equals("TRADE_FINISHED")){//该判断表示买家已经确认收货，这笔交易完成
				
				System.out.println("--------确认收货...交易完成...-----------");
				
			}
	

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            hm.put("state", "success");
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			System.out.println("-----------验签失败-----------");
			hm.put("state", "fail");

		}
	    }catch(Exception e) {
	    	hm.put("state", "fail");
			e.printStackTrace();
		}
		
		return hm;
		
	}
	
	//支付宝页面跳转同步通知(获取支付宝GET过来反馈信息)
	@RequestMapping("/return_url.action")
	public Map<String, Object> return_url(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> hm = new HashMap<String, Object>();
		try{
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			*//**try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (Exception e) {
				hm.put("state", "fail");
				e.printStackTrace();
			}*//*
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		System.out.println(out_trade_no+":"+trade_no+":"+trade_status);
		
		Map ddztMap = new HashMap();
		ddztMap.put("XSDD01", out_trade_no);
		ddztMap.put("trade_no", trade_no);
		JSONArray ddztJson = JSONArray.fromObject(ddztMap);
		
		if(verify_result){//验证成功
			System.out.println("--------验签成功-----------");
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("WAIT_SELLER_SEND_GOODS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				
				System.out.println("--------付款成功...没有发货...-----------");
				
				//执行业务逻辑
				ofm.updateOrderInfo(ddztJson.toString());
				
			}
			
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			hm.put("state", "success");
			response.sendRedirect("http://127.0.0.1:8088/customer/qfy/shopping/paysuccess.html");
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
			//该页面可做页面美工编辑
			System.out.println("-----------验签失败-----------");
			hm.put("state", "fail");
			response.sendRedirect("/customer/qfy/shopping/payerror.html");

		}
		}catch(Exception e) {
			hm.put("state", "fail");
			response.sendRedirect("/customer/qfy/shopping/payerror.html");
			e.printStackTrace();
		}
		return hm;
		
	}
	
	/**
	 * 支付宝-确认发货接口
	 */
	/*@RequestMapping("/alipayQRFH.action")
	public Map alipayQRFH(String XmlData) {
		Map<String, Object> hm = new HashMap<String, Object>();
		String returnString="";
		try {
			cds = new DataSet(XmlData);
			//支付宝交易号
			String trade_no = new String(cds.getField("WIDtrade_no",0).getBytes("ISO-8859-1"),"UTF-8");
			//必填

			//物流公司名称
			String logistics_name = new String(cds.getField("WIDlogistics_name",0).getBytes("ISO-8859-1"),"UTF-8");
			//必填

			//物流发货单号

			String invoice_no = new String(cds.getField("WIDinvoice_no",0).getBytes("ISO-8859-1"),"UTF-8");
			//物流运输类型
			String transport_type = new String(cds.getField("WIDtransport_type",0).getBytes("ISO-8859-1"),"UTF-8");
			//三个值可选：POST（平邮）、EXPRESS（快递）、EMS（EMS）
			
			
			//////////////////////////////////////////////////////////////////////////////////
			
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "send_goods_confirm_by_platform");
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("trade_no", trade_no);
			sParaTemp.put("logistics_name", logistics_name);
			sParaTemp.put("invoice_no", invoice_no);
			sParaTemp.put("transport_type", transport_type);
			
			//建立请求
		    returnString = AlipaySubmit.buildRequest("", "", sParaTemp);
		    System.out.println("返回数据："+returnString);
		    hm.put("STATE", "1");
		    if(returnString!=null){
		   
		    }
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", "1");
		}
			return hm;
	}*/
}
