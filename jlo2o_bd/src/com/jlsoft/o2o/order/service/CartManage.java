/**********************************************************************
 * @file	CartManage.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	曾海峰/20140312
 * @author	Code:	曾海峰/20140312
 * @author	Modify:	曾海峰/20140312
 * @copy	Tag:	金力软件
 **********************************************************************/

package com.jlsoft.o2o.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ocsp.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.product.service.ProductAttachForsrch;
import com.jlsoft.o2o.product.service.ProductQueryService;
import com.jlsoft.o2o.user.service.UserInfo;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * 
 * @breif 购物车相关操作管理
 * 
 */
@Controller
@RequestMapping("/CartManage")
public class CartManage extends JLBill {
	private Logger logger = Logger.getLogger(CartManage.class);

	@Autowired
	private ProductAttachForsrch productItem;
	@Autowired
	private CartManageForSql cartManage;
	@Autowired
	private ProductQueryService productQueryService;
	
	private int ROADMAP = JLTools.strToInt(JlAppResources
			.getProperty("ROADMAP"));

	public CartManage() {

	}

	public CartManage(JdbcTemplate o2o, JdbcTemplate scm) {
		this.o2o = o2o;
		this.scm = scm;
	}

	/**
	 * 查询购物车客户所有的商品信息
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String DQXX01 地区编码
	 * 
	 * @return Map - ZTID 供货商编码 - ZCXX02 供货商名称 - DWYEZ04 账户余额 - TOTALNUM 商品数量小计
	 *         - TOTALAMOUNT 供货商金额总计 - SPITEMLIST 商品列表 - SPXX01 商品内码 - SPXX02
	 *         商品编码 - SPXX04 商品名称 - GWC01 商品数量 - GWC02 商品价格 - SPGL14 商品起订量 -
	 *         SPGL15 商品限定量 - SINGLEAMOUNT 单个商品金额小计
	 * @note 点击“我的购物车” 查询所有购物车信息数据
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectGwcByHbid.action")
	public Map selectGwcByHbid(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String HBID = cds.getField("HBID", 0).toString();
		String DQXX01 = cds.getField("DQXX01", 0).toString();
		//修改购物车中商品的区域信息
		modifyGwcDqxx(HBID, DQXX01);
		// 根据客户编码获得购物车中所有供货商的信息（供货商编码、所有商品金额总计、商品数量合计、供货商名称）
//		String querySQL = " 	SELECT 	A.ZTID ,\n"
//				+ "		 	B.ZCXX01 ,\n"
//				+ "		 	SUM(A.GWC01 * A.GWC02) TOTALAMOUNT,\n"
//				+ "		 	B.ZCXX02,\n"
//				+ " (SELECT S.SPTP02 FROM W_SPTP S WHERE A.ZTID=S.ZCXX01 AND S.SPXX01=A.SPXX01 AND S.SPTP01=1) SPTP02, "
//				+ "		 	SUM(A.GWC01) TOTALNUM_,\n"
//				+ "			( SELECT SUM(A.GWC01)\n "
//				+ "				 FROM W_GWC A, W_ZCXX B WHERE A.ZTID = B.ZCXX01 AND A.HBID = '"
//				+ cds.getField("HBID", 0) + "' GROUP BY HBID ) TOTALNUM \n"
//				+ "     FROM 	W_GWC A, W_ZCXX B \n"
//				+ "    WHERE 	A.ZTID = B.ZCXX01 \n" + "      AND 	A.HBID = '"
//				+ cds.getField("HBID", 0) + "' \n"
//				+ " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02";
		
		//modify 2015.11.17. xup 修改查询：将商品价格由w_gwc表查询修改为从w_spjgb中查询
		String querySQL = " 	SELECT 	A.ZTID ,\n"
				//+ "		 	B.ZCXX01 ," 
				+ "		 	D.ZCXX01 ,"
				// modify 2016.01.08.注释掉
				//+				"K.ck01,\n"
				+				"IFNULL(fn_getCk(D.ZCXX01, A.dqxx01, A.spxx01),'00000000') ck01,\n"
				+ "		 	SUM(A.GWC01 * C.spjgb05) TOTALAMOUNT,\n"
				+ "		 	B.ZCXX02, A.dqxx01 DQXX01,\n"
				+ " (SELECT S.SPTP02 FROM W_SPTP S WHERE D.zcxx01=S.ZCXX01 AND S.SPXX01=A.SPXX01 AND S.SPTP01=1) SPTP02, "
				+ "		 	SUM(A.GWC01) TOTALNUM_,\n"
				+ "			( SELECT SUM(A.GWC01)\n "
				+ "				 FROM W_GWC A, W_ZCXX B WHERE A.ZTID = B.ZCXX01 AND A.HBID = '"
				+ cds.getField("HBID", 0) + "' GROUP BY HBID ) TOTALNUM \n"
				//modify 2015.12.17. 修改分组方式增加仓库的分组项
//				+ "     FROM 	W_GWC A, W_ZCXX B , w_spjgb C \n"
//				+ "    WHERE 	A.ZTID = B.ZCXX01 \n" + "      AND 	A.HBID = '"
//				+ cds.getField("HBID", 0) + "' \n"
//				+ " AND (A.ZTID= C.zcxx01 and A.spxx01=C.spxx01) "
//				+ " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02";				
				+ " FROM W_GWC A LEFT JOIN W_ZCXX B ON A.ZTID = B.ZCXX01  LEFT JOIN W_SPGL D ON a.spxx01 = D.spxx01 LEFT JOIN w_spjgb C ON (D.zcxx01= C.zcxx01 and A.spxx01=C.spxx01) \n"
				//modify 2015.12.30. 修改关联查询
				// modify 2016.01.08.注释掉
 				//+ " LEFT JOIN "
 				//+ " (SELECT s.zcxx01,s.spxx01, s.ck01, t.dqxx01  FROM w_kcxx s LEFT JOIN w_dqck t ON s.ck01 = t.ck01 LEFT JOIN ck x ON t.ck01=x.ck01 WHERE t.status=0 and x.ck09=0) K ON A.ZTID = K.zcxx01 AND A.spxx01 = K.spxx01 AND A.dqxx01 = K.dqxx01 "
				+ " WHERE A.HBID = '"
				+ cds.getField("HBID", 0) + "' \n"
				// modify 2016.01.08.注释掉
				//+ "	AND  A.ZTID = K.ZCXX01 AND A.SPXX01=K.SPXX01 "
				//+ " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02, K.ck01";
				+ " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02, ck01";
			
		List infoList = queryForList(o2o, querySQL);
		// 如果购物车不为空，则进行明细数据查询封装
		Map parmap = new HashMap<String, String>();
		//parmap.put("DQXX01", cds.getField("DQXX01", 0));
		if (cds.getField("CKQX", 0) != null) {
			parmap.put("CKQX", cds.getField("CKQX", 0));
		}

		UserInfo userInfo = new UserInfo(o2o, scm);
		if (infoList != null && infoList.size() > 0) {
			for (int i = 0; i < infoList.size(); i++) {
				Map infoMap = (Map) infoList.get(i);
				// 根据主体编码、伙伴编码获得单位余额账
				parmap.put("ZTID", infoMap.get("ZTID").toString());
				parmap.put("HBID", HBID);				
				//modify 2015.12.17.增加仓库ID、spxx01
				if(infoMap.get("CK01") !=null){
					parmap.put("CK01", infoMap.get("CK01").toString());
				}
				if(infoMap.get("spxx01") !=null){
					parmap.put("SPXX01", infoMap.get("spxx01").toString());
				}
				if(infoMap.get("DQXX01") !=null){
					parmap.put("DQXX01", infoMap.get("DQXX01").toString());
				}
				if(infoMap.get("ZCXX01") !=null){
					parmap.put("ZCXX01", infoMap.get("ZCXX01").toString());
				}
				JSONArray jsonArray = JSONArray.fromObject(parmap);
				Map yemap = userInfo.selectUserDwyez(jsonArray.toString());
				infoMap.put("DWYEZ04", yemap.get("DWYEZ04").toString());
//				infoMap.putAll(selectGwcSpxxInfo(jsonArray.toString()));
				infoMap.putAll(queryGwcSpxxInfoNew(jsonArray.toString()));
				
			}
		}

		Map map = new HashMap();
		map.put("GWCLIST", infoList);
		return map;
	}

	public List<Map> selectGWC(String XmlData) throws Exception {
		try {
			cds = new DataSet(XmlData);
			String HBID = cds.getField("HBID", 0).toString();
			String ZTID = cds.getField("ZTID", 0).toString();
			String ztSql = "";
			if (ZTID != null) {
				ztSql = "   AND A.HBID = '" + HBID + "'";
			}
			// 查询购物车中供货商信息
			String sql = "SELECT A.ZTID, A.SPXX01, A.GWC01, A.GWC02, A.DQXX01\n"
					+ "  FROM W_GWC A\n"
					+ " WHERE A.HBID = '"
					+ HBID
					+ "'"
					+ ztSql;
			List<Map> sqlMap = queryForList(o2o, sql);
			return sqlMap;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	/**
	 * 根据主体编码、伙伴编码获得购物车商品明细数据
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String DQXX01
	 *            客户地区编码
	 * 
	 * @return Map - SPXX01 商品内码 - SPXX02 商品编码 - SPXX04 商品名称 - GWC01 商品数量 -
	 *         GWC02 商品价格 - DQXX01 地区仓库商品 - SPGL14 商品起订量 - SPGL15 商品限定量 -
	 *         SINGLEAMOUNT 单个商品金额小计 - ITEMLIST 套餐明细 - SPXX04 商品名称 - PRICE 商品价格
	 *         - SPSL 组合数量
	 * @note 获得注定主体和客户在购物车里面的商品数据
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectGwcSpxxInfo.action")
	public Map selectGwcSpxxInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String CKQX = cds.getField("CKQX", 0);
		String ztid = cds.getField("ZTID", 0);
		String DQXX01 = cds.getField("DQXX01", 0);
		String ckqxSql;
		System.out.println(CKQX != null && (!"".equals(CKQX)) && CKQX != "");
		if (CKQX != null && (!"".equals(CKQX)) && CKQX != "") {
			ckqxSql = " AND EXISTS (SELECT 1  " + "  FROM W_CKQX W   "
					+ " WHERE W.ZTID = K.ZCXX01  " + "  AND W.CK01 = K.CK01   "
					+ " and W.GSID ='" + cds.getField("HBID", 0).toString()
					+ "') ";
		} else {
			ckqxSql = "";
		}
//		String querySQL = "SELECT   A.SPXX01,"
//				+ "					A.HBID,"
//				+ "					B.SPXX02,"
//				+ "					B.SPFL01_CODE,"
//				+ "					A.ZTID,"
//				+ "					A.GWC01,"
//				+ "					B.SPXX04,"
//				+ "					A.GWC02,"
//				+ "					A.GWC03,"
//				+ "					A.DQXX01,"
//				+ "					C.SPGL14,"
//				+ "					C.SPGL02,"
//				+ "					IFNULL(C.SPGL15,999) SPGL15,"
//				+ "				  	(select ROUND(ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0)) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' and k.spxx01= B.SPXX01 and k.zcxx01=C.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01="
//				+ cds.getField("DQXX01", 0)
//				+ " )) CKSL,"
//				+ // Integer.valueOf(99.00)会报错
//				"					C.SPGL16,"
//				+ "					C.SPGL06,"
//				+ "					C.SPGL07,"
//				+ "					B.SPXX09 LENGTH ,"
//				+ "         B.SPXX10 WIDTH,"
//				+ "         B.SPXX11 HEIGHT,"
//				+ "         C.CKSP12 CKSP12,(select sptp02 from w_sptp where spxx01 = b.spxx01 and sptp01= '1') SPTP02"
//				+ "			  FROM  W_GWC A, W_SPXX B, W_SPGL C"
//				+ // ,W_CKSPSX S 不需要
//				"			 WHERE  A.SPXX01 = B.SPXX01 "
//				+ "			   AND  A.ZTID = C.ZCXX01 "
//				+ "			   AND  A.SPXX01 = C.SPXX01" + "			   AND  ZTID = '"
//				+ cds.getField("ZTID", 0).toString() + "'"
//				+ "			   AND  HBID = '" + cds.getField("HBID", 0).toString()
//				+ "'";

		String querySQL = "SELECT   A.SPXX01,"
				+ "					A.HBID,"
				+ "					B.SPXX02,"
				+ "					B.SPFL01_CODE,"
				+ "					A.ZTID,"
				+ "					A.GWC01,"
				+ "					B.SPXX04,"
				+ "					D.spjgb05 GWC02,"
				+ "					A.GWC03,"
				+ "					A.DQXX01,"
				+ "					C.SPGL14,"
				+ "					C.SPGL02,"
				+ "					IFNULL(C.SPGL15,999) SPGL15,"
				//modify 2015.12.17. 修改库存数量查询, 增加商品图片查询
//				+ "				  	(select ROUND(ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0)) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' and k.spxx01= B.SPXX01 and k.zcxx01=C.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01="
//				+ cds.getField("DQXX01", 0)
//				+ " )) CKSL,"
				+ "					C.zcxx01 ZCXX01,"
				+ "	fn_getStockNum(C.zcxx01,'"+ DQXX01+"',A.spxx01)			  	 CKSL,"
				+ " (SELECT S.SPTP02 FROM W_SPTP S WHERE A.ZTID=S.ZCXX01 AND S.SPXX01=A.SPXX01 AND S.SPTP01=1) SPTP02, "
				//modify 2016.01.08.
				//+ "					K.CK01,"
				+" IFNULL(fn_getCk(C.ZCXX01, A.dqxx01, A.spxx01),'00000000') CK01,"
				+ // Integer.valueOf(99.00)会报错
				"					C.SPGL16,"
				+ "					C.SPGL06,"
				+ "					C.SPGL07,"
				+ "					B.SPXX09 LENGTH ,"
				+ "         B.SPXX10 WIDTH,"
				+ "         B.SPXX11 HEIGHT,"
				+ "         C.CKSP12 CKSP12,(select sptp02 from w_sptp where spxx01 = b.spxx01 and sptp01= '1') SPTP02"
				+ "			  FROM  W_GWC A, W_SPXX B, W_SPGL C, w_spjgb D"
				+ ", W_KCXX K"
				+ // ,W_CKSPSX S 不需要				
				"			 WHERE  A.SPXX01 = B.SPXX01 "
				//+ "			   AND  A.ZTID = C.ZCXX01 "
				//modify 2015.12.17. 增加w_kcxx表关联项
				//+ "			   AND  A.spxx01 = C.spxx01 "
				//+ "			   AND  A.ZTID = K.ZCXX01 AND A.SPXX01=K.SPXX01 "
				+ "			   AND B.spxx01 = C.spxx01 "
				+ "			   AND C.spxx01 = D.spxx01 "
				+ "			   AND A.ztid = K.zcxx01 "
				//+ "			   AND  A.SPXX01 = C.SPXX01"
				+ "			   AND  A.ZTID = '"
				+ cds.getField("ZTID", 0).toString() + "'"
				+ "			   AND  A.HBID = '" + cds.getField("HBID", 0).toString()
				+ "'"
				//--------modify 2015.12.17. 增加查询条件开始
				+ " AND K.ZCXX01 ='" + cds.getField("ZTID", 0).toString() + "'"
//				+ " AND K.SPXX01 ='" + cds.getField("SPXX01", 0).toString() + "'"
//				+ " AND K.CK01 ='" + cds.getField("CK01", 0).toString() + "'"
				+ " AND (C.zcxx01= D.zcxx01 and A.spxx01=D.spxx01)"
				+ " GROUP BY A.SPXX01,A.HBID, A.ZTID ";
				//--------modify 2015.12.17. 增加查询条件结束
		// ckqxSql+//仓库权限
		// "	      GROUP BY  A.SPXX01,A.HBID,B.SPXX02,A.ZTID,A.GWC01, B.SPXX04,A.GWC02,A.GWC03, C.SPGL14, C.SPGL15,"
		// +
		// "                           K.KCXX01,C.SPGL16,C.SPGL06,C.SPGL02, C.SPGL07,B.SPXX09,B.SPXX10,B.SPXX11,C.CKSP12,B.SPFL01_CODE,A.DQXX01,B.SPXX09,B.SPXX10,B.SPXX11";

		List spxxList = queryForList(o2o, querySQL);
		Map cxmap = new HashMap<String, String>();
		cxmap.put("ZCXX01", cds.getField("ZTID", 0));
		if (spxxList != null && spxxList.size() > 0) {
			for (int i = 0; i < spxxList.size(); i++) {
				Map spmap = (Map) spxxList.get(i);
				// 如果是套餐商品则加入套餐明细
				List<Integer> num = new ArrayList<Integer>();
				// 仓库数量
				num.add(Integer.valueOf(spmap.get("CKSL").toString()));
				// 商品本身自带的限购数量
				num.add(Integer.valueOf(spmap.get("SPGL15").toString()));

				/**
				 * -- 待删除 if(1 ==
				 * Integer.valueOf(spmap.get("SPGL16").toString())){ //查询套餐明细
				 * List tcList= cartManage.selectTCProductInfo(spmap);
				 * if(tcList!=null&&!"".equals(tcList)&&tcList.size()>0){
				 * spmap.put("tcList", tcList); } }else{ //查询促销执行表看是否有促销活动信息
				 * cxmap.put("SPXX01", spmap.get("SPXX01").toString());
				 * JSONArray cxzxbArray = JSONArray.fromObject(cxmap); Map
				 * cxzxbMap = selectSpcxInfo(cxzxbArray.toString()); if(cxzxbMap
				 * != null && cxzxbMap.size() > 0){ //促销商品加入list
				 * spmap.put("cxzxbMap", cxzxbMap); if(1 ==
				 * Integer.valueOf(cxzxbMap.get("HDLX").toString())){
				 * num.add(Integer.valueOf(cxzxbMap.get("HDSL").toString()) -
				 * Integer.valueOf(cxzxbMap.get("YSSL").toString()));
				 * num.add(Integer.valueOf(cxzxbMap.get("HYXL").toString())); }
				 * } }
				 */
				spmap.put("SPGL15", JLTools.getMinNum(num));
			}
		}
		Map map = new HashMap();

		map.put("SPITEMLIST", spxxList);
		// 查询门店或者地区
		/**
		 * List<Map<String, Object>> dqxxList=
		 * productItem.selectDqxxForCk(ztid); if(dqxxList!=null){
		 * map.put("dqxxList", dqxxList); }
		 */
		return map;
	}

	/**
	 * 根据主体编码、伙伴编码、商品集合删除购物车里面的商品数据
	 * 
	 * @param String
	 *            XmlData - String ZCXX01 主体编码 - String SPXX01 商品编码 - String
	 *            DQXX01 地区编码
	 * 
	 * @return Map - STATE 返回标记（0：失败 1：成功） - ERRORMESSAGE 失败原因（成功返回空）
	 * @note 一般用于前台点击“我的购物车”界面中“删除按钮”
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping("/selectSpcxInfo.action")
	public Map selectSpcxInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL = "SELECT HDBH,"
				+ "				  HYXL,"
				+ "				  CXJG,"
				+ "				  HDSL,"
				+ "				  YSSL,"
				+ "				  HDMS,"
				+ "				  B.BEGAINDATE,"
				+ "				  B.ENDDATE,"
				+ "				  SYSDATE() SYSTEMDATE,"
				+ "				  IFNULL((SELECT GMSL FROM W_SPXGSL W WHERE W.HDBH = B.HDBH AND W.GSXX01 = B.GSXX01 AND W.SPXX01 = B.SPXX01),0) YGMSL ,"
				+ "				  HDLX," + "				  B.SJFS," + "				  B.YYBEGAINDATE,"
				+ "				  B.YYENDDATE " + "		     FROM W_CXZXB B "
				+ "		    WHERE SPXX01 = '" + cds.getField("SPXX01", 0) + "'"
				+ "			  AND GSXX01 = '" + cds.getField("ZCXX01", 0) + "'"
				+ "			  AND DQXX01 LIKE SUBSTR('" + cds.getField("DQXX01", 0)
				+ "',1,6)"
				+ "			  AND SYSDATE() >= IFNULL(B.YYBEGAINDATE,SYSDATE())"
				+ "			  AND SYSDATE() <= B.ENDDATE	";

		Map cxmap = (Map) queryForMap(o2o, querySQL);
		return cxmap;
	}

	/**
	 * 根据主体编码、伙伴编码、商品集合删除购物车里面的商品数据
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String SPLIST
	 *            商品集合 - String SPXX01 商品编码
	 * 
	 * @return Map - STATE 返回标记（0：失败 1：成功） - ERRORMESSAGE 失败原因（成功返回空）
	 * @throws Exception
	 * @note 一般用于前台点击“我的购物车”界面中“删除按钮”
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/deleteGwc.action")
	public Map deleteGwc(String XmlData) throws Exception {
		Map ResultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			StringBuffer sql = new StringBuffer();
			String spxx = cds.getField("SPLIST", 0);
			JSONArray spxxList = JSONArray.fromObject(spxx);
			sql.append("DELETE FROM W_GWC ");
			sql.append("WHERE ZTID =ZTID?");
			sql.append(" AND HBID =HBID?");
			if (spxxList != null && spxxList.size() > 0) {
				sql.append(" AND SPXX01 IN(");
				for (int i = 0; i < spxxList.size(); i++) {
					Map spmap = (Map) spxxList.get(i);
					if (i == 0) {
						sql.append("'" + spmap.get("SPXX01").toString() + "'");
					} else {
						sql.append(",'" + spmap.get("SPXX01").toString() + "'");
					}
				}
				sql.append(")");
			}
			// 获取行值
			Map row = getRow(sql.toString(), null, 0);
			execSQL(o2o, sql.toString(), row);
			ResultMap.put("STATE", "1");
			ResultMap.put("ERRORMESSAGE", "");
		} catch (Exception e) {
			e.printStackTrace();
			ResultMap.put("STATE", "0");
			ResultMap.put("ERRORMESSAGE", "删除失败！");
		}
		return ResultMap;
	}

	/**
	 * 根据主体编码、伙伴编码、商品集合删除购物车里面的商品数据
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String SPLIST
	 *            商品集合 - String SPXX01 商品编码
	 * 
	 * @return Map - STATE 返回标记（0：失败 1：成功） - ERRORMESSAGE 失败原因（成功返回空）
	 * @throws Exception
	 * @note 用于订单生成删除购物车商品
	 */

	public Map deleteGwcMap(Map Gwcmap) throws Exception {
		Map ResultMap = new HashMap();
		try {
			StringBuffer sql = new StringBuffer();
			String spxx = Gwcmap.get("SPLIST").toString();
			JSONArray spxxList = JSONArray.fromObject(spxx);
			sql.append("DELETE FROM W_GWC ");
			sql.append("WHERE ZTID ='" + Gwcmap.get("ZTID").toString() + "'");
			sql.append(" AND HBID ='" + Gwcmap.get("HBID").toString() + "'");
			if (spxxList != null && spxxList.size() > 0) {
				sql.append(" AND SPXX01 IN(");
				for (int i = 0; i < spxxList.size(); i++) {
					Map spmap = (Map) spxxList.get(i);
					if (i == 0) {
						sql.append("'" + spmap.get("SPXX01").toString() + "'");
					} else {
						sql.append(",'" + spmap.get("SPXX01").toString() + "'");
					}
				}
				sql.append(")");
			}
			// 获取行值
			Map row = new HashMap();
			execSQL(o2o, sql.toString(), row);
			ResultMap.put("STATE", "1");
			ResultMap.put("ERRORMESSAGE", "");
		} catch (Exception e) {
			e.printStackTrace();
			ResultMap.put("STATE", "0");
			ResultMap.put("ERRORMESSAGE", "删除失败！");
		}
		return ResultMap;
	}

	/**
	 * 根据主体编码、伙伴编码查询购物车商品信息
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String DQXX01
	 *            地区编码
	 * 
	 * @return Map - ZTID 供货商编码 - ZCXX02 供货商名称 - DWYEZ04 账户余额 - SPNUM 商品数量小计 -
	 *         TOTALAMOUNT 供货商商品金额总计 - SPITEMLIST 商品列表 - SPXX01 商品内码 - SPXX02
	 *         商品编码 - SPXX04 商品名称 - GWC01 商品数量 - GWC02 商品价格 - SINGLEAMOUNT
	 *         单个商品金额小计
	 * @note 前台用于订单信息完善页面中在指定结算供货商下的商品信息
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectGwcByZtidHbid.action")
	public Map selectGwcByZtidHbid(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL = " 	SELECT 	A.ZTID," + "		 	B.ZCXX01,"
				+ "		 	SUM(A.GWC01 * A.GWC02) TOTALAMOUNT," + "		 	B.ZCXX02,"
				+ "		 	SUM(A.GWC01) TOTALNUM" + "     FROM 	W_GWC A, W_ZCXX B"
				+ "    WHERE 	A.ZTID = '" + cds.getField("ZTID", 0) + "'"
				+ "      AND 	A.HBID = '" + cds.getField("HBID", 0) + "'"
				+ " 	   AND  A.ZTID = B.ZCXX01"
				+ " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02";
		Map infoMap = queryForMap(o2o, querySQL);
		Map parmap = new HashMap<String, String>();
		parmap.put("DQXX01", cds.getField("DQXX01", 0));
		UserInfo userInfo = new UserInfo(o2o, scm);
		if (infoMap != null && infoMap.size() > 0) {
			parmap.put("ZTID", infoMap.get("ZTID").toString());
			parmap.put("HBID", cds.getField("HBID", 0).toString());
			JSONArray jsonArray = JSONArray.fromObject(parmap);
			Map yemap = userInfo.selectUserDwyez(jsonArray.toString());
			infoMap.put("DWYEZ04", yemap.get("DWYEZ04").toString());
			infoMap.putAll(selectGwcSpxxInfo(jsonArray.toString()));
		}
		return infoMap;
	}

	/**
	 * 根据伙伴编码查询收货地址信息
	 * 
	 * @param String
	 *            XmlData - String ZCXX01 客户编码
	 * 
	 * @return Map - DZBH 地址编号 - SHRMC 收货人 - MOBILE 手机号码 - YZBM 邮政编码 - ADDRESS
	 *         收货地址
	 * 
	 * @note 用于前台查询收货地址信息
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectAddressInfo.action")
	public Map selectAddressInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL = "";
		String mrdzSQL = "";
		if (ROADMAP != 4) {
			if (ROADMAP == 2) {
				mrdzSQL = "  W.MRBJ, ";
			}
			querySQL = "SELECT W.DZLX,W.DZBH,"
					+ "				  		  W.WLDW01 ZCXX01,"
					+ "				          W.SHRMC  SHRMC,"
					+ "				          W.PROVINCE,"
					+ "						  (SELECT Q.DQXX02 FROM DQXX Q WHERE Q.DQXX01 = W.PROVINCE)"
					+ "						  || (SELECT X.DQXX02 FROM DQXX X WHERE X.DQXX01 = W.CITY)"
					+ "			              || (SELECT X.DQXX02 FROM DQXX X WHERE X.DQXX01 = W.COUNTY)"
					+ "						  || W.OTHERDZ ADDRESS, (SELECT Q.dqbzm02 FROM dqbzm Q WHERE Q.dqbzm01 = W.PROVINCE) PROVINCE_NAME, (SELECT X.dqbzm02 FROM dqbzm X WHERE X.dqbzm01 = W.CITY) CITY_NAME,"
					+ "						  W.CITY,"
					+ "						  W.COUNTY,"
					+ "						  (SELECT Q.dqbzm02 FROM dqbzm Q	WHERE Q.dqbzm01 = W.COUNTY) COUNTY_NAME,"
					+ "						  W.OTHERDZ," + "						  W.SJHM MOBILE,"
					+ "						  W.YZBM," + "" + mrdzSQL + "						  W.EMAIL" +

					"				    FROM  W_SHDZWH W" + "				   WHERE  W.WLDW01 = '"
					+ cds.getField("ZCXX01", 0) + "'"
					+ "					ORDER BY W.DZBH ASC";
		} else {// 汽服云

			if (!"".equals(cds.getField("MARK", 0))) {
				mrdzSQL = " AND W.MARK = '" + cds.getField("MARK", 0) + "'";
			}
			if (!"".equals(cds.getField("DZBH", 0))) {
				mrdzSQL = mrdzSQL + " AND W.DZBH = '" + cds.getField("DZBH", 0)
						+ "'";
			}
			querySQL = "SELECT W.DZLX,W.DZBH,"
					+ "				  		  W.WLDW01 ZCXX01,"
					+ "				          W.SHRMC  SHRMC,"
					+ "				          W.PROVINCE,"
					+ "						 CONCAT( (SELECT Q.DQBZM02 FROM DQBZM Q WHERE Q.DQBZM01 = W.PROVINCE)"
					+ "						 , (SELECT X.DQBZM02 FROM DQBZM X WHERE X.DQBZM01 = W.CITY)"
					+ "			              , (SELECT X.DQBZM02 FROM DQBZM X WHERE X.DQBZM01 = W.COUNTY)"
					+ "			              , (SELECT CASE WHEN  ( SELECT X.DQBZM02  FROM DQBZM X WHERE X.DQBZM01 = W.STREET )is null  then  '' else ( SELECT X.DQBZM02  FROM DQBZM X WHERE X.DQBZM01 = W.STREET ) END )"
					+ "						  , W.OTHERDZ) ADDRESS,(SELECT Q.dqbzm02 FROM dqbzm Q WHERE Q.dqbzm01 = W.PROVINCE) PROVINCE_NAME, (SELECT X.dqbzm02 FROM dqbzm X WHERE X.dqbzm01 = W.CITY) CITY_NAME,"
					+ "						  W.CITY,"
					+ "						  W.COUNTY,"
					+ "						  (SELECT Q.dqbzm02 FROM dqbzm Q	WHERE Q.dqbzm01 = W.COUNTY) COUNTY_NAME,"
					+ "						  W.STREET,(SELECT Q.dqbzm02 FROM dqbzm Q WHERE Q.dqbzm01 = W.STREET) STREET_NAME,"
					+ "						  W.OTHERDZ," + "						  W.SJHM MOBILE,"
					+ "						  W.GDDH," + "						  W.YZBM,"
					+ "						  W.EMAIL,W.MARK" + "				    FROM  W_SHDZWH W"
					+ "				   WHERE  W.WLDW01 = '" + cds.getField("ZCXX01", 0)
					+ "'" + "" + mrdzSQL + "					ORDER BY W.DZBH ASC";
		}
		List result = queryForList(o2o, querySQL);

		Map map = new HashMap();
		map.put("ADDRESSLIST", result);
		return map;
	}

	/**
	 * 根据伙伴编码查询收货地址信息
	 * 
	 * @param String
	 *            XmlData - String ZCXX01 客户编码
	 * 
	 * @return Map - DZBH 地址编号 - SHRMC 收货人 - MOBILE 手机号码 - YZBM 邮政编码 - ADDRESS
	 *         收货地址
	 * 
	 * @note 用于前台查询收货地址信息
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectAddressInfo_mj.action")
	public Map selectAddressInfo_mj(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL = "";
		querySQL =" SELECT W.DZLX,W.DZBH,W.WLDW01 ZCXX01,W.SHRMC  SHRMC, W.PROVINCE,CONCAT( (SELECT Q.DQBZM02 FROM DQBZM Q WHERE Q.DQBZM01 = W.PROVINCE), (SELECT X.DQBZM02 FROM DQBZM X WHERE X.DQBZM01 = W.CITY), (SELECT X.DQBZM02 FROM DQBZM X WHERE X.DQBZM01 = W.COUNTY), (SELECT CASE WHEN  ( SELECT X.DQBZM02  FROM DQBZM X WHERE X.DQBZM01 = W.STREET )IS NULL  THEN  '' ELSE ( SELECT X.DQBZM02  FROM DQBZM X WHERE X.DQBZM01 = W.STREET ) END ), W.OTHERDZ) ADDRESS,(SELECT Q.dqbzm02 FROM dqbzm Q WHERE Q.dqbzm01 = W.PROVINCE) PROVINCE_NAME, (SELECT X.dqbzm02 FROM dqbzm X WHERE X.dqbzm01 = W.CITY) CITY_NAME, W.CITY, W.COUNTY, (SELECT Q.dqbzm02 FROM dqbzm Q	WHERE Q.dqbzm01 = W.COUNTY) COUNTY_NAME, W.STREET,(SELECT Q.dqbzm02 FROM dqbzm Q WHERE Q.dqbzm01 = W.STREET) STREET_NAME, W.OTHERDZ, W.SJHM MOBILE,  W.GDDH, W.YZBM, W.EMAIL,W.MARK	FROM  W_SHDZWH W WHERE  W.DZBH='" + cds.getField("DZBH", 0)+"' ORDER BY W.DZBH ASC ";
		System.out.println(querySQL);
		List result = queryForList(o2o, querySQL);
		Map map = new HashMap();
		map.put("ADDRESSLIST", result);
		return map;
	}

	/**
	 * 根据伙伴编码查询发票信息
	 * 
	 * @param String
	 *            XmlData - String ZCXX01 客户编码
	 * 
	 * @return Map - ZCXX37 开票户头 - ZCXX28 纳税人识别号 - ZCXX22 开户银行 - ZCXX23 财务账号 -
	 *         ZCXX29 发票联系电话 - ZCXX24 发票邮寄地址
	 * 
	 * @note 用于前台查询收货地址信息
	 */

	@RequestMapping("/selectInvoiceInfo.action")
	public Map selectInvoiceInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL = "SELECT C.ZCXX37,C.ZCXX28, C.ZCXX22, C.ZCXX24, C.ZCXX29, C.ZCXX23"
				+ "		     FROM W_ZCXX C"
				+ "			WHERE C.ZCXX01 = '"
				+ cds.getField("ZCXX01", 0) + "'";

		Map resultMap = queryForMap(o2o, querySQL);
		return resultMap;
	}

	/**
	 * 将商品加入购物车
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String SPLIST
	 *            商品集合 - int SPXX01 商品编码 - Double GWC01 商品数量 - Double GWC02 商品价格
	 * @return Map - STATE 状态（0失败 1成功） - TOTALNUM 客户购物车商品总数量 - TOTALAMOUNT
	 *         客户购物车商品总金额
	 * 
	 * @note 将前台传递的商品信息加入购物车
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/insertGwcSpxx.action")
	public Map insertGwcSpxx(String XmlData) {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			String sql = "";
			String spxx = cds.getField("SPLIST", 0);
			JSONArray spxxList = JSONArray.fromObject(spxx);
			if (spxxList != null && spxxList.size() > 0) {
				for (int i = 0; i < spxxList.size(); i++) {
					Map spxxInfo = (Map) spxxList.get(i);
					
					//modify 2015.12.17.确认订单主体ID
					String ztid = productQueryService.queryOrderZtidByParam(cds.getField("ZTID", 0), cds.getField("DQXX01", 0), spxxInfo.get("SPXX01").toString());
					// 首先进行数据验证，如果存在则进行更新数量 如果不存在则进行插入
					sql = "SELECT COUNT(0) FROM W_GWC "
							+ "   WHERE ZTID = '"
							//+ cds.getField("ZTID", 0)
							+ ztid
							+ "'"
							+ "	  AND HBID = '"
							+ cds.getField("HBID", 0)
							+ "'"
							+ "	  AND SPXX01 = "
							+ Integer
									.valueOf(spxxInfo.get("SPXX01").toString());
					int count = queryForInt(o2o, sql);
					//modify 2015.12.17. 注释掉
					/*
					if (ROADMAP == 2) {
						sql = "SELECT  (select ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' and k.spxx01= A.SPXX01 and k.zcxx01=A.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01='"
								+ cds.getField("DQXX01", 0)
								+ "')) SPIMGURL "
								+ " FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z"
								+ " WHERE A.SPXX01 = N.SPXX01 "
								+ " AND A.ZCXX01 = N.ZCXX01 "
								+ " AND A.SPXX01 = B.SPXX01 "
								+ " AND A.ZCXX01 = Z.ZCXX01 "
								+ " AND A.SPGL12 IN ('1','3') "
								+ " AND A.SPXX01 = '"
								+ Integer.valueOf(spxxInfo.get("SPXX01")
										.toString())
								+ "' "
								+ " AND A.ZCXX01 = '"
								+ cds.getField("ZTID", 0)
								+ "'";
					} else {
						sql = "SELECT 	(select ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 where ck.ck09= '0' and k.spxx01= A.SPXX01 and k.zcxx01=A.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01='"
								+ cds.getField("DQXX01", 0)
								+ "')) SPIMGURL "
								+ " FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z"
								+ " WHERE A.SPXX01 = N.SPXX01 "
								+ " AND A.ZCXX01 = N.ZCXX01 "
								+ " AND A.SPXX01 = B.SPXX01 "
								+ " AND A.ZCXX01 = Z.ZCXX01 "
								+ " AND A.SPGL12 IN ('1','3') "
								+ " AND A.SPXX01 = '"
								+ Integer.valueOf(spxxInfo.get("SPXX01")
										.toString())
								+ "' "
								+ " AND A.ZCXX01 = '"
								+ cds.getField("ZTID", 0)
								+ "'";
					}
					*/
					
					// Map a=queryForMap(o2o, sql);
					// double SPIMGURL=0;
					// if(a.get("SPIMGURL") == null ){
					// SPIMGURL=0;
					// }else{
					// SPIMGURL =
					// Double.parseDouble(a.get("SPIMGURL").toString());
					// }
					if (spxxInfo.get("DQXX01") == null) {
						spxxInfo.put("DQXX01", "");
					}
					//modify 2015.12.17.修改库存数量查询----开始
