package com.jlsoft.manageLogs.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.PubFun;

@Service("ManageLogs")
public class ManageLogs extends JLBill{
	/**
	 * @todo 写错误LOG记录
	 * @param map
	 * @throws Exception
	 */
	public void writeLogs(Map map) throws Exception{
		Map returnMap=new HashMap();
		int RZBH=PubFun.updateWBHZT(o2o,"W_MarkRecord",1);
		//RZBH:日志编号,DJLX:单据类型,YWDH:业务单号,DFHM:对方单号,CZR操作人,CZSJ操作时间,RZZT日志状态：0失败;1成功,ERROR错误信息
		String sql="INSERT INTO W_MarkRecord (RZBH,DJLX,YWDH,DFHM,CZR,CZSJ,RZZT,ERROR) " +
						 "VALUES ('"+RZBH+"','"+map.get("DJLX")+"','"+map.get("YWDH")+"'," +
						 "'"+map.get("DFHM")+"','"+map.get("CZR")+"',NOW()," +
						 "'"+map.get("RZZT")+"','"+map.get("ERROR")+"')";
		execSQL(o2o, sql, returnMap);
	}
	
	/**
	 * @todo 写错误日志
	 * @param DJLX 单据类型
	 * @param YWDH 业务单号
	 * @param DFHM 对方单号
	 * @param CZR 操作人
	 * @param RZZT 日志状态：0失败;1成功
	 * @param ERROR 错误信息
	 * @throws Exception
	 */
	public void writeLogs(int DJLX,String YWDH,String DFHM,String CZR,int RZZT,String ERROR) throws Exception{
		Map returnMap=new HashMap();
		int RZBH=PubFun.updateWBHZT(o2o,"W_MarkRecord",1);
		String sql="INSERT INTO W_MarkRecord (RZBH,DJLX,YWDH,DFHM,CZR,CZSJ,RZZT,ERROR) " +
		 "VALUES ('"+RZBH+"',"+DJLX+",'"+YWDH+"','"+DFHM+"','"+CZR+"',NOW(),"+RZZT+",'"+ERROR+"')";
		execSQL(o2o, sql, returnMap);
	}
	
}
