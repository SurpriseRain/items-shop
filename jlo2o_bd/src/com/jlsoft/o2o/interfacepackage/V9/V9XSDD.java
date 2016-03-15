package com.jlsoft.o2o.interfacepackage.V9;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.JLTools;

@Controller
public class V9XSDD  extends JLBill{
	/**
	 * @todo 创建分销单
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String createFXD(Map map) throws Exception{
		String sql = "";
		//查询销售单主表
		sql = "SELECT DISTINCT A.ZTID,A.HBID," +
				 "(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=A.HBID) KHDM,A.XSDD01,A.XSDD02,C.ERPDZ ERPCK,IFNULL(A.XSDD07,'无') BZ " +
				 "FROM W_XSDD A,W_XSDDITEM B,CK C " +
				 "WHERE A.XSDD01=B.XSDD01 AND B.CK01=C.CK01 AND A.XSDD01='"+map.get("XSDD01").toString()+"'";
		Map xsMap = queryForMap(o2o,sql);
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","FXDZD");
		paramterMap.put("Order_Code", xsMap.get("XSDD01"));//外部订单号
		paramterMap.put("DepNo", "0001020101");//销售部门
		paramterMap.put("InvoiceType", "0");
		paramterMap.put("JL_OrderType", "0");//单据类型(0:正常批发;1:延付转批发;2:提单冲红;3:提单退货;4:商品退货;5:集团变价;6:延付商品退货冲红)
		paramterMap.put("CusNo", xsMap.get("KHDM"));//客户代码
		paramterMap.put("SaleAssistantCode", "00019999");//营业员代码
		paramterMap.put("SalesMan", "车福");//业务员名称
		paramterMap.put("PayMent", xsMap.get("XSDD02"));//分销金额
		paramterMap.put("SaleTypeNo", "02");//销售方式代码
		paramterMap.put("SysTemCon", "0");//系统内部参数
		paramterMap.put("remark", xsMap.get("BZ"));//订单备注
		//查询商品明细
		sql = "SELECT '"+xsMap.get("ERPCK")+"' WhNo, B.ERP_SPXX01 ProductID,A.XSDDI05 Number,A.XSDDI02 Price,(A.XSDDI05*A.XSDDI02) Amount,'' ProductAttribute " +
				 "FROM W_XSDDITEM A,W_SPXXDZ B WHERE A.SPXX01=B.SPXX01 AND A.XSDD01='"+map.get("XSDD01").toString()+"'";
		List spList = queryForList(o2o,sql);
		paramterMap.put("SPLIST", spList);
		//存放返利明细
		List flList = new ArrayList();
		Map flMap = new HashMap();
		flMap.put("RebateCode", "0");//返利单号
		flMap.put("RebateAmountTotal", "0");//返利金额
		flList.add(flMap);
		paramterMap.put("FLLIST", flList);
		//与ERP对接调用
		System.out.println("制定分销单："+paramterMap.toString());
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 创建分销单收款
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String createFXDSK(Map map) throws Exception{
		String sql = "";
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","FXDSK");
		paramterMap.put("Order_Code", map.get("XSDD01"));//外部订单号
		paramterMap.put("DepNo", "0001020101");//销售部门
		paramterMap.put("JL_OrderCode", map.get("FXDH"));//金力分销单号
		paramterMap.put("CashCode", "00019999");//收款员代码
		paramterMap.put("CashName", "车福");//收款员名称
		paramterMap.put("SysTemCon", "0");//系统内部参数
		paramterMap.put("BillType", "0");//0普通发票 1增值发票
		paramterMap.put("ReMark", "");//备注
		//查询收款方式
		sql = "SELECT CASE SKFS WHEN 6 THEN '01' WHEN 8 THEN '02' WHEN 9 THEN '03' WHEN 10 THEN '04' WHEN 11 THEN '05' END PayCode,ZFJE PayAmount," +
				"'01' AmountCode,0 PayMark,(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=B.HBID) CusNo " +
				"FROM W_XSDDSKFS A,W_XSDD B WHERE A.XSDD01=B.XSDD01 AND A.XSDD01='"+map.get("XSDD01")+"'";
		List skList = queryForList(o2o,sql);
		paramterMap.put("SKLIST", skList);
		//与ERP对接调用
		System.out.println("制定分销收款单："+paramterMap.toString());
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 客户建档
	 * @param map
	 * @return
	 */
	public String createKHJD(Map map) throws Exception{
		//查询收货人信息
		String sql = "SELECT concat(B.ZCXX02,'(',XSDD19,')') SHR," +
				           "IFNULL((SELECT SHWD FROM W_DQWD WHERE DQXX01=A.STREET),'00010116') SHWD FROM W_XSDD A,W_ZCGS B WHERE A.ZTID=B.ZCXX01 AND A.XSDD01='"+map.get("XSDD01")+"'";
		Map mapXSDD = queryForMap(o2o,sql);
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","POST_AZJD_V9");
		paramterMap.put("JL_OrderType", "8");//单据类型，如零售单10
		paramterMap.put("JL_RetailCode", map.get("FXDH"));//ERP单号，分销单号
		paramterMap.put("Order_Code", map.get("XSDD01"));//外部订单号，销售单号
		paramterMap.put("DepNo", "0001020101");//销售部门
		paramterMap.put("ServiceArea", mapXSDD.get("SHWD"));//服务区域
		paramterMap.put("CusterCategories", "01");//客户类型
		paramterMap.put("DeliveryMethod", "01");//送货方式
		paramterMap.put("SysTemCon", "131");//系统内部参数131
		paramterMap.put("Freight", "0");//运费，不传默认0
		paramterMap.put("SHWD", mapXSDD.get("SHWD"));//网点编码,不传默认为空
		paramterMap.put("Receiver_name", mapXSDD.get("SHR"));//联系人
		paramterMap.put("Receiver_phone", map.get("SHRDH"));//固定电话
		paramterMap.put("Receiver_address", map.get("SHDZ"));//联系地址
		paramterMap.put("AZWD", "");//安装网点
		//与ERP对接调用
		System.out.println("客户建档："+paramterMap.toString());
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/AZJD_V9/POST_AZJD_V9.do");
	}
	
	/**
	 * @todo 创建分销收付款
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String createFXSFK(Map map) throws Exception{
		String sql = "";
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","FXSFK");
		paramterMap.put("Order_Code", map.get("XSDD01"));//外部订单号
		paramterMap.put("SysTemCon", "");//系统内部参数
		paramterMap.put("CashCode", "00019999");//收款员代码
		paramterMap.put("CashName", "");//收款员名称
		paramterMap.put("SaleAssistantName", "");//营业员名称
		paramterMap.put("OperateCode", "00019999");//操作员代码
		paramterMap.put("DepNo", "0001020101");//销售部门
		paramterMap.put("BankAccount", "");//银行账号
		paramterMap.put("CusNo", "");//客户代码
		paramterMap.put("Flag", "");//预收冲应收标记
		paramterMap.put("Remarks", "");//备注
		//查询收款方式
		sql = "SELECT '' PayCode,'' DepNo,ZFJE PayAmount,0 PayMark FROM W_XSDDSKFS WHERE XSDD01='"+map.get("XSDD01")+"'";
		List skList = queryForList(o2o,sql);
		paramterMap.put("SKLIST", skList);
		//与ERP对接调用
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
}
