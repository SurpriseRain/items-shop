package com.jlsoft.o2o.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JlAppResources;

@Controller
@RequestMapping("/ForgetPwd")
public class ForgetPwd extends JLBill{
	private static final String FROM = JlAppResources.getProperty("emailName");
	private static final String CHECK_CODE = "checkCode";
	
	/**
	 * 查找用户名或邮箱是否存在
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findUserByNameOrEmail.action")
	public List<Map<String, Object>> findUserByNameOrEmail(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String sql=
			"SELECT ZCXX01,PERSON_ID,ZCXX09 \n" +
			"FROM W_ZCXX A,W_XTCZY B \n" +
			"WHERE A.ZCXX01=B.GSID AND \n"+
			"(B.PERSON_ID='"+cds.getField("NameOrEmail", 0)+"' "+
			"OR A.ZCXX09='"+cds.getField("NameOrEmail", 0)+"')";
		List zhmmList=queryForList(o2o, sql);
		return zhmmList;
	}
	
	/**
	 * 找回密码
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendEmail.action")
	public Map sendEmail(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map map=new HashMap();
		String zcxx01 = cds.getField("zcxx01", 0);
		String person_id = cds.getField("username", 0);
		String email = cds.getField("email", 0);
		String checkcode = UUID.randomUUID().toString();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());//获取当前系统时间
		//先查询该用户此前的找回次数
		String sql1="SELECT DISTINCT MAX(SXH) FROM W_ZHMM WHERE PERSON_ID='"+person_id+"'";
		int i=queryForInt(o2o, sql1);
		int sxh = i+1;
		//记录此次发送重新设置密码的的相关信息
		String sql=
			"INSERT INTO W_ZHMM(ZCXX01,PERSON_ID,SXH,SENDDATA,CHECKCODE) "+
			"VALUES('"+zcxx01+"','"+person_id+"','"+sxh+"','"+date+"','"+checkcode+"')";
		Map row=getRow(sql, null, 0);
		int j=execSQL(o2o, sql, row);		
		if(j>0){
			// 发送重新设置密码的链接
			sendResetPasswordEmail(person_id,email,checkcode,sxh);
			map.put("state", "1");
		}else{
			map.put("state", "0");
		}
		
		return map;
	}
	
	
	/**
	 * 发送重设密码链接的邮件
	 */
	public static void sendResetPasswordEmail(String str,String str2,String str3,int str4) {
		Session session = getSession();
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject("找回您的帐户与密码");
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(FROM));
			message.setRecipient(RecipientType.TO, new InternetAddress(str2));
			message.setContent("要使用新的密码, 请使用以下链接启用密码（有效时间3分钟）:<br/><a href='" + generateResetPwdLink(str,str3,str4) +"'>点击重新设置密码</a>","text/html;charset=utf-8");
			// 发送邮件
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Session getSession() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", JlAppResources.getProperty("emailHost"));
		props.setProperty("mail.smtp.port", JlAppResources.getProperty("emailPost"));
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String password = JlAppResources.getProperty("emailPassword");
				return new PasswordAuthentication(FROM, password);
			}
			
		});
		return session;
	}
	
	/**
	 * 生成重设密码的链接
	 */
	public static String generateResetPwdLink(String str,String str2,int str3) {
		// String strr = "http://localhost:8088//ForgetPwd/verifyCheckcode.action?XmlData=[{'username':'"+str+"','sxh':'"+str3+"','CHECK_CODE':'"+generateCheckcode(str,str2)+"'}]"; 
		String path = JlAppResources.getProperty("emailUrl");
		String strr =path+"/customer/qfy/back/yhgl/zhmm.html?userName=" 
				+ str + "&sxh=" + str3 + "&" + CHECK_CODE + "=" + generateCheckcode(str,str2);
		return strr;
	}
	
	/**
	 * 生成验证帐户的MD5校验码
	 * @param str  要激活的用户名
	 * * @param str2  随机码
	 * @return 将用户名和密码组合后，通过md5加密后的16进制格式的字符串
	 */
	public static String generateCheckcode(String str,String str2) {
		String userName = str;
		String randomCode = str2;
		return md5(userName + ":" + randomCode);
	}
	
	public static String md5(String string) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
			md.update(string.getBytes());
			byte[] md5Bytes = md.digest();
			return bytes2Hex(md5Bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String bytes2Hex(byte[] byteArray)
	{
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++)
		{
			if(byteArray[i] >= 0 && byteArray[i] < 16)
			{
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	}
	
	/*****************以下为点击邮箱中链接后的功能函数*************************/
	/**
	 * 验证该链接是否为有效可用链接
	 * @param XmlData
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@RequestMapping("/verifyCheckcode.action")
	public Map verifyCheckcode(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map map=new HashMap();
		String sql=
			"SELECT SENDDATA,CHECKCODE \n" +
			"FROM W_ZHMM A \n" +
			"WHERE A.PERSON_ID='"+cds.getField("username", 0)+"' "+
			"AND A.SXH='"+cds.getField("sxh", 0)+"'";
		map=queryForMap(o2o, sql);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    Date d1 = df.parse(df.format(new Date()));
	    Date d2 = df.parse(map.get("SENDDATA").toString()); 
	    long diff = d1.getTime() - d2.getTime(); 
	    long days = diff / (1000 * 60);//计算时间差为分钟
	    if(days>3){
	    	map.put("state", "0");
	    }
	    else{
	    	//boolean flag=generateCheckcode(cds.getField("username", 0),map.get("checkcode").toString()).equals(cds.getField("checkCode", 0).toString());
			String str1=generateCheckcode(cds.getField("username", 0),map.get("checkcode").toString());
			String str2=cds.getField("checkcode", 0).toString();
			boolean flag=str1.equals(str2);
	    	if(!flag){
				map.put("state", "1");
			}
			else{
				map.put("state", "2");
			}
	    }
	    return map;
	}
	
	
}


