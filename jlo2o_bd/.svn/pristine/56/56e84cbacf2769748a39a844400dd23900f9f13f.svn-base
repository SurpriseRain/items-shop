package com.jlsoft.c2b.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;


@Controller
@RequestMapping("/MapService")
public class MapService extends JLBill{
	/*
	 * 保存车主需求
	 */
	@RequestMapping("/updateservice.action")
	public Map updateservice(HttpServletRequest request,
			HttpServletResponse response, String xmlData) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		//cds=new DataSet(jsonData);
		xmlData= JLTools.unescape(xmlData);
		cds=new DataSet(xmlData);
		try {
		//当前系统时间,即发布时间
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String PublishTime = format.format(new Date()).toString();
		String zcxx01 = cds.getField("zcxx01", 0);
		String id = cds.getField("id", 0);
		String update =  "update servicedemand set DemandStatus='1',ServiceUserId='"+zcxx01+"',ReceiveOrderTime='"+PublishTime+"' where Id='"+id+"'";
		Map row=getRow(update, null, 0);
		execSQL(o2o, update, 0);
		map.put("STATE", 1);
		} catch (Exception e) {
			map.put("STATE", 0);
			throw e;	
		}
			return map;
	}
	/*
	 * 保存产品需求
	 */
	@RequestMapping("/updateproduce.action")
	public Map updateproduce(HttpServletRequest request,
			HttpServletResponse response, String xmlData) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		//cds=new DataSet(jsonData);
		xmlData= JLTools.unescape(xmlData);
		cds=new DataSet(xmlData);
		try {
		//当前系统时间,即发布时间
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String PublishTime = format.format(new Date()).toString();
		String zcxx01 = cds.getField("zcxx01", 0);
		String id = cds.getField("id", 0);
		String update =  "update producedemand set DemandStatus='1',ServiceUserId='"+zcxx01+"',ReceiveOrderTime='"+PublishTime+"' where Id='"+id+"'";
		Map row=getRow(update, null, 0);
		execSQL(o2o, update, 0);
		map.put("STATE", 1);
		} catch (Exception e) {
			map.put("STATE", 0);
			throw e;	
		}
			return map;
	}
}
