package com.jlsoft.o2o.info.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

/**
 * 
 * @author 齐俊宇
 * 
 * 20150821
 */
@Controller
@RequestMapping("/JcszDt")
public class Cxtp extends JLBill {
	JLTools tool = new JLTools();
	
	@RequestMapping("/addPicture.action")
	public Map<String, Object> addCXTP(String XmlData,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			Iterator iterator = mrRequest.getFileNames();
			String numStr = cds.getField("maxsxh", 0);
			
			int num = 0;
			if(!"".equals(numStr)){
				num = Integer.parseInt(numStr);
			} 
			while(iterator.hasNext()){
				String fileType = (String)iterator.next();
				MultipartFile file = mrRequest.getFile(fileType);
				// 前端获取文件名的后缀字符数字
				String strNo = fileType.substring(5);
				// 类型转换
				int no = Integer.parseInt(strNo);
				// 获取的数字与查询顺序号最大值进行比较
				if(no > num){
					no = ++num;	
					saveCXTP(file, no, XmlData);
				} else {
					// 这行插入操作
					saveCXTP(file, no, XmlData);
				}
			}
			resultMap.put("STATE", "1");
		} catch (Exception ex) {
			resultMap.put("STATE", "0");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 保存图片
	 * @param file
	 * @param json
	 * @throws Exception 
	 */
	public void saveCXTP(MultipartFile file,int num,String XmlData) throws Exception{
		try{
			cds = new DataSet(XmlData);
			//获取图片名称和图片类型
			String fileName = "";
			String name = tool.getTimestamp();
			fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
			// 上传图片到服务器
			Map mapImg = new HashMap();
			mapImg.put(
					"imgPath",
					JlAppResources.getProperty("path_cxtp")
							+ cds.getField("mmid", 0));
			mapImg.put("imgName", fileName);
			JLTools.fileUploadNew(file, mapImg);
			//写入W_CXTP
			Map cxtpMap = new HashMap();
			cxtpMap.put("MMID", cds.getField("mmid", 0));
			cxtpMap.put("CXTP01", num);
			cxtpMap.put("CXTP02", fileName);
			insertW_CXTP(cxtpMap);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入车型图片
	 * @param hm
	 * @throws Exception
	 */
	public void insertW_CXTP(Map<String, Object> hm) throws Exception {
		try {
			String MMID = hm.get("MMID").toString();
			String CXTP01 = hm.get("CXTP01").toString();
			String CXTP02 = hm.get("CXTP02").toString();
			// 删除原有图片
			String sql = "DELETE FROM W_CXTP WHERE MOBILE_MODLE_ID='" + MMID
					+ "' AND CXTP01=" + CXTP01 + "";
			execSQL(o2o, sql, hm);
			// 插入新图片
			sql = "INSERT INTO W_CXTP(MOBILE_MODLE_ID,CXTP01,CXTP02) "
					+ "VALUES('" + MMID + "'," + CXTP01 + ",'" + CXTP02
					+ "')";
			execSQL(o2o, sql, hm);
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 详情显示图片
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showPicture.action")
	public Map<String, Object> showPicture(String jsonData) throws Exception {
		String sql = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			cds = new DataSet(jsonData);
			String mmid = cds.getField("MMID", 0);

			sql = "SELECT MOBILE_MODLE_ID AS MMID, CXTP01 AS CXSXH, CXTP02 AS CXTP, MAX(CXTP01) AS MAXSXH "
				+ "FROM W_CXTP C "
				+ "WHERE "
				+ "C.MOBILE_MODLE_ID = '" + mmid + "' "
				+ "GROUP BY MMID, CXSXH, CXTP";
			List list = queryForList(o2o, sql, resultMap);
			resultMap.put("cxtpList", list);
		} catch (Exception ex) {
			throw ex;
		}
		return resultMap;

	}
	
	/**
	 * 详情车型图片
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showCXTP.action")
	public Map<String, Object> showCXTP(String jsonData) throws Exception {
		String sql = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 获取传入的Json对象字符串
			cds = new DataSet(jsonData);
			// 获取车型编码 MMID 代表 MOBILE_MODLE_ID
			String mmid = cds.getField("MMID", 0);
			
			sql = "SELECT MOBILE_MODLE_ID, CXTP01 AS CXSXH, CXTP02 AS CXTP "
				+ "FROM W_CXTP C "
				+ "WHERE "
				+ "C.MOBILE_MODLE_ID = '" + mmid + "' ";
			List list = queryForList(o2o, sql, null);
			resultMap.put("cxtpList", list);
		} catch (Exception ex) {
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * 删除图片信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deletePicture.action")
	public Map<String, Object> deletePicture(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "";
		try {
			String JsonData = JLTools.unescape(request.getParameter("jsonData"));
			cds = new DataSet(JsonData);

			String mmid = cds.getField("MMID", 0);
			String cxsxhStr = cds.getField("CXSXH", 0);
			String cxtp = cds.getField("CXTP", 0);
			int cxsxh = Integer.parseInt(cxsxhStr);
			
			// 删除数据库图片信息
			sql = "DELETE FROM W_CXTP WHERE MOBILE_MODLE_ID='" + mmid
					+ "' AND CXTP01=" + cxsxh + "";
			Map row = getRow(sql, null, 0);
			int deleteRow = execSQL(o2o, sql, row);
			
			// 查看删除状态
			if(deleteRow == 1){
				// 删除服务器路径中存放的图片
				new File(JlAppResources.getProperty("path_cxtp") + mmid + "/" + cxtp).delete();
				map.put("STATE", 1);
			} else {
				map.put("STATE", 0);
			}
		} catch (Exception ex) {
			map.put("STATE", 0);
			throw ex;
		}
		return map;
	}
}
