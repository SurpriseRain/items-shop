package com.jlsoft.o2o.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JLQuery;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V7.V7LSD;
import com.jlsoft.o2o.interfacepackage.V9.V9THD;
import com.jlsoft.o2o.interfacepackage.alipay.UnionpayOnline;
import com.jlsoft.o2o.interfacepackage.alipayCom.UnionpayCommunications;
import com.jlsoft.o2o.interfacepackage.jlinterface.GopInterface;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.o2o.pub.service.SmsService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
/**
 * 
 * @THD 退货操作类
 *
 */
@Controller
@RequestMapping("/Oper_THD")
public class Oper_THD extends JLBill{
		JLTools tool = new JLTools();
		@Autowired
		private UnionpayCommunications unionpayCom;
		private UnionpayOnline unionpayOnline;
		@Autowired
		private GopInterface gopInterface;
		@Autowired
		private ManageLogs manageLogs;
		@Autowired
		private KmsService kmsService;
		@Autowired
		private SmsService smsService;
		@Autowired
		private PubService pubService;
		@Autowired
		private V9THD v9THD;
		@Autowired
		private V7LSD v7LSD;
		
		/*
		 * 新增退货单-沈阳大家庭
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/insert_THD_SYXL.action")
		public Map insert_THD_SYXL(HttpServletRequest request,
				HttpServletResponse response,String XmlDataa,String spjs,String spcs)throws Exception{
			Map map =new HashMap();
			try {
				String XmlData = JLTools.unescape(request.getParameter("XmlData"));
				cds=new DataSet(XmlData);
				//获取退货单号
				String thdh = "TH"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_THD",1),10);
				String insert_thd="INSERT INTO W_THD (THDH,XSDD01,ZTID,HBID,THLX,THPZ,WLLX,THZT,THJE,THSL,ZDSJ,PROVINCE,CITY,COUNTY,XXDZ,LXR,LXDH,BZ) "
						+ "VALUES('"+thdh+"',XSDD01?,(select ZTID from w_xsdd where xsdd01=XSDD01?),(select HBID from w_xsdd where xsdd01=XSDD01?),THLX?,THPZ?,WLLX?,0,THJE?,THSL?,NOW(),PROVINCE?,CITY?,COUNTY?,XXDZ?,LXR?,LXDH?,BZ?)";
				Map row=getRow(insert_thd, null, 0);
				int a =execSQL(o2o, insert_thd, row);
				
				String insert_thditem="INSERT INTO w_thditem (THDH,SPXX01,SPJG,THSL,THJE) VALUES('"+thdh+"',SPXX01?,SPJG?,THSL?,THJE?)";
				Map row_item=getRow(insert_thditem, null, 0);
				int b =execSQL(o2o, insert_thditem, row_item);
				
				String query_thsl="SELECT THSL FROM W_XSDDITEM where XSDD01='"+cds.getField("XSDD01", 0)+"' and SPXX01='"+cds.getField("SPXX01", 0)+"'";
				int thsl = queryForInt(o2o, query_thsl);
				thsl=thsl+Integer.parseInt(cds.getField("THSL", 0));
				String update_xsdditem = "update w_xsdditem set thsl="+thsl+" where XSDD01=XSDD01? and SPXX01=SPXX01?";
				Map row_xsdditem=getRow(update_xsdditem, null, 0);
				execSQL(o2o, update_xsdditem, row_xsdditem);
				
				int d=a*b;
				if(d==1)
				{
					map.put("STATE", "1");	
				}
				else
				{
					map.put("STATE", "0");	
				}
				MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
				Iterator iterator = mrRequest.getFileNames();
				int TPXH=0;
				while(iterator.hasNext()){
					TPXH++;
					String fileType = (String)iterator.next();
					MultipartFile file = mrRequest.getFile(fileType);
					
					String name = tool.getTimestamp();
					if("".equals(file.getOriginalFilename()))
					{
						
					}else
					{
						String fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
						//上传图片到服务器
						Map mapImg = new HashMap();
						mapImg.put("imgPath", JlAppResources.getProperty("path_thdtp")+cds.getField("ZCXX01", 0));
						mapImg.put("imgName", fileName);
						JLTools.fileUploadNew(file,mapImg);
						String insert_thdtp="INSERT INTO w_thdtp (THDH,SPXX01,TPXH,TPMC) VALUES('"+thdh+"',SPXX01?,'"+TPXH+"','"+fileName+"')";
						Map row_tp=getRow(insert_thdtp, null, 0);
						execSQL(o2o, insert_thdtp, row_tp);
					}
				}
				//给卖家发送短信，提醒审核
				if(JLTools.getCurConf(2) == 1){
					String xsdd01=cds.getField("XSDD01", 0);
					String smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.ZTID,B.ZCXX02 FROM W_XSDD A,W_ZCGS B WHERE A.ZTID=B.ZCXX01 AND A.XSDD01='"+xsdd01+"'";
					Map smsMap = queryForMap(o2o,smsSQL);
					String content = "尊敬的"+smsMap.get("ZCXX02").toString()+"您好，您的店铺有一条退/换货申请,单号："+thdh+"，请您尽快审核!";
					smsService.sendSms(4, thdh, smsMap.get("SJHM").toString(), content, smsMap.get("ZTID").toString());
				}
				
				String xsSql="update w_djzx set w_djzx02=16 where w_djzx01=XSDD01?";
				Map xsrow=getRow(xsSql, null, 0);
				execSQL(o2o, xsSql, xsrow);
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
				throw e;
			}
			return map;
		}
		
		/*
		 * 新增退货单
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/insert_THD.action")
		public Map insert_THD(HttpServletRequest request,
				HttpServletResponse response,String XmlDataa,String spjs,String spcs)throws Exception{
			Map map =new HashMap();
			try {
				String XmlData = JLTools.unescape(request.getParameter("XmlData"));
				cds=new DataSet(XmlData);
				//获取退货单号
				String thdh = "CSTH"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_THD",1),10);
				String insert_thd="INSERT INTO W_THD (THDH,XSDD01,ZTID,HBID,THLX,THPZ,WLLX,THZT,THJE,THSL,ZDSJ,PROVINCE,CITY,COUNTY,XXDZ,LXR,LXDH,BZ,STREET,KHMC,KHYH,YHZH) "
						+ "VALUES('"+thdh+"',XSDD01?,(select ZTID from w_xsdd where xsdd01=XSDD01?),(select HBID from w_xsdd where xsdd01=XSDD01?),THLX?,THPZ?,WLLX?,0,THJE?,THSL?,NOW(),PROVINCE?,CITY?,COUNTY?,XXDZ?,LXR?,LXDH?,BZ?,'"+cds.getField("streetQY", 0)+"',KHMC?,KHYH?,YHZH?)";
				Map row=getRow(insert_thd, null, 0);
				int a =execSQL(o2o, insert_thd, row);
				
				String insert_thditem="INSERT INTO w_thditem (THDH,SPXX01,SPJG,THSL,THJE) VALUES('"+thdh+"',SPXX01?,SPJG?,THSL?,THJE?)";
				Map row_item=getRow(insert_thditem, null, 0);
				int b =execSQL(o2o, insert_thditem, row_item);
				
				String query_thsl="SELECT THSL FROM W_XSDDITEM where XSDD01='"+cds.getField("XSDD01", 0)+"' and SPXX01='"+cds.getField("SPXX01", 0)+"'";
				int thsl = queryForInt(o2o, query_thsl);
				thsl=thsl+Integer.parseInt(cds.getField("THSL", 0));
				String update_xsdditem = "update w_xsdditem set thsl="+thsl+" where XSDD01=XSDD01? and SPXX01=SPXX01?";
				Map row_xsdditem=getRow(update_xsdditem, null, 0);
				execSQL(o2o, update_xsdditem, row_xsdditem);
				
				int d=a*b;
				if(d==1)
				{
					map.put("STATE", "1");	
				}
				else
				{
					map.put("STATE", "0");	
				}
				MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
				Iterator iterator = mrRequest.getFileNames();
				int TPXH=0;
				while(iterator.hasNext()){
					TPXH++;
					String fileType = (String)iterator.next();
					MultipartFile file = mrRequest.getFile(fileType);
					
					String name = tool.getTimestamp();
					if("".equals(file.getOriginalFilename()))
					{
						
					}else
					{
						String fileName = name + "." + file.getOriginalFilename().split("\\.")[1];
						//上传图片到服务器
						Map mapImg = new HashMap();
						mapImg.put("imgPath", JlAppResources.getProperty("path_thdtp")+cds.getField("ZCXX01", 0));
						mapImg.put("imgName", fileName);
						JLTools.fileUploadNew(file,mapImg);
						String insert_thdtp="INSERT INTO w_thdtp (THDH,SPXX01,TPXH,TPMC) VALUES('"+thdh+"',SPXX01?,'"+TPXH+"','"+fileName+"')";
						Map row_tp=getRow(insert_thdtp, null, 0);
						execSQL(o2o, insert_thdtp, row_tp);
					}
				}
				//给卖家发送短信，提醒审核
				if(JLTools.getCurConf(2) == 1){
					String xsdd01=cds.getField("XSDD01", 0);
					String smsSQL = "SELECT ifnull(B.ZCXX06,'') SJHM,A.ZTID,B.ZCXX02 FROM W_XSDD A,W_ZCGS B WHERE A.ZTID=B.ZCXX01 AND A.XSDD01='"+xsdd01+"'";
					Map smsMap = queryForMap(o2o,smsSQL);
					String content = "尊敬的"+smsMap.get("ZCXX02").toString()+"您好，您的店铺有一条退/换货申请,单号："+thdh+"，请您尽快审核!";
					smsService.sendSms(4, thdh, smsMap.get("SJHM").toString(), content, smsMap.get("ZTID").toString());
				}
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
				throw e;
			}
			return map;
		}
		
		/*
		 * 更新卖家审核意见
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/update_SHYJ.action")
		public Map update_SHYJ(String XmlData,HttpServletRequest request) throws Exception{
			Map map =new HashMap();
			String flag="success";
			try {
				cds=new DataSet(XmlData);
				String THZT = cds.getField("THZT",0);
				String zffs = cds.getField("ZFFS",0); //支付方式
				String thfs = cds.getField("THFS",0); //退货方式
				String fkfs = cds.getField("FKFS", 0);
				System.out.println("THZT:"+THZT);
				
				if(flag.equals("success"))
				{
					//b为跳转页面标示:1：跳转退货单菜单 2：跳转退款单菜单 3：财务审核菜单
					int b=0;
					if("10".equals(THZT)||"3".equals(THZT) || "11".equals(THZT))
					{
						b=1;
					}else if("4".equals(THZT)||"5".equals(THZT))
					{
						b=2;
					}else if("8".equals(THZT)||"7".equals(THZT))
					{
						b=3;
					}
					if("1".equals(THZT)||"3".equals(THZT)||"5".equals(THZT)||"7".equals(THZT))
					{
						String update_xsdditem = "update w_xsdditem set thsl=thsl-(select THSL from w_thditem where THDH=THDH?) where XSDD01=(select XSDD01 from w_thd where THDH=THDH?) and SPXX01=(select SPXX01 from w_thditem where THDH=THDH?)";
						Map row_xsdditem=getRow(update_xsdditem, null, 0);
						execSQL(o2o, update_xsdditem, row_xsdditem);
					}
					//更新退货单状态和回复意见，XSDD中zffs为1时表示线下汇款
					String update = "update w_thd set SHYJ=SHYJ?,THZT=THZT? where THDH =THDH?";
					if("10".equals(THZT) && "2".equals(thfs) && "1".equals(zffs)){
						update = "update w_thd set SHYJ=SHYJ?,THZT=THZT?,KTDH=KTDH?,KTGS=KTGS? where THDH =THDH?";
					}
					if("8".equals(THZT) && ("1".equals(zffs) || "8".equals(fkfs) || "11".equals(fkfs))){
						update = "update w_thd set SHYJ=SHYJ?,THZT=6 where THDH =THDH?";
					}
					Map row=getRow(update, null, 0);
					int a =execSQL(o2o, update, row);
					//同意退货
					if("10".equals(THZT)){
						//与ERP系统对接
						// 20151224 齐俊宇 update 修改查询ZCXX65的sql改为查询出仓的仓库类型为ZCXX65->CKLX BEGIN
						/*String sql = "SELECT A.ZTID,(SELECT PFD01 FROM W_XSDDGROUP WHERE XSDD01=A.XSDD01) PFD01," +
								           "B.XSDD01,(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=A.ZTID) ZCXX25," +
								           "B.CK01,(SELECT ERPDZ FROM CK WHERE CK01=B.CK01) ERPDZ,A.LXR,A.LXDH,A.XXDZ,A.WLLX,A.BZ," +
								           "IFNULL((SELECT SHWD FROM W_DQWD WHERE DQXX01=A.STREET),'00010116') SHWD," +
								           "(SELECT ZCXX65 FROM W_ZCXX WHERE ZCXX01=A.ZTID) ZCXX65 " +
								           "FROM W_THD A,W_XSDDITEM B,W_THDITEM C " +
								           "WHERE A.XSDD01=B.XSDD01 AND A.THDH=C.THDH AND B.SPXX01=C.SPXX01 AND A.THDH='"+cds.getField("THDH", 0)+"'";*/
						String sql = "SELECT DISTINCT A.ZTID, (SELECT PFD01 FROM W_XSDDGROUP WHERE XSDD01 = A.XSDD01) PFD01, B.XSDD01, "
								+ "Z.ZCXX25 ZCXX25, B.CK01,(SELECT ERPDZ FROM CK WHERE CK01 = B.CK01) ERPDZ, A.LXR, A.LXDH, A.XXDZ, A.WLLX, "
								+ "A.BZ, IFNULL((SELECT SHWD FROM W_DQWD WHERE DQXX01 = A.STREET), '00010116') SHWD, "
								+ "(SELECT DISTINCT D.TYPE FROM CK D, W_GSCK E, W_DQCK F, W_KCXX G WHERE G.CK01 = D.CK01 "
								+ "AND D.CK01 = E.CK01 AND E.CK01 = F.CK01 AND G.ZCXX01 = A.ZTID AND F.DQXX01 = A.CITY) CKLX "
								+ "FROM W_THD A LEFT JOIN W_XSDDITEM B ON A.XSDD01 = B.XSDD01 "
								+ "LEFT JOIN W_THDITEM C ON A.THDH = C.THDH "
								+ "LEFT JOIN W_SPGL S ON S.SPXX01 = B.SPXX01 "
								+ "LEFT JOIN W_ZCXX Z ON Z.ZCXX01 = S.ZCXX01 "
								+ "WHERE B.SPXX01 = C.SPXX01 AND A.THDH = '" + cds.getField("THDH", 0) + "'";
						// 20151224 齐俊宇 update 修改查询ZCXX65的sql改为查询出仓的仓库类型为ZCXX65->CKLX END
						Map thdMap = queryForMap(o2o,sql);
 						Map erpMap = pubService.getECSURL(thdMap.get("ZTID").toString());
 						if(erpMap.get("DJLX") != null){
							//存放退货单号、分销单、仓库信息
							erpMap.put("THDH", cds.getField("THDH", 0));
							erpMap.put("ERPDZ", thdMap.get("ERPDZ").toString());
							erpMap.put("PFD01", thdMap.get("PFD01").toString());
							erpMap.put("XSDD01", thdMap.get("XSDD01"));
							erpMap.put("ZCXX25", thdMap.get("ZCXX25").toString());
							erpMap.put("LXR", thdMap.get("LXR").toString());
							erpMap.put("LXDH", thdMap.get("LXDH").toString());
							erpMap.put("XXDZ", thdMap.get("XXDZ").toString());
							erpMap.put("BZ", thdMap.get("BZ"));
							erpMap.put("SHWD", thdMap.get("SHWD"));
							if(erpMap.get("DJLX").equals("V9")){
								//创建退货单
								String returnStr = v9THD.createTHD(erpMap);
								System.out.println(returnStr+"  @@@@####创建退货单参数");
								JSONObject jsonObject = JSONObject.fromObject(returnStr);
								JSONObject returnData =(JSONObject) jsonObject.get("data");
								if(returnData.getString("JL_State").equals("1")){
									String thPfd01=returnData.get("JL_OrderCode").toString();
									sql = "UPDATE W_THD SET PFD01="+thPfd01+" WHERE THDH='"+cds.getField("THDH", 0)+"'";
									execSQL(o2o, sql, row);
									//创建客户建档
									if(((Integer)thdMap.get("WLLX")).intValue() == 0 && ((Integer)thdMap.get("CKLX")).intValue() == 1){
										erpMap.put("thPfd01", thPfd01);
										returnStr = v9THD.createTHKHJD(erpMap);
										jsonObject = JSONObject.fromObject(returnStr);
										returnData =(JSONObject) jsonObject.get("data");
										if(!returnData.getString("JL_State").equals("1")){
											throw new Exception("退货建档失败");
										}
									}
								}else{
									throw new Exception("退货制单失败");
								}
							}
						}
						//与安迅物流对接
						if(JLTools.getCurConf(1) == 1){
							Map maps = gopInterface.transTHD(cds.getField("THDH", 0));
							if(!maps.get("status").equals("0")){
								//错误后写入状态
								//gopInterfaceMassage(maps);
								/********操作日志记录Start*/
								Map errorMap=new HashMap();
								errorMap.put("DJLX", "0");//单据类型（默认为0）
								errorMap.put("YWDH", cds.getField("THDH", 0));//业务单号
								errorMap.put("DFHM", "");//对方单号（默认为空）
								errorMap.put("CZR", cds.getField("ZCXX01", 0));//操作人
								errorMap.put("RZZT", "0");//日志状态（0失败;1成功）
								errorMap.put("ERROR", maps.get("message"));//错误信息
								manageLogs.writeLogs(errorMap);
								/**********操作日志记录end*/
								map.put("STATE", "0");
								throw new Exception(maps.get("message").toString());
							}
						}
					}
					//管理可以二次销售的库存
					if("4".equals(THZT)){
						//库存管理
						//modify 2015.10.28. 修改查询语句，增加and t2.SPXX01=t3.spxx01
						String queryString="select  t1.ztid,t2.SPXX01,t3.ck01,t1.XSDD01,(select count(1) from w_thdcm where xsbj='0' and thdh='"+cds.getField("THDH", 0)+"') THSL from   w_thd  t1,w_thditem t2,w_xsdditem t3  where  t1.thdh=t2.thdh  and t1.xsdd01=t3.xsdd01 and t2.SPXX01=t3.spxx01 and t1.thdh='"+cds.getField("THDH", 0)+"'";
						Map infoMap = queryForMap(o2o, queryString);
						if(Integer.parseInt(infoMap.get("THSL").toString())>0)
						{
							kmsService.insertGwcSpxx(infoMap.get("ztid").toString(), Double.parseDouble(infoMap.get("SPXX01").toString()), "0", 0, 0, 0, infoMap.get("ck01").toString(), "0",
									4, cds.getField("THDH", 0), Integer.parseInt(infoMap.get("THSL").toString()),0 );
						}
					}
					//管理不以二次销售的库存
					if("4".equals(THZT)){
						//库存管理
						//modify 2015.10.28. 修改查询语句，增加and t2.SPXX01=t3.spxx01
						// 20151130 齐俊宇 修改sql,查询仓库通过销售订单的CITY字段进行查询
						// 原始SQL:"select  t1.ztid,t2.SPXX01,(SELECT ck.ck01 from w_dqck right JOIN ck on ck.ck01 = w_dqck.ck01 where ck.ck09 = '1' AND w_dqck.dqxx01 = (select dqxx01 from w_dqck where ck01 = t3.ck01)) ck01,t1.XSDD01,(select count(1) from w_thdcm where xsbj='1' and thdh='"+cds.getField("THDH", 0)+"') THSL from   w_thd  t1,w_thditem t2,w_xsdditem t3  where  t1.thdh=t2.thdh  and t1.xsdd01=t3.xsdd01 and t2.SPXX01=t3.spxx01 and t1.thdh='"+cds.getField("THDH", 0)+"'"
						String queryString="select  t1.ztid,t2.SPXX01,(SELECT ck.ck01 from w_dqck right JOIN ck on ck.ck01 = w_dqck.ck01 where ck.ck09 = '1' AND w_dqck.dqxx01 = (select X.CITY from W_XSDD X where X.XSDD01 = t1.XSDD01)) ck01,t1.XSDD01,(select count(1) from w_thdcm where xsbj='1' and thdh='"+cds.getField("THDH", 0)+"') THSL from   w_thd  t1,w_thditem t2,w_xsdditem t3  where  t1.thdh=t2.thdh  and t1.xsdd01=t3.xsdd01 and t2.SPXX01=t3.spxx01 and t1.thdh='"+cds.getField("THDH", 0)+"'";
						Map infoMap = queryForMap(o2o, queryString);
						if(Integer.parseInt(infoMap.get("THSL").toString())>0)
						{
							kmsService.insertGwcSpxx(infoMap.get("ztid").toString(), Double.parseDouble(infoMap.get("SPXX01").toString()), "0", 0, 0, 0, infoMap.get("ck01").toString(), "0",
									4, cds.getField("THDH", 0), Integer.parseInt(infoMap.get("THSL").toString()),0 );
						}
					}
					/**
					if("8".equals(THZT)){
						//库存管理
						String sql = "SELECT ztid from w_thd where thdh ='"+cds.getField("THDH", 0)+"'";
						Map ztidMap = queryForMap(o2o, sql);
						
						Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
						if(erpMap.get("DJLX") != null){
							String query_fxdh="SELECT xsdd01,pfd01 from w_xsddgroup where where xsdd01 = (select xsdd01 from w_thd where thdh='"+cds.getField("THDH", 0)+"')";
							Map fxdhMap = queryForMap(o2o, query_fxdh);
							erpMap.put("THDH",fxdhMap.get("xsdd01"));
							erpMap.put("FXDH",fxdhMap.get("pfd01"));
							if(erpMap.get("DJLX").equals("V9")){
								String str = v9THD.createFXDTK(erpMap);
							}
						}
					}
					*/
					//财务同意退款后短信通知客户
					if(JLTools.getCurConf(2) == 1){
						String thdh = cds.getField("THDH", 0);
						String smsSQL = "SELECT ifnull(C.ZCXX06,'') SJHM,A.THZT THZT, B.HBID FROM W_THD A,W_XSDD B,W_ZCGS C WHERE A.XSDD01=B.XSDD01 AND B.HBID = C.ZCXX01 AND A.THDH='"+thdh+"'";
						Map smsMap = queryForMap(o2o,smsSQL);
						String context = "";
						THZT = smsMap.get("THZT") + "";
						if("6".equals(THZT)){
							//同意退款
							context = "您的退/换货订单商家已审核通过，单号："+thdh+"，我们将在7个工作日内将退货款项返回到您的支付账号，请注意查收！";
							smsService.sendSms(4, thdh, smsMap.get("SJHM").toString(), context, smsMap.get("HBID").toString());
						}
						if("7".equals(THZT)){
							//不同意退款
							context = "您的退/换货订单商家不同意审核，单号："+thdh+"，我们将联系商家进行协商！";
							smsService.sendSms(4, thdh, smsMap.get("SJHM").toString(), context, smsMap.get("HBID").toString());
						}
					}
					if(a==1){
						map.put("STATE",b);
					}else{
						map.put("STATE", "0");	
					}
				}
				else{
					map.put("STATE", "0");	
				}
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
				throw e;
			}
			return map;
		}
		
		/*
		 * 更新卖家审核意见
		 * */
		@SuppressWarnings("unchecked")
		@RequestMapping("/update_SHYJ_SYXL.action")
		public Map update_SHYJ_SYXL(String XmlData,HttpServletRequest request) throws Exception{
			Map map =new HashMap();
			String flag="success";
			try {
				cds=new DataSet(XmlData);
				String THZT = cds.getField("THZT",0);
				System.out.println("THZT:"+THZT);
				String THLX = cds.getField("THLX", 0);
				if(THLX.equals("1")){
					String update = "update w_thd set SHYJ=SHYJ?,THZT='12' where THDH =THDH?";
					Map row=getRow(update, null, 0);
					int a =execSQL(o2o, update, row);
					map.put("STATE", "2");
					return map;
				}
				if(THZT.equals("6")){
					String querySql="SELECT YLLS,THJE,DATE_FORMAT(ZFSJ,'%Y%m%d')ZFSJ,JYJE,w_xsdd.JYDH,w_xsdd.XSDD01,OEDERSJC orderDatetime from w_thd "
							+ "LEFT JOIN w_xsdd on w_xsdd.xsdd01 = w_thd.XSDD01 "
							+ "LEFT JOIN w_xsdd_ylls on w_xsdd_ylls.jydh =w_xsdd.jydh where w_thd.thdh='"+cds.getField("THDH", 0)+"'";
					Map yllsList = queryForMap(o2o, querySql);
					String  JYDH = yllsList.get("JYDH").toString();
					String  JYRQ = yllsList.get("ZFSJ").toString();
					String  THJE = yllsList.get("THJE").toString();
					String  JYJE = yllsList.get("JYJE").toString();
					String  XSDD01 = yllsList.get("XSDD01").toString();
					String  orderDatetime = yllsList.get("orderDatetime").toString();
					//与银联交互
					Map mapTK = new HashMap();
					mapTK.put("JYDH",JYDH);
					mapTK.put("JYRQ",JYRQ);
					mapTK.put("JYJE",Float.parseFloat(JYJE));
					mapTK.put("TKJE",Float.parseFloat(THJE)*100);
					mapTK.put("orderDatetime", orderDatetime);
					JSONArray json = JSONArray.fromObject(mapTK);
					System.out.println("银行交易JSON:"+json.toString()+"=============================");
					Map resultMap=unionpayCom.refund(json.toString(),request);
					flag =resultMap.get("STATE").toString();
				}
				if(flag.equals("success"))
				{
					//b为跳转页面标示:1：跳转退货单菜单 2：跳转退款单菜单 3：财务审核菜单
					int b=0;
					if("10".equals(THZT)||"3".equals(THZT))
					{
						b=1;
					}else if("6".equals(THZT)||"7".equals(THZT))
					{
						b=2;
					}
					if("1".equals(THZT)||"3".equals(THZT)||"5".equals(THZT)||"7".equals(THZT))
					{
						String update_xsdditem = "update w_xsdditem set thsl=thsl-(select THSL from w_thditem where THDH=THDH?) where XSDD01=(select XSDD01 from w_thd where THDH=THDH?) and SPXX01=(select SPXX01 from w_thditem where THDH=THDH?)";
						Map row_xsdditem=getRow(update_xsdditem, null, 0);
						execSQL(o2o, update_xsdditem, row_xsdditem);
					}
					
					String update = "update w_thd set SHYJ=SHYJ?,THZT=THZT? where THDH =THDH?";
					Map row=getRow(update, null, 0);
					int a =execSQL(o2o, update, row);
					//扫码入库
					if("10".equals(THZT)){
						//与ERP系统对接
						String sql = "SELECT A.ZTID,(SELECT BM01 FROM W_XSDDGROUP WHERE XSDD01=A.XSDD01) BM01," +
								           "(SELECT PFD01 FROM W_XSDDGROUP WHERE XSDD01=A.XSDD01)LSDH,B.XSDD01,C.THJE,C.SPXX01,C.THSL,C.SPJG," +
								           "(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=A.ZTID) ZCXX25,E.PROVINCE ADD_SHENG,E.CITY ADD_SHI,E.COUNTY ADD_QU,E.OTHERDZ ADD_XQ,E.XSDD21 Receiver_phone," +
								           "B.CK01,(SELECT ERPDZ FROM CK WHERE CK01=B.CK01) ERPDZ," +
								           "A.LXR,A.LXDH,A.XXDZ,A.WLLX,IFNULL((SELECT SHWD FROM W_DQWD WHERE DQXX01=A.STREET),'00010116') SHWD FROM W_THD A,W_XSDDITEM B,W_THDITEM C,W_XSDD E " +
								           "WHERE A.XSDD01=B.XSDD01 AND A.THDH=C.THDH AND B.SPXX01=C.SPXX01 AND B.XSDD01=E.XSDD01 AND A.THDH='"+cds.getField("THDH", 0)+"'";
						Map thdMap = queryForMap(o2o,sql);
						Map erpMap = pubService.getECSURL(thdMap.get("ZTID").toString());
						if(erpMap.get("DJLX") != null){
							//存放退货信息
							erpMap.put("TYPE_DJ", "Post_CancelRetail");
							erpMap.put("Order_Code", thdMap.get("XSDD01"));
							erpMap.put("JL_RetailCode", thdMap.get("LSDH"));
							erpMap.put("DepNo", thdMap.get("BM01"));
							erpMap.put("SysTemCon", "23");
							erpMap.put("ProductID", thdMap.get("SPXX01"));
							erpMap.put("Number", "-"+Math.abs(Double.parseDouble(thdMap.get("THSL").toString())));
							erpMap.put("Price", thdMap.get("SPJG"));
							erpMap.put("Amount", (Double.parseDouble(thdMap.get("SPJG").toString())*Double.parseDouble(erpMap.get("Number").toString())));//负数
							erpMap.put("TotalAmount",(Double.parseDouble(erpMap.get("Amount").toString())*Double.parseDouble(thdMap.get("THSL").toString())));
							erpMap.put("Receiver_phone", thdMap.get("Receiver_phone"));//固定电话
							erpMap.put("ADD_SHENG", thdMap.get("ADD_SHENG"));//省
							erpMap.put("ADD_SHI", thdMap.get("ADD_SHI"));//市
							erpMap.put("ADD_QU", thdMap.get("ADD_QU"));//区/县
							erpMap.put("ADD_XQ", thdMap.get("ADD_XQ"));//小区/村/大队
							erpMap.put("SHWD", thdMap.get("SHWD"));//小区/村/大队
							/***
							erpMap.put("THDH", cds.getField("THDH", 0));
							erpMap.put("ERPDZ", thdMap.get("ERPDZ").toString());
							erpMap.put("PFD01", thdMap.get("PFD01").toString());
							erpMap.put("XSDD01", thdMap.get("XSDD01"));
							erpMap.put("ZCXX25", thdMap.get("ZCXX25").toString());
							erpMap.put("LXR", thdMap.get("LXR").toString());
							erpMap.put("LXDH", thdMap.get("LXDH").toString());
							erpMap.put("XXDZ", thdMap.get("XXDZ").toString());
							****/
							if(erpMap.get("DJLX").equals("SCMV7")){
								//创建退货单
								String returnStr = v7LSD.createLSTHSPD(erpMap);
								System.out.println(returnStr+"  @@@@####创建退货单参数");
								JSONObject jsonObject = JSONObject.fromObject(returnStr);
								Map returnData =(Map) jsonObject.get("data");
								if(returnData.get("JL_State").toString().equals("1")){
									String thPfd01=returnData.get("JL_CanRetailCode").toString();
									sql = "UPDATE W_THD SET PFD01="+thPfd01+" WHERE THDH='"+cds.getField("THDH", 0)+"'";
									execSQL(o2o, sql, row);
									String xsSql="update w_djzx set w_djzx02=17 where w_djzx01=XSDD01?";
									execSQL(o2o, xsSql, erpMap);
									//创建客户建档
									if(((Integer)thdMap.get("WLLX")).intValue() == 0){
										//存放退货单号
										erpMap.put("JL_RetailCode", thPfd01);
										returnStr = v7LSD.createLSKHJD(erpMap);
										jsonObject = JSONObject.fromObject(returnStr);
										returnData =(Map) jsonObject.get("data");
										if(!returnData.get("JL_State").toString().equals("1")){
											//写入W_WTDJ
											pubService.insertW_WTDJ(7,cds.getField("THDH", 0),0,0);
											//写入错误日志
											manageLogs.writeLogs(7,cds.getField("THDH", 0),"","系统",0,"生成零售单退货审批ERP失败"+returnData.get("JL_ERR"));
											throw new Exception("退货建档失败");
										}
									}
								}else{
									throw new Exception("退货制单失败");
								}
							}
						}
					}
					if("6".equals(THZT)){
						//库存管理
						String queryString="select  t1.ztid,t2.SPXX01,t3.ck01,t1.XSDD01,t2.THSL from   w_thd  t1,w_thditem t2,w_xsdditem t3  where  t1.thdh=t2.thdh  and t1.xsdd01=t3.xsdd01 and t1.thdh='"+cds.getField("THDH", 0)+"'";
						Map infoMap = queryForMap(o2o, queryString);
						kmsService.insertGwcSpxx(infoMap.get("ztid").toString(), Double.parseDouble(infoMap.get("SPXX01").toString()), "0", 0, 0, 0, infoMap.get("ck01").toString(), "0",
								4, cds.getField("THDH", 0), Integer.parseInt(infoMap.get("THSL").toString()),0 );
						//退货完成
						String xsSql="update w_djzx set w_djzx02=18 where w_djzx01=XSDD01?";
						execSQL(o2o, xsSql, infoMap);
						String sql = "SELECT ztid from w_thd where thdh ='"+cds.getField("THDH", 0)+"'";
						Map ztidMap = queryForMap(o2o, sql);
						
						Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
						if(erpMap.get("DJLX") != null){
							String query_fxdh="SELECT xsdd01,(select pfd01 from w_thd where thdh='"+cds.getField("THDH", 0)+"') lsdh from w_xsddgroup where xsdd01 = (select xsdd01 from w_thd where thdh='"+cds.getField("THDH", 0)+"')";
							Map lsdhMap = queryForMap(o2o, query_fxdh);
							erpMap.put("Order_Code",lsdhMap.get("xsdd01"));
							erpMap.put("JL_CanRetailCode",lsdhMap.get("lsdh"));
							erpMap.put("THDH", cds.getField("THDH", 0));
							if(erpMap.get("DJLX").equals("SCMV7")){
								String returnStr = v7LSD.createLSTHTKSPD(erpMap);
								System.out.println("零售单退款制单："+returnStr);
								JSONObject jsonObject = JSONObject.fromObject(returnStr);
								Map returnData =(Map) jsonObject.get("data");
								if(!returnData.get("JL_State").toString().equals("1")){
									//写入W_WTDJ
									pubService.insertW_WTDJ(7,cds.getField("THDH", 0),0,0);
									//写入错误日志
									manageLogs.writeLogs(7,cds.getField("THDH", 0),"","系统",0,"生成零售退款单ERP失败"+returnData.get("JL_ERR"));
								}
								System.out.println(returnStr + "   @@@@@@@@@@##########");
							}
						}
					}
					/**
					if("8".equals(THZT)){
						//库存管理
						String sql = "SELECT ztid from w_thd where thdh ='"+cds.getField("THDH", 0)+"'";
						Map ztidMap = queryForMap(o2o, sql);
						
						Map erpMap = pubService.getECSURL(ztidMap.get("ztid").toString());
						if(erpMap.get("DJLX") != null){
							String query_fxdh="SELECT xsdd01,pfd01 from w_xsddgroup where where xsdd01 = (select xsdd01 from w_thd where thdh='"+cds.getField("THDH", 0)+"')";
							Map fxdhMap = queryForMap(o2o, query_fxdh);
							erpMap.put("THDH",fxdhMap.get("xsdd01"));
							erpMap.put("FXDH",fxdhMap.get("pfd01"));
							if(erpMap.get("DJLX").equals("V9")){
								String str = v9THD.createFXDTK(erpMap);
							}
						}
					}
					*/
					//财务同意退款后短信通知客户
					if(JLTools.getCurConf(2) == 1){
						String thdh = cds.getField("THDH", 0);
						String smsSQL = "SELECT ifnull(C.ZCXX06,'') SJHM,B.HBID FROM W_THD A,W_XSDD B,W_ZCGS C WHERE A.XSDD01=B.XSDD01 AND B.HBID = C.ZCXX01 AND A.THDH='"+thdh+"'";
						Map smsMap = queryForMap(o2o,smsSQL);
						String context = "";
						if("6".equals(THZT)){
							//同意退款
							context = "【电器服务云】您的退/换货订单商家已审核通过，单号："+thdh+"，我们将在7个工作日内将退货款项返回到您的支付账号，请注意查收！";
						}
						if("7".equals(THZT)){
							//不同意退款
							context = "【电器服务云】您的退/换货订单商家不同意审核，单号："+thdh+"，我们将联系商家进行协商！";
						}
						//发送短信
						smsService.sendSms(4, thdh, smsMap.get("SJHM").toString(), context, smsMap.get("HBID").toString());
					}
					if(a==1){
						map.put("STATE",b);
					}else{
						map.put("STATE", "0");	
					}
				}
				else{
					map.put("STATE", "0");	
					String sql="UPDATE W_THD SET THZT='9',TKDZH ='"+map.get("refundDatetime")+"',ERROR='"+map.get("ERROR")+"' WHERE THDH='"+cds.getField("THDH", 0)+"'";
					execSQL(o2o,sql,new HashMap());
				}
			} catch (Exception e) {
				map.put("STATE", "0");
				e.printStackTrace();
				throw e;
			}
			return map;
		}
		
		@RequestMapping("/queryXsddInfo.action")
		public Map queryXsddInfo(String json) throws Exception{
			Map resultMap = new HashMap();
			ObjectMapper mapper = new ObjectMapper();
			List list =  (List)mapper.readValue(json, ArrayList.class); 
			Map map = (Map)list.get(0);
			JLQuery a = new JLQuery();
			List listResult = (List)a.queryForListByXML("o2o","com.jlsoft.o2o.sql.return.good.queryXsddInfo",map);
			resultMap.put("map", listResult);
			return resultMap;
		}
		
}
