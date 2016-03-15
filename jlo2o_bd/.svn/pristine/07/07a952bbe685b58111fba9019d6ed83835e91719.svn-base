package com.jlsoft.o2o.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;

import net.sf.json.JSONArray;

/**
 * 收货地址信息
 * @author huangrui
 * 
 */
@Controller
@RequestMapping("/oper_shdz")
public class Oper_Shdz extends JLBill{
	/**
	 * 新增收货地址
	 * @param XmlData
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertshdz.action")
	public Map<String, Object> insertShdz(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		JSONArray json=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)json.get(0);
		int dzbh=PubFun.updateWBHZT(o2o,"W_SHDZWH",1);
		if(hm.get("gddh")==null){
			hm.put("gddh", "");
		}
		if(hm.get("email")==null){
			hm.put("email", "");
		}
		if(hm.get("yzbm")==null){
			hm.put("yzbm", "");
		}
		if(hm.get("DZLX")==null){
			hm.put("DZLX", 0);
		}
		String sql="INSERT INTO W_SHDZWH(DZBH,WLDW01,SHRMC,PROVINCE,CITY,COUNTY,STREET,OTHERDZ,SJHM,GDDH,EMAIL,YZBM,MARK,DZLX)"+
		"VALUES("+dzbh+",'"+hm.get("wldw01")+"','"+hm.get("shrmc")+"','"+hm.get("province")+"','"+hm.get("city")+"','"+hm.get("county")+"','"+hm.get("street")+"','"+hm.get("otherdz")+"','"+hm.get("sjhm")+"','"+hm.get("gddh")+"','"+hm.get("email")+"','"+hm.get("yzbm")+"','"+hm.get("mark")+"','"+hm.get("DZLX")+"')";
		Map row;
		int i;
		try {
			row = getRow(sql, null, 0);
			   i=execSQL(o2o, sql, row);
			   hm.put("status","success");
		} catch (Exception e) {
			hm.put("status", "false");
			e.printStackTrace();
		}
		return hm;
	}
	//后台个人中心修改地址
	@RequestMapping("/updateXGSCDZ_mj.action")
	public Map<String, Object> updateXGSCDZ_mj(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map=new HashMap();
		try {
		String fplxsql="UPDATE W_SHDZWH W "+
				"SET W.SHRMC = '"+cds.getField("shrmc", 0)+"',"+
		       "W.PROVINCE = '"+cds.getField("province", 0)+"',"+
		       "W.CITY     = '"+cds.getField("city", 0)+"',"+
		       "W.COUNTY   = '"+cds.getField("county", 0)+"',"+
		       "W.STREET   = '"+cds.getField("street", 0)+"',"+
		       "W.OTHERDZ  = '"+cds.getField("otherdz", 0)+"',"+
		       "W.SJHM     = '"+cds.getField("sjhm", 0)+"',"+
		       "W.GDDH     = '"+cds.getField("gddh", 0)+"',"+
		       "W.EMAIL    = '"+cds.getField("email", 0)+"',"+
		       "W.MARK    = '"+cds.getField("mark", 0)+"',"+
		       "W.YZBM     = '"+cds.getField("yzbm", 0)+"'"+
		 "WHERE W.DZBH = '"+cds.getField("dzbm", 0)+"'";
		Map row0 = getRow(fplxsql, null, 0);
		execSQL(o2o, fplxsql, row0);
		map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 前台修改收货地址
	 * @param XmlData
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateshdz.action")
	public Map<String, Object> updateShdz(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		JSONArray json=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)json.get(0);
		if(hm.get("gddh")==null){
			hm.put("gddh", "");
		}
		
		//修改收货地址之前将该客户所有地址设为非默认
		/*String sqlStr=" UPDATE W_SHDZWH W SET W.MARK=0 WHERE W.WLDW01='"+hm.get("zcxx01")+"'";
		execSQL(o2o,sqlStr,getRow(sqlStr,null,0));*/
		
