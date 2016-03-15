package com.jlsoft.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.jlsoft.framework.JLBill;

@Service("PubGetBH")
public class PubGetBH extends JLBill{
	/**
	 * @todo 获取增加编号
	 * @param conn  连接库
	 * @param sTblName  更新表
	 * @param sSize  增加幅度
	 * @return
	 * @throws Exception
	 */
	public int getWBHZT(JdbcTemplate conn,String sTblName,int sSize) throws Exception{
		int jlbh = -1;
		Map row = new HashMap();
		String sql = "update W_BHZT set REC_NUM = REC_NUM+"+sSize+" where TBLNAME = '"+sTblName+"'";
		int row_count = execSQL(conn,sql,row);
		if(row_count == 0){
			sql = "insert into W_BHZT(TBLNAME,REC_NUM) values('"+sTblName+"',"+sSize+")";
			execSQL(conn,sql,row);
		}
		sql = "select  REC_NUM from W_BHZT where TBLNAME = '"+sTblName+"'";
		return queryForInt(conn,sql);
	}
	
}
