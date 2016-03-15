package com.jlsoft.framework.aop;

import com.jlsoft.framework.dataset.JSONDataSetWrapper;
import com.jlsoft.framework.dataset.XmlDataSetWrapper;
import com.jlsoft.utils.JLTools;

import java.io.PrintWriter;
import java.util.Map;
import org.apache.ibatis.session.ResultContext;

public class BillResultHandler extends JlResultHandler {
    public BillResultHandler(PrintWriter pw, String configKey, boolean isJson) {
        super(pw, configKey, isJson);
    }
    
    @SuppressWarnings("unchecked")
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
    	//map = (Map) context.getResultObject();
        if (map != null) {
        	map.put("JLID", jlid++);
            data.add(map);
        }
    }

    @Override
    public void Post() {
        String xmlorjson = null;
        if (isJson) {
            xmlorjson = new JSONDataSetWrapper(data).convert();
            pw.print(toJson("json", xmlorjson, false, false, fileName));
        } else {
            xmlorjson = new XmlDataSetWrapper(data, configKey).convert();
            pw.print(toJson("xml", new sun.misc.BASE64Encoder().encode(xmlorjson.getBytes()), false, false, fileName));
        }
        pw.close();
    }
}
