/**********************************************************************
 * @file	HomeDisplay.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	赵明亮/20140314
 * @author	Code:	
 * @author	Modify:	
 * @copy	Tag:	金力软件
 **********************************************************************/

package com.jlsoft.o2o.product.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;

/**
 * 
 * @breif 各类商品展示
 *
 */
@Controller
@RequestMapping("/QFYdisplay/jlquery")
public class QFYdisplay extends JLBill{
	private SqlSession session=null;
	
	@RequestMapping("/qfylistitem.action")	
	public Map qfylistitem(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.QFYproduct.qfylistitem");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");//Report
		map.put("dataType", "Json");
		map.put("pagesize", "20");
		return map;
	}
	
	@RequestMapping("/qfylistitemsum.action")	
	public Map qfylistitemsum(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();	
		List list =  mapper.readValue(XmlData, ArrayList.class); 	
		Map map = (Map)list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.QFYproduct.qfylistitemsum");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		map.put("pagesize", "20");
		return map;
	}
	
}
