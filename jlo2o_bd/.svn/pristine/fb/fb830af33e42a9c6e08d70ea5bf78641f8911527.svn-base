package com.jlsoft.c2b.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
@Controller
@RequestMapping("/FWSP")
public class Oper_FWSP extends JLBill{
	JLTools tool = new JLTools();
	/**
	 * @todo 查询服务商品
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectFWSP")
	public Map<String, Object> selectFWSP(String json) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds=new DataSet(json);
		try{
			String id = cds.getField("id", 0);
			String sql = "SELECT A.ID,B.ServerTypeName,A.ServiceName,A.ServiceTitlePicturePath,A.ServiceDetailPicturePath,A.OriginalPrice,A.CurrentPrice," +
					           "(SELECT ZCXX02 FROM W_ZCGS WHERE ZCXX01=A.PublisherId) ZCXX02 " +
					           "FROM SERVICE A,SERVICETYPE B WHERE A.ServiceTypeId=B.ID AND A.ID="+id+"";
			returnMap = queryForMap(o2o,sql);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return returnMap;
	}
	/**
	 * @todo 查询服务商品列表
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/selectFWSPList")
	public Map<String, Object> selectFWSPList(String json) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds=new DataSet(json);
		try{
			String num = cds.getField("num", 0);
			String sql = "SELECT A.ID,A.ServiceTitlePicturePath,A.OriginalPrice,A.CurrentPrice,A.ServiceName," +
							   "(SELECT ZCXX02 FROM W_ZCGS WHERE ZCXX01=A.PublisherId) ZCXX02 FROM SERVICE A WHERE A.Status=1 ";
			if(num != null && !num.equals("")){
				sql = sql + "limit "+num;
			}
			returnMap.put("fwspList", queryForList(o2o,sql));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return returnMap;
	}
	/**
	 * 查询服务名称
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select_FWMC.action")
	public Map<String, Object> select_Produce() throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String sql = "";
			sql = "SELECT ID,SERVERTYPENAME FROM SERVICETYPE WHERE PARENTID = 1";
			
			returnMap.put("fwmcList", queryForList(o2o,sql));
		} catch (Exception ex) {
			throw ex;
		}
		return returnMap;
	}
	/**
	 * 发布服务套餐
	 * @param XmlData
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertFWSP.action")
	public Map<String, Object> insertDPXX(String XmlData,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> hm =new HashMap<String, Object>();
		try {
			XmlData= JLTools.unescape(XmlData);
			cds=new DataSet(XmlData);//信息
			String titlePic = "";
			String detailPic = "";
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			Iterator iterator = mrRequest.getFileNames();
			//判断上传图片是否使用默认图片
			if(iterator.hasNext()){
			}else{
				titlePic = "";
				detailPic = "";
			}
			while(iterator.hasNext()){
				String fileType = (String)iterator.next();
				MultipartFile file = mrRequest.getFile(fileType);
				String fileName = "";
				String name = tool.getTimestamp();
				fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
				if("titlePic".equals(fileType)){
					titlePic = fileName;
				} else if("detailPic".equals(fileType)){
					detailPic = fileName;
				}
				// 上传图片到服务器
				Map mapImg = new HashMap();
				mapImg.put(
						"imgPath",
						JlAppResources.getProperty("path_fftp")
								+ "fwsp");
				mapImg.put("imgName", fileName);
				JLTools.fileUploadNew(file, mapImg);
			}
			String sql = "INSERT INTO service(Status,ServiceTypeId,ServiceName,PublisherId,OriginalPrice,"
					   + "CurrentPrice,ServiceTitlePicturePath,ServiceDetailPicturePath) "
					   + "VALUES(0,fwlx?, fwmc?, wxc?, yjg?, xjg?, '" + titlePic + "', '" + detailPic + "')";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, row);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return hm;
	}
	//商品上架、下架(商品同步)
			@RequestMapping("/update_FWTC.action")
			public Map<String, Object>update_SPFB_SPXX(String XmlData) throws DataAccessException, Exception{
				Map map=new HashMap();
				cds=new DataSet(XmlData);
				try{
					String sql="UPDATE service SET STATUS =(CASE WHEN STATUS='0' THEN '1' ELSE '0' END) WHERE id='"+cds.getField("id", 0)+"'";
					Map	row = getRow(sql, null, 0);
					execSQL(o2o, sql, row);
					map.put("STATE", "1");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("STATE", "0");
				}
				return map;
			}
	//商品编辑查询
			@RequestMapping("/update_FWTC_BJ.action")
			public Map<String, Object>update_FWTC_BJ(String XmlData) throws DataAccessException, Exception{
				Map map=new HashMap();
				cds=new DataSet(XmlData);
				try{
					String sql="SELECT ServiceTypeId,ServiceName,OriginalPrice,CurrentPrice,ServiceTitlePicturePath,ServiceDetailPicturePath FROM service WHERE id='"+cds.getField("fwtcid", 0)+"'";
					Map	row = getRow(sql, null, 0);
					map.put("fwmcList", queryForList(o2o,sql));
					map.put("STATE", "1");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("STATE", "0");
				}
				return map;
			}
	//商品编辑更新
			@RequestMapping("/insertFWSP_UP.action")
			public Map<String, Object> insertFWSP_UP(String XmlData,HttpServletRequest request,HttpServletResponse response) throws Exception{
				Map<String, Object> hm =new HashMap<String, Object>();
				try {
					XmlData= JLTools.unescape(XmlData);
					cds=new DataSet(XmlData);//信息
					String titlePic = "";
					String detailPic = "";
					MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
					Iterator iterator = mrRequest.getFileNames();
					//判断上传图片是否使用默认图片
					if(iterator.hasNext()){
						String sql ="UPDATE service SET ServiceTypeId=fwlx?,ServiceName=fwmc?,PublisherId=wxc?,OriginalPrice=yjg?,CurrentPrice= xjg?,ServiceTitlePicturePath= '" + titlePic + "',ServiceDetailPicturePath='" + detailPic + "' WHERE id=fwtcid?";
						System.out.println(sql);
						Map row=getRow(sql, null, 0);
						execSQL(o2o, sql, row);
					}else{
						titlePic = "";
						detailPic = "";
					while(iterator.hasNext()){
						String fileType = (String)iterator.next();
						MultipartFile file = mrRequest.getFile(fileType);
						String fileName = "";
						String name = tool.getTimestamp();
						fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
						if("titlePic".equals(fileType)){
							titlePic = fileName;
						} else if("detailPic".equals(fileType)){
							detailPic = fileName;
						}
						// 上传图片到服务器
						Map mapImg = new HashMap();
						mapImg.put(
								"imgPath",
								JlAppResources.getProperty("path_fftp")
										+ "fwsp");
						mapImg.put("imgName", fileName);
						JLTools.fileUploadNew(file, mapImg);
					}
						String sql ="UPDATE service SET ServiceTypeId=fwlx?,ServiceName=fwmc?,PublisherId=wxc?,OriginalPrice=yjg?,CurrentPrice= xjg? WHERE id=fwtcid?";
						System.out.println(sql);
						Map row=getRow(sql, null, 0);
						execSQL(o2o, sql, row);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return hm;
			}
	//保养分类数据
	@RequestMapping("/select_ServiceType.action")
	public Map<String, Object> selectServiceType(String XmlData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "";
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);//信息
			String initialid = cds.getField("initialid", 0);
			if(initialid != null &&!"".equals(initialid)){
				sql ="(SELECT id,ServerTypeName,Level,initialid,IFNULL(ParentId,0) ParentId FROM SERVICETYPE WHERE level = '1' and initialid='"+ initialid +"') union (select id,ServerTypeName,Level,initialid,IFNULL(ParentId,0) ParentId from SERVICETYPE WHERE level = '2' and initialid='"+ initialid +"' and ParentId in (SELECT ID FROM SERVICETYPE WHERE level = '1' and initialid='"+ initialid +"'))";
			}	
			List spfllist = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("spfllist", spfllist);
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//根据市取的地区
	@RequestMapping("/select_dqmc.action")
	public Map<String, Object> selectDQDM(String XmlData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "";
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);//信息
			String dqmc = cds.getField("dqmc", 0);
			if(dqmc != null &&!"".equals(dqmc)){
				sql = "select dqbzm01,dqbzm02 from dqbzm where dqbzm_dqbzm01 in (select dqbzm01 from dqbzm  where dqbzm02 = '"+ dqmc +"')";
			} 
			List dqlist = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("dqlist", dqlist);
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//根据保养/美容类型查询下面所有的商品列表
	@RequestMapping("/select_ServiceSP.action")
	public Map<String, Object> selectServiceSP(String XmlData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//每页多少条记录
			Integer pageCount = 0;
			//总共多少条
			Integer page = 0;
			String sql = "";
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);//信息
			String servicetypeid = cds.getField("servicetypeid", 0);
			String page_Count = cds.getField("pageCount", 0);
			String _page = cds.getField("page", 0);
			if(page_Count != null && !"".equals(page_Count)){
				pageCount = Integer.parseInt(page_Count);
			}else{
				pageCount = 10;
			}
			if(_page != null && !"".equals(_page)){
				page = (Integer.parseInt(_page)-1)*pageCount;
			}else{
				page = 1;
			}
			if(servicetypeid != null &&!"".equals(servicetypeid)){
				if(_page.equals("1")){ 
					//查询总条数
					sql = "SELECT * FROM service where Status='1' and ServiceTypeId = '"+ servicetypeid +"'";
					List totallist = queryForList(o2o,sql);
					resultMap.put("total", totallist.size());
					//查询第一页
					sql = "SELECT * FROM service where Status='1' and ServiceTypeId = '"+ servicetypeid +"' limit "+page+","+pageCount;
				}else{
					
					sql = "SELECT * FROM service where Status='1' and ServiceTypeId = '"+ servicetypeid +"' limit "+page+","+pageCount;
				}
			} 
			List servicelist = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("servicelist", servicelist);
				resultMap.put("filepath", JlAppResources.getProperty("path_fftp")+"fwsp/");
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//查看具体商品的详细信息
	@RequestMapping("/select_ServiceSPdetail.action")
	public Map<String, Object> selectServiceSPdetail(String XmlData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "";
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);//信息
			String serviceid = cds.getField("serviceid", 0);
			if(serviceid != null &&!"".equals(serviceid)){
				sql = "SELECT service.*,zcxx.zcxx27,zcxx.zcxx06,zcxx.Longitude,zcxx.Latitude from  (select * from  service where  id = '"+ serviceid +"') service"+
						" left join w_zcxx zcxx on zcxx.zcxx01 = service.PublisherId ";
			} 
			List servicelist = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("servicelist", servicelist);
				resultMap.put("filepath", JlAppResources.getProperty("path_fftp")+"fwsp/");
				resultMap.put("dpfilepath", JlAppResources.getProperty("path_sptp")+"dptp/");
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//维修分类数据
	@RequestMapping("/select_ServiceWX.action")
	public Map<String, Object> selectServiceWX(String XmlData) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "";
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);//信息
			//每页多少条记录
			Integer pageCount = 0;
			//总共多少条
			Integer page = 0;
			//纬度
			double lat = 0;
			//经度
			double lng = 0;
			String initialid = cds.getField("initialid", 0);
			String page_Count = cds.getField("pageCount", 0);
			String _page = cds.getField("page", 0);
			String _lat = cds.getField("lat", 0);
			String _lng = cds.getField("lng", 0);
			if(page_Count != null && !"".equals(page_Count)){
				pageCount = Integer.parseInt(page_Count);
			}else{
				pageCount = 10;
			}
			if(_page != null && !"".equals(_page)){
				page = (Integer.parseInt(_page)-1)*pageCount;
			}else{
				page = 1;
			}
			if(_lat != null && !"".equals(_lat)){
				lat = Double.valueOf(_lat); 
			}else{
				lat = 0;
			}
			if(_lng != null && !"".equals(_lng)){
				lng = Double.valueOf(_lng); 
			}else{
				lng = 0;
			}
			if(initialid != null &&!"".equals(initialid)){
				if(_page.equals("1")){ 
					//查询总条数
					sql = "select zcgs.zcxx01,zcgs.zcxx08,zcgs.zcxx02,zcgs.zcxx06,zcxx.zcxx27,zcxx.Longitude,zcxx.Latitude " +
							  "from (select zcxx01,zcxx02,zcxx06,zcxx08 from w_zcgs where zcgs03='" + initialid + "' AND zcgs01='4') zcgs " +
							  "left join w_zcxx zcxx on zcxx.zcxx01 = zcgs.zcxx01";
					List totallist = queryForList(o2o,sql);
					resultMap.put("total", totallist.size());
					//查询第一页
					sql = "select zcgs.zcxx01,zcgs.zcxx08,zcgs.zcxx02,zcgs.zcxx06,zcxx.zcxx27,zcxx.Longitude,zcxx.Latitude,ifnull(round(6378.138*2*asin(sqrt(pow(sin( (zcxx.Latitude *pi()/180-"+lat+"*pi()/180)/2),2)+cos(zcxx.Latitude *pi()/180)*cos("+lat+"*pi()/180)* pow(sin( (zcxx.Longitude*pi()/180-"+lng+"*pi()/180)/2),2)))*1000),0) as distance"+
							 " from (select zcxx01,zcxx02,zcxx06,zcxx08 from w_zcgs where zcgs03='"+initialid+"' AND zcgs01='4') zcgs " +
							 " left join w_zcxx zcxx on zcxx.zcxx01 = zcgs.zcxx01 " +
							 " order by round(6378.138*2*asin(sqrt(pow(sin( (zcxx.Latitude *pi()/180-"+lat+"*pi()/180)/2),2)+cos(zcxx.Latitude *pi()/180)*cos("+lat+"*pi()/180)* pow(sin( (zcxx.Longitude*pi()/180-"+lng+"*pi()/180)/2),2)))*1000) asc limit "+page+","+pageCount;
				}else{
					sql = "select zcgs.zcxx01,zcgs.zcxx08,zcgs.zcxx02,zcgs.zcxx06,zcxx.zcxx27,zcxx.Longitude,zcxx.Latitude,ifnull(round(6378.138*2*asin(sqrt(pow(sin( (zcxx.Latitude *pi()/180-"+lat+"*pi()/180)/2),2)+cos(zcxx.Latitude *pi()/180)*cos("+lat+"*pi()/180)* pow(sin( (zcxx.Longitude*pi()/180-"+lng+"*pi()/180)/2),2)))*1000),0) as distance"+
							 " from (select zcxx01,zcxx02,zcxx06,zcxx08 from w_zcgs where zcgs03='"+initialid+"' AND zcgs01='4') zcgs " +
							 " left join w_zcxx zcxx on zcxx.zcxx01 = zcgs.zcxx01 " +
							 " order by round(6378.138*2*asin(sqrt(pow(sin( (zcxx.Latitude *pi()/180-"+lat+"*pi()/180)/2),2)+cos(zcxx.Latitude *pi()/180)*cos("+lat+"*pi()/180)* pow(sin( (zcxx.Longitude*pi()/180-"+lng+"*pi()/180)/2),2)))*1000) asc limit "+page+","+pageCount;
				}	
			}
			resultMap.put("dpfilepath", JlAppResources.getProperty("path_sptp")+"dptp/");
			List spfllist = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("spfllist", spfllist);
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	
}
