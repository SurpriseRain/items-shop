package com.jlsoft.o2o.interfacepackage.jlinterface.TGYInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ecc.liana.security.NetpaySCSignature;


/**
 * <p>Title: 加密解密工具类，封装对DES加密解密方法调用</p>
 * <p>Description: 暂时将一些配置信息（des算法key值、签名验证公钥私钥等）写在类中
 *                 后期应该配置在数据库中，系统初始化时加载到缓存</p>
 * <p>Copyright: yuchengtech Copyright (c) 2014</p>
 * <p>Company:yucheng </p>
 * @author lsw 2014-10-24
 * @version 1.0
 */
public class DESUtil {
	public final static String IMAGETRANCODES = "Image0001,Image0002,image_transferSchedule,image_generateContract,image_stopUseTemplate,image_generateAppendix,image_identifyContract";
	
	/**
	 * 支付平台
	 */
	public final static String TAG_PAYFLAT = "<ENCRYPTINFO>";
	public final static String TAG_END_PAYFLAT = "</ENCRYPTINFO>";
	
	/**
	 * 中转服务器
	 */
	public final static String TAG_TRANFER = "<TRANFERINFO>";
	public final static String TAG_END_TRANFER = "</TRANFERINFO>";
	
	/**
	 * 运营平台 
	 */
	public final static String TAG_CMIS = "<CMISINFO>";
	public final static String TAG_END_CMIS = "</CMISINFO>";
	
	/**
	 * 渠道
	 */
	public final static String TAG_QEEGOO = "<QEEGOOINFO>";
	public final static String TAG_END_QEEGOO = "</QEEGOOINFO>";
	
	/**
	 * 汽修云
	 */
	public final static String TAG_QXY = "<QXYINFO>";
	public final static String TAG_END_QXY = "</QXYINFO>";
	
	/**
	 * 签名信息
	 */
	public final static String FLD_SIGN = "TRANMSG";
	public final static String SIGN_TAG = "<SIGNINFO>";
	public final static String SIGN_TAG_END = "</SIGNINFO>";
	public final static String PUBLICKEY_QEEGOO = "MIIBtzCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYQAAoGACKXQIJf3jqidsghvuwyMPD5GlqZzcN13jyll64Anelp99H4qTLvamY9622A3vdjaOdhRWn4rgt8mjbAHZC7TAS3odFc4LORzQBMisqn5TgKV6e8o32e6+IbiRnTiK4fosyxBwNhoUs6uC/Htbad7yNwdjt9VjBJ86kSlyaSvlxA=";
	public final static String PUBLICKEY_LIANA = "MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBAONd/yMdc09LQ+ulQ3pKGReIXR2WNxKCv7l7O+Rp7JImhhWxbo8dyf56naify3a2uqlIYo9niw/X3YbxHfh+EjmrRgC1MF6Kkcnou9pXK0aqtJ3suS+StBX6Ibf7qj9ey1TbaFn5aCHLzTGghKUIWKPEZ7xg6ZSPxYqQzDDB1hMB";
	public final static String PRIVATEKEY_LIANA = "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUPKoQnZksrRq7vGwkeJdTOk4OT1k=";
	
	// key值
	private static Map<String, String> keyMap = new HashMap<String, String>();
	// 静态块初始数据
	static {
		keyMap.put(DESUtil.TAG_PAYFLAT, "abcdefgh");
		keyMap.put(DESUtil.TAG_TRANFER, "tgy*liana&tranfer*");
		keyMap.put(DESUtil.TAG_CMIS, "tgy*liana&cmis*");
		keyMap.put(DESUtil.TAG_QEEGOO, "tgy*liana&b2b*");
		keyMap.put(DESUtil.TAG_QXY, "tgy*liana&b2b*qxy");
	}
	
	/**
	 * 获取des算法key值
	 * @param keyVal
	 * @return
	 */
	public static String getDESKeyStr(String keyVal) {
		return keyMap.get(keyVal);
	}
	
