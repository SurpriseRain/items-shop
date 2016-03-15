package com.jlsoft.o2o.interfacepackage.loop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V7.V7LSD;
import com.jlsoft.o2o.interfacepackage.V9.V9XSDD;

@Controller
@RequestMapping("/ErpLSD")
public class ErpLSD extends JLBill{
	@Autowired
	private V7LSD v7lsd;
	@Autowired
	private ManageLogs manageLogs;
	
	/**
	 * @todo 轮询待处理完零售单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleTreatLSD")
	public Map handleTreatLSD() throws Exception {
		Map resultMap = new HashMap();
		String sql = "SELECT A.XSDD01,A.XSDDG02,B.PROVINCE ADD_SHENG,B.CITY ADD_SHI,B.COUNTY ADD_QU,B.OTHERDZ ADD_XQ,B.XSDD21 Receiver_phone,C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord," +
						   "PFD01 LSDH,PFSFK01 FXSKD,XSDD19 SHR,XSDD20 SHDZ,XSDD21 SHRDH," +
						   "DATE_FORMAT(B.XSDD44, '%Y-%m-%d') KHZL16," +
						   "DATE_FORMAT(B.XSDD44, '%H:%i') SHSJ_S," +
						   "DATE_FORMAT(B.XSDD45, '%H:%i') SHSJ_E," +
						   "B.XSDD46 SHQYDM," +
						   "B.XSDD47 SHQYMC," +
						   "B.XSDD48 SJCKMC " +
				           "FROM W_XSDDGROUP A,W_XSDD B,W_ZCXX C,W_DJZX D,W_GSLX E WHERE A.XSDD01=B.XSDD01 " +
				           "AND B.ZTID=C.ZCXX01 AND B.XSDD01=D.W_DJZX01 AND B.HBID=E.GSID AND E.LX=24 AND (A.XSDDG02 = 0 OR A.XSDDG02 = 5) AND D.W_DJZX02=14";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			if(map.get("DJLX") != null){
				if(map.get("DJLX").equals("SCMV7")){
					manageV7LSD(map);
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
	@RequestMapping("/paySucessV7LSD")
	public boolean paySucessV7LSD(String XSDD01) throws Exception{
		boolean flag = false;
		String sql = "SELECT A.XSDD01,A.XSDDG02,B.PROVINCE ADD_SHENG,B.CITY ADD_SHI,B.COUNTY ADD_QU,B.OTHERDZ ADD_XQ,B.XSDD21 Receiver_phone,C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord," +
						   "PFD01 LSDH,PFSFK01 FXSKD,XSDD19 SHR,XSDD20 SHDZ,XSDD21 SHRDH, " +
						   "DATE_FORMAT(B.XSDD44, '%Y-%m-%d') KHZL16," +
						   "DATE_FORMAT(B.XSDD44, '%H:%i') SHSJ_S," +
						   "DATE_FORMAT(B.XSDD45, '%H:%i') SHSJ_E," +
						   "B.XSDD46 SHQYDM," +
						   "B.XSDD47 SHQYMC," +
						   "B.XSDD48 SJCKMC " +
				           "FROM W_XSDDGROUP A,W_XSDD B,W_ZCXX C,W_DJZX D,W_GSLX E WHERE A.XSDD01=B.XSDD01 " +
				           "AND B.ZTID=C.ZCXX01 AND B.XSDD01=D.W_DJZX01 AND B.HBID=E.GSID AND E.LX=24 AND (A.XSDDG02 = 0 OR A.XSDDG02 = 5) AND D.W_DJZX02=14 AND B.XSDD01='"+XSDD01+"'";
		Map map = queryForMap(o2o,sql);
		if(map.get("DJLX") != null){
			if(map.get("DJLX").equals("SCMV7")){
				flag = manageV7LSD(map);
			}
		}
		return flag;
	}
	/**
	 * @todo 处理单个零售店
	 * @param map
	 * @throws Exception 
	 */
	public boolean manageV7LSD(Map map) throws Exception{
		String sql = "";
		String returnStr="";
		Map returnData;
		JSONObject jsonObject;
		boolean flag = true;
		int djzt = Integer.parseInt(map.get("XSDDG02").toString());
		//创建零售单收款
		if(djzt==0||djzt== 5){
			returnStr = v7lsd.createLSD(map);
			System.out.println("############创建零售单V7返回+"+returnStr+"#############");
			jsonObject = JSONObject.fromObject(returnStr);
			returnData = (Map) jsonObject.get("data");
			System.out.println(returnData.get("JL_State").toString().equals("2"));
			if(!returnData.get("JL_State").toString().equals("1")){
				flag=false;
				writeLog(map.get("XSDD01").toString(),returnData.get("JL_ERR").toString());
				return false;
			}else{
				//零售单号
				map.put("JL_RetailCode", returnData.get("JL_RetailCode").toString());
				//订单号
				map.put("Order_Code", returnData.get("Order_Code").toString());
				//更新单据状态
				sql = "UPDATE W_XSDDGROUP SET XSDDG02=5,PFD01='"+returnData.get("JL_RetailCode").toString()+"' WHERE XSDD01='"+map.get("XSDD01").toString()+"'";
				execSQL(o2o, sql, map);
			}
		}
		//零售客户建档
		if(djzt==0||djzt== 5){//零售收款
			returnStr = v7lsd.createLSKHJD(map);
			System.out.println("############创建零售单建档V7返回+"+returnStr+"#############");
			jsonObject = JSONObject.fromObject(returnStr);
			returnData = (Map) jsonObject.get("data");
			if(!returnData.get("JL_State").toString().equals("1")){
				flag=false;
				writeLog(map.get("XSDD01").toString(),returnData.get("JL_ERR").toString());
				return false;
			}else{
				//更新单据状态
				sql = "UPDATE W_XSDDGROUP SET XSDDG02=3,JD01='"+returnData.get("JL_RecordCode").toString()+"' WHERE XSDD01='"+map.get("XSDD01").toString()+"'";
				execSQL(o2o, sql, map);
			}
		}
		
		//当接口执行全部成功后，更新状态为待发货状态
		if(flag){
			sql = "UPDATE W_DJZX SET W_DJZX02=4 WHERE W_DJZX01='"+map.get("XSDD01").toString()+"'";
			System.out.println(sql+"   @@@@@@@@@@@@@@@@@@");
			execSQL(o2o, sql, map);
		}
		return true;
	}

