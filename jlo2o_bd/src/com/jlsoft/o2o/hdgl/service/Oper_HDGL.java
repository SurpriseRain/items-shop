package com.jlsoft.o2o.hdgl.service; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/Oper_HDGL")
public class Oper_HDGL extends JLBill{
	
	/**
	 * 插入活动
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertActivity.action")
	public Map insertActivity(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(jsonData);
			long activityId = JLTools.getOrderId();
			String activitySql = "INSERT INTO ACTIVITY (ACTIVITYID, "
							   + "ACTIVITYNAME, "
							   + "ACTIVITYTYPEID, "
							   + "ZCXX01, "
							   + "ACTIVITYBUDGET, "
							   + "SPFL01, "
							   + "ACTIVITYSTARTTIME, "
							   + "ACTIVITYENDTIME, "
							   + "ENROLLSTARTTIME, "
							   + "ENROLLENDTIME, "
							   + "STATE) "
							   + "VALUES('" + activityId + "', "
					   		   + "ACTIVITYNAME?, "
				   		   	   + "ACTIVITYTYPEID?, "
				   		   	   + "ZCXX01?, "
				   		   	   + "ACTIVITYBUDGET?, "
				   		   	   + "SPFL01?, "
				   		   	   + "ACTIVITYSTARTTIME?, "
				   		   	   + "ACTIVITYENDTIME?, "
				   		   	   + "ENROLLSTARTTIME?, "
				   		   	   + "ENROLLENDTIME?, "
				   		   	   + "0)";
			Map row = getRow(activitySql, null, 0);
			execSQL(o2o, activitySql, row);
			String fullPrice = cds.getField("ACTIVITYFULLPRICE", 0).replace("[", "")
					.replace("]", "").replace("\"", "");
			String offPrice = cds.getField("ACTIVITYOFFPRICE", 0).replace("[", "")
					.replace("]", "").replace("\"", "");
			// 插入满减表
			String[] fullPriceArray = fullPrice.split(",");
			String[] offPriceArray = offPrice.split(",");
			for (int i = 0; i < fullPriceArray.length; i++) {
				String mjSql = "INSERT INTO ACTIVITYFULLOFFRULE(ACTIVITYID, ACTIVITYFULLPRICE, ACTIVITYOFFPRICE) "
							 + "VALUES('" + activityId + "', " + fullPriceArray[i] + ", " + offPriceArray[i] + ")";
				row = getRow(mjSql, null, 0);
				execSQL(o2o, mjSql, row);
			}
			
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	
	/**
	 * 活动关闭修改状态
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateActivityForState.action")
	public Map updateActivityForState(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(jsonData);
			String sql = "";
			sql = "UPDATE ACTIVITY SET STATE = 2, ACTIVITYREALLYENDTIME = NOW() WHERE ACTIVITYID = " + cds.getField("ACTIVITYID", 0);
			execSQL(o2o, sql, resultMap);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	/**
	 * 查询活动的商品分类
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectHDSPFL.action")
    public Map selectHDSPFL(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "";
			String yxfl = "";
			if(jsonData != null){
				cds = new DataSet(jsonData);
				yxfl = cds.getField("yxfl", 0);
			} else {
				yxfl = "''";
			}
			sql = "SELECT TRIM(F.SPFL01) FLCODE, "
				+ "TRIM(F.SPFL02) FLNAME, "
				+ "CASE WHEN F.SPF_SPFL01 IS NULL OR F.SPF_SPFL01='' THEN 0 ELSE TRIM(F.SPF_SPFL01) END FATHERCODE, "
				+ "TRIM((SELECT SPFL02 FROM SPFL WHERE  SPFL01=F.SPF_SPFL01)) AS FATHERNAME, "
				+ "F.SPFL04 ISEND, "
				+ "A.SPFL01  YXFL, "
				+ "F.SPFL03 "
				+ "FROM SPFL F "
				+ "LEFT JOIN SPFL A ON A.SPFL01 IN (" + yxfl + ") "
				+ "AND A.SPFL01 = F.SPFL01 "
				+ "WHERE F.YXBJ='1' "
				+ "AND F.SPFL03 IN (1, 2, 3) "
				+ "ORDER BY 3";
			List spfllist = queryForList(o2o,sql);
			resultMap.put("spfllist", spfllist);
		} catch (Exception e) {
			
			throw e;
		}
		
		return resultMap;
    }
	/**
	 * 查询活动种类信息
	 * @author NineDragon
	 * @version 2015/11/13
	 * @return returnMap
	 * @throws Exception
	 */
	@RequestMapping("/select_NHDZL.action")
	public Map<String, Object> select_NHDZL() throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String sql = "";
			sql = "SELECT ActivityTypeId,ActivityTypeName,Mark FROM ActivityType WHERE Mark = 1";
			returnMap.put("hdzlList", queryForList(o2o,sql));
		} catch (Exception ex) {
			throw ex;
		}
		return returnMap;
	}

	/**
	 * 查询审批活动
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectSPHD.action")
	public Map selectSPHD(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			cds=new DataSet(jsonData);
			String activityId = cds.getField("ACTIVITYID", 0);
			String sql = "SELECT A.ACTIVITYNAME, "
					   + "A.ACTIVITYBUDGET, "
					   + "DATE_FORMAT(A.ACTIVITYSTARTTIME,'%Y-%m-%d %k:%i:%s') ACTIVITYSTARTTIME, "
					   + "DATE_FORMAT(A.ACTIVITYENDTIME,'%Y-%m-%d %k:%i:%s') ACTIVITYENDTIME, "
					   + "DATE_FORMAT(A.ENROLLSTARTTIME,'%Y-%m-%d %k:%i:%s') ENROLLSTARTTIME, "
					   + "DATE_FORMAT(A.ENROLLENDTIME,'%Y-%m-%d %k:%i:%s') ENROLLENDTIME, "
					   + "B.ACTIVITYFULLPRICE ACTIVITYFULLPRICE, "
					   + "B.ACTIVITYOFFPRICE ACTIVITYOFFPRICE, "
					   + "(SELECT GROUP_CONCAT(C.SPFL02) FROM SPFL C WHERE FIND_IN_SET(C.spfl01, A.SPFL01) > 0) SPFL02, "
					   + "A.STATE ACTIVITYSTATE,"
					   + "A.ACTIVITYVIEWS ACTIVITYVIEWS "
					   + "FROM ACTIVITY A "
					   + "LEFT JOIN ACTIVITYFULLOFFRULE B ON A.ACTIVITYID = B.ACTIVITYID "
					   + "WHERE A.ACTIVITYID = " + activityId;
			List spfllist = queryForList(o2o,sql);
			resultMap.put("spfllist", spfllist);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
    }

	/**
	 * 更新活动状态 --- 通过ID
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateActivitySHByID.action")
	public Map<String, Object> updateActivitySHByID(String jsonData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(jsonData);
			String sql = "UPDATE ACTIVITY SET ACTIVITYVIEWS = ACTIVITYVIEWS?, STATE = ACTIVITYSTATE? WHERE ACTIVITYID = ACTIVITYID?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}

	/**
	 * 批量更新审批状态-审核通过
	 * @author NineDragon
	 * @version 2015/11/13
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping("/updateByid_HDSP.action")
	public Map<String, Object> updateByid_HDSP(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sql = "UPDATE ActivityProduct SET State=1 WHERE ActivityShopId='" + cds.getField("ActivityShopId", 0)+"' AND SPXX01='" + cds.getField("SPXX01", 0)+"' ";
			Map row = getRow(sql, null, 0);
			int i = execSQL(o2o, sql, row);
			String sql1 = "UPDATE w_spgl SET SPGL29=1 WHERE   SPXX01='" + cds.getField("SPXX01", 0)+"' ";
			Map row1 = getRow(sql1, null, 0);
			int i1 = execSQL(o2o, sql1, row1);
			map.put("STATE", i1);
			
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	/**
	 * 批量更新审批状态-驳回
	 * @author NineDragon
	 * @version 2015/11/13
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping("/updateByid_HDSP_BH.action")
	public Map<String, Object> updateByid_HDSP_BH(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "UPDATE ActivityProduct SET State=2,activityProductViews='" + cds.getField("activityProductViews", 0)+"' WHERE ActivityShopId='" + cds.getField("ActivityShopId", 0)+"' AND SPXX01='" + cds.getField("SPXX01", 0)+"' ";
		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);
		map.put("STATE", i);
		return map;
	}
	/**
	 * 查询商品信息用于商品详情页的跳转
	 * @author NineDragon
	 * @version 2015/11/13
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping("/select_NSPTZ.action")
	public Map select_NSPTZ(String XmlData) throws Exception {
			cds=new DataSet(XmlData);
			String s= cds.getField("spxx01", 0);
			String sql=" SELECT a.zcxx01,b.zcxx02  FROM w_goods a, w_zcxx b WHERE spxx01='"+s+"' AND a.zcxx01=b.zcxx01   ";
			List spfllist = queryForList(o2o,sql);
	    	Map map = new HashMap();
	    	map.put("spfllist", spfllist);
			return map;
	    }
	
	
	/**
	 * 活动报名
	 * @author NineDragon
	 * @version 2015/11/13
	 * @return returnMap
	 * @throws Exception
	 */
	 @RequestMapping("/insertActivityBM.action")
	 public Map insertActivityBM(String jsonData) throws Exception {
	  Map resultMap = new HashMap();
	  try {
	   cds = new DataSet(jsonData);
	   //活动店铺编码   ActivityShopId
	   long activityId = JLTools.getOrderId();
	   long ActivityShopId= activityId;
	   //ActivityId 
	   String ActivityId= cds.getField("ActivityId", 0);
	   //注册公司ID
	   String ZCXX01= cds.getField("ZCXX01", 0);
	   //联系人
	   String ContactPerson= cds.getField("ContactPerson", 0);
	   //电话
	   String ContactPhone= cds.getField("ContactPhone", 0);
	   //邮箱
	   String Email= cds.getField("Email", 0);
	   String QQ= cds.getField("QQ", 0);
	   //商品spxx01和参加数量
	   String ActivityNumber= cds.getField("ActivityNumber", 0);
	   String activitySql = "  INSERT INTO ActivityShop (ActivityShopId,ActivityId,ZCXX01,ContactPerson,ContactPhone,Email,QQ )VALUES( '"+ActivityShopId+"', '"+ActivityId+"','"+ZCXX01+"', '"+ContactPerson+"', '"+ContactPhone+"' ,'"+Email+"','"+QQ+"') ";
	   System.out.println(activitySql);
	   Map row = getRow(activitySql, null, 0);
	   execSQL(o2o, activitySql, row);
	   // 插入满减表
	   
	   JSONArray array = JSONArray.fromObject(ActivityNumber);
	   for(int i = 0; i < array.size(); i++){
		   JSONObject obj = (JSONObject) array.get(i);
			 Object productId = obj.get("productId");
			 Object num = obj.get("num");

			 String mjSql = "INSERT INTO ActivityProduct(ActivityShopId, ActivityId, ZCXX01,SPXX01,ActivityNumber,ActivitySYNumber,State) VALUES('"+ActivityShopId+"', '"+ActivityId+"','"+ZCXX01+"', " + productId + ", '"+num+"','"+num+"', 0)";
			 row = getRow(mjSql, null, 0);
			 execSQL(o2o, mjSql, row);
	   }
	   resultMap.put("STATE", "1");
	  } catch (Exception e) {
	   resultMap.put("STATE", "0");
	   throw e;
	  }
	  return resultMap;
	 }
		/**
		 * 查询活动的商品的状态
		 * @author NineDragon
		 * @version 2015/11/13
		 * @return returnMap
		 * @throws Exception
		 */
		@RequestMapping("/select_SHBH.action")
	    public Map select_SHBH(String XmlData) throws Exception {
			Map resultMap = new HashMap();
			try {
				String sql = "";
				String ActivityShopId = "";
				String SPXX01 = "";
				cds = new DataSet(XmlData);
				ActivityShopId = cds.getField("ActivityShopId", 0);
				SPXX01 = cds.getField("SPXX01", 0);
				sql = "SELECT State FROM activityproduct WHERE ActivityShopId="+ActivityShopId+" AND SPXX01="+SPXX01+"";
				List hdlist = queryForList(o2o,sql);
				resultMap.put("hdlist", hdlist);
			} catch (Exception e) {
				throw e;
			}
			return resultMap;
	    }
}
 