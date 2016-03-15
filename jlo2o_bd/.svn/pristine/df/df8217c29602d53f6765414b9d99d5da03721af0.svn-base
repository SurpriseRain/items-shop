package com.jlsoft.o2o.interfacepackage.ebc;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jlsoft.utils.JlAppResources;

import sun.misc.BASE64Decoder;

/**
*
* Title: DES加密算法�?
* Copyright: Copyright (c) 2002 yipsilon.com
* @author yipsilon
* @version 0.1
*/

public class DSDES {
 public static int _DES = 1;
 public static int _DESede = 2;
 public static int _Blowfish = 3;

 private Cipher p_Cipher;
 private SecretKey p_Key;
 private String p_Algorithm;

 private void selectAlgorithm(int al){
   switch(al){
     default:
     case 1:
       this.p_Algorithm = "DES";
       break;
     case 2:
       this.p_Algorithm = "DESede";
       break;
     case 3:
       this.p_Algorithm = "Blowfish";
       break;
   }
 }

 public DSDES(int algorithm) throws Exception{
   this.selectAlgorithm(algorithm);
   Security.addProvider(new com.sun.crypto.provider.SunJCE());
   this.p_Cipher = Cipher.getInstance(this.p_Algorithm);
 }

 public byte[] getKey(){
   return this.checkKey().getEncoded();
 }

 private SecretKey checkKey(){
   try{
     if(this.p_Key == null){
       KeyGenerator keygen = KeyGenerator.getInstance(this.p_Algorithm);
       this.p_Key = keygen.generateKey();
     }
   }catch(NoSuchAlgorithmException nsae){}
   return this.p_Key;
 }

 public void setKey(byte[] enckey){
   this.p_Key = new SecretKeySpec(enckey,this.p_Algorithm);
 }

 public byte[] encode(byte[] data) throws Exception{
   this.p_Cipher.init(Cipher.ENCRYPT_MODE,this.checkKey());
   return this.p_Cipher.doFinal(data);
 }

 public byte[] decode(byte[] encdata, byte[] enckey) throws Exception{
   this.setKey(enckey);
   this.p_Cipher.init(Cipher.DECRYPT_MODE,this.p_Key);
   return this.p_Cipher.doFinal(encdata);
 }

 public String byte2hex(byte[] b){
   String hs = "";
   String stmp = "";
   for(int i = 0;i < b.length;i++){
     stmp = Integer.toHexString(b[i] & 0xFF);
     if(stmp.length() == 1) hs += "0" + stmp;
     else hs += stmp;
   }
   return hs.toUpperCase();
 }

 public byte[] hex2byte(String hex) throws IllegalArgumentException{
   if(hex.length() % 2 != 0)
     throw new IllegalArgumentException();
   char[] arr = hex.toCharArray();
   byte[] b = new byte[hex.length()/2];
   for(int i = 0,j = 0,l = hex.length();i < l;i++,j++){
     String swap = "" + arr[i++] + arr[i];
     int byteint = Integer.parseInt(swap,16) & 0xFF;
     b[j] = new Integer(byteint).byteValue();
   }
   return b;
 }
 

 public static byte[] strToHex(String arg0) throws Exception{
   byte[] bit = new byte[8];
   for (int k = 0, kk = 0; k < arg0.length(); k += 2, kk++) {
     char tmp1 = arg0.charAt(k);
     char tmp2 = arg0.charAt(k + 1);
     bit[kk] = (byte) ( ( (tmp1 & 0x0f) << 4) | (tmp2 & 0x0f));
   }
   return bit;

 }

 

 //根据密钥生成对应的密�?
 
 public static String getWhiteData(byte[] mykey ,byte[] data) throws Exception {
	    byte[] key; //密钥文件(byte)
	    //byte[] mykey = {49, 49, 50, 50, 51, 51, 52, 52}; //初始化密钥文�?byte)
	    DSDES des = new DSDES(DSDES._DES); // 声明DES
	    des.setKey(mykey); //设置密钥文件
	    key = des.getKey(); //获取随机生成的密�?-这里获取的实际上是固定的密钥，呵�?
	    byte[] dec = des.decode(data, key); //解密文件,其中转换十六进制密钥为byte
	    return new String(dec);
	  }
 
 	public static String getBlackData( byte[] mykey ,byte[] data) throws Exception {
 		byte[] key; //密钥文件(byte)
 		//byte[] mykey = {49, 49, 50, 50, 51, 51, 52, 52}; //初始化密钥文�?byte)
 		DSDES des = new DSDES(DSDES._DES); // 声明DES
 		des.setKey(mykey); //设置密钥文件
 		key = des.getKey(); //获取随机生成的密�?-这里获取的实际上是固定的密钥，呵�?
 		byte[] enc = des.encode(data); //生成加密文件(byte)
 		String hexenc = des.byte2hex(enc); //生成十六进制加密文件
 		return hexenc;
 	}
 	//解密
 	private byte[] byteMi = null;
   private byte[] byteMing = null;
   private String strMi= "";
   private String strM= ""; 
   private Key key;
 	public  void setDesString(String strMi){  
 	   BASE64Decoder base64De = new BASE64Decoder();   
 	    try
 	    {
 	     this.byteMi = base64De.decodeBuffer(strMi);  
 	      this.byteMing = this.getDesCode(byteMi);  
 	      this.strM = new String(byteMing,"UTF8");  
 	      }  
 	    catch(Exception e)
 	     {
 	      e.printStackTrace();
 	      }  
 	    finally
 	     {
 	      base64De = null;  
 	      byteMing = null;  
 	      byteMi = null;
 	      }  
 	  
 	  }
 	private byte[] getDesCode(byte[] byteD){
 	   Cipher cipher;  
 	    byte[] byteFina=null;  
 	    try{
 	     cipher = Cipher.getInstance("DES");  
 	      cipher.init(Cipher.DECRYPT_MODE,key);  
 	      byteFina = cipher.doFinal(byteD);
 	      }
 	   catch(Exception e)
 	     {
 	      e.printStackTrace();
 	      }
 	   finally
 	     {
 	      cipher=null;
 	      }  
 	    return byteFina;
 	  } 
 	
 	/**
	 * 判断内容是否一致
	 * @param dstbdatasign 密文
	 * @param dstbdata  明文
	 * @return
	 */
	public static boolean verify(String dstbdatasign,String dstbdata){
		boolean flag = false;
		try {
			if(dstbdatasign!=null && dstbdata!=null){
				//将明文加密
				String screat = DSDES.getBlackData(JlAppResources.getProperty("EBC_PASSWORD").getBytes(), dstbdata.getBytes("utf-8"));
				if(dstbdatasign.equals(screat)){
					flag = true;
				}
			}
		} catch (Exception e) {
		}
		return flag;
	}
 	
 	
}


