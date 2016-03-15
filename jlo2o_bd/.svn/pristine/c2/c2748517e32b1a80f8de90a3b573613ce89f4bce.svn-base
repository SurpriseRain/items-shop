package com.jlsoft.utils.rcl;

import com.jlsoft.utils.JLConn;
import com.jlsoft.utils.JLDefsys;
import com.jlsoft.utils.JLSql;
import com.jlsoft.utils.JLTools;
import java.sql.*;


/**
 * <p>Title: 日处理函数</p>
 *
 * <p>Description: 调用日处理的存储过程</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: JL Software Corp. </p>
 *
 * @author 李汉
 * @version 7.0
 */
public class RclFun extends JLConn {
  private java.sql.Date dRclRQ = null;
  private String sCurrentProc = "";
  private String sNextProc = "";
  private String sProcNote = "";
  private String sLocalHostIP = "";
  private int iProcParam = 0; //是否带参数,0:步带,1:带
  private int iProcType = -1; // 0:开始,1:结束,2:其他
  private int iProcExec = 0; //存储过程是否可执行,0:不执行,1:执行
  private int iInitLSKH = 0; //是否初始化零售客户
  //private int iSJTCData = 0; //是否写入司机提成数据
  private int iFirst = 0; //RCL表中是否已写入WRITE_RCL_BEGIN;

  public RclFun() {
  }

