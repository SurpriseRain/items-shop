/**********************************************************************
 * @file	OrderFlowManage.java
 * @breif	未经授权不得使用或修改该文档
 * @author	Design:	曾海峰/20140313
 * @author	Code:	曾海峰/20140313
 * @author	Modify:	曾海峰/20140313
 * @copy	Tag:	金力软件
 **********************************************************************/

package com.jlsoft.o2o.order.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V9.V9XSDD;
import com.jlsoft.o2o.interfacepackage.jlinterface.GopInterface;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;

/**
 * 
 * @breif 订单流程操作管理
 * 
 */
@Controller
@RequestMapping("/OrderFlowManage")
public class OrderFlowManage extends JLBill {
	private static final Logger logger = Logger.getLogger(OrderFlowManage.class);
	@Autowired
	private GopInterface gopInterface;
	@Autowired
	private ManageLogs manageLogs;
	@Autowired
	private KmsService kmsService;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9XSDD v9XSDD;

	
	JLTools tool = new JLTools();
	String path = JlAppResources.getProperty("trace_url");
	PublicZSXT zs =new PublicZSXT();
	
	String PayCode = JlAppResources.getProperty("PayCode");
	String SysTemCon = JlAppResources.getProperty("SysTemCon");
	String JL_OrderType = JlAppResources.getProperty("JL_OrderType");
	String ROADMAP = JlAppResources.getProperty("ROADMAP");
	String SALER = JlAppResources.getProperty("SALER"); // CashCode:收款员代码
	String SALERNAME = JlAppResources.getProperty("SALERNAME"); // CashName:收款员名称
	String CKNUM = JlAppResources.getProperty("CKNUM");

	public OrderFlowManage() {
	}

	public OrderFlowManage(JdbcTemplate o2o, JdbcTemplate scm) {
		this.o2o = o2o;
		this.scm = scm;
	}
	
	/*
	 * 合并订单支付insertOrder
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertCombinedOrder.action")
	public Map insertCombinedOrder(String XmlData) throws Exception {
		Map map = new HashMap();
		try {
			SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			SimpleDateFormat sdfend = new SimpleDateFormat("yyyy-MM-dd 23:59:00");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd/hh");  
			cds = new DataSet(XmlData);
			String HBID=cds.getField("HBID", 0);
			//获取大区对应仓库
			String DQXX01=cds.getField("DQXX01", 0);
			//送货开始时间——沈阳大家庭增加字段_add_syw
			String start=cds.getField("start", 0)==""?sdfnew.format(new Date()):sdf1.parse(cds.getField("start", 0)).toLocaleString();
			//送货结束时间
			String end=cds.getField("end", 0)==""?sdfend.format(new Date()):sdf1.parse(cds.getField("end", 0)).toLocaleString();
			//配送区域代码
			String shqydm=cds.getField("shqydm", 0);
			//配送区域名称
			String shqymc=cds.getField("shqymc", 0);
			//送货时间-中文 
			String definename =cds.getField("definename", 0);
			//
			String XSDD25 =cds.getField("XSDD25", 0)==""?"0":cds.getField("XSDD25", 0);
			String XSDD30 =cds.getField("XSDD30", 0)==""?"0":cds.getField("XSDD30", 0);
			// 调用过程获得订单编号01
			String JYDH = "CS_XS"+ JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_JYD",1),10);
			
			//获取支付方式及支付状态 add 2015.10.19.
			String payType = cds.getField("XSDD50", 0);
			
			int XSDD50 = 0;//支付方式：0线上支付 1线下汇款
			if(org.apache.commons.lang.StringUtils.isNumeric(payType)){
				XSDD50 = Integer.parseInt(payType); 
			}
			int XSDD51 = 0; //线下支付状态： 0默认 1申请 2审核 3驳回
			
			//创建交易单  
			//modify 2015.10.19. 增加支付方式 及支付状态
			String jydSql="INSERT INTO W_JYD(JYDH,HBID,GMSJ,ZT,JYJE,XSDD50,XSDD51) VALUES ('"+JYDH+"','"+HBID+"',NOW(),0,JYJE?,"+XSDD50+","+XSDD51+")";
			Map jydRow = getRow(jydSql, null, 0);
			execSQL(o2o, jydSql, jydRow);
			
			String SPITEMLIST=cds.getField("SPITEMLIST", 0);
			JSONArray SPITEMJSON = JSONArray.fromObject(SPITEMLIST);
			for(int i=0;i<SPITEMJSON.size();i++){
				Map spitemMap = (Map) SPITEMJSON.get(i);
				String ZTID=spitemMap.get("ZTID").toString();
				String XSDD02=spitemMap.get("DDJE").toString();//总金额
				//创建交易单详情
				//订单号：JYD+001
				String XSDD01 = JYDH+"00"+(i+1);
				String jydItemSql="INSERT INTO W_JYDITEM(JYDH,XSDD01) VALUES ('"+JYDH+"','"+XSDD01+"')";
				Map jydIRow = getRow(jydItemSql, null, 0);
				execSQL(o2o, jydItemSql, jydIRow);
				/*地址，电话，联系人*/
				String province = "";
				String city = "";
				String county ="";
				String street ="";
				String otherdz = "";
				String gddh = "";
				String email = "";
				String DZBH="";
				if("".equals(cds.getField("DZBH", 0))){
				}else{
					DZBH= cds.getField("DZBH", 0);
					String query_dzbh="SELECT dzbh,wldw01,shrmc,province,city,county,street,otherdz,sjhm,gddh,email,yzbm,mark from w_shdzwh where dzbh='"+DZBH+"'";
					Map dzbmList = queryForMap(o2o, query_dzbh);
					province = dzbmList.get("province").toString();
					city = dzbmList.get("city").toString();
					county = dzbmList.get("county").toString();
					street = dzbmList.get("street").toString();
					otherdz = dzbmList.get("otherdz").toString();
					gddh = dzbmList.get("gddh").toString();
					email = dzbmList.get("email").toString();
				}
				//配送方式为买家自提
				String PSFS=cds.getField("XSDD26", 0);
				if(PSFS.equals("1")){
					province="12";
					city="1201";
					county="120101";
				}
//				//订单主表W_XSDD
//				//modify 2015.10.19. 增加支付方式及支付状态字段
//				String xsddSQL = "INSERT INTO W_XSDD(XSDD01,ZTID,HBID,XSDD02,XSDD03,XSDD04,XSDD07,XSDD10,XSDD11,"
//					+ "XSDD13,XSDD14,XSDD15,XSDD16,XSDD17,XSDD18,XSDD19,XSDD20,"
//					+ "XSDD21,XSDD22,XSDD26,XSDD27,XSDD32,PROVINCE,CITY,COUNTY,STREET,OTHERDZ,GDDH,EMAIL,JYDH,XSDD44,XSDD45,XSDD46,XSDD47,XSDD48,XSDD25,XSDD30,XSDD50,XSDD51)"
//					+ "VALUES('"+ XSDD01+ "','"+ZTID+"','"+HBID+"',"+XSDD02+",0,NOW(),'"+spitemMap.get("XSDD07").toString()+"',XSDD10?,XSDD11?,"
//					+ "XSDD13?,XSDD14?,XSDD15?,XSDD16?,XSDD17?,XSDD18?,XSDD19?,XSDD20?,"
//					+ "XSDD21?,XSDD22?,XSDD26?,XSDD27?,XSDD32?,'"+province+"','"+city+"','"+county+"','"+street+"','"+otherdz+"','"+gddh+"','"+email+"','"+JYDH+"',DATE_FORMAT('"+start+"','%Y-%m-%d %H:%i'),DATE_FORMAT('"+end+"','%Y-%m-%d %H:%i'),'"+shqydm+"','"+shqymc+"','"+definename+"',"+XSDD25+","+XSDD30+","+XSDD50+","+XSDD51+")";
//				// 获取行值
//				Map row = getRow(xsddSQL, null, 0);
//				execSQL(o2o, xsddSQL, row);
			
