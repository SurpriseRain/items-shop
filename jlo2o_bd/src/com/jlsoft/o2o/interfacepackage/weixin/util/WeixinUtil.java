package com.jlsoft.o2o.interfacepackage.weixin.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;

import com.jlsoft.o2o.interfacepackage.alipay.unionpay.MyX509TrustManager;

public class WeixinUtil {

	// 服务号
	public final static String SER_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	public static final String SER_USER_ID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public final static String SER_MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	public final static String SER_SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	public static final String SER_USER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
	public static final String SER_USER_DETAIL_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	// 微信支付、
	public static final String WX_JS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=${access_token}&type=jsapi";
	public static final String WX_PAY_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String WX_PAY_ORDER_QUERY="https://api.mch.weixin.qq.com/pay/orderquery";
	public static final String WX_PAY_DELIVER_NOTIFY="https://api.weixin.qq.com/pay/delivernotify?access_token=";
	
	
	/* ************************************************************************
	 * 服务号接入校验
	 */
	public static boolean checkSignature(String token, String signature,
			String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	/* ************************************************************************
	 * 服务号接入校验
	 */
	public static boolean checkSignatureForMessage(String token, String signature,
			String timestamp, String nonce,String msg_encrypt) {
		String[] arr = new String[] { token, timestamp, nonce, msg_encrypt};
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	

	/*
	 * 服务号从微信服务器获取最新的AccessToken
	 * 
	 * appid appsecret
	 */
	public static String getWeixinAccessTokenForService(String appid,
			String appsecret) throws Throwable {
		String new_access_token = null;
		String access_token_url = SER_ACCESS_TOKEN_URL
				+ "?grant_type=client_credential&appid=" + appid + "&secret="
				+ appsecret;
		JSONObject jsonObject = (JSONObject) httpRequest(access_token_url,
				"GET", null, null);
		new_access_token = (String) jsonObject.get("access_token");
		return new_access_token;
	}

	/*
	 * 服务号创建菜单
	 * 
	 * postString 菜单拼接jason字符串 accessToken
	 */
	public static void createMenuForService(String postString,
			String accessToken) throws Throwable {
		String menu_create_url = SER_MENU_CREATE_URL + accessToken;
		JSONObject jsonObject = (JSONObject) httpRequest(menu_create_url,
				"POST", postString, null);
		int errcode = jsonObject.getInt("errcode");
		if (0 != errcode) {
			throw new Throwable(jsonObject.toString());
		}
	}

	/*
	 * 服务号OAuth2.0网页授权,获取用户ID
	 * 
	 * appid secret code 微信回调code
	 */
	public static String getWeixinUserIdForService(String appid, String secret,
			String code) throws Throwable {
		String user_id_url = SER_USER_ID_URL + "?appid=" + appid + "&secret="
				+ secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = (JSONObject) httpRequest(user_id_url, "GET",
				null, null);
		String UserId = (String) jsonObject.get("openid");
		return UserId;
	}
	/*
	 * 服务号OAuth2.0网页授权,获取access_token
	 * 
	 * 
	 */
	public static JSONObject getWeixinAuthAccessTokenForService(String appid, String secret,
			String code) throws Throwable {
		String user_id_url = SER_USER_ID_URL + "?appid=" + appid + "&secret="
				+ secret + "&code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = (JSONObject) httpRequest(user_id_url, "GET",
				null, null);
		return jsonObject;
	}
	/*
	 * 服务号获取用户列表
	 */
	public static JSONObject getUserListForService(String accessToken)
			throws Throwable {
		if (null == accessToken || "".equals(accessToken)) {
			throw new Throwable("access_token不能为空");
		}
		JSONObject jsonObject = (JSONObject) httpRequest(
				SER_USER_LIST_URL.concat(accessToken), "GET", null, null);
		return jsonObject;
	}

	public static JSONObject getUserDetailForService(String accessToken,
			String openid) throws Throwable {
		if (null == accessToken || "".equals(accessToken)) {
			throw new Throwable("access_token不能为空");
		}
		JSONObject jsonObject = (JSONObject) httpRequest(StringUtils.replace(
				StringUtils.replace(SER_USER_DETAIL_URL, "ACCESS_TOKEN",
						accessToken), "OPENID", openid), "GET", null, null);
		return jsonObject;
	}

	/*
	 * 服务号发送文本消息
	 */
	public static JSONObject sendWeixinMessageTextForService(
			String accessToken, String touser, String message) throws Throwable {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("touser", touser);
		jsonObject.put("msgtype", "text");
		jsonObject.put("text", new JSONObject().put("content", message));
		return (JSONObject) httpRequest(
				SER_SEND_MESSAGE_URL.concat(accessToken), "POST",
				jsonObject.toString(), null);
	}

	/*
	 * 通用方法**********************************************************************
	 * ************************** http请求，
	 * 
	 * requestUrl 请求url requestMethod get或者post outputStr
	 */
	public static Object httpRequest(String requestUrl, String requestMethod,
			String outputStr, String type) throws Exception {
		JSONObject jsonObject;
		StringBuffer buffer = new StringBuffer();
		HttpsURLConnection httpUrlConn = null;
		try {

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;

			httpUrlConn.disconnect();
			if ("1".equals(type)) {
				return buffer.toString();
			} else {
				jsonObject = new JSONObject(buffer.toString());
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != httpUrlConn) {
				httpUrlConn.disconnect();
			}
		}
		return jsonObject;
	}

	/*
	 * 
	 * 解析xml
	 */
	public static Map<String, String> parseXml(String xmlString)
			throws Exception {
		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			// 从request中取得输入流
			Document document = DocumentHelper.parseText(xmlString);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return map;
	}

	/*
	 * 获取微信post请求字符串
	 */
	public static String getPostString(HttpServletRequest request)
			throws IOException {
		InputStream inputStream = null;
		StringBuffer sb = new StringBuffer();

		try {
			inputStream = request.getInputStream();
			byte[] data = new byte[5000];
			while (true) {
				int len = inputStream.read(data);
				if (len != -1) {
					sb.append(new String(data, 0, len));
				} else {
					break;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				inputStream.close();
				inputStream = null;
			}
		}
		return sb.toString();
	}

	/*
	 * 获取指定位置文件
	 */
	public static String getLocalResource(String path) throws Throwable {

		StringBuffer buf = new StringBuffer(5000);
		byte[] data = new byte[5000];

		InputStream in = null;

		in = new WeixinUtil().getClass().getResourceAsStream(path);

		try {
			if (in != null) {
				while (true) {
					int len = in.read(data);
					if (len != -1) {
						buf.append(new String(data, 0, len));
					} else {
						break;
					}
				}
				return buf.toString();
			} else {
				throw new Throwable("Invalid path to resource: " + path);
			}

		} catch (Throwable e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}

	/**
	 * @param request
	 * @throws Throwable
	 *             重新转化成功跳转后的参数
	 */
	public static void resetSuccessUrl(HttpServletRequest request)
			throws Throwable {
		// 对request数据参数处理
		Object successUrl = request.getAttribute("successUrl");
		if (successUrl == null) {
			throw new Throwable("跳转地址不能为空!");
		}
		StringBuffer url = new StringBuffer(256);
		url.append(successUrl.toString());
		Object successParams = request.getAttribute("successParams");
		if (successParams != null && successParams.toString().length() > 0) {
			String[] params = successParams.toString().split(",");
			for (int i = 0; i < params.length; i++) {
				String value = request.getParameter(params[i]);
				if (value == null) {
					Object v = request.getAttribute(params[i]);
					if (v != null) {
						value = v.toString();
					}
				}
				if (value != null) {
					if (url.indexOf("?") > 0) {
						url.append("&");
					} else {
						url.append("?");
					}
					url.append(params[i]).append("=").append(value);
				}
			}
		}
		request.setAttribute("successUrl", url.toString());
	}

	/**
	 * MD5加密
	 * 
	 * @param plainText
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String Md5(String plainText) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/*
	 * Base64加密
	 */
	public static String base64Encode(String str) {
		String base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

		int c1, c2, c3;
		int len = str.length();
		int i = 0;
		String out = "";
		while (i < len) {

			c1 = str.charAt(i++) & 0xff;
			if (i == len) {
				out += base64EncodeChars.charAt(c1 >> 2);
				out += base64EncodeChars.charAt((c1 & 0x3) << 4);
				out += "==";
				break;
			}
			c2 = str.charAt(i++);
			if (i == len) {
				out += base64EncodeChars.charAt(c1 >> 2);
				out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
						| ((c2 & 0xF0) >> 4));
				out += base64EncodeChars.charAt((c2 & 0xF) << 2);
				out += "=";
				break;
			}
			c3 = str.charAt(i++);
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += base64EncodeChars.charAt(((c2 & 0xF) << 2)
					| ((c3 & 0xC0) >> 6));
			out += base64EncodeChars.charAt(c3 & 0x3F);
		}
		return out;
	}



	/*
	 * 微信支付获取统一下单sign
	 */
	public static String getSign(String appid, String mch_id,
			String spbill_create_ip, String nonce_str,
			String body, String out_trade_no, String notify_url,
			String trade_type, String total_fee, String partnerKey)
			throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("body", body);
		params.put("out_trade_no", out_trade_no);
		params.put("spbill_create_ip", spbill_create_ip);
		params.put("total_fee", total_fee);
		params.put("notify_url", notify_url);
		params.put("trade_type", trade_type);
		
		
		Object[] akeys = (Object[]) params.keySet().toArray();
		Arrays.sort(akeys);

		for (int i = 0; i < akeys.length; i++) {
			if (i != akeys.length - 1) {
				sb.append(akeys[i] + "=" + params.get(akeys[i]) + "&");
			} else {
				sb.append(akeys[i] + "=" + params.get(akeys[i]));
			}
		}
		sb.append("&key=" + partnerKey);
		return MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
	}

	
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/*
	 * 微信支付操作签名1
	 */
	public static String getsign1(Map<String,String> params)
			throws JSONException {
		StringBuilder sb = new StringBuilder();

		Object[] akeys = (Object[]) params.keySet().toArray();
		Arrays.sort(akeys);

		for (int i = 0; i < akeys.length; i++) {
			if (i != akeys.length - 1) {
				sb.append(akeys[i] + "=" + params.get(akeys[i]) + "&");
			} else {
				sb.append(akeys[i] + "=" + params.get(akeys[i]));
			}
		}
		String signature=null;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(sb.toString().getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}
	/*
	 * 微信支付操作签名2
	 */
	public static String getsign2(Map<String,String> params,String key){
		String signString="";
		
		StringBuilder sb = new StringBuilder();

		Object[] akeys = (Object[]) params.keySet().toArray();
		Arrays.sort(akeys);

		for (int i = 0; i < akeys.length; i++) {
			if (i != akeys.length - 1) {
				sb.append(akeys[i] + "=" + params.get(akeys[i]) + "&");
			} else {
				sb.append(akeys[i] + "=" + params.get(akeys[i]));
			}
		}
		sb.append("&key=" + key);
		signString = MD5Util.MD5Encode(sb.toString(), "UTF-8")
				.toUpperCase();
		return signString;
	}
	/*
	 * 微信支付，发货通知
	 */
	public static JSONObject deliverNotify(String appid,String openid,String transid,String out_trade_no,String deliver_timestamp,String deliver_status
			,String deliver_msg,String appkey,String accessToken) throws Throwable{
		Map<String, String> map =new HashMap<String,String>();
		map.put("appid", appid);
		map.put("openid", openid);
		map.put("transid", transid);
		map.put("out_trade_no", out_trade_no);
		map.put("deliver_timestamp", deliver_timestamp);
		map.put("deliver_status", deliver_status);
		map.put("deliver_msg", deliver_msg);
		String app_signature=getsign2(map,appkey);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("appid", appid);
		jsonObject.put("openid", openid);
		jsonObject.put("transid", transid);
		jsonObject.put("out_trade_no", out_trade_no);
		jsonObject.put("deliver_timestamp", deliver_timestamp);
		jsonObject.put("deliver_status", deliver_status);
		jsonObject.put("deliver_msg", deliver_msg);
		jsonObject.put("app_signature", app_signature);
		jsonObject.put("sign_method", "sha1");
		String outputStr=jsonObject.toString();
		return (JSONObject)httpRequest(WX_PAY_DELIVER_NOTIFY+accessToken, "POST", outputStr, null);
	}
	/*
	 * 微信支付----查询订单
	 */
	public static String orderQuery(String appid,String mch_id,String transaction_id,String out_trade_no,String nonce_str,String key) throws Throwable{
		Map<String, String> map=new HashMap<String,String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		
		if(null!=transaction_id&&!"".equals(transaction_id)){
			map.put("transaction_id", transaction_id);
		}
		if(null!=out_trade_no&&!"".equals(out_trade_no)){
			map.put("out_trade_no", out_trade_no);
		}
		String sign=getsign2(map,key);
		
		String outputString=
		"<xml>"+
		   "<appid>"+appid+"</appid>"+
		   "<mch_id>"+mch_id+"</mch_id>"+
		   "<nonce_str>"+nonce_str+"</nonce_str>"+
		   (null!=transaction_id&&!"".equals(transaction_id)?"<transaction_id>"+transaction_id+"</transaction_id>":"")+
		   (null!=out_trade_no&&!"".equals(out_trade_no)?"<out_trade_no>"+out_trade_no+"</out_trade_no>":"")+
		   "<sign>"+sign+"</sign>"+
		"</xml>";
		return (String)httpRequest(WX_PAY_ORDER_QUERY, "POST", outputString, "1");
	}
	public static String create_nonce_str() {
		Random random = new Random();
		return MD5Util
				.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
	}
	
	/*
	 * 获取当前时间
	 */
	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	/*public static void main(String[] args) {
		String signString="640F86BE621C59C5213D1E068D4F1031";

		Map<String, String> map=new HashMap<String, String>();
		map.put("is_subscribe","Y");
		map.put("appid","wxb40f75a4ee5f5e51");
		map.put("fee_type","CNY");
		map.put("nonce_str","4511dccbea0a3350d8ff72c7648cd678");
		map.put("out_trade_no","CS_XS0000100073");
		map.put("transaction_id","1000430842201508210674080010");
		map.put("trade_type","NATIVE");
		map.put("result_code","SUCCESS");
		map.put("mch_id","1260528201");
		map.put("total_fee","1");
		map.put("time_end","20150821161143");
		map.put("openid","or3W_s5MM_yrle-uwisEG11xpAhI");
		map.put("bank_type","CFT");
		map.put("return_code","SUCCESS");
		map.put("cash_fee","1");
		String parnerkeyString="T0Gi3pJsaLHi7sbNT19NUoXTJVgA6NYo";
		
		System.out.println();
	}*/
}
