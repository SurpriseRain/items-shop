package com.jlsoft.o2o.interfacepackage.jlinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gome.gop.client.common.AccessToken;
import com.gome.gop.client.common.AccessTokenUtil;
import com.gome.gop.client.common.GOPAPIUtil;
import com.gome.gop.client.common.GOPConfig;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.PropertiesReader;

@Service("GopInterface")
public class GopInterface  extends JLBill{
	@Autowired
	private PubService pubService;
	//private String APP_KEY = PropertiesReader.getInstance().getProperty("APP_KEY");
	//private String APP_SECRET = PropertiesReader.getInstance().getProperty("APP_SECRET");
	private String APP_KEY = "";
	private String APP_SECRET = "";
	private String APP_URL = PropertiesReader.getInstance().getProperty("APP_URL");
	
	/**
	 * @todo 获取订单路由信息
	 * @param map
	 * @return
	 */
	public Map getOrderStatus(String json) throws Exception{
		//获取物流配置信息
		cds = new DataSet(json);
		String xsdd01 = cds.getField("XSDD01", 0);
		Map xsMap = pubService.getXSDD(xsdd01);
		Map wlMap = pubService.getZCGSWL(xsMap.get("ZTID").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		String accessToken = fetchAccessToken();
		String paramXml = getXmlForOrderStatus(json);
		String returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_SHIP_STATUS", paramXml);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", getXmlNodeValue(returnXml,"status"));
		resultMap.put("message", getXmlNodeValue(returnXml,"message"));
		resultMap.put("created", getXmlNodeValue(returnXml,"created"));
		resultMap.put("createdby", getXmlNodeValue(returnXml,"createdby"));
		return resultMap;
	}
	
