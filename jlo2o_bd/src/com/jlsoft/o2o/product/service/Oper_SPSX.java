package com.jlsoft.o2o.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

/**
 * 
 * @breif 查询商品属性
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/Oper_SPSX")
public class Oper_SPSX extends JLBill{
	@RequestMapping("/selectSpsx.action")	
	public Map sxlist(){
		Map sxlist = new HashMap();
		try {
			List SPFL01list=select_spfl01();  //查询一级分类
			/***
			if(SPFL01list != null&&SPFL01list.size() > 0){				
				for (int i=0; i < SPFL01list.size(); i++) {
					Map spfl01map = (Map<String, String>) SPFL01list.get(i);	
					String spfl01code = (String) spfl01map.get("FLCODE");
					String spfl01name = (String) spfl01map.get("FLNAME");

					List SPSX02list = select_spsx02(spfl01code);	   //查询二级分类				
					spfl01map.put("SPSX02list", SPSX02list);
					
					if(SPSX02list !=null&&SPSX02list.size()>0){
						for (int j = 0; j < SPSX02list.size(); j++) {							
							Map spfl02map = (Map) SPSX02list.get(j);								
							spfl01code = spfl02map.get("SPFL01").toString();
							String sxbhcode = spfl02map.get("SXBH").toString();						

							List SPSX03list = select_spsx03(spfl01code,sxbhcode);	  //查询三级分类	
							spfl02map.put("SPSX03list", SPSX03list);
							/***
							if(PPFL03list !=null&&PPFL03list.size()>0){
								for (int  m= 0; m < PPFL03list.size(); m++) {							
									Map spfl03map = (Map) PPFL03list.get(m);	
									String ppfl03code = spfl03map.get("FLCODE").toString();
									String ppfl03name = spfl03map.get("FLNAME").toString();

									List<HashMap<String, String>> PPFL04list = dao.select_pjfl04(ppfl03code);	  //查询四级分类	
									spfl03map.put("SPFL04List", PPFL04list);
								}
							}***//***

						}	
					}
				}
			}****/
			List SPSX02list = select_spsx02();
			List SPSX03list = select_spsx03();
			SPFL01list.addAll(SPSX02list);
			SPFL01list.addAll(SPSX03list);
			sxlist.put("spsxlist", SPFL01list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sxlist;
	}
	
	public List select_spfl01(){
		String sql ="SELECT DISTINCT(S.SPFL) Ccode, '0' AS Fcode, F.SPFL02 Cname, '0' AS isend, '1' AS jb FROM W_SPFLSX S, SPFL F WHERE S.SPFL = F.SPFL01";	
		List list = queryForList(o2o,sql);
		return list;
	}
	
	public List select_spsx02() {
		String sql ="SELECT S.SPFL Fcode, S.SXBH Ccode, TRIM(S.SXNAME) Cname, '0' AS isend, '2' AS jb FROM W_SPFLSX S";// WHERE S.SPFL= '"+spfl01code+"'";
		List list = queryForList(o2o,sql);
		return list;
	}
	
	public List select_spsx03(){
		String sql ="SELECT I.SXBH Fcode,I.SXZDM Ccode,TRIM(I.SXZNAME) Cname, '1' AS isend, '3' AS jb "+  
					"FROM W_SPFLSXITEM I";// WHERE I.SPFL='"+spfl01code+"' AND I.SXBH='"+sxbhcode+"'";
		List list = queryForList(o2o,sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert_SPSX.action")
	public Map<String, Object>insert_SPSX(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String bj = cds.getField("jb", 0);
			String queryMaxCode ="";
			if(bj.equals("2")){
				queryMaxCode="select max(CAST(SXZDM AS SIGNED INTEGER)) maxid from w_spflsxitem  where sxbh ='"+cds.getField("SPF_SPFL01", 0)+"'";
			}else if(bj.equals("1")){
				queryMaxCode="select max(CAST(SXBH AS SIGNED INTEGER)) from w_spflsx";
			}
			/**
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
			}***/
			int i = queryForInt(o2o,queryMaxCode);
			String finalCode = "";
			if(i==0){
				finalCode = cds.getField("SPF_SPFL01", 0)+ "01";
			}else{
				finalCode = String.valueOf((i +1));
			}
			
			String insertSql = "";
			if(bj.equals("2")){
				insertSql="insert into w_spflsxitem values((select spfl from w_spflsx where sxbh='"+cds.getField("SPF_SPFL01", 0)+"'),'"+cds.getField("SPF_SPFL01", 0)+"','"+finalCode+"','"+cds.getField("SPFL02", 0)+"')";
			}else if(bj.equals("1")){
				//String tSql="select max(CAST(SXBH AS SIGNED INTEGER)) from w_spflsx";
				//int i = queryForInt(o2o,tSql)+1;
				insertSql="insert into w_spflsx values('"+cds.getField("SPF_SPFL01", 0)+"','"+finalCode+"','"+cds.getField("SPFL02", 0)+"',0)";
			}
			Map	row = getRow(insertSql, null, 0);
			execSQL(o2o, insertSql, row);
			
			/**
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
			****/
			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("新增失败");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteSPSX.action")
	public Map<String, Object>deleteSPSX(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String sql="DELETE From w_spflsxitem where sxbh='"+cds.getField("SPF_SPFL01", 0)+"' and sxzdm='"+cds.getField("SPFL01", 0)+"'";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);

			map.put("STATE", "1");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "0");
			throw new Exception("删除失败");
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/update_SPSX.action")
	public Map<String, Object>update_SPSX(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try{
			String bj = cds.getField("jb", 0);
			String sql="";
			if(bj.equals("2")){
				sql="UPDATE w_spflsx set sxname='"+cds.getField("SPFL02", 0)+"' where sxbh='"+cds.getField("SPFL01", 0)+"' and spfl='"+cds.getField("SPF_SPFL01", 0)+"'";
			}else if(bj.equals("3")){
				sql="UPDATE w_spflsxitem set sxzname='"+cds.getField("SPFL02", 0)+"' where spfl=(select spfl from w_spflsx where sxbh='"+cds.getField("SPF_SPFL01", 0)+"') and sxbh='"+cds.getField("SPF_SPFL01", 0)+"' and sxzdm='"+cds.getField("SPFL01", 0)+"'";
			}else{}
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
	
	/**
	 * 根据前台选择的分类加载
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@RequestMapping("/selectSxlist.action")	
	public Map selectSxlist(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql ="SELECT S.SPFL Fcode, S.SXBH Ccode, TRIM(S.SXNAME) Cname, '0' AS isend, '2' AS jb FROM W_SPFLSX S WHERE S.SPFL= '"+cds.getField("Fcode", 0)+"'";
			List SPSX01list = queryForList(o2o,sql);
			if(SPSX01list != null&&SPSX01list.size() > 0){				
				for (int i=0; i < SPSX01list.size(); i++) {
					Map spflsx01map = (Map<String, String>) SPSX01list.get(i);	
					String spfl01code = spflsx01map.get("Fcode").toString();
					String sxbhcode = spflsx01map.get("Ccode").toString();	
					String sql2 ="SELECT I.SXBH Fcode,I.SXZDM Ccode,TRIM(I.SXZNAME) Cname, '1' AS isend, '3' AS jb "+  
					"FROM W_SPFLSXITEM I WHERE I.SPFL='"+spfl01code+"' AND I.SXBH='"+sxbhcode+"'";
					List list2 = queryForList(o2o,sql2);
					spflsx01map.put("SPSX02list", list2);
				}
			}else{}
			map.put("selectSxlist", SPSX01list);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return map;
	}
	
	/**
	 * 查询单个商品属性
	 * @param XmlData
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	@RequestMapping("/selectSx.action")	
	public Map selectSx(String XmlData) throws DataAccessException, Exception{
		Map map=new HashMap();
		cds=new DataSet(XmlData);
		try {
			String sql ="select sxzdm from w_spsx where spxx01='"+cds.getField("SPXX01", 0)+"'";
			List SPSX01list = queryForList(o2o,sql);
			map.put("Sxlist", SPSX01list);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return map;
	}
}
