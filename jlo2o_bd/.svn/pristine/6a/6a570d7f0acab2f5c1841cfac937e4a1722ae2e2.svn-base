package com.jlsoft.o2o.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
@RequestMapping("/audit")
public class InvoiceAudit extends JLBill {
	
	/**
	 * 查询所有发票订单列表
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select_fpxx.action")
	public Map select_fp(String XmlData) throws Exception {
		cds=new DataSet(XmlData);
		String fpbh = cds.getField("fpbh", 0);
		Map resultMap = new HashMap();
		try {
			String sql = "SELECT " +  
					"DATE_FORMAT(ApplyTime,'%Y-%m-%d %k:%i:%s') applytime, " +
					"HBID," +
					"InvoiceType fplx," +
					"(select zcxx02 from w_zcgs where zcxx01=ZTID) sName, " +
					"(select zcxx02 from w_zcgs where zcxx01=HBID) bName, " +
					"(select count(1) from orderinvoiceitem where OrderInvoiceId='"+ fpbh +"') ddsl, " +
					"(CASE InvoiceType " +
			       "  WHEN 1 THEN " +
			       "   '普通发票' " +
			       "  WHEN 2 THEN " +
			        "  '增值税发票' " +
			       "END) fptype, " +
					"(CASE InvoiceState " +
			        " WHEN 1 THEN " +
			        "  '未审核' " +
			        " WHEN 2 THEN " +
			        "  '已审核' " +
			        " WHEN 3 THEN " +
			        "  '已驳回' " +
			       "END) fpstate,InvoiceState invoicestate,InvoiceTitle fptt,InvoiceMoney m,MailAddress yjdz,ContactPerson lxr"
			       + ",ContactPhone lxdh,AuditTime audittime  " +
			       "FROM OrderInvoice where OrderInvoiceId='"+fpbh+"'";
			resultMap = queryForMap(o2o, sql,resultMap);
			//是增值税发票
			if(resultMap != null && "2".equals(String.valueOf(resultMap.get("fplx")))){
				String hbid = String.valueOf(resultMap.get("HBID"));
				sql = "SELECT ZCXX01,DPTP01 TPLX,DPTP02 TPMC FROM W_DPTP WHERE ZCXX01='"
						+ hbid + "'";
				List list = queryForList(o2o, sql, resultMap);
				resultMap.put("tpList", list);
				
				Map invoiceMap = select_InvoiceForUserInfo(hbid);
				resultMap.put("zcxxInfo", invoiceMap);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
    }
	
	public Map select_InvoiceForUserInfo(String ZCXX01) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "";
			sql = "SELECT "
				+ "A.ZCXX02 ZCXX02, "
				+ "A.ZCXX28 ZCXX28, "
				+ "A.ZCXX71 ZCXX71, "
				+ "A.ZCXX72 ZCXX72, "
				+ "C.GSYHK02 GSYHK02, "
				+ "C.GSYHK03 GSYHK03 "
				+ "FROM "
				+ "W_ZCXX A "
				+ "LEFT JOIN W_GSYHK C "
				+ "ON A.ZCXX01 = C.ZCXX01 "
				+ "AND C.GSYHK11 = 1 "
				+ "WHERE "
				+ "A.ZCXX01 = '" + ZCXX01 + "' ";
			resultMap = queryForMap(o2o, sql);
			
		} catch (Exception e) {
			throw e;
		}
		
		return resultMap;
	}
	
	/**
	 * 审核发票订单信息
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update_fpsh.action")
	public Map update_fpsh(String XmlData) throws Exception {
		cds=new DataSet(XmlData);
		String fpbh = cds.getField("fpbh", 0);
		
		Map map = new HashMap();
		String updateFpshSql = "update OrderInvoice set AuditTime=now() , InvoiceState=2 where InvoiceState=1 and OrderInvoiceId='" + fpbh + "'";
		String updateXsddSql = "update w_xsdd set InvoiceState=2 "
				+ "where InvoiceState=1 "
				+ "and XSDD01 in(SELECT XSDD01 FROM OrderInvoiceItem WHERE OrderInvoiceId='" + fpbh + "')";;
		
		try {
			Map row = getRow(updateFpshSql, null, 0);
			execSQL(o2o, updateFpshSql, row);
			
			Map row2 = getRow(updateXsddSql, null, 0);
			execSQL(o2o, updateXsddSql, row2);
			
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
    }
	
	/**
	 * 驳回发票信息
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update_fpbh.action")
	public Map update_fpbh(String XmlData) throws Exception {
		cds=new DataSet(XmlData);
		String fpbh = cds.getField("fpbh", 0);
		
		Map map = new HashMap();
		String updateFpshSql = "update OrderInvoice set AuditTime=now() , InvoiceState=3 where InvoiceState=1 and OrderInvoiceId='" + fpbh + "'";
		String updateXsddSql = "update w_xsdd set InvoiceState=3 "
				+ "where InvoiceState=1 "
				+ "and XSDD01 in(SELECT XSDD01 FROM OrderInvoiceItem WHERE OrderInvoiceId='" + fpbh + "')";;
		
		try {
			Map row = getRow(updateFpshSql, null, 0);
			execSQL(o2o, updateFpshSql, row);
			
			Map row2 = getRow(updateXsddSql, null, 0);
			execSQL(o2o, updateXsddSql, row2);
			
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
		}
		return map;
    }

}
