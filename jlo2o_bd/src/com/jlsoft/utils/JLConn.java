package com.jlsoft.utils;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.util.*;

/**
 *
 * <p>Title: 数据库连接类</p>
 *
 * <p>Description: 系统与数据库的连接类，关于数据库的连接及提交事务的基础类</p>
 *
 * <p>Copyright: 2005</p>
 *
 * <p>Company: JLSoftware</p>
 *
 * @author
 * @version V7.0
 */
public class JLConn {
  /**
   * 常量：基础连接。只能为本类和本类之子类所引用。
   */
  protected Connection basecn = null; //V7主引导连接

  protected Connection hrcn = null; //人事连接

  protected Connection vipcn = null; //会员连接

  protected Connection cwcn = null; //财务连接

  protected Connection shcn = null; //售后连接

  protected Connection jkcn = null; //与其它系统连接

  protected Connection oacn = null; //与OA系统连接

  protected Connection vipjkcn = null; //会员与其它系统连接

  protected Connection vipxfk1cn = null; //会员消费卡与其它系统连接

  protected Connection vipxfk2cn = null; //会员消费卡与其它系统连接
  
  protected Connection viptmq3cn = null; //会员消费卡与其它系统连接

  protected Connection cxcn = null; //与查询系统连接

  protected Connection SHWX =null; //SERV7 售后维修系统连接

  protected boolean is_SHWX_active = false;

  protected boolean is_basecn_active = false;

  protected boolean is_hrcn_active = false;

  protected boolean is_vipcn_active = false;

  protected boolean is_cwcn_active = false;

  protected boolean is_shcn_active = false;

  protected boolean is_jkcn_active = false;

  protected boolean is_oacn_active = false;

  protected boolean is_vipjkcn_active = false;

  protected boolean is_vipxfk1cn_active = false;

  protected boolean is_vipxfk2cn_active = false;
  
  protected boolean is_viptmq3cn_active = false;

  protected boolean is_cxcn_active = false;

  protected static final String MUTIL_CN_MSG = "不允许循环打开现有连接资源,请检查源代码!";

