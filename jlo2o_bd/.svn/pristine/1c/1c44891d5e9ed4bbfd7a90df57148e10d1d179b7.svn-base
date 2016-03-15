package com.jlsoft.o2o.qxgl.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
@Controller
@RequestMapping("/Oper_FSFW")
public class Oper_FSFW extends JLBill{
	/**
	 * 插入生产企业的辐射区域范围
	 * 2015/12/14
	 * NineDragon
	 * @param
	 * 		array:区域代码
	 * 		zcxx01:公司id
	 * 		type:  经销企业品牌未入驻时：自行分配的销售区域 type 字段为1   生产企业和物流为0
	 * 		lx: 1表示物流  0 表示生产企业或者经销
	 * 		t : 0表示虚拟仓库  1表示：实体仓库
	 * @throws Exception
	 */
	@RequestMapping("/insert_SCFSFW.action")
	public Map<String, Object> insert_SCFSFW(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		String[] QYFW = cds.getField("array", 0).split(",");
		String zcxx01 = cds.getField("zcxx01", 1);
		String type = cds.getField("type", 2);//0 表示物流和生产  1表示经销
		String t = cds.getField("t", 4);// 0表示虚拟仓库  1表示：实体仓库
		String entity_ERPDZ = JlAppResources.getProperty("entity_ERPDZ");
		Map<String, Object> map=new HashMap<String, Object>();
		int i1 = 0;
		try {
			//判断插入的是物流企业    1表示物流 
			String WLlx = cds.getField("lx", 3);
			if(WLlx.equals("1")){
				//判断是否第一次插入
				String WL_sql =" SELECT c.dqbzm01 ,d.dqbzm02 SF,c.dqbzm02 SJ FROM  w_gsck e LEFT JOIN ck a ON e.ck01 = a.ck01 LEFT JOIN w_dqck b ON a.ck01=b.ck01 AND b.type = '0' LEFT JOIN dqbzm c ON b.dqxx01=c.dqbzm01 LEFT JOIN dqbzm d ON c.dqbzm_dqbzm01= d.dqbzm01 WHERE e.zcxx01='"+zcxx01+"' AND a.ck09=0 AND a.type='1'";
				List WL_List = queryForList(o2o, WL_sql);
				int size = WL_List.size();
				if(size >0){
					//第n次插入后：判断插入的是否为空
					if("".equals(QYFW[0])){
						//赋值，不做任何操作，提示操作成功
						 i1 = 1;
					}else{
						//第n次插入后：取插入的数据
						for(int j=0 ; j<QYFW.length ; j++){
					        String GSXX = new String(QYFW[j]);   
					        String GSXXS[] = GSXX.split("-");  
					        String dqxx01 = GSXXS[0];
					        String dqxx02 = GSXXS[1];

					        // 判断是否省级单位
					        List<String> GSXX_list = new ArrayList<String>();
					        if("0000".equals(dqxx01.substring(2))){
								String citySql = "SELECT a.DQBZM01 DQXX01, CONCAT (b.dqbzm02,a.DQBZM02)DQBZM02   "
											+ "FROM DQBZM a  LEFT JOIN DQBZM b ON a. dqbzm_dqbzm01=b.dqbzm01   WHERE a.DQBZM_DQBZM01 = '" + dqxx01 +  "' "
											+ "AND a.DQBZM03 = 2 ORDER BY a.DQBZM01";
								List city_List = queryForList(o2o, citySql);
								for (int GSXX_Nj = 0; GSXX_Nj < city_List.size(); GSXX_Nj++){
									 Map city_map = (Map)city_List.get(GSXX_Nj);
									 String city_DQXX01 =  (String) city_map.get("DQXX01");
									 String city_DQBZM02 =  (String) city_map.get("DQBZM02");
									 String city = city_DQXX01+"-"+city_DQBZM02;
									 GSXX_list.add(city);
								}
								 for (int n = 0; n < GSXX_list.size(); n++) {
								        String GSXX_DQXX = new String(GSXX_list.get(n));   
								        String GSXX_DQ[] = GSXX_DQXX.split("-");  
								        String dq01 = GSXX_DQ[0];
								        String dq02 = GSXX_DQ[1];
								        
								      //生成ck01
								        String ck01 = JLTools.getID_X(PubFun.updateWBHZT(o2o, "CK", 1),8);
										System.out.println(ck01);
								        
								        //插入gsck表
								        String GSCK_sqls="INSERT INTO w_gsck(zcxx01,CK01) VALUE('"+zcxx01+"','"+ck01+"')";
										Map GSCK_rows = getRow(GSCK_sqls, null, 0);
										execSQL(o2o, GSCK_sqls, GSCK_rows);
								        
										//插入ck表
										String CK_sqls="INSERT INTO ck(ck01,ck02,ck09,gsxx01,ERPDZ,TYPE) VALUE('"+ck01+"','"+dq02+"仓库',0,'"+zcxx01+"','"+entity_ERPDZ+"','1')";
										Map CK_rows = getRow(CK_sqls, null, 0);
										execSQL(o2o, CK_sqls, CK_rows);
										
										//插入dqck表
								        String  DQCK_SQL = "INSERT INTO w_dqck(dqxx01,ck01,dqxx02,ck02,type) VALUES ('"+dq01+"','"+ck01+"','"+dq02+"','"+dq02+"仓库','"+type+"')";
										Map DQCK_row = getRow(DQCK_SQL, null, 0);
										i1+=execSQL(o2o, DQCK_SQL, DQCK_row);
								 }
							}else{
								//生成ck01
						        String ck01 = JLTools.getID_X(PubFun.updateWBHZT(o2o, "CK", 1),8);
								System.out.println(ck01);
								
								//插入gsck表
						        String GSCK_sqls="INSERT INTO w_gsck(zcxx01,CK01) VALUE('"+zcxx01+"','"+ck01+"')";
								Map GSCK_rows = getRow(GSCK_sqls, null, 0);
								execSQL(o2o, GSCK_sqls, GSCK_rows);
						        
								//插入ck表
								String CK_sqls="INSERT INTO ck(ck01,ck02,ck09,gsxx01,ERPDZ,TYPE) VALUE('"+ck01+"','"+dqxx02+"仓库',0,'"+zcxx01+"','"+entity_ERPDZ+"','1')";
								Map CK_rows = getRow(CK_sqls, null, 0);
								execSQL(o2o, CK_sqls, CK_rows);
								
								//省份单位 插入w_dqck表
								String DQCK_sqls="INSERT INTO w_dqck(dqxx01,ck01,dqxx02,ck02,type) VALUES ('"+dqxx01+"','"+ck01+"','"+dqxx02+"','"+dqxx02+"仓库','"+type+"')";
								Map DQCK_rows = getRow(DQCK_sqls, null, 0);
								i1+=execSQL(o2o, DQCK_sqls, DQCK_rows);
								
							}
						}
					}
				}else{
					//全部插入
					if(QYFW.length>0){
						for(int j=0 ; j<QYFW.length ; j++){
					        String GSXX = new String(QYFW[j]);   
					        String GSXXS[] = GSXX.split("-");  
					        String dqxx01 = GSXXS[0];
					        String dqxx02 = GSXXS[1];
							
					        // 判断是否省级单位
					        List<String> GSXX_list = new ArrayList<String>();
					        if("0000".equals(dqxx01.substring(2))){
								String citySql = "SELECT a.DQBZM01 DQXX01, CONCAT (b.dqbzm02,a.DQBZM02)DQBZM02   "
											+ "FROM DQBZM a  LEFT JOIN DQBZM b ON a. dqbzm_dqbzm01=b.dqbzm01   WHERE a.DQBZM_DQBZM01 = '" + dqxx01 +  "' "
											+ "AND a.DQBZM03 = 2 ORDER BY a.DQBZM01";
								List city_List = queryForList(o2o, citySql);
								for (int GSXX_Nj = 0; GSXX_Nj < city_List.size(); GSXX_Nj++){
									 Map city_map = (Map)city_List.get(GSXX_Nj);
									 String city_DQXX01 =  (String) city_map.get("DQXX01");
									 String city_DQBZM02 =  (String) city_map.get("DQBZM02");
									 String city = city_DQXX01+"-"+city_DQBZM02;
									 GSXX_list.add(city);
								}
								 for (int n = 0; n < GSXX_list.size(); n++) {
								        String GSXX_DQXX = new String(GSXX_list.get(n));   
								        String GSXX_DQ[] = GSXX_DQXX.split("-");  
								        String dq01 = GSXX_DQ[0];
								        String dq02 = GSXX_DQ[1];
								        
								        //生成ck01
								        String ck01 = JLTools.getID_X(PubFun.updateWBHZT(o2o, "CK", 1),8);
										System.out.println(ck01);
								        
								        //插入gsck表
								        String GSCK_sqls="INSERT INTO w_gsck(zcxx01,CK01) VALUE('"+zcxx01+"','"+ck01+"')";
										Map GSCK_rows = getRow(GSCK_sqls, null, 0);
										execSQL(o2o, GSCK_sqls, GSCK_rows);
								        
										//插入ck表
										String CK_sqls="INSERT INTO ck(ck01,ck02,ck09,gsxx01,ERPDZ,TYPE) VALUE('"+ck01+"','"+dq02+"仓库',0,'"+zcxx01+"','"+entity_ERPDZ+"',1)";
										Map CK_rows = getRow(CK_sqls, null, 0);
										execSQL(o2o, CK_sqls, CK_rows);
										
										//插入dqck表
								        String  DQCK_SQL = "INSERT INTO w_dqck(dqxx01,ck01,dqxx02,ck02,type) VALUES ('"+dq01+"','"+ck01+"','"+dq02+"','"+dq02+"仓库','"+type+"')";
										Map DQCK_row = getRow(DQCK_SQL, null, 0);
										i1+=execSQL(o2o, DQCK_SQL, DQCK_row);
								 }
							}else{
								//生成ck01
						        String ck01 = JLTools.getID_X(PubFun.updateWBHZT(o2o, "CK", 1),8);
								System.out.println(ck01);
								
								//插入gsck表
						        String GSCK_sqls="INSERT INTO w_gsck(zcxx01,CK01) VALUE('"+zcxx01+"','"+ck01+"')";
								Map GSCK_rows = getRow(GSCK_sqls, null, 0);
								execSQL(o2o, GSCK_sqls, GSCK_rows);
						        
								//插入ck表
								String CK_sqls="INSERT INTO ck(ck01,ck02,ck09,gsxx01,ERPDZ,TYPE) VALUE('"+ck01+"','"+dqxx02+"仓库',0,'"+zcxx01+"','"+entity_ERPDZ+"','1')";
								Map CK_rows = getRow(CK_sqls, null, 0);
								execSQL(o2o, CK_sqls, CK_rows);
								
								//省份单位 插入w_dqck表
								String DQCK_sqls="INSERT INTO w_dqck(dqxx01,ck01,dqxx02,ck02,type) VALUES ('"+dqxx01+"','"+ck01+"','"+dqxx02+"','"+dqxx02+"仓库','"+type+"')";
								Map DQCK_rows = getRow(DQCK_sqls, null, 0);
								i1+=execSQL(o2o, DQCK_sqls, DQCK_rows);
							}
						}
					}
					
				}
				
			}else{
				//生产和经销
				String sql ="DELETE FROM w_dqck WHERE ck01 = (SELECT b.ck01 FROM w_gsck a LEFT JOIN ck b ON a.ck01=b.ck01 WHERE a.zcxx01='"+zcxx01+"' AND b.ck09=0 AND b.type='"+t+"')  AND type='"+type+"' ";
				Map row = getRow(sql, null, 0);
				execSQL(o2o, sql, row);
				
				if(QYFW.length>0){
					for(int j=0 ; j<QYFW.length ; j++){
				        String GSXX = new String(QYFW[j]);   
				        String GSXXS[] = GSXX.split("-");  
				        String dqxx01 = GSXXS[0];
				        String dqxx02 = GSXXS[1];
				        // 判断是否省级单位
				        List<String> GSXX_list = new ArrayList<String>();
				        if("0000".equals(dqxx01.substring(2))){
							String citySql = "SELECT a.DQBZM01 DQXX01, CONCAT (b.dqbzm02,a.DQBZM02)DQBZM02   "
										+ "FROM DQBZM a  LEFT JOIN DQBZM b ON a. dqbzm_dqbzm01=b.dqbzm01   WHERE a.DQBZM_DQBZM01 = '" + dqxx01 +  "' "
										+ "AND a.DQBZM03 = 2 ORDER BY a.DQBZM01";
							List city_List = queryForList(o2o, citySql);
							for (int GSXX_Nj = 0; GSXX_Nj < city_List.size(); GSXX_Nj++){
								 Map city_map = (Map)city_List.get(GSXX_Nj);
								 String city_DQXX01 =  (String) city_map.get("DQXX01");
								 String city_DQBZM02 =  (String) city_map.get("DQBZM02");
								 String city = city_DQXX01+"-"+city_DQBZM02;
								 GSXX_list.add(city);
							}
							 for (int n = 0; n < GSXX_list.size(); n++) {
							        String GSXX_DQXX = new String(GSXX_list.get(n));   
							        String GSXX_DQ[] = GSXX_DQXX.split("-");  
							        String dq01 = GSXX_DQ[0];
							        String dq02 = GSXX_DQ[1];
							        String  DQCK_SQL = "INSERT INTO w_dqck(dqxx01,ck01,dqxx02,ck02,type) VALUES ('"+dq01+"',(SELECT b.ck01 FROM w_gsck a LEFT JOIN ck b ON a.ck01=b.ck01 WHERE a.zcxx01='"+zcxx01+"' AND b.ck09=0 AND b.type='"+t+"'),'"+dq02+"',(SELECT b.ck02 FROM w_gsck a LEFT JOIN ck b ON a.ck01=b.ck01 WHERE a.zcxx01='"+zcxx01+"' AND b.ck09=0 AND b.type='"+t+"'),'"+type+"')";
									Map DQCK_row = getRow(DQCK_SQL, null, 0);
									i1+=execSQL(o2o, DQCK_SQL, DQCK_row);
							 }
						}else{
							//省份单位
							String DQCK_sqls="INSERT INTO w_dqck(dqxx01,ck01,dqxx02,ck02,type) SELECT '" + dqxx01 + "', b.ck01, '" + dqxx02 + "', b.ck02, '" + type + "' FROM w_gsck a LEFT JOIN ck b ON a.ck01 = b.ck01 WHERE a.zcxx01 = '" + zcxx01 + "' AND b.ck09 = 0 AND b.type = '" + t + "'";
							Map DQCK_rows = getRow(DQCK_sqls, null, 0);
							i1+=execSQL(o2o, DQCK_sqls, DQCK_rows);
						}
					}
				}
			}
			if (i1 > 0) {
				map.put("STATE", "0");
			} else {
				map.put("STATE", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATE", "1");
			throw e;
		}
		return map;
	}
	
	/**
	 * 
	 * 前提条件：ck表  type为0的虚拟仓库  必须保证一个公司下只有一个虚拟仓库
	 * http://localhost:8088/Oper_FSFW/insert_test.action?XmlData=[{"in":"in"}]
	 * 
	 */
	@RequestMapping("/insert_oldData.action")
	public Map<String, Object> insert_oldData(String XmlData) throws Exception{
		cds=new DataSet(XmlData);
		Map<String, Object> map=new HashMap<String, Object>();
		int i1=0;
		try {
			//查询之前注册公司的 zcxx01 
			List<String> list = new ArrayList<String>();
			String sql = "SELECT zcxx01,zcxx02 FROM w_zcgs WHERE zcgs01=4 AND (zcgs03 =43 OR zcgs03=42)";
			List GSList = queryForList(o2o, sql);
			for (int j = 0; j < GSList.size(); j++){
				 Map GSmap = (Map)GSList.get(j);
				 String z01 =  (String) GSmap.get("zcxx01");
				 String z02 =  (String) GSmap.get("zcxx02");
				 //为空判断
				 if( z01 == null || "".equals(z01)){
					 continue;
				 }
				 if( z02 == null || "".equals(z02)){
					 continue;
				 }
				 String gs = z01+"-"+z02;
				 list.add(gs);
			}
			for (int n = 0; n < list.size(); n++) {
		        String GSXX = new String(list.get(n));   
		        String GSXXS[] = GSXX.split("-"); 
		        String zcxx01 = GSXXS[0];
		        String zcxx02 = GSXXS[1];
		        
		        //生成ck01
		        String ck01 = JLTools.getID_X(PubFun.updateWBHZT(o2o, "CK", 1),8);
				System.out.println(ck01);
				//判断之前老数据ck01是否为8位，ture不操作
				String sql8 =" SELECT ck01 FROM w_gsck WHERE zcxx01='"+zcxx01+"' AND CK01=(SELECT ck01 FROM ck WHERE ck09=0 AND TYPE=0 AND gsxx01='"+zcxx01+"')";
				List queryForList = queryForList(o2o, sql8);
				//为空判断 
				int length;
				if(queryForList.size() > 0){
					Map map8 = (Map) queryForList.get(0);
					String ck018 = (String) map8.get("ck01");
				    length = ck018.toString().length();
				}else{
					length =9;
				}
				if(length == 8){
					//不操作
				}else{
				
		        //【w_gsck 表插入 数据】
		        //进行判断是否有值：
		        String GS_sql = "SELECT zcxx01,CK01 FROM w_gsck WHERE zcxx01='"+zcxx01+"' ";
				List GS_List = queryForList(o2o, GS_sql);
				int size = GS_List.size();
				if(size >0){
					//更新
					String  GSCK_SQL = "UPDATE w_gsck SET CK01='"+ck01+"' WHERE zcxx01='"+zcxx01+"' AND CK01=(SELECT ck01 FROM ck WHERE ck09=0 AND TYPE=0 AND gsxx01='"+zcxx01+"')  ";
					Map GSCK_row = getRow(GSCK_SQL, null, 0);
			        i1+=execSQL(o2o, GSCK_SQL, GSCK_row); 
				}else{
					//插入
			        String GSCK_SQLS ="INSERT INTO w_gsck(zcxx01,CK01) VALUES ('"+zcxx01+"','"+ck01+"') ";
					Map GSCK_rows = getRow(GSCK_SQLS, null, 0);
					i1 += execSQL(o2o, GSCK_SQLS, GSCK_rows);
				}
				//【ck表中插入数据】
				//进行判断是否有值 
				String CK_SQL = "SELECT ck01 FROM ck WHERE gsxx01='"+zcxx01+"' AND ck09=0 AND TYPE=0 ";
				List CK_List = queryForList(o2o, CK_SQL);
				int sizes = CK_List.size();
				if(sizes >0){
					//更新
					String  W_CK_SQL = "UPDATE ck SET ck01='"+ck01+"' ,ERPDZ='0001010103'  WHERE gsxx01='"+zcxx01+"'AND TYPE='0' ";
					Map W_CK_row = getRow(W_CK_SQL, null, 0);
			        i1+=execSQL(o2o, W_CK_SQL, W_CK_row);
				}else{
					//插入
					String W_CK_SQLS ="INSERT INTO ck(ck01,ck02,ck09,gsxx01,ERPDZ,TYPE)  VALUES ('"+ck01+"','"+zcxx02+"仓库',0,'"+zcxx01+"','0001010103',0) ";
					Map W_CK_rows = getRow(W_CK_SQLS, null, 0);
					i1 += execSQL(o2o, W_CK_SQLS, W_CK_rows);
				}
				
				//【w_kcxx表插入数据】
				//进行判断是否有值 
				String KCXX_SQL = "SELECT zcxx01,spxx01,ck01 FROM w_kcxx WHERE zcxx01='"+zcxx01+"' AND ck01=03 ";
				List KCXX_List = queryForList(o2o, KCXX_SQL);
				int sizess = KCXX_List.size();
				if(sizess >0){
					//更新  
					String W_KCXX_SQL ="UPDATE w_kcxx SET ck01='"+ck01+"'   WHERE zcxx01='"+zcxx01+"' AND ck01='03' ";
					Map W_KCXX_rows = getRow(W_KCXX_SQL, null, 0);
					i1 += execSQL(o2o, W_KCXX_SQL, W_KCXX_rows);
				}else{
					//插入
					//无需操作：说明该企业没有发布商品。
				}
				//往  w_dqck增加默认数据 --- dqxx01=120100 
				//判断zcxx01 是生产还是经销企业  43生产  42经销
				String sqllx = "SELECT zcgs03 FROM w_zcgs WHERE zcxx01='"+zcxx01+"'";
				List Listlx = queryForList(o2o, sqllx);
				 Map maplx = (Map)Listlx.get(0);
				 Integer zcgslx =  (Integer) maplx.get("zcgs03");
				 System.out.println(zcgslx);
		        if(zcgslx ==43){
		        	//生产
		        	//判断w_dqck表中是否有天津仓库的数据
		        	String sqltj ="SELECT ck01 FROM w_dqck WHERE ck01=(SELECT ck01 FROM ck WHERE gsxx01='"+zcxx01+"' AND TYPE='0') AND dqxx01='120100'";
		        	List Listtj = queryForList(o2o, sqltj);
		        	int sizetj = Listtj.size();
		        	if(sizetj > 0){
		        		//不插入
		        	}else{
		        		String insertDqckSql = "INSERT INTO w_dqck (dqxx01, ck01, ck02, dqxx02, type, status) VALUES ('120100','"+ck01+"', '"+zcxx02+"仓库',"+"'天津天津市', 0, 0)";
						Map row = getRow(insertDqckSql, null, 0);
						i1 += execSQL(o2o, insertDqckSql, row);	
						System.out.println("insert into w_dqck: "+ ck01);
		        	}
		        }else{
		        	//经销
		        	//判断w_dqck表中是否有天津仓库的数据
		        	String sqltj ="SELECT ck01 FROM w_dqck WHERE ck01=(SELECT ck01 FROM ck WHERE gsxx01='"+zcxx01+"' AND TYPE='0') AND dqxx01='120100'";
		        	List Listtj = queryForList(o2o, sqltj);
		        	int sizetj = Listtj.size();
		        	if(sizetj > 0){
		        		//不插入
		        	}else{
		        		String insertDqckSql = "INSERT INTO w_dqck (dqxx01, ck01, ck02, dqxx02, type, status) VALUES ('120100','"+ck01+"', '"+zcxx02+"仓库',"+"'天津天津市', 1, 0)";
						Map row = getRow(insertDqckSql, null, 0);
						i1 += execSQL(o2o, insertDqckSql, row);	
						System.out.println("insert into w_dqck: "+ ck01);
		        	}
		        }
				}
			}
			if (i1 > 0) {
				map.put("STATE", "成功");
			} else {
				map.put("STATE", "失败");
			}
		} catch (Exception e) {
			e.getStackTrace();
			map.put("STATE", "失败");
		}
		return map;
	}

	//事物控制更新数据方法
	@RequestMapping("/handleData.action")
	public Map handleData() {
		Map resultMap = new HashMap();
		try {
			//查询之前注册公司的 zcxx01 
			List<String> list = new ArrayList<String>();
			String sql = "SELECT zcxx01,zcxx02 FROM w_zcgs WHERE zcgs01=4 AND (zcgs03 =43 OR zcgs03=42)";
			List GSList = queryForList(o2o, sql);
			for (int j = 0; j < GSList.size(); j++){
				 Map GSmap = (Map)GSList.get(j);
				 String z01 =  (String) GSmap.get("zcxx01");
				 String z02 =  (String) GSmap.get("zcxx02");
				 //为空判断
				 if( z01 == null || "".equals(z01) ){
					 continue;
				 }
				 if( z02 == null || "".equals(z02)){
					 continue;
				 }
				 String gs = z01+"-"+z02;
				 list.add(gs);
			}
			for (int n = 0; n < list.size(); n++) {
		        String GSXX = new String(list.get(n));   
		        String GSXXS[] = GSXX.split("-"); 
		        String zcxx01 = GSXXS[0];
		        String zcxx02 = GSXXS[1];
		        
		        //生成ck01
		        String ck01 = JLTools.getID_X(PubFun.updateWBHZT(o2o, "CK", 1),8);
				System.out.println(ck01);
		        //String ck01 = n +"0000";
		        
				//判断之前老数据ck01是否为8位，ture不操作
				String sql8 =" SELECT ck01 FROM w_gsck WHERE zcxx01='"+zcxx01+"' AND CK01=(SELECT ck01 FROM ck WHERE ck09=0 AND TYPE=0 AND gsxx01='"+zcxx01+"')";
				List queryForList = queryForList(o2o, sql8);
				//为空判断 
				int length;
				if(queryForList.size() > 0){
					Map map8 = (Map) queryForList.get(0);
					String ck018 = (String) map8.get("ck01");
				    length = ck018.toString().length();
				}else{
					length =9;
				}
				if(length == 8){
					//不操作
				}else{
		        //【w_gsck 表插入 数据】
		        //进行判断是否有值：
		        String GS_sql = "SELECT zcxx01,CK01 FROM w_gsck WHERE zcxx01='"+zcxx01+"' ";
				List GS_List = queryForList(o2o, GS_sql);
				int size = GS_List.size();
				if(size >0){
					//事物控制更新
					updateGSCK(ck01,zcxx01);
				}else{
					//事物控制插入
					insertGSCK(ck01,zcxx01);
				}
				//【ck表中插入数据】
				//进行判断是否有值 
				String CK_SQL = "SELECT ck01 FROM ck WHERE gsxx01='"+zcxx01+"' AND ck09=0 AND TYPE=0 ";
				List CK_List = queryForList(o2o, CK_SQL);
				int sizes = CK_List.size();
				if(sizes >0){
					//事物控制更新CK
					updateCK(ck01,zcxx01);
				}else{
					//事物控制插入CK
					insertCK(ck01,zcxx02,zcxx01);
				}
				//【w_kcxx表插入数据】
				//进行判断是否有值 
				String KCXX_SQL = "SELECT zcxx01,spxx01,ck01 FROM w_kcxx WHERE zcxx01='"+zcxx01+"' AND ck01=03 ";
				List KCXX_List = queryForList(o2o, KCXX_SQL);
				int sizess = KCXX_List.size();
				if(sizess >0){
					//事物控制更新w_kcxx
					updateKCXX(ck01,zcxx01);
				}else{
					//插入
					//无需操作：说明该企业没有发布商品。
				}
				//往  w_dqck增加默认数据 --- dqxx01=120100 
				//判断zcxx01 是生产还是经销企业  43生产  42经销
				String sqllx = "SELECT zcgs03 FROM w_zcgs WHERE zcxx01='"+zcxx01+"'";
				List Listlx = queryForList(o2o, sqllx);
				Map maplx = (Map)Listlx.get(0);
				Integer zcgslx =  (Integer) maplx.get("zcgs03");
				System.out.println(zcgslx);
				if(zcgslx ==43){
		        	//生产
		        	//判断w_dqck表中是否有天津仓库的数据
		        	String sqltj ="SELECT ck01 FROM w_dqck WHERE ck01=(SELECT ck01 FROM ck WHERE gsxx01='"+zcxx01+"' AND TYPE='0') AND dqxx01='120100'";
		        	List Listtj = queryForList(o2o, sqltj);
		        	int sizetj = Listtj.size();
		        	if(sizetj > 0){
		        		//不插入
		        	}else{
		        		//事物管理插入w_dqck 公司类型为43
		        		insertDQCK(ck01,zcxx02);
		        	}
		        }else{
		        	//经销
		        	//判断w_dqck表中是否有天津仓库的数据
		        	String sqltj ="SELECT ck01 FROM w_dqck WHERE ck01=(SELECT ck01 FROM ck WHERE gsxx01='"+zcxx01+"' AND TYPE='0') AND dqxx01='120100'";
		        	List Listtj = queryForList(o2o, sqltj);
		        	int sizetj = Listtj.size();
		        	if(sizetj > 0){
		        		//不插入
		        	}else{
		        		//事物管理插入w_dqck 公司类型为42
		        		insertDQCKS(ck01,zcxx02);
		        	}
		        }
				}
				resultMap.put("STATE", "成功");
			}
		} catch (Exception e) {
			resultMap.put("STATE", "失败");
		}
		return resultMap;
		}

	//事物控制插入w_dqck --42--经销
	@RequestMapping("/insertDQCKS")
	private Map insertDQCKS(String ck01, String zcxx02) throws Exception {
		Map resultMap = new HashMap();
		try {
			String insertDqckSql = "INSERT INTO w_dqck (dqxx01, ck01, ck02, dqxx02, type, status) VALUES ('120100','"+ck01+"', '"+zcxx02+"仓库',"+"'天津天津市', 1, 0)";
			Map row = new HashMap();
			execSQL(o2o, insertDqckSql, row);	
			System.out.println("insert into w_dqck: "+ ck01);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}	
	
	//事物控制插入w_dqck --43--生产
	@RequestMapping("/insertDQCK")
	private Map insertDQCK(String ck01, String zcxx02) throws Exception {
		Map resultMap = new HashMap();
		try {
			String insertDqckSql = "INSERT INTO w_dqck (dqxx01, ck01, ck02, dqxx02, type, status) VALUES ('120100','"+ck01+"', '"+zcxx02+"仓库',"+"'天津天津市', 0, 0)";
			Map row = new HashMap();
			execSQL(o2o, insertDqckSql, row);	
			System.out.println("insert into w_dqck: "+ ck01);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}

	//事物控制更新w_kcxx
	@RequestMapping("/updateKCXX")
	private Map updateKCXX(String ck01, String zcxx01) throws Exception{
		Map resultMap = new HashMap();
		try {
			String W_KCXX_SQL ="UPDATE w_kcxx SET ck01='"+ck01+"'   WHERE zcxx01='"+zcxx01+"' AND ck01='03' ";
			Map row = new HashMap();
			execSQL(o2o, W_KCXX_SQL, row);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}	
	
	//事物控制插入ck
	@RequestMapping("/insertCK")
	private Map insertCK(String ck01, String zcxx02, String zcxx01) throws Exception {
		Map resultMap = new HashMap();
		try {
			String W_CK_SQLS ="INSERT INTO ck(ck01,ck02,ck09,gsxx01,ERPDZ,TYPE)  VALUES ('"+ck01+"','"+zcxx02+"仓库',0,'"+zcxx01+"','0001010103',0) ";
			Map row = new HashMap();
			execSQL(o2o, W_CK_SQLS, row);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}	

	//事物控制更新ck
	@RequestMapping("/updateCK")
	private Map updateCK(String ck01, String zcxx01) throws Exception{
		Map resultMap = new HashMap();
		try {
			String  W_CK_SQL = "UPDATE ck SET ck01='"+ck01+"' ,ERPDZ='0001010103'  WHERE gsxx01='"+zcxx01+"'AND TYPE='0' ";
			Map row = new HashMap();
	        execSQL(o2o, W_CK_SQL, row);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}
	
	//事物控制插入w_gsck
	@RequestMapping("/insertGSCK")
	private Map insertGSCK(String ck01, String zcxx01) throws Exception {
		Map resultMap = new HashMap();
		try {
			String GSCK_SQLS ="INSERT INTO w_gsck(zcxx01,CK01) VALUES ('"+zcxx01+"','"+ck01+"') ";
			Map row = new HashMap();
			execSQL(o2o, GSCK_SQLS, row);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}

	//事物控制更新w_gsck
	@RequestMapping("/updateGSCK")
	private Map updateGSCK(String ck01, String zcxx01) throws Exception{
		Map resultMap = new HashMap();
		try {
			String  GSCK_SQL = "UPDATE w_gsck SET CK01='"+ck01+"' WHERE zcxx01='"+zcxx01+"' AND CK01=(SELECT ck01 FROM ck WHERE ck09=0 AND TYPE=0 AND gsxx01='"+zcxx01+"')  ";
			Map row = new HashMap();
			execSQL(o2o, GSCK_SQL, row);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
		
	}

}