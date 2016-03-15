package com.jlsoft.o2o.interfacepackage.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
@RequestMapping("/MemberShop")
public class MemberShop extends JLBill {
	public MemberShop(JdbcTemplate o2o){
		this.o2o = o2o;
	}
	public MemberShop(){
		
	}
	/**
	 * @todo 配件品牌查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getJson/searchPPB.action")
	public Map searchPPB(String XmlData) throws Exception {
		Map map = new HashMap();
		cds = new DataSet(XmlData);
		List arrList = new ArrayList();
		String pptp01 = cds.getField("pptp01", 0);
		String zcxx01 = cds.getField("ZCXX01", 0);
		Map resultMap = new HashMap();
		String querySql = "";
		//NineDragon 2015/11/17 修改以W_SPXX 为主表查询 店铺的品牌图片
		if(!"null".equals(zcxx01) && !"".equals(zcxx01)){
			querySql = "SELECT P.PPB01 ppb01, P.PPB02 ppb02, P.PPB06 ppb06, T.PPTP02 PPTP02 "
					 + "FROM W_SPXX S "
					 + "LEFT JOIN W_SPGL G ON S.SPXX01 = G.SPXX01 "
					 + "LEFT JOIN PPB P ON S.PPB01 = P.PPB01  "
					 + "LEFT JOIN W_PPTP T ON P.PPB01 = T.PPB01  "
					 + "WHERE G.ZCXX01 = '" + zcxx01 + "' "
					 + "AND T.PPTP01 = 0 "
					 + "AND P.PPB04 = 1 "
					 + "AND P.YXBJ = 1 "
			 		 + "GROUP BY P.PPB01 "
			 		 + "ORDER BY P.PPB06";
		} else {
			querySql = "SELECT t1.ppb01,t1.ppb02,t1.ppb06, t2.PPTP02 FROM PPB t1 left join  W_PPTP t2  on  t1.ppb01=t2.ppb01 "
					 + " where t2.pptp01='" + pptp01 + "' and t1.ppb04 = 1 and YXBJ = 1 order by t1.ppb06";
				     //" where t2.pptp01="+pptp01+" or t2.pptp01 is null ";
		}
		
		List ppbList=queryForList(o2o, querySql);
		for (int i = 0; i < ppbList.size(); i++) {
			HashMap<String, Object> rowMap=(HashMap<String, Object>)ppbList.get(i);
			String ppb06=(String) rowMap.get("ppb06");
			
			boolean isExists=false;
			int isExistIndex = 0;
			for(int j=0;j<arrList.size();j++){
				HashMap<String, Object> rowMapNew=(HashMap<String, Object>)arrList.get(j);
				String ppb06New=(String) rowMapNew.get("code");
				if(ppb06.equals(ppb06New)){
					isExists=true;
					isExistIndex=j;
					break;
				}
			}
			if(isExists){
				Map isExistsMap=(Map)arrList.get(isExistIndex);
				List existlist=(List) isExistsMap.get("list");
				existlist.add(rowMap);
			}else{
				Map<String,Object> sortMap=new HashMap<String,Object>();
				sortMap.put("code", ppb06);
				List addList=new ArrayList();
				addList.add(rowMap);
				sortMap.put("list",addList );
				arrList.add(sortMap);
			}
		}
		  
		resultMap.put("data", arrList);
		return resultMap;
	}
}
