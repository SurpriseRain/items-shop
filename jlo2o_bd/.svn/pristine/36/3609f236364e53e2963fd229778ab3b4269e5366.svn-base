package com.jlsoft.o2o.interfacepackage.jlinterface.TGYInterface;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


/**
 * �ṩ���������������CC������ʹ�õ�DES�ԳƼ���
 * 2013-7-2
 * @author liwh3@yuchengtech.com
 *
 */
public class DESEncrypt {
	private String encroptMode = "ECB"; // ����ģʽ

	private String paddingMode = "PKCS5Padding"; // ���ģʽ

	private String algorithm = "DES"; // �����㷨��Ŀǰ֧��DES��DESede

	private String keyStr ="abcdefgh";  //"abc&*123";

	public String desEncrypt(String oriData){
		try {
			byte[] keyBytes = getKeyStr().getBytes("UTF-8");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
			secureRandom.setSeed(keyBytes);  

			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			generator.init(secureRandom);
			Key key = generator.generateKey();
			Cipher cipher =
				Cipher.getInstance(
					algorithm + "/" + encroptMode + "/" + paddingMode);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptBytes =
				cipher.doFinal(oriData.getBytes("UTF-8"));
			return Base64Util.encode(encryptBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
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

	public static void main(String[] args){
		DESEncrypt encrypt = new DESEncrypt(); 
		String encryStr = encrypt.desEncrypt("11111111");
		System.out.println(encryStr);
	}
}
