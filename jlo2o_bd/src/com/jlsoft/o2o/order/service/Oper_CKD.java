package com.jlsoft.o2o.order.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.Request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.framework.dataset.JSONDataSet;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.interfacepackage.V9.V9DHD;
import com.jlsoft.o2o.interfacepackage.V9.V9RKD;
import com.jlsoft.o2o.interfacepackage.jlinterface.GopInterface;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
import com.jlsoft.utils.PubGetBH;
/**
 * 出库单
 * @author asus
 *
 */
@Controller
@RequestMapping("/Oper_CKD")
public class Oper_CKD extends JLBill{
	@Autowired
	private GopInterface gopInterface;
	@Autowired
	private PubGetBH pubGetBH;
	@Autowired
	private ManageLogs manageLogs;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9DHD v9DHD;
	@Autowired
	private V9RKD v9RKD;
	@Autowired
	private KmsService kmsService;
	JLTools tool = new JLTools();
	PublicZSXT zs =new PublicZSXT();
	String path = JlAppResources.getProperty("trace_url");
	/**
	 * 出库单
	 * @author asus
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@RequestMapping("/insertW_CKD")
	public Map insertW_CKD(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception{
		XmlData= JLTools.unescape(XmlData);
			cds=new DataSet(XmlData);
			Map map=new HashMap();
			String YWDH="";
			String CZR="";
			try {
			String CKD=cds.getField("CKD", 0); //获取明细list值
			String BZ=cds.getField("BZ", 0);
			String SHCK=cds.getField("SHCK", 0);
			String CKDLX=cds.getField("CKDLX", 0);
			String flaghcl=cds.getField("flaghcl", 0);
			String cklx = cds.getField("CKLX", 0);
			String CKR = cds.getField("CKR", 0);
			String CKTYPE = cds.getField("CKTYPE", 0); // 选择实体仓库的类型
			String SHGS = "";
			JSONObject  jasonObject = JSONObject.fromObject(CKD);
			Map ckdMap = (Map)jasonObject;
			//获取出库单号
			String CKDH="CK"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_CKD",1),10);
			YWDH=CKDH;
			String ZCXX01=ckdMap.get("ZCXX01").toString();
			CZR=ZCXX01;
			String CKSJ=ckdMap.get("DRSJ").toString();
			String CKDITEM= ckdMap.get("CKDITEM").toString();
			cds=new DataSet(CKDITEM);
			String barcodeList = cds.getField("barcodeList", 0);
			JSONArray list = JSONArray.fromObject(barcodeList);
			if(!("").equals(flaghcl) && flaghcl.equals("flaghcl")){
				String flag = ckdmList(list);
				if(!flag.equals("")){
					map.put("returnlist",flag);
					map.put("STATE","1");
					return map;
				}
			}
			if("".equals(SHCK)){
				String ckSql = "SELECT A.CK01 FROM W_GSCK A LEFT JOIN CK B ON A.CK01 = B.CK01 WHERE A.ZCXX01 = '" + ZCXX01 + "' AND B.CK09 = 0 AND B.TYPE = 0 ";
				Map xnCkMap = queryForMap(o2o, ckSql);
				SHCK = xnCkMap.get("CK01").toString();
				SHGS = ZCXX01;
			} else {
				String shgsSql = "";
				if("1".equals(CKTYPE)){
					shgsSql = "SELECT A.ZCXX01 FROM W_GSCK A LEFT JOIN CK B ON A.CK01 = B.CK01 WHERE A.CK01 = '" + SHCK + "' AND B.CK09 = 0 AND B.TYPE = 1 AND A.ZCXX01 = B.GSXX01";
				} else {
					shgsSql = "SELECT A.ZCXX01 FROM W_GSCK A LEFT JOIN CK B ON A.CK01 = B.CK01 WHERE A.CK01 = '" + SHCK + "' AND B.CK09 = 0 AND B.TYPE = 0";
				}
				Map shgsMap = queryForMap(o2o, shgsSql);
				SHGS = shgsMap.get("ZCXX01").toString();
			}
			//出库主表
			String ckdSql="INSERT INTO W_CKD(CKDH,ZCXX01,SHCK,CKR,CKSJ,BZ,SHGS) VALUES ('"+CKDH+"','"+ZCXX01+"','" + SHCK + "','" + CKR + "','"+CKSJ+"','" + BZ + "','" + SHGS + "')";
			Map row = getRow(ckdSql, null, 0);
			int i=execSQL(o2o, ckdSql, row); 
			
			//导入商品串码主表 (屏蔽掉)
			/**int SPCMDR01=PubFun.updateWBHZT(o2o,"W_SPCMDR",1);
			String spcmSql = "insert into W_SPCMDR(ZCXX01,SPCMDR01,SBM,HYGLM,WZM,DRSL,DRLX,DRRQ) " +
					             "values ('"+ZCXX01+"','"+SPCMDR01+"','"+ckdMap.get("CompanyCode")+"','"+ckdMap.get("GuildCode")+"','"+ckdMap.get("PositionCode")+"','"+ckdMap.get("TraceNum")+"','"+ckdMap.get("FileType")+"','"+ckdMap.get("DRSJ")+"')";
			Map rows=getRow(spcmSql, null, 0);
			execSQL(o2o, spcmSql, rows);*/
			for(int j=0;j<list.size();j++){
				Map ckcmMap=(Map) list.get(j);
				
				//商品串码记录表
//				String sql = "insert into W_SPCMJLB(ZCXX01,DJBH,DJLX,spxx01,barcode,spcm01,cmclsj,cmclry,ckqydm,ckqymc,cmzt) " +
//				"values ('"+ZCXX01+"','"+CKDH+"',3,'"+ckcmMap.get("SPXX01")+"','"+ckcmMap.get("barcode")+"','"+ckcmMap.get("spcm01")+"','"+ckcmMap.get("cmclsj")+"','"+ckcmMap.get("cmclry")+"','"+ckcmMap.get("ckqydm")+"','"+ckcmMap.get("ckqymc")+"','"+ckcmMap.get("cmzt")+"')";
//				Map row1=getRow(sql, null, 0);
//				execSQL(o2o, sql, row1);
				//判断是否是生成企业导入，如果是则需要插入W_SPCM表
				String sql = "SELECT COUNT(0) FROM W_SPCM WHERE ZCXX01='"+ZCXX01+"' AND SPXX01="+ckcmMap.get("SPXX01")+" AND SPCM01='"+ckcmMap.get("spcm01")+"'";
				int cmNm = queryForInt(o2o,sql);
				if(cmNm == 0){
					if(ckcmMap.get("spcm01").toString().substring(ckcmMap.get("spcm01").toString().length()-2, ckcmMap.get("spcm01").toString().length()).equals("00"))
					{
						String ckdcmSql="INSERT INTO W_CKDCM(CKDH,SPXX01,SPCM01,BARCODE,PCH,XLH) " +
								"VALUES('"+CKDH+"','"+ckcmMap.get("SPXX01")+"','"+ckcmMap.get("spcm01")+"','"+ckcmMap.get("barcode")+"'" +
										",'"+ckcmMap.get("pc")+"','"+ckcmMap.get("xlh")+"')";
								Map row2 = getRow(ckdcmSql, null, 0);
								execSQL(o2o, ckdcmSql, row2); 
						
						sql = "INSERT INTO W_SPCM(ZCXX01,SPXX01,SPCM01,BARCODE,SPCM02,SPCM03,SPCM04) " +
								 "VALUES('"+ZCXX01+"',"+ckcmMap.get("SPXX01")+",'"+ckcmMap.get("spcm01")+"','"+ckcmMap.get("barcode")+"',now(),'"+ckcmMap.get("pc")+"','"+ckcmMap.get("xlh")+"')";
								Map row3=getRow(sql, null, 0);
								execSQL(o2o, sql, row3);
					}else
					{
						String query="SELECT SPXX01,barcode,curCode  from w_spcmbz where parentCode =  '"+ckcmMap.get("spcm01")+"' ";
						List  cmlist = queryForList(o2o,query);
						for(int a=0;a<cmlist.size();a++)
						{
							Map cmMap =(Map) cmlist.get(a);
							String spcm01 = (String) cmMap.get("curCode");
							String barcode = (String) cmMap.get("barcode");
							//String spxx01 = (String) cmMap.get("SPXX01");
							String xlh = "";
							String pc = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",2)+1, JLTools.getStrSplitNum(spcm01,"(",3));
							if(JLTools.getStringRepeatShowNum(spcm01,"(") == 4){
								xlh = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",3)+1, JLTools.getStrSplitNum(spcm01,"(",4));
							}
							
							String ckdcmSql="INSERT INTO W_CKDCM(CKDH,SPXX01,SPCM01,BARCODE,PCH,XLH) " +
									"VALUES('"+CKDH+"','"+cmMap.get("SPXX01")+"','"+spcm01+"','"+barcode+"'" +
											",'"+ckcmMap.get("pc")+"','"+ckcmMap.get("xlh")+"')";
									Map row2 = getRow(ckdcmSql, null, 0);
									execSQL(o2o, ckdcmSql, row2); 
									
							sql = "INSERT INTO W_SPCM(ZCXX01,SPXX01,SPCM01,BARCODE,SPCM02,SPCM03,SPCM04) " +
									 "VALUES('"+ZCXX01+"',"+cmMap.get("SPXX01")+",'"+spcm01+"','"+barcode+"',now(),'"+pc+"','"+xlh+"')";
									Map row3=getRow(sql, null, 0);
									execSQL(o2o, sql, row3);
						}
					}
				}
			}
		
			String spcmList=cds.getField("spcmList", 0);
			JSONArray  list2 = JSONArray.fromObject(spcmList);
			for(int j=0;j<list2.size();j++){
				Map ckcmMap=(Map) list2.get(j);
				String ckditemSql="INSERT INTO W_CKDITEM(CKDH,SPXX01,CKSL,RKSL,BZ) " +
				"VALUES('"+CKDH+"','"+ckcmMap.get("SPXX01")+"','"+ckcmMap.get("num")+"',0,'"+BZ+"')";
				Map row3 = getRow(ckditemSql, null, 0);
				execSQL(o2o, ckditemSql, row3);
			}
		
			if(JLTools.getCurConf(1) == 1){
				Map maps = gopInterface.transCKD(XmlData,CKDH);
				if(!maps.get("status").equals("0")){
					//错误后写入状态
					//gopInterfaceMassage(maps);
					/********操作日志记录Start*/
					Map errorMap=new HashMap();
					errorMap.put("DJLX", "0");//单据类型（默认为0）
					errorMap.put("YWDH", CKDH);//业务单号
					errorMap.put("DFHM", "");//对方单号（默认为空）
					errorMap.put("CZR", ZCXX01);//操作人
					errorMap.put("RZZT", "0");//日志状态（0失败;1成功）
					errorMap.put("ERROR", maps.get("message"));//错误信息
					manageLogs.writeLogs(errorMap);
					/**********操作日志记录end*/
					map.put("state", "error");
					return map;
					//throw new Exception(maps.get("message").toString());
				}
			}
			// 对接ERP系统
			Map erpMap = pubService.getECSURL(ZCXX01);
			if(erpMap.get("DJLX") != null){
				if(erpMap.get("DJLX").equals("V9")){
					String erpResponse = v9DHD.createDHD(erpMap, CKDH);
					System.out.println("-->ERP createDHD response: " + erpResponse);
					JSONObject resJson = JSONObject.fromObject(erpResponse);
					String resResult = "0";
					String resMsg = "";
					if( null != resJson && resJson.containsKey("data") ){
						JSONObject dataJson = resJson.getJSONObject("data");
						resResult = dataJson.getString("JL_State");
						resMsg = dataJson.getString("JL_ERR");
					}
					// 返回失败时抛出错误信息
					if( !"1".equals(resResult)){
						//modify 2016.01.18 增加erp的错误状态
						map.put("STATE","3");
						throw new Exception("ERP createDHD response ERROR: " + resMsg);
					}
				}
			}
			//生产厂家出厂--状态0--追溯系统对接--NineDragon
			if(tool.getCurConf(4)==1)
			{
				//追溯地址
				String address=path+"/AddProduceCollection.json";
				//String address="http://192.168.1.244:88/producetrace/AddProduceCollection.json";
				//拼接串码
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP FROM w_spcm LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 where spcm01 in (select SPCM01 from w_ckdcm where CKDH='"+CKDH+"')";
				List list1 = queryForList(o2o, query);
				//modify 2015.12.04.修改为分批提交
				//zs.rk(list1,address,"0");
				zs.batchRk(1000, list1, address, "0");
			}
			//生成入库单
			// 20151215 齐俊宇 update 增加仓库类型
			//String query="select zcxx65 from w_zcxx where zcxx01 ='"+ZCXX01+"'";
			//int  zcxx65 =queryForInt(o2o, query);
			if("0".equals(cklx))
			{
				insert_RKD(CKDH, ZCXX01);
			}
			map.put("CKDH", CKDH);
			map.put("STATE","1");
		} catch (Exception ex) {
			if(!map.containsKey("STATE") || (map.get("STATE")!=null && "1".equals(map.get("STATE")))){
				map.put("STATE","0");
			}
			
			/********操作日志记录Start*/
			Map errorMap=new HashMap();
			errorMap.put("DJLX", "0");//单据类型（默认为0）
			errorMap.put("YWDH", YWDH);//业务单号
			errorMap.put("DFHM", "");//对方单号（默认为空）
			errorMap.put("CZR", CZR);//操作人
			errorMap.put("RZZT", "0");//日志状态（0失败;1成功）
			errorMap.put("ERROR", "数据插入异常");//错误信息
			manageLogs.writeLogs(errorMap);
			/**********操作日志记录end*/
			ex.printStackTrace();
			throw ex;
		}

		return map;

	}
	
