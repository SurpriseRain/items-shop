package com.jlsoft.o2o.qxgl.service; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/Oper_FWQYGL")
public class Oper_FWQYGL extends JLBill{

	
	/**
	 * 企业审核厂商邀请
	 */
	@RequestMapping("/auditSQPP.action")
	public Map<String, Object> auditSQPP(String jsonData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(jsonData);
			String ztid = cds.getField("ZTID", 0);
			String hbid = cds.getField("HBID", 0);
			String state = cds.getField("STATE", 0);
			String spyj = cds.getField("SPYJ", 0);
			if (state.equals("1")) {
				insert_DQXX(jsonData);
			} 
			String sql = "UPDATE W_GSGX SET STATE = '"+state+"', Notes = '"+spyj+"'"
			             +"WHERE ztid = '"+ztid+"' AND hbid = '"+hbid+"'";
			Map rowPPB = getRow(sql, null, 0);
			execSQL(o2o, sql, rowPPB);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	 
	/**
	 * 经销企业审核厂商邀请,针对大区过滤,规则,经销商在一个区域只有一个条记录,type不同可以重复
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Map<String, Object> insert_DQXX(String jsonData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(jsonData);
			String dqbm = cds.getField("DQBM", 0);
			//新增的大区编码
			List<String> Nalist = Arrays.asList(dqbm.split(","));
			List<String> Nlist = new ArrayList<String>();
			for (int i = 0; i < Nalist.size(); i++) {
				String cityId = Nalist.get(i);
				if("0000".equals(cityId.substring(2))){
					String citySql = "SELECT DQBZM01 DQXX01 "
								+ "FROM DQBZM WHERE DQBZM_DQBZM01 = '" + cityId +  "' "
								+ "AND DQBZM03 = 2 ORDER BY DQBZM01";
					List cList = queryForList(o2o, citySql);
					for (int j = 0; j < cList.size(); j++){
						 Map map = (Map)cList.get(j);
						 String s =  (String) map.get("DQXX01");
						 Nlist.add(s);
					}
				} else {
					Nlist.add(cityId);
				}
			}
			String hbid = cds.getField("HBID", 0);
			String sql = "SELECT a.dqxx01 FROM w_dqck a LEFT JOIN ck b ON a.ck01 = b.ck01 LEFT JOIN w_gsck c ON c.CK01 = b.ck01  "
					   + "WHERE a.type = '2' AND c.zcxx01 ='" + hbid + "'";
			//经销企业原来的大区编码
			List  list = queryForList(o2o, sql);
			List<String> Odlist=new ArrayList<String>();
			for (int i = 0; i < list.size(); i++){
				 Map map = (Map)list.get(i);
				 String s =  (String) map.get("dqxx01");
				 Odlist.add(s);
			}
			Set<String> set=new HashSet<String>(); 
			set.addAll(Odlist);
			String SQL = "";
			//比较大区编码是否重复
					for (int j = 0; j < Nlist.size(); j++) {
						if (!set.contains(Nlist.get(j))){
							SQL = "INSERT INTO W_DQCK(DQXX01,CK01,DQXX02,CK02,TYPE) " +
								     " SELECT '"+Nlist.get(j)+"', A.ck01, IFNULL(CONCAT(D.DQBZM02,C.DQBZM02),C.DQBZM02),A.ck02,2 " +
								     " FROM	ck a LEFT JOIN w_gsck b ON a.ck01 = b.CK01 " +
								     " LEFT JOIN dqbzm c on c.dqbzm01 = '"+Nlist.get(j)+"'  " +
								     " LEFT JOIN dqbzm d ON c.dqbzm_dqbzm01 = d.dqbzm01 " +
								     " WHERE b.zcxx01 = '"+hbid+"' AND A.CK09 = 0 AND A.TYPE = 0 ";
								Map row1 = getRow(SQL, null, 0);
								execSQL(o2o, SQL, row1);
						}
					}
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}
	
	//查询经销商的品牌信息
	@RequestMapping("/selectPP.action")
	public Map<String, Object> selectKXPP(String jsonData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(jsonData);
			String sql = "SELECT A.PPB01 PPBH, C.PPB02 PPMC FROM W_GSGXPP A LEFT JOIN W_PPQX B ON A.PPB01 = B.PPB01 LEFT JOIN PPB C ON A.PPB01 = C.PPB01 "
					   + "WHERE A.HBID = '" + cds.getField("ZCXX01", 0) + "' AND B.STATE = 1 ";
			List resultList = queryForList(o2o, sql);
			resultMap.put("resultList", resultList);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	
	//查询经销商企业的授权区域
	@RequestMapping("/selectFWQYQY.action")
	public Map<String, Object> selectGSGXQY(String jsonData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(jsonData);
			String sql = "SELECT DISTINCT A.DQBZM01, C.DQBZM02 PROV, B.DQBZM02 "
					   + "FROM W_GSGXQY A "
					   + "LEFT JOIN DQBZM B "
					   + "ON A.DQBZM01 = B.DQBZM01 "
					   + "LEFT JOIN DQBZM C "
					   + "ON B.DQBZM_DQBZM01 = C.DQBZM01 "
					   + "WHERE A.HBID = '" + cds.getField("ZCXX01", 0) + "'";
			List resultList = queryForList(o2o, sql);
			resultMap.put("resultList", resultList);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	 
}
 