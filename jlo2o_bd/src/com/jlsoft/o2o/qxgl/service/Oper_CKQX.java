package com.jlsoft.o2o.qxgl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
@RequestMapping("/Oper_CKQX")
public class Oper_CKQX extends JLBill {
	
	/**
	 * 权限分配查询仓库状况
	 * @param JsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectCKQXList.action")
	public Map<String, Object> selectCKQXList(String JsonData) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds = new DataSet(JsonData);
		try {
			String sql = "SELECT A.CK01, A.CK02 "
					+ "FROM CK A "
					+ "LEFT JOIN W_XTCZYCK B "
					+ "ON A.CK01 = B.CK01 "
					+ "LEFT JOIN W_XTCZY C "
					+ "ON B.PERSON_ID = C.PERSON_ID "
					+ "LEFT JOIN W_ZCGS D "
					+ "ON D.ZCXX01 = C.GSID "
					+ "WHERE C.PERSON_ID = '" + cds.getField("personid", 0) + "'";
			List ckqxList = queryForList(o2o, sql);	
			returnMap.put("ckqxList", ckqxList);
		} catch (Exception e) {
			throw e;
		}
		return returnMap;
	}
	
	/**
	 * 查询仓库信息显示到页面
	 * @param JsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectCKList.action")
	public Map<String, Object> selectCKList() throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String sql = "SELECT A.CK01, A.CK02 "
					+ "FROM CK A ";
			List ckList = queryForList(o2o, sql);	
			returnMap.put("ckList", ckList);
		} catch (Exception e) {
			throw e;
		}
		return returnMap;
	}

	/**
	 * 插入仓库权限
	 * @param JsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertCKQXList.action")
	public Map<String, Object> insertCKQXList(String JsonData) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds = new DataSet(JsonData);
		try {
			// 获取仓库代码
			String ckdm_val = cds.getField("CKDM", 0);
			// 转换为仓库代码的数组，通过 "," 进行分割
			String ckdm_vals[] = ckdm_val.split(",");
			
			String sql = "";
			// 先执行全部删除操作，这样可以便于修改
			sql = "DELETE FROM W_XTCZYCK WHERE PERSON_ID = '" + cds.getField("RYZH", 0) +  "' ";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			// 判断是否为空
			if(!"".equals(ckdm_val)){
				// 循环执行插入操作
				for (int i = 0; i < ckdm_vals.length; i++) {
					sql = "INSERT INTO W_XTCZYCK VALUES('" + cds.getField("RYZH", 0) +  "','" + ckdm_vals[i] + "')";
					row = getRow(sql, null, 0);
					execSQL(o2o, sql, row);
				}
			}
			returnMap.put("STATE", "1");
		} catch (Exception e) {
			returnMap.put("STATE", "0");
			throw e;
		}
		
		return returnMap;
	}
}
