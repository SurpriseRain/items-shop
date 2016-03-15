package com.jlsoft.utils;

/**
 *
 * <p>Title: DB2 数据库连接类</p>
 *
 * <p>Description: 此类应用了JLDBBase接口，是关于DB2 数据库的连接，包括数据库连接地址和驱动程序名。 </p>
 *
 * <p>Company: JLSoftware</p>
 *
 * @version V7.0
 */
public final class DB2DB implements JLDBBase
{
    /**
     * 声明一个新的DB2DB类型实例的常量，允许多态。
     */
    public static DB2DB DB2DB_L=new DB2DB();

    /**
     * 此处将本类的实例初始化方法设为内部调用。这样处理的用意在于:
     * 为了尽量减少创建新的实例，已经声明了一个本类实例作公共常量。只要在调用此类的时候，通过常量调用方法既可。
     */
    private DB2DB (){}

    /**
    * 此方法用以得到DB2数据库连接的地址。<p>
    * @return String -  返回DB2数据库的连接地址。<p>
    * @throws JLException
    */
    public String getCN_URL() throws Exception
    {
      return "jdbc:db2://";
    }

    /**
    * 此方法用以得到DB2数据库连接的驱动程序名。<p>
    * @return String -  返回DB2数据库连接的驱动程序名。<p>
    * @throws JLException
    */
    public String getCN_CLS_NAME() throws Exception
    {
      return "com.ibm.db2.jdbc.app.DB2Driver";
    }
}
