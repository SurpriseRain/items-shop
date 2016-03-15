package com.jlsoft.utils;

/**
 *
 * <p>Title: 数据库连接接口</p>
 *
 * <p>Description: 对于系统的数据库背景性质进行抽象定义.</p>
 *
 * @version V7.0
 */
public interface JLDBBase {
    /**
     * 得到数据库的连接地址的抽象方法
     * @return String - 数据库的连接地址
     * @throws JLException
     */
    public abstract String getCN_URL() throws Exception;

    /**
     * 到数据库的驱动程序名的抽象方法
     * @return String - 数据库的驱动程序名
     * @throws JLException
     */
    public abstract String getCN_CLS_NAME() throws Exception;
}
