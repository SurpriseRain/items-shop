package com.jlsoft.o2o.interfacepackage.jlinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Service("transformRuleSercice")
public class PubSPCM extends JLBill{
	private Logger logger=Logger.getLogger(PubSPCM.class);
	
	/**
	 * @todo 判断串码临时表中，串码是否已存在
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public int checkW_T_SPCMExist(String json) throws Exception{
		int num=0;
		try{
			cds = new DataSet(json);
			String spcm01=cds.getField("spcm01", 0);
			String sql = "SELECT COUNT(0) FROM W_T_SPCM WHERE SPCM01='"+spcm01+"'";
			num = queryForInt(o2o,sql);
		}catch(Exception ex){
			throw ex;
		}
		return num;
	}
	
	/**
	 * @todo 判断该公司下是否有该商品
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public int checkW_SPGLExist(String json) throws Exception{
		int num=0;
		try{
			cds = new DataSet(json);
			String spxx01=cds.getField("spxx01", 0);
			String xtczy01=cds.getField("xtczy01", 0);
			String sql="SELECT COUNT(0) FROM W_SPGL A,W_XTCZY B WHERE A.ZCXX01=B.GSID " +
							 "AND B.PERSON_ID='"+xtczy01+"' AND A.SPXX01='"+spxx01+"'";
			num = queryForInt(o2o,sql);
		}catch(Exception ex){
			throw ex;
		}
		return num;
	}
	
	/**
	 * @todo 插入串码存放临时表
	 * @param json
	 * @throws Exception
	 */
	public void insertW_T_SPCM(String json) throws Exception{
		try{
			cds = new DataSet(json);
			String sql = "INSERT INTO W_T_SPCM(XTCZY01,YWLX,SPXX01,BARCODE,SPCM01,DRSJ)" +
							   " VALUES(xtczy01?,ywlx?,spxx01?,barcode?,spcm01?,now())";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 根据操作人和业务类型查询关联的临时数据
	 * @param json
	 * @throws Exception
	 */
	public List selectW_T_SPCM(String json) throws Exception{
		List list = new ArrayList();
		try{
			cds = new DataSet(json);
			String sql = "SELECT A.SPXX01,B.SPXX02,B.SPXX04,A.BARCODE,A.SPCM01,date_format(A.DRSJ,'%Y-%m-%d %h:%m:%s') DRSJ " +
							   "FROM W_T_SPCM A,W_SPXX B WHERE A.SPXX01=B.SPXX01 AND A.XTCZY01='"+cds.getField("xtczy01", 0)+"' AND A.YWLX='"+cds.getField("ywlx", 0)+"'";
			Map row = getRow(sql, null, 0);
			list = queryForList(o2o,sql,row);
		}catch(Exception ex){
			throw ex;
		}
		return list;
	}
	
	/**
	 * @todo 查询商品串码
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public List selectW_SPCM(String json) throws Exception{
		List list = new ArrayList();
		try{
			cds = new DataSet(json);
			String zcxx01 = cds.getField("zcxx01", 0);
			String barCode = cds.getField("barCode", 0);
			String startTime = cds.getField("startTime", 0);
			String endTime = cds.getField("endTime", 0);
			String sql = "SELECT B.SPXX01,B.SPXX02,B.SPXX04,A.SPCM01,A.BARCODE,date_format(A.SPCM02,'%Y-%m-%d %h:%m:%s') DRSJ," +
					           "A.SPCM03 PCH,A.SPCM04 XLH FROM W_SPCM A,W_SPXX B WHERE A.SPXX01=B.SPXX01 AND A.ZCXX01='"+zcxx01+"' ";
			if(barCode != null && !barCode.equals("")){
				sql = sql + "AND A.BARCODE='"+barCode+"' ";
			}
			if(startTime != null && !startTime.equals("")){
				sql = sql + "AND A.SPCM02=STR_TO_DATE('"+startTime+"','%Y-%m-%d %k:%i:%s') ";
			}
			if(endTime != null && !endTime.equals("")){
				sql = sql + "AND A.SPCM02=STR_TO_DATE('"+endTime+"','%Y-%m-%d %k:%i:%s') ";
			}
			sql = sql + "ORDER BY A.ZCXX01,B.SPXX02";
			Map map = new HashMap();
			list = queryForList(o2o,sql,map);
		}catch(Exception ex){
			throw ex;
		}
		return list;
	}
	
	/**
	 * @todo 删除串码单条数据
	 * @param json
	 * @throws Exception
	 */
	public void deleteW_T_SPCMForSPCM(String json) throws Exception{
		try{
			cds = new DataSet(json);
			String sql = "DELETE FROM W_T_SPCM WHERE SPCM01=spcm01?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 删除单个操作员、业务类型的临时数据
	 * @param json
	 * @throws Exception
	 */
	public void deleteW_T_SPCMForCZY(String json) throws Exception{
		try{
			cds = new DataSet(json);
			String sql = "DELETE FROM W_T_SPCM WHERE XTCZY01=userName? AND YWLX=YWLX?";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 判断该条码商品是否存在
	 * @param zcxx01
	 * @param barCode
	 * @return
	 */
	public Map<String,Object> checkSPXXExist(String barCode){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sql = "SELECT SPXX01,ZCXX01 FROM W_GOODS WHERE BARCODE='"+barCode+"'";
		resultMap = (Map)queryForMap(o2o,sql);
		return resultMap;
	}
	
	/**
	 * @todo 获取商品信息
	 * @param zcxx01
	 * @param spxx01
	 * @return
	 */
	public Map<String,Object> srchSPXX(String zcxx01,int spxx01) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String sql="SELECT A.ZCXX01,A.SPXX01,C.SPXX02,C.SPXX04,B.BARCODE,C.PPB01,C.PPB02,D.ZCXX02,A.SPGL04 SPJG,C.SPXH01,"+
							  "D.ZCXX08 ADDRESS,D.ZCXX06 SJHM,D.ZCXX55 ZJHM "+
							  "FROM W_SPGL A,W_GOODS B,W_SPXX C,W_ZCGS D WHERE A.SPXX01=B.SPXX01 "+
							  "AND A.ZCXX01=B.ZCXX01 AND A.SPXX01=C.SPXX01 AND A.ZCXX01=D.ZCXX01 "+
							  "AND A.SPXX01="+spxx01+" AND A.ZCXX01='"+zcxx01+"'";
			resultMap = (Map)queryForMap(o2o,sql);
		}catch(Exception ex){
			logger.error("Error to getSPXX."+ex);
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 根据商品串码查询相应商品内码、公司ID
	 * @param spcm01
	 * @return
	 */
	public Map<String,Object> srchSPCM(String spcm01){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String sql="SELECT ZCXX01,SPXX01,SPCM01,BARCODE FROM W_SPCM WHERE SPCM01='"+spcm01+"'";
		resultMap = (Map)queryForMap(o2o,sql);
		return resultMap;
	}
	
	/**
	 * @todo 根据串码查询该配件出入库记录
	 * @param spcm01
	 * @return
	 */
	public List srchSPCMJLB(String spcm01){
		String sql = "SELECT A.CMZT,(SELECT ZCXX02 FROM W_ZCGS WHERE ZCXX01=A.ZCXX01) ZCXX02,date_format(CMCLSJ,'%Y-%m-%d %h:%m:%s') CMCLSJ "+
							"FROM W_SPCMJLB A WHERE A.SPCM01='"+spcm01+"'";
		return (List)queryForList(o2o,sql);
	}
	
	/**
	 * @todo 从临时表中获取数据，存入串码记录表
	 * @param map
	 * @return
	 */
	public int insertW_SPCMJLBForTemp(Map map) throws Exception{
		int drsl=0;
		String sql="";
		try{
			//插入串码记录表
			int DJBH = Integer.parseInt(map.get("SPCMDR01").toString());
			int DJLX = Integer.parseInt(map.get("DJLX").toString());
			String YWLX = map.get("YWLX").toString(); //业务类型
			String ZCXX01 = map.get("ZCXX01").toString(); //注册信息
			String CMCLRY = map.get("userName").toString(); //操作处理人员XTCZY01
			String CMZT = map.get("CMZT").toString(); //串码状态
			sql = "INSERT INTO W_SPCMJLB(ZCXX01,DJBH,DJLX,SPXX01,BARCODE,SPCM01,CMCLSJ,CMCLRY,CMZT) " +
					"SELECT '"+ZCXX01+"' ZCXX01,"+DJBH+" DJBH,"+DJLX+" DJLX,SPXX01,BARCODE,SPCM01,now(),'"+CMCLRY+"' CMCLRY," +
					 "'"+CMZT+"' CMZT FROM W_T_SPCM WHERE YWLX='"+YWLX+"' AND XTCZY01='"+CMCLRY+"'";
			execSQL(o2o,sql,map);
			//获取插入数量
			sql = "SELECT COUNT(0) FROM W_SPCMJLB WHERE ZCXX01='"+ZCXX01+"' AND DJBH="+DJBH+"";
			drsl = queryForInt(o2o,sql);
		}catch(Exception ex){
			throw ex;
		}
		return drsl;
	}
	
	/**
	 * @todo 插入商品串码记录表W_SPCMJLB
	 * @param map
	 * @return
	 */
	public int insertW_SPCMJLB(Map map) throws Exception{
		int drsl=0;
		String sql="";
		try{
			List spcmList = (List)map.get("SPCMList");
			Map mxMap=null;
			int DJBH = Integer.parseInt(map.get("SPCMDR01").toString());
			int DJLX = Integer.parseInt(map.get("DJLX").toString());
			String ZCXX01 = map.get("ZCXX01").toString();
			String CMCLRY = map.get("userName").toString();
			String CMZT = map.get("CMZT").toString();
			for(int i=0;i<spcmList.size();i++){
				mxMap = (Map)spcmList.get(i);
				int SPXX01 = Integer.parseInt(mxMap.get("SPXX01").toString());
				String BARCODE = mxMap.get("BARCODE").toString();
				String SPCM01 = mxMap.get("SPCM01").toString();
				sql = "INSERT INTO W_SPCMJLB(ZCXX01,DJBH,DJLX,SPXX01,BARCODE,SPCM01,CMCLSJ,CMCLRY,CMZT) "+
					     "VALUES('"+ZCXX01+"',"+DJBH+","+DJLX+","+SPXX01+",'"+BARCODE+"','"+SPCM01+"',now(),'"+CMCLRY+"','"+CMZT+"')";
				execSQL(o2o,sql,map);
				//导入数量加一
				drsl++;
			}
		}catch(Exception ex){
			logger.error("Error to insertW_SPCMJLB."+ex);
			throw ex;
		}
		return drsl;
	}
	
	/**
	 * @todo 插入商品串码导入表W_SPCMDR
	 * @param map
	 */
	public void insertW_SPCMDR(Map map) throws Exception{
		try{
			int DJBH = Integer.parseInt(map.get("SPCMDR01").toString());
			int DRLX = ((Integer)map.get("DRLX")).intValue();
			int DRSL = ((Integer)map.get("DRSL")).intValue();
			String ZCXX01 = map.get("ZCXX01").toString();
			String SBM = map.get("SBM").toString();
			String HYGLM =map.get("HYGLM").toString();
			String sql = "INSERT INTO W_SPCMDR(ZCXX01,SPCMDR01,SBM,HYGLM,DRSL,DRLX,DRRQ) "+
							   "VALUES('"+ZCXX01+"',"+DJBH+",'"+SBM+"','"+HYGLM+"',"+DRSL+","+DRLX+",now())";
			execSQL(o2o,sql,map);
		}catch(Exception ex){
			logger.error("Error to insertW_SPCMDR."+ex);
			throw ex;
		}
	}
	
}