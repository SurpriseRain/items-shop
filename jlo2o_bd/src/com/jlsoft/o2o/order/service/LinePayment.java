package com.jlsoft.o2o.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.loop.ErpXSDD;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
/**
 * 线下转账审核
 * @author Ninel
 *
 */
@Controller
@RequestMapping("/LinePayment")
public class LinePayment extends JLBill {
	
	@Autowired
	private PubService pubService;
	@Autowired
	private OrderFlowManage ofm;
	@Autowired
	private ErpXSDD erpXSDD;
	@Autowired
	private SmsService smsService;
	
	
	//给运营发送支付成功短信,运营电话号码
	String yyPhone = JlAppResources.getProperty("notice_Phone");
	
	@RequestMapping("/select_LinePayment.action")
	public Map select_LinePayment(String XmlData) throws Exception {
			cds=new DataSet(XmlData);
			String s= cds.getField("JYLSH", 0);
			String sql="SELECT a.JYDH,a.JYLSH,b.XSDD01,a.JYLSH,a.JYJE,a.JYDZ,a.HKR,a.JYYH,DATE_FORMAT(a.HKRQ,'%Y-%c-%d') HKRQ,a.HKBZ, a.XSDD51 FROM w_jyd a, w_jyditem b WHERE a.JYLSH='"+s+"'AND a.jydh =b.jydh";
			List spfllist = queryForList(o2o,sql);
	    	Map map = new HashMap();
	    	map.put("spfllist", spfllist);
			return map;
	    }
	
	/**
	 * 审核线下转账申请
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/auditPayStatus")
	public Map auditUnionpayReceiveBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String JYDH = request.getParameter("JYDH");
		
		if(JYDH=="" || JYDH == null){
			resultMap.put("state", "failure");
			return resultMap;
		}		
		//更新W_JYD交易单状态，为支付完成
		String jySQL = "UPDATE W_JYD SET ZT=2,XSDD51=2 WHERE JYDH='"+JYDH+"'";
		execSQL(o2o,jySQL,resultMap);
		
		//查询交易单下的的订单，更新W_XSDD订单状态
		String sql = "SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"'";
		List list = queryForList(o2o,sql);
		if(list != null && list.size()>0){
			Map map;
			for(int i=0;i<list.size();i++){
				map = (Map)list.get(i);
				Map ddztMap = new HashMap();
				ddztMap.put("XSDD01",map.get("XSDD01").toString());
				JSONArray ddztJson = JSONArray.fromObject(ddztMap);				
				modifyOrderInfo(ddztJson.toString());
				try{
					//与erp对接
					String queryzidsql = "SELECT ztid from w_xsdd where xsdd01 ='"+map.get("XSDD01").toString()+"'";
					Map ztidMap = queryForMap(o2o, queryzidsql);
					ddztMap.put("trade_no", map.get("XSDD01"));
					Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
					if(erpMap.get("DJLX") != null){
						if(erpMap.get("DJLX").equals("V9")){
							erpXSDD.paySucessV9XSDD(map.get("XSDD01").toString());
						}
					}					
				}catch(Exception e){
					e.printStackTrace();
					resultMap.put("state", "failure");
				}
			}
			//上述操作成功后，发送短信
			sendSms(JYDH);
		}
		resultMap.put("state", "success");
		return resultMap;
	}
	
	
	/**
	 * 订单线下转账审核通过-业务逻辑处理
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	private Map modifyOrderInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String orderId = cds.getField("XSDD01", 0);
		// 更新W_DJZX表中状态为14
		String djzxSQL = "UPDATE w_djzx SET w_djzx02=14 WHERE w_djzx01 =XSDD01?";
		Map row = getRow(djzxSQL, null, 0);
		execSQL(o2o, djzxSQL, row);
		
		// 更新单据状态W_DJZT
		String djztSQL = "INSERT INTO w_djzt(W_DJZT01,W_DJZT02,W_DJZT03,TIME01) VALUES(XSDD01?,NOW(),'订单已支付，待发货！',NOW())";
		Map row2 = getRow(djztSQL, null, 0);
		execSQL(o2o, djztSQL, row2);

		//查询交易单的线下转账时间及订单的总金额
		String queryXsddSql = "select a.xsdd02, b.HKRQ from w_xsdd a LEFT JOIN w_jyd b on a.JYDH = b.JYDH where a.xsdd01='"+orderId +"'";
		Map queryMap = queryForMap(o2o, queryXsddSql);
		
		//向w_xsddskfs插入记录
		String xsddskfsSQL = "insert into w_xsddskfs (xsdd01, skfs, zfje, zfsj, yxbj) values ('"+orderId+"', 10,"+queryMap.get("xsdd02")+",'"+ queryMap.get("HKRQ")+"',1)";
		Map xsddskfsMap = getRow(xsddskfsSQL, null, 0);
		execSQL(o2o, xsddskfsSQL, xsddskfsMap);
		
		Map resultMap = new HashMap();
		resultMap.put("STATE", 1);
		return resultMap;
	}
	
	/**
	 * 向审核通过线下转账的交易单发送短信
	 * @param JYDH 交易单号
	 */
	private void sendSms(String JYDH){
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
			//给运营发送订单付款通知短信
			content = "订单提醒,单号："+xsddStr+" 已付款！请跟踪处理";
			smsService.sendSms(2, JYDH, yyPhone, content, bhid);
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

	}
	
	
}
