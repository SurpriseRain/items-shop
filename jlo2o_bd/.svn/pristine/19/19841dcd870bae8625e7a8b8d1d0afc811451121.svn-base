package com.jlsoft.scm.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;


@Controller
@RequestMapping("/Purchase")
public class OperSCM extends JLBill {

	@RequestMapping("/insert.do")
	public Map<String, String> insert(String XmlData,String zcxx01,String zcxx02,String lx,String gssx)
			throws DataAccessException, Exception {
		Map<String, String> ResultMap = new HashMap<String, String>();
		cds = new DataSet(XmlData);
		System.out.println("XmlData:"+XmlData);
		if (lx.equals("WLDW")) {
			String sqlscm = "INSERT INTO WLDW(WLDW01,WLDW02,WLDW16,WLDW20,WLDW21)  VALUES ('"+zcxx01+"','"+(zcxx01+zcxx02)+"',1,"+gssx+",0)";
			Map rowscm = getRow(sqlscm, null, 0);
			execSQL(scm, sqlscm, rowscm);
		} else if (lx.equals("GSXX")) {
			String sql = "INSERT INTO GSXX(GSXX01,GSXX02,GSXX03,GSXX09,GSXX10,GSXX11,GSXX13,GSXX14,CWJK,GS_GSXX01) " +
					" VALUES('"+zcxx01+"' ,'"+(zcxx01+zcxx02)+"' ,0,0,0,0,0,0,0,substr('"+zcxx01+"',0,2))";
			Map row = getRow(sql, null, 0);
			execSQL(scm, sql, row);
		}
		return ResultMap;
	}
}
