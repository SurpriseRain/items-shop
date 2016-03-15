package com.jlsoft.o2o.order.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 
 * @breif 订单评价
 *
 */
@Controller
@RequestMapping("/Oper_LBT")
public class Oper_LBT extends JLBill{
	/*
	 * 新增轮播图
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertLBT.action")
	public Map insertW_SPPJ	(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		MultipartHttpServletRequest mrRequest = (MultipartHttpServletRequest) request;
		Iterator iterator = mrRequest.getFileNames();
		Map map = new HashMap();

		cds = new DataSet(JLTools.unescape(XmlData));
		System.out.println("----->>"+JLTools.unescape(XmlData));
		while(iterator.hasNext()){
			String fileType = (String)iterator.next();
			MultipartFile file = mrRequest.getFile(fileType);
			try {
				InputStream in = file.getInputStream();
				//String[] pathFull=request.getClass().getClassLoader().getResource("").getPath().split("/WEB-INF/");
				String path1 =JlAppResources.getProperty("path_banner");
				String oldFileName = file.getOriginalFilename();
				String fileSuffix = oldFileName.substring(oldFileName.lastIndexOf("."));// 截取文件后缀
				String newFileName = System.currentTimeMillis()+ (int) (Math.random() * 10) + fileSuffix;
				Map row = null;
				//String num=cds.getField("num", i);
				//String tplx = cds.getField("tplx", i);
				String link = cds.getField("file05", 0).replace("?", "%3F").replace("&", "%26");
				System.out.println("link="+link);
				String fileid = cds.getField("file01", 0);
				String path=path1+fileid;
				String filePath = path+ newFileName;
				String queryOrder = "select max(file06)+1 from w_file";
				int file06=queryForInt(o2o, queryOrder);
				int id=PubFun.updateWBHZT(o2o,"W_FILE",1);
				String sql3 = "insert into W_FILE(FILE01,FILE02,FILE03,FILE04,FILE05,FILE06,id) values('"						    
							+ fileid
							+ "','"
							+ newFileName
							+ "','"
							+ filePath
							+ "','" + "" + "','" + link + "','"+file06+"','"+id+"')";
					row = getRow(sql3, null, 0);
					execSQL(o2o, sql3, row);
					File filePath2 = new File(path);
					if (!filePath2.exists()) {
						filePath2.mkdirs();
						System.out.println("创建目录为：" + filePath2);
					}
					String fileString = filePath2 + "/" + newFileName;
					FileOutputStream out = new FileOutputStream(fileString);
					System.out.println(fileString);
					byte buffer[] = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					map.put("STATE", "1");
			} catch (IOException e) {
				e.printStackTrace();
				map.put("STATE", "0");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
		}
		return map;
	}
	/*
	 * 新增轮播图
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateLBT.action")
	public Map updateLBT	(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		MultipartHttpServletRequest mrRequest = (MultipartHttpServletRequest) request;
		cds = new DataSet(JLTools.unescape(XmlData));
		Iterator iterator = mrRequest.getFileNames();
		Map map = new HashMap();
		String id=cds.getField("id", 0);
		String link = cds.getField("file05", 0).replace("?", "%3F").replace("&", "%26");
		Map row = null;
		cds = new DataSet(JLTools.unescape(XmlData));
		System.out.println("----->>"+JLTools.unescape(XmlData));
			while(iterator.hasNext()){
				String fileType = (String)iterator.next();
				MultipartFile file = mrRequest.getFile(fileType);
				try {
					InputStream in = file.getInputStream();
					//String[] pathFull=request.getClass().getClassLoader().getResource("").getPath().split("/WEB-INF/");
					String path1 =JlAppResources.getProperty("path_banner");
					String oldFileName = file.getOriginalFilename();
					String fileSuffix = oldFileName.substring(oldFileName.lastIndexOf("."));// 截取文件后缀
					String newFileName = System.currentTimeMillis()+ (int) (Math.random() * 10) + fileSuffix;
					
					//String num=cds.getField("num", i);
					//String tplx = cds.getField("tplx", i);
					
					System.out.println("link="+link);
					String fileid = cds.getField("file01", 0);
					String path=path1+fileid;
					String filePath = path+ newFileName;
					String queryOrder = "select max(file06)+1 from w_file";
					int file06=queryForInt(o2o, queryOrder);
					String queryPath="select file03 from w_file where id = '"+id+"'";
					Map mappath=queryForMap(o2o, queryPath);
					String delPath =(String) mappath.get("file03");
					File delfile = new File(delPath);
					delfile.delete();
					String sql3 = "update w_file set file02='"+newFileName+"',file03='"+filePath+"',file05='"+link+"' where id='"+id+"'";
					row = getRow(sql3, null, 0);
					execSQL(o2o, sql3, row);
					File filePath2 = new File(path);
					if (!filePath2.exists()) {
							filePath2.mkdirs();
							System.out.println("创建目录为：" + filePath2);
						}
						String fileString = filePath2 + "/" + newFileName;
						FileOutputStream out = new FileOutputStream(fileString);
						System.out.println(fileString);
						byte buffer[] = new byte[1024];
						int len = 0;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						in.close();
						out.close();
						
				} catch (IOException e) {
					e.printStackTrace();
					map.put("STATE", "0");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("STATE", "0");
				}
		}
			String sql3 = "update w_file set file05='"+link+"' where id='"+id+"'";
			row = getRow(sql3, null, 0);
			execSQL(o2o, sql3, row);
			map.put("STATE", "1");
		return map;
	}
		/*
		 * 删除录播图
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/deleteLBT.action")
		public Map deleteLBT(String XmlData){
			Map map =new HashMap();
			try {
					cds=new DataSet(XmlData);
					String sql="DELETE FROM W_FILE WHERE id='"+cds.getField("id", 0)+"'";
					Map row=getRow(sql, null, 0);
					int j=execSQL(o2o, sql, row);
					if (j == 1) {
						map.put("STATE", "1");
					} else {
						map.put("STATE", "0");
					}
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
			}
			return map;
		}
		/*
		 * 更新录播图序号
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/updateLBTByOrder.action")
		public Map updateLBTByOrder(String XmlData){
			Map map =new HashMap();
			try {
					cds=new DataSet(XmlData);
					String ids []=cds.getField("tp_id", 0).split(",");
					for(int i=0;i<ids.length;i++)
					{
						int show_order=i+1;
						String sql="update w_file set file06='"+show_order+"' WHERE id='"+ids[i]+"'";
						Map row=getRow(sql, null, 0);
						int j=execSQL(o2o, sql, row);
					}
					map.put("STATE", "1");
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
			}
			return map;
		}
}
