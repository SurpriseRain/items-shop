package com.jlsoft.init;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.order.service.PublicZSXT;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
/**
 * @author asus
 *
 */
@Controller
@RequestMapping("/Oper_MongoDB")
public class InitMongoDB extends JLBill{
	JLTools tool = new JLTools();
	PublicZSXT zs =new PublicZSXT();
	String path = JlAppResources.getProperty("trace_url");
	String address=path+"/AddProduceCollection.json";
	@RequestMapping("/insertW_CKD")
	public Map insertW_CKD(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		try {
			//-----出库单
			if(tool.getCurConf(4)==1)
			{
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP " +
						 " FROM w_spcm " +
						 " LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 " +
						 " LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 " +
						 " where w_spcm.spcm01 in (select DISTINCT SPCM01 from w_ckdcm)";
				List list1 = queryForList(o2o, query);
				int flag = getForlist(list1,"0");
				if(flag==1){
					map.put("STATE", "1");
				}
			}
		map.put("STATE", "1");
	} catch (Exception e) {
		map.put("STATE", "0");
	}
	return map;
}
	@RequestMapping("/insertW_RKD")
	public Map insertW_RKD(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		try {
			//-----入库单
			if(tool.getCurConf(4)==1)
			{
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,(case when w_zcxx.ZCXX65='0' then '安迅物流' else ZCXX02 end ) as ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP " +
						 " FROM w_spcm " +
						 " LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 " +
						 " LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 " +
						 " where w_spcm.spcm01 in (select DISTINCT spcm01 from w_rkdcm);";
				List list1 = queryForList(o2o, query);
				int flag = getForlist(list1,"1");
				if(flag==1){
					map.put("STATE", "1");
				}
			}
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
		}
		return map;
	}
	@RequestMapping("/insertW_XSDD")
	public Map insertW_XSDD(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		try {
			//---------物流送货
			if(tool.getCurConf(4)==1)
			{
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,(case when w_zcxx.ZCXX65='0' then '安迅物流' else ZCXX02 end ) as ZCXX02,ZCXX08,ZCXX55,barcode,XSDD01" +
						 " FROM w_spcm " +
						 " LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 " +
						 " LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 " +
						 " left join w_xsdd on W_xsdd.ZTID = w_spcm.zcxx01 " +
						 " where w_spcm.spcm01 in (select DISTINCT spcm01 from w_xsddcm) " +
						 " and w_xsdd.xsdd01 in (SELECT w_djzx01 FROM w_djzx where w_djzx02='5');";
				List list1 = queryForList(o2o, query);
				int flag = getForlist(list1,"2");
				if(flag==1){
					map.put("STATE", "1");
				}
			}
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
		}
		return map;
	}
	@RequestMapping("/insertW_XSDDMJ")
	public Map insertW_XSDDMJ(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		try {
			//---------买家收货
			if(tool.getCurConf(4)==1)
			{
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,(select ZCXX02 from w_zcxx where w_zcxx.zcxx01= w_xsdd.hbid) ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP " +
						 " FROM w_spcm " +
						 " LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 " +
						 " LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 " +
						 " left join w_xsdd on W_xsdd.ZTID = w_spcm.zcxx01 " +
						 " where w_spcm.spcm01 in (SELECT DISTINCT spcm01 FROM w_xsddcm)" +
						 " and w_xsdd.xsdd01 in (SELECT w_djzx01 FROM w_djzx where  w_djzx02='6');";
				List list = queryForList(o2o, query);
				int flag = getForlist(list,"3");
				if(flag==1){
					map.put("STATE", "1");
				}
			}
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
		}
		return map;
	}
	@RequestMapping("/insertW_THDCM")
	public Map insertW_THDCM(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		try {
			//---------退货
			if(tool.getCurConf(4)==1)
			{
				String query="SELECT w_spcm.SPCM01, SPXX04, SPCM03, SPCM02, (case when w_zcxx.ZCXX65='0' then '安迅物流' else ZCXX02 end ) as ZCXX02,ZCXX08,ZCXX55,THDH,w_spcm.barcode"  +
							" FROM w_spcm " +
							" LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 " +
							" LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 " +
							"  left join w_thdcm on w_spcm.spcm01 = w_thdcm.SPCM01" +
							" where w_spcm.spcm01 in (select spcm01 from w_thdcm);";
		    	List list = queryForList(o2o, query);
		    	int flag = getForlist(list,"4");
				if(flag==1){
					map.put("STATE", "1");
				}
			}
			map.put("STATE", "1");
		} catch (Exception e) {
			map.put("STATE", "0");
		}
		return map;
	}
	public int getForlist(List list1,String type) throws Exception{
		int flag = 0;
		try {
			if(list1!=null){
				int count = 0;
				int percount = 2000;
				if(list1.size()>=10000){
					if(list1.size()/percount==0){
						count = Integer.valueOf(list1.size()/percount);
					}else{
						count = Integer.valueOf(list1.size()/percount)+1;
					}
				}else{
					if(type.equals("0")||type.equals("1")||type.equals("3")){
						zs.rk(list1,address,type);
					}else if(type.equals("2")||type.equals("4")){
						zs.ck1(list1,address,type);
					}else{
						zs.rk(list1,address,type);
					}
				}
				if(count>0){
					for(int i=0;i<count;i++){
						int listlength  = percount*i;
						List list = null; 
						if(i==count-1){
							list = list1.subList(listlength, listlength+Integer.valueOf(list1.size()%percount));
						}else{
							list = list1.subList(listlength, listlength+percount);
						}
						if(type.equals("0")||type.equals("1")||type.equals("3")){
							zs.rk(list,address,type);
						}else if(type.equals("2")||type.equals("4")){
							zs.ck1(list,address,type);
						}else{
							zs.rk(list,address,type);
						}
					}
				}
			}
			flag = 1;
		} catch (Exception ex) {
			flag = 0;
		}
		return flag;
	}
	
}

