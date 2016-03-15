package com.jlsoft.utils;

import com.jlsoft.utils.config.JKConfig;
import com.jlsoft.utils.rcl.RclFun;
import java.util.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * <p>Title: 宏定义类</p>
 *
 * <p>Description: 一些较固定的数据库连接相关常量和常量得到方法存放在此类中</p>
 *
 * <p>Copyright: 2005</p>
 *
 * <p>Company: JLSoftware</p>
 *
 * @version V7.0
 */
public class JLDefsys
        extends HttpServlet {

    public static int IGSJB = 2;
    /**
     * 当前系统版本号
     */
    public static final String CURRENT_VERSION = "7.0.0.0";
    /**
     * 双引号
     */
    public static final char CHAR_QUOT = '"';
    /**
     * 单引号
     */
    public static final String STR_QUOT = "'";
    /**
     * 回车
     */
    public static final char CHAR_ENTER = '\r';
    /**
     * 换行
     */
    public static final char CHAR_RETURN = '\n';
    /**
     * XML字符集定义
     */
    public static final String XML_ENCODING = "GB2312";
    /**
     * 写XML文档时缓冲区大小
     */
    public static final int XML_BUFFER_SIZE = 1024000;
    /**
     * 当前连接池类型
     */
    public static String CURRENT_CONNECTION_POOL_LX = "";
    /**
     * 系统报错日志文件
     */
    public final static String ERR_LOG_FILE_F = "jlerr.log";
    /**
     * 日处理日志文件
     */
    public final static String RCL_LOG_FILE_F = "jlrcl.log";
    /**
     * 处理时长跟踪文件
     */
    public final static String TIMER_LOG_FILE_F = "jltimer.log";
    /*
     * 记录超时的SQL
     */
    public final static String SQL_TIMEOUT_LOG_FILE = "jlsql_timeout.log";
    /**
     * 查询日志
     */
    public final static String QUERY_LOG_FILE_F = "jlquery.log";
    /**
     * 系统配置文件名
     */
    public final static String JL_CONFIG_FILE_F = "jlconfig.xml";
    public final static String LoggerXML_F = "JLlog4j.xml";
    public static String QUERY_LOG_FILE = "";
    public static String ERR_LOG_FILE = "";
    /**
     * 日处理日志文件
     */
    public static String RCL_LOG_FILE = "";
    /**
     * 处理时长跟踪文件
     */
    public static String TIMER_LOG_FILE = "";
    /**
     * 系统配置文件名
     */
    public static String JL_CONFIG_FILE = "";
    public static String LoggerXML = "";
    public final static String JL_CONFIG_HOME = "JLCONFIG_PATH";
    /**
     * 当前的操作系统类型，默认值为WINDOWS 9X
     */
    public static int CURRENT_OSLX = 0;
    /**
     * GBK编码集
     */
    public static final String GBK = "GBK";
    /**
     * UTF-8编码集
     */
    public static final String UTF8 = "UTF-8";
    /**
     * cp850编码集
     */
    public static final String CP850 = "cp850";
    /**
     * GB2312编码集
     */
    public static final String GB2312 = "GB2312";
    public static final String ISO_8859_1 = "ISO-8859-1";
    /**
     * 是否允许调试信息
     */
    public static boolean DEBUG_ENABLED = true;
    /**
     * 数据库名
     */
    private static String DB_NAME = "";
    /**
     * 数据库IP
     */
    private static String CN_IP = "";
    /**
     * 数据库端口号
     */
    private static String CN_PORT = "";
    /**
     * 数据库实例名
     */
    private static String CN_SERVER_NAME = "";
    /**
     * 数据库连接用户名
     */
    public static String CN_USER = "";
    /**
     * 数据库连接用户密码
     */
    public static String CN_PWD = "";
    /**
     * .
     * 丛远双密码控制
   *
     */
    public static String CN_RETPWD = "";//yc add
    /**
     * 连接池的JNDI名称
     */
    public static String POOL_JNDI = "";
    /**
     * 连接池主机IP
     */
    public static String POOL_IP = "";
    /**
     * 连接池服务端口号
     */
    public static String POOL_PORT = "";
    /**
     * 第三方连接地址
     */
    public static String OTHER_CN_URL = "";
    /**
     * 第三方驱动程序名
     */
    public static String OTHER_CN_CLS_NAME = "";
    /**
     * 第三方用户名
     */
    public static String OTHER_CN_USER = "";
    /**
     * 第三方用户密码
     */
    public static String OTHER_CN_PWD = "";
    /**
     * 自动日处理时间
     */
    public static String RCL_TIME = "";
    /**
     * 返回记录条数限制
     */
    public static String CN_ROW_SIZE = "";
    public static String QUERY_LOG = "";
    public static String QUERY_CONDITION = "";
    /**
     * 是否检查半个汉字导致的XML文本错误
     */
    public static String CHECK_HALF_WORD = "0";
    /*
     * 限制最长SQL语句长度
     */
    public static String MAX_SQL_LENGTH = "1048576";
    /*
     * 整个查询包含拼XML超时记录 单位是秒
     */
    public static String MAX_SQL_TIMEOUT = "180";
    /*
     * 只是查询超时时间 单位是秒
     */
    public static String MAX_QUERY_TIMEOUT = "60";
    /**
     * WEBLOGIC上下文工厂字符串
     */
    public static final String WEBLOGIC_INITFACTORY_STRING =
            "weblogic.jndi.WLInitialContextFactory";
    /**
     * WEBSPHERE上下文工厂字符串
     */
    public static final String WEBSPHERE_INITFACTORY_STRING =
            "com.ibm.websphere.naming.WsnInitialContextFactory";
    /**
     * JBOSS上下文工厂字符串
     */
    public static final String JBOSS_INITFACTORY_STRING =
            "org.jnp.interfaces.NamingContextFactory";
    public static boolean RES_LOADED_FLAG = false;
    public static final int CACHED_SIZE = 100; //查询缓存记录条数
    public static java.util.Timer time = new Timer(true);
    public static boolean isBeginRcl = false; //是否已经开始日处理
    public static String[] AryTables = null; //查询分离表
    public static String Message_HttpAddr = ""; //短信平台配置  接口地址
    public static String Message_HttpAddr_BY = "";//短信平台配置（备用）接口地址
    public static String Message_Send = ""; //短信平台配置  发送短信函数名
    public static String Message_Receive = ""; //短信平台配置  接收短信函数名
    public static String Message_GetMoney = ""; //短信平台配置  查询短信余额
    public static String Message_userId = ""; //短信平台配置  用户名
    public static String Message_password = ""; //短信平台配置 密码
    public static long Message_SendTime = 0; //短信平台配置 发送轮循时间
    public static long Message_ReceiveTime = 0; //短信平台配置 接收轮循时间
    public static String Message_Project = ""; //项目参数
    public static String HTD_SWITCH = "0"; // HTD平台开关
    public static String Ws_Adds = ""; //三方物流webservice地址
    public static String Eas_Service = "";//金蝶 EAS webservice地址
    public static String Instance = "";//金蝶 EAS 数据中心地址
    public static String TRANSIT_MODE = "0"; //0.内存压缩模式 1.IO压缩模式 
    public static String HOSTNAME = "";

    @Override
    public void init() throws ServletException {
        try {
            JLDefsys.loadRes();
            try {
//                if (!"".equals(Message_HttpAddr)) {
//                    new jlserver.jlinterface.MessageInterface().SenderMessage(); //发送短信
//                    new jlserver.jlinterface.MessageInterface().ReceiveMessage(); //获取短信
//                }
//                if (HTD_SWITCH.equals("1")) {
//                    new jlserver.jlinterface.HTD().SyncHTDData();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 本方法用于从当前系统数据库读取数据，以获得数据库资源的配置情况，其中包括以上常量中的：
     * 第三方连接相关数据，数据库连接相关数据，数据连接池相关数据。 <p>
     *
     * @throws JLException
     */
    public static void loadRes() throws Exception {
        Vector OTHER_CN_URL_V = null;
        Vector OTHER_CN_CLS_NAME_V = null;
        Vector OTHER_CN_USER_V = null;
        Vector OTHER_CN_PWD_V = null;

        Vector DB_NAME_V = null;
        Vector CN_IP_V = null;
        Vector CN_PORT_V = null;
        Vector CN_SERVER_NAME_V = null;
        Vector CN_USER_V = null;
        Vector CN_PWD_V = null;
        Vector CN_RETPWD_V = null;  //yc add
        Vector POOL_JNDI_V = null;
        Vector CURRENT_CONNECTION_POOL_LX_V = null;
        Vector RCL_TIME_V = null;
        Vector CN_ROW_SIZE_V = null;
        Vector QUERY_LOG_V = null;
        Vector QUERY_CONDITION_V = null;
        Vector CHECK_HALF_WORD_V = null;
        Vector TRANSIT_MODE_V = null;
        Vector Message_HttpAddr_V = null; //短信平台配置  接口地址
        Vector Message_HttpAddr_BY_V = null; //短信平台配置  接口地址
        Vector Message_Send_V = null; //短信平台配置  发送短信函数名
        Vector Message_Receive_V = null; //短信平台配置  接收短信函数名
        Vector Message_GetMoney_V = null; //短信平台配置 短信余额
        Vector Message_userId_V = null; //短信平台配置  用户名
        Vector Message_password_V = null; //短信平台配置 密码
        Vector Message_SendTime_V = null; //短信平台配置 发送轮循时间
        Vector Message_ReceiveTime_V = null; //短信平台配置 接收轮循时间
        Vector Message_Project_V = null;//项目参数
        Vector HTD_SWITCH_V = null;
        Vector Ws_Adds_V = null;//三方物流webservice地址
        Vector Eas_Service_V = null;//EAS WEBSERVICE
        Vector Instance_V = null;//eas  数据中心地址
        BufferedReader reader = null;
        String tempStr = "";
        String ewXml = "";

        String s_t = "";
        try {
            JLTools.makeDirectory("jltemp");
            JLTools.deleteAllFile("jltemp");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        try {
            try {
                java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("cx");
                if (rb != null) {
                    AryTables = rb.getString("tables").toUpperCase().split(",");
                    rb = null;
                }
            } catch (Exception eee) {
            }
            s_t = JLTools.getConfigDir();

            JL_CONFIG_FILE = s_t + JL_CONFIG_FILE_F;
            TIMER_LOG_FILE = s_t + TIMER_LOG_FILE_F;
            LoggerXML = s_t + LoggerXML_F;
            ERR_LOG_FILE = s_t + ERR_LOG_FILE_F;
            RCL_LOG_FILE = s_t + RCL_LOG_FILE_F;
            QUERY_LOG_FILE = s_t + QUERY_LOG_FILE_F;

            System.out.println("V7_CONFIG_HOME :" + s_t);
            System.out.println("JL_CONFIG_FILE :" + JL_CONFIG_FILE);
            System.out.println("TIMER_LOG_FILE :" + TIMER_LOG_FILE);
            System.out.println("LoggerXML :" + LoggerXML);
            System.out.println("ERR_LOG_FILE:" + ERR_LOG_FILE);
            System.out.println("RCL_LOG_FILE:" + RCL_LOG_FILE);
            System.out.println("QUERY_LOG_FILE:" + QUERY_LOG_FILE);

            reader = new BufferedReader(new FileReader(JL_CONFIG_FILE));
            while ((tempStr = reader.readLine()) != null) {
                ewXml = ewXml + tempStr;
            }

            CURRENT_CONNECTION_POOL_LX_V = JLTools.getParameterValue(
                    "CURRENT_CONNECTION_POOL_LX", ewXml);
            CURRENT_CONNECTION_POOL_LX = CURRENT_CONNECTION_POOL_LX_V.get(0).toString();
            CURRENT_OSLX = JLTools.getOsLx();

            DB_NAME_V = JLTools.getParameterValue("DB_NAME", ewXml);
            CN_IP_V = JLTools.getParameterValue("CN_IP", ewXml);
            CN_PORT_V = JLTools.getParameterValue("CN_PORT", ewXml);
            CN_SERVER_NAME_V = JLTools.getParameterValue("CN_SERVER_NAME", ewXml);
            CN_USER_V = JLTools.getParameterValue("CN_USER", ewXml);
            CN_PWD_V = JLTools.getParameterValue("CN_PWD", ewXml);
            OTHER_CN_URL_V = JLTools.getParameterValue("OTHER_CN_URL", ewXml);
            OTHER_CN_CLS_NAME_V = JLTools.getParameterValue("OTHER_CN_CLS_NAME",
                    ewXml);
            OTHER_CN_USER_V = JLTools.getParameterValue("OTHER_CN_USER", ewXml);
            OTHER_CN_PWD_V = JLTools.getParameterValue("OTHER_CN_PWD", ewXml);
            RCL_TIME_V = JLTools.getParameterValue("RCL_TIME", ewXml);
            CN_ROW_SIZE_V = JLTools.getParameterValue("CN_ROW_SIZE", ewXml);
            CHECK_HALF_WORD_V = JLTools.getParameterValue("CHECK_HALF_WORD", ewXml);
            TRANSIT_MODE_V = JLTools.getParameterValue("TRANSIT_MODE", ewXml);
            HTD_SWITCH_V = JLTools.getParameterValue("HTD_SWITCH", ewXml);//达创抽去数据
            Message_HttpAddr_V = JLTools.getParameterValue("Message_HttpAddr", ewXml); //短信平台配置  接口地址
            Message_HttpAddr_BY_V = JLTools.getParameterValue("Message_HttpAddr_BY", ewXml); //短信平台配置  接口地址
            Message_Send_V = JLTools.getParameterValue("Message_Send", ewXml); //短信平台配置  发送短信函数名
            Message_Receive_V = JLTools.getParameterValue("Message_Receive", ewXml); //短信平台配置  接收短信函数名
            Message_GetMoney_V = JLTools.getParameterValue("Message_GetMoney", ewXml);//短信平台配置 短信余额接口
            Message_userId_V = JLTools.getParameterValue("Message_userId", ewXml); //短信平台配置  用户名
            Message_password_V = JLTools.getParameterValue("Message_password", ewXml); //短信平台配置 密码
            Message_SendTime_V = JLTools.getParameterValue("Message_SendTime", ewXml); //短信平台配置 发送轮循时间
            Message_ReceiveTime_V = JLTools.getParameterValue("Message_ReceiveTime", ewXml); //短信平台配置 接收轮循时间
            Message_Project_V = JLTools.getParameterValue("Message_Project", ewXml);//短信平台配置，项目参数
            Ws_Adds_V = JLTools.getParameterValue("Ws_Adds", ewXml);//三方物流webservice地址
            Eas_Service_V = JLTools.getParameterValue("Eas_Webservie", ewXml);//EAS webservice地址
            Instance_V = JLTools.getParameterValue("Instance", ewXml);//EAS 数据中心地址
//            try {
//                TMSJK.TMS_URL = JLTools.getParameterValue("TMS_URL", ewXml).get(0).toString();//20120920lzqadd 唯智接口 
//                TMSJK.TMS_YN_URL = JLTools.getParameterValue("TMS_YN_URL", ewXml).get(0).toString();//20120920lzqadd 唯智接口
//            } catch (Exception e) {
//            }
            DB_NAME = DB_NAME_V.get(0).toString();
            CN_IP = CN_IP_V.get(0).toString();
            CN_PORT = CN_PORT_V.get(0).toString();
            CN_SERVER_NAME = CN_SERVER_NAME_V.get(0).toString();
            CN_USER = CN_USER_V.get(0).toString();
            CN_PWD = CN_PWD_V.get(0).toString();

            OTHER_CN_URL = OTHER_CN_URL_V.get(0).toString();
            OTHER_CN_CLS_NAME = OTHER_CN_CLS_NAME_V.get(0).toString();
            OTHER_CN_USER = OTHER_CN_USER_V.get(0).toString();
            OTHER_CN_PWD = OTHER_CN_PWD_V.get(0).toString();
            RCL_TIME = RCL_TIME_V.get(0).toString();
            CN_ROW_SIZE = CN_ROW_SIZE_V.get(0).toString();
            try {
                HTD_SWITCH = HTD_SWITCH_V.get(0).toString();
            } catch (Exception e) {
                HTD_SWITCH = "0";
            }

            try {
                CHECK_HALF_WORD = CHECK_HALF_WORD_V.get(0).toString();
                if (CHECK_HALF_WORD == null || CHECK_HALF_WORD.equals("null")) {
                    CHECK_HALF_WORD = "0";
                }
            } catch (Exception e) {
                CHECK_HALF_WORD = "0";
            }
            try {
                TRANSIT_MODE = TRANSIT_MODE_V.get(0).toString();
                if (TRANSIT_MODE == null || TRANSIT_MODE.equals("null")) {
                    TRANSIT_MODE = "0";
                }
            } catch (Exception e) {
                TRANSIT_MODE = "0";
            }
            try {
                Message_HttpAddr = Message_HttpAddr_V.get(0).toString(); //短信平台配置  接口地址
            } catch (Exception e) {
                Message_HttpAddr = "";
            }
            try {
                Message_Project = Message_Project_V.get(0).toString(); //项目参数
            } catch (Exception e) {
                Message_Project = "";
            }
            try {
                Message_Send = Message_Send_V.get(0).toString(); //短信平台配置  发送短信函数名
            } catch (Exception e) {
                Message_Send = "";
            }
            try {
                Message_Send = Message_Send_V.get(0).toString(); //短信平台配置  发送短信函数名
            } catch (Exception e) {
                Message_Send = "";
            }
            try {
                Message_Receive = Message_Receive_V.get(0).toString(); //短信平台配置  接收短信函数名
            } catch (Exception e) {
                Message_Receive = "";
            }
            try {
                Message_userId = Message_userId_V.get(0).toString(); //短信平台配置  用户名
            } catch (Exception e) {
                Message_userId = "";
            }
            try {
                Message_password = Message_password_V.get(0).toString(); //短信平台配置 密码
            } catch (Exception e) {
                Message_password = "";
            }
            try {
                Message_SendTime = JLTools.strToLong(Message_SendTime_V.get(0).toString()); //短信平台配置 发送轮循时间
            } catch (Exception e) {
                Message_SendTime = 0;
            }
            try {
                Message_ReceiveTime = JLTools.strToLong(Message_ReceiveTime_V.get(0).
                        toString()); //短信平台配置 接收轮循时间
            } catch (Exception e) {
                Message_ReceiveTime = 0;
            }
            try {
                Message_GetMoney = Message_GetMoney_V.get(0).toString(); //短信平台配置  短信余额查询
            } catch (Exception e) {
                Message_GetMoney = "";
            }
            try {
                Message_HttpAddr_BY = Message_HttpAddr_BY_V.get(0).toString(); //短信平台配置  接口地址(备用)
            } catch (Exception e) {
                Message_HttpAddr_BY = "";
            }
            try {
                Ws_Adds = Ws_Adds_V.get(0).toString(); //三方物流webservice地址
            } catch (Exception e) {
                Ws_Adds = "";
            }
            try {
                Eas_Service = Eas_Service_V.get(0).toString(); //eas webservice地址
            } catch (Exception e) {
                Eas_Service = "";
            }
            try {
                Instance = Instance_V.get(0).toString(); //eas 数据中心地址
            } catch (Exception e) {
                Instance = "";
            }

            try {
                HOSTNAME = JLTools.getLocalHostName();
            } catch (Exception e) {
            }
            try {
                QUERY_LOG_V = JLTools.getParameterValue("QUERY_LOG", ewXml);
                QUERY_CONDITION_V = JLTools.getParameterValue("QUERY_CONDITION", ewXml);
                CN_RETPWD_V = JLTools.getParameterValue("CN_RETPWD", ewXml);//yc add
                QUERY_LOG = QUERY_LOG_V.get(0).toString();
                QUERY_CONDITION = QUERY_CONDITION_V.get(0).toString();
                CN_RETPWD = CN_RETPWD_V.get(0).toString();//yc add
            } catch (Exception e1) {
                QUERY_LOG = "";
                QUERY_CONDITION = "";
                CN_RETPWD = "";
            }
            if (!CURRENT_CONNECTION_POOL_LX.equals("NO_POOL")) {
                POOL_JNDI_V = JLTools.getParameterValue("POOL_JNDI", ewXml);
                POOL_JNDI = POOL_JNDI_V.get(0).toString();
                System.out.println("ZZZZZZZZ" + POOL_JNDI);
            }

            // DOMConfigurator.configure(LoggerXML);

        } catch (Exception e) {
            throw e;
        } finally {
            RES_LOADED_FLAG = true;
            System.out.println("Load Res Is Running!");
            CURRENT_CONNECTION_POOL_LX_V.clear();
            RCL_TIME_V.clear();
            CN_ROW_SIZE_V.clear();
            if (CURRENT_CONNECTION_POOL_LX.equals("NO_POOL")) {
                DB_NAME_V.clear();
                CN_IP_V.clear();
                CN_PORT_V.clear();
                CN_SERVER_NAME_V.clear();
                CN_USER_V.clear();
                CN_PWD_V.clear();
                CN_RETPWD_V.clear();//yc add
            } else {
                POOL_JNDI_V.clear();
            }
            reader.close();
            reader = null;
            CURRENT_CONNECTION_POOL_LX_V = null;
            DB_NAME_V = null;
            CN_IP_V = null;
            CN_PORT_V = null;
            CN_SERVER_NAME_V = null;
            CN_USER_V = null;
            CN_PWD_V = null;
            POOL_JNDI_V = null;
            OTHER_CN_URL_V = null;
            OTHER_CN_CLS_NAME_V = null;
            OTHER_CN_USER_V = null;
            OTHER_CN_PWD_V = null;
            RCL_TIME_V = null;
            CN_ROW_SIZE_V = null;
            QUERY_LOG_V = null;
            QUERY_CONDITION_V = null;
            CHECK_HALF_WORD_V = null;
            TRANSIT_MODE_V = null;
            CN_RETPWD_V = null;
            Message_HttpAddr_V = null; //短信平台配置  接口地址
            Message_HttpAddr_BY_V = null; //短信平台配置  接口地址
            Message_Send_V = null; //短信平台配置  接收短信函数名
            Message_Receive_V = null; //短信平台配置  发送短信函数名
            Message_GetMoney_V = null;//短信平台配置 短信余额函数名
            Message_userId_V = null; //短信平台配置  用户名
            Message_password_V = null; //短信平台配置 密码
            Message_SendTime_V = null; //短信平台配置 发送轮循时间
            Message_ReceiveTime_V = null; //短信平台配置 接收轮循时间
            Message_Project_V = null; //项目参数
            Ws_Adds_V = null; //三方物流webservice地址
        }
        IGSJB = 2;
        new JKConfig().init();
    }

//    static {
//        time.schedule(new java.util.TimerTask() {
//
//            public void run() {
//                java.util.Date d1 = new java.util.Date();
//                String rcl_t[] = null;
//                try {
//                    if (!RCL_TIME.trim().equals("")) {
//                        rcl_t = RCL_TIME.split(":");
//                        if (Integer.parseInt(rcl_t[0]) == d1.getHours()
//                                && Integer.parseInt(rcl_t[1]) == d1.getMinutes()) {
//                            new RclFun().doRcl();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Auto Rcl Fail!");
//                }
//            }
//        }, 0, 0XEA60);
//    }

    /**
     * 通过本方法可以得到读取结果中的数据库名称。一般来说，数据库名称是在当前系统数据库读取数据成功后得到，
     * 然而在读取数据未启动的情况下，用此方法也可以得到，因为此方法会在得到返回值前自动启动数据的读取。 <p>
     *
     * @return String - 返回数据库名称
     * @throws JLException
     */
    public static String getDBName() throws Exception {
        if (!RES_LOADED_FLAG) {
            loadRes();
        }
        return DB_NAME;
    }

    /**
     * 此方法根据从读取结果中得到的数据库名，在应用了JLDBBase接口的各个数据库连接类中分辨中对应的数据库驱动程序名。
     * 一般来说，数据库名是在当前系统数据库读取数据成功后得到，然读取数据未动的情况下，用此方法也可以得到，
     * 因为此方法会在得到返回值前自动启动数据的读取。 <p>
     *
     * @return String - 返回数据库驱动程序名
     * @throws JLException
     */
    public static String getDBInfo_CLS() throws Exception {
        String sTmp = "";
        if (!RES_LOADED_FLAG) {
            loadRes();
        }
        if (getDBName().equals("ORACLE")) {
            sTmp = OracleDB.OracleDB_L.getCN_CLS_NAME();
        } else if (getDBName().equals("SYBASE")) {
            sTmp = SybaseDB.SybaseDB_L.getCN_CLS_NAME();
        } else if (getDBName().equals("DB2")) {
            sTmp = DB2DB.DB2DB_L.getCN_CLS_NAME();
        } else if (getDBName().equals("SQLSERVER")) {
            sTmp = SqlserverDB.SqlserverDB_L.getCN_CLS_NAME();
        }
        return sTmp;
    }

    /**
     * 此方法根据从读取结果中得到的数据库名，在应用了JLDBBase接口的各个数据库连接类中分辨中对应的数据库连接地址。
     * 一般来说，数据库名是在当前系统数据库读取数据成功后得到，然而在读取数据未启动的情况下，用此方法也可以得到，
     * 因为此方法会在得到返回值前自动启动数据的读取。 <p>
     *
     * @return String - 返回数据库连接地址
     * @throws JLException
     */
    public static String getDBInfo_URL() throws Exception {
        String sTmp = "";
        if (!RES_LOADED_FLAG) {
            loadRes();
        }
        if (getDBName().equals("ORACLE")) {
            sTmp = OracleDB.OracleDB_L.getCN_URL() + CN_IP + ":" + CN_PORT + ":"
                    + CN_SERVER_NAME;
        } else if (getDBName().equals("SYBASE")) {
            sTmp = SybaseDB.SybaseDB_L.getCN_URL() + CN_IP + ":" + CN_PORT + "/"
                    + CN_SERVER_NAME + "?charset=cp850";
        } else if (getDBName().equals("DB2")) {
            sTmp = DB2DB.DB2DB_L.getCN_URL() + CN_IP + ":" + CN_PORT + "/"
                    + CN_SERVER_NAME;
        } else if (getDBName().equals("SQLSERVER")) {
            sTmp = SqlserverDB.SqlserverDB_L.getCN_URL() + CN_IP + ":" + CN_PORT
                    + ";DatabaseName=" + CN_SERVER_NAME;
        }
        return sTmp;
    }

    /**
     * 通过本方法可以得到读取结果中的数据库连接用户名。一般来说，用户名是在当前系统数据库读取数据成功后得到，
     * 然而在读取数据未启动的情况下，用此方法也可以得到，因为此方法会在得到返回值前自动启动数据的读取。 <p>
     *
     * @return String - 返回数据库连接用户名
     * @throws JLException
     */
    public static String getDBInfo_USER() throws Exception {
        if (!RES_LOADED_FLAG) {
            loadRes();
        }
        return CN_USER;
    }

    /**
     * 通过本方法可以得到读取结果中的数据库连接用户密码。一般来说，用户密码是在当前系统数据库读取数据成功后得到，
     * 然而在读取数据未启动的情况下，用此方法也可以得到，因为此方法会在得到返回值前自动启动数据的读取。 <p>
     *
     * @return String - 返回数据库连接用户密码
     * @throws JLException
     */
    public static String getDBInfo_PWD() throws Exception {
        if (!RES_LOADED_FLAG) {
            loadRes();
        }
        return CN_PWD;
    }

    /**
     * 通过本方法可以得到读取结果中的数据库实例名。一般来说，数据库实例名是在当前系统数据库读取数据成功后得到，
     * 然而在读取数据未启动的情况下，用此方法也可以得到，因为此方法会在得到返回值前自动启动数据的读取。 <p>
     *
     * @return String - 返回数据库实例名
     * @throws JLException
     */
    public static String getDB_ServerName() throws Exception {
        if (!RES_LOADED_FLAG) {
            loadRes();
        }
        return CN_SERVER_NAME;
    }
}
