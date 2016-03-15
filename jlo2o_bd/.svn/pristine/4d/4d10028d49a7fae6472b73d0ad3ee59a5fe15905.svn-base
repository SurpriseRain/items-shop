package com.jlsoft.utils;

import java.lang.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.CallableStatementCallback;

/**
 * 
 * <p>Title: </p>
 * 
 * <p>Description: 调用公共的存储过程</p>
 * 
 * <p>Copyright: Copyright (c) 2005</p>
 * 
 * <p>Company: JL Software Corp.</p>
 * 
 * @author 李汉
 * @version 7.0
 */

public class PubFun {
	//调用存储过程根据表获取编号(workflow)  待删除
	@SuppressWarnings("unchecked")
	public static int callProcedureForJLBH(JdbcTemplate jdbcTemplate,String tableName,int step){
		LinkedList inParameter = new LinkedList();
  		inParameter.add(tableName);
        inParameter.add(step);
        LinkedList outParameter = new LinkedList();
        outParameter.add(0);
        outParameter = callProc("{call UPDATE_BHZT(?,?,?)}", inParameter, outParameter, jdbcTemplate);
        return ((Integer)(outParameter.get(0))).intValue();
	}
	
	/**
	 * @todo O2O取编号状态值
	 * @param jdbcTemplate
	 * @param sTblName
	 * @param sSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static int updateWBHZT(JdbcTemplate jdbcTemplate,String sTblName,int sSize) throws Exception {
        LinkedList inParameter = new LinkedList();
        inParameter.add(sTblName);
        inParameter.add(sSize);
        LinkedList outParameter = new LinkedList();
        outParameter.add(0);        
        outParameter = callProc("{call Update_WBHZT(?,?,?)}", inParameter, outParameter, jdbcTemplate);
        return ((Integer)(outParameter.get(0))).intValue();
    }
	
	/**
	 * 返回基础信息的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int updateBHZTJC(String sTblName, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call Update_JCBHZT(?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.setString(1, sTblName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(2);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 返回基础信息的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int updateBHZTJC_LX(String sTblName, String sLX, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call UPDATE_JCBHZT_LX(?,?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sTblName);
			cstmt.setString(2, sLX);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 返回单据的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static long updateBHZTDJ(String sGSID, String sTblName, Connection cn)
			throws Exception {
		long iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call Update_DJBHZT(?,?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sTblName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getLong(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static long updateBHZTDJ(String sGSID, String sTblName,
			JdbcTemplate jdbcTemplate) {

		LinkedList inParameter = new LinkedList();
		inParameter.add(sGSID);
		inParameter.add(sTblName);
		LinkedList outParameter = new LinkedList();
		outParameter.add(0l);
		outParameter = callProc("{call Update_DJBHZT(?,?,?)}", inParameter,
				outParameter, jdbcTemplate);
		return ((Long) (outParameter.get(0))).longValue();

	}

	public static long getLSDBH(String sGSID, String sTblName, String sSKR,
			JdbcTemplate jdbcTemplate) {
		LinkedList inParams = new LinkedList();
		inParams.add(sGSID);
		inParams.add(sTblName);
		inParams.add(sSKR);
		LinkedList outParams = new LinkedList();
		outParams.add(0l);
		outParams = callProc("{call UPDATE_TSBHZTGS(?,?,?,?)}", inParams,
				outParams, jdbcTemplate);
		return ((Long) (outParams.get(0))).longValue();
	}

	public static LinkedList callProc(String sql, final LinkedList inP,
			final LinkedList outP, JdbcTemplate jdbcTemplate) {
		final LinkedList outData = new LinkedList();
		jdbcTemplate.execute(sql, new CallableStatementCallback() {

			public Object doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				// 设置输入参数
				if (inP != null) {
					for (int i = 0; i < inP.size(); i++) {
						cs.setObject(i + 1, inP.get(i));
						// if
						// (inP.get(i).getClass().toString().equals("class java.lang.String"))
						// {
						// cs.setString(i + 1, (String) inP.get(i));
						// } else if
						// (inP.get(i).getClass().toString().equals("class java.lang.Integer"))
						// {
						// cs.setInt(i + 1, (Integer) inP.get(i));
						// } else if
						// (inP.get(i).getClass().toString().equals("class java.lang.Long"))
						// {
						// cs.setLong(i + 1, (Long) inP.get(i));
						// } else if
						// (inP.get(i).getClass().toString().equals("class java.lang.Double"))
						// {
						// cs.setDouble(i + 1, (Double) inP.get(i));
						// }
					}
				}
				// 注册输出参数
				if (outP != null) {
					for (int i = 0; i < outP.size(); i++) {
						if (outP.get(i).getClass().toString()
								.equals("class java.lang.String")) {
							cs.registerOutParameter(i + inP.size() + 1,
									java.sql.Types.VARCHAR);
						} else if (outP.get(i).getClass().toString()
								.equals("class java.lang.Integer")) {
							cs.registerOutParameter(i + inP.size() + 1,
									java.sql.Types.INTEGER);
						} else if (outP.get(i).getClass().toString()
								.equals("class java.lang.Long")) {
							cs.registerOutParameter(i + inP.size() + 1, java.sql.Types.BIGINT);
						} else if (outP.get(i).getClass().toString()
								.equals("class java.lang.Double")) {
							cs.registerOutParameter(i + inP.size() + 1, java.sql.Types.DOUBLE);
						}
					}
				}
				cs.execute();

				if (outP != null) {
					// 获得非结果集的返回值
					for (int i = 0; i < outP.size(); i++) {
						if (outP.get(i).getClass().toString()
								.equals("class java.lang.String")) {
							outData.add(cs.getString(i + inP.size() + 1));
						} else if (outP.get(i).getClass().toString()
								.equals("class java.lang.Integer")) {
							outData.add(cs.getInt(i + inP.size() + 1));
						} else if (outP.get(i).getClass().toString()
								.equals("class java.lang.Long")) {
							outData.add(cs.getLong(i + inP.size() + 1));
						} else if (outP.get(i).getClass().toString()
								.equals("class java.lang.Double")) {
							outData.add(cs.getDouble(i + inP.size() + 1));
						}
					}
				}
				return outData;
			}
		});
		return outData;
	}

	/**
	 * 返回售后单据的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int updateBHZTCS(String sGSID, String sTblName, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call Update_CSBHZT(?,?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sTblName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}
	/**
	 * 服务量公共函数cds,送货数量,购自商场部门,时间,增OR减
	 * @param TblName String
	 * @param cn Connection
	 * @return null
	 * @throws Exception
	 */
	public static void update_delte_insert_FWL(double SL, String GZBM,
			long KHZL01, String datetime, int lx, String GSXX01, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call UPDATE_GZSCFWL(?,?,?,?,?,?)}");
			cstmt.setDouble(1, SL);
			cstmt.setString(2, GZBM);
			cstmt.setLong(3, KHZL01);
			cstmt.setString(4, datetime);
			cstmt.setInt(5, lx);
			cstmt.setString(6, GSXX01);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static void update_delte_insert_FWLAZD(double SL, String GZBM,
			long KHZL01, String datetime, int lx, String GSXX01, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call UPDATE_GZSCFWLAZD(?,?,?,?,?,?)}");
			cstmt.setDouble(1, SL);
			cstmt.setString(2, GZBM);
			cstmt.setLong(3, KHZL01);
			cstmt.setString(4, datetime);
			cstmt.setInt(5, lx);
			cstmt.setString(6, GSXX01);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}
	/**
	 * 返回财务单据的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int updateBHZTCW(String sGSID, String sTblName, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call Update_CWBHZT(?,?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sTblName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 返回VIP单据的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int updateBHZTVIP(String sGSID, String sTblName, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call Update_VIPBHZT(?,?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sTblName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 返回人事单据的最大编号
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int updateBHZTHR(String sGSID, String sTblName, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call Update_HRBHZT(?,?,?)}"); // 更新编号状态
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sTblName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 取零售单的最大编号
	 * @param sComputerName String 机器名称
	 * @param cn Connection
	 * @return int 分配给该机器的编号
	 * @throws Exception
	 */
	public static int updateBHZTSYT(String sGSID, String sComputerName,
			Connection cn) throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call UPDATE_SYTBHZT(?,?,?)}");
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sComputerName);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static String read_CZK(String sGSID, String sJMKH, Connection cn)
			throws Exception {
		String sRet = "";
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call read_CZK(?,?,?,?)}");
			cstmt.setString(1, sGSID);
			cstmt.setString(2, sJMKH);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			sRet = cstmt.getInt(3) + ";" + cstmt.getString(4);
			return sRet;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 取凭证编号的最大编号
	 * @param sComputerName String 机器名称
	 * @param cn Connection
	 * @return int 分配给该机器的编号
	 * @throws Exception
	 */
	public static int updateBHZTPZ(String sZTID, String sYear, String sMonth,
			String sPZLX, Connection cn) throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call UPDATE_PZBHZT(?,?,?,?,?)}");
			cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
			cstmt.setString(1, sZTID);
			cstmt.setString(2, sPZLX);
			cstmt.setInt(3, JLTools.strToInt(sYear));
			cstmt.setInt(4, JLTools.strToInt(sMonth));
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(5);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 取商品型号编号的最大编号
	 * @param sPPB String 品牌代码
	 * @param sSPFL String 商品分类代码
	 * @param cn Connection
	 * @return int 分配给该商品的编号
	 * @throws Exception
	 */
	public static int GET_MaxNo(String sPPB, String sSPFL, Connection cn)
			throws Exception {
		int iJLBH = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call GET_SPXH_BH(?,?,?)}");
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setString(1, sPPB);
			cstmt.setString(2, sSPFL);
			cstmt.executeUpdate();
			iJLBH = cstmt.getInt(3);
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 修改CKSP的可卖数
	 * @param sBM01 String 部门
	 * @param sCK01 String 仓库
	 * @param sWLDW01 String 供货商
	 * @param iSP_ID int 商品内码
	 * @param iTJBJ int 特价标记
	 * @param iSIZE int 包装含量
	 * @param dSL double 数量 +:加, -:减
	 * @param cn Connection 连接
	 * @throws Exception
	 */
	public static void setSP_KMS(String sBM01, String sCK01, String sWLDW01,
			int iSP_ID, int iTJBJ, double dSIZE, double dSL, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SETSP_KMSL(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setString(3, sWLDW01);
			cstmt.setInt(4, iSP_ID);
			cstmt.setInt(5, iTJBJ);
			cstmt.setDouble(6, dSIZE);
			cstmt.setDouble(7, dSL);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static void setSP_DFKMS(String sBM01, String sCK01, String sWLDW01,
			int iSP_ID, int iTJBJ, double dSIZE, double dSL, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SETSP_DFKMSL(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setString(3, sWLDW01);
			cstmt.setInt(4, iSP_ID);
			cstmt.setInt(5, iTJBJ);
			cstmt.setDouble(6, dSIZE);
			cstmt.setDouble(7, dSL);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}
	/**
	 * 修改CKSP的保管帐数量
	 * @param sBM01 String 部门
	 * @param sCK01 String 仓库
	 * @param sWLDW01 String 供货商
	 * @param iSP_ID int 商品内码
	 * @param iTJBJ int 特价标记
	 * @param iSIZE int 包装含量
	 * @param dSL double 数量 +:加, -:减
	 * @param cn Connection 连接
	 * @throws Exception
	 */
	public static void setSP_BGZ(String sBM01, String sCK01, String sWLDW01,
			int iSP_ID, int iTJBJ, double dSIZE, double dSL, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SETSP_BGZSL(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setString(3, sWLDW01);
			cstmt.setInt(4, iSP_ID);
			cstmt.setInt(5, iTJBJ);
			cstmt.setDouble(6, dSIZE);
			cstmt.setDouble(7, dSL);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 修改CKSP的三级帐数量
	 * @param sBM01 String 部门
	 * @param sCK01 String 仓库
	 * @param sWLDW01 String 供货商
	 * @param iSP_ID int 商品内码
	 * @param iTJBJ int 特价标记
	 * @param iSIZE int 包装含量
	 * @param dSL double 数量 +:加, -:减
	 * @param cn Connection 连接
	 * @throws Exception
	 */
	public static void setSP_SJZ(String sBM01, String sCK01, String sWLDW01,
			int iSP_ID, int iTJBJ, double dSIZE, double dSL, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SETSP_SJZSL(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setString(3, sWLDW01);
			cstmt.setInt(4, iSP_ID);
			cstmt.setInt(5, iTJBJ);
			cstmt.setDouble(6, dSIZE);
			cstmt.setDouble(7, dSL);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 修改CKSP的延付帐数量
	 * @param sBM01 String 部门
	 * @param sCK01 String 仓库
	 * @param sWLDW01 String 供货商
	 * @param iSP_ID int 商品内码
	 * @param iTJBJ int 特价标记
	 * @param iSIZE int 包装含量
	 * @param dSL double 数量 +:减, -:加
	 * @param cn Connection 连接
	 * @throws Exception
	 */
	public static void setSP_YFZ(String sBM01, String sCK01, String sWLDW01,
			int iSP_ID, int iTJBJ, double dSIZE, double dSL, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SETSP_YFSL(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setString(3, sWLDW01);
			cstmt.setInt(4, iSP_ID);
			cstmt.setInt(5, iTJBJ);
			cstmt.setDouble(6, dSIZE);
			cstmt.setDouble(7, dSL);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}
	/**
	 * 存入赠品保管明细
	 * @param sBM01 String 部门
	 * @param sCK01 String 仓库
	 * @param sSPID String 赠品代码
	 * @param iPJLX int 票据类型
	 * @param iPJBH int 票据编号
	 * @param iDJSL double 单据数量
	 * @param sBZ String 备注
	 * @param cn Connection 连接
	 * @throws Exception
	 */
	public static void WRITE_ZPKPZ(String sBM01, String sCK01, String sSPID,
			int iPJLX, long iPJBH, double iDJSL, String sBZ, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call WRITE_ZPKPZ(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setString(3, sSPID);
			cstmt.setInt(4, iPJLX);
			cstmt.setLong(5, iPJBH);
			cstmt.setDouble(6, iDJSL);
			cstmt.setString(7, sBZ);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}
	/**
	 * 写WDZ商品未达帐
	 * @param sBM01 String 部门
	 * @param sCK01 String 仓库
	 * @param iDJLB int 单据类别
	 * @param iDJHM int 单据号码
	 * @param iKMS int 可卖数标记
	 * @param iBGZ int 保管帐标记
	 * @param iSJZ int 三级帐标记
	 * @param cn Connection
	 * @throws Exception
	 */
	public static void setSP_WDZ(String sBM01, String sCK01, int iDJLB,
			long iDJHM, int iKMS, int iBGZ, int iSJZ, Connection cn) throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SET_WDZ(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setInt(3, iDJLB);
			cstmt.setLong(4, iDJHM);
			cstmt.setInt(5, iKMS);
			cstmt.setInt(6, iBGZ);
			cstmt.setInt(7, iSJZ);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 写DWYEZ,修改往来单位余额帐
	 * @param sBM01 String 部门
	 * @param sWLDW01 String 往来单位
	 * @param dDKYE double 货款余额
	 * @param dYUFK double 预付款余额
	 * @param dYFYE double 应付款余额
	 * @param dYSYE double 应收款余额
	 * @param dFLYE double 返利余额
	 * @param dYSSR double 收入余额
	 * @param cn Connection
	 * @throws Exception
	 */

	public static void setDW_YEZ(String sBM01, String sWLDW01, double dDKYE,
			double dYUFK, double dYFYE, double dYSYE, double dFLYE, double dYSSRYW,
			double dYSSRCW, double dYSSRWS, Connection cn) throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SET_DWYEZ(?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sWLDW01);
			cstmt.setDouble(3, dDKYE);
			cstmt.setDouble(4, dYUFK);
			cstmt.setDouble(5, dYFYE);
			cstmt.setDouble(6, dYSYE);
			cstmt.setDouble(7, dFLYE);
			cstmt.setDouble(8, dYSSRYW);
			cstmt.setDouble(9, dYSSRCW);
			cstmt.setDouble(10, dYSSRWS);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}
	// 写业务员余额帐
	public static void setYWY_YEZ(String sBM01, String sWLDW01, String sRYXX01,
			double dXYED, double dYSYE, double dYFYE, double QPYE, Connection cn)
			throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call SET_YWYYEZ(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sWLDW01);
			cstmt.setString(3, sRYXX01);
			cstmt.setDouble(4, dXYED);
			cstmt.setDouble(5, dYSYE);
			cstmt.setDouble(6, dYFYE);
			cstmt.setDouble(7, QPYE);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static void WRITE_KHMXZ(String sGSID, int iDJLX, long iDH, String sBM,
			String sWLDW, double dJE, Connection cn) throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call WRITE_KHMXZ(?,?,?,?,?,?,?)}");
			cstmt.setString(1, sGSID);
			cstmt.setLong(2, iDH);
			cstmt.setInt(3, iDJLX);
			cstmt.setString(4, sBM);
			cstmt.setString(5, sWLDW);
			cstmt.setDouble(6, dJE);
			cstmt.setInt(7, 0);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 
	 * @param sTblName String 表名
	 * @param iJLBH int 单据号码
	 * @param iDel int 标记,0:修改,1:删除
	 * @param cn Connection
	 * @throws Exception
	 */
	public static void setDJ_DEL(String sGSID, String sTblName, long iJLBH,
			String sTimeStamp, int iDel, Connection cn) throws Exception {
		CallableStatement cstmt = null;
		String sSQL = "";
		try {
			// 入库单
			if (sTblName.equals("RKD")) {
				sSQL = "{call EXEC_RKD_DEL(?,?,?,?)}";
			}
			// 调拨单
			else if (sTblName.equals("DBD")) {
				sSQL = "{call EXEC_DBD_DEL(?,?,?,?)}";
			}
			// 转仓单
			else if (sTblName.equals("ZCD")) {
				sSQL = "{call EXEC_ZCD_DEL(?,?,?,?)}";
			}
			// 返厂单
			else if (sTblName.equals("FCD")) {
				sSQL = "{call EXEC_FCD_DEL(?,?,?,?)}";
			}
			// 批发单
			else if (sTblName.equals("PFD")) {
				sSQL = "{call EXEC_PFD_DEL(?,?,?,?)}";
			}
			// 延付单
			else if (sTblName.equals("YFD")) {
				sSQL = "{call EXEC_YFD_DEL(?,?,?,?)}";
			}
			// 返修单
			else if (sTblName.equals("FXD")) {
				sSQL = "{call EXEC_FXD_DEL(?,?,?,?)}";
			}
			// 组装单
			else if (sTblName.equals("ZZD")) {
				sSQL = "{call EXEC_ZZD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("CYD")) {
				sSQL = "{call EXEC_CYD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("FXSQD")) {
				sSQL = "{call EXEC_FXSQD_DEL(?,?,?,?)}";
			}
			// 返厂申请单
			else if (sTblName.equals("FCSQD")) {
				sSQL = "{call EXEC_FCSQD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("ZCSQD")) {
				sSQL = "{call EXEC_ZCSQD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("JJRKD")) {
				sSQL = "{call EXEC_JJRKD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("JJCKD")) {
				sSQL = "{call EXEC_JJCKD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("JJJSD")) {
				sSQL = "{call EXEC_JJJSD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("YJHXJD")) {
				sSQL = "{call EXEC_YJHXD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("JDXXBT")) {
				sSQL = "{call EXEC_JDXXBT_DEL(?,?,?,?)}";
			} else if (sTblName.equals("ZBZCD")) {
				sSQL = "{call EXEC_ZBZCD_DEL(?,?,?,?)}";
			} else if (sTblName.equals("GCKK")) {
				sSQL = "{call EXEC_GCKK_DEL(?,?,?,?)}";
			} else if (sTblName.equals("YHDGL")) {
				sSQL = "{call EXEC_YHDGL_DEL(?,?,?,?)}";
			}
			cstmt = cn.prepareCall(sSQL);
			cstmt.setLong(1, iJLBH);
			cstmt.setInt(2, iDel);
			cstmt.setString(3, sGSID);
			cstmt.setString(4, sTimeStamp);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static void WriteBMSP_KPZ(String sBM01, String sCK01, int p_PCH,
			int p_SXH, String p_JHDW, String p_XSDW, int p_SPID, int p_PJLX,
			int p_PJBH, double p_PJSL, double p_CBDJ, double p_XSDJ,
			double p_TAX_RATE, String p_FPHM, int p_YFZ, String p_BZ, int p_TJBZ,
			Connection cn) throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call WRITE_BMSPKPZ(?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?)}");
			cstmt.setString(1, sBM01);
			cstmt.setString(2, sCK01);
			cstmt.setInt(3, p_PCH);
			cstmt.setInt(4, p_SXH);
			cstmt.setString(5, p_JHDW);
			cstmt.setString(6, p_XSDW);
			cstmt.setInt(7, p_SPID);
			cstmt.setInt(8, p_PJLX);
			cstmt.setInt(9, p_PJBH);
			cstmt.setDouble(10, p_PJSL);
			cstmt.setDouble(11, p_CBDJ);
			cstmt.setDouble(12, p_XSDJ);
			cstmt.setDouble(13, p_TAX_RATE);
			cstmt.setString(14, p_FPHM);
			cstmt.setInt(15, p_YFZ);
			cstmt.setString(16, p_BZ);
			cstmt.setInt(17, p_TJBZ);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static void WriteCKSP_KPZ(String p_DEPTID, String p_CK, String p_WLDW,
			int p_SPID, int p_PJLX, long p_PJBH, double p_DJSL, String p_BZ,
			int p_TJBZ, Connection cn) throws Exception {
		CallableStatement cstmt = null;
		try {
			cstmt = cn.prepareCall("{call WRITE_CKSPKPZ(?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, p_DEPTID);
			cstmt.setString(2, p_CK);
			cstmt.setString(3, p_WLDW);
			cstmt.setInt(4, p_SPID);
			cstmt.setInt(5, p_PJLX);
			cstmt.setLong(6, p_PJBH);
			cstmt.setDouble(7, p_DJSL);
			cstmt.setString(8, p_BZ);
			cstmt.setInt(9, p_TJBZ);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	public static void SET_YHYEZ(int p_DJLX, long p_DJHM, String p_YHDM,
			String p_YHZH, String p_SKFS, double p_CR, double p_ZC, String p_GSXX01,
			String p_WLDW01, String p_WLDW02, String p_BZ, JdbcTemplate cn)
			throws Exception {
		LinkedList<Object> inParameter = new LinkedList<Object>();
		inParameter.add(p_DJLX);
		inParameter.add(p_DJHM);
		inParameter.add(p_YHDM);
		inParameter.add(p_YHZH);
		inParameter.add(p_SKFS);
		inParameter.add(p_CR);
		inParameter.add(p_ZC);
		inParameter.add(p_GSXX01);
		inParameter.add(p_WLDW01);
		inParameter.add(p_WLDW02);
		inParameter.add(p_BZ);
		callProc("{call SET_YHYEZ(?,?,?,?,?,?,?,?,?,?,?)}", inParameter, null, cn);
	}

	public static void setJQM_SFH(String sJQM, int iSPID, int iSPNM, int iTJBJ,
			String sBM, String sWLDW, String sCK, int iDJLX, long iJLBH, String sCZY,
			String sKHDM, String sYWY, double dWSDJ, double dXSDJ, int iSFH,
			String sKQ, Connection cn) throws Exception {
		String sSql;
		CallableStatement cstmt = null;
		try {
			sSql = "{call EXEC_JQM_SFH(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cstmt = cn.prepareCall(sSql);
			cstmt.setString(1, sJQM);
			cstmt.setInt(2, iSPID);
			cstmt.setInt(3, iSPNM);
			cstmt.setInt(4, iTJBJ);
			cstmt.setString(5, sBM);
			cstmt.setString(6, sWLDW);
			cstmt.setString(7, sCK);
			cstmt.setInt(8, iDJLX);
			cstmt.setLong(9, iJLBH);
			cstmt.setString(10, sCZY);
			cstmt.setString(11, sKHDM);
			cstmt.setString(12, sYWY);
			cstmt.setDouble(13, dWSDJ);
			cstmt.setDouble(14, dXSDJ);
			cstmt.setInt(15, iSFH);
			cstmt.setString(16, sKQ);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			cstmt.close();
			cstmt = null;
		}
	}

	/**
	 * 返回唯一编号 add w.p 需首先建立Oracle序列，再调用此方法
	 * @param TblName String 表名
	 * @param cn Connection
	 * @return int 最大编号
	 * @throws Exception
	 */
	public static int Get_Unique_No(String sTblName, Connection cn)
			throws Exception {
		int iJLBH = 0;
		String sSql = "";
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = cn.createStatement();
			sSql = " SELECT JL_SEQ_JC_" + sTblName + ".NEXTVAL FROM DUAL";
			System.out.println(sSql);
			rs = ps.executeQuery(sSql);
			while (rs.next()) {
				iJLBH = rs.getInt("NEXTVAL");
			}
			return iJLBH;
		} catch (Exception e) {
			throw e;
		} finally {
			ps.close();
			ps = null;
			if (rs != null) {
				rs.close();
				rs = null;
			}
		}
	}
	
	
}
