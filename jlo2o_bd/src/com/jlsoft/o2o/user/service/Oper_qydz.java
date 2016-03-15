/**********************************************************************
 * @file	UserLogin.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	赵明亮/20140318
 * @author	Code:	赵明亮/20140318
 * @author	Modify:	
 * @copy	Tag:	金力软件
 **********************************************************************/
package com.jlsoft.o2o.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;

@Controller
@RequestMapping("/oper_qydz")
public class Oper_qydz extends JLBill{
	
	// 获取区域信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/showQY.action")
	public List<Map<String,Object>> showQy(String XmlData) throws Exception{
		JSONArray jsonList = JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>) jsonList.get(0);
		List<Map<String, Object>> list = null;
		SqlSession session = null;
		try{
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		list = session.selectList("com.jlsoft.o2o.user.sql.select_zcgs_qy", hm);
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw e;
	    }finally{
	    	session.close();
	    }
		return list;
	}
	
	/**
	 * 汽服云区域查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/qryDQ.action")
	public List<Map<String,Object>> qryDQ(String XmlData) throws Exception{
		JSONArray jsonList = JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>) jsonList.get(0);
		List<Map<String, Object>> list = null;
		SqlSession session = null;
		try{
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		list = session.selectList("com.jlsoft.o2o.user.sql.select_qfydq", hm);
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw e;
	    }finally{
	    	session.close();
	    }
		return list;
	}
	
	@RequestMapping("/selectCode.action")
	public Map<String, Object> selectCode(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		XmlData = request.getParameter("Code");
		Map hm=new HashMap();
		String sql="SELECT dqbzm08 Code FROM dqbzm WHERE dqbzm01 ='"+XmlData+"'";
		hm = (Map) queryForMap(o2o, sql);
		System.out.println(XmlData);
		if(hm != null){
			hm.put("state", "1");
		}else{
			hm.put("state", "0");
		}
		return hm;
	}
	 
}
