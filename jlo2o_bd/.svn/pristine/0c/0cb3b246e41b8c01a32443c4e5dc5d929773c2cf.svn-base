package com.jlsoft.o2o.interfacepackage.jlinterface;

import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import com.jlsoft.o2o.order.service.OrderFlowManage;
import com.jlsoft.utils.JlAppResources;

@Controller
public class VipOfJlInterfaces {
	private static final Logger logger = Logger.getLogger(OrderFlowManage.class);
	String PayCode = JlAppResources.getProperty("PayCode");
	String SysTemCon = JlAppResources.getProperty("SysTemCon");
	String JL_OrderType = JlAppResources.getProperty("JL_OrderType");
	String ROADMAP = JlAppResources.getProperty("ROADMAP");
	String SALER = JlAppResources.getProperty("SALER"); // CashCode:收款员代码
	String SALERNAME = JlAppResources.getProperty("SALERNAME"); // CashName:收款员名称
	
	/*会员建卡接口
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String createVIPInterface(Map infoMap)
			throws Exception {
		String sreturn="";
		JSONObject json = new JSONObject();
		/*
		 *  Order_Code	Varchar	20	y	外部单号
				Name	Varchar	20	Y	姓名
				Tel	Varchar	50	Y	电话
				Addr	Varchar	50	Y	地址
				Sex	Varchar	20	Y	性别
				Birth	Varchar	20	Y	出生年月
				Type	Varchar	20	Y	会员类型(默认为：微信电子会员卡)
				DepNo	Varchar	20	Y	发卡部门代码（默认为：移动互联事业部）
				DepName	Varchar	20	Y	发卡部门（默认为：移动互联事业部）
				Person	Varchar	20	Y	发卡人代码（默认为：汇银微信的人员代码）
				PersonName	Varchar	20	Y	发卡人名称				（默认为：汇银微信）
				Model	Varchar	20	Y	机器型号
				SN	Varchar	20	Y	机器串号
				CardNumber	Varchar	20	Y	会员卡号
				Password	Varchar	20	Y	会员卡密码
				SysTemCon	Varchar	20	Y	系统参数。默认23 为建卡发卡激活一步完成
		 * */
		json.put("Order_Code", infoMap.get("Order_Code").toString());// 外部订单号
		json.put("Name", infoMap.get("Name").toString());// 姓名
		json.put("Tel", infoMap.get("Tel").toString());// 电话
		json.put("Addr", infoMap.get("Addr").toString());// 地址
		json.put("Sex", infoMap.get("Sex").toString());// Sex	Varchar	20	Y	性别
		json.put("Birth", infoMap.get("Birth").toString());// Birth	Varchar	20	Y	出生年月
		json.put("Type", infoMap.get("Type").toString());// Type	Varchar	20	Y	会员类型(默认为：微信电子会员卡)
		json.put("DepNo", infoMap.get("DepNo").toString());//DepNo	Varchar	20	Y	发卡部门代码（默认为：移动互联事业部）
		json.put("DepName", infoMap.get("DepName").toString());//DepName	Varchar	20	Y	发卡部门（默认为：移动互联事业部）
		json.put("PersonID", infoMap.get("PersonID").toString());//PersonID	Varchar	20	Y	发卡人代码（默认为：汇银微信的人员代码）
		json.put("Person", infoMap.get("Person").toString());//Person	Varchar	20	Y	发卡人名称				（默认为：汇银微信）
		json.put("Model", infoMap.get("Model").toString());////Model	Varchar	20	Y	机器型号
		json.put("SN", infoMap.get("SN").toString());//SN	Varchar	20	Y	机器串号
		json.put("CardNumber", infoMap.get("CardNumber").toString());//CardNumber	Varchar	20	Y	会员卡号
		json.put("Password", infoMap.get("Password").toString());//Password	Varchar	20	Y	会员卡密码
		json.put("SysTemCon", infoMap.get("SysTemCon").toString());//SysTemCon	Varchar	20	Y	系统参数。默认23 为建卡发卡激活一步完成

