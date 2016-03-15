package com.jlsoft.o2o.product.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.order.service.CartManage;
import com.jlsoft.utils.DownLoad;
import com.jlsoft.utils.JlAppResources;
@Controller
@RequestMapping("/productAttach")
public class ProductAttach extends JLBill{
	@Autowired
	private ProductAttachForsrch productItem;
	@Autowired
	private CartManage myCart;
	
	/**
	 * 查询图片信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectImageNumAndSpxxInfo.action")
	public Map<String, Object> selectImageNumAndSpxxInfo(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
		String sql=
			"SELECT SPTP01, SPTP02 FROM W_SPTP WHERE SPXX01="+hm.get("SPXX01");
		List list=queryForList(o2o, sql);
		//hm=queryForMap(o2o, spSql);
		int num = 0;
		if(list==null){
			num = 0;
		}else{
		num = list.size();
		}
		hm.put("NUM", num);
		//List<Map<String, Object>> mapList=selectGoodsAttribute(XmlData);
		hm.put("tpList", list);
		return hm;
	}
	
	/**
	 * 查询商品属性
	 * @throws Exception 
	 * @Param SPXX01
	 */
	@RequestMapping("/selectGoodsAttribute.action")
	public List<Map<String, Object>> selectGoodsAttribute(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		String spxx01=cds.getField("SPXX01", 0);
		String sql=
			"SELECT A.SPFL, A.SXBH, A.SXNAME, B.SXZDM, B.SXZNAME\n" +
			"  FROM W_SPFLSX A, W_SPFLSXITEM B\n" + 
			" WHERE A.SPFL = B.SPFL\n" + 
			"   AND A.SXBH = B.SXBH\n" + 
			"   AND A.SPFL =\n" + 
			"       (SELECT X.SPFL02_CODE FROM W_SPXX X WHERE X.SPXX01 = "+spxx01+")\n" + 
			"   AND B.SXZDM in (SELECT G.SXZDM FROM W_SPSX G WHERE G.SPXX01 = "+spxx01+")";
		List<Map<String, Object>> mapList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return mapList;
	}

	/**
	 * 登录时查询筛选商品
	 * old version
	 * @throws Exception 
	 * @Param  GSXX01
	 * @param  QYBM
	 * @param  SPFL01
	 */
	@RequestMapping("/selectScreeningGoods.action")
	public List<Map<String,Object>> selectScreeningGoods(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);

