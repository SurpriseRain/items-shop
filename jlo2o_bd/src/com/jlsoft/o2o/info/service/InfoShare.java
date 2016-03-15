package com.jlsoft.o2o.info.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.ClientDataSet;
import com.jlsoft.utils.JlAppResources;
@Controller
@RequestMapping("/InfoShare")
public class InfoShare extends JLBill{
	/**
	 * 查询以商会友
	 * @param	String XmlData
	 * 	- String	CKQX	客户编码
	 *  - String	TJBJ    地区编码
	 * 
	 * @return	Map
	 *  - ZTID		供货商编码
	 * @note 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/selectYshy.action")
	public Map selectYshy(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
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
						"         WHERE X.CKQX =" +cds.getField("CKQX", 0)+
						"           AND X.TJBJ = " +cds.getField("TJBJ", 0)+
						"         ORDER BY X.FBSJ DESC) N \n" + 
						" limit 5";


		List result = queryForList(o2o, querySQL);
		
		Map map = new HashMap();
		map.put("yshyList", result);
		return map;
	}
	

	/**
	 * 查询营销案例
	 * @param	String XmlData
	 * 	- String	CKQX	客户编码
	 * 
	 * @return	Map
	 *  - ZTID		供货商编码
	 * @note 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/selectYxal.action")
	public Map selectYxal(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL =
						"  SELECT M.*\n" + 
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
						"                 WHERE A.CKQX = " +cds.getField("CKQX", 0)+
						"                 ORDER BY A.FBSJ DESC) M\n" + 
						"         limit  8\n" ;
		List result = queryForList(o2o, querySQL);
		System.out.println(((Map)result.get(0)).get("FBSJ"));
		Map map = new HashMap();
		map.put("yxalList", result);
		return map;
	}
	
	/**
	 * 查询营销案例
	 * @param	String XmlData
	 * 	- String	ALBH	案例编号
	 * 
	 * @return	Map
	 *  - ZTID		供货商编码
	 * @note 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/selectAlMessage.action")
	public Map selectAlMessage(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL =
				"SELECT A.CONTEXT CONTEXT," +
						"A.ALTITLE ALTITLE," +
						"date_format(A.FBSJ,'%Y-%m-%d') FBSJ\n" +
						"  FROM W_ALFX A\n" + 
						"  WHERE A.ALBH = " +cds.getField("ALBH", 0);
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			querySQL =
					"SELECT A.CONTEXT CONTEXT," +
							"A.ALTITLE ALTITLE," +
							"W.LXBH LXBH," +
							"W.LXMC LXMC," +
							"W.DHBJ DHBJ," +
							"W.DHMC DHMC," +
							"date_format(A.FBSJ,'%Y-%m-%d') FBSJ\n" +
							"  FROM W_ALFX A, W_ALLX W\n" + 
							"  WHERE A.ALLX=W.LXBH AND A.ALBH = " +cds.getField("ALBH", 0);
		} 
		Map map = queryForMap(o2o, querySQL);
		
		return map;
	}
	/**
	 * 查询新闻资讯
	 * @param	String XmlData
	 * 	- String	ALBH	案例编号
	 * 
	 * @return	Map
	 *  - ZTID		供货商编码
	 * @note 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/selectJlMessage.action")
	public Map selectJlMessage(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		String querySQL ="SELECT TITLE,TO_CHAR(FBSJ,'yyyy-MM-dd') FBSJ,CONTEXT FROM W_XX WHERE JLBH="+cds.getField("JLBH", 0);
		
		Map map = queryForMap(o2o, querySQL);
		
		return map;
	}
}
