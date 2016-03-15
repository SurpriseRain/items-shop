package com.jlsoft.o2o.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

/**
 * 商品库存查询等功能 2015-12-15 上午10:38:57
 * 
 * @author Uktar
 * 
 */
@Service("productQueryService")
@RequestMapping("/productQueryService")
public class ProductQueryService extends JLBill {

	/**
	 * 查询库存
	 * 
	 * @param XmlData
	 * @return 2015-12-15 上午11:19:40
	 */
	@RequestMapping("/queryStockNum.action")
	public Map<String, Object> queryStockNum(String XmlData) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (XmlData == null) {
			map.put("STATE", "0");
			return map;
		}

		cds = new DataSet(XmlData);
		// 主体ID
		String ztid = cds.getField("ZCXX01", 0);
		// 区域id
		String dqxx01 = cds.getField("DQXX01", 0);
		// 商品id
		String spxx01 = cds.getField("SPXX01", 0);
		// 品牌id
		String ppb01 = cds.getField("PPB01", 0);

		// 检查参数
		if (ztid == null || dqxx01 == null || spxx01 == null || ppb01 == null) {
			map.put("STATE", "0");
			return map;
		}

		// 检查品牌是否入驻,判断主体id
		List brandList = this.queryEnteringBrand(XmlData);
		if (brandList != null && brandList.size() > 0) {
			// 如果已经入驻，获取主体ID
			ztid = queryOrderZtid(XmlData);
		}

