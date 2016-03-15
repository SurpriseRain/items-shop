package com.jlsoft.o2o.info.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/FWSM")
public class Invoice_FWSM extends JLBill {
	private static final String SPXX01 = null;
	JLTools tool = new JLTools();
	//服务查询
		@RequestMapping("/select_FWSM.action")
		public Map select_FWSM(String XmlData) throws Exception {
			cds=new DataSet(XmlData);
			String sql="SELECT * FROM invoice WHERE zcxx01='"+ cds.getField("ZCXX01", 0) + "'";
			List zcxx = queryForList(o2o,sql);
	    	Map map = new HashMap();
	    	map.put("zcxx", zcxx);
			return map;
	    }
	//服务说明-首次插入
	@RequestMapping("/insert_FWSM.action")
	public Map<String, Object> insert_FWSM(String XmlData)
			throws DataAccessException, Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		try {
			String sql = "INSERT INTO invoice (ZCXX01, InvoiceType,InvoicePeriod,InvoiceMoney,InvoiceDescribe) VALUES ('"+ cds.getField("ZCXX01", 0) + "','"+ cds.getField("InvoiceType", 0) + "','"+ cds.getField("InvoicePeriod", 0) + "','"+ cds.getField("InvoiceMoney", 0) + "','"+ cds.getField("InvoiceDescribe", 0) + "')";
			String sql1 = "UPDATE invoice SET ZCXX01='"+ cds.getField("ZCXX01", 0) + "' InvoiceType='"+ cds.getField("InvoiceType", 0) + "' InvoicePeriod='"+ cds.getField("InvoicePeriod", 0) + "'  InvoiceMoney='"+ cds.getField("InvoiceMoney", 0) + "' InvoiceDescribe='"+ cds.getField("InvoiceDescribe", 0) + "' ";
			System.out.println(sql);
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
	}
	//服务说明-修改更新
		@RequestMapping("/update_FWSM.action")
		public Map<String, Object> update_FWSM(String XmlData)
				throws DataAccessException, Exception {
			Map map = new HashMap();
			cds = new DataSet(XmlData);
			try {
				String sql = "UPDATE invoice SET ZCXX01='"+ cds.getField("ZCXX01", 0) + "', InvoiceType='"+ cds.getField("InvoiceType", 0) + "', InvoicePeriod='"+ cds.getField("InvoicePeriod", 0) + "',  InvoiceMoney='"+ cds.getField("InvoiceMoney", 0) + "', InvoiceDescribe='"+ cds.getField("InvoiceDescribe", 0) + "' WHERE ZCXX01 = '"+ cds.getField("ZCXX01", 0) + "' ";
				System.out.println(sql);
				Map row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				map.put("STATE", "1");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("STATE", "0");
			}
			return map;
		}
		
		/**
		 * 判断是否填写发票说明
		 * @param ZCXX01
		 * @return
		 */
		@RequestMapping("/getFPSMWS.action")
		public Map getFPSMWS(String ZCXX01){
			Map map = new HashMap();
			if(JLTools.getCurConf(6) == 1){
				String sql = "SELECT COUNT(1) FROM INVOICE WHERE ZCXX01 = '" + ZCXX01 + "'";
				int count = queryForInt(o2o, sql);
				if(count == 1){
					map.put("CurConf", "0");
				} else {
					map.put("CurConf", "1");
				}
			}else{
				map.put("CurConf", "0");
			}
			return map;
		}


}
