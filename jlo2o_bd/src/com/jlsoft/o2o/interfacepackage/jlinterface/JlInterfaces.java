package com.jlsoft.o2o.interfacepackage.jlinterface;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.sqlwriter.ISQLWriter;
import com.jlsoft.framework.sqlwriter.SQLConvertor;
import com.jlsoft.framework.sqlwriter.SpringSQLWriter;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;

@Controller
public class JlInterfaces extends JLBill {
	private static final Logger logger = Logger.getLogger(JlInterfaces.class);
	 private static String JL_JBOSS_URL = JlAppResources.getProperty("JL_JBOSS_URL");
	 private static String UserName = JlAppResources.getProperty("JL_JBOSS_UserName");
	 private static String PassWord = JlAppResources.getProperty("JL_JBOSS_PassWord");

	public static Map<String, Object> retail(String json) throws Exception{
		StringBuffer sb= new StringBuffer();
		sb.append(JL_JBOSS_URL+"JLESServer/POST_SCM_DJ");
		sb.append(mosaicToString("?Type", "Post_Retail"));
		sb.append(mosaicToString("UserName", UserName));
		sb.append(mosaicToString("PassWord", PassWord));
		JSONObject jsonSignXml= new JSONObject();
		jsonSignXml.put("Parms", "JSONXML");
		jsonSignXml.put("Sign", "4d4d7828d59fd5f797ea7bccae99c9ec");
		sb.append(mosaicToString("SIGNXML", jsonSignXml.toString()));
		//sb.append(mosaicToString("JSONXML", json));
		/*
		JSONObject jsonSignXml=new JSONObject();
		jsonSignXml.put("Parms", "");
		jsonSignXml.put("Sign", "");
		JSONObject jsonXml=new JSONObject();
		jsonXml.put("Order_Code", "WBDJ0001");
		jsonXml.put("Vip_Code", "KANCHAO");
		jsonXml.put("SysTemCon", "");
		jsonXml.put("DepNo", "");
		jsonXml.put("SaleAssistantCode", "");
		jsonXml.put("SaleAssistantName", "");
		jsonXml.put("CashCode", "");
		jsonXml.put("CaseName", "");
		jsonXml.put("PayMent", "");
		jsonXml.put("SaleTypeNo", "");
		JSONArray jsonSpList= new JSONArray();
		JSONObject jsonSpInfo=new JSONObject();
		jsonSpList.add(jsonSpInfo);
		JSONArray jsonSkList= new JSONArray();
		JSONObject jsonSkInfo= new JSONObject();
		jsonSkInfo.put("PayCode", "");
		jsonSkInfo.put("PayAmount", "");
		jsonSkInfo.put("AmountCode", "");
		jsonSkInfo.put("ForeignCurr", "");
		jsonSkList.add(jsonSkInfo);
		JSONObject jsonCusinfo= new JSONObject();
		jsonCusinfo.put("Name", "");
		jsonCusinfo.put("Addr", "");
		jsonCusinfo.put("telephone", "");
		jsonCusinfo.put("mobile", "");
		sb.append(mosaicToString("SIGNXML", jsonSignXml.toString()));
		sb.append(mosaicToString("JSONXML", jsonXml.toString()));
		sb.append(mosaicToString("SPLIST", jsonSpList.toString()));
		sb.append(mosaicToString("SKLIST", ""));
		sb.append(mosaicToString("CUSINFO", ""));
		//url=sb.toString();
		 */
		String url=sb.toString();
		String returnJson=JLTools.sendToSync(json, url);
		Map<String, Object> hm=null;
		if(returnJson!=null){
			hm=(Map<String, Object>)JSONObject.fromObject(returnJson); 
		}
		
		return hm;
	}
	
	public static String mosaicToString(String param1,String param2){
		String param=null;
		if(param1.substring(0, 1).equals("?")){
			param=param1+"="+param2;
		}else{
			param="&"+param1+"="+param2;
		}
		 return param;
	}
	
