package com.jlsoft.o2o.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.pub.service.PubService;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/Oper_SPFL")
public class Oper_SPFL extends JLBill{
	@Autowired
	private PubService pubService;
	@Autowired
	private V9BasicData v9BasicData;
	
	public Oper_SPFL() {
	}
	public Oper_SPFL(JdbcTemplate o2o){
		this.o2o = o2o;
	}
	
	@RequestMapping("/selectTotalSPLX.action")
    public Map selectTotalSPLX(String XmlData) throws Exception {
		String sql="";
		if(null==XmlData||"".equals(XmlData)){
			sql ="SELECT TRIM(F.SPFL01) FLCODE, TRIM(F.SPFL02) FLNAME,case when SPF_SPFL01 is null or SPF_SPFL01='' then 0 else  TRIM(SPF_SPFL01) end  FATHERCODE,TRIM((select SPFL02 from SPFL where  SPFL01=f.SPF_SPFL01)) as FATHERNAME,spfl04 isend FROM SPFL  F where YXBJ='1'  order by 3";	

		}else{
			cds=new DataSet(XmlData);
			sql ="SELECT TRIM(F.SPFL01) FLCODE, TRIM(F.SPFL02) FLNAME,case when SPF_SPFL01 is null or SPF_SPFL01='' then 0 else  TRIM(SPF_SPFL01) end  FATHERCODE,TRIM((select SPFL02 from SPFL where  SPFL01=f.SPF_SPFL01)) as FATHERNAME,spfl04 isend FROM "
					+ "SPFL F "
					+ "where  YXBJ='1' and F.SPFL02 like '%"+cds.getField("search_type_name", 0)+"%' "
							+ "order by 3";	

		}
		List spfllist = queryForList(o2o,sql);
    	Map map = new HashMap();
    	map.put("spfllist", spfllist);
		return map;
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteSPFL.action")
	public Map<String, Object>deleteSPFL(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String sql="UPDATE SPFL SET YXBJ ='0',spfl04='0' where  SPFL01=SPFL01?";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			String updateParentString="update SPFL set spfl04='1' where "
					+ "SPFL01=SPF_SPFL01?"
					+" and exists("
							+"select 1 from  (select count(1) total from SPFL where SPF_SPFL01=SPF_SPFL01? and spfl04='1') a where a.total=0 "
					+")";
			row = getRow(updateParentString, null, 0);
			execSQL(o2o, updateParentString, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("删除失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert_SPFL.action")
	public Map<String, Object>insert_SPFL(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String queryMaxCode="select max(spfl01) maxid from SPFL  where SPF_SPFL01 ='"+cds.getField("SPF_SPFL01", 0)+"'";
			List list=queryForList(o2o, queryMaxCode);
			Map maxMap=(Map) list.get(0);
			String maxid=(String) maxMap.get("maxid");
			String finalCode="";
			if(null==maxid){
				finalCode=cds.getField("SPF_SPFL01", 0).concat("01");
			}else{
				String frontString="";
				String backString="";
				int index=0;
				for(int i=0;i<maxid.length();i++){
					String cString=String.valueOf(maxid.charAt(i));
					if(cString.equals("0")){
						frontString=frontString+cString;
						index=i;
					}else{
						break;
					}
				}
				backString=maxid.substring(index+1);
				finalCode=frontString.concat((String.valueOf(Integer.parseInt(backString)+1)));
			}
			
			String insertSql="insert into SPFL values('"+finalCode+"'," +
									  "'"+cds.getField("SPF_SPFL01", 0)+"'," +
									  "'"+cds.getField("SPFL02", 0)+"'," +
									  "(select (SPFL03+1) from SPFL tt where  SPFL01='"+cds.getField("SPF_SPFL01", 0)+"')," +
									  ""+"(select case when SPFL04='1' then '1' else (select case when count(1)>0 then '1' else '0' end from SPFL yy where SPF_SPFL01='"+cds.getField("SPF_SPFL01", 0)+"' and SPFL04='1') end from SPFL tt where   SPFL01='"+cds.getField("SPF_SPFL01", 0)+"')," +
									  "null,null,null,(select SPFL05 from SPFL tt where   SPFL01='"+cds.getField("SPF_SPFL01", 0)+"'),(select SPFL06 from SPFL tt where   SPFL01='"+cds.getField("SPF_SPFL01", 0)+"'),'1')";
			Map	row = getRow(insertSql, null, 0);
			execSQL(o2o, insertSql, row);
			
			String updateSql="update SPFL set SPFL04=0 where SPFL01='"+cds.getField("SPF_SPFL01", 0)+"'";
			row = getRow(updateSql, null, 0);
			execSQL(o2o, updateSql, row);
			
			//对接ERP系统
			Map erpMap = pubService.getECSURL("JL");
			if(erpMap.get("DJLX") != null){
				String flSQL = "SELECT SPFL01,ifnull(SPF_SPFL01,'') SPF_SPFL01,SPFL02,SPFL03 FLJB,SPFL04 MJBJ FROM SPFL WHERE SPFL01='"+finalCode+"'";
				Map flMap = queryForMap(o2o,flSQL);
				if(erpMap.get("DJLX").equals("V9")){
					v9BasicData.createSPFL(flMap,erpMap);
				}
			}
			
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("新增失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/update_SPFL.action")
	public Map<String, Object>update_SPFL(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String sql="UPDATE SPFL SET SPFL02 =SPFL02? where  SPFL01=SPFL01?";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("更新失败");
		}
		return map;
	}
}
