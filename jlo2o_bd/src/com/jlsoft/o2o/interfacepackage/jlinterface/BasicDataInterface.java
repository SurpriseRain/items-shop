package com.jlsoft.o2o.interfacepackage.jlinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
@RequestMapping("/BasicDataInterface")
public class BasicDataInterface extends JLBill{
	/**
	 * @todo 根据行业管理码获取ZCXX01
	 * @param zcxx01
	 * @return
	 */
	public String getHYGLM(String hyglm){
		String zcxx01="";
		String sql="SELECT ZCXX01 FROM W_ZCGS WHERE HYGLM='"+hyglm+"'";
		Map map = queryForMap(o2o,sql);
		zcxx01=map.get("ZCXX01").toString();
		return zcxx01;
	}
	
	/**
	 * @todo 查询商品信息
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/srchSPXX")
	public Map<String,Object> srchSPXX(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String ywlx = "SPXX";
			String sjly =  cds.getField("sjly", 0);
			String hyglm = cds.getField("hyglm", 0);
			String ZCXX01 = getHYGLM(hyglm);
			String sql = "SELECT B.SPGL24,A.GGXH,A.SPXX02,A.SPXX04,A.PPB01,A.PPB02,A.JLDW01,"+
							   "(SELECT JLDW02 FROM W_JLDW WHERE JLDW01=A.JLDW01) JLDW02,"+
							   "(SELECT BARCODE FROM W_GOODS WHERE SPXX01=A.SPXX01) BARCODE,"+
							   "CASE WHEN B.SPGL12=1 OR B.SPGL12=3 THEN 1 ELSE 0 END AS YXBJ,B.BZ "+
							   "FROM W_SPXX A,W_SPGL B WHERE A.SPXX01=B.SPXX01 AND B.ZCXX01='"+ZCXX01+"'";
			String time01 = getTIMESTAMP(ZCXX01,sjly,ywlx);
			if(!time01.equals("")){
				sql = sql + " AND B.TIME01>'"+time01+"'";
			}
			List list = queryForList(o2o,sql,resultMap);
			resultMap.put("spxxList", list==null?"":list);
			resultMap.put("ywlx",ywlx);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	@RequestMapping("/srchPP")
	public Map<String,Object> srchPP(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String ywlx = "PP";
			String hyglm = cds.getField("hyglm", 0);
			String sjly =  cds.getField("sjly", 0);
			String ZCXX01 = getHYGLM(hyglm);
			String sql="SELECT PPB01,PPB02,YXBJ FROM PPB WHERE ZCXX01='"+ZCXX01+"' AND PPB04=1";
			String time01 = getTIMESTAMP(ZCXX01,sjly,ywlx);
			if(!time01.equals("")){
				sql = sql + " AND TIME01>'"+time01+"'";
			}
			
			List list = queryForList(o2o,sql,resultMap);
			resultMap.put("ppList", list==null?"":list);
			resultMap.put("ywlx",ywlx);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	@RequestMapping("/srchJLDW")
	public Map<String,Object> srchJLDW(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String ywlx="JLDW";
			String sjly =  cds.getField("sjly", 0);
			String sql="SELECT JLDW01,JLDW02,JLDW03 YXBJ FROM W_JLDW WHERE JLDW03=1";
			//String time01 = getTIMESTAMP("",sjly,ywlx);
			String time01 = "";
			if(!time01.equals("")){
				sql = sql + " AND TIME01>'"+time01+"'";
			}
			
			List list = queryForList(o2o,sql,resultMap);
			resultMap.put("jldwList", list==null?"":list);
			resultMap.put("ywlx",ywlx);
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	@RequestMapping("/srchSPFL")
	public Map<String,Object> srchSPFL(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String ywlx="SPFL";
			String sjly =  cds.getField("sjly", 0);
			String sql="SELECT SPFL01 FLDM,SPF_SPFL01 SJFL,SPFL02 FLMC,SPFL03 FLJB,SPFL04 MJBJ,YXBJ FROM SPFL ";
			String time01 = getTIMESTAMP("",sjly,ywlx);
			if(!time01.equals("")){
				sql = sql + " AND TIME01>'"+time01+"'";
			}
			sql = sql + "ORDER BY SPFL01";
			
			List list = queryForList(o2o,sql,resultMap);
			resultMap.put("spflList", list==null?"":list);
			resultMap.put("ywlx","SPFL");
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	@RequestMapping("/srchCK")
	public Map<String,Object> srchCK(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String sql="SELECT CK01 CKDM,CK02 CKMC,1 YXBJ FROM W_DQCK";
			List list = queryForList(o2o,sql,resultMap);
			resultMap.put("ckList", list==null?"":list);
			resultMap.put("ywlx","CK");
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 获取最新时间戳
	 * @param zcxx01 注册编号
	 * @param ywlx 业务类型
	 * @return
	 * @throws Exception
	 */
	public String getTIMESTAMP(String zcxx01,String sjly,String ywlx) throws Exception{
		String timesTamp = "";
		try{
			ywlx = sjly+"_"+ywlx;
			String sql = "SELECT TIME01 FROM W_TIMESTAMP WHERE YWLX='"+ywlx+"'";
			if(!zcxx01.equals("")){
				sql = sql + " AND ZCXX01='"+zcxx01+"'";
			}
			Map row = new HashMap();
			Map map 	= queryForMap(o2o,sql,row);
			if(map !=null && map.get("TIME01") != null && !map.get("TIME01").equals("")){
				timesTamp = map.get("TIME01").toString();
			}
		}catch(Exception ex){
			throw ex;
		}
		return timesTamp;
	}
	
	/**
	 * @todo 更新时间戳
	 * @param json，包括（hyglm:行业管理码 sjly:数据来源 ywlx:业务类型）
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateTIMESTAMP")
	public Map<String,Object> updateTIMESTAMP(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String sql="";
			String zcxx01="";
			String hyglm = cds.getField("hyglm", 0);
			String ywlx = cds.getField("sjly", 0)+"_"+cds.getField("ywlx", 0);
			String srchPara = "AND YWLX='"+ywlx+"' ";
			if(hyglm != null && !hyglm.equals("")){
				zcxx01 = getHYGLM(hyglm);
				srchPara = srchPara + "AND ZCXX01='"+zcxx01+"'";
			}
			sql="SELECT COUNT(0) FROM W_TIMESTAMP WHERE 1=1 "+srchPara+"";
			int num = queryForInt(o2o,sql);
			if(num==0){
				sql = "INSERT INTO W_TIMESTAMP(ZCXX01,YWLX,TIME01) VALUES('"+zcxx01+"','"+ywlx+"',nowts())";
			}else{
				sql = "UPDATE W_TIMESTAMP SET TIME01=nowts() WHERE ZCXX01='"+zcxx01+"' AND YWLX='"+ywlx+"'";
			}
			execSQL(o2o, sql, resultMap);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
}