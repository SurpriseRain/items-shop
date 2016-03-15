package com.jlsoft.o2o.interfacepackage.ebc;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.loop.ErpXSDD;
import com.jlsoft.o2o.order.service.OrderFlowManage;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

@Controller
@RequestMapping("/EbcController")
public class EBCController extends JLBill {
	private OrderFlowManage ofm;
	@Autowired
	private SmsService smsService;
	@Autowired
	private ErpXSDD erpXSDD;
	@Autowired
	private PubService pubService;
	@Autowired
	private OrderFlowManage orderFlowManage;

	@Autowired
	public void setOfm(OrderFlowManage ofm) {
		this.ofm = ofm;
	}
	
	
	//给运营发送支付成功短信,运营电话号码
	String yyPhone = JlAppResources.getProperty("notice_Phone");
		
	@RequestMapping("/payOnline.action")
	public Map<String, Object> payOnline(String XmlData,String skfsArr,HttpServletResponse response,HttpServletRequest request) throws Exception {
		cds = new DataSet(XmlData);
		String bankcard = ""; //用户名称
		String payusername = ""; //用户名称
		String userbankcustom = ""; //网银客户号
		String cardType = "";//卡类型 b2c必填 【01	借记卡（储蓄卡）接口】
		
		orderFlowManage.insertOrderPayway(skfsArr);
		String out_trade_no = cds.getField("JYDH", 0);
		String sqlzt = " SELECT distinct (select sum(t3.xsddi02*t3.xsddi05) from w_jyd t1,w_xsdd t2,w_xsdditem t3  where t1.jydh=t2.jydh and t2.xsdd01=t3.xsdd01 and t2.JYDH='"
				+ out_trade_no
				+ "') jyje"
				+ " FROM W_XSDDITEM A,W_XSDD B WHERE A.XSDD01=B.XSDD01 and B.JYDH='"
				+ out_trade_no + "'";
		Map map = queryForMap(o2o, sqlzt);
		
		String jyje = map.get("jyje").toString();
		// 付款金额
		DecimalFormat df = new DecimalFormat("0.00");
		String amount =df.format(Double.valueOf(jyje));
		
		//卖家名称
		String selSql = "SELECT zcxx02 FROM w_zcgs "
				+ "WHERE zcxx01 IN(SELECT DISTINCT(t2.ztid) FROM w_jyd t1,w_xsdd t2,w_xsdditem t3  "
				+ "WHERE t1.jydh=t2.jydh AND t2.xsdd01=t3.xsdd01 AND t2.JYDH='"+out_trade_no+"')";
		List list = queryForList(o2o, selSql);
		String selMan = this.list2Str(list);
		String lx = cds.getField("LX", 0);  //用户类型 生产42 维修44 经销43 车主24
		String userType = "";
		if("44".equals(lx)){ //b2b
			userType="1"; //企业为1
			bankcard = request.getParameter("yhkh");
			payusername = request.getParameter("yhmc");
			userbankcustom = request.getParameter("wykhh");
		}else if("24".equals(lx)){ //b2c
			userType="0"; //个人为0  
			cardType="01";
		} 
		
		//商品名称
		String goodSql = "SELECT B.spxx04 zcxx02 FROM w_xsdditem A,w_goods B,w_xsdd C" +
				" WHERE A.spxx01=B.spxx01 AND C.xsdd01=A.xsdd01 AND C.jydh='"+out_trade_no+"'";
		list = queryForList(o2o, goodSql);
		String goodName = this.list2Str(list);
		String dstbdata = this.getPayTradeSign(selMan, out_trade_no, goodName, 
				"CNY", "CNY", amount, bankcard,
				payusername, userbankcustom, cardType, userType, "");
		
		String dstbdatasign=DSDES.getBlackData(JlAppResources.getProperty("EBC_PASSWORD").getBytes(), dstbdata.getBytes("utf-8"));
		//重定向到ebc提供的银行列表页面
		response.sendRedirect(JlAppResources.getProperty("to_ebc_url")+"?"+dstbdata+"&dstbdatasign="+dstbdatasign);
		return new HashMap<String,Object>();
	}
	
