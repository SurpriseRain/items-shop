package com.jlsoft.o2o.interfacepackage.alipay.unionpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.HttpException;

import com.jlsoft.o2o.interfacepackage.alipay.unionpay.MyX509TrustManager;

/**
 * 功能说明
 * 		<ul>
 * 			<li>创建文件描述：HttpUtils工具，doPost、doGet</li>
 * 			<li>修改文件描述：</li>
 *		</ul>
 * @author 
 * 		<ul>
 * 			<li>创建者：<a href="mailto:43840397@qq.com">蔡越锐  </a></li>
 * 			<li>修改者：</li>
 * 		</ul>
 * @version 
 * 		<ul>
 * 			<li>创建版本：v1.0.0  日期：2011-11-19</li>
 * 			<li>修改版本：</li>
 * 		</ul>
 */
public class HttpUtils
{
	private int httpStatus = -1;
	private final int TIME_OUT = 60000;
	private String JKSPath;
	private String JKSPwd;
	
	public String doPost(String postUrl, String params, String charset, String Referer) 
	{
		if(postUrl != null && postUrl.toUpperCase().startsWith("https".toUpperCase()))
		{
			return this.doHttpsPost(postUrl, params, charset, Referer);
		}
		else if(postUrl != null && postUrl.toUpperCase().startsWith("http".toUpperCase()))
		{
			return this.doHttpPost(postUrl, params, charset, Referer);
		}
		else
		{
			this.httpStatus = 999;
			return null;
		}
	}
	
	private String doHttpPost(String postUrl, String params, String charset, String Referer) 
	{
		int timeOut = TIME_OUT;
		charset = charset==null?"UTF-8":charset;
		StringBuffer buffer = new StringBuffer();
		buffer.append("发送失败");
		HttpURLConnection c = null;
		try
		{
			if (postUrl == null || "".equals(postUrl.trim())) throw new Exception("PostUrl不能为空");
			if (params == null || "".equals(params.trim())) throw new Exception("POST参数不能为空");
			if (Referer == null || "".equals(Referer.trim())) throw new Exception("商户域名不能为空");
			
			URL url = new URL(postUrl);
			c = (HttpURLConnection) url.openConnection();
			c.setRequestMethod("POST");
			c.setRequestProperty("Accept-Language", "zh-cn");
			c.setRequestProperty("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			c.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mathon 2.0)");
			c.setRequestProperty("Connection", "Keep-Alive");
			c.setRequestProperty("Accept-Charset", charset);
			c.setRequestProperty("Charsert", charset);
			c.setRequestProperty("Referer", Referer);
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setConnectTimeout(timeOut);// 设置连接主机超时（单位：毫秒）
			c.setReadTimeout(timeOut);// 设置从主机读取数据超时（单位：毫秒）
			c.connect();
			//PrintWriter out = new PrintWriter(c.getOutputStream());// 发送数据
			PrintWriter out = new PrintWriter(new OutputStreamWriter(c.getOutputStream(), charset));// 发送数据
			out.print(params);
			out.flush();
			out.close();
			String header;
			for (int i = 0; true; i++)
			{
				header = c.getHeaderField(i);
				if (header == null) break;
			}
			int res = 0;
			res = c.getResponseCode();
			this.httpStatus = res;
			InputStream u = c.getInputStream(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(u, charset));
			String line = "";
			buffer = new StringBuffer();
			char _line = 10;
			while ((line = in.readLine()) != null)
			{
				buffer.append(line).append(_line);
			}
			u.close();
			in.close();
		}
		catch (HttpException ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		finally
		{
			if (c!=null) c.disconnect();
		}
		return buffer.toString();
	}
	
	
	/**
	 * HTTPS-POST
	 * 
	 * @param postUrl
	 * @param params
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	
	private String doHttpsPost(String postUrl, String params, String charset, String Referer) 
	{
		int timeOut = TIME_OUT;
		charset = charset==null?"UTF-8":charset;
		StringBuffer buffer = new StringBuffer();
		buffer.append("发送失败");
		HttpsURLConnection c = null;
		try 
		{
			if (postUrl == null || "".equals(postUrl.trim())) throw new Exception("PostUrl不能为空");
			if (params == null || "".equals(params.trim())) throw new Exception("POST参数不能为空");
			if (Referer == null || "".equals(Referer.trim())) throw new Exception("商户域名不能为空");
			
	        TrustManager[] tm = { new MyX509TrustManager(JKSPath, JKSPwd) };
	        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	        sslContext.init(null, tm, new java.security.SecureRandom());
	        SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(postUrl);
			c = (HttpsURLConnection) url.openConnection();
			c.setSSLSocketFactory(ssf);
			c.setRequestMethod("POST");
			c.setRequestProperty("Accept-Language", "zh-cn");
			c.setRequestProperty("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			c.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mathon 2.0)");
			c.setRequestProperty("Connection", "Keep-Alive");
			c.setRequestProperty("Accept-Charset", charset);
			c.setRequestProperty("Charsert", charset);
			c.setRequestProperty("Referer", Referer);
			c.setDoOutput(true);
			c.setDoInput(true);
			c.setConnectTimeout(timeOut);// 设置连接主机超时（单位：毫秒）
			c.setReadTimeout(timeOut);// 设置从主机读取数据超时（单位：毫秒）
			c.connect();
			//PrintWriter out = new PrintWriter(c.getOutputStream());// 发送数据
			PrintWriter out = new PrintWriter(new OutputStreamWriter(c.getOutputStream(), charset));// 发送数据
			out.print(params);
			out.flush();
			out.close();
			String header;
			for (int i = 0; true; i++)
			{
				header = c.getHeaderField(i);
				if (header == null) break;
			}
			int res = 0;
			res = c.getResponseCode();
			this.httpStatus = res;
			InputStream u = c.getInputStream(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(u, charset));
			String line = "";
			buffer = new StringBuffer();
			char _line = 10;
			while ((line = in.readLine()) != null)
			{
				buffer.append(line).append(_line);
			}
			u.close();
			in.close();
		}
		catch (HttpException ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return ex.toString();
		}
		finally
		{
			if (c!=null) c.disconnect();
		}
		return buffer.toString();
	}
	
	public Map<String, String> getParam(String httpParam)
	{
		String[] httpParamList = httpParam.split("&");
		Map<String, String> map = new HashMap<String, String> ();
		for(String Param : httpParamList)
		{
			int flag = Param.indexOf("=");
			if (flag == -1) continue;
			String _key = Param.substring(0, flag);
			String _value = Param.substring(flag+1, Param.length());
			map.put(_key, _value);
		}
		return map; 
	}

	public int getHttpStatus()
	{
		return httpStatus;
	}

	public String getJKSPath()
	{
		return JKSPath;
	}

	public void setJKSPath(String jKSPath)
	{
		JKSPath = jKSPath;
	}

	public String getJKSPwd()
	{
		return JKSPwd;
	}

	public void setJKSPwd(String jKSPwd)
	{
		JKSPwd = jKSPwd;
	}
}
