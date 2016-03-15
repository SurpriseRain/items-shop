package com.jlsoft.framework.aop;

import com.jlsoft.framework.dataset.JSONDataSetWrapper;
import com.jlsoft.framework.dataset.XmlDataSetWrapper;
import java.io.PrintWriter;
import java.util.Map;

import com.jlsoft.utils.JLTools;

import com.jlsoft.utils.PropertiesReader;
import org.apache.ibatis.session.ResultContext;

public class ReportResultHandler extends JlResultHandler {

    private int pagesize = Integer.parseInt(PropertiesReader.getInstance().getProperty("PAGESIZE"));
    private int pagecount = 1;

    public ReportResultHandler(PrintWriter pw, String configKey, boolean isJson) {
        super(pw, configKey, isJson);
    }

    @Override
    public void handleResult(ResultContext context) {
    	Map map=null;
    	Object obj = context.getResultObject();
    	if(!obj.getClass().getName().equals("java.util.HashMap") && !obj.getClass().getName().equals("java.util.Map")){
    		try {
				map = (Map)JLTools.convertBean(obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
    	}else{
    		map = (Map) context.getResultObject();
    	}
        //Map map = (Map) context.getResultObject();
        if (map != null) {
            map.put("JLID", jlid++);
            data.add(map);
            try {
                xmlToFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void Post() {
        String xmlorjson = null;
        if (pagecount == 1) {
            // 向客户端抛出第一页数据
            if (isJson) {
                xmlorjson = new JSONDataSetWrapper(data).convert();
                pw.print(toJson("json", xmlorjson, false, false, fileName));
            } else {
                xmlorjson = new XmlDataSetWrapper(data, configKey, true).convert();
                pw.print(toJson("xml", new sun.misc.BASE64Encoder().encode(xmlorjson.getBytes()), false, false, fileName));
            }
            pw.close();
        } else if ((pagecount > 1)
                && ((data.size() > 0) && (data.size() < pagesize))) {
            if (isJson) {
                xmlorjson = new JSONDataSetWrapper(data).convert();
            } else {
                xmlorjson = new XmlDataSetWrapper(data, configKey, true).convert();
            }
            // 向文件服务器存储最后一页数据
            try {
                JLTools.sendToSync(xmlorjson,
                        PropertiesReader.getInstance().getProperty("REMOTE_ADD_PHP") + "?filename="
                        + fileName + "-" + pagecount + ".xml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            data.clear();
        }
    }

    private void xmlToFile() throws Exception {
        if (data.size() == pagesize) {
            String xmlorjson = null;
            if (isJson) {
                xmlorjson = new JSONDataSetWrapper(data).convert();
            } else {
                xmlorjson = new XmlDataSetWrapper(data, configKey, true).convert();
            }
            if (pagecount == 1) {
                // 向客户端抛出第一页数据
                if (isJson) {
                    pw.print(toJson("json", xmlorjson, false, true, fileName));
                } else {
                    pw.print(toJson("xml", new sun.misc.BASE64Encoder().encode(xmlorjson.getBytes()), false, true, fileName));
                }
                pw.close();
            }
            // 向文件服务器存储第一页数据
            JLTools.sendToSync(xmlorjson, PropertiesReader.getInstance().getProperty("REMOTE_ADD_PHP")
                    + "?filename=" + fileName + "-" + pagecount + ".xml");
            data.clear();
            pagecount++;
        }
    }
}
