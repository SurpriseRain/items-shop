package com.jlsoft.utils;

import java.text.SimpleDateFormat;

/**
 * 
 * <p>Title: </p>
 * 
 * <p>Description: </p>
 * 
 * <p>Copyright: Copyright (c) 2005</p>
 * 
 * <p>Company: </p>
 * 
 * @version 1.0
 */
public final class JLSql {
	/**
	 * 
	 * @param str String 时间格式字串
	 * @return String 带“-”分隔符的格式化时间字串
	 */
	public static String strToDateStr(String str) throws Exception {
		String strDes = "", strTime = "";
		if (str.indexOf("-") == -1) {

			for (int i = 0; i <= str.length() - 1; i++) {
				if (((i % 4 == 0) || (i % 6 == 0)) && (i != 0) && (i <= 6)) {
					strDes += "-";
				}
				if (i == 8) {
					strDes += " ";
				} else {
					strDes += str.charAt(i);
				}
			}

			if (strDes.length() > 19) {
				System.out.println(strDes.substring(0, 19));
				strTime += strDes.substring(0, 19);
				strDes = "";
				strDes += strTime;
			}
			return strDes;
		} else
			return str;
	}

	/**
	 * 本方法取得数据库的当前日期时间。 <p>
	 * @return String - 一个完整独立的SQL语句表达式，不能加入其他SQL语句。 <p>
	 * @throws JLException
	 */
	public static String getSysDateTime() throws Exception {
		return " select sysdate JLDATETIME from dual ";
	}

	/**
	 * 本方法取得数据库的当前日期。 <p>
	 * @return String - 一个完整独立的SQL语句表达式，不能加入其他SQL语句。 <p>
	 * @throws JLException
	 */
	public static String getSysDate() throws Exception {
		return " SELECT TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD') JLDATE, SYSDATE JLDATETIME FROM DUAL ";
	}

	/**
	 * 本方法用于将yyyyMMdd格式的日期字符串转化为 java.sql.Date型的数据
	 * @param sDate String - yyyyMMdd格式的日期字符串
	 * @return Date - 转化得到的日期 (java.sql.Date)
	 * @throws Exception
	 */
	public static java.sql.Date dateStrToDate(String sDate) throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return new java.sql.Date(sdf.parse(sDate).getTime());
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 本方法将一个字符类型(String)的数据标准化为"yyyy-MM-dd HH:mm:ss"的字符(String)类型数据。
	 * 在数据无法符合标准格式的情况下，方法报告JLException自定义异常。 <p>
	 * @param sMyDateTime String - 字符类型数据 <p>
	 * @return String - 标准化过的字符类型数据。如果操作报错，则返回空字符串。 <p>
	 * @throws JLException
	 */
	public static String dateStrToDateTimeStr(String sMyDateTime)
			throws Exception {
		String sTmp = "";
		if (sMyDateTime.equalsIgnoreCase("null")
				|| (sMyDateTime.equalsIgnoreCase("''"))) {
			sTmp = "null";
			return sTmp;
		}
		if (sMyDateTime.length() > 0) {
			sMyDateTime = strToDateStr(sMyDateTime);
		}
		return "TO_DATE('" + sMyDateTime + "','YYYY-MM-DD HH24:MI:SS')";
	}

	/**
	 * 本方法将一个日期类型(Date)的数据转化为一个标准化的"yyyy-MM-dd HH:mm:ss"的字符(String)类型数据。
	 * 在数据无法符合标准格式的情况下，方法报告JLException自定义异常。 <p>
	 * @param dMyDate Date - 日期类型的数据 <p>
	 * @return String - 标准化的字符串。如果操作报错，则返回空字符串。 <p>
	 * @throws JLException
	 */
	public static String dateToDateTimeStr(java.sql.Date dMyDate)
			throws Exception {
		String sTmp = "";
		java.text.SimpleDateFormat sdf = null;
		try {
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sTmp = sdf.format(dMyDate);
			return "TO_DATE('" + sTmp + "','YYYY-MM-DD HH24:MI:SS')";
		} catch (Exception e) {
			throw e;
		} finally {
			sdf = null;
		}
	}

	/**
	 * 本方法将一个日期类型(Date)的数据转化为一个标准化的"yyyy-MM-dd HH:mm:ss"的字符(String)类型数据。
	 * 在数据无法符合标准格式的情况下，方法报告JLException自定义异常。 <p>
	 * @param dMyDate Date - 日期类型的数据 <p>
	 * @return String - 标准化的字符串。如果操作报错，则返回空字符串。 <p>
	 * @throws JLException
	 */
	public static String dateToDateStr(java.sql.Date dMyDate) throws Exception {
		String sTmp = "";
		java.text.SimpleDateFormat sdf = null;
		try {
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd ");
			sTmp = sdf.format(dMyDate);
			return "TO_DATE('" + sTmp + "','YYYY-MM-DD')";
		} catch (Exception e) {
			throw e;
		} finally {
			sdf = null;
		}
	}

	/**
	 * 本方法将一个字符类型(String)的数据标准化为"yyyy-MM-dd"的字符(String)类型数据。 <p>
	 * @param sMyDateTime String - 一个字符类型的数据。 <p>
	 * @return String - 标准化过的字符类型数据。方法报错时，返回值为空字符串。 <p>
	 * @throws JLException - 在数据无法符合标准格式的情况下，方法报告JLException自定义异常。
	 */
	public static String dateTimeStrToDateStr(String sMyDateTime)
			throws Exception {
		sMyDateTime = strToDateStr(sMyDateTime);
		return "TO_DATE('" + sMyDateTime + "','YYYY-MM-DD')";
	}

	/**
	 * 本方法将一个日期时间类型(DateTime)的数据转化为一个标准化的"yyyy-MM-dd"的字符(String)类型数据。 <p>
	 * @param dMyDateTime Timestamp - 一个日期时间类型的数据。 <p>
	 * @return String - 标准化过的字符类型数据。方法报错时，返回值为空字符串。 <p>
	 * @throws JLException - 在数据无法符合标准格式的情况下，方法报告JLException自定义异常。
	 */
	public static String dateTimeToDateStr(java.sql.Timestamp dMyDateTime)
			throws Exception {
		String sTmp = "";
		java.text.SimpleDateFormat sdf = null;
		try {
			sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			sTmp = sdf.format(dMyDateTime);
			return "TO_DATE('" + sTmp + "','YYYY-MM-DD')";
		} catch (Exception e) {
			throw e;
		} finally {
			sdf = null;
		}
	}

	public static String JLTBdateStrToDateTimeStr(String sMyDateTime)
			throws Exception {
		String sTmp = "";
		if (sMyDateTime.equalsIgnoreCase("null")) {
			sTmp = "null";
		} else {
			if (sMyDateTime.length() > 0) {
				sMyDateTime = strToDateStr(sMyDateTime);
				sTmp = "TO_DATE('" + sMyDateTime + "','YYYY-MM-DD HH24:MI:SS')";
			}
		}
		return sTmp;
	}

	/**
	 * 本方法取得数据库的当前日期时间。 <p>
	 * @return String - 一段当前日期时间的SQL语句表达式。 <p>
	 * @throws JLException
	 */
	public static String getSqlDateTime() throws Exception {
		return "SYSDATE";
	}

	/**
	 * 本方法取得数据库的当前日期。 <p>
	 * @return String - 一段当前日期的SQL语句表达式。 <p>
	 * @throws JLException
	 */
	public static String getSqlDate() throws Exception {
		return "TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD')";
	}

	/**
	 * 本方法取"Begin"在SQL语法中的语句 <p>
	 * @return String 返回"Begin"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlBegin() throws Exception {
		return " BEGIN ";
	}

	/**
	 * 本方法取"End"在SQL语法中的语句 <p>
	 * @return String 返回"End"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlEnd() throws Exception {
		return " END; ";
	}

	/**
	 * 本方法取"EndIF"在SQL语法中的语句 <p>
	 * @return String 返回"EndIF"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlEndIF() throws Exception {
		return "END IF; ";
	}

	/**
	 * 本方法取"NotFound"在SQL语法中的语句 <p>
	 * @return String 返回"NotFound"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlNotFound() throws Exception {
		return " IF SQL%ROWCOUNT = 0 THEN ";
	}

	/**
	 * 本方法取分号在SQL语法中的语句 <p>
	 * @return String 返回"Semi"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlSemi() throws Exception {
		return " ; ";
	}

	/**
	 * 本方法取"Then"在SQL语法中的语句 <p>
	 * @return String 返回"Then"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlThen() throws Exception {
		return " THEN ";
	}

	/**
	 * 本方法取"Exec"在SQL语法中的语句 <p>
	 * @return String 返回"Exec"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlExec() throws Exception {
		return " ";
	}

	/**
	 * 本方法取"Left"在SQL语法中的语句 <p>
	 * @return String 返回"Left"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlLeft() throws Exception {
		return " ( ";
	}

	/**
	 * 本方法取"Right"在SQL语法中的语句 <p>
	 * @return String 返回"Right"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlRight() throws Exception {
		return " ) ";
	}

	/**
	 * 本方法取"Add"在SQL语法中的语句 <p>
	 * @return String 返回"Add"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlAdd() throws Exception {
		return " || ";
	}

	/**
	 * 本方法取"DefTbl"在SQL语法中的语句 <p>
	 * @return String 返回"DefTbl"语句的SQL语法 <p>
	 * @throws JLException
	 */
	public static String getsqlDefTbl() throws Exception {
		return " from dual ";
	}
}