	/**
	 * 出入库单号查询  
	 * http://115.182.209.133/Oper_CKD/selectCKD.action?json={CK1434009599}
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectCKD")
	public Map selectCKD(String json) throws Exception{
		//Map map=new HashMap();
		Map maps = gopInterface.selectCKD(json);
		return maps;
	}
	
	/**
	 * @tod 出库单JSON对接取值，用于跟燕总对接
	 * @author asus    http://127.0.0.1:8088/Oper_CKD/insertCKD.action?json=
	 * {"CKDITEM":[{"ActualServicePerson":"系统管理员","ActualServiceTime":"2015-02-12 16:12:11","num":1,
		"spcm01":"(01)06920128200028(10)150211(21)B4(9999)02","barcode":"6920128200028",
		"DeliveryCompanyCode":"111","DeliveryCompanyName":"上海汽车有限公司","TraceState":"1020"}],
		"CompanyCode":"6951361","GuildCode":"91A00002","TraceNum":"1","FileType":"0","DRSJ":"2015-04-30 13:46:06",
		"PositionCode":"xxldsewarew","SHCK":"1006587-0001","BZ":"","CKR":"系统管理员"}
	 */
	@RequestMapping("/insertCKD")
	public Map insertCKD(String json) throws Exception{
		cds=new DataSet(json);
		Map map=new HashMap();
		Map returnMap=new HashMap();
		try {
		String BZ=cds.getField("BZ", 0);//备注
		String SHCK=cds.getField("SHCK", 0);//仓库代码
		//查询企业编码ZCXX01
		String gsSql="SELECT ZCXX01 FROM W_ZCGS WHERE  HYGLM ='"+cds.getField("GuildCode", 0)+"'";
		Map gsMap=queryForMap(o2o, gsSql);
		String ZCXX01=gsMap.get("ZCXX01").toString();
		//获取出库单号
		String CKDH="CK"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_CKD",1),10);
		String isCkdsql="SELECT count(*) count FROM  W_CKD WHERE CKDH ='"+CKDH+"'";
		int sum=queryForInt(o2o, isCkdsql);
		String ckdSql="";
		if(sum==0){
			ckdSql="INSERT INTO W_CKD(CKDH,ZCXX01,SHCK,CKR,CKSJ,BZ) VALUES ('"+CKDH+"','"+ZCXX01+"',SHCK?,CKR?,CKSJ?,BZ?)";
			Map row = getRow(ckdSql, null, 0);
			execSQL(o2o, ckdSql, row); 
		}else{
			returnMap.put("STATE","failure");
			return returnMap;
		}
		//导入商品串码主表 (屏蔽掉)
		/*
		int SPCMDR01=pubGetBH.getWBHZT(o2o,"W_SPCMDR",1);
		String issql="SELECT count(*) count FROM  W_SPCMDR WHERE ZCXX01 ='"+ZCXX01+"' AND SPCMDR01='"+SPCMDR01+"'";
		int count=queryForInt(o2o, issql);
		String spcmSql="";
		if(count==0){
			spcmSql = "insert into W_SPCMDR(ZCXX01,SPCMDR01,SBM,HYGLM,WZM,DRSL,DRLX,DRRQ) " +
			"values ('"+ZCXX01+"','"+SPCMDR01+"',CompanyCode?,GuildCode?,PositionCode?,TraceNum?,FileType?,DRSJ?)";
			Map rows=getRow(spcmSql, null, 0);
			execSQL(o2o, spcmSql, rows);
		}else{
			returnMap.put("STATE","failure");
			return returnMap;
		}
		 * */
		String CKDITEM= cds.getField("CKDITEM",0);
		JSONArray  list = JSONArray.fromObject(CKDITEM);
		String SPXX01="";
		String SPXX02="";
		//插入W_CKDCM
		for(int j=0;j<list.size();j++){
			Map ckcmMap=(Map) list.get(j);
			//查询商品编码SPXX01
			String spSql="SELECT A.SPXX01,B.SPXX02 FROM W_GOODS A,W_SPXX B WHERE  A.SPXX01=B.SPXX01 AND A.BARCODE ='"+ckcmMap.get("barcode")+"'";
			Map spMap=queryForMap(o2o, spSql);
			SPXX01=spMap.get("SPXX01").toString();
			SPXX02=spMap.get("SPXX02").toString();
			//批次号，序列号
			String spcm01=ckcmMap.get("spcm01").toString();
			String xlh = "";
			String pc = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",2)+1, JLTools.getStrSplitNum(spcm01,"(",3));
			if(JLTools.getStringRepeatShowNum(spcm01,"(") == 4){
				xlh = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",3)+1, JLTools.getStrSplitNum(spcm01,"(",4));
			}
			String ckdcmSql="INSERT INTO W_CKDCM(CKDH,SPXX01,SPCM01,BARCODE,PCH,XLH) " +
			"VALUES('"+CKDH+"','"+SPXX01+"','"+ckcmMap.get("spcm01")+"','"+ckcmMap.get("barcode")+"'" +
					",'"+ckcmMap.get("pc")+"','"+ckcmMap.get("xlh")+"')";
			Map row2 = getRow(ckdcmSql, null, 0);
			execSQL(o2o, ckdcmSql, row2); 
			//商品串码记录表
			String ckqydm="";
			String ckqymc="";
				if(ckcmMap.get("DeliveryCompanyCode")==null){ckqydm="";};
				if(ckcmMap.get("DeliveryCompanyName")==null){ckqymc="";};
			String sql = "insert into W_SPCMJLB(ZCXX01,DJBH,DJLX,spxx01,barcode,spcm01,cmclsj,cmclry,ckqydm,ckqymc,cmzt) " +
			"values ('"+ZCXX01+"','"+CKDH+"',1,'"+SPXX01+"','"+ckcmMap.get("barcode")+"','"+ckcmMap.get("spcm01")+"'," +
					"'"+ckcmMap.get("ActualServiceTime")+"','"+ckcmMap.get("ActualServicePerson")+"','"+ckqydm+"','"+ckqymc+"','"+ckcmMap.get("TraceState")+"')";
			Map row1=getRow(sql, null, 0);
			execSQL(o2o, sql, row1);
			//判断是否是生成企业导入，如果是则需要插入W_SPCM表
			sql = "SELECT COUNT(0) sum FROM W_SPCM WHERE ZCXX01='"+ZCXX01+"' AND SPXX01="+SPXX01+" AND SPCM01='"+ckcmMap.get("spcm01")+"'";
			int cmNm = queryForInt(o2o,sql);
			if(cmNm == 0){
					sql = "INSERT INTO W_SPCM(ZCXX01,SPXX01,SPCM01,BARCODE,SPCM02,SPCM03,SPCM04) " +
					 "VALUES('"+ZCXX01+"',"+SPXX01+",'"+ckcmMap.get("spcm01")+"','"+ckcmMap.get("barcode")+"',now(),'"+pc+"','"+xlh+"')";
					Map row3=getRow(sql, null, 0);
					execSQL(o2o, sql, row3);
				}
		}
		//插入W_CKDITEM
		HashMap maps=null;
		List spcmList=new ArrayList();
		boolean find = false;
		for(int j=0;j<list.size();j++){
			Map ckcmMap=(Map) list.get(j);
			String barcode=ckcmMap.get("barcode").toString();
			String spcm01=ckcmMap.get("spcm01").toString();
			String pc=spcm01.substring(JLTools.getStrSplitNum(spcm01,"(",2)+1, JLTools.getStrSplitNum(spcm01,")",2));
			String xlh=spcm01.substring(JLTools.getStrSplitNum(spcm01,"(",3)+1, JLTools.getStrSplitNum(spcm01,")",3));
			String ckSql="SELECT COUNT(*) NUM FROM W_CKDCM  WHERE BARCODE='"+ckcmMap.get("barcode")+"' AND CKDH='"+CKDH+"'";
			//统计一个商品的出库数量
			int cksl=queryForInt(o2o, ckSql);
		
			String sql="SELECT COUNT(*) NUM FROM W_CKDITEM  WHERE CKDH='"+CKDH+"'";
			int nums=queryForInt(o2o, sql);
			if(nums==0){
				sql="INSERT INTO W_CKDITEM (CKDH,SPXX01,CKSL,RKSL,BZ) VALUES('"+CKDH+"','"+SPXX01+"','"+cksl+"',0,BZ?)";
				Map row3 = getRow(sql, null, 0);
				execSQL(o2o, sql, row3);
			}
			int num=1;
			for(int i=0;i<spcmList.size();i++){
				maps=(HashMap) spcmList.get(i);
				if(maps.get("barcode").toString().equals(barcode)){
					num=Integer.parseInt(maps.get("num").toString())+1;
					String spcm=maps.get("spcm01").toString().replaceAll("'", "")+","+spcm01;
					maps.put("spcm01","'"+spcm+"'");
					maps.put("num",num);
					find = true;
					break;
				}
			}
			if(!find){
				Map tempMap=new HashMap();
				tempMap.put("barcode", barcode);
				tempMap.put("num",num);
				tempMap.put("SPXX01",SPXX01);
				tempMap.put("SPXX02",SPXX02);
				tempMap.put("pc",pc);
				tempMap.put("xlh",xlh);
				tempMap.put("spcm01","'"+spcm01+"'");
				spcmList.add(tempMap);
			}
			find=false;
		}
		map.put("CKDH", CKDH);
		map.put("BZ",BZ);
		map.put("SHCK",SHCK);
		map.put("spcmList", spcmList);

		/*测试通过
		if(JLTools.getCurConf(1) == 1){
			Map mapss = gopInterface.transCKD2(map);
			if(!mapss.get("status").equals("0")){
				//错误后写入状态
				//maps.put("XSDD01", "");
				//gopInterfaceMassage(maps);
				mapss.put("state", "error");
				return mapss;
			}
		}
		 * */
		returnMap.put("CKDH", CKDH);
		returnMap.put("STATE","success");
		} catch (Exception ex) {
			returnMap.put("STATE","failure");
			throw ex;
		}
		return returnMap;
	}

	/**
	 * 查询出库单
	 * @author 
	 */
	@RequestMapping("/selectW_CKD.action")
	public Map selectW_CKD(String json) throws Exception{
		cds=new DataSet(json);
		Map map = new HashMap();
		String CKDH=cds.getField("CKDH", 0);
		String sql = "SELECT A.CKDH,A.ZCXX01,(SELECT ck02 FROM ck WHERE ck01=A.SHCK) SHCK,A.CKR,DATE_FORMAT(A.CKSJ,'%Y-%m-%d %H:%i:%s') CKSJ,A.BZ FROM W_CKD A WHERE A.CKDH='"+CKDH+"'";
		map = queryForMap(o2o, sql);
		return map;
	}
	
	/**
	 * 查询入库单
	 * @author 
	 */
	@RequestMapping("/selectW_RKD.action")
	public Map selectW_RKD(String json) throws Exception{
		cds=new DataSet(json);
		Map map = new HashMap();
		String RKDH=cds.getField("RKDH", 0);
		String sql = "SELECT A.RKDH,A.ZCXX01,(SELECT ck02 FROM ck WHERE ck01=A.SHCK) SHCK,DATE_FORMAT(A.RKSJ,'%Y-%m-%d %H:%i:%s') RKSJ,A.BZ FROM W_RKD A WHERE A.RKDH='"+RKDH+"'";
		map = queryForMap(o2o, sql);
		return map;
	}
	
	/*
	 * 新增入库单
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert_RKD.action")
	public Map insert_RKD(String CKDH, String ZCXX01)throws Exception{
		Map map =new HashMap();
		try {
			//JSONArray json= JSONArray.fromObject(XmlData);
			//获取单号
			String RKDH = "RK"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_RKD",1),10);
			// 20151218 齐俊宇 update
			// 查询入库的ZCXX01 和 收货仓库SHCK
			// 20160108 齐俊宇 update 增加ZCXX01 条件查询
			Map ckdMap = pubService.selectCKDForRKD(CKDH, ZCXX01);
			//插入入库单
			String insert_rkd ="INSERT INTO W_RKD (RKDH,ZCXX01,CKDH,SHCK,RKSJ) VALUES('" + RKDH + "','" + ckdMap.get("ZCXX01").toString() + "','" + CKDH + "','" + ckdMap.get("SHCK").toString() + "',NOW())";
			//String insert_rkd ="INSERT INTO w_rkd (RKDH,ZCXX01,CKDH,SHCK,RKSJ) VALUES('"+RKDH+"',(SELECT ZCXX01 FROM W_CKD WHERE CKDH ='"+CKDH+"'),'"+CKDH+"',(SELECT SHCK FROM W_CKD WHERE CKDH ='"+CKDH+"'),NOW())";
			Map row=getRow(insert_rkd, null, 0);
			int a =execSQL(o2o, insert_rkd, row);
			//插入入库单串码
			String insert_rkdcm = "INSERT INTO w_rkdcm(RKDH,SPXX01,SPCM01,BARCODE,PCH,XLH)  select  '"+RKDH+"',SPXX01,SPCM01,BARCODE,PCH,XLH FROM W_CKDCM WHERE CKDH='"+CKDH+"'";
			Map row_rkdcm=getRow(insert_rkdcm, null, 0);
			execSQL(o2o, insert_rkdcm, row_rkdcm);
			//插入入库单明细
			String insert_rkditem="INSERT INTO w_rkditem  SELECT RKDH,SPXX01,COUNT(1),(select hjbh from w_sphj where spxx01 = w_rkdcm.spxx01) as hjbh FROM w_rkdcm WHERE RKDH='"+RKDH+"' GROUP BY RKDH,SPXX01";
			Map row_rkditem=getRow(insert_rkditem, null, 0);
			execSQL(o2o, insert_rkditem, row_rkditem);
			//写入库存信息
			String queryRKD="SELECT ZCXX01, SPXX01, SHCK, RKSL FROM w_rkd LEFT JOIN w_rkditem ON w_rkditem.RKDH = w_rkd.RKDH where w_rkd.RKDH='"+RKDH+"'";
			List ckList = queryForList(o2o, queryRKD);
			for(int i =0;i<ckList.size();i++)
			{
				Map ckMap = (Map) ckList.get(i);
				kmsService.insertGwcSpxx(ckMap.get("ZCXX01").toString(), Double.valueOf(ckMap.get("SPXX01").toString()),ckMap.get("RKSL").toString(), 0, 0, 0, ckMap.get("SHCK").toString(), "0", 1, RKDH, Integer.parseInt(ckMap.get("RKSL").toString()), 0);
			}

			//更新入库数量
			String update_rkd="UPDATE w_rkditem,(SELECT spxx01,count(1) c from w_rkdcm where rkdh = '"+RKDH+"' GROUP BY spxx01)t set w_rkditem.RKSL = t.c where t.spxx01 = w_rkditem.SPXX01" ;
			Map row_rkdsl=getRow(update_rkd, null, 0);
			execSQL(o2o, update_rkd, row_rkdsl);

			//更新入库总数量
			String update_ckdsl="UPDATE w_ckditem, ( SELECT spxx01, sum(rksl) c FROM w_rkd LEFT JOIN w_rkditem ON w_rkditem.RKDH = w_rkd.RKDH WHERE ckdh = '"+CKDH+"' GROUP BY spxx01 ) t SET RKSL = t.c WHERE t.spxx01 = w_ckditem.spxx01 AND w_ckditem.CKDH = '" + CKDH + "'";
			Map row_ckdsll=getRow(update_ckdsl, null, 0);
			execSQL(o2o, update_ckdsl, row_ckdsll);
			//更新出库单状态 1部分出库 2全部出库
			String update_ckzt="UPDATE w_ckd SET CKZT = (CASE WHEN (SELECT SUM(CKSL)-SUM(RKSL) AS C from w_ckditem where CKDH = '"+CKDH+"')>0 THEN '1' ELSE '2' END) WHERE CKDH = '"+CKDH+"'";
			Map row_ckzt=getRow(update_ckzt, null, 0);
			execSQL(o2o, update_ckzt, row_ckzt);
			
			//于追溯系统对接
			if(tool.getCurConf(4)==1)
			{
				//追溯地址
				String address=path+"/AddProduceCollection.json";
				//String address="http://192.168.1.244:88/producetrace/AddProduceCollection.json";
				//拼接串码
				String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP FROM w_spcm LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 where spcm01 in (select SPCM01 from w_rkdcm where RKDH='"+RKDH+"')";
				List list = queryForList(o2o, query);
				//modify 2015.12.04. 修改为分批提交
				//zs.rk(list,address,"1");
				zs.batchRk(1000, list, address, "1");
			}
			//入库对接
			String sql = "SELECT ZCXX01 FROM w_ckd WHERE CKDH ='"+CKDH+"'";
			Map zcMap = queryForMap(o2o, sql);
			Map erpMap = pubService.getECSURL(zcMap.get("ZCXX01").toString());
			if(erpMap.get("DJLX") != null){
				if(erpMap.get("DJLX").equals("V9")){
					String returnStr = v9RKD.createRKD(erpMap,CKDH,RKDH);
					System.out.println(returnStr+"  @@@@@@@@@@@@");
					JSONObject jsonObject = JSONObject.fromObject(returnStr);
					Map returnData =(Map) jsonObject.get("data");
					if(!returnData.get("JL_State").equals("1")){
						throw new Exception("入库对接ERP失败");
					}
				}
			}
			
			int d=a;
			if(d==1)
			{
				map.put("STATE", "1");	
			}
			else
			{
				map.put("STATE", "0");	
			}
		} catch (Exception ex) {
			map.put("STATE", "0");
			ex.printStackTrace();
			throw ex;
		}
		return map;
	}
	//判断是否在出库单里面存在串码
	public String ckdmList(List list){
		StringBuffer strbf = new StringBuffer();
		StringBuffer str = new StringBuffer();
		for(int k=0;k<list.size();k++){
		  Map ckcmMap=(Map) list.get(k);
		  if(ckcmMap.get("spcm01").toString().substring(ckcmMap.get("spcm01").toString().length()-2, ckcmMap.get("spcm01").toString().length()).equals("00"))
			{
			  strbf = strbf.append("'"+ckcmMap.get("spcm01")+"',");
			}else{
				String query="SELECT curCode from w_spcmbz where parentCode = '"+ckcmMap.get("spcm01")+"' ";
				List  cmlist = queryForList(o2o,query);
				if(cmlist!=null&&cmlist.size()==0){
					for(int a=0;a<cmlist.size();a++)
					{
						Map cmMap =(Map) cmlist.get(a);
						strbf = strbf.append("'"+ckcmMap.get("curCode")+"',");	
					}
				}
			}
		}
		String SPCM01 = strbf.toString().substring(0, strbf.length()-1);
		String returnsql = "SELECT SPCM01 FROM W_CKDCM WHERE SPCM01 IN ("+SPCM01+");";
		List returnlist = queryForList(o2o,returnsql);
		if(returnlist != null && returnlist.size()>0){
			for(int l=0;l<returnlist.size();l++){
			  Map ckcmMap=(Map) returnlist.get(l);
			  str = str.append(ckcmMap.get("SPCM01")+",");
			}
			return str.substring(0,str.length()-1);
		}
		return str.toString();
	}
	
	
	/**
	 * 查看当前商品是放到虚拟仓库还是实体仓库中 
	 * 	20151214 齐俊宇 新增 
	 *  20160107 齐俊宇 确认使用
	 *  20160108 齐俊宇 修改
	 * 	查询逻辑待修改 20151218 
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectQYCK.action")
	public Map selectQYCK(String jsonData)throws Exception{
		Map resultMap = new HashMap();
		try {
			cds = new DataSet(jsonData);
			//获取单号
			String barcode = cds.getField("BARCODE", 0);
			String zcxx01 = cds.getField("ZCXX01", 0);
			String xzck = cds.getField("XZCK", 0);
			ObjectMapper mapper = new ObjectMapper();
			Map covMap = new HashMap();
			jsonData = JLTools.unescape(jsonData);
			List list = mapper.readValue(jsonData, ArrayList.class);
			covMap = (Map) list.get(0);
			List cityList = (List) covMap.get("CKLIST");
			StringBuilder select_kcck = new StringBuilder();
			List dqList = new ArrayList();
			for (int i = 0; i < cityList.size(); i++) {
				String[] cityArr = cityList.get(i).toString().split(",");
				if("1".equals(cityArr[1])){
					select_kcck.append(" AND NOT EXISTS(SELECT D.DQXX01 FROM W_DQCK D WHERE D.CK01 = '" + cityArr[0] + "' "
							 + "AND A.DQXX01 = D.DQXX01)");
				} else {
					select_kcck.append(" AND NOT EXISTS(SELECT D.DQBZM01 DQXX01 FROM DQBZM D "
							+ "WHERE EXISTS (SELECT 1 FROM W_GSGX E "
							+ "LEFT JOIN W_GSGXQY F "
							+ "ON E.ZTID = F.ZTID AND E.HBID = F.HBID "
							+ "WHERE E.ZTID = '" + zcxx01 + "' "
							+ "AND E.HBID = '" + cityArr[2] + "' "
							+ "AND (D.DQBZM01 = F.DQBZM01 "
							+ "OR D.DQBZM_DQBZM01 = F.DQBZM01) "
							+ "AND D.DQBZM03 = 2) "
							+ "AND A.DQXX01 = D.DQBZM01)");
				}
				
				// 目前先不用list比较
//				dqList.addAll(queryForList(o2o, select_kcck));
			}
			// 20160108 齐俊宇 可以修改成拼串 一条sql得出结果不需要list比较 上边sql注释部分进行拼串 ckqyList就是结果集
			StringBuilder select_qy = new StringBuilder();
			select_qy.append("SELECT A.DQXX01 FROM W_DQCK A LEFT JOIN W_GSCK B ON A.CK01 = B.CK01 LEFT JOIN CK C ON A.CK01 = C.CK01 "
					+ "WHERE B.ZCXX01 = '" + zcxx01 + "' AND C.TYPE = 0 AND C.CK09 = 0 "
					+ select_kcck);
			List ckqyList = queryForList(o2o, select_qy.toString());
			select_kcck.delete(0, select_kcck.length());
			select_qy.delete(0, select_qy.length());
			// 目前先不用list比较
			/*List dqNewList = new ArrayList();
			for (int j = 0; j < dqList.size(); j++) {
				Map dqMap = (Map) dqList.get(j);
				String dqStr = dqMap.get("DQXX01").toString();
				dqNewList.add(dqStr);
			}
			
			String select_ckqy = "SELECT A.DQXX01 FROM W_DQCK A LEFT JOIN W_GSCK B ON A.CK01 = B.CK01 LEFT JOIN CK C ON A.CK01 = C.CK01 "
					+ "WHERE B.ZCXX01 = '" + zcxx01 + "' AND C.TYPE = 0 AND C.CK09 = 0";
			List ckqyList = queryForList(o2o, select_ckqy);
			List qyNewList = new ArrayList();
			for (int k = 0; k < ckqyList.size(); k++) {
				Map qyMap = (Map) ckqyList.get(k);
				String qyStr = qyMap.get("DQXX01").toString();
				qyNewList.add(qyStr);
			}
			qyNewList.removeAll(dqNewList);*/
			if(ckqyList.size() > 0){
				resultMap.put("STATE", "1");
			} else {
				resultMap.put("STATE", "2");
			}
			
		} catch (Exception ex) {
			resultMap.put("STATE", "0");
			throw ex;
		}
		return resultMap;
	}
	
}
