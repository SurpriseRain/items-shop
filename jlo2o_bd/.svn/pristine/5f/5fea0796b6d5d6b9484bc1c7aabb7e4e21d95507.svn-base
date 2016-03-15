package com.jlsoft.o2o.interfacepackage.jlinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.PubFun;

@Controller
@RequestMapping("/AppInterface")
public class AppInterface extends JLBill{
	private Logger logger=Logger.getLogger(AppInterface.class);
	@Autowired
	private PubSPCM pubSPCM;
	
	/**
	 * @todo 插入串码存放临时表
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertW_T_SPCM")
	public Map<String,Object> insertW_T_SPCM(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//判断临时表中是否已扫码
			int cmNum = pubSPCM.checkW_T_SPCMExist(json);
			if(cmNum == 1){
				resultMap.put("STATE", "cmExist");
			}
			if(cmNum == 0){
				pubSPCM.insertW_T_SPCM(json);
				resultMap.put("STATE", "success");
			}
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 删除串码单条数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteW_T_SPCM")
	public Map<String,Object> deleteW_T_SPCM(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			pubSPCM.deleteW_T_SPCMForSPCM(json);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 根据操作人和业务类型查询关联的临时数据
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectW_T_SPCM")
	public Map<String,Object> selectW_T_SPCM(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			List list = pubSPCM.selectW_T_SPCM(json);
			resultMap.put("SPXXList", list);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 确认临时表数据无误后，插入正式数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertSPCMForAppTemp")
	public Map<String,Object> insertSPCMForAppTemp(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			JSONObject parametersMap = JSONObject.fromObject(json);
			//获取新生成的编号
			int  SPCMDR01 = PubFun.updateWBHZT(o2o,"W_SPCMDR",1);
			parametersMap.put("SPCMDR01", new Integer(SPCMDR01));
			parametersMap.put("DJLX", new Integer(3));
			//插入W_SPCMJLB
			int DRSL = pubSPCM.insertW_SPCMJLBForTemp(parametersMap);
			parametersMap.put("DRSL", new Integer(DRSL));
			parametersMap.put("SBM", "");
			//插入W_SPCMDR
			pubSPCM.insertW_SPCMDR(parametersMap);
			//删除临时W_T_SPCM表数据
			pubSPCM.deleteW_T_SPCMForCZY(json);
			
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 查询商品串码
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectW_SPCM")
	public Map<String,Object> selectW_SPCM(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			List list = pubSPCM.selectW_SPCM(json);
			resultMap.put("SPCMList", list);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 登录获取用户信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appLogin")
	public  Map<String,Object> appLogin(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String person_id = cds.getField("userName", 0);
			String password = cds.getField("password", 0);
			String sql = "SELECT COUNT(0) FROM W_XTCZY WHERE PERSON_ID='"+person_id+"' AND PASSWD='"+password+"'";
			int num = queryForInt(o2o,sql);
			if(num == 1){
				//获取用户信息
				sql = "SELECT A.ZCXX01,A.ZCXX02 GSMC,C.LX,A.XTCZY01,A.ZCXX55 ZJHM,A.ZCXX06 SJHM,A.HYGLM," +
						 "A.ZCXX03 LXR,A.ZCXX09 EMAIL,A.ZCXX08 ADDRESS,A.ZCXX07,A.WZM FROM W_ZCGS A,W_XTCZY B,W_GSLX C "+
						 "WHERE A.XTCZY01=B.PERSON_ID AND A.ZCXX01=C.GSID AND A.XTCZY01='"+person_id+"' AND B.TYBJ=0";
				resultMap = (Map)queryForMap(o2o,sql);
				//查询厂家识别码
				sql = "SELECT SBM FROM W_ZCGSSBM WHERE ZCXX01='"+resultMap.get("ZCXX01").toString()+"'";
				List sbmList = queryForList(o2o,sql);
				resultMap.put("SBMList", sbmList);
				
				resultMap.put("STATE", "success");
			}else{
				resultMap.put("STATE", "failure");
			}
		}catch(Exception ex){
			logger.info("Error to appLogin."+ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}
	
	/**
	 * @todo 获取用户详细信息
	 * @param json
	 * @return
	 */
	@RequestMapping("/getUserMessage")
	public Map<String,Object> getUserMessage(String json){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String person_id = cds.getField("userName", 0);
			//获取用户信息
			String sql = "SELECT A.ZCXX01,A.ZCXX02 GSMC,C.LX,A.XTCZY01,A.ZCXX55 ZJHM,A.ZCXX06 SJHM,A.HYGLM," +
					 		   "A.ZCXX03 LXR,A.ZCXX09 EMAIL,A.ZCXX08 ADDRESS,A.ZCXX07 FROM W_ZCGS A,W_XTCZY B,W_GSLX C "+
					           "WHERE A.XTCZY01=B.PERSON_ID AND A.ZCXX01=C.GSID AND A.XTCZY01='"+person_id+"' AND B.TYBJ=0";
			resultMap = (Map)queryForMap(o2o,sql);
			//根据地址区域查询该区域代码
			if(resultMap.get("ZCXX07") == null || resultMap.get("ZCXX07").equals("")){
				resultMap.put("PROVINCE", "");
				resultMap.put("PROVINCENAME", "");
				resultMap.put("CITY", "");
				resultMap.put("CITYNAME", "");
				resultMap.put("COUNTY", "");
				resultMap.put("COUNTYNAME", "");
			}else{
				resultMap = getQYDZ(resultMap,resultMap.get("ZCXX07").toString());
			}
			
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			logger.info("Error to getUserMessage."+ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}
	
	/**
	 * @todo 更新注册信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateZCXX")
	public Map<String,Object> updateZCXX(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String sql = "";
			String WZM = cds.getField("WZM", 0);
			String SBM = cds.getField("SBM", 0);
			String ZCXX01 = cds.getField("ZCXX01", 0);
			//更新位置码
			if(WZM != null && !WZM.equals("")){
				sql = "UPDATE W_ZCGS SET WZM='"+WZM+"' WHERE ZCXX01='"+ZCXX01+"'";
				execSQL(o2o, sql, new HashMap());
			}
			//更新识别码
			if(SBM != null && !SBM.equals("")){
				sql = "DELETE FROM W_ZCGSSBM WHERE ZCXX01='"+ZCXX01+"'";
				execSQL(o2o, sql, new HashMap());
				sql = "INSERT INTO W_ZCGSSBM(ZCXX01,SBM) VALUES('"+ZCXX01+"','"+SBM+"')";
				execSQL(o2o, sql, new HashMap());
			}
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 根据传入编码确定
	 * @param resultMap 需要存值的Map
	 * @param dqbh 大区编号
	 * @return
	 */
	public Map<String, Object> getQYDZ(Map<String, Object> resultMap,String dqbh){
		Map map = new HashMap();
		for(int i=0;i<3;i++){
			if(map.get("SJDQBM") != null){
				dqbh = map.get("SJDQBM").toString();
			}
			map = (Map)selectDQBZM(dqbh);
		    //县区信息
			if(((Integer)map.get("DQJB")).intValue() == 3){
				resultMap.put("COUNTY", map.get("DQBM").toString());
				resultMap.put("COUNTYNAME", map.get("DQMC").toString());
			}
			//市区信息
			if(((Integer)map.get("DQJB")).intValue() == 2){
				resultMap.put("CITY", map.get("DQBM").toString());
				resultMap.put("CITYNAME", map.get("DQMC").toString());
			}
			//省信息，当为省时跳出循环
			if(((Integer)map.get("DQJB")).intValue() == 1){
				resultMap.put("PROVINCE", map.get("DQBM").toString());
				resultMap.put("PROVINCENAME", map.get("DQMC").toString());
				break;
			}
		}
		return resultMap;
	}
	
	/**
	 * @todo 查询大区编码
	 * @param dqbh
	 * @return
	 */
	public Map<String, Object> selectDQBZM(String dqbh){
		String sql = "SELECT DQBZM01 DQBM,DQBZM_DQBZM01 SJDQBM,DQBZM02 DQMC,DQBZM03 DQJB FROM DQBZM WHERE DQBZM01='"+dqbh+"'";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = queryForMap(o2o,sql,resultMap);
		return resultMap;
	}
	
	/**
	 * @todo 获取商品信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appGetSPXX")
	public  Map<String,Object> appGetSPXX(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String barCode = cds.getField("barCode", 0);
			Map spcmMap;
			Map barcodeMap = pubSPCM.checkSPXXExist(barCode);
			if(barcodeMap != null){
				//传13位条码
				int spxx01 = Integer.parseInt(barcodeMap.get("SPXX01").toString());
				String zcxx01 = barcodeMap.get("ZCXX01").toString();
				resultMap = pubSPCM.srchSPXX(zcxx01,spxx01);
				resultMap.put("STATE", "success");
			}else{
				//传串码
				spcmMap = pubSPCM.srchSPCM(barCode);
				if(spcmMap != null){
					//查询商品基础信息
					int spxx01 = Integer.parseInt(spcmMap.get("SPXX01").toString());
					String zcxx01 = spcmMap.get("ZCXX01").toString();
					resultMap = pubSPCM.srchSPXX(zcxx01,spxx01);
					//查询商品串码记录表
					List cmList = pubSPCM.srchSPCMJLB(barCode);
					resultMap.put("cmList", cmList);
					resultMap.put("STATE", "success");
				}else{
					resultMap.put("STATE", "failure");
				}
			}
		}catch(Exception ex){
			logger.info("Error to appGetSPXX."+ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}
	
	/**
	 * @todo 判断商品串码是否存在
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkSPCMExist")
	public  Map<String,Object> checkSPCMExist(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String spcm01 = cds.getField("spcm01", 0);
			Map checkMap = pubSPCM.srchSPCM(spcm01);
			if(checkMap != null){
				resultMap.put("STATE", "success");
			}else{
				resultMap.put("STATE", "failure");
			}
		}catch(Exception ex){
			logger.info("Error to checkSPCMExist."+ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}
	
	/**
	 * @todo 商品串码出入库
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appSPCMInAndOut")
	public  Map<String,Object> appSPCMInAndOut(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			JSONObject parametersMap = JSONObject.fromObject(json);
			//获取新生成的编号
			int  SPCMDR01 = PubFun.updateWBHZT(o2o,"W_SPCMDR",1);
			parametersMap.put("SPCMDR01", new Integer(SPCMDR01));
			parametersMap.put("DJLX", new Integer(2));
			//插入W_SPCMJLB
			int DRSL = pubSPCM.insertW_SPCMJLB(parametersMap);
			parametersMap.put("DRSL", new Integer(DRSL));
			parametersMap.put("SBM", "");
			//插入W_SPCMDR
			pubSPCM.insertW_SPCMDR(parametersMap);
			resultMap.put("STATE", "success");
		}catch(Exception ex){
			logger.info("Error to appSPCMIn."+ex);
			resultMap.put("STATE", "failure");
			throw ex;	
		}
		return resultMap;
	}
	
	/**
	 * @todo 获取商品追溯信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appTraceSPXX")
	public  Map<String,Object> appTraceSPXX(String json) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(json);
			String spcm01 = cds.getField("spcm01", 0);
			Map checkMap = pubSPCM.srchSPCM(spcm01);
			if(checkMap != null){
				resultMap.put("SPXX", pubSPCM.srchSPXX(checkMap.get("ZCXX01").toString(),Integer.parseInt(checkMap.get("SPXX01").toString())));
				resultMap.put("TraceList", pubSPCM.srchSPCMJLB(spcm01));
				resultMap.put("STATE", "success");
			}else{
				resultMap.put("STATE", "failure");
			}
		}catch(Exception ex){
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}
	
}