	private String list2Str(List list){
		StringBuilder sb = new StringBuilder();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(0);
				String str = map.get("zcxx02").toString();
				sb.append(str);
				if(i != list.size()-1){
					sb.append(",");
				}
			}
		}
		String s = sb.toString();
		if(s.length() > 30)
			s = s.substring(0, 30);
		return s;
	}
	
	//过滤特殊字符
	public static String stringFilter(String str) throws PatternSyntaxException   {      
        String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
        Pattern p = Pattern.compile(regEx);      
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();      
     }
	
	//ebc支付及时到账异步接口
	//returncode=00&merchno=611100000310883&
	//dsorderid=CS_XS0000100990&amount=0.01&
	//orderid=201511051016386558000102&transdate=20151105&transtime=101638
	@RequestMapping("/update_notify_url.action")
	public Map<String, Object> update_notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> hm = new HashMap<String, Object>();
		String returncode = request.getParameter("returncode"); //返回的状态值
		String dsorderid = request.getParameter("dsorderid"); //返回商户的订单号或者交易单号
		String orderid = request.getParameter("orderid"); //返回银联的订单号
		
		//判断是否已经支付
		String yfkSql = "SELECT JYDH FROM W_JYD WHERE ZT=2 AND JYDH='"+dsorderid+"'";
		List yfkList = queryForList(o2o,yfkSql);
		if(yfkList != null && yfkList.size()>0){
			hm.put("is_update_success", "1");
			return hm;
		}
		
		try {
			if("00".equals(returncode)){
				//支付成功
				//更新交易单状态，为支付完成
				String jySQL = "UPDATE W_JYD SET ZT=2 WHERE JYDH='"+dsorderid+"'";
				//查询结果成功，更新支付成功标记
				execSQL(o2o, jySQL, new HashMap());
				//更新订单状态
				String sql = "SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+dsorderid+"'";
				List list = queryForList(o2o,sql);
				if(list != null && list.size()>0){
					Map map;
					for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						Map ddztMap = new HashMap();
						ddztMap.put("XSDD01",map.get("XSDD01").toString());
						ddztMap.put("trade_no", orderid);
						JSONArray ddztJson = JSONArray.fromObject(ddztMap);
						ofm.updateOrderInfo(ddztJson.toString());
						try{
							//与erp对接
							String queryzidsql = "SELECT ztid from w_xsdd where xsdd01 ='"+map.get("XSDD01").toString()+"'";
							Map ztidMap = queryForMap(o2o, queryzidsql);
							Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
							if(erpMap.get("DJLX") != null){
								if(erpMap.get("DJLX").equals("V9")){
									erpXSDD.paySucessV9XSDD(map.get("XSDD01").toString());
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				try{
					//发送短信
					if(JLTools.getCurConf(2) == 1){
						String smsSQL = "SELECT ifnull(A.XSDD21,'') SJHM,A.HBID,A.XSDD01,B.ZCXX02 "+
						                           "FROM W_XSDD A,W_ZCGS B WHERE A.HBID=B.ZCXX01 AND A.JYDH='"+dsorderid+"'";
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
						smsService.sendSms(2, dsorderid, sjhm, content, bhid);
						//给运营发送订单付款通知短信
						content = "订单提醒,单号："+xsddStr+" 已付款！请跟踪处理";
						smsService.sendSms(2, dsorderid, yyPhone, content, bhid);
						//跟卖家发送短信
						smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.ZTID,A.XSDD01,B.ZCXX02 FROM W_XSDD A,W_ZCGS B WHERE A.ZTID=B.ZCXX01 AND A.JYDH='"+dsorderid+"'";
						List dpList = queryForList(o2o,smsSQL);
						Map smsMap;
						for(int i=0;i<dpList.size();i++){
							smsMap=(Map)dpList.get(i);
							content = "尊敬的"+smsMap.get("ZCXX02").toString()+"您好，您的店铺有新增订单，单号："+smsMap.get("XSDD01").toString()+"，请您尽快发货！";
							smsService.sendSms(2, dsorderid, smsMap.get("SJHM").toString(), content, smsMap.get("ZTID").toString());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				String updateTHBStatussql="UPDATE W_DJZX t1 SET t1.w_djzx02='3' WHERE exists( select 1 from  W_XSDD t2 where t1.w_djzx01=t2.xsdd01 and  t2.JYDH='"+dsorderid+"')";
				execSQL(o2o,updateTHBStatussql,new HashMap());
			}
			hm.put("is_update_success", "1");
		} catch (Exception e) {
			//System.out.println("-----支付成功，更新状态失败----------");
			e.printStackTrace();
			hm.put("is_update_success", "0");
			//更新状态为待支付
			String updateTHBStatussql="UPDATE W_DJZX t1 SET t1.w_djzx02='3' WHERE exists( select 1 from  W_XSDD t2 where t1.w_djzx01=t2.xsdd01 and  t2.JYDH='"+dsorderid+"')";
			execSQL(o2o,updateTHBStatussql,new HashMap());
			throw new Exception(e.getMessage());
		}
		return hm;
	}
	
	/**
	 * @param merchname 卖家名称
	 * @param dsorderid  订单号
	 * @param product   商品名称
	 * @param currency  币种  默认CNY
	 * @param transcurrency  扣款币种  默认CNY
	 * @param amount  扣款金额
	 * @param bankcard  b2b银行卡号
	 * @param payusername b2b用户名
	 * @param userbankcustom  网银客户号
	 * @param cardtype  卡类型   b2c [01 借记卡 必填]
	 * @param usertype  用户类型   0 个人 1企业（b2b是1，b2c是0）
	 * @param dstburl 备用   默认""
	 * @return
	 */
    public String getPayTradeSign(String merchname,String dsorderid, String product,
			String currency, String transcurrency, String amount, String bankcard,
			String payusername,String userbankcustom, String cardtype, String usertype,
			String dstburl){
    	//参数拼接
        String parameters = "";
        parameters += "merchno=" + JlAppResources.getProperty("EBC_SHOP_ACCOUNT");//商户号
        parameters += "&merchname=" + HexConvert.toHexCode(merchname);//商户名称
        parameters += "&dsorderid=" + dsorderid;//商户订单号
        parameters += "&product=" + HexConvert.toHexCode(product);//商品名称
        parameters += "&userno=" + JlAppResources.getProperty("userno");//用户id
        parameters += "&mediumno=" + JlAppResources.getProperty("EBC_ID");//钱包id
        parameters += "&cardno=" + JlAppResources.getProperty("EBC_ACCOUNT");//卡号/电子账户/钱包账户
        parameters += "&currency=" + currency;//币种
        parameters += "&transcurrency=" + transcurrency;//扣款币种
        parameters += "&amount=" + amount;//金额
        if(usertype.equals("1")){//B2B必填
        	parameters += "&bankcard=" + bankcard;//用户账号/银行卡
            parameters += "&payusername=" + HexConvert.toHexCode(payusername);//用户名称
            parameters += "&userbankcustom=" + userbankcustom;//网银客户号
        }else{//为空值时，不做待验签字符串的拼写
        	if(bankcard!=null && !bankcard.trim().equals("")){
        		parameters += "&bankcard=" + bankcard;//用户账号/银行卡
            }
        	if(payusername!=null && !payusername.trim().equals("")){
        		parameters += "&payusername=" + payusername;//用户名称
            }
        	if(userbankcustom!=null && !userbankcustom.trim().equals("")){
        		parameters += "&userbankcustom=" + userbankcustom;//网银客户号
            }
        }
        if("0".equals(usertype)){//B2C必填
        	parameters += "&cardtype=" + cardtype;//卡类型
        }else{//为空值时，不做待签名字符串的拼写
        	if(cardtype!=null && !cardtype.trim().equals("")){
        		parameters += "&cardtype=" + cardtype;//卡类型
            }
        }
        parameters += "&usertype=" + usertype;//用户类型
        parameters += "&banktburl=" + JlAppResources.getProperty("ebc_return_url");//页面同步通知地址
        parameters += "&dsyburl=" + JlAppResources.getProperty("ebc_notify_url");//异步通知地址
        if(dstburl!=null && !dstburl.trim().equals("")){
        	parameters += "&dstburl=" + dstburl;//备用
        }
    	return parameters;
    }
}
