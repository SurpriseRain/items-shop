package com.jlsoft.o2o.gggl.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.DATE;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
/**
 * 楼层
 * @author asus
 *
 */
@Controller
@RequestMapping("/oper_GGGL")
public class Oper_GGGL extends JLBill{
	
	/***
	 * 兴隆查询促销商品
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectGGWs.action")
	public List<Map<String, Object>> selectGGWs(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map1= new HashMap<String, Object>();
		//查询广告类型
		String sql="select GGLXZ01,ZCXX01,GGLX01,GGLXZ02,GGLXZ03,DQXX01 from W_GGLXZ where ZCXX01='"+cds.getField("ZCXX01", 0)+"' and GGLX01='"+cds.getField("GGLX01", 0)+"' and DQXX01='"+cds.getField("DQXX01", 0)+"' and YXBJ=1 ORDER BY GGLXZ03;";
		String sql1="";
		String sql2="";
		Map<String, Object> ggwList=new HashMap<String, Object>();
		List<Map<String, Object>> list = queryForList(o2o, sql);
		//查询广告时间段取最小时间
		String sqlsj="select b.GGQJ04,b.GGQJ02,b.GGQJ03 from W_GGW a,W_GGQJ b,W_GGLXZ c where a.ZCXX01=b.ZCXX01 and b.ZCXX01 = c.ZCXX01 and a.GGLXZ01 = c.GGLXZ01 and a.DQXX01 = c.DQXX01 and a.GGW01=b.GGW01 and a.ZCXX01='"+cds.getField("ZCXX01", 0)+"' and a.DQXX01='"+cds.getField("DQXX01", 0)+"'" +
				" and c.GGLX01='"+cds.getField("GGLX01", 0)+"'" +
				" and b.GGQJ04>=str_to_date('"+cds.getField("date_s", 0).toString()+"','%Y-%m-%d')" +
				" and b.GGQJ04<=str_to_date('"+cds.getField("date_e", 0).toString()+"','%Y-%m-%d')" +
				" and b.GGQJ02='"+cds.getField("sjqj_s", 0).toString()+"'" +
				" and b.GGQJ03='"+cds.getField("sjqj_e", 0).toString()+"'" +
				" and a.YXBJ=1 and b.YXBJ=1 and c.YXBJ =1" +
				" order by b.GGQJ04,b.GGQJ02";
		List<Map<String, Object>> list1=queryForList(o2o, sqlsj);
		if(list1.size()>0){
			map1= list1.get(0);
		}
		List<Map<String, Object>> ggws = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map= list.get(i);
			//查询广告位及占用商品
			sql1="select a.GGW01,DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ02),'%Y-%m-%d %H:%i:%s')GGQJ02,DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ03),'%Y-%m-%d %H:%i:%s')GGQJ03," +
							"(select GGGL04 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 = concat(b.GGQJ04,' ', b.GGQJ02) and gggl07 = concat(b.GGQJ04,' ', b.GGQJ03) and GGGL08=2)SPMC, " +
							"(select GGGL05 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 = concat(b.GGQJ04,' ', b.GGQJ02) and gggl07 = concat(b.GGQJ04,' ', b.GGQJ03) and GGGL08=2)SPJG, " +
							"(select GGGL09 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 = concat(b.GGQJ04,' ', b.GGQJ02) and gggl07 = concat(b.GGQJ04,' ', b.GGQJ03) and GGGL08=2)SQR," +
							"(select DATE_FORMAT(GGGL10,'%Y-%m-%d %H:%i:%s') From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 = concat(b.GGQJ04,' ', b.GGQJ02) and gggl07 = concat(b.GGQJ04,' ', b.GGQJ03) and GGGL08=2)SQRQ" +
							" from W_GGW a,W_GGQJ b where a.ZCXX01=b.ZCXX01 and a.GGW01=b.GGW01 and a.ZCXX01='"+cds.getField("ZCXX01", 0)+"' and a.DQXX01='"+cds.getField("DQXX01", 0)+"' and a.GGLXZ01='"+map.get("GGLXZ01").toString()+"'" +
									" and b.GGQJ04=str_to_date('"+map1.get("GGQJ04").toString()+"','%Y-%m-%d')" +
									" and b.GGQJ02='"+map1.get("GGQJ02").toString().toString()+"'" +
									" and b.GGQJ03='"+map1.get("GGQJ03").toString().toString()+"'" +
									" and a.YXBJ=1 and b.YXBJ=1" +
									" order by a.GGLXZ01,a.GGW01;";
			List<Map<String, Object>> ggw= queryForList(o2o, sql1);
			map.put("leftggwsp", ggw);
			ggws.add(map);
		}
		
		/*sql="select A.GGLXZ03,A.ZCXX01,A.GGLX01,A.GGLXZ02,A.DQXX01,A.DQXX02,C.GGQJ02,C.GGQJ03," +
				"(select GGGL04 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and GGGL08=2)SPMC," +
				"(select GGGL05 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and GGGL08=2)SPJG," +
				"(select GGGL09 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and GGGL08=2)SQR," +
				"(select GGGL10 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and GGGL08=2)SQRQ" +
				" from W_GGLXZ A,W_GGW b,W_GGQJ c " +
				"where a.ZCXX01=b.ZCXX01 and a.GGLXZ01=b.GGLXZ01" +
				" and a.DQXX01=b.DQXX01 and a.ZCXX01=c.ZCXX01" +
				" and b.GGW01=c.GGW01 and a.ZCXX01='"+cds.getField("ZCXX01", 0)+"'" +
				" and a.DQXX01='"+cds.getField("DQXX01", 0)+"'" +
				" and a.GGLX01 ='"+cds.getField("GGLX01", 0)+"'" +
				" and c.GGQJ02=str_to_date('2015-12-05 10:00:00','%Y-%m-%d %H:%i:%s')" +
				" and c.GGQJ03=str_to_date('2015-12-05 11:00:00','%Y-%m-%d %H:%i:%s');";
		List<Map<String, Object>> ggws = queryForList(o2o, sql);*/
		System.out.println(ggws.toString()+"##################"+ggws.size());
		return ggws;
	}
	
	/***
	 * 兴隆查询促销商品
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectRightGGWs.action")
	public List<Map<String, Object>> selectRightGGWs(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map map1= new HashMap<String, Object>();
		String sql1="";
		String sql2="";
		List<Map<String, Object>> ggws = new ArrayList();
			sql1="select a.GGW01,DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ02),'%Y-%m-%d %H:%i:%s')GGQJ02,DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ03),'%Y-%m-%d %H:%i:%s')GGQJ03," +
					"(select GGGL04 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 =DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ02),'%Y-%m-%d %H:%i:%s') AND gggl07 =DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ03),'%Y-%m-%d %H:%i:%s') and GGGL08=2)SPMC, " +
					"(select GGGL05 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 =DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ02),'%Y-%m-%d %H:%i:%s') AND gggl07 =DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ03),'%Y-%m-%d %H:%i:%s') and GGGL08=2)SPJG, " +
					"(select GGGL09 From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 =DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ02),'%Y-%m-%d %H:%i:%s') AND gggl07 =DATE_FORMAT(concat( b.GGQJ04,' ', b.GGQJ03),'%Y-%m-%d %H:%i:%s') and GGGL08=2)SQR," +
					"(select DATE_FORMAT(GGGL10,'%Y-%m-%d %H:%i:%s') From w_gggl where zcxx01=a.ZCXX01 and gglxz01=a.GGLXZ01 and dqxx01=a.DQXX01 and ggw01=b.GGW01 and gggl06 =b.GGQJ02 AND gggl07 =b.GGQJ03 and GGGL08=2)SQRQ" +
					" from W_GGW a,W_GGQJ b where a.ZCXX01=b.ZCXX01 and a.GGW01=b.GGW01 and a.ZCXX01='"+cds.getField("ZCXX01", 0)+"' and a.DQXX01='"+cds.getField("DQXX01", 0)+"' and a.GGLXZ01='"+cds.getField("GGLXZ01", 0).toString()+"'" +
							" and b.GGQJ04>='"+cds.getField("date_s", 0).toString()+"'" +
							" and b.GGQJ04<='"+cds.getField("date_e", 0).toString()+"'" +
							" and b.GGQJ02='"+cds.getField("sjqj_s", 0).toString()+"'" +
							" and b.GGQJ03='"+cds.getField("sjqj_e", 0).toString()+"'" +
							" and a.YXBJ=1 and b.YXBJ=1 and a.GGW01='" +cds.getField("GGW01", 0).toString()+"'"+
							" order by a.GGLXZ01,a.GGW01,b.GGQJ04,b.GGQJ02;";
			List<Map<String, Object>> ggw= queryForList(o2o, sql1);
		return ggw;
	}
	/*****
	 * 申请广告位，结算
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertGGGL.action")
	public Map insertLSGGW(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map hm =new HashMap();
		String XmlData = JLTools.unescape(request.getParameter("XmlData"));//商品介绍
		cds=new DataSet(XmlData);
		Map map = new HashMap();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = format.format(new Date());
		
			try {
				System.out.println(cds.getField("ggwsp", 0));
				JSONArray jsonarry = JSONArray.fromObject(cds.getField("ggwsp",0));
				String sql = "";
				Integer ggspid = 0;
				Integer ggsp02 = PubFun.updateWBHZT(o2o, "W_GGSPTP", 1);
				String spxx02 = JLTools.getID_X(ggsp02, 6);
				int num = 0;
				System.out.print(num + "##########" + XmlData);
				JSONObject ggw = new JSONObject();
				for (int i = 0; i < jsonarry.size(); i++) {
					ggw = jsonarry.getJSONObject(i);
					ggspid = PubFun.updateWBHZT(o2o, "W_GGGL", 1);/*'"+cds.getField("GGGL04", 0)+"'*/
					sql = "insert into w_gggl(GGGL01,ZCXX01,GGLXZ01,GGW01,GGGL02,GGGL04,GGGL05,GGGL06,GGGL07,DQXX01,GGGL08,GGGL09,GGGL10,GGGL11,GGGL12,GGGL15,YXBJ)"
							+ "VALUES('"
							+ ggspid
							+ "','"
							+ ggw.getString("ZCXX01").toString()
							+ "','"
							+ ggw.getString("GGLXZ01").toString()
							+ "','"
							+ ggw.getString("GGW01")
							+ "','"
							+ spxx02
							+ "',GGGL04?,GGGL05?,str_to_date('"
							+ ggw.getString("GGQJ02").toString()
							+ "','%Y-%m-%d %H:%i:%s'),str_to_date('"
							+ ggw.getString("GGQJ03").toString()
							+ "','%Y-%m-%d %H:%i:%s'),'"
							+ ggw.getString("DQXX01").toString()
							+ "',GGGL08?,GGGL09?,str_to_date('"
							+ strDate
							+ "','%Y-%m-%d %H:%i:%s'),GGGL11?,GGGL12?,GGGL15?,'1')";
					Map row = getRow(sql, null, 0);
					num = execSQL(o2o, sql, row);
					if(num==0){
						throw new Exception("发布广告失败");
					}
					map.put("STATE",1);
				}
				
				MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
				List<MultipartFile> listFile= mrRequest.getFiles("files");
				DiskFileItemFactory factory=new DiskFileItemFactory();
				ServletFileUpload upload=new ServletFileUpload(factory);
				//Map row ;
				if(!upload.isMultipartContent(request)){
					map.put("STATE", "0");
					return map;
				}
				String tpName="";
				for(int i=0;i<listFile.size();i++){
					JSONObject obj = (JSONObject) jsonarry.get(i);
					MultipartFile file=listFile.get(i);
					String fileName=file.getOriginalFilename();//上传的原图片名+后缀
					String nameType=fileName.substring(fileName.lastIndexOf(".")+1);//文件名后缀,jpg,png...
					if(file.getSize()>0){
						tpName=spxx02+"."+nameType;//新图片名加上后缀
						hm.put("spxx02",spxx02);//图片名称
						hm.put("ZCXX01", jsonarry.getJSONObject(0).getString("ZCXX01").toString());
						hm.put("path", "path_ggsptp");
						//生成商品原图
						String newTpName=spxx02+"_big."+tpName.split("\\.")[1];
						
						uploadImg(newTpName,request,hm,map,file);
					}
					map.put("STATE", "1");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				map.put("error", e.getMessage());
			}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> uploadImg(String newTpName,HttpServletRequest request,Map hm,Map map,MultipartFile file) throws Exception{
		 BufferedOutputStream bos = null;
		 String filepath;
		 String path_sptp = JlAppResources.getProperty(hm.get("path").toString());
		 filepath = path_sptp+hm.get("ZCXX01").toString()+"/"+hm.get("spxx02").toString()+"/images/";
		 
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
	
}