//					double SPIMGURL = Double.parseDouble(queryForMap(o2o, sql)
//							.get("SPIMGURL").toString());
					double SPIMGURL = 0;
					Map<String, Object> numMap = productQueryService.queryStockNumByParam(ztid, cds.getField("DQXX01", 0),spxxInfo.get("SPXX01").toString());
					if(numMap !=null && numMap.size()>0 && numMap.get("number") !=null){
						SPIMGURL = Double.parseDouble(numMap.get("number").toString());
					}
					//修改库存数量查询----结束
					
					double GWC01_C = Double.valueOf(spxxInfo.get("GWC01")
							.toString());
					if (count == 0) {
						if (GWC01_C <= SPIMGURL) {
							sql = "INSERT INTO W_GWC(ZTID,HBID,SPXX01,GWC01,GWC02,DQXX01) "
									//modify 2015.12.17.修改主体id
									//+ "     VALUES (ZTID?,HBID?,"
									+ "     VALUES ('"+ ztid +"',HBID?,"
									+ Integer.valueOf(spxxInfo.get("SPXX01")
											.toString())
									+ ","
									+ Double.valueOf(spxxInfo.get("GWC01")
											.toString())
									+ ","
									+ Double.valueOf(spxxInfo.get("GWC02")
											.toString())
									+ ",'"
									//modify 2015.12.22.修改区域信息的获取
									//+ spxxInfo.get("DQXX01") + "')";
									+ cds.getField("DQXX01", 0) + "')";
							// 获取行值
							Map row = getRow(sql, null, 0);
							execSQL(o2o, sql, row);
							resultMap.put("STATE", "1");
						} else {
							resultMap.put("STATE", "0");
						}
					} else {
						sql = "SELECT GWC01 FROM W_GWC "
								+ "   WHERE ZTID = '"
								//modify 2015.12.17.修改主体id
								//+ cds.getField("ZTID", 0)
								+ ztid
								+ "'"
								+ "	  AND HBID = '"
								+ cds.getField("HBID", 0)
								+ "'"
								+ "	  AND SPXX01 = "
								+ Integer.valueOf(spxxInfo.get("SPXX01")
										.toString());
						double GWC01 = Double.parseDouble(queryForMap(o2o, sql)
								.get("GWC01").toString());
						if (GWC01_C + GWC01 <= SPIMGURL) {
							sql = "UPDATE W_GWC SET GWC01 = GWC01 + "
									+ Double.valueOf(spxxInfo.get("GWC01")
											.toString())
									+ ",GWC02 = "
									+ Double.valueOf(spxxInfo.get("GWC02")
											.toString())
									//modify 2015.12.17.修改主体id	及地区信息
									//+ " WHERE ZTID =ZTID? AND HBID =HBID? AND SPXX01 = "
									+ ", dqxx01 = '"
									+ cds.getField("DQXX01", 0) + "'"									
									+ " WHERE ZTID ='"+ ztid +"' AND HBID =HBID? AND SPXX01 = "
									+ Integer.valueOf(spxxInfo.get("SPXX01")
											.toString()) + "";
							Map row = getRow(sql, null, 0);
							execSQL(o2o, sql, row);
							resultMap.put("STATE", "1");
						} else {
							resultMap.put("STATE", "0");
						}
					}
				}
			}
			// 获取商品总数量总金额
			sql = "SELECT IFNULL(SUM(GWC01),0) TOTALNUM,IFNULL(SUM(GWC02*GWC01),0) TOTALAMOUNT FROM W_GWC WHERE HBID = '"
					+ cds.getField("HBID", 0) + "'";
			Map gwcmap = queryForMap(o2o, sql);
			resultMap.putAll(gwcmap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
		}
		return resultMap;
	}

	/**
	 * 将商品加入购物车 -----------关联商品---最佳组合
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String SPLIST
	 *            商品集合 - int SPXX01 商品编码 - Double GWC01 商品数量 - Double GWC02 商品价格
	 * @return Map - STATE 状态（0失败 1成功） - TOTALNUM 客户购物车商品总数量 - TOTALAMOUNT
	 *         客户购物车商品总金额
	 * 
	 * @note 将前台传递的商品信息加入购物车
	 */

	@RequestMapping("/insertGwcGlspxx.action")
	public Map insertGwcGlspxx(String XmlData) {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			String sql = "";
			// String spxx = cds.getField("SPLIST", 0);
			JSONArray spxxList = JSONArray.fromObject(XmlData);
			String ZCXX01 = "";
			if (spxxList != null && spxxList.size() > 0) {
				for (int i = 0; i < spxxList.size(); i++) {
					Map spxxInfo = (Map) spxxList.get(i);
					String ZTID = spxxInfo.get("ZTID").toString();
					String HBID = spxxInfo.get("HBID").toString();
					ZCXX01 = HBID;
					// 首先进行数据验证，如果存在则进行更新数量 如果不存在则进行插入
					sql = "SELECT COUNT(0) FROM W_GWC "
							+ "   WHERE ZTID = '"
							+ ZTID
							+ "'"
							+ "	  AND HBID = '"
							+ HBID
							+ "'"
							+ "	  AND SPXX01 = "
							+ Integer
									.valueOf(spxxInfo.get("SPXX01").toString());
					int count = queryForInt(o2o, sql);

					sql = "SELECT SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02) SPIMGURL "
							+ " FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z, W_KCXX K,CK "
							+ " WHERE A.SPXX01 = N.SPXX01 "
							+ " AND A.ZCXX01 = N.ZCXX01 "
							+ " AND A.SPXX01 = B.SPXX01 "
							+ " AND A.ZCXX01 = Z.ZCXX01 "
							+ " AND A.SPXX01 = K.SPXX01 "
							+ " AND A.ZCXX01 = K.ZCXX01 "
							+ " AND K.CK01 = CK.CK01 AND CK.ck09='0'"
							+ " AND A.SPGL12 IN ('1','3') "
							+ " AND A.SPXX01 = '"
							+ Integer
									.valueOf(spxxInfo.get("SPXX01").toString())
							+ "' " + " AND A.ZCXX01 = '" + ZTID + "'";

					if (spxxInfo.get("DQXX01") == null) {
						spxxInfo.put("DQXX01", "");
					}
					double SPIMGURL = Double.parseDouble(queryForMap(o2o, sql)
							.get("SPIMGURL").toString());
					double GWC01_C = Double.valueOf(spxxInfo.get("GWC01")
							.toString());
					if (count == 0) {
						if (GWC01_C <= SPIMGURL) {
							sql = "INSERT INTO W_GWC(ZTID,HBID,SPXX01,GWC01,GWC02) "
									+ "     VALUES ('"
									+ ZTID
									+ "','"
									+ HBID
									+ "',"
									+ Integer.valueOf(spxxInfo.get("SPXX01")
											.toString())
									+ ","
									+ Double.valueOf(spxxInfo.get("GWC01")
											.toString())
									+ ","
									+ Double.valueOf(spxxInfo.get("GWC02")
											.toString()) + ")";
							// 获取行值
							Map row = getRow(sql, null, 0);
							execSQL(o2o, sql, row);
							resultMap.put("STATE", "1");
						} else {
							resultMap.put("STATE", "0");
						}
					} else {
						sql = "SELECT GWC01 FROM W_GWC "
								+ "   WHERE ZTID = '"
								+ ZTID
								+ "'"
								+ "	  AND HBID = '"
								+ HBID
								+ "'"
								+ "	  AND SPXX01 = "
								+ Integer.valueOf(spxxInfo.get("SPXX01")
										.toString());
						double GWC01 = Double.parseDouble(queryForMap(o2o, sql)
								.get("GWC01").toString());
						if (GWC01_C + GWC01 <= SPIMGURL) {
							sql = "UPDATE W_GWC SET GWC01 = GWC01 + "
									+ Double.valueOf(spxxInfo.get("GWC01")
											.toString())
									+ ",GWC02 = "
									+ Double.valueOf(spxxInfo.get("GWC02")
											.toString())
									+ " "
									+ "WHERE ZTID ='"
									+ ZTID
									+ "' AND HBID ='"
									+ HBID
									+ "' AND SPXX01 = "
									+ Integer.valueOf(spxxInfo.get("SPXX01")
											.toString()) + "";
							Map row = getRow(sql, null, 0);
							execSQL(o2o, sql, row);
							resultMap.put("STATE", "1");
						} else {
							resultMap.put("STATE", "0");
						}
					}
				}
			}
			// 获取商品总数量总金额
			sql = "SELECT IFNULL(SUM(GWC01),0) TOTALNUM,IFNULL(SUM(GWC02*GWC01),0) TOTALAMOUNT FROM W_GWC WHERE HBID = '"
					+ ZCXX01 + "'";
			Map gwcmap = queryForMap(o2o, sql);
			resultMap.putAll(gwcmap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
		}
		return resultMap;
	}

	/**
	 * 修改购物车商品数量
	 * 
	 * @param String
	 *            XmlData - String GWC01 商品数量 - String ZTID 主体编码 - String HBID
	 *            伙伴编码 - String SPXX01 商品编码
	 * @return Map - STATE 状态（0失败 1成功）
	 * 
	 * @note 将前台传递的商品数量修改
	 */
	@RequestMapping("/updateGWCspxx.action")
	public Map updataGWCSpxx(String XmlData) {
		Map map = new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			// 查询库存数量
			//modify 2015.12.31. 修改库存数量查询
//			String ssString = cartManage.selectCXSP(XmlData).get("MAXNUM")
//					.toString();
//			String maxNum = ssString.substring(
//					0,
//					(ssString.indexOf(".") == -1 ? ssString.length() : ssString
//							.indexOf(".")));
//			int kcsl = Integer.parseInt(maxNum);
			int kcsl = 0;
			Map numMap = productQueryService.queryStockNum(XmlData);
			if(numMap != null && numMap.get("number")!=null){
				float num = Float.parseFloat(numMap.get("number").toString());
				kcsl = (int)num;
			}
			// 查询购物车数量
			int gwcNum = cartManage.selectMyCartSPNum(XmlData);
			// 修改数量
			int passNum = Integer.parseInt(cds.getField("GWC01", 0));
			if (passNum > kcsl) {
				map.put("STATE", 0);
				map.put("errorMessage", "购买数量不能大于限定数量");
				return map;
			}
			String sql = "UPDATE W_GWC SET GWC01='" + cds.getField("GWC01", 0)
					+ "' WHERE ZTID='" + cds.getField("ZTID", 0)
					+ "' AND HBID='" + cds.getField("HBID", 0)
					+ "' AND SPXX01='" + cds.getField("SPXX01", 0) + "'";
			Map row = getRow(sql, null, 0);
			int i = execSQL(o2o, sql, row);
			if (i > 0) {
				map.put("STATE", 1);
				map.put("SPSL", cds.getField("GWC01", 0));
			} else {
				map.put("STATE", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", 0);
		}
		return map;
	}

	/**
	 * 查询该客户购物车内商品种类数量
	 * 
	 * @param String
	 *            XmlData - HBID 公司ID
	 * 
	 * @return Map - GWCNUM 商品种类数量
	 * @note
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping("/selectGwcNum.action")
	public Map selectGwcNum(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String sql = "SELECT COUNT(1) GWCNUM\n" + "  FROM (SELECT COUNT(1)\n"
				+ "          FROM W_GWC A\n" + "         WHERE A.HBID = '"
				+ cds.getField("HBID", 0)
				+ "'         GROUP BY A.ZTID, A.HBID, A.SPXX01) C";

		Map map = queryForMap(o2o, sql);
		return map;
	}

	/**
	 * 根据伙伴编码查询收货地址信息
	 * 
	 * @param String
	 *            XmlData - String ZCXX01 客户编码
	 * 
	 * @return Map - DZBH 地址编号 - SHRMC 收货人 - MOBILE 手机号码 - YZBM 邮政编码 - ADDRESS
	 *         收货地址
	 * 
	 * @note 用于前台查询收货地址信息
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/updateAddressInfo.action")
	public Map updateAddressInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		System.out.println(XmlData);

		String sql = "";
		if (cds.getField("DZBH", 0).equals("")) {
			LinkedList inParameter = new LinkedList();
			inParameter.add("W_SHDZWH");
			inParameter.add(1);
			LinkedList outParameter = new LinkedList();
			outParameter.add(java.sql.Types.INTEGER);
			String sqlq = "{call Update_WBHZT(?,?,?)}";
			List DZBH = callProc(o2o, sqlq, inParameter, outParameter);

			System.out.println("Update_WBHZT过程返回的单号:" + DZBH.get(0));

			sql = "INSERT INTO W_SHDZWH(DZBH,WLDW01,SHRMC,PROVINCE,CITY,COUNTY,OTHERDZ,SJHM,YZBM) "
					+ "VALUES("
					+ DZBH.get(0)
					+ ",WLDW01?,SHRMC?,PROVINCE?,CITY?,COUNTY?,OTHERDZ?,SJHM?,YZBM?)";
		} else {
			sql = "UPDATE W_SHDZWH SET WLDW01=WLDW01?,SHRMC=SHRMC?,PROVINCE=PROVINCE?,CITY=CITY?,COUNTY=COUNTY?,OTHERDZ=OTHERDZ?,SJHM=SJHM?,YZBM=YZBM? "
					+ "WHERE DZBH=DZBH?";
		}

		Map row = getRow(sql, null, 0);

		int i = execSQL(o2o, sql, row);
		System.out.println("数据库返回值：" + i);

		// List result = queryForList(o2o, querySQL);

		Map map = new HashMap();

		if (i > 0) {
			map.put("STATE", "0");
		} else {
			map.put("STATE", "1");
		}
		return map;
	}

	/**
	 * 根据地址编号删除收货地址信息
	 * 
	 * @param String
	 *            XmlData - String DZBH 客户编码
	 * 
	 * @return Map - STATE 成功标记
	 * 
	 * @note 用于前台查询收货地址信息
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/deleteAddressInfo.action")
	public Map deleteAddressInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);

		String sql = "DELETE W_SHDZWH W WHERE W.DZBH=DZBH?";

		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);

		Map map = new HashMap();
		if (i > 0) {
			map.put("STATE", "0");
		} else {
			map.put("STATE", "1");
		}
		return map;
	}

	/**
	 * 加入购物车或修改购物车数量
	 * 
	 * @param cartGoodsCount
	 * @param SPXX01
	 * @param SPJG
	 * @param ZCXX01
	 * @param ZTID
	 * @param maxCount
	 *            (库存和限定数量取最小)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertOrUpdateCartCount.action")
	public Map<String, Object> insertOrUpdateCartCount(String XmlData)
			throws Exception {
		cds = new DataSet(XmlData);
		Map hm = selectGoodsNumForId(XmlData);
		int sumCount = Integer.parseInt(cds.getField("cartGoodsCount", 0));
		int count = Integer.parseInt(hm.get("NUM").toString());
		int maxCount = Integer.parseInt(cds.getField("maxCount", 0));
		int spxxCount = sumCount + count;// 购物车里面的商品和传过来的数量
		String sql = "";
		if (count == 0) {
			sql = "INSERT INTO W_GWC\n"
					+ "  (ZTID, HBID, SPXX01, GWC01, GWC02)\n" + "VALUES\n"
					+ "  ('" + cds.getField("ZTID", 0) + "', '"
					+ cds.getField("ZCXX01", 0) + "', "
					+ cds.getField("SPXX01", 0) + ", " + sumCount + ", "
					+ cds.getField("SPJG", 0) + ");";

		} else if (count + sumCount > maxCount) {
			hm.put("error", "超过限购数量,请查看购物车是否有该商品");
			hm.put("status", 0);
			return hm;
		} else {
			sql = "UPDATE W_GWC\n" + "   SET GWC01 = " + spxxCount + "\n"
					+ " WHERE SPXX01 = " + cds.getField("SPXX01", 0) + "\n"
					+ "   AND ZTID = '" + cds.getField("ZTID", 0) + "'\n"
					+ "   AND HBID = '" + cds.getField("ZCXX01", 0) + "'";
		}
		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);
		if (i > 0) {
			hm.put("success", "加入购物车成功!");
			hm.put("status", 1);
		} else {
			hm.put("error", "加入购物车失败");
			hm.put("status", 0);
		}
		return hm;
	}

	/**
	 * 查询购物车该商品的数量
	 * 
	 * @param SPXX01
	 *            -商品编码
	 * @param ZCXX01
	 *            -用户名编码
	 * @param ZTID
	 *            -供货商编码
	 * @return -NUM -商品数量
	 * @param -CXSPAMOUNT 促销商品金额
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectGoodsNumForId.action")
	public Map<String, Object> selectGoodsNumForId(String XmlData)
			throws Exception {
		cds = new DataSet(XmlData);
		String sql = "SELECT Z.GWC01 NUM   \n" + "  FROM W_GWC Z\n"
				+ " WHERE Z.SPXX01 = " + cds.getField("SPXX01", 0) + "\n"
				+ "   AND Z.ZTID = '" + cds.getField("ZTID", 0) + "'\n"
				+ "   AND Z.HBID = '" + cds.getField("ZCXX01", 0) + "'";
		Map hm = new HashMap();
		try {
			hm = queryForMap(o2o, sql);
		} catch (Exception e) {

		}
		if (hm == null || "".equals(hm.get("NUM"))) {
			hm = new HashMap<String, Object>();
			hm.put("NUM", "0");
		} else if (hm.get("NUM") == null) { // 防止插入数据时插入null
			hm.put("NUM", "0");
		}
		return hm;
	}

	/**
	 * 查询地区仓库 的商品库存
	 * 
	 * @param SPXX01
	 *            商品编码
	 * @param ZCXX01
	 *            商品对应主体编码
	 * @param DQXX01
	 *            地区仓库
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectDQCKOfPruduct.action")
	public Map<String, Object> selectDQCKOfPruduct(String XmlData)
			throws Exception {
		Map<String, Object> hm = new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			List<Map<String, Object>> ckspList = cartManage.selectCKSP(XmlData);
			if (ckspList != null && ckspList.size() != 0) {
				Map<String, Object> ckspMap = ckspList.get(0);
				int spnum = Integer.parseInt(ckspMap.get("SPNUM").toString());
				int fmbj = Integer.parseInt(ckspMap.get("FMBJ").toString());
				if (spnum == 0 && fmbj == 0) {
					hm.put("STATE", false);
				} else {
					hm.put("STATE", true);
					hm.putAll(ckspMap);
				}
			} else {
				hm.put("STATE", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return hm;
	}

	/**
	 * 
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectWLMoney.action")
	public Map selectWLMoney(String XmlData) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			JSONArray json = JSONArray.fromObject(XmlData);
			List<Map<String, Object>> listJson = (List<Map<String, Object>>) json;
			List<Map<String, Object>> listSpxx = (List<Map<String, Object>>) (listJson
					.get(0).get("topics"));
			for (Map mp : listSpxx) {
				// 判断商品是否是组合商品
				String spxx01 = mp.get("SPXX").toString();
				Map splx = cartManage.selectSplx(spxx01);
				if (splx != null && splx.get("SPLX") != null) {
					int lx = Integer.parseInt(splx.get("SPLX").toString());
					if (lx == 1) {
						// 套餐商品
						List<Map> spList = cartManage.selectGWCForZTID(spxx01);
						for (Map spMap : spList) {
							Map hm = new HashMap<String, Object>();
							hm.put("SPXX", spMap.get("SPXX01"));
							hm.put("SL", spMap.get("ZHSL"));
							listSpxx.add(hm);
						}
					} else {
						Map<String, Object> spMap = cartManage.selectErpSpxx(mp
								.get("SPXX").toString());
						mp.put("SPXX", spMap.get("SPXX"));
					}
				}
			}
			Map hm = getWLFY(JSONObject.fromObject(listJson.get(0)).toString());
			map.putAll(hm);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}

	/**
	 * @todo 获取物流费用
	 * @param jsonData
	 * @return
	 */
	@RequestMapping("/getWLFY.action")
	public Map getWLFY(String XmlData) {
		double postage = 0;
		String postageMoney;
		Map map = new HashMap();
		try {
			postageMoney = JLTools
					.sendToSync(
							XmlData,
							JlAppResources.getProperty("V7Service_URL")
									+ "/jlwebserver/JLQueryServlet?jlc=jlserver.jlscm.jlsh.bill.BSHJS&jlm=callProc&lx=EXEC_SHJS_TW&jles=1&json="
									+ XmlData);
			postage = Double.valueOf(postageMoney);
		} catch (Exception e) {
			// e.printStackTrace();
			postage = -1;
		}
		map.put("wlfy", new Double(postage));
		return map;
	}

	@RequestMapping("/queryMyOrderOffline.action")
	public List queryMyOrderOffline(String XmlData, HttpServletResponse response)
			throws Exception {
		cds = new DataSet(XmlData);
		// String hbid = cds.getField("HBID", 0);
		String hbid = "80013802";
		String sql = "select a.lsd01," + " a.bm01, "
				+ " (SELECT BM02 FROM BM WHERE BM01 = A.BM01) AS XHBMMC, "
				+ " a.lsd10, " + " TO_CHAR(a.lsd06, 'YYYY-MM-DD') lsd06 "
				+ " from lsd a " + " where a.lsd19 = '" + hbid + "'";
		List ddlist = queryForList(scm, sql);
		System.out.println(ddlist);

		for (int i = 0; i < ddlist.size(); i++) {
			Map map = (Map) ddlist.get(i);
			String lsd01 = map.get("LSD01").toString();
			// String sql1 =
			// "SELECT A.GSXX01, A.LSD01,A.BM01, (SELECT BM02 FROM BM WHERE BM01 = A.BM01) AS XHBMMC,         A.LSD02, A.LSD03, A.LSD04, A.LSD05, A.LSD06, A.LSD07, A.DYCS01, A.LSDG01,         A.LSD16 AS YYY ,A.LSD44 AS TJR, A.LSD17 AS SKR,       (SELECT XSFZ02 FROM XSFZ WHERE XSFZ01 = A.XSFZ01 ) XSFZ02,        (SELECT RYXX22 FROM RYXX WHERE RYXX01=A.LSD08 AND GSXX01=A.GSXX01) RYXX22,        CASE MOD(B.LSDI01,100) WHEN 1 THEN A.LSD11 ELSE 0 END LSD11,         CASE MOD(B.LSDI01,100) WHEN 1 THEN A.LSD20 ELSE 0 END LSD20,         A.LSD12, A.LSD13, A.LSD19, A.LSD21, A.LSD22, A.LSD24, A.LSD25, A.LSD34, A.LSD35, A.LSD36, A.LSD40, A.LSD41, A.WSKH, A.LSD42, A.LSD45, A.LSD46,A.LSD49,A.LSD50,        (SELECT FLFS02 FROM FLFS WHERE FLFS01=A.FLFS01) AS FLFS01,         B.LSDI01,B.BM01 CGBM01,B.WLDW01,B.SPXX01,E.WLDW02,         (SELECT C.CK02 FROM CK C WHERE B.CK01 = C.CK01) CKMC,        (SELECT BM02 FROM BM WHERE BM01 = B.BM01) AS CGBMMC,         B.LSDI08 HSCBJE,ROUND(B.LSDI08/(1+E.JXSL01),2) WSCBJE,        ROUND(B.LSDI08 / B.LSDI05,2) HSCBDJ,B.LSDI16 MAOL,        ROUND((B.LSDI08/B.LSDI05)/(1+E.JXSL01),2) WHSCBDJ,         CASE WHEN (B.LSDI08=0) AND (B.LSDI23>0) AND (A.LSD07=TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD')) THEN         B.LSDI06*(1-B.LSDI23) ELSE B.LSDI08 END LSDI08,         CASE WHEN (B.LSDI08=0) AND (B.LSDI31>0) AND (A.LSD07=TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD')) THEN (LSDI06 - B.LSDI05 * (B.LSDI31 * (1+E.JXSL01)))         WHEN (B.LSDI08=0) AND (B.LSDI23>0) AND (A.LSD07=TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD')) THEN         LSDI06-B.LSDI06*(1-B.LSDI23) ELSE B.LSDI16 END LSDI16,         CASE WHEN (B.LSDI06 <> 0) AND (B.LSDI08=0) AND (B.LSDI28=1) THEN (B.LSDI23*100) WHEN (B.LSDI06=0) THEN 0 ELSE         ROUND(B.LSDI16/B.LSDI06 * 100,2) END MLL,B.LSDI05/B.LSDI04 JS,         B.LSDI02, B.LSDI03,B.LSDI04,B.LSDI05, B.LSDI06, B.LSDI07, B.LSDI09, B.LSDI10, B.LSDI11,         B.LSDI12 AS FHR, B.LSDI17,B.LSDI19,B.LSDI20, B.LSDI22, (B.LSDI23*100) LSDI23,B.LSDI24, B.LSDI25,         B.LSDI26, B.LSDI27, B.LSDI28, B.LSDI30, B.LSDI38, B.LSDI39, B.LSDI40, B.LSDI44, B.LSDI45, B.LSDI46, B.LSDI47,         B.LSDI48, B.LSDI49, B.LSDI50, B.LSDI51, B.LSDI52, B.LSDI53, (CASE WHEN B.LSDI57 = 0 THEN B.LSDI37-B.LSDI08 ELSE B.LSDI54 END) LSDI54, B.LSDI55, B.LSDI56, B.LSDI57, D.SPXX02,D.SPXX04, D.SPFL01, D.SPFL02, D.GGB01,         CASE WHEN B.LSDI27 <> 0 THEN (B.LSDI27 - B.LSDI02) * B.LSDI05 ELSE 0 END RLJE, B.LSDI36, B.LSDI37, B.LSDI41, B.LSDI42, B.LSDI43,B.LSDI58, B.LSDI59,B.LSDI61,B.LSDI62,B.LSDI63,        (SELECT GSXX02 FROM GSXX WHERE GSXX01 = A.GSXX01 ) GSXX02,         (SELECT CPFL01||','||CPFL02  FROM SPGNSM WHERE SPXX01=D.SPXX01) CPFL02,         (SELECT SCCJ02 FROM SCCJ   WHERE SCCJ01=D.SCCJ01) SCCJ02,         (SELECT PPB02 FROM PPB WHERE PPB01=D.PPB01) PPB02,D.PPB01,         (SELECT DQXX02 FROM DQXX  WHERE  DQXX01= E.DQXX01 ) DQXX02,         (SELECT HYXX02 FROM HYXX  WHERE  HYXX01= E.HYXX01 ) HYXX02,         (SELECT SPPZ02 FROM SPPZ WHERE SPPZ01 = D.SPPZ01) SPPZ02,         (SELECT SPHS02 FROM SPHS WHERE SPHS01 = D.SPHS01) SPHS02, D.SPXH01,        (SELECT KSLX02 FROM KSLX WHERE KSLX01 = E.KSLX01 ) KSLX02,         F.LSKH02, F.LSKH03, F.LSKH17, F.LSKH18,F.LSKH25,F.LSKH26,B.THFS01,(SELECT THFS02 FROM THFS WHERE THFS01=B.THFS01)THFS02,         (select THSP16 FROM THSPD WHERE GSXX01=A.GSXX01 AND THSP01=A.LSD01)THSP16,         (SELECT NVL(SUM(LSQZ02),0) FROM LSQZ L,QZ Q                 WHERE L.QZ01=Q.QZ01 AND Q.QZ03=3 AND                     L.GSXX01 = B.GSXX01 AND L.LSDI01 = B.LSDI01) LSQZ02, F.LSKH22,        (SELECT L.LSFP19 FROM LSDFP L WHERE L.GSXX01 = A.GSXX01 AND L.LSD01 = A.LSD01 AND A.LSD05=L.LSFP02 AND L.LSFP19 IS NOT NULL AND  = 1) LSFP19,         (CASE WHEN A.LSD27<>0 THEN TO_CHAR(A.LSD27) WHEN A.LSD27=0 AND A.LSD54 IS NOT NULL THEN A.LSD54 END) LSD27,  nvl((Select 1  From SPZTZXB t   Where t.SPXX01 = D.SPXX01  And t.GSXX01 = A.GSXX01),0) SPZT,(CASE WHEN B.LSDI57 = 0 THEN (B.LSDI37-B.LSDI08-B.LSDI65) ELSE (B.LSDI54-B.LSDI65) END) JLSKHML,(B.LSDI37-B.LSDI57)GMKHML,(B.LSDI37-B.LSDI57-B.LSDI65)GMJKHML,   H.XSFZ01,   (B.LSDI37- B.LSDI65) SSJE,(B.LSDI37-B.LSDI65-B.LSDI52)BHCZSSJE,        (B.LSDI06 - B.LSDI08 - B.LSDI65) JML,ROW_NUMBER() OVER(ORDER BY B.ROWID) RN FROM LSDITEM B,LSD A, SPXX D,LSKHXX F, WLDW E,  CK C  , RYXX H , XSFZ J WHERE A.GSXX01 = B.GSXX01 AND A.LSD01=B.LSD01 AND A.LSD01='"+lsd01+"' AND B.CK01=C.CK01 AND B.SPXX01=D.SPXX01 AND B.WLDW01=E.WLDW01  AND A.GSXX01=F.GSXX01(+) AND A.LSD01=F.LSD01(+) AND A.LSD08=H.RYXX01 AND A.XSFZ01=J.XSFZ01(+)  ";
			String sql1 = "SELECT B.LSDI02, B.LSDI05, B.LSDI10, D.SPXX04 FROM LSDITEM B, LSD A, SPXX D, LSKHXX F, WLDW E, CK C, RYXX H, XSFZ J WHERE A.GSXX01 = B.GSXX01 AND A.LSD01 = B.LSD01 AND A.LSD01 = '"
					+ lsd01
					+ "' AND B.CK01 = C.CK01 AND B.SPXX01 = D.SPXX01 AND B.WLDW01 = E.WLDW01 AND A.GSXX01 = F.GSXX01(+) AND A.LSD01 = F.LSD01(+)  AND A.LSD08 = H.RYXX01 AND A.XSFZ01 = J.XSFZ01(+)";
			List dditemlist = queryForList(scm, sql1);
			map.put("dditemlist", dditemlist);
			// System.out.println(ss);
		}
		System.out.println(ddlist);
		return ddlist;
	}

	@RequestMapping("/qfyselectGwcByHbid.action")
	public Map qfyselectGwcByHbid(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String HBID = cds.getField("HBID", 0).toString();
		String querySQL = " 	SELECT 	A.ZTID ,\n" + "		 	B.ZCXX01 ,\n"
				+ "		 	SUM(A.GWC01 * A.GWC02) TOTALAMOUNT,\n"
				+ "		 	B.ZCXX02,\n" + "		 	SUM(A.GWC01) TOTALNUM"
				+ "     FROM 	W_GWC A, W_ZCXX B \n"
				+ "    WHERE 	A.ZTID = B.ZCXX01 \n" + "      AND 	A.HBID = '"
				+ cds.getField("HBID", 0) + "' \n"
				+ " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02";
		List infoList = queryForList(o2o, querySQL);
		Map map = new HashMap();
		map.put("GWCLIST", infoList);
		return map;
	}

	/**
	 * 收藏夹
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/insertCollectSpxx.action")
	public Map insertCollectSpxx(String XmlData) {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			String sql = "";
			String spxx = cds.getField("SPLIST", 0);
			JSONArray spxxList = JSONArray.fromObject(spxx);
			if (spxxList != null && spxxList.size() > 0) {
				for (int i = 0; i < spxxList.size(); i++) {
					Map spxxInfo = (Map) spxxList.get(i);
					// 首先进行数据验证，如果存在则进行更新数量 如果不存在则进行插入
					sql = "SELECT COUNT(0) FROM W_COLLECT "
							+ "   WHERE ZTID = '"
							+ cds.getField("ZTID", 0)
							+ "'"
							+ "	  AND HBID = '"
							+ cds.getField("HBID", 0)
							+ "'"
							+ "	  AND SPXX01 = "
							+ Integer
									.valueOf(spxxInfo.get("SPXX01").toString());
					int count = queryForInt(o2o, sql);
					if (spxxInfo.get("DQXX01") == null) {
						spxxInfo.put("DQXX01", "");
					}
					if (count == 0) {
						sql = "INSERT INTO W_COLLECT(ZTID,HBID,SPXX01) "
								+ "     VALUES (ZTID?,HBID?,"
								+ Integer.valueOf(spxxInfo.get("SPXX01")
										.toString()) + ")";
						// 获取行值
						Map row = getRow(sql, null, 0);
						execSQL(o2o, sql, row);
						resultMap.put("STATE", "1");
					} else {
						resultMap.put("STATE", "0");
					}
				}
			}
			// 获取商品总数量总金额
			sql = "SELECT IFNULL(Count(1),0) TOTALNUM FROM W_COLLECT WHERE HBID = '"
					+ cds.getField("HBID", 0) + "'";
			Map gwcmap = queryForMap(o2o, sql);
			resultMap.putAll(gwcmap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
		}
		return resultMap;
	}

	/**
	 * 收藏夹
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/deleteCollectSpxx.action")
	public Map deleteCollectSpxx(String XmlData) {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			String sql = "Delete W_COLLECT where ZTID=ZTID? and HBID=HBID? and SPXX01=SPXX01?";
			if (cds.getField("SPXX01", 0) == null) {
				sql = "Delete W_COLLECT where ZTID=ZTID? and HBID=HBID? ";
			}
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "1");

			// 获取商品总数量总金额
			sql = "SELECT IFNULL(Count(1),0) TOTALNUM FROM W_COLLECT WHERE HBID = '"
					+ cds.getField("HBID", 0) + "'";
			Map gwcmap = queryForMap(o2o, sql);
			resultMap.putAll(gwcmap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
		}
		return resultMap;
	}

	/**
	 * 查询订单的品牌数量(返利单使用)
	 * 
	 * @param String
	 *            XmlData - String DZBH 客户编码
	 * 
	 * @return Map - STATE 成功标记
	 * 
	 * @note 用于支付界面查询订单的品牌数量
	 */

	@SuppressWarnings({ "unchecked" })
	@RequestMapping("/selectPPSum.action")
	public Map selectPPSum(String XmlData) throws Exception {
		Map map = new HashMap();
		try {

			cds = new DataSet(XmlData);

			String sql = "select COUNT(B.PPB01) COUNT from w_xsdditem A,W_SPXX B  where A.SPXX01=B.SPXX01 AND xsdd01='"
					+ cds.getField("xsdd01", 0) + "'";

			int count = queryForInt(o2o, sql);
			if (count == 1) {
				sql = "select B.PPB01 PPB01 from w_xsdditem A,W_SPXX B  where A.SPXX01=B.SPXX01 AND xsdd01='"
						+ cds.getField("xsdd01", 0) + "'";
				int ppb01 = queryForInt(o2o, sql);
				map.put("ppb01", ppb01);
				map.put("STATE", "1");
			} else {
				map.put("ppb01", "");
				map.put("STATE", "2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 未登录时候将商品加入购物车临时表
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String SPLIST
	 *            商品集合 - int SPXX01 商品编码 - Double GWC01 商品数量 - Double GWC02 商品价格
	 * @return Map - STATE 状态（0失败 1成功） - TOTALNUM 客户购物车商品总数量 - TOTALAMOUNT
	 *         客户购物车商品总金额
	 * 
	 * @note 将前台传递的商品信息加入购物车临时表
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/insertW_GwcTemp.action")
	public Map insertW_GwcTemp(String XmlData) {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			String sql = "";
			String spxx = cds.getField("SPLIST", 0);
			// int GWCBH=0;
			JSONArray spxxList = JSONArray.fromObject(spxx);
			if (spxxList != null && spxxList.size() > 0) {
				for (int i = 0; i < spxxList.size(); i++) {
					Map spxxInfo = (Map) spxxList.get(i);
					// 首先进行数据验证，如果存在则进行更新数量 如果不存在则进行插入
					sql = "SELECT COUNT(0) FROM W_GWCTEMP "
							+ "   WHERE ZTID = '"
							+ cds.getField("ZTID", 0)
							+ "'"
							+ "	  AND HBID = '"
							+ cds.getField("HBID", 0)
							+ "'"
							+ "	  AND SPXX01 = "
							+ Integer
									.valueOf(spxxInfo.get("SPXX01").toString());
					int count = queryForInt(o2o, sql);
					sql = "SELECT SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02) SPIMGURL "
							+ " FROM W_SPGL A, W_SPJGB N, W_SPXX B, W_ZCXX Z, W_KCXX K,CK "
							+ " WHERE A.SPXX01 = N.SPXX01 "
							+ " AND A.ZCXX01 = N.ZCXX01 "
							+ " AND A.SPXX01 = B.SPXX01 "
							+ " AND A.ZCXX01 = Z.ZCXX01 "
							+ " AND A.SPXX01 = K.SPXX01 "
							+ " AND A.ZCXX01 = K.ZCXX01 "
							+ " AND K.CK01 = CK.CK01 AND CK.ck09='0'"
							+ " AND A.SPGL12 IN ('1','3') "
							+ " AND A.SPXX01 = '"
							+ Integer
									.valueOf(spxxInfo.get("SPXX01").toString())
							+ "' "
							+ " AND A.ZCXX01 = '"
							+ cds.getField("ZTID", 0) + "'";
					Map a = queryForMap(o2o, sql);
					if (spxxInfo.get("DQXX01") == null) {
						spxxInfo.put("DQXX01", "");
					}
					double SPIMGURL = Double.parseDouble(queryForMap(o2o, sql)
							.get("SPIMGURL").toString());
					double GWC01_C = Double.valueOf(spxxInfo.get("GWC01")
							.toString());
					if (count == 0) {
						if (GWC01_C <= SPIMGURL) {
							// GWCBH=PubFun.updateWBHZT(o2o,"W_GWCTEMP",1);
							sql = "INSERT INTO W_GWCTEMP(ZTID,HBID,SPXX01,GWC01,GWC02) "
									+ "     VALUES (ZTID?,HBID?,"
									+ Integer.valueOf(spxxInfo.get("SPXX01")
											.toString())
									+ ","
									+ Double.valueOf(spxxInfo.get("GWC01")
											.toString())
									+ ","
									+ Double.valueOf(spxxInfo.get("GWC02")
											.toString()) + ")";
							// 获取行值
							Map row = getRow(sql, null, 0);
							execSQL(o2o, sql, row);
							resultMap.put("STATE", "1");
						} else {
							resultMap.put("STATE", "0");
						}
					} else {
						sql = "SELECT GWC01 FROM W_GWCTEMP "
								+ "   WHERE ZTID = '"
								+ cds.getField("ZTID", 0)
								+ "'"
								+ "	  AND HBID = '"
								+ cds.getField("HBID", 0)
								+ "'"
								+ "	  AND SPXX01 = "
								+ Integer.valueOf(spxxInfo.get("SPXX01")
										.toString());
						double GWC01 = Double.parseDouble(queryForMap(o2o, sql)
								.get("GWC01").toString());
						if (GWC01_C + GWC01 <= SPIMGURL) {
							sql = "UPDATE W_GWCTEMP SET GWC01 = GWC01 + "
									+ Double.valueOf(spxxInfo.get("GWC01")
											.toString())
									+ ",GWC02 = "
									+ Double.valueOf(spxxInfo.get("GWC02")
											.toString())
									+ " WHERE ZTID =ZTID? AND HBID =HBID? AND SPXX01 = "
									+ Integer.valueOf(spxxInfo.get("SPXX01")
											.toString()) + "";
							Map row = getRow(sql, null, 0);
							execSQL(o2o, sql, row);
							resultMap.put("STATE", "1");
						} else {
							resultMap.put("STATE", "0");
						}
					}
				}
			}
			// 获取商品总数量总金额
			sql = "SELECT IFNULL(SUM(GWC01),0) TOTALNUM,IFNULL(SUM(GWC02*GWC01),0) TOTALAMOUNT FROM W_GWCTEMP WHERE HBID = '"
					+ cds.getField("HBID", 0) + "'";
			Map gwcmap = queryForMap(o2o, sql);
			resultMap.putAll(gwcmap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
		}
		return resultMap;
	}

	/**
	 * 查询购物车客户所有的商品信息
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String DQXX01 地区编码
	 * 
	 * @return Map - ZTID 供货商编码 - ZCXX02 供货商名称 - DWYEZ04 账户余额 - TOTALNUM 商品数量小计
	 *         - TOTALAMOUNT 供货商金额总计 - SPITEMLIST 商品列表 - SPXX01 商品内码 - SPXX02
	 *         商品编码 - SPXX04 商品名称 - GWC01 商品数量 - GWC02 商品价格 - SPGL14 商品起订量 -
	 *         SPGL15 商品限定量 - SINGLEAMOUNT 单个商品金额小计
	 * @note 点击“我的购物车” 查询所有购物车信息数据 （未登录状态）
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectGwcTemp.action")
	public Map selectGwcTemp(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String HBID = cds.getField("HBID", 0).toString();
		// 根据客户编码获得购物车中所有供货商的信息（供货商编码、所有商品金额总计、商品数量合计、供货商名称）
		String querySQL = " 	SELECT 	A.ZTID ,\n" + "		 	A.HBID ,\n"
				+ "		 	B.ZCXX01 ,\n"
				+ "		 	SUM(A.GWC01 * A.GWC02) TOTALAMOUNT,\n"
				+ "		 	B.ZCXX02,\n" + "		 	SUM(A.GWC01) TOTALNUM"
				+ "     FROM 	W_GWCTEMP A, W_ZCXX B \n"
				+ "    WHERE 	A.ZTID = B.ZCXX01 \n" + "      AND 	A.HBID = '"
				+ HBID + "' \n" + " GROUP BY  A.ZTID,B.ZCXX01,B.ZCXX02";
		List infoList = queryForList(o2o, querySQL);
		// 如果购物车不为空，则进行明细数据查询封装
		Map parmap = new HashMap<String, String>();
		UserInfo userInfo = new UserInfo(o2o, scm);
		if (infoList != null && infoList.size() > 0) {
			for (int i = 0; i < infoList.size(); i++) {
				Map infoMap = (Map) infoList.get(i);
				// 根据主体编码、伙伴编码获得单位余额账
				parmap.put("ZTID", infoMap.get("ZTID").toString());
				parmap.put("HBID", infoMap.get("HBID").toString());
				JSONArray jsonArray = JSONArray.fromObject(parmap);
				infoMap.putAll(selectGwcTempSpxxInfo(jsonArray.toString()));
			}
		}
		Map map = new HashMap();
		map.put("GWCLIST", infoList);
		return map;
	}

	/**
	 * 根据主体编码、伙伴编码获得购物车商品明细数据
	 * 
	 * @param String
	 *            XmlData - String HBID 客户编码 - String ZTID 主体编码 - String DQXX01
	 *            客户地区编码
	 * 
	 * @return Map - SPXX01 商品内码 - SPXX02 商品编码 - SPXX04 商品名称 - GWC01 商品数量 -
	 *         GWC02 商品价格 - DQXX01 地区仓库商品 - SPGL14 商品起订量 - SPGL15 商品限定量 -
	 *         SINGLEAMOUNT 单个商品金额小计 - ITEMLIST 套餐明细 - SPXX04 商品名称 - PRICE 商品价格
	 *         - SPSL 组合数量
	 * @note 获得注定主体和客户在购物车里面的商品数据
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectGwcTempSpxxInfo.action")
	public Map selectGwcTempSpxxInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String ztid = cds.getField("ZTID", 0);
		String querySQL = "SELECT   A.SPXX01,"
				+ "					A.HBID,"
				+ "(SELECT SUBSTRING_INDEX(SPTP02,'.',-1) FROM W_SPTP WHERE SPXX01 = B.SPXX01 AND ZCXXO1 = C.ZCXXO1 AND SPTP01= '1')  SPTP02"
				+ "					B.SPXX02,"
				+ "					B.SPFL01_CODE,"
				+ "					A.ZTID,"
				+ "					A.GWC01,"
				+ "					B.SPXX04,"
				+ "					A.GWC02,"
				+ "					C.SPGL14,"
				+ "					C.SPGL02,"
				+ "					IFNULL(C.SPGL15,999) SPGL15,"
				+ "				  floor	(SUM(K.CKSP04 + CKSP05 - K.KCXX02 + K.KCXX01)) CKSL,"
				+ // Integer.valueOf(99.00)会报错
				"					C.SPGL16,"
				+ "					C.SPGL06,"
				+ "					C.SPGL07,"
				+ "					B.SPXX09 LENGTH ,"
				+ "         B.SPXX10 WIDTH,"
				+ "         B.SPXX11 HEIGHT,"
				+ "         C.CKSP12 CKSP12"
				+ "			  FROM  W_GWCTEMP A, W_SPXX B, W_SPGL C ,W_KCXX K,CK"
				+ // ,W_CKSPSX S 不需要
				"			 WHERE  A.SPXX01 = B.SPXX01 "
				+ "			   AND  A.ZTID = C.ZCXX01 "
				+ "			   AND  A.SPXX01 = C.SPXX01"
				+ "			   AND  C.SPXX01 = K.SPXX01"
				+ "			   AND  C.ZCXX01 = K.ZCXX01"
				+ " AND K.CK01 = CK.CK01 AND CK.ck09='0'"
				+ "			   AND  ZTID = '"
				+ cds.getField("ZTID", 0).toString()
				+ "'"
				+ "			   AND  A.HBID = '"
				+ cds.getField("HBID", 0).toString()
				+ "'"
				+ "	      GROUP BY  A.SPXX01,A.HBID,B.SPXX02,A.ZTID,A.GWC01, B.SPXX04,A.GWC02,C.SPGL14, C.SPGL15,"
				+ "                           K.KCXX01,C.SPGL16,C.SPGL06,C.SPGL02, C.SPGL07,B.SPXX09,B.SPXX10,B.SPXX11,C.CKSP12,B.SPFL01_CODE,B.SPXX09,B.SPXX10,B.SPXX11";

		List spxxList = queryForList(o2o, querySQL);
		Map cxmap = new HashMap<String, String>();
		cxmap.put("ZCXX01", cds.getField("ZTID", 0));
		// cxmap.put("DQXX01", cds.getField("DQXX01", 0));
		Map map = new HashMap();
		map.put("SPITEMLIST", spxxList);
		// 查询门店或者地区
		return map;
	}

	/*
	 * 从购物临时表转入购物车表
	 */
	@RequestMapping("/insertW_GWC.action")
	public Map insertW_GWC(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map map = new HashMap();
		String HBID = cds.getField("HBID", 0);
		String tempHbid = cds.getField("tempHbid", 0);
		// 购物临时表中数据转入购物车表
		String sql = "INSERT INTO W_GWC(ZTID, HBID, SPXX01, GWC01, GWC02) "
				+ "SELECT A.ZTID,'" + HBID
				+ "',A.SPXX01,A.GWC01,A.GWC02 FROM W_GWCTEMP A WHERE A.HBID='"
				+ tempHbid + "'";
		Map row = getRow(sql, null, 0);
		execSQL(o2o, sql, row);
		// 删除临时表数据
		String deletesql = "DELETE FROM W_GWCTEMP WHERE HBID='" + tempHbid
				+ "'";
		Map row2 = getRow(deletesql, null, 0);
		execSQL(o2o, deletesql, row);
		map.put("STATE", "success");
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectGwcSpxxInfo_New.action")
	public Map selectGwcSpxxInfo_New(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String HBID = cds.getField("HBID", 0);
		String ddList = cds.getField("ddList", 0);
		Map returnMap = new HashMap();
		JSONArray list = JSONArray.fromObject(ddList);
		List spxxListObj = new ArrayList();
		float spze = 0;
		String ZTMC = "";
		for (int j = 0; j < list.size(); j++) {
			Map ckcmMap = (Map) list.get(j);
			/* 购物车中要结算的商品SPXX01 开始 */
			String splists = ckcmMap.get("SPLIST").toString();
			JSONArray splist = JSONArray.fromObject(splists);
			Map spxxMap = new HashMap();
			List spxxList = new ArrayList();
			for (int m = 0; m < splist.size(); m++) {
				Map spMap = (Map) splist.get(m);
				// if(m==splist.size()-1){
				// spxxstr=spxxstr+spMap.get("SPXX01")+")";
				// }else{
				// spxxstr=spxxstr+spMap.get("SPXX01")+",";
				// }
				float spnum = Float.parseFloat(spMap.get("GWC01").toString());
				float spprice = Float.parseFloat(spMap.get("GWC02").toString());
				spze += spnum * spprice;
				/* 购物车中要结算的商品详情 开始 */
				//modify 2015.12.22.修改库存查询
//				String querySQL = "SELECT  B.SPXX01,B.SPXX02,(select ifnull(sum(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) from W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01  where ck.ck09= '0' and k.spxx01= B.SPXX01 and k.zcxx01=C.ZCXX01 and k.ck01 in (select ck01 from w_dqck where dqxx01='"
//						+ spMap.get("CKZT")
//						+ "')) CKZT,"
				
				String strZcxx = spMap.get("ZCXX01")!=null ?spMap.get("ZCXX01").toString():ckcmMap.get("ZTID")!=null?ckcmMap.get("ZTID").toString():"0";
				String querySQL = "SELECT  B.SPXX01,B.SPXX02,fn_getStockNum('"+ strZcxx + "','"
						+ cds.getField("CKZT", 0)
						+ "','"+spMap.get("SPXX01")+"') CKZT,"
						+ "					Z.ZCXX02,C.ZCXX01, "
						+ "					B.SPXX04,"
						+ spMap.get("GWC01")
						+ " GWC01,  "
						+ spMap.get("GWC02")
						+ " GWC02,  "
						+ "					C.SPGL14,"
						+ "					IFNULL(C.SPGL15,999) SPGL15,"
						+ "		(SELECT S.SPTP02 FROM W_SPTP S WHERE C.ZCXX01=S.ZCXX01 AND S.SPXX01=C.SPXX01 AND S.SPTP01=1) SPTP02"	
						+ "			  FROM  W_SPXX B, W_SPGL C ,W_ZCXX Z, w_gwc gwc "
						+ "			 WHERE  B.SPXX01 = C.SPXX01 "
						//modify 2015.12.22. 修改关联项
						//+ "			   AND  C.ZCXX01 = Z.ZCXX01 "
						+ "			 AND  gwc.spxx01  =C.spxx01 AND gwc.ztid = z.zcxx01"
						//add 2015.10.30. 增加与购物车的关联
						+ "			   AND  gwc.spxx01 = B.SPXX01 and gwc.hbid = '" + HBID +"' "
						+ "			   AND  B.SPXX01 = "
						+ spMap.get("SPXX01") + " AND gwc.ztid='" + ckcmMap.get("ZTID").toString()+ "'"
						+ "	      GROUP BY   B.SPXX01,B.SPXX04,C.SPGL14, C.SPGL15,B.SPXX02,SPTP02";
				System.out.println("querySQL===" + querySQL);
				Map spxxMapone = queryForMap(o2o, querySQL);
				if(spxxMapone !=null){
					spxxList.add(spxxMapone);
					ZTMC = spxxMapone.get("ZCXX02").toString();
				}
			}
			Map ddMap = new HashMap();
			ddMap.put("ZTMC", ZTMC);
			ddMap.put("DDJE", spze);
			ddMap.put("ZTID", ckcmMap.get("ZTID"));
			ddMap.put("HBID", HBID);
			ddMap.put("SPXXLIST", spxxList);
			spxxListObj.add(ddMap);
			// 每单总额清零
			spze = 0;
		}
		returnMap.put("spxxListObj", spxxListObj);
		System.out.println("returnMap=" + returnMap);
		return returnMap;
	}
	
	
	
	/**
	 * 解析拆分ddlist
	 * 
	 * @param ddlist
	 * @return 2016-1-14 下午2:12:24
	 */
	@RequestMapping("/parseDdlist.action")
	public Map parseDdlist(String ddlist, String hbid) throws Exception {
		Map map = new HashMap();
		if (ddlist == null) {
			return map;
		}
		cds = new DataSet(ddlist);
		JSONArray list = JSONArray.fromObject(ddlist);
		JSONArray newlist = new JSONArray();
		JSONArray appendList = new JSONArray();
		// 遍历
		for (int j =0; j < list.size(); j++) {
			Object obj = list.get(j);
			if (obj == null) {
				continue;
			}
			JSONObject jsonObj = JSONObject.fromObject(obj);
			if (!jsonObj.has("SPLIST")) {
				continue;
			}

			// 取值并验证
			Object ztidObj = jsonObj.get("ZTID");
			Object splistObj = jsonObj.get("SPLIST");
			if (splistObj == null || ztidObj == null) {
				continue;
			}

			String ztid = ztidObj.toString();
			// 定义商品列表newsplist,赋值并遍历
			JSONArray newspList = JSONArray.fromObject(splistObj);
			if(newspList.size() == 0){
				//如果商品列表为空，删除
				list.remove(j);
				j = j -1;
				continue;
			}
			for (int i = 0; i < newspList.size(); i++) {
				Object spObj = newspList.get(i);
				JSONObject sp = JSONObject.fromObject(spObj);				
				//获取spxx01,区域信息，zcxx01 
				Object spxxObj = sp.get("SPXX01");
				Object dqxxObj = sp.get("CKZT");
				Object zcxxObj = sp.get("ZCXX01");
				if(zcxxObj ==null || dqxxObj==null || spxxObj==null){
					continue;
				}
				//查询主体ID
				String newZtid = productQueryService.queryOrderZtidByParam(zcxxObj.toString(), dqxxObj.toString(), spxxObj.toString());
				//主体ID变更时，更新购物车中的信息
				if (!newZtid.equals(ztid) && newspList.size() > 0) {
					String ckid = productQueryService.getCkId(ztid,
							dqxxObj.toString());
					// 向新的list里添加，并删除指定的对象
					JSONObject appendObj = new JSONObject();
					appendObj.put("ZTID", newZtid);
					JSONArray ja = new JSONArray();
					ja.add(spObj);
					appendObj.put("SPLIST", ja.toString());
					appendList.add(appendObj);
					// 删除对象，并更新索引
					newspList.remove(i);
					i = i - 1;

					// 更新购物车主体及区域
					modifyGwcInfo(newZtid, ztid, dqxxObj.toString(), hbid,
							spxxObj.toString());

				}
			}
			//如果spList有一条以上，添加合并
			if (newspList.size() > 0) {
				jsonObj.put("SPLIST", newspList);
				jsonObj.put("ZTID", ztid);
				newlist.add(jsonObj);
			}
		}
		// 合并两个jsonarray
		if (appendList.size() > 0) {
			for (Object obj : appendList) {				
				newlist = mergeArray(newlist, obj);
			}
		}
		map.put("result", newlist.toString());
		return map;
	}
	
	
	/**
	 * 更新购物车主体及区域
	 * @param newZtid 新的主体ID
	 * @param ztid 主体ID
	 * @param dqxx 大区信息
	 * @param hbid 伙伴id
	 * @param spxx 商品信息
	 * @throws Exception
	 * 2016-1-15 下午2:10:31
	 */
	private void modifyGwcInfo(String newZtid, String ztid, String dqxx, String hbid, String spxx)  throws Exception{
		//根据新主体ID查询购物车中记录数量
		String delGwcSql = "delete from w_gwc where ztid='"+ newZtid +"' and hbid='"+ hbid +"' and spxx01='"+ spxx +"'";
		Map delRow = getRow( delGwcSql, null, 0);
		execSQL(o2o, delGwcSql, delRow);			

		String updateGwcSql = "UPDATE w_gwc set ztid ='"+newZtid+"' ,dqxx01='"+dqxx+"' where ztid='"+ ztid +"' and hbid='"+ hbid +"' and spxx01='"+ spxx +"'";
		Map row = getRow(updateGwcSql, null, 0);
		execSQL(o2o, updateGwcSql, row);
	}
	
	/**
	 * 合并JSONObject到JSONArray
	 * @param array 
	 * @param obj
	 * @return
	 * 2016-1-15 上午11:23:35
	 */
	public JSONArray mergeArray(JSONArray array, Object obj){
		if(array != null && array.size() > 0){
			int count = 0;
			//遍历比较
			for(Object json : array){
				JSONObject jsonObj = JSONObject.fromObject(json);
				JSONObject jsonObjs = JSONObject.fromObject(obj);
				//如果ztid相同，进行合并
				if(jsonObj.getString("ZTID").equals(jsonObjs.getString("ZTID"))){
					JSONArray arrays = jsonObj.getJSONArray("SPLIST");
					JSONArray appendArray = jsonObjs.getJSONArray("SPLIST");
					for(Object sp : appendArray){
						arrays.add(sp);
					}
					JSONObject newObj = new JSONObject();
					newObj.put("ZTID", jsonObj.getString("ZTID"));
					newObj.put("SPLIST", arrays);
					//删除旧的，追加新的
					array.remove(json);
					array.add(newObj);
					count ++;
					break;
				}
			}
			//如果都不同，追加
			if(count == 0){
				array.add(obj);
			}
		}
		else{
			array.add(obj);
		}
		return array;
	}
	
	/**
	 * 根据ztid,hbid,spxx01,dqxx01,ck01查询购物车中的商品信息 
	 * @param XmlData
	 * @return
	 * @throws Exception
	 * 2016-1-20 上午10:11:04
	 */
	public Map queryGwcSpxxInfoNew(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String CKQX = cds.getField("CKQX", 0);
		String ztid = cds.getField("ZTID", 0);
		String zcxx01 = cds.getField("ZCXX01", 0);
		String DQXX01 = cds.getField("DQXX01", 0);
		String ck01 = cds.getField("CK01", 0);
		String ckqxSql;

		if (CKQX != null && (!"".equals(CKQX)) && CKQX != "") {
			ckqxSql = " AND EXISTS (SELECT 1  " + "  FROM W_CKQX W   "
					+ " WHERE W.ZTID = K.ZCXX01  " + "  AND W.CK01 = K.CK01   "
					+ " and W.GSID ='" + cds.getField("HBID", 0).toString()
					+ "') ";
		} else {
			ckqxSql = "";
		}

		String querySQL = "SELECT   A.SPXX01,"
				+ "					A.HBID,"
				+ "					B.SPXX02,"
				+ "					B.SPFL01_CODE,"
				+ "					A.ZTID,"
				+ "					A.GWC01,"
				+ "					B.SPXX04,"
				+ "					D.spjgb05 GWC02,"
				+ "					A.GWC03,"
				+ "					A.DQXX01,"
				+ "					C.SPGL14,"
				+ "					C.SPGL02,"
				+ "					IFNULL(C.SPGL15,999) SPGL15,"
				+ "					C.zcxx01 ZCXX01,"
				+ "	fn_getStockNum(C.zcxx01,'"+ DQXX01+"',A.spxx01)			  	 CKSL,"
				+ " (SELECT S.SPTP02 FROM W_SPTP S WHERE A.ZTID=S.ZCXX01 AND S.SPXX01=A.SPXX01 AND S.SPTP01=1) SPTP02, "
				+ " IFNULL(fn_getCk(C.ZCXX01, A.dqxx01, A.spxx01),'00000000') CK01,"
				+ "					C.SPGL16,"
				+ "					C.SPGL06,"
				+ "					C.SPGL07,"
				+ "					B.SPXX09 LENGTH ,"
				+ "         B.SPXX10 WIDTH,"
				+ "         B.SPXX11 HEIGHT,"
				+ "         C.CKSP12 CKSP12,(select sptp02 from w_sptp where spxx01 = b.spxx01 and sptp01= '1') SPTP02"
				+ "			  FROM  W_GWC A, W_SPXX B, W_SPGL C, w_spjgb D"
				+ ", W_KCXX K"
				+ "			 WHERE  A.SPXX01 = B.SPXX01 "
				+ "			   AND B.spxx01 = C.spxx01 "
				+ "			   AND C.spxx01 = D.spxx01 "
				+ "			   AND A.ztid = K.zcxx01 "
				+ "			   AND  A.ZTID = '"
				+ cds.getField("ZTID", 0).toString() + "'"
				+ "			   AND  A.HBID = '" + cds.getField("HBID", 0).toString()
				+ "'"
				+ " AND K.ZCXX01 ='" + cds.getField("ZTID", 0).toString() + "'"
				+ " AND IFNULL(fn_getCk(C.ZCXX01, A.dqxx01, A.spxx01),'00000000') ='" +  ck01 + "'"
				+ " AND (C.zcxx01= D.zcxx01 and A.spxx01=D.spxx01)"
				+ " GROUP BY A.SPXX01,A.HBID, A.ZTID ";

		List spxxList = queryForList(o2o, querySQL);
		Map cxmap = new HashMap<String, String>();
		cxmap.put("ZCXX01", cds.getField("ZTID", 0));
		if (spxxList != null && spxxList.size() > 0) {
			for (int i = 0; i < spxxList.size(); i++) {
				Map spmap = (Map) spxxList.get(i);
				// 如果是套餐商品则加入套餐明细
				List<Integer> num = new ArrayList<Integer>();
				// 仓库数量
				num.add(Integer.valueOf(spmap.get("CKSL").toString()));
				// 商品本身自带的限购数量
				num.add(Integer.valueOf(spmap.get("SPGL15").toString()));


				spmap.put("SPGL15", JLTools.getMinNum(num));
			}
		}
		
		Map map = new HashMap();
		map.put("SPITEMLIST", spxxList);
		return map;
	}
	
	/**
	 * 根据hbid,dqxx01修改购物车中商品的区域信息
	 * @param hbid
	 * @param dqxx01
	 * @throws Exception
	 * 2016-1-20 上午10:25:03
	 */
	public void modifyGwcDqxx(String hbid, String dqxx01) throws Exception{
		if(hbid != null && dqxx01 != null){
			String updateSql = "UPDATE w_gwc SET dqxx01='"+ dqxx01 +"' WHERE hbid='"+hbid+"'";
			Map updateRow = getRow(updateSql, null, 0);
			execSQL(o2o, updateSql, updateRow);
		}
	}
	
	/**
	 * 根据hbid进行购物车商品数量查询
	 * @param XmlData
	 * @return
	 * @throws Exception
	 * 2016-1-20 上午11:21:30
	 */
	@RequestMapping("/selectGwcCountByHbid.action")
	public Map queryGwcCountByHbid(String hbid) throws Exception {
		int count = 0;
		if(hbid != null){
			String queryCountSql = "SELECT SUM(gwc01) FROM w_gwc WHERE hbid='"+ hbid +"'";
			count = queryForInt(o2o, queryCountSql);
		}
		Map map = new HashMap();
		map.put("GWCLIST", count);
		return map;		
	}
}