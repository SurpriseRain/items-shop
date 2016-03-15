package com.jlsoft.o2o.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/Oper_SPHJ")
public class Oper_SPHJ extends JLBill{
	@Autowired
	private PubService pubService;
	@Autowired
	private V9BasicData v9BasicData;
	
	public Oper_SPHJ() {
	}
	public Oper_SPHJ(JdbcTemplate o2o){
		this.o2o = o2o;
	}
	
	
	/*
	 * 查询货架的子节点有效数量,防止更新时将其节点设为末级
	 */
	@RequestMapping("/selectCountMJBJ.action")
	public Map selectCountMJBJ(String JsonData)throws Exception{
		Map map=new HashMap();
		cds=new DataSet(JsonData);
		try{
			String sql = "SELECT COUNT(1) FROM W_HJ WHERE HJBM LIKE '" + cds.getField("HJBM", 0) + "%'"
					+ " AND YXBJ = '1'";
			int count = queryForInt(o2o, sql);
			map.put("COUNT_MJBJ", count);
		} catch (Exception e) {
			throw e;
		}
		return map;
	}
	
	@RequestMapping("/selectTotalSPHJ.action")
    public Map selectTotalSPHJ(String XmlData) throws Exception {
		String sql="";
		if(null==XmlData||"".equals(XmlData)){
			sql ="SELECT TRIM(F.HJBM) FLCODE, TRIM(F.HJMC) FLNAME,case when SJBM is null or SJBM='' then 0 else  TRIM(SJBM) end  FATHERCODE,TRIM((select HJMC from W_HJ where  HJBM=f.SJBM)) as FATHERNAME,MJBJ isend FROM W_HJ F where YXBJ='1'  order by HJBM,HJJB ASC";	

		}
		else{
			cds=new DataSet(XmlData);
			sql ="SELECT TRIM(F.HJBM) FLCODE, TRIM(F.HJMC) FLNAME,case when SJBM is null or HJBM='' then 0 else  TRIM(HJBM) end  FATHERCODE,TRIM((select HJMC from W_HJ where  HJBM=f.SJBM)) as FATHERNAME,MJBJ isend FROM "
					+ "W_HJ F "
					+ "where  YXBJ='1' and F.HJMC like '%"+cds.getField("search_type_name", 0)+"%' "
							+ "order by HJBM,HJJB ASC ";	

		}
		List spfllist = queryForList(o2o,sql);
    	Map map = new HashMap();
    	map.put("spfllist", spfllist);
		return map;
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteSPHJ.action")
	public Map<String, Object> deleteSPHJ(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String sql = "UPDATE W_HJ SET YXBJ = 0 where HJBM LIKE '" + cds.getField("HJBM", 0) + "%' AND YXBJ = 1";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
			e.printStackTrace();
			throw new Exception("删除失败");
		}
		return map;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert_SPHJ.action")
	public Map<String, Object> insert_SPHJ(String JsonData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(JsonData);
 		try{
			
 			
			String queryMaxCode="select max(HJBM) maxid from W_HJ where SJBM ='"+cds.getField("SJBM", 0)+"'";
			
			String SJBM = cds.getField("SJBM", 0);
			if ("0".equals(cds.getField("SJBM", 0))){
				SJBM="";
			}
			
			List list = queryForList(o2o, queryMaxCode);
			Map maxMap = (Map) list.get(0);
			String maxid = (String) maxMap.get("maxid");
			
			String finalCode="";
			if(null == maxid){
				if(cds.getField("SJBM", 0).equals("0")){
					finalCode = SJBM.concat("01").substring(1);
				}else {
					finalCode = SJBM.concat("01");
				}
			}else{
				String frontString="";
				String backString="";
				int index=0;
				if (maxid.startsWith("0")) {
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
				}
				
				backString = maxid;
				finalCode=frontString.concat((String.valueOf(Integer.parseInt(backString)+1)));
			
			}
			int hjjb = 0;
			if(!"0".equals(cds.getField("SJBM", 0))){
				String select_HJJB = "select (HJJB+1) from W_HJ tt where HJBM='"+cds.getField("SJBM", 0)+"'";
				hjjb = queryForInt(o2o, select_HJJB);
			}else if(SJBM.equals("")){
				SJBM = "0";
			}
			if(finalCode.length() ==3){
				finalCode = finalCode.substring(1,finalCode.length());
			}
			
			String insertSql="insert into W_HJ(HJBM,SJBM,HJMC,MJBJ,HJJB) values('"+finalCode+"'," +
									  "'"+SJBM+"'," +
									  "'"+cds.getField("HJMC", 0)+"'," +
									  "'"+cds.getField("MJBJ", 0)+"'," +
									   hjjb + ")";

									  
			
			Map	row = getRow(insertSql, null, 0);
			execSQL(o2o, insertSql, row);
			
			String updateSql="update W_HJ set MJBJ=0 where HJBM='"+cds.getField("SJBM", 0)+"'";
			row = getRow(updateSql, null, 0);
			execSQL(o2o, updateSql, row);
			
			
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("新增失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/update_SPHJ.action")
	public Map<String, Object> update_SPFL(String JsonData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(JsonData);
		try{
			String sql="UPDATE W_HJ SET HJMC = HJMC?,MJBJ = MJBJ? where HJBM=HJBM?";
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert_sphj.action")
	public Map<String, Object> insert_sphj(HttpServletRequest request,
			HttpServletResponse response,String XmlDataa,String spjs,String spcs)throws Exception{
		Map map=new HashMap();
		String XmlData = JLTools.unescape(request.getParameter("XmlData"));//商品介绍
		cds=new DataSet(XmlData);
		
		try{
			String splist[]=cds.getField("sp_list", 0).split(",");
			String hjlist[]=cds.getField("hjlist", 0).split(",");
			String shck = cds.getField("shck", 0);
			String spcm="";
			for(int i=0;i<splist.length;i++)
			{
				if(i==0)
				{
					spcm=spcm+"'"+splist[i]+"'";
				}else
				{
					spcm=spcm+",'"+splist[i]+"'";
				}
			}
			String del="delete from w_sphj where spxx01 in ("+spcm+")";
			Map	rowDel = getRow(del, null, 0);
			execSQL(o2o, del, rowDel);
			for(int i=0;i<splist.length;i++)
			{
				String sql="INSERT INTO w_sphj(ck01,spxx01,barcode,hjbh) values('"+shck+"','"+splist[i]+"',(select barcode from w_goods where spxx01 = '"+splist[i]+"'),'"+hjlist[i]+"')";
				Map	row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
			}
			
			String update="update w_rkditem,(select spxx01,hjbh from w_sphj)t set w_rkditem.hjbh=t.hjbh where w_rkditem.SPXX01 = t.spxx01 and w_rkditem.SPXX01 in ("+spcm+")";
			Map	rowUpdate = getRow(update, null, 0);
			execSQL(o2o, update, rowUpdate);
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("更新失败");
		}
		return map;
	}
}
