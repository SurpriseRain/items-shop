package com.jlsoft.o2o.interfacepackage.loop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.order.service.CartManage;
import com.jlsoft.o2o.order.service.Oper_CKD;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
import com.jlsoft.utils.PubTransaction;

@Controller
@RequestMapping("/KmsXSDD")
public class KmsXSDD extends JLBill {
	private Logger logger = Logger.getLogger(CartManage.class);
	@Autowired
	private KmsService kmsService;
	@Autowired
	private Oper_CKD pper_CKD;
	@Autowired
    private DataSourceTransactionManager txManager_o2o;
	@Autowired
	private PubService pubService;
	
	/**
	 * @todo 库存超时管理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleXSDD.action")
	public Map handleXSDD() {
		Map resultMap = new HashMap();
		String querySQL = "select t1.xsdd01,t1.ztid From W_XSDD t1 ,W_DJZX t2 "
				+ "WHERE t1.xsdd01=t2.w_djzx01 AND t2.w_djzx02='3' "
				+ "and TIMESTAMPDIFF(SECOND, t1.xsdd04, CURRENT_TIMESTAMP())>"
				+ (Double.parseDouble(JlAppResources.getProperty("XSDD_TIMEOUT")) * 3600)
				+" group by t1.xsdd01,t1.ztid" ;
		List infoList = queryForList(o2o, querySQL);
		if (infoList != null && infoList.size() > 0) {
			for (int i = 0; i < infoList.size(); i++) {
				Map<String, Object> infoMap = (Map) infoList.get(i);
				try {
					updateKC(infoMap);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
		return resultMap;
	}
	
	@RequestMapping("/updateKC")
	public Map updateKC(Map infoMap) throws Exception {
		Map resultMap = new HashMap();
		TransactionStatus status = PubTransaction.getTransactionStatus_o2o(txManager_o2o);
		try {
			String xsdd01 = infoMap.get("xsdd01").toString();
			String zcxx01 = infoMap.get("ztid").toString();
			String ywdh = "CS"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_XSDD_CS",1),10);
			Map row = new HashMap();
			//生成销售单超时单号
			String insertSql = "insert into W_XSDD_CS(CSDH,XSDD01,CLSJ) values('"
					+ ywdh + "','" + xsdd01 + "',now())";
			execSQL(o2o, insertSql, row);
			//更新单据状态
			String updateSql = "update W_DJZX t1 set w_djzx02='12' WHERE t1.w_djzx01='"+ xsdd01 +"'";
			execSQL(o2o, updateSql, row);
			//查询明细，还原库存
			String ck01="";
			double spxx01=0;
			int bqfss=0;
			Map map;
			String spmxSql="SELECT SPXX01,XSDDI05,CK01 FROM W_XSDDITEM WHERE XSDD01='"+xsdd01+"'";
			List spmxList = queryForList(o2o,spmxSql);
			for(int i=0;i<spmxList.size();i++){
				map=(Map)spmxList.get(i);
				ck01 = map.get("CK01").toString();
				spxx01 = Double.parseDouble(map.get("SPXX01").toString());
				bqfss = Integer.parseInt(map.get("XSDDI05").toString());
				kmsService.insertGwcSpxx(zcxx01, spxx01, "0", 0, 0, 0, ck01, "0",3, ywdh, bqfss, 0);
			}
			txManager_o2o.commit(status);
		} catch (Exception e) {
			txManager_o2o.rollback(status);
			throw e;
		}
		return resultMap;
	}
	
	/**
	 * @todo #卖家发货,买家未点击收货，系统自动确认收货,单位小时
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleSHSJ.action")
	public Map handleSHSJ() {
		Map resultMap = new HashMap();
		String querySQL = "SELECT t1.xsdd01,t1.ztid From  W_XSDD  t1 ,W_DJZX t2 "
				+ "WHERE t1.xsdd01=t2.w_djzx01 "
				+ "and  t2.w_djzx02='5' "
				+ "and TIMESTAMPDIFF(SECOND, t1.XSDD40, CURRENT_TIMESTAMP())>"
				+ (Double.parseDouble(JlAppResources.getProperty("XSDD_ConfirmReceipt")) * 3600)
				+" group by  t1.xsdd01,t1.ztid";
		List infoList = queryForList(o2o, querySQL);
		if (infoList != null && infoList.size() > 0) {
			for (int i = 0; i < infoList.size(); i++) {
				Map<String, Object> infoMap = (Map) infoList.get(i);
				try {
					updateSHSJ(infoMap);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
		return resultMap;
	}
	
	//事物控制自动确认收货
	@RequestMapping("/updateSHSJ")
	public Map updateSHSJ(Map infoMap) throws Exception {
		Map resultMap = new HashMap();
		TransactionStatus status = PubTransaction.getTransactionStatus_o2o(txManager_o2o);
		try {
			String xsdd01 = infoMap.get("xsdd01").toString();
			Map row = new HashMap();
			//修改收货状态
			String updateSql = "update W_DJZX t1 set w_djzx02='6' WHERE t1.w_djzx01='"
					+ xsdd01 + "'";
			execSQL(o2o, updateSql, row);
			txManager_o2o.commit(status);
		} catch (Exception e) {
			txManager_o2o.rollback(status);
			throw e;
		}
		return resultMap;
	}
	
	@RequestMapping("/handleRKD.action")
	/*
	 * 与安讯物流入库对接管理
	 */
	public Map handleRKD() {
		Map resultMap = new HashMap();
		String queryCKD = "select t1.ckdh from   w_ckd t1  where exists(select 1 from w_ckditem t2 where t1.CKDH=t2.CKDH and ifnull(t2.CKSL,0)>ifnull(t2.RKSL,0))";
		String queryRKD = "select dzdh from w_rkd where  CKDH='";
		List infoList = queryForList(o2o, queryCKD);
		if (infoList != null && infoList.size() > 0) {
			// 通过出库单号，查询安讯物流入库信息
			for (int i = 0; i < infoList.size(); i++) {
				Map<String, Object> infoMap = (Map) infoList.get(i);
				String ckdh = infoMap.get("ckdh").toString();
				
				List<Map<String,Object>> axlist=null;
				try {
					Map interfaceMap=pper_CKD.selectCKD("{\"ckdh\":\""+ckdh+"\"}");
					Object dataObject=interfaceMap.get("BARCODEList");
					if(null==dataObject){
						continue;
					}
					axlist=(List<Map<String, Object>>) dataObject;
					/*//测试
					String ssString="{\"dataType\":\"xml\",\"data\":{\"status\":\"0\",\"BARCODEList\":[{\"ACT_RCVQTY\":\"100\",\"BUSINESS_DATE\":\"2014-3-28 10:15:00\",\"BATCH_NO\":\"001\",\"WAREHOUSE_CODE\":\"1006523-2323\",\"PRODUCT_NO\":\"6934104810002\",\"BUSINESS_NO\":\"AXABC.CG201403280001\",\"CUSTORDER_NO\":\"CK1430291353\",\"BUSINESS_TYPE\":\"1\",\"SPXX04\":\"金特威 燃油清洗剂\"},{\"ACT_RCVQTY\":\"100\",\"BUSINESS_DATE\":\"2014-3-28 10:15:00\",\"BATCH_NO\":\"001\",\"WAREHOUSE_CODE\":\"1006523-2323\",\"PRODUCT_NO\":\"6934104810019\",\"BUSINESS_NO\":\"AXABC.CG201403280001\",\"CUSTORDER_NO\":\"CK1430291353\",\"BUSINESS_TYPE\":\"1\",\"SPXX04\":\"金特威 进气道清洗剂\"}]}}";
					JSONObject jsonObject = JSONObject.fromObject(ssString);
					Map map = (Map)jsonObject;
					Map dataMap =(Map)map.get("data");
					axlist=(List<Map<String, Object>>) dataMap.get("BARCODEList");*/
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 安讯物流没入库则跳出
				if (axlist == null || axlist.size() == 0) {
					continue;
				}
				List<Map<String, Object>> axlistNew = new ArrayList();
				// 组装物流数据
				for (int j = 0; j < axlist.size(); j++) {
					Map<String, Object> axlistMap = (Map) axlist.get(j);
					String businessNo = axlistMap.get("BATCH_NO").toString();
					String actrcvqty = axlistMap.get("ACT_RCVQTY").toString();
					String product_no = axlistMap.get("PRODUCT_NO").toString();
					boolean isExists = false;
					Map<String, Object> axMaplistNew = null;
					for (int k = 0; k < axlistNew.size(); k++) {
						axMaplistNew = (Map) axlistNew.get(k);
						String businessNoNew = axlistNew.get(k).get("BATCH_NO").toString();
						if (businessNoNew.equals(businessNo)) {
							isExists = true;
							break;
						}
					}
					if (isExists) {
						List<Map<String, Object>> oldList = (List<Map<String, Object>>) axMaplistNew.get("BATCH_NO_LIST");

						Map<String, Object> listMap = new HashMap<String, Object>();
						listMap.put("ACT_RCVQTY", actrcvqty);
						listMap.put("PRODUCT_NO", product_no);

						oldList.add(listMap);
					} else {
						Map<String, Object> mapNew = new HashMap<String, Object>();
						mapNew.put("BATCH_NO", businessNo);
						List<Map<String, Object>> listNew = new ArrayList<Map<String, Object>>();
						Map<String, Object> listMap = new HashMap<String, Object>();
						listMap.put("ACT_RCVQTY", actrcvqty);
						listMap.put("PRODUCT_NO", product_no);
						listNew.add(listMap);
						mapNew.put("BATCH_NO_LIST", listNew);
						axlistNew.add(mapNew);
					}
				}
				List localRKD = queryForList(o2o,queryRKD+ckdh+"'");
				// 通过本地入库信息排除安讯物流
					for (int k = 0; k < localRKD.size(); k++) {
						Map<String, Object> localRKDMap = (Map) localRKD.get(k);
						String dzdhLocalString = localRKDMap.get("dzdh")
								.toString();
						for (int j = 0; j < axlistNew.size(); j++) {
							Map<String, Object> axlistMap = (Map) axlistNew.get(j);
							String businessNo = axlistMap.get("BATCH_NO").toString();
							if (dzdhLocalString.equals(businessNo)) {
								axlistNew.remove(j);
								break;
							}
						}
					}
				// 通过安讯物流单号和本地入库单号，生成入库单，并进行库存管理
				for (int j = 0; j < axlistNew.size(); j++) {
					try {
						Map<String, Object> axlistMap = (Map<String, Object>) axlistNew.get(j);
						updateKCForAX(axlistNew.get(j), ckdh);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}
		return resultMap;
	}

	@RequestMapping("/updateKCForAX")
	public Map updateKCForAX(Map axlistMap, String ckdh) throws Exception {
		Map resultMap = new HashMap();
		TransactionStatus status = PubTransaction.getTransactionStatus_o2o(txManager_o2o);
		try {
			String businessNo = axlistMap.get("BATCH_NO").toString();
			String rkd = "RK"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_RKD",1),10);
			Map row = new HashMap();
			// 20151218 齐俊宇 update
			// 查询入库的ZCXX01 和 收货仓库SHCK
			// 20160108 齐俊宇 update 增加ZCXX01 条件查询 好像没用
			Map ckdMap = pubService.selectCKDForRKD(ckdh, "AXWL");
			//生成入库单主表
			String insertrkdSql = "insert into w_rkd values('" + rkd
					+ "','" + ckdMap.get("ZCXX01").toString() + "','"+ckdh+"','" + ckdMap.get("SHCK").toString() + "'," 
					+ "now(),'" + businessNo + "',null)";
			/*String insertrkdSql = "insert into w_rkd values('" + rkd
					+ "',( select zcxx01 From  w_ckd where ckdh= '" + ckdh + "'),'"+ckdh+"',( select shck From  w_ckd where ckdh= '" + ckdh + "')," 
					+ "now(),'" + businessNo + "',null)";*/
			execSQL(o2o, insertrkdSql, row);
			
			List<Map<String, Object>> listItem = (List<Map<String, Object>>) axlistMap
					.get("BATCH_NO_LIST");
			for (int i = 0; i < listItem.size(); i++) {
				Map<String, Object> axBusinessMap = listItem.get(i);
				String productNo = axBusinessMap.get("PRODUCT_NO").toString();
				String actRcvqty = axBusinessMap.get("ACT_RCVQTY").toString();

				String queryCKDITEMSQL = "select  t1.zcxx01,t1.shck,t2.spxx01 from w_ckd t1,w_ckditem t2,w_goods t3  where t1.CKDH=t2.CKDH  and t2.SPXX01=t3.spxx01 and t1.CKDH='"
						+ ckdh + "' and t3.barcode='" + productNo + "' and t1.zcxx01=t3.zcxx01";
				Map<String, Object> ckdITEMMap = queryForMap(o2o,queryCKDITEMSQL);

				String zcxx01 = ckdITEMMap.get("zcxx01").toString();
				String shck = ckdITEMMap.get("shck").toString();
				String spxx01 = ckdITEMMap.get("spxx01").toString();
				//生成入库单明细
				String insertrkditemSql = "insert into w_rkditem values('"
						+ rkd + "','" + spxx01 + "'," + actRcvqty + ")";
				execSQL(o2o, insertrkditemSql, row);
				//更新出库单入库数量
				String updateSql = "update w_ckditem t1 set RKSL=(RKSL+" + actRcvqty
						+ ") WHERE t1.ckdh='" + ckdh + "' and SPXX01='" + spxx01
						+ "'";
				execSQL(o2o, updateSql, row);
				//写入库存信息
				kmsService.insertGwcSpxx(zcxx01, Double.valueOf(spxx01), actRcvqty,
						0, 0, 0, shck, "0", 1, rkd,
						Integer.parseInt(actRcvqty),0 );
			}
			txManager_o2o.commit(status);
		} catch (Exception e) {
			txManager_o2o.rollback(status);
			throw e;
		}
		return resultMap;
	}
}