  public void doRcl() throws Exception {
    try {
      if (JLDefsys.isBeginRcl) {throw new Exception("日处理已经开始执行,禁止同一时刻重复执行");}
      JLDefsys.isBeginRcl=true;
      sLocalHostIP = JLTools.getLocalHostIP();
      openConnection();

      //日处理开始
      initRcl();
      if (sCurrentProc.equals("WRITE_RCL_BEGIN") && (iFirst == 0)){
         execRclProc();
         JLTools.writeXmlFile(JLTools.dateToStr(dRclRQ) + "日处理开始", JLDefsys.RCL_LOG_FILE);
      }

      getNextRclProc();

      //处理日处理的过程
      while (iProcType == 2) {
          execRclProc();
          getNextRclProc();
      }

      //日处理结束
      if (iProcType == 1) {
          execRclProc();
          JLTools.writeXmlFile(JLTools.dateToStr(dRclRQ) + "日处理结束", JLDefsys.RCL_LOG_FILE);
      }
      JLDefsys.isBeginRcl=false;
    }
    catch (Exception e) {
      JLDefsys.isBeginRcl=false;
      e.printStackTrace();
      JLTools.writeXmlFile(sProcNote + ":" + e.getMessage(),
                           JLDefsys.RCL_LOG_FILE);
      throw e;
    }
    finally {
      closeConnection();
    }
  }
  /**
   * 取的日处理进行的初始条件:日期,过程等
   * @throws Exception
   */
  private void initRcl() throws Exception {
    CallableStatement cstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sTmp = "";
    try {
      sProcNote = "WRITE_RCL_BEGIN";
      cstmt = basecn.prepareCall("{call WRITE_RCL_BEGIN(?,?,?,?,?)}");
      cstmt.registerOutParameter(1,java.sql.Types.VARCHAR);
      cstmt.registerOutParameter(2,java.sql.Types.VARCHAR);
      cstmt.registerOutParameter(3,java.sql.Types.DATE);
      cstmt.registerOutParameter(4,java.sql.Types.NUMERIC);
      cstmt.registerOutParameter(5,java.sql.Types.NUMERIC);
      //cstmt.registerOutParameter(6,java.sql.Types.NUMERIC);
      cstmt.executeUpdate();
      sCurrentProc = cstmt.getString(1);
      sNextProc = cstmt.getString(2);
      dRclRQ = cstmt.getDate(3);
      iInitLSKH = cstmt.getInt(4);
      iFirst = cstmt.getInt(5);
      //iSJTCData = cstmt.getInt(6);
      iProcType = 0;
      iProcExec = 0;
      iProcParam = 0;
      if(sCurrentProc.equals("") ) {
        throw new Exception("过程未定义");
      }

      stmt = basecn.createStatement();
      sTmp = JLSql.getSysDate();
      rs = stmt.executeQuery(sTmp);
      if (rs.next()) {
        if (dRclRQ.getTime() > rs.getDate("JLDATE").getTime()) {
            throw new Exception("日结操作只能处理本日之前的数据");
         }
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if (cstmt!=null){	
        cstmt.close();
        cstmt = null;
      }
      if (rs!=null) {
        rs.close();
        rs = null;
      }
      if (stmt!=null) {
        stmt.close();     
        stmt = null;
      }
     
    }
  }

  /**
   * 取下一步过程
   * @throws Exception
   */
  private void getNextRclProc() throws Exception {
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = basecn.createStatement();
      rs = stmt.executeQuery("select JLPR01, JLPR02, JLPR03, JLPR04, JLPR05, JLPR06 "
                            +" from JLPROC where JLPR01 = "+ JLTools.getStrQuot(sNextProc));
      if (rs.next()) {
         sCurrentProc = rs.getString("JLPR01");
         sNextProc = rs.getString("JLPR02");
         sProcNote = rs.getString("JLPR03");
         iProcParam = rs.getInt("JLPR04");
         iProcType = rs.getInt("JLPR05");
         iProcExec = rs.getInt("JLPR06");
      }
      else {
        throw new Exception(sNextProc + "未定义");
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if (rs!=null){	
      rs.close();
      rs = null;
      }
      if (stmt!=null){
    	stmt.close();     
        stmt = null;
      }
    }
  }
  /**
   * 执行日处理过程
   * @param sStoreName String 存储过程名称
   * @param iType int 0:不带参数;1:带日期参数
   * @param iStatus int 日处理步骤
   * @throws Exception
   */
  private void execRclProc() throws Exception {
    CallableStatement cstmt = null;
    Statement ps = null;
    ResultSet rs = null;
    String sSql, sSJ = "";
    try {
      beginTrans();
      //初始化零售客户
      if (iInitLSKH == 1){
        cstmt = basecn.prepareCall(
            "INSERT INTO WLDW(WLDW01, WLDW20, WLDW02) "
            + " VALUES(" + JLTools.getStrQuot("A00001") + ",-1," + JLTools.getStrQuot("零售单位") + ")");
        cstmt.executeUpdate();
        cstmt = basecn.prepareCall(
            "INSERT INTO CKKQ(KQ01, CK01, CKK_KQ01, KQ02) "
            + " VALUES(" + JLTools.getStrQuot("00") + ","
                         + JLTools.getStrQuot("00") + ","
                         + JLTools.getStrQuot("00") + ","
                         + JLTools.getStrQuot("无") + ")");
        cstmt.executeUpdate();
        iInitLSKH = 0;
      }
     //fuly modi 20120813 去掉，直接在回执时写
     /* if (iSJTCData == 1) {  //写入司机提成数据
        try {
          openConnection(JLDB.SH);
          beginTrans(JLDB.SH);

          sSql =
              "SELECT SJTC01, LSDI01, SJTC02, SPXX01, KHZL01, SJTC03, GSXX01 " +
              "  FROM SJTC WHERE SJTC01 = " + JLSql.dateToDateStr(dRclRQ);
          ps = shcn.createStatement();
          rs = ps.executeQuery(sSql);
          while (rs.next()) {
            if (rs.getString(6)!=null) {
              sSJ = rs.getString(6);
            }
            cstmt = basecn.prepareCall("INSERT INTO SJTC(SJTC01,LSDI01,SJTC02," +
                  "            SPXX01,KHZL01,SJTC03,GSXX01,SJTC04) "
                  + " VALUES(" + JLSql.dateToDateStr(rs.getDate(1)) + "," +
                  + rs.getLong(2) + ","
                  + rs.getFloat(3) + ","
                  + rs.getInt(4) + ","
                  + rs.getInt(5) + ",'"
                  + sSJ + "','"
                  + rs.getString(7) + "',"
                  + " (SELECT LSDI06 FROM LSDITEM WHERE GSXX01 = '" + rs.getString(7) + "'"
                  + "    AND LSDI01 = " + rs.getLong(2) + "))"
                );
            cstmt.executeUpdate();
          }
          sSql =
              "DELETE FROM SJTC WHERE SJTC01 = " + JLSql.dateToDateStr(dRclRQ);
          ps = shcn.createStatement();
          ps.executeUpdate(sSql);

          commitTrans(JLDB.SH); //事务提交
          iSJTCData = 0;
        }
        catch (Exception e) {
          rollBackTrans(JLDB.SH);
          throw e;
        }
        finally {
          closeConnection(JLDB.SH); //关闭连接
          if (rs!=null){
        	  rs.close(); 
        	  rs = null;
          }
          if (ps!=null){
            ps.close();          
            ps = null;
          }
          
        }
      }*/

      //写每步处理的时间,防止重复操作
      cstmt = basecn.prepareCall("INSERT INTO RCLPROC(RCLP01, RCLP02, RCLP03, RCLP04) "
                               + " VALUES(?,?," + JLSql.getSqlDateTime()+ ",?)");
      cstmt.setDate(1, dRclRQ);
      cstmt.setString(2, sCurrentProc);
      cstmt.setString(3, sLocalHostIP);
      cstmt.executeUpdate();
      //调用业务处理过程
      if (iProcExec == 1){
          if (iProcParam == 0) {
            cstmt = basecn.prepareCall("{call " + sCurrentProc + "()}");
          }
          else {
            cstmt = basecn.prepareCall("{call " + sCurrentProc + "(?)}");
            cstmt.setDate(1, dRclRQ);
          }
          cstmt.executeUpdate();
      }
      //写日处理步骤
      cstmt = basecn.prepareCall("{call WRITE_RCL_STATUS(?,?,?)}");
      cstmt.setDate(1, dRclRQ);
      cstmt.setString(2, sCurrentProc);
      cstmt.setInt(3, iProcType);
      cstmt.executeUpdate();

      commitTrans();
    }
    catch (Exception e) {
      rollBackTrans();
      throw e;
    }
    finally {
      if (cstmt!=null){
        cstmt.close();
        cstmt = null;
      }
    }
  }

}
