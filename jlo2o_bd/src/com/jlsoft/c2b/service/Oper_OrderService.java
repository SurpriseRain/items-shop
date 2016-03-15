package com.jlsoft.c2b.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/Oper_OrderService")
public class Oper_OrderService extends JLBill{
	/**
	 * @todo 新增购买服务商品
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertOrderService")
	public Map<String, Object> insertOrderService(String json) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds=new DataSet(json);
		try{
			String ServiceId = cds.getField("ServiceId", 0);
			String UserId = cds.getField("UserId", 0);
			String TotailPrice = cds.getField("TotailPrice", 0); //总价格
			String OrderNumer = JLTools.getDateTime()+(int)(Math.random()*1000); //预约单号
			String yykssj = cds.getField("OrderServiceStartTime", 0); //预约开始时间
			String yyjssj = cds.getField("OrderServiceEndTime", 0); //预约结束时间
			String IsVisit = cds.getField("IsVisit", 0); //是否需上门
			String sql = "INSERT INTO ORDERSERVICE(ServiceId,UserId,SubscriberTime,OrderNumer,TotailPrice,OrderStatus,OrderServiceStartTime,OrderServiceEndTime,IsVisit) " +
							   "VALUES("+ServiceId+","+UserId+",NOW(),'"+OrderNumer+"',"+TotailPrice+",1,'"+yykssj+"','"+yyjssj+"',"+IsVisit+")";
			execSQL(o2o, sql, new HashMap());
			returnMap.put("STATE", "success");
		}catch(Exception ex){
			ex.printStackTrace();
			returnMap.put("STATE", "failure");
			throw ex;
		}
		return returnMap;
	}
	
}