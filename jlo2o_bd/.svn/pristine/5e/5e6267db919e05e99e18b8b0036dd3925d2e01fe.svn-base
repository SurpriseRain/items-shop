package com.jlsoft.o2o.interfacepackage.V7;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.JLTools;


@Controller
public class V7LSD extends JLBill{
	@Autowired
	private V7Public v7Public;
	/************
	 * 
	 * @param 创建零售单
	 * @return
	 * @throws Exception
	 */
	public String createLSD(Map map) throws Exception{
		String sql = "";
		//查询销售单主表
		sql = "SELECT A.ZTID,A.HBID,(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=A.HBID) KHDM," +
				 "A.XSDD01,A.XSDD02,C.ERPDZ ERPCK,IFNULL(A.XSDD30,0) IntergalRate " +
				 "FROM W_XSDD A,W_DQCK B,CK C WHERE A.CITY=B.DQXX01 AND B.CK01=C.CK01 AND A.XSDD01='"+map.get("XSDD01").toString()+"'";
		Map xsMap = queryForMap(o2o,sql);
		
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", map.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("TYPE_DJ","Post_Retail");//获取类型/Y
		paramterMap.put("Order_Code",map.get("XSDD01"));//外部订单号/N
		//paramterMap.put("Vip_Code","WP001");//ERP VIP卡号/N
		paramterMap.put("IntergalRate", map.get("IntergalRate"));//积分率/Y
		//paramterMap.put("TimeStamp",map.get("TimeStamp").toString());//下单时间（时间戳）/N
		//paramterMap.put("ComInvoice", map.get("ComInvoice"));//发票抬头/N
		paramterMap.put("DepNo","000103010101");//销售部门/Y
		paramterMap.put("SaleAssistantCode","00019999");//营业员代码/Y
		paramterMap.put("SaleAssistantName","沈阳兴隆大家庭");//营业员名称/Y
		paramterMap.put("CashCode","00019999");//收款员代码/Y
		paramterMap.put("CaseName","沈阳兴隆大家庭");//收银员名称/Y
		paramterMap.put("PayMent",xsMap.get("XSDD02"));//交易总金额/Y
		paramterMap.put("SaleTypeNo","02");//销售方式/Y
		paramterMap.put("SysTemCon","0");//系统内部参数/Y
		paramterMap.put("SHWD","000116");//送货网点/Y
		paramterMap.put("DeliveryMethod","01");//送货方式/Y
		paramterMap.put("CusterCategories","01");//客户类型/Y
		paramterMap.put("ServiceArea","0001");//服务区域/Y
		
		/*-----------------------开始--------------------------
		paramterMap.put("SPLIST",map.get("SPLIST"));//JSON[]/Y
		paramterMap.put("ProductID",map.get("ProductID"));//商品内码/Y
		paramterMap.put("SN",map.get("SN"));//串码/N
		paramterMap.put("Number",map.get("Number"));//商品数量/Y
		paramterMap.put("Price",map.get("Price"));//商品单价（扣除折扣后）/Y
		paramterMap.put("OldPrice",map.get("OldPrice"));//原单价（无会员卡时等于price）/Y
		paramterMap.put("Amount",map.get("Amount"));//商品金额/Y
		paramterMap.put("WhNo",map.get("WhNo"));//仓库代码/Y
		paramterMap.put("isBetray",map.get("isBetray"));//负卖标记（1不允许 0 允许）/Y
		-------------------------结束---------------------------*/

		/*-----------------------开始--------------------------
		paramterMap.put("SKLIST",map.get("SKLIST"));//JSON[]/Y二级节点
		paramterMap.put("PayCode",map.get("PayCode"));//收款方式代码/Y
		paramterMap.put("PayAmount",map.get("PayAmount"));//收款金额/Y
		paramterMap.put("AmountCode",map.get("AmountCode"));//金额方式/Y
		paramterMap.put("ForeignCurr",map.get("ForeignCurr"));//外币编码/Y
		-------------------------结束---------------------------*/
		
		/*-----------------------开始--------------------------
		paramterMap.put("CUSINFO",map.get("CUSINFO"));//JSON/Y
		paramterMap.put("Name",map.get("Name"));//收货人/Y
		paramterMap.put("Addr",map.get("Addr"));//收货地址/Y
		paramterMap.put("telephone",map.get("telephone"));//电话/Y
		paramterMap.put("mobile",map.get("mobile"));//手机/Y
		paramterMap.put("DeliveryRem",map.get("DeliveryRem"));//送货要求/N
		paramterMap.put("Remarks","电商");//备注/N
		*/
		//-------------------------结束---------------------------
		//查询商品明细
		//SPLIST":[{"ProductID":"1","Number":"1","Price":"100.0000","Amount":"100.0000","WhNo":"TN010101","isBetray":"1"}]
		sql = "SELECT '"+xsMap.get("ERPCK")+"' WhNo, B.ERP_SPXX01 ProductID,A.XSDDI05 Number,A.XSDDI02 Price,A.XSDDI02 OldPrice,(A.XSDDI05*A.XSDDI02) Amount,'1' isBetray " +
				 "FROM W_XSDDITEM A,W_SPXXDZ B WHERE A.SPXX01=B.SPXX01 AND A.XSDD01='"+map.get("XSDD01").toString()+"'";
		List spList = queryForList(o2o,sql);
		paramterMap.put("SPLIST", spList);
		
		//查询收款方式
		sql = "SELECT CASE SKFS WHEN 6 THEN '15' WHEN 8 THEN '3' END PayCode,ZFJE PayAmount," +
						"'01' AmountCode,'01' ForeignCurr " +
						"FROM W_XSDDSKFS A,W_XSDD B WHERE A.XSDD01=B.XSDD01 AND A.XSDD01='"+map.get("XSDD01")+"'";
		List skList = queryForList(o2o,sql);
		paramterMap.put("SKLIST", skList);	
		
		//查收货地址
		sql = "SELECT A.xsdd19 Name,A.xsdd20 Addr,'' telephone,xsdd21 mobile,xsdd07 DeliveryRem,xsdd27 Remarks FROM W_XSDD A where A.XSDD01='"+map.get("XSDD01").toString()+"'";
		List cusList = queryForList(o2o,sql);
		paramterMap.put("CUSINFO",cusList);
		/*//存放返利明细
		List flList = new ArrayList();
		Map flMap = new HashMap();
		flMap.put("RebateCode", "0");//返利单号
		flMap.put("RebateAmountTotal", "0");//返利金额
		flList.add(flMap);
		paramterMap.put("FLLIST", flList);*/		
		//与ERP对接调用
		System.out.println("制定零售单参数："+paramterMap.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		//map.get("URL").toString()
		return JLTools.sendToSync_V7(XmlData.toString(), map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	/**
	 * @todo 创建零售退货审批单
	 * @return
	 * @throws Exception 
	 */
	public String createLSTHSPD(Map map) throws Exception{
		String sql = "";
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", map.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("TYPE_DJ", map.get("TYPE_DJ"));//获取类型/Y
		//paramterMap.put("SIGNXML", map.get("SIGNXML"));//JSON/N
		//paramterMap.put("Parms", map.get("Parms"));//加密的字段多个中间用#号隔开/N
		//paramterMap.put("Sign",map.get("Sign"));//约定key后的MD5/N
		//paramterMap.put("JSONXML", map.get("JSONXML"));//JSON/Y
		paramterMap.put("Order_Code",map.get("Order_Code"));//外部订单号/Y
		paramterMap.put("JL_RetailCode",map.get("JL_RetailCode"));//ERP零售单单号/Y
		paramterMap.put("DepNo","000103010101");//销售部门/Y
		paramterMap.put("TotalAmount",map.get("TotalAmount"));//退货总金额/Y
		paramterMap.put("SysTemCon",map.get("SysTemCon"));//系统内部参数/Y
		ArrayList SPLIST=new ArrayList();
		JSONObject json = new JSONObject();//二级节点
		json.put("ProductID",map.get("ProductID"));//ERP商品内码/Y
		json.put("Number",map.get("Number"));//商品数量(负数)/Y
		json.put("Price",map.get("Price"));//商品单价/Y
		json.put("Amount",map.get("Amount"));//商品金额(负数)/Y
		SPLIST.add(json);
		paramterMap.put("SPLIST",SPLIST);//JSON[]/Y
		/*//查询商品列表
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
			String fxdResponse = v7Public.queryFXTHDH(map, mxMap);
			JSONObject resJson = JSONObject.fromObject(fxdResponse);
			if( null != resJson && resJson.containsKey("data") ){
				JSONObject dataJson = resJson.getJSONObject("data");
				String THDH = (dataJson.containsKey("JL_OrderLineCode") ? dataJson.getString("JL_OrderLineCode") : "");//提货单号
				mxMap.put("LadingBillCode", THDH);
				mxMap.put("WhNo", map.get("ERPDZ"));//仓库代码
			}
		}
		paramterMap.put("SPLIST", list);*/
		//与ERP对接调用
		System.out.println("制定零售退货单参数："+paramterMap.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		return JLTools.sendToSync_V7(XmlData, map.get("URL").toString()+ "/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 创建零售退货退款审批单
	 * @return
	 * @throws Exception 
	 */
	public String createLSTHTKSPD(Map map) throws Exception{
		String sql = "";
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", map.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("TYPE_DJ",  "Post_CancelRetailConfirmation");//获取类型/Y
		paramterMap.put("Order_Code", map.get("Order_Code"));//外部订单号/Y
		paramterMap.put("SysTemCon", "0");//系统内部参数/Y
		paramterMap.put("DepNo","000103010101");//销售部门/Y
		paramterMap.put("CashCode","00019999");//收款员代码/Y
		paramterMap.put("CaseName","沈阳兴隆大家庭");//收银员名称/Y
		paramterMap.put("JL_CanRetailCode", map.get("JL_CanRetailCode"));
		/*-----------------------开始--------------------------
		paramterMap.put("SKLIST",map.get("SPLIST"));//JSON[]/Y
		paramterMap.put("PayCode",map.get("PayCode"));//收款方式代码/Y
		paramterMap.put("PayAmount",map.get("PayAmount"));//收款金额/Y
		paramterMap.put("AmountCode",map.get("AmountCode"));//金额方式/Y
		paramterMap.put("ForeignCurr",map.get("ForeignCurr"));//外币编码/Y
		-------------------------结束-------------------------*/
		//查询商品列表
		sql = "SELECT CASE D.SKFS WHEN 6 THEN '15' WHEN 8 THEN '3' END PayCode,0 AmountCode,-1*(A.THSL*A.SPJG) PayAmount,0 ForeignCurr " +
				"FROM W_THDITEM A,W_THD B,W_SPXXDZ C,W_XSDDSKFS D WHERE A.THDH=B.THDH AND B.XSDD01=D.XSDD01 AND A.SPXX01=C.SPXX01 AND A.THDH='"+map.get("THDH").toString()+"'";
		Map mxMap;
		List list = queryForList(o2o,sql);
		paramterMap.put("SKLIST", list);
		
		/*
		for(int i=0;i<list.size();i++){
			mxMap = (Map)list.get(i);
			//获取分销单号和提单号
			mxMap.put("zcxx25", map.get("ZCXX25"));
			mxMap.put("xsdd01", map.get("XSDD01"));
			mxMap.put("erp_spxx01", mxMap.get("ProductID"));
			String fxdResponse = v7Public.queryFXTHDH(map, mxMap);
			JSONObject resJson = JSONObject.fromObject(fxdResponse);
			if( null != resJson && resJson.containsKey("data") ){
				JSONObject dataJson = resJson.getJSONObject("data");
				String THDH = (dataJson.containsKey("JL_OrderLineCode") ? dataJson.getString("JL_OrderLineCode") : "");//提货单号
				mxMap.put("LadingBillCode", THDH);
				mxMap.put("WhNo", map.get("ERPDZ"));//仓库代码
			}
		}
		paramterMap.put("SPLIST", list);*/
		//与ERP对接调用
		System.out.println("制定零售退货单参数："+paramterMap.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		return JLTools.sendToSync_V9(XmlData, map.get("URL").toString() + "/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 零售客户建档
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String createLSKHJD(Map map) throws Exception{
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", map.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("TYPE_DJ", "Post_CreateRecord");//获取类型/Y
		paramterMap.put("JL_OrderType", "10");//单据类型，如零售单10/Y
		paramterMap.put("JL_RetailCode", map.get("JL_RetailCode"));//ERP零售单号/Y
		paramterMap.put("Order_Code", map.get("Order_Code"));//外部订单号，销售单号/Y
		paramterMap.put("DepNo", "000103010101");//销售部门/Y
		paramterMap.put("SHWD", "000116");//送货网点/Y
		paramterMap.put("ServiceArea", "0001");//服务区域/Y
		paramterMap.put("CusterCategories", "01");//客户类型/Y
		paramterMap.put("DeliveryMethod", "06");//送货方式/Y
		paramterMap.put("SysTemCon", "23");//系统内部参数131/Y
		paramterMap.put("Freight", "0");//运费，不传默认0/Y
		paramterMap.put("Receiver_phone", map.get("Receiver_phone"));//固定电话
		paramterMap.put("ADD_SHENG", map.get("ADD_SHENG"));//省
		paramterMap.put("ADD_SHI", map.get("ADD_SHI"));//市
		paramterMap.put("ADD_QU", map.get("ADD_QU"));//区/县
		paramterMap.put("ADD_XQ", map.get("ADD_XQ"));//小区/村/大队
		paramterMap.put("ADD_LU", "");//路/乡/镇
		paramterMap.put("ADD_HAO", "");//号/弄
		paramterMap.put("ADD_LC", "");//单元
		paramterMap.put("SHSJ_S", map.get("SHSJ_S"));//送货时间起
		paramterMap.put("SHSJ_E", map.get("SHSJ_E"));//送货时间止
		paramterMap.put("SHQYDM", map.get("SHQYDM"));//唯智送货区域代码
		paramterMap.put("SHQYMC", map.get("SHQYMC"));//唯智送货区域名称
		paramterMap.put("ADD_C", "");//楼层
		paramterMap.put("SJCKMC", map.get("SJCKMC"));//唯智时间窗口名称
		paramterMap.put("SMSPOD", "");//唯智短信回执码
		paramterMap.put("KHZL16", map.get("KHZL16"));//送货时间
		//与ERP对接调用
		System.out.println("零售客户建档："+paramterMap.toString());
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V7(XmlData.toString(), map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 查询零售单发货状态接口
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String createLSDFH(Map map) throws Exception{
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", map.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("Type", "Get_SYDJD_FHZT");//获取类型/Y
		paramterMap.put("Code", map.get("PFD01"));//零售单号/N
		paramterMap.put("SaleDep", "0001");//公司代码/Y
		//与ERP对接调用
		System.out.println("零售单发货接口："+paramterMap.toString());
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V7(XmlData,map.get("URL").toString()+"/JLQueryServlet_BASE/GET_BASE.do");
	}
	
}
