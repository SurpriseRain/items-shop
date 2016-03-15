package com.jlsoft.o2o.order.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JLQuery;
import com.jlsoft.framework.dataset.DataSet;
/**
 * 
 * @breif 订单评价
 *
 */
@Controller
@RequestMapping("/OrderPj")
public class OrderPj extends JLBill{
	
	//卖家进行回复时，查询数据对买家商品评价进行赋值
	@RequestMapping("/select_SPPJhf.action")
	public Map select_SPPJhf(String XmlData) throws Exception {
			cds=new DataSet(XmlData);
			String s= cds.getField("SPPJ01", 0);
			String sql="SELECT sppj07,sppj12 FROM w_sppj WHERE SPPJ01='"+s+"'";
			System.out.println(sql);
			List spfllist = queryForList(o2o,sql);
	    	Map map = new HashMap();
	    	map.put("spfllist", spfllist);
			return map;
	    }
	// 卖家回复买家的商品咨询
	@RequestMapping("/updateByxsdd01.action")
	public Map<String, Object> updateByxsdd01(String XmlData) throws Exception {
		cds = new DataSet(XmlData);
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "UPDATE w_sppj SET SPPJ12='"+cds.getField("hfnr", 0)+"' WHERE SPPJ01='"+cds.getField("SPPJ01", 0)+"'";
		System.out.println(sql);
		Map row = getRow(sql, null, 0);
		int i = execSQL(o2o, sql, row);
		map.put("STATE", i);
		return map;
	}
		/*
		 * 后台订单评价管理删除评价
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/deleteOrderPj.action")
		public Map deleteOrderPj(String XmlData){
			Map map =new HashMap();
			try {
				cds=new DataSet(XmlData);
				//JSONArray jsonList=JSONArray.fromObject(XmlData);
				//for (int i = 0; i <jsonList.size() ; i++) {
					String sql="DELETE FROM W_SPPJ WHERE SPPJ01=SPPJ01?";
					Map row=getRow(sql, null, 0);
					int j=execSQL(o2o, sql, row);
					if (j == 1) {
						map.put("STATE", "1");
					} else {
						map.put("STATE", "0");
					}
				//}
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
			}
			return map;
		} 
		/*
		 * 新增客户评价 
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/insertW_SPPJ.action")
		public Map insertW_SPPJ(String XmlData){
			Map map =new HashMap();
			try {
				cds=new DataSet(XmlData);
				String SPXX01=cds.getField("SPXX01", 0);
				String spxxSql="DELETE FROM  W_SPPJ  WHERE XSDD01=XSDD01? AND SPXX01="+SPXX01;
				Map row=getRow(spxxSql, null, 0);
				execSQL(o2o, spxxSql, row);
				//List spxxList=queryForList(o2o, spxxSql);
				//for(int i=0;i<spxxList.size();i++){
					//String SPXX01=((Map<String, Object>)(spxxList.get(i))).get("SPXX01").toString();
					LinkedList inParameter = new LinkedList();
					inParameter.add("W_SPPJ");
					inParameter.add(1);
					LinkedList outParameter = new LinkedList();
					outParameter.add(java.sql.Types.CHAR);
					String sqlq="{call Update_WBHZT(?,?,?)}";
					List SPPJ01 = callProc(o2o, sqlq, inParameter, outParameter);
					String sql="INSERT INTO W_SPPJ(SPPJ01,ZTID,SPXX01,XSDD01,SPPJ02,HBID,ZCXX02,SPPJ03,SPPJ04,SPPJ05,SPPJ06,SPPJ07,SPPJ08,SPPJ09,SPPJ10,SPPJ11,XTCZY01) VALUES" +
							"('"+SPPJ01.get(0)+"',ZTID?,'"+SPXX01+"',XSDD01?,NOW(),HBID?,ZCXX02?,SPPJ03?,SPPJ04?,SPPJ05?,SPPJ06?,SPPJ07?,DATE_FORMAT(SPPJ08?,'%Y-%m-%d'),SPPJ09?,SPPJ10?,NOW(),XTCZY01?)";
					row=getRow(sql, null, 0);
					execSQL(o2o, sql, row);
					//更新订单主线状态
					String sql2 ="update w_djzx set w_djzx02 ='10' where w_djzx01 ='"+cds.getField("XSDD01", 0)+"'";
					Map row2 = getRow(sql2, null, 0);
					execSQL(o2o, sql2, row2);		
				//}
				//updateShbj(XSDD01);
				map.put("STATE", "1");	
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
			}
			return map;
		}
		
		/*
		 * 新增客户评价//沈阳兴隆自主增加评价
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/insertW_SPPJ_xlpj.action")
		public Map insertW_SPPJ_xlpj(String XmlData){
			Map map =new HashMap();
			try {
				cds=new DataSet(XmlData);
				String SPXX01=cds.getField("SPXX01", 0);
				LinkedList inParameter = new LinkedList();
				inParameter.add("W_SPPJ");
				inParameter.add(1);
				LinkedList outParameter = new LinkedList();
				outParameter.add(java.sql.Types.CHAR);
				String sqlq="{call Update_WBHZT(?,?,?)}";
				List SPPJ01 = callProc(o2o, sqlq, inParameter, outParameter);
				String sql="INSERT INTO W_SPPJ(SPPJ01,ZTID,SPXX01,XSDD01,SPPJ02,HBID,ZCXX02,SPPJ03,SPPJ04,SPPJ05,SPPJ06,SPPJ07,SPPJ08,SPPJ09,SPPJ10,SPPJ11,XTCZY01) VALUES" +
							"('"+SPPJ01.get(0)+"',ZTID?,'"+SPXX01+"',XSDD01?,DATE_FORMAT(NOW(),'%Y-%m-%d'),HBID?,ZCXX02?,SPPJ03?,SPPJ04?,SPPJ05?,SPPJ06?,SPPJ07?,DATE_FORMAT(SPPJ08?,'%Y-%m-%d'),SPPJ09?,SPPJ10?,NOW(),XTCZY01?)";
				Map row=getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				map.put("STATE", "1");	
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
			}
			return map;
		}
				
		/*
		 * 更改单据主线标记*/
		public void updateShbj(String XSDD01) throws Exception{
			Map row;
			try {
				String sql="UPDATE W_DJZX A SET A.W_DJZX02=10  WHERE A.W_DJZX01 = '"+XSDD01+"'";
				row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				//更改W_DJZT 
				String CZNR="用户已评价";
				String sql2="update w_djzt set W_DJZT03='"+CZNR+"', W_DJZT02 =sysdate where W_DJZT01='"+cds.getField("xsdd01", 0)+"'";
				Map row2 = getRow(sql2, null, 0);
				execSQL(o2o, sql2, row2);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}				
		
		/**
		 * 查看单个订单用于收货
		 * @param SPXX01
		 * @param ZCXX01
		 * @param ZTID
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping("/selectOrderItem.action")
		public Map<String, Object> selectOrderItem(String XmlData) throws Exception{
			System.out.println("XmlData----"+XmlData);
			cds=new DataSet(XmlData);
			String sql="SELECT A.XSDD01 XSDD01, \n" +
				       "(SELECT D.ZCXX02 FROM W_ZCXX D WHERE D.ZCXX01 = C.ZTID) ZTMC,\n" +
				       "to_char(C.XSDD04,'yyyy.mm.dd') JYCJSJ,\n" +
				       "B.SPXX01,\n" +
				       "B.SPXX04 SPMC,\n" +
				       "(A.ITEMD05 + NVL(A.ITEMD06, 0)) SHSL,\n" +
				       "((A.ITEMD05 + NVL(A.ITEMD06, 0)) * (A.ITEMD02 + NVL(A.ITEMD04, 0))) SPJE,\n" +
				       "NVL(A.ITEMD14,0) SHZT \n" +
				  "FROM w_itemdetail A, w_spxx B, W_XSDD C \n" +
				 "WHERE A.SPXX01 = B.SPXX01 \n" +
				   "AND A.XSDD01 = C.XSDD01 \n" +
				   "AND A.XSDD01 = '"+cds.getField("XSDD01", 0)+"'";
		   List result = queryForList(o2o, sql);		
			Map map = new HashMap();
			map.put("map", result);
			return map;
		}
		
		
		
		/**
		 * 查看单个订单用于收货
		 * @param SPXX01
		 * @param ZCXX01
		 * @param ZTID
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping("/shouhuo.action")
		public void shouhuo(String XmlData) throws Exception{
			cds=new DataSet(XmlData);
			
			String sql="UPDATE W_ITEMDETAIL A  "+
			  " SET A.ITEMD10 = NVL(A.ITEMD10, 0) + "+cds.getField("shsl", 0)+",A.ITEMD14 = 1  "+
			  " WHERE XSDD01 ='"+cds.getField("xsdd01", 0)+"'"+
			  " and SPXX01 = "+cds.getField("spxx01", 0)+"";		
	          Map row = getRow(sql, null, 0);
	          int i=execSQL(o2o, sql, row);
		    
			String sql2="SELECT COUNT(0) FROM W_ITEMDETAIL A WHERE " +
			  		"A.XSDD01 = '"+cds.getField("xsdd01", 0)+"' AND NVL(A.ITEMD14, 0) = 0";		  
			  int count = queryForInt(o2o, sql2);		  
			  
				if(count == 0){
					String sql3="update w_djzx set w_djzx02 =6 where w_djzx01 ='"+cds.getField("xsdd01", 0)+"'";
					String sql4="update w_xsdd set xsdd33 =sysdate where xsdd01='"+cds.getField("xsdd01", 0)+"'";
					String aa="用户已确认收货";
					String sql5="update w_djzt set W_DJZT03='"+aa+"', W_DJZT02 =sysdate where W_DJZT01='"+cds.getField("xsdd01", 0)+"'";				 
					  Map row3 = getRow(sql3, null, 0);
			          int i3=execSQL(o2o, sql3, row3);
					  Map row4 = getRow(sql4, null, 0);
			          int i4=execSQL(o2o, sql4, row4);
					  Map row5 = getRow(sql5, null, 0);
			          int i5=execSQL(o2o, sql5, row5);
				}  	
		}
		
		
		
		
		
		/**
		 * (yhl)
		 * 供应商审核单子修改单价或数量   根据订单号   
		 * @param SPXX01
		 * @param ZCXX01
		 * @param ZTID
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping("/updateOrderItemById.action")
		public Map updateOrderItemById(String XmlData) throws Exception{
			cds=new DataSet(XmlData);			
			System.out.println("XmlData----"+XmlData);	
			Map map =new HashMap();
			if (cds.getField("reson", 0).equals("")|| cds.getField("reson", 0).equals(null)){				
				Map maplist=updateGHScheck(XmlData);				
				Object XSDD02=maplist.get("XSDD02");				
				updateshq(XmlData,XSDD02);						
				//更新状态信息
				Map ddztMap = new HashMap();
				ddztMap.put("XSDD01", cds.getField("XSDD01", 0));
				ddztMap.put("W_DJZX02", 3);
				ddztMap.put("W_DJZT03", "订单审核通过，待支付！");
				JSONArray ddztJson = JSONArray.fromObject(ddztMap);
				updateshenhe(ddztJson.toString());						
				//冻结库存
				djkc(XmlData);					
				map.put("STATE", "1");				
			}else{
				Map mapddzt = new HashMap();
				mapddzt.put("XSDD01", cds.getField("XSDD01", 0));
				mapddzt.put("W_DJZX02", 2);
				mapddzt.put("W_DJZT03", "供货商审核没通过");
				JSONArray Jsonddzt = JSONArray.fromObject(mapddzt);
				updateshenhe(Jsonddzt.toString());	
				insertReson(XmlData);				
				map.put("STATE", "0");
			}			
			return map;
		}
		
		@SuppressWarnings("unchecked")
		public Map updateGHScheck(String XmlData) throws Exception{
			cds=new DataSet(XmlData);			
			double totalMoney = 0.0;
			// 修改w_xsdditem中商品采购单价及金额、收货地址
			List<Map<String,Object>> spidList = selectXSDDForID(XmlData);						
			for(int j = 0; j < spidList.size() ; j++){          					
				double xgxsddi10=0.0;
				Double gxsddi05=0.0;
				Double xsddi05 =0.0;				
				String item=cds.getField("grids", j);			
				JSONArray itemList = JSONArray.fromObject(item);				
				for (int i = 0; i < itemList.size(); i++) {        //grids	
					Map itemMap = (Map) itemList.get(i);														
					if(itemMap.get("SPXX01").equals(""+spidList.get(j).get("SPXX01"))){						
	     				gxsddi05+=Double.valueOf(itemMap.get("SHSL").toString());
	     				xsddi05=Double.valueOf(itemMap.get("GMSL").toString());
	     				xgxsddi10=Double.valueOf(itemMap.get("SHJG").toString())-Double.valueOf(itemMap.get("GMJG").toString());										    				
					}				                  	
				}			
				int endsl=(int) (gxsddi05-xsddi05);				
				String sql2 ="UPDATE W_XSDDITEM A SET A.XSDDI04 = '"+xgxsddi10+"' ," +
				" A.XSDDI06 ='"+endsl+"' "+	              
				" WHERE A.XSDD01 = '"+cds.getField("XSDD01", 0)+"'"+
				" AND A.SPXX01 =  '"+spidList.get(j).get("SPXX01")+"'";
				Map row2 = getRow(sql2, null, 0);
				execSQL(o2o, sql2, row2);				
				System.out.println("-----------------------sql-2------------------------");
		    }
						
			String sql3 ="DELETE FROM W_ITEMDETAIL WHERE XSDD01 ='"+cds.getField("XSDD01", 0)+"'";
			Map row3 = getRow(sql3, null, 0);
			execSQL(o2o, sql3, row3);		
				System.out.println("-----------------------sql-3------------------------");	
			// 修改w_itemdetail中商品详情
			for(int j = 0; j < spidList.size() ; j++){          							
				String item=cds.getField("grids", j);			
				JSONArray itemList = JSONArray.fromObject(item);			
				for (int i = 0; i < itemList.size(); i++) {        
					Map itemMap = (Map) itemList.get(i);				
					String xsddi01=((String)cds.getField("XSDD01", 0))+ "00" + (i);				
					int gxsddi05=(int)(Double.valueOf(itemMap.get("SHSL").toString())-Double.valueOf(itemMap.get("GMSL").toString()));
					double xgxsddi10=Double.valueOf(itemMap.get("SHJG").toString())-Double.valueOf(itemMap.get("GMJG").toString());				
					//  null					
					String hdbh=null;
					int dtbj=0;
					int scjfnum=0;
					int xfjfnum=0;	
					String sql4 ="INSERT INTO W_ITEMDETAIL" +
							"(XSDD01,ITEMD01,SPXX01,ITEMD02,ITEMD03,ITEMD04,  " +
							"ITEMD05,ITEMD06,ITEMD09,SPGL02,ITEMD07,ITEMD08," +							
							"CK01,FLFS01,ITEMD15, BM01,RYXX01,RYXX02,CKSP12) VALUES" +														
							" ( '"+cds.getField("XSDD01", 0)+"','"+xsddi01+"','"+itemMap.get("SPXX01")+"'," +
							" '"+itemMap.get("GMJG")+"','"+0+"','"+xgxsddi10+"'," +							
							" '"+itemMap.get("GMSL")+"','"+gxsddi05+"','"+hdbh+"',  " +							
							" '"+dtbj+"','"+scjfnum+"','"+xfjfnum+"'," +									
							" '"+itemMap.get("CK01")+"','"+itemMap.get("XSLX")+"','"+itemMap.get("SHSL")+"'," +							
							" '"+itemMap.get("BM01")+"','"+itemMap.get("RYXX01")+"','"+itemMap.get("RYXX02")+"','"+itemMap.get("CKSP12")+"' )";
					Map row4 = getRow(sql4, null, 0);
					execSQL(o2o, sql4, row4);					
					System.out.println("-----------------------sql-4------------------------");					
					double gjg = Double.valueOf(itemMap.get("SHJG").toString());
					double zje = gjg * Double.valueOf(itemMap.get("SHSL").toString());
					totalMoney += zje;			
				}		
		    }
						
			//确认单据类型
			String sql5 ="update w_xsdd set xsdd35 ='"+cds.getField("djlx35", 0)+"' where xsdd01='"+cds.getField("XSDD01", 0)+"'";
			Map row5 = getRow(sql5, null, 0);
			execSQL(o2o, sql5, row5);	
			System.out.println("-----------------------sql-5------------------------");
			
			//供货商订单审批备注
			String sql6 ="update w_xsdd set xsdd38 = '"+cds.getField("ghsbz", 0)+"' where xsdd01='"+cds.getField("XSDD01", 0)+"'";
			Map row6 = getRow(sql6, null, 0);
			execSQL(o2o, sql6, row6);
			System.out.println("-----------------------sql-6------------------------");
			
			//重新插入销售订单分组表
			orderGrouping(XmlData);
			
			Map map =new HashMap();
			map.put("XSDD02",totalMoney );
			return map;
		}
		
		
		//重新插入销售订单分组表
		@SuppressWarnings("unchecked")
		public void orderGrouping(String XmlData) throws Exception{
			cds=new DataSet(XmlData);			
			//
			String sql7 ="DELETE FROM W_XSDDGROUP WHERE XSDD01 = '"+cds.getField("XSDD01", 0)+"'";
			Map row7 = getRow(sql7, null, 0);
			execSQL(o2o, sql7, row7);
			System.out.println("-----------------------sql-7------------------------");						
			//
			String sql8="INSERT INTO W_XSDDGROUP" +
					"(XSDDG01,XSDD01,XSDDG02,CK01,FLFS01,SPFL01,PPB01,SPXX08,SPGL02,XSDDG03,BM01,RYXX01,RYXX02)" +
					"(SELECT F.XSDD01 || 'G' || ROWNUM XSDDG01,F.* FROM " +
					"(SELECT I.XSDD01 XSDD01, '0' XSDDG02, I.CK01,I.FLFS01," +
					"P.SPFL01_CODE,P.PPB01,P.SPXX08,G.SPGL02," +
					"SUM((I.ITEMD02 + I.ITEMD04)*(I.ITEMD05 + I.ITEMD06))XSDDG03,I.BM01,I.RYXX01," +
					"I.RYXX02 FROM W_ITEMDETAIL I, W_SPXX P, W_SPGL G, W_XSDD X WHERE I.SPXX01 = P.SPXX01 " +
					"AND I.SPXX01 = G.SPXX01 AND X.ZTID = G.ZCXX01 AND X.XSDD01 = I.XSDD01 " +
					"AND I.XSDD01 = '"+cds.getField("XSDD01", 0)+"' AND I.ITEMD15 !=0 " +
					"GROUP BY I.XSDD01,I.CK01,I.FLFS01,P.SPFL01_CODE,P.PPB01,P.SPXX08,G.SPGL02,I.BM01,I.RYXX01,I.RYXX02) F)";			
			Map row8 = getRow(sql8, null, 0);
			execSQL(o2o, sql8, row8);
			System.out.println("-----------------------sql-8------------------------");			
			//
			String querySQL="SELECT G.XSDD01 XSDD01,"+
                "  G.XSDDG01 XSDDG01,"+
                "  G.XSDDG02 XSDDG02,"+
                "  G.CK01 CK01,"+
                "  G.SPFL01 SPFL01,"+
                "  G.FLFS01 FLFS01,"+
                "  G.PPB01 PPB01,"+
                "  G.SPXX08 SPXX08,"+
                "  G.SPGL02 SPGL02,"+
                "  G.JD01 JD01,"+
                "  G.FLD01 FLD01,"+
                "  G.XSDDG03 XSDDG03,"+
                "  G.XSDDG04 XSDDG04,"+
                "  G.PFD01 PFD01,"+
                "  G.PFSFK01 PFSFK01,"+
                "  G.BM01 BM01,"+
                "  G.RYXX01 RYXX01,"+
                "  G.RYXX02 RYXX02,"+
                " (SELECT ZCXX25 FROM W_ZCXX A WHERE A.ZCXX01 = D.ZTID) GXSS01"+
           " FROM W_XSDDGROUP G, W_XSDD D"+
           " WHERE G.XSDD01 = D.XSDD01"+
           " AND G.XSDD01 ='"+cds.getField("XSDD01", 0)+"'";
			
			List<Map<String,Object>> listaa = queryForList(o2o,querySQL);
			System.out.println("-----------------------sql-9------------------------");
			for(int i=0;i<listaa.size();i++){				
 			Map<String,Object> maps=listaa.get(i);			
 			String sql10="UPDATE W_ITEMDETAIL I SET I.XSDDG01 = '"+maps.get("XSDDG01").toString()+"' "+
 			"   WHERE I.XSDD01 = '"+maps.get("XSDD01").toString()+"'  "+
   			"   AND I.CK01 = '"+maps.get("CK01").toString()+"'   "+
   			"   AND I.FLFS01 = '"+maps.get("FLFS01").toString()+"'  "+
   			"   AND I.ITEMD15!=0  "+
   			"   AND I.SPXX01 IN (SELECT P.SPXX01  "+
 			"   FROM W_SPXX P, W_SPGL G, W_XSDD X  "+
			"   WHERE P.SPXX01 = G.SPXX01  "+
  			"   AND X.ZTID = G.ZCXX01  "+
  			"   AND X.XSDD01 = I.XSDD01  "+
  			"   AND P.SPFL01_CODE = '"+maps.get("SPFL01").toString()+"'  "+
  			"   AND P.PPB01 = '"+maps.get("PPB01").toString()+"'  "+
  			"   AND P.SPXX08 = '"+Float.parseFloat(maps.get("SPXX08").toString())+"'  "+
  			"   AND G.SPGL02 = '"+maps.get("SPGL02").toString()+"' )  ";
 			
 			Map row10 = getRow(sql10,null, 0);
			execSQL(o2o, sql10, row10);
			System.out.println("-----------------------sql-10------------------------");
			}						
		}
		
		
		@SuppressWarnings("unchecked")
		public void insertReson(String XmlData) throws Exception{
			cds=new DataSet(XmlData);
			String sql ="insert into web_ddreson values('"+cds.getField("XSDD01",0)+"','"+cds.getField("reson", 0)+"' )";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);				
		}
		
		
		@SuppressWarnings("unchecked")
		public void updateshq(String XmlData,Object XSDD02) throws Exception{
			cds=new DataSet(XmlData);
			String sql ="UPDATE W_XSDD SET XSDD02 ='"+XSDD02+"',XSDD05 = SYSDATE WHERE XSDD01 ='"+cds.getField("XSDD01", 0)+"' ";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);				
		}
		
		
		@SuppressWarnings("unchecked")
		public void djkc(String XmlData) throws Exception{			
			cds=new DataSet(XmlData);					
			List<Map<String,Object>> spidList = selectXSDDForID(XmlData);										
			for(int j = 0; j < spidList.size() ; j++){           					
				String item=cds.getField("grids", j);			
				JSONArray itemList = JSONArray.fromObject(item);	
	     		for (int i = 0; i < itemList.size(); i++) {  	     			
					Map itemMap = (Map) itemList.get(i);	
			   		  String spid = (String) itemMap.get("SPXX01");
			   		  String ckbh01 =(String) itemMap.get("CK01") ;
			   		  String cksp12 =(String) itemMap.get("CKSP12")  ;
			   		  Object itemd15 =itemMap.get("SHSL") ;
					  String itemd01=((String)cds.getField("XSDD01", 0))+ "00" + (i);							   		  
					   if(spid==null||ckbh01==null){
			   			  continue;
			   		   }								   
					String sql ="update w_kcxx set KCXX02=KCXX02+"+itemd15+" where CK01='"+ckbh01+"' and SPXX01='"+spid+"' " +
							    "    and ZCXX01='"+cds.getField("gsxxid", 0)+"' and CKSP12='"+cksp12+"' ";
					Map row = getRow(sql, null, 0);
					execSQL(o2o, sql, row);													
					
					String sql2 ="INSERT INTO W_KCDJ (XSDD01,ITEMD01,SPXX01,ZTID,CK01,CKSP12,KCDJ01,KCDJ03) " +
							"     VALUES ( '"+cds.getField("XSDD01", 0)+"' ,'"+itemd01+"', " +
							"     '"+spid+"', '"+cds.getField("gsxxid", 0)+"' ," +
							"     '"+ckbh01+"', '"+cksp12+"',"+itemd15+",sysdate)";
					Map row2 = getRow(sql2, null, 0);
					execSQL(o2o, sql2, row2);			
											
				}	    		
		    }						
		}
		
	
		@SuppressWarnings("unchecked")
		public void updateshenhe(String XmlDatas) throws Exception{
			cds=new DataSet(XmlDatas);			
			//更细订单主线状态
			String sql ="update w_djzx set w_djzx02 ='"+cds.getField("W_DJZX02", 0)+"' where w_djzx01 ='"+cds.getField("XSDD01", 0)+"'";
			Map row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);			
			
			//更新收货时间
			if((cds.getField("W_DJZX02", 0)).equals(6)){
				String sql2 ="update w_xsdd set xsdd33 =sysdate where xsdd01='"+cds.getField("XSDD01", 0)+"'";
				Map row2 = getRow(sql2, null, 0);
				execSQL(o2o, sql2, row2);			  	
			}
			
			//更新订单状态
			String sql3 ="insert into w_djzt(w_djzt01,w_djzt03) values('"+cds.getField("XSDD01", 0)+"','"+cds.getField("W_DJZT03", 0)+"')";
			Map row3 = getRow(sql3, null, 0);
			execSQL(o2o, sql3, row3);			
		}
		
		
		 //查询订单商品ID
		@SuppressWarnings("unchecked")
		   public List<Map<String,Object>> selectXSDDForID(String XmlData) throws Exception{
			  cds=new DataSet(XmlData);  
			  String sql1="SELECT SPXX01 FROM W_XSDDITEM WHERE XSDD01='"+cds.getField("XSDD01", 0)+"'";
			  List<Map<String,Object>> listz=queryForList(o2o, sql1);
			  System.out.println("------------------sql-1-----------"+listz);
			  return listz;
			}	
		@RequestMapping("/selectW_XSDD2.action")
		public Map selectW_XSDD2(String json) throws Exception{
			Map resultMap = new HashMap();
			ObjectMapper mapper = new ObjectMapper();
			List list =  (List)mapper.readValue(json, ArrayList.class); 
			Map map = (Map)list.get(0);
			JLQuery a = new JLQuery();
			List listResult = (List)a.queryForListByXML("o2o","com.jlsoft.o2o.sql.order.selectW_XSDD2",map);
			resultMap.put("map", listResult);
			return resultMap;
		}
}