  protected static final String CLOSE_CN_MSG = "不允许未打开的连接进行关闭,请检查源代码!";
  /**
   * 此方法用于打开一个对外接口连接，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void openConnection(String sSub) throws Exception {
    Properties h = null;
    InitialContext ctx = null;
    DataSource ds = null;
    try {
      if (!JLDefsys.RES_LOADED_FLAG) {
        JLDefsys.loadRes();
      }
      if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("NO_POOL")) {
        Class.forName(JLDefsys.getDBInfo_CLS());
        if (sSub.equals(JLDB.HR)) {
          if (is_hrcn_active)
            throw new Exception(MUTIL_CN_MSG);
          hrcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_hrcn_active = true;
        }
        else if(sSub.equals(JLDB.SHWX)){
          if (is_SHWX_active)
            throw new Exception(MUTIL_CN_MSG);
           SHWX = DriverManager.getConnection(
            JLDefsys.getDBInfo_URL(),JLDefsys.getDBInfo_USER(),
            JLDefsys.getDBInfo_PWD());
           is_SHWX_active = true;
        }
        else if (sSub.equals(JLDB.SH)) {
          if (is_shcn_active)
            throw new Exception(MUTIL_CN_MSG);
          shcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_shcn_active = true;
        }
        else if (sSub.equals(JLDB.VIP)) {
          if (is_vipcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_vipcn_active = true;
        }
        else if (sSub.equals(JLDB.CW)) {
          if (is_cwcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cwcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_cwcn_active = true;
        }
        else if (sSub.equals(JLDB.JK)) {
          if (is_jkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          jkcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_jkcn_active = true;
        }
        else if (sSub.equals(JLDB.OA)) {
          if (is_oacn_active)
            throw new Exception(MUTIL_CN_MSG);
          oacn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_oacn_active = true;
        }
        else if (sSub.equals(JLDB.VIPJK)) {
          if (is_vipjkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipjkcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_vipjkcn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK1)) {
          if (is_vipxfk1cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk1cn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_vipxfk1cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK2)) {
          if (is_vipxfk2cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk2cn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_vipxfk2cn_active = true;
        }       
        else if (sSub.equals(JLDB.VIPTMQ3)) {
            if (is_viptmq3cn_active)
              throw new Exception(MUTIL_CN_MSG);
            viptmq3cn = DriverManager.getConnection(
                JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
                JLDefsys.getDBInfo_PWD()); //连接数据库字符串
            is_viptmq3cn_active = true;
          }
        else if (sSub.equals(JLDB.CX)) {
          if (is_cxcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cxcn = DriverManager.getConnection(
              JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
              JLDefsys.getDBInfo_PWD()); //连接数据库字符串
          is_cxcn_active = true;
        }

      }
      else if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("JBOSS_POOL")) {
        ctx = new InitialContext();
        ds = (DataSource) ctx.lookup("java:/" + sSub);

        if (sSub.equals(JLDB.HR)) {
          if (is_hrcn_active)
            throw new Exception(MUTIL_CN_MSG);
          hrcn = ds.getConnection();
          is_hrcn_active = true;
        }
        else if (sSub.equals(JLDB.SHWX)){
          if (is_SHWX_active)
              throw new Exception(MUTIL_CN_MSG);
          SHWX = ds.getConnection();
          is_SHWX_active = true;
        }
        else if (sSub.equals(JLDB.SH)) {
          if (is_shcn_active)
            throw new Exception(MUTIL_CN_MSG);
          shcn = ds.getConnection();
          is_shcn_active = true;
        }
        else if (sSub.equals(JLDB.VIP)) {
          if (is_vipcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipcn = ds.getConnection();
          is_vipcn_active = true;
        }
        else if (sSub.equals(JLDB.CW)) {
          if (is_cwcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cwcn = ds.getConnection();
          is_cwcn_active = true;
        }
        else if (sSub.equals(JLDB.JK)) {
          if (is_jkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          jkcn = ds.getConnection();
          is_jkcn_active = true;
        }
        else if (sSub.equals(JLDB.OA)) {
          if (is_oacn_active)
            throw new Exception(MUTIL_CN_MSG);
          oacn = ds.getConnection();
          is_oacn_active = true;
        }
        else if (sSub.equals(JLDB.VIPJK)) {
          if (is_vipjkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipjkcn = ds.getConnection();
          is_vipjkcn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK1)) {
          if (is_vipxfk1cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk1cn = ds.getConnection();
          is_vipxfk1cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK2)) {
          if (is_vipxfk2cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk2cn = ds.getConnection();
          is_vipxfk2cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPTMQ3)) {
            if (is_viptmq3cn_active)
              throw new Exception(MUTIL_CN_MSG);
            viptmq3cn = ds.getConnection();
            is_viptmq3cn_active = true;
          }
        else if (sSub.equals(JLDB.CX)) {
          if (is_cxcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cxcn = ds.getConnection();
          is_cxcn_active = true;
        }

      }
      else if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("WEBLOGIC_POOL")) {
        ctx = new InitialContext();
        ds = (DataSource) ctx.lookup(sSub);
        if (sSub.equals(JLDB.HR)) {
          if (is_hrcn_active)
            throw new Exception(MUTIL_CN_MSG);
          hrcn = ds.getConnection();
          is_hrcn_active = true;
        }
        else if (sSub.equals(JLDB.SHWX)) {
            if (is_SHWX_active)
                throw new Exception(MUTIL_CN_MSG);
            SHWX = ds.getConnection();
            is_SHWX_active = true;
        }
        else if (sSub.equals(JLDB.SH)) {
          if (is_shcn_active)
            throw new Exception(MUTIL_CN_MSG);
          shcn = ds.getConnection();
          is_shcn_active = true;
        }
        else if (sSub.equals(JLDB.VIP)) {
          if (is_vipcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipcn = ds.getConnection();
          is_vipcn_active = true;
        }
        else if (sSub.equals(JLDB.CW)) {
          if (is_cwcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cwcn = ds.getConnection();
          is_cwcn_active = true;
        }
        else if (sSub.equals(JLDB.JK)) {
          if (is_jkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          jkcn = ds.getConnection();
          is_jkcn_active = true;
        }
        else if (sSub.equals(JLDB.OA)) {
          if (is_oacn_active)
            throw new Exception(MUTIL_CN_MSG);
          oacn = ds.getConnection();
          is_oacn_active = true;
        }
        else if (sSub.equals(JLDB.VIPJK)) {
          if (is_vipjkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipjkcn = ds.getConnection();
          is_vipjkcn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK1)) {
          if (is_vipxfk1cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk1cn = ds.getConnection();
          is_vipxfk1cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK2)) {
          if (is_vipxfk2cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk2cn = ds.getConnection();
          is_vipxfk2cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPTMQ3)) {
            if (is_viptmq3cn_active)
              throw new Exception(MUTIL_CN_MSG);
            viptmq3cn = ds.getConnection();
            is_viptmq3cn_active = true;
          }
        else if (sSub.equals(JLDB.CX)) {
          if (is_cxcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cxcn = ds.getConnection();
          is_cxcn_active = true;
        }

      }
      else if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("WEBSPHERE_POOL")) {
        ctx = new InitialContext();
        ds = (DataSource) ctx.lookup("jdbc/" + sSub);
        if (sSub.equals(JLDB.HR)) {
          if (is_hrcn_active)
            throw new Exception(MUTIL_CN_MSG);
          hrcn = ds.getConnection();
          is_hrcn_active = true;
        }
        else if (sSub.equals(JLDB.SHWX)) {
            if (is_SHWX_active)
                throw new Exception(MUTIL_CN_MSG);
            SHWX = ds.getConnection();
            is_SHWX_active = true;
        }
        else if (sSub.equals(JLDB.SH)) {
          if (is_shcn_active)
            throw new Exception(MUTIL_CN_MSG);
          shcn = ds.getConnection();
          is_shcn_active = true;
        }
        else if (sSub.equals(JLDB.VIP)) {
          if (is_vipcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipcn = ds.getConnection();
          is_vipcn_active = true;
        }
        else if (sSub.equals(JLDB.CW)) {
          if (is_cwcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cwcn = ds.getConnection();
          is_cwcn_active = true;
        }
        else if (sSub.equals(JLDB.JK)) {
          if (is_jkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          jkcn = ds.getConnection();
          is_jkcn_active = true;
        }
        else if (sSub.equals(JLDB.OA)) {
          if (is_oacn_active)
            throw new Exception(MUTIL_CN_MSG);
          oacn = ds.getConnection();
          is_oacn_active = true;
        }
        else if (sSub.equals(JLDB.VIPJK)) {
          if (is_vipjkcn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipjkcn = ds.getConnection();
          is_vipjkcn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK1)) {
          if (is_vipxfk1cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk1cn = ds.getConnection();
          is_vipxfk1cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPXFK2)) {
          if (is_vipxfk2cn_active)
            throw new Exception(MUTIL_CN_MSG);
          vipxfk2cn = ds.getConnection();
          is_vipxfk2cn_active = true;
        }
        else if (sSub.equals(JLDB.VIPTMQ3)) {
            if (is_viptmq3cn_active)
              throw new Exception(MUTIL_CN_MSG);
            viptmq3cn = ds.getConnection();
            is_viptmq3cn_active = true;
          }
        else if (sSub.equals(JLDB.CX)) {
          if (is_cxcn_active)
            throw new Exception(MUTIL_CN_MSG);
          cxcn = ds.getConnection();
          is_cxcn_active = true;
        }

      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if (ctx != null) {
        ctx.close();
      }
      ctx = null;
      ds = null;
    }
  }

  /**
   * 此方法用于打开一个数据库连接，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void openConnection() throws Exception {
    Properties h = null;
    InitialContext ctx = null;
    DataSource ds = null;
    try {
      if (!JLDefsys.RES_LOADED_FLAG) {
        JLDefsys.loadRes();
      }

      if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("NO_POOL")) {
        if (is_basecn_active)
          throw new Exception(MUTIL_CN_MSG);
        Class.forName(JLDefsys.getDBInfo_CLS());
        basecn = DriverManager.getConnection(
            JLDefsys.getDBInfo_URL(), JLDefsys.getDBInfo_USER(),
            JLDefsys.getDBInfo_PWD()); //连接数据库字符串
        is_basecn_active = true;
      }
      else if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("JBOSS_POOL")) {
        ctx = new InitialContext();
        if (is_basecn_active)
          throw new Exception(MUTIL_CN_MSG);
        ds = (DataSource) ctx.lookup("java:/" + JLDefsys.POOL_JNDI);
        basecn = ds.getConnection();
        is_basecn_active = true;
      }
      else if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("WEBLOGIC_POOL")) {
        ctx = new InitialContext();
        if (is_basecn_active)
          throw new Exception(MUTIL_CN_MSG);
        ds = (DataSource) ctx.lookup(JLDefsys.POOL_JNDI);
        basecn = ds.getConnection();
        is_basecn_active = true;
      }
      else if (JLDefsys.CURRENT_CONNECTION_POOL_LX.equals("WEBSPHERE_POOL")) {
        ctx = new InitialContext();
        if (is_basecn_active)
          throw new Exception(MUTIL_CN_MSG);
        ds = (DataSource) ctx.lookup("jdbc/" + JLDefsys.POOL_JNDI);
        basecn = ds.getConnection();
        is_basecn_active = true;
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      if (ctx != null) {
        ctx.close();
      }
      ctx = null;
      ds = null;
    }
  }

  /**
   * 此方法用于回滚一个事务，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void rollBackTrans() throws Exception { //回滚事务
    try {
      if (basecn != null) {
        basecn.rollback();
      }
    }
    catch (Exception e) {
      throw e;
    }

  }

  /**
   * 此方法用于回滚一个对外接口事务，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void rollBackTrans(String sSub) throws Exception { //回滚事务
    try {
      if (sSub.equals(JLDB.HR)) {
        if (hrcn != null) {
          hrcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.SH)) {
        if (shcn != null) {
          shcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.VIP)) {
        if (vipcn != null) {
          vipcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.CW)) {
        if (cwcn != null) {
          cwcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.JK)) {
        if (jkcn != null) {
          jkcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.OA)) {
        if (oacn != null) {
          oacn.rollback();
        }
      }
      else if (sSub.equals(JLDB.VIPJK)) {
        if (vipjkcn != null) {
          vipjkcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.VIPXFK1)) {
        if (vipxfk1cn != null) {
          vipxfk1cn.rollback();
        }
      }
      else if (sSub.equals(JLDB.VIPXFK2)) {
        if (vipxfk2cn != null) {
          vipxfk2cn.rollback();
        }
      }
      else if (sSub.equals(JLDB.VIPTMQ3)) {
          if (viptmq3cn != null) {
            viptmq3cn.rollback();
          }
        }
      else if (sSub.equals(JLDB.CX)) {
        if (cxcn != null) {
          cxcn.rollback();
        }
      }
      else if (sSub.equals(JLDB.SHWX)){
        if (SHWX != null){
          SHWX.rollback();
        }
      }

    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 此方法用于关闭一个连接，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void closeConnection() throws Exception { //关闭连接
    try {
    /*  if (!is_basecn_active) {
        throw new Exception(CLOSE_CN_MSG);
      }*/