		String sql="UPDATE W_SHDZWH W "+
				"SET W.SHRMC = '"+hm.get("shrmc")+"',"+
		       "W.PROVINCE = '"+hm.get("province")+"',"+
		       "W.CITY     = '"+hm.get("city")+"',"+
		       "W.COUNTY   = '"+hm.get("county")+"',"+
		       "W.STREET   = '"+hm.get("street")+"',"+
		       "W.OTHERDZ  = '"+hm.get("otherdz")+"',"+
		       "W.SJHM     = '"+hm.get("sjhm")+"',"+
		       "W.GDDH     = '"+hm.get("gddh")+"',"+
		       "W.EMAIL    = '"+hm.get("email")+"',"+
		       "W.MARK    = '"+hm.get("mark")+"',"+
		       "W.YZBM     = '"+hm.get("yzbm")+"'"+
		 "WHERE W.DZBH = '"+hm.get("dzbm")+"'";
		Map row;
		int i;
		try {
			row = getRow(sql, null, 0);
			   i=execSQL(o2o, sql, row);
			   hm.put("status","success");
		} catch (Exception e) {
			hm.put("status", "false");
			e.printStackTrace();
		}
		return hm;
	}
	/*查询发票类型*/
	@RequestMapping("/selectFplx.action")
	public Map<String, Object> selectFplx(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		String sql="SELECT ZCXX20 FROM W_ZCXX WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"'";
		Map map =new HashMap();
		map=queryForMap(o2o, sql);
		return map;
	}
	/*设置默认发票类型*/
	@RequestMapping("/updateFplx.action")
	public Map<String, Object> updateFplx(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map=new HashMap();
		try {
		//更改发票类型      liyong14-12-12
		String fplxsql="UPDATE W_ZCXX SET ZCXX20='"+cds.getField("fplx", 0)+"' WHERE ZCXX01 = '"
				+ cds.getField("ZCXX01", 0) + "'";
		Map row0 = getRow(fplxsql, null, 0);
		execSQL(o2o, fplxsql, row0);
		map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		}
		return map;
	}
	
	/*删除收货地址*/
	@RequestMapping("/deleteDz.action")
	public Map<String, Object> deleteDz(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map=new HashMap();
		try {
		String fplxsql=" DELETE FROM W_SHDZWH WHERE DZBH='"+cds.getField("DZBH", 0)+"'";				
		Map row = getRow(fplxsql, null, 0);
		int i = execSQL(o2o, fplxsql, row);
		if(i>0){
			map.put("STATE", "success");
		}else{
			map.put("STATE", "error");
		}
		} catch (Exception e) {
			map.put("STATE", "error");
			e.printStackTrace();
		}
		return map;
	}
	
	/*买家设置默认收货地址*/
	@RequestMapping("/updateDz.action")
	public Map<String, Object> updateDz(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map=new HashMap();
		try {
		String sql0=" SELECT dzbh FROM w_shdzwh WHERE  WLDW01 = '"+cds.getField("ZCXX01", 0)+"' AND dzlx=0 AND mark=1 ";
		System.out.println(sql0);
		Map map1 =new HashMap();
		map1=queryForMap(o2o, sql0);
		if(map1 == null){
			String sql1=" UPDATE W_SHDZWH SET MARK=1 WHERE DZBH='"+cds.getField("DZBH", 0)+"'";				
			Map row1 = getRow(sql1, null, 0);
			int j = execSQL(o2o, sql1, row1);
			if(j>0){
				map.put("STATE", "success");
			}else{
				map.put("STATE", "error");
			}
			return map;
		}else{
			String sql=" UPDATE W_SHDZWH SET MARK=0 WHERE DZBH='"+map1.get("dzbh")+"'";				
			Map row = getRow(sql, null, 0);
			int i = execSQL(o2o, sql, row);
			String sql1=" UPDATE W_SHDZWH SET MARK=1 WHERE DZBH='"+cds.getField("DZBH", 0)+"'";				
			Map row1 = getRow(sql1, null, 0);
			int j = execSQL(o2o, sql1, row1);
			if(i>0&&j>0){
				map.put("STATE", "success");
			}else{
				map.put("STATE", "error");
			}
		}
		} catch (Exception e) {
			map.put("STATE", "error");
			e.printStackTrace();
		}
		return map;
	}
	/*卖家设置默认收货地址*/
	@RequestMapping("/updateDzmj.action")
	public Map<String, Object> updateDzmj(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map=new HashMap();
		try {
		String sql0=" SELECT dzbh FROM w_shdzwh WHERE  WLDW01 = '"+cds.getField("ZCXX01", 0)+"' AND dzlx=1 AND mark=1 ";
		System.out.println(sql0);
		Map map1 =new HashMap();
		map1=queryForMap(o2o, sql0);
		if(map1 == null){
			String sql1=" UPDATE W_SHDZWH SET MARK=1 WHERE DZBH='"+cds.getField("DZBH", 0)+"'";				
			Map row1 = getRow(sql1, null, 0);
			int j = execSQL(o2o, sql1, row1);
			if(j>0){
				map.put("STATE", "success");
			}else{
				map.put("STATE", "error");
			}
			return map;
		}else{
			String sql=" UPDATE W_SHDZWH SET MARK=0 WHERE DZBH='"+map1.get("dzbh")+"'";				
			Map row = getRow(sql, null, 0);
			int i = execSQL(o2o, sql, row);
			String sql1=" UPDATE W_SHDZWH SET MARK=1 WHERE DZBH='"+cds.getField("DZBH", 0)+"'";				
			Map row1 = getRow(sql1, null, 0);
			int j = execSQL(o2o, sql1, row1);
			if(i>0&&j>0){
				map.put("STATE", "success");
			}else{
				map.put("STATE", "error");
			}
		}
		} catch (Exception e) {
			map.put("STATE", "error");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/srchSJCK.action")
	public Map sendSms(String XmlData) throws Exception{
		cds = new DataSet(XmlData);
		String type = cds.getField("TYPE", 0);//"TMSTIMEWINDOW";
		Long messageid = System.currentTimeMillis();
		String city = cds.getField("CITY", 0);
		String cityZone = cds.getField("CITYZONE", 0);
		String disTrict = cds.getField("DISTRICT", 0);
		String zone = cds.getField("ZONE", 0);
		String endpoint = "http://61.161.160.12:9080/xldjtedi/webServices/vtradexGlobalServlet";
    	String url = endpoint+"?type="+type+"&sender=JINLI&code=0&messageid="+messageid;
    	StringBuffer sbf = new StringBuffer();
    	Date datenew=new Date();
    	SimpleDateFormat simple=new SimpleDateFormat("yyyyMMdd");
    	String startDate=simple.format(datenew);
    	sbf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    			"<JL>" +
    			"<HEAD><TYPE>"+type+"</TYPE>" +
    			"<SENDER>JINLI</SENDER>" +
    			"<RECEIVER>TMS</RECEIVER>" +
    			"<MESSAGEID>"+type+messageid+"</MESSAGEID>" +
    			"</HEAD>" +
    			"<MESSAGE>" +
    			"<STORE></STORE>" +
    			"<SHIPDC>001</SHIPDC>" +
    			"<SHIPDCLOCATION></SHIPDCLOCATION>" +
    			"<SHFS></SHFS>" +
    			"<ADDRESS>" +
    			"<ZONE>"+zone+"</ZONE>" +
    			"<CITY>"+city+"</CITY>" +
    			"<CITYZONE>"+cityZone+"</CITYZONE>" +
    			"<DISTRICT>"+disTrict+"</DISTRICT>" +
    			"<STREET></STREET>" +
    			"<STREETNO></STREETNO>" +
    			"</ADDRESS><ISCHEAP>" +
    			"</ISCHEAP>" +
    			"<LAYER>null</LAYER>" +
    			"<CARGONUM></CARGONUM>" +
    			"<STARTDATE>"+startDate+"</STARTDATE>" +
    			"</MESSAGE></JL>");
    	//sbf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><JL><HEAD><TYPE>TMSTIMEWINDOW</TYPE><SENDER>JINLI</SENDER><RECEIVER>TMS</RECEIVER><MESSAGEID>TMSTIMEWINDOW1441164809250</MESSAGEID></HEAD><MESSAGE><STORE>00010301</STORE><SHIPDC>001</SHIPDC><SHIPDCLOCATION></SHIPDCLOCATION><SHFS></SHFS><ADDRESS><ZONE></ZONE><CITY>沈阳市</CITY><CITYZONE>皇姑区</CITYZONE><DISTRICT></DISTRICT><STREET></STREET><STREETNO></STREETNO></ADDRESS><ISCHEAP></ISCHEAP><LAYER>null</LAYER><CARGONUM></CARGONUM><STARTDATE>20150902</STARTDATE></MESSAGE></JL>");
    	
    	try {
    		System.out.println("#############请求报文################" + sbf.toString());
			String xml=JLTools.sendToVtradEx(sbf.toString(), url);
			System.out.println("#############应答报文################" + xml);
			Document document = DocumentHelper.parseText(xml);  
			Element root = document.getRootElement();
			List windwos = document.selectNodes("//TIMEWINDOWS");
			List childNodes = document.selectNodes("//TIMEWINDOWS/TIMEWINDOW/DATE"); 
			List days = new ArrayList();
			List list;
			List psqys =new ArrayList();
			Map mappsqys = new HashMap();
			for(Object obj:childNodes) { 
				Node childNode = (Node)obj;
				Map day = new HashMap();
				//childNode.valueOf("@DATE")
				System.out.println(Long.parseLong(childNode.getText()));
		        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");  
		        Date date=null;
		        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd"); 
		        date = sdf1.parse(childNode.getText());
		        String text =sdf2.format(date);
				String name = childNode.getName();
				day.put(name, text);
				days.add(day);
				System.out.println(name+"----------"+text);
				}
			mappsqys.put("psdays", days);
			System.out.println("根节点：" + root.getName());
			System.out.println(document.selectSingleNode("//MESSAGE").getText());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//resultMap.put("message", getXmlNodeValue(xml,"HEAD/RECEIVER"));
			//时间段list
			list=new ArrayList();
			for (Iterator its = root.elementIterator(); its.hasNext();) {  
	             Element ele = (Element) its.next(); 
	             System.out.println(ele.getName()+"----"+ele.getTextTrim());
	             if (ele.getName().equals("MESSAGE")) {
	            	 for(Iterator item=ele.elementIterator();item.hasNext();){
	            		  ele = (Element) item.next();
	            		 if(ele.getName().equals("ZONES")){
	            			 for(Iterator item1=ele.elementIterator();item1.hasNext();){
	            				 ele = (Element) item1.next();
	            				 Map map=new HashMap();
	            				 if(ele.getName().equals("ZONE")){
	            					 for(Iterator item2=ele.elementIterator();item2.hasNext();){
	            						 Element temp = (Element) item2.next();
	            						 map.put(temp.getName(), temp.getTextTrim());
	            					 }
	            					 psqys.add(map); 
	            				 }
	            			 }
	            		 }else if(ele.getName().equals("DEFS")){
	            			 for(Iterator item1=ele.elementIterator();item1.hasNext();){
	            				 ele = (Element) item1.next();
	            				 Map map=new HashMap();
	            				 if(ele.getName().equals("DEFINE")){
	            					 for(Iterator item2=ele.elementIterator();item2.hasNext();){
	            						 Element temp = (Element) item2.next();
	            						 if(temp.getName()=="DEFINENAME"){
	            							 map.put(temp.getName(), temp.getTextTrim());
	            						 }else{
	            							 map.put(temp.getName(), temp.getTextTrim().substring(0,2)+":00");
	            						 }
	            					 }
	            					 list.add(map); 
	            				 }
	            			 }
	            		 }
	            	 }
	                  
	             }  
	         }
			mappsqys.put("pstimes", list);
			mappsqys.put("psqys", psqys);
			mappsqys.put("status", "success");
			System.out.println("#################返回值:"+mappsqys);
			return mappsqys;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
