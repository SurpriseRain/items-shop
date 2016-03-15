package com.jlsoft.o2o.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
public class ProductAttachForsrch extends JLBill{
	
	/**
	 * 登入后查询商品详情页面
	 * @param XmlData
	 * @param SPXX01  --商品编码	
	 * @param ZCXX01  --公司编码
	 * @param QYDM    --地区代码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectProductItem(String XmlData){
		Map<String, Object> map= new HashMap<String, Object>();
		try {
			cds =new DataSet(XmlData);
			String qydm=cds.getField("QYDM", 0);
			StringBuffer sb=new StringBuffer();
			String sql1=
				"SELECT A.SPXX01 SPID ,\n" +
				"       B.SPXX04 SPNAME ,\n" + 
				"       A.SPGL01 SPBT ,\n" + 
				"       B.SPXX02 SPCODE ,\n" + 
				"       A.ZCXX01 ZTID ,\n" + 
				"       B.SPFL01_CODE FL01CODE ,\n" + 
				"       B.SPFL02_CODE FL02CODE ,\n" + 
				"       B.PPB01 PPBCODE ,\n" + 
				"       B.PPB02 SPPP ,\n" + 
				"       B.SPFL01_NAME SPFL ,\n" + 
				"       B.SPFL01 FLNAME ,\n" + 
				"       N.GHHT01 GHHT01 ,\n" + 
				"       B.SPFL02_NAME FLNAME4 ,\n" + 
				"       A.SPGL02 DTBJ ,\n" + 
				"       	(select ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' and k.spxx01= A.SPXX01 and k.zcxx01=A.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01='"+cds.getField("DQXX01", 0)+"')) SPIMGURL,\n" + 
				"       Z.ZCXX02 GHSNAME ,\n" + 
				"       N.SPJGB01 SPJG ,\n" + 
				"       N.SPJGB02 SCJG ,\n" + 
				"       IFNULL(N.SPJGB05, 0) LSJG ,\n" + 
				"       A.SPGL04 FBJG ,\n" + 
				"		IFNULL((SELECT D.DWJG02\n" + 
				"            FROM DWJGB D\n" + 
				"            WHERE D.GSXX01 = A.ZCXX01\n" + 
				"              AND D.KH01 = "+cds.getField("ZCXX01", 0)+"\n" + 
				"              AND D.SPXX01 = A.SPXX01),0) YHJG,"+
				"       Z.ZCXX06 GHSLXDH ,\n" + 
				"       A.SPGL14 MINNUM ,\n" + 
				"       A.SPGL15 MAXNUM ,\n" + 
				"       A.SPGL06 SCJFBJ ,\n" + 
				"       B.SPXH01 SPXH01 ,\n" + 
				"       B.JLDW01 JLDW01 ,\n" + 
				"       B.GGB01 GGB01   ,\n" + 
				"        A.SPGL01 TJLY  \n" +
				"  FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z" ;
			String sql2= "";
			if(qydm!=null&&!"".equals(qydm)){
				sql2=", W_SPQY Q";
			}
			String sql3=
				" WHERE A.SPXX01 = N.SPXX01\n" + 
				"   AND A.ZCXX01 = N.ZCXX01\n" + 
				"   AND A.SPXX01 = B.SPXX01\n" + 
				"   AND A.ZCXX01 = Z.ZCXX01\n" + 
				"   AND A.SPGL12 IN ('1', '3')\n" + 
				"   AND A.SPXX01 = "+cds.getField("SPXX01", 0)+"\n" + 
				"   AND A.ZCXX01 = "+cds.getField("ZCXX01", 0)+"\n" ;
			String sql4="";
				if(qydm!=null&&!"".equals(qydm)){
					sql4="        AND A.ZCXX01 = Q.JXS\n" + 
					"       AND A.SPXX01 = Q.SPID\n" + 
					"       AND Q.QYDM LIKE SUBSTR("+qydm+", 1, 4) || '%'\n" ;
				}
			String sql5=
				" GROUP BY A.SPXX01,\n" + 
				"          B.SPXX04,\n" + 
				"          A.SPGL01,\n" + 
				"          B.SPXX02,\n" + 
				"          A.ZCXX01,\n" + 
				"          B.SPFL01_CODE,\n" + 
				"          B.SPFL02_CODE,\n" + 
				"          B.PPB01,\n" + 
				"          B.PPB02,\n" + 
				"          B.SPFL01_NAME,\n" + 
				"          B.SPFL01,\n" + 
				"          N.GHHT01,\n" + 
				"          B.SPFL02_NAME,\n" + 
				"          A.SPGL02,\n" + 
				"          Z.ZCXX02,\n" + 
				"          N.SPJGB01,\n" + 
				"          N.SPJGB02,\n" + 
				"          N.SPJGB05,\n" + 
				"          A.SPGL04,\n" + 
				"          Z.ZCXX06,\n" + 
				"          A.SPGL14,\n" + 
				"          A.SPGL15,\n" + 
				"          A.SPGL06,\n" + 
				"          B.SPXH01,\n" + 
				"          B.JLDW01,\n" + 
				"          B.GGB01";
			sb.append(sql1);
			sb.append(sql2);
			sb.append(sql3);
			sb.append(sql4);
			//sb.append(sql5);
			map=queryForMap(o2o, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 
	 * @param XmlData
	 * @param SPXX01  --商品编码	
	 * @param ZCXX01  --公司编码
	 * @param QYDM    --地区代码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectProductCXInfo(String XmlData){
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			cds=new DataSet(XmlData);
			String qydm=cds.getField("QYDM", 0);
			String sql=
				"SELECT B.HDBH HDBH,\n" +
				"             B.HYXL HYXL,\n" + 
				"             B.CXJG CXJG,\n" + 
				"             B.HDSL HDSL,\n" + 
				"             B.YSSL YSSL,\n" + 
				"             B.HDMS HDMS,\n" + 
				"             B.BEGAINDATE BEGAINDATE,\n" + 
				"             B.ENDDATE ENDDATE,\n" + 
				"             SYSDATE SYSTEMDATE,\n" + 
				"             IFNULL((SELECT GMSL FROM W_SPXGSL W WHERE W.HDBH = B.HDBH AND W.GSXX01 = B.GSXX01 AND W.SPXX01 = B.SPXX01),0) YGMSL,\n" + 
				"             B.HDLX HDLX,\n" + 
				"             B.SJFS SJFS,\n" + 
				"             B.YYBEGAINDATE,\n" + 
				"             B.YYENDDATE,\n" + 
				"             B.HYQDL HYQDL,\n" + 
				"             B.DJJE DJJE,\n" + 
				"             B.JFNUM JFNUM,\n" + 
				"             B.HDYXSL hdyxsl,\n" + 
				"             B.ZFFS zffs,\n" + 
				"             B.PAYDATE PAYDATE,\n" + 
				"             B.HDZT HDZT\n" + 
				"          FROM W_CXZXB B\n" + 
				"        WHERE SPXX01 = "+cds.getField("SPXX01", 0)+"\n" + 
				"        AND GSXX01 = "+cds.getField("ZCXX01", 0)+"\n" + 
				"        AND SYSDATE >= IFNULL(B.YYBEGAINDATE,SYSDATE)\n" + 
				"        AND SYSDATE <= B.ENDDATE";
			if(qydm!=null&&!"".equals(qydm)){
				if(qydm.length()>=6){
					sql += "        AND DQXX01 ='"+cds.getField("QYDM", 0)+"'";	
				}else{
					sql += "        AND DQXX01 LIKE SUBSTR("+cds.getField("QYDM", 0)+",1,4)\n" ;
				}
			}
			map=queryForMap(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @param XmlData --
	 * @param SPXX01  --商品编码
	 * @param ZCXX01  --商品对应的主体编码
	 * @return
	 */
	public Map<String, Object> selectProductForNotLogin(String XmlData){
		Map<String, Object> hm=new HashMap<String, Object>();
		try {
			cds=new DataSet(XmlData);
			String spid=cds.getField("SPXX01", 0);
			String ztid=cds.getField("ZCXX01", 0);
			String sql=
				"SELECT A.SPXX01 SPID,\n" +
				"       B.SPXX04 SPNAME,\n" + 
				"       A.SPGL01 SPBT,\n" + 
				"       B.SPXX02 SPCODE,\n" + 
				"       A.ZCXX01 ZTID,\n" + 
				"       B.SPFL01_CODE FL01CODE,\n" + 
				"       B.SPFL02_CODE FL02CODE,\n" + 
				"       B.PPB01 PPBCODE,\n" + 
				"       B.PPB02 SPPP,\n" + 
				"       B.SPFL01_NAME SPFL,\n" + 
				"       B.SPFL01 FLNAME,\n" + 
				"       N.GHHT01 GHHT01,\n" + 
				"       B.SPFL02_NAME FLNAME4,\n" + 
				"       A.SPGL02 DTBJ,\n" + 
				"       (select ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' and k.spxx01= A.SPXX01 and k.zcxx01=A.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01='"+cds.getField("DQXX01", 0)+"')) SPIMGURL,\n" + 
				"       Z.ZCXX02 GHSNAME,\n" + 
				"       N.SPJGB01 SPJG,\n" + 
				"       N.SPJGB02 SCJG,\n" + 
				"       IFNULL(N.SPJGB05, 0) LSJG,\n" + 
				"       A.SPGL04 FBJG,\n" + 
				"       0 YHJG,\n" + 
				"       Z.ZCXX06 GHSLXDH,\n" + 
				"       A.SPGL14 MINNUM,\n" + 
				"       A.SPGL15 MAXNUM,\n" + 
				"       A.SPGL06 SCJFBJ,\n" + 
				"       B.SPXH01 SPXH01,\n" + 
				"       B.JLDW01 JLDW01,\n" + 
				"       B.GGB01 GGB01,\n" + 
				"       A.SPGL01 TJLY\n" + 
				"  FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z, W_KCXX K\n" + 
				" WHERE A.SPXX01 = N.SPXX01\n" + 
				"   AND A.ZCXX01 = N.ZCXX01\n" + 
				"   AND A.SPXX01 = B.SPXX01\n" + 
				"   AND A.ZCXX01 = Z.ZCXX01\n" + 
				"   AND A.SPGL12 IN ('1', '3')\n" + 
				"   AND A.SPXX01 = "+spid+"\n" + 
				"   AND A.ZCXX01 = "+ztid+"\n";
			hm=queryForMap(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	
	/**
	 * 登录时查询筛选商品
	 * @throws Exception 
	 * @Param  GSXX01
	 * @param  QYBM
	 * @param  SPFL01
	 */
	public List<Map<String,Object>> selectScreeningGoods(Map hm,Map user) throws Exception{
		String sql=
			"SELECT G.SPXX01 SP_ID,\n" +
			"           P.SPXX02 SPCODE,\n" + 
			"           P.SPXX04 SPNAME,\n" + 
			"           M.SPJGB01 SPJG,\n" + 
			"\t\t       G.SPGL04 FBJG,\n" + 
			"\t\t       G.ZCXX01 JXS,\n" + 
			"\t\t       G.SPGL02 DTBJ,\n" + 
			"\t\t       IFNULL(M.SPJGB05, 0) LSJG,\n" + 
			"\t\t       SUM(K.CKSP04 + K.CKSP05 - K.KCXX02 + K.KCXX01) SPSL,\n" + 
			"\t\t       IFNULL(C.CXJG, 0) CXJG,\n" + 
			"\t\t       IFNULL((SELECT D.DWJG02\n" + 
			"              FROM DWJGB D, W_GSGX E\n" + 
			"             WHERE D.GSXX01 = E.ZTID\n" + 
			"               AND D.KH01 = E.HBID\n" + 
			"               AND D.SPXX01 = G.SPXX01\n" + 
			"               AND D.KH01 = "+hm.get("ZTID")+"\n" + 
			"               AND D.GSXX01 = "+hm.get("ZTID")+"),0)YHJG\n" + 
			"\t\t  FROM W_SPGL G,\n" + 
			"\t\t       W_SPJGB M,\n" + 
			"\t\t       W_SPXX P,\n" + 
			"\t\t       W_KCXX K,CK,\n" + 
			"\t\t       (SELECT B.GSXX01, B.SPXX01, B.CXJG, B.HDLX\n" + 
			"\t\t          FROM W_CXZXB B\n" + 
			"\t\t           WHERE SYSDATE >= B.BEGAINDATE\n" + 
			"\t\t           AND SYSDATE <= B.ENDDATE\n" + 
			"\t\t           AND B.HDLX = 1) C\n" + 
			"\t\t WHERE G.SPXX01 = M.SPXX01\n" + 
			"\t\t   AND G.ZCXX01 = M.ZCXX01\n" + 
			"\t\t   AND G.SPXX01 = P.SPXX01\n" + 
			"\t\t   AND G.SPGL12 IN ('1', '3')\n" + 
			"\t\t   AND G.ZCXX01 = C.GSXX01(+)\n" + 
			"\t\t   AND G.SPXX01 = C.SPXX01(+)\n" + 
			"\t\t   AND K.ZCXX01 = G.ZCXX01\n" + 
			" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
			"\t\t   AND K.SPXX01 = G.SPXX01\n" + 
			"\t\t   AND G.SPGL02 = 2\n" + 
			"\t\t   AND P.SPFL01_CODE = SUBSTR('"+hm.get("FLNAME")+"',1,2)\n" + 
			"\t\t   LIMIT 10\n" + 
			"\t\t GROUP BY G.SPXX01,\n" + 
			"\t\t          P.SPXX02,\n" + 
			"\t\t          P.SPXX04,\n" + 
			"\t\t          G.SPGL04,\n" + 
			"\t\t          G.ZCXX01,\n" + 
			"\t\t          G.SPGL02,\n" + 
			"\t\t          M.SPJGB05,\n" + 
			"\t\t          C.CXJG,\n" + 
			"\t\t          M.SPJGB01,\n" + 
			"\t\t          G.SPGL03\n" + 
			"  \t\t  ORDER BY SPGL03";
		List<Map<String, Object>> spList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return spList;
	}
	
	/**
	 * 不登陆时赛选商品
	 * 
	 */
	public List<Map<String,Object>> selectScreeningGoodsNotLogin(Map hm) throws Exception{
		String sql=
			"SELECT G.SPXX01 SP_ID,\n" +
			"\t       P.SPXX02 SPCODE,\n" + 
			"\t       P.SPXX04 SPNAME,\n" + 
			"\t       M.SPJGB05 SPJG,\n" + 
			"\t       G.SPGL04 FBJG,\n" + 
			"\t       G.ZCXX01 JXS,\n" + 
			"\t       G.SPGL02 DTBJ,\n" + 
			"\t       IFNULL(M.SPJGB05, 0) LSJG,\n" + 
			"\t       SUM(K.CKSP04 + K.CKSP05 - K.KCXX02 + K.KCXX01) SPSL,\n" + 
			"\t       0 CXJG,\n" + 
			"\t       0 YHJG\n" + 
			"\t  FROM W_SPGL G, W_SPJGB M, W_SPXX P, W_KCXX K,CK\n" + 
			"\t WHERE G.SPXX01 = M.SPXX01\n" + 
			"\t   AND G.ZCXX01 = M.ZCXX01\n" + 
			"\t   AND G.SPXX01 = P.SPXX01\n" + 
			"\t   AND G.SPGL12 IN ('1', '3')\n" + 
			"\t   AND K.ZCXX01 = G.ZCXX01\n" + 
			"\t   AND K.SPXX01 = G.SPXX01\n" + 
			" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
			"\t   AND G.SPGL02 = 2\n" + 
			"\t   AND P.SPFL01_CODE = '10'\n" + 
			"\t   LIMIT 10\n" + 
			"\t GROUP BY G.SPXX01,\n" + 
			"\t          P.SPXX02,\n" + 
			"\t          P.SPXX04,\n" + 
			"\t          G.SPGL04,\n" + 
			"\t          G.ZCXX01,\n" + 
			"\t          G.SPGL02,\n" + 
			"\t          M.SPJGB05,\n" + 
			"\t          M.SPJGB01,\n" + 
			"\t          G.SPGL03\n" + 
			"\t ORDER BY SPGL03";
		List<Map<String, Object>> spList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return spList;
	}
	/**
	 * @param  HDBH --活动编号
	 * @return
	 */
	public Map<String, Object>  selectMZCX(Map<String, Object> hm,Map<String, Object> sphm){
		Map<String, Object> mzMap=new HashMap<String, Object>();
		String hdbh=hm.get("HDBH").toString();
		String sql=
			"SELECT D.SPXX01 HDSPBH, D.SPXX04 HDSPMC, C.SPXX02 HDSPLM, B.SPJGB05 HDSPJG\n" +
			"  FROM W_SPGL A, W_SPJGB B, W_SPXX C, W_MZCXITEM D\n" + 
			" WHERE A.SPXX01 = B.SPXX01\n" + 
			"   AND A.SPXX01 = C.SPXX01\n" + 
			"   AND A.SPXX01 = D.SPXX01\n" + 
			"   AND A.SPGL12 IN (1, 3)\n" + 
			"   AND D.HDBH = '"+hdbh+"'\n" + 
			"   AND D.SPXX01 <> "+sphm.get("SPID").toString()+"";
		String tdsql=
			"SELECT DISTINCT TDJE\n" +
			"  FROM W_MZCXTD A\n" + 
			" WHERE A.HDBH = '"+hdbh+"'\n" + 
			" ORDER BY TDJE";
		List<Map<String, Object>> hdspList=queryForList(o2o, sql);
		List<Map<String, Object>> tdjeList=queryForList(o2o, tdsql);
		List<Map<String, Object>> zpList = new ArrayList<Map<String,Object>>();
		for(Map map:tdjeList){
			String zpsql=
				"SELECT DISTINCT SPXX01 HDZPHM, SPXX04 HDZPMC, SPSL HDZPSL\n" +
				"  FROM W_MZCXTD A\n" + 
				" WHERE A.HDBH = '"+hdbh+"'" +
				" AND A.TDJE ='"+map.get("TDJE")+"'";
			zpList= queryForList(o2o, zpsql);
			map.put("zpList", zpList);
		}
		if(hdspList!=null){
			mzMap.put("hdsp", hdspList);
		}
		if(tdjeList!=null){
			mzMap.put("tdje", tdjeList);
		}
		if(zpList!=null){
			mzMap.put("hdzp",zpList);
		}
		return mzMap;
	}
	
	public Map<String, Object> selectProductInfoImages(Map<String, Object> hm){
		Map<String, Object> spMap= new HashMap<String, Object>();
		String sql= "SELECT COUNT(0) FROM W_SPTP A WHERE A.SPXX01 = "+hm.get("SPID").toString()+"";
		int i=queryForInt(o2o, sql);
		spMap.put("NUM", i);
		List<Map<String, Object>> mapList= selectGoodsAttribute(hm);
		spMap.put("mapList", mapList);
		return spMap;
	}
	
	public List<Map<String, Object>> selectDqxxForCk(String ztid){
		String sql = "select a.bm01||'01' bm01,a.bm02 from bm a where a.bm03 = 4 and bm04 = 0 and gsxx01 = '"+ztid+"' and bm06 = 3 order by bm01";
		List<Map<String, Object>> listMap=queryForList(scm, sql);
		return listMap;
	}
	
	public List<Map<String, Object>> selectGoodsAttribute(Map<String, Object> hm){
		String sql=
			"SELECT A.SPFL, A.SXBH, A.SXNAME, B.SXZDM, B.SXZNAME\n" +
			"  FROM W_SPFLSX A, W_SPFLSXITEM B\n" + 
			" WHERE A.SPFL = B.SPFL\n" + 
			"   AND A.SXBH = B.SXBH\n" + 
			"   AND A.SPFL =\n" + 
			"       (SELECT X.SPFL02_CODE FROM W_SPXX X WHERE X.SPXX01 = "+hm.get("SPID").toString()+")\n" + 
			"   AND B.SXZDM in (SELECT G.SXZDM FROM W_SPSX G WHERE G.SPXX01 = "+hm.get("SPID").toString()+")";
		List<Map<String, Object>> mapList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return mapList;
	} 
//	/**
//	 * @param list
//	 * @return cartMZAmount参加商品促销的金额
//	 */
//	public Map<String, Double> selectAboutCXCart(List<Map<String, Object>> list) {
//		Map<String, Double> hm = new HashMap<String, Double>();
//		double amount = 0;
//		if (list != null && list.size() > 0) {
//			for (Map<String, Object> map : list) {
//				Map<String, Object> num = selectMyCartForId(map.get("SPID")
//						.toString());
//				if (num != null) {
//					amount += Double.parseDouble(num.get("GWCSPAMOUNT")
//							.toString());
//				}
//			}
//		}
//		hm.put("cartMZAmount", amount);
//		return hm;
//	}
//	/**
//	 * 查询购物车中的促销商品
//	 * @param spid
//	 * @return
//	 */
//	public Map<String, Object> selectMyCartForId(String spid) {
//		String sql = "SELECT B.SPXX01 CXSPID,\n" + "       B.GWC01 CXSPNUM,\n"
//				+ "       B.GWC02 CXSPJG,\n"
//				+ "       B.GWC01 * TO_NUMBER(B.GWC02) GWCSPAMOUNT\n"
//				+ "  FROM W_CXZXB A, W_GWC B\n"
//				+ " WHERE A.SPXX01 = B.SPXX01\n" + "   AND A.GSXX01 = B.ZTID\n"
//				+ "   AND A.SPXX01 = " + spid + "";
//		Map<String, Object> hm = queryForMap(o2o, sql);
//		return hm;
//	}
	
	/**
	 * 
	 * @param begainDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	 //比较两个时间
    public static boolean compareDate(String begainDate,String endDate) throws ParseException{
       SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
 	   Date comBegain = myfmt.parse(begainDate);
 	   Date comEnd = myfmt.parse(endDate);
 	   //如果开始时间小于结束时间，返回true 否则返回false
 	   if(comBegain.getTime() <= comEnd.getTime()){
 		   return true;
 	   }else{
 		   return false;
 	   }
    }
   
 }
