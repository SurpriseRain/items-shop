package com.jlsoft.o2o.interfacepackage.weixin;

import java.util.SortedMap;
import java.util.TreeMap;

import com.jlsoft.o2o.interfacepackage.weixin.client.ClientCustomSSL;


public class Refund {
	public void wechatRefund() {
		String out_refund_no = "654232";// 退款单号
		String out_trade_no = "CS_XS0000100073";// 订单号
		String total_fee = "1";// 总金额
		String refund_fee = "1";// 退款金额
		String nonce_str = "4232343765";// 随机字符串
		String appid = "wxb40f75a4ee5f5e51";
		String appsecret = "6183de50dced5883bba74736ae6c5ad3";
		String mch_id = "1260528201";
		String op_user_id = "1260528201";//就是MCHID
		String partnerkey = "T0Gi3pJsaLHi7sbNT19NUoXTJVgA6NYo";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			String s= ClientCustomSSL.doRefund(createOrderURL, xml);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Refund refund=new Refund();
		refund.wechatRefund();
	}
}