	//票据分销收付款
	public static String pjFxsfkInterface(String json) throws Exception{
		String returnJson = JLTools.sendToSync(json,JL_JBOSS_URL
						+ "JLESServer/POST_SCM_DJ?Type=Post_B2BPJ&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
		return returnJson;
	}
	
	//创建分销单接口
	public static String createPFDInterface(String json) throws Exception{
		
		String returnJson = JLTools
		.sendToSync(json,JL_JBOSS_URL
						+ "JLESServer/POST_SCM_DJ?Type=Post_WholesaleSigle&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
		return returnJson;
	}
	//调用建档接口
	public static String scmJDInterface(String json) throws Exception{
		
		String returnJson = JLTools
		.sendToSync(json,JL_JBOSS_URL
						+ "JLESServer/POST_SCM_DJ?Type=Post_CreateRecord&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"052c9a48c069660933ed8dc1b8f541ff\"}");
		return returnJson;
	}
	
	//分销收款
	public static String orderPaymentInterface(String json) throws Exception{
		String returnJson = JLTools
		.sendToSync(json,JL_JBOSS_URL
						+ "JLESServer/POST_SCM_DJ?Type=Post_WholesalePayment&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
		return returnJson;
	}
	
	//制定返利单接口
	public static String createRebateInterface(String json) throws Exception{
		String returnJson = JLTools
		.sendToSync(json,JL_JBOSS_URL
						+ "JLESServer/POST_SCM_DJ?Type=Post_RebateSigle&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
		return returnJson;
	}
	
	//分销收付款
	public static String strikeaBalanceInterface(String json) throws Exception{
		String returnJson = JLTools
		.sendToSync(json,JL_JBOSS_URL
						+ "JLESServer/POST_SCM_DJ?Type=Post_StrikeaBalance&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
		return returnJson;
	}
	
	//通过商品条码查询编码中心数据
	public static String qfyInterface(String json) throws Exception{
		String returnJson = JLTools
		.sendToSync(json, JlAppResources.getProperty("ECS_URL")+"/JLESServer/POST_QFY?Type=Post_Check_BarCode&UserName=mycar&PassWord=88888&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"052c9a48c069660933ed8dc1b8f541ff\"}");
		return returnJson;
	}
	
	//通过GLN查询编码中心企业位置信息  119.79.224.118:30002
	public static String glnInterface(String json) throws Exception{
		String returnJson = JLTools
		.sendToSync(json, "http://192.168.1.104:59100/JLESServer/POST_QFY?Type=Post_GetPartyByGLN&UserName=JLSOFT&PassWord=88888&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"e30cad25e9df4be63c472295124b7420\"}");
		return returnJson;
	}
	
	public static String sendShortMessage(Map<String, Object> map){
		//logger.info("-----------进入短信接口-------");
		Map<String, Object> hm=new HashMap<String, Object>();
		hm.put("SpCode", "215650");//企业编号
		hm.put("LoginName", "gc_gp");//用户名称
		hm.put("Password", "106667gp");//用户密码
		hm.put("MessageContent", map.get("ShortMessage").toString());
		hm.put("UserNumber", map.get("sendMobilePhoneNo").toString());//手机号码(多个号码用”,”分隔)，最多1000个号码
		String lsh=System.currentTimeMillis()+""+(int)(Math.random()*10000000);
		hm.put("SerialNumber", lsh);//流水号，20位数字，唯一 
		hm.put("ScheduleTime", "");//预约发送时间，格式:yyyyMMddhhmmss,如‘20090901010101’，立即发送请填空
		hm.put("ExtendAccessNum", "");
		hm.put("f", "1");
		String url="http://js.ums86.com:8899/sms/Api/Send.do";
		String returnVal=JLTools.sendToRequest(url, hm, "GB2312");
		//logger.info(returnVal);
		//logger.info("-----------短信发送结束--------");
		return returnVal;
	}
	/*会员接口
	 * Post_VIPCard  会员建卡
	 * Post_VIPRecharge  会员储值充值接口
	 * Post_VIPPresentValue 会员赠送储值接口
	 * add by  liuwx  2014年10月20日14:20:28  for 明巢 
	 * */

  public static String vipinterface(String json,String Lx) throws Exception{
  	String returnJson="";
		returnJson=JLTools
		.sendToSync(json,JL_JBOSS_URL
				+ "JLESServer/POST_SCM_DJ?Type="+Lx+"&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
	
  /*	if (Lx.equals("Post_VIPCard")){
  		returnJson=PubFun
  		.sendToSync(json,JL_JBOSS_URL
					+ "JLESServer/POST_SCM_DJ?Type=Post_VIPCard&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
  	}
  	else if(Lx.equals("Post_VIPPresentValue")){
  		returnJson=PubFun
  		.sendToSync(json,JL_JBOSS_URL
					+ "JLESServer/POST_SCM_DJ?Type=Post_VIPRecharge&UserName="+UserName+"&PassWord="+PassWord+"&SIGNXML={\"Parms\":\"JSONXML\",\"Sign\":\"1503497db5e979b7a44f42585a38c363\"}");
  	}*/
		return returnJson;  	
  }	
  
  /***
   * 发送短信
   * @param map（SMS_pszMobis手机号码，SMS_Messages短信内容，DJLX单据类型，YWDH业务单号）
   * @return boolean
   * @throws Exception
   */
	public boolean sendSMS(Map<String, Object> map) throws Exception{
		boolean isSuccess = false;
		String error = "";
		String mobile = "";
		String content = "";
		String zcxx01 = "";
		try{
			mobile = JLTools.getMapStringValue(map, "SJHM", "");
			content = JLTools.getMapStringValue(map, "FSNR", "");
			zcxx01 = JLTools.getMapStringValue(map, "ZCXX01", "");
			if ( StringUtils.isBlank(mobile) ){
				error = "手机号码为空";
			} else if( StringUtils.isBlank(content) ) {
				error = "短信内容为空";
			} else {
				String SMS_BaseUrl = JlAppResources.getProperty("ECS_URL") + "/JLESServer/Post_Send_SMS";
				String data = "Type=MongateCsSpSendSmsNew&JSONXML=";
				JSONObject jo = new JSONObject();
				jo.put("SMS_Address", JlAppResources.getProperty("SMS_Address"));
				jo.put("SMS_UserName", JlAppResources.getProperty("SMS_UserName"));
				jo.put("SMS_PSW", JlAppResources.getProperty("SMS_PSW"));
				jo.put("SMS_pszMobis", mobile);
				jo.put("SMS_Messages", content);
				String sign = JlAppResources.getProperty("SMS_Sign");
				sign = URLDecoder.decode(sign, "utf-8");
				jo.put("SMS_Sign", sign);
				data += jo;
				String response = JLTools.postRequest(data, SMS_BaseUrl);
				if( StringUtils.isNotBlank(response) ){
					/*** 错误代码
					 * 	-1	参数为空。信息、电话号码等有空指针，登陆失败
						-12	有异常电话号码
						-14	实际号码个数超过100
						-999	服务器内部错误
						-10001	用户登陆不成功(帐号不存在/停用/密码错误)
						-10003	用户余额不足
						-10011	信息内容超长
						-10029	此用户没有权限从此通道发送信息(用户没有绑定该性质的通道，比如：用户发了小灵通的号码)
						-10030	不能发送移动号码
						-10031	手机号码(段)非法
						-10057	IP受限
						-10056	连接数超限
					 */
					String errCodes = ",-1,-12,-14,-999,-10001,-10003,-10011,-10029,-10030,-10031,-10056,-10057,";
					if( !errCodes.contains("," + response + ",") ){
						isSuccess = true;
					} else {
						error = response;
					}
				}
			}
		} catch(Exception ex) {
			error = ex.getMessage();
			logger.error(ex);
		} finally {
			//插入W_SMS表
			int djlx = Integer.parseInt(map.get("DJLX").toString());
			String ywdh = map.get("YWDH").toString();
			String sql="INSERT INTO W_SMS(DJLX,YWDH,FSSJ,SJHM,FSNR,CGBJ,ERROR,ZCXX01) "+
				             "VALUES(" + djlx + ", '" + ywdh + "', now(), '" + mobile + "', '" + content + "', " + (isSuccess ? 1 : 0) + ", '" + error + "','"+zcxx01+"')";
			execSQL(o2o, sql, map);
		}
		return isSuccess;
	}
  
}