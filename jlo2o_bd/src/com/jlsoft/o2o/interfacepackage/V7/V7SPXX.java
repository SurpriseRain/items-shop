package com.jlsoft.o2o.interfacepackage.V7;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/V7SPXX")
public class V7SPXX extends JLBill{
	@Autowired
	private V7Public v7Public;
	
	/************
	 * 
	 * @param 抽取V7商品库存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateV7KC")
	public void updateV7KC(Map kcMap) throws Exception{
		/**
		String sql = "";
		sql = "SELECT A.ZCXX59 URL,A.ZCXX60 UserName,A.ZCXX61 PassWord " +
			"FROM W_ZCXX A WHERE A.ZCXX01='0000' ";
		Map map = queryForMap(o2o,sql);
		//查询商品表中最大的时间戳
		sql = "select MAX(erptime01) time01 from w_goods";
		Map kcMap = queryForMap(o2o,sql);
		**/
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", kcMap.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", kcMap.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("Type","Get_SYDJD_KC");//获取类型/Y
		paramterMap.put("ModifyDate",kcMap.get("time01"));//获取类型/Y
		
		//与ERP对接调用
		System.out.println("抽取V7库存参数："+paramterMap.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		String returnStr = JLTools.sendToSync_V7(XmlData.toString(), kcMap.get("URL").toString()+"/JLQueryServlet_BASE/GET_BASE.do");
		
		//将返回值同步到库存
		JSONObject jsonObject = JSONObject.fromObject(returnStr);
		if(jsonObject.getJSONObject("data").get("JL_State").toString().equals("1")){
		JSONArray list = jsonObject.getJSONObject("data").getJSONArray("topics");
		int k=1;
		for(int i=0; i<list.size(); i++){
			JSONObject obj=list.getJSONObject(i);
			/***2015.8.18,ZDKC,ZDJG增加到W_GOODS表后直接更新
			//先查询是否有该条商品信息
			String sql1 = "select count(spxx01) from cksp A WHERE A.CK01='"+obj.getString("CK01")+"' AND A.SPXX01='"+obj.getString("SPXX01")+"' AND A.CKSP12='"+obj.getString("CKSP12")+"'  AND A.GSXX01='"+obj.getString("GSXX01")+"' ";
			Map row1=null;
			int a=queryForInt(o2o,sql1);

			String sql2="";
			cds=new DataSet(list.getJSONObject(i).toString());
			if(a==1){
			sql2="UPDATE CKSP A SET A.CKSP04=CKSP04? WHERE A.CK01=CK01? AND A.SPXX01=SPXX01? AND A.CKSP12=CKSP12? AND A.GSXX01=GSXX01? ";
			}else{
				sql2="INSERT INTO CKSP(CK01,BM01,WLDW01,CKSP12,SPXX01,CKSP01,CKSP02,CKSP03,CKSP04,CKSP05,CKSP10,CKSP13,GSXX01,TIME01) " +
						"VALUES(CK01?,BM01?,WLDW01?,CKSP12?,SPXX01?,CKSP01?,CKSP02?,CKSP03?,CKSP04?,CKSP05?,CKSP10?,CKSP13?,GSXX01?,TIME01?)";
			}
			****/
			String sql2="UPDATE W_GOODS SET ZDKC=CKSP04? Where SPXX01 =SPXX01?";
			int j=execSQL(o2o,sql2,obj);
			k=k++;
		}
		if(k>0){
			System.out.println("更新V7库存成功");
		}else{
			System.out.println("更新V7库存失败");
		}
		}else{
			System.out.println("没有找到需要更新的库存记录");
		}
	}
	
	/************
	 * 
	 * @param 抽取V7商品价格
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateV7JG")
	public void updateV7JG(Map kcMap) throws Exception{
		/**
		String sql = "";
		sql = "SELECT A.ZCXX59 URL,A.ZCXX60 UserName,A.ZCXX61 PassWord " +
			"FROM W_ZCXX A WHERE A.ZCXX01='0000' ";
		Map map = queryForMap(o2o,sql);
		//查询商品表中最大的时间戳
		sql = "select MAX(erptime01) time01 from w_goods";
		Map kcMap = queryForMap(o2o,sql);
		*/
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", kcMap.get("UserName"));//用户名(ISV提供)/Y
		paramterMap.put("PassWord", kcMap.get("PassWord"));//密码(ISV提供)/Y
		paramterMap.put("Type","Get_SYDJD_JG");//获取类型/Y
		paramterMap.put("ModifyDate",kcMap.get("time01"));//获取类型/Y
		
