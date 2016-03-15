package com.jlsoft.o2o.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PublicZSXT {
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String date = sdf.format(new Date());;
	public void rk(List list,String address,String type)
	{
		JSONArray jsonList=new JSONArray(); 
		for(int i=0;i<list.size();i++)
		{
			Map mapList = (Map) list.get(i);
			String zcxx02 = mapList.get("ZCXX02").toString();
			JSONObject jsonObject_ = new JSONObject();
			jsonObject_.put("uniqueCode", mapList.get("SPCM01"));
			jsonObject_.put("name", mapList.get("SPXX04"));
			jsonObject_.put("batchNumber", mapList.get("SPCM03"));
			jsonObject_.put("produceBar", mapList.get("barcode"));
			jsonObject_.put("manuFacturer", zcxx02);
			jsonObject_.put("manuFacturerAddress", mapList.get("ZCXX08"));
			jsonObject_.put("contactPhoto", mapList.get("ZCXX55"));
			jsonObject_.put("produceBrand",mapList.get("SPTP"));
			JSONArray eveList=new JSONArray();
			JSONObject eventjsonObject_ = new JSONObject();
			eventjsonObject_.put("eventType", type);
			if(type.equals("0"))
			{
				eventjsonObject_.put("eventName", "配件出厂");
			}else if(type.equals("1"))
			{
				eventjsonObject_.put("eventName", "仓库收货");
			}else if (type.equals("3"))
			{
				eventjsonObject_.put("eventName", "买家收货");
			}
			
			eventjsonObject_.put("eventTime", date);
			eventjsonObject_.put("sponsor", zcxx02);
			eventjsonObject_.put("orderNumber", "");
			eventjsonObject_.put("description", "");
			eveList.add(eventjsonObject_);
			jsonObject_.put("produceEvents", eveList);
			jsonList.add(jsonObject_);
		}

		Map zsmap=new HashMap();
		zsmap.put("content", jsonList.toString());
		String reponseString=RequestOauthUtil.postData(address, null,zsmap,"POST");
		JSONObject jsonObject = JSONObject.fromObject(reponseString);
		String resultCode=(String) jsonObject.get("resultCode");
		if("1".equals(resultCode)){
			//成功
			
		}else{
			//失败
			
		}
	}
	public void ck(List list,String adress,String type,String dh)
	{
		String text ="";
		if(type.equals("2"))
		{
			text="物流送货";
		}else if(type.equals("4"))
		{
			text="退货入库";
		}
		JSONArray jsonList=new JSONArray(); 
		for(int i=0;i<list.size();i++)
		{
			Map mapList = (Map) list.get(i);
			JSONObject jsonObject_ = new JSONObject();
			jsonObject_.put("uniqueCode", mapList.get("SPCM01"));
			jsonObject_.put("eventName", text);
			jsonObject_.put("eventTime",date);
			jsonObject_.put("sponsor", mapList.get("ZCXX02"));
			jsonObject_.put("orderNumber", dh);
			jsonObject_.put("description", "");
			jsonObject_.put("eventType", type);
			jsonList.add(jsonObject_);
		}

		Map zsmap=new HashMap();
		zsmap.put("content", jsonList.toString());
		String reponseString=RequestOauthUtil.postData(adress, null,zsmap,"POST");
		JSONObject jsonObject = JSONObject.fromObject(reponseString);
		String resultCode=(String) jsonObject.get("resultCode");
		if("1".equals(resultCode)){
			//成功
			
		}else{
			//失败
			
		}
	}
	//----hcl
	public void ck1(List list,String address,String type)
	{
		JSONArray jsonList=new JSONArray(); 
		for(int i=0;i<list.size();i++)
		{
			Map mapList = (Map) list.get(i);
			String zcxx02 = mapList.get("ZCXX02").toString();
			JSONObject jsonObject_ = new JSONObject();
			jsonObject_.put("uniqueCode", mapList.get("SPCM01"));
			jsonObject_.put("name", mapList.get("SPXX04"));
			jsonObject_.put("batchNumber", mapList.get("SPCM03"));
			jsonObject_.put("produceBar", mapList.get("barcode"));
			jsonObject_.put("manuFacturer", zcxx02);
			jsonObject_.put("manuFacturerAddress", mapList.get("ZCXX08"));
			jsonObject_.put("contactPhoto", mapList.get("ZCXX55"));
			JSONArray eveList=new JSONArray();
			JSONObject eventjsonObject_ = new JSONObject();
			eventjsonObject_.put("eventType", type);
			String text ="";
			if(type.equals("2"))
			{
				eventjsonObject_.put("eventName", "物流送货");
				eventjsonObject_.put("orderNumber", mapList.get("XSDD01"));
			}else if(type.equals("4"))
			{
				eventjsonObject_.put("orderNumber", mapList.get("THDH"));
				eventjsonObject_.put("eventName", "退货入库");
			}
			if(type.equals("2"))
			{
				text="";
				
			}else if(type.equals("4"))
			{
				text="";
				
			}
			eventjsonObject_.put("eventTime", date);
			eventjsonObject_.put("sponsor", zcxx02);
			eventjsonObject_.put("description", "");
			eveList.add(eventjsonObject_);
			jsonObject_.put("produceEvents", eveList);
			jsonList.add(jsonObject_);
		}

		Map zsmap=new HashMap();
		zsmap.put("content", jsonList.toString());
		String reponseString=RequestOauthUtil.postData(address, null,zsmap,"POST");
		JSONObject jsonObject = JSONObject.fromObject(reponseString);
		String resultCode=(String) jsonObject.get("resultCode");
		if("1".equals(resultCode)){
			//成功
			
		}else{
			//失败
			
		}
	}
	
	
	/**
	 * 分批提交
	 * @param maxSize 批的最大数量
	 * @param list 结果集
	 * @param address 地址
	 * @param type 类型
	 * 2015-12-3 下午4:09:07
	 */
	public void batchRk(int maxSize, List list,String address,String type){
		//参数检查
		if(maxSize < 1 || list ==null){
			return;			
		}
		
		List dataList = new ArrayList();
		int size = list.size();
		
		//如果结果集的条数小于批的数量，直接提交
		if(size < maxSize){
			this.rk(list, address, type);
		}
		else{
			//遍历拆分list，分批提交
			for(int i = 1; i < size + 1 ; i++){
				//如果小于批的数量且不是最后一条，放入分批的list里
				if(i % maxSize > 0 && i < size){
					dataList.add(list.get(i-1));
				}
				else{
					//放入并提交写入，之后清空list
					dataList.add(list.get(i-1));
					this.rk(dataList, address, type);
					dataList.clear();					
				}
			}
		}
	}
}