				//商品详情保存至W_XSDDITEM
				String xsdditem = spitemMap.get("SPXXLIST").toString();
				List spxxList = new ArrayList();
				JSONArray xsddItemList = JSONArray.fromObject(xsdditem);
				
				//总金额
				BigDecimal totalPrice = BigDecimal.ZERO;
				// 循环保存子表
				if (xsddItemList != null && xsddItemList.size() > 0) {
					for (int  j= 0; j < xsddItemList.size(); j++) {
						Map itemMap = (Map) xsddItemList.get(j);
						Map spcodeMap = new HashMap();
						spcodeMap.put("SPXX01", itemMap.get("SPXX01").toString());
						spxxList.add(spcodeMap);
						itemMap.put("XSDDI01", XSDD01 + String.format("%03d", (j + 1)));
						itemMap.put("XSDD01", XSDD01);
						//itemMap.put("XSDDI02", itemMap.get("GWC02").toString());
						//itemMap.put("XSDDI05", itemMap.get("GWC01").toString());
						itemMap.put("XSDDI07", 0);
						itemMap.put("XSDDI08", 0);
						itemMap.put("ZTID", ZTID);
						
						//modify 2015.11.17. xup 商品价格由前台传入修改为后台查询
						//商品编码
						String querySPXX01 = itemMap.get("SPXX01")!=null?itemMap.get("SPXX01").toString():"0";
						//商品数量
						String queryCount = itemMap.get("GWC01")!=null?itemMap.get("GWC01").toString():"1";
						//根据商品编码及商家ID从商品价格表中查询商品价格
						//modify 2015.12.29.修改主体获取
						String strZtid = itemMap.get("ZCXX01")!=null? itemMap.get("ZCXX01").toString():ZTID;
						
						BigDecimal price = getProductPrice(strZtid, querySPXX01);
						BigDecimal amount = price.multiply(new BigDecimal(queryCount));
						totalPrice = totalPrice.add(amount);
						
						itemMap.put("XSDDI02",price);
						itemMap.put("XSDDI05",queryCount);
						
						//String dqSQL = "SELECT DISTINCT w_kcxx.CK01 from w_kcxx INNER JOIN w_dqck on w_dqck.ck01 = w_kcxx.ck01 where w_dqck.dqxx01 = '"+DQXX01+"' and spxx01 = '"+itemMap.get("SPXX01").toString()+"'";
						//modify 2016.01.08. 修改仓库查询
						//String dqSQL = "SELECT DISTINCT w_kcxx.CK01 from w_kcxx " +
						//						 "INNER JOIN w_dqck on w_dqck.ck01 = w_kcxx.ck01 " +
						//						 "INNER JOIN CK on CK.ck01=w_kcxx.ck01 " +
						//						 "where w_dqck.dqxx01 = '"+DQXX01+"' and w_kcxx.spxx01 = '"+itemMap.get("SPXX01").toString()+"' and ck.CK09=0";
						String dqSQL = "SELECT fn_getCk('"+ strZtid +"','"+DQXX01+"','"+itemMap.get("SPXX01").toString()+"' ) CK01";
						String CK01= ((Map)queryForMap(o2o,dqSQL)).get("CK01").toString();
						itemMap.put("CK01",CK01);
						//JSONArray itemJson = JSONArray.fromObject(itemMap);
						insertOrderItem(itemMap);
						// 查询获得商品其他基础信息(积分信息、商品类型)
						String spxxInfoSql = "SELECT IFNULL(SPGL06,0) SPGL06,IFNULL(SPGL07,0) SPGL07,SPGL02,IFNULL(SPGL16,0) SPGL16 FROM W_SPGL WHERE ZCXX01 = '"
							//modify 修改主体ID								
							//+ ZTID
							+ strZtid
							+ "' AND SPXX01 = "
							+ Integer.valueOf(itemMap.get("SPXX01").toString());
						Map spxxInfo = queryForMap(o2o, spxxInfoSql);
						itemMap.put("SPGL02", spxxInfo.get("SPGL02").toString());
						if (0 == Integer.valueOf(spxxInfo.get("SPGL16").toString())) {
							// 普通商品生成积分
							insertItemDetail(itemMap);
						}
					}
				}