		//与ERP对接调用
		System.out.println("抽取V7商品价格参数："+paramterMap.toString());
		String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
		String returnStr = JLTools.sendToSync_V7(XmlData.toString(), kcMap.get("URL").toString()+"/JLQueryServlet_BASE/GET_BASE.do");
		
		//将返回值同步到库存
		JSONObject jsonObject = JSONObject.fromObject(returnStr);
		if(jsonObject.getJSONObject("data").get("JL_State").toString().equals("1")){
		JSONArray list = jsonObject.getJSONObject("data").getJSONArray("topics");
		int k=1;
		for(int i=0; i<list.size(); i++){
			JSONObject obj=list.getJSONObject(i);
			/***
			//先查询是否有该条商品信息
			String sql1 = "select count(spxx01) from w_spjgb A WHERE A.SPXX01='"+obj.getString("SPXX01")+"' ";
			int a=queryForInt(o2o,sql1);
			
			String sql2="";
			cds=new DataSet(list.getJSONObject(i).toString());
			if(a==1){
			sql2="UPDATE W_SPJGB A SET A.###=###? WHERE A.SPXX01=SPXX01?";
			}else{
				sql2="INSERT INTO CKSP(CK01,BM01,WLDW01,CKSP12,SPXX01,CKSP01,CKSP02,CKSP03,CKSP04,CKSP05,CKSP10,CKSP13,GSXX01,TIME01) " +
						"VALUES(CK01?,BM01?,WLDW01?,CKSP12?,SPXX01?,CKSP01?,CKSP02?,CKSP03?,CKSP04?,CKSP05?,CKSP10?,CKSP13?,GSXX01?,TIME01?)";
			}
			****/
			String sql2="UPDATE W_GOODS SET ZDJG=SPJG05? Where SPXX01 =SPXX01?";
			int j=execSQL(o2o,sql2,obj);
			k=k++;
		}
		if(k>0){
			System.out.println("更新V7商品价格成功");
		}else{
			System.out.println("更新V7商品价格失败");
		}
		}else{
			System.out.println("没有找到需要更新的价格记录");
		}
	}


/************
 * 
 * @param 抽取V7商品信息
 * @return
 * @throws Exception
 */
@RequestMapping("/updateV7SPXX")
public void updateV7SPXX(Map map) throws Exception{
	JSONObject paramterMap = new JSONObject();
	paramterMap.put("UserName", map.get("UserName"));//用户名(ISV提供)/Y
	paramterMap.put("PassWord", map.get("PassWord"));//密码(ISV提供)/Y
	paramterMap.put("Type","Get_SYDJD_SPXX");//获取类型/Y
	//paramterMap.put("Code","Code");//商品代码/N
	paramterMap.put("ModifyDate",map.get("time01"));//最大ERPTime01时间戳to_char(systimestamp,'YYYYMMDDHH24MISSFF6')/Y
	//与ERP对接调用
	System.out.println("抽取V7商品参数："+paramterMap.toString());
	String XmlData = "XmlData=" + URLEncoder.encode(paramterMap.toString(), "utf-8");
	String returnStr = JLTools.sendToSync_V7(XmlData.toString(), map.get("URL").toString()+"/JLQueryServlet_BASE/GET_BASE.do");
	//将返回值同步到库存
	JSONObject jsonObject = JSONObject.fromObject(returnStr);
	if(jsonObject.getJSONObject("data").get("JL_State").toString().equals("1")){
	JSONArray list = jsonObject.getJSONObject("data").getJSONArray("topics");
	int k=1;
	for(int i=0; i<list.size(); i++){
		JSONObject obj=list.getJSONObject(i);
		String sql="UPDATE W_GOODS SET " +
				"SPXX04=SPXX04?"+
				",specification=GGB01?,zcxx01='0000',Modal=SPXH01?"+
						",erptime01=TIME01? Where spxx01 =SPXX01?;";
		int j=execSQL(o2o,sql,obj);
		if(j==0){
			sql="INSERT INTO W_GOODS(SPXX01,SPXX04,specification,zcxx01,Modal,erptime01) " +
			"VALUES(SPXX01?,SPXX04?,GGB01?,'0000',SPXH01?,TIME01?);";
			j=execSQL(o2o,sql,obj);
		}
		k++;
		System.out.print("#####"+list.size());
		System.out.print("*********"+k);
	}
	if(k>1){
		System.out.println("更新V7商品信息成功");
	}else{
		System.out.println("更新V7商品信息失败");
	}
	}else{
		System.out.println("没有找到需要更新的商品记录");
	}
	//同步调用库存和价格
	updateV7KC(map);
	updateV7JG(map);
}

}