		String sql=
			"SELECT G.SPXX01 SP_ID,\n" +
			"           P.SPXX02 SPCODE,\n" + 
			"           P.SPXX04 SPNAME,\n" + 
			"           M.SPJGB01 SPJG,\n" + 
			"\t\t       G.SPGL04 FBJG,\n" + 
			"\t\t       G.ZCXX01 JXS,\n" + 
			"\t\t       G.SPGL02 DTBJ,\n" + 
			"\t\t       IFNULL(M.SPJGB05, 0) LSJG,\n" + 
			"\t\t       SUM(K.CKSP04 + K.CKSP05 - K.KCXX02 + K.KCXX01) SPSL,\n" + 
			"\t\t       IFNULL(C.CXJG, 0) CXJG,\n" + 
			"\t\t       IFNULL((SELECT D.DWJG02\n" + 
			"              FROM DWJGB D, W_GSGX E\n" + 
			"             WHERE D.GSXX01 = E.ZTID\n" + 
			"               AND D.KH01 = E.HBID\n" + 
			"               AND D.SPXX01 = G.SPXX01\n" + 
			"               AND D.KH01 = '"+hm.get("GSXX01")+"'\n" + 
			"               AND D.GSXX01 = '"+hm.get("GSXX01")+"'),0)YHJG\n" + 
			"\t\t  FROM W_SPGL G\n" + 
			"\t\t      LEFT JOIN  (SELECT B.GSXX01, B.SPXX01, B.CXJG, B.HDLX\n" + 
			"\t\t          FROM W_CXZXB B\n" + 
			"\t\t           WHERE now() >= B.BEGAINDATE\n" + 
			"\t\t           AND now() <= B.ENDDATE\n" + 
			"\t\t           AND B.HDLX = 1) C\n" + 
			"\t\t 	ON G.ZCXX01 = C.GSXX01 AND G.SPXX01 = C.SPXX01,\n" +
			"\t\t       W_SPJGB M,\n" + 
			"\t\t       W_SPXX P,\n" + 
			"\t\t       W_KCXX K,CK   \n" + 
			"\t\t WHERE G.SPXX01 = M.SPXX01\n" + 
			"\t\t   AND G.ZCXX01 = M.ZCXX01\n" + 
			"\t\t   AND G.SPXX01 = P.SPXX01\n" + 
			" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
			"\t\t   AND G.SPGL12 IN ('1', '3')\n" + 
			"\t\t   AND K.ZCXX01 = G.ZCXX01\n" + 
			"\t\t   AND K.SPXX01 = G.SPXX01\n" + 
			"\t\t   AND G.SPGL02 = 2\n" + 
			//"\t\t   AND P.SPFL01_CODE = SUBSTR("+hm.get("SPFL01")+",1,2)\n" +
			"\t\t GROUP BY G.SPXX01,\n" + 
			"\t\t          P.SPXX02,\n" + 
			"\t\t          P.SPXX04,\n" + 
			"\t\t          G.SPGL04,\n" + 
			"\t\t          G.ZCXX01,\n" + 
			"\t\t          G.SPGL02,\n" + 
			"\t\t          M.SPJGB05,\n" + 
			"\t\t          C.CXJG,\n" + 
			"\t\t          M.SPJGB01,\n" + 
			"\t\t          G.SPGL03\n" + 
			"  \t\t  ORDER BY SPGL03"+ 
			"\t\t   LIMIT 5\n" ;
		System.out.println(sql);
		List<Map<String, Object>> spList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return spList;
	}
	
	/**
	 * 登录时区分权限查询商品
	 * @wuquan
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectScreeningGoodsMZ.action")
	public List<Map<String,Object>> selectScreeningGoodsMZ(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
		String sql=
			
		  "			SELECT A.SPXX01 SP_ID, P.SPXX02 SPCODE, P.SPXX04 SPNAME, \n"+
		  "\t\t   			A.SPGL04 FBJG,SUM(K.CKSP04 +K.CKSP05 - K.KCXX02 + K.KCXX01) SPSL, \n"+
		  "\t\t   			A.ZCXX01 JXS, A.SPGL02 DTBJ,A.CKSP12, IFNULL(M.SPJGB05, 0) LSJG, M.SPJGB01 SPJG \n"+
		  "\t\t 	 FROM W_SPGL A, W_SPQY B,W_KCXX K,CK,W_SPXX P, W_SPJGB M \n"+
		  "\t\t 	WHERE  A.ZCXX01 = B.JXS \n"+
		  "\t\t 	AND A.SPXX01=P.SPXX01 \n"+
		  "\t\t 	AND A.SPXX01 = K.SPXX01 \n"+
		  "\t\t 	AND A.ZCXX01 = M.ZCXX01 \n"+
		  "\t\t 	AND A.SPXX01 = M.SPXX01 \n"+
		  " AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
		  "\t\t 	AND A.ZCXX01 = "+ hm.get("ztid") +"\n" +
		  "\t\t 	AND A.SPGL12 IN ('1', '3') \n"+
		  "\t\t 	AND A.SPXX01 = B.SPID \n"+
		  "\t\t		AND M.SPJGB01 <> 0 \n"+
		  "\t\t 	AND B.QYDM LIKE SUBSTR("+ hm.get("qydm")+ ", 1, 6) || '%' \n"+
		  "\t\t 	AND EXISTS (SELECT 1 "+
		  "\t\t        FROM W_CKQX C   "+
		  "\t\t      WHERE C.ZTID = K.ZCXX01 "+
		  "\t\t         AND C.CK01 = K.CK01 "+
		  "\t\t        and C.GSID = '"+ hm.get("hbid")+"')"+
		  //属性
		  "\t\t     AND EXISTS (SELECT 0 FROM W_SPSXQX Z WHERE Z.HBID='"+ hm.get("hbid")+"'"+
		  "\t\t    AND Z.ZTID=A.ZCXX01 AND A.CKSP12=Z.CKSP12)     "+
		  "\t\t  AND EXISTS (SELECT 1 FROM W_FLPPQX R, W_GSGX H  WHERE R.ZTID = H.ZTID   \n"+
		  "\t\t   			 AND R.HBID = H.HBID  AND R.PPB01 = P.PPB01 \n"+
		  "\t\t   			 AND R.SPFL01 = SUBSTR(P.SPFL01, 1, 4) \n"+
		  "\t\t	             AND R.ZTID = A.ZCXX01 \n"+
		  "\t\t	          AND R.FLPP02 = 8 \n"+
		  "\t\t         AND H.HBID = '"+ hm.get("hbid")+"'"+
		  "\t\t		 )  LIMIT 5 \n"+
		 "GROUP BY A.SPXX01, P.SPXX02, P.SPXX04,A.SPGL04, A.CKSP12, A.ZCXX01, A.SPGL02, M.SPJGB05 , M.SPJGB01, A.SPGL03 \n"+
		 "\t\t   ORDER BY A.SPGL03";
		System.out.println(sql);
		List<Map<String, Object>> spList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return spList;
	}
	
	
	/**
	 * 不登陆时筛选商品
	 * 
	 */
	@RequestMapping("/selectScreeningGoodsNotLogin.action")
	public List<Map<String,Object>> selectScreeningGoodsNotLogin(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		JSONArray jsonArray=JSONArray.fromObject(XmlData);
		Map<String, Object> hm=(Map<String, Object>)jsonArray.get(0);
		String sql=
			"SELECT G.SPXX01 SP_ID,\n" +
			"\t       P.SPXX02 SPCODE,\n" + 
			"\t       P.SPXX04 SPNAME,\n" + 
			"\t       M.SPJGB05 SPJG,\n" + 
			"\t       G.SPGL04 FBJG,\n" + 
			"\t       G.ZCXX01 JXS,\n" + 
			"\t       G.SPGL02 DTBJ,\n" + 
			"\t       G.CKSP12 ,\n" + 
			"\t       IFNULL(M.SPJGB05, 0) LSJG,\n" + 
			"\t       SUM(K.CKSP04 + K.CKSP05 - K.KCXX02 + K.KCXX01) SPSL,\n" + 
			"\t       0 CXJG,\n" + 
			"\t       0 YHJG\n" + 
			"\t  FROM W_SPGL G, W_SPJGB M, W_SPXX P, W_KCXX K,CK\n" + 
			"\t WHERE G.SPXX01 = M.SPXX01\n" + 
			"\t   AND G.ZCXX01 = M.ZCXX01\n" + 
			"\t   AND G.SPXX01 = P.SPXX01\n" + 
			" AND K.CK01 = CK.CK01 AND CK.ck09='0'" +
			"\t   AND G.SPGL12 IN ('1', '3')\n" + 
			"\t   AND K.ZCXX01 = G.ZCXX01\n" + 
			"\t   AND K.SPXX01 = G.SPXX01\n" + 
			"\t   AND G.SPGL02 = 2\n" + 
			"\t   AND P.SPFL01_CODE = '10'\n" + 
			"\t   LIMIT 5\n" + 
			"\t GROUP BY G.SPXX01,\n" + 
			"\t          P.SPXX02,\n" + 
			"\t          P.SPXX04,\n" + 
			"\t          G.SPGL04,\n" + 
			"\t          G.ZCXX01,\n" + 
			"\t          G.CKSP12,\n" + 
			"\t          G.SPGL02,\n" + 
			"\t          M.SPJGB05,\n" + 
			"\t          M.SPJGB01,\n" + 
			"\t          G.SPGL03\n" + 
			"\t ORDER BY SPGL03";
		System.out.println(sql);
		List<Map<String, Object>> spList=(List<Map<String,Object>>)queryForList(o2o, sql);
		return spList;
	}
	/**
	 * 区域代理商
	 * @throws Exception 
	 */
	@RequestMapping("/selectAreaAgents.action")
	public Map<String, Object> selectAreaAgents(String XmlData) throws Exception{
		cds =new DataSet(XmlData);
		Map<String, Object> mapPage=paging(Integer.parseInt(cds.getField("size", 0)), Integer.parseInt(cds.getField("currentPage", 0)), Integer.parseInt(cds.getField("sumPage", 0)));
		String sql=
			"  SELECT S.* \n" + 
			"          FROM (SELECT DISTINCT A.ZCXX01,\n" + 
			"                                B.ZCXX02 GSMC,\n" + 
			"                                B.ZCXX03 FRDB,\n" + 
			"                                B.ZCXX06 MOBILE,\n" + 
			"                                B.ZCXX31 KHDJ,\n" + 
			"                                B.ZCXX32 LXR,\n" + 
			"                                B.ZCXX33 PHONE\n" + 
			"                  FROM W_ZTFLPP A, W_ZCXX B\n" + 
			"                 WHERE A.ZCXX01 = B.ZCXX01\n" + 
			"                   AND A.TYBJ = 1) S\n" + 
			"         WHERE LIMIT("+mapPage.get("minPage")+","+cds.getField("size", 0)+")\n" ;
		List<Map<String, Object>> list=queryForList(o2o, sql);
		for (Map<String, Object> map : list) {
			map.put("brandList", selectAreaAgentsBrand(map.get("ZCXX01").toString()));
		}
		mapPage.put("list", list);
		return mapPage;
	}
	/**
	 * @param -user     --用户信息
	 * @param -ZCXX01   --商品所属的供应商ID
	 * @param -SPXX01   --商品编码
	 * @param -QYDM     --地区信息
	 */
	public Map<String, Object> selectProductFlowProcess(String XmlData){
		Map<String, Object> hm =new HashMap<String, Object>();
		try {
			cds=new DataSet(XmlData);
			String userInfo=cds.getField("user", 0);
			JSONArray user=new JSONArray();
			if(userInfo!=null){
				//登录
				user=JSONArray.fromObject(userInfo);
				selectProductInfo(XmlData);
			}else{
				//未登录
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	/**
	 * @param user   --用户信息
	 * @param ZCXX01 --用户名称
	 * @param SPXX01 --商品编码
	 * @param QYDM   --地区信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectProductInfo.action")
	public Map<String, Object> selectProductInfo(String XmlData){
		Map<String, Object> hm=new HashMap<String, Object>();
		try {
			cds=new DataSet(XmlData);
			String userInfo=cds.getField("user", 0);
			JSONObject user=new JSONObject();
			//商品赛选
			List<Map<String, Object>> creenMap=new ArrayList<Map<String,Object>>();
			if(userInfo!=null&&!"".equals(userInfo)){
				user=JSONObject.fromObject(userInfo);
				//查询商品信息
				hm=(Map<String, Object>) productItem.selectProductItem(XmlData);
				if(hm!=null){
					JSONObject json= new JSONObject();
					JSONArray cartParam= new JSONArray();
					json.put("ZTID", cds.getField("ZCXX01", 0));
					json.put("SPXX01", cds.getField("SPXX01", 0));
					json.put("ZCXX01", user.get("ZCXX01"));
					cartParam.add(json);
					//查询购物车里该商品的数量
					Map<String, Object> cartNum=myCart.selectGoodsNumForId(cartParam.toString());
					hm.putAll(cartNum);
					//商品限定数量 库存,最大限购数量,
					List<Integer> num = new ArrayList<Integer>();
					num.add(Integer.parseInt(hm.get("SPIMGURL").toString()));
					num.add(Integer.parseInt(hm.get("MAXNUM").toString()));
					//最大限购数量
					hm.put("MAXBUYNUM",getMinNum(num)-Integer.parseInt(cartNum.get("NUM").toString()));
					//TODO
					//计算商品积分
					if(hm.get("SCJFBJ")!=null){
						int scjfbj=Integer.parseInt(hm.get("SCJFBJ").toString());
						if(scjfbj==1){
							//积分转换率
							int jfhl=Integer.parseInt(JlAppResources.getProperty("JFGZ"));
							//积分数量
							hm.put("JFNUM", (Double.parseDouble(hm.get("SPJG").toString())/jfhl));
						}
					}
					//查询该商品的促销执行表
					hm=selectCXInfo(hm,XmlData);
					//商品限购数量
					//TODO
					//登录时商品赛选
					creenMap=productItem.selectScreeningGoods(hm,user);
				}else{
					//不存在该商品或者未上架
					//TODO
				}
			}else{
				//未登录
				Map<String, Object> spNotMap=productItem.selectProductForNotLogin(XmlData);
				if(spNotMap!=null){
					hm.putAll(spNotMap);
				}
				hm.putAll(spNotMap);
				//不登陆时商品赛选
				creenMap = productItem.selectScreeningGoodsNotLogin(hm);
			}
			//商品图片
		//	Map<String, Object> imageMap = selectImageNumAndSpxxInfo(XmlData);
			//查询门店或者地区
			/*
			List<Map<String, Object>> dqxxList= productItem.selectDqxxForCk(cds.getField("ZTID", 0).toString());
			if(dqxxList!=null){
				hm.put("dqxxList", dqxxList);
			}
			*/
			Map<String, Object> imageMap= productItem.selectProductInfoImages(hm);
			if(imageMap!=null){
				hm.put("imgMap", imageMap);
			}
			if(creenMap !=null){
				hm.put("creenList",creenMap);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	/**
	 * 查询促销商品信息
	 * @param hm
	 * @return
	 */
	public Map<String, Object> selectCXInfo(Map<String, Object> hm,String XmlData){
		Map<String, Object> cxInfo=new HashMap<String, Object>();
			cxInfo=productItem.selectProductCXInfo(XmlData);
		try {
			if(cxInfo!=null&&cxInfo.size()>0){
				//活动开始时间
				String begainDate= cxInfo.get("BEGAINDATE").toString();
				//活动结束时间
				String endDate=cxInfo.get("endDate").toString();
				//数据库系统时间
				String sysdate=cxInfo.get("SYSTEMDATE").toString();
				//剩余活动数量
				int surplus=Integer.parseInt(cxInfo.get("HDSL").toString())-Integer.parseInt(cxInfo.get("YSSL").toString());
				//比较活动开始时间与系统时间，如果活动时间小于系统时间，返回true
				boolean flag=false;
				try {
					flag = productItem.compareDate(begainDate, sysdate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//活动类型
				int activitNo=Integer.parseInt(cxInfo.get("HDLX").toString());
				//活动赠送积分
				if(cxInfo.get("JFNUM")==null){
					cxInfo.put("JFNUM", "无");
				}
				//限时促销
				if(activitNo == 1){
				}
				//满赠
				else if(activitNo == 2){
					//满赠促销
					Map<String, Object> mzInfo=productItem.selectMZCX(cxInfo,hm);
						if(mzInfo!=null){
							hm.putAll(mzInfo);
							//查询相关的活动商品
							List<Map<String, Object>> hdsp=(List<Map<String,Object>>)mzInfo.get("hdsp");
							//查询购物车相关商品的集合
							//hm.putAll(productItem.selectAboutCXCart(hdsp));
						}
				}
				//时间计算
				SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
				Calendar beginCal= Calendar.getInstance();
				beginCal.setTime(myfmt.parse(begainDate));
				Calendar endCal=Calendar.getInstance();
				endCal.setTime(myfmt.parse(endDate));
				long time= 0;
				//活动开始时间大于系统时间  则表示活动即将开始
				if(flag==false){
					//活动即将开始
					cxInfo.put("hdzt", "1");
					//剩余活动时间
					time=(beginCal.getTime().getTime()-System.currentTimeMillis())/1000;
				}
				//如果库存数量已经变为0，显示活动售罄
				else if(activitNo!=2&&surplus<=0||Integer.parseInt(hm.get("SPIMGURL").toString())<=0){
					cxInfo.put("hdzt", "3");
					
				}else{
					//活动进行中
					cxInfo.put("hdzt", "2");
					if(activitNo!=2){
						hm.put("CXJG", cxInfo.get("CXJG"));
					}
					time=(endCal.getTime().getTime()-System.currentTimeMillis())/1000;
					/*
					List<Integer> num= new ArrayList<Integer>();
					int xdsl=Integer.parseInt(hm.get("MAXBUYNUM").toString());
					num.add(xdsl);
					num.add(Integer.parseInt(cxInfo.get("HYXL").toString()));
					int cartNum=Integer.parseInt(hm.get("NUM").toString());
					int maxNum=getMinNum(num)-cartNum;
					if(maxNum>=0){
						//计算购物车改商品的数量
						hm.put("MAXBUYNUM",maxNum);
					}else{
						hm.put("MAXBUYNUM", 0);
					}*/
				}
				long day = time/(24*3600);
				long hours =time%(24*3600)/3600;
				long minute=time%(60*60)/60;
				long second=time%60/60;
				hm.put("day", day);
				hm.put("hours", hours);
				hm.put("minute", minute);
				hm.put("second", second);
			}else{
				String jg=hm.get("YHJG").toString();
				int yhjg=Integer.parseInt(jg);
				if(yhjg>0){
					hm.put("SPJG", jg);
				}else if(Integer.parseInt(hm.get("DTBJ").toString()) == 2){
					hm.put("SPJG", hm.get("FBJG").toString());
				}
			}
			if(cxInfo!=null){
				hm.putAll(cxInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	/**
	 * @param hdzt --活动状态
	 * @return
	 */
	@RequestMapping("/selectHDCXTime.action")
	public Map<String, Object> selectHDCXTime(String XmlData){
		Map<String, Object> hm= new HashMap<String, Object>();
		try {
			cds = new DataSet(XmlData);
			int hdzt=Integer.parseInt(cds.getField("hdzt", 0));
			Map<String, Object> cxMap=productItem.selectProductCXInfo(XmlData);
			SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			Calendar beginCal= Calendar.getInstance();
			  beginCal.setTime(myfmt.parse(cxMap.get("BEGAINDATE").toString()));
			Calendar endCal=Calendar.getInstance();
			  endCal.setTime(myfmt.parse(cxMap.get("ENDDATE").toString()));
			//数据库系统时间
			Calendar sysdate=Calendar.getInstance();
			  sysdate.setTime(myfmt.parse(cxMap.get("SYSTEMDATE").toString()));
			 boolean flag=productItem.compareDate(myfmt.format(beginCal.getTime()).toString(), myfmt.format(sysdate.getTime()).toString());
			 long time= 0;
			//活动即将开始
			if(flag==false){
				time=(beginCal.getTime().getTime()-sysdate.getTime().getTime())/1000;
				hm.put("hdzt", 1);
			}//活动进行中
			else{
				time=(endCal.getTime().getTime()-sysdate.getTime().getTime())/1000;
				if(time<=0){
					hm.put("hdzt", 3);
				}else{
					hm.put("hdzt", 2);
				}
			}
			long day = time/(24*3600);
			long hours =time%(24*3600)/3600;
			long minute=time%(60*60)/60;
			long second=time%60/60;
			hm.put("day", day);
			hm.put("hours", hours);
			hm.put("minute", minute);
			hm.put("second", second);
			hm.putAll(cxMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	/**
	 * 查询区域代理品牌
	 * @return
	 */
	public  List<Map<String, Object>> selectAreaAgentsBrand(String ZCXX01){
		String sql=
			"SELECT A.PPB02 || A.SPFL02 FLPP\n" +
			"  FROM W_ZTFLPP A\n" + 
			" WHERE A.ZCXX01 = "+ZCXX01+"\n" + 
			"   AND A.TYBJ = 1";
		List<Map<String, Object>> brandList=(List<Map<String, Object>>)queryForList(o2o, sql);
		return brandList;
	}
	
	public Map<String, Object> paging(int size,int currentPage,int sumPage){
		Map<String, Object> map=new HashMap<String, Object>();
		int minPage=(currentPage-1)*size;
		int maxPage=currentPage*size;
		if(sumPage==-1){
			int sum=selectSumPage();
			if(sum%size>0){
				sumPage=sum/size+1;
			}else{
				sumPage=sum/size;
			}
			map.put("sumPage", sumPage);
			map.put("sum", sum);
		}else{
			map.put("sumPage", sumPage);
		}
		map.put("minPage", minPage);
		map.put("maxPage", maxPage);
		return map;
	}
	
	public int selectSumPage(){
		String sql=
			"SELECT COUNT(1)\n" +
			"  FROM (SELECT DISTINCT A.ZCXX01,\n" + 
			"                        B.ZCXX02 GSMC,\n" + 
			"                        B.ZCXX03 FRDB,\n" + 
			"                        B.ZCXX06 MOBILE,\n" + 
			"                        B.ZCXX31 KHDJ,\n" + 
			"                        B.ZCXX32 LXR,\n" + 
			"                        B.ZCXX33 PHONE\n" + 
			"          FROM W_ZTFLPP A, W_ZCXX B\n" + 
			"         WHERE A.ZCXX01 = B.ZCXX01\n" + 
			"           AND A.TYBJ = 1)";
		int sum=queryForInt(o2o, sql);
		return sum;
	}
	
	//获取数字中最小的数字
    public int getMinNum(List<Integer> num){
	  return Collections.min(num);
    }
    
	@RequestMapping("/updateDownLoad.action")
	public Map<String, Object> updateDownLoad(String XmlData,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = null;
		try {
			cds = new DataSet(XmlData);
			map = new HashMap<String, Object>();
			// 文件下载的路径
			map.put("uploadPath", JlAppResources.getProperty("WLFILEPATH"));
			// 下载的文件名称
			String fileName=JlAppResources.getProperty("WLFILENAME");
			map.put("realName", fileName);
			map.put("storeName", fileName);
			map.put("contentType", "application/octet-stream");
			DownLoad.downLoad(request, response, map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
}
