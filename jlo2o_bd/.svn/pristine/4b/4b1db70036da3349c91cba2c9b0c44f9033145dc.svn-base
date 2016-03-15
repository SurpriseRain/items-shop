package com.jlsoft.o2o.interfacepackage.jlinterface.TGYInterface;


import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


/**
 * 提供给非网银渠道（如CC渠道）使用的DES对称解密
 * 2013-7-2
 * @author liwh3@yuchengtech.com
 *
 */
public class DESDecrypt {

	String encroptMode = "ECB"; // 加密模式

	String paddingMode = "PKCS5Padding"; // 填充模式

	String algorithm = "DES"; // 加密算法，目前支持DES、DESede

	String keyStr = "abcdefgh"; //"abc&*123";

	public String desDecrept(String encryStr){ 
		try {
			byte[] keyBytes = getKeyStr().getBytes();
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");   
			secureRandom.setSeed(keyBytes);
			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			
			generator.init(secureRandom);
			Key key = generator.generateKey();
			Cipher cipher = Cipher.getInstance(algorithm + "/" + encroptMode
					+ "/" + paddingMode);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] oriBytes = cipher.doFinal(Base64Util.decode(encryStr));
			return new String(oriBytes,"UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getEncroptMode() {
		return encroptMode;
	}

	public void setEncroptMode(String encroptMode) {
		this.encroptMode = encroptMode;
	}

	public String getPaddingMode() {
		return paddingMode;
	}

	public void setPaddingMode(String paddingMode) {
		this.paddingMode = paddingMode;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DESEncrypt encrypt = new DESEncrypt();
		String encryStr = encrypt.desEncrypt("QXY010003|0151|010003|2015-05-05|2015-05-05 16:12:03.0|1430813523|002|#<?xml version=\"1.0\" encoding=\"UTF-8\"?><ReqData><ReqParam><CUS_ID>0151</CUS_ID><CUS_NAME>武汉老兄弟汽配专营店</CUS_NAME><TRANS_TYPE>00</TRANS_TYPE></ReqParam></ReqData>");
		System.out.println("secret:"+encryStr);
		
		DESDecrypt decrypt = new DESDecrypt();
		String decryptStr = decrypt.desDecrept(encryStr);
		System.out.println("source:"+decryptStr);

	}

}
