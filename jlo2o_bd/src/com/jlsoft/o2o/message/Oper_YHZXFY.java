package com.jlsoft.o2o.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.framework.dataset.DataSet;

/**
 * 
 * @breif 查询所有的信息
 * 
 */
@Controller
@RequestMapping("/message/jlquery")
public class Oper_YHZXFY extends JLBill {
	private SqlSession session = null;
	// 查询所有收藏商品的信息
	@RequestMapping("/selectAllSCSP.action")
	public Map selectAllSCSP(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.user.sql.MyCollection.selectAllSCSP");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}
	// 查询所有收藏店铺的信息
	@RequestMapping("/selectAllSCDP.action")
	public Map selectAllSCDP(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.user.sql.MyCollection.selectAllSCDP");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}
	// 查询所有收藏品牌的信息
	@RequestMapping("/selectAllSCPP.action")
	public Map selectAllSCPP(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.user.sql.MyCollection.selectAllSCPP");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}
	// 查询所有系统消息
	@RequestMapping("/selectAllXT.action")
	public Map selectAllXT(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.message.sql.Oper_XiaoXiGL.selectAllXT");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}

	// 查询单条系统消息
	@RequestMapping("/selectByid_xt.action")
	public Map selectByid_xt(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid",
				"com.jlsoft.o2o.message.sql.Oper_XiaoXiGL.selectByid_xt");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}

	// 查询所有卖家咨询消息
	@RequestMapping("/selectAllZX.action")
	public Map selectAllZX(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.message.sql.Oper_XiaoXiGL.selectAllZX");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}

	// 查询所有买家咨询消息
	@RequestMapping("/selectAllZX_maijia.action")
	public Map selectAllZX_maijia(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid",
				"com.jlsoft.o2o.message.sql.Oper_XiaoXiGL.selectAllZX_maijia");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}

	// 查询单条咨询消息
	@RequestMapping("/selectByid_zx.action")
	public Map selectByid_zx(String XmlData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid",
				"com.jlsoft.o2o.message.sql.Oper_XiaoXiGL.selectByid_zx");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}

	/*---------------------------------------------------------------------------------*/
	// 查询留言
	@RequestMapping("/selectAllGoods.action")
	public Map selectAllGoods(String XmlData) throws Exception {
		System.out.println("XmlData=" + XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list = mapper.readValue(XmlData, ArrayList.class);
		Map map = (Map) list.get(0);
		map.put("sqlid", "com.jlsoft.o2o.message.sql.Oper_YHZX.selectAllGoods");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Report");
		map.put("dataType", "Json");
		System.out.println("map=" + map);
		return map;
	}

	// 删除单条消息记录
	@RequestMapping("/deleteXX.action")
	public Map deleteXX(String XmlData) throws Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		String sql = "DELETE FROM usermessage WHERE id='"
				+ cds.getField("id", 0) + "' ";
		Map row = getRow(sql, null, 0);
		execSQL(o2o, sql, row);
		map.put("STATE", 1);
		return map;
	}
}