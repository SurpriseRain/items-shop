package com.jlsoft.framework.aop;

import java.io.PrintWriter;
import java.util.*;
import org.apache.ibatis.session.ResultHandler;


public abstract class JlResultHandler implements ResultHandler {

    public String configKey = null;
    public PrintWriter pw = null;
    public boolean isJson = false;
    protected List data = new ArrayList();
    protected String fileName = "_"+UUID.randomUUID().toString(); 
    protected int jlid = 1;
    
    public JlResultHandler(PrintWriter pw, String configKey, boolean isJson) {
        this.pw = pw;
        this.configKey = configKey;
        this.isJson = isJson;
    }
    
    protected String toJson(String dataType, String data, boolean bZip, boolean bPage, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"dataType\":\"").
            append(dataType);
        if (isJson) {
        	  sb.append("\",\"data\":").
			          append(data).
			          append(",\"zip\":\"");
        } else {
            sb.append("\",\"data\":\"").
                append(data).
                append("\",\"zip\":\"");
        }
        sb.append(String.valueOf(bZip)).
		        append("\",\"page\":\"").
		        append(String.valueOf(bPage)).
		        append("\",\"fileName\":\"").
		        append(fileName).append("\"}");
        return sb.toString();
    }

    public abstract void Post();
}
