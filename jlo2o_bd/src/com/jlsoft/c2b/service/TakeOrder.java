package com.jlsoft.c2b.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;


@Controller
@RequestMapping("/TakeOrder")
public class TakeOrder extends JLBill{
	/*
	 * 新增门店订单
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertTakeOrder.action")
	public Map insertTakeOrder(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		XmlData= JLTools.unescape(XmlData);
		cds=new DataSet(XmlData);
		try {
		//当前系统时间,即发布时间
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String PublishTime = format.format(new Date()).toString();
		//预约单号
		String OrderNumer = JLTools.getDateTime()+(int)(Math.random()*1000); 
		//接单ID
		int id=PubFun.updateWBHZT(o2o,"TAKEORDER",1);
		//接单来源
		String TakeOrderSource="0";
		//接单状态
		String TakeOrderStatus="0";
		//支付方式 9：微信支付
		//String PayType="9";
		//接单时间
		String FetchCarTime = cds.getField("FetchCarTime", 0);
		//维修厂员工
		String FetchCarEmployeeId = cds.getField("FetchCarEmployeeId", 0);
		//当前里程
		String CurrentMileage = cds.getField("CurrentMileage", 0);
		//当前油量
		String CurrentOilMeter = cds.getField("CurrentOilMeter", 0);
		//室内部饰件
		String InsideSeleced = cds.getField("InsideSeleced", 0);
		//总金额
		String TotalPrice = cds.getField("TotalPrice", 0);
		//来源单号
		String SourceId = cds.getField("SourceId", 0);
		//车架号
		String VIN = cds.getField("VIN", 0);
		//插入接单主表
		String insertTakeOrder =  "INSERT INTO TAKEORDER(Id,TakeOrderSource,OrganizationId,FetchCarTime,FetchCarEmployeeId,CurrentMileage,CurrentOilMeter,InsideSeleced,TakeOrderStatus,TotalPrice,SourceId,VIN,OrderNumber) "
				+ "VALUES('"+id+"','"+TakeOrderSource+"',(select ServiceUserId from servicedemand where id='"+SourceId+"'),'"+FetchCarTime+"','"+FetchCarEmployeeId+"','"+CurrentMileage+"','"+CurrentOilMeter+"','"+InsideSeleced+"','"+TakeOrderStatus+"','"+TotalPrice+"','"+SourceId+"','"+VIN+"','"+OrderNumer+"')";
		Map rowTakeOrder=getRow(insertTakeOrder, null, 0);
		execSQL(o2o, insertTakeOrder, 0);
		//插入接单车身部位表
		//接单ID
		String head = cds.getField("head", 0);
		String tail = cds.getField("tail", 0);
		String left = cds.getField("left", 0);
		String right = cds.getField("right", 0);
		String top = cds.getField("top", 0);
		if(!head.equals("")&&null!=head)
		{
			String inserttakeordersurfacesituation="INSERT INTO takeordersurfacesituation(TakeOrderId,Carpart,Description) VALUES('"+id+"','"+1+"','"+head+"')";
			Map rowTOSF=getRow(inserttakeordersurfacesituation, null, 0);
			execSQL(o2o, inserttakeordersurfacesituation, 0);
		}
		if(!tail.equals("")&&null!=tail)
		{
			String inserttakeordersurfacesituation="INSERT INTO takeordersurfacesituation(TakeOrderId,Carpart,Description) VALUES('"+id+"','"+2+"','"+tail+"')";
			Map rowTOSF=getRow(inserttakeordersurfacesituation, null, 0);
			execSQL(o2o, inserttakeordersurfacesituation, 0);
		}
		if(!left.equals("")&&null!=left)
		{
			String inserttakeordersurfacesituation="INSERT INTO takeordersurfacesituation(TakeOrderId,Carpart,Description) VALUES('"+id+"','"+3+"','"+left+"')";
			Map rowTOSF=getRow(inserttakeordersurfacesituation, null, 0);
			execSQL(o2o, inserttakeordersurfacesituation, 0);
		}
		if(!right.equals("")&&null!=right)
		{
			String inserttakeordersurfacesituation="INSERT INTO takeordersurfacesituation(TakeOrderId,Carpart,Description) VALUES('"+id+"','"+4+"','"+right+"')";
			Map rowTOSF=getRow(inserttakeordersurfacesituation, null, 0);
			execSQL(o2o, inserttakeordersurfacesituation, 0);
		}
		if(!top.equals("")&&null!=top)
		{
			String inserttakeordersurfacesituation="INSERT INTO takeordersurfacesituation(TakeOrderId,Carpart,Description) VALUES('"+id+"','"+5+"','"+top+"')";
			Map rowTOSF=getRow(inserttakeordersurfacesituation, null, 0);
			execSQL(o2o, inserttakeordersurfacesituation, 0);
		}
		
		
		//插入接单维修项目编号
		String MaintainServiceItemId []=cds.getField("MaintainServiceItemId", 0).split(",");
		String EmployeeId []=cds.getField("EmployeeId", 0).split(",");
		for(int i=0;i<MaintainServiceItemId.length;i++)
		{
			String inserttakeordersurfacesituation="INSERT INTO TAKEORDERMAINTAINITEM(TakeOrderId,MaintainServiceItemId,EmployeeId,Price) "
					+ "VALUES('"+id+"','"+MaintainServiceItemId[i]+"','"+EmployeeId[i]+"',(select Price from maintainserviceitem where Id ='"+MaintainServiceItemId[i]+"' ))";
			Map rowTOMT=getRow(inserttakeordersurfacesituation, null, 0);
			execSQL(o2o, inserttakeordersurfacesituation, 0);
		}
		
		//插入接单维修项目编号
				String MaterialId []=cds.getField("MaterialId", 0).split(",");
				String Count []=cds.getField("Count", 0).split(",");
				for(int i=0;i<MaterialId.length;i++)
				{
					String inserttakeordersurfacesituation="INSERT INTO TAKEORDERPARTSUSE(MaterialId,Count,TakeOrderId,Price) "
							+ "VALUES('"+MaterialId[i]+"','"+Count[i]+"','"+id+"',(select SalesPrice*'"+Count[i]+"' from material where Id='"+MaterialId[i]+"'))";
					Map rowTOMT=getRow(inserttakeordersurfacesituation, null, 0);
					execSQL(o2o, inserttakeordersurfacesituation, 0);
				}		
		String update ="update servicedemand set DemandStatus='2' where Id = '"+id+"'";
		Map rowSD=getRow(update, null, 0);
		execSQL(o2o, update, 0);
		map.put("STATE", 1);
		map.put("JYDH", OrderNumer);
		map.put("zfje", TotalPrice);
		} catch (Exception e) {
			map.put("STATE", 0);
			throw e;	
		}
			return map;
	}
}
