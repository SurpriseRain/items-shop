package com.jlsoft.o2o.interfacepackage.alipay.unionpay;

import java.security.MessageDigest;

/**
 * 功能说明
 * 		<ul>
 * 			<li>创建文件描述：MD5加密</li>
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
public class Md5
{
	public static String md5(String encryString)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(encryString.getBytes());
			byte b[] = digest.digest();
			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++)
			{
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			digest = null;
			b = null;
			return buf.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
}