				//订单主表W_XSDD
				//modify 2015.10.19. 增加支付方式及支付状态字段
				//modify 2015.11.17. 订单总金额改成查询商品价格后计算，此段代码从上边移到遍历计算之后
				XSDD02 = totalPrice.toString();
				String xsddSQL = "INSERT INTO W_XSDD(XSDD01,ZTID,HBID,XSDD02,XSDD03,XSDD04,XSDD07,XSDD10,XSDD11,"
					+ "XSDD13,XSDD14,XSDD15,XSDD16,XSDD17,XSDD18,XSDD19,XSDD20,"
					+ "XSDD21,XSDD22,XSDD26,XSDD27,XSDD32,PROVINCE,CITY,COUNTY,STREET,OTHERDZ,GDDH,EMAIL,JYDH,XSDD44,XSDD45,XSDD46,XSDD47,XSDD48,XSDD25,XSDD30,XSDD50,XSDD51)"
					+ "VALUES('"+ XSDD01+ "','"+ZTID+"','"+HBID+"',"+XSDD02+",0,NOW(),'"+spitemMap.get("XSDD07").toString()+"',XSDD10?,XSDD11?,"
					+ "XSDD13?,XSDD14?,XSDD15?,XSDD16?,XSDD17?,XSDD18?,XSDD19?,XSDD20?,"
					+ "XSDD21?,XSDD22?,XSDD26?,XSDD27?,XSDD32?,'"+province+"','"+city+"','"+county+"','"+street+"','"+otherdz+"','"+gddh+"','"+email+"','"+JYDH+"',DATE_FORMAT('"+start+"','%Y-%m-%d %H:%i'),DATE_FORMAT('"+end+"','%Y-%m-%d %H:%i'),'"+shqydm+"','"+shqymc+"','"+definename+"',"+XSDD25+","+XSDD30+","+XSDD50+","+XSDD51+")";
				// 获取行值
				Map row = getRow(xsddSQL, null, 0);
				execSQL(o2o, xsddSQL, row);
				
