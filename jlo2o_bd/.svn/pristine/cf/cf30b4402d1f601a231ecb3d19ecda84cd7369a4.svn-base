package com.jlsoft.c2b.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
import com.jlsoft.o2o.interfacepackage.loop.ErpXSDD;
import com.jlsoft.o2o.interfacepackage.weixin.RequestHandler;
import com.jlsoft.o2o.interfacepackage.weixin.client.ClientCustomSSL;
import com.jlsoft.o2o.interfacepackage.weixin.util.WeixinUtil;
import com.jlsoft.o2o.interfacepackage.weixin.util.XMLUtil;
import com.jlsoft.o2o.order.service.OrderFlowManage;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

@Controller
@RequestMapping("/WeixinController_FW")
public class WeixinController_FW extends JLBill {
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
	/*
	 * 获取微信二维码接口
	 */
	@RequestMapping("/getcoreurl_fw.action")
	public Map<String, Object> getcoreurl(String XmlData, String skfsArr,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//写入收款方式
		orderFlowManage.insertOrderPayway(skfsArr);
		
		Map resultMap = new HashMap<String, String>();
		cds = new DataSet(XmlData);
		String out_trade_no = cds.getField("JYDH", 0);
		String sqlzt = " SELECT TAKEORDERSOURCE, TOTALPRICE jyje FROM "
				+ "TAKEORDER "
				+ "WHERE OrderNumber = '" + out_trade_no + "'";
		Map map = queryForMap(o2o, sqlzt);
		String body = "";
		body = "服务";
		/*if (map != null || !map.get("TAKEORDERSOURCE").equals("")) {
			body = (String) map.get("TAKEORDERSOURCE");
		}
		if ("".equals(body) || null == body) {
			throw new Exception("body is not null");
		}*/
		String nonce_str = WeixinUtil.create_nonce_str();

		String amount = map.get("jyje").toString();
		// 付款金额
		DecimalFormat df = new DecimalFormat("0");
		String total_fee = df.format(Double.valueOf(amount)*100);
		String spbill_create_ip = request.getRemoteAddr();
		// 
		String appId = JlAppResources.getProperty("WEIXIN_APP_ID");
		String partner = JlAppResources.getProperty("WEIXIN_PARTNER");
		String notify_url = JlAppResources.getProperty("WEIXIN_NOTIFY_FW_URL");
		String partnerKey = JlAppResources.getProperty("WEIXIN_PARTNER_KEY");
		String trade_type = "NATIVE";
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", partner);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", "NATIVE");
		// 签名信息
		String sign = WeixinUtil.getsign2(packageParams, partnerKey);
		String payXml = "<xml>" + "<appid>" + appId + "</appid>" + "<body>"
				+ body + "</body>" + "<mch_id>" + partner + "</mch_id>"
				+ "<nonce_str>" + nonce_str + "</nonce_str>" + "<notify_url>"
				+ notify_url + "</notify_url>" + "<out_trade_no>"
				+ out_trade_no + "</out_trade_no>" + "<spbill_create_ip>"
				+ spbill_create_ip + "</spbill_create_ip>" + "<total_fee>"
				+ total_fee + "</total_fee>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		String resultXml = (String) WeixinUtil.httpRequest(
				WeixinUtil.WX_PAY_UNIFIEDORDER_URL, "POST", payXml, "1");
		System.out.println(resultXml);
		Map returnMap = XMLUtil.doXMLParse(resultXml);
		String return_code = (String) returnMap.get("return_code");
		String result_code = (String) returnMap.get("result_code");
		if ("SUCCESS".equals(return_code)) {
			if ("SUCCESS".equals(result_code)) {
				String code_url=(String) returnMap.get("code_url");
				String prepay_id=(String) returnMap.get("prepay_id");
				resultMap.put("flag", "1");
				resultMap.put("code_url", code_url);
			} else {
				String err_code_des=returnMap.get("err_code_des").toString();
				resultMap.put("flag", "0");
				resultMap.put("err_code_des", err_code_des);
			}
		}else{
			String return_msg = (String) returnMap.get("return_msg");
			
			resultMap.put("flag", "0");
			resultMap.put("err_code_des", return_msg);
		}
		return resultMap;
	}
	
	/*
	 * 查询订单信息接口
	 */
	@RequestMapping("/querOrder_fw.action")
	public Map<String, Object> querorder(String XmlData, String skfsArr,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		Map resultMap = new HashMap<String, String>();
		cds = new DataSet(XmlData);
		String out_trade_no = cds.getField("JYDH", 0);
		String partnerKey = JlAppResources.getProperty("WEIXIN_PARTNER_KEY");
		String nonce_str = WeixinUtil.create_nonce_str();
		String appId = JlAppResources.getProperty("WEIXIN_APP_ID");
		String partner = JlAppResources.getProperty("WEIXIN_PARTNER");
		String returnmsg = WeixinUtil.orderQuery(appId, partner, null,
				out_trade_no, nonce_str, partnerKey);
		Map returnMap = XMLUtil.doXMLParse(returnmsg);
		String trade_state = (String) returnMap.get("trade_state");
		String transaction_id = (String) returnMap.get("transaction_id");
		resultMap.put("trade_state", trade_state);
		resultMap.put("transaction_id", transaction_id);
		return resultMap;
	}
	
	//支付及时到账异步接口
	@RequestMapping("/update_notify_fw_url.action")
	public Map<String, Object> update_notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> hm = new HashMap<String, Object>();
		String statusString = request.getParameter("status");
		String JYDH = request.getParameter("out_trade_no");
		String trade_no = request.getParameter("trade_no");
		try{
			if("1".equals(statusString)){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
			
				//更新交易单状态，为支付完成
				String jySQL = "UPDATE TAKEORDER SET PAYTYPE=9,TAKEORDERSTATUS=3,PAYORDERNUMBER='" + trade_no + "' WHERE OrderNumber='" + JYDH + "'";
				//查询结果成功，更新支付成功标记
				execSQL(o2o, jySQL, new HashMap());
			}else{
				String updateTHBStatussql = "UPDATE TAKEORDER SET TAKEORDERSTATUS=2 WHERE OrderNumber='" + JYDH + "'";
				execSQL(o2o,updateTHBStatussql,new HashMap());
			}
			
			hm.put("is_update_success", "1");
		}catch(Exception e){
			//System.out.println("-----支付成功，更新状态失败----------");
			e.printStackTrace();
			hm.put("is_update_success", "0");
			//更新状态为待支付
			String updateTHBStatussql = "UPDATE TAKEORDER SET TAKEORDERSTATUS=2 WHERE OrderNumber='" + JYDH + "'";
			execSQL(o2o,updateTHBStatussql,new HashMap());
			throw new Exception(e.getMessage());
		}
		return hm;
	}
	
	
	/*
	 * 退款异步请求接口
	 */
	@RequestMapping("/THTBline.action")
	public Map<String, Object> THTBline(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String thdh=request.getParameter("thdh");
		String shyj=request.getParameter("shyj");
		String returnString="";
		String queryTHDH="SELECT  (SELECT sum(B.xsddi02*B.xsddi05) FROM W_JYDITEM A,W_XSDDITEM B WHERE A.JYDH=t2.JYDH AND A.XSDD01=B.XSDD01) total_money,"+"t1.THJE refund_fee,t3.trade_no from w_thd t1 ,w_xsdd t2,W_XSDDDZ t3 where t1.xsdd01=t2.XSDD01 and t1.XSDD01=t3.XSDD01"+
							" and  t1.thdh='"+thdh+"' ";
		List mjList = queryForList(o2o,queryTHDH);
		Map resultMap=(Map)mjList.get(0);
		String trade_no=resultMap.get("trade_no").toString();
		String total_money=resultMap.get("total_money").toString();
		String refund_fee=resultMap.get("refund_fee").toString();
		
		
		returnString = THTBTimely(thdh,trade_no,total_money,shyj,refund_fee);
		Map map =new HashMap();
		if(returnString.contains("SUCCESS")){
			map.put("is_success", "1");
		}else{
			map.put("is_success", "0");
		}
		return map;
	}
	public String THTBTimely(String thdh,String trade_no,String total_money,String shyj,String refund_fee) throws Exception {
		String out_refund_no = thdh;// 退款单号
		DecimalFormat df = new DecimalFormat("0");
		String total_fee_format =df.format(Double.valueOf(total_money)*100);
		String refund_fee_format =df.format(Double.valueOf(refund_fee)*100);

		String nonce_str = WeixinUtil.create_nonce_str();// 随机字符串
		String appid = JlAppResources.getProperty("WEIXIN_APP_ID");
		String appsecret = JlAppResources.getProperty("WEIXIN_APP_SECRET");
		String mch_id = JlAppResources.getProperty("WEIXIN_PARTNER");
		String op_user_id = JlAppResources.getProperty("WEIXIN_PARTNER");//就是MCHID
		String partnerkey = JlAppResources.getProperty("WEIXIN_PARTNER_KEY");//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("transaction_id", trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee_format);
		packageParams.put("refund_fee", refund_fee_format);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<transaction_id>" + trade_no + "</transaction_id>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee_format + "</total_fee>"
				+ "<refund_fee>" + refund_fee_format + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String return_code ="";
		try {
			String returnString= ClientCustomSSL.doRefund(createOrderURL, xml);
			Map returnMap=XMLUtil.doXMLParse(returnString);
			return_code=(String) returnMap.get("return_code");
			if("SUCCESS".equals(return_code)){
				//财务同意退款后短信通知客户
				if(JLTools.getCurConf(2) == 1){
					String smsSQL = "SELECT ifnull(C.ZCXX06,'') SJHM,B.HBID FROM W_THD A,W_XSDD B,W_ZCGS C WHERE A.XSDD01=B.XSDD01 AND A.HBID = C.ZCXX01 AND A.THDH='"+thdh+"'";
					Map smsMap = queryForMap(o2o,smsSQL);
					String  context = "您的退/换货订单商家已审核通过，单号："+thdh+"，我们将在7个工作日内将退货款项返回到您的支付账号，请注意查收！";

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
				
				String updateTHBStatussql="UPDATE W_THD SET THZT='6',shyj='"+shyj+"' WHERE THDH='"+thdh+"'";
				execSQL(o2o,updateTHBStatussql,new HashMap());
			}else{
				String errorMsg = returnMap.get("err_code_des").toString()+returnMap.get("err_code").toString();
				String sql="UPDATE W_THD SET THZT='9',TKDZH ='"+(String) returnMap.get("refund_id").toString()+"',ERROR='"+errorMsg+"' WHERE THDH='"+thdh+"'";
				execSQL(o2o,sql,new HashMap());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_code;
	}
	
	/*public static void main(String[] args) throws Throwable {
		String out_trade_no = "11";

		String body = "0";
		
		String nonce_str = WeixinUtil.create_nonce_str();

		String amount = "1";
		// 付款金额
		DecimalFormat df = new DecimalFormat("0.00");
		String total_fee = df.format(Double.parseDouble((amount)));

		String spbill_create_ip = "101.36.68.165";
		String appId = JlAppResources.getProperty("WEIXIN_APP_ID");
		String partner = JlAppResources.getProperty("WEIXIN_PARTNER");
		String notify_url = JlAppResources.getProperty("WEIXIN_NOTIFY_FW_URL");
		String partnerKey = JlAppResources.getProperty("WEIXIN_PARTNER_KEY");
		String trade_type = "NATIVE";
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", partner);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("total_fee", "1");
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", "NATIVE");

		String sign = WeixinUtil.getsign2(packageParams, partnerKey);
		String payXml = "<xml>" + "<appid>" + appId + "</appid>" + "<body>"
				+ body + "</body>" + "<mch_id>" + partner + "</mch_id>"
				+ "<nonce_str>" + nonce_str + "</nonce_str>" + "<notify_url>"
				+ notify_url + "</notify_url>" + "<out_trade_no>"
				+ out_trade_no + "</out_trade_no>" + "<spbill_create_ip>"
				+ spbill_create_ip + "</spbill_create_ip>" + "<total_fee>"
				+ 1 + "</total_fee>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		System.out.println(payXml);
		String resultXml = (String) WeixinUtil.httpRequest(
				WeixinUtil.WX_PAY_UNIFIEDORDER_URL, "POST", payXml, "1");
		System.out.println(resultXml);
		Map returnMap=XMLUtil.doXMLParse(resultXml);
		String return_code = (String) returnMap.get("return_code");
		String result_code = (String) returnMap.get("result_code");
		if ("SUCCESS".equals(return_code)) {
			if ("SUCCESS".equals(result_code)) {
				String code_url=(String) returnMap.get("code_url");
				String prepay_id=(String) returnMap.get("prepay_id");
				
			} else {

			}
		}*/
		/*String partnerKey = JlAppResources.getProperty("WEIXIN_PARTNER_KEY");
		String nonce_str = WeixinUtil.create_nonce_str();
		String appId = JlAppResources.getProperty("WEIXIN_APP_ID");
		String partner = JlAppResources.getProperty("WEIXIN_PARTNER");
		String returnmsg=WeixinUtil.orderQuery(appId, partner, null, "11", nonce_str, partnerKey);
		Map returnMap=XMLUtil.doXMLParse(returnmsg);
		String trade_state=(String) returnMap.get("trade_state");
		String transaction_id=(String) returnMap.get("transaction_id");
		System.out.println(trade_state);
		System.out.println(transaction_id);
		
	}*/
	
}
