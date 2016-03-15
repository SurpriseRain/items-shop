package com.jlsoft.o2o.interfacepackage.V9;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;

import com.jlsoft.framework.JLBill;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;

@Controller
public class V9FH extends JLBill {
	private static final String subDJPath = "/PubInteface/POST_SCM_DJ.do";
	@Autowired
	private V9Public v9Public;
	@Autowired
	private PubService pubService;
	@Autowired
	private ManageLogs manageLogs;
	
	
	/***
	 * 分销单发货
	 * @param erpMap
	 * @return xsdd01 订单编号
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<String> createFH(Map erpMap, String xsdd01) throws Exception{
		List<String> result = new ArrayList<String>();
		Object UserName = erpMap.get("UserName");
		Object PassWord = erpMap.get("PassWord");
		String url = erpMap.get("URL").toString() + subDJPath;
		// 查询分销单发货数据
		String sql = "SELECT d.xsdd01,m.xsddi01,z.erp_spxx01, x.zcxx25, "
					+ "(CASE WHEN m.xsddi05 IS NULL THEN 0 ELSE m.xsddi05 END) AS num,d.XSDD41 "
					+ "FROM W_XSDD d "
					+ "LEFT JOIN W_XSDDITEM m ON d.xsdd01 = m.xsdd01 "
					+ "LEFT JOIN W_SPXXDZ z ON m.spxx01 = z.spxx01 "
					// modify 2016.01.09.修改zcxx25的关联查询
					//			+ "LEFT JOIN w_zcxx x ON d.ztid = x.zcxx01 "
					+ " LEFT JOIN w_spgl y ON m.spxx01 = y.spxx01 "
					+ " LEFT JOIN w_zcxx x ON y.zcxx01 = x.zcxx01 "
					+ "WHERE d.xsdd01 = '" + xsdd01 + "'";
		List list = queryForList(o2o, sql);
		int len = (null != list && list.size() > 0 ? list.size() : 0);
		for( int i = 0; i < len; i++){
			Map map = (Map) list.get(i);
			// 获取单据编号、提单号
			String fxdResponse = v9Public.queryFXTHDH(erpMap, map);
			JSONObject resJson = JSONObject.fromObject(fxdResponse);
			if( null != resJson && resJson.containsKey("data") ){
				JSONObject dataJson = resJson.getJSONObject("data");
				String Order_Code = ( dataJson.containsKey("JL_OrderCode") ? dataJson.getString("JL_OrderCode") : "" );// 单据编号
				String TDH = ( dataJson.containsKey("JL_OrderLineCode") ? dataJson.getString("JL_OrderLineCode") : "" );// 提单号
				
				//分销单数据赋值
				JSONObject json = new JSONObject();
				json.put("UserName", UserName);
				json.put("PassWord", PassWord);
				json.put("TYPE_DJ", "POST_PFD_FH_V9");
				json.put("SysTemCon", "23");// 系统内部参数
				json.put("Order_Code", Order_Code);// 单据编号（WMS流水号）
				json.put("DJLX", "8");// 单据类型(0:正常批发;1:延付转批发;2:提单冲红;3:提单退货;4:商品退货;5:集团变价;6:延付商品退货冲红)
				json.put("TDH", TDH);// 提单号
				json.put("GSXX01", "0001");// 公司代码
				json.put("FHSL", map.get("num"));// 发货数量
				json.put("FHR", "00019999");// 收货人
				json.put("DDBZZL", map.get("XSDD41"));//订单包装重量
				
				//与ERP对接调用
				System.out.println("-->ERP createFH request: " + json.toString());
				System.out.println("-->ERP createFH url: " + url);
				String XmlData = "XmlData=" + URLEncoder.encode(json.toString(), "utf-8");
				String response = JLTools.sendToSync_V9(XmlData, url);
				System.out.println("-->ERP createFH response: " + response);
				JSONObject fhJson = JSONObject.fromObject(response);
				String resResult = "0";
				String resMsg = "";
				if( null != fhJson && fhJson.containsKey("data") ){
					JSONObject dJson = fhJson.getJSONObject("data");
					resResult = dJson.getString("JL_State");
					resMsg = dJson.getString("JL_ERR");
				}
				// 调用结果失败时抛出异常
				if( !"1".equals(resResult) ){
					//写入W_WTDJ
					pubService.insertW_WTDJ(8,map.get("xsddi01").toString(),0,0);
					//写入错误日志
					manageLogs.writeLogs(8,map.get("xsddi01").toString(),"","车福",0,"分销单生成发货提单失败"+resMsg);
					//throw new Exception("ERP createFH response ERROR: " + resMsg);
				}
				result.add(response);
			}else{
				//写入W_WTDJ
				pubService.insertW_WTDJ(8,map.get("xsddi01").toString(),0,0);
				//写入错误日志
				manageLogs.writeLogs(8,map.get("xsddi01").toString(),"","车福",0,"分销单发货找不到提单");
				//throw new Exception("ERP queryFXTHDH response data not exists.");
			}
		}
		return result;
	}
	
}
