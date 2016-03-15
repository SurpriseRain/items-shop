package com.jlsoft.utils;

public final class SybaseDB implements JLDBBase{
  /**
   * 声明一个新的SybaseDB类型实例的常量，允许多态。
   */
  public static SybaseDB SybaseDB_L=new SybaseDB();

  /**
   * 此处将本类的实例初始化方法设为内部调用。这样处理的用意在于:
   * 为了尽量减少创建新的实例，已经声明了一个本类实例作公共常量。只要在调用此类的时候，通过常量调用方法既可。
   */
  private SybaseDB () {}

  /**
    * 此方法用以得到Sybase数据库连接的地址。<p>
    * @return String -  返回Sybase数据库的连接地址。<p>
    * @throws JLException
    */
  public String getCN_URL() throws Exception
  {
   return "jdbc:sybase:Tds:";
  }

  /**
     * 此方法用以得到Sybase数据库连接的驱动程序名。<p>
     * @return String -  返回Sybase数据库连接的驱动程序名。<p>
     * @throws JLException
   */
  public String getCN_CLS_NAME() throws Exception
  {
   return "com.sybase.jdbc2.jdbc.SybDriver";
  }
}
