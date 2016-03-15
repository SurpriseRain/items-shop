package com.jlsoft.o2o.interfacepackage.binkofchina;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ZHPKTools {
	public static PKCS7Tool getSigner() throws GeneralSecurityException, IOException{
		String keyStorePath=ZHPKTools.class.getResource("南京高鹏投资有限公司.pfx").getPath();
		System.out.println(keyStorePath);
		String keyStorePassword="111111";
		String keyPassword="111111";
		PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath,keyStorePassword,keyPassword);
		return tool;
	}
	public static String getSignPath(){
		String path=ZHPKTools.class.getResource("bocnet ca.cer").getPath().replaceAll("%20", " ");
	 	System.out.println("根证书的路径："+path);
	 	return path;
	}
	
}