      if (basecn != null) {
        basecn.close();
        basecn = null;
        is_basecn_active = false;
      }

    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 此方法用于关闭一个对外接口连接，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void closeConnection(String sSub) throws Exception { //关闭连接
    try {
      if (sSub.equals(JLDB.HR)) {
        /*if (!is_hrcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (hrcn != null) {
          hrcn.close();
          is_hrcn_active = false;
        }
        hrcn = null;
      }
      else if (sSub.equals(JLDB.SHWX)) {
         /* if (!is_SHWX_active) {
              throw new Exception(CLOSE_CN_MSG);
          }*/
          if (SHWX != null) {
              SHWX.close();
              is_SHWX_active = false;
          }
          SHWX = null;
      }
      else if (sSub.equals(JLDB.SH)) {
        /*if (!is_shcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (shcn != null) {
          shcn.close();
          is_shcn_active = false;
        }
        shcn = null;
      }
      else if (sSub.equals(JLDB.VIP)) {
       /* if (!is_vipcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (vipcn != null) {
          vipcn.close();
          is_vipcn_active = false;
        }
        vipcn = null;
      }
      else if (sSub.equals(JLDB.CW)) {
        /*if (!is_cwcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (cwcn != null) {
          cwcn.close();
          is_cwcn_active = false;
        }
        cwcn = null;
      }
      else if (sSub.equals(JLDB.JK)) {
        /*if (!is_jkcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (jkcn != null) {
          jkcn.close();
          is_jkcn_active = false;
        }
        jkcn = null;
      }
      else if (sSub.equals(JLDB.OA)) {
        /*if (!is_oacn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (oacn != null) {
          oacn.close();
          is_oacn_active = false;
        }
        oacn = null;
      }
      else if (sSub.equals(JLDB.VIPJK)) {
       /* if (!is_vipjkcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (vipjkcn != null) {
          vipjkcn.close();
          is_vipjkcn_active = false;
        }
        vipjkcn = null;
      }
      else if (sSub.equals(JLDB.VIPXFK1)) {
        /*if (!is_vipxfk1cn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (vipxfk1cn != null) {
          vipxfk1cn.close();
          is_vipxfk1cn_active = false;
        }
        vipxfk1cn = null;
      }
      else if (sSub.equals(JLDB.VIPXFK2)) {
       /* if (!is_vipxfk2cn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (vipxfk2cn != null) {
          vipxfk2cn.close();
          is_vipxfk2cn_active = false;
        }
        vipxfk2cn = null;
      }
      
      else if (sSub.equals(JLDB.VIPTMQ3)) {          
           if (viptmq3cn != null) {
             viptmq3cn.close();
             is_viptmq3cn_active = false;
           }
           viptmq3cn = null;
         }
      else if (sSub.equals(JLDB.CX)) {
        /*if (!is_cxcn_active) {
          throw new Exception(CLOSE_CN_MSG);
        }*/
        if (cxcn != null) {
          cxcn.close();
          is_cxcn_active = false;
        }
        cxcn = null;
      }
    }
    catch (Exception e) {
      throw e;
    }

  }

