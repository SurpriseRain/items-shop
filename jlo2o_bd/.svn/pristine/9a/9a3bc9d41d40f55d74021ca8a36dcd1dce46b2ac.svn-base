package com.jlsoft.o2o.interfacepackage.alipayCom;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.allinpay.ets.client.util.MySecureProtocolSocketFactory;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.GnetePayUtils;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.HttpUtils;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.Md5;
import com.jlsoft.o2o.interfacepackage.alipay.unionpay.PayUtils;
import com.jlsoft.o2o.interfacepackage.loop.ErpLSD;
import com.jlsoft.o2o.interfacepackage.loop.ErpXSDD;
import com.jlsoft.o2o.order.service.OrderFlowManage;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
/*****************
 * 通联支付异步通知结果
 * @author Sun
 *
 */
@Controller
@RequestMapping("/UnionpayCommunications")
public class UnionpayCommunications extends JLBill{
	@Autowired
	private OrderFlowManage ofm;
	@Autowired
	private SmsService smsService;
	@Autowired
	private OrderFlowManage orderFlowManage;
	@Autowired
	private ErpLSD erpLSD;
	@Autowired
	private PubService pubService;
	
	/**
	 * @todo 获取银联记录，判断支付是否成功
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectW_XSDD_YLLS")
	public Map<String,Object> selectW_XSDD_YLLS(String json) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			cds = new DataSet(json);
			String sql = "SELECT YLLS,JYDH,ZFDH,SJCQM,CGBJ,DATE_FORMAT(ZFSJ,'%Y-%m-%d')ZFSJ FROM W_XSDD_YLLS WHERE YLLS='"+cds.getField("YLLS", 0)+"'";
			resultMap = queryForMap(o2o,sql);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入银联流水记录表
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertW_XSDD_YLLS")
	public Map<String,Object> insertW_XSDD_YLLS(String json,String skfsArr) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			cds = new DataSet(json);
			String sql = "";
			//插入收款记录
			orderFlowManage.insertOrderPayway(skfsArr);
			//插入银联流水号
			sql = "INSERT INTO W_XSDD_YLLS(YLLS,JYDH,ZFDH,SJCQM,CGBJ,ZFSJ,JYJE) " +
					           "VALUES(YLLS?,JYDH?,ZFDH?,SJCQM?,0,now(),JYJE?)";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			//更新单据状态
			String JYDH = cds.getField("JYDH", 0);
			sql = "UPDATE W_DJZX SET W_DJZX02=13 WHERE W_DJZX01 IN (SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"')";
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入通联银联流水记录表
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertW_XSDD_TL_YLLS")
	public Map<String,Object> insertW_XSDD_TL_YLLS(String json) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//String merchantId,String version,String signType,String paymentOrderId,String orderNo,String orderDatetime,String orderAmount,String payDatetime,String payAmount,String payResult
		//System.out.println(merchantId+"---"+version+"---"+paymentOrderId);
		/*JSONObject json=new JSONObject();
		json.put("YLLS",orderNo);
		json.put("JYDH",orderNo);
		json.put("ZFDH",paymentOrderId);
		json.put("JYJE",payAmount);	
		json.put("SJCQM",payDatetime);*/
		try{
			cds = new DataSet(json);
			String sql = "";
			//插入收款记录
			//orderFlowManage.insertOrderPayway(json.toString());
			//插入银联流水号
			sql = "INSERT INTO W_XSDD_YLLS(YLLS,JYDH,ZFDH,SJCQM,CGBJ,ZFSJ,JYJE,OEDERSJC) " +
					           "VALUES(YLLS?,JYDH?,ZFDH?,SJCQM?,0,now(),JYJE?,orderDatetime?)";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			//更新单据状态
			String JYDH = cds.getField("JYDH", 0);
			sql = "UPDATE W_DJZX SET W_DJZX02=13 WHERE W_DJZX01 IN (SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"')";
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入通联银联流水记录表
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertW_XSDD_TL_JYJG")
	public String insertW_XSDD_TL_JYJG(String merchantId,String version,String signType,String paymentOrderId,String orderNo,String orderDatetime,String orderAmount,String payDatetime,String payAmount,String payResult) throws Exception{
		System.out.println("成功----------------------"+merchantId);
		return "";
	}
	
	/**
	 * @todo 更新银联表支付成功标记
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateW_XSDD_YLLS(String ylls) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String sql="UPDATE W_XSDD_YLLS SET CGBJ=1 WHERE YLLS='"+ylls+"'";
			execSQL(o2o,sql,resultMap);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	/*****
	 * 更新积分表
	 * @param JYDH
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateW_HYJF_YLLS(String JYDH,String person_id) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			String Sql ="Select a.XSDD01,IFNULL((a.xsdd25-a.xsdd30),0)jfnum,IFNULL(a.xsdd25,0)xsdd25,IFNULL(a.xsdd30,0)xsdd30,0 mxjf,2 BGBJ,NOW() BGDATE  from w_xsdd a,w_xsdditem b where a.xsdd01=b.xsdd01 and a.jydh='"+JYDH+"'";
			List  list = queryForList(o2o, Sql);
			Map map = new HashMap();
			for(int i=0;i<list.size();i++){
				map = (Map) list.get(i);
				if(Float.parseFloat(map.get("xsdd25").toString())>0){//获得积分
					String insrtsqlm="insert into w_hyjfitem (person_id,BGBJ,XSDD01,BGNUM,BGDATE,BGZT)values('"+person_id+"','2',XSDD01?,'"+map.get("xsdd25").toString()+"',DATE_FORMAT(NOW(),'%Y-%m-%d %H:%m:%s'),'0')";
					int j=execSQL(o2o, insrtsqlm, map);
				}
				if(Float.parseFloat(map.get("xsdd30").toString())>0){//消费积分
					String insrtsqlm="insert into w_hyjfitem (person_id,BGBJ,XSDD01,BGNUM,BGDATE,BGZT)values('"+person_id+"','3',XSDD01?," +
							"'"+map.get("xsdd30").toString()+"',DATE_FORMAT(NOW(),'%Y-%m-%d %H:%m:%s'),'1')";
					int j=execSQL(o2o, insrtsqlm, map);
				}
			}
			String updateSql="update w_hyjf set jfnum=jfnum+"+((Map)list.get(0)).get("jfnum")+" where person_id='"+person_id+"'";
			int m=execSQL(o2o, updateSql, 0);
			if(m==0){
				String insrtsqld="insert into w_hyjf (person_id,jfnum)values('"+person_id+"','"+((Map)list.get(0)).get("jfnum")+"')";
				int k=execSQL(o2o, insrtsqld, map);	
			}
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}

	/**
	 * @todo 银联支付完成处理
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/callUnionpaySucess")
	public Map<String,Object> callUnionpaySucess(String merchantId,String version,String signType,String paymentOrderId,String orderNo,String orderDatetime,String orderAmount,String payDatetime,String payAmount,String payResult,String person_id) throws Exception{
		//var jsonData = {"JYDH":JYDH,"YLLS":paymentOrderId,"ZFDH":PayNo,"SJCQM":SignMsg,"JYJE":JYJE};
		JSONObject json=new JSONObject();
		json.put("YLLS",paymentOrderId);
		json.put("JYDH",orderNo);
		json.put("ZFDH",paymentOrderId);
		json.put("JYJE",payAmount);	
		json.put("SJCQM",payDatetime);
		json.put("orderDatetime", orderDatetime);
		json.put("person_id", person_id);
		System.out.println("银联支付回调 start"+json.toString()+"#########################");
		insertW_XSDD_TL_YLLS(json.toString());
		cds = new DataSet(json.toString());
		Map<String,Object> resultMap = new HashMap<String,Object>();
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM");
		String dateMonth=simple.format(new Date());
		try{
			
			//处理查询结果
			if(payResult.equals("1")){
				String JYDH = cds.getField("JYDH", 0);
				//更新交易单状态，为支付完成
				String jySQL = "UPDATE W_JYD SET ZT=2 WHERE JYDH='"+JYDH+"'";
				execSQL(o2o,jySQL,resultMap);
				System.out.println("############1#############");
				//查询结果成功，更新银联支付成功标记
				updateW_XSDD_YLLS(cds.getField("YLLS", 0));
				//更新积分表
				updateW_HYJF_YLLS(cds.getField("JYDH", 0),json.get("person_id").toString());
				System.out.println("############2#############");
				//更新订单状态
				String sql = "SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"'";
				List list = queryForList(o2o,sql);
				if(list != null && list.size()>0){
					Map map;
					for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						Map ddztMap = new HashMap();
						ddztMap.put("XSDD01",map.get("XSDD01").toString());
						ddztMap.put("trade_no", cds.getField("YLLS", 0));
						JSONArray ddztJson = JSONArray.fromObject(ddztMap);
						ofm.updateOrderInfo(ddztJson.toString());
						try{
							//与erp对接
							String queryzidsql = "SELECT ztid from w_xsdd where xsdd01 ='"+map.get("XSDD01").toString()+"'";
							Map ztidMap = queryForMap(o2o, queryzidsql);
							Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
							if(erpMap.get("DJLX") != null){
								if(erpMap.get("DJLX").equals("SCMV7")){
									//erpLSD.paySucessV7LSD(map.get("XSDD01").toString());
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				//发送短信
				if(JLTools.getCurConf(2) == 1){
					String smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.HBID,A.XSDD01,B.ZCXX02 "+
					                           "FROM W_XSDD A,W_ZCGS B WHERE A.HBID=B.ZCXX01 AND A.JYDH='"+JYDH+"'";
					List mjList = queryForList(o2o,smsSQL);
					//跟买家发送短信
					String bhid="";
					String sjhm = "";
		            String xsddStr = "";
		            String content = "";
					Map mjMap;
					for(int i=0;i<mjList.size();i++){
						mjMap=(Map)mjList.get(i);
						bhid=mjMap.get("HBID").toString();
						sjhm=mjMap.get("SJHM").toString();
						xsddStr = xsddStr + mjMap.get("XSDD01").toString() + ",";
					}
					xsddStr=xsddStr.substring(0, xsddStr.length()-1);
					content = "尊敬客户您好，您的订单，单号："+xsddStr+" 已付款！感谢您的购买！";
					smsService.sendSms(2, JYDH, sjhm, content, bhid);
					//跟卖家发送短信
					smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.ZTID,A.XSDD01,B.ZCXX02 FROM W_XSDD A,W_ZCGS B WHERE A.ZTID=B.ZCXX01 AND A.JYDH='"+JYDH+"'";
					List dpList = queryForList(o2o,smsSQL);
					Map smsMap;
					for(int i=0;i<dpList.size();i++){
						smsMap=(Map)dpList.get(i);
						content = "尊敬的"+smsMap.get("ZCXX02").toString()+"您好，您的店铺有新增订单，单号："+smsMap.get("XSDD01").toString()+"，请您尽快发货！";
						smsService.sendSms(2, JYDH, smsMap.get("SJHM").toString(), content, smsMap.get("ZTID").toString());
					}
				}
				
				
				//遍历订单商品
				String sql1 = "select ztid,spxx01 from w_xsdd a,w_xsdditem b where a.xsdd01=b.xsdd01 and a.jydh='"+JYDH+"'";
				List yxlspxxlist =queryForList(o2o, sql1);
				Map temp=new HashMap();
				for(int i=0;i<yxlspxxlist.size();i++){
					temp=(Map) yxlspxxlist.get(i);
					LinkedList inParameter = new LinkedList();
					System.out.println("######################月销量统计开始#####"+dateMonth+"-"+temp.get("ztid")+"-"+temp.get("spxx01"));
					inParameter.add(dateMonth+"-"+temp.get("ztid")+"-"+temp.get("spxx01"));//年-月-公司代码-商品代码
					LinkedList outParameter = new LinkedList();
					outParameter.add(java.sql.Types.INTEGER);
					String sqlq="{call UPDATE_JCBHZT(?,?)}";
					List DZBH = callProc(o2o, sqlq, inParameter, outParameter);
					System.out.println("######################月销量统计结束######"+DZBH.get(0));
					String sql2="update w_spgl set spgl26='"+DZBH.get(0)+"' where spxx01='"+temp.get("spxx01")+"'"; 
					int j=execSQL(o2o, sql2, 0);
					System.out.println("######################更新月销量########"+j);
				}
				resultMap.put("STATE", "success");
			}else{
				resultMap.put("STATE", "failure");
			}
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	

	/**
	 * @todo 退款与银联发起交互
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refund")
	public Map<String,Object> refund(String json,HttpServletRequest request) throws Exception{
		cds = new DataSet(json);
		request.setCharacterEncoding("UTF-8");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//商户网站域名
		String posturl = JlAppResources.getProperty("unionpay_serverUrl");
		String key="1234567890";
		//组装请求参数
		StringBuffer SourceText = new StringBuffer();
		String version = "v1.3";
		String signType = "0";
		String merchantId = JlAppResources.getProperty("unionpay_merchantId");
		String OrderNo = cds.getField("JYDH", 0);//cds.getField("YLLS", 0);
		String refundAmount =  "100";//cds.getField("TKJE", 0);//cds.getField("TKJE", 0);
		String orderDatetime = cds.getField("orderDatetime", 0);
		SourceText.append("&Version=").append(version);
		SourceText.append("&signType=").append(signType);
		SourceText.append("&merchantId=").append(merchantId);
		SourceText.append("&OrderNo=").append(OrderNo);//商户退款订单号/Y
		SourceText.append("&refundAmount=").append(refundAmount); //退款金额
		//SourceText.append("&mchtRefundOrderNo=").append(cds.getField("TKJE", 0)); //商户退款订单号/N
		SourceText.append("&orderDatetime=").append(orderDatetime); //商户订单提交时间/Y
		SourceText.append("&key=").append(key); //商户退款订单号/N			
		
		com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
		requestOrder.setVersion(version);
		requestOrder.setSignType(Integer.parseInt(signType));
		requestOrder.setMerchantId(merchantId);
		requestOrder.setOrderNo(OrderNo);
		requestOrder.setRefundAmount(Long.parseLong(refundAmount));
		requestOrder.setOrderDatetime(orderDatetime);
		requestOrder.setKey(key); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。
		String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
		String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
		PostMethod postMethod = null;
		Map map = new HashMap();
		try{
		Protocol myhttps = new Protocol("https",new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient httpclient = new HttpClient();
		postMethod = new PostMethod(posturl);
		NameValuePair[] datas = {
				new NameValuePair("merchantId", merchantId),
				new NameValuePair("version", version),
				new NameValuePair("signType", signType),
				new NameValuePair("orderNo", OrderNo),
				new NameValuePair("orderDatetime", orderDatetime),
				new NameValuePair("refundAmount", refundAmount),
				new NameValuePair("signMsg", strSignMsg) };
		postMethod.setRequestBody(datas);		
		int responseCode = httpclient.executeMethod(postMethod);		
		// 取得查询交易结果
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String trxXML = postMethod.getResponseBodyAsString();
			String[] msg =trxXML.split("&");
			String [] paramPair=null;
			for(int i=0;i<msg.length;i++){
				paramPair=msg[i].split("=");
	        	map.put(paramPair[0], paramPair[1]); 
			}					
		}	
		}catch(Exception e){
		System.out.println(e);
		}		
		//验签是商户为了验证接收到的报文数据确实是支付网关发送的。
		//构造订单结果对象，验证签名。
		com.allinpay.ets.client.PaymentResult paymentResult = new com.allinpay.ets.client.PaymentResult();
		if("".equals(map.get("ERRORCODE"))||null==map.get("ERRORCODE")){
		//如果errorCode为空，说明返回正确退款报文信息，接下来对报文进行解析验签
		paymentResult.setMerchantId(map.get("merchantId").toString());
		paymentResult.setVersion(map.get("version").toString());
		paymentResult.setSignType(map.get("signType").toString());
		paymentResult.setOrderNo(map.get("orderNo").toString());
		paymentResult.setOrderDatetime(map.get("orderDatetime").toString());
		paymentResult.setOrderAmount(map.get("orderAmount").toString());
		paymentResult.setErrorCode(null==map.get("errorCode")?"":map.get("errorCode").toString());
		paymentResult.setRefundAmount(map.get("refundAmount").toString());
		paymentResult.setRefundDatetime(map.get("refundDatetime").toString());
		paymentResult.setRefundResult(map.get("refundResult").toString());
		paymentResult.setReturnDatetime(map.get("returnDatetime").toString());
		//signMsg为服务器端返回的签名值。
		paymentResult.setSignMsg(map.get("signMsg").toString()); 
		paymentResult.setKey(key);
		//验证签名：返回true代表验签成功；否则验签失败。
		boolean verifyResult = paymentResult.verify();
			if(verifyResult){
				map.put("STATE", "success");
			}else{
				map.put("STATE", "failure");
			}
		}
		return map;
	}
	
	@RequestMapping("/tomain")
	public void tomain(){
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM");
		String dateMonth=simple.format(new Date());
		LinkedList inParameter = new LinkedList();
		inParameter.add(dateMonth+"-SPXX01-7");//年-月-商品代码
		LinkedList outParameter = new LinkedList();
		outParameter.add(java.sql.Types.INTEGER);
		String sqlq="{call UPDATE_JCBHZT(?,?)}";
		List DZBH = callProc(o2o, sqlq, inParameter, outParameter);
		System.out.println("##################"+DZBH.toString());
	}
}