	/**
	 * 对报文进行加密
	 * @param taghead
	 * @param tagend
	 * @param sendMsg
	 * @return
	 * @throws EMPException
	 */
	public static String encrypt(String taghead, String tagend, String sendMsg) throws Exception {
//		EMPLog.log(EMPConstance.EMP_HTTPACCESS, EMPLog.INFO, 0, "加密前报文数据: " + sendMsg);
//		Trace.logDebug( Trace.COMPONENT_MAPPING, "加密前报文数据: \n" + sendMsg );
		
		try {
			// 签名
			String signStr = "";
			if (TAG_QEEGOO.equals(taghead)) {
				Properties p = new Properties();
				p.setProperty(FLD_SIGN, sendMsg);
				NetpaySCSignature sign = new NetpaySCSignature();
				signStr = SIGN_TAG + sign.dsaSign(p, PRIVATEKEY_LIANA, "UTF-8") + SIGN_TAG_END;
			}
			
			// 加密
			DESEncrypt encrypt = new DESEncrypt(); 
			encrypt.setKeyStr(getDESKeyStr(taghead));
			String encryptStr = taghead + encrypt.desEncrypt(sendMsg) + tagend;
			
			sendMsg = encryptStr ;//+ signStr
//			EMPLog.log(EMPConstance.EMP_HTTPACCESS, EMPLog.INFO, 0, "加密后报文数据: " + sendMsg);
//			Trace.logDebug( Trace.COMPONENT_MAPPING, "加密后报文数据: \n" + sendMsg );
		} catch (Exception e) {
			throw new Exception("交易报文加密失败!");
		}
		
		return sendMsg;
	}

	/**
	 * 对报文进行解密
	 * @param receiMsg
	 * @param request
	 * @return
	 * @throws EMPException
	 */
	public static String decrypt(String receiMsg, HttpServletRequest request) throws Exception {
//		EMPLog.log(EMPConstance.EMP_HTTPACCESS, EMPLog.DEBUG, 0, "解密前报文数据: " + receiMsg);
//		Trace.logDebug( Trace.COMPONENT_MAPPING, "解密前报文数据:\n" + receiMsg );

		// 检查加密报文
		String tagHead = null;
		String tagEnd = null;
		String desKeyStr = null;
		String signStr = null;
		if (receiMsg.indexOf(TAG_PAYFLAT) == 0 && receiMsg.indexOf(TAG_END_PAYFLAT) > 0) {
			// 支付平台
			tagHead = TAG_PAYFLAT;
			tagEnd = TAG_END_PAYFLAT;
			desKeyStr = getDESKeyStr(TAG_PAYFLAT);
		} else if (receiMsg.indexOf(TAG_TRANFER) == 0 && receiMsg.indexOf(TAG_END_TRANFER) > 0) {
			// 中转服务器
			tagHead = TAG_TRANFER;
			tagEnd = TAG_END_TRANFER;
			desKeyStr = getDESKeyStr(TAG_TRANFER);
		} else if (receiMsg.indexOf(TAG_CMIS) == 0 && receiMsg.indexOf(TAG_END_CMIS) > 0) {
			// 运营平台
			tagHead = TAG_CMIS;
			tagEnd = TAG_END_CMIS;
			desKeyStr = getDESKeyStr(TAG_CMIS);
		} else if (receiMsg.indexOf(TAG_QEEGOO) == 0 && receiMsg.indexOf(TAG_END_QEEGOO) > 0) {
			// 渠道
			tagHead = TAG_QEEGOO;
			tagEnd = TAG_END_QEEGOO;
			desKeyStr = getDESKeyStr(TAG_QEEGOO);
			signStr = StringUtils.substringBetween(receiMsg, SIGN_TAG, SIGN_TAG_END);
		}else if (receiMsg.indexOf(TAG_QXY) == 0 && receiMsg.indexOf(TAG_END_QXY) > 0) {
			// 渠道
			tagHead = TAG_QXY;
			tagEnd = TAG_END_QXY;
			desKeyStr = getDESKeyStr(TAG_QXY);
			signStr = StringUtils.substringBetween(receiMsg, SIGN_TAG, SIGN_TAG_END);
		} else {
			throw new Exception("交易报文格式异常!");
		}
		
		// 交易报文必须有数据
		receiMsg = StringUtils.substringBetween(receiMsg, tagHead, tagEnd);
		if (0 == receiMsg.length()) {
			throw new Exception("交易报文数据异常!");
		}
		
		//  渠道时传入request对象，将标签暂存
		if (null != request) {
			request.setAttribute("TRANMSG_TAGHEAD", tagHead);
			request.setAttribute("TRANMSG_TAGEND", tagEnd);
		}
		
		try {
			// 解密
			DESDecrypt decrypt = new DESDecrypt();
			decrypt.setKeyStr(desKeyStr);
			receiMsg = decrypt.desDecrept(receiMsg);
//			EMPLog.log(EMPConstance.EMP_HTTPACCESS, EMPLog.INFO, 0, "解密后报文数据: " + receiMsg);
//			Trace.logDebug( Trace.COMPONENT_MAPPING, "解密后报文数据:\n" + receiMsg );
			// 验签
			if (TAG_QEEGOO.equals(tagHead)) {
				Properties p = new Properties();
				p.setProperty(FLD_SIGN, receiMsg);
				p.setProperty("sign", signStr);
				p.setProperty("sign_type", "DSA");
				NetpaySCSignature sign = new NetpaySCSignature();
				if (sign.verify(p, PUBLICKEY_QEEGOO, "UTF-8")) {
					throw new Exception("交易报文验签失败!");
				}
			}
		} catch (Exception e) {
			throw new Exception("返回报文解密失败!");
		}
		
		return receiMsg;
	}
	
