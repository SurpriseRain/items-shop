package com.jlsoft.o2o.info.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.jlsoft.utils.ClientDataSet;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/JcszDt")
public class Jcsz extends JLBill {
	@SuppressWarnings("unchecked")
	@RequestMapping("/afreshSPFLPX.action")
	public Map<String, Object> afreshSPFLPX(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map map = new HashMap(3);
		Map row = null;
		try {
			// sqlMap.startTransaction();
			String[] spfls = cds.getField("spflpx", 0).split(",");
			// 将所有一级分类清“0”
			String sql = null;
			if(JlAppResources.getProperty("ROADMAP").equals("3")) {
				sql = "UPDATE SPFLZDY SET PXBJ=0 WHERE LENGTH(SPFL_CODE)=14 AND SPFL02 = 1";
			} else {
				sql = "UPDATE SPFL SET PXBJ=0 WHERE LENGTH(SPFL01)=2 AND SPFL03 = 1";

			}
			// String[] spfls = form.getSpflpx().split(",");
			row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			for (int i = 0; i < spfls.length; i++) {
				String spfl01 = spfls[i].trim();
				int pxbj = new Integer(i + 1);
				String sql2 = null;
				if(JlAppResources.getProperty("ROADMAP").equals("3")) {
					sql2 = "UPDATE SPFLZDY SET PXBJ='" + pxbj
					+ "' WHERE SPFL_CODE='" + spfl01 + "'";
				} else {
					sql2 = "UPDATE SPFL SET PXBJ='" + pxbj
					+ "' WHERE SPFL01='" + spfl01 + "'";
				}
				row = getRow(sql2, null, 0);
				execSQL(o2o, sql2, row);
			}
			map.put("STATE", "1");
		} catch (Exception ex) {
			map.put("STATE", "0");
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/selectPictureForMainPage.action")
	public Map<String, Object> selectPictureForMainPage(String XmlData)
			throws Exception {
		cds = new DataSet(XmlData);
		Map map = new HashMap();
		try {
			String sql = "SELECT B.FILE01 ID , B.FILE02 FILENAME, B.FILE03 FILEPATH, B.FILE04 FILETYPE,B.FILE05 URL FROM W_FILE B WHERE B.FILE01='"
					+ cds.getField("ZCXX01", 0) + "' AND B.FILE04 LIKE '9%'";
			List tpList = queryForList(o2o, sql);
			map.put("pictureList", tpList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/displayPicture.action")
	public Map<String, Object> displayPicture(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		MultipartHttpServletRequest mrRequest = (MultipartHttpServletRequest) request;
		
		List<MultipartFile> listFile = mrRequest.getFiles("files");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map map = new HashMap();
		if (!upload.isMultipartContent(request)) {
			map.put("STATE", "0");
			return map;
		}
		cds = new DataSet(JLTools.unescape(XmlData));
		System.out.println("----->>"+JLTools.unescape(XmlData));
		for (int i = 0; i < listFile.size(); i++) {
			MultipartFile file = listFile.get(i);
			try {
				InputStream in = file.getInputStream();
				//String[] pathFull=request.getClass().getClassLoader().getResource("").getPath().split("/WEB-INF/");
				String path1 =JlAppResources.getProperty("path_banner");
				String oldFileName = file.getOriginalFilename();
				String fileSuffix = oldFileName.substring(oldFileName.lastIndexOf("."));// 截取文件后缀
				String newFileName = System.currentTimeMillis()+ (int) (Math.random() * 10) + fileSuffix;
				Map row = null;
				//String num=cds.getField("num", i);
				String tplx = cds.getField("tplx", i);
				String link = cds.getField("link", i).replace("?", "%3F").replace("&", "%26");
				System.out.println("link="+link);
				String fileid = cds.getField("gsid", i);
				String path=path1+fileid;
				if (!"".equals(oldFileName) && oldFileName.length() > 0) {
					String filePath = path+ newFileName;
					String sqlString = "SELECT COUNT(0) FROM W_FILE A WHERE A.FILE04="
							+ tplx + " AND A.FILE01='" + fileid + "'";
					int isexit = queryForInt(o2o, sqlString);
					if (isexit > 0) {
						String sql2 = "DELETE FROM W_FILE WHERE FILE04 = '"
								+ tplx + "'";
						row = getRow(sql2, null, 0);
						execSQL(o2o, sql2, row);
					}
					String sql3 = "insert into W_FILE(FILE01,FILE02,FILE03,FILE04,FILE05) values('"						    
							+ fileid
							+ "','"
							+ newFileName
							+ "','"
							+ filePath
							+ "','" + tplx + "','" + link + "')";
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
				} else if (("".equals(oldFileName) || oldFileName.length() == 0)
						&& link.length() > 0 && !"".equals(link)) {
					String sql4 = "UPDATE W_FILE A SET A.FILE05='" + link
							+ "' WHERE  A.FILE04='" + tplx + "' AND A.FILE01='"
							+ fileid + "'";
					row = getRow(sql4, null, 0);
					execSQL(o2o, sql4, row);
					map.put("STATE", "1");
				}
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
	
	/**
	 * @param dqxx01 地区编码 （名巢为门店名称）
	 * @param bmbm 部门编码
	 * @param ckbm 仓库编码
	 * @return STATE
	 */
	@RequestMapping("/insertSetRelation.action")
	public Map<String, Object> insertSetRelation(String XmlData){
		Map<String,Object> hm= new HashMap<String, Object>();
		try {
			cds= new DataSet(XmlData);
			String sql = "INSERT INTO W_DQCK (DQXX01, BM01, CK01) VALUES ("
					+ cds.getField("dqxx01", 0) + ", "
					+ cds.getField("bmbm", 0) + ", " + cds.getField("ckbm", 0)
					+ ")";
			Map row= getRow(sql, null, 0);
			int i=execSQL(o2o, sql, row);
			if(i>0){
				hm.put("STATE", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("STATE", false);
		}
		return hm;
	} 
	
	/**
	 * 后台登陆界面查询 供应商编码
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectZCXX.action")
	public Map selectZCXX(String XmlData) throws Exception {
		System.out.println("XmlData=="+XmlData);
		cds = new DataSet(XmlData);
		String querySQL=
			"SELECT A.ZCXX01 ZCXX01, A.SPXX01 SPXX01 FROM W_SPGL A WHERE A.SPXX01 = "+
			cds.getField("SPXX01",0)+	
			"  LIMIT 1 ";
		Map map = queryForMap(o2o, querySQL);
		return map;
	}
	//轮播图管理
	@SuppressWarnings("unchecked")
	@RequestMapping("/downloadAttach.action")
	public Map downloadAttach(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Map map = new HashMap();
		//cds = new DataSet(XmlData);
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("filePath");
		System.out.println(fileName+"--"+path);
		Map<String, Object> map=new HashMap<String, Object>();
		if (fileName != null && !"".equals(fileName)) {
			try {
				String disposition = "attachment;filename="
						+ URLEncoder.encode(fileName, "UTF-8");
				response.setContentType("application/x-msdownload;charset=UTF-8");
				response.setHeader("Content-disposition", disposition);
				fis = new FileInputStream(path);
				FileOutputStream out=new FileOutputStream(path);
				byte[] buffer = new byte[2048];
				while (fis.read(buffer) != -1) {
					out.write(buffer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
					}
				}
			}
		}
		map.put("STATUS", 1);
		return map;
	
	}
	
}


