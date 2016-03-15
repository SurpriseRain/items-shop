package com.jlsoft.o2o.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.aspectj.weaver.ast.Var;
import org.jdom.input.SAXBuilder;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.user.service.Register;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/QFY")
public class QFY_PRODUCT extends JLBill {
	@Autowired
	private Oper_floor oper_floor;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9BasicData v9BasicData;

	@SuppressWarnings("unchecked")
	@RequestMapping("/qfyCPZQ.action")
	public Map selectqfyCPZQ(String XmlData) {
		Map hm = new HashMap();
		try {
			String sql = "select  SPFL01, SPFL02 FROM spfl where spfl03='1' order by pxbj LIMIT 5";
			List list = queryForList(o2o, sql);
			for (int i=0;i<list.size();i++) {
				Map map = (Map) list.get(i);
				String SPFL01 = map.get("SPFL01").toString();
				String sql1 = "SELECT SPFL01, SPFL02 FROM spfl where SPF_SPFL01='"+SPFL01+"' and spfl03='2' LIMIT 5 ";
				List list1 = queryForList(o2o, sql1);
				map.put("list1", list1);
			}
			hm.put("list", list);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/qfyFWZQ.action")
	public Map selectqfyWYZQ(String XmlData) {
		Map hm = new HashMap();
		try {
			//String sql = "SELECT SPFL01, SPFL02 FROM spfl where spfl03='1' order by pxbj";
			String sql = "select SPFL01, SPFL02 from spfl t where spfl01 like 'EE'||'%' and spfl03='2'";
			List list = queryForList(o2o, sql);
			hm.put("list", list);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectFloorGoods.action")
	public List<Map<String, Object>> selectFloorGoods(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		String lcflStr="";
		int limitNum=-1;
		String LCFL01=cds.getField("LCFL01", 0);
		
		String spSql=
			"SELECT DISTINCT A.SPXX01  SPXX01,\n" +
			"       B.SPXX02  SPXX02,\n" + 
			"       B.SPXX04  SPMC,\n" + 
			"       E.SPJGB01 SPJG,\n" + 
			"       E.SPJGB05 SPJGB01,\n" + 
			"       E.SPJGB02 SPJGB02,\n" + 
			"       E.SPJGB05 SPJGB05,\n" + 
			"       F.SPGL04  SPGL04,\n" +
			"       F.CKSP12  SPSX, \n" +
			"       E.SPJGB02 SCJG,\n" + 
			"       A.ZCXX01  ZCXX01,\n" +
			"       C.ZCXX02  ZCXX02,\n" +
			"		(SELECT SPTP02 FROM W_SPTP WHERE ZCXX01=F.ZCXX01 AND SPXX01=F.SPXX01 AND SPTP01=1) SPTP02,F.SPGL26,F.SPGL27,F.SPGL28 \n"+
			"  FROM W_LCSP A, W_SPXX B, W_ZCXX C, W_LCSPLX D, W_SPJGB E, W_SPGL F\n" + 
			" WHERE A.SPXX01 = B.SPXX01\n" + 
			"   AND A.ZCXX01 = C.ZCXX01\n" + 
			"   AND A.LCSPLX01 = D.LCSPLX01\n" + 
			"   AND A.SPXX01 = E.SPXX01\n" + 
			"   AND A.ZCXX01 = E.ZCXX01\n" + 
			"   AND A.SPXX01 = F.SPXX01\n" + 
			"   AND F.SPGL12 IN ('1', '3')\n" ;
			
		if(null!=LCFL01&&!"".equals(LCFL01)){
			lcflStr=LCFL01.split(",")[0];
			if(!"".equals(lcflStr)){
				spSql=spSql+"   AND A.LCSPLX01 = ";
			}
			limitNum=Integer.parseInt(LCFL01.split(",")[1]);
		}
		String queryfinalSql="";
		if(limitNum==-1){
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT 9";
		}else{
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT "+limitNum;
		}
		List<Map<String, Object>> lcsp = queryForList(o2o, queryfinalSql);
		return lcsp;
		//List list = oper_floor.selectFloorGoods(XmlData);
	}
	
	
	/****
	 * 兴隆楼层查询
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectFloorGoodsDT.action")
	public List<Map<String, Object>> selectFloorGoodsDT(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String strdate=format.format(new Date());
		String lcflStr="";
		int limitNum=-1;
		String LCFL01=cds.getField("LCFL01", 0);
		String spSql="select c.ZCXX01,C.GGGL02 SPXX02,C.GGGL02,C.GGGL04 SPMC,C.GGGL05 SPJG,C.GGGL15 URL From w_gglxz a,w_ggw b,w_gggl c where a.ZCXX01=b.ZCXX01 and a.GGLXZ01=b.GGLXZ01 and b.ZCXX01=c.ZCXX01 and b.GGW01=c.GGW01" +
				" and c.ZCXX01='"+cds.getField("ZCXX01", 0)+"' and c.DQXX01='"+cds.getField("DQXX01", 0)+"' and a.GGLX01='"+cds.getField("GGLX01", 0)+"' and a.GGLXZ01='"+cds.getField("LCFL01", 0)+"' " +
						" and c.GGGL06<DATE_FORMAT('"+strdate+"','%Y-%m-%d %H:%i:%s') and c.GGGL07>DATE_FORMAT('"+strdate+"','%Y-%m-%d %H:%i:%s')" +
						" ORDER BY c.GGGL06,b.GGW01 LIMIT 0,1";
		/*String spSql1=
			"SELECT DISTINCT A.SPXX01  SPXX01,\n" +
			"       (select SXSP05 from w_sxsp where sxsp01=(select SXSP01 from w_spsxz where zcxx01=C.ZCXX01 and spxx01=A.SPXX01))  SPXX02,\n" + 
			"       B.SPXX04  SPMC,\n" + 
			"       E.SPJGB01 SPJG,\n" + 
			"       E.SPJGB05 SPJGB01,\n" + 
			"       E.SPJGB02 SPJGB02,\n" + 
			"       E.SPJGB05 SPJGB05,\n" + 
			"       F.SPGL04  SPGL04,\n" +
			"       F.CKSP12  SPSX, \n" +
			"       E.SPJGB02 SCJG,\n" + 
			"       A.ZCXX01  ZCXX01,\n" +
			"       C.ZCXX02  ZCXX02,\n" +
			"		F.SPGL26,F.SPGL27,F.SPGL28 \n" +
			",(SELECT S.SPTP02 FROM W_SPTP S WHERE S.ZCXX01=C.ZCXX01 AND S.SPXX01=(select SXSP01 from w_spsxz where zcxx01=C.ZCXX01 and spxx01=A.SPXX01) AND S.SPTP01=1) SPTP02,(SELECT SXSP01 FROM W_SPSXZ WHERE ZCXX01=C.ZCXX01 AND SPXX01=A.SPXX01) SXSP01\n"+
			"  FROM W_LCSP A, W_SPXX B, W_ZCXX C, W_LCSPLX D, W_SPJGB E, W_SPGL F\n" + 
			" WHERE A.SPXX01 = B.SPXX01\n" + 
			"   AND A.ZCXX01 = C.ZCXX01\n" + 
			"   AND A.LCSPLX01 = D.LCSPLX01\n" + 
			"   AND A.SPXX01 = E.SPXX01\n" + 
			"   AND A.ZCXX01 = E.ZCXX01\n" + 
			"   AND A.SPXX01 = F.SPXX01\n" + 
			"   AND F.SPGL12 IN ('1', '3')\n" ;
			
		if(null!=LCFL01&&!"".equals(LCFL01)){
			lcflStr=LCFL01.split(",")[0];
			if(!"".equals(lcflStr)){
				spSql=spSql+"   AND A.LCSPLX01 = ";
			}
			limitNum=Integer.parseInt(LCFL01.split(",")[1]);
		}
		String queryfinalSql="";
		if(limitNum==-1){
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT 9";
		}else{
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT "+limitNum;
		}*/
		List<Map<String, Object>> lcsp = queryForList(o2o, spSql);
		return lcsp;
		//List list = oper_floor.selectFloorGoods(XmlData);
	}
	
	/****
	 * 广告位楼层查询
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectFloorGoodsNew.action")
	public List<Map<String, Object>> selectFloorGoodsNew(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String strdate=format.format(new Date());
		String lcflStr="";
		int limitNum=-1;
		String LCFL01=cds.getField("LCFL01", 0);
		String spSql="select c.ZCXX01,C.GGGL02 SPXX02,C.GGGL02,C.GGGL04 SPMC,C.GGGL05 SPJG,C.GGGL15 URL From w_gglxz a,w_ggw b,w_gggl c where a.ZCXX01=b.ZCXX01 and a.GGLXZ01=b.GGLXZ01 and b.ZCXX01=c.ZCXX01 and b.GGW01=c.GGW01" +
				" and c.ZCXX01='"+cds.getField("ZCXX01", 0)+"' and c.DQXX01='"+cds.getField("DQXX01", 0)+"' and a.GGLX01='"+cds.getField("GGLX01", 0)+"' and a.GGLXZ01='"+cds.getField("LCFL01", 0)+"' " +
						" and c.GGGL06<DATE_FORMAT('"+strdate+"','%Y-%m-%d %H:%i:%s') and c.GGGL07>DATE_FORMAT('"+strdate+"','%Y-%m-%d %H:%i:%s')" +
						" ORDER BY c.GGGL06,b.GGW01";
		/*String spSql1=
			"SELECT DISTINCT A.SPXX01  SPXX01,\n" +
			"       (select SXSP05 from w_sxsp where sxsp01=(select SXSP01 from w_spsxz where zcxx01=C.ZCXX01 and spxx01=A.SPXX01))  SPXX02,\n" + 
			"       B.SPXX04  SPMC,\n" + 
			"       E.SPJGB01 SPJG,\n" + 
			"       E.SPJGB05 SPJGB01,\n" + 
			"       E.SPJGB02 SPJGB02,\n" + 
			"       E.SPJGB05 SPJGB05,\n" + 
			"       F.SPGL04  SPGL04,\n" +
			"       F.CKSP12  SPSX, \n" +
			"       E.SPJGB02 SCJG,\n" + 
			"       A.ZCXX01  ZCXX01,\n" +
			"       C.ZCXX02  ZCXX02,\n" +
			"		F.SPGL26,F.SPGL27,F.SPGL28 \n" +
			",(SELECT S.SPTP02 FROM W_SPTP S WHERE S.ZCXX01=C.ZCXX01 AND S.SPXX01=(select SXSP01 from w_spsxz where zcxx01=C.ZCXX01 and spxx01=A.SPXX01) AND S.SPTP01=1) SPTP02,(SELECT SXSP01 FROM W_SPSXZ WHERE ZCXX01=C.ZCXX01 AND SPXX01=A.SPXX01) SXSP01\n"+
			"  FROM W_LCSP A, W_SPXX B, W_ZCXX C, W_LCSPLX D, W_SPJGB E, W_SPGL F\n" + 
			" WHERE A.SPXX01 = B.SPXX01\n" + 
			"   AND A.ZCXX01 = C.ZCXX01\n" + 
			"   AND A.LCSPLX01 = D.LCSPLX01\n" + 
			"   AND A.SPXX01 = E.SPXX01\n" + 
			"   AND A.ZCXX01 = E.ZCXX01\n" + 
			"   AND A.SPXX01 = F.SPXX01\n" + 
			"   AND F.SPGL12 IN ('1', '3')\n" ;
			
		if(null!=LCFL01&&!"".equals(LCFL01)){
			lcflStr=LCFL01.split(",")[0];
			if(!"".equals(lcflStr)){
				spSql=spSql+"   AND A.LCSPLX01 = ";
			}
			limitNum=Integer.parseInt(LCFL01.split(",")[1]);
		}
		String queryfinalSql="";
		if(limitNum==-1){
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT 9";
		}else{
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT "+limitNum;
		}*/
		List<Map<String, Object>> lcsp = queryForList(o2o, spSql);
		return lcsp;
		//List list = oper_floor.selectFloorGoods(XmlData);
	}
	
	
	
	/***
	 * 兴隆查询促销商品
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectBuyingGoods.action")
	public List<Map<String, Object>> selectBuyingGoods(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		String lcflStr="";
		int limitNum=-1;
		String LCFL01=cds.getField("LCFL01", 0);
		
		String spSql=
			"SELECT DISTINCT A.SPXX01  SPXX01,\n"+
       "A.SPXX02  SPXX02,\n"+
       "A.SPXX04  SPMC,\n"+
       "C.SPJGB01 SPJG,\n"+
       "C.SPJGB05 SPJGB01,\n"+
       "C.SPJGB02 SPJGB02,\n"+
       "C.SPJGB05 SPJGB05,\n"+
       "IFNULL(C.SPJBG06,0) SPJGB06,\n"+
       "D.SPGL04  SPGL04,\n"+
       "D.CKSP12  SPSX, \n"+
       "C.SPJGB02 SCJG,\n"+
       "B.ZCXX01  ZCXX01,\n"+
       "B.ZCXX02  ZCXX02,\n"+
		"ifnull((SELECT S.SPTP02 FROM W_SPTP S WHERE S.ZCXX01=B.ZCXX01 AND S.SPXX01=(select SXSP01 from w_spsxz where zcxx01=B.ZCXX01 and spxx01=A.SPXX01) AND S.SPTP01=1),(SELECT SPTP02 FROM W_SPTP WHERE ZCXX01=B.ZCXX01 AND SPXX01=A.SPXX01 AND SPTP01=1)) SPTP02,D.SPGL26,D.SPGL27,D.SPGL28,(SELECT SXSP01 FROM W_SPSXZ WHERE ZCXX01=B.ZCXX01 AND SPXX01=A.SPXX01) SXSP01\n"+ 
  "FROM  W_SPXX A, W_ZCXX B,W_SPJGB C, W_SPGL D\n"+
 "WHERE A.SPXX01 = C.SPXX01\n"+
	 "AND C.SPXX01 = D.SPXX01\n"+
   "AND B.ZCXX01 = D.ZCXX01\n"+
   "AND C.ZCXX01 = D.ZCXX01\n"+
   "AND D.SPGL12 IN ('1', '3')\n"+
   "AND D.SPGL08=1\n";
			
		/*if(null!=LCFL01&&!"".equals(LCFL01)){
			lcflStr=LCFL01.split(",")[0];
			if(!"".equals(lcflStr)){
				spSql=spSql+"   AND A.LCSPLX01 = ";
			}
			limitNum=Integer.parseInt(LCFL01.split(",")[1]);
		}
		String queryfinalSql="";
		if(limitNum==-1){
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT 9";
		}else{
			queryfinalSql=spSql+lcflStr+" ORDER BY A.OPERTIME DESC  LIMIT "+limitNum;
		}*/
		List<Map<String, Object>> lcsp = queryForList(o2o, spSql);
		return lcsp;
		//List list = oper_floor.selectFloorGoods(XmlData);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectZCGS.action")
	public Map selectZCGS(String XmlData) {
		Map hm = new HashMap();
		try {
			String sql = "SELECT A.ZCXX01, A.ZCXX02 FROM W_ZCXX A, W_GSLX B WHERE A.ZCXX01=B.GSID AND B.LX='43'";
			List list = queryForList(o2o, sql);
			hm.put("list", list);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	@RequestMapping("/insertDPXX.action")
	public Map<String, Object> insertDPXX(String XmlData,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> hm =new HashMap<String, Object>();
		try {
			XmlData= JLTools.unescape(XmlData);
			cds=new DataSet(XmlData);
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			List<MultipartFile> listFile= mrRequest.getFiles("file");
			for (int i = 0; i < listFile.size(); i++) {
				MultipartFile file = listFile.get(i);
				String fileName = file.getOriginalFilename();
				String fileSuffix=fileName.substring(fileName.lastIndexOf("."));
			String HBID=cds.getField("HBID", 0);
			String DPXX02=HBID+fileSuffix;//图片名称，店铺编码+图片格式
			String path=JlAppResources.getProperty("path_dptp");
			String DPXX03=path+DPXX02;
			String sqlIs="SELECT COUNT(*) COUNT FROM W_DPXX WHERE ZCXX01='"+HBID+"'";
			int num=queryForInt(o2o, sqlIs);
			if(num==0){
				String sql="INSERT INTO W_DPXX (ZCXX01,DPXX01,DPXX02,DPXX03)VALUES(HBID?,gjnr?,'"+DPXX02+"','"+DPXX03+"')";
				Map row=getRow(sql, null, 0);
				execSQL(o2o, sql, row);
			}else{
				String sql="UPDATE W_DPXX SET DPXX01=gjnr? WHERE ZCXX01='"+HBID+"'";
				Map row=getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				
				String sql1="UPDATE W_ZCXX SET ZCXX02=gsmc? WHERE ZCXX01='"+HBID+"'";
				Map row1=getRow(sql1, null, 0);
				execSQL(o2o, sql1, row1);
			}
			Object obj=request.getParameter("file");
			if(!upload.isMultipartContent(request)&&obj==null){
				
			}else{
				hm.put("files", "file");
				hm.put("imgPath",path);
				hm.put("imgName", DPXX02);
				JSONArray jso =JSONArray.fromObject(XmlData);
				hm.put("sqlParam", (List<Map<String, Object>>)jso);
				//TODO 案例分享 
				//调用图片上传公用方法
				hm=JLTools.fileUploadNew(file, hm);
				if("success".equals(hm.get("Flag"))&&true==Boolean.parseBoolean(hm.get("STATE").toString())){
					hm.clear();
					hm.put("STATE", "1");
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", "0");
			throw e;
		}
		return hm;
	} 	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteDPXX.action")
	public Map deleteDPXX(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql = "DELETE W_DPXX WHERE ZCXX01=ZCXX01?";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectDPSrch.action")
	public Map selectDPSrch(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String dpmc = cds.getField("dpmc", 0);
			String sql = "SELECT * FROM (SELECT DISTINCT A.ZCXX01, A.ZCXX02, A.ZCXX34 FROM W_ZCXX A, W_DPXX B WHERE A.ZCXX01=B.ZCXX01 AND A.ZCXX02 LIKE CONCAT('%','"+dpmc+"','%') OR A.ZCXX01 LIKE CONCAT('%','"+dpmc+"','%') ORDER BY A.ZCXX34) t limit 11";
			List list = queryForList(o2o, sql);
			hm.put("list", list);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/showkhlx.action")
	public Map showkhlx(String XmlData) throws Exception {
		Map hm = new HashMap();
		try {
			String sql = "SELECT KHLX01, KHLX02 FROM W_KHLX ORDER BY KHLX01";
			List list = queryForList(o2o, sql);
			hm.put("list", list);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertNewKH.action")
	public Map insertNewKH(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String ZTID = cds.getField("ZCXX01", 0).toString();
			JSONArray json = JSONArray.fromObject(cds.getField("khList", 0).toString());
			List list = (List) json;
			for (int i=0; i<list.size(); i++) {
				Map map1 = (Map) list.get(i);
				String HBID = map1.get("ZCXX01").toString();
				String KHLX01 = map1.get("KHLX01").toString();
				String sql = "insert into W_KHGX values('"+ZTID+"','"+HBID+"','"+KHLX01+"')";
				Map row=getRow(sql, null, 0);
				execSQL(o2o, sql, row);
			}
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateMYKH.action")
	public Map updateMYKH(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String KHLX01 = cds.getField("KHLX01", 0).toString();
			String ZTID = cds.getField("ZTID", 0).toString();
			String HBID = cds.getField("HBID", 0).toString();
			String sql = "UPDATE W_KHGX SET KHLX01='"+KHLX01+"' WHERE ZTID='"+ZTID+"' AND HBID='"+HBID+"'";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteMYKH.action")
	public Map deleteMYKH(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String KHLX01 = cds.getField("KHLX01", 0).toString();
			String ZTID = cds.getField("ZTID", 0).toString();
			String HBID = cds.getField("HBID", 0).toString();
			String sql = "delete W_KHGX WHERE ZTID='"+ZTID+"' AND HBID='"+HBID+"'";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	//查询首页登陆后登陆栏展示数据
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectDPXXLogin.action")
	public Map selectDPXXLogin(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String HBID = cds.getField("HBID", 0).toString();
			String sql = "SELECT A.ZCXX01, A.ZCXX02, B.DPXX02, B.DPXX03 FROM W_ZCXX A, W_DPXX B WHERE A.ZCXX01=B.ZCXX01 AND A.ZCXX01='"+HBID+"'";
			Map dpmcmap = queryForMap(o2o, sql);
			String sql1 = "SELECT COUNT(1) SUM FROM W_XSDD WHERE HBID='"+HBID+"'";
			Map ddzsmap = queryForMap(o2o, sql1);

			hm.put("dpmcmap", dpmcmap);
			hm.put("ddzsmap", ddzsmap);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	//查询店铺信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectW_DPXX.action")
	public Map selectW_DPXX(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql="SELECT DPXX01,DPXX02,DPXX03,DPXX04 FROM W_DPXX WHERE ZCXX01='"+cds.getField("gsid", 0)+"'";
			List dpxxlist=queryForList(o2o, sql);
			hm.put("dpxxlist", dpxxlist);
			hm.put("state", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 0);
		} 
		return hm;
	}
	
	//审核店铺信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkDPXX.action")
	public Map checkDPXX(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql="UPDATE W_DPXX SET DPXX04=1 WHERE ZCXX01='"+cds.getField("HBID", 0)+"'";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			hm.put("STATE", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", 0);
		} 
		return hm;
	}
	//审核店铺信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/backclick.action")
	public Map backclick(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql="UPDATE W_DPXX SET DPXX04=-1 WHERE ZCXX01='"+cds.getField("HBID", 0)+"'";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			hm.put("STATE", 1);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", 0);
		} 
		return hm;
	}
	
	/**
	 * 直通车
	 */
	@RequestMapping("/selectD_DDPX.action")
	public Map selectD_DDPX(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String sql="   SELECT B.ZCXX34,A.ZCXX01,B.ZCXX02,A.DPXX01, A.DPXX02," +
					" A.DPXX03 FROM W_DPXX A,W_ZCXX B "+
						"	WHERE A.ZCXX01=B.ZCXX01 ORDER BY ZCXX34";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询当前登陆用户的商品客户价
	 */
	@RequestMapping("/selectSPKHJG.action")
	public Map selectSPKHJG(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String ZTID = cds.getField("ZTID", 0);
			String SPXX01 = cds.getField("SPXX01", 0);
			String HBID = cds.getField("HBID", 0);
			String sql="SELECT ZCXX01, SPXX01, KHSPJGI01 FROM W_KHGX A, W_KHSPJGB B WHERE A.ZTID = B.ZCXX01 AND A.KHLX01 = B.KHLX01 AND A.ZTID = '0124' AND B.SPXX01 = '1161' AND A.HBID = '0149'";
			map=queryForMap(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询店铺名称
	 */
	@RequestMapping("/selectGSMC.action")
	public Map selectGSMC(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String gsid = cds.getField("gsid", 0);
			String sql = "SELECT A.ZCXX01,A.ZCXX57,A.ZCXX02,A.ZCXX64,A.ZCXX27 DPTP " +
					           "FROM W_ZCXX A WHERE ZCXX01='"+gsid+"'";
			map=queryForMap(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询店铺名称
	 */
	@RequestMapping("/selectPPMC.action")
	public Map selectPPMC(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String PPB01 = cds.getField("PPB01", 0);
			String sql="SELECT PPB01, PPB02 FROM PPB WHERE PPB01='"+PPB01+"'";
			map=queryForMap(o2o, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询商品属性名称
	 */
	@RequestMapping("/selectspsx.action")
	public Map selectspsx(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String SPFL03_CODE = cds.getField("SPFL03_CODE", 0);
			String sql="select spfl, sxbh, sxname from W_SPFLSX where spfl='"+SPFL03_CODE+"'";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询商品属性值
	 */
	@RequestMapping("/selectspsxz.action")
	public Map selectspsxz(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String sxbh = cds.getField("sxbh", 0);
			String SPFL03_CODE = cds.getField("SPFL03_CODE", 0);
			String sql="select sxzdm, sxzname from W_SPFLSXITEM where spfl='"+SPFL03_CODE+"' and sxbh='"+sxbh+"'";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询首页滚动企业
	 */
	@RequestMapping("/selectcompany.action")
	public Map selectcompany(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			List list = new ArrayList();
			//经销商
			List listJXS = selectNewZCGS("42");
			list.addAll(listJXS);
			//生产企业
			List listSCQY = selectNewZCGS("43");
			list.addAll(listSCQY);
			//维修厂
			List listWXC = selectNewZCGS("44");
			list.addAll(listWXC);
			
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @todo 查询不同类型注册公司倒序前15条数据
	 * @param gslx
	 * @return
	 * @throws Exception
	 */
	public List selectNewZCGS(String gslx) throws Exception{
		List list = new ArrayList();
		try{
			String sql="select a.zcxx01, a.zcxx02, b.lx from w_zcgs a, w_gslx b where a.zcxx01<>'JL' and a.zcxx01=b.gsid and a.zcxx02<>'null'" +
			 "AND a.zcgs01=4 and b.lx='"+gslx+"' ORDER BY a.zcxx14 desc limit 15";
			list = queryForList(o2o,sql);
		}catch(Exception ex){
			throw ex;
		}
		return list;
	}
	
	/**
	 * 查询新加入品牌
	 */
	@RequestMapping("/selectpinpai.action")
	public Map selectpinpai(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String sql="select ppb01,ppb02,b.zcxx01,b.zcxx02 from ppb a,w_zcxx b where a.zcxx01=b.zcxx01 and a.ppb04=1 order by ppb01 desc limit 20 ";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询厂商报表
	 */
	@RequestMapping("/selectALLXX.action")
	public Map selectALLXX(String XmlData){
		Map hm = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String sql="select  A.dqbzm01, A.dqbzm02 from DQBZM A WHERE A.dqbzm03='1' AND A.dqbzm01 IN(SELECT PROVINCE FROM W_SHDZWH)  ORDER BY A.dqbzm01 ASC";
			List list1 = queryForList(o2o, sql);
			for (int i=0; i<list1.size(); i++) {
				Map map = (Map) list1.get(i);
				String dqbzm01 = map.get("dqbzm01").toString();
				String sql1 = "SELECT count(1) FROM W_SHDZWH B, W_ZCXX C, w_gslx d WHERE B.WLDW01 = C.ZCXX01 AND C.ZCXX01=D.gsid AND D.lx = '43' AND B.PROVINCE = '"+dqbzm01+"'";
				int productionSUM = queryForInt(o2o, sql1);
				map.put("productionSUM", productionSUM);
				
				String sql2 = "SELECT count(1) FROM DQBZM A, W_SHDZWH B, W_ZCXX C, w_gslx d, w_spgl e WHERE B.WLDW01 = C.ZCXX01 AND D.lx = '43' AND c.zcxx01 = e.zcxx01 AND B.PROVINCE = A.dqbzm01 AND B.PROVINCE = '"+dqbzm01+"'";
				int spsum = queryForInt(o2o, sql2);
				map.put("spsum", spsum);
				
				String sql3 = "SELECT count(1) FROM DQBZM A, W_SHDZWH B, W_ZCXX C, w_gslx d, w_goods e WHERE B.WLDW01 = C.ZCXX01 AND barcode<>'' AND D.lx = '43' AND c.zcxx01 = e.zcxx01 AND B.PROVINCE = A.dqbzm01 AND B.PROVINCE = '"+dqbzm01+"'";
				int sptmsum = queryForInt(o2o, sql3);
				
				map.put("sptmsum", sptmsum);
				
				String sql5 = "SELECT count(1) FROM W_SHDZWH B, W_ZCXX C, w_gslx d WHERE B.WLDW01 = C.ZCXX01 AND C.ZCXX01=D.gsid AND D.lx = '44' AND B.PROVINCE = '"+dqbzm01+"'";
				int Repair_enterpriseSUM = queryForInt(o2o, sql5);
				map.put("Repair_enterpriseSUM", Repair_enterpriseSUM);

			}
			hm.put("list", list1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	
	
	/**
	 * 根据省份查询该省份企业
	 */
	@RequestMapping("/accordanceProvinceSelectcompany.action")
	public Map accordanceProvinceSelectcompany(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String dqbzm01 = cds.getField("dqbzm01", 0);
			String sql = "SELECT DISTINCT C.zcxx01, C.zcxx02, date_format(C.zcxx11,'%Y-%m-%d %h:%m:%s') zcxx11, C.zcxx08, C.zcxx03, C.zcxx06  FROM DQBZM A, W_SHDZWH B, W_ZCXX C, w_gslx d WHERE B.WLDW01 = C.ZCXX01 AND D.lx = '43' AND B.PROVINCE = A.dqbzm01 AND B.PROVINCE = '"+dqbzm01+"'";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据分类关联查找商城推介商品
	 */
	@RequestMapping("/shopRecommend.action")
	public Map shopRecommend(String XmlData){
		Map map = new HashMap();
		try {
			cds=new DataSet(XmlData);
			String dqbzm01 = cds.getField("SPFL02", 0);
			String sql = "SELECT SPXX04,SPTP02 FROM W_SPXX A,W_SPTP B WHERE A.SPXX01=B.SPXX01 AND A.SPFL02='"+dqbzm01+"' limit 4";
			List list=queryForList(o2o, sql);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 商品详情页面查询车系
	 * */
	/**
	 * [{A:"A",B:"{C:'C'}"}]
	 */
	@RequestMapping("/selectW_SPCX.action")
	public Map selectW_SPCX(String XmlData) throws Exception{
		Map map = new HashMap();
		try {
			List list=null;
			cds=new DataSet(XmlData);
			// 查询品牌和MMID
			String sql = "SELECT B.BRAND,B.CAR_TYPE BrandName "
					   + "FROM W_SPCX A,W_AUTOMOBILE B "
					   + "WHERE A.MOBILE_MODLE_ID = B.MOBILE_MODLE_ID  AND A.ZCXX01='" + cds.getField("ZCXX01", 0) + "' "
					   + "AND A.SPXX01='" + cds.getField("SPXX01", 0) + "' "
					   + "GROUP BY BrandName " ;
			List BrandList=queryForList(o2o, sql);
			ArrayList lists = new ArrayList();
			for (int i = 0; i < BrandList.size(); i++) {
				Map Brandmap = (Map) BrandList.get(i);
				String cartype = Brandmap.get("BrandName").toString();
				String spcxsql = "SELECT B.MOBILE_MODLE TYPE, B.MOBILE_MODLE_ID TYPECODE "
						+ "FROM W_SPCX A, W_AUTOMOBILE B "
						+ "WHERE A.MOBILE_MODLE_ID = B.MOBILE_MODLE_ID "
						+ "AND A.ZCXX01='" + cds.getField("ZCXX01", 0) + "' "
						+ "AND A.SPXX01='" + cds.getField("SPXX01", 0) + "' "
						+ "AND B.CAR_TYPE = '" + cartype + "' "
						+ "GROUP BY B.MOBILE_MODLE_ID";
				List cxList = queryForList(o2o, spcxsql);
				//处理数据
				Brandmap.put("cxlist", cxList);
				lists.add(Brandmap);
			}
			map.put("lists", lists);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return map;
	}
	
	//新增品牌
	@RequestMapping("/insertPPB.action")
	public Map<String, Object> insertPPB(String XmlData,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> hm =new HashMap<String, Object>();
		try {
			XmlData= JLTools.unescape(XmlData);
			cds=new DataSet(XmlData);
			String HBID=cds.getField("HBID", 0);
			String PPMC=cds.getField("ppmc", 0);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date = df.format(new Date());//获取当前系统时间				
			String s="SELECT DISTINCT MAX(CAST(PPB01 AS SIGNED INTEGER)) as ppb01 FROM PPB";
			int PPB01=queryForInt(o2o, s);
			PPB01 = PPB01+1;
			String sPPB01=""+PPB01;
			int j = sPPB01.length();
			for(int i=j;i<6;i++)
			{
				sPPB01="0"+sPPB01;
			}
			
			String sql="INSERT INTO PPB(ZCXX01,PPB01,PPB02,time01,PPB04,PPB06) VALUES('"+HBID+"','"+sPPB01+"','"+PPMC+"','"+date+"','0',UPPER(SUBSTRING(fn_getpy('" + PPMC + "'),1,1)))";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			String[] ppflList = cds.getField("ppfl", 0).split(",");
			for(int i=0; i<ppflList.length ; i++){
				String sql2="INSERT INTO W_PPFL(ZCXX01,PPB01,SPFL01) VALUES('"+HBID+"','"+sPPB01+"','"+ppflList[i]+"')";
				Map row2=getRow(sql2, null, 0);
				execSQL(o2o, sql2, row2);
			}
						
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			List<MultipartFile> listFile= mrRequest.getFiles("file");
			for (int i = 0; i < listFile.size(); i++) {
				MultipartFile file = listFile.get(i);
				String fileName = file.getOriginalFilename();
				String fileSuffix=fileName.substring(fileName.lastIndexOf("."));
				String PPTP02=HBID+"_"+PPMC+"_"+i+fileSuffix;//图片名称，店铺编码+图片类型+图片格式
				String path=JlAppResources.getProperty("path_pptp");
				String PPTP03=path+PPTP02;
				
				String sql3="INSERT INTO W_PPTP(ZCXX01,PPB01,PPTP01,PPTP02,PPTP03,PPTP04) VALUES('"+HBID+"','"+sPPB01+"','"+i+"','"+PPTP02+"','"+PPTP03+"','"+date+"')";
				Map row3=getRow(sql3, null, 0);
				execSQL(o2o, sql3, row3);

				Object obj=request.getParameter("file");
				if(!upload.isMultipartContent(request)&&obj==null){
				
				}
				else{
				//hm.put("files", "file");
				hm.put("imgPath",path);
				hm.put("imgName", PPTP02);
				JSONArray jso =JSONArray.fromObject(XmlData);
				hm.put("sqlParam", (List<Map<String, Object>>)jso);
				//TODO  
				//调用图片上传公用方法
				hm=JLTools.fileUploadNew(file, hm);
				if("success".equals(hm.get("Flag"))&&true==Boolean.parseBoolean(hm.get("STATE").toString())){
					hm.clear();
					hm.put("STATE", "1");
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", "0");
			throw e;
		}
		return hm;
	} 	
	
	//审核品牌信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/auditPPXX.action")
	public Map auditPPXX(String XmlData) throws Exception {
		Map hm = new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql="UPDATE PPB SET PPB04='"+cds.getField("STATE", 0)+"',PPB05='"+cds.getField("SPYJ", 0)+"' WHERE PPB01='"+cds.getField("PPB01", 0)+"'";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			hm.put("STATE", 1);
			//审核通过时，对接ERP系统
			if(cds.getField("STATE", 0).equals("1")){
				Map map = pubService.getECSURL("JL");
				if(map.get("DJLX") != null){
					String ppSQL = "SELECT PPB02 PPMC,fn_getpy(PPB02) PPYW FROM PPB WHERE PPB01='"+cds.getField("PPB01", 0)+"'";
					Map ppMap = queryForMap(o2o,ppSQL);
					if(map.get("DJLX").equals("V9")){
						String returnStr = v9BasicData.createSPPP(ppMap, map);
						JSONObject jsonObject = JSONObject.fromObject(returnStr);
						JSONObject returnData =(JSONObject) jsonObject.get("data");
						if(!returnData.getString("JL_State").equals("1")){
							throw new Exception("品牌对接V9失败，"+returnData.get("JL_ERR").toString());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", 0);
		} 
		return hm;
	}

	//根据OE码查询商品信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectSPXXbyOE.action")
	public Map selectSPXXbyOE(HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		Map hm = new HashMap();
		String oe=request.getParameter("oe");
		try {
			String sql="SELECT A.SPXX01,A.ZCXX01,B.ZCXX02 FROM W_SPGLLJH A,W_ZCXX B,W_SPGL C WHERE C.SPGL12='1'AND A.ZCXX01=B.ZCXX01 AND C.spxx01 =A.spxx01 AND A.SPGLLJH01 like'%"+oe+"%'";
			List resultCount=queryForList(o2o, sql);
			if(resultCount.size()>1){
				map.put("STATE", 2);
				map.put("data", null);
			}else{
				hm = queryForMap(o2o, sql);
				if(hm!=null){
					map.put("STATE", 1);
					map.put("data", hm);
				}
				else{
					map.put("STATE", 0);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", 0);
		} 
		return map;
	}
	
	//根据商品条码查询信息
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectSPXXbySPTM.action")
	public Map selectSPXXbySPTM(HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		Map hm = new HashMap();
		String SPTM=request.getParameter("sptm");
		try {
			String sql="SELECT A.SPXX01,A.ZCXX01,B.ZCXX02 FROM W_GOODS A,W_ZCXX B,W_SPGL C WHERE C.SPGL12='1'AND C.spxx01 =A.spxx01 AND  A.ZCXX01=B.ZCXX01 AND A.BARCODE like '%"+SPTM+"%'";
			List resultCount=queryForList(o2o, sql);
			if(resultCount.size()>1){
				map.put("STATE", 2);
				map.put("data", null);
			}else{
				hm = queryForMap(o2o, sql);
				if(hm!=null){
					map.put("STATE", 1);
					map.put("data", hm);
				}
				else{
					map.put("STATE", 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", 0);
		} 
		return map;
	}
}