	/**
	 * 是否对中转服务器交易
	 * @param serviceId
	 * @return
	 * @throws EMPException
	 */
	public static boolean isImageTran(String serviceId) throws Exception {
		return IMAGETRANCODES.indexOf(serviceId) >= 0; 
	}
	
	public static void main(String[] args) {
		String aa="QXY010001|0150|010001|2015-04-27|2015-04-27 15:59:03.0|1430121543|002|#<?xml version=\"1.0\" encoding=\"UTF-8\"?><ReqData><ReqParam><CUS_ID>0150</CUS_ID><CUS_NAME>武汉吉达顺旗舰店</CUS_NAME><CUS_AREA>000</CUS_AREA><CUS_ADDRESS>湖北省武汉市汉阳区龙阳大道陶家岭特1号</CUS_ADDRESS><CONT_NAME>wuhanjida</CONT_NAME><CONT_TEL>18007151587</CONT_TEL><LANDLINE>null</LANDLINE><OA_ZIP></OA_ZIP><FAX_NO>null</FAX_NO><CUS_TYPE>01</CUS_TYPE></ReqParam></ReqData>";
		try {
//			aa=DESUtil.encrypt(DESUtil.TAG_QEEGOO, DESUtil.TAG_END_QEEGOO, aa);
			aa=DESUtil.decrypt("<QXYINFO>qU73eYtZAwNCZv2ERPChOEjVylZvkzKrV7QxyoDJs1vLRrMnQHtXIiKp7+f8fYcnA+dpZ+yLy5Ff379UMH2pK0+Jiw96USRWXLpCemMAdN8trT6YQwYT2Khl4o4yw88WpdEd9lpiioL9vpRYPp+qJ5r2+bzZafvjwoKvDZuz3Vf6XWZRH29ahK5fDvhpyXOF7i3Ti8vdqNc=</QXYINFO>", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(aa);
	}
}
