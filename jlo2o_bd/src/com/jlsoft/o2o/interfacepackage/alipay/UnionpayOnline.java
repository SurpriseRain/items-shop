package com.jlsoft.o2o.interfacepackage.alipay;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.GnetePayUtils;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.HttpUtils;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.Md5;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils;
import com.jlsoft.o2o.interfacepackage.loop.ErpXSDD;
import com.jlsoft.o2o.order.service.OrderFlowManage;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

@Controller
@RequestMapping("/UnionpayOnline")
public class UnionpayOnline extends JLBill{
	@Autowired
	private OrderFlowManage ofm;
	@Autowired
	private SmsService smsService;
	@Autowired
	private OrderFlowManage orderFlowManage;
	@Autowired
	private ErpXSDD erpXSDD;
	@Autowired
	private PubService pubService;
	
	//给运营发送支付成功短信,运营电话号码
	String yyPhone = JlAppResources.getProperty("notice_Phone");
	
	/**
	 * @todo 获取银联记录，判断支付是否成功
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectW_XSDD_YLLS")
	public Map<String,Object> selectW_XSDD_YLLS(String json) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			cds = new DataSet(json);
			String sql = "SELECT YLLS,JYDH,ZFDH,SJCQM,CGBJ,DATE_FORMAT(ZFSJ,'%Y-%m-%d')ZFSJ FROM W_XSDD_YLLS WHERE YLLS='"+cds.getField("YLLS", 0)+"'";
			resultMap = queryForMap(o2o,sql);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入银联流水记录表
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertW_XSDD_YLLS")
	public Map<String,Object> insertW_XSDD_YLLS(String json) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			cds = new DataSet(json);
			String sql = "";
			//插入收款记录
			//orderFlowManage.insertOrderPayway(skfsArr);
			//插入银联流水号
			sql = "INSERT INTO W_XSDD_YLLS(YLLS,JYDH,ZFDH,SJCQM,CGBJ,ZFSJ,JYJE) " +
					           "VALUES(YLLS?,JYDH?,ZFDH?,SJCQM?,0,now(),JYJE?)";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			//更新单据状态
			//String JYDH = cds.getField("JYDH", 0);
			//sql = "UPDATE W_DJZX SET W_DJZX02=13 WHERE W_DJZX01 IN (SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"')";
			//execSQL(o2o, sql, row);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 回调时更新单据状态和回调信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateW_XSDDZT")
	public Map<String,Object> updateW_XSDDZT(String json) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			cds = new DataSet(json);
			String sql = "";
			//跟新银联信息
			sql = "UPDATE W_XSDD_YLLS SET ZFDH=ZFDH? ,SJCQM=SJCQM?,ZFSJ=now() WHERE YLLS=YLLS?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			//更新单据状态
			String JYDH = cds.getField("JYDH", 0);
			sql = "UPDATE W_DJZX SET W_DJZX02=13 WHERE W_DJZX01 IN (SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"')";
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	@RequestMapping("/auditUnionpayReceiveBack")
	public Map<String, Object> auditUnionpayReceiveBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println("pay start ************");
		Map<String, Object> hm = new HashMap<String, Object>();
		String RespCode = request.getParameter("RespCode");
		String YLLS = request.getParameter("YLLS");
		String sql = "";
		//查询交易流水号
		sql = "SELECT YLLS,JYDH,ZFDH,SJCQM,CGBJ,DATE_FORMAT(ZFSJ,'%Y-%m-%d')ZFSJ FROM W_XSDD_YLLS WHERE YLLS='"+YLLS+"'";
		Map resultMap = queryForMap(o2o,sql);
		String JYDH= resultMap.get("JYDH").toString();
		if(RespCode.equals("00")){
			if(((Integer)resultMap.get("CGBJ")).intValue()==0){
				JSONObject jasonObject = JSONObject.fromObject(resultMap);
				Map returnMap = callUnionpaySucess(jasonObject.toString(),request);
				if(returnMap.get("STATE").equals("success")){
					hm.put("STATE", "success");
				}else{
					hm.put("STATE", "failure");
				}
			}else{
				hm.put("STATE", "success");
			}
		}else{
			//交易失败，更新状态为3
			sql = "UPDATE W_DJZX SET W_DJZX02=3 WHERE W_DJZX01 IN (SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"')";
			execSQL(o2o, sql, new HashMap());
			hm.put("STATE", "failure");
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
	
	/**
	 * @todo 银联支付完成处理
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	//@RequestMapping("/callUnionpaySucess")
	public Map<String,Object> callUnionpaySucess(String json,HttpServletRequest request) throws Exception{
		System.out.println("银联支付回调 start");
		cds = new DataSet(json);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			//商户网站域名
			String MerDomain = JlAppResources.getProperty("unionpay_MerDomain");
			String url = JlAppResources.getProperty("unionpay_srchUrl");
			//组装请求参数
			StringBuffer SourceText = new StringBuffer();
			SourceText.append("TranType=").append("100");
			SourceText.append("&JavaCharset=").append("UTF-8");
			SourceText.append("&Version=").append("V60");
			SourceText.append("&UserId=").append(JlAppResources.getProperty("unionpay_UserId"));
			SourceText.append("&Pwd=").append(Md5.md5(JlAppResources.getProperty("unionpay_Pwd")));
			SourceText.append("&MerId=").append(JlAppResources.getProperty("unionpay_MerId"));
			SourceText.append("&PayStatus=").append("2");
			SourceText.append("&OrderNo=").append(cds.getField("YLLS", 0));
			String BeginTime = GnetePayUtils.GetCurrentDate("yyyy-MM-dd") + " 00:00:00";
			String EndTime = GnetePayUtils.GetCurrentDate("yyyy-MM-dd") + " 23:59:59";
			SourceText.append("&BeginTime=").append(BeginTime);
			SourceText.append("&EndTime=").append(EndTime);
			//发起请求返回值：Resp
			String path = request.getRealPath("WEB-INF/classes/com/jlsoft/o2o/interfacepackage/alipay/unionpay/");
			String JKSPath = path + "www.gnetpg.com.jks";
			path=path.replaceAll("\\\\", "/");
			HttpUtils http = new HttpUtils();
			http.setJKSPath(JKSPath);
			http.setJKSPath(JlAppResources.getProperty("JKSPath_PASS"));
			String Resp = http.doPost(url, SourceText.toString(), "UTF-8", MerDomain);
			System.out.println(path+"   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println(Resp+"   ################################");
			//返回查询结果集处理
			Resp = Resp.replace("\\n","&");
			String[] respArrays = Resp.split("&");
			//处理查询结果
			if(Resp != null && Resp.length()>0 && respArrays.length>0){
				String JYDH = cds.getField("JYDH", 0);
				//更新交易单状态，为支付完成
				String jySQL = "UPDATE W_JYD SET ZT=2 WHERE JYDH='"+JYDH+"'";
				execSQL(o2o,jySQL,resultMap);
				//查询结果成功，更新银联支付成功标记
				updateW_XSDD_YLLS(cds.getField("YLLS", 0));
				//更新订单状态
				String sql = "SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"'";
				List list = queryForList(o2o,sql);
				if(list != null && list.size()>0){
					Map map;
					for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						Map ddztMap = new HashMap();
						ddztMap.put("XSDD01",map.get("XSDD01").toString());
						ddztMap.put("trade_no", cds.getField("YLLS", 0));
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
				//封装返回值
				/**
				resultMap.put("ShoppingDate", respArrays[0]);//交易日期
				resultMap.put("OrderAmount", respArrays[1]);//支付金额
				resultMap.put("OrderNo", respArrays[2]);//商户订单号
				resultMap.put("TermNo", respArrays[3]);//商户终端号
				resultMap.put("SystemSSN", respArrays[4]);//系统参考号
				resultMap.put("RespCode", respArrays[5]);//响应码
				*/
				resultMap.put("STATE", "success");
			}else{
				resultMap.put("STATE", "failure");
			}
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 退款与银联发起交互
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> refund(String json,HttpServletRequest request) throws Exception{
		cds = new DataSet(json);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			//商户网站域名
			String MerDomain = JlAppResources.getProperty("unionpay_MerDomain");
			String url = JlAppResources.getProperty("unionpay_srchUrl");
			//组装请求参数
			StringBuffer SourceText = new StringBuffer();
			SourceText.append("&Version=").append("V36");
			SourceText.append("&TranType=").append("31");
			SourceText.append("&JavaCharset=").append("UTF-8");
			SourceText.append("&MerId=").append(JlAppResources.getProperty("unionpay_MerId"));
			SourceText.append("&OrderNo=").append(cds.getField("YLLS", 0));
			SourceText.append("&ShoppingDate=").append(cds.getField("JYRQ", 0)); //交易日期
			SourceText.append("&PayAmount=").append(cds.getField("JYJE", 0)); //交易金额
			SourceText.append("&RefundAmount=").append(cds.getField("TKJE", 0)); //退款金额
			SourceText.append("&Reserved=").append("");
			//签名数据
			PayUtils payUtils = new PayUtils();
			String SignedMsg = "";
			StringBuffer singedText= new StringBuffer();
			singedText.append("JavaCharset=").append("UTF-8");
			singedText.append("&MerId=").append(JlAppResources.getProperty("unionpay_MerId"));
			singedText.append("&OrderNo=").append(cds.getField("YLLS", 0));
			singedText.append("&PayAmount=").append(cds.getField("JYJE", 0)); //交易金额
			singedText.append("&RefundAmount=").append(cds.getField("TKJE", 0)); //退款金额
			singedText.append("&Reserved=").append("");
			singedText.append("&ShoppingDate=").append(cds.getField("JYRQ", 0)); //交易日期
			singedText.append("&TranType=").append("31");
			singedText.append("&Version=").append("V36");
			SignedMsg = payUtils.md5(singedText.toString()+"&"+payUtils.md5(JlAppResources.getProperty("unionpay_PKey")));
			
			SourceText.append("&SignMsg=").append(SignedMsg);//签名
			//发起请求返回值：Resp
			String path = request.getRealPath("WEB-INF/classes/com/jlsoft/o2o/interfacepackage/alipay/unionpay/");
			String JKSPath = path + "www.gnetpg.com.jks";
			path=path.replaceAll("\\\\", "/");
			HttpUtils http = new HttpUtils();
			http.setJKSPath(JKSPath);
			http.setJKSPath(JlAppResources.getProperty("JKSPath_PASS"));
			String Resp = http.doPost(url, SourceText.toString(), "UTF-8", MerDomain);
			System.out.println("returnMessage："+Resp+"   ################################");
			//返回查询结果集处理
			Resp = Resp.replace("\\n","&");
			String[] respArrays = Resp.split("&");
			//处理查询结果
			if(Resp != null && Resp.length()>0 && respArrays.length>0){
				//封装返回值
				String parameter="";
				for(int i=0;i<respArrays.length;i++){
					parameter = respArrays[i];
					if(parameter.split("=")[0].equals("Code")){
						if((parameter.split("=")[1].trim()).equals("0000")){
							resultMap.put("STATE", "success");
						}else{
							resultMap.put("STATE", "failure");
						}
						break;
					}
				}
			}
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}

	
	/**
	 * 根据交易单号查询订单总金额
	 * @param json 交易单号
	 * @return
	 * @throws Exception
	 * 2015-11-18 上午10:08:22
	 */
	@RequestMapping("/queryOrderAmount")
	public Map<String,Object> queryOrderAmount(String json) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			cds = new DataSet(json);
			//商户订单号
			String out_trade_no = cds.getField("JYDH", 0);
			
			String querySql=" SELECT (select sum(t3.xsddi02*t3.xsddi05) from w_jyd t1,w_xsdd t2,w_xsdditem t3  where t1.jydh=t2.jydh and t2.xsdd01=t3.xsdd01 and t2.JYDH='"+out_trade_no+"') jyje,GROUP_CONCAT((SELECT SPXX04 FROM W_SPXX WHERE SPXX01=A.SPXX01)) DDMC " +
				     " FROM W_XSDDITEM A,W_XSDD B WHERE A.XSDD01=B.XSDD01 and B.JYDH='"+out_trade_no+"'";
			Map map=queryForMap(o2o, querySql);
			
			String amount= "0";
			//检查map是否为空
			if(map ==null || map.isEmpty() || map.get("jyje")==null){
				resultMap.put("amount", amount);
				return resultMap;
			}
			if(!map.containsKey("jyje") || map.get("jyje") == null){
				resultMap.put("amount", amount);
				return resultMap;				
			}
			amount = map.get("jyje").toString();			
			resultMap.put("amount", amount);
		}catch(Exception ex){
			resultMap.put("amount", "0");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * 参照支付宝的处理方式，处理银联在线支付
	 * @param XmlData 请求的数据
	 * @param response
	 * @return
	 * @throws Exception
	 * 2015-11-18 下午3:06:24
	 */
	@RequestMapping("/payOnline.action")
	public Map<String, Object> payOnline(String XmlData, HttpServletResponse response) throws Exception {
		cds = new DataSet(XmlData);
		String payment = cds.getField("payment", 0);

		Map map =new HashMap();
		// 收款方式:支付宝支付6  银联支付8，微信支付9
		if(!"8".equals(payment)){
			return map;
		}
		//交易单号
		String JYDH = cds.getField("JYDH", 0);
		
		//根据交易单号查询交易金额
		JSONObject queryObj = new JSONObject();
		queryObj.put("JYDH", JYDH);
		Map amountMap = queryOrderAmount(queryObj.toString());
		String amount = "0";
		if(amountMap !=null){
			amount = amountMap.get("amount").toString();
		}
		if("0".equals(amount)){
			return map;
		}
		
		//将订单支付信息，写入W_XSDD_YLLS表
		String PayNo = "";
		String SignMsg = "银联支付开始";
		//商户订单号
		String OrderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		JSONObject obj = new JSONObject();
		obj.put("JYDH", JYDH);
		obj.put("YLLS", OrderNo);
		obj.put("ZFDH", PayNo);
		obj.put("SJCQM", SignMsg);
		obj.put("JYJE", amount);
		map = insertW_XSDD_YLLS(obj.toString());
		if(!map.containsKey("STATE") ||"failure".equals(map.get("STATE"))){
			return map;
		}
		
		//组织表单，模拟提交
		//支付方式
		String EntryMode = cds.getField("EntryMode", 0);
		//银行代码，直通车用
		String BankCode = cds.getField("nBankCode", 0);
		//构建form表单html代码
		Map<String, String> formMap = buildFormData(OrderNo, amount, JYDH, BankCode, EntryMode);		
		String returnString = buildFormCode(formMap, "post","确定");
		
		Writer pw= response.getWriter();
		pw.write(returnString);
		pw.flush();
		pw.close();
		
		return map;
	}
	
	/**
	 * 参照支付宝的处理方式，构建Form表单需要提交的Map数据
	 * @param OrderNo 订单号
	 * @param amount 订单金额
	 * @param JYDH 交易单号
	 * @param BankCode银行代码，直通车用
	 * @param EntryMode支付方式
	 * @return
	 * 2015-11-18 下午2:33:58
	 */
	private Map<String, String> buildFormData(String OrderNo, String amount,String JYDH, String BankCode,String EntryMode){
		//定义变量
		String MerId,  CurrCode, OrderType, CallBackUrl, ResultMode, Reserved01, Reserved02, SourceText, PKey, SignedMsg;
		//交易密钥
		PKey = JlAppResources.getProperty("unionpay_PKey");
		//商户ID参数
		MerId = JlAppResources.getProperty("unionpay_MerId");
		//货币代码，值为：CNY
		CurrCode = "CNY"; 
		//支付结果接收URL
		CallBackUrl = JlAppResources.getProperty("unionpay_returnUrl")+"?JYDH="+JYDH;
	    //支付结果返回方式(0-成功和失败支付结果均返回；1-仅返回成功支付结果)
		ResultMode = "0";
		Reserved01 = "just"; //保留域1
		Reserved02 = "atest"; //保留域2
		//订单类型
		OrderType = "B2B";
		//签名数据
		SignedMsg = "";
		SourceText = MerId + OrderNo + amount + CurrCode + OrderType + CallBackUrl+ ResultMode + BankCode + EntryMode + Reserved01+ Reserved02;
		PayUtils payUtils = new PayUtils();
		SignedMsg = payUtils.md5(SourceText + payUtils.md5(PKey));
        //验证签名
		boolean ret = payUtils.checkSign(SourceText,SignedMsg,PKey);
		Map<String, String> dataMap = new HashMap<String, String>();		
		if(!ret){
			return dataMap;
		}
		//将数据放入map
		dataMap.put("MerId", MerId);
		dataMap.put("OrderNo", OrderNo);
		dataMap.put("OrderAmount", amount);
		dataMap.put("CurrCode", CurrCode);
		dataMap.put("CallBackUrl", CallBackUrl);
		dataMap.put("ResultMode", ResultMode);
		dataMap.put("OrderType", OrderType);
		dataMap.put("BankCode", BankCode);
		dataMap.put("EntryMode", EntryMode);
		dataMap.put("Reserved01", Reserved01);
		dataMap.put("Reserved02", Reserved02);
		dataMap.put("SignMsg", SignedMsg);
		return dataMap;
	}
	
	/**
	 * 参照支付宝的处理方式，构建form表单html代码
	 * @param map form表单数据map
	 * @param strMethod 提交方式
	 * @param strButtonName 按钮名称
	 * @return
	 * 2015-11-18 下午2:48:54
	 */
	private String buildFormCode(Map<String, String> map, String strMethod, String strButtonName){
		
		if(map == null || map.isEmpty()){
			return "0";
		}
		
		List<String> keys = new ArrayList<String>(map.keySet());
		StringBuilder formBuilder = new StringBuilder();
		
		//组织form表单的html代码
		formBuilder.append("<form method=\"").append(strMethod)
				.append("\" name=\"SendOrderForm\" action=\"")
				.append(JlAppResources.getProperty("unionpay_paySend"))
				.append("\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) map.get(name);
            formBuilder.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        
        formBuilder.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        formBuilder.append("<script>document.forms['SendOrderForm'].submit();</script>");
		return formBuilder.toString();
	}
}