package com.jlsoft.o2o.interfacepackage.http;



import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;



public class RequestUtil {




	private static String serverUrl = null;

	public final static String POST = "POST";

	public final static String GET = "GET";

	public enum METHOD {
		POST("POST"), GET("GET");
		private String key;

		private METHOD(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public String toString() {
			return key;
		}
	}


	@SuppressWarnings("unchecked")
	public static String  postData(String address, String body) {
		return httpRequest(address, body, "POST",
				Collections.EMPTY_MAP);
	}

	public static String postData(String address, String body,
			Map<String, String> paramMap, String method) {
		return httpRequest(address, body, method, paramMap);
	}

	public static String postData(String address, String body,
			Map<String, String> paramMap) {
		return httpRequest(address, body, "POST", paramMap);
	}

	private static String httpRequest(String address, String outputStr, String method, Map<String, String> paramMap) {
		String response = "";
		HttpURLConnection httpUrlConn = null;
		try {
			
			String path = address;
			for (String key : paramMap.keySet()) {
				if (paramMap.get(key) != null) {
					outputStr += "&" + key + "=" + paramMap.get(key).toString();
				}
			}
			if (paramMap.size() > 0) {
				outputStr = outputStr.substring(1, outputStr.length());
			}
			if ("GET".equalsIgnoreCase(method)) {
				path += (path.indexOf("?") > 0 ? "&" : "?") + outputStr;
			}
			// 建立连接
			URL url = new URL(path);
			httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setChunkedStreamingMode(0);
			// httpUrlConn.setConnectTimeout(5000);
			// httpUrlConn.setReadTimeout(5000);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(method);
			httpUrlConn.setRequestProperty("Accept-Charset", "UTF-8");
			httpUrlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			
			if ("GET".equalsIgnoreCase(method)) {
				httpUrlConn.connect();
			} else {
				httpUrlConn.setDoOutput(true);
				httpUrlConn.setDoInput(true);
				httpUrlConn.setChunkedStreamingMode(0);
				// httpUrlConn.setConnectTimeout(5000);
				// httpUrlConn.setReadTimeout(5000);
				httpUrlConn.setUseCaches(false);
				if (null!=outputStr&&""!=outputStr) {
					// 数据提交
					OutputStream outputStream = httpUrlConn.getOutputStream(); // 注意编码格式，防止中文乱码
					outputStream.write(outputStr.getBytes("UTF-8"));
					outputStream.flush();
					outputStream.close();
				}
			}
			int code = httpUrlConn.getResponseCode();
			if (code == 200) {
				// 将返回的输入流转换成字符串
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(httpUrlConn.getInputStream(),
								"UTF-8"));
				StringBuffer buffer = new StringBuffer(256);
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				response = buffer.toString();
			} else {
				throw new Exception(httpUrlConn.getResponseCode() + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpUrlConn) {
				httpUrlConn.disconnect();
			}
		}
		return response;
	}

	public InputStream getInputStream(String address, String outputStr,
			String method, Map<String, String> paramMap) {
		HttpURLConnection httpUrlConn = null;
		InputStream inputStream = null;
		try {
			String path = serverUrl + address;
			for (String key : paramMap.keySet()) {
				if (paramMap.get(key) != null) {
					outputStr += "&" + key + "=" + paramMap.get(key).toString();
				}
			}
			if (paramMap.size() > 0) {
				outputStr = outputStr.substring(1, outputStr.length());
			}
			if ("GET".equalsIgnoreCase(method)) {
				path += (path.indexOf("?") > 0 ? "&" : "?") + outputStr;
			}
			// 建立连接
			URL url = new URL(path);
			httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setChunkedStreamingMode(0);
			// httpUrlConn.setConnectTimeout(5000);
			// httpUrlConn.setReadTimeout(5000);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(method);
			httpUrlConn.setRequestProperty("Accept-Charset", "UTF-8");
			httpUrlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			if ("GET".equalsIgnoreCase(method)) {
				httpUrlConn.connect();
			} else {
				httpUrlConn.setDoOutput(true);
				httpUrlConn.setDoInput(true);
				httpUrlConn.setChunkedStreamingMode(0);
				httpUrlConn.setConnectTimeout(5000);
				httpUrlConn.setUseCaches(false);
				if (null!=outputStr&&""!=outputStr) {
					// 数据提交
					OutputStream outputStream = httpUrlConn.getOutputStream(); // 注意编码格式，防止中文乱码
					outputStream.write(outputStr.getBytes("UTF-8"));
					outputStream.flush();
					outputStream.close();
				}
			}
			int code = httpUrlConn.getResponseCode();
			if (code == 200) {
				inputStream = httpUrlConn.getInputStream();
			} else {
				throw new Exception(httpUrlConn.getResponseCode() + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != httpUrlConn) {
				httpUrlConn.disconnect();
			}
		}
		return inputStream;
	}

}