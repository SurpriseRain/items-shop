package com.jlsoft.o2o.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.order.service.CartManage;
import com.jlsoft.utils.DownLoad;
import com.jlsoft.utils.JlAppResources;
@Controller
@RequestMapping("/ProductCart")
public class ProductCart extends JLBill{
	@Autowired
	private ProductAttachForsrch productItem;
	@Autowired
	private CartManage myCart;
	
	
	
	/*****
	 * 查询商品属性类型列表
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/srchSxlx.action")
	public Map<String, String> srchSxlx(String XmlData) throws Exception {
		List datas = null;
		System.out.println("XmlData=="+XmlData);
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
		String sql="select a.SXSP01,a.SXSP02,a.SXSP03,a.SXSP04,(SELECT SXLX03 FROM w_sxlx WHERE SXLX01 = (SELECT SXLX01 FROM w_sxlxz WHERE ZCXX01 = b.ZCXX01 AND SXLXZ01 = b.SPSXZ01)) isfile," +
				"b.SPSXZ01,(SELECT SXLXZ02 FROM w_sxlxz WHERE ZCXX01 = b.ZCXX01 AND SXLXZ01 = b.SPSXZ01) SPSXZ01NAME," +
				"b.SPSXZ02,(SELECT SXLXZ02	FROM w_sxlxz WHERE ZCXX01 = b.ZCXX01 AND SXLXZ01 = b.SPSXZ02) SPSXZ02NAME," +
				"b.SPSXZ03,(SELECT SXLXZ02 FROM w_sxlxz WHERE ZCXX01 = b.ZCXX01 AND SXLXZ01 = b.SPSXZ03) SPSXZ03NAME," +
				"b.SPSXZ04,(SELECT SXLXZ02 FROM w_sxlxz WHERE ZCXX01 = b.ZCXX01 AND SXLXZ01 = b.SPSXZ04) SPSXZ04NAME," +
				"b.SPSXZ05,(SELECT SXLXZ02 FROM w_sxlxz WHERE ZCXX01 = b.ZCXX01 AND SXLXZ01 = b.SPSXZ05) SPSXZ05NAME" +
				" From w_sxsp a,w_spsxz b where a.ZCXX01 = b.ZCXX01 and a.SXSP01 = b.SXSP01 and a.zcxx01='"+hm.get("ZCXX01")+"' and a.sxsp01='"+hm.get("SXSP01")+"'and b.SPXX01='"+hm.get("SPXX01")+"'";
		Map sxsp = queryForMap(o2o, sql);
		String sql1="select a.ZCXX01,a.SXSP01,a.SXLX01,a.SPSXXH01,a.SPSXXH02,a.SPSXXH03,a.SPSXXH04,b.SXSP05 From w_spsxxh a,w_sxsp b where a.ZCXX01=b.ZCXX01 and a.SXSP01=b.SXSP01 and a.zcxx01='"+hm.get("ZCXX01")+"' and a.sxsp01='"+hm.get("SXSP01")+"'"; 
		List list=queryForList(o2o, sql1);
		sxsp.put("sxlb", list);
		return sxsp;
	}
	
	/*****
	 * 查询商品属性类型列表
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/srchSxlxTP.action")
	public Map<String, String> srchSxlxTP(String XmlData) throws Exception {
		List datas = null;
		System.out.println("XmlData=="+XmlData);
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
		String sql="select SXSPTP01 SPTP02 from w_sxsptp where zcxx01='"+hm.get("ZCXX01")+"' and SXSP01='"+hm.get("SXSP01")+"' and SXLXZ01='"+hm.get("SXLXZ01")+"'";
		Map map = queryForMap(o2o, sql);
		return map;
	}
	
	/*****
	 * 查询商品属性代码
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/srchSpxx.action")
	public Map<String, String> srchSpxx(String XmlData) throws Exception {
		List datas = null;
		System.out.println("XmlData=="+XmlData);
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
		String[] sxlxlist = hm.get("SXLXZ").toString().split(",");
		// and SPSXZ01='1' and SPSXZ02 ='3'"
		String sql="select SPXX01 from w_spsxz where ZCXX01='"+hm.get("ZCXX01")+"' and SXSP01='"+hm.get("SXSP01")+"'";
		for(int i=1;i<=sxlxlist.length;i++){
			sql+=" and SPSXZ0"+i+"='"+sxlxlist[i-1]+"'";
		}
		Map spxx = queryForMap(o2o, sql);
		return spxx;
	}
	/**
	 * 查询商品属性
	 * @throws Exception 
	 * @Param SPXX01
	 */
	@RequestMapping("/selectGoodsAttribute.action")
	public List<Map<String, Object>> selectGoodsAttribute(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		String spxx01=cds.getField("SPXX01", 0);
		String sql=
			"SELECT A.SPFL, A.SXBH, A.SXNAME, B.SXZDM, B.SXZNAME\n" +
			"  FROM W_SPFLSX A, W_SPFLSXITEM B\n" + 
			" WHERE A.SPFL = B.SPFL\n" + 
			"   AND A.SXBH = B.SXBH\n" + 
			"   AND A.SPFL =\n" + 
			"       (SELECT X.SPFL02_CODE FROM W_SPXX X WHERE X.SPXX01 = "+spxx01+")\n" + 
			"   AND B.SXZDM in (SELECT G.SXZDM FROM W_SPSX G WHERE G.SPXX01 = "+spxx01+")";
		List<Map<String, Object>> mapList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return mapList;
	}
}
	
