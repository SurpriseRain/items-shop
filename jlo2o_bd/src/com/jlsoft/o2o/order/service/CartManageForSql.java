package com.jlsoft.o2o.order.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JlAppResources;

@Controller
public class CartManageForSql extends JLBill {
	private Logger logger=Logger.getLogger(CartManageForSql.class);
	String ROADMAP = JlAppResources.getProperty("ROADMAP");
	/**
	 * @param XmlData
	 * @param ZCXX01
	 *            --主体编码
	 * @param SPXX01
	 *            --商品编码
	 * @return
	 */
	public Map<String, Object> selectKCSL(String XmlData) {
		Map<String, Object> hm = new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			String sql = "SELECT SPGL15 MAXNUM, 	(select ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) from W_KCXX K where k.spxx01= A.SPXX01 and k.zcxx01=A.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01='"+cds.getField("DQXX01", 0)+"')) SPIMGURL"
					+ " FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z,CK"
					+ " WHERE A.SPXX01 = N.SPXX01 "
					+ " AND A.ZCXX01 = N.ZCXX01 "
					+ " AND A.SPXX01 = B.SPXX01 "
					+ " AND A.ZCXX01 = Z.ZCXX01 "
					+" AND K.CK01 = CK.CK01 AND CK.ck09='0'"
					+ " AND A.SPGL12 IN ('1','3') "
					+ " AND A.SPXX01 = '"
					+ Integer.valueOf(cds.getField("SPXX01", 0))
					+ "' "
					+ " AND A.ZCXX01 = '"
					+ cds.getField("ZTID", 0)
					+ "'"
					+ "GROUP BY SPGL15";

			Map<String, Object> rowMap = queryForMap(o2o, sql);
			if (rowMap != null) {
				hm.putAll(rowMap);
			} else {
				hm = rowMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	/**
	 * 查询促销商品的数量
	 * 
	 * @param ZTID
	 *            --公司编码
	 * @param SPXX01
	 *            --商品编码
	 * @return
	 */
	public Map<String, Object> selectCXSP(String XmlData) {
		Map<String, Object> hm = new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			//modify 2015.12.24.修改为取zcxx01
			//String zcxx01 = cds.getField("ZTID", 0);
			String zcxx01 = cds.getField("ZCXX01", 0);
			String spxx01 = cds.getField("SPXX01", 0);
			String DQXX01 = cds.getField("DQXX01", 0);
			String hbid=cds.getField("HBID", 0);
			String sql =
				"SELECT DISTINCT LEAST(ifnull(SPGL15,0),(select ROUND(ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0)) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' AND k.spxx01= A.SPXX01 and k.zcxx01=A.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01="+DQXX01+" ))) MAXNUM,\n" +
				"       IFNULL((SELECT MIN(X.HDSL)\n" + 
				"             FROM W_CXZXB X\n" + 
				"            WHERE X.SPXX01 = "+spxx01+"\n" + 
				"              AND X.GSXX01 = '"+zcxx01+"'\n" + 
				"            GROUP BY X.SPXX01, X.GSXX01),\n" + 
				"           0) HDSL,\n" + 
				"       (SELECT C.HDLX\n" + 
				"          FROM W_CXZXB C\n" + 
				"         WHERE C.SPXX01 = "+spxx01+"\n" + 
				"           AND C.GSXX01 = '"+zcxx01+"' AND C.DQXX01 = '"+DQXX01+"' ) HDLX\n" + 
				"  FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z,W_KCXX K\n"+
				" WHERE A.SPXX01 = N.SPXX01\n" + 
				"   AND A.ZCXX01 = N.ZCXX01\n" + 
				"   AND A.SPXX01 = B.SPXX01\n" + 
				"   AND A.ZCXX01 = Z.ZCXX01\n" + 
				"   AND A.SPGL12 IN ('1', '3')\n" + 
				"   AND A.SPXX01 = "+spxx01+"\n" + 
				"   AND A.ZCXX01 = '"+zcxx01+"'\n";
			
			Map<String, Object> num = queryForMap(o2o, sql);
			List<Integer> minSP= new ArrayList<Integer>();
			if (num != null) {
				String ssString=num.get("MAXNUM").toString();
				String maxNum=ssString.substring(0, (ssString.indexOf(".")==-1?ssString.length():ssString.indexOf(".")));
				minSP.add(Integer.parseInt(maxNum));
				if(num.get("HDLX")!=null){
					String hdlx=num.get("HDLX").toString();
					//为促销商品
					if(Integer.parseInt(hdlx)!=2){
						//满赠时活动数量为0
						minSP.add(Integer.parseInt(num.get("HDSL").toString()));
						num.put("MAXNUM", Collections.min(minSP));
					}
				}
				hm.putAll(num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	public int getKCSLMin(String XmlData) {
		Map<String, Object> hm = selectKCSL(XmlData);
		List<Integer> num = new ArrayList<Integer>();
		if (hm != null) {
			num.add(Integer.parseInt(hm.get("MAXNUM").toString()));
			num.add(Integer.parseInt(hm.get("SPIMGURL").toString()));
		} else {
			return 0;
		}
		Map<String, Object> cxMap = selectCXSP(XmlData);
		if (cxMap != null) {
			// 该商品为促销商品
			num.add(Integer.parseInt(cxMap.get("HDSL").toString()));
		}
		int kcsl = Collections.min(num);
		return kcsl;
	}

	/**
	 * 查询购物车商品数量
	 * 
	 * @param XmlData
	 * @param ZTID
	 *            --主体编码
	 * @param HBID
	 *            --用户编码
	 * @param SPXX01
	 *            --商品编码
	 * @return
	 */
	public int selectMyCartSPNum(String XmlData) {
		Map<String, Object> cartMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			String ztid = cds.getField("ZTID", 0);
			String spxx01 = cds.getField("SPXX01", 0);
			String hbid = cds.getField("HBID", 0);
			String sql = "SELECT A.GWC01\n" + "  FROM W_GWC A\n"
					+ " WHERE A.SPXX01 = '" + spxx01 + "'\n"
					+ "   AND A.ZTID = '" + ztid + "'\n" + "   AND A.HBID = '"
					+ hbid + "'";
			cartMap = queryForMap(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int kcsl = Integer.parseInt(cartMap.get("GWC01").toString());
		return kcsl;
	}
	public List<Map<String, Object>> selectCKSP(String XmlData) throws Exception{
		List<Map<String, Object>> ckspList=null;
		try {
				cds =new DataSet(XmlData);
			String sql = "SELECT A.CKSP04 SPNUM, A.cksp13 FMBJ\n" +
			"  FROM CKSP A, W_DQCK B, W_SPXXDZ C\n" + 
			" WHERE A.CK01 = B.CK01\n" + 
			"   AND A.SPXX01 = C.ERP_SPXX01\n" + 
			"   AND B.DQXX01 = "+cds.getField("DQXX01", 0)+"\n" + 
			"   AND C.SPXX01 = "+cds.getField("SPXX01", 0)+"\n" + 
			"   AND A.GSXX01 = "+cds.getField("ZCXX01", 0)+"";
			ckspList= queryForList(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ckspList;
	}
	/**
	 * 
	 */
	
	/**
	 * 查询套餐商品明细 -- 待删除
	 * @param SPXX01
	 * @throws Exception 
	 */
	public List<Map> selectTCProductInfo(Map map) throws Exception{
		try {
			String sql=
				"SELECT B.SPXX04, B.SPFL01, B.SPFL02, B.PPB01, B.PPB02, B.ZHSL, B.SPJG\n" + 
				"  FROM W_CXZXB A, W_TCCXITEM B\n" + 
				" WHERE A.HDBH = B.HDBH\n" + 
				"   AND A.SPXX01 = '"+map.get("SPXX01")+"'" +
				"   AND A.HDLX = 4";
			List<Map> tcItem= queryForList(o2o, sql);
			return tcItem;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * @param spxx01
	 * @return SPXX
	 */
	public Map<String, Object> selectErpSpxx(String spxx01){
		String sql="SELECT A.ERP_SPXX01 SPXX FROM W_SPXXDZ A WHERE A.SPXX01 = '"+spxx01+"'";
		Map<String, Object> map=queryForMap(o2o, sql);
		return map;
	}
	/**
	 * @Title: selectSplx
	 * @Description: 查询商品的类型(0：普通商品 1：套餐商品)
	 * @param: @param XmlData
	 * @param: @return   
	 * @return: Map   
	 * @throws
	 */
	public Map selectSplx(String spxx01){
		String sql="SELECT A.SPGL16 SPLX FROM W_SPGL A WHERE A.SPXX01 = "+spxx01+"";
		Map map= queryForMap(o2o, sql);
		return map;
	}
	
	/**
	 * @Title: selectGWCForZTID
	 * @Description: (查询套餐商品明细)
	 * @param: @param XmlData
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<Map>   
	 * @throws
	 */
	public List<Map> selectGWCForZTID(String spxx01) throws Exception{
		try {
			String sql=
				"SELECT B.SPXX01, B.ZHSL, B.SPJG\n" +
				"  FROM W_CXZXB A, W_TCCXITEM B\n" + 
				" WHERE A.HDBH = B.HDBH\n" + 
				"   AND A.SPXX01 = '"+spxx01+"'";
			List<Map> list= queryForList(o2o, sql);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
}
