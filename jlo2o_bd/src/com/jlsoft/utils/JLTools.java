package com.jlsoft.utils;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.*;
import org.mira.lucene.analysis.MIK_CAnalyzer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

import java.util.Iterator;

import org.dom4j.Document;

import com.jlsoft.o2o.init.service.Globals;



import java.sql.*;
import java.io.*;
import java.util.List;
import java.net.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * <p>Title: 系统小工具</p>
 *
 * <p>Description: 此类是一个小工具的集合体。所谓小工具，即是在系统操作中时常会用到的一些辅助工具，
 * 如屏幕信息显示、X文件删除、XML数据集解析、特殊符号转换、操作系统信息取得 </p>
 *
 * @author WuKeQing
 * @version V7.0.0.0
 */
public class JLTools {
//  public static Logger Log = Logger.getLogger("V7");
	private static Logger log = Logger.getLogger(JLTools.class);
	private static int readTimeOut = 10000;  //读取数据超时
	
    public static String chineseFC(String sCZ) throws Exception {
        String sSplit = "";
        boolean hasCZ = false;
        Reader r = null;
        try {
            Analyzer analyzer = new MIK_CAnalyzer();
            r = new StringReader(sCZ);
            TokenStream ts = (TokenStream) analyzer.tokenStream("", r);
            Token t = null;
            hasCZ = false;
            while ((t = ts.next()) != null) {
                sSplit += t.termText() + ",";
                if (t.termText().equals("sCZ")) {
                    hasCZ = true;
                }
            }
            r.close();
            if (sSplit.equals("") || (hasCZ == false)) {
                sSplit = sCZ + "," + sSplit;
            }
            return sSplit.substring(0, sSplit.length() - 1);
        } catch (Exception e) {
            throw e;
        } finally {
            r.close();
        }
    }

    /**
     * 此处将本类的实例初始化方法设为内部调用。这样处理的用意在于: 此类中的常量和方法全部都是static形式的，
     * 当外部调用类中常量或方法的时候，没有必要创建实例，于是干脆不允许其创建实例，而是直接从类名调用。
     */
    //得到本地计算机IP地址
    public static String getLocalHostIP() throws Exception {
        String sAddress = "";
        try {
            sAddress = InetAddress.getLocalHost().getHostAddress();
            return sAddress;
        } catch (Exception e) {
            throw e;
        }
    }

    //得到本地计算机名称
    public static String getLocalHostName() throws Exception {
        String sName = "";
        try {
            sName = InetAddress.getLocalHost().getHostName();
            return sName;
        } catch (Exception e) {
            throw e;
        }
    }

    //时间前移或后移,最小单位天
    public static java.sql.Timestamp chanDay(java.sql.Timestamp souDateTime,
            int day) {
        return new java.sql.Timestamp(souDateTime.getTime() + day * 0X5265C00L);
    }

    //日期前移或后移,最小单位天
    public static java.sql.Date chanDay(java.sql.Date souDate, int day) {
        return new java.sql.Date(souDate.getTime() + day * 0X5265C00L);
    }

    //两个日期比较，返回日期大的那个
    public static java.sql.Date compareDay(java.sql.Date date1,
            java.sql.Date date2) {
        return (date1.getTime() > date2.getTime()) ? date1 : date2;
    }

    //两个日期带时间比较，返回日期及时间大的那个
    public static java.sql.Timestamp compareDay(java.sql.Timestamp time1,
            java.sql.Timestamp time2) {
        return (time1.getTime() > time2.getTime()) ? time1 : time2;
    }

    //格式化数字
    public static double format(double d, int i) {
        String sFormat = "#.";
        for (int n = 0; n < i; n++) {
            sFormat += "0";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat(sFormat);
        return Double.parseDouble(df.format(d));
    }

    /*
     * public static void main(String[] args) { System.out.print(format(
     * -3.33456, 3)); }
     */
    /**
     * 此方法将系统运行中尚未使用的内存资源时时显示在屏幕上。
     */
    public static void writeFreeMemoryToScreen() {
        System.out.println(Runtime.getRuntime().freeMemory() / (1024 * 1024) + "MB");
    }

    /**
     * 此方法用以删除一个文件。操作过程出错时，系统会报错，将报错信息写入报错日志，并终止操作。 <p>
     *
     * @param FileName String: 方法参数是以字符串形式代表的一个文件名。
     */
    public static void deleteFile(String FileName) throws Exception { //删除文件
        File fdelFile = null;
        try {
            fdelFile = new File(FileName);
            fdelFile.delete();
        } catch (Exception e) {
            throw e;
        } finally {
            fdelFile = null;
        }
    }

    public static void makeDirectory(String folderPath) throws Exception { //创建目录
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean deleteAllFile(String folderFullPath) { //删除指定文件夹下所有文件
        boolean ret = false;
        File file = new File(folderFullPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    String filePath = fileList[i].getPath();
                    deleteAllFile(filePath);
                }
            }
            if (file.isFile()) {
                file.delete();
            }
        }
        return ret;
    }

    /**
     * 此方法多用于通过解析XML格式的数据集得到某一指定列的所有值。操作过程出错时，系统会报错，将报错信息写入报错日志，并终止操作。
     *
     * @param XmlData String - 一个XML格式封装的文件。此文件的内容多为数据库查询结果，即数据集，由一条条纪录组成，
     * 每条记录的列数据格式相同。<p>
     * @param ParamName String - XMLData 数据集中的某一列之列名。<p>
     * @return Vector - 每条记录中，同列数据的集合。
     */
    public static Vector getParameterValue(String ParamName, String XmlData) throws
            Exception { //通过解析XML得到参数值
        Vector vec = null;
        Document doc = null;
        Element root = null;
        Iterator it = null;
        try {
            vec = new Vector();
            vec.clear();
            doc = DocumentHelper.parseText(XmlData);
            root = doc.getRootElement(); //得到XML根
            it = root.elementIterator(); //增加一个迭代器
            while (it.hasNext()) {
                Element item = (Element) it.next();
                if (item.getName().equals("PARAMDATA")) { //迭代查找PARAMDATA节点
                    if (item.attribute("parameter_name").getValue().equals(ParamName)) {
                        vec.add(item.attribute("parameter_value").getValue());
                    }
                }
            }
            return vec;
        } catch (Exception e) {
            throw e;
        } finally {
            if (vec == null) {
                vec.add("");
            }
            it = null;
            root = null;
            doc = null;
        }
    }

