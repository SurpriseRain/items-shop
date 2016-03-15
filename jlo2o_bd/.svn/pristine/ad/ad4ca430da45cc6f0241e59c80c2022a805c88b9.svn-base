package com.jlsoft.framework.aop;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.XmlDataSetWrapper;
import com.jlsoft.utils.JLTools;

@Component
@RequestMapping("/JLAuthorize")
public class JLAuthorize extends JLBill {

	public static void main(String[] args) throws IOException {
		//
	}

	public Map<String, Object> checkSession(HttpServletRequest request) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		String uri = request.getRequestURI();
		String root = request.getContextPath();
		uri = uri.replace(root, "");
		String PI_USERNAME = request.getParameter("PI_USERNAME");
		String SessionID = request.getParameter("SessionID");
		System.out.println("["+PI_USERNAME+"]"+SessionID);
		if ((uri.indexOf("Login/insertSession.do") > 0)||(uri.indexOf("getSpxxProperties.do") > 0)) {
			rsMap.put("Flag", "1");
		} else {
			Map<String, String> paramers = new HashMap<String, String>();
			String sql = "UPDATE JLSESSION set ZHRQ=SysDate "
				+ " WHERE SESSIONID='"+SessionID+"' AND CZYID='"+PI_USERNAME+"'";
				//+ " WHERE SESSIONID=:ID AND CZYID=:CZYID";			
			//paramers.put("ID", SessionID);
			//paramers.put("CZYID", PI_USERNAME);
			try {
				int i = execSQL(scm, sql, paramers);
				if (i > 0){
					rsMap.put("Flag", "1");
				}else{
					rsMap.put("Flag", "0");
				}
			} catch (Exception e) {
				rsMap.put("Flag", "0");
				e.printStackTrace();
			}
		}
		return rsMap;
	}
}
