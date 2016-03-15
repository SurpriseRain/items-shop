package com.jlsoft.o2o.interfacepackage.V9;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.JLTools;

@Controller
public class V9THD extends JLBill{
	@Autowired
	private V9Public v9Public;
	
	
	/**
	 * @todo 创建退货单
	 * @return
	 * @throws Exception 
	 */
	public String createTHD(Map map) throws Exception{
		String sql = "";
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","POST_PFD_TH_V9");
		paramterMap.put("SysTemCon", "0");//系统内部参数0
		paramterMap.put("DistributionCode", map.get("PFD01"));//分销单号
		paramterMap.put("Order_Code",map.get("THDH").toString());//平台外部订单号，退货单号
		paramterMap.put("DepNo", "0001020101");//部门
		paramterMap.put("SHR","车福");//审核人
		paramterMap.put("remark",map.get("BZ"));//退货单备注
		//查询商品列表
		sql = "SELECT -1*A.THSL Number,A.SPJG Price,-1*(A.THSL*A.SPJG) Amount,B.ERP_SPXX01 ProductID " +
				"FROM W_THDITEM A,W_SPXXDZ B WHERE A.SPXX01=B.SPXX01 AND A.THDH='"+map.get("THDH").toString()+"'";
		Map mxMap;
		List list = queryForList(o2o,sql);
		for(int i=0;i<list.size();i++){
			mxMap = (Map)list.get(i);
			//获取分销单号和提单号
			mxMap.put("zcxx25", map.get("ZCXX25"));
			mxMap.put("xsdd01", map.get("XSDD01"));
			mxMap.put("erp_spxx01", mxMap.get("ProductID"));
			String fxdResponse = v9Public.queryFXTHDH(map, mxMap);
			JSONObject resJson = JSONObject.fromObject(fxdResponse);
			if( null != resJson && resJson.containsKey("data") ){
				JSONObject dataJson = resJson.getJSONObject("data");
				String THDH = (dataJson.containsKey("JL_OrderLineCode") ? dataJson.getString("JL_OrderLineCode") : "");//提货单号
				mxMap.put("LadingBillCode", THDH);
				mxMap.put("WhNo", map.get("ERPDZ"));//仓库代码
			}
		}
		paramterMap.put("SPLIST", list);
		//与ERP对接调用
		System.out.println("制定提货单参数："+paramterMap.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		return JLTools.sendToSync_V9(XmlData, map.get("URL").toString() + "/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 客户建档
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String createTHKHJD(Map map) throws Exception{
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","POST_AZJD_V9");
		paramterMap.put("JL_OrderType", "8");//单据类型，如零售单10
		paramterMap.put("JL_RetailCode", map.get("thPfd01"));//ERP单号，分销退单号
		paramterMap.put("Order_Code", map.get("THDH"));//外部订单号，销售单号
		paramterMap.put("DepNo", "0001020101");//销售部门
		paramterMap.put("ServiceArea", map.get("SHWD"));//服务区域
		paramterMap.put("CusterCategories", "01");//客户类型
		paramterMap.put("DeliveryMethod", "01");//送货方式
		paramterMap.put("SysTemCon", "131");//系统内部参数131
		paramterMap.put("Freight", "0");//运费，不传默认0
		paramterMap.put("SHWD", map.get("SHWD"));//网点编码,不传默认为空
		paramterMap.put("Receiver_name", map.get("LXR"));//联系人
		paramterMap.put("Receiver_phone", map.get("LXDH"));//固定电话
		paramterMap.put("Receiver_address", map.get("XXDZ"));//联系地址
		paramterMap.put("AZWD", "");//安装网点
		//与ERP对接调用
		System.out.println("客户建档："+paramterMap.toString());
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/AZJD_V9/POST_AZJD_V9.do");
	}
	
	/**
	 * @todo 创建退货入库
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String createTHRK(Map map) throws Exception{
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","FXTHRK");
		paramterMap.put("SysTemCon", "0");//系统内部参数默认0
		paramterMap.put("Order_Code", map.get("THDH"));//外部订单号
		//查询退货单明细
		String sql = "SELECT A.THDH thdh,B.SPXX01,(SELECT ERP_SPXX01 FROM W_SPXXDZ WHERE SPXX01=B.SPXX01) erp_spxx01," +
						   "(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=A.ZTID) zcxx25 " +
						   "FROM W_THD A,W_THDITEM B WHERE A.THDH=B.THDH AND A.THDH='"+map.get("THDH").toString()+"'";
		Map spMap = queryForMap(o2o,sql);
		String fxdResponse = v9Public.queryTHTDH(map, spMap);
		JSONObject resJson = JSONObject.fromObject(fxdResponse);
		if( null != resJson && resJson.containsKey("data") ){
			JSONObject dataJson = resJson.getJSONObject("data");
			String THDH = (dataJson.containsKey("JL_OrderLineCode") ? dataJson.getString("JL_OrderLineCode") : "");//提货单号
			paramterMap.put("TDH", THDH);
		}
		paramterMap.put("FHR", "00019999");//发货人
		paramterMap.put("GSXX01", "0001");//公司代码
		//与ERP对接调用
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		return JLTools.sendToSync_V9(XmlData, map.get("URL").toString() + "/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 分销单退款
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String createFXDTK(Map map) throws Exception{
		String sql = "";
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","FXDSK");
		paramterMap.put("Order_Code", map.get("THDH"));//外部订单号，退货单号
		paramterMap.put("DepNo", "0001020101");//销售部门
		paramterMap.put("JL_OrderCode", map.get("FXDH"));//金力分销单号PFD01
		paramterMap.put("CashCode", "00019999");//收款员代码
		paramterMap.put("CashName", "车福");//收款员名称
		paramterMap.put("SysTemCon", "0");//系统内部参数
		paramterMap.put("BillType", "0");//0普通发票 1增值发票
		paramterMap.put("ReMark", "");//备注
		//查询收款方式
		sql = "SELECT CASE SKFS WHEN 6 THEN '01' WHEN 8 THEN '02' WHEN 9 THEN '03' WHEN 10 THEN '04' WHEN 11 THEN '05' END PayCode," +
				"(SELECT -1*THJE FROM W_THD WHERE THDH='"+map.get("THDH")+"') PayAmount," +
				"'01' AmountCode,0 PayMark,(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=B.HBID) CusNo " +
				"FROM W_XSDDSKFS A,W_XSDD B WHERE A.XSDD01=B.XSDD01 AND A.XSDD01='"+map.get("XSDD01")+"'";
		
		List skList = queryForList(o2o,sql);
		paramterMap.put("SKLIST", skList);
		//与ERP对接调用
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
}
