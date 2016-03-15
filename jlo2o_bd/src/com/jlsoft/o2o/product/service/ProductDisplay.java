/**********************************************************************
 * @file	HomeDisplay.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	赵明亮/20140314
 * @author	Code:	
 * @author	Modify:	
 * @copy	Tag:	金力软件
 **********************************************************************/

package com.jlsoft.o2o.product.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.framework.dataset.DataSet;

/**
 * 
 * @breif 各类商品展示
 *
 */
@Controller
@RequestMapping("/ProductDisplay/jlquery")
public class ProductDisplay extends JLBill{
	private SqlSession session=null;
	
	/**
	 * 登录前,各种商品展示时调用
	 * @param	String XmlData
	 * 	- int		SPGL08	促销标记
	 * 	- int		SPGL09	热卖标记
	 *	- int		SPGL10	新品标记
	 *	- int		SPGL11	特价标记
	 * 	- int		SPGL02	大厅标记(标记是否为大厅商品展示)
	 * 
	 * @return	Map spList
	 *	- String	ZTID	供货商
	 * 	- String	SPXX01	商品id 
	 * 	- String	SPXX02	商品内码		
	 * 	- String	SPXX04	商品名称
	 *	- Date		SPGL03	商品发布时间
	 *	- int		SPGL02	大厅标记(标记是否为大厅商品展示)
	 *  - int		HDLX	商品参与活动类型
	 * 	- int		SPGL08	促销标记
	 * 	- int		SPGL09	热卖标记
	 *	- int		SPGL10	新品标记
	 *	- int		SPGL11	特价标记 
	 *  - int		ZCXX34	排序(商品按公司排序:又高到低)
	 *	- int		SPGL13	排序(某供货商商品展示顺序:由高到低)
	 *	- float		SPJGB02	分销限价(产品的限制价格,商品各种价格不得低于此价格)
	 *  - float		SPJGB05	零售单价(未登录前所有商品的建议零售价格)
	 *  
	 * @note 
	 */
		
