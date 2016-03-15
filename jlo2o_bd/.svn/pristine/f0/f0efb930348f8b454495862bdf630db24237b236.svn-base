package com.jlsoft.utils;

/**
 *
 * <p>Title: Oracle 数据库连接类</p>
 *
 * <p>Description: 此类应用了JLDBBase接口，是关于Oracle数据库的连接，包括数据库连接地址和驱动程序名。 </p>
 * @version V7.0.0.0
 */
public final class OracleDB implements JLDBBase{
  /**
   * 声明一个新的OracleDB类型实例的常量，允许多态。
   */
  public static OracleDB OracleDB_L=new OracleDB();

  /**
   * 此处将本类的实例初始化方法设为内部调用。这样处理的用意在于:
   * 为了尽量减少创建新的实例，已经声明了一个本类实例作公共常量。只要在调用此类的时候，通过常量调用方法既可。
   */
  private OracleDB () {}

  /**
   * 此方法用以得到Oracle数据库连接的地址。<p>
   * @return String -  返回Oracle数据库的连接地址。<p>
   * @throws JLException
   */
  public String getCN_URL() throws Exception
  {
   return "jdbc:oracle:thin:@";
  }

  /**
   * 此方法用以得到Oracle数据库连接的驱动程序名。<p>
   * @return String -  返回Oracle数据库连接的驱动程序名。<p>
   * @throws JLException
   */
  public String getCN_CLS_NAME() throws Exception
  {
   return "oracle.jdbc.driver.OracleDriver";
  }
}
