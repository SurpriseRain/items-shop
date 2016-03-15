package com.jlsoft.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.interfacepackage.V9.V9DHD;
import com.jlsoft.o2o.interfacepackage.V9.V9RKD;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.PubTransaction;

@Controller
@RequestMapping("/InitV9Data")
public class InitV9Data extends JLBill{
	@Autowired
	private V9BasicData v9BasicData;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9DHD v9DHD;
	@Autowired
	private V9RKD v9RKD;
	@Autowired
	private KmsService kmsService;
	@Autowired
    private DataSourceTransactionManager txManager_o2o;
	
	//同步注册数据
	@RequestMapping("/startZCXX")
	public Map startZCXX(){
		Map returnMap = new HashMap();
		String sql = "SELECT A.ZCXX01,A.ZCGS01,B.LX GSLX,A.ZCXX06 SJHM,A.ZCXX02," +
						   "A.ZCXX02,'' KHPYM,A.ZCXX07,A.ZCXX08,A.ZCXX14,A.XTCZY01 " +
						   "FROM W_ZCGS A,W_GSLX B WHERE A.ZCXX01=B.GSID AND A.ZCGS01=4 AND A.ZCXX01 NOT IN ('900005','0000')  AND LENGTH(A.XTCZY01)>0  ORDER BY ZCXX01";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			try{
				manageZCXX(map);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return returnMap;
	}
	
	public void manageZCXX(Map map) throws Exception{
		try{
			Map erpMap = pubService.getECSURL("JL");
			//写入问题状态表
			if(pubService.getWTDJSL(5,map.get("ZCXX01").toString())==0){
				pubService.insertW_WTDJ(5,map.get("ZCXX01").toString(),0,0);
			}
			//调用ERP接口
			boolean flag = v9BasicData.registerDock(map,erpMap);
			if(flag){
				writeINIT_V9(5,map.get("ZCXX01").toString(),1,"注册成功");
			}else{
				writeINIT_V9(5,map.get("ZCXX01").toString(),0,"注册失败");
			}
		}catch(Exception ex){
			writeINIT_V9(5,map.get("ZCXX01").toString(),0,"注册失败："+ex);
			throw ex;
		}
	}
	
	//同步商品数据
	@RequestMapping("/startSPXX")
	public Map startSPXX(){
		Map returnMap = new HashMap();
		String sql = "SELECT A.SPXX01,A.ZCXX01 FROM W_SPGL A,W_SPXX B,W_GOODS C WHERE A.SPXX01=B.SPXX01 AND A.SPXX01=C.SPXX01 " +
				           "AND B.SPFL01 IN (SELECT SPFL01 FROM SPFL) AND LENGTH(C.BARCODE)>0 AND LENGTH(B.SPFL01)>0  " +
				           "AND A.SPXX01 NOT IN (SELECT YWDH FROM init_v9 WHERE DJLX=6) AND A.ZCXX01<>'0044'";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			try{
				manageSPXX(map);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return returnMap;
	}
	
	public void manageSPXX(Map map) throws Exception{
		try{
			Map erpMap = pubService.getECSURL(map.get("ZCXX01").toString());
			//写入问题状态表
			if(pubService.getWTDJSL(6,map.get("SPXX01").toString())==0){
				pubService.insertW_WTDJ(6,map.get("SPXX01").toString(),0,0);
			}
			//调用ERP接口
			erpMap.put("SPXX01", map.get("SPXX01"));
			erpMap.put("ZCXX01", map.get("ZCXX01"));
			boolean flag = v9BasicData.spxxDock(erpMap);
			if(flag){
				writeINIT_V9(6,map.get("SPXX01").toString(),1,"发布商品成功");
			}else{
				writeINIT_V9(6,map.get("SPXX01").toString(),0,"发布商品失败");
			}
		}catch(Exception ex){
			writeINIT_V9(6,map.get("SPXX01").toString(),0,"发布商品失败："+ex);
			throw ex;
		}
	}
	
	//出库单同步数据
	@RequestMapping("/startCKD")
	public Map startCKD(){
		Map returnMap = new HashMap();
		String sql = "SELECT CKDH,ZCXX01 FROM W_CKD WHERE CKDH IN ('CK1434009599','CK1434338460','CK1434365395') ORDER BY CKSJ";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			try{
				manageCKD(map);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return returnMap;
	}
	
	public void manageCKD(Map map) throws Exception{
		try{
			Map erpMap = pubService.getECSURL(map.get("ZCXX01").toString());
			String returnStr = v9DHD.createDHD(erpMap, map.get("CKDH").toString());
			System.out.println("制定订货单返回参数："+returnStr);
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			Map returnData =(Map) jsonObject.get("data");
			if(((Integer)returnData.get("JL_State")).toString().equals("1")){
				writeINIT_V9(0,map.get("CKDH").toString(),1,"出库单生成订单成功");
			}else{
				writeINIT_V9(0,map.get("CKDH").toString(),0,"出库单生成订单失败:"+returnData.get("JL_ERR").toString());
			}
		}catch(Exception ex){
			writeINIT_V9(0,map.get("CKDH").toString(),0,"出库单生成订单失败");
			throw ex;
		}
	}
	
	//入库单同步数据
	@RequestMapping("/startRKD")
	public Map startRKD() {
		Map returnMap = new HashMap();
		String sql = "SELECT RKDH,CKDH,ZCXX01 FROM W_RKD WHERE RKDH IN ('RK0000000000','RK0000000002') ORDER BY RKSJ";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			try{
				manageRKD(map);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return returnMap;
	}
	
	public void manageRKD(Map map) throws Exception{
		TransactionStatus status = PubTransaction.getTransactionStatus_o2o(txManager_o2o);
		try{
			Map erpMap = pubService.getECSURL(map.get("ZCXX01").toString());
			String returnStr = v9RKD.createRKD(erpMap,map.get("CKDH").toString(),map.get("RKDH").toString());
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			Map returnData =(Map) jsonObject.get("data");
			if(returnData.get("JL_State").equals("1")){
				writeINIT_V9(1,map.get("RKDH").toString(),1,"入库单生成成功");
				//写入库存信息
				String queryRKD="SELECT ZCXX01, SPXX01, SHCK, RKSL FROM w_rkd LEFT JOIN w_rkditem ON w_rkditem.RKDH = w_rkd.RKDH where w_rkd.RKDH='"+map.get("RKDH").toString()+"'";
				List ckList = queryForList(o2o, queryRKD);
				for(int i =0;i<ckList.size();i++){
					Map ckMap = (Map) ckList.get(i);
					kmsService.insertGwcSpxx(ckMap.get("ZCXX01").toString(), Double.valueOf(ckMap.get("SPXX01").toString()),ckMap.get("RKSL").toString(), 0, 0, 0, ckMap.get("SHCK").toString(), "0", 1, map.get("RKDH").toString(), Integer.parseInt(ckMap.get("RKSL").toString()), 0);
				}
			}else{
				throw new Exception("");
			}
			txManager_o2o.commit(status);
		}catch(Exception ex){
			txManager_o2o.rollback(status);
			writeINIT_V9(1,map.get("RKDH").toString(),0,"入库单生成失败");
			throw ex;
		}
	}
	
	//写日志
	public void writeINIT_V9(int DJLX,String YWDH,int FLAG,String ERROR) throws Exception{
		String sql = "";
		sql = "SELECT COUNT(0) FROM INIT_V9 WHERE DJLX="+DJLX+" AND YWDH='"+YWDH+"'";
		int num = queryForInt(o2o,sql);
		if(num == 0){
			sql = "INSERT INTO INIT_V9(DJLX,YWDH,FLAG,ERROR) VALUES("+DJLX+",'"+YWDH+"',"+FLAG+",'"+ERROR+"')";
		}else{
			sql = "UPDATE INIT_V9 SET FLAG="+FLAG+",ERROR='"+ERROR+"' WHERE DJLX="+DJLX+" AND YWDH='"+YWDH+"'";
		}
		Map map = new HashMap();
		execSQL(o2o,sql,map);
	}
	
}
