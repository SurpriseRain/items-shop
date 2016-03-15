/**********************************************************************
 * @file	HomeDisplay.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	赵明亮/20140314
 * @author	Code:	
 * @author	Modify:	
 * @copy	Tag:	金力软件
 **********************************************************************/

package com.jlsoft.o2o.product.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.utils.JLTools;

/**
 * 
 * @breif 各类商品展示
 *
 */
@Controller
@RequestMapping("/PCdisplay/jlquery")
public class PCdisplay extends JLBill{
	private SqlSession session=null;
	
	@RequestMapping("/selectFCSS.action")	
	public Map selectFCSS(String XmlData) throws Exception {	
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.FCSS");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		//map.put("pagesize", "50");
		System.out.println("map="+map);
		return map;
	}
	
	@RequestMapping("/selectFCSSSUM.action")	
	public Map selectFCSSSUM(String XmlData) throws Exception {	
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		//map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.FCSSSUM");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		//map.put("pagesize", "10");
		System.out.println("map="+map);
		return map;
	}
	@RequestMapping("/selectHYSPnotloginPX.action")	
	public Map selectHYSPnotloginPX(String XmlData) throws Exception {	
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.HYSPnotloginPX");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		//map.put("pagesize", "50");
		System.out.println("map="+map);
		return map;
	}
	
	@RequestMapping("/selectHYSPnotloginPXSUM.action")	
	public Map selectHYSPnotloginPXSUM(String XmlData) throws Exception {	
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		//map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.HYSPnotloginPXsum");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		//map.put("pagesize", "10");
		System.out.println("map="+map);
		return map;
	}
	
	//登陆前促销商品展示
	@RequestMapping("/selectCXSPnotlogin.action")	
	public Map selectCXSPnotlogin(String XmlData) throws Exception {	
		System.out.println("XmlData="+XmlData);
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.CXSPnotlogin");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		//map.put("pagesize", "50");
		System.out.println("map="+map);
		return map;
	}
	
	@RequestMapping("/selectCXSPnotloginSUM.action")	
	public Map selectCXSPnotloginSUM(String XmlData) throws Exception {	
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.CXSPnotloginSum");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		//map.put("pagesize", "");
		System.out.println("map="+map);
		return map;
	}
	
	@RequestMapping("/selectHYSPloginPX.action")
	public Map selectHYSPloginPX(String XmlData) throws Exception {		
		System.out.println("XmlData="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);
		map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.selectHYSPloginPX");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		//map.put("pagesize", "50");
		return map;
	}
	
	@RequestMapping("/selectHYSPloginPXSUM.action")
	public Map selectHYSPloginPXSUM(String XmlData) throws Exception {		
		System.out.println("XmlData="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);
		map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.selectHYSPloginPXSUM");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		//map.put("pagesize", "50");
		return map;
	}
	
	@RequestMapping("/selectLenovoSrch.action")
	public Map selectLenovoSrch(String XmlData) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper();
			List list =  mapper.readValue(XmlData, ArrayList.class); 
			Map map = (Map)list.get(0);
			map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
			map.put("sqlid", "com.jlsoft.o2o.PCproduct.selectSPMCLxss");		
			session = JlAppSqlConfig.getO2OSqlMapInstance();
			map.put("session", session);
			map.put("QryType", "Fore");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	//查询店铺全部商品
	@RequestMapping("/selectAllGoods.action")	
	public Map selectAllGoods(String XmlData) throws Exception {	
		System.out.println("XmlData="+XmlData);
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.selectAllGoods");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		//map.put("pagesize", "50");
		System.out.println("map="+map);
		return map;
	}
	
	@RequestMapping("/selectAllGoodsSUM.action")
	public Map selectAllGoodsSUM(String XmlData) throws Exception {		
		System.out.println("XmlData="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);
		map.put("SPNAMELIST", JLTools.spitWord(map.get("SPNAMELIST").toString()));
		map.put("sqlid", "com.jlsoft.o2o.PCproduct.selectAllGoodsSUM");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		//map.put("pagesize", "50");
		return map;
	}
	
}