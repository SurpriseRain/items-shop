package com.jlsoft.o2o.product.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.interfacepackage.jlinterface.GopInterface;
import com.jlsoft.o2o.interfacepackage.jlinterface.JlInterfaces;
import com.jlsoft.o2o.order.service.RequestOauthUtil;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @breif 前台我的商品管理相关操作管理
 *
 */
@Controller
@RequestMapping("/SydjtGoods")
public class SydjtGoods extends JLBill{
	@Autowired
	private GopInterface gopInterface;
	@Autowired
	private ManageLogs manageLogs;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9BasicData v9BasicData;
	private String path = JlAppResources.getProperty("trace_url");
	// 获取分类信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/showFL.action")
	public List<Map<String,Object>> showFL(String XmlData) throws Exception{
		JSONArray jsonList = JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>) jsonList.get(0);
		List<Map<String, Object>> list = null;
		SqlSession session = null;
		try{
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		list = session.selectList("com.jlsoft.o2o.sql.goods.select_spfl", hm);
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw e;
	    }finally{
	    	session.close();
	    }
		return list;
	}
	// 获取品牌信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/showPP.action")
	public List<Map<String,Object>> showPP(String XmlData) throws Exception{
		JSONArray jsonList = JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>) jsonList.get(0);
		List<Map<String, Object>> list = null;
		SqlSession session = null;
		try{
		session = JlAppSqlConfig.getO2OSqlMapInstance();
		list = session.selectList("com.jlsoft.o2o.sql.goods.select_ppb", hm);
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw e;
	    }finally{
	    	session.close();
	    }
		return list;
	}
	/*商品源信息
	 * W_GOODS
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertW_GOODS.action")
	public Map insertW_GOODS(String XmlData)throws Exception{
		Map map=new HashMap();
		try{
		cds = new DataSet(XmlData);
		System.out.println(XmlData);
		Integer SPXX01=PubFun.updateWBHZT(o2o,"W_SPXX",1);
		String sql="INSERT INTO W_GOODS(BARCODE,SPXX04,ORIGIN_COUNTRY,ORIGIN_CPLACE,ORIGIN_PLACE_CODE,ORIGIN_EPLACE,   "+
					"HIGH,WIDTH,DEPTH,HIGH_UNIT,WIDTH_UNIT,DEPTH_UNIT,CATEGORY_CODE,BRAND,KEYWORD,DESCRIPTION,PACKAGING_MATERIAL,PACKAGING_TYPE,  "+
					"SPECIFICATION,QUALITY_GUARANTEE_PERIOD,SUPPLIER,GOODS_STATUS,LISTDATE,SPXX01,ZCXX01) VALUES (BARCODE?,SPXX04?,ORIGIN_COUNTRY?,   "+
					"ORIGIN_CPLACE?,ORIGIN_PLACE_CODE?,ORIGIN_EPLACE?,HIGH?,WIDTH?,DEPTH?,HIGH_UNIT?,WIDTH_UNIT?,DEPTH_UNIT?,CATEGORY_CODE?,BRAND?, "+
					"KEYWORD?,DESCRIPTION?,PACKAGING_MATERIAL?,PACKAGING_TYPE?,   "+
					"SPECIFICATION?,QUALITY_GUARANTEE_PERIOD?,SUPPLIER?,GOODS_STATUS?,LISTDATE?,'"+SPXX01+"',ZCXX01?) ";
		int num=execSQL(o2o, sql, 0);
		if(num==1){
			map.put("STATE", "1");
		}else{
			map.put("STATE", "0");
		}
		}catch(Exception e){
			map.put("STATE", "0");
	    	e.printStackTrace();
	    	throw e;
	    }
		return map;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkExistSpmc.action")
	public Map checkExistSpmc(String XmlData) throws Exception{
		Map map=new HashMap();
		cds = new DataSet(XmlData);
		try{
			String sql="SELECT COUNT(*) NUM FROM SPXX WHERE SPXX04='"+cds.getField("spname", 0)+"'";
			int i=queryForInt(scm, sql);
			if(i==0){
				map.put("STATE", "0");
			}else{
				map.put("STATE", "1");
			}
		}catch(Exception e){
			map.put("STATE", "1");
	    	e.printStackTrace();
	    	throw e;
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkExistKhlx.action")
	public Map checkExistKhlx(String XmlData) throws Exception{
		Map map=new HashMap();
		cds = new DataSet(XmlData);
		try{
			String sql="SELECT KHLX01,KHLX02 FROM W_KHLX ORDER BY KHLX01";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw e;
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteW_SPXX.action")
	public Map deleteW_SPXX(String XmlData) throws Exception{
		Map map=new HashMap();
		cds = new DataSet(XmlData);
		try{
			String sql="SELECT COUNT(*) NUM FROM SPXX WHERE SPXX04='"+cds.getField("spname", 0)+"'";
			int i=queryForInt(scm, sql);
			if(i==0){
				map.put("STATE", "0");
			}else{
				map.put("STATE", "1");
			}
		}catch(Exception e){
			map.put("STATE", "1");
	    	e.printStackTrace();
	    	throw e;
		}
		return map;
	}
	
	/*商品信息
	 * 1.V7 商品信息生成  去编号 SPXX01=PubFun.updateBHZTJC(scm,"SPXX");
	 * 2.INSERT INTO SPXX(SPXX01,SPXX02,SPXX04,SPFL01,PPB01) VALUES(1,000001,'MC','0101','000001');内码 编码 名称 商品分类  品牌
	 * 3.SELECT (REC_NUM+1) A FROM W_BHZT WHERE TBLNAME='W_SPXX';  
	 * 4.INSERT INTO W_SPXXDZ(SPXX01,ERP_SPXX01,ERP01,TIME01,CKSP12)  VALUES (A,SPXX01,ZCXX01?,TIME01?,0) 
	 * O2O编码 SCM内码 注册公司 时间戳  商品属性
	 * 5.INSERT INTO W_SPXX
	 * 6.INSERT INTO W_SPGL
	 * 7.INSERT INTO W_SPJGB
	 * 8.5.INSERT INTO W_KCXX
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertNewGoods.action")
	public Map insertNewGoods(HttpServletRequest request,
			HttpServletResponse response,String XmlDataa,String spjs,String spcs,String bzqd)throws Exception{
		Map map=new HashMap();
		try{
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			List<MultipartFile> listFile= mrRequest.getFiles("files");
			List<MultipartFile> listFile1= mrRequest.getFiles("filecors");
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
		System.out.println(request.getParameter("spjs")+"---"+request.getParameter("spcs"));
		String XmlData = JLTools.unescape(request.getParameter("XmlData"));//商品介绍
		cds = new DataSet(XmlData);
		JSONArray jsonarry = JSONArray.fromObject(cds.getField("sxlxz", 0));
		System.out.println(cds.getField("spid", 0)=="");
		Integer spid;
		spid=0;//Integer.parseInt(cds.getField("spid", 0)==""?"0":cds.getField("spid", 0));
		String spfl01=cds.getField("SPFL03_CODE", 0);
		System.out.println("spid===="+spid);
		int g=1;
		int b=1;
		int c=1;
		int d=1;
		int e=1;
		int f=1;
		int k=1;
		int m=1;
		int n=1;
		String Sql="";
			if(spid==0){//没有SPID就新增
				spid=PubFun.updateWBHZT(o2o,"W_SXSP",1);
				String sxsp05 = JLTools.getID_X(spid, 6);
				Sql="insert into w_sxsp (ZCXX01,SXSP01,SXSP02,SXSP03,SXSP04,SXSP05)values(ZCXX01?,'"+spid+"',SXSP02?,NOW(),SXSP04?,'"+sxsp05+"')";
				//商品属性
				m=insertSpflsx(XmlData,spid.toString());
				Map rowg=getRow(Sql,null,0);
				g=execSQL(o2o, Sql, rowg);
				Sql="select SXLX01 code,SXLX02 name from w_sxlx where yxbj=1 and spfl01='"+spfl01+"'";
				List<Map<String,String>> sxlxs = queryForList(o2o, Sql);
				for(int i=0;i<sxlxs.size();i++){
					Map temp =(Map)sxlxs.get(i);
					Sql="select sxlxz01 code,sxlxz02 name from w_sxlxz where ZCXX01='"+cds.getField("ZCXX01", 0)+"' and sxlx01='"+temp.get("code").toString()+"' and yxbj='1' order by sxlxz01";
					List sxlxzs = queryForList(o2o, Sql);
					String codeString="";
					String nameString="";
					for(int j=0;j<sxlxzs.size();j++){
						Map temp1 = (Map)sxlxzs.get(j);
						if(j==sxlxzs.size()-1){
							codeString +=temp1.get("code");
							nameString +=temp1.get("name");
						}else{
							codeString +=temp1.get("code")+",";
							nameString +=temp1.get("name")+",";
						}
					}
					temp.put("SPSXXH02", codeString);
					temp.put("SPSXXH04", nameString);
					//int xh=PubFun.updateWBHZT(o2o,"w_spsxxh",1);
					Sql="insert into w_spsxxh(zcxx01,sxsp01,sxlx01,spsxxh01,spsxxh02,spsxxh03,spsxxh04)values('"+cds.getField("ZCXX01", 0)+"','"+spid+"','"+temp.get("code")+"','"+(i+1)+"','"+temp.get("SPSXXH02")+"','"+temp.get("name")+"','"+temp.get("SPSXXH04")+"')";
					int spxh=execSQL(o2o, Sql, 0);
				}
			}
			else{
				
			}
			String spxx02=JLTools.getID_X(spid, 6);
			spjs = JLTools.unescape(request.getParameter("spjs"));//商品介绍
			spcs = JLTools.unescape(request.getParameter("spcs"));//商品参数
			bzqd = JLTools.unescape(request.getParameter("bzqd"));//包装清单15.8.6沈阳项目用
			JSONArray json= JSONArray.fromObject(XmlData);
			List<Map<String, Object>> listJson=(List<Map<String,Object>>)json;
			Map hm=listJson.get(0);
			hm.put("spjs", spjs);
			hm.put("spcs", spcs);
			hm.put("bzqd", bzqd);//15.8.6沈阳项目用
			hm.put("spid", spid);
			hm.put("spxx02", spxx02);
			Map maps=updateSpbj(request,response,hm);
			int j=1;
			if("1"==maps.get("STATE")||""==maps.get("STATE")){
				j=1;
			}else{
				j=0;
				throw new Exception("图片上传出错!");
			}
			
			String SPFL01=cds.getField("SPFL01", 0);
			String SPFL01_CODE=cds.getField("SPFL01_CODE", 0);
			//Map mapflmc=getSPFLMC(SPFL01_CODE);
			String SPFL01_NAME=cds.getField("SPFL01_NAME", 0);
			String SPFL02_CODE=cds.getField("SPFL02_CODE", 0);
			//mapflmc=getSPFLMC(SPFL02_CODE);
			String SPFL02_NAME=cds.getField("SPFL02_NAME", 0);
			//长宽高重量
			String SPXX09=cds.getField("SPXX09", 0);
			String SPXX10=cds.getField("SPXX10", 0);
			String SPXX11=cds.getField("SPXX11", 0);
			String SPXX12=cds.getField("SPXX12", 0);
			if(SPXX09.equals("")){SPXX09="0";}
			if(SPXX10.equals("")){SPXX10="0";}
			if(SPXX11.equals("")){SPXX11="0";}
			if(SPXX12.equals("")){SPXX12="0";}
			Integer sxsp01=spid;;
			Integer xh=0;
			for(int i=0;i<jsonarry.size();i++){
				JSONObject obj=(JSONObject) jsonarry.get(i);
				System.out.println(obj.toString());
				spid = new Integer(obj.getString("spid").toString());
				Integer ii=new Integer(obj.getString("spid"));
				String SPSXZ01=obj.getString("SPSXZ01")==null?"":obj.getString("SPSXZ01").toString();
				String SPSXZ02=obj.getString("SPSXZ02")==null?"":obj.getString("SPSXZ02").toString();
				String SPSXZ03=obj.getString("SPSXZ03")==null?"":obj.getString("SPSXZ03").toString();
				String SPSXZ04=obj.getString("SPSXZ04")==null?"":obj.getString("SPSXZ04").toString();
				String SPSXZ05=obj.getString("SPSXZ05")==null?"":obj.getString("SPSXZ05").toString();
				xh=PubFun.updateWBHZT(o2o,"W_SPSXZ",1);
				int jj=ii.intValue();
				Sql="INSERT INTO W_SPSXZ(ZCXX01,SXSP01,SPXX01,SPSXZ01,SPSXZ02,SPSXZ03,SPSXZ04,SPSXZ05,SXH)VALUES(ZCXX01?,'"+sxsp01+"','"+spid+"','"+SPSXZ01+"','"+SPSXZ02+"','"+SPSXZ03+"','"+SPSXZ04+"','"+SPSXZ05+"','"+xh+"')";
				Map row0=getRow(Sql,null,0);
				int a=execSQL(o2o,Sql,row0);
				Sql="INSERT INTO W_SPXXDZ(SPXX01,ERP_SPXX01,ERP01,TIME01,CKSP12)  " +
						"VALUES ("+spid+",'"+JLTools.getID_X(jj, 6)+"',ZCXX01?,TIME01?,0)";
				Map row=getRow(Sql,null,0);
				b=execSQL(o2o,Sql,row);
				Sql="INSERT INTO W_SPXX(SPXX01,SPXX02,SPXX04,SPFL01,SPFL02,PPB01,PPB02,SPFL01_CODE,SPFL01_NAME,SPFL02_CODE,SPFL02_NAME,jldw01,ggxh,SPXX09,SPXX10,SPXX11,SPXX12)" +
				" VALUES("+spid+",'"+JLTools.getID_X(spid, 6)+"','"+obj.getString("spname")+"','"+cds.getField("SPFL03_CODE", 0)+"','"+cds.getField("SPFL03_NAME", 0)+"',PPB01?,PPB02?,'"+SPFL01_CODE+"','"+SPFL01_NAME+"','"+SPFL02_CODE+"','"+SPFL02_NAME+"','"+obj.getString("jldw01")+"','"+obj.getString("ggxh")+"','"+SPXX09+"','"+SPXX10+"','"+SPXX11+"','"+SPXX12+"')";
				Map row2=getRow(Sql,null,0);
				c=execSQL(o2o,Sql,row2);
				Sql="INSERT INTO W_SPGL(SPXX01,ZCXX01,SPGL02,SPGL03,SPGL04,SPGL08,SPGL09,SPGL10,SPGL11,SPGL12,SPGL13,SPGL14,SPGL15,SPGL16,SPGL18,CKSP12,SPGL24,TIME01,SPGL25,SPGL26,SPGL27,SPGL28)" +
				" VALUES("+spid+",ZCXX01?,dtbj?,sysdate(),'"+obj.getString("SPJGB02")+"',SPGL08?,SPGL09?,SPGL10?,SPGL11?,1,0,1,999,0,sysdate(),0,SPGL24?,nowts(),sycxa?,spgl26?,spgl27?,spgl28?)";
				Map rows=getRow(Sql,null,0);
				f=execSQL(o2o,Sql,rows);
				n = insertW_SPGLLJH(XmlData,spid.toString());
				/***沈阳项目。插入平台库存**/
				Sql="INSERT INTO W_KCXX(ZCXX01,SPXX01,CKSP04,CK01) " +
						"VALUES(ZCXX01?,'"+spid+"','"+obj.getString("kcsl")+"',ZCXX01?)";
				Map row3=getRow(Sql,null,0);
				d=execSQL(o2o,Sql,row3);
				/********************/
				Sql="INSERT INTO W_SPJGB (ZCXX01,SPXX01,SPJGB01,SPJGB02) "+
				"VALUES(ZCXX01?,'"+spid+"',SPJGB01?,'"+obj.getString("SPJGB02")+"')";
				Map row4=getRow(Sql,null,0);
				e=execSQL(o2o,Sql,row4);
				
				Sql="UPDATE W_GOODS SET " +
								"goods_status=1 "+
								"WHERE BARCODE='"+obj.getString("BARCODE")+"' AND SPXX01='"+spid+"' AND ZCXX01=ZCXX01?";
			    Map rowg=getRow(Sql,null,0);
				g=execSQL(o2o, Sql, rowg);
				int num=b*c*d*e*f*j*k*g*m*n;
				if(num==1){
					//updateSPFBBJ(String.valueOf(spid.intValue()));//沈阳兴隆，商品发布成功后更新w_goods有效标记
					map.put("STATE", "1");
				}else{
					map.put("STATE", "0");
					throw new Exception("数据插入异常!");
				}
			}
		}catch(Exception e){
			map.put("STATE", "0");
	    	e.printStackTrace();
	    	throw e;
	    }
		return map;
	}
	
	public int insertW_SPGLLJH(String XmlData,String spid) throws Exception{
		int n=1;
		cds = new DataSet(XmlData);
		try {
			String sqldel="DELETE FROM W_SPGLLJH WHERE SPXX01="+spid+" AND ZCXX01="+cds.getField("ZCXX01", 0);
			Map rowD = getRow(sqldel, null, 0);
			execSQL(o2o, sqldel, rowD);
			String spglljhs=cds.getField("SPGLLJH", 0).replace("[", "").replace("]", "").replace("\"", "");
			String[] spglljh= spglljhs.split(",");
			if(!spglljh.equals("")&&spglljh.length > 0){
				for(int i=0;i<spglljh.length;i++){
					String spglljh01=spglljh[i];
					String sql="INSERT INTO W_SPGLLJH(ZCXX01,SPXX01,SPGLLJH01) VALUES(ZCXX01?,"+spid+",'"+spglljh01+"')";
					Map row = getRow(sql, null, 0);
					execSQL(o2o, sql, row);
				}
			}
			n=1;
		} catch (Exception e) {
			e.printStackTrace();
			n=0;
		}
		return n;
	}
	
	public Map getSPFLMC(String SPFL01) throws Exception{
		Map map=new HashMap();
		String sql1="SELECT SPFL02 FROM SPFL WHERE SPFL01='"+SPFL01+"'";
		map=queryForMap(o2o, sql1);
		return map;
	} 
	
	@SuppressWarnings("unchecked")
	public int insertSpflsx(String XmlData,String spid) throws Exception{
		int n=1;
		cds = new DataSet(XmlData);
		try {	
		String sqlDete="DELETE FROM W_SPSX WHERE ZCXX01=ZCXX01? AND SPXX01="+spid;
		Map row = getRow(sqlDete, null, 0);
		execSQL(o2o, sqlDete, row);
		/***
		String sxz = "";
		String sxzdms=cds.getField("spsxz", 0);
		String[] sxzs= sxzdms.split(",");
		if(!sxzdms.equals("")&&sxzs.length > 0){
			for(int i=0;i<sxzs.length;i++){
				sxz = sxzs[i];
				String sxzdm=sxz.split("&")[0];
				String sxzname=sxz.split("&")[1];
				String sql1="INSERT INTO W_SPSX(ZCXX01,SPXX01,SXZDM,SXZNAME) VALUES(ZCXX01?,"+spid+",'"+sxzdm+"','"+sxzname+"')";
				row = getRow(sql1, null, 0);
				execSQL(o2o, sql1, row);
				}
			}
			***/
		int i = Integer.parseInt(cds.getField("sumsx", 0));
		if(i>0){
			for(int j=0; j<i; j++){
				String spsxz = "spsxz_"+j;
				String sxz = cds.getField(spsxz, 0);
				String sql = "INSERT INTO W_SPSX(ZCXX01,SPXX01,SXZDM,SXZNAME) VALUES(ZCXX01?,"+spid+",'"+sxz+"',(select sxzname from w_spflsxitem where sxzdm='"+sxz+"'))";
				row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
			}
		}
		n=1;
		} catch (Exception e) {
			e.printStackTrace();
			n=0;
		} 
		return n;
	}
	
	@SuppressWarnings("unchecked")
	public int insertKHSP(String XmlData,String spid) throws Exception{
		int n=1;
		cds = new DataSet(XmlData);
		try {	
		String sqllx="SELECT KHLX01,KHLX02 FROM W_KHLX ORDER BY KHLX01";
		List list =queryForList(o2o, sqllx);
		System.out.println(((Map)list.get(0)).get("KHLX01"));
		int KHSPJG01=PubFun.updateWBHZT(o2o,"W_KHSPJG",1);
		int a=0;
		int b=0;
		int c=0;
		String sql="INSERT INTO W_KHSPJG(KHSPJG01,ZCXX01,KHSPJG02,KHSPJG03)VALUES('"+KHSPJG01+"',ZCXX01?,sysdate(),sysdate())";
		Map row=getRow(sql, null, 0);
		a=execSQL(o2o, sql, row);
		for(int i=0;i<list.size();i++){
			String KHSPJGI01="SPJG0"+String.valueOf(i);
			
			System.out.println(KHSPJGI01+"CDS:"+cds.getField("lsj", 0));
			String isexit="SELECT COUNT(*) COUNT FROM W_KHSPJGB WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"' AND SPXX01='"+spid+"' AND KHLX01='"+((Map)list.get(i)).get("KHLX01")+"'";
			int count=queryForInt(o2o, isexit);
			if(count==0){
				KHSPJG01=PubFun.updateWBHZT(o2o,"W_KHSPJG",1);//记录编号
				String sql2="INSERT INTO W_KHSPJGITEM(KHSPJG01,ZCXX01,SPXX01,KHLX01,KHSPJGI01)VALUES('"+KHSPJG01+"',ZCXX01?,'"+spid+"','"+((Map)list.get(i)).get("KHLX01")+"','"+cds.getField(KHSPJGI01, 0)+"')";
				Map row2=getRow(sql, null, 0);
				b=execSQL(o2o, sql2, row2);
				String sql3="INSERT INTO W_KHSPJGB(ZCXX01,SPXX01,KHLX01,KHSPJGI01)VALUES(ZCXX01?,'"+spid+"','"+((Map)list.get(i)).get("KHLX01")+"','"+cds.getField(KHSPJGI01, 0)+"')";
				Map row3=getRow(sql3, null, 0);
				c=execSQL(o2o, sql3, row3);
			}else{
				String sql22="UPDATE W_KHSPJGITEM SET KHSPJGI01='"+cds.getField(KHSPJGI01, 0)+"'WHERE ZCXX01=ZCXX01? AND SPXX01='"+spid+"' AND KHLX01='"+((Map)list.get(i)).get("KHLX01")+"'";
				Map row22=getRow(sql22, null, 0);
				b=execSQL(o2o, sql22, row22);
				String sql33="UPDATE W_KHSPJGB SET KHSPJGI01='"+cds.getField(KHSPJGI01, 0)+"'WHERE ZCXX01=ZCXX01? AND SPXX01='"+spid+"' AND KHLX01='"+((Map)list.get(i)).get("KHLX01")+"'";
				Map row33=getRow(sql, null, 0);
				c=execSQL(o2o, sql33, row33);
			}
		}
		n=a*b*c;
		} catch (Exception e) {
			n=0;
			throw new Exception("客户商品插入出错!");
		}
		return n;
	}
	
	//商品编辑--更新商品信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateSpbj.action")
	public Map updateSpbj(HttpServletRequest request,
			HttpServletResponse response,Map hm) throws DataAccessException, Exception{
 		Map map=new HashMap();
		String spjs=hm.get("spjs").toString();
		String spcs="";
		/*****15.8.6 沈阳项目用******/
		spcs=hm.get("spcs").toString();
		String bzqd=hm.get("bzqd").toString();
		/*****/
		try {
		//商品介绍
		String sql2="UPDATE W_SPJS SET SPJS01='"+spjs+"',SPJS02='"+spcs+"' WHERE ZCXX01='"+hm.get("ZCXX01")+"' AND SPXX01="+hm.get("spid");
		/*****15.8.6 沈阳项目用******/
		if(bzqd !="" || bzqd !=null){
			sql2="UPDATE W_SPJS SET SPJS01='"+spjs+"',SPJS02='"+spcs+"',SPJS03='"+bzqd+"' WHERE ZCXX01='"+hm.get("ZCXX01")+"' AND SPXX01="+hm.get("spid");
		}else{}
		/*****/
		Map row = getRow(sql2, null, 0);
		int num=execSQL(o2o, sql2, row);
		if(num==0){
			String sql3="INSERT INTO W_SPJS(ZCXX01,SPXX01,SPJS01,SPJS02,SPJS03) VALUES('"+hm.get("ZCXX01")+"',"+hm.get("spid")+",'"+spjs+"','"+spcs+"','"+bzqd+"')";
			row = getRow(sql3, null, 0);
			execSQL(o2o, sql3, row);
			}
		//生成静态页面
		String htmlName = hm.get("spxx02").toString()+"_spym.html";
		JLTools.createHtmlMethod(htmlName,spjs, request, response);
		htmlName = hm.get("spxx02").toString()+"_spcs.html";
		JLTools.createHtmlMethod(htmlName,spcs, request, response);
		/*****15.8.6 沈阳项目用******/
		if(bzqd !="" || bzqd !=null){
			htmlName = hm.get("spxx02").toString()+"_spbz.html";
			JLTools.createHtmlMethod(htmlName,bzqd, request, response);
		}else{}
		/*****/
		//写商品图片
		createSPTPFile(hm,request,map);
		//写商品属性值图片
		JSONArray jsonsx = JSONArray.fromObject(hm.get("sxfile")==""?"[]":hm.get("sxfile"));
		createSPSXTPFile(hm,jsonsx,request, map);
		map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		} 
		System.out.println(map);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createSPTPFile(Map hm,HttpServletRequest request , Map map) throws DataAccessException, Exception{
		MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
		List<MultipartFile> listFile= mrRequest.getFiles("files");
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		//Map row ;
		if(!upload.isMultipartContent(request)){
			map.put("STATE", "0");
			return map;
		}
		int sxh = 0;
		String tpName = "";
		for(int i=0;i<listFile.size();i++){
			MultipartFile file=listFile.get(i);
			String fileName=file.getOriginalFilename();//上传的原图片名+后缀
			String nameType=fileName.substring(fileName.lastIndexOf(".")+1);//文件名后缀,jpg,png...
			if(file.getSize()>0){
				sxh=i + 1;
				tpName=hm.get("spxx02").toString()+"_"+sxh+"."+nameType;//新图片名加上后缀
				String sql1="UPDATE W_SPTP SET SPTP02='"+tpName+"' WHERE ZCXX01='"+hm.get("ZCXX01").toString()+"' AND SPXX01='"+hm.get("spid").toString()+"' AND SPTP01='"+sxh+"'";
				Map row = getRow(sql1, null, 0);
				int m=execSQL(o2o, sql1, row);
				if(m==0){
					String sql2="INSERT INTO W_SPTP(ZCXX01,SPXX01,SPTP01,SPTP02) VALUES('"+hm.get("ZCXX01").toString()+"','"+hm.get("spid").toString()+"','"+sxh+"','"+tpName+"')";
					Map row2 = getRow(sql2, null, 0);
					execSQL(o2o, sql2, row2);
				}
				hm.put("sxpxx02",hm.get("spxx02"));//图片名称
				hm.put("path", "path_sptp");//图片地址
				//生成商品原图
				String newTpName=tpName.split("\\.")[0]+"_big."+tpName.split("\\.")[1];
				uploadImg(newTpName,request,hm,map,file);
				//压缩至原图四分之一
				String TpName=newTpName.replace("big", "small");
				uploadScaleImg(TpName,25,request,hm,map,file);
				//压缩至原图二分之一
				TpName=newTpName.replace("big", "mid");
				uploadScaleImg(TpName,50,request,hm,map,file);
				
			}
			map.put("STATE", "1");
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> createSPSXTPFile(Map hm,JSONArray jsonarry,HttpServletRequest request , Map map) throws DataAccessException, Exception{
		MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
		List<MultipartFile> listFile= mrRequest.getFiles("filecors");
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		//Map row ;
		if(!upload.isMultipartContent(request)){
			map.put("STATE", "0");
			return map;
		}
		int sxh = 0;
		String tpName = "";
		int spid=0;
		String spxx02="";
		for(int i=0;i<listFile.size();i++){
			JSONObject obj = (JSONObject) jsonarry.get(i);
			spid = new Integer(obj.getString("code"));
			spxx02 = JLTools.getID_X(spid, 6);
			MultipartFile file=listFile.get(i);
			String fileName=file.getOriginalFilename();//上传的原图片名+后缀
			String nameType=fileName.substring(fileName.lastIndexOf(".")+1);//文件名后缀,jpg,png...
			if(file.getSize()>0){
				sxh=i + 1;
				tpName=spxx02+"."+nameType;//新图片名加上后缀
				String sql1="UPDATE W_SXSPTP SET SXSPTP01='"+tpName+"' WHERE ZCXX01='"+hm.get("ZCXX01").toString()+"' AND SXSP01='"+hm.get("spid").toString()+"' AND SXLXZ01='"+obj.getString("code")+"'";
				Map row = getRow(sql1, null, 0);
				int m=execSQL(o2o, sql1, row);
				if(m==0){
					String sql2="INSERT INTO W_SXSPTP(ZCXX01,SXSP01,SXLXZ01,SXSPTP01)VALUES('"+hm.get("ZCXX01").toString()+"','"+hm.get("spid").toString()+"','"+obj.getString("code")+"','"+tpName+"')";
					Map row2 = getRow(sql2, null, 0);
					execSQL(o2o, sql2, row2);
				}
				hm.put("sxpxx02", hm.get("spxx02").toString()+"\\"+spxx02);//图片名称
				hm.put("path", "path_sxsptp");//图片地址
				//生成商品原图
				String newTpName=tpName.split("\\.")[0]+"_big."+tpName.split("\\.")[1];
				uploadImg(newTpName,request,hm,map,file);
				//压缩至原图四分之一
				String TpName=newTpName.replace("big", "small");
				uploadScaleImg(TpName,25,request,hm,map,file);
				//压缩至原图二分之一
				/*TpName=newTpName.replace("big", "mid");
				uploadScaleImg(TpName,50,request,hm,map,file);*/
				
			}
			map.put("STATE", "1");
		}
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> uploadImg(String newTpName,HttpServletRequest request,Map hm,Map map,MultipartFile file) throws Exception{
		 BufferedOutputStream bos = null;
		 String filepath;
		 String path_sptp = JlAppResources.getProperty(hm.get("path").toString());
		 filepath = path_sptp+hm.get("ZCXX01").toString()+"/"+hm.get("sxpxx02").toString()+"/images/";
		 
	      try {
	    	  //获取路径生成文件
	          String path =filepath;
	          path = path.replace("\\", "/");
	          System.out.println("path:"+path);
	          File file2 = new File(path);
	          if (!file2.exists()) {
	        	  file2.mkdirs();
	  		  }
	        //获取输入流
	          String fileName=newTpName;
	          //FileOutputStream out=new FileOutputStream(path+"\\"+fileName);
	          //bos = new BufferedOutputStream(new FileOutputStream(path+"\\"+fileName));
	          bos = new BufferedOutputStream(new FileOutputStream(path+fileName));
	          //System.out.println(path+"\\"+fileName);
	          System.out.println(path+fileName);
	          BufferedInputStream bis=new BufferedInputStream(file.getInputStream());
	          byte buffer[] = new byte[8192];
	    	  int bytesRead = 0;
	    	  while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
	    		  bos.write(buffer, 0, bytesRead);
	    	  }
				bos.flush();
				bos.close();
	      } catch (Exception e) {
	    	  map.put("STATE", "0");
				e.printStackTrace();
				throw e;
			} 
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> uploadScaleImg(String TpName,int scale,HttpServletRequest request,Map hm,Map map,MultipartFile file) throws Exception{
		 //BufferedOutputStream bos = null;
		String filepath=null;
		 String path_sptp = JlAppResources.getProperty(hm.get("path").toString());
		 filepath = path_sptp+hm.get("ZCXX01").toString()+"/"+hm.get("sxpxx02").toString()+"/images/";
		 
		try {
	    	  //获取路径生成文件
	    	 // String path = request.getSession().getServletContext().getRealPath("/");
			  String path =filepath;
	          path = path.replace("\\", "/");
	          File file3 = new File(path);
	          if (!file3.exists()) {
	        	  file3.mkdirs();
	  		  }
	          //开始写流
		      Image src = javax.imageio.ImageIO.read(file.getInputStream());
		      int width = (int) (src.getWidth(null) * scale / 100.0);
		      int height = (int) (src.getHeight(null) * scale / 100.0);
		      BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		      bufferedImage.getGraphics().drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
		      String fileName=TpName;
		      OutputStream os = new FileOutputStream(path+fileName);
		      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		      encoder.encode(bufferedImage);
		      os.flush();
		      os.close();
		      map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
				e.printStackTrace();
				throw e;
			} 
		return map;
	}
	
	//修改商品信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectSPTP.action")
	public Map selectSPTP(String XmlData) throws DataAccessException, Exception{
		//查询商品图片
		Map map =new HashMap();
		cds= new DataSet(XmlData);
		String gsid=cds.getField("ZCXX01", 0);
		String spid=cds.getField("spid", 0);
		try {
			String sqlTp = "SELECT SPTP01,SPTP02 FROM W_SPTP WHERE ZCXX01='"+gsid+"' AND SPXX01='"+spid+"' ORDER BY SPTP01";
			List sptpList=queryForList(o2o, sqlTp);
			map.put("sptpList", sptpList);
			
			String sqljs="SELECT SPJS01,SPJS02 FROM W_SPJS WHERE ZCXX01='"+gsid+"' AND SPXX01='"+spid+"'";
			List spjsList=queryForList(o2o, sqljs);
			map.put("xxMap", spjsList);
		} catch (Exception e) {
			//map.put("STATE", "0");
			e.printStackTrace();
			throw new Exception("查询商品图片和商品介绍出错!");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateNewGoods.action")
	public Map updateNewGoods(HttpServletRequest request,
			HttpServletResponse response,String XmlDataa)throws Exception{
		Map map=new HashMap();
		try{
		String XmlData=JLTools.unescape(request.getParameter("XmlData"));
		cds = new DataSet(XmlData);
		System.out.println(XmlData);
		String spid=cds.getField("spid", 0);
		String spxx02=cds.getField("spcode", 0);
		String Sqll="UPDATE W_GOODS SET BARCODE=BARCODE?,SPXX04=spname?,ORIGIN_COUNTRY=ORIGIN_COUNTRY?,ORIGIN_CPLACE=ORIGIN_CPLACE?,ORIGIN_PLACE_CODE=ORIGIN_PLACE_CODE?,ORIGIN_EPLACE=ORIGIN_EPLACE?,   "+
		"HIGH=HIGH?,WIDTH=WIDTH?,DEPTH=DEPTH?,HIGH_UNIT=HIGH_UNIT?,WIDTH_UNIT=WIDTH_UNIT?,DEPTH_UNIT=DEPTH_UNIT?,CATEGORY_CODE=CATEGORY_CODE?,BRAND=BRAND?,KEYWORD=KEYWORD?,DESCRIPTION=DESCRIPTION?," +
		"PACKAGING_MATERIAL=PACKAGING_MATERIAL?,PACKAGING_TYPE=PACKAGING_TYPE?,  "+
		"SPECIFICATION=SPECIFICATION?,QUALITY_GUARANTEE_PERIOD=QUALITY_GUARANTEE_PERIOD?,SUPPLIER=SUPPLIER?,GOODS_STATUS=GOODS_STATUS? WHERE SPXX01='"+spid+"'";
		Map rowg=getRow(Sqll,null,0);
		int g=execSQL(o2o, Sqll, rowg); 
		
//		String Sql="UPDATE SPXX SET SPXX04=spname?,SPFL01=SPFL01?,SPFL02=SPFL02?,PPB01=PPB01?,PPB02=PPB02? WHERE SPXX02=spcode?";
//		Map row=getRow(Sql,null,0);
//		int a=execSQL(scm,Sql,row);
		String SPFL01=cds.getField("SPFL01", 0);
		String SPFL01_CODE=SPFL01.substring(0, 2);
		Map mapflmc=getSPFLMC(SPFL01_CODE);
		String SPFL01_NAME=mapflmc.get("SPFL02").toString();
		String SPFL02_CODE=SPFL01.substring(0, 4);
		mapflmc=getSPFLMC(SPFL02_CODE);
		String SPFL02_NAME=mapflmc.get("SPFL02").toString();
		String Sql="UPDATE W_SPXX SET SPXX04=spname?,SPFL01=SPFL01?,SPFL02=SPFL02?,PPB01=PPB01?,PPB02=PPB02?,SPFL01_CODE='"+SPFL01_CODE+"',SPFL01_NAME='"+SPFL01_NAME+"',SPFL02_CODE='"+SPFL02_CODE+"',SPFL02_NAME='"+SPFL02_NAME+"' WHERE SPXX01=spid?";
		Map row2=getRow(Sql,null,0);
		int b=execSQL(o2o,Sql,row2);
		
		Sql="UPDATE W_SPGL SET SPGL02=dtbj?,SPGL04=SPJGB05?,SPGL08=SPGL08?,SPGL09=SPGL09?,SPGL10=SPGL10?,SPGL11=SPGL11?,SPGL26=SPGL26?,SPGL27=SPGL27?,SPGL28=SPGL28? WHERE SPXX01=spid?";
		Map rows=getRow(Sql,null,0);
		int c=execSQL(o2o,Sql,rows);
		
		Sql="UPDATE W_SPJGB SET SPJGB05=SPJGB05?,SPJGB01=SPJGB01? WHERE ZCXX01=ZCXX01? AND SPXX01=spid?";
		Map row4=getRow(Sql,null,0);
		int d=execSQL(o2o,Sql,row4);
		
		String spjs = JLTools.unescape(request.getParameter("spjs"));//商品介绍
		String spcs = JLTools.unescape(request.getParameter("spcs"));//商品参数
		JSONArray json= JSONArray.fromObject(XmlData);
		List<Map<String, Object>> listJson=(List<Map<String,Object>>)json;
		Map hm=listJson.get(0);
		hm.put("spjs", spjs);
		hm.put("spcs", spcs);
		hm.put("spid", spid);
		hm.put("spxx02", spxx02);
		Map maps=updateSpbj(request,response,hm);
		int j=1;
		if("1"==maps.get("STATE")||null==maps.get("STATE")){
			j=1;
		}else{
			j=0;
			throw new Exception("图片上传出错!");
		}
		int m=insertSpflsx(XmlData,spid.toString());//插入商品分类属性\
		
		int k=insertW_SPCX(XmlData,spid);
		//int h=insertKHSP(XmlData,spid);
		int num=b*c*d*j*k*g*m;
		if(num==1){
			map.put("STATE", "1");
		}else{
			map.put("STATE", "0");
			throw new Exception("数据插入异常!");
		}
		}catch(Exception e){
			map.put("STATE", "0");
	    	e.printStackTrace();
	    	throw e;
	    }
		return map;
	}
	
	/*
	 * http://192.13.4.55:59100/JLESServer/POST_QFY?Type=Post_Check_BarCode&UserName=mycar&PassWord=88888&SIGNXML=
		{"Parms":"JSONXML","Sign":"052c9a48c069660933ed8dc1b8f541ff"}&JSONXML={"Bar_Code":"6904724022406"}
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SrchGoods.action")
	public Map SrchGoods(String XmlData) throws Exception{
		
		Map map=new HashMap();
		cds = new DataSet(XmlData);
		 //XmlData=[{"Bar_Code":"6904724022406"}];
		Map parameterMap = new HashMap<String, Object>();
		parameterMap.put("Bar_Code", cds.getField("BARCODE", 0).replaceAll("^(0+)", ""));//去掉商品条码头部的0
		JSONObject jsonObject = JSONObject.fromObject(parameterMap);
		//String returnJson = strikeaBalanceInterface(jsonObject.toString());
		try{
			//编码中心查询商品信息
			String returnJson = JlInterfaces.qfyInterface(jsonObject.toString());
			//String returnJson = "[]";
			//JSONObject map2 = JSONObject.fromObject(returnJson);
			JSONObject returnJson1  = JSONObject.fromObject(returnJson);  
	        //List<Map<String,Object>> mapListJson = returnJson1; 
			Map goodsMap = returnJson1;
			map.put("goodsMap", goodsMap);
			
			
			if(JlAppResources.getProperty("ROADMAP").equals("4")) {
				String sql = "SELECT COUNT(1) FROM W_GOODS WHERE BARCODE='"+cds.getField("BARCODE", 0)+"'";
				int i = queryForInt(o2o, sql);
				if(i == 1) {
					//String sql_ = "SELECT A.SPXX01, A.ZCXX01, B.ZCXX02 FROM W_GOODS A, W_ZCXX B WHERE A.ZCXX01=B.ZCXX01 AND A.BARCODE='"+cds.getField("BARCODE", 0)+"'";
					//Map spxx = queryForMap(o2o, sql_);
					//map.put("spxx", spxx);
					map.put("STATE", "2");//不允许重复
				}else{
					map.put("STATE", "1");
				}
				//map.put("ino2o", i);
			} 
			//map.put("STATE", "1");
		}catch(Exception e){
			map.put("STATE", "0");
	    	e.printStackTrace();
	    	//throw e;
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public int insertW_SPCX(String XmlData,String spid) throws Exception{
		int n=1;
		try {	
			cds=new DataSet(XmlData);
			String spcx=cds.getField("CXBH", 0);
			String [] listArray=spcx.split(",");
			//JSONArray list=JSONArray.fromObject(spcx);
			//Map<String, Object> hm=(Map<String, Object>)list.get(0);
			//JSONArray listArray=JSONArray.fromObject(hm.get("CXBH"));
			String sqliext="SELECT COUNT(*) COUNT FROM W_SPCX WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"' AND SPXX01='"+spid+"'";
			int count=queryForInt(o2o, sqliext);
			if(count>0){
				String sqldete="DELETE FROM W_SPCX WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"' AND SPXX01='"+spid+"'";
				execSQL(o2o, sqldete, 0);
			}
			if(spcx == "null") {
				return n;
			}
			for (int i = 0; i < listArray.length; i++) {
				String sql="INSERT INTO W_SPCX(ZCXX01,SPXX01,MOBILE_MODLE_ID,MODLE_ID)VALUES ('"+cds.getField("ZCXX01", 0)+"','"+spid+"','"+listArray[i]+"',(select MOBILE_MODLE_ID from w_automobile where type_code ='"+listArray[i]+"'))";
				Map row=getRow(sql, null, 0);
				n=execSQL(o2o, sql, row);
			}
		 
		} catch (Exception e) {
			n=0;
	    	e.printStackTrace();
		}
		return n;
	}
	
	//修改商品信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteSpcx.action")
	public Map deleteSpcx(String XmlData) throws DataAccessException, Exception{
		//查询商品图片
		Map map =new HashMap();
		cds= new DataSet(XmlData);
		String CXBH=cds.getField("CXBH", 0);
		String[] CXBHS=CXBH.split(",");
		try {
			for(int i=0;i<CXBHS.length;i++){
				String MOBILE_MODLE_ID=CXBHS[i];
				String sql=" DELETE FROM W_SPCX WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"' AND SPXX01='"+cds.getField("SPXX01", 0)+"' AND MOBILE_MODLE_ID="+MOBILE_MODLE_ID;
				Map row=getRow(sql, null, 0);
				execSQL(o2o, sql, row);
			}
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		}
		return map;
	}
	
	//修改商品信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkKhjg.action")
	public Map checkKhjg(String XmlData) throws DataAccessException, Exception{
		//查询商品图片
		Map map =new HashMap();
		cds= new DataSet(XmlData);
		try {
			String sql="SELECT KHLX01,KHSPJGI01 FROM W_KHSPJGB WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"' AND SPXX01='"+cds.getField("spid", 0)+"'";
			List list=queryForList(o2o, sql);
			map.put("khjgMap", list);
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public int updateKHSP(String XmlData) throws Exception{
		int n=1;
		cds = new DataSet(XmlData);
		try {	
		String sqllx="SELECT KHLX01,KHLX02 FROM W_KHLX ORDER BY KHLX01";
		List list =queryForList(o2o, sqllx);
		System.out.println(((Map)list.get(0)).get("KHLX01"));
		int KHSPJG01=PubFun.updateWBHZT(o2o,"W_KHSPJG",1);
		int a=0;
		int b=0;
		int c=0;
		String sql="INSERT INTO W_KHSPJG(KHSPJG01,ZCXX01,KHSPJG02,KHSPJG03)VALUES('"+KHSPJG01+"',ZCXX01?,sysdate(),sysdate())";
		Map row=getRow(sql, null, 0);
		a=execSQL(o2o, sql, row);
		for(int i=0;i<list.size();i++){
			String KHSPJGI01="SPJG0"+String.valueOf(i);
			String isexit="SELECT COUNT(*) COUNT W_KHSPJGB WHERE ZCXX01='"+cds.getField("ZCXX01", 0)+"' AND SPXX01='"+cds.getField("spid", 0)+"','"+((Map)list.get(i)).get("KHLX01")+"'";
			int count=queryForInt(o2o, isexit);
			if(count==0){
				KHSPJG01=PubFun.updateWBHZT(o2o,"W_KHSPJG",1);//记录编号
				String sql2="INSERT INTO W_KHSPJGITEM(KHSPJG01,ZCXX01,SPXX01,KHLX01,KHSPJGI01)VALUES('"+KHSPJG01+"',ZCXX01?,spid?,'"+((Map)list.get(i)).get("KHLX01")+"','"+cds.getField(KHSPJGI01, 0)+"')";
				Map row2=getRow(sql, null, 0);
				b=execSQL(o2o, sql2, row2);
				String sql3="INSERT INTO W_KHSPJGB(ZCXX01,SPXX01,KHLX01,KHSPJGI01)VALUES(ZCXX01?,spid?,'"+((Map)list.get(i)).get("KHLX01")+"','"+cds.getField(KHSPJGI01, 0)+"')";
				Map row3=getRow(sql3, null, 0);
				c=execSQL(o2o, sql3, row3);
			}else{
				String sql22="UPDATE W_KHSPJGITEM SET KHSPJGI01='"+cds.getField(KHSPJGI01, 0)+"'WHERE ZCXX01=ZCXX01? AND SPXX01=spid? AND KHLX01='"+((Map)list.get(i)).get("KHLX01")+"'";
				Map row22=getRow(sql22, null, 0);
				b=execSQL(o2o, sql22, row22);
				String sql33="UPDATE W_KHSPJGITEM SET KHSPJGI01='"+cds.getField(KHSPJGI01, 0)+"'WHERE ZCXX01=ZCXX01? AND SPXX01=spid? AND KHLX01='"+((Map)list.get(i)).get("KHLX01")+"'";
				Map row33=getRow(sql, null, 0);
				b=execSQL(o2o, sql33, row33);
			}
		}
		n=a*b*c;
		} catch (Exception e) {
			n=0;
			throw new Exception("客户商品插入出错!");
		}
		return n;
	}
	
	//查询商品分类属性
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectSpflsx.action")
	public Map selectSpflsx(String XmlData) throws DataAccessException, Exception{
		//查询商品图片
		Map map =new HashMap();
		cds= new DataSet(XmlData);
		try {
			String gsid=cds.getField("gsid", 0);
			String sp_id=cds.getField("spid", 0);
			//将商品基础信息放入
			String sql="SELECT A.SPXX01,A.SPXX02,A.SPXX04,A.PPB01,A.PPB02,A.SPFL01,A.SPFL02,A.SPFL02_CODE SPFL02CODE,A.SPFL02_NAME SPFL02NAME," +
					"B.SPJS01 SPYM,B.SPJS02 SPCS FROM W_SPXX A,W_SPJS B WHERE A.SPXX01=B.SPXX01(+) AND A.SPXX01='"+sp_id+"'";
			List list=queryForList(o2o, sql);
			map.put("xxMap", list);

			String sql4="SELECT distinct A.SPFL,A.SXBH,A.SXNAME FROM W_SPFLSX A,W_SPFLSXITEM B " +
			                   "WHERE A.SPFL=B.SPFL AND A.SXBH=B.SXBH AND A.SPFL='"+cds.getField("flcode", 0)+"' AND A.YXBJ=0 ORDER BY A.SXBH";
			List listsx=queryForList(o2o, sql4);
			List listsxz= new ArrayList();
			for(int j=0;j<listsx.size();j++){
				Map map3=(Map) listsx.get(j);
				String SXBH=map3.get("SXBH").toString();
				String sql2="SELECT B.SXZDM,B.SXZNAME,A.SXNAME,A.SXBH FROM W_SPFLSX A,W_SPFLSXITEM B WHERE A.SPFL=B.SPFL AND A.SXBH=B.SXBH AND B.SXBH='"+SXBH+"'";
				List spsxList=queryForList(o2o, sql2);
				listsxz.add(spsxList);
				String sql3 = "SELECT B.SXZDM " + 
								 " FROM W_SPFLSX A, W_SPFLSXITEM B, W_SPSX C " +
								 " WHERE A.SPFL = B.SPFL " +
								   " AND A.SXBH = B.SXBH " +
								   " AND A.SPFL = C.SXZDM " +
								   " AND B.SXBH = '"+SXBH+"' " +
								   " AND A.SPFL = '"+cds.getField("flcode", 0)+"'" +
								   " AND C.ZCXX01 = '"+gsid+"' " +
								   " AND C.SPXX01 = '"+sp_id+"' " +
								   " AND SUBSTR(B.SXZDM, 9, 10) = C.SXZNAME " +
								   " AND A.YXBJ = 0 ";
				Map SXZDM = queryForMap(o2o,sql3);
				map3.put("SXZDM",SXZDM);
			}
			map.put("spsxList", listsx);
			map.put("sxzList", listsxz);
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		}
		return map;
	}
	
	//查询商品分类属性明细
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectSpflsxItem.action")
	public Map selectSpflsxItem(String XmlData) throws DataAccessException, Exception{
		Map map =new HashMap();
		cds= new DataSet(XmlData);
		try {
			String sql="SELECT * FROM W_SPSX WHERE ZCXX01='"+cds.getField("gsid", 0)+"' AND SPXX01='"+cds.getField("spid", 0)+"'";
			List list=queryForList(o2o, sql);
			map.put("sxlist", list);
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
		}
		return map;
	
	}
	//商品编辑
	@SuppressWarnings("unchecked")
	@RequestMapping("/Spbj.action")
	public Map Spbj(HttpServletRequest request,
			HttpServletResponse response,String XmlDataa,String spjs,String spcs,String bzqd)throws Exception{
		Map map=new HashMap();
		String XmlData = JLTools.unescape(request.getParameter("XmlData"));//商品介绍
		cds = new DataSet(XmlData);
		Integer spid;
		spid=Integer.parseInt(cds.getField("SPXX01", 0));
		String Sqll="UPDATE W_GOODS SET SPXX04=SPXX04?,ORIGIN_CPLACE=ORIGIN_CPLACE?,BARCODE=BARCODE? "+
		 "WHERE SPXX01='"+spid+"'";
		Map rowg=getRow(Sqll,null,0);
		int a=execSQL(o2o, Sqll, rowg); 
		//长宽高重量
		String SPXX09=cds.getField("SPXX09", 0);
		String SPXX10=cds.getField("SPXX10", 0);
		String SPXX11=cds.getField("SPXX11", 0);
		String SPXX12=cds.getField("SPXX12", 0);
		if(SPXX09.equals("")){SPXX09="0";}
		if(SPXX10.equals("")){SPXX10="0";}
		if(SPXX11.equals("")){SPXX11="0";}
		if(SPXX12.equals("")){SPXX12="0";}
		String Sql="UPDATE W_SPXX SET SPFL01=SPFL01?,PPB01=ppb01?,PPB02=ppbNAME? ,SPXX04=SPXX04?,SPXX09='"+SPXX09+"',SPXX10='"+SPXX10+"',SPXX11='"+SPXX11+"',SPXX12='"+SPXX12+"', ggxh=ggxh? , jldw01=jldw01? WHERE SPXX01=SPXX01?";
		Map row2=getRow(Sql,null,0);
		int b=execSQL(o2o,Sql,row2);
		//商品价格表
		Sql="UPDATE W_SPJGB SET SPJGB05=SPJGB02?,SPJGB01=SPJGB02?,SPJGB02=SPJGB02? WHERE SPXX01="+spid;
		Map rowJg=getRow(Sql,null,0);
		int j=execSQL(o2o,Sql,rowJg);
		Sql="UPDATE W_SPGL SET SPGL04=SPJGB05? , SPGL24=SPGL24? , SPGL25=sycxa?,SPGL08=spgl08?,SPGL26=spgl26?,SPGL27=spgl27?,SPGL28=spgl28?,TIME01=nowts() WHERE SPXX01="+spid;
		Map row4=getRow(Sql,null,0);
		int c=execSQL(o2o,Sql,row4);
		//平台库存数量15.8.9YWL
		Sql="UPDATE W_KCXX SET CKSP04=kcsl? WHERE SPXX01="+spid+" AND ZCXX01=ZCXX01? AND CK01=ZCXX01?";
		Map row5=getRow(Sql,null,0);
		int f=execSQL(o2o,Sql,row5);
		//选择车系
		//int d=insertW_SPCX(XmlData,spid.toString());//15.8.9沈阳项目暂时不用
		spjs = JLTools.unescape(request.getParameter("spjs"));//商品介绍
		spcs = JLTools.unescape(request.getParameter("spcs"));//商品参数15.8.9沈阳项目用
		bzqd = JLTools.unescape(request.getParameter("bzqd"));//包装清单15.8.9沈阳项目用		
		JSONArray json= JSONArray.fromObject(XmlData);
		List<Map<String, Object>> listJson=(List<Map<String,Object>>)json;
		String spxx02=JLTools.getID_X(spid, 6);
		Map hm=listJson.get(0);
		hm.put("spjs", spjs);
		hm.put("spcs", spcs);
		hm.put("bzqd", bzqd);
		hm.put("spid", spid);
		hm.put("spxx02", spxx02);
		Map maps=updateSpbj(request,response,hm);
		int n = insertW_SPGLLJH(XmlData,spid.toString());
		//商品属性
		int m=insertSpflsx(XmlData,spid.toString());
		int e=1;
		if("1"==maps.get("STATE")||""==maps.get("STATE")){
			e=1;
		}else{
			e=0;
			throw new Exception("图片上传出错!");
		}
		int num=a*b*c*e*f*j*n*m;
		if(num==1){
			map.put("STATE", "1");
		}else{
			map.put("STATE", "0");
			throw new Exception("数据插入异常!");
		}
		return map;
	}

	/*
	 * http://192.13.4.55:59100/JLESServer/POST_QFY?Type=Post_Check_BarCode&UserName=mycar&PassWord=88888&SIGNXML=
		{"Parms":"JSONXML","Sign":"052c9a48c069660933ed8dc1b8f541ff"}&JSONXML={"Bar_Code":"6904724022406"}
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/SrchGLN.action")
	public Map SrchGLN(String json) throws Exception{
		
		Map map=new HashMap();
		cds = new DataSet(json);
		 //XmlData=[{"Bar_Code":"6904724022406"}];
		Map parameterMap = new HashMap<String, Object>();
		parameterMap.put("GLN", cds.getField("wzm", 0));
		JSONObject jsonObject = JSONObject.fromObject(parameterMap);
		try{
			//编码中心查询企业位置码
			String returnJson = JlInterfaces.glnInterface(jsonObject.toString());
			JSONObject returnJson1  = JSONObject.fromObject(returnJson);  
			Map glnMap = returnJson1;
			map.put("glnMap", glnMap);
			map.put("STATE", "1");
		}catch(Exception e){
			map.put("STATE", "0");
	    	e.printStackTrace();
	    	//throw e;
		}
		return map;
	}
	//项目维护新增/修改
	@SuppressWarnings("unchecked")
	@RequestMapping("/XmAdd.action")
	public Map Xmadd(HttpServletRequest request,
			HttpServletResponse response,String XmlDataa,String spjs,String spcs)throws Exception{
		Map map=new HashMap();
		String XmlData = JLTools.unescape(request.getParameter("XmlData"));//商品介绍
		cds = new DataSet(XmlData);
		String spfl=cds.getField("SPFL01", 0);
		String [] spfls =spfl.split(",");
		String code=cds.getField("XMBH",0);
		if(code==null||code.equals(""))
		{
			int xmbh=PubFun.updateWBHZT(o2o,"W_PROJECT",1);
			String insert_project ="insert into w_project(XMBH,XMMC) VALUES("+xmbh+",XMMC?)";
			Map rows=getRow(insert_project, null, 0);
			int a =execSQL(o2o, insert_project, rows);
			
			for(int i=0;i<spfls.length;i++)
			{
				String insert_project_fl ="insert into w_project_fl(XMBH,SPFL01) VALUES("+xmbh+",'"+spfls[i]+"')";
				Map rows1=getRow(insert_project_fl, null, 0);
				execSQL(o2o, insert_project_fl, rows1);
			}
			
			if(1==a){
				map.put("STATE", "1");
			}else{
				map.put("STATE", "0");
			}
		}else
		{
			String update_xm="update w_project set XMMC=XMMC? WHERE XMBH='"+code+"'";
			Map rows=getRow(update_xm, null, 0);
			int a =execSQL(o2o, update_xm, rows);
			
			String del_xm="delete from w_project_fl where XMBH='"+code+"'";
			Map rows2=getRow(del_xm, null, 0);
			execSQL(o2o, del_xm, rows2);
			
			for(int i=0;i<spfls.length;i++)
			{
				String insert_project_fl ="insert into w_project_fl(XMBH,SPFL01) VALUES('"+code+"','"+spfls[i]+"')";
				Map rows1=getRow(insert_project_fl, null, 0);
				execSQL(o2o, insert_project_fl, rows1);
			}
			if(1==a){
				map.put("STATE", "1");
			}else{
				map.put("STATE", "0");
			}
		}
	
		return map;
	}
	//项目维护删除
		@SuppressWarnings("unchecked")
		@RequestMapping("/XmDel.action")
		public Map XmDel(HttpServletRequest request,
				HttpServletResponse response,String XmlDataa,String spjs,String spcs)throws Exception{
			Map map=new HashMap();
			String XmlData = JLTools.unescape(request.getParameter("XmlData"));//商品介绍
			cds = new DataSet(XmlData);
			String code=cds.getField("XMBH",0);
			
			String del="delete from w_project where XMBH='"+code+"'";
			Map rows1=getRow(del, null, 0);
			int a=execSQL(o2o, del, rows1);
			
			String del_xm="delete from w_project_fl where XMBH='"+code+"'";
			Map rows2=getRow(del_xm, null, 0);
			int b=execSQL(o2o, del_xm, rows2);
			if(1==a){
				map.put("STATE", "1");
			}else{
				map.put("STATE", "0");
			}
			return map;
		}
		/*
		 * 更新订单状态
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/update_ddzt.action")
		public Map update_SHYJ(String XmlData){
			Map map =new HashMap();
			try {
				cds=new DataSet(XmlData);
				String DDZT = cds.getField("DDZT",0);
				String DDBH = cds.getField("XSDD01",0);
				String update = "update w_DJZX set w_djzx02='"+DDZT+"' where w_djzx01 ='"+DDBH+"'";
				Map row=getRow(update, null, 0);
				int a =execSQL(o2o, update, row);
				if(a==1)
				{
					map.put("STATE","1");
				}else
				{
					map.put("STATE", "0");	
				}
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
			}
			return map;
		}
		
		/*商品源信息(带图片)
		 * W_GOODS
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping("/GetProduceTraceByUniqueCode.action")
		public Map GetProduceTraceByUniqueCode(String XmlData, HttpServletRequest request)throws Exception{
			Map map=new HashMap();
			try{
				String address=path+"/GetProduceTraceByUniqueCode.json";
				//JSONArray jsonList=new JSONArray(); 
				cds = new DataSet(XmlData);
				JSONObject jsonObject_ = new JSONObject();
				jsonObject_.put("uniqueCode", cds.getField("produceUniqueCode",0));
				//jsonList.add(jsonObject_);
			
				Map zsmap=new HashMap();
				zsmap.put("content",writeObject(jsonObject_));
				String reponseString=RequestOauthUtil.postData(address, null,zsmap,"POST");
				JSONObject jsonObject = JSONObject.fromObject(reponseString);
				String resultCode=(String) jsonObject.get("resultCode");				
				
				String sql = "SELECT A.ZCXX01,B.SPXX02,C.SPTP02 FROM W_SPCM A,W_SPXX B,W_SPTP C  "
						+ "WHERE A.SPXX01=B.SPXX01 AND A.ZCXX01=C.ZCXX01 AND A.SPXX01=C.SPXX01  "
						+ "AND A.SPCM01='"+cds.getField("produceUniqueCode",0)+"' LIMIT 1 "; 
				
				Map imgUrl = queryForMap(o2o, sql);
	            String imgPath = JlAppResources.getProperty("emailUrl") + "/customer/qfy/images/msimg.jpg";
				if(imgUrl.get("sptp02") != null && !"".equals(imgUrl.get("sptp02"))){
					String[] tpArr = ((String) imgUrl.get("sptp02")).split("\\.");
					imgPath = JlAppResources.getProperty("emailUrl")+"/jlsoft/sptp/" + imgUrl.get("zcxx01") + "/" + imgUrl.get("spxx02") + "/images/" + imgUrl.get("spxx02") + "_1_big." + tpArr[1];
					URL url = new URL(imgPath);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					if(connection.getResponseCode() == 404){
						imgPath = JlAppResources.getProperty("emailUrl") + "/customer/qfy/images/msimg.jpg";
					}
				}
				
				if("1".equals(resultCode)){
				//成功
					map.put("STATE", "1");
					try{
						JSONObject result=(JSONObject) jsonObject.get("result");
						result.put("imgPath", imgPath);
						map.put("result",result);
					}catch(Exception e){
						JSONObject result=(JSONObject) jsonObject.get("result");
						map.put("result","");
					}
					
				}else{
				//失败
					map.put("STATE", "0");
				}
			}catch(Exception e){
				map.put("STATE", "0");
		    	e.printStackTrace();
		    /*	throw e;*/
		    }
			return map;
		}
		
		
		/**
		 * @todo 获取商品基础信息
		 * @param json
		 * @return
		 */
		@RequestMapping("/getBaseSPTP")
		public Map getBaseSPTP(String json){
			Map map=new HashMap();
			try{
				cds = new DataSet(json);
				String spcm01=cds.getField("spcm01", 0);
				String sql = "SELECT A.ZCXX01,A.SPXX01,B.SPTP02,(SELECT SPXX02 FROM W_SPXX WHERE SPXX01=A.SPXX01) SPXX02 " +
									"FROM W_SPCM A,W_SPTP B WHERE A.SPCM01='"+spcm01+"' " +
								   "AND A.SPXX01=B.SPXX01 AND A.ZCXX01=B.ZCXX01  ORDER BY B.SPTP01 LIMIT 1";
				map = queryForMap(o2o,sql);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return map;
		}
		
		public static String writeObject(Object obj) {
			ObjectMapper mapper = new ObjectMapper();
			
			StringWriter writer = new StringWriter();
			String re = null;
			try {
				JsonGenerator json = new JsonFactory().createGenerator(writer);
				mapper.writeValue(json, obj);
				re = writer.toString();
				json.close();
				writer.close();
			} catch (Exception e) {
			}
			return re;
		}
		
}