  /**
   * 此方法用于提交一个事务，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void commitTrans() throws Exception { //提交事务
    try {
      if (basecn != null) {
        basecn.commit();
      }
    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 此方法用于提交一个对外接口事务，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void commitTrans(String sSub) throws Exception { //提交事务
    try {
      if (sSub.equals(JLDB.HR)) {
        if (hrcn != null) {
          hrcn.commit();
        }
      }
      else if (sSub.equals(JLDB.SH)) {
        if (shcn != null) {
          shcn.commit();
        }
      }
      else if (sSub.equals(JLDB.VIP)) {
        if (vipcn != null) {
          vipcn.commit();
        }
      }
      else if (sSub.equals(JLDB.CW)) {
        if (cwcn != null) {
          cwcn.commit();
        }
      }
      else if (sSub.equals(JLDB.JK)) {
        if (jkcn != null) {
          jkcn.commit();
        }
      }
      else if (sSub.equals(JLDB.OA)) {
        if (oacn != null) {
          oacn.commit();
        }
      }
      else if (sSub.equals(JLDB.VIPJK)) {
        if (vipjkcn != null) {
          vipjkcn.commit();
        }
      }
      else if (sSub.equals(JLDB.VIPXFK1)) {
        if (vipxfk1cn != null) {
          vipxfk1cn.commit();
        }
      }
      else if (sSub.equals(JLDB.VIPXFK2)) {
        if (vipxfk2cn != null) {
          vipxfk2cn.commit();
        }
      }
      else if (sSub.equals(JLDB.VIPTMQ3)) {
          if (viptmq3cn != null) {
            viptmq3cn.commit();
          }
        }
      else if (sSub.equals(JLDB.CX)) {
        if (cxcn != null) {
          cxcn.commit();
        }
      }
      else if (sSub.equals(JLDB.SHWX)) {
          if (SHWX != null) {
              SHWX.commit();
          }
      }
    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 此方法用于开始一个事务，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void beginTrans() throws Exception { //开始事务
    try {
      if (basecn != null) {
        basecn.setAutoCommit(false);
      }
    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 此方法用于开始一个对外接口事务，但只能为本类和本类之子类内部调用。
   * @throws JLException
   */
  protected void beginTrans(String sSub) throws Exception { //开始事务
    try {
      if (sSub.equals(JLDB.HR)) {
        if (hrcn != null) {
          hrcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.SH)) {
        if (shcn != null) {
          shcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.VIP)) {
        if (vipcn != null) {
          vipcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.CW)) {
        if (cwcn != null) {
          cwcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.JK)) {
        if (jkcn != null) {
          jkcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.OA)) {
        if (oacn != null) {
          oacn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.VIPJK)) {
        if (vipjkcn != null) {
          vipjkcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.VIPXFK1)) {
        if (vipxfk1cn != null) {
          vipxfk1cn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.VIPXFK2)) {
        if (vipxfk2cn != null) {
          vipxfk2cn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.VIPTMQ3)) {
          if (viptmq3cn != null) {
            viptmq3cn.setAutoCommit(false);
          }
        }
      else if (sSub.equals(JLDB.CX)) {
        if (cxcn != null) {
          cxcn.setAutoCommit(false);
        }
      }
      else if (sSub.equals(JLDB.SHWX)){
        if (SHWX !=null){
          SHWX.setAutoCommit(false);
        }
      }

    }
    catch (Exception e) {
      throw e;
    }
  }

}
