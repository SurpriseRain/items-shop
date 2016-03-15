 package com.jlsoft.o2o.init.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chefu.common.RedisClient;
import com.chefu.common.RedisUtil;
import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.product.service.showGoods;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;

@Component
@RequestMapping("/startup")
public class Startup extends JLBill implements ApplicationListener<ApplicationEvent> {
    private static Logger logger = Logger.getLogger(Startup.class);
	private static boolean isStarted = true;
	public void onApplicationEvent(ApplicationEvent event) {
		if (isStarted) {
			isStarted=false;//事件只在开启时启动一次
			//查询开关
			Globals.confList=conflists();
			
			if(JLTools.getCurConf(5) == 1){
				String redisIp = JlAppResources.getProperty("REDIS_IP");
				int redisPort = Integer.parseInt(JlAppResources.getProperty("REDIS_PORT"));
				RedisUtil.InitConnectionPool(redisIp, redisPort);
				RedisClient rc = new RedisClient();
				try {
					//查询分类品牌
					rc.SetObjectByKey("goodslist", ShowGoodslist());
					rc.SetObjectByKey("projectList", projectlists());
					//所有车型车系展示页面初始
					rc.SetObjectByKey("qfylist", qfylists());
					//首页车型车系初始数据
					rc.SetObjectByKey("qfypplist", qfypplists());
					rc.SetObjectByKey("cxcxlist", qfypplist());
					//查询所有配件品牌
					rc.SetObjectByKey("pjpplist", pjpplists());
					//查询所有配件品牌，主要用于首页显示分类
					rc.SetObjectByKey("fllist", fllist());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			} else {
				//查询分类品牌
				Globals.goodslist=ShowGoodslist();
				Globals.projectList=projectlists();
				//所有车型车系展示页面初始
				Globals.qfylist=qfylists();
				//首页车型车系初始数据
				Globals.qfypplist=qfypplists();
				Globals.cxcxlist=qfypplist();
				//查询所有配件品牌
				Globals.pjpplist=pjpplists();
				//查询所有配件品牌，主要用于首页显示分类
				Globals.fllist=fllist();
			}
		}
	}
	
	/**
	 * 更新初始化状态
	 * @return
	 */
	@RequestMapping("/reStart.action")
	public Map<String, Object> reStart(){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if(JLTools.getCurConf(5) == 1){
				RedisClient rc = new RedisClient();
				//查询分类品牌
				rc.SetObjectByKey("goodslist", ShowGoodslist());
				rc.SetObjectByKey("projectList", projectlists());
				//所有车型车系展示页面初始
				rc.SetObjectByKey("qfylist", qfylists());
				//首页车型车系初始数据
				rc.SetObjectByKey("qfypplist", qfypplists());
				rc.SetObjectByKey("cxcxlist", qfypplist());
				//查询所有配件品牌
				rc.SetObjectByKey("pjpplist", pjpplists());
				//查询所有配件品牌，主要用于首页显示分类
				rc.SetObjectByKey("fllist", fllist());
			} else {
				//查询分类品牌
				Globals.goodslist=ShowGoodslist();
				Globals.projectList=projectlists();
				//所有车型车系展示页面初始
				Globals.qfylist=qfylists();
				//首页车型车系初始数据
				Globals.qfypplist=qfypplists();
				Globals.cxcxlist=qfypplist();
				//查询所有配件品牌
				Globals.pjpplist=pjpplists();
				//查询所有配件品牌，主要用于首页显示分类
				Globals.fllist=fllist();
			}
			// 状态返回更新成功
			returnMap.put("STATE", "1");
		} catch (Exception e) {
			// 状态返回失败
			returnMap.put("STATE", "0");
			e.printStackTrace();
		} 
		return returnMap;
	}
	private List projectlists() {
		List list = new ArrayList();
		showGoods dao= new showGoods(o2o);
		try {
			list= (List) dao.selectProjects().get("projectlist");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询系统开关参数
	private List conflists(){
		String sql = "SELECT JLCO01,JLCO02,JLCO04,JLCO07 FROM W_CONF ORDER BY JLCO01";
		List list = queryForList(o2o,sql);
		return list;
	}
	
	private List pjpplists() {
		List list = new ArrayList();
		showGoods dao= new showGoods(o2o);
		try {
			list= dao.selectPjpplists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//根据“国家分类”显示车型车系
	private List qfypplists() {
		List ShowGoodslist = new ArrayList();
		showGoods dao= new showGoods(o2o);
		try {
			List<Map<String ,String>> PPFL01list=dao.select_qfyppfl01();   //查询一级分类
			if(PPFL01list != null&&PPFL01list.size() > 0){
				for (int i=0; i < PPFL01list.size(); i++) {
					Map spfl01map = (Map<String, String>) PPFL01list.get(i);	
					String ppfl01code = spfl01map.get("ORIGIN_COUNTRY_ID").toString();																		
					List PPFL02list = dao.select_qfyppfl02(ppfl01code);	
				    Map PPFL03Map = null;

					for(int j=0;j<PPFL02list.size();j++){
					    PPFL03Map = (Map)PPFL02list.get(j);
						String BRAND = PPFL03Map.get("BRAND").toString();  
						List<Map<String, String>> PPFL03list = dao.qfySelect_fl03(BRAND);	 //获取3级
						Map PPFL04Map = null;
						
						for(int k=0;k<PPFL03list.size();k++){
						    PPFL04Map = (Map)PPFL03list.get(k);
							String CAR_TYPE = PPFL04Map.get("CAR_TYPE").toString();  
							List<Map<String, String>> PPFL04list = dao.qfySelect_fl04(CAR_TYPE);	 //获取4级
						    PPFL04Map.put("PPFL04list",PPFL04list);
						}
						PPFL03Map.put("PPFL03list",PPFL03list);
					}
					spfl01map.put("PPFL02list", PPFL02list);
				}
			}
			ShowGoodslist=JSONArray.fromObject(PPFL01list);
		} catch (Exception e) {
			e.printStackTrace();
		}  //查询一级分类
		return ShowGoodslist;
	}
	//根据“国家分类”显示车型车系
		private List qfypplist() {
			List ShowGoodslist = new ArrayList();
			showGoods dao= new showGoods(o2o);
			try {
				List<Map<String ,String>> PPFL01list=dao.select_qfyppfl01();   //查询一级分类
				if(PPFL01list != null&&PPFL01list.size() > 0){
					for (int i=0; i < PPFL01list.size(); i++) {
						Map spfl01map = (Map<String, String>) PPFL01list.get(i);	
						String ppfl01code = spfl01map.get("ORIGIN_COUNTRY_ID").toString();																		
						List PPFL02list = dao.select_qfyppfl02(ppfl01code);	
						spfl01map.put("PPFL02list", PPFL02list);
					}
				}
				ShowGoodslist=JSONArray.fromObject(PPFL01list);
			} catch (Exception e) {
				e.printStackTrace();
			}  //查询一级分类
			return ShowGoodslist;
		}
	public List ShowGoodslist(){
		List ShowGoodslist = new ArrayList();
		showGoods dao= new showGoods(o2o);
		try {
			if(JlAppResources.getProperty("ROADMAP").equals("4")) {
				List<Map<String ,String>> PPFL01list=dao.select_fl01();  //查询一级分类
				if(PPFL01list != null&&PPFL01list.size() > 0){				
					for (int i=0; i < PPFL01list.size(); i++) {
						Map spfl01map = (Map<String, String>) PPFL01list.get(i);	
						String ppfl01code = (String) spfl01map.get("FLCODE");
						String ppfl01name = (String) spfl01map.get("FLNAME");
						List list01 = new ArrayList();
						Map map = new HashMap();
						map.put("ppfl01code", ppfl01code);
						list01.add(map);
						String XmlData = JSONArray.fromObject(list01).toString();
						List<HashMap<String, String>> PPFL02list = dao.select_fl02(XmlData);	   //查询二级分类				
						List<HashMap<String, String>> PPBlist = new ArrayList<HashMap<String,String>>();
						
						Map newmap = new HashMap();
						newmap.put("ppfl01code", ppfl01code);
						newmap.put("ppfl01name", ppfl01name);	
						if(JlAppResources.getProperty("ROADMAP").equals("4")) {
							//根据一级分类查询品牌
							List PPlist = dao.select_pp(ppfl01code);
							newmap.put("PPlist", PPlist);
							System.out.println("PPlist----"+PPlist);
						} 
						
						
						if(PPFL02list !=null&&PPFL02list.size()>0){
							for (int j = 0; j < PPFL02list.size(); j++) {							
								Map spfl02map = (Map) PPFL02list.get(j);								
								String ppfl02code = spfl02map.get("FLCODE").toString();
								String ppfl02name = spfl02map.get("FLNAME").toString();						
								//查询条件
								List list02 = new ArrayList();
								Map maps = new HashMap();
								maps.put("ppfl02code", ppfl02code);
								maps.put("usergsid","");
								list02.add(maps);	
								XmlData= JSONArray.fromObject(list02).toString();
								List<HashMap<String, String>> PPFL03list = dao.select_fl03(XmlData);	  //查询三级分类	
								if(PPFL03list !=null&&PPFL03list.size()>0){
									for (int  m= 0; m < PPFL03list.size(); m++) {							
										Map spfl03map = (Map) PPFL03list.get(m);	
										String ppfl03code = spfl03map.get("FLCODE").toString();
										String ppfl03name = spfl03map.get("FLNAME").toString();
										//查询条件
										List list03 = new ArrayList();
										Map map3 = new HashMap();
										map3.put("ppfl03code", ppfl03code);
										map3.put("usergsid","");
										list03.add(map3);	
										XmlData= JSONArray.fromObject(list03).toString();
										List<HashMap<String, String>> PPFL04list = dao.select_fl04(XmlData);	  //查询四级分类	
										spfl03map.put("SPFL04List", PPFL04list);
									}
								}
								spfl02map.put("SPFL03List", PPFL03list);
								
								PPBlist = dao.select_ppblist(maps);
								spfl02map.put("PPBLIST", PPBlist);
							}	
							newmap.put("SPFL02List", PPFL02list);	
						}
						ShowGoodslist.add(newmap);
					}
				}
			}else{
					List<Map<String ,String>> PPFL01list=dao.select_fl01();  //查询一级分类
					if(PPFL01list != null&&PPFL01list.size() > 0){				
						for (int i=0; i < PPFL01list.size(); i++) {
							Map spfl01map = (Map<String, String>) PPFL01list.get(i);	
							
							String ppfl01code = (String) spfl01map.get("FLCODE");
							String ppfl01name = (String) spfl01map.get("FLNAME");
							
							List list01 = new ArrayList();
							Map map = new HashMap();
							map.put("ppfl01code", ppfl01code);
							list01.add(map);
							String XmlData = JSONArray.fromObject(list01).toString();
							
							List<HashMap<String, String>> PPFL02list = dao.select_fl02(XmlData);					
							List<HashMap<String, String>> PPBlist = new ArrayList<HashMap<String,String>>();
							
							Map newmap = new HashMap();
							newmap.put("ppfl01code", ppfl01code);
							newmap.put("ppfl01name", ppfl01name);	
							
							if(JlAppResources.getProperty("ROADMAP").equals("4")) {
								//根据一级分类查询品牌
								List PPlist = dao.select_pp(ppfl01code);	
								newmap.put("PPlist", PPlist);
							} 
							
							if(PPFL02list !=null&&PPFL02list.size()>0){
								for (int j = 0; j < PPFL02list.size(); j++) {							
									Map spfl02map = (Map) PPFL02list.get(j);								
									String ppfl02code = spfl02map.get("FLCODE").toString();
									String ppfl02name = spfl02map.get("FLNAME").toString();						
									
									//查询条件
									List lists = new ArrayList();
									Map maps = new HashMap();
									maps.put("ppfl02code", ppfl02code);
									maps.put("usergsid","");
									lists.add(maps);	
									
									PPBlist = dao.select_ppblist(maps);
									spfl02map.put("PPBLIST", PPBlist);
								}	
								newmap.put("SPFL02List", PPFL02list);	
								ShowGoodslist.add(newmap);						
							}
						}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ShowGoodslist;
	}
	
	public List qfylist(){
		List ShowGoodslist = new ArrayList();
		List<HashMap<String, String>> PPBlist = new ArrayList<HashMap<String,String>>();
		showGoods dao= new showGoods(o2o);
		try {
			List<Map<String ,String>> PPFL01list=dao.qfySelect_fl01();  //查询一级分类  A B C D
			Map mapend = new HashMap();
			if(PPFL01list != null&&PPFL01list.size() > 0){	
				for (int i=0; i < PPFL01list.size(); i++) {
					Map<String ,String> spfl01map = (Map<String, String>) PPFL01list.get(i);
					String MNEMONICCODE = spfl01map.get("MNEMONICCODE");
					List listTJ = new ArrayList();
					Map mapTJ = new HashMap();
					mapTJ.put("MNEMONICCODE", MNEMONICCODE);
					listTJ.add(mapTJ);
					String XmlData = JSONArray.fromObject(listTJ).toString();
					List<HashMap<String, String>> PPFL02list = dao.qfySelect_fl02(XmlData);	//获取2级
					mapend.put("MNEMONICCODE",MNEMONICCODE);
					if(PPFL02list !=null&&PPFL02list.size()>0){
						for (int j = 0; j < PPFL02list.size(); j++) {	
							Map spfl02map = (Map) PPFL02list.get(j);								
							String BRAND = spfl02map.get("BRAND").toString();  
							
							//查询条件
							mapTJ.put("BRAND", BRAND);
							listTJ.add(mapTJ);	
							String XmlData2 = JSONArray.fromObject(listTJ).toString();

							List<HashMap<String, String>> PPFL03list = dao.qfySelect_fl03(XmlData2);	 //获取3级
							spfl02map.put("PPFL03list",PPFL03list);
						}	
					}
					mapend.put("PPFL02list", PPFL02list);
				}
			}
			ShowGoodslist.add(mapend);					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ShowGoodslist;
	}
	
	public List qfylists(){
		List ShowGoodslist = new ArrayList();
		showGoods dao= new showGoods(o2o);
		try {
			
		    List<Map<String ,String>> resultList=dao.qfySelect_fl01();    //获取1级
		    Map PPFL02Map = null;
		    
		    for(int i=0;i<resultList.size();i++){
			    PPFL02Map = (Map<String, String>)resultList.get(i);
			    String MNEMONICCODE = PPFL02Map.get("MNEMONICCODE").toString();
				List<Map<String, String>> PPFL02list = dao.qfySelect_fl02(MNEMONICCODE);	//获取2级
			    Map PPFL03Map = null;
			      
			    for(int j=0;j<PPFL02list.size();j++){
				    PPFL03Map = (Map)PPFL02list.get(j);
					String BRAND = PPFL03Map.get("BRAND").toString();  
					List<Map<String, String>> PPFL03list = dao.qfySelect_fl03(BRAND);	 //获取3级
					Map PPFL04Map = null;
					
				    for(int k=0;k<PPFL03list.size();k++){
					    PPFL04Map = (Map)PPFL03list.get(k);
						String CAR_TYPE = PPFL04Map.get("CAR_TYPE").toString();  
						List<Map<String, String>> PPFL04list = dao.qfySelect_fl04(CAR_TYPE);	 //获取4级
					    PPFL04Map.put("PPFL04list",PPFL04list);
				    }
				    PPFL03Map.put("PPFL03list",PPFL03list);
			    }
			    PPFL02Map.put("PPFL02list",PPFL02list);
		    }
		    ShowGoodslist=JSONArray.fromObject(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ShowGoodslist;
	}
	
	public List fllist(){
		List fllist = new ArrayList();
		showGoods dao= new showGoods(o2o);
		try {
			List PPFL01list=dao.select_pjfl01();  //查询一级分类
			if(PPFL01list != null&&PPFL01list.size() > 0){				
				for (int i=0; i < PPFL01list.size(); i++) {
					Map spfl01map = (Map<String, String>) PPFL01list.get(i);	
					String ppfl01code = (String) spfl01map.get("FLCODE");
					String ppfl01name = (String) spfl01map.get("FLNAME");

					List<Map<String, String>> PPFL02list = dao.select_pjfl02(ppfl01code);	   //查询二级分类				
					spfl01map.put("PPFL02list", PPFL02list);
					
					if(PPFL02list !=null&&PPFL02list.size()>0){
						for (int j = 0; j < PPFL02list.size(); j++) {							
							Map spfl02map = (Map) PPFL02list.get(j);								
							String ppfl02code = spfl02map.get("FLCODE").toString();
							String ppfl02name = spfl02map.get("FLNAME").toString();						

							List<HashMap<String, String>> PPFL03list = dao.select_pjfl03(ppfl02code);	  //查询三级分类	
							spfl02map.put("PPFL03list", PPFL03list);
							if(PPFL03list !=null&&PPFL03list.size()>0){
								for (int  m= 0; m < PPFL03list.size(); m++) {							
									Map spfl03map = (Map) PPFL03list.get(m);	
									String ppfl03code = spfl03map.get("FLCODE").toString();
									String ppfl03name = spfl03map.get("FLNAME").toString();

									List<HashMap<String, String>> PPFL04list = dao.select_pjfl04(ppfl03code);	  //查询四级分类	
									spfl03map.put("SPFL04List", PPFL04list);
								}
							}

						}	
					}
				}
			}
			fllist = PPFL01list;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fllist;
	}


}