		System.out.println("会员注册信息传递至SCM接口拼接字符串JSON格式：" + json.toString());
		// 调用V7接口
		String returnJson = JlInterfaces.vipinterface(json.toString(),"Post_VIPCard");
		System.out.println("会员注册信息调用SCM接口返回信息：" + returnJson);
		// {"JL_State":"1","Order_Code":"CG2013121222135166001","JL_TimeStamp":"2013121410090793","JL_ComPanyCode":"0806","JL_OrderCode":"551"}
		/*
		 *  Order_Code	Varchar	50	外部订单号
				JL_OrderGoods	Varchar	16	会员卡号
				JL_ComPanyCode	Varchar	50	ERP 公司代码
				JL_State	Varchar	4	0失败 1成功
		 */
		
		//String returnError = "";
		try {
			// 根据接口信息进行判断
			JSONObject result = JSONObject.fromObject(returnJson);
			int resultState = Integer.valueOf(result.get("JL_State").toString());
		//	returnError = result.get("JL_ERR") == null ? "" : result.get("JL_ERR").toString();
			if (resultState == 1) {
				sreturn = result.get("JL_OrderCode").toString();// 
			} else if (resultState == 0) {
				// 抛出异常信息
				logger.info("分销单分组编号：" + infoMap.get("Order_Code").toString()
						+ "\n报错原因: " );//+ returnError);
				throw new Exception("分销单分组编号：" + infoMap.get("Order_Code").toString()
						+ "\n报错原因: ");// + returnError);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw e;
		}
		return sreturn;
	}
/*
 * 会员储值充值接口
 * */	
	@SuppressWarnings("unchecked")
	public static String VipPost_VIPRechargeInterface(Map infoMap)
			throws Exception {
		String sreturn="";
		JSONObject json = new JSONObject();
		/*
		 *  VIPCode	Varchar	50	Y	会员卡号
				Operator	Varchar	50	Y	操作员
				Money	Varchar	50	Y	充值金额
				SaleDep	Varchar	50	Y	销售部门
				Remark	Varchar	16	Y	备注
				SysTemCon	varchar	18	Y	默认0

		 * */
		json.put("VIPCode", infoMap.get("VIPCode").toString());// VIPCode	Varchar	50	Y	会员卡号
		json.put("Operator", infoMap.get("Operator").toString());// Operator	Varchar	50	Y	操作员名
		json.put("Money", infoMap.get("Money").toString());// Money	Varchar	50	Y	充值金额
		json.put("SaleDep", infoMap.get("SaleDep").toString());// SaleDep	Varchar	50	Y	销售部门
		json.put("Remark", infoMap.get("Remark").toString());// Remark	Varchar	16	Y	备注
		json.put("SysTemCon", infoMap.get("SysTemCon").toString());//SysTemCon	varchar	18	Y	默认0

		System.out.println("会员储值充值接口传递至SCM接口拼接字符串JSON格式：" + json.toString());
		// 调用V7接口
		String returnJson = JlInterfaces.vipinterface(json.toString(),"Post_VIPRecharge");
		System.out.println("会员储值充值接口调用SCM接口返回信息：" + returnJson);
		// {"JL_State":"1","Order_Code":"CG2013121222135166001","JL_TimeStamp":"2013121410090793","JL_ComPanyCode":"0806","JL_OrderCode":"551"}
		/*
		 *  Order_Code	Varchar	50	外部订单号
				JL_OrderGoods	Varchar	16	会员卡号
				JL_ComPanyCode	Varchar	50	ERP 公司代码
				JL_State	Varchar	4	0失败 1成功
		 */
		
		//String returnError = "";
		try {
			// 根据接口信息进行判断
			JSONObject result = JSONObject.fromObject(returnJson);
			int resultState = Integer.valueOf(result.get("JL_State").toString());
		//	returnError = result.get("JL_ERR") == null ? "" : result.get("JL_ERR").toString();
			if (resultState == 1) {
				sreturn = result.get("JL_OrderCode").toString();// 
			} else if (resultState == 0) {
				// 抛出异常信息
				logger.info("会员储值充值接口：" + infoMap.get("VIPCode").toString()
						+ "\n报错原因: " );//+ returnError);
				throw new Exception("会员储值充值接口：" + infoMap.get("VIPCode").toString()
						+ "\n报错原因: ");// + returnError);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw e;
		}
		return sreturn;
	}	
}
