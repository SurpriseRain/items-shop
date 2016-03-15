package com.jlsoft.o2o.solr; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

@Controller
@RequestMapping("/SearchHandler")
public class SearchHandler extends JLBill {
	
	// 获取服务
	private static SolrServer server;
	static {
		String SOLR_URL = JlAppResources.getProperty("SOLR_URL");
		server  = new HttpSolrServer(SOLR_URL);
	}

	/**
	 * 全量增加索引
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchAllIndex.action")
	public Map searchAllIndex() throws Exception{
		Map rMap = new HashMap();
		try {
			List ind = new ArrayList();
			String sql = "";
			sql = "SELECT DISTINCT "
				+ "GROUP_CONCAT(DISTINCT SH.SPGLLJH01 ORDER BY SH.SPGLLJH01) ZJCLJH, "
				+ "P.SPXX01 SPBM, "
				+ "P.SPXX02 SPNM, "
				+ "WG.BARCODE BARCODE, "
				+ "P.GGXH GGXH, "
				+ "P.SPXX04 SPMC, "
				+ "P.PPB01 PPPP, "
				+ "P.PPB02 , "
				+ "P.SPFL01 PPFL, "
				+ "(SELECT IFNULL(GROUP_CONCAT(CAST(MOBILE_MODLE_ID AS CHAR)),'通配件') FROM W_SPCX WHERE W_SPCX.SPXX01 = P.SPXX01) MMID,"
				+ "J.SPJGB01 FXDJ, "
				+ "J.SPJGB02 FXXJ, "
				+ "G.SPGL04 FBJG, "
				+ "IFNULL(J.SPJGB05, 0) LSJG, "
				+ "G.ZCXX01 GSBM, "
				+ "G.SPGL13 SPSDPXBJ, "
				+ "G.SPGL03 SPFBSJ, "
				+ "G.SPGL02 DTBJ, "
				+ "G.SPGL08 CXBJ, "
				+ "A.ZCXX02 GSMC, "
				+ "G.SPGL06 KSCJFBJ, "
				+ "G.SPGL14 SPQDSL, "
				+ "G.SPGL15 SPXDSL, "
				+ "P.SPXX08 XXSL, "
				+ "G.SPGL18 SJSJ, "
				+ "G.CKSP12 SPSX, "
				+ "0 CXJG, "
				+ "0 YHJG, "
				+ "0 CXLX, "
				+ "SUBSTRING_INDEX(S.SPTP02, '.' ,- 1) TPMC, "
				// 添加图片顺序号 20151123 齐俊宇 BEGIN
				+ "S.SPTP01 SPTP01, "
				// 添加图片顺序号 20151123 齐俊宇 END
				+ "SPGL25 SYCX, "
//				+ "(SELECT IFNULL(SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) "
//				+ "FROM W_KCXX K "
//				+ "LEFT JOIN CK ON CK.CK01 = K.CK01 WHERE CK.CK09 = '0' AND K.SPXX01 = P.SPXX01 "
//				+ "AND K.ZCXX01 = G.ZCXX01 "
//				+ "AND K.CK01 IN (SELECT CK01 FROM W_DQCK)) SPIMGURL, "
//				+ "(SELECT IFNULL(SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) "
//				+ "FROM W_KCXX K "
//				+ "LEFT JOIN CK ON CK.CK01 = K.CK01 "
//				+ "WHERE "
//				+ "CK.CK09 = '0' "
//				+ "AND K.SPXX01 = P.SPXX01 "
//				+ "AND K.ZCXX01 = G.ZCXX01 "
//				+ "AND K.CK01 IN (SELECT CK01 FROM W_DQCK)) SPSL "
				+ "fn_getStockNum(G.ZCXX01,'120100',G.SPXX01) SPIMGURL, "
				+ "fn_getStockNum(G.ZCXX01,'120100',G.SPXX01) SPSL "
				+ "FROM W_SPGL G "
				+ "INNER JOIN W_SPJGB J ON G.SPXX01 = J.SPXX01 "
				+ "AND G.ZCXX01 = J.ZCXX01 "
				+ "INNER JOIN W_SPXX P ON G.SPXX01 = P.SPXX01 "
				+ "INNER JOIN W_ZCXX A ON G.ZCXX01 = A.ZCXX01 "
				+ "LEFT JOIN W_SPGLLJH SH ON G.ZCXX01 = SH.ZCXX01 "
				// 20151123 齐俊宇 修改获取图片顺序号 BEGIN
				+ "LEFT JOIN (SELECT SPTP01,SPTP02,ZCXX01,SPXX01 FROM W_SPTP GROUP BY SPXX01) S "
				+ "ON G.ZCXX01 = S.ZCXX01 "
				+ "AND S.SPXX01 = G.SPXX01 "
				// 20151123 齐俊宇 修改获取图片顺序号 BEGIN
				+ "INNER JOIN W_GOODS WG ON WG.SPXX01 = P.SPXX01 "
				+ "INNER JOIN SPFL ON SPFL.SPFL01 = P.SPFL01 "
				+ "WHERE 1=1 "
				+ "AND G.SPGL12 IN ('1', '3') "
				+ "AND SH.SPXX01 = G.SPXX01 "
				+ "AND IFNULL(J.SPJGB05, 0) >= CAST('0' AS DECIMAL(10,4)) "
				+ "AND IFNULL(J.SPJGB05, 0) <= CAST('9999999' AS DECIMAL(10,4)) "
				+ "GROUP BY G.SPXX01 "
				+ "ORDER BY P.SPXX04,SPFL.SPFL02 ASC";
			ind = queryForList(o2o, sql);
			System.out.println(ind.size());
			// 先删除全部索引
			server.deleteByQuery("*:*");
			server.commit();
			// 在重新插入
			SolrInputDocument doc = new SolrInputDocument();
			for (int i = 0; i < ind.size(); i++) {
				Map map = (Map) ind.get(i);
				doc.setField("ZJCLJH", map.get("ZJCLJH"));
	    		doc.setField("SPBM", map.get("SPBM"));
	    		doc.setField("SPNM", map.get("SPNM"));
	    		doc.setField("BARCODE", map.get("BARCODE"));
	    		doc.setField("PPFL", map.get("PPFL"));
	    		doc.setField("PPPP", map.get("PPPP"));
	    		doc.setField("MMID", map.get("MMID"));
	    		doc.setField("GGXH", map.get("GGXH"));
	    		doc.setField("SPMC", map.get("SPMC"));
	    		doc.setField("LSJG", map.get("LSJG"));
	    		doc.setField("GSBM", map.get("GSBM"));
	    		doc.setField("SPSDPXBJ", map.get("SPSDPXBJ"));
	    		doc.setField("SPFBSJ", map.get("SPFBSJ"));
	    		doc.setField("DTBJ", map.get("DTBJ"));
	    		doc.setField("CXBJ", map.get("CXBJ"));
	    		doc.setField("GSMC", map.get("GSMC"));
	    		doc.setField("KSCJFBJ", map.get("KSCJFBJ"));
	    		doc.setField("SPQDSL", map.get("SPQDSL"));
	    		doc.setField("SPXDSL", map.get("SPXDSL"));
	    		doc.setField("XXSL", map.get("XXSL"));
	    		doc.setField("SJSJ", map.get("SJSJ"));
	    		doc.setField("SPSX", map.get("SPSX"));
	    		doc.setField("CXJG", map.get("CXJG"));
	    		doc.setField("YHJG", map.get("YHJG"));
	    		doc.setField("CXLX", map.get("CXLX"));
	    		doc.setField("TPMC", map.get("TPMC"));
	    		doc.setField("SPTP01", map.get("SPTP01"));
	    		doc.setField("SYCX", map.get("SYCX"));
	    		doc.setField("SPIMGURL", map.get("SPIMGURL"));
	    		doc.setField("SPSL", map.get("SPSL"));
	    		server.add(doc);
	    		server.commit();
			}
			rMap.put("STATE", "1");
		} catch (Exception e) {
			rMap.put("STATE", "0");
			throw e;
		}
		return rMap;
	}
	
	/**
	 * 增量索引,增加、修改和上架商品的时候使用
	 * @param sqid
	 * @throws Exception
	 */
	@RequestMapping("/searchAddNewIndex.action")
	public void searchAddNewIndex(HttpServletRequest request) throws Exception{
		try {
			Integer spid = Integer.parseInt(request.getParameter("spid"));
			String sql = "";
			sql = "SELECT DISTINCT "
				+ "GROUP_CONCAT(DISTINCT SH.SPGLLJH01 ORDER BY SH.SPGLLJH01) ZJCLJH, "
				+ "P.SPXX01 SPBM, "
				+ "P.SPXX02 SPNM, "
				+ "WG.BARCODE BARCODE, "
				+ "P.GGXH GGXH, "
				+ "P.SPXX04 SPMC, "
				+ "P.PPB01 PPPP, "
				+ "P.PPB02 , "
				+ "P.SPFL01 PPFL, "
				+ "(SELECT IFNULL(GROUP_CONCAT(CAST(MOBILE_MODLE_ID AS CHAR)),'通配件') FROM W_SPCX WHERE W_SPCX.SPXX01 = G.SPXX01) MMID,"
				+ "J.SPJGB01 FXDJ, "
				+ "J.SPJGB02 FXXJ, "
				+ "G.SPGL04 FBJG, "
				+ "IFNULL(J.SPJGB05, 0) LSJG, "
				+ "G.ZCXX01 GSBM, "
				+ "G.SPGL13 SPSDPXBJ, "
				+ "G.SPGL03 SPFBSJ, "
				+ "G.SPGL02 DTBJ, "
				+ "G.SPGL08 CXBJ, "
				+ "A.ZCXX02 GSMC, "
				+ "G.SPGL06 KSCJFBJ, "
				+ "G.SPGL14 SPQDSL, "
				+ "G.SPGL15 SPXDSL, "
				+ "P.SPXX08 XXSL, "
				+ "G.SPGL18 SJSJ, "
				+ "G.CKSP12 SPSX, "
				+ "0 CXJG, "
				+ "0 YHJG, "
				+ "0 CXLX, "
				+ "SUBSTRING_INDEX(S.SPTP02, '.' ,- 1) TPMC, "
				// 添加图片顺序号 20151123 齐俊宇 BEGIN
				+ "S.SPTP01 SPTP01, "
				// 添加图片顺序号 20151123 齐俊宇 END
				+ "SPGL25 SYCX, "
				+ "(SELECT IFNULL(SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) "
				+ "FROM W_KCXX K "
				+ "LEFT JOIN CK ON CK.CK01 = K.CK01 WHERE CK.CK09 = '0' AND K.SPXX01 = P.SPXX01 "
				+ "AND K.ZCXX01 = G.ZCXX01 "
				+ "AND K.CK01 IN (SELECT CK01 FROM W_DQCK)) SPIMGURL, "
				+ "(SELECT IFNULL(SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) "
				+ "FROM W_KCXX K "
				+ "LEFT JOIN CK ON CK.CK01 = K.CK01 "
				+ "WHERE "
				+ "CK.CK09 = '0' "
				+ "AND K.SPXX01 = P.SPXX01 "
				+ "AND K.ZCXX01 = G.ZCXX01 "
				+ "AND K.CK01 IN (SELECT CK01 FROM W_DQCK)) SPSL "
				+ "FROM W_SPGL G "
				+ "INNER JOIN W_SPJGB J ON G.SPXX01 = J.SPXX01 "
				+ "AND G.ZCXX01 = J.ZCXX01 "
				+ "INNER JOIN W_SPXX P ON G.SPXX01 = P.SPXX01 "
				+ "INNER JOIN W_ZCXX A ON G.ZCXX01 = A.ZCXX01 "
				+ "LEFT JOIN W_SPGLLJH SH ON G.ZCXX01 = SH.ZCXX01 "
				// 20151123 齐俊宇 修改获取图片顺序号 BEGIN
				+ "LEFT JOIN (SELECT SPTP01,SPTP02,ZCXX01,SPXX01 FROM W_SPTP GROUP BY SPXX01) S "
				+ "ON G.ZCXX01 = S.ZCXX01 "
				+ "AND S.SPXX01 = G.SPXX01 "
				// 20151123 齐俊宇 修改获取图片顺序号 BEGIN
				+ "INNER JOIN W_GOODS WG ON WG.SPXX01 = P.SPXX01 "
				+ "INNER JOIN SPFL ON SPFL.SPFL01 = P.SPFL01 "
				+ "WHERE 1=1 "
				+ "AND G.SPXX01 = " 
				+ spid 
				+ " "
				+ "AND G.SPGL12 IN ('1', '3') "
				+ "AND SH.SPXX01 = G.SPXX01 "
				+ "AND IFNULL(J.SPJGB05, 0) >= CAST('0' AS DECIMAL(10,4)) "
				+ "AND IFNULL(J.SPJGB05, 0) <= CAST('9999999' AS DECIMAL(10,4)) "
				+ "GROUP BY G.SPXX01 "
				+ "ORDER BY P.SPXX04,SPFL.SPFL02 ASC";
			Map map = queryForMap(o2o, sql);
			
			// 先删除当前索引
			server.deleteByQuery("SPBM:" + spid);
			server.commit();
			// 在进行插入
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("ZJCLJH", map.get("ZJCLJH"));
    		doc.setField("SPBM", map.get("SPBM"));
    		doc.setField("SPNM", map.get("SPNM"));
    		doc.setField("BARCODE", map.get("BARCODE"));
    		doc.setField("PPFL", map.get("PPFL"));
    		doc.setField("PPPP", map.get("PPPP"));
    		doc.setField("MMID", map.get("MMID"));
    		doc.setField("GGXH", map.get("GGXH"));
    		doc.setField("SPMC", map.get("SPMC"));
    		doc.setField("LSJG", map.get("LSJG"));
    		doc.setField("GSBM", map.get("GSBM"));
    		doc.setField("SPSDPXBJ", map.get("SPSDPXBJ"));
    		doc.setField("SPFBSJ", map.get("SPFBSJ"));
    		doc.setField("DTBJ", map.get("DTBJ"));
    		doc.setField("CXBJ", map.get("CXBJ"));
    		doc.setField("GSMC", map.get("GSMC"));
    		doc.setField("KSCJFBJ", map.get("KSCJFBJ"));
    		doc.setField("SPQDSL", map.get("SPQDSL"));
    		doc.setField("SPXDSL", map.get("SPXDSL"));
    		doc.setField("XXSL", map.get("XXSL"));
    		doc.setField("SJSJ", map.get("SJSJ"));
    		doc.setField("SPSX", map.get("SPSX"));
    		doc.setField("CXJG", map.get("CXJG"));
    		doc.setField("YHJG", map.get("YHJG"));
    		doc.setField("CXLX", map.get("CXLX"));
    		doc.setField("TPMC", map.get("TPMC"));
    		doc.setField("SPTP01", map.get("SPTP01"));
    		doc.setField("SYCX", map.get("SYCX"));
    		doc.setField("SPIMGURL", map.get("SPIMGURL"));
    		doc.setField("SPSL", map.get("SPSL"));
    		server.add(doc);
    		server.commit();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 下架商品的时候使用, 修改索引
	 * @param sqid
	 * @throws Exception
	 */
	@RequestMapping("/searchDeleteIndex.action")
	public void searchDeleteIndex(HttpServletRequest request) throws Exception{
		try {
			Integer spid = Integer.parseInt(request.getParameter("spid"));
			// 删除当前索引
			server.deleteByQuery("SPBM:" + spid);
			server.commit();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 返回结果集
	 * @param jsonData
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchAllData.action")
	public Map searchAllData(String jsonData, HttpServletRequest request) throws Exception {
		Map resultMap = new HashMap();
		try {
			String pageStr = request.getParameter("pages");
			// 创建查询对象
			SolrQuery query = new SolrQuery();
			// 获取返回的条件的值
			String SPMC = "";
			String ZJCLJH = "";
			String BARCODE = "";
			String PRICE = "";
			String SPFL01 = "";
			ObjectMapper mapper = new ObjectMapper();
			Map covMap = new HashMap();
			if(jsonData != null){
				jsonData = JLTools.unescape(jsonData);
				List list = mapper.readValue(jsonData, ArrayList.class);
				covMap = (Map) list.get(0);
				SPMC = (String) covMap.get("SPNAMELIST");// 产品
				ZJCLJH = (String) covMap.get("oe"); // OEM
				BARCODE = (String) covMap.get("barcode"); // 商品条码
				PRICE = (String) covMap.get("jiage");
				SPFL01 = (String) covMap.get("spfl01"); // 商品分类一级
			} else {
				SPMC = request.getParameter("SPXX04"); // 产品
				ZJCLJH = request.getParameter("oe"); // OEM
				BARCODE = request.getParameter("barcode"); // 商品条码
			}
			
			if(ZJCLJH != null && !"".equals(ZJCLJH)){
				ZJCLJH = coventSplit(ZJCLJH);
			}
			if(BARCODE != null && !"".equals(BARCODE)){
				BARCODE = coventSplit(BARCODE);
			}
			
			String type = "";
			String para = "";
			String q = "";
			// 对值进行判断 设置查询条件
			if(!"".equals(SPMC) && !"null".equals(SPMC) && SPMC != null){
				para = "SPMC:(" + SPMC + ")";
				q = "SPXX04";
				type = "SPMC";
			} else if(!"".equals(ZJCLJH) && ZJCLJH != null){
				para = "ZJCLJH:*" + ZJCLJH + "*";
				q = "SPGLLJH01";
				type = "ZJCLJH";
			} else if(!"".equals(BARCODE) && BARCODE != null){
				para = "BARCODE:*" + BARCODE + "*";
				q = "BARCODE";
				type = "BARCODE";
			} else if(!"".equals(SPFL01) && SPFL01 != null){
				para = "PPFL:" + SPFL01 + "*";
			} else {
				para = "*:*";
			}

			// 配件分类
			List flList = new ArrayList();
			flList = (List) covMap.get("flList");
			if(flList != null){
				for (int i = 0; i < flList.size(); i++) {
					if(i == 0){
						para = para + " AND (PPFL:" + flList.get(i);
					} else {
						para = para + " OR PPFL:" + flList.get(i);
					}
				}
				para = para + ")";
			}
			
			// 配件品牌
			List ppList = new ArrayList();
			ppList = (List) covMap.get("ppList");
			if(ppList != null){
				for (int i = 0; i < ppList.size(); i++) {
					if(i == 0){
						para = para + " AND (PPPP:" + ppList.get(i);
					} else {
						para = para + " OR PPPP:" + ppList.get(i);
					}
				}
				para = para + ")";
			}

			// 车型车系
			List cxList = new ArrayList();
			cxList = (List) covMap.get("cxList");
			if(cxList != null){
				String cx = coventStr(cxList);
				if(cx != null && !"".equals(cx)){
					para = para + " AND MMID:" + cx + "通配件";
				}
			}
			
			// 设置查询条件
			query.setQuery(para);
			if(type != null && !"".equals(type)){
				// 高亮设置
				query.setHighlight(true);
				// 这里是传入的参数高亮设置
				query.addHighlightField(type); 
				// 标记,高亮关键字前缀
				query.setHighlightSimplePre("<font color='red'>");
				// 后缀
				query.setHighlightSimplePost("</font>");
			} else if("0".equals(PRICE)) {
				// 排序升序
				query.addSort("SPBM", ORDER.desc);
			}
			
			// 价格排序
			if("5".equals(PRICE)){
				query.addSort("LSJG", ORDER.asc);
			} else if("6".equals(PRICE)){
				query.addSort("LSJG", ORDER.desc);
			} 
			
			if(!"".equals(pageStr) && pageStr != null){
				// 分页 start = (currentPage - 1) * pageSize
				// currentPage:当前页码，从1开始计算
				int page = Integer.parseInt(pageStr) - 1;
				query.setStart(page * 8);
				// rows就相当于pageSize 设置每页显示多少
				query.setRows(8);
			}
			
		    // 执行搜索
			QueryResponse response = server.query(query);
			/****************查询返回文档打印begin***********/
			// 返回结果集
			SolrDocumentList results = response.getResults();
			// 返回总条数
			long numFound = results.getNumFound();
			List resultList = new ArrayList();
			for(SolrDocument doc : results){
				Map map = new HashMap();
				// 主机厂零件号(OEM)
				map.put("SPGLLJH01", doc.get("ZJCLJH"));
				// 商品编码
				map.put("SPXX01", doc.get("SPBM"));
				// 商品内码
				map.put("SPXX02", doc.get("SPNM"));
				// 商品条码
				map.put("BARCODE", doc.get("BARCODE"));
				// 规格型号
				map.put("GGXH", doc.get("GGXH"));
				// 商品名称
				map.put("SPXX04", doc.get("SPMC"));
				// 分销单价
				// map.put("SPJGB01", doc.get("FXDJ"));
				// 分销限价
				// map.put("SPJGB02", doc.get("FXXJ"));
				// 发布价格
				// map.put("SPGL04", doc.get("FBJG"));
				// 零售单价
				// map.put("SPJGB05", doc.get("LSJG"));
				// 公司编码
				map.put("ZCXX01", doc.get("GSBM"));
				// 商品手动排序标记
				map.put("SPGL13", doc.get("SPSDPXBJ"));
				// 商品发布时间
				map.put("SPGL03", doc.get("SPFBSJ"));
				// 大厅标记
				map.put("SPGL02", doc.get("DTBJ"));
				// 促销标记
				map.put("SPGL08", doc.get("CXBJ"));
				// 公司名称
				map.put("ZCXX02", doc.get("GSMC"));
				// 可生成积分标记
				map.put("SPGL06", doc.get("KSCJFBJ"));
				// 商品起订数量
				map.put("SPGL14", doc.get("SPQDSL"));
				// 商品限订数量
				map.put("SPGL15", doc.get("SPXDSL"));
				// 销项税率
				map.put("SPXX08", doc.get("XXSL"));
				// 上架时间
				map.put("SPGL18", doc.get("SJSJ"));
				// CKSP12
				map.put("CKSP12", doc.get("SPSX"));
				// CXJG
				map.put("CXJG", doc.get("CXJG"));
				// YHJG
				map.put("YHJG", doc.get("YHJG"));
				// CXLX
				map.put("CXLX", doc.get("CXLX"));
				// 图片名称
				map.put("SPTP02", doc.get("TPMC"));
				// 图片顺序号
				map.put("SPTP01", doc.get("SPTP01"));
				// 适用车系
				map.put("spgl25", doc.get("SYCX"));
				// 可卖数量 + 预售数量 + 平台设定预售数 - 网购数量
				// map.put("SPIMGURL", doc.get("SPIMGURL"));
				// 商品数量
				// map.put("SPSL", doc.get("SPSL"));
				// 通过索引得到的数据放入list中
				resultList.add(map);
			}
			System.out.println(resultList.size());
			/****************查询返回文档打印end***********/
			
			/****************高亮信息打印begin***********/
			if(!"".equals(type) && numFound > 0){
				// 获取高亮字段
				Map<String,Map<String,List<String>>> hightlists = response.getHighlighting();
				for (int j = 0; j < resultList.size(); j++) {
					Map lMap = (Map) resultList.get(j);
					String spbm = (String) lMap.get("SPXX01");
					Map<String, List<String>> map = hightlists.get(spbm);
					List<String> list = map.get(type);
					lMap.put(q, list.get(0).toString());
				}
				resultMap.put("STATE", "2");
			} else {
				resultMap.put("STATE", "1");
			}
			/****************高亮信息打印end***********/
			resultMap.put("resultList", resultList);
			resultMap.put("numFound", numFound);
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	
	/**
	 * 判断oem或者barcode是否中间有空格进行转义
	 * @param str
	 * @return
	 */
	public String coventSplit(String str){
		String result = "";
		String[] split = str.split(" ");
		for (int i = 0; i < split.length; i++) {
			if(i == 0){
				result = split[i];
			} else {
				result = result + "\\ " + split[i];
			}
		}
		return result;
	}
	
	/**
	 * list转String
	 * @param list
	 * @return
	 */
	public String coventStr(List list){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if(i == 0){
				sb.append(list.get(i));
			} else {
				sb.append(",").append(list.get(i));
			}
		}
		String str = sb.toString();
		System.out.println(str);
		return str;
	}
	
	/**
	 * 查询价格和数量通过数据库进行查询
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectPriceAndNum.action")
	public Map selectPriceAndNum(String jsonData) throws Exception{
		Map resultMap = new HashMap();
		cds = new DataSet(jsonData);
		try {
			// 获取商品编码
			String spbm = cds.getField("SPBM", 0);
			/*String sql = "SELECT DISTINCT "
					+ "J.SPJGB01 FXDJ, "
					+ "J.SPJGB02 FXXJ, "
					+ "G.SPGL04 FBJG, "
					+ "IFNULL(J.SPJGB05, 0) LSJG, "
					+ "(SELECT IFNULL(SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) "
					+ "FROM "
					+ "W_KCXX K "
					+ "LEFT JOIN CK ON CK.CK01 = K.CK01 "
					+ "WHERE "
					+ "CK.CK09 = '0' "
					+ "AND K.SPXX01 = P.SPXX01 "
					+ "AND K.ZCXX01 = G.ZCXX01 "
					+ "AND K.CK01 IN (SELECT CK01 FROM W_DQCK)) SPIMGURL, "
					+ "(SELECT IFNULL(SUM(K.CKSP04 + K.CKSP05 + K.KCXX01 - K.KCXX02),0) "
					+ "FROM "
					+ "W_KCXX K "
					+ "LEFT JOIN CK ON CK.CK01 = K.CK01 "
					+ "WHERE "
					+ "CK.CK09 = '0' "
					+ "AND K.SPXX01 = P.SPXX01 "
					+ "AND K.ZCXX01 = G.ZCXX01 "
					+ "AND K.CK01 IN (SELECT CK01 FROM W_DQCK)) SPSL "
					+ "FROM "
					+ "W_SPGL G "
					+ "INNER JOIN W_SPJGB J ON G.SPXX01 = J.SPXX01 "
					+ "AND G.ZCXX01 = J.ZCXX01 "
					+ "INNER JOIN W_SPXX P ON G.SPXX01 = P.SPXX01 "
					+ "WHERE P.SPXX01 = '" + spbm + "'";*/
			String sql = "SELECT DISTINCT "
					+ "J.SPJGB01 FXDJ, "
					+ "J.SPJGB02 FXXJ, "
					+ "G.SPGL04 FBJG, "
					+ "IFNULL(J.SPJGB05, 0) LSJG, "
					+ "fn_getStockNum(G.ZCXX01,'" + cds.getField("DQXX01", 0) + "',G.SPXX01) SPIMGURL, "
					+ "fn_getStockNum(G.ZCXX01,'" + cds.getField("DQXX01", 0) + "',G.SPXX01) SPSL "
					+ "FROM "
					+ "W_SPGL G "
					+ "INNER JOIN W_SPJGB J ON G.SPXX01 = J.SPXX01 "
					+ "AND G.ZCXX01 = J.ZCXX01 "
					+ "INNER JOIN W_SPXX P ON G.SPXX01 = P.SPXX01 "
					+ "WHERE P.SPXX01 = '" + spbm + "'";
			Map pnMap = queryForMap(o2o, sql);
			resultMap.putAll(pnMap);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	
	/**
	 * 单独高亮
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sorlSearchHighLight.action")
	public Map sorlSearchHighLight(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		jsonData = JLTools.unescape(jsonData);
		cds = new DataSet(jsonData);
		try {
			String val = cds.getField("q", 0);
			String SPMC = cds.getField("SPMC", 0);
			
			SolrQuery query = new SolrQuery();
			String para = "";
			if(val != null && !"".equals(val)){
				para = para + "SPMC:" + val;
				//开启高亮
				query.setHighlight(true);
				query.setQuery(para);
				query.addHighlightField(SPMC);
				query.setHighlightSimplePre("<font color='red'>");
				query.setHighlightSimplePost("</font>");
				//分页 start = (currentPage - 1) * pageSize
				// currentPage:当前页码，从1开始计算
				query.setStart(0);
				// rows就相当于pageSize
				query.setRows(4);
				//FastVectorHightLighter: 不是位置偏移量 基于项向量
				//write type: json/xml,default json
				//query.setParam("wt", "josn");
				//tz: timezone  默认UTC
				//query.setParam("tz", "Asia/Shanghai");
				//排序
				//query.addSort(new SortClause("price", ORDER.desc));
				//query.addSort("id", ORDER.desc);
				QueryResponse response = server.query(query);
				
				List resultList = new ArrayList();
				/****************高亮信息打印start***********/
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				long numFound = response.getResults().getNumFound();
				for(String key : highlighting.keySet()){
					Map map = highlighting.get(key);
					List list = (List) map.get(SPMC);
					resultList.add(map);
				}
				/****************高亮信息打印end***********/
				System.out.println(resultList);
				System.out.println(numFound);
				resultMap.put("resultList", resultList);
				resultMap.put("numFound", numFound);
				resultMap.put("STATE", "1");
			} else {
				resultMap.put("STATE", "0");
			}
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	
	/**
	 * 返回前台的Facet
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/solrFacets.action")
	public Map solrFacets(String jsonData) throws Exception{
		Map resultMap = new HashMap();
		jsonData = JLTools.unescape(jsonData);
		cds = new DataSet(jsonData);
		
		try {
			// String url = "http://10.129.22.205:8099/solr/db";
			// SolrServer server = new HttpSolrServer(url);//获取SolrServer
			SolrQuery query = new SolrQuery();//建立一个新的查询
			String type = cds.getField("type", 0);
			String val = cds.getField("q", 0);
			String para = "";
			if(val == null || "".equals(val)){
				return resultMap;
			}
			if("SPMC".equals(type)){
				para = "SPMC:" + val;
				type = "SPMC_STR";
			} else if("BARCODE".equals(type)){
				para = "BARCODE:*" + val + "*";
			} else if("ZJCLJH".equals(type)){
				para = "ZJCLJH:*" + val + "*";
			} 
			/*********** 设置facet ***********/
			query.setFacet(true);//设置facet=on
			query.addFacetField(type);//设置需要facet的字段
			query.setFacetLimit(5);// 限制facet返回的数量
			query.setFacetMinCount(1);// 限制最小返回数
			query.setQuery(para);
			// 搜索结果
			QueryResponse response = server.query(query);
			// 返回的facet
			FacetField facet = response.getFacetField(type);
			List resultList = new ArrayList();
			// 遍历结果返回给前端页面
		    List<Count> counts = facet.getValues();
		    if (facet != null) {
			     counts = facet.getValues();
			     if (counts.size() > 0) {
			    	 for (Count count : counts) {
					    	Map map = new HashMap();
					    	map.put("label", count.getName());
					    	map.put("NUM", count.getCount());
					    	resultList.add(map);
					        System.out.println(count.getName() + ":" + count.getCount());
					    }
			     }
		    }
		    
		    System.out.println(resultList);
			resultMap.put("resultList", resultList);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}
	
	public static void main(String[] args) {
		String src = "06B 905 431 E";
		String result = "";
		String[] split = src.split(" ");
		for (int i = 0; i < split.length; i++) {
			if(i == 0){
				result = split[i];
			} else {
				result = result + "\\ " + split[i];
			}
		}
		//result = a.substring(0, a.length() - 2);
		System.out.println(result);
	}
}
 