package com.jlsoft.o2o.pub.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.interfacepackage.jlinterface.JlInterfaces;

@Service("smsService")
public class SmsService extends JLBill {
	@Autowired
	private JlInterfaces jlInterfaces;
	
	/**
	 * @todo 发送短信
	 * @param DJLX 单据类型
	 * @param YWDH 业务单号
	 * @param SJHM 手机号码
	 * @param FSNR 发送内容
	 * @param ZCXX01 注册公司编码
	 * @throws Exception 
	 */
	public boolean sendSms(int DJLX,String YWDH,String SJHM,String FSNR,String ZCXX01){
		boolean flag = false;
		try{
			Map map = new HashMap();
			map.put("DJLX", new Integer(DJLX));
			map.put("YWDH", YWDH);
			map.put("SJHM", SJHM);
			map.put("FSNR", FSNR);
			map.put("ZCXX01", ZCXX01);
			flag = jlInterfaces.sendSMS(map);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
}
