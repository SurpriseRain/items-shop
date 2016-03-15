package com.jlsoft.o2o.interfacepackage.weixin.util;

/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------



import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XMLParse class
 *
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 */
public class XMLParse {

	/**
	 * 提取出xml数据包中的加密消息
	 * @param xmltext 待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException 
	 */
	public static Object[] extract(String xmltext) throws AesException     {
		Object[] result = new Object[3];
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("ToUserName");
			result[0] = 0;
			result[1] = nodelist1.item(0).getTextContent();
			result[2] = nodelist2.item(0).getTextContent();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ParseXmlError);
		}
	}
	
	/**
	 * 生成xml消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {

		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);

	}
	public static String generate(String openid,String body,String out_trade_no,String total_fee,String notify_url,String trade_type,String appid,String mch_id
			,String spbill_create_ip,String nonce_str,String sign){
		String format="<xml>"+
			  "<openid><![CDATA[%1$s]]></openid>"+
			  "<body><![CDATA[%2$s]]></body>"+
			  "<out_trade_no><![CDATA[%3$s]]></out_trade_no>"+
			  "<total_fee>"+total_fee+"</total_fee>"+
			  "<notify_url><![CDATA[%4$s]]></notify_url>"+
			  "<trade_type><![CDATA[JSAPI]]></trade_type>"+
			  "<appid><![CDATA[%5$s]]></appid>"+
			  "<mch_id>"+mch_id+"</mch_id>"+
			  "<spbill_create_ip><![CDATA[%6$s]]></spbill_create_ip>"+
			  "<nonce_str><![CDATA[%7$s]]></nonce_str>"+
			  "<sign><![CDATA[%8$s]]></sign>"+
			"</xml>";
		return String.format(format, openid, body, out_trade_no, notify_url,appid,spbill_create_ip,nonce_str,sign);
	}
	public static Element getElement(String xmltext) throws AesException, ParserConfigurationException, SAXException, IOException     {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xmltext);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		return document.getDocumentElement();
	}
	public static String generatePassiveText(String toUser,String fromUser,String createTime,String content){
		String ss="<xml>"+
		"<ToUserName><![CDATA[%1$s]]></ToUserName>"+
		"<FromUserName><![CDATA[%2$s]]></FromUserName>"+
		"<CreateTime>%3$s</CreateTime>"+
		"<MsgType><![CDATA[text]]></MsgType>"+
		"<Content><![CDATA[%4$s]]></Content>"+
		"</xml>";
		return String.format(ss, toUser, fromUser, createTime, content);
	}
}
