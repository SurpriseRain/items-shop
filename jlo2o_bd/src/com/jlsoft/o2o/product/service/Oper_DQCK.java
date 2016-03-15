package com.jlsoft.o2o.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.pub.service.PubService;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/Oper_DQCK")
public class Oper_DQCK extends JLBill{
	@Autowired
	private PubService pubService;
	@Autowired
	private V9BasicData v9BasicData;
	
	public Oper_DQCK() {
	}
	public Oper_DQCK(JdbcTemplate o2o){
		this.o2o = o2o;
	}
	//查询所有的大区代码和名称
	@RequestMapping("/selectTotalDQCK.action")
    public Map selectTotalDQCK(String XmlData) throws Exception {
		String sql="";
		if(null==XmlData||"".equals(XmlData)){
			sql = "SELECT TRIM(d.dqbzm01) dqbzm01, TRIM(d.dqbzm02) dqbzm02,d.dqbzm03 isend,case when dqbzm_dqbzm01 is null or dqbzm_dqbzm01='' then 0 else  TRIM(dqbzm_dqbzm01) end  pid from dqbzm d" +
					" where (d.dqbzm03=1 or d.dqbzm03=2) order by 1;";
		}
		List spfllist = queryForList(o2o,sql);
    	Map map = new HashMap();
    	map.put("spfllist", spfllist);
		return map;
    }
	//查询地区的所有仓库
	@RequestMapping("/selectDQCKName.action")
    public Map selectDQCKName(String dqcode) throws Exception {
		String sql="";
		if(null!=dqcode &&!"".equals(dqcode)){
			sql = "SELECT ck02 ckname from w_dqck  where dqxx01 in ('"+dqcode+"');";
		}
		List cklist = queryForList(o2o,sql);
    	Map map = new HashMap();
    	map.put("cklist", cklist);
		return map;
    }
	
	/**
	 * 大区插入仓库
	 * @param JsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertDQCKList.action")
	public Map<String, Object> insertDQCKList(String JsonData) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds = new DataSet(JsonData);
		try {
			//获取仓库名称
			String ckdm_name = cds.getField("CKNAME", 0);
			// 转换为仓库名称的数组，通过 "," 进行分割
			String ckdm_names[] = ckdm_name.split(",");
			// 获取仓库代码
			String ckdm_val = cds.getField("CKDM", 0);
			// 转换为仓库代码的数组，通过 "," 进行分割
			String ckdm_vals[] = ckdm_val.split(",");
			
			String sql = "";
			// 先执行全部删除操作，这样可以便于修改
			sql = "DELETE FROM W_DQCK WHERE dqxx01 = '" + cds.getField("DQBM", 0) +  "' ";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			// 判断是否为空
			if(!"".equals(ckdm_val)){
				// 循环执行插入操作
				for (int i = 0; i < ckdm_vals.length; i++) {
					sql = "INSERT INTO W_DQCK (dqxx01,ck01,ck02) VALUES('" + cds.getField("DQBM", 0) +  "','" + ckdm_vals[i] + "','" + ckdm_names[i]+ "')";
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
