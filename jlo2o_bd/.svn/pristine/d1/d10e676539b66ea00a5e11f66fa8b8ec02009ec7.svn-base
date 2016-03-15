package com.jlsoft.o2o.order.service; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/OrderInvoice")
public class OrderInvoice extends JLBill {
	
	/**
	 * 插入OrderInvoice表
	 * @param jsonData
	 * @return
	 */
	@RequestMapping("/insert_OrderInvoice.action")
	public Map insert_OrderInvoice(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(jsonData);
			long djfp = JLTools.getOrderId();
			String sql = "";
			sql = "INSERT INTO ORDERINVOICE ("
				+ "ORDERINVOICEID, "
				+ "ZTID, "
				+ "HBID, "
				+ "INVOICETYPE, "
				+ "INVOICETITLE, "
				+ "INVOICEMONEY, "
				+ "INVOICESTATE, "
				+ "MAILADDRESS, "
				+ "CONTACTPERSON, "
				+ "CONTACTPHONE, "
				+ "APPLYTIME) "
				+ "VALUES( "
				+ djfp + ", "
				+ "ZTID?, "
				+ "HBID?, "
				+ "FPLB?, "
				+ "FPTT?, "
				+ "FPJE?, "
				+ "1, "
				+ "YJDZ?, "
				+ "LXR?, "
				+ "LXDH?, "
				+ "NOW())";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			ObjectMapper mapper = new ObjectMapper();
			Map covMap = new HashMap();
			List list = mapper.readValue(jsonData, ArrayList.class);
			covMap = (Map) list.get(0);
			List xsddList = (List) covMap.get("xsddList");
			for (int i = 0; i < xsddList.size(); i++) {
				// 更新W_XSDD表
				sql = "UPDATE W_XSDD SET INVOICESTATE = 1 WHERE XSDD01 = '" + xsddList.get(i) + "'";
				execSQL(o2o, sql, row);
				// 插入ORDERINVOICEITEM表
				sql = "INSERT ORDERINVOICEITEM (ORDERINVOICEID, XSDD01) VALUES(" + djfp + ",'" + xsddList.get(i) + "')";
				execSQL(o2o, sql, row);
			}
			
			resultMap.put("STATE", "1");
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		
		return resultMap;
	}
	
	/**
	 * 查询发票申请信息
	 * @param jsonData
	 * @return
	 */
	@RequestMapping("/select_InvoiceFromZcxx.action")
	public Map select_InvoiceFromZcxx(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		
		try {
			cds = new DataSet(jsonData);
			String ZCXX01 = cds.getField("ZCXX01", 0);
			String sql = "";
			sql = "SELECT "
				+ "A.ZCXX03 LXR, "
				+ "A.ZCXX20 FPLB, "
				+ "A.ZCXX02 FPTT, "
				+ "A.ZCXX29 LXDH, "
				+ "A.ZCXX24 YJDZ "
				+ "FROM "
				+ "W_ZCXX A "
				+ "WHERE "
				+ "A.ZCXX01 = '" + ZCXX01 + "'";
			resultMap = queryForMap(o2o, sql);
			resultMap.put("STATE", "1");
			
		} catch (Exception e) {
			resultMap.put("STATE", "0");

			throw e;
		}
		return resultMap;
	}
	
	/**
	 * 查询卖家允许开票的发票金额
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select_invoiceMoney.action")
	public Map select_invoiceMoney(String ZCXX01) throws Exception {
		Map resultMap = new HashMap();		
		try {
			String sql = "";
			sql = "SELECT "
				+ "A.INVOICEMONEY INVOICEMONEY, "
				+ "A.INVOICETYPE INVOICETYPE "
				+ "FROM "
				+ "INVOICE A "
				+ "WHERE "
				+ "A.ZCXX01 = '" + ZCXX01 + "'";
			resultMap = queryForMap(o2o, sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
			} else {
				resultMap = new HashMap();
				resultMap.put("STATE", "0");
			}
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			throw e;
		}
		return resultMap;
	}
	
}
 