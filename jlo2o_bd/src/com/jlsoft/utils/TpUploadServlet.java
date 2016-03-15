package com.jlsoft.utils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;


@SuppressWarnings("serial")
public class TpUploadServlet extends HttpServlet {

	JLTools tool = new JLTools();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//D\:/jboss/server/default/deploy/jlo2o.war/b2b/tpgl/
		//获取路径生成文件
  	  	//String jboss_path1 = request.getSession().getServletContext().getRealPath("/");
  	  	//String jboss_path = jboss_path1.replace("\\.", "");//jboss磁盘路径
  	  	//System.out.println("jboss_path="+jboss_path);
		String path = request.getParameter("filename");
		String filetype = request.getParameter("filetype");
		String contextpath = "";
		String saveUrl = "";
		if("sptp".equals(filetype)){
			contextpath =JlAppResources.getProperty("path_sptp");
			saveUrl  = JlAppResources.getProperty("show_sptp") + "sptp/" + path;
		} else if("dptp".equals(filetype)){
			contextpath =JlAppResources.getProperty("path_dptp");
			saveUrl  = JlAppResources.getProperty("show_sptp") + "dptp/" + path;
		}
//		String contextpath =JlAppResources.getProperty("path_sptp");
//		String saveUrl  = JlAppResources.getProperty("show_sptp")+path;
		//文件保存目录路径  path = spdetail/800172/003213/images/
		String savePath = contextpath+path;
		System.out.println("savePath="+savePath);
		//savePath = D\:/jboss/server/default/deploy/jlo2o.war/b2b/tpgl/spdetail/800172/003213/images/
		//文件保存目录URL http\://127.0.0.1/b2b/tpgl/
		//saveUrl = http\://127.0.0.1/b2b/tpgl/spdetail/800172/003213/images/
//		//定义允许上传的文件扩展名
//		HashMap<String, String> extMap = new HashMap<String, String>();
//		
//		extMap.put("image", "gif,jpg,jpeg,png,bmp");
//		extMap.put("flash", "swf,flv");
//		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
//		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		
		//最大文件大小
		long maxSize = 20480000;

		response.setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(request)){
			System.out.println("请选择文件！");
			return;
		}
//		//检查目录
//		File uploadDir = new File(savePath);
//		if(!uploadDir.isDirectory()){
//			System.out.println("上传目录不存在。！");
//			return;
//		}
//		//检查目录写权限
//		if(!uploadDir.canWrite()){
//			System.out.println("上传目录没有写权限。！");
//			return;
//		}

//		String dirName = request.getParameter("dir");
//		if (dirName == null) {
//			dirName = "image";
//		}
//		if(!extMap.containsKey(dirName)){
//			System.out.println("目录名不正确。！");
//			return;
//		}
		//创建文件夹
//		savePath += dirName + "/";
//		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String ymd = sdf.format(new Date());
//		savePath += ymd + "/";
//		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = null;
		try {
			items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem)items.get(i);
				if(item.getName()==null){
					items.remove(i);
				}
			}  
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String name = tool.getTimestamp();
			String fileName = item.getName();
			fileName = name + "." + fileName.split("\\.")[1];
			String temp[] = fileName.replaceAll("\\\\","/").split("/");
			if (temp.length > 1) {   
				fileName = temp[temp.length - 1];   
			}
			if (!item.isFormField()) {
				//检查文件大小
				if(item.getSize() > maxSize){
					System.out.println("上传文件大小超过限制。");
					return;
				}
				//检查扩展名
//				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
//					System.out.println("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
//					return;
//				}
				try{
					File uploadedFile = new File(savePath, fileName);
					item.write(uploadedFile);
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("上传文件失败。");
					return;
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + fileName);
//				out.println(obj.toJSONString());
				response.setContentType("text/html");
		        response.setCharacterEncoding("UTF-8");
		        PrintWriter out = response.getWriter();
		        out.write(obj.toJSONString());
		        out.flush();
		        out.close();
			}
		}
	}

}
