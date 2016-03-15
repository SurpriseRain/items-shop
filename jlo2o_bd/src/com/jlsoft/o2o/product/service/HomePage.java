/**********************************************************************
 * @file	HomeDisplay.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	赵明亮/20140309
 * @author	Code:	赵明亮/20140310
 * @author	Modify:	曾海峰/20140310
 * @author	Modify:	丁毅/20140310
 * @copy	Tag:	金力软件
 **********************************************************************/


package com.jlsoft.o2o.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.ClientDataSet;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;

/**
 * 
 * @breif 首页相关后台操作
 *
 */
@Controller
@RequestMapping("/HomePage")
public class HomePage extends JLBill{
	private int ROADMAP = JLTools.strToInt(JlAppResources.getProperty("ROADMAP"));
	/**
	 * 查询首页商品信息
	 * @param	String XmlData
	 * 	- int	spbj	商品标记
	 * 	- int	dtbj	大厅标记
	 * 	- int	cxsl	查询数量
	 * 
	 * @return	Map
	 * 	- spxx01	商品id 
	 * 	- spxx02	商品内码		
	 * 	- spxx04	商品名称
	 * 
	 * @note 未登录时,各种商品展示时调用
	 */
	@RequestMapping("/selectSysp.action")
	public Map selectSysp(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData);
		String SPBJ="";
		String DTBJ="";
		if(cds.getField("SPBJ", 0).equals("1")){
			SPBJ="AND G.SPGL09 = 1\n";
		} else if(cds.getField("SPBJ", 0).equals("2")){
			SPBJ="AND G.SPGL11 = 1\n";
		} else if(cds.getField("SPBJ", 0).equals("3")){
			SPBJ="AND G.SPGL10 = 1\n";
		} else if(cds.getField("SPBJ", 0).equals("4")){
			SPBJ="AND G.SPGL08 = 1\n";
		}
		if(cds.getField("DTBJ", 0).equals("0")){
			DTBJ="AND G.SPGL02 = 0\n AND G.SPGL19 = 1\n";
		}else if(cds.getField("DTBJ", 0).equals("2")){
			DTBJ="AND G.SPGL02 = 2\n";
		} 
		String ZTID="";
		if(ROADMAP==4){
			ZTID="AND G.ZCXX01='"+cds.getField("gsid", 0)+"'";
		}
		String querySQL =
		"SELECT G.SPXX01,\n" +
		"           P.SPXX02,\n" + 
		"           P.SPXX04,\n" + 
		"			SUM(K.CKSP04 + K.CKSP05 - K.KCXX02) CKSP04,\n" +
		"           M.SPJGB01,\n" + 
		"           M.SPJGB02,\n" + 
		"           G.SPGL04,\n" + 
		"           G.ZCXX01,\n" + 
		"           G.SPGL02,\n" + 
		"           G.SPGL13,\n" + 
		"           G.SPGL09,\n" + 
		"           G.SPGL11,\n" + 
		"           G.SPGL08,\n" + 
		"           G.CKSP12 SPSX,\n "+
		"           IFNULL(M.SPJGB05, 0) SPJGB05,\n" + 
		"           0 HDLX,\n" + 
		"           0 CXJG\n" + 
		"      FROM W_SPGL G, W_SPJGB M, W_SPXX P,W_KCXX K,CK\n" + 
		"     WHERE G.SPXX01 = M.SPXX01\n" + 
		"       AND G.ZCXX01 = M.ZCXX01\n" + 
		"       AND G.SPXX01 = P.SPXX01\n" + 
		"		 AND G.SPXX01=K.SPXX01\n" +
		" 		AND G.ZCXX01=K.ZCXX01\n "+
		" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
		"       AND G.SPGL12 IN ('1', '3')\n" + 
				SPBJ+DTBJ+ZTID+
		//"       AND <9\n" + 
		"    GROUP BY \n" +
		"	G.SPXX01, \n"+
		"      P.SPXX02,\n" +
		"       P.SPXX04,\n" +
		"     M.SPJGB01,\n" +
		"      M.SPJGB02,\n" +
		"      G.SPGL04,\n" +
		"      G.ZCXX01,\n" +
		"     G.SPGL02,\n" +
		"     G.SPGL13,\n" +
		"     G.SPGL09,\n" +
		"     G.SPGL11,\n" +
		"     G.SPGL08,\n" +
		"     G.CKSP12,\n" +
		"     M.SPJGB05\n" +
		"     ORDER BY G.SPGL13 DESC";
		


		List result = cds.queryForList(o2o, querySQL);
		