    /**
     *
     * @param ParamName String
     * @param XmlData String
     * @return Vector
     * @throws Exception
     * @deprecated
     */
    public static Vector getParameterValueEx(String ParamName, String XmlData) throws
            Exception { //通过解析XML得到参数值
        Document doc = null;
        Element root = null;
        List ls = null;
        Vector vec = null;
        int i = 0;
        try {
            doc = DocumentHelper.parseText(XmlData);
            ls = doc.selectNodes("//ROW");
            vec = new Vector();
            for (i = 0; i <= ls.size() - 1; i++) {
                root = (Element) ls.get(i);
                vec.add(root.attribute(ParamName).getValue());
            }
            if (vec.isEmpty()) {
                vec.add("");
            }
            return vec;
        } catch (Exception e) {
            throw e;
        } finally {
            root = null;
            doc = null;
        }
    }

    /**
     * 此方法用于写操作日志。操作过程出错时，系统会报错，将报错信息写入报错日志，并终止操作。 <p>
     *
     * @param msg String - 操作日志的内容。<p>
     * @param FileName String - 一个字符串格式的文件名，即记录操作日志的文件。
     */
    public static void writeTimerLog(String msg, String FileName) throws
            Exception { //写操作日志
        String tmp_s = "";
        java.text.SimpleDateFormat sdf = null;
        String date = "";
        FileWriter fw = null;
        try {
            sdf = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss    ");
            date = sdf.format(new java.sql.Timestamp(System.currentTimeMillis()));
            fw = new FileWriter(FileName, true);
            switch (JLDefsys.CURRENT_OSLX) {
                case 0:
                    ;
                case 1:
                    tmp_s = date + msg; //+JLDefsys.CHAR_RETURN;
                    break;
                case 2:
                    tmp_s = date + msg;
                case 5:
                    tmp_s = date + msg;
                default:
                    break;
            }
            fw.write(tmp_s + "\n");
        } catch (Exception e) {
            throw e;
        } finally {
            fw.close();
            sdf = null;
            fw = null;
        }

    }

    public static java.sql.Timestamp cTimeToTimeStamp(String x) {
        String year = x.substring(0, 4); //1-4
        String month = x.substring(4, 6); //5-6
        String day = x.substring(6, 8); //7-8
        String hour = x.substring(9, 11); //10-11
        String min = x.substring(12, 14); //13-14
        String sec = x.substring(15, 17); //16-17
        String msec = x.substring(17, 20); //18-20
        return java.sql.Timestamp.valueOf(year + "-" + month + "-" + day + " "
                + hour + ":" + min + ":" + sec + "." + msec);
    }

    /**
     * 此方法将操作日志显示到屏幕。操作过程出错时，系统会报错，将报错信息写入报错日志，并终止操作。 <p>
     *
     * @param msg String - 操作日志的内容。
     * @deprecated
     */
    public static void writeTimerToScreen(String msg) throws Exception { //写操作日志
        java.text.SimpleDateFormat sdf = null;
        String date = "";
        try {
            sdf = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            date = sdf.format(new java.sql.Timestamp(System.currentTimeMillis()));
            System.out.println(Thread.currentThread().getName() + " is Operate");
            System.out.println(date + msg);
        } catch (Exception e) {
            throw e;
        } finally {
            sdf = null;
        }
    }

