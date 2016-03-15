package com.jlsoft.o2o.interfacepackage.V9;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jlsoft.framework.JLBill;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;

@Controller
public class V9BasicData  extends JLBill{
	@Autowired
	private V9Public v9Public;
	@Autowired
	private ManageLogs manageLogs;
	@Autowired
	private PubService pubService;
	
	/**
	 * @todo 注册对接（0待处理；1创建客户资料；2创建采购合同）
	 * @param map
	 * @param erpMap
	 * @return
	 * @throws Exception 
	 */
	public boolean registerDock(Map map,Map erpMap) throws Exception{
		boolean flag = true;
		String sql = "";
		String returnStr = "";
		JSONObject jsonObject;
		String GSLX = map.get("GSLX").toString();
		//查询单据状态
		Map ztMap = pubService.querWTDJ(5,map.get("ZCXX01").toString());
		int CLZT = Integer.parseInt(ztMap.get("CLZT").toString());
		int DJZT = Integer.parseInt(ztMap.get("DJZT").toString());
		if(DJZT==0){
			//创建客户资料
			if(CLZT == 0){
				returnStr = createKHZL(map,erpMap);
				jsonObject = JSONObject.fromObject(returnStr);
				JSONObject returnData =(JSONObject) jsonObject.get("data");
				if(returnData.getString("JL_State").equals("1")){
					map.put("ERPDM", returnData.get("JL_OrderCode"));
					//成功后更新公司对照字段值
					sql = "UPDATE W_ZCXX SET ZCXX25='"+returnData.get("JL_OrderCode")+"' WHERE ZCXX01='"+returnData.get("Order_Code")+"'";
					execSQL(o2o, sql, returnData);
					//跟新问题表状态
					int newDJZT = 0;
					if(GSLX.equals("42") || GSLX.equals("43")){
						newDJZT = 1;
					}
					pubService.updateW_WTDJ(5,map.get("ZCXX01").toString(),1,newDJZT);
				}else{
					flag = false;
					manageLogs.writeLogs(5,map.get("ZCXX01").toString(),"","管理员",0,"注册对接V9创建客户资料失败，"+returnData.get("JL_ERR").toString());
				}
			}
			//创建采购合同
			if(GSLX.equals("42") || GSLX.equals("43")){
				if(CLZT == 0 || CLZT == 1){
					returnStr = createCGHT(map,erpMap);
					jsonObject = JSONObject.fromObject(returnStr);
					JSONObject returnData =(JSONObject) jsonObject.get("data");
					if(returnData.getString("JL_State").equals("1")){
						//跟新问题表状态
						pubService.updateW_WTDJ(5,map.get("ZCXX01").toString(),2,1);
						//更新合同号、合作方式
						String CGHT = returnData.get("JL_OrderCode").toString();
						String HZFS = returnData.get("HZFS").toString();
						sql = "UPDATE W_ZCXX SET ZCXX62="+CGHT+",ZCXX63="+HZFS+" WHERE ZCXX01='"+map.get("ZCXX01").toString()+"'";
						execSQL(o2o, sql, returnData);
					}else{
						flag = false;
						manageLogs.writeLogs(5,map.get("ZCXX01").toString(),"","管理员",0,"注册对接V9生成采购合同失败，"+returnData.get("JL_ERR").toString());
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * @todo 创建客户资料
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public String createKHZL(Map map,Map erpMap) throws Exception{
		Map paramterMap = new HashMap();
		paramterMap.put("UserName", erpMap.get("UserName"));
		paramterMap.put("PassWord", erpMap.get("PassWord"));
		paramterMap.put("TYPE_DJ","POST_WLDW_CGHT_V9");
		paramterMap.put("Order_Code", map.get("ZCXX01")); //平台客户编号
		paramterMap.put("ADD_CODE", map.get("ZCXX07")); //地区代码
		paramterMap.put("NAME", map.get("ZCXX02")); //中文名称
		paramterMap.put("NAMEPY", map.get("KHPYM")); //拼音码
		paramterMap.put("CHADDRESS", map.get("ZCXX08")); //中文地址
		paramterMap.put("PHONE", map.get("SJHM")); //联系电话
		paramterMap.put("KH_BANK", ""); //开户银行
		paramterMap.put("ACCOUNT", ""); //账号
		paramterMap.put("EIN", ""); //税号
		paramterMap.put("InputTaxRate", "0.17");//进项税率
		paramterMap.put("DATE", map.get("ZCXX14")); //登记日期
		paramterMap.put("REGISTER",map.get("XTCZY01")); //登记人
		paramterMap.put("GSXX01", "0001");//公司代码
		paramterMap.put("CZY", "00019999");//账号
		String gslxName = "";
		String gslx = map.get("GSLX").toString();
		if(gslx.equals("42")){
			gslxName = "0";
		}else if(gslx.equals("43")){
			gslxName = "0";
		}else if(gslx.equals("44")){
			gslxName = "1";
		}
		paramterMap.put("type", gslxName); //公司类型名称
		//与ERP对接调用
		JSONObject jsonObject = JSONObject.fromObject(paramterMap);
		String XmlData = "XmlData="+URLEncoder.encode(jsonObject.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,erpMap.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 创建采购合同
	 * @param map
	 * @param erpMap
	 * @return
	 * @throws Exception 
	 */
	public String createCGHT(Map map,Map erpMap) throws Exception{
		Map paramterMap = new HashMap();
		paramterMap.put("UserName", erpMap.get("UserName"));
		paramterMap.put("PassWord", erpMap.get("PassWord"));
		paramterMap.put("TYPE_DJ","POST_CGHT_V9");
		paramterMap.put("Order_Code", map.get("ERPDM")); //平台客户编号
		paramterMap.put("NAME",map.get("ZCXX02"));//中文名称
		paramterMap.put("DEPARTMENT_ID", "00010101"); //部门代码
		//获取当前日期
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sdf.format(new Date());
		String endDate = DateFormatUtils.format(DateUtils.addDays(sdf.parse(curDate),3650),"yyyy-MM-dd");
		paramterMap.put("SIGN_DATE", curDate); //签订日期
		paramterMap.put("START_DATE",curDate); //合同有效期（起）
		paramterMap.put("END_DATE",endDate); //合同有效期（止）
		paramterMap.put("NOTE", ""); //备注
		paramterMap.put("MAKER", "车福"); //制单人
		paramterMap.put("MAKE_DATE", curDate); //制单日期
		paramterMap.put("SINGER", ""); //签订人名称
		paramterMap.put("COMPANY_ID", "0001"); //公司id
		paramterMap.put("SysTemCon", "23");//系统内部参数(23为自动审核,审批)
		paramterMap.put("HZFS", "3");//合作方式
		//与ERP对接调用
		JSONObject jsonObject = JSONObject.fromObject(paramterMap);
		String XmlData = "XmlData="+URLEncoder.encode(jsonObject.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,erpMap.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 创建商品分类
	 * @param map
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String createSPFL(Map map,Map erpMap) throws Exception{
		Map paramterMap = new HashMap();
		paramterMap.put("UserName", erpMap.get("UserName"));
		paramterMap.put("PassWord", erpMap.get("PassWord"));
		paramterMap.put("TYPE_DJ","ADD_SPFL");
		paramterMap.put("GoodFL_CODE",map.get("SPFL01")); //分类代码
		paramterMap.put("GoodFL_UPCODE",map.get("SPF_SPFL01")); //分类上级代码
		paramterMap.put("GoodFL_NAME",map.get("SPFL02")); //分类名称
		paramterMap.put("GoodFL_CLASS",map.get("FLJB")); //分类级次
		paramterMap.put("GoodFL_FLAG",map.get("MJBJ")); //末级标志
		paramterMap.put("GoodFL_ABC",map.get("")); //ABC标志
		//与ERP对接调用
		JSONObject jsonObject = JSONObject.fromObject(paramterMap);
		String XmlData = "XmlData="+URLEncoder.encode(jsonObject.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,erpMap.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 创建品牌
	 * @param map
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String createSPPP(Map map,Map erpMap) throws Exception{
		Map paramterMap = new HashMap();
		paramterMap.put("UserName", erpMap.get("UserName"));
		paramterMap.put("PassWord", erpMap.get("PassWord"));
		paramterMap.put("TYPE_DJ","ADD_PPB");
		paramterMap.put("Brand_NAME", map.get("PPMC")); //品牌名称
		paramterMap.put("Brand_LX", "A"); //ABC类型
		paramterMap.put("Brand_ENAME", map.get("PPYW")); //品牌英文名称
		//与ERP对接调用
		JSONObject jsonObject = JSONObject.fromObject(paramterMap);
		String XmlData = "XmlData="+URLEncoder.encode(jsonObject.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,erpMap.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 商品对接
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public boolean spxxDock(Map map) throws Exception{
		boolean flag = true;
		String sql = "";
		String returnStr = "";
		JSONObject jsonObject;
		//查询单据状态
		Map ztMap = pubService.querWTDJ(6,map.get("SPXX01").toString());
		int CLZT = Integer.parseInt(ztMap.get("CLZT").toString());
		int DJZT = Integer.parseInt(ztMap.get("DJZT").toString());
		//查询合同号
		sql = "SELECT ZCXX62 CGHT,ZCXX63 HZFS FROM W_ZCXX WHERE ZCXX01='"+map.get("ZCXX01").toString()+"'";
		Map htMap =  queryForMap(o2o,sql);
		map.put("CGHT", htMap.get("CGHT"));
		map.put("HZFS", htMap.get("HZFS"));
		if(DJZT==0){
			//新建商品
			if(CLZT == 0){
				returnStr = createSPXX(map);
				jsonObject = JSONObject.fromObject(returnStr);
				JSONObject returnData =(JSONObject) jsonObject.get("data");
				if(returnData.getString("JL_State").equals("1")){
					//跟新W_SPXXDZ值
					sql = "UPDATE W_SPXXDZ SET ERP_SPXX01="+returnData.get("JL_SPXX01")+" WHERE SPXX01="+map.get("SPXX01").toString()+"";
					execSQL(o2o, sql, returnData);
					//跟新问题表状态
					pubService.updateW_WTDJ(6,map.get("SPXX01").toString(),1,0);
				}else{
					flag = false;
					manageLogs.writeLogs(6,map.get("SPXX01").toString(),"","管理员",0,"商品对接V9创建商品失败，"+returnData.get("JL_ERR").toString());
				}
			}
			//新建价格文件
			if(CLZT == 0 || CLZT == 1){
				returnStr = createJGWJ(map);
				jsonObject = JSONObject.fromObject(returnStr);
				JSONObject returnData =(JSONObject) jsonObject.get("data");
				if(returnData.getString("JL_State").equals("1")){
					//跟新问题表状态
					pubService.updateW_WTDJ(6,map.get("SPXX01").toString(),2,1);
				}else{
					flag = false;
					manageLogs.writeLogs(6,map.get("SPXX01").toString(),"","管理员",0,"商品对接V9创建价格文件失败，"+returnData.get("JL_ERR").toString());
				}
			}
		}
		return flag;
	}
	
	/**
	 * @todo 新建商品
	 * @param map
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String createSPXX(Map map) throws Exception{
		String SPXX01 = map.get("SPXX01").toString();
		String ZCXX01 = map.get("ZCXX01").toString();
		//查询商品基础信息
		String sql = "SELECT C.ZCXX03,A.PPB01,A.JLDW01,(SELECT JLDW02 FROM W_JLDW WHERE JLDW01=A.JLDW01) JLDW02, " +
						   "A.SPXX04,A.SPFL01,A.SPXX09,A.SPXX10,A.SPXX11,(A.SPXX09*A.SPXX10*A.SPXX11) SPTJ,A.GGXH,A.SPXX12," +
						   "(SELECT BARCODE FROM W_GOODS WHERE SPXX01=A.SPXX01) BARCODE " +
				           "FROM W_SPXX A,W_SPGL B,W_ZCXX C WHERE A.SPXX01=B.SPXX01 AND B.ZCXX01=C.ZCXX01 " +
				           "AND A.SPXX01="+SPXX01+" AND B.ZCXX01='"+ZCXX01+"'";
		Map spMap = queryForMap(o2o,sql);
		//主表信息赋值
		Map paramterMap = new HashMap();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","ADD_SPXX");
		paramterMap.put("SPXX02", spMap.get("BARCODE"));//商品编码
		paramterMap.put("HSJJ", "3");//0店内码 1条形码 2流水码 3自编码
		paramterMap.put("BrandID", spMap.get("PPB01"));//品牌代码
		paramterMap.put("venderID","");//生产厂家代码（待修改）
		paramterMap.put("Unit", spMap.get("JLDW02"));//计量单位
		paramterMap.put("SysTemCon", new Integer(0));
		paramterMap.put("Model", spMap.get("GGXH"));//商品型号
		paramterMap.put("Norms", spMap.get("GGXH"));//规格代码
		paramterMap.put("GoodsName", spMap.get("SPXX04"));//商品名称
		paramterMap.put("SalesTaxRate", "0.17");//销项税率
		paramterMap.put("FactoryPrice", "0");//出厂价
		paramterMap.put("Bid", "0");//最后进价
		paramterMap.put("FixPriceFlag", "1");//限价标记
		paramterMap.put("EffectiveFlag", "1");//有效标记
		paramterMap.put("ArtNo", "");//货号
		paramterMap.put("Color", "");//颜色
		paramterMap.put("ABC", "");//ABC分类
		paramterMap.put("InputTaxRate", "0.17");//进项税率（必填）
		paramterMap.put("PlaceOrigin", "");//产地
		paramterMap.put("QualityGrade", "");//质量等级
		paramterMap.put("CodeFlag", "0");//机器码标记（必填）
		paramterMap.put("Length", spMap.get("SPXX09"));//长
		paramterMap.put("Wide", spMap.get("SPXX10"));//宽
		paramterMap.put("high", spMap.get("SPXX11"));//高
		paramterMap.put("Volume", spMap.get("SPTJ"));//体积
		paramterMap.put("Classification", spMap.get("SPFL01"));//商品分类代码
		paramterMap.put("CJLJ_Code", "");//厂家零件编号
		paramterMap.put("Introduction", "");//商品功能简介
		paramterMap.put("SPPZ01", "");//商品配置代码
		paramterMap.put("SPHS01", "");//商品颜色代码
		paramterMap.put("SPXH_Code", "");//商品型号代码
		paramterMap.put("CWJZ_Flag", "");//财务结转凭证标志
		paramterMap.put("WL_Code", "");//物料编码
		paramterMap.put("DM_Flag", "");//多码标记
		paramterMap.put("JQJX01", "");//机器件型
		paramterMap.put("SPSX", "");
		paramterMap.put("PHBJ", "0");//批号管理标记(0:不管批号;1:管批号)
		paramterMap.put("CMT", "");//串码头
		paramterMap.put("BarCode", "");//条码
		paramterMap.put("Weight", spMap.get("SPXX12"));//重量
		paramterMap.put("BZHL", "1");//包装含量		
		//查询商品属性
		List sxList = new ArrayList();
		Map sxMap = new HashMap();
		sxMap.put("SX01", "");//经营类别
		sxMap.put("SX02", "");//生产类别
		sxMap.put("SX03", "");//
		sxMap.put("SX04", "");//质量类别(对应 表ZLLB)
		sxMap.put("SX05", "");//储存条件(0,密封;1,冷藏;2,通风;3,阴凉;4,阴凉处;5,密闭)
		sxList.add(sxMap);
		paramterMap.put("SPXX_SX",sxList);
		//查询车型车系
		List cxList = new ArrayList();
		Map cxMap = new HashMap();
		cxMap.put("SPCLXX01", "");//品牌
		cxMap.put("SPCLXX02", "");//车系
		cxMap.put("SPCLXX03", "");//车型
		cxMap.put("SPCLXX04", "");//排量
		cxMap.put("SPCLXX05", "");//变速箱类型
		cxMap.put("SPCLXX06", "");//
		cxList.add(cxMap);
		paramterMap.put("SPCLXX", cxList);
		//与ERP对接调用
		JSONObject jsonObject = JSONObject.fromObject(paramterMap);
		String XmlData = "XmlData="+URLEncoder.encode(jsonObject.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
	/**
	 * @todo 创建价格文件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String createJGWJ(Map map) throws Exception{
		String sql = "";
		String SPXX01 = map.get("SPXX01").toString();
		String ZCXX01 = map.get("ZCXX01").toString();
		//主表信息赋值
		JSONObject paramterMap = new JSONObject();
		paramterMap.put("UserName", map.get("UserName"));
		paramterMap.put("PassWord", map.get("PassWord"));
		paramterMap.put("TYPE_DJ","JGWJ");
		paramterMap.put("Registrant", "");//登记人
		paramterMap.put("ContractNumber", map.get("CGHT"));//合同号
		paramterMap.put("CompanyCode", "0001");//公司信息
		paramterMap.put("SysTemCon", "23");//系统参数 23为自动审核
		//存放商品价格数据
		int HZFS = ((Integer)map.get("HZFS")).intValue();
		sql = "SELECT (SELECT ERP_SPXX01 FROM W_SPXXDZ WHERE SPXX01=A.SPXX01) ProductID," +
				"A.SPJGB05 UnPrice,A.SPJGB05 FixPrice,A.SPJGB05 DBUnPrice,A.SPJGB05 DBFixPrice," +
				"CASE WHEN "+HZFS+"=3 THEN 0 ELSE A.SPJGB05 END NowPrice,A.SPJGB05 SPJG01," +
				"0 SPJG04,0 SPJG11,0 SPJG12,0 SPJG13,0 SPJG23,0 SPJG25,0 TFJE01,0 YFLL01 " +
				"FROM W_SPJGB A WHERE A.ZCXX01='"+ZCXX01+"' AND A.SPXX01="+SPXX01+"";
		List spjgList = queryForList(o2o,sql);
		paramterMap.put("SPLIST", spjgList);
		//与ERP对接调用
		String XmlData = "XmlData="+URLEncoder.encode(paramterMap.toString(),"utf-8");
		return JLTools.sendToSync_V9(XmlData,map.get("URL").toString()+"/PubInteface/POST_SCM_DJ.do");
	}
	
}
