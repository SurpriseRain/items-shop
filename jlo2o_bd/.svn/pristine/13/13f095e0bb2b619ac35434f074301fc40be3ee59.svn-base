package com.jlsoft.o2o.interfacepackage.alipay.unionpay;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager
{
	X509TrustManager sunJSSEX509TrustManager;
	public MyX509TrustManager(String JKSPath, String JKSPwd) throws Exception
	{
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(JKSPath), JKSPwd.toCharArray());
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
		tmf.init(ks);
		TrustManager tms[] = tmf.getTrustManagers();
		for (int i = 0; i < tms.length; i++)
		{
			if (tms[i] instanceof X509TrustManager)
			{
				sunJSSEX509TrustManager = (X509TrustManager) tms[i];
				return;
			}
		}
		throw new Exception("无法初始化HTTPS-JKS，请重新加截");
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
	{
		sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
	{
		sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
	}

	public X509Certificate[] getAcceptedIssuers()
	{
		return sunJSSEX509TrustManager.getAcceptedIssuers();
	}
}
