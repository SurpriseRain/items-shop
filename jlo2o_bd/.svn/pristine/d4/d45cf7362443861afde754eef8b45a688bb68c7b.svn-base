package com.jlsoft.o2o.interfacepackage.loop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V9.V9XSDD;

@Controller
@RequestMapping("/ErpXSDD")
public class ErpXSDD extends JLBill{
	@Autowired
	private V9XSDD v9XSDD;
	@Autowired
	private ManageLogs manageLogs;
	
	/**
	 * @todo 轮询待处理完销售单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleTreatXSDD")
	public Map handleTreatXSDD() throws Exception {
		Map resultMap = new HashMap();
		String sql = "SELECT A.XSDD01,A.XSDDG02,C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord," +
						   "PFD01 FXDH,PFSFK01 FXSKD,XSDD19 SHR,XSDD20 SHDZ,XSDD21 SHRDH " +
				           "FROM W_XSDDGROUP A,W_XSDD B,W_ZCXX C,W_DJZX D WHERE A.XSDD01=B.XSDD01 " +
				           "AND B.ZTID=C.ZCXX01 AND B.XSDD01=D.W_DJZX01 AND A.XSDDG02 <> 2 AND D.W_DJZX02=14";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			if(map.get("DJLX") != null){
				if(map.get("DJLX").equals("V9")){
					manageV9XSDD(map);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * @todo 付款成功调用ERP
	 * @param XSDD01
	 * @return
	 * @throws Exception 
	 */
	public boolean paySucessV9XSDD(String XSDD01) throws Exception{
		boolean flag = false;
		String sql = "SELECT A.XSDD01,A.XSDDG02,C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord," +
		   				   "A.PFD01 FXDH,A.PFSFK01 FXSKD,B.XSDD19 SHR,B.XSDD20 SHDZ,B.XSDD21 SHRDH " +
                           "FROM W_XSDDGROUP A,W_XSDD B,W_ZCXX C,W_DJZX D WHERE A.XSDD01=B.XSDD01 " +
                           "AND B.ZTID=C.ZCXX01 AND B.XSDD01=D.W_DJZX01 AND A.XSDDG02 <> 2 AND D.W_DJZX02=14 AND B.XSDD01='"+XSDD01+"'";
		Map map = queryForMap(o2o,sql);
		if(map.get("DJLX") != null){
			if(map.get("DJLX").equals("V9")){
				flag = manageV9XSDD(map);
			}
		}
		return flag;
	}
	
	/**
	 * @todo 处理单个销售单
	 * @param map
	 * @throws Exception 
	 */
	public boolean manageV9XSDD(Map map) throws Exception{
		String sql = "";
		String returnStr="";
		JSONObject returnData;
		JSONObject jsonObject;
		boolean flag = true;
		int djzt = Integer.parseInt(map.get("XSDDG02").toString());
		//创建分销单
		if(djzt == 0){
			returnStr = v9XSDD.createFXD(map);
			System.out.println("分销制单返回值："+returnStr);
			jsonObject = JSONObject.fromObject(returnStr);
			returnData = (JSONObject) jsonObject.get("data");
			if(!returnData.getString("JL_State").equals("1")){
				flag=false;
				writeLog(map.get("XSDD01").toString(),returnData.get("JL_ERR").toString());
				return false;
			}else{
				//存放分销单号
				map.put("FXDH", returnData.get("JL_OrderCode").toString());
				//更新单据状态
				sql = "UPDATE W_XSDDGROUP SET XSDDG02=1,PFD01='"+returnData.get("JL_OrderCode").toString()+"' WHERE XSDD01='"+map.get("XSDD01").toString()+"'";
				execSQL(o2o, sql, map);
			}
		}
		//创建分销单收款
		if(djzt == 0 || djzt ==1){
			returnStr = v9XSDD.createFXDSK(map);
			System.out.println("分销收款返回值："+returnStr);
			jsonObject = JSONObject.fromObject(returnStr);
			returnData = (JSONObject) jsonObject.get("data");
			if(!returnData.getString("JL_State").equals("1")){
				flag=false;
				writeLog(map.get("XSDD01").toString(),returnData.get("JL_ERR").toString());
				return false;
			}else{
				//存放分销收款单
				map.put("FXSKD", returnData.get("JL_OrderCode").toString());
				//更新单据状态
				sql = "UPDATE W_XSDDGROUP SET XSDDG02=2,PFSFK01='"+returnData.get("JL_OrderCode").toString()+"' WHERE XSDD01='"+map.get("XSDD01").toString()+"'";
				execSQL(o2o, sql, map);
			}
		}
		//客户建档
		/**
		if(djzt == 0 || djzt ==1 || djzt == 2){
			returnStr = v9XSDD.createKHJD(map);
			jsonObject = JSONObject.fromObject(returnStr);
			returnData = (Map) jsonObject.get("data");
			if(!returnData.get("JL_State").equals("1")){
				flag=false;
				writeLog(map.get("XSDD01").toString(),returnData.get("JL_ERR").toString());
				return false;
			}else{
				//更新单据状态
				sql = "UPDATE W_XSDDGROUP SET XSDDG02=3,JD01='"+returnData.get("JL_RecordCode").toString()+"' WHERE XSDD01='"+map.get("XSDD01").toString()+"'";
				execSQL(o2o, sql, map);
			}
		}
		*/
		//当接口执行全部成功后，更新状态为待发货状态
		if(flag){
			sql = "UPDATE W_DJZX SET W_DJZX02=4 WHERE W_DJZX01='"+map.get("XSDD01").toString()+"'";
			System.out.println(sql+"   @@@@@@@@@@@@@@@@@@");
			execSQL(o2o, sql, map);
		}
		return true;
	}
	
	/**
	 * @todo 公共写日志
	 * @param xsdd01
	 * @param error
	 * @throws Exception 
	 */
	public void writeLog(String xsdd01,String error) throws Exception{
		Map errorMap=new HashMap();
		errorMap.put("DJLX", "2");//单据类型（默认为0）
		errorMap.put("YWDH", xsdd01);//业务单号
		errorMap.put("DFHM", "");//对方单号（默认为空）
		errorMap.put("CZR", "");//操作人
		errorMap.put("RZZT", "0");//日志状态（0失败;1成功）
		errorMap.put("ERROR",error);//错误信息
		manageLogs.writeLogs(errorMap);
	}
}
