package com.jlsoft.c2b.service; 

import java.util.HashMap;
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

/**
 * 车主注册
 * @author 齐俊宇
 * @version 1.0
 * @date 2015年9月12日
 *
 */
@Controller
@RequestMapping("/CarOwnerRegist")
public class CarOwnerRegist extends JLBill{
	JLTools tool = new JLTools();
	
	/**
	 * 插入车主基本信息
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert_carownerinfo.action")
	public Map<String, Object> insert_carownerinfo(String jsonData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		cds = new DataSet(jsonData);
		try {
			String sql = "";
			sql = "INSERT INTO CAROWNER (ACCOUNTNAME, PASSWORD, "
				+ "REGISTERTIME, ACCOUNTSTATUS) "
				+ "VALUES('" + cds.getField("XTCZY01", 0) + "', '" + cds.getField("XTCZY02", 0) +"', "
				+ "NOW(), 1)";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	} 
	
	/**
	 * 完善车主信息
	 * @param jsonData
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update_carownerinfo.action")
	public Map<String, Object> update_carownerinfo(String jsonData, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		jsonData = JLTools.unescape(jsonData);
		cds = new DataSet(jsonData);
		try {
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			MultipartFile file = mrRequest.getFile("HEADIMAGEPATH");
			String fileName = "";
			// 如果没有上传头像信息则不进行插入图片
			if(file.getOriginalFilename() != "" && file.getOriginalFilename() != null){
				//获取图片名称和图片类型
				String name = tool.getTimestamp();
				fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
				// 上传图片到服务器
				Map mapImg = new HashMap();
				mapImg.put(
						"imgPath",
						JlAppResources.getProperty("path_fftp")
								+ "cztp");
				mapImg.put("imgName", fileName);
				Map<String, Object> fileUploadNew = JLTools.fileUploadNew(file, mapImg);
			}
			String sql = "";
			sql = "UPDATE CAROWNER "
				+ "SET REALLYNAME = REALLYNAME?, "
				+ "GENDER = GENDER?, "
				+ "BIRTHDAY = BIRTHDAY?, "
				+ "HEADIMAGEPATH = '" + fileName + "', "
				+ "PHONE = PHONE?, "
				+ "EMAIL = EMAIL?, "
				+ "ADDRESS = ADDRESS?, "
				+ "CARNUMBER = CARNUMBER?, "
				+ "CARBRAND = CARBRAND? "
				+ "WHERE ACCOUNTNAME = ACCOUNTNAME? "
				+ "AND PASSWORD = PASSWORD?";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}
	
	/**
	 * 查询车主登录名和密码
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select_carOwnerNameAndPwd.action")
	public Map<String, Object> select_carOwnerNameAndPwd(String jsonData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		cds = new DataSet(jsonData);
		try {
			String sql = "";
			sql = "SELECT ACCOUNTNAME, PASSWORD FROM CAROWNER "
				+ "WHERE ACCOUNTNAME = '" + cds.getField("ACCOUNTNAME", 0) + "'";
			Map m = queryForMap(o2o, sql);
			if(m != null){
				resultMap.putAll(m);
				resultMap.put("STATE", "1");
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}
	
	
	/**
	 * 车主信息注册
	 * @param jsonData
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert_carowner.action")
	public Map<String, Object> insert_carowner(String jsonData, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		jsonData = JLTools.unescape(jsonData);
		cds = new DataSet(jsonData);
		try {
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			MultipartFile file = mrRequest.getFile("HEADIMAGEPATH");
			String fileName = "";
			// 如果没有上传头像信息则不进行插入图片
			if(file.getOriginalFilename() != "" && file.getOriginalFilename() != null){
				//获取图片名称和图片类型
				String name = tool.getTimestamp();
				fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
				// 上传图片到服务器
				Map mapImg = new HashMap();
				mapImg.put(
						"imgPath",
						JlAppResources.getProperty("path_fftp")
								+ "cztp");
				mapImg.put("imgName", fileName);
				Map<String, Object> fileUploadNew = JLTools.fileUploadNew(file, mapImg);
			}
			String sql = "";
			sql = "INSERT INTO CAROWNER (ACCOUNTNAME, PASSWORD, "
				+ "REGISTERTIME, REALLYNAME, GENDER, BIRTHDAY, "
				+ "HEADIMAGEPATH, PHONE, EMAIL, ADDRESS, ACCOUNTSTATUS) "
				+ "VALUES(ACCOUNTNAME?, PASSWORD?, "
				+ "NOW(), REALLYNAME?, GENDER?, BIRTHDAY?, "
				+ "'" + fileName + "', PHONE?, EMAIL?, ADDRESS?, 1)";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}
	
	/**
	 * @todo 判断用户是否存在
	 * @param jsonData
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/checkCarOwnerExist.action")
	public Map<String, Object> select_OwnerExist(String jsonData) throws Exception{
		Map<String, Object> ResultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(jsonData);
			String sql = "SELECT COUNT(0) FROM CAROWNER WHERE 1=1 ";
			//判断用户名是否已存在
			String ACCOUNTNAME = cds.getField("ACCOUNTNAME", 0);
			if(ACCOUNTNAME != null && !ACCOUNTNAME.equals("")){
				sql = sql + " AND ACCOUNTNAME='" + ACCOUNTNAME + "'";
			}
			ResultMap.put("num", queryForInt(o2o,sql));
		}catch(Exception ex){
			throw ex;
		}
		return ResultMap;
	} 
	
	/**
	 * @todo 车主登录
	 * @param jsonData
	 * @return state:0 用户名密码不对 1成功 2失败
	 */
	@RequestMapping("/carOwerLogin")
	public Map<String, Object> carOwerLogin(String jsonData){
		Map<String, Object> ResultMap = new HashMap<String, Object>();
		try{
			cds = new DataSet(jsonData);
			String userName = cds.getField("userName", 0);
			String password = cds.getField("password", 0);
			String sql = "SELECT Id,AccountName XTCZY01,ReallyName ZCXX02,Address,Longitude,Latitude,Phone FROM CAROWNER " +
							   "WHERE (AccountName='"+userName+"' OR Phone='"+userName+"') AND Password='"+password+"'";
			Map carMap = queryForMap(o2o,sql);
			if(carMap == null){
				ResultMap.put("STATE", "0");
			}else{
				ResultMap.putAll(carMap);
				ResultMap.put("STATE", "1");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			ResultMap.put("STATE", "2");
		}
		return ResultMap;
	}
	
}
 