	/**
	 * @todo 发货运送货物
	 * @param map
	 */
	public Map  transOrder(String xsdd01) throws Exception{
		//获取物流配置信息
		Map xsMap = pubService.getXSDD(xsdd01);
		Map wlMap = pubService.getZCGSWL(xsMap.get("ZTID").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		String accessToken = fetchAccessToken();
		String paramXml = getXmlForTransOrder(xsdd01);
		String returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_TRANS_ORDER", paramXml);
		System.out.println(returnXml);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", getXmlNodeValue(returnXml,"status"));
		resultMap.put("message", getXmlNodeValue(returnXml,"message"));
		return resultMap;
	}
	
	/**
	 * @todo 查询路由获取数据
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String getXmlForOrderStatus(String json) throws Exception{
		cds = new DataSet(json);
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<request><SHIP_STATUS_QUERY_PARAM>");
		//判断订单
		if(cds.getField("XSDD01", 0) != null){
			xml.append("<ORDER_NO>"+cds.getField("XSDD01", 0)+"</ORDER_NO>");
		}
		xml.append("</SHIP_STATUS_QUERY_PARAM></request>");
		return xml.toString();
	}
	
	/**
	 * @todo 发货运行获取参数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String  getXmlForTransOrder(String xsdd01) throws Exception{
		StringBuffer xml = new StringBuffer();
		try{
			Map main = getOrderMain(xsdd01);
			List item = getOrderItem(xsdd01);
			System.out.println("main="+main+"item="+item);
			//根据区域查询对应仓库
			String dqck=getDQCK(main.get("CITY").toString());
			//开始组装XML
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<request><TRANS_ORDER_COMMIT_PARAM><ORDER>");
			xml.append("<ORDER_CODE>"+main.get("XSDD01").toString()+"</ORDER_CODE>");
			xml.append("<COD_AMOUNT>0</COD_AMOUNT>");
			xml.append("<SENDER_NAME>"+main.get("FHR").toString()+"</SENDER_NAME>");
			xml.append("<SENDER_MOBILE>"+main.get("FHRSJHM").toString()+"</SENDER_MOBILE>");
			xml.append("<RECEIVER_NAME>"+main.get("SHR").toString()+"</RECEIVER_NAME>");
			xml.append("<RECEIVER_MOBILE>"+main.get("SHRSJHM").toString()+"</RECEIVER_MOBILE>");
			xml.append("<RECEIVER_PROVINCE>"+main.get("PROVINCENAME").toString()+"</RECEIVER_PROVINCE>");
			xml.append("<RECEIVER_CITY>"+main.get("CITYNAME").toString()+"</RECEIVER_CITY>");
			xml.append("<RECEIVER_DEVISION>"+main.get("COUNTYNAME").toString()+"</RECEIVER_DEVISION>");
			xml.append("<RECEIVER_ADDRESS>"+main.get("SHRDZ").toString()+"</RECEIVER_ADDRESS>");
			xml.append("<DC_CODE>"+dqck+"</DC_CODE>");  //送货仓库
			xml.append("<SPECIAL_SIGN>70</SPECIAL_SIGN>"); //待定
			xml.append("<ORDER_STATUS>20</ORDER_STATUS>");
			xml.append("<TRANS_TYPE>1</TRANS_TYPE>");
			//组装明细
			xml.append("<ORDER_ITEMS>");
			Map itemMap;
			for(int i=0;i<item.size();i++){
				itemMap = (Map)item.get(i);
				xml.append("<ORDER_ITEM>");
				xml.append("<PRODUCT_CODE>"+itemMap.get("BARCODE")+"</PRODUCT_CODE>");
				xml.append("<PACKAGE_NUMBER>"+itemMap.get("SPSL")+"</PACKAGE_NUMBER>");
				xml.append("</ORDER_ITEM>");
			}
			xml.append("</ORDER_ITEMS>");
			xml.append("</ORDER></TRANS_ORDER_COMMIT_PARAM></request>");
		}catch(Exception ex){
			throw ex;
		}
		System.out.println("dqck+XML"+xml.toString());
		return xml.toString();
	}
	
	/**
	 * 获取AccessToken
	 * @return 
	 * @throws Exception
	 */
	public String fetchAccessToken() throws Exception {
		AccessToken token = AccessTokenUtil.createNewAccessToken();
		System.out.println("AccessToken值:"+ ReflectionToStringBuilder.toString(token));
		return token==null?null:token.getAccessToken();
	}
	
	/**
	 * @todo 获取国美物流中地区对应的仓库
	 * @param dqxx
	 * @return
	 */
	public String getDQCK(String dqxx){
		String DQCK="";
		String sql="SELECT B.CKDZ99 DQCK FROM W_DQCK A,CK B  WHERE A.CK01=B.CK01 AND A.DQXX01='"+dqxx+"'";
		Map resultMap = new HashMap();
		resultMap = queryForMap(o2o,sql);
		if(resultMap != null){
			DQCK=resultMap.get("DQCK").toString();
		}
		return DQCK;
	}
	
	/**
	 * @todo 获取销售主记录
	 * @param xsdd01
	 * @return
	 */
	public Map getOrderMain(String xsdd01){
		Map resultMap = new HashMap();
		String sql = "SELECT A.XSDD01,A.XSDD19 SHR,A.XSDD21 SHRSJHM,A.XSDD20 SHRDZ,"+
						   "A.PROVINCE,(SELECT DQBZM02 FROM DQBZM WHERE DQBZM01=A.PROVINCE) PROVINCENAME,"+
						   "A.CITY,(SELECT DQBZM02 FROM DQBZM WHERE DQBZM01=A.CITY) CITYNAME,"+
						   "A.COUNTY,(SELECT DQBZM02 FROM DQBZM WHERE DQBZM01=A.COUNTY) COUNTYNAME,"+
						   "B.ZCXX02 FHR,B.ZCXX06 FHRSJHM "+
						   "FROM W_XSDD A,W_ZCXX B WHERE A.ZTID=B.ZCXX01 AND A.XSDD01='"+xsdd01+"'";
		resultMap = queryForMap(o2o,sql);
		return resultMap;
	}
	
	/**
	 * @todo 获取销售明细记录
	 * @param xsdd01
	 * @return
	 */
	public List getOrderItem(String xsdd01){
		List list = new ArrayList();
		String sql = "SELECT A.XSDD01,A.XSDDI01,A.SPXX01,C.BARCODE,B.SPXX02,A.XSDDI02 SPJG,A.XSDDI05 SPSL,B.GGXH " +
				           "FROM W_XSDDITEM A,W_SPXX B, W_GOODS C  WHERE A.SPXX01=B.SPXX01 AND A.SPXX01=C.SPXX01 AND A.XSDD01='"+xsdd01+"'";
		list = queryForList(o2o,sql);
		return list;
	}
	
	/**
	 * @todo 获取XML节点值
	 * @param xml
	 * @param nodeName
	 * @return
	 * @throws Exception 
	 */
	public String getXmlNodeValue(String xml,String nodeName) throws Exception{
		String nodeValue = "";
		Document document = DocumentHelper.parseText(xml);
		nodeValue = ((Element)document.selectSingleNode("//"+nodeName+"")).getText();
		return nodeValue;
	}
	/**
	 * @todo 出库单对接
	 * @param map
	 */
	public Map  transCKD(String json,String CKDH) throws Exception{
		//获取物流配置信息
		Map ckMap = pubService.getCKD(CKDH);
		Map wlMap = pubService.getZCGSWL(ckMap.get("ZCXX01").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		String accessToken = fetchAccessToken();
		String paramXml = getXmlForTransCKD(json,CKDH);
		String returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_ASN_ORDER", paramXml);
		System.out.println("returnXml="+returnXml);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", getXmlNodeValue(returnXml,"status"));
		resultMap.put("message", getXmlNodeValue(returnXml,"message"));
		return resultMap;
	}
	
	/**
	 * @todo 出库获取参数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String  getXmlForTransCKD(String json,String CKDH) throws Exception{
		StringBuffer xml = new StringBuffer();
		try{
			cds=new DataSet(json);
			//根据区域查询对应仓库
			String dqck=cds.getField("SHCK", 0);
			String sql = "SELECT CKDZ99 FROM CK WHERE CK01='"+dqck+"'";
			dqck = ((Map)queryForMap(o2o,sql)).get("CKDZ99").toString();
			String BZ=cds.getField("BZ", 0);
			String CKDLX=cds.getField("CKDLX", 0);
			String CKDITEM="";
			if(CKDLX.equals("1")){
				String CKD=cds.getField("CKD", 0);
				JSONObject  jasonObject = JSONObject.fromObject(CKD);
				Map main = (Map)jasonObject;
				CKDITEM= main.get("CKDITEM").toString();
			}else{
				CKDITEM=cds.getField("CKDITEM", 0);
			}
			cds=new DataSet(CKDITEM);
			String spcmList=cds.getField("spcmList", 0);
			JSONArray  item = JSONArray.fromObject(spcmList);//明细
			//开始组装XML
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<request><ASN_ORDER_COMMIT_PARAM><ORDER>");
			xml.append("<ORDER_NO>"+CKDH+"</ORDER_NO>");
			xml.append("<DC_CODE>"+dqck+"</DC_CODE>");  //送货仓库
			xml.append("<SPECIAL_SIGN>2</SPECIAL_SIGN>"); //特殊处理标记，2：送货到仓库
			xml.append("<REMARK>"+BZ+"</REMARK>");
			//组装明细
			xml.append("<ORDER_ITEMS>");
			Map itemMap;
			for(int i=0;i<item.size();i++){
				itemMap = (Map)item.get(i);
				xml.append("<ORDER_ITEM>");
				xml.append("<PRODUCT_CODE>"+itemMap.get("barcode")+"</PRODUCT_CODE>");//"+itemMap.get("barcode")+"spxx02:000077(测试用)"+itemMap.get("SPXX02")+"
				xml.append("<PACKAGE_NUMBER>1</PACKAGE_NUMBER>");
				xml.append("<PRODUCT_COUNT>"+itemMap.get("num")+"</PRODUCT_COUNT>");//仓库数量
				xml.append("<PRODUCT_PRICE>10</PRODUCT_PRICE>");//价格
				xml.append("<SN></SN>");//串码
				xml.append("</ORDER_ITEM>");
			}
			xml.append("</ORDER_ITEMS>");
			xml.append("</ORDER></ASN_ORDER_COMMIT_PARAM></request>");
			System.out.println("组装XML="+xml.toString());
		}catch(Exception ex){
			throw ex;
		}
		return xml.toString();
	}
	
	/**
	 * @todo 出库单对接，燕总后台直接跟我们对接
	 * @param map
	 */
	public Map  transCKD2(Map map) throws Exception{
		//获取物流配置信息
		Map ckMap = pubService.getCKD(map.get("CKDH").toString());
		Map wlMap = pubService.getZCGSWL(ckMap.get("ZCXX01").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		String accessToken = fetchAccessToken();
		String paramXml = getXmlForTransCKD2(map);
		String returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_ASN_ORDER", paramXml);
		System.out.println(returnXml);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", getXmlNodeValue(returnXml,"status"));
		resultMap.put("message", getXmlNodeValue(returnXml,"message"));
		return resultMap;
	}
	
	/**
	 * @todo 出库获取参数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String  getXmlForTransCKD2(Map map) throws Exception{
		StringBuffer xml = new StringBuffer();
		try{
			//cds=new DataSet(spcmList);
			//根据区域查询对应仓库
			String BZ=map.get("BZ").toString();
			String CKDH=map.get("CKDH").toString();
			String dqck=map.get("SHCK").toString();
		
			String spcmList=map.get("spcmList").toString();
			//spcmList="[{SPXX02=000177, num=1, SPXX01=177, spcm01=(01)06920128200028(10)150211(21)B4(9999)02, barcode=6920128200028}]";
			JSONArray  item = JSONArray.fromObject(spcmList);//明细
			//开始组装XML
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<request><ASN_ORDER_COMMIT_PARAM><ORDER>");
			xml.append("<ORDER_NO>"+CKDH+"</ORDER_NO>");
			xml.append("<DC_CODE>"+dqck+"</DC_CODE>");  //送货仓库
			xml.append("<SPECIAL_SIGN>2</SPECIAL_SIGN>"); //特殊处理标记，2：送货到仓库
			xml.append("<REMARK>"+BZ+"</REMARK>");
			//组装明细
			xml.append("<ORDER_ITEMS>");
			Map itemMap;
			for(int i=0;i<item.size();i++){
				itemMap = (Map)item.get(i);
				xml.append("<ORDER_ITEM>");
				xml.append("<PRODUCT_CODE>"+itemMap.get("barcode")+"</PRODUCT_CODE>");//spxx02:000077(测试用)"+itemMap.get("SPXX02")+"
				xml.append("<PACKAGE_NUMBER>1</PACKAGE_NUMBER>");
				xml.append("<PRODUCT_COUNT>"+itemMap.get("num")+"</PRODUCT_COUNT>");//仓库数量
				xml.append("<PRODUCT_PRICE>10</PRODUCT_PRICE>");//价格
				xml.append("<SN></SN>");//串码
				xml.append("</ORDER_ITEM>");
			}
			xml.append("</ORDER_ITEMS>");
			xml.append("</ORDER></ASN_ORDER_COMMIT_PARAM></request>");
			System.out.println(xml.toString());
		}catch(Exception ex){
			throw ex;
		}
		return xml.toString();
	}
	
	/**
	 * @todo 出库单查询
	 * @param map
	 */
	@SuppressWarnings("finally")
	public Map  selectCKD(String json) throws Exception{
		//获取物流配置信息
		cds=new DataSet(json);
		try{
		String CKDH = cds.getField("ckdh", 0);
		Map ckMap = pubService.getCKD(CKDH);
		Map wlMap = pubService.getZCGSWL(ckMap.get("ZCXX01").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		String accessToken = fetchAccessToken();
		String paramXml = getXmlForCKD(json);
		System.out.println(paramXml+"    ####################################################################");
		String returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_STOCK_INOUT", paramXml);
		System.out.println("returnXml="+returnXml);
		/*模拟数据
		String returnXml ="<?xml version='1.0' encoding='UTF-8'?><response><status>0</status><message>SUCCESS</message><uuid>2c378ec76e2641a9b7b8984bb4ec42b3</uuid>"
		+ " <data><STOCK_INOUT_QUERY_RESULT><CUSTORDER_NO>CK1430291353</CUSTORDER_NO><STOCK_INOUT>" +
				"<ITEM><PRODUCT_NO>6934104810002</PRODUCT_NO>" +
				"<BUSINESS_TYPE>1</BUSINESS_TYPE>" +
				"<CUSTORDER_NO>CK1430291353</CUSTORDER_NO>" +
				"<ACT_RCVQTY>100</ACT_RCVQTY>" +
				"<WAREHOUSE_CODE>1006523-2323</WAREHOUSE_CODE>" +
				"<BUSINESS>CK1430291353</BUSINESS>" +
				"<BUSINESS_NO>AXABC.CG201403280001</BUSINESS_NO>" +
				"<BUSINESS_DATE>2014-3-28 10:15:00</BUSINESS_DATE>" +
				"<PRODUCT_STATUS>10</PRODUCT_STATUS>" +
				"<BATCH_NO>001</BATCH_NO>" +
				"</ITEM>" +
				"<ITEM><PRODUCT_NO>6934104810019</PRODUCT_NO>" +
				"<BUSINESS_TYPE>1</BUSINESS_TYPE>" +
				"<CUSTORDER_NO>CK1430291353</CUSTORDER_NO>" +
				"<ACT_RCVQTY>100</ACT_RCVQTY>" +
				"<WAREHOUSE_CODE>1006523-2323</WAREHOUSE_CODE>" +
				"<BUSINESS>CK1430291353</BUSINESS>" +
				"<BUSINESS_NO>AXABC.CG201403280001</BUSINESS_NO>" +
				"<BUSINESS_DATE>2014-3-28 10:15:00</BUSINESS_DATE>" +
				"<PRODUCT_STATUS>10</PRODUCT_STATUS>" +
				"<BATCH_NO>001</BATCH_NO>" +
				"</ITEM></STOCK_INOUT></STOCK_INOUT_QUERY_RESULT></data></response>";
		 *  * */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//System.out.println(getXmlNodeValue(returnXml,"status"));
		resultMap=returnXmlHandle(returnXml);
		if(resultMap.get("status")!= "2"){
			resultMap.put("status", getXmlNodeValue(returnXml,"status"));
		}
		return resultMap;
		}
		catch(Exception ex){
			throw ex;
		}finally{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			return resultMap;
		}
	}
	
	public Map returnXmlHandle(String returnXml)throws Exception{
		//取的根元素
		Document document = DocumentHelper.parseText(returnXml);
		Element root = document.getRootElement();
		Element head = (Element)document.selectSingleNode("//status");
		List orderResponses = document.selectNodes("//ITEM");
		Element node_item = null;
		List list=new ArrayList();
		for(int i=0;i<orderResponses.size();i++){
			//基础数据
			node_item = (Element)orderResponses.get(i);
            String PRODUCT_NO=node_item.selectSingleNode("PRODUCT_NO").getText();//商品条码
            String BUSINESS_TYPE=node_item.selectSingleNode("BUSINESS_TYPE").getText();//交易类型
            String CUSTORDER_NO=node_item.selectSingleNode("CUSTORDER_NO").getText();//来源订单号
            String ACT_RCVQTY=node_item.selectSingleNode("ACT_RCVQTY").getText();//实收数量
            String WAREHOUSE_CODE=node_item.selectSingleNode("WAREHOUSE_CODE").getText();//仓库代码
            String BUSINESS_NO=node_item.selectSingleNode("BUSINESS_NO").getText();//收货/出货单号
            String BUSINESS_DATE=node_item.selectSingleNode("BUSINESS_DATE").getText();//收货/出货时 间
            String BATCH_NO=node_item.selectSingleNode("BATCH_NO").getText();//批次
            Map DataMap = new HashMap();
            DataMap.put("PRODUCT_NO", PRODUCT_NO);
            String spxxsql = "select SPXX04 from w_goods where barcode='"+PRODUCT_NO+"'";
			Map spxxmap = queryForMap(o2o, spxxsql);
			if(spxxmap == null){
				Map returnMap=new HashMap();
				returnMap.put("barcode",PRODUCT_NO);
				returnMap.put("status","2");
				return returnMap;
			}
			String SPXX04 = spxxmap.get("SPXX04").toString();
			DataMap.put("SPXX04", SPXX04);
            DataMap.put("BUSINESS_TYPE", BUSINESS_TYPE);
            DataMap.put("CUSTORDER_NO", CUSTORDER_NO);
            DataMap.put("ACT_RCVQTY", ACT_RCVQTY);
            DataMap.put("WAREHOUSE_CODE", WAREHOUSE_CODE);
            DataMap.put("BUSINESS_DATE", BUSINESS_DATE);
            DataMap.put("BUSINESS_NO", BUSINESS_NO);
            DataMap.put("BATCH_NO", BATCH_NO);
            list.add(DataMap);
		}
		Map map=new HashMap();
		map.put("BARCODEList",list);
		System.out.println("map==="+map);
		return map;
		
	}
	/**
	 * @todo 出库获取参数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String  getXmlForCKD(String json) throws Exception{
		StringBuffer xml = new StringBuffer();
		cds=new DataSet(json);
		try{
			//开始组装XML
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<request><STOCK_INOUT_QUERY_PARAM>");
			xml.append("<CUSTORDER_NO>"+cds.getField("ckdh", 0)+"</CUSTORDER_NO>");//CKDH
			xml.append("<BUSINESS_TYPE>1</BUSINESS_TYPE>");  //空值 全部，1.收货， 2.出货
			xml.append("<START_TIME>"+cds.getField("START_TIME", 0)+"</START_TIME>"); //特殊处理标记，2：送货到仓库
			xml.append("<END_TIME>"+cds.getField("END_TIME", 0)+"</END_TIME>");
//			xml.append("<PAGE_NO>1</PAGE_NO>");
			xml.append("</STOCK_INOUT_QUERY_PARAM></request>");
			System.out.println(xml.toString());
		}catch(Exception ex){
			throw ex;
		}
		return xml.toString();
	}
	/**
	 * @todo 退货单对接
	 * @param map
	 */
	public Map  transTHD(String THDH) throws Exception{
		//获取物流配置信息
		Map thMap = pubService.getTHD(THDH);
		Map wlMap = pubService.getZCGSWL(thMap.get("ZTID").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		String accessToken = fetchAccessToken();
		String paramXml = getXmlFortransTHD(THDH);
		String returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_TRANS_ORDER", paramXml);
		System.out.println("THD---returnXml="+returnXml);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", getXmlNodeValue(returnXml,"status"));
		resultMap.put("message", getXmlNodeValue(returnXml,"message"));
		return resultMap;
	}
	
	/**
	 * @todo 退货 获取参数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String  getXmlFortransTHD(String THDH) throws Exception{
		StringBuffer xml = new StringBuffer();
		try{
			Map main = getTHDMain(THDH);
			List item = getTHDItem(THDH);
			//根据区域查询对应仓库
			String dqck=getDQCK(main.get("CITY").toString());
			//开始组装XML
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<request><TRANS_ORDER_COMMIT_PARAM><ORDER>");
			xml.append("<ORDER_CODE>"+THDH+"</ORDER_CODE>");
			xml.append("<COD_AMOUNT>0</COD_AMOUNT>");
			xml.append("<SENDER_NAME>"+main.get("FHR").toString()+"</SENDER_NAME>");
			xml.append("<SENDER_MOBILE>"+main.get("FHRSJHM").toString()+"</SENDER_MOBILE>");
			xml.append("<RECEIVER_NAME>"+main.get("SHR").toString()+"</RECEIVER_NAME>");
			xml.append("<RECEIVER_MOBILE>"+main.get("SHRSJHM").toString()+"</RECEIVER_MOBILE>");
			xml.append("<RECEIVER_PROVINCE>"+main.get("PROVINCENAME").toString()+"</RECEIVER_PROVINCE>");
			xml.append("<RECEIVER_CITY>"+main.get("CITYNAME").toString()+"</RECEIVER_CITY>");
			xml.append("<RECEIVER_DEVISION>"+main.get("COUNTYNAME").toString()+"</RECEIVER_DEVISION>");
			xml.append("<RECEIVER_ADDRESS>"+main.get("SHRDZ").toString()+"</RECEIVER_ADDRESS>");
			xml.append("<DC_CODE>"+dqck+"</DC_CODE>");  //送货仓库
			xml.append("<SPECIAL_SIGN>70</SPECIAL_SIGN>"); //待定
			xml.append("<ORDER_STATUS>20</ORDER_STATUS>");
			xml.append("<TRANS_TYPE>11</TRANS_TYPE>");//11上面提退货
			//组装明细
			xml.append("<ORDER_ITEMS>");
			Map itemMap;
			for(int i=0;i<item.size();i++){
				itemMap = (Map)item.get(i);
				xml.append("<ORDER_ITEM>");
				xml.append("<PRODUCT_CODE>"+itemMap.get("BARCODE")+"</PRODUCT_CODE>");
				xml.append("<PACKAGE_NUMBER>"+itemMap.get("SPSL")+"</PACKAGE_NUMBER>");
				xml.append("</ORDER_ITEM>");
			}
			xml.append("</ORDER_ITEMS>");
			xml.append("</ORDER></TRANS_ORDER_COMMIT_PARAM></request>");
		}catch(Exception ex){
			throw ex;
		}
		System.out.println(xml.toString());
		return xml.toString();
	}
	/**
	 * @todo 商品发布对接
	 * @param map
	 */
	public Map  goodsPUBLISH(String goodsCode,String  goodsName,String weight) throws Exception{
		//获取物流配置信息
		Map spMap = pubService.getSPXX(goodsCode);
		Map wlMap = pubService.getZCGSWL(spMap.get("ZCXX01").toString());
		GOPConfig.APP_KEY = wlMap.get("APP_KEY").toString();
		GOPConfig.APP_SECRET = wlMap.get("APP_SECRET").toString();
		GOPConfig.APP_URL  = APP_URL;
		//获取AccessToken
		Map<String, Object> resultMap =null;
		String accessToken=null;
		String paramXml=null;
		String returnXml=null;
		try{
			accessToken = fetchAccessToken();
			paramXml = getXmlForgoodsPUBLISH(goodsCode,goodsName,weight);
			returnXml = GOPAPIUtil.invokeApi(accessToken, "AX_GOODS", paramXml);
			System.out.println("accessToken--2:"+accessToken+"--->paramXml:"+paramXml+"---->returnXml:"+returnXml);
			resultMap = new HashMap<String, Object>();
			resultMap.put("status", getXmlNodeValue(returnXml,"status"));
			resultMap.put("message", getXmlNodeValue(returnXml,"message"));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("accessToken:"+accessToken+"--->paramXml:"+paramXml+"---->returnXml:"+returnXml);
			throw new Exception("安迅发布商品接口调用失败");
		}
		return resultMap;
	}
	
	/**
	 * @todo 商品发布对接 获取参数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String  getXmlForgoodsPUBLISH(String goodsCode,String  goodsName,String weight) throws Exception{
		StringBuffer xml = new StringBuffer();
		try{
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<request>");
			xml.append("<AX_GOODS_COMMIT_PARAM>");
			xml.append("<GOODS_CODE>"+goodsCode+"</GOODS_CODE>");
			xml.append("<GOODS_NAME>"+goodsName+"</GOODS_NAME>");
			xml.append("<WEIGHT>"+weight+"</WEIGHT>");
			xml.append("<VOLUME>"+"</VOLUME>");
			xml.append("<TYPE_IN>"+"</TYPE_IN>");
			xml.append("<TYPE_OUT>"+"</TYPE_OUT>");
			xml.append("</AX_GOODS_COMMIT_PARAM>");
			xml.append("</request>");
		}catch(Exception ex){
			throw ex;
		}
		return xml.toString();
	}
	
	/**
	 * @todo 获取销售主记录
	 * @param xsdd01
	 * @return
	 */
	public Map getTHDMain(String THDH){
		Map resultMap = new HashMap();
		String sql = "SELECT A.XSDD01,C.THDH,A.XSDD19 SHR,A.XSDD21 SHRSJHM,A.XSDD20 SHRDZ,"+
						   "A.PROVINCE,(SELECT DQBZM02 FROM DQBZM WHERE DQBZM01=A.PROVINCE) PROVINCENAME,"+
						   "A.CITY,(SELECT DQBZM02 FROM DQBZM WHERE DQBZM01=A.CITY) CITYNAME,"+
						   "A.COUNTY,(SELECT DQBZM02 FROM DQBZM WHERE DQBZM01=A.COUNTY) COUNTYNAME,"+
						   "B.ZCXX02 FHR,B.ZCXX06 FHRSJHM "+
						   "FROM W_XSDD A,W_ZCXX B,W_THD C" +
						   " WHERE A.ZTID=B.ZCXX01 AND A.XSDD01=C.XSDD01 AND C.THDH='"+THDH+"'";
		resultMap = queryForMap(o2o,sql);
		System.out.println("resultMap:"+resultMap);
		return resultMap;
	}
	/**
	 * @todo 获取销售明细记录
	 * @param xsdd01
	 * @return
	 */
	public List getTHDItem(String THDH){
		List list = new ArrayList();
		String sql = "SELECT A.THDH,A.SPXX01,A.THSL SPSL,B.BARCODE FROM W_THDITEM A,W_GOODS B" +
				           " WHERE A.SPXX01=B.SPXX01 AND A.THDH ='"+THDH+"'";
		list = queryForList(o2o,sql);
		System.out.println("list="+list);
		return list;
	}
}