	/**
	 * @todo 查询零售单发货状态接口
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/doLSDFH")
	public boolean doLSDFH() throws Exception{
		boolean flag = false;
		String returnStr="";
		String sql = "SELECT C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord,PFD01 " +
        "FROM W_XSDDGROUP A,W_XSDD B,W_ZCXX C,W_DJZX D WHERE A.XSDD01=B.XSDD01 " +
        "AND B.ZTID=C.ZCXX01 AND B.XSDD01=D.W_DJZX01 AND D.W_DJZX02=4";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			if(map.get("DJLX").equals("SCMV7")){
			returnStr=v7lsd.createLSDFH(map);
			
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			JSONObject obj= jsonObject.getJSONObject("data");
			if((obj.get("JL_State").toString()).equals("1")){
				JSONArray objlist = obj.getJSONArray("topics");
				for(int j=0; j<list.size(); j++){
					JSONObject result=objlist.getJSONObject(i);
					String sql2="UPDATE W_DJZX A SET A.W_DJZX02=5 WHERE A.W_DJZX01 IN (SELECT B.XSDD01 FROM W_XSDDGROUP B WHERE PFD01='"+result.getString("LSD01").toString()+"')";
					int k=execSQL(o2o,sql2,result);
					if(k==1){flag=true;
					System.out.println("发货状态更新成功");}
					else{flag=false;}
				}
			}
			}else{flag=false;}
		}
		return flag;
	}
	/**
	 * @todo 查询零售单退货状态接口
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/doLSDTHZT")
	public boolean doLSDTHZT() throws Exception{
		boolean flag = false;
		String returnStr="";
		String sql = "SELECT C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord,A.PFD01 " +
				"FROM W_THD A,W_ZCXX C WHERE A.ZTID=C.ZCXX01 AND A.THZT=10";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			if(map.get("DJLX").equals("SCMV7")){
			returnStr=v7lsd.createLSDFH(map);
			
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			JSONObject obj= jsonObject.getJSONObject("data");
			if((obj.get("JL_State").toString()).equals("1")){
				JSONArray objlist = obj.getJSONArray("topics");
				for(int j=0; j<list.size(); j++){
					JSONObject result=objlist.getJSONObject(i);
					String sql2="UPDATE W_THD SET THZT=2 WHERE PFD01='"+result.getString("LSD01").toString()+"'";
					int k=execSQL(o2o,sql2,result);
					if(k==1){flag=true;
					System.out.println("零售单退货状态更新成功");}
					else{flag=false;}
				}
			}
			}else{flag=false;}
		}
		return flag;
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
