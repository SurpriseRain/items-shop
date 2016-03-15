package com.jlsoft.o2o.pub.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.product.service.ProductQueryService;

/**
 * 
 * @breif 购物车相关操作管理
 * 
 */
@Service("kmsService")
@RequestMapping("/KmsService")
public class KmsService extends JLBill {
	
	@Autowired
	private ProductQueryService productQueryService;
	
	/*
	 * zcxx01 注册公司编码 spxx01 商品内码 ck01 仓库编码 cksp04 可卖数量 cksp05 预售数量 kcxx01
	 * 平台设定预售数 kcxx02 网购数量 cksp12 仓库商品属性 djlx 单据类型 ywdh 业务单号 bqfss 本期发生数 jcsl
	 * 结存数量
	 */
	public Map insertGwcSpxx(String zcxx01, double spxx01, String cksp04,
			double cksp05, double kcxx01, double kcxx02, String ck01,
			String cksp12, int djlx, String ywdh, int bqfss, int jcsl) throws Exception{
			Map resultMap = new HashMap();
			String queryExistsSql = "select count(1) from w_kcxx where  ZCXX01='"
					+ zcxx01 + "' and  CK01='" + ck01 + "' and SPXX01="
					+ spxx01;
			int count = queryForInt(o2o, queryExistsSql);
			String updateSql = "update w_kcxx set cksp04=(cksp04+(" + bqfss
					+ ")) where ZCXX01='" + zcxx01 + "' and  CK01='" + ck01
					+ "' and SPXX01=" + spxx01;
			String insertKcxxSql = "INSERT INTO w_kcxx VALUES ('" + zcxx01
					+ "', " + spxx01 + ", " + bqfss + ", " + cksp05 + ", "
					+ kcxx01 + ", " + kcxx02 + ", '" + ck01 + "', " + cksp12
					+ ");";
			String insertKmsSql = "INSERT INTO w_ckspkms VALUES ('" + zcxx01
					+ "', '" + ck01 + "', " + spxx01 + ", now(), " + djlx
					+ ", '" + ywdh + "', " + bqfss
					+ ", (select cksp04 from w_kcxx where zcxx01='" + zcxx01
					+ "' and spxx01=" + spxx01 + " and ck01='" + ck01
					+ "' ), now());";
			
			Map row;
			if (count > 0) {
				row  = new HashMap();
				execSQL(o2o, updateSql, row);
			} else {
				row  = new HashMap();
				execSQL(o2o, insertKcxxSql, row);
			}
			row  = new HashMap();
			execSQL(o2o, insertKmsSql, row);

		return resultMap;
	}
	
	/*
	 * 根据ZCXX01和SPXX01查询库存
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/CKXX_COUNT.action")
	public Map CKXX_COUNT(String XmlData) throws Exception {
		Map map=new HashMap();
		cds = new DataSet(XmlData);
		try{
			//modify 修改库存查询
			//String sql="SELECT (SELECT SPXX04 FROM W_SPXX WHERE SPXX01='"+cds.getField("SPXX01", 0)+"') SPXX04,ifnull( sum( K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02 ), 0 ) number FROM W_KCXX K LEFT JOIN CK ON ck.ck01 = K.ck01 WHERE ck.ck09= '0' and SPXX01='"+cds.getField("SPXX01", 0)+"' AND ZCXX01='"+cds.getField("ZCXX01", 0)+"' and ck.ck01 IN (select ck01 from w_dqck where dqxx01='"+cds.getField("DQXX01", 0)+"')";
			//map = queryForMap(o2o, sql);
			map = productQueryService.queryStockNum(XmlData);
			map.put("STATE", "1");
		}catch(Exception e){
			map.put("STATE", "0");
	    	e.printStackTrace();
	    	throw e;
		}
		return map;
	}
}
