package com.jlsoft.o2o.interfacepackage.V7;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.JLTools;

@Controller
public class V7Public extends JLBill {
	private static final String subSelectPath = "/jlquery/selectSCM.do";
	private static final String subDJPath = "/PubInteface/POST_SCM_DJ.do";
	
	/**
	 * 获取合同号
	 * @param erpMap
	 * @param companyNo 公司编码
	 * @return
	 * @throws Exception 
	 */
	public Map queryGHHT(Map erpMap, String companyNo) throws Exception{
		Map returnMap = new HashMap();
		try{
			String contractNo = "";
			String sql = "SELECT x.zcxx25 FROM w_zcxx x WHERE x.zcxx01 = '" + companyNo + "'";
			Map map = queryForMap(o2o, sql);
			if( null != map && null != map.get("zcxx25") ){
				// 订单数据赋值
				JSONObject json = new JSONObject();
				json.put("UserName", erpMap.get("UserName"));
				json.put("PassWord", erpMap.get("PassWord"));
				json.put("PI_USERNAME", "00019999");
				json.put("QryType", "Report");
				json.put("sqlid", "V9.Get_GHHT_QFY");
				json.put("dataType", "Json");
				json.put("wldw01", map.get("zcxx25"));
				json.put("gsxx01", "0001");
				json.put("PAGESIZE", "1");
				//与ERP对接调用
				String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
				String response = JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subSelectPath);
				JSONObject resJson = JSONObject.fromObject(response);
				if( null != resJson && resJson.containsKey("data") ){
					JSONArray dataArr = resJson.getJSONArray("data");
					if( null != dataArr && dataArr.size() > 0 ){
						JSONObject dataJson = (JSONObject)dataArr.get(0);
						if( dataJson.containsKey("GHHT01") ){
							//contractNo = dataJson.getString("GHHT01");
							returnMap.put("GHHT01", dataJson.getString("GHHT01"));
							returnMap.put("BM01", dataJson.getString("BM01"));
							returnMap.put("HZFS01", dataJson.getString("HZFS01"));
						}
					}
				}
			}
			return returnMap;
		}catch(Exception ex){
			throw new Exception("ERP queryGHHT ERROR: " + ex.getMessage());
		}
	}

	/***
	 * 订货单查询（根据入库单号查询ERP订货单号）
	 * @param erpMap
	 * @param RKDH 入库单号
	 * @return
	 * @throws Exception
	 */
	public String queryDHDH(Map erpMap, String RKDH) throws Exception {
		try{
			String JLOrderGoods = "";
			// 订单数据赋值
			JSONObject json = new JSONObject();
			json.put("UserName", erpMap.get("UserName"));
			json.put("PassWord", erpMap.get("PassWord"));
			json.put("TYPE_DJ", "POST_DHD_CX_V9");
			json.put("Order_Code", RKDH);
			json.put("CompanyCode", "0001");
			//与ERP对接调用
			String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
			String response = JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subDJPath);
			System.out.println("response:"+response);
			JSONObject resJson = JSONObject.fromObject(response);
			if(null != resJson && resJson.containsKey("data")){
				JSONObject dataJson = resJson.getJSONObject("data");
				if( dataJson.containsKey("JL_OrderCode")){
					JLOrderGoods = dataJson.getString("JL_OrderCode");
				}
			}
			System.out.println("订货单号查询结果："+JLOrderGoods);
			return JLOrderGoods;
		}catch(Exception ex){
			throw new Exception("ERP queryRKD ERROR: " + ex.getMessage());
		}
	}
	
	/**
	 * 分销单查询（查询分销单号、提货单号）
	 * @param erpMap
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryFXTHDH(Map erpMap, Map map) throws Exception {
		try{
			// 订单数据赋值
			JSONObject json = new JSONObject();
			json.put("UserName", erpMap.get("UserName"));
			json.put("PassWord", erpMap.get("PassWord"));
			json.put("TYPE_DJ", "POST_FXD_CX_V9");
			json.put("Order_Code", map.get("xsdd01"));
			json.put("CompanyCode", "0001");
			json.put("WLDW01", map.get("zcxx25"));
			json.put("SPXX01", map.get("erp_spxx01"));
			//与ERP对接调用
			String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
			String response = JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subDJPath);
			System.out.println("参数："+json.toString());
			System.out.println("返回值："+response);
			return response;
		}catch(Exception ex){
			throw new Exception("ERP queryFXTHDH ERROR: " + ex.getMessage());
		}
	}
	
	/**
	 * @todo 查询退货提单号
	 * @param erpMap
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryTHTDH(Map erpMap, Map map) throws Exception {
		try{
			// 订单数据赋值
			JSONObject json = new JSONObject();
			json.put("UserName", erpMap.get("UserName"));
			json.put("PassWord", erpMap.get("PassWord"));
			json.put("TYPE_DJ", "POST_FXD_CX_V9");
			json.put("Order_Code", map.get("thdh"));
			json.put("CompanyCode", "0001");
			json.put("WLDW01", map.get("zcxx25"));
			json.put("SPXX01", map.get("erp_spxx01"));
			//与ERP对接调用
			String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
			String response = JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subDJPath);
			System.out.println("参数："+json.toString());
			System.out.println("返回值："+response);
			return response;
		}catch(Exception ex){
			throw new Exception("ERP queryFXTHDH ERROR: " + ex.getMessage());
		}
	}
	
	/**
	 * @todo 查询退货提单号
	 * @param erpMap
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String queryFHTDH(Map erpMap, Map map) throws Exception{
		JSONObject json = new JSONObject();
		json.put("UserName", erpMap.get("UserName"));
		json.put("PassWord", erpMap.get("PassWord"));
		json.put("TYPE_DJ", "Get_PFDTHD_QFY");
		json.put("Order_Code", map.get("THDH"));
		json.put("CompanyCode", "0001");
		json.put("WLDW01", map.get("zcxx25"));
		json.put("SPXX01", map.get("erp_spxx01"));
		//与ERP对接调用
		String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
		return JLTools.sendToSync_V9(XmlData, erpMap.get("URL").toString() + subDJPath);
	}
	
}
