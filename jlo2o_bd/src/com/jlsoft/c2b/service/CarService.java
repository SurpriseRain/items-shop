package com.jlsoft.c2b.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;


@Controller
@RequestMapping("/CarService")
public class CarService extends JLBill{
	JLTools tool = new JLTools();
	/*
	 * 保存车主发布需求
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@RequestMapping("/insertCarService.action")
	public Map<String, Object> insertCarService(String jsonData,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		jsonData = JLTools.unescape(jsonData);
		cds=new DataSet(jsonData);
		try {
			jsonData= JLTools.unescape(jsonData);
			cds=new DataSet(jsonData);//信息
			String carPic = "";
			MultipartHttpServletRequest mrRequest = (MultipartHttpServletRequest)request;
			Iterator iterator = mrRequest.getFileNames();
			
			while(iterator.hasNext()){
				String fileType = (String)iterator.next();
				MultipartFile file = mrRequest.getFile(fileType);
				if(file.getOriginalFilename() != null && file.getOriginalFilename() != ""){
					String fileName = "";
					String name = tool.getTimestamp();
					fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
					if("carPic".equals(fileType)){
						carPic = fileName;
					}
					
					// 上传图片到服务器
					Map mapImg = new HashMap();
					mapImg.put("imgPath",JlAppResources.getProperty("path_fftp")+ "xctp");
					mapImg.put("imgName", fileName);
					JLTools.fileUploadNew(file, mapImg);
				}
				
			}
			//当前系统时间,即发布时间
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String PublishTime = format.format(new Date()).toString();
			String userId = cds.getField("userId", 0);
			//无效时间
			String InvalidTime = cds.getField("InvalidTime", 0);
			//报价
			String QuotedPrice = cds.getField("QuotedPrice", 0);
			//服务类型
			String ServiceTypeID = cds.getField("ServiceTypeID", 0);
			//需求描述
			String DemandDescription = cds.getField("DemandDescription", 0);
			//地址
			String PublishAddress = cds.getField("PublishAddress", 0);
			//车牌Id
			String CarId = cds.getField("CarId", 0);
			//
			String Longitude  = cds.getField("lngt", 0);
			String Latitude = cds.getField("lntt", 0);
			
			String	sql = "INSERT INTO servicedemand(userId, PublishTime, InvalidTime, QuotedPrice, ServiceTypeID, DemandDescription, DemandStatus,PublishAddress,CarNumber,CarBrand,SceneImagePage) "
							     + "VALUES('"+userId+"', '" + PublishTime + "', "
							     + "DATE_ADD('" + PublishTime + "',INTERVAL '" + InvalidTime + "' HOUR), "
							     + "QuotedPrice?, ServiceTypeID?, DemandDescription?,0,PublishAddress?,'"+CarId+"',CarBrand?,'"+carPic+"')";
			Map row=getRow(sql, null, 0);
			execSQL(o2o, sql, 0);

			sql = "UPDATE carowner SET Longitude = " + Longitude + ", Latitude = "+Latitude+" WHERE Id = "+userId+"";
			Map	upRow = getRow(sql, null, 0);
			execSQL(o2o, sql, upRow);
		
			map.put("STATE", 1);
		} catch (Exception e) {
			map.put("STATE", 0);
			throw e;	
		}
		return map;
	}
	
	//查询服务类型
	@RequestMapping("/selectServiceInfo.action")
	public Map<String, Object> selectServiceInfo() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {	
			String sql = "SELECT distinct InitialId,InitialName FROM servicetype where InitialId is not null";
			List list = queryForList(o2o, sql, map);
			map.put("ServiceDetails", list);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	
	//查询具体服务类型详情
	@RequestMapping("/selectType.action")
	public Map<String, Object> selectType(String jsonData) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		cds=new DataSet(jsonData);
		try {
			String sql = "SELECT ServerTypeName,id FROM servicetype WHERE InitialId= '"+cds.getField("num", 0)+"'";
			List list = queryForList(o2o, sql, map);
			map.put("jsonData", list);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	//返回车主需求状态
	@RequestMapping("/update_CarServiceStatus.action")
	public Map<String, Object> update_CarServiceStatus() throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String sql = "UPDATE servicedemand SET DemandStatus = 2 WHERE INVALIDTIME < NOW()";
			Map	row = new HashMap();
			execSQL(o2o, sql, row);
			returnMap.put("STATE", "1");
		} catch (Exception e) {
			returnMap.put("STATE", "0");
			throw e;
		}
		return returnMap;
	}
	
	//接口   
	@RequestMapping("/selectCarService.action")
	public Map<String, Object> selectCarService() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {	
			String sql = "SELECT carowner.Gender,carowner.ReallyName,carowner.Phone,(select InitialName from servicetype where Id=servicedemand.ServiceTypeID)InitialName  " 
						 + " from carowner carowner "
						 + " RIGHT JOIN servicedemand servicedemand on carowner.Id = servicedemand.UserId "
						 + " where servicedemand.Id IN(SELECT Max(servicedemand.Id) "
						 + " from carowner carowner "
						 + " RIGHT JOIN servicedemand servicedemand on carowner.Id = servicedemand.UserId "
						 + " where servicedemand.DemandStatus = '0' and servicedemand.InvalidTime >=now() GROUP BY carowner.Id) "
						 + " order by servicedemand.PublishTime desc limit 4 ";
			List list = queryForList(o2o, sql, map);
			for (int i = 0; i < list.size(); i++) {
				Map Phone = (Map) list.get(i);
				String phoneStr = (String) Phone.get("Phone");
				String name = (String)Phone.get("ReallyName");
				   if(phoneStr != null && phoneStr != ""){
				      phoneStr = phoneStr.substring(0,3) + "****" + phoneStr.substring(7, phoneStr.length());
				      Phone.put("Phone", phoneStr);
				   }
				   if (name !=null && name != ""){
					  if (Phone.get("Gender") != null && Phone.get("Gender").equals(1)) {
						  name = name.substring(0,1)+"女士";
						  Phone.put("ReallyName", name);
					  	}else if (Phone.get("Gender") != null && Phone.get("Gender").equals(0)) {
						name = name.substring(0,1)+"先生";
						  Phone.put("ReallyName", name);
					}
					}  
			}
			map.put("carowner", list);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	
	//查询车主车牌号
	@RequestMapping("/selectCarInfo.action")
	public Map<String, Object> selectCarInfo(String jsonData) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		cds = new DataSet(jsonData);
		try {	
			String sql = "SELECT carnumber FROM carowner WHERE id = '"+cds.getField("id", 0)+"'";
			map = queryForMap(o2o, sql);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
}
