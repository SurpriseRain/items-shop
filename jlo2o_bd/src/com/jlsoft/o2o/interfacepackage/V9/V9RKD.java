package com.jlsoft.o2o.interfacepackage.V9;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.JLTools;

@Controller
public class V9RKD extends JLBill {
	@Autowired
	private V9Public v9Public;
	private static final String subDJPath = "/PubInteface/POST_SCM_DJ.do";
	
	/***
	 * 创建入库单
	 * @param erpMap
	 * @param RKDH入库单号
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String createRKD(Map erpMap, String CKDH,String RKDH) throws Exception{
		// 获取ERP订货单号
		String DJBH = v9Public.queryDHDH(erpMap, CKDH);
		// 获取生成日期、失效日期
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		Calendar cal = Calendar.getInstance(timeZone);
		String curDate = sdfDate.format(cal.getTime());
		cal.add(Calendar.DATE, 15);
		String invalidDate = sdfDate.format(cal.getTime());
		// 订单数据赋值
		JSONObject json = new JSONObject();
		json.put("UserName", erpMap.get("UserName"));
		json.put("PassWord", erpMap.get("PassWord"));
		json.put("TYPE_DJ", "POST_DHD_RK_V9");
		json.put("SysTemCon", "23");// 系统内部参数
		json.put("CompanyCode", "0001");// 公司代码（固定值0101）
		json.put("Order_Code", RKDH);// 外部单号
		json.put("DJBH", DJBH);// ERP单号/订货单号
		json.put("SHR", "00019999");// 收货人
		json.put("YSR", "00019999");// 验收人
		//查询商品列表
		String sqlph = "SELECT m.RKDH, m.RKSL, z.erp_spxx01 "
						+ "FROM W_RKDITEM m "
						+ "LEFT JOIN W_SPXXDZ z ON m.spxx01 = z.spxx01 "
						+ "WHERE m.RKDH = '" + RKDH + "'";
		
		List phList = queryForList(o2o, sqlph);
		int phLen = (null != phList ? phList.size() : 0);
		JSONArray spArr = new JSONArray();
		JSONArray phArr = new JSONArray();
		for(int i=0;i<phLen;i++){
			Map phMap = (Map)phList.get(i);
			// SPLIST赋值
			JSONObject spJson = new JSONObject();
			spJson.put("ProductID", phMap.get("erp_spxx01"));// 商品内码
			spJson.put("num", phMap.get("RKSL"));// 商品数量
			spArr.add(spJson);
			// PHLIST赋值
			JSONObject phJson = new JSONObject();
			phJson.put("ProductID", phMap.get("erp_spxx01"));// 商品内码
			phJson.put("num", phMap.get("RKSL"));// 商品数量
			phJson.put("SCRQ", curDate);// 生成日期
			phJson.put("SXRQ", invalidDate);// 失效日期
			phJson.put("PH", "1");// 批号
		}
		json.put("SPLIST", spArr);
		json.put("PHLIST", phArr);
		//与ERP对接调用
		System.out.println("入库对接参数："+json.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
		return JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subDJPath);
	}
	
}