		// 查询库存信息
		map = queryStockNumByParam(ztid, dqxx01, spxx01);
		map.put("STATE", "1");
		return map;
	}




	/**
	 * 根据主体ID及品牌ID查询品牌是否入驻
	 * 
	 * @param XmlData
	 * @return 2015-12-15 上午10:45:59
	 */
	@SuppressWarnings("unchecked")
	public List queryEnteringBrand(String XmlData)
			throws Exception {
		// 如果只是库存查询使用，下边的参数检查可以去掉
		List list   = new ArrayList();
		if (XmlData == null) {
			return list;
		}

		cds = new DataSet(XmlData);
		// 主体ID
		String ztid = cds.getField("ZCXX01", 0);
		// 品牌id
		String ppb01 = cds.getField("PPB01", 0);
		// 商品ID
		String spxx01 = cds.getField("SPXX01", 0);

		// 检查参数
		if (ztid == null || spxx01 == null) {
			return list;
		}
		// 如果没有传入品牌，根据商品信息查询品牌
		if (ppb01 == null || "".equals(ppb01)) {
			ppb01 = queryBrandBySpxx(spxx01);
		}

		// 查询品牌是否入驻
		String querySql = "select a.ZTID, a.HBID, a.PPB01 from w_gsgxpp a LEFT JOIN w_gsgx b on a.ZTID=b.ztid and a.HBID = b.hbid where a.ZTID ='"
				+ ztid + "' and a.PPB01='" + ppb01 + "' and b.State=1 and b.EndTime > now()";
		list = queryForList(o2o, querySql, null);

		return list;
	}




	/**
	 * 确定订单的主体ID
	 * 
	 * @param XmlData
	 * @return 2015-12-15 上午11:28:40
	 */
	public String queryOrderZtid(String XmlData) throws Exception {

		// 如果只是库存查询使用，下边的参数检查可以去掉
		String ztid = "";
		if (XmlData == null) {
			return ztid;
		}

		cds = new DataSet(XmlData);
		// 主体ID
		ztid = cds.getField("ZCXX01", 0);
		// 区域id
		String dqxx01 = cds.getField("DQXX01", 0);
		//商品ID
		String spxx01 = cds.getField("SPXX01", 0);
		// 检查参数
		if (ztid == null || dqxx01 == null || spxx01 ==null) {
			return ztid;
		}

		// 确定订单的主体ID
		ztid = queryOrderZtidByParam(ztid, dqxx01, spxx01);

		return ztid;
	}




	/**
	 * 查询库存
	 * 
	 * @param ztid 主体id
	 * @param dqxx01 区域id
	 * @param spxx01 商品id
	 * @return
	 * @throws Exception 2015-12-15 下午1:29:46
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryStockNumByParam(String ztid,
			String dqxx01, String spxx01) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		

		//根据主体id和区域获得对应的仓库id
		String ckId = getCkId(ztid, dqxx01);
		//查询数量
		String querySql = "SELECT IFNULL(sum( K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02 ),0)  number" 
				+ " FROM W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 "
				+ " WHERE ck.ck09 = '0' AND K.SPXX01='"+ spxx01 +"' AND K.ZCXX01='"+ ztid + "' AND "
				+ " K.ck01='"+ ckId +"'";
		map = queryForMap(o2o, querySql);
		
		//查询商品spxx04
		String querySpxxSql = "SELECT SPXX04 FROM W_SPXX WHERE SPXX01='"+ spxx01 +"'";
		Map<String, Object> spxxMap = queryForMap(o2o, querySpxxSql);
		if(spxxMap != null && spxxMap.get("SPXX04")!=null){
			map.put("SPXX04", spxxMap.get("SPXX04"));
		}
		return map;
	}




	/**
	 * 获取商品的品牌
	 * 
	 * @param spxx01 商品ID
	 * @return 2015-12-16 上午10:56:22
	 */
	@SuppressWarnings("unchecked")
	private String queryBrandBySpxx(String spxx01) {
		String brand = "";
		if (spxx01 == null) {
			return brand;
		}
		String querySql = "select ppb01 from w_spxx where spxx01= '" + spxx01
				+ "'";
		Map<String, Object> map = queryForMap(o2o, querySql);
		if (map != null && map.size() > 0) {
			Object obj = map.get("ppb01");
			if (obj != null) {
				brand = obj.toString();
			}
		}
		return brand;
	}




	/**
	 * 根据主体ID和区域ID确认订单主体ID
	 * 
	 * @param ztid 主体ID
	 * @param dqxx01 区域ID
	 * @return 2015-12-17 下午2:33:31
	 */
	@SuppressWarnings("unchecked")
	public String queryOrderZtidByParam(String ztid, String dqxx01, String spxx01) {
		
		if(ztid == null || ztid.length() ==0 || dqxx01 == null || dqxx01.length() ==0 || spxx01==null || spxx01.length()==0){
			return ztid;
		}
		//区域代码
		String areaCode = this.queryAreaCode(dqxx01);
		//品牌id
		String ppId = queryBrandBySpxx(spxx01);
		// 根据主体ID和区域ID能查询出伙伴id,如果没有记录，主体为生产企业
		String querySql = "SELECT a.HBID FROM w_gsgxqy a LEFT JOIN w_gsgx b ON a.ZTID=b.ztid AND a.HBID = b.hbid  LEFT JOIN w_gsgxpp pp on a.ZTID= pp.ZTID AND a.HBID=pp.HBID  WHERE a.ztid='" + ztid
				+ "' AND b.State=1 AND pp.PPB01='"+ ppId + "' AND b.EndTime > now()";
		// 加入市级及省级查询条件
		if (areaCode.length() == 0) {
			querySql = querySql + " AND a.DQBZM01='" + dqxx01 + "'";
		}
		else {
			querySql = querySql + " AND (a.DQBZM01='" + dqxx01 + "' or a.DQBZM01='"
					+ areaCode + "')";
		}

		Map<String, Object> map = queryForMap(o2o, querySql);
		if (map != null && map.size() > 0) {
			Object obj = map.get("HBID");
			if (obj != null) {
				ztid = obj.toString();
			}
		}
		return ztid;
	}




	/**
	 * 查询上级区域代码
	 * 
	 * @param dqxx01 上级区域代码
	 * @return 2015-12-17 下午2:30:21
	 */
	@SuppressWarnings("unchecked")
	private String queryAreaCode(String dqxx01) {
		// 查询上级区域代码
		String areaCode = "";
		String queryAreaSql = "select dqbzm_dqbzm01 from dqbzm where dqbzm01='"
				+ dqxx01 + "'";

		Map<String, Object> areaMap = queryForMap(o2o, queryAreaSql);
		if (areaMap != null && areaMap.size() > 0) {
			Object obj = areaMap.get("dqbzm_dqbzm01");
			if (obj != null) {
				areaCode = obj.toString();
			}
		}
		return areaCode;
	}
	
	/**
	 * 提供主体ID的查询
	 * @param XmlData
	 * @return
	 * @throws Exception
	 * 2015-12-31 下午3:55:09
	 */
	@RequestMapping("/queryOrderZtid.action")
	public Map<String, Object> queryOrderZtidForMap(String XmlData) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ZTID", this.queryOrderZtid(XmlData));
		return map;
	}
	
	
	/**
	 * 根据ztid,dqxx01 查询对应的仓库
	 * @param ztid
	 * @param dqxx01
	 * @return
	 * 2016-1-7 下午5:36:47
	 */
	public String getCkId(String ztid, String dqxx01) {
		// 查询公司类型
		String queryZcgsSql = "select w_zcgs.zcgs03 from w_zcgs where w_zcgs.zcxx01="
				+ ztid;
		int zcgs03 = queryForInt(o2o, queryZcgsSql);

		String ckid = "";
		if (zcgs03 == 42) {
			// 查询经销企业仓库ID
			String queryCkSql = "SELECT ck01 from ck where gsxx01=" + ztid;
			List list = queryForList(o2o, queryCkSql);
			if (list == null || list.size() == 0) {
				return ckid;
			}
			Object obj = list.get(0);
			if (obj == null) {
				return ckid;
			}
			Map map = (Map)obj;
			if(map.get("ck01")==null){
				return ckid;
			}
			ckid = map.get("ck01").toString();
		}
		if (zcgs03 == 43) {
			// 生产企业
			// 查询对应的仓库
			String queryCkSql = "SELECT a.ck01,c.type FROM w_dqck a LEFT JOIN w_gsck b ON a.ck01 = b.CK01 LEFT JOIN ck c ON b.CK01 = c.ck01 WHERE a.dqxx01='"
					+ dqxx01 + "' AND b.zcxx01='"	+ ztid	+ "' AND a.status = 0 AND c.ck09 = '0'";
			List list = queryForList(o2o, queryCkSql);
			if (list == null || list.size() == 0) {
				return ckid;
			}
			String axCkId = ""; //安迅
			String ownCkId = "";//自有虚拟
			for(Object obj : list){
				if(obj == null){
					continue;
				}
				Map map = (Map)obj;
				if(map.get("type")==null ){
					continue;
				}
				Object ckObj = map.get("type");
				if("0".equals(ckObj.toString())){
					//虚拟仓库
					if(map.get("ck01")==null){
						return ownCkId;
					}
					ownCkId = map.get("ck01").toString();
				}
				if("1".equals(ckObj.toString())){
					//实体仓库
					if(map.get("ck01")==null){
						return axCkId;
					}
					axCkId = map.get("ck01").toString();
				}
			}
			ckid = axCkId!=null && axCkId.length() > 0? axCkId:ownCkId;
		}
		return ckid;
	}	

}