				// 插入订单状态
				Map ddztMap = new HashMap();
				ddztMap.put("XSDD01", XSDD01);
				// 根据主体编码获得当前主体针对订单处理信息（支付方式和审核状态）
				String ztinfoSql = "SELECT IFNULL(ZCXX18,0) ZCXX18 FROM W_ZCXX WHERE ZCXX01 = '"
					+ ZTID + "'";
				Map ztInfoMap = queryForMap(o2o, ztinfoSql);
				int orderFlowBJ = Integer.valueOf(ztInfoMap.get("ZCXX18").toString());
				if (orderFlowBJ == 0 || orderFlowBJ == 4) {// orderFlowBJ ==
					//insertOrderSCM(XmlData, xsddItemList, XSDD01);
					// 订单提交后可以直接点支付也可以审核订单后再支付
					ddztMap.put("W_DJZX02", 3);
					ddztMap.put("W_DJZT03", "订单已提交，待支付！");
				} else {
					ddztMap.put("W_DJZX02", 1);
					ddztMap.put("W_DJZT03", "订单已提交，等待系统确认！");
				}
				// 插入单据状态
				insertOrderDjzx(ddztMap);
				// 插入订单跟踪
				insertOrderDjzt(ddztMap);
				//插入W_XSDDGROUP记录
				String groupSql="INSERT INTO W_XSDDGROUP(XSDD01,XSDDG02) VALUES('"+XSDD01+"',0)";
				execSQL(o2o, groupSql, ddztMap);
				// 删除购物车数据
				Map spmap = new HashMap();
				spmap.put("HBID", HBID);
				spmap.put("ZTID", ZTID);
				spmap.put("SPLIST", spxxList);
				CartManage cartManage = new CartManage(o2o, scm);
				cartManage.deleteGwcMap(spmap);
				map.put("FORWORD", orderFlowBJ);
				map.put("DZBH", DZBH);
			}
			map.put("STATE", "1");
			map.put("JYDH", JYDH);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			map.put("STATE", "0");
			throw e;
		}
		return map;
	}

	/**
	 * 保存订单状态W_DJZX
	 * @param String
	 *          XmlData - String XSDD01 订单编号 - int W_DJZX02 单据状态
	 * @note
	 */
	@SuppressWarnings("unchecked")
	public void insertOrderDjzx(Map map) throws Exception {
		String xsddSQL = "INSERT INTO W_DJZX(W_DJZX01,W_DJZX02) VALUES('"+map.get("XSDD01")+"','"+map.get("W_DJZX02")+"')";
		// 获取行值
		Map row = new HashMap();
		execSQL(o2o, xsddSQL, row);
	}
	
	/**
	 * @todo 更新单据主线
	 * @param XmlData
	 * @throws Exception
	 */
	public void updateOrderDdjzx(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		// 调用过程获得订单编号
		String xsddSQL = "UPDATE W_DJZX SET W_DJZX02 = W_DJZX02? WHERE W_DJZX01 =  XSDD01?";
		// 获取行值
		Map row = getRow(xsddSQL, null, 0);
		execSQL(o2o, xsddSQL, row);
	}
	
	/**
	 * 保存订单跟踪W_DJZT
	 * @param String
	 *          XmlData - String XSDD01 订单编号 - int W_DJZT03 跟踪描述
	 * @note
	 */
	@SuppressWarnings("unchecked")
	public void insertOrderDjzt(Map map) throws Exception {
		
		String xsddSQL = "INSERT INTO W_DJZT(W_DJZT01,W_DJZT02,W_DJZT03) VALUES('"+map.get("XSDD01")+"',NOW(),'"+map.get("W_DJZT03")+"')";
		// 获取行值
		Map row = new HashMap();
		execSQL(o2o, xsddSQL, row);
	}
	
	/**
	 * 保存订单子表W_XSDDITEM
	 * 
	 * @param String
	 *          XmlData - String XSDD01 订单主表编码 
	 *          		- String XSDDI01 订单子表编码 
	 *          		- String SPXX01 商品编码 
	 *          		- Double XSDDI02 商品价格 
	 *          		- Double XSDDI03 商品优惠金额 
	 *          		- Double XSDDI05 商品数量 
	 *          		- Double XSDDI07 商品赠送积分数量 
	 *          		- String XSDDI09 商品参与活动编号 
	 *          		- String DQXX01 地区仓库编码 
	 *          		- int XSDDI13 商品类型（0普通商品 1套餐商品）
	 *          		- int SPGL02   商品属性（0代理商品 2会员商品）
	 * 					@note
	 */
	@SuppressWarnings("unchecked")
	public void insertOrderItem(Map itemMap) throws Exception {
		// 调用过程获得订单编号
		String itemSQL = "INSERT INTO W_XSDDITEM(XSDD01, XSDDI01,SPXX01,XSDDI02,XSDDI05,CK01)"
				+ "	VALUES('"+itemMap.get("XSDD01")+"','"+itemMap.get("XSDDI01")+"','"+itemMap.get("SPXX01")+"','"+itemMap.get("XSDDI02")+"',"+itemMap.get("XSDDI05")+",'"+itemMap.get("CK01")+"')";
		// 获取行值
		Map row = new HashMap();
		execSQL(o2o, itemSQL, row);
		//库存管理
		kmsService.insertGwcSpxx(itemMap.get("ZTID").toString(), Double.parseDouble(itemMap.get("SPXX01").toString()), "0", 0, 0, 0, itemMap.get("CK01").toString(), "0",
				2, itemMap.get("XSDD01").toString(), Integer.parseInt(itemMap.get("XSDDI05").toString())*-1,0 );
	}

	/**
	 * 将普通商品的套餐明细表插入表W_ITEMDETAIL
	 * 
	 * @param String
	 *          XmlData - String XSDD01 订单编号 - String XSDDI01 订单子表编号 - String
	 *          XSDDI05 商品数量 - String SPGL02 大厅标记 - String SPXX01 商品编码 - Double
	 *          XSDDI02 商品价格 - Double XSDDI05 商品数量
	 * 
	 * @note
	 */
	@SuppressWarnings("unchecked")
	public void insertItemDetail(Map map) throws Exception {
		String itemDetailSQL = "INSERT INTO W_ITEMDETAIL(XSDD01, ITEMD01,SPXX01,ITEMD02, ITEMD05,ITEMD09,SPGL02,ITEMD07,ITEMD08)"
				+ "VALUES('"+map.get("XSDD01")+"','"+map.get("XSDDI01")+"','"+map.get("SPXX01")+"','"+map.get("XSDDI02")+"'," +
						"'"+map.get("XSDDI05")+"','"+map.get("XSDDI09")+"','"+map.get("SPGL02")+"','"+map.get("XSDDI07")+"','"+map.get("XSDDI08")+"')";
		// 获取行值
		Map row = new HashMap();
		execSQL(o2o, itemDetailSQL, row);
	}
	
	/**
	 * @todo 更新单据状态
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public Map updateOrderState(String json) throws Exception{
		Map resultMap = new HashMap();
		cds = new DataSet(json);
		String JYDH= cds.getField("JYDH", 0);
		String DJZT = cds.getField("DJZT", 0);
		String sql = "UPDATE W_DJZX SET W_DJZX02="+DJZT+" WHERE W_DJZX01 IN (SELECT XSDD01 FROM W_XSDD WHERE JYDH='"+JYDH+"')";
		execSQL(o2o, sql, resultMap);
		return resultMap;
	}
	
	/**
	 * 订单在线支付成功-业务逻辑处理
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOrderInfo.action")
	public Map updateOrderInfo(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		// 更新单据主线
		String djzxSQL = "UPDATE W_DJZX SET W_DJZX02 = 14 WHERE W_DJZX01 = XSDD01?";
		Map row = getRow(djzxSQL, null, 0);
		execSQL(o2o, djzxSQL, row);
		
		// 更新单据状态
		String djztSQL = "INSERT INTO W_DJZT(W_DJZT01,W_DJZT02,W_DJZT03,TIME01) VALUES(XSDD01?,NOW(),'订单已支付，待发货！',NOWTS())";
		Map row2 = getRow(djztSQL, null, 0);
		execSQL(o2o, djztSQL, row2);
		String aplipaySql="INSERT INTO W_XSDDDZ (DDLX,XSDD01,trade_no,gmwldh) VALUES('W_XSDD',XSDD01?,trade_no?,'')";
		Map row3 = getRow(aplipaySql, null, 0);
		execSQL(o2o, aplipaySql, row3);
		Map resultMap = new HashMap();
		resultMap.put("STATE", 1);
		return resultMap;
	}
	
	/**
	 * @todo 订单提交成功后，获取订单基本信息
	 * @param json
	 * @return
	 */
	@RequestMapping("/getOrderMessage")
	public Map getOrderMessage(String json) throws Exception {
		cds = new DataSet(json);
		String JYDH = cds.getField("JYDH", 0);
		String sql = "SELECT A.JYDH,HBID,XSDD01,XSDD50 FROM W_JYD A,W_JYDITEM B "+
			" WHERE A.JYDH=B.JYDH AND A.JYDH='"+JYDH+"'";
		Map resultMap = new HashMap();
		List list = queryForList(o2o,sql);
		resultMap.put("DDLIST", list);
		return resultMap;
	}
	
	/**
	 * @todo 订单发货
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOrderFH")
	public Map updateOrderFH(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String XSDD01 = cds.getField("XSDD01", 0);
			if(JLTools.getCurConf(1) == 1){
				Map map = gopInterface.transOrder(XSDD01);
				if(!map.get("status").equals("0")){
					//错误后写入状态
					map.put("XSDD01", XSDD01);
					gopInterfaceMassage(map);
					resultMap.put("state", "error");
					/********操作日志记录Start*/
					/*
					Map errorMap=new HashMap();
					errorMap.put("DJLX", "0");//单据类型（默认为0）
					errorMap.put("YWDH", XSDD01);//业务单号
					errorMap.put("DFHM", "");//对方单号（默认为空）
					errorMap.put("CZR", cds.getField("ZCXX01", 0));//操作人
					errorMap.put("RZZT", "1");//日志状态（0失败;1成功）
					errorMap.put("ERROR", "操作成功");//错误信息
					manageLogs.writeLogs(errorMap);
					 * */
					
					/**********操作日志记录end*/
					return resultMap;
				}
			}
			//更新单据主线
			Map ddMap = new HashMap();
			ddMap.put("XSDD01", XSDD01);
			ddMap.put("W_DJZX02", new Integer(15));
			ddMap.put("W_DJZT03", "订单已发货，待收货！");
			JSONArray ddJson = JSONArray.fromObject(ddMap);
			updateOrderDdjzx(ddJson.toString());
			//插入单据状态
			insertOrderDjzt(ddMap);
			resultMap.put("djzt", new Integer(15));
			//根据收款方式确定发货
			String sqlSkfs="SELECT SKFS from w_xsddskfs WHERE XSDD01='"+XSDD01+"'";
			Map mapSkfs=queryForMap(o2o, sqlSkfs, null);
			if(mapSkfs.get("SKFS").toString().equals("6")){//支付宝支付
				String sql="SELECT TRADE_NO FROM W_XSDDDZ WHERE XSDD01='"+XSDD01+"'";
				Map map=queryForMap(o2o, sql, null);
				
				String TRADE_NO=map.get("TRADE_NO").toString();
				resultMap.put("TRADE_NO", TRADE_NO);
			}
			// 对接ERP系统 -- 现改为扫码入库时才生成
			/**
			String sqlXSDD = "SELECT A.ZTID,B.PFD01 FXDH,A.XSDD19 SHR,A.XSDD20 SHDZ,A.XSDD21 SHRDH,A.XSDD26 PSFS FROM W_XSDD A,W_XSDDGROUP B " +
					                    "WHERE A.XSDD01=B.XSDD01 AND A.XSDD01='"+XSDD01+"'";
			Map xsddMap = queryForMap(o2o,sqlXSDD);
			//当时送货上门时才建档
			if(((Integer)xsddMap.get("PSFS")).intValue()==0){
				Map erpMap = pubService.getECSURL(xsddMap.get("ZTID").toString());
				if(erpMap.get("DJLX") != null){
					if(erpMap.get("DJLX").equals("V9")){
						xsddMap.putAll(erpMap);
						String returnStr=v9XSDD.createKHJD(xsddMap);
						System.out.println(returnStr+"  @@@@@@@@@@@@");
						JSONObject jsonObject = JSONObject.fromObject(returnStr);
						Map returnData =(Map) jsonObject.get("data");
						if(!returnData.get("JL_State").equals("1")){
							throw new Exception("客户建档ERP失败");
						}
					}
				}
			}*/
			
			resultMap.put("state", "success");
		}catch(Exception ex){
			resultMap.put("state", "error");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 物流接口对接写入信息
	 * @param map
	 * @throws Exception 
	 */
	public void gopInterfaceMassage(Map map) throws Exception{
		Map ddMap = new HashMap();
		ddMap.put("XSDD01", map.get("XSDD01").toString());
		ddMap.put("W_DJZT03", map.get("message").toString());
		JSONArray ddJson = JSONArray.fromObject(ddMap);
		insertOrderDjzt(ddMap);
	}
	
	/**
	 * @todo 调用国美物流接口查询单据状态
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrderStatus")
	public Map getOrderStatus(String json) throws Exception{
		Map returnmap=new HashMap();
		returnmap = gopInterface.getOrderStatus(json);
		System.out.println("returnmap="+returnmap);
		return returnmap;
	}
	
	/**
	 * @todo  订单收货
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOrderSH")
	public Map updateOrderSH(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String XSDD01 = cds.getField("XSDD01", 0);
			//更新单据主线
			Map ddMap = new HashMap();
			ddMap.put("XSDD01", XSDD01);
			ddMap.put("W_DJZX02", new Integer(6));
			ddMap.put("W_DJZT03", "订单已收货！");
			JSONArray ddJson = JSONArray.fromObject(ddMap);
			updateOrderDdjzx(ddJson.toString());
			//插入单据状态
			insertOrderDjzt(ddMap);
			
			resultMap.put("djzt", new Integer(6));
			resultMap.put("state", "success");
		}catch(Exception ex){
			resultMap.put("state", "error");
			throw ex;
		}
		return resultMap;
	}
	/*
	 * 确认收货
	 * */
	@RequestMapping("/OkMyOrder.action")
	public Map OkMyOrder(String XmlData) {
		Map map = new HashMap();
		String sql = "";
		String sqlSPCM="";
		try {
			cds = new DataSet(XmlData);
			//  更新单据主线
			String deleteSQL = "UPDATE  W_DJZX SET W_DJZX02=6  WHERE W_DJZX01=XSDD01?";
			Map deleteMap = getRow(deleteSQL, null, 0);
			execSQL(o2o, deleteSQL, deleteMap);
			// 插入单据状态
			String deleteSQL1 = "INSERT INTO W_DJZT(W_DJZT01,W_DJZT02,TIME01,W_DJZT03) Values (XSDD01?,NOW()" +
					",nowts(),'货已收到！单据已完成！')";
			Map deleteMap1 = getRow(deleteSQL1, null, 0);
			execSQL(o2o, deleteSQL1, deleteMap);
			
			// add 2015.10.13. 更新W_XSDD增加收货完成时间XSDD49
			String updateSql = "UPDATE W_XSDD set XSDD49=NOW() where XSDD01=XSDD01?";
			Map updateMap = getRow(updateSql, null, 0);
			execSQL(o2o, updateSql, updateMap);
			
			//NineDragon 2015/11/18 修改 address 买家确认收货 为3
			//于追溯系统对接
			if(tool.getCurConf(4)==1)
			{
				//追溯地址
				String address=path+"/AddProduceCollection.json";
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP FROM w_spcm LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 where spcm01 in (SELECT spcm01 FROM w_xsddcm WHERE xsdd01 = '"+cds.getField("XSDD01", 0)+"')";
				List list = queryForList(o2o, query);
				zs.rk(list,address,"3");
			}
			map.put("STATE", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}
	
	/**
	 * 订单支付保存收款方式
	 * 
	 * @param String
	 *          XmlData - String XSDD01 订单编号 - String ZHBJ 组合标记 1：农行支付方式 2：联动网银支付
	 *          3：短信支付 - String SKLIST 收款集合 - int SKFS 收款方式 0：余额支付 1：农行支付方式
	 *          2：联动网银支付 3：短信支付 4积分支付方式 - Double ZFJE 支付金额
	 * 
	 * @return Map - STATE 状态 - ERROR 错误日志
	 * 
	 * @note 订单支付保存收款方式
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertOrderPayway.action")
	public Map insertOrderPayway(String XmlData) {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(XmlData);
			String JYDH = cds.getField("JYDH", 0);
			String ddsql="SELECT A.JYDH,B.XSDD01,B.XSDD02 from w_jyd A,w_xsdd B  where A.JYDH=B.JYDH AND A.JYDH='"+JYDH+"'";
			List list=queryForList(o2o, ddsql);
			System.out.println("list");
			for(int j=0;j<list.size();j++){
				Map ddMap=(Map) list.get(j);
				String XSDD01=ddMap.get("XSDD01").toString();
				
				String ZHBJ = cds.getField("ZHBJ", 0);
				String skList = cds.getField("SKLIST", 0);
				JSONArray skJsonList = JSONArray.fromObject(skList);
				// 首先查询是否已经有当前的支付方式组合了
				String isExitSQL = "SELECT COUNT(0) FROM W_XSDDSKFS WHERE XSDD01 = '"
					+ XSDD01 + "' AND ZHBJ = '" + ZHBJ + "' AND YXBJ = 1";
				int count = queryForInt(o2o, isExitSQL);
				if (count == 0) {
					// 先删除以前保存的数据后重新保存
					String deleteSQL = "DELETE FROM W_XSDDSKFS WHERE XSDD01 = '" + XSDD01
					+ "' AND ZHBJ = '" + ZHBJ + "' AND YXBJ = 0";
					Map deleteMap = getRow(deleteSQL, null, 0);
					execSQL(o2o, deleteSQL, deleteMap);
					for (int i = 0; i < skJsonList.size(); i++) {
						Map paymap = (Map) skJsonList.get(i);
						String insertSQL = "INSERT INTO W_XSDDSKFS(XSDD01,SKFS,ZFJE,ZHBJ,ZFSJ,YXBJ) VALUES('"
							+ XSDD01
							+ "','"
							+ paymap.get("SKFS").toString()
							+ "','"
							+ Double.valueOf(ddMap.get("XSDD02").toString())
							+ "','"
							+ Integer.valueOf(ZHBJ) + "',NOW(),0)";
						Map insertMap = getRow(insertSQL, null, 0);
						execSQL(o2o, insertSQL, insertMap);
					}
					resultMap.put("STATE", "1");
				} else {
					resultMap.put("STATE", "0");
					resultMap.put("ERROR", "亲爱的用户，您曾经使用过该种支付方式并且有效，请完成上次支付或者联系管理员！");
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			resultMap.put("STATE", "0");
			resultMap.put("ERROR", "支付提交异常！");
		}
		return resultMap;
	}
	
	/*
	 * 个人中心我的订单中合并付款
	 * */
	@RequestMapping("/ConsolidatedPayment.action")
	public Map ConsolidatedPayment(String XmlData) {
		Map map = new HashMap();
		try {
			cds = new DataSet(XmlData);
			
			String HBID=cds.getField("HBID", 0);
			String ZFJE=cds.getField("ZFJE", 0);
			
			//获取订单编号 modify 2015.10.20.
			String DDBHLIST=cds.getField("DDBHLIST", 0);
			
			//拆分订单号，拼sql查询各订单支付类型的和 add 2015.10.20.
			String[] orderIds = DDBHLIST.split(",");
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("select sum(xsdd50) from w_xsdd where xsdd01 in (");
			int size = orderIds.length;
			for(int j = 0; j < size; j++){
				sqlBuilder.append("'").append(orderIds[j]).append("'");
				if(j < size -1){
					sqlBuilder.append(",");
				}
			}
			sqlBuilder.append(")");			
			String selectSql = sqlBuilder.toString();
			int payType = queryForInt(o2o, selectSql);
			//比较支付类型的和，判断支付类型是否都相同
			if(payType !=0 && payType != size*1){
				map.put("STATE", "2"); //线上线下不能合并支付
				//不能合并支付时，返回
				return map;
			}
			
			// 调用过程获得订单编号
			String JYDH = "CS_XS"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_JYD",1),10);
			//创建交易单
			String jydSql="INSERT INTO W_JYD(JYDH,HBID,GMSJ,ZT,JYJE) VALUES ('"+JYDH+"','"+HBID+"',NOW(),0,0)";
			Map jydRow = getRow(jydSql, null, 0);
			execSQL(o2o, jydSql, jydRow);

			String List [] =DDBHLIST.split(",");
			for(int i=0;i<List.length;i++)
			{
				//创建交易单详情
				//订单号：JYD+001
				String XSDD01 = List[i];
				String jydItemSql="INSERT INTO W_JYDITEM(JYDH,XSDD01) VALUES ('"+JYDH+"','"+XSDD01+"')";
				Map jydIRow = getRow(jydItemSql, null, 0);
				execSQL(o2o, jydItemSql, jydIRow);
				
				String updateXsddSql="UPDATE W_XSDD SET JYDH='"+JYDH+"' WHERE XSDD01 = '"+XSDD01+"'";
				Map UpdateXsddRow = getRow(updateXsddSql, null, 0);
				execSQL(o2o, updateXsddSql, UpdateXsddRow);
			}
			
			String update_jyje="update W_JYD set JYJE=(select sum(xsdd02) from w_xsdd where jydh='"+JYDH+"') where jydh='"+JYDH+"'";
			execSQL(o2o,update_jyje,new HashMap());
			map.put("HBID", HBID);
			map.put("ZFJE", Double.parseDouble(ZFJE));
			map.put("DDBHLIST", DDBHLIST);
			map.put("JYDH", JYDH);
			map.put("type", payType/size);
			map.put("STATE", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}
	/**
	 * @todo 订单提交成功后，获取订单基本信息
	 * @param json
	 * @return
	 */
	@RequestMapping("/getOrderInfo")
	public Map getOrderInfo(String json) throws Exception {
		cds = new DataSet(json);
		String xsdd01 = cds.getField("xsdd01", 0);
		// 20151207 齐俊宇  添加 卖家名称
		String sql = "SELECT A.XSDD01,A.XSDD02,A.XSDD20,A.XSDD19,A.XSDD21,A.xsdd10,A.xsdd11,A.xsdd07,B.ZCXX02,B.ZCXX06,C.W_DJZX02,"+
							"(SELECT SUM(XSDDI05) FROM W_XSDDITEM WHERE XSDD01=A.xsdd01) XSSL, D.ZCXX02 ZTMC, DATE_FORMAT(A.XSDD04,'%Y-%m-%d %H:%i:%s') XSDD04 "+
							"FROM W_XSDD A,W_ZCGS B,W_DJZX C, W_ZCGS D WHERE A.hbid=B.ZCXX01 AND A.ZTID = D.ZCXX01 AND A.XSDD01=C.W_DJZX01 AND A.XSDD01='"+xsdd01+"'";
		Map resultMap = new HashMap();
		resultMap = queryForMap(o2o,sql);
		return resultMap;
	}
	
	/**
	 * @todo 点击结算验证库存数量
	 * @param json
	 * @return
	 */
	@RequestMapping("/getKcxxNum")
	public Map getKcxxNum(String json) throws Exception {
		String SPXX04="";
		Map resultMap = new HashMap();
		 JSONArray jsonList =JSONArray.fromObject(json);
		 Map parmap =(Map) jsonList.get(0);
		 List list =(List) parmap.get("SPITEMLIST");
		for(int i=0;i<list.size();i++)
		{
			Map mapitem =(Map) list.get(i);
			String zxcc01 =(String) mapitem.get("ZTID");
			String dqxx01 =(String) mapitem.get("DQXX01");
			List listItem =(List) mapitem.get("SPXXLIST");
			for(int j=0;j<listItem.size();j++)
			{
				Map map =(Map) listItem.get(j);
				map.put("ZCXX01", zxcc01);
				map.put("DQXX01", dqxx01);
				//购买数量
				int count =(Integer) map.get("GWC01");
				JSONArray jsonArray = JSONArray.fromObject(map);
				 resultMap = kmsService.CKXX_COUNT(jsonArray.toString());
				 if(resultMap.get("STATE").equals("1"))
				 {
					 //库存数量
					 float num =Float.parseFloat(resultMap.get("number").toString());
					 String spxx04 =(String) resultMap.get("SPXX04");
					 if(count>num)
					 {
						 SPXX04+=spxx04+",";
					 }
				 }else
				 {
					 resultMap.put("STATE", "0");
					 break;
				 }
			}
			if(resultMap.get("STATE").equals("0"))break;
		}
		if(SPXX04.length()>0)
		{
			SPXX04=SPXX04.substring(0, SPXX04.length()-1);
			resultMap.put("SPXX04", SPXX04);
			resultMap.put("STATE", "2");
		}else
		{
			resultMap.put("STATE", "1");
		}
			
		
		return resultMap;
	}
	
	/**
	 * 处理线下转账表单信息
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateJydInfo.action")
	public Map<String, String> updateJydInfo(String XmlData) throws Exception {
		Map<String, String> ResultMap = new HashMap<String, String>();
		try {

			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);
			
			String JYDZ = cds.getField("JYDZ", 0); //交易地址
			String HKRQ = cds.getField("HKRQ", 0); //汇款日期
			String HKR = cds.getField("HKR", 0); //汇款人
			String JYLSH = cds.getField("JYLSH", 0); //交易流水
			String JYYH = cds.getField("JYYH", 0); //交易银行
			String HKBZ = cds.getField("HKBZ", 0); //汇款备注
			String strOrderIds = cds.getField("orderIds", 0);
			
			//验证交易流水号是否已经存在
			String queryJylshSql = "select count(1) from w_jyd where JYLSH='"+ JYLSH +"'";
			int count = queryForInt(o2o, queryJylshSql);
			if(count > 0){
				ResultMap.put("state", "jylsh_err");
				return ResultMap;
			}
			
			//拆分订单号，拼sql查询各订单的交易单号和 add 2015.10.20.
			String[] orderIds = strOrderIds.split(",");
			StringBuilder sqlBuilder = new StringBuilder();
			StringBuilder updateDjzxBuilder = new StringBuilder(); 
			StringBuilder updateXsddBuilder = new StringBuilder();
			
			//sql:查询不重复的交易单号
			sqlBuilder.append("select DISTINCT(jydh) from w_xsdd where xsdd01 in (");
			//sql:更新W_DJZX的线下转账状态
			updateDjzxBuilder.append("update W_DJZX set W_DJZX02=17 where w_djzx01 in (");
			//sql:更新w_xsdd的线下转账状态
			updateXsddBuilder.append("update w_xsdd set XSDD50=1, XSDD51=1 where xsdd01 in(");
			
			int size = orderIds.length;
			for(int j = 0; j < size; j++){
				sqlBuilder.append("'").append(orderIds[j]).append("'");
				updateDjzxBuilder.append("'").append(orderIds[j]).append("'");
				updateXsddBuilder.append("'").append(orderIds[j]).append("'");
				if(j < size -1){
					sqlBuilder.append(",");
					updateDjzxBuilder.append(",");
					updateXsddBuilder.append(",");
				}
			}
			sqlBuilder.append(")");	
			updateDjzxBuilder.append(")");
			updateXsddBuilder.append(")");
			
			String selectSql = sqlBuilder.toString();
			List jydList = queryForList(o2o, selectSql);
			
			//清空,拼更新W_JYD的sql
			sqlBuilder.delete(0, sqlBuilder.length());
			
			sqlBuilder.append("update w_jyd set XSDD50=1, XSDD51=1, JYDZ='").append(JYDZ).append("'").append(",");
			sqlBuilder.append(" HKRQ='").append(HKRQ).append("'").append(",");
			sqlBuilder.append(" HKR='").append(HKR).append("'").append(",");
			sqlBuilder.append(" JYLSH='").append(JYLSH).append("'").append(",");
			sqlBuilder.append(" JYYH='").append(JYYH).append("'").append(",");
			sqlBuilder.append(" HKBZ='").append(HKBZ).append("'");
			sqlBuilder.append(" where JYDH in (");
			int jydSize = jydList.size();
			for(int i = 0; i < jydSize; i++ ){
				Object obj = jydList.get(i);
				sqlBuilder.append(obj!=null?"'"+((Map)obj).get("jydh")+"'":"");
				if(i < (jydSize-1) && obj!=null){
					sqlBuilder.append(",");
				}
			}
			sqlBuilder.append(")");			
			
			//更新Jyd中的线下转账信息
			String updateJydSql = sqlBuilder.toString();
			Map UpdateJydRow = getRow(updateJydSql, null, 0);
			execSQL(o2o, updateJydSql, UpdateJydRow);
			
			//更新w_xsdd的线下转账信息
			String updateXsddSql = updateXsddBuilder.toString();
			Map UpdateXsddRow = getRow(updateXsddSql, null, 0);
			execSQL(o2o, updateXsddSql, UpdateXsddRow);
			
			//更新更新W_DJZX的w_djzx02 状态为17
			String updateDjzxSql = updateDjzxBuilder.toString();
			Map UpdateDjzxRow = getRow(updateDjzxSql, null, 0);
			execSQL(o2o, updateDjzxSql, UpdateDjzxRow);
			
			ResultMap.put("state", "success");
			
		} catch (Exception ex) {
			ResultMap.put("state", "error");
			throw ex;
		}
		return ResultMap;
	}

	
	/**
	 * @todo 取消订单
	 * @param map
	 * @throws Exception 
	 */
	@RequestMapping("/Cancel.action")
	public Map Cancel(String XmlData) throws Exception{
		Map map = new HashMap();
		try {
			cds = new DataSet(XmlData); 
			Map ddMap = new HashMap();
			ddMap.put("XSDD01", cds.getField("XSDD01", 0));
			ddMap.put("W_DJZT03", "用户已取消订单！");
			ddMap.put("W_DJZX02", "12");
			JSONArray ddJson = JSONArray.fromObject(ddMap);
			insertOrderDjzt(ddMap);
			updateOrderDdjzx(ddJson.toString());
			map.put("STATE", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}
	

	
	/**
	 * 根据zcxx01，spxx01从w_spjgb表查询商品价格
	 * @param zcxx01 公司编码
	 * @param spxx01 商品编码
	 * @return
	 * 2015-11-17 下午2:46:25
	 */
	private BigDecimal getProductPrice(String zcxx01, String spxx01) {
		//价格，默认为0
		BigDecimal price = BigDecimal.ZERO;
		// 根据zcxx01，spxx01从w_spjgb表中查询价格
		String querySql = "select spjgb05 from w_spjgb where  zcxx01='"
				+ zcxx01 + "' and spxx01='" + spxx01 + "'";
		Map resultMap = queryForMap(o2o, querySql);
		// 如果结果为null，返回默认值
		if (resultMap == null || resultMap.isEmpty()) {
			return price;
		}
		// 获取价格值，保留2位精度
		if (resultMap.get("spjgb05") != null) {
			price = new BigDecimal(resultMap.get("spjgb05").toString())
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return price;
	}

}
