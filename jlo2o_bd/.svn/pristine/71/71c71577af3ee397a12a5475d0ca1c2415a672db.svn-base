package com.jlsoft.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoad {

	public static void downLoad(HttpServletRequest request,HttpServletResponse response,Map<String, Object> map) throws Exception{
		response.setContentType("text/html;charset=UTF-8");  
        request.setCharacterEncoding("UTF-8");  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        String ctxPath = request.getSession().getServletContext().getRealPath("/")+ map.get("uploadPath");  
        String downLoadPath = ctxPath + map.get("storeName");  
        long fileLength = new File(downLoadPath).length();  
  
        response.setContentType(map.get("contentType").toString());  
        response.setHeader("Content-disposition", "attachment; filename="+ new String(map.get("realName").toString().getBytes("utf-8"), "ISO8859-1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        OutputStream os= response.getOutputStream();
        bos = new BufferedOutputStream(os);  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();  
        bos.close();
        os.close();  
	    }
}
