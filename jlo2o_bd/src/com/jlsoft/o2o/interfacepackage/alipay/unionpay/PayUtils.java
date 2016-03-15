package com.jlsoft.o2o.interfacepackage.alipay.unionpay;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * 名称：支付工具类
 * 功能：工具类，可以生成付款表单等
 * 类属性：公共类
 * 版本：1.0
 * 日期：2012-10-23
 * 作者：银联网络互联网团队
 * 版权：银联网络
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
public class PayUtils {
	/**
	 * 验证签名
	 * @param sourceText
	 * @param signature
	 * @param strPKey
	 * @return
	 */
	public  boolean checkSign(String SourceMsg, String signature, String strPKey) {
		String strKey;
		
		if(SourceMsg == null) return false;
		if(signature == null) return false;  
		if(strPKey == null) return false;
		System.out.println("SourceMsg="+SourceMsg);
		strKey = md5(strPKey);
		strKey = SourceMsg +strKey;
		System.out.println("strKey="+strKey);
		strKey = md5(strKey);
		System.out.println("strKey="+strKey);
		System.out.println("signature="+signature);
		if(signature.equals(strKey)){
			return true; 
		}else{
			return false;
		}
		
	}
	

	/**
	 * get the md5 hash of a string
	 * 
	 * @param str
	 * @return
	 */
	public String md5(String str) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			
			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	

	// Clean up resources
	public void destroy() {
	}


	/**
	 * 查询方法
	 * @param strURL
	 * @param req
	 * @return
	 */
	public String doPostQueryCmd(String strURL, String req) {
		String result = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			URL url = new URL(strURL);
			URLConnection con = url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			out = new BufferedOutputStream(con.getOutputStream());
			System.out.println("Request:=="+req);
			byte outBuf[] = req.getBytes("UTF-8");
			out.write(outBuf);
			out.close();
			in = new BufferedInputStream(con.getInputStream());
			result = ReadByteStream(in);
		} catch (Exception ex) {
			System.out.print(ex);
			return "";
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		if (result == null)
			return "";
		else
			return result;
	}


	private static String ReadByteStream(BufferedInputStream in) throws IOException {
		LinkedList<Mybuf> bufList = new LinkedList<Mybuf>();
		int size = 0;
		byte buf[];
		do {
			buf = new byte[128];
			int num = in.read(buf);
			if (num == -1)
				break;
			size += num;
			bufList.add(new Mybuf(buf, num));
		} while (true);
		buf = new byte[size];
		int pos = 0;
		for (ListIterator<Mybuf> p = bufList.listIterator(); p.hasNext();) {
			Mybuf b = p.next();
			for (int i = 0; i < b.size;) {
				buf[pos] = b.buf[i];
				i++;
				pos++;
			}

		}

		return new String(buf,"UTF-8");
	}
	
	public static void main( String[] aaa){
		String a="acqCode=27630276&backEndUrl=http://127.0.0.1:8080/easypay2/cupSecureUpopBackEndStatus.do&charset=UTF-8&commodityDiscount=&commodityName=&commodityQuantity=&commodityUnitPrice=&commodityUrl=&customerIp=192.168.0.116&customerName=&defaultBankNumber=&defaultPayType=&frontEndUrl=http://127.0.0.1:8080/easypay2/cupSecureUpopFrontEndStatus.do&merAbbr=SAW&merCode=7399&merId=763027645119001&merReserved=&orderAmount=100&orderCurrency=156&orderNumber=kim20110523010&orderTime=20110523113458&origQid=&transTimeout=120000&transType=01&transferFee=&version=1.0.0&8ddcff3a80f4189ca1c9d4d902c3c909";
		System.out.print(new PayUtils().md5(a));
	}

}

 
class UTF8PostMethod extends PostMethod{   
    public UTF8PostMethod(String url){   
        super(url);   
    }   
    @Override  
    public String getRequestCharSet() {   
        return "UTF-8";   
    }   
} 

class GBKPostMethod extends PostMethod{   
	public GBKPostMethod(String url){   
		super(url);   
	}   
	@Override  
	public String getRequestCharSet() {   
		return "UTF-8";   
	}   
} 

class Mybuf
{

	public byte buf[];
	public int size;

	public Mybuf(byte b[], int s)
	{
		buf = b;
		size = s;
	}
}