		System.out.println("-----"+result);
		Map map = new HashMap();
		if(ROADMAP==4){
			String hbid=cds.getField("hbid", 0);
			List list = getAllSpxx(result,hbid,cds);
			map.put("list", list);
		}
		map.put("spList", result);
		return map;
	}
	public List getAllSpxx(List<Map<String, Object>> list,String hbid,ClientDataSet cds){
		String sql1 = "SELECT ZCXX01, SPXX01, KHSPJGI01 FROM W_KHGX A, W_KHSPJGB B WHERE A.ZTID = B.ZCXX01 AND A.KHLX01 = B.KHLX01 AND A.HBID = '"+hbid+"'";
		List<Map<String, Object>> hbidlist = cds.queryForList(o2o, sql1);
		for (Map<String, Object> map : list) {
			//List<Map<String, Object>> lcsp = (List) map.get("lcsp");
			for (Map<String, Object> map1 : hbidlist) {
				String ZCXX01 = map1.get("ZCXX01").toString();
				String SPXX01 = map1.get("SPXX01").toString();
				String KHSPJGI01 = map1.get("KHSPJGI01").toString();
				for (Map<String, Object> map2 : list) {
					String zcxx01 = map2.get("ZCXX01").toString();
					String spxx01 = map2.get("SPXX01").toString();
					if (ZCXX01.equals(zcxx01) && SPXX01.equals(spxx01)) {
						map2.put("KHSPJGI01", KHSPJGI01);
					}
				}
			}
		}
		return list;
	}
	/**
	 * 查询首页商品信息
	 * @param	String XmlData
	 * 	- int	spbj	商品标记
	 * 	- int	dtbj	大厅标记
	 * 	- int	cxsl	查询数量
	 * 
	 * @return	Map
	 * 	- spxx01	商品id 
	 * 	- spxx02	商品内码		
	 * 	- spxx04	商品名称
	 * 
	 * @note 未登录时,各种商品展示时调用
	 */
	@RequestMapping("/selectKhspjg.action")
	public Map selectKhspjg(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData);
		Map map=new HashMap();
		JSONArray jsonArray = JSONArray.fromObject(XmlData);  
		  
        List<Map<String,Object>> mapListJson = (List)jsonArray; 
        List list = getAllSpxx(mapListJson,cds.getField("hbid", 0),cds);
        map.put("map", list);
		return map;
	}
	/**
	 * 查询首页商品信息(明珠专用)
	 * @param	String XmlData
	 * 	- int	spbj	商品标记
	 * 	- int	dtbj	大厅标记
	 * 	- int	cxsl	查询数量
	 * 
	 * @return	Map
	 * 	- spxx01	商品id 
	 * 	- spxx02	商品内码		
	 * 	- spxx04	商品名称
	 * 
	 * @note 登录时,各种商品展示时调用（带分类品牌，区域权限）
	 */
	@RequestMapping("/selectTjspLogin.action")
	public Map selectTjspLogin(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData);
		String HBID=cds.getField("HBID", 0);
		String DQBM=cds.getField("DQBM", 0);
		String DTBJ=cds.getField("DTBJ", 0);//大厅标记2表示大厅展示
		String SPBJ=cds.getField("SPTJ", 0);//本期促销标记
		String spsxqx=cds.getField("spsxqx", 0);
		String sxSql="";
		if("1".equals(spsxqx)){
			sxSql=" AND EXISTS (SELECT 0 FROM W_SPSXQX Z WHERE Z.HBID='"+HBID+"' AND Z.ZTID=G.ZCXX01 AND G.CKSP12=Z.CKSP12) ";
		}
		String querySQL = "SELECT G.SPXX01,P.SPXX02,P.SPXX04,J.SPJGB01, "
				+ "J.SPJGB02,G.SPGL04,IFNULL(J.SPJGB05, 0) SPJGB05,"
				+ "         G.ZCXX01, G.SPGL13, G.SPGL03, G.SPGL02,"
				+ "         G.SPGL08,A.ZCXX02,G.SPGL06,G.SPGL14, "
				+ "         G.SPGL15,P.SPXX08,G.SPGL18,IFNULL(C.CXJG, 0) CXJG,"
				+ "            IFNULL((SELECT D.DWJG02"
				+ "                 FROM DWJGB D"
				+ "                WHERE D.GSXX01 = G.ZCXX01"
				+ "                 AND D.KH01 = '"+HBID+"'"
				+ "                AND D.SPXX01 = G.SPXX01),"
				+ "              0) YHJG,C.HDLX CXLX,"
				+ "            SUM(K.CKSP04 + K.CKSP05 - K.KCXX02) CKSP04,"
				+ "         G.CKSP12 SPSX "
				+ "        FROM W_SPGL G,W_SPJGB J, W_KCXX K,CK, W_SPXX P,W_ZCXX A, W_SPQY Q,"
				+ "             (SELECT M.GSXX01,  M.SPXX01, M.CXJG, M.HDLX "
				+ "               FROM W_CXZXB M "
				+ "              WHERE M.DQXX01 LIKE SUBSTR('"+DQBM+"', 1, 4) || '%'"
				+ "                AND SYSDATE >= M.BEGAINDATE"
				+ "                AND SYSDATE <= M.ENDDATE) C "
				+ "        WHERE G.SPXX01 = J.SPXX01 	"
				+ "			AND G.SPGL08='"+SPBJ+"' AND G.SPGL02='"+DTBJ+"'    "
				+ "         AND G.ZCXX01 = J.ZCXX01 "
				+ "         AND G.ZCXX01 = A.ZCXX01	"
				+ "         AND G.SPXX01 = K.SPXX01	"
				+ "         AND G.ZCXX01 = K.ZCXX01	"
				+" AND K.CK01 = CK.CK01 AND CK.ck09='0'" 
				+ "         AND G.ZCXX01 = C.GSXX01(+)	"
				+ "          AND G.SPXX01 = C.SPXX01(+)	"
				+ "         AND G.ZCXX01 = Q.JXS	"
				+ "          AND TO_CHAR(G.SPXX01) = Q.SPID	"
				+ "          AND Q.QYDM LIKE SUBSTR('"+DQBM+"', 1, 6) || '%'"
				+ "          AND EXISTS (SELECT 1    "
				+ "              FROM W_FLPPQX R, W_GSGX H   "
				+ "            WHERE R.ZTID = H.ZTID  "
				+ "              AND R.HBID = H.HBID  "
				+ "             AND H.HBID = '"+HBID+"'  "
				+ "             AND R.PPB01 = P.PPB01  "
				+ "             AND R.SPFL01 = SUBSTR(P.SPFL01, 1, 4)  "
				+ "             AND R.ZTID = G.ZCXX01  "
				+ "             AND R.FLPP02 = 8)  "
				+
				/*
				 * AND EXISTS (SELECT 1 FROM W_CKQX A WHERE A.ZTID = K.ZCXX01
				 * AND A.CK01 = K.CK01 and A.GSID = '905013')
				 */
				sxSql+
				"          AND NOT EXISTS (SELECT 1  "
				+ "                FROM W_SPQXHMD D  "
				+ "              WHERE D.ZTID = G.ZCXX01  "
				+ "                 AND D.HBID = '"+HBID+"'  "
				+ "                AND D.SPXX01 = G.SPXX01)  "
				+ "          AND G.SPXX01 = P.SPXX01  "
				+ "          AND G.SPGL12 IN ('1', '3')  "
				+ "         AND G.SPGL04 >= cast('0' as decimal(10,4))  "
				+ "         AND G.SPGL04 <= cast('9999999' as decimal(10,4))  "
				+ "        GROUP BY G.SPXX01,P.SPXX02,P.SPXX04,J.SPJGB01,J.SPJGB02,  "
				+ "        G.SPGL04,J.SPJGB05, G.ZCXX01, G.SPGL03,G.SPGL02,  "
				+ "       G.SPGL08,G.SPGL13,G.SPGL18,A.ZCXX02, G.SPGL06,  "
				+ "              G.SPGL14,G.SPGL15,G.CKSP12,P.SPXX08, A.ZCXX34,C.CXJG,C.HDLX  "
				+ "    ORDER BY A.ZCXX34 DESC, G.SPGL13 ASC  ";
		List result = cds.queryForList(o2o, querySQL);
		
		System.out.println("-----"+result);
		Map map = new HashMap();
		map.put("spList", result);
		return map;
	}
	
	/**
	 * 查询首页商品信息
	 * @param	String XmlData
	 * 	- int	spbj	商品标记
	 * 	- int	dtbj	大厅标记
	 * 	- int	cxsl	查询数量
	 * 
	 * @return	Map
	 * 	- spxx01	商品id 
	 * 	- spxx02	商品内码		
	 * 	- spxx04	商品名称
	 * 
	 * @note 登录时,各种商品展示时调用
	 */
	@RequestMapping("/selectSyspLogin.action")
	public Map selectSyspLogin(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData);
		String querySQL =
		"SELECT G.SPXX01,\n" +
		"           P.SPXX02,\n" + 
		"           P.SPXX04,\n" + 
		"			SUM(K.CKSP04 + K.CKSP05 - K.KCXX02) CKSP04,\n" +
		"           M.SPJGB01,\n" + 
		"           M.SPJGB02,\n" + 
		"           G.SPGL04,\n" + 
		"           G.ZCXX01,\n" + 
		"           G.SPGL02,\n" + 
		"           G.SPGL13,\n" + 
		"           G.SPGL09,\n" + 
		"           G.SPGL11,\n" + 
		"           G.SPGL08,\n" + 
		"           IFNULL(M.SPJGB05, 0) SPJGB05,\n" + 
		"           0 HDLX,\n" + 
		"           0 CXJG\n" + 
		"      FROM W_SPGL G, W_SPJGB M, W_SPXX P,W_KCXX K,CK\n" + 
		"     WHERE G.SPXX01 = M.SPXX01\n" + 
		"       AND G.ZCXX01 = M.ZCXX01\n" + 
		"       AND G.SPXX01 = P.SPXX01\n" + 
		"		 AND G.SPXX01=K.SPXX01\n" +
		" 		AND G.ZCXX01=K.ZCXX01\n "+
		" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
		"       AND G.SPGL12 IN ('1', '3')\n" + 
		"       AND G.SPGL08 = 1\n" + 
		"       AND G.SPGL02 = 2\n" + 
		//"       AND <8\n" + 
		"   	 GROUP BY \n" +
		"		G.SPXX01, \n"+
		"      P.SPXX02,\n" +
		"       P.SPXX04,\n" +
		"     M.SPJGB01,\n" +
		"      M.SPJGB02,\n" +
		"      G.SPGL04,\n" +
		"      G.ZCXX01,\n" +
		"     G.SPGL02,\n" +
		"     G.SPGL13,\n" +
		"     G.SPGL09,\n" +
		"     G.SPGL11,\n" +
		"     G.SPGL08,\n" +
		"     M.SPJGB05\n" +
		"      ORDER BY G.SPGL13 DESC";

	
		List result = cds.queryForList(o2o, querySQL);
		
		Map map = new HashMap();
		map.put("spList", result);
		return map;
	}
	
	/**	
	 * 查询各状态订单数量
	 * @param	String XmlData
	 * 	- int		W_DJZX02	订单状态
	 * 	- String	HBID		伙伴ID	
	 * 
	 * @return	Map
	 * 	- ORDERSUM	订单数量 
	 * 
	 * @note 登陆后显示订单数量
	 */
	@RequestMapping("/selectDdztCount.action") 
	public Map selectDdztCount(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String querySQL =
		"SELECT G.SPXX01,\n" +
		"           P.SPXX02,\n" + 
		"           P.SPXX04,\n" + 
		"           M.SPJGB01,\n" + 
		"           M.SPJGB02,\n" + 
		"           G.SPGL04,\n" + 
		"           G.ZCXX01,\n" + 
		"           G.SPGL02,\n" + 
		"           G.SPGL13,\n" + 
		"           G.SPGL09,\n" + 
		"           G.SPGL11,\n" + 
		"           G.SPGL08,\n" + 
		"           IFNULL(M.SPJGB05, 0) SPJGB05,\n" + 
		"           0 HDLX,\n" + 
		"           0 CXJG\n" + 
		"      FROM W_SPGL G, W_SPJGB M, W_SPXX P\n" + 
		"     WHERE G.SPXX01 = M.SPXX01\n" + 
		"       AND G.ZCXX01 = M.ZCXX01\n" + 
		"       AND G.SPXX01 = P.SPXX01\n" + 
		"       AND G.SPGL12 IN ('1', '3')\n" + 
		"       AND G.SPGL08 = 1\n" + 
		"       AND G.SPGL02 = 2\n" + 
		"       LIMIT 8\n" + 
		"      ORDER BY G.SPGL13 DESC";

	
		List result = cds.queryForList(o2o, querySQL);
		
		Map map = new HashMap();
		map.put("spList", result);
		return map;
	}
	/**
	 * 首页查询案例
	 */
	@RequestMapping("/selectInforMation.action")
	public List<Map<String, Object>> selectInforMation(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData); 
		int len=Integer.parseInt(cds.getField("num", 0));
		String titleSql="SELECT LXBH, LXMC FROM W_ALLX WHERE 1=1 ORDER BY LXBH DESC limit "+len;
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			String DHBJ=cds.getField("DHBJ", 0);
			titleSql="SELECT LXBH, LXMC FROM W_ALLX WHERE DHBJ='"+DHBJ+"' ORDER BY LXBH limit "+len;
		}
		List<Map<String, Object>> titleList=cds.queryForList(o2o, titleSql);
		String infoSql=" SELECT W.ALBH, W.ALLX, W.ALTITLE,W.FILENAME,W.FILEPATH, date_format(W.FBSJ,'%Y-%m-%d') FBSJ\n" +  
					   "  FROM W_ALFX W\n" + 
			           " WHERE W.TJBJ=1 AND W.ALLX = ";
		for (Map<String, Object> map:titleList) {
			List<Map<String, Object>> infoList= cds.queryForList(o2o, (infoSql+map.get("LXBH")+" ORDER BY W.FBSJ DESC"));
			map.put("alInfo", infoList);
		}
		return titleList;
	}
	
	/**
	 * 查询政策法规
	 */
	@RequestMapping("/selectZCFG.action")
	public List<Map<String, Object>> selectZCFG(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String LXBH=cds.getField("LXBH", 0);
		String infoSql=" SELECT W.ALBH, W.ALLX, W.ALTITLE,W.FILENAME,W.FILEPATH, W.GJNR\n" +
		"  FROM W_ALFX W\n" + 
		" WHERE W.TJBJ=1 AND W.ALLX = '"+LXBH+"'";
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			infoSql=" SELECT W.ALBH, W.ALLX, W.ALTITLE,W.FILENAME,W.FILEPATH,date_format(W.FBSJ,'%Y-%m-%d') FBSJ, W.XWGG, A.LXMC\n" +
					"  FROM W_ALFX W, W_ALLX A\n" + 
					" WHERE W.TJBJ=1 AND W.ALLX=A.LXBH AND W.ALLX = '"+LXBH+"'ORDER BY W.FBSJ DESC LIMIT 12" ;
		} 
		List infoList= cds.queryForList(o2o, infoSql);
		return infoList;
	}
	
	/**
	 * 首页直通车
	 */
	@RequestMapping("/selectW_DDPX.action")
	public List<Map<String, Object>> selectW_DDPX(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String sql="   SELECT B.ZCXX34,A.ZCXX01,B.ZCXX02,A.DPXX01, A.DPXX02, A.DPXX03 FROM W_DPXX A,W_ZCXX B "+
					"	WHERE A.ZCXX01=B.ZCXX01 AND A.DPXX04=1 ORDER BY ZCXX34";
		List List=cds.queryForList(o2o, sql);
		return List;
	}
	
	/**
	 * 展示登陆后资料
	 */
	@RequestMapping("/selectW_DPXX.action")
	public List<Map<String, Object>> selectW_DPXX(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String sql="   SELECT B.ZCXX34,A.ZCXX01,B.ZCXX02,A.DPXX01, A.DPXX02, A.DPXX03 FROM W_DPXX A,W_ZCXX B "+
					"	WHERE A.ZCXX01=B.ZCXX01 AND A.ZCXX01='"+cds.getField("ZCXX01", 0)+"' ORDER BY ZCXX34";
		List List=cds.queryForList(o2o, sql);
		return List;
	}
	/**
	 * 查询以商会友
	 */
	@RequestMapping("/selectYshy.action")
	public Map selectYshy(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String querySQL =
				"SELECT *\n" +
						"  FROM (SELECT X.ALBH ALBH,\n" + 
						"               X.ALLX ALLX,\n" + 
						"               X.CKQX CKQX,\n" + 
						"               X.CONTEXT CONTEXT,\n" + 
						"               X.FBR ZCXX01,\n" + 
						"               (SELECT LXMC FROM W_ALLX WHERE LXBH = X.ALLX) LXNAME,\n" + 
						"               X.ALTITLE ALTITLE,\n" + 
						"               date_format(X.FBSJ,'%Y-%m-%d') FBSJ,\n" + 
						"               X.FILENAME FILENAME,\n" + 
						"               X.FILEPATH FILEPATH,\n" + 
						"               X.GJNR GJNR,\n" + 
						"               X.TJBJ TJBJ,\n" + 
						"               (SELECT ZCXX02 FROM W_ZCXX WHERE ZCXX01 = X.FBR) ZCXX02\n" + 
						"          FROM W_ALFX X\n" + 
						"         WHERE X.CKQX = 1\n" + 
						"           AND X.TJBJ = 1\n" + 
						"         ORDER BY X.FBSJ DESC) N\n" + 
						" limit  5";
		List result = cds.queryForList(o2o, querySQL);
		Map map = new HashMap();
		map.put("yshyList", result);
		return map;
	}
	
	
	/**
	 * 查询营销案例
	 */
	@RequestMapping("/selectYxal.action")
	public Map selectYxal(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String querySQL =
						"  SELECT M.* \n" + 
						"          FROM (SELECT A.ALBH ALBH,\n" + 
						"                       A.ALLX ALLX,\n" + 
						"                       A.CKQX CKQX,\n" + 
						"                       A.CONTEXT CONTEXT,\n" + 
						"                       A.FBR ZCXX01,\n" + 
						"                       (SELECT LXMC FROM W_ALLX WHERE LXBH = A.ALLX) LXNAME,\n" + 
						"                       A.ALTITLE ALTITLE,\n" + 
						"                       date_format(A.FBSJ,'%Y-%m-%d') FBSJ,\n" + 
						"                       A.FILENAME FILENAME,\n" + 
						"                       A.FILEPATH FILEPATH,\n" + 
						"                       A.GJNR GJNR,\n" + 
						"                       A.TJBJ TJBJ,\n" + 
						"                       (SELECT ZCXX02 FROM W_ZCXX WHERE ZCXX01 = A.FBR) ZCXX02\n" + 
						"                  FROM W_ALFX A\n" + 
						"                 WHERE A.CKQX = 1\n" + 
						"                 ORDER BY A.FBSJ DESC) M\n" + 
						"         limit 8\n" + 
						" ";
		System.out.println(querySQL);
			
		List result = cds.queryForList(o2o, querySQL);
		
		//System.out.println(((Map)result.get(0)).get("FBSJ"));
		
		Map map = new HashMap();
		map.put("yxalList", result);
		return map;
	}
	
	/**
	 * 资讯推荐
	 * new_open.html  中有用到
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectYshyMZ.action")
	public Map selectYshyMZ(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String querySQL =
				"SELECT *\n" +
						"  FROM (SELECT X.ALBH ALBH,\n" + 
						"               X.ALLX ALLX,\n" + 
						"               X.CKQX CKQX,\n" + 
						"               X.CONTEXT CONTEXT,\n" + 
						"               X.FBR ZCXX01,\n" + 
						"               (SELECT LXMC FROM W_ALLX WHERE LXBH = X.ALLX) LXNAME,\n" + 
						"               X.ALTITLE ALTITLE,\n" + 
						"               date_format(X.FBSJ,'%Y-%m-%d') FBSJ,\n" + 
						"               X.FILENAME FILENAME,\n" + 
						"               X.FILEPATH FILEPATH,\n" + 
						"               X.GJNR GJNR,\n" + 
						"               X.TJBJ TJBJ,\n" + 
						"               (SELECT ZCXX02 FROM W_ZCXX WHERE ZCXX01 = X.FBR) ZCXX02\n" + 
						"          FROM W_ALFX X\n" + 
						"         WHERE /*X.CKQX = 1\n" + 
						"           AND*/ X.TJBJ = 1\n" + 
						"         ORDER BY X.FBSJ DESC)\n" + 
						" LIMIT 5";
		List result = cds.queryForList(o2o, querySQL);
		Map map = new HashMap();
		map.put("yshyList", result);
		return map;
	}
	
	
	/**
	 * 明珠新闻
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectYxalMZ.action")
	public Map selectYxalMZ(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData); 
		String querySQL =
				"SELECT * \n" +
						"	FROM (SELECT A.JLBH JLBH, A.TITLE ALTITLE, A.CONTEXT CONTEXT, FBSJ \n" +
						"			FROM W_XX A	\n"+
						"			WHERE CODE <> 1 \n"+
						"			ORDER BY A.FBSJ DESC  )\n"+
						"	LIMIT  5 ";

		List result = cds.queryForList(o2o, querySQL);
		System.out.println(((Map)result.get(0)).get("FBSJ"));
		
		Map map = new HashMap();
		map.put("yxalList", result);
		return map;
	}
	
	
	/**
	 * 查询会员商品信息
	 * @param	String XmlData
	 * 	- int	
	 * 	- int	
	 * 	
	 * 
	 * @return	Map
	 * 	- spxx01	商品id 
	 * 	- spxx02	商品内码		
	 * 	- spxx04	商品名称
	 * 
	 * @note 未登录时,各种商品展示时调用
	 */
	@RequestMapping("/selectHYSPnotlogin.action")
	public Map selectHYSPnotlogin(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ClientDataSet cds = new ClientDataSet(XmlData);
		String querySQL ="SELECT " +
				"   G.SPXX01 ," +
				"   P.SPXX02 ," +
				"   P.SPXX04 ," +
				"   J.SPJGB01 , " +
				"   J.SPJGB02 ," +
				"   G.SPGL04 ," +
				"   IFNULL(J.SPJGB05, 0) SPJGB05," +
				"   G.ZCXX01 ," +
				"   G.SPGL13 ," +
				"   G.SPGL03 ," +
				"   G.SPGL02 ," +
				"   G.SPGL08 ," +
				"   A.ZCXX02 ," +
				"   G.SPGL06 ," +
				"   G.SPGL14 ," +
				"   G.SPGL15 ," +
				"   P.SPXX08 ," +
				"   G.SPGL18 ," +
				"   0 CXJG," +
				"   0 YHJG," +
				"   0 CXLX," +
				"   SUM(K.CKSP04 + K.CKSP05 - K.KCXX02) SPSL" +
				"   FROM    W_SPGL G, W_SPJGB J, W_KCXX K,CK, W_SPXX P,W_ZCXX A " +
				"   WHERE  G.SPXX01 = J.SPXX01" +
				"   AND G.ZCXX01 = J.ZCXX01" +
				"   AND G.ZCXX01 = A.ZCXX01" +
				"   AND G.SPXX01 = K.SPXX01" +
				"   AND G.ZCXX01 = K.ZCXX01" +
				"   AND G.SPXX01 = P.SPXX01" +
				" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
				"   AND G.SPGL02 = 2" +
				"   AND G.SPGL12 IN ('1', '3')" +
				"   AND IFNULL(J.SPJGB05, 0) >= cast('0' as decimal(10,4))" +
				"   AND IFNULL(J.SPJGB05, 0) <= cast('9999999' as decimal(10,4))" +
				"   GROUP BY   " +
				"   G.SPXX01,P.SPXX02,P.SPXX04,J.SPJGB01,J.SPJGB02,G.SPGL04," +
				"   J.SPJGB05,G.ZCXX01,G.SPGL03,G.SPGL02,G.SPGL08 ," +
				"   G.SPGL13,G.SPGL18,A.ZCXX02, G.SPGL06, G.SPGL14, " +
				"   G.SPGL15,P.SPXX08,A.ZCXX34   " +
				"   ORDER BY A.ZCXX34 DESC,G.SPGL13 DESC ";
		
		List result = cds.queryForList(o2o, querySQL);
		System.out.println(result);
		Map map = new HashMap();
		map.put("spList", result);
		return map;
	}
	
	/**
	 * 查询会员商品信息
	 * @param	String XmlData
	 * 	- int	
	 * 	- int	
	 * 	
	 * 
	 * @return	Map
	 * 	- spxx01	商品id 
	 * 	- spxx02	商品内码		
	 * 	- spxx04	商品名称
	 * 
	 * @note 登录后,各种商品展示时调用
	 */
	@RequestMapping("/selectHYSPlogin.action")
	public Map selectHYSPlogin(String XmlData) throws Exception {
		ClientDataSet cds = new ClientDataSet(XmlData);
		System.out.println("XmlData=="+XmlData);
		System.out.println("XmlData=="+cds.getField("prista", 0));
		System.out.println("XmlData=="+cds.getField("HBID", 0));
		String querySQL ="SELECT " +
				"   G.SPXX01 ," +
				"   P.SPXX02 ," +
				"   P.SPXX04 ," +
				"   J.SPJGB01 ," +
				"   J.SPJGB02 ," +
				"   G.SPGL04 ," +
				"   IFNULL(J.SPJGB05, 0) SPJGB05," +
				"   G.ZCXX01 ," +
				"   G.SPGL13 ," +
				"   G.SPGL03 ," +
				"   G.SPGL02 ," +
				"   G.SPGL08 ," +
				"   A.ZCXX02 ," +
				"   G.SPGL06 ," +
				"   G.SPGL14 ," +
				"   G.SPGL15 ," +
				"   P.SPXX08 ," +
				"   G.SPGL18 ," +
				"   CASE C.HDLX   WHEN 1 THEN    IFNULL(C.CXJG,9999999)  ELSE    9999999   END  CXJG ," +
				"   C.HDLX CXLX," +
				"   SUM(K.CKSP04 + K.CKSP05 - K.KCXX02) SPSL" +
				" FROM    W_SPGL G, W_SPJGB J, W_KCXX K,CK, W_SPXX P,W_ZCXX A, W_SPQY Q," +
				"    (SELECT M.GSXX01,M.SPXX01,M.CXJG,M.HDLX FROM W_CXZXB M  WHERE M.DQXX01 LIKE SUBSTR('"+cds.getField("DQXX01",0)+"', 1, 4) || '%' " +
				"          AND SYSDATE >= M.BEGAINDATE AND SYSDATE <= M.ENDDATE ) C  " +
				"    WHERE G.SPXX01 = J.SPXX01" +
				"      AND G.ZCXX01 = J.ZCXX01" +
				"      AND G.ZCXX01 = A.ZCXX01" +
				"      AND G.SPXX01 = K.SPXX01" +
				"      AND G.ZCXX01 = K.ZCXX01" +
				" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
				"      AND G.ZCXX01 = C.GSXX01(+)" +
				"      AND G.SPXX01 = C.SPXX01(+)" +
				"      AND G.ZCXX01 = Q.JXS " +
				"      AND G.SPXX01 = Q.SPID" +
				"      AND Q.QYDM LIKE SUBSTR('"+cds.getField("QYDM",0)+"', 1, 4) || '%'" +
				"      AND NOT EXISTS (SELECT 1 FROM  W_SPQXHMD D " +
				"    WHERE D.ZTID=G.ZCXX01 " +
				"      AND D.HBID='"+cds.getField("HBID",0)+"'   " +
				"      AND D.SPXX01=G.SPXX01 ) " +
				"      AND G.SPXX01 = P.SPXX01" +
				"      AND G.SPGL02 = 2 " +
				"      AND G.SPGL12 IN ('1', '3')" +
				"      AND G.SPGL04 >= cast('" +cds.getField("prista",0)+"' as decimal(10,4))" +
				"      AND G.SPGL04 <= cast('" +cds.getField("priend",0)+"' as decimal(10,4))" +
				"  GROUP BY   G.SPXX01,P.SPXX02,P.SPXX04,J.SPJGB01,J.SPJGB02,G.SPGL04," +
				"             J.SPJGB05,G.ZCXX01,G.SPGL03,G.SPGL02,G.SPGL08 ,G.SPGL13," +
				"             G.SPGL18,A.ZCXX02, G.SPGL06, G.SPGL14,G.SPGL15,P.SPXX08," +
				"             A.ZCXX34, C.CXJG,C.HDLX     " +
				"      ORDER BY A.ZCXX34 DESC,G.SPGL13 DESC";
		
		List result = cds.queryForList(o2o, querySQL);
		System.out.println(result);
		Map map = new HashMap();
		map.put("spList", result);
		return map;
	}
	//游客图片展示
	@RequestMapping("/selectPictureMainPage.action")
	public List<Map<String, Object>> selectPictureMainPage(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData);
		String sql = null;
		System.out.println(XmlData);
		//System.out.println(cds.getField("gsid", 0));
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			sql=
				"SELECT B.FILE01 ID,\n" +
				"       B.FILE02 FILENAME,\n" + 
				"       B.FILE03 FILEPATH,\n" + 
				"       B.FILE04 FILETYPE,\n" + 
				"       B.FILE05 URL\n" + 
				"  FROM W_FILE B\n" + 
				" WHERE 1=1 and B.FILE01='"+(cds.getField("gsid", 0)==null?"jladmin":cds.getField("gsid", 0))+"' order by FILE06";
		} else {		
			sql=
				"SELECT B.FILE01 ID,\n" +
				"       B.FILE02 FILENAME,\n" + 
				"       B.FILE03 FILEPATH,\n" + 
				"       B.FILE04 FILETYPE,\n" + 
				"       B.FILE05 URL\n" + 
				"  FROM W_FILE B\n" + 
				" WHERE B.FILE04 >=90 and B.FILE04 <=97 order by FILE04";
		}
		List picList=cds.queryForList(o2o, sql);
		return picList;
	}
	//图文查询滚动大图下面的滚动小图
		@RequestMapping("/selectXTPictureMainPage.action")
		public List<Map<String, Object>> selectXTPictureMainPage(String XmlData){
			ClientDataSet cds = new ClientDataSet(XmlData);
			String sql=
				"SELECT B.FILE01 ID,\n" +
				"       B.FILE02 FILENAME,\n" + 
				"       B.FILE03 FILEPATH,\n" + 
				"       B.FILE04 FILETYPE,\n" + 
				"       B.FILE05 URL\n" + 
				"  FROM W_FILE B\n" + 
				" WHERE B.FILE04 >=120 and B.FILE04 <=129 order by FILE04";
			List picList=cds.queryForList(o2o, sql);
			return picList;
		}
	//会员图片展示(明珠用)
	@RequestMapping("/selectHYPictureMainPage.action")
	public List<Map<String, Object>> selectHYPictureMainPage(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData);
		String sql = null;
		System.out.println(XmlData);
		//System.out.println(cds.getField("gsid", 0));
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			sql=
				"SELECT B.FILE01 ID,\n" +
				"       B.FILE02 FILENAME,\n" + 
				"       B.FILE03 FILEPATH,\n" + 
				"       B.FILE04 FILETYPE,\n" + 
				"       B.FILE05 URL\n" + 
				"  FROM W_FILE B\n" + 
				" WHERE B.FILE04 >=98 and B.FILE04 <= 105 and B.FILE01='"+(cds.getField("gsid", 0)==null?"jladmin":cds.getField("gsid", 0))+"' order by cast(FILE04 as unsigned int)";
		} else {		
			sql=
				"SELECT B.FILE01 ID,\n" +
				"       B.FILE02 FILENAME,\n" + 
				"       B.FILE03 FILEPATH,\n" + 
				"       B.FILE04 FILETYPE,\n" + 
				"       B.FILE05 URL\n" + 
				"  FROM W_FILE B\n" + 
				" WHERE B.FILE04 >=98 and B.FILE04 <= 105 order by cast(FILE04 as unsigned int)";
		}
		List picList=cds.queryForList(o2o, sql);
		return picList;
	}
	/**
	 * 查询热门搜索词
	 * @param XmlData
	 * @param NUM
	 * @return
	 */
	@RequestMapping("/selectRMSS.action")
	public List<Map<String, Object>> selectRMSS(String XmlData){
		ClientDataSet cds=new ClientDataSet(XmlData);
		String sql=
			"SELECT RMSSC02 FROM W_RMSS  ORDER BY RMSSC03 LIMIT "+cds.getField("NUM", 0)+"";
		List<Map<String, Object>> rmList=cds.queryForList(o2o, sql);
		return rmList;
	}
	@RequestMapping("/selectNewsInfomation.action")
	public List<Map<String, Object>> selectNewsInfomation(String XmlData){
		ClientDataSet cds=new ClientDataSet(XmlData);
		List<Map<String, Object>> newList;
		String sql = null;
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
//			sql=
//				"SELECT *\n" +
//				"  FROM (SELECT A.JLBH, B.CODE, TITLE,A.GSID\n" + 
//				"          FROM W_XX A, W_XXLX B\n" + 
//				"         WHERE A.CODE = B.CODE\n" + 
//				"           AND A.CODE = "+cds.getField("code", 0)+"\n" + 
//				"         ORDER BY A.FBSJ DESC)\n" + 
//				" WHERE ROWNUM < "+cds.getField("num", 0)+" and GSID='"+cds.getField("gsid", 0)+"'";
			sql=
				"SELECT *\n" +
				"  FROM (SELECT A.JLBH, B.CODE, TITLE,A.GSID\n" + 
				"          FROM W_XX A, W_XXLX B\n" + 
				"         WHERE A.CODE = B.CODE\n" + 
				"           AND A.CODE = "+cds.getField("code", 0)+"\n" + 
				"         ORDER BY A.FBSJ DESC)\n" + 
				" LIMIT "+cds.getField("num", 0)+" and GSID='"+cds.getField("gsid", 0)+"'";
			System.out.println(sql);
			newList=cds.queryForList(o2o, sql);
		} else {	
//			sql = 	"SELECT *\n" +
//			"  FROM (SELECT A.JLBH, B.CODE, TITLE\n" + 
//			"          FROM W_XX A, W_XXLX B\n" + 
//			"         WHERE A.CODE = B.CODE\n" + 
//			"           AND A.CODE = "+cds.getField("code", 0)+"\n" + 
//			"         ORDER BY A.FBSJ DESC)\n" + 
//			" WHERE ROWNUM < "+cds.getField("num", 0)+"";
			sql = 	"SELECT *\n" +
			"  FROM (SELECT A.JLBH, B.CODE, TITLE\n" + 
			"          FROM W_XX A, W_XXLX B\n" + 
			"         WHERE A.CODE = B.CODE\n" + 
			"           AND A.CODE = "+cds.getField("code", 0)+"\n" + 
			"         ORDER BY A.FBSJ DESC) C\n" + 
			"  limit "+cds.getField("num", 0)+"";
		}
		newList=cds.queryForList(o2o, sql);
		return newList;
	}
	/**
	 * 汽服云店铺分类
	 * @param XmlData
	 * @param NUM
	 * @return
	 */
	@RequestMapping("/selectSpfl.action")
	public List<Map<String, Object>> selectSpfl(String XmlData){
		ClientDataSet cds=new ClientDataSet(XmlData);
		String sql=
			"SELECT DISTINCT (select spfl01 from spfl where spfl01 = SUBSTR(A.spfl01,1,2))SPFLCODE,(select spfl02 from spfl where spfl01 = SUBSTR(A.spfl01,1,2)) SPFLNAME FROM  W_SPXX A,W_SPGL B WHERE A.SPXX01=B.SPXX01 AND B.ZCXX01='"+cds.getField("gsid",0)+"' and LENGTH(spfl01)=6";
		List<Map<String, Object>> flList=cds.queryForList(o2o, sql);
		return flList;
	}
	
	/**
	 * 显示小图信息
	 * @param XmlData
	 * @return
	 */
	@RequestMapping("/selectpic.action")
	public List<Map<String, Object>> selectpic(String XmlData){
		ClientDataSet cds = new ClientDataSet(XmlData);
		String sql="SELECT t.SPXX01, t.SPXX02, t.SPXX04, t.SPFL05, t.ZCXX01, t.SPTP02, t.ZCXX02, t.spgl03 "
				+ "FROM ( SELECT B.SPXX01, B.SPXX02, B.SPXX04, C.SPFL05, A.ZCXX01, D.SPTP02, Z.ZCXX02, a.spgl03 FROM W_SPGL A "
				+ "LEFT JOIN W_SPXX B ON A.SPXX01 = B.SPXX01 "
				+ "LEFT JOIN SPFL C ON B.SPFL01 = C.SPFL01 "
				+ "INNER JOIN W_SPTP D ON A.SPXX01 = D.SPXX01 AND D.ZCXX01 = A.ZCXX01 AND D.SPTP01 = 1 "
				+ "LEFT JOIN W_ZCXX Z ON A.zcxx01 = Z.zcxx01 WHERE A.spgl12 = '1' ) t "
				+ "LEFT JOIN ( SELECT B.SPXX01, B.SPXX02, B.SPXX04, C.SPFL05, A.ZCXX01, D.SPTP02, Z.ZCXX02, a.spgl03 FROM W_SPGL A "
				+ "LEFT JOIN W_SPXX B ON A.SPXX01 = B.SPXX01 LEFT JOIN SPFL C ON B.SPFL01 = C.SPFL01 "
				+ "INNER JOIN W_SPTP D ON A.SPXX01 = D.SPXX01 AND D.ZCXX01 = A.ZCXX01 AND D.SPTP01 = 1 "
				+ "LEFT JOIN W_ZCXX Z ON A.zcxx01 = Z.zcxx01 WHERE A.spgl12 = '1' ) t1 ON t1.SPFL05 = t.SPFL05 AND t.spgl03 < t1.spgl03 "
				+ "GROUP BY t.SPXX01, t.SPXX02, t.SPXX04, t.SPFL05, t.ZCXX01, t.SPTP02, t.ZCXX02 HAVING count(t.SPXX01) < 15 ORDER BY t.spgl03 DESC";
		List picList=cds.queryForList(o2o, sql);
		return picList;
	}
	
	/**
	 * 商品追溯
	 * @param XmlData
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectRemind_tmzs.action")
	public Map<String, Object> selectRemind_tmzs(String XmlData) throws Exception{
		ClientDataSet cds = new ClientDataSet(XmlData);
		Map map=new HashMap();
		try {
		String basesql="SELECT DISTINCT A.SPXX04,A.description,S.SPXX02,A.BARCODE,B.ZCXX01,C.ZCXX02,C.ZCXX08,C.ZCXX06 "
				+ " FROM W_GOODS A, W_SPGL B, W_ZCXX C,W_SPXX S WHERE A.SPXX01 = B.SPXX01 AND A.SPXX01=S.SPXX01" 
				+ " AND B.ZCXX01=C.ZCXX01  AND  A.BARCODE = '"+cds.getField("barcode", 0)+"'";
		map=cds.queryForMap(o2o, basesql);
		if(map==null){
			map=new HashMap();
			map.put("STATE","0");
		}else{
			map.put("STATE","1");
		}
		} catch(Exception e) {
		map.put("STATE","0");
		throw e;
		// TODO: handle exception
	}
		return map;
	}
	/**
	 * 商品追溯_批次号
	 * @param XmlData
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectRemind_tmzsPch.action")
	public Map<String, Object> selectRemind_tmzsPch(String XmlData) throws Exception{
		Map map=new HashMap();
		try {
			ClientDataSet cds = new ClientDataSet(XmlData);
			String barcode=cds.getField("barcode", 0);
			String PCH=cds.getField("spcm03", 0);
			String XLH=cds.getField("spcm04", 0);
			String basesql="SELECT DISTINCT A.SPXX04,A.description,S.SPXX02,A.BARCODE,D.SPCM01,B.ZCXX01,C.ZCXX02, "
					+ "  C.ZCXX08,C.ZCXX06,E.CKSJ,E.SHCK,'安讯物流' SHF,NOW() SHSJ,'010' PHONE,'GMSJ' GMSJ,'ghf' GHF" 
					+ "  FROM W_GOODS A, W_SPGL B, W_ZCXX C,W_CKDCM D,W_CKD E,W_SPXX S " 
					+ "  WHERE A.SPXX01 = B.SPXX01 AND B.ZCXX01=C.ZCXX01 AND A.SPXX01=S.SPXX01 "
					+ "  AND A.SPXX01 = D.SPXX01 AND C.ZCXX01 = E.ZCXX01 AND D.CKDH= E.CKDH "
					+ "  AND D.PCH =  '"+PCH+"' AND D.XLH= '"+XLH+"' AND A.BARCODE = '"+barcode+"'";
			map=cds.queryForMap(o2o, basesql);
			if(map==null){
				map=new HashMap();
				map.put("STATE","0");
			}else{
				map.put("STATE","1");
			}
			
		} catch (Exception e) {
			map.put("STATE","0");
			throw e;
			// TODO: handle exception
		}
		
		return map;
	}
	
}
