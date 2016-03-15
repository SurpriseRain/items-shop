package com.jlsoft.o2o.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/YHZX")
public class Oper_YHZX extends JLBill {
	private static final String SPXX01 = null;
	JLTools tool = new JLTools();
	
	// 取消收藏的商品
	@RequestMapping("/deleteSCSP.action")
	public Map<String, Object> deleteSCSP(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "DELETE FROM owncollect WHERE id='"
				+ cds.getField("id", 0) + "' ";
		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);
		map.put("STATE", i);
		return map;
	}

	// 批量删除-系统消息
	@RequestMapping("/deleteByid.action")
	public Map<String, Object> deleteByid(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "DELETE FROM usermessage  WHERE id = id?";
		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);
		map.put("STATE", i);
		return map;
	}

	// 批量更新-系统消息为已读
	@RequestMapping("/updateByid.action")
	public Map<String, Object> updateByid(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "UPDATE usermessage SET MessageState=1 WHERE id=id?";
		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);
		map.put("STATE", i);
		return map;
	}

	// 修改-咨询状态
	@RequestMapping("/update_zx.action")
	public Map<String, Object> update_zx(String XmlData)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		try {
			String sql = "UPDATE usermessage SET MessageState=1 WHERE Id=id?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}

	// 卖家进行回复
	@RequestMapping("/insert_ZXhf.action")
	public Map<String, Object> insert_ZXhf(String XmlData)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		try {
			String sql = "UPDATE usermessage SET Reply=hfnr? WHERE Id=id?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}

	/**
	 * 系统回复消息
	 * 
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@RequestMapping("/system_HF.action")
	public Map<String, Object> system_HF(String ZCXX01, String context)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		try {
			String sql = "INSERT INTO usermessage(Title,SendUserId,ReceiveUserId,Content,MessageCreateTime,MessageState,MessageType) VALUES('车福网','JL',"
					+ ZCXX01 + ",'" + context + "',NOW(),0,0)";
			execSQL(o2o, sql, new HashMap());
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}

	/**
	 * 新增用户咨询
	 * 
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@RequestMapping("/insert_YHZX.action")
	public Map<String, Object> update_SPFB_SPXX(String XmlData)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		try {
			String sql = "INSERT INTO usermessage(SendUserId,ReceiveUserId,Content,MessageCreateTime,AssociateProduceId,MessageState,MessageType) VALUES(fszid?,jszid?,spvalue?,NOW(),SPXX01?,MessageState?,MessageType?)";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}

	/**
	 * 回复
	 * 
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	/*
	 * @RequestMapping("/insert_XiaoXi.action") public Map<String, Object>
	 * insert_XiaoXi(String XmlData) throws DataAccessException, Exception { Map
	 * map = new HashMap(); cds = new DataSet(XmlData); try { String sql =
	 * "INSERT INTO usermessage(SendUserId,ReceiveUserId,Content,MessageCreateTime,AssociateProduceId,MessageState,MessageType) VALUES(fid?,jid?,hfnr?,NOW(),gid?,0,1)"
	 * ; Map row = getRow(sql, null, 0); execSQL(o2o, sql, row);
	 * map.put("STATE", "1"); } catch (Exception e) { e.printStackTrace();
	 * map.put("STATE", "0"); } return map; }
	 */
	/**
	 * 回复
	 * 
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@RequestMapping("/insert_XiaoXi.action")
	public Map<String, Object> insert_XiaoXi(String XmlData)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		try {
			String sql = "UPDATE usermessage SET Reply=hfnr? WHERE Id=id?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}

	/**
	 * 查看修改状态
	 * 
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@RequestMapping("/insert_YHCK.action")
	public Map<String, Object> insert_YHCK(String XmlData)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		try {
			String sql = "UPDATE usermessage SET MessageState=1 WHERE Id=id?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}

}