	//加载首页商品
	@RequestMapping("/selectFKQGnotlogin.action")
	public Map selectFKQGnotlogin(String XmlData,HttpServletResponse response) throws Exception {
		System.out.println("XmlData="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectSpForNotLogin");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		return map;
	}
	
	/**
	 * 登录后,各种商品展示时调用
	 * @param	String XmlData
	 * 	- int		SPGL08	促销标记
	 * 	- int		SPGL09	热卖标记
	 *	- int		SPGL10	新品标记
	 *	- int		SPGL11	特价标记
	 *	- String	QYDM	区域代码
	 *	- String	HBID	客户ID
	 * 	- int		SPGL02	大厅标记(标记是否为大厅商品展示)
	 * @return	Map spList
	 *	- String	ZTID	供货商
	 * 	- String	SPXX01	商品id 
	 * 	- String	SPXX02	商品内码		
	 * 	- String	SPXX04	商品名称
	 *	- Date		SPGL03	商品发布时间
	 *	- int		SPGL02	大厅标记(标记是否为大厅商品展示)
	 *  - int		HDLX	商品参与活动类型
	 * 	- int		SPGL08	促销标记
	 * 	- int		SPGL09	热卖标记
	 *	- int		SPGL10	新品标记
	 *	- int		SPGL11	特价标记 
	 *  - int		ZCXX34	排序(商品按公司排序:又高到低)
	 *	- int		SPGL13	排序(某供货商商品展示顺序:由高到低)
	 *	- float		SPJGB01	分销单价 (登陆后代理商品/普通商品价格)
	 *	- float		SPJGB02	分销限价(产品的限制价格,商品各种价格不得低于此价格)
	 *  - float		SPJGB05	零售单价(未登录前所有商品的建议零售价格)
	 *  - float		SPGL04	发布价格(登陆后的会员/大厅商品价格)
	 *  - float		CXJG	促销价格(所有促销活动的价格)
	 *  
	 * @note
	 */

	//疯狂抢购
	@RequestMapping("/selectFKQGlogin.action")
	public Map selectFKQGlogin(String XmlData,HttpServletResponse response) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);	
		map.put("sqlid", "com.jlsoft.o2o.product.selectSpForUserLogin");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		return map;
	}
	
	/**
	 * 登录前,全局查询商品
	 * @param	String XmlData
	 *	- String	MHCX	模糊查询名称(商品ID,商品名称,商品分类名称,商品品牌名称,商品属性名称)
	 * @return	Map spList
	 *	- String	ZTID	供货商
	 * 	- String	SPXX01	商品id 
	 * 	- String	SPXX02	商品内码		
	 * 	- String	SPXX04	商品名称
	 *	- Date		SPGL03	商品发布时间
	 *	- int		SPGL02	大厅标记(标记是否为大厅商品展示)
	 *  - int		HDLX	商品参与活动类型
	 * 	- int		SPGL08	促销标记
	 * 	- int		SPGL09	热卖标记
	 *	- int		SPGL10	新品标记
	 *	- int		SPGL11	特价标记 
	 *  - int		ZCXX34	排序(商品按公司排序:又高到低)
	 *	- int		SPGL13	排序(某供货商商品展示顺序:由高到低)
	 *	- float		SPJGB02	分销限价(产品的限制价格,商品各种价格不得低于此价格)
	 *  - float		SPJGB05	零售单价(未登录前所有商品的建议零售价格)
	 *  
	 * @note
	 */
	@RequestMapping("/global_searchnotlogin.action")
	public Map global_searchnotlogin(String XmlData,HttpServletResponse response) throws IOException {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);	
		map.put("sqlid", "com.jlsoft.o2o.product.NotLogin_global_serach");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("pagesize", "8");
		map.put("dataType", "Json");
		return map;
	}
	
	/**
	 * 登录后,全局查询商品
	 * @param	String XmlData
	 *	- String	QYDM	区域代码
	 *	- String	HBID	客户ID
	 *	- String	MHCX	模糊查询名称(商品ID,商品名称,商品分类名称,商品品牌名称,商品属性名称)
	 * 
	 * @return	Map spList
	 *	- String	ZTID	供货商
	 * 	- String	SPXX01	商品id 
	 * 	- String	SPXX02	商品内码		
	 * 	- String	SPXX04	商品名称
	 *	- Date		SPGL03	商品发布时间
	 *	- int		SPGL02	大厅标记(标记是否为大厅商品展示)
	 *  - int		HDLX	商品参与活动类型
	 * 	- int		SPGL08	促销标记
	 * 	- int		SPGL09	热卖标记
	 *	- int		SPGL10	新品标记
	 *	- int		SPGL11	特价标记 
	 *  - int		ZCXX34	排序(商品按公司排序:又高到低)
	 *	- int		SPGL13	排序(某供货商商品展示顺序:由高到低)
	 *	- float		SPJGB01	分销单价 (登陆后代理商品/普通商品价格)
	 *	- float		SPJGB02	分销限价(产品的限制价格,商品各种价格不得低于此价格)
	 *  - float		SPJGB05	零售单价(未登录前所有商品的建议零售价格)
	 *  - float		SPGL04	发布价格(登陆后的会员/大厅商品价格)
	 *  - float		CXJG	促销价格(所有促销活动的价格)
	 *  
	 * @note
	 */
	@RequestMapping("/global_searchlogin.action")
    public Map global_searchlogin(String XmlData,HttpServletResponse response) throws IOException{
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);	
		map.put("sqlid", "com.jlsoft.o2o.product.Login_global_serach");
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("pagesize", "8");
		map.put("dataType", "Json");
		return map;
	}
	
	
	//登入前大厅商品筛选
	@RequestMapping("/DTSPnotlogin.action")
	public Map DTSPnotlogin(String XmlData,HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.NotLogin_global_serach");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		return map;
	}
	
	
	//登入后大厅商品筛选
	@RequestMapping("/DTSPlogin.action")
	public Map DTSPlogin(String XmlData,HttpServletResponse response) throws Exception {
		System.out.println(XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.Login_global_serach");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		return map;
	}
	
	//商城推荐
	@RequestMapping("/SPXX_recommend.action")
	public Map SPXX_recommend(String XmlData,HttpServletResponse response) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectW_SPXX-recommend");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("pagesize", "8");
		map.put("dataType", "Json");
		System.out.println("map=="+map);
		return map;
	}
	//最佳组合
	@RequestMapping("/selelct_bestGoods.action")
	public Map selelct_bestGoods(String XmlData,HttpServletResponse response) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectW_SPXX-bestGoods");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("pagesize", "8");
		map.put("dataType", "Json");
		System.out.println("map=="+map);
		return map;
	}
	
	//登入前商品详情查询
	@RequestMapping("/SPXQnotlogin.action")
	public Map SPXQnotlogin(String XmlData,HttpServletResponse response) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectW_SPXX-SPXXItemNotLogin");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("pagesize", "8");
		map.put("dataType", "Json");
		System.out.println("map=="+map);
		return map;
	}
	
	//登入前商品详情查询
		@RequestMapping("/SXSPXXQnotlogin.action")
		public Map SXSPXXQnotlogin(String XmlData,HttpServletResponse response) throws Exception {
			System.out.println("XmlData=="+XmlData);
			ObjectMapper mapper = new ObjectMapper();
			List list =  mapper.readValue(XmlData, ArrayList.class); 
			Map map = (Map)list.get(0);		
			map.put("sqlid", "com.jlsoft.o2o.product.selectW_SXSPXX-SPXXItemNotLogin");		
			session = JlAppSqlConfig.getO2OSqlMapInstance();
			map.put("session", session);
			map.put("QryType", "Fore");
			map.put("pagesize", "8");
			map.put("dataType", "Json");
			System.out.println("map=="+map);
			return map;
		}
	
	//登入后商品详情查询
	@RequestMapping("/SPXQlogin.action")
	public Map SPXQlogin(String XmlData,HttpServletResponse response) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectW_SPXX-SPXXItemLogin");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("pagesize", "8");
		map.put("dataType", "Json");
		System.out.println("map=="+map);
		return map;
	}
	
	//会员评价
	@RequestMapping("/selectKHPJ.action")
	public Map selectKHPJ(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectKHPJ");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		System.out.println("map=="+map);
		return map;
	}
	
	//会员评价总数 
	@RequestMapping("/selectKHPJsum.action")
	public Map selectKHPJsum(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		ObjectMapper mapper = new ObjectMapper();
		List list =  mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);		
		map.put("sqlid", "com.jlsoft.o2o.product.selectKHPJsum");		
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		map.put("session", session);
		map.put("QryType", "Fore");
		map.put("dataType", "Json");
		System.out.println("map=="+map);
		return map;
	}
	
}