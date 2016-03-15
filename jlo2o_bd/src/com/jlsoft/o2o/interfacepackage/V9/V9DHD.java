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
public class V9DHD  extends JLBill{
	@Autowired
	private V9Public v9Public;
	private static final String subDJPath = "/PubInteface/POST_SCM_DJ.do";
	
	/**
	 * @todo 创建订货单
	 * @param erpMap
	 * @param CKDH 出库单号
	 * @return
	 * @throws Exception 
	 */
	public String createDHD(Map erpMap, String CKDH) throws Exception{
		// 获取交货时限起、交货时限止
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		Calendar cal = Calendar.getInstance(timeZone);
		String TimeBegin = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 15);
		String TimeEnd = sdf.format(cal.getTime());
		// 查询出库单
		String sqlRKD = "SELECT k.CKDH, c.ERPDZ, k.ZCXX01, k.CKR, "
						+ "(SELECT SUM(CASE WHEN dm.CKSL IS NULL THEN 0 ELSE dm.CKSL END) FROM W_CKDITEM dm WHERE dm.CKDH = k.CKDH) AS num, "
						+ "(SELECT SUM(CASE WHEN sx.spjgb05 IS NULL THEN 0.0000 ELSE sx.spjgb05 END) FROM W_CKDITEM dm LEFT JOIN w_spjgb sx ON dm.spxx01 = sx.spxx01 WHERE dm.CKDH = k.CKDH) AS money "
						+ "FROM W_CKD k "
						+ "LEFT JOIN CK c ON c.ck01 = k.SHCK "
						+ "LEFT JOIN W_ZCXX z ON k.ZCXX01 = z.zcxx01 "
						+ "WHERE k.CKDH = '" + CKDH + "'";
		Map rkdMap = queryForMap(o2o, sqlRKD);
		// 获取合同号
		String companyNo = String.valueOf(rkdMap.get("ZCXX01"));
		String htSQL = "SELECT ZCXX62 CGHT,ZCXX63 HZFS FROM W_ZCXX WHERE ZCXX01='"+rkdMap.get("ZCXX01").toString()+"'";
		Map htMap =  queryForMap(o2o,htSQL);
		String contractNo = htMap.get("CGHT").toString();
		// 订单数据赋值
		JSONObject json = new JSONObject();
		json.put("UserName", erpMap.get("UserName"));
		json.put("PassWord", erpMap.get("PassWord"));
		json.put("TYPE_DJ", "POST_DHD_ZD_V9");
		json.put("Order_Code", rkdMap.get("CKDH"));// 外部订单号
		json.put("SysTemCon", "23");// 系统内部参数(23为自动审核订单)
		json.put("ContractNumber", contractNo);// 合同号
		json.put("TimeBegin", TimeBegin);// 交货时限起
		json.put("TimeEnd", TimeEnd);// 交货时限止
		json.put("SaesmanCode", "0001999");// 业务员代码
		json.put("SaesmanName", "车福");// 业务员名称
		json.put("Amount", rkdMap.get("money"));// 订货总金额
		json.put("Numbers", rkdMap.get("num"));// 货总数量
		json.put("TouchingName", rkdMap.get("CKR"));// 制单人
		json.put("CompanyCode", "0001");// 公司信息
		json.put("WhNo", rkdMap.get("ERPDZ"));// 仓库代码
		// 查询商品列表
		JSONArray spList = new JSONArray();
		String sqlSP = "SELECT s.erp_spxx01, i.CKSL FROM W_CKDITEM i "
						+ "LEFT JOIN W_SPXXDZ s ON s.SPXX01 = i.SPXX01 "
						+ "WHERE i.CKDH = '" + CKDH + "'";
		List itemList = queryForList(o2o, sqlSP);
		int itemLen = (null != itemList ? itemList.size() : 0);
		for(int i=0;i<itemLen;i++){
			Map itemMap = (Map)itemList.get(i);
			// 商品赋值
			JSONObject spJson = new JSONObject();
			spJson.put("ProductID", itemMap.get("erp_spxx01"));// 商品内码
			spJson.put("Numbers", itemMap.get("CKSL"));// 数量
			spJson.put("ArrivalTime", TimeEnd);// 交货日期
			spList.add(spJson);
		}
		json.put("SPLIST", spList);
		//与ERP对接调用
		System.out.println("制定订货单参数："+json.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
		return JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subDJPath);
	}
	
}