    /**
     * 此方法将操作日志显示到屏幕。操作过程出错时，系统会报错，将报错信息写入报错日志，并终止操作。 <p>
     *
     * @param msg String - 操作日志的内容。
     * @deprecated
     */
    public static void executeUpdate(String msg, Statement stmt) throws Exception { //屏幕输出调试信息
        try {
            if (JLDefsys.DEBUG_ENABLED) {
                System.out.println(msg);
            }
            stmt.executeUpdate(msg);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 此方法从部门或仓库代码中分离出公司代码。 <p>
     *
     * @param SouStr String - 部门或仓库代码。<p>
     * @return String - 公司代码。
     */
    public static String getGSID(String SouStr) throws Exception {
        if (JLDefsys.IGSJB == -1) {
            throw new Exception("取公司级别错误！" + JLTools.intToStr(JLDefsys.IGSJB));
        }
        return SouStr.substring(0, JLDefsys.IGSJB * 2);
    }

    /**
     * 此方法给字符串前后各加一个双引号。 <p>
     *
     * @param SouStr String - 需要加双引号的字符串。<p>
     * @return String - 加上了一对双引号的字符串。
     * @deprecated
     */
    public static String getQuot(String SouStr) {
        return JLDefsys.CHAR_QUOT + SouStr + JLDefsys.CHAR_QUOT;
    }

    /**
     * 此方法给字符串前后各加一个双引号。 <p>
     *
     * @param SouStr String - 需要加单引号的字符串。<p>
     * @return String - 加上了一对单引号的字符串。
     */
    public static String getStrQuot(String SouStr) { //字符串加单引号
        return JLDefsys.STR_QUOT + SouStr + JLDefsys.STR_QUOT;
    }

    /**
     * 此方法检查一个字符串是否为空。 <p>
     *
     * @param s String - 检查的对象字符串。<p>
     * @return boolean - 如果是空串，则返回true，否则返回false。
     */
    public static boolean isNull(String s) { //检查传入字符串是否为空
        return ((s == null) || s.trim().equals("")) ? true : false;
    }

    /**
     * 此方法将一个字符串内的所有(\\)括号内部分替换成(/)括号内部分。 <p>
     *
     * @param SouStr String - 替换的对象字符串。<p>
     * @return String - 替换后的字符串。
     */
    public static String replaceStrBackSlash(String SouStr) { //替换转义字符
        if (isNull(SouStr)) {
            SouStr = "";
        }
        return SouStr.replace('\\', '/');
    }

    /**
     * 此方法将一个字符串内的所有双引号替换成单引号。 <p>
     *
     * @param SouStr String - 替换的对象字符串。<p>
     * @return String - 替换后的字符串。
     */
    public static String replaceStrQuote(String SouStr) { //将双引号替换成单引号
        return SouStr.replace('"', '\'');
    }

    /**
     * 此方法用以得到当前操作系统类型。 <p>
     *
     * @return int - 返回操作系统类型的数字代表。返回值分别为是: <p> 0: WINDOWS 9X <p> 1: 微软操作系统 <p>
     * 2: IBM UNIX操作系统 <p> 3: 微系统 Solaris 操作系统 <p> 4: HP Unix 操作系统 <p> 5: 红帽子，红旗
     * LINUX <p> 6: SCO UNIX <p> 100: 其它操作系统
     */
    public static int getOsLx() { //得到操作系统类型
        int OSLX = 100; //其它操作系统
        if (getOSName().indexOf("WINDOWS 9") != -1) { //WINDOWS 9X
            OSLX = 0;
        } else if (getOSName().indexOf("WIN") != -1) { // 微软操作系统
            OSLX = 1;
        } else if (getOSName().indexOf("AIX") != -1) { //IBM UNIX操作系统
            OSLX = 2;
        } else if (getOSName().indexOf("SUNOS") != -1) { //微系统 Solaris 操作系统
            OSLX = 3;
        } else if (getOSName().indexOf("HP") != -1) { //HP Unix 操作系统
            OSLX = 4;
        } else if (getOSName().indexOf("LINUX") != -1) { //红帽子，红旗 LINUX
            OSLX = 5;
        } else if (getOSName().indexOf("SCO") != -1) { //SCO UNIX
            OSLX = 6;
        }
        return OSLX;
    }

    /**
     * 此方法用于得到当前操作系统名称。<p>
     *
     * @return String - 返回字符串类型的操作系统名称。
     */
    private static String getOSName() { //得到操作系统名称
        return System.getProperty("os.name").toString().toUpperCase().trim();
    }

    /**
     * 此方法将一个文本信息写入文件。操作过程出错时，系统会报错，将报错信息写入报错日志，并终止操作。<p>
     *
     * @param XmlStr String - 文本信息 <p>
     * @param XmlFileName String - 文件名
     */
    public static void writeXmlFile(String XmlStr, String XmlFileName) throws
            Exception { //将文本信息写入文件
        try {
            writeTimerLog(XmlStr, XmlFileName);
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 此方法将String变量转换未Int变量
     *
     * @param sou String
     * @return int
     */
    public static int strToInt(String sou) {
        return Integer.parseInt(sou);
    }

    public static long strToLong(String sou) {
        return Long.parseLong(sou);
    }

    /**
     * 此方法将String变量转换未Float变量
     *
     * @param sou String
     * @return float
     * @deprecated
     */
    public static float strToFloat(String sou) {
        return Float.parseFloat(sou);
    }

    /**
     * 此方法将String变量转换未Date变量
     *
     * @param sou String
     * @return Date
     */
    public static java.sql.Date strToDate(String sou) {
        return java.sql.Date.valueOf(sou);
    }

    /**
     * 此方法将String变量转换未Timestamp变量
     *
     * @param sou String
     * @return Timestamp
     */
    public static java.sql.Timestamp strToDateTime(String sou) {
        return java.sql.Timestamp.valueOf(sou);
    }

    /**
     * 此方法将String变量转换未Double变量
     *
     * @param sou String
     * @return double
     */
    public static double strToDouble(String sou) {
        return Double.parseDouble(sou);
    }

    public static String dateToStr(java.sql.Date sou) {
        return sou.toString();
    }

    public static String dateTimeToStr(java.sql.Timestamp sou) {
        return sou.toString();
    }

    public static String doubleToStr(double sou) {
        return String.valueOf(sou);
    }

    /**
     *
     * @param sou float
     * @return String
     * @deprecated
     */
    public static String floatToStr(float sou) {
        return String.valueOf(sou);
    }

    public static String intToStr(int sou) {
        return String.valueOf(sou);
    }

    public static String intToStr(long sou) { //yc alter重载方法
        return String.valueOf(sou);
    }

    public static String byteTohexStr(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static byte[] hexStrTobyte(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        byte[] hanzi = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            hanzi[i
                    / 2] = (byte) (Integer.parseInt(hexString.substring(i, i + 2), 16)
                    & 0xff);
        }
        return hanzi;
    }

    public static String formatStr(String ch, String s, int count) {
        while ((count - s.length()) > 0) {
            s = ch + s;
        }
        return s;
    }

    public static String getCurTimestamp() {
        String sjc_sql = "";
        try {
            if (JLDefsys.getDBName().trim().equals("ORACLE")) {
                sjc_sql = "to_char(systimestamp,'YYYYMMDDHH24MISSFF2')";
            } else if (JLDefsys.getDBName().trim().equals("DB2")) {
                sjc_sql = "to_char(current timestamp,'YYYYMMDDHH24MISSFF2')";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sjc_sql;
    }
    
    /**
     * @获取单号
     * @return
     */
    public static long getOrderId(){
    	String orderId = getDateTime()+(int)(Math.random()*1000);
    	return Long.parseLong(orderId);
    }
    
    //获取时间
    public static String getDateTime(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	return df.format(new Date());
    }
    
    //JAVA获取时间戳
    public static String getTimestamp(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	return df.format(new Date());
    }
    
    public static int testCountCode(int iCount, String cStr) {
        int iOdd = 0; // 奇
        int iEven = 0; // 偶
        int iTestCode = 0;
        int iTmp = 0;
        String sCodeStr = "";

        sCodeStr = cStr;
        if (iCount == 12) {
            for (iTmp = 0; iTmp < iCount; iTmp = iTmp + 2) {
                iOdd += strToInt(String.valueOf(sCodeStr.charAt(iTmp)));
                iEven += strToInt(String.valueOf(sCodeStr.charAt(iTmp + 1)));
            }
            iTestCode = 10 - (iOdd + iEven * 3) % 10;
        } else if (iCount == 7) {
            for (iTmp = 0; iTmp < iCount; iTmp = iTmp + 2) {
                iOdd += strToInt(String.valueOf(sCodeStr.charAt(iTmp)));
                if (iTmp != 6) {
                    iEven += strToInt(String.valueOf(sCodeStr.charAt(iTmp + 1)));
                }
            }
            iTestCode = 10 - (iOdd * 3 + iEven) % 10;
        }
        if (iTestCode == 10) {
            iTestCode = 0;
        }
        sCodeStr = sCodeStr + JLTools.intToStr(iTestCode);
        return iTestCode;
    }

    public static String getConfigDir() throws Exception { //返回JL里面配置文件目录位置
        String TempDirStr = "";
        try {
            Properties p = getEnvVars();
            TempDirStr = p.getProperty(JLDefsys.JL_CONFIG_HOME);
            TempDirStr = replaceStrBackSlash(TempDirStr);
            if (!JLTools.isNull(TempDirStr)) {
                if (!TempDirStr.substring(TempDirStr.length() - 1).equals("/")) {
                    TempDirStr += "/";
                }
            }
            return TempDirStr;
        } catch (Exception e) {
            throw e;
        }

    }

    private static Properties getEnvVars() throws Exception {
        Process p = null;
        Properties envVars = new Properties();
        Runtime r = Runtime.getRuntime();
        try {
            if (getOsLx() == 0) {
                p = r.exec("command.com /c set");
            } else if (getOsLx() == 1) {
                p = r.exec("cmd.exe /c set");
            } else {
                p = r.exec("env");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                int idx = line.indexOf("=");
                String key = line.substring(0, idx);
                String value = line.substring(idx + 1);
                envVars.setProperty(key, value);
            }
            br.close();
        } catch (Exception e) {
            throw e;
        }
        return envVars;
    }

    /**
     *
     * @param str String
     * @return String 将字符串转换为半角字符串
     */
    public static String strToBJZF(String str) {
        int i = 0;
        String tmp = "";
        for (i = 0; i <= str.length() - 1; i++) {
            if ((int) str.charAt(i) >= 65281 && (int) str.charAt(i) <= 65373) {
                tmp += (char) ((int) str.charAt(i) - 65248);
            } else if ((int) str.charAt(i) == 12288) {
                tmp += (char) 32;
            } else {
                tmp += str.charAt(i);
            }
        }
        return tmp;
    }
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

    public static void copyLarge(InputStream input, OutputStream output) throws
            IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copyLarge(input, output);
        output.close();
        input.close();
        return output.toByteArray();
    }

    /**
     *
     * @param str String
     * @return String 将字符串转换为全角字符串
     */
    public static String strToQJZF(String str) {
        int i = 0;
        String tmp = "";
        for (i = 0; i <= str.length() - 1; i++) {
            if ((int) str.charAt(i) >= 33 && (int) str.charAt(i) <= 125) {
                tmp += (char) ((int) str.charAt(i) + 65248);
            } else if ((int) str.charAt(i) == 32) {
                tmp += (char) 12288;
            } else {
                tmp += str.charAt(i);
            }
        }
        return tmp;
    }

    /**
     *
     * @param str String
     * @return boolean 判断是否存在半角字符
     */
    public static boolean existsBJZF(String str) {
        int i = 0;
        boolean result = false;
        for (i = 0; i <= str.length() - 1; i++) {
            if ((int) str.charAt(i) >= 32 && (int) str.charAt(i) <= 125) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     *
     * @param str String
     * @return boolean 判断是否存在全角字符
     */
    public static boolean existsQJZF(String str) {
        int i = 0;
        boolean result = false;
        for (i = 0; i <= str.length() - 1; i++) {
            if ((int) str.charAt(i) >= 65281 && (int) str.charAt(i) <= 65373
                    || (int) str.charAt(i) == 12288) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     *
     * @param str String
     * @return boolean 判断是否存在需要转换字符
     */
    public static boolean existsXMLTransfer(String str) {
        int i = 0;
        boolean result = false;
        for (i = 0; i <= str.length() - 1; i++) {
            if (str.charAt(i) == '<' || str.charAt(i) == '>' || str.charAt(i) == '\''
                    || str.charAt(i) == '"' || str.charAt(i) == '&') {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     *
     * @param str String
     * @return String 替换XML中存在的转意字符
     */
    public static String xmlTransfer(String str) {
        int i = 0;
        String tmp = "";
        for (i = 0; i <= str.length() - 1; i++) {
            if (str.charAt(i) == '<') {
                tmp += "&lt;";
            } else if (str.charAt(i) == '>') {
                tmp += "&gt;";
            } else if (str.charAt(i) == '\'') {
                tmp += "&apos;";
            } else if (str.charAt(i) == '&') {
                tmp += "&amp;";
            } else if (str.charAt(i) == '"') {
                tmp += "&quot;";
            } else {
                tmp += str.charAt(i);
            }
        }
        return tmp;
    }

    public static boolean existsKXJS(String str) {
        //return str.matches("[-|+]{0,1}[\\d]{1}[.][\\d]*[E|e]{1}[+|-]{0,1}[\\d]*");
        if (str == null) {
            return false;
        }
        if (str.length() >= 2) {
            return ((str.indexOf("E") != -1) || (str.indexOf("e") != -1)) ? true : false;
        } else {
            return false;
        }
    }

    /**
     *
     * @param str String
     * @return boolean 判断是否字符串是否包含某个字符
     */
    public static boolean jl_contains(String str1, String str2) {
        boolean b_result = false;
        char[] stra_1 = str1.toCharArray();
        char[] stra_2 = str2.toCharArray();
        int i_index = 0;
        int i_length = stra_2.length;
        int i_end = stra_1.length - i_length;
        while (i_index <= i_end) {
            int i_temp = i_index;
            int i_TrueFlag = 0;
            for (int i = 0; i < i_length; i++) {
                if (stra_2[i] == stra_1[i_temp]) {
                    i_TrueFlag++;
                } else {
                    break;
                }
                if (i_TrueFlag == i_length) {
                    return true;
                }
                i_temp++;
            }
            i_index++;
        }
        return b_result;
    }

    /**
     *
     * @param str String
     * @param i int
     * @return String 将科学计数法转换为普通数值
     */
    public static String kxjsTransfer(String d, int i) {
        String sFormat = "#.";
        for (int n = 0; n < i; n++) {
            sFormat += "0";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat(sFormat);
        return df.format(Double.parseDouble(d));
    }
    //解密ZIP文件流,解压缩ZIP文件得到数据 weiqiao20091030 add

    public static String getXML_UnZip(String content) throws Exception {
        FileOutputStream onf = null;
        FileInputStream fin = null;
        ZipInputStream zip = null;
        String ZipFileName = "jltemp/"
                + Math.abs(new java.util.Random().nextInt())
                + ".zip";
        String XmlFileName = "jltemp/"
                + Math.abs(new java.util.Random().nextInt())
                + ".xml";
        try {
            if (content == null || content.length() == 0
                    || content.indexOf("DATAPACKET") != -1) {
                return content;
            }
            String sTmp = "";
            byte[] data = new sun.misc.BASE64Decoder().decodeBuffer(content);
            onf = new FileOutputStream(ZipFileName, true);
            onf.write(data);
            onf.flush();
            onf.close();
            File inFile = new File(ZipFileName);
            fin = new FileInputStream(inFile);
            zip = new ZipInputStream(fin);
            byte[] bBuf = new byte[(int) zip.getNextEntry().getSize()];
            int len;
            onf = new FileOutputStream(XmlFileName);
            while ((len = zip.read(bBuf)) != -1) {
                onf.write(bBuf, 0, len);
            }
            zip.closeEntry();
            zip.close();
            fin.close();
            onf.flush();
            onf.close();
            inFile = new File(XmlFileName);
            fin = new FileInputStream(inFile);
            bBuf = new byte[(int) inFile.length()];
            while (fin.read(bBuf) != -1) {
                sTmp += new String(bBuf);
            }
            fin.close();
            return sTmp;
        } catch (Exception e) {
            throw e;
        } finally {
            onf = null;
            fin = null;
            zip = null;
            JLTools.deleteFile(ZipFileName);
            JLTools.deleteFile(XmlFileName);
        }
    }

    public static String sendToSync(String data, String sendurl) throws Exception {
        DataOutputStream wr = null;
        DataInputStream rd = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(sendurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //String BOUNDARY = "------------------------7dc2fd5c0894";
            conn.setRequestProperty("Content-Type", "application/octet-stream");//multipart/form-data; boundary=---------------------------7d33a816d302b6");
            conn.setRequestMethod("POST");
            conn.connect();
            wr = new DataOutputStream(conn.getOutputStream());
//          logger.info(data);
            if (data != null) {
                wr.write(data.getBytes("UTF-8"));
            }
            wr.flush();
            int returnCode = conn.getResponseCode();

            DataInputStream dis = new DataInputStream(conn.getInputStream());
            byte[] aryZlib = JLTools.toByteArray(dis);
            if (dis != null) {
                dis.close();
                dis = null;
            }
            String sValue = new String(aryZlib, "UTF-8");
            return sValue;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rd != null) {
                rd.close();
                rd = null;
            }
            if (wr != null) {
                wr.close();
                wr = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }
    
    //
    public static String sendToVtradEx(String data, String sendurl) throws Exception {
        DataOutputStream wr = null;
        DataInputStream rd = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(sendurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //String BOUNDARY = "------------------------7dc2fd5c0894";
            conn.setRequestProperty("Content-Type", "application/octet-stream");//multipart/form-data; boundary=---------------------------7d33a816d302b6");
            conn.setRequestMethod("POST");
            conn.connect();
            wr = new DataOutputStream(conn.getOutputStream());
//          logger.info(data);
            if (data != null) {
                wr.write(data.getBytes("UTF-8"));
            }
            wr.flush();
            int returnCode = conn.getResponseCode();

            DataInputStream dis = new DataInputStream(conn.getInputStream());
            byte[] aryZlib = JLTools.toByteArray(dis);
            if (dis != null) {
                dis.close();
                dis = null;
            }
            String sValue = new String(aryZlib, "GBK");
            return sValue;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rd != null) {
                rd.close();
                rd = null;
            }
            if (wr != null) {
                wr.close();
                wr = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }
    
    public static String sendToSync_V9(String data, String sendurl)throws Exception {
    	URL url = null;
    	HttpURLConnection httpurlconnection = null;
    	try {
    		url = new URL(sendurl);
			// 以post方式请求
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setRequestMethod("POST");
			String XmlData = data;
			httpurlconnection.getOutputStream().write(XmlData.getBytes());
			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();
			// 获取响应代码
			int code = httpurlconnection.getResponseCode();
			if (code != 200) {
				throw new Exception("未成功发送数据!" + code);
			}
			System.out.println("code   " + code);

			// 获取页面内容
			java.io.InputStream in = httpurlconnection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			String str = breader.readLine();
			return str;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return e.getMessage();
    	} finally {
    		if (httpurlconnection != null)
    			httpurlconnection.disconnect();
    	}
    }
    
    public static String sendToSync_V7(String data, String sendurl)throws Exception {
    	URL url = null;
    	HttpURLConnection httpurlconnection = null;
    	try {
    		url = new URL(sendurl);
			// 以post方式请求
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setRequestMethod("POST");
			String XmlData = data;
			httpurlconnection.getOutputStream().write(XmlData.getBytes());
			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();
			// 获取响应代码
			int code = httpurlconnection.getResponseCode();
			if (code != 200) {
				throw new Exception("未成功发送数据!" + code);
			}
			System.out.println("code   " + code);

			// 获取页面内容
			java.io.InputStream in = httpurlconnection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(
					new InputStreamReader(in, "UTF-8"));
			String str = breader.readLine();
			return str;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return e.getMessage();
    	} finally {
    		if (httpurlconnection != null)
    			httpurlconnection.disconnect();
    	}
    }
    
    public static String postRequest(String data, String sendurl) throws Exception {
        DataOutputStream wr = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(sendurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
    		conn.setConnectTimeout(30000);	//（单位：毫秒）
    		conn.setReadTimeout(5000);	//（单位：毫秒）
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setRequestMethod("POST");
            conn.connect();
            wr = new DataOutputStream(conn.getOutputStream());
            if (data != null) {
                wr.write(data.getBytes("UTF-8"));
            }
            wr.flush();
            int returnCode = conn.getResponseCode();
            if( 200 != returnCode ){
            	throw new Exception("Error Http ResponseCode " + returnCode);
            }
            String sValue = "";
    		String line = "";
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),Charset.forName("UTF-8")));
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sValue += line;
			}
            return sValue;
        } catch (Exception e) {
            throw e;
        } finally {
            if (wr != null) {
                wr.close();
                wr = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
            try {
            	if(null != br){
    				br.close();
            	}
			} catch (Exception e) {
				throw new Exception();
			}
        }
    }
    private static Map XML_KEY = new HashMap(4);
    private static String REG_XML = ".*[<>&\"].*";

    static {
        XML_KEY.put('<', "&lt;");
        XML_KEY.put('>', "&gt;");
        XML_KEY.put('&', "&amp;");
        XML_KEY.put('"', "&quot;");
    }

    public static String getXmlString(String colvalue) {
        if (colvalue != null && !colvalue.equals("") && colvalue.matches(REG_XML)) {
            StringBuilder xmlStr = new StringBuilder();
            for (int j = 0; (colvalue != null && j < colvalue.length()); j++) {
                if (XML_KEY.containsKey(colvalue.charAt(j))) {
                    xmlStr.append(XML_KEY.get(colvalue.charAt(j)).toString());
                } else {
                    xmlStr.append(colvalue.charAt(j));
                }
            }
            return xmlStr.toString();
        } else {
            return colvalue;
        }
    }

    public static boolean isEmpty(String value) {
        return (value == null || value.equals("") || value.equals("null"));
    }

    public static java.sql.Date parseDate(String sdate) {

        Calendar calendar = Calendar.getInstance();
        try {
            sdate = JLSql.strToDateStr(sdate);
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(sdate));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new java.sql.Date(calendar.getTimeInMillis());

//        GregorianCalendar gc = null;
//
//        if (sdate.indexOf("-") == -1) {
//
//            gc = new GregorianCalendar(Integer.parseInt(sdate.substring(0, 4)),
//                    Integer.parseInt(sdate.substring(4, 6)) - 1,
//                    Integer.parseInt(sdate.substring(6, 8)));
//        } else {
//            gc = new GregorianCalendar(Integer.parseInt(sdate.substring(0, 4)),
//                    Integer.parseInt(sdate.substring(5, 7)) - 1,
//                    Integer.parseInt(sdate.substring(8, 10)));
//        }
//
//        return new java.sql.Date(gc.getTimeInMillis());

    }

    public static java.sql.Timestamp parseTimestamp(String sdate) {

        Calendar calendar = Calendar.getInstance();
        try {
            sdate = JLSql.strToDateStr(sdate);
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdate));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new java.sql.Timestamp(calendar.getTimeInMillis());

//        GregorianCalendar gc = null;
//
//        if (sdate.indexOf("-") == -1) {
//
//            gc = new GregorianCalendar(Integer.parseInt(sdate.substring(0, 4)),
//                    Integer.parseInt(sdate.substring(4, 6)) - 1,
//                    Integer.parseInt(sdate.substring(6, 8)),
//                    Integer.parseInt(sdate.substring(9, 11)),
//                    Integer.parseInt(sdate.substring(12, 14)),
//                    Integer.parseInt(sdate.substring(15, 20)));
//        } else {
//            gc = new GregorianCalendar(Integer.parseInt(sdate.substring(0, 4)),
//                    Integer.parseInt(sdate.substring(5, 7)) - 1,
//                    Integer.parseInt(sdate.substring(8, 10)),
//                    Integer.parseInt(sdate.substring(11, 13)),
//                    Integer.parseInt(sdate.substring(14, 16)),
//                    Integer.parseInt(sdate.substring(17, 22)));
//        }
//
//        return new java.sql.Timestamp(gc.getTimeInMillis());

    }

    public static String toStr(Object value) {
        if (value == null) {
            return "";
        }
        String type = value.getClass().getName();
        if ("java.lang.Integer".equals(type)) {
            return String.valueOf(value);
        } else if ("java.lang.String".equals(type)) {
            return (String) value;
        } else if ("java.lang.Double".equals(type)) {
            return String.valueOf(value);
        } else if ("java.lang.Float".equals(type)) {
            return String.valueOf(value);
        } else if ("java.lang.Character".equals(type)) {
            return String.valueOf(value);
        } else if ("java.util.Date".equals(type)) {
            return String.valueOf(value);
        } else if ("java.sql.Date".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(value);
        } else if ("java.sql.Timestamp".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(value);
        } else if ("java.math.BigDecimal".equals(type)) {
            return value.toString();
        } else {
            return String.valueOf(value);
        }
    }

    public static double toDouble(String sou) {
        return Double.parseDouble(sou);
    }
    
    public static String MD5(String s) {
       return MD5.getMD5(s);
    }    
    
    // 获取连接数据库名
	public static String getDataBaseType(String DataBaseType) {
		if (DataBaseType == null) {
			DataBaseType = JlAppResources.getProperty("DataBaseType");
		}
		return DataBaseType;
	}
    
	/**
	 * @todo 本方法将一个字符类型(String)的数据标准化为"yyyy-MM-dd HH:mm:ss"的字符(String)类型数据
	 * @param sMyDateTime
	 * @return
	 * @throws Exception
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
			sTmp = "TO_DATE('" + sMyDateTime + "','YYYY-MM-DD HH24:MI:SS')";
		}
		return sTmp;
	}
	
	/**
	 * @todo str String 时间格式字串，转换成带“-”分隔符的格式化时间字串
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
	
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static int getJLBH(JdbcTemplate jdbcTemplate, String table, int size) throws Exception {
		LinkedList inParameter = new LinkedList();
		inParameter.add(table);
		inParameter.add(size);
		LinkedList outParameter = new LinkedList();
		outParameter.add(0);
		String sql = "{call Update_WBHZT(?,?,?)}";
		outParameter = PubFun.callProc(sql, inParameter, outParameter,
				jdbcTemplate);
		return ((Integer) (outParameter.get(0))).intValue();
	}
	
	/**
	 * @todo 自动补0方法
	 * @param id
	 * @param length
	 * @return
	 */
	public static String getID_X(int id, int length) {
		char[] c = new char[length];
		for (int i = 0; i < c.length; i++)
			c[i] = '0';
		String s = new String(c);
		java.text.DecimalFormat df = new java.text.DecimalFormat(s);
		return df.format(id);
	}
	
	/**
	 * getCurrentDate1 获取当前日期,格式（年-月-日 时:分:秒）
	 * 
	 * @author jhj
	 * @return String
	 */
	public static String getCurrentDate1() {
		String time = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = sdf.format(new java.util.Date());
		return time;
	}
	
	/**
	 * @todo 上传附件
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> fileUploadNew(MultipartFile file,Map map) throws Exception{
		try {
			// 上传附件
			if (map.get("picCover") == null || Boolean.parseBoolean(map.get("picCover").toString()) == false) {
					// 上传成功标记
					InputStream in = file.getInputStream();
					String path = map.get("imgPath").toString();
					String fileName = map.get("imgName").toString();
					File filePath = new File(path);
					if (!filePath.exists()) {
						filePath.mkdirs();
						System.out.println("创建目录为：" + filePath);
					}
					path = filePath + "/" + fileName;
					map.put("fileName", fileName);
					map.put("path", path);
					FileOutputStream out = new FileOutputStream(path);
					byte buffer[] = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					if (map.get("sqlObj") != null && map.get("sqlMethod") != null) {
						List<Map> list = (List<Map>) map
								.get("sqlMethodParamter");
						// 上传完是否执行方法
						Class obj = Class.forName(map.get("sqlObj").toString());
						Method method = obj.getMethod(map.get("sqlMethod")
								.toString(), java.util.Map.class);
						Map paramMap = new HashMap<String, Object>();
						paramMap.put("imgName", fileName);
						paramMap.putAll(list.get(0));
						method.invoke(obj, paramMap);
					}
				}
				map.put("STATE", true);
			return map;
		} catch (Exception e) {
			map.put("STATE", false);
			throw e;
		}
	}
	
	/**
	 * @todo 前台JS获取流，生成相关文件
	 * @param strean
	 * @param fileUrl
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> fileUploadStream(String strean,String fileUrl,String fileName) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//判断文件路径是否存在
			File filePath = new File(fileUrl);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			//上传文件
			String path = fileUrl + "/" + fileName;
			InputStream ins = new ByteArrayInputStream(strean.getBytes("ISO-8859-1"));
			OutputStream os = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
			resultMap.put("state", "sucess");
		}catch(Exception ex){
			resultMap.put("state", "false");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 上传图片，从一个路劲下赋值到另一个路劲下
	 * @param outUrl  导出图片路劲名称
	 * @param inUrl  生成图片路劲
	 * @param inFileName  生成图片名称
	 * @return
	 */
	public static Map<String, Object> uploadFormUrlToOtherUrl(String outUrl,String inUrl,String inFileName) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			File file = new File(outUrl);
			InputStream in = new FileInputStream(file);
			//判断文件路径是否存在
			File filePath = new File(inUrl);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			//上传文件
			String path = inUrl + "/" + inFileName;
			FileOutputStream out = new FileOutputStream(path);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
			resultMap.put("state", "sucess");
		}catch(Exception ex){
			resultMap.put("state", "false");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 后台获取流，生成相关文件
	 * @param ins
	 * @param fileUrl
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, Object> fileUploadIoStream(InputStream ins,String fileUrl,String fileName) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//判断文件路径是否存在
			File filePath = new File(fileUrl);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			//上传文件
			String path = fileUrl + "/" + fileName;
			OutputStream os = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
			resultMap.put("state", "sucess");
		}catch(Exception ex){
			resultMap.put("state", "false");
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * 将通过字符串中特殊字符拆分成list
	 * @param String word 
	 * @return list<String> list
	 * @author asus huangrui
	 */
	public static List<String> spitWord(String word){
		char[] stringStr=word.toCharArray();
		String paramWords=word;
		List<String> list=new ArrayList<String>();
		for(int i=0;i<stringStr.length;i++){
			char ch=stringStr[i];
			//匹配英文字符，数字，中文汉字
			String reg="[a-zA-Z0-9\u4e00-\u9fa5]";
			Pattern pattern = Pattern.compile(reg);
			 Matcher matcher = pattern.matcher(Character.toString(ch));
			if(!matcher.matches()){
				//通过特殊字符拆分
				int num=paramWords.indexOf(Character.toString(ch));
				if(num!=0){
					String param= paramWords.substring(0,num);
					System.out.println(param);
					list.add(param);
				}
				paramWords=paramWords.substring(num+1, paramWords.length());
			}
		}
		if(!"".equals(paramWords)){
			list.add(paramWords);
		}
		return list;
	}
	
	/**
	 * 从集合中获得最小数量
	 * 
	 * @param num
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int getMinNum(List num) {
		return Collections.min(num);
	}
	
	public static String sendToRequest(String reqUrl, Map parameters,
	          String recvEncoding){
	      HttpURLConnection url_con = null;
	      String responseContent = null;
	      try
	      {
	          StringBuffer params = new StringBuffer();
	          for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();){
	              Entry element = (Entry) iter.next();
	              params.append(element.getKey().toString());
	              params.append("=");
	              params.append(URLEncoder.encode(element.getValue().toString(),
	            		  recvEncoding));
	              params.append("&");
	          }

	         if (params.length() > 0){
	              params = params.deleteCharAt(params.length() - 1);
	          }

	         URL url = new URL(reqUrl);
	          url_con = (HttpURLConnection) url.openConnection();
	          url_con.setRequestMethod("POST");
	         
	          System.setProperty("sun.net.client.defaultReadTimeout", String
	                  .valueOf(JLTools.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
	          url_con.setConnectTimeout(5000);//（单位：毫秒）jdk
	          // 1.5换成这个,连接超时
	          url_con.setReadTimeout(5000);//（单位：毫秒）jdk 1.5换成这个,读操作超时
	          url_con.setDoOutput(true);
	          byte[] b = params.toString().getBytes();
	          url_con.getOutputStream().write(b, 0, b.length);
	          url_con.getOutputStream().flush();
	          url_con.getOutputStream().close();

	         InputStream in = url_con.getInputStream();
	          BufferedReader rd = new BufferedReader(new InputStreamReader(in,recvEncoding));
	          String tempLine = rd.readLine();
	          StringBuffer tempStr = new StringBuffer();
	          String crlf=System.getProperty("line.separator");
	          while (tempLine != null){
	              tempStr.append(tempLine);
	              tempStr.append(crlf);
	              tempLine = rd.readLine();
	          }
	          responseContent = tempStr.toString();
	          rd.close();
	          in.close();
	      }
	      catch (IOException e){
	    	  log.error("网络故障", e);
	      }
	      finally{
	          if (url_con != null)
	          {
	              url_con.disconnect();
	          }
	      }
	      return responseContent;
	  }
	
	/**
	 * @todo 企业介绍创建生成html静态页面
	 * @param fileName
	 * @param content
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void createHtml(String fileName, String content,String addurl,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String path = request.getSession().getServletContext().getRealPath(
					"/");
			path = path.replace("\\.", "");
			//静态页面存放路径
			String path_Html=JlAppResources.getProperty("path_dptp")+addurl;
			File file  = new File(path_Html);
	        if(!file.exists()) {  
	            try {  
	                file.mkdirs();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        path = path_Html + "/"+fileName;
	        File file1  = new File(path);
	        if(file1.exists()) {  
	            try {  
	            	file.delete();
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        
			path = path.replace("\\", "/");
			System.out.println(path);

			response.setContentType("text/html; charset=UTF-8");
			FileOutputStream fileoutputstream = new FileOutputStream(path);
			OutputStreamWriter outWrite = new OutputStreamWriter(
					fileoutputstream, "utf-8");
			BufferedWriter br = new BufferedWriter(outWrite);
			content = "<html><head><meta http-equiv=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\"/></head><body><div id=\"content\">"
					+ content + "</div></body></html>";
			br.write(content);
			br.flush();
			outWrite.flush();
			br.close();
			outWrite.close();
			fileoutputstream.close();
		} catch (Exception ex) {
			log.error("Error to createHtmlMethod.", ex);
			throw ex;
		}
	}
	

	/**
	 * @todo 创建生成html静态页面
	 * @param fileName
	 * @param content
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void createHtmlMethod(String fileName, String content,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String path = request.getSession().getServletContext().getRealPath(
					"/");
			path = path.replace("\\.", "");
			//静态页面存放路径
			String path_Html=JlAppResources.getProperty("path_spjs");
			path = path_Html + fileName;

			path = path.replace("\\", "/");
			System.out.println(path);

			response.setContentType("text/html; charset=UTF-8");
			FileOutputStream fileoutputstream = new FileOutputStream(path);
			OutputStreamWriter outWrite = new OutputStreamWriter(
					fileoutputstream, "utf-8");
			BufferedWriter br = new BufferedWriter(outWrite);
			content = "<html><head><meta http-equiv=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\"/></head><body><div id=\"content\">"
					+ content + "</div></body></html>";
			br.write(content);
			br.flush();
			outWrite.flush();
			br.close();
			outWrite.close();
			fileoutputstream.close();
		} catch (Exception ex) {
			log.error("Error to createHtmlMethod.", ex);
			throw ex;
		}
	}
	
	/**
	 * @todo 上传XML文件
	 * @param request
	 * @param hm
	 * @param listFile
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> uploadXml(HttpServletRequest request,Map<String, Object> hm,List<MultipartFile> listFile) throws Exception{
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		if(!upload.isMultipartContent(request)){
			hm.put("Flag", "error");
			return hm;
		}
		for(int i=0;i<listFile.size();i++){
			MultipartFile file=listFile.get(i);
			try {
				InputStream in=file.getInputStream();
				String path=JlAppResources.getProperty("tempFile")+hm.get("gsid");
				String oldFileName=file.getOriginalFilename();
				if(!"".equals(oldFileName) && oldFileName != null){
					String fileSuffix=oldFileName.substring(oldFileName.lastIndexOf("."));//截取文件后缀
					String newFileName=System.currentTimeMillis()+(int)(Math.random()*10)+oldFileName;
					File filePath=new File(path);
					if(!filePath.exists()){
						filePath.mkdirs();
						System.out.println("创建目录为："+filePath);
					}	
					FileOutputStream out=new FileOutputStream(filePath+"/"+newFileName);
					byte buffer[] = new byte[1024];
					int len = 0;
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
					}
					in.close();
					out.close();
					String filepath=filePath+"/"+newFileName;
					hm.put("filepath", filepath);
					int n = 1;
					if(n<=0){
						hm.put("Flag", "error");
					}else{
						hm.put("Flag", "success");
					}
				}else{
					hm.put("Flag", "error");
					return hm;
				}
			} catch (IOException e) {
				e.printStackTrace();
				hm.put("Flag", "error");
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				hm.put("Flag", "error");
				throw e;
			}
		}
		return hm;
	}
	
	/**
	 * @todo 删除文件 flag:false删除失败 true删除成功
	 * @param filePath
	 */
	public static boolean deleteXml(String filePath){
		boolean flag = false;
		File file = new File(filePath);
		if(file.exists()){
			flag =file.delete();
		}
		return flag;
	}
	
	/**
	 * @todo 获取开关当前值
	 * @param JLCO01
	 * @return
	 */
	public static int getCurConf(int JLCO01){
		int curConf=0;
		List list = Globals.confList;
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			if(((Long)map.get("JLCO01")).intValue() == JLCO01){
				curConf = ((Long)map.get("JLCO04")).intValue();
				break;
			}
		}
		return curConf;
	}
	
	/**
	 * @todo 获取在字符串中特定字符第几次出现的位置
	 * @param str 被查询的字符串
	 * @param splitStr 查找的字符
	 * @param num 要找第几次出现
	 * @return
	 */
	public static int getStrSplitNum(String str,String splitStr, int num) { 
        int i = 0; 
        int star = 0; 
        int index = 0;
        for(i=0;i<num;i++){
        	index = str.indexOf(splitStr, star); 
        	star = index +1;
        }
        return index;
    } 
	
	/**
	 * @todo 获取在一个字串中，指定字符出现次数
	 * @param str  字串
	 * @param showStr  制定字符
	 * @return
	 */
	public static int getStringRepeatShowNum(String str,String showStr){
		int num = 0;
		for(int i=0;i<str.length();i++){
			if(String.valueOf(str.charAt(i)).equals(showStr)){
				num=num+1;
			}
		}
		return num;
	}
	
	/**
	 * @todo 将一个 JavaBean 对象转化为一个 Map
	 * @param bean
	 * @return
	 * @throws RuntimeException
	 * @throws Exception
	 */
	public static Map convertBean(Object bean) throws RuntimeException,
	Exception {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = null;

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if ((!propertyName.equals("class")) && (propertyDescriptors[i].getPropertyType() != null)) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}
	
	public static String getMapStringValue(Map<String, ?> map, String key, String defaultValue){
		String value = defaultValue;
		if( map.containsKey(key) && null != map.get(key) && !"".equals(String.valueOf(map.get(key))) ){
			value = String.valueOf(map.get(key));
		}
		return value;
	}
}