package com.jlsoft.utils.rcl;

import com.jlsoft.utils.JLConn;
import java.sql.*;

public class ThreadRclWorkNode extends JLConn {

    public void doWork(String sGSID, java.sql.Date dat, String sProcName)
            throws Exception {
        boolean bError = false;
        String sErrorInfo = "";
        CallableStatement cstmt = null;
        PreparedStatement pstmt = null;
        long startTime = System.currentTimeMillis();
        long endTime = 0L;
        long clTime = 0L;
        try {
            openConnection();
            beginTrans();
            if (dat == null) {
                cstmt = basecn.prepareCall("{call " + sProcName + "(?)}");
                cstmt.setString(1, sGSID);
            } else {
                cstmt = basecn.prepareCall("{call " + sProcName + "(?,?)}");
                cstmt.setDate(1, dat);
                cstmt.setString(2, sGSID);

            }
            cstmt.executeUpdate();
            commitTrans();
            bError = false;
            sErrorInfo = "";
        } catch (Exception e) {
            bError = true;
            sErrorInfo = e.toString();
            rollBackTrans();
            e.printStackTrace();
        } finally {
            endTime = System.currentTimeMillis();
            clTime = (endTime - startTime) / 1000; // 记录到秒
            if (basecn != null) {

                /*
                 * RCL_PARALLEL 并行日处理表GSID 公司代码 varchar(10)PROC 过程名称 varchar(40)
                 * CLSC 处理时长 number(16,0) 以秒计算CLZT 处理状态 varchar(8) 正常完成或发生异常CLBZ
                 * 处理备注 varchar(255) 记录异常信息WCSJ 完成时间 date 默认取数据库当前时间
                 */
                try {
                    beginTrans();
                    pstmt = basecn.prepareStatement("insert into RCL_PARALLEL (GSID,PROC,CLSC,CLZT,CLBZ,WCSJ) values (?,?,?,?,?,sysdate)");
                    pstmt.setString(1, sGSID);
                    pstmt.setString(2, sProcName);
                    pstmt.setLong(3, clTime);
                    if (bError) {
                        pstmt.setString(4, "发生异常");
                    } else {
                        pstmt.setString(4, "正常完成");
                    }
                    pstmt.setString(5, sErrorInfo);
                    pstmt.executeUpdate();
                    commitTrans();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    try {
                        rollBackTrans();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            if (cstmt != null) {
                cstmt.close();
                cstmt = null;
            }
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
            closeConnection();
        }
    }
}
