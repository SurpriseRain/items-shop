package com.jlsoft.o2o.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.activemq.console.Main;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chefu.common.RedisClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.init.service.Globals;
import com.jlsoft.utils.ClientDataSet;
import com.jlsoft.utils.JLConn;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

/**
 * 
 * @breif 首页相关后台操作
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/showGoods")
public class showGoods extends JLBill{
	public showGoods() {
	}
	public showGoods(JdbcTemplate o2o){
		this.o2o = o2o;
	}
	
	private RedisClient rc = new RedisClient();
	
	//查询项目
	@RequestMapping("/selectProjects.action")	
	public Map selectProjects() throws Exception {
		String sql  ="select XMBH, XMMC From W_PROJECT";	
		List list = queryForList(o2o,sql);
		
		Map map = new HashMap();
    	map.put("projectlist", list);
    	
	    return map;
    }
	
	//根据项目查询一级分类
	@RequestMapping("/selectFenlei1ByProject.action")	
	public Map  selectFenlei1ByProject(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String sql = "SELECT distinct t3.spfl01 FLCODE, t3.spfl02 FLNAME FROM SPFL t1 "
							+ "join  W_PROJECT_FL w on  t1.spfl01=w.spfl01 "+
							"join  spfl t2 on t1.spf_spfl01=t2.spfl01 "+
							"join  spfl t3 on t2.spf_spfl01=t3.spfl01 "+
							 "WHERE t1.SPFL03 = 3 AND w.XMBH='"+cds.getField("xmbh", 0)+"' ORDER BY t3.spf_spfl01";
		List list=queryForList(o2o, sql);
		Map map1 = new HashMap();
		map1.put("list", list);
		return map1; 
    }
	//根据项目查询三级分类
	@RequestMapping("/selectFenlei3ByProject.action")	
	public Map  selectFenlei2ByProject(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String sql = "SELECT distinct t1.spfl01 FLCODE, t1.spfl02 FLNAME FROM SPFL t1 "
							+ "join  W_PROJECT_FL w on  t1.spfl01=w.spfl01 "+
							 "WHERE t1.SPFL03 = 3 AND w.XMBH='"+cds.getField("xmbh", 0)+"' ORDER BY t1.spf_spfl01";
		List list=queryForList(o2o, sql);
		Map map1 = new HashMap();
		map1.put("list", list);
		return map1; 
    }
	//根据项目查询品牌
	@RequestMapping("/selectPinpaiByProject.action")	
	public Map  selectPinpaiByProject(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String sql="SELECT distinct t5.ppb01 FLCODE, t5.ppb02 FLNAME FROM SPFL t1 "+
					"join  W_PROJECT_FL w on  t1.spfl01=w.spfl01 "+
					"join  spfl t2 on t1.spf_spfl01=t2.spfl01 "+
					"join  spfl t3 on t2.spf_spfl01=t3.spfl01 "+
					"join  w_ppfl t4 on t3.spfl01=t4.spfl01 "+
					"join  ppb t5 on  t4.ppb01=t5.ppb01 "+
					"WHERE t1.SPFL03 = 3 AND w.XMBH='"+cds.getField("xmbh", 0)+"' ORDER BY t5.ppb01";
		List list=queryForList(o2o, sql);
		
		Map map1 = new HashMap();
		map1.put("list", list);
		
		return map1;  	
    }
		
	//很据分类查询品牌
	@RequestMapping("/selectPinpaiByfenlei.action")	
	public Map selectPinpaiByfenlei(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String[] ss=cds.getField("spfl01", 0).split(",");
		StringBuffer filterStringBuffer=new StringBuffer();
		for(int i=0;i<ss.length;i++){
			if(i==0){
				filterStringBuffer.append("'"+ss[i]+"'");
			}else{
				filterStringBuffer.append(",'"+ss[i]+"'");
			}
		}
		String sql="SELECT  distinct t5.ppb01 FLCODE,t5.ppb02 FLNAM from   w_ppfl t1 "+
		"join  spfl t2 on t1.spfl01=t2.spfl01 "+
		"join  spfl t3 on t2.spfl01=t3.spf_spfl01 "+
		"join  spfl t4 on t3.spfl01=t4.spf_spfl01 "+
		"join  ppb t5 on  t1.ppb01=t5.ppb01 "+
		"WHERE  t2.SPFL03='1' and t3.SPFL03='2'  and t4.SPFL03='3' and t4.spfl01 in("+filterStringBuffer.toString()+")  ORDER BY t5.ppb01";

		List<HashMap<String, String>> list=queryForList(o2o, sql);
		
		Map map1 = new HashMap();
		map1.put("list", list);
		
		return map1; 
		
    }
	
	//很据分类查询项目
	@RequestMapping("/selectProjectByfenlei.action")	
	public Map selectProjectByfenlei(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String[] ss=cds.getField("spfl01", 0).split(",");
		StringBuffer filterStringBuffer=new StringBuffer();
		for(int i=0;i<ss.length;i++){
			if(i==0){
				filterStringBuffer.append("'"+ss[i]+"'");
			}else{
				filterStringBuffer.append(",'"+ss[i]+"'");
			}
		}
		String sql="SELECT distinct t4.XMBH FLCODE, t4.XMMC FLNAME FROM SPFL t1 "+
				"join  W_PROJECT_FL w on  t1.spfl01=w.spfl01 "+
				"join  W_PROJECT t4 on w.XMBH=t4.XMBH "+
				"WHERE t1.SPFL03 = 3 AND t1.SPFL01 in("+filterStringBuffer.toString()+") ORDER BY t4.XMBH";
		List list=queryForList(o2o, sql);
		
		Map map1 = new HashMap();
		map1.put("list", list);
		
		return map1;  	
    }
	//查询商品属性 
    @RequestMapping("/selectSPSX.action")
    public Map selectSPSX(String XmlData) throws Exception {
    	cds = new DataSet(XmlData);
    	String sql ="SELECT SXBH, SXNAME " +
    			"    FROM w_spflsx   WHERE SPFL='"+cds.getField("flcode02", 0)+"'";	
       List list = queryForList(o2o,sql);
    	Map map = new HashMap();
    	map.put("sx", list);
    	
	   return map;
    }
	
	//查询商品属性 值
    @RequestMapping("/selectSPSXItem.action")
    public Map selectSPSXItem(String XmlData) throws Exception {
    	cds = new DataSet(XmlData);
    	String sql ="SELECT SXZDM, SXZNAME " +
    			"    FROM w_spflsxitem  WHERE SXBH='"+cds.getField("sxbh", 0)+"'";	
    	   List list = queryForList(o2o,sql);
       	Map map = new HashMap();
       	map.put("sxitem", list);
	   return map;
    }
  //查询品牌
	public List<HashMap<String, String>> selectPPB(Map map){
				String sql="SELECT DISTINCT\n" +
		"	P.PPB01 PPBCODE,\n" +
		"	P.PPB02 PPBNAME,\n" +
		"	P.PPZJ PPZJ,\n" +
		"	'"+map.get("ppfl02code")+"' FL02CODE\n" +
		"FROM\n" +
		"	W_SPGL L,\n" +
		"	PPB P,\n" +
		"	W_SPXX B\n" +
		"WHERE\n" +
		"	L.SPXX01 = B.SPXX01\n" +
		"AND B.PPB01 = P.PPB01\n" +
		"AND B.SPFL01 LIKE CONCAT('"+map.get("ppfl02code")+"', '%')\n" +
		"ORDER BY\n" +
		"	P.PPB01";
		List<HashMap<String, String>> list=queryForList(o2o, sql);
		return list;
	}  
	
	//一级分类	
	public List select_fl01() throws Exception {
		String sql = null;
		if(JlAppResources.getProperty("ROADMAP").equals("3")) {
			sql ="SELECT F.SPFL_CODE FLCODE, F.SPFL_NAME FLNAME, " +
			"    F.SPFL_CODE FATHERCODE FROM SPFLZDY F WHERE" +
			"    F.SPFL02 = 1 AND F.PXBJ <> 0 ORDER BY F.PXBJ";	
		} else if(JlAppResources.getProperty("ROADMAP").equals("4")){
			sql ="SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME, " +
			"    F.SPF_SPFL01 FATHERCODE, F.SPFLZJ SPFLZJ FROM SPFL F WHERE" +
			"    F.SPFL03 = 1 ORDER BY F.PXBJ";	
		} else {
			sql ="SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME, " +
			"    F.SPF_SPFL01 FATHERCODE FROM SPFL F WHERE" +
			"    F.SPFL03 = 1 AND F.PXBJ <> 0 ORDER BY F.PXBJ";	
		}
		String s = "3";
		List list = queryForList(o2o,sql);
	    return list;
    }
       
    //二级分类
    public List select_fl02(String XmlData) throws Exception {
    	cds = new DataSet(XmlData);
    	String sql = null;
		if(JlAppResources.getProperty("ROADMAP").equals("3")) {
	    	sql=" SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME,G.SPFL_CODE FATHERCODE " +
			   " FROM SPFL F,SPFLZDY G WHERE F.SPFL03 = 2 AND F.SPFL01 = G.SPFL01 AND G.SPFL_CODE " +
			   " LIKE '" +cds.getField("ppfl01code", 0)+ "' ||'%'" +
			   " ORDER BY F.SPFL01";
		} else {
	    	sql=" SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME,F.SPF_SPFL01 FATHERCODE " +
	    			   " FROM SPFL F WHERE F.SPFL03 = 2 AND F.SPFL01 " +
	    			   " LIKE CONCAT('"+cds.getField("ppfl01code", 0)+"','%')"  +
	    			   " ORDER BY F.SPFL01";
		}
    	List list = queryForList(o2o,sql);
	    return list;
    }
  //三级分类
    public List select_fl03(String XmlData) throws Exception {
    	cds = new DataSet(XmlData);
    	String sql = null;
	    sql=" SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME,F.SPF_SPFL01 FATHERCODE " +
	    			   " FROM SPFL F WHERE F.SPFL03 = 3 AND F.SPFL01 " +
	    			   " LIKE CONCAT('"+cds.getField("ppfl02code", 0)+"','%')"  +
	    			   " ORDER BY F.SPFL01";
		
    	List list = queryForList(o2o,sql);
	    return list;
    }
    
    //四级分类
    public List select_fl04(String XmlData) throws Exception {
    	cds = new DataSet(XmlData);
    	String sql = null;
	    sql=" SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME,F.SPF_SPFL01 FATHERCODE " +
	    			   " FROM SPFL F WHERE F.SPFL03 = 4 AND F.SPFL01 " +
	    			   " LIKE CONCAT('"+cds.getField("ppfl03code", 0)+"','%')"  +
	    			   " ORDER BY F.SPFL01";
		
    	List list = queryForList(o2o,sql);
	    return list;
    }
    @RequestMapping("/selectGlobals.action")
    public Map selectGlobals(String XmlData) throws Exception {
    	List goodslist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		goodslist = (List) rc.GetObjectByKey("goodslist");
    	} else {
    		goodslist = Globals.goodslist;
    	}
    	Map map = new HashMap();
    	map.put("fl", goodslist);
		return map;
    }
    
    @RequestMapping("/selectGlobalss.action")
    public Map selectGlobalss(String er,int lengths) throws Exception {    	
    	JSONObject object;
    	Map map = new HashMap();
    	List goodslist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		goodslist = (List) rc.GetObjectByKey("goodslist");
    	} else {
    		goodslist = Globals.goodslist;
    	}
    	JSONArray XmlDatas=JSONArray.fromObject(goodslist);   	  	
    	for(int i=0;i<lengths;i++){  	
       		object = (JSONObject)XmlDatas.get(i);     		      		
	    	if(object.get("ppfl01code").toString().equals(er)){	
	    		map.put("fll", object.get("SPFL02List"));
	    	}    		
    	}    	   	   	
		return map;
    }
      
    
      	
	/**
	 * 保存签约信息
	 * @param	 String XmlData
	 *  - String	ZTID	主体编码
	 *  - String	HBID	伙伴编码
	 *  - String	QYXX01	卡户名
	 *  - String	QYXX03	卡号
	 *  - String	QYXX04	身份证号
	 *  - String	QYXX05	签约手机号
	 *  
	 * @return	Map
	 *  - STATE			保存状态 0失败 1成功
	 *  - ERRORMESSAGE	错误信息
	 * @throws Exception 
	 * @throws Exception 
	 *  
	 * @note 保存签约信息
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping("/applyBatchPermission.action")
	public Map applyBatchPermission(String XmlData) throws Exception {
		Map map=new HashMap();
		int j=0;
		try {
			cds = new DataSet(XmlData);
			String flpp=cds.getField("FLPPLIST", 0);
			if(!"".equals(flpp) && null != flpp){
				JSONArray FLPPLIST = JSONArray.fromObject(flpp);
				if(FLPPLIST != null && FLPPLIST.size() > 0){
					for (int i = 0; i < FLPPLIST.size(); i++) {
						Map flppMap = (Map) FLPPLIST.get(i);
						JSONArray flppInfo = JSONArray.fromObject(flppMap);
						j+=insertFLPP(flppInfo.toString());
					}
				}
			}
			if(j>0){
				map.put("STATE","1");
			}else{
				map.put("STATE","0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
		return map;
	}
    	
	public int insertFLPP(String XmlData) throws Exception {
		int j=0;
		try {
			cds = new DataSet(XmlData);
			String querySQL = "SELECT COUNT(0) FROM W_FLPPQX WHERE HBID ='" + cds.getField("HBID", 0) + "'\n" +
															 "AND ZTID = '" + cds.getField("ZTID", 0) + "'\n" +
															 "AND PPB01 = '" + cds.getField("PPB01", 0) + "'\n" +
															 "AND SPFL01 ='" + cds.getField("SPFL01", 0) + "'";
			int i=queryForInt(o2o, querySQL);
			if(i==0){
				String insertSQL = "INSERT INTO W_FLPPQX\n" +
											"   (HBID,ZTID,PPB01,SPFL01,FLPP02,FLPP01,FLPP05)\n" + 
											"VALUES\n" + 
											"  (HBID?,ZTID?,PPB01?,SPFL01?,1,SYSDATE,1);";
				
				//获取行值
				Map row = getRow(insertSQL, null, 0);
				j=execSQL(o2o, insertSQL, row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
		return j;
	}
	
	
	/**
	 * 汽服云 yihailing
	 * @return
	 * @throws Exception
	 */
	
	//一级分类	
	public List qfySelect_fl01() throws Exception {
		
		String sql ="SELECT DISTINCT MNEMONICCODE FROM W_AUTOMOBILE ORDER BY MNEMONICCODE";	
		List list = queryForList(o2o,sql);
	    return list;
    }
	
	  //二级分类
    public List qfySelect_fl02(String MNEMONICCODE) throws Exception {
    	
    	String sql="SELECT DISTINCT BRAND,fn_getpy(BRAND) BRANDPY,BRAND_ID FROM W_AUTOMOBILE " +
    			"WHERE MNEMONICCODE='"+MNEMONICCODE+"' ORDER BY BRAND ";
    	List list = queryForList(o2o,sql);
	    return list;
    }
	
	  //三级分类
    public List qfySelect_fl03(String BRAND) throws Exception {
   
    	String sql="SELECT DISTINCT CAR_TYPE FROM W_AUTOMOBILE " +
    			"WHERE BRAND='"+BRAND+"' ORDER BY CAR_TYPE ";
    	List list = queryForList(o2o,sql);
	    return list;
    }
	
	  //四级分类
    public List qfySelect_fl04(String CAR_TYPE) throws Exception {
    
    	String sql="SELECT DISTINCT CAR_ID,CAR FROM W_AUTOMOBILE " +
    			"WHERE CAR_TYPE='"+CAR_TYPE+"' ORDER BY CAR ";
    	List list = queryForList(o2o,sql);
	    return list;
    }
	
    @RequestMapping("/qfylist.action")
    public Map qfylist(String XmlData) throws Exception {
    	List qfylist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		qfylist = (List) rc.GetObjectByKey("qfylist");
    	} else {
    		qfylist = Globals.qfylist;
    	}
    	Map map = new HashMap();
    	map.put("qfylist", qfylist);
		return map;
    }
    
    @RequestMapping("/qfySelectTJ.action")
    public Map qfySelectTJ(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String sql1="SELECT DISTINCT MOBILE_MODLE_YEAR FROM W_AUTOMOBILE WHERE CAR_ID='"+cds.getField("CAR_ID", 0)+"' ORDER BY MOBILE_MODLE_YEAR";
		String sql2="SELECT DISTINCT MOBILE_DISPLACEMENT FROM W_AUTOMOBILE WHERE CAR_ID='"+cds.getField("CAR_ID", 0)+"' ORDER BY MOBILE_DISPLACEMENT";
		String sql3="SELECT DISTINCT MOBILE_TRANSMISSION_TYPE FROM W_AUTOMOBILE WHERE CAR_ID='"+cds.getField("CAR_ID", 0)+"' ORDER BY MOBILE_TRANSMISSION_TYPE";
		
		List list1 = queryForList(o2o,sql1);
		List list2 = queryForList(o2o,sql2);
		List list3 = queryForList(o2o,sql3);
		
    	Map map = new HashMap();
    	map.put("qfySelectTJ1",list1);
    	map.put("qfySelectTJ2",list2);
    	map.put("qfySelectTJ3",list3);
		return map;
    }
    
    @RequestMapping("/qfypplist.action")
    public Map qfyPPlist(String XmlData) throws Exception {
    	List qfypplist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		qfypplist = (List) rc.GetObjectByKey("qfypplist");
    	} else {
    		qfypplist = Globals.qfypplist;
    	}
    	Map map = new HashMap();
    	map.put("qfypplist", qfypplist);
		return map;
    }
    @RequestMapping("/cxcxlist.action")
    public Map cxcxlist(String XmlData) throws Exception {
    	List cxcxlist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		cxcxlist = (List) rc.GetObjectByKey("cxcxlist");
    	} else {
    		cxcxlist = Globals.cxcxlist;
    	}
    	Map map = new HashMap();
    	map.put("cxcxlist", cxcxlist);
		return map;
    }
	public List<Map<String, String>> select_qfyppfl01() throws Exception {
		
		String sql ="SELECT distinct ORIGIN_COUNTRY, ORIGIN_COUNTRY_ID FROM W_AUTOMOBILE ORDER BY ORIGIN_COUNTRY";	
		List list = queryForList(o2o,sql);
	    return list;
	}
	
	public List<HashMap<String, String>> select_qfyppfl02(String ppfl01code) throws Exception {
		
		String sql ="SELECT distinct BRAND,fn_getpy(BRAND) BRANDPY, MNEMONICCODE,brand_id FROM W_AUTOMOBILE where ORIGIN_COUNTRY_ID='"+ppfl01code+"' ORDER BY MNEMONICCODE,brand_id";	
		List list = queryForList(o2o,sql);
		return list;
	}
	
    @RequestMapping("/pjpplist.action")
    public Map pjpplist(String XmlData) throws Exception {
    	List pjpplist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		pjpplist = (List) rc.GetObjectByKey("pjpplist");
    	} else {
    		pjpplist = Globals.pjpplist;
    	}
    	Map map = new HashMap();
    	map.put("pjpplist", pjpplist);
		return map;
    }
	
	public List selectPjpplists() {
		String sql = "SELECT PPB01, PPB02 FROM PPB WHERE YXBJ = 1";
		List list = queryForList(o2o,sql);
		return list;
	}
	
	public List select_pp(String ppfl01code) {
		//String sql = "SELECT PPB01, PPB02, PPZJ FROM ppb WHERE spfl01='"+ppfl01code+"' order by PPZJ";
		//String sql = "SELECT DISTINCT A.PPB01,B.PPB02,B.PPZJ FROM W_PPFL A,PPB B WHERE A.PPB01=B.PPB01 AND B.PPB04=1 AND A.SPFL01='"+ppfl01code+"' ORDER BY B.PPZJ";
		String sql = "SELECT DISTINCT A.PPB01,B.PPB02,B.PPZJ FROM W_PPFL A "
				+ "LEFT JOIN PPB B ON A.PPB01 = B.PPB01 "
				+ "LEFT JOIN W_PPQX C ON A.PPB01 = C.PPB01 "
				+ "WHERE  B.PPB04 = 1 AND C.STATE = 1 AND B.YXBJ = 1 AND A.SPFL01='" + ppfl01code + "' ORDER BY B.PPZJ";
		List list = queryForList(o2o,sql);
		return list;
	}
	
    @RequestMapping("/selectFL.action")
    public Map selectFL(String XmlData) throws Exception {
    	List fllist = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		fllist = (List) rc.GetObjectByKey("fllist");
    	} else {
    		fllist = Globals.fllist;
    	}
    	Map map = new HashMap();
    	map.put("fl", fllist);
		return map;
    }
    @RequestMapping("/selectFL2.action")
    public Map selectFL2(String XmlData) throws Exception {
    	Map map = new HashMap();
    	String sql ="SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME " +
				"     FROM SPFL F WHERE" +
				"     F.SPFL03 = 1 ORDER BY F.PXBJ";	
		List list = queryForList(o2o,sql);
		map.put("fl",list);
		return map;
    }
    /**
     * @todo 查询维修项目
     * @param json
     * @return
     * @throws Exception 
     */
    @RequestMapping("/sselectWXXM")
    public Map selectWXXM(String json) throws Exception{
    	Map map = new HashMap();
    	List projectList = new ArrayList();
    	if(JLTools.getCurConf(5) == 1){
    		projectList = (List) rc.GetObjectByKey("projectList");
    	} else {
    		projectList = Globals.projectList;
    	}
    	map.put("project", projectList);
		return map;
    }
    
	public List select_pjfl01() {
		String sql ="select DISTINCT spfl05 FLCODE, spfl06 FLNAME from spfl where spfl05<>'NULL'";	
		List list = queryForList(o2o,sql);
		return list;
	}
	
	public List<Map<String, String>> select_pjfl02(String ppfl01code) {
		String sql ="SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME, " +
				"    F.SPF_SPFL01 FATHERCODE ,(select case when  SPFL02 is null then  '' else   SPFL02 end from SPFL where SPFL03 ='1' and SPFL01= F.SPF_SPFL01) FATHERNAME FROM SPFL F WHERE" +
				"    F.SPFL05 = '"+ppfl01code+"'" +
				"    AND F.SPFL03 = 1 ORDER BY F.PXBJ";	
		List list = queryForList(o2o,sql);
		return list;
	}
	
	public List<HashMap<String, String>> select_pjfl03(String ppfl02code) {
		String sql ="SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME, " +
				"    F.SPF_SPFL01 FATHERCODE FROM SPFL F WHERE" +
				"    F.SPFL01 LIKE CONCAT('"+ppfl02code+"','%')" +
				"    AND F.SPFL03 = 2 ORDER BY F.PXBJ";	
		List list = queryForList(o2o,sql);
		return list;
	}
	
	public List<HashMap<String, String>> select_pjfl04(String ppfl03code) {
		String sql ="SELECT F.SPFL01 FLCODE, F.SPFL02 FLNAME, " +
				"    F.SPF_SPFL01 FATHERCODE FROM SPFL F WHERE" +
				"    F.SPFL01 LIKE CONCAT('"+ppfl03code+"','%')" +
				"    AND F.SPFL03 = 3 ORDER BY F.PXBJ";	
		List list = queryForList(o2o,sql);
		return list;
	}
	
	/**
	 * @todo 查询品牌表
	 * @param map
	 * @return
	 */
	public List select_ppblist(Map maps){
		String sql="SELECT * FROM ("+
						 "SELECT DISTINCT P.PPB01 PPBCODE,P.PPB02 PPBNAME,'"+maps.get("ppfl02code").toString()+"' FL02CODE,'1' STATE "+
						 "FROM PPB P, W_SPXXGL S WHERE S.PPB01 = P.PPB01 AND S.SPFL01 LIKE CONCAT('"+maps.get("ppfl02code").toString()+"','%') "+
						 "ORDER BY P.PPB01) A ORDER BY STATE DESC";
		List list = queryForList(o2o,sql);
		return list;
	}
	/*商品同步单条商品查询商品信息*/
	@SuppressWarnings("unchecked")
	@RequestMapping("/srchSPXX.action")
	public Map<String, Object> srchSPXX(HttpServletRequest request,
			HttpServletResponse response,String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		//cds=new DataSet(XmlData);
		//String GSID=request.getParameter("gsid"); "AND A.ZCXX01='"+GSID+"'
		String SPID=request.getParameter("sp_id");
		String sql="SELECT A.SPXX01 SP_ID,A.ZCXX01 JXS,B.SPXX04 SPNAME,B.SPFL02_NAME FLNAME,B.PPB02 PPNAME,A.SPGL04 SPJG,A.SPGL02 DTBJ,A.SPGL11 TJBJ,A.SPGL08 TJSP," +
				"A.SPGL09 RMBJ,A.SPGL10 XPBJ,A.SPGL05 FBYY,A.SPGL14 MINNUM,A.SPGL15 MAXNUM,A.SPGL06 SCJFBJ,A.SPGL07 JFXFBJ,E.SPJGB02 FXXJ,SUM(C.CKSP04 + C.CKSP05 - C.KCXX02 + C.KCXX01) CKSL," +
				"A.SPGL01 TJLY,A.SPGL17 CXLXBJ,SUM(C.CKSP04 + C.CKSP05 - C.KCXX02) CKKC" +
				" FROM W_SPGL A, W_SPXX B, W_KCXX C,CK, W_SPJGB E WHERE A.SPXX01 = B.SPXX01 AND A.SPXX01 = C.SPXX01 AND " +
				"A.ZCXX01 = C.ZCXX01 AND A.SPXX01 = E.SPXX01 AND C.CK01 = CK.CK01 AND CK.ck09='0' AND A.ZCXX01 = E.ZCXX01 " +
				 " AND  A.SPXX01='"+SPID+"' GROUP BY A.SPXX01,B.SPXX04,B.SPFL02_NAME,B.PPB02,A.SPGL04,A.SPGL05,A.ZCXX01,A.SPGL02,A.SPGL17,A.SPGL11," +
						"A.SPGL08,A.SPGL09,A.SPGL10,A.SPGL14,A.SPGL15,A.SPGL06,A.SPGL07,E.SPJGB02,A.SPGL01";
		List list=queryForList(o2o, sql);
		map.put("list",list);
		return map;
	}
	
	//插入商品发布，新增。(商品同步)
	@SuppressWarnings("unchecked")
	@RequestMapping("/save_SPFB_SPXX.action")
	public Map<String, Object> save_SPFB_SPXX(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String sql="UPDATE W_SPGL SET SPGL03 = NOW(),SPGL11 =tjsp?,SPGL10=xpbj?,SPGL09=rmbj?,SPGL08=tjbj?,SPGL02=dtbj?," +
					"SPGL04=spjg?,SPGL14=minnum?,SPGL15=maxnum?,SPGL05=fbyy?," +
					"SPGL01=tjly?,SPGL17=cxlxbj? WHERE SPXX01=sp_id? and ZCXX01=jxs?";

			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}
	//删除商品发布(商品同步)
		@SuppressWarnings("unchecked")
		@RequestMapping("/del_SPFB_SPXX.action")
		public Map<String, Object> del_SPFB_SPXX(String XmlData) throws DataAccessException, Exception{
			Map map=new HashMap();
			cds=new DataSet(XmlData);
			try{
				String sql="DELETE FROM W_SPGL WHERE SPXX01=SPXX01? AND ZCXX01=ZCXX01?";
				Map	row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				
				map.put("STATE", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
			return map;
		}
		//商品上架、下架(商品同步)
		@SuppressWarnings("unchecked")
		@RequestMapping("/update_SPFB_SPXX.action")
		public Map<String, Object>update_SPFB_SPXX(String XmlData) throws DataAccessException, Exception{
			Map map=new HashMap();
			cds=new DataSet(XmlData);
			try{
				String sql="UPDATE W_SPGL SET SPGL12 =(CASE WHEN SPGL12='0' THEN '1' ELSE '0' END) WHERE SPXX01=SPXX01? AND ZCXX01=ZCXX01?";
				Map	row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				
				map.put("STATE", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
			return map;
		}
		//商品关联
		@SuppressWarnings("unchecked")
		@RequestMapping("/update_SPGL.action")
		public Map<String, Object>update_SPGL(String XmlData) throws DataAccessException, Exception{
			Map map=new HashMap();
			cds=new DataSet(XmlData);
			try{
				String selectSql="SELECT count(1) count from w_glsp where ZCXX01="+cds.getField("ZCXX01", 0)+" AND SPXX01="+cds.getField("SPXX01", 0)+" AND GLSPXX01="+cds.getField("SPXX", 0);
				int count = queryForInt(o2o, selectSql);
				if(count==0){
					
					String sql="INSERT INTO W_GLSP (ZCXX01,SPXX01,GLSPXX01,GLSJ) VALUES (ZCXX01?,SPXX01?,SPXX?,NOW())";
					Map	row = getRow(sql, null, 0);
					execSQL(o2o, sql, row);
					map.put("STATE", "1");
				}else{
					map.put("STATE", "2");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
			return map;
		}
		
		/***
		 * 发布商品属性类型
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/selectFbspsxs.action")
	    public List selectFbspsxs(String XmlData) throws Exception {
			cds =new DataSet(XmlData);
			JSONArray jsonArray=JSONArray.fromObject(XmlData);
			Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
			String sql="select SPFL01,SXLX01,SXLX02,SXLX03,YXBJ from w_sxlx where YXBJ=1 and spfl01='"+hm.get("SPFL01")+"'";
			List flsxs = queryForList(o2o, sql);
			List<Map> list01 = new ArrayList();
			for(int i=0;i<flsxs.size();i++){
				Map spflsx01map = (Map<String, String>) flsxs.get(i);	
				String spflsx01mapcode = (String) spflsx01map.get("SXLX01");
				String spflsx01mapname = (String) spflsx01map.get("SXLX02");
				String spflsx01isfile = (String) spflsx01map.get("SXLX03").toString();
				Map map = new HashMap();
				map.put("spflsx01mapcode", spflsx01mapcode);
				map.put("spflsx01mapname", spflsx01mapname);
				map.put("spflsx01isfile", spflsx01isfile);
				list01.add(map);
				sql="select SXLXZ01 spflsx02mapcode,SXLXZ02 spflsx02mapname From w_sxlxz where yxbj=1 and zcxx01='"+hm.get("ZCXX01")+"' and sxlx01='"+spflsx01mapcode+"'";
				List flsx02list = queryForList(o2o, sql);
				map.put("flsx02list", flsx02list);
				
			}
	    	
			return list01;
	    }
		
		/***
		 * 发布商品属性类型值
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/selectFbspsxzs.action")
	    public List selectFbspsxsz(String XmlData) throws Exception {
			cds =new DataSet(XmlData);
			JSONArray jsonArray=JSONArray.fromObject(XmlData);
			Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
			String sql="select SXLX01 code,SXLXZ02 name From w_sxlxz where yxbj=1 and zcxx01='"+hm.get("ZCXX01")+"' and sxlx01='"+hm.get("spflsx01")+"'";
			List flsx02list = queryForList(o2o, sql);
			return flsx02list;
	    }
}
