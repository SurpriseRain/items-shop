package com.jlsoft.o2o.info.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
//import oracle.jdbc.driver.OracleResultSet;
//import oracle.sql.CLOB;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.init.service.Globals;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;
import com.sun.corba.se.pept.transport.Connection;
//import com.thoughtworks.xstream.converters.reflection.AbstractAttributedCharacterIteratorAttributeConverter;

@Controller
@RequestMapping("/Xxgl")
public class Xxgl extends JLBill{
	/**
	 * 
	 * @param request
	 * @param response
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectW_XXLX.action")
	public Map<String, Object> selectW_XXLX(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		try{
		String sqlString="select CODE,NAME from W_XXLX WHERE CODE != 9 ORDER BY CODE";
		List list=queryForList(o2o, sqlString);
		map.put("xxlxlist", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 查询咨询新闻详情
	 * @param request
	 * @param response
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectZxxwDetail.action")
	public Map<String, Object> selectZxxwDetail(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		try{
		String jlbh=request.getParameter("jlbh");
		String gsid=request.getParameter("gsid");
		String sql="select title,context,(SELECT X.NAME FROM W_XXLX X WHERE X.CODE = A.CODE) LXNAME," +
				"(SELECT X.CODE FROM W_XXLX X WHERE X.CODE = A.CODE) LXCODE,"+
				"DATE_FORMAT(fbsj,'%Y-%m-%d %h:%m:%s') fbsj,DATE_FORMAT(jzsj,'%Y-%m-%d %h:%m:%s') jzsj from w_xx A where A.jlbh='"+jlbh+"'and gsid='"+gsid+"'";
		List list=queryForList(o2o, sql);
		map.put("xwDetailList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//gslx=1-->客户
		//gslx!=1-->供货商
		return map;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/delZxxw.action")
	public Map<String, Object> delZxxw(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		Map row=null;
		try{
			cds = new DataSet(XmlData);
			String sql="delete from W_XX where jlbh=jlbh? and gsid=gsid?";
			row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
		return map;
	}
	//更新，新增资讯新闻
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateZxxw.action")
	public Map<String, Object> updateZxxw(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map resultMap = new HashMap();
		Map row=null;
		cds=new DataSet(XmlData);
		String sql=null;
		String context=JLTools.unescape(cds.getField("context", 0));
		try{
		String jlbh=cds.getField("jlbh",0);
		String gsid=cds.getField("gsid",0);
		int count =0;
		if(!jlbh.equals("")){
			String isExitSQL="SELECT COUNT(0) FROM W_XX WHERE JLBH='"+jlbh+"'and gsid='"+gsid+"'";
			 count = queryForInt(o2o, isExitSQL);
		}
		if(count==0){
			int JLBH=PubFun.updateWBHZT(o2o,"W_XX",1);
			sql="insert into W_XX(JLBH,CODE,TITLE,CONTEXT,FBSJ,GXSJ,ZT,GSID,ZSFW) " +
					"values("+JLBH+",code?,title?,'"+context+"',NOW(),NOW(),-1,'"+gsid+"',zsfw?)";
		}else if(count==1){
			//saveW_XXQY(XmlData,jlbh);
			sql="UPDATE W_XX SET CODE=code?,TITLE=title?,CONTEXT='"+context+"',ZSFW=zsfw? WHERE JLBH=jlbh? and GSID=gsid?";
			
		}
		row = getRow(sql, null, 0);
		execSQL(o2o, sql, row);
		resultMap.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}
	@SuppressWarnings("unchecked")
	public void saveW_XXQY(String XmlData,String BH) throws Exception{
	cds=new DataSet(XmlData);
	    
		//修改商品发布区域，首先删除之前的区域数据
		String deleteSql = "DELETE W_XXQY A WHERE A.BH='"+BH+"' AND A.ZCXX01=gsid?";
		Map row1 = getRow(deleteSql, null, 0);
		execSQL(o2o, deleteSql, row1); 
		
		//再插入提交的区域数据
		String gsid=cds.getField("gsid", 0);
		//String jlbh=cds.getField("jlbh", 0);
		String QXFLall = cds.getField("QYCODES", 0);
		if(!QXFLall.equals("null")&&!QXFLall.equals("")){
			String[] QXFL = QXFLall.split(",");
			if(!QXFLall.equals("null") && QXFL.length>0){
				for(int j=0 ; j<QXFL.length ; j++){
					String sql2="INSERT INTO W_XXQY (ZCXX01, BH, QYDM, ADDRESSNAME) VALUES"+
					"('"+gsid+"','"+BH+"','"+QXFL[j]+"',(select DQXX02 from DQXX where DQXX01 = '"+QXFL[j]+"'))";
					Map row2 = getRow(sql2, null, 0);
					execSQL(o2o, sql2, row2); 
				}
			}
		}
	    
	}

	/**
	 * 查询案例分享详情
	 * @param request
	 * @param response
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectAlfxDetail.action")
	public Map<String, Object> selectAlfxDetail(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		try{
		String albh=request.getParameter("albh");
		System.out.println(albh); 
		String sql="SELECT X.ALBH, X.ckqx,X.tjbj,X.gjnr,X.CONTEXT CONTEXT, Y.LXBH LXBH,X.FBR GSID," +
				"X.ALTITLE ALTITLE,STR_TO_DATE(X.FBSJ,'%Y-%m-%d %k:%i:%s') FBSJ,X.FILEPATH,X.FILENAME FROM W_ALFX X , W_ALLX Y where  X.ALLX = Y.LXBH AND X.albh='"+albh+"'";
		List list=queryForList(o2o, sql);
		map.put("alDetailList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//gslx=1-->客户
		//gslx!=1-->供货商
		return map;
	}

	//删除案例分享
	@SuppressWarnings("unchecked")
	@RequestMapping("/delAllx.action")
	public Map<String, Object> delAllx(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		Map row=null;
		try{
			cds = new DataSet(XmlData);
			String sql="delete from W_ALFX where albh=albh?";
			row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
		return map;
	}
	//新增政策法规（在用）。
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateAllx.action")
	public Map<String, Object> updateAllx(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map resultMap = new HashMap();
		Map row=null;
		XmlData=JLTools.unescape(XmlData);
		cds=new DataSet(XmlData);
		final String albh=cds.getField("albh",0);
		if(albh.equals("")){
			
		}
		/*
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		//Map row ;
		if(!upload.isMultipartContent(request)){
			resultMap.put("STATE", "0");
			return resultMap;
		}
		 * */
		String sql=null;
		String filepath=null;
		String tjbj=cds.getField("tjbj",0);
		String context=cds.getField("context", 0);
		String sql1="";
		try{
		if(tjbj.equals("1")){
			//图片上传
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			List<MultipartFile> listFile= mrRequest.getFiles("file");
			MultipartFile file=listFile.get(0);
			InputStream in=file.getInputStream();
			//String[] pathFull=request.getClass().getClassLoader().getResource("").getPath().split("/WEB-INF/");
			String path1=JlAppResources.getProperty("path_zcfg");
			//String path1=path+ "alfxtp/";
			//String path1="D:\jboss\server\default\deploy\jlo2o.war\b2b\tpgl\alfxtp\";
			String oldFileName=file.getOriginalFilename();
			String fileSuffix=oldFileName.substring(oldFileName.lastIndexOf("."));//截取文件后缀
			String newFileName=System.currentTimeMillis()+(int)(Math.random()*10)+fileSuffix;
			File filePath=new File(path1);
			if(!filePath.exists()){
				filePath.mkdirs();
				System.out.println("创建目录为："+filePath);
			}	
			//		file.transferTo(filePath);
			FileOutputStream out=new FileOutputStream(filePath+"/"+newFileName);
			System.out.println(filePath+"/"+newFileName);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			in.close();
			out.close();
			filepath=filePath+"/"+newFileName;
			//如果不存在，则新增
			if(albh.equals("")){
				//获取新增编号
				LinkedList inParameter = new LinkedList();
				inParameter.add("W_ALFX");
				inParameter.add(1);
				LinkedList outParameter = new LinkedList();
				outParameter.add(java.sql.Types.INTEGER);
				String sqlq="{call Update_WBHZT(?,?,?)}";
				List ALBH = callProc(o2o, sqlq, inParameter, outParameter);
				//执行SQL
	//			sql="INSERT INTO W_ALFX(ALBH,ALLX,ALTITLE,FBSJ,FBR,CKQX,CONTEXT,STATE,GXSJ,FILENAME,FILEPATH,TJBJ,GJNR) " +
	//			"VALUES("+ALBH+",allx?,altitle?,sysdate,fbr?,ckqx?,context?,0,sysdate,null,null,tjbj?,gjnr?)";
				sql="INSERT INTO W_ALFX(ALBH,ALLX,ALTITLE,FBSJ,FBR,CKQX,CONTEXT,STATE,GXSJ,FILENAME,FILEPATH,TJBJ,GJNR,XWGG) " +
				"VALUES("+ALBH.get(0)+",allx?,altitle?,now(),fbr?,ckqx?,'"+context+"',0,NOW(),'"+newFileName+"','"+filepath+"',tjbj?,gjnr?,xwgg?)";
				
				//sql1=" UPDATE W_ALFX W SET W.CONTEXT=? WHERE W.ALBH="+ALBH.get(0)+" ";
			}else if(!albh.equals("")){
				sql="update W_ALFX SET ALLX=allx?,ALTITLE=altitle?,FBR=fbr?,CKQX=ckqx?,CONTEXT='"+context+"',FILENAME='"+newFileName+"',FILEPATH='"+filepath+"',TJBJ=tjbj?,GJNR=gjnr?,XWGG=xwgg? WHERE ALBH=albh?";
				//sql1=" UPDATE W_ALFX W SET W.CONTEXT=? WHERE W.ALBH="+albh+" ";
			}
		}else if(tjbj.equals("0")){
			if(albh.equals("")){
				LinkedList inParameter = new LinkedList();
				inParameter.add("W_ALFX");
				inParameter.add(1);
				LinkedList outParameter = new LinkedList();
				outParameter.add(java.sql.Types.INTEGER);
				String sqlq="{call Update_WBHZT(?,?,?)}";
				List ALBH = callProc(o2o, sqlq, inParameter, outParameter);
				sql="INSERT INTO W_ALFX(ALBH,ALLX,ALTITLE,FBSJ,FBR,CKQX,CONTEXT,STATE,GXSJ,FILENAME,FILEPATH,TJBJ,GJNR) " +
				"VALUES("+ALBH.get(0)+",allx?,altitle?,NOW(),fbr?,ckqx?,'"+context+"',0,NOW(),null,null,tjbj?,gjnr?)";
				if(JlAppResources.getProperty("ROADMAP").equals("4")) {
					sql="INSERT INTO W_ALFX(ALBH,ALLX,ALTITLE,FBSJ,FBR,CKQX,CONTEXT,STATE,GXSJ,FILENAME,FILEPATH,TJBJ,GJNR,XWGG) " +
							"VALUES("+ALBH.get(0)+",allx?,altitle?,now(),fbr?,ckqx?,'"+context+"',0,NOW(),null,null,tjbj?,gjnr?,xwgg?)";				
				} 
				//sql1=" UPDATE W_ALFX W SET W.CONTEXT=? WHERE W.ALBH="+ALBH.get(0)+" ";
			}else if(!albh.equals("")){
				sql="update W_ALFX SET ALLX=allx?,ALTITLE=altitle?,CONTEXT='"+context+"',CKQX=ckqx?,TJBJ=TJBJ?,GJNR=GJNR?,XWGG=xwgg? WHERE ALBH=albh?";
				//sql1=" UPDATE W_ALFX W SET W.CONTEXT=? WHERE W.ALBH="+albh+" ";
			}
		}
		row = getRow(sql, null, 0);
		execSQL(o2o, sql, row);
//		final String context1=context;
//		final LobHandler lobHandler=new DefaultLobHandler();
//		o2o.execute(sql1,new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
//			@Override
//			protected void setValues(PreparedStatement pstmt, LobCreator lobCreator)
//					throws SQLException, DataAccessException {
//				lobCreator.setClobAsString(pstmt, 1, context1);
//				
//			}
//		});
		
		resultMap.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("STATE", "0");
		}
		return resultMap;
	}
	
	//政策法规修改（在用）
	@RequestMapping("/updateALLXInfo.action")
	public Map<String, Object> updateALLXInfo(String XmlData,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> hm =new HashMap<String, Object>();
		try {
			XmlData= JLTools.unescape(XmlData);
			cds=new DataSet(XmlData);
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			String context=cds.getField("context", 0);
			hm.put("albh", cds.getField("albh", 0));
			Object obj=request.getParameter("file");
			if(context!=null){
				hm.put("context",context);
			}
			if(!upload.isMultipartContent(request)&&obj==null){
				//非文件类型
				hm.put("fileName", "");
				hm.put("path", "");
				int i = updateALFXInfo(hm);
				if(i>0){
					hm.clear();
					hm.put("STATE", "1");
				}
			}else{
				hm.put("files", "file");
				hm.put("imgPath",JlAppResources.getProperty("path_zcfg"));
				hm.put("picCover", null);
				//调用图片上传公用方法   （要进行调整）
				//图片上传
				MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
				List<MultipartFile> listFile= mrRequest.getFiles("file");
				MultipartFile file=listFile.get(0);
				hm.put("imgName", file.getOriginalFilename());
				hm=JLTools.fileUploadNew(file, hm);
				if(true==Boolean.parseBoolean(hm.get("STATE").toString())){
					updateALFXInfo(hm);
					hm.clear();
					hm.put("STATE", "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", "0");
			throw e;
		}
		return hm;
	} 
	/**
	 * 
	 * @return
	 */
	public Map<String,Object> selectImagesName(Map<String, Object> map){
			try {
			String sql = "SELECT A.FILENAME FILENAME,A.FILEPATH filePath FROM W_ALFX A WHERE A.ALBH = '"
					+ map.get("albh") + "'";
				Map<String, Object> sqlMap=queryForMap(o2o, sql);
				map.putAll(sqlMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return map;
	}
	
	public int updateFileName(Map<String, Object> map) throws Exception{
		try {
			String sql = "UPDATE W_ALFX A SET A.FILENAME = '"
					+ map.get("FILENAME") + "', A.FILEPATH = '"
					+ map.get("FILEPATH") + "' WHERE A.ALBH = '"
					+ map.get("albh") + "'";
			int i= execSQL(o2o, sql, 0);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public int updateALFXInfo(Map map) throws Exception {
		int i = 0;
			try {
				final String context=map.get("context").toString();
				String sql ="update W_ALFX SET ALLX=allx?,ALTITLE=altitle?,CONTEXT='"+context+"',CKQX=ckqx?," +
						"TJBJ=tjbj?,GJNR=gjnr?,XWGG=xwgg?,filename='"+map.get("fileName").toString()+"',filepath='"+map.get("path").toString()+"' WHERE ALBH=albh?";
				Map row = getRow(sql, null, 0);
				i = execSQL(o2o, sql, row);
//				final LobHandler lobHandler=new DefaultLobHandler();
//				o2o.execute(sql1,new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
//					@Override
//					protected void setValues(PreparedStatement pstmt, LobCreator lobCreator)
//							throws SQLException, DataAccessException {
//						lobCreator.setClobAsString(pstmt, 1, context);
//					}
//				});
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		return i;
	}
	
	//定义案例类型
	@SuppressWarnings({ "unchecked"})
	@RequestMapping("/insertW_ALLX.action")
	public Map<String, Object> insertW_ALLX(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
	Map map = new HashMap();
	cds=new DataSet(XmlData);
	String sql=null;
	try{
	//获取新增编号
	String allx=cds.getField("allx", 0);
	if(allx.equals("")){
		LinkedList inParameter = new LinkedList();
		inParameter.add("W_ALLX");
		inParameter.add(1);
		LinkedList outParameter = new LinkedList();
		outParameter.add(java.sql.Types.INTEGER);
		String sqlq="{call Update_WBHZT(?,?,?)}";
		List LXBH= callProc(o2o, sqlq, inParameter, outParameter);
		sql="INSERT INTO W_ALLX(LXBH,LXMC,CKQX) VALUES('"+LXBH.get(0)+"',lxname?,ckqx?)";
		if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			sql="INSERT INTO W_ALLX(LXBH,LXMC,CKQX,DHBJ) VALUES('"+LXBH.get(0)+"',lxname?,ckqx?,DHBJ?)";
		} 
	}else if(!allx.equals("")){
		sql="UPDATE W_ALLX SET LXMC=lxname?,CKQX=ckqx? WHERE LXBH=allx?";
		//if(JlAppResources.getProperty("ROADMAP").equals("4")) {
			//sql="UPDATE W_ALLX SET LXMC=lxname?,CKQX=ckqx? WHERE LXBH=allx? AND DHBJ=DHBJ?";
		//}
	}
	Map row=getRow(sql, null, 0);
	execSQL(o2o, sql, row);
	map.put("STATE", "1");
	} catch (Exception e) {
		e.printStackTrace();
		map.put("STATE", "0");
	}
	return map;
	}
	//删除案例类型
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteW_ALLX.action")
	public Map<String, Object> deleteW_ALLX(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		Map row=null;
		try{
			cds = new DataSet(XmlData);
			String sql="DELETE FROM W_ALLX WHERE LXBH = allx?";
			//if (JlAppResources.getProperty("ROADMAP").equals("4")) {
			//	sql = "DELETE FROM W_ALLX A WHERE A.LXBH = allx? AND A.DHBJ = DHBJ?";
			//}
			row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
		return map;
	}

	/**
	 * 查询案例类型
	 * @throws Exception 
	 */
	@RequestMapping("/selectAllx.action")
	public List<Map<String, Object>> selectAllx(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		String DHBJ = cds.getField("DHBJ", 0);
		String sql=
			"SELECT LXBH, LXMC FROM W_ALLX A WHERE A.DHBJ='"+DHBJ+"'";
		List<Map<String, Object>> allxList=queryForList(o2o, sql);
		return allxList;
	}
	/**
	 * 定义新增信息类型
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping("/insertW_XXLX.action")
	public Map<String, Object> insertW_XXLX(HttpServletRequest request,
				HttpServletResponse response, String XmlData) throws Exception {
		Map map = new HashMap();
		cds=new DataSet(XmlData);
		String sql=null;
		try{
			LinkedList inParameter = new LinkedList();
			inParameter.add("W_XXLX");
			inParameter.add(1);
			LinkedList outParameter = new LinkedList();
			outParameter.add(java.sql.Types.INTEGER);
			String sqlq="{call Update_WBHZT(?,?,?)}";
			List COD= callProc(o2o, sqlq, inParameter, outParameter);
			 sql="INSERT INTO W_XXLX( CODE,NAME) VALUES('"+COD.get(0)+"',name?)";
			 Map row=getRow(sql, null, 0);
			 execSQL(o2o, sql, row);
			 map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
		}
		//删除信息
		@SuppressWarnings("unchecked")
		@RequestMapping("/deleteW_XXLX.action")
		public Map<String, Object> deleteW_XXLX(HttpServletRequest request,
				HttpServletResponse response, String XmlData) throws Exception {
			Map map = new HashMap();
			Map row=null;
			try{
				cds = new DataSet(XmlData);
				String sql="DELETE FROM W_XXLX WHERE CODE = code?";
				row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				map.put("STATE", "1");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("STATE", "0");
				}
			return map;
		}
		//更新修改信息
		@SuppressWarnings("unchecked")
		@RequestMapping("/updateW_XXLX.action")
		public Map<String, Object> updateW_XXLX(HttpServletRequest request,
				HttpServletResponse response, String XmlData) throws Exception {
			Map map = new HashMap();
			Map row=null;
			try{
				cds = new DataSet(XmlData);
				String sql="UPDATE W_XXLX SET CODE=code?,NAME=name? WHERE CODE=code?";
				row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				map.put("STATE", "1");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("STATE", "0");
				}
			return map;
		}

}
