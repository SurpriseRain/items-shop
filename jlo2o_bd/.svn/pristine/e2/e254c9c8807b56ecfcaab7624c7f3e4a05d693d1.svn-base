package com.jlsoft.framework;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class JlAppSqlConfig {

    private static Logger logger = Logger.getLogger(JlAppSqlConfig.class);
    private static final HashMap DATA_SOURCES = new HashMap();

    public static void initMybatis() {
        InputStream inputStream = null;
        SqlSessionFactory sqlSessionFactory = null;
        SqlSession session = null;

        try {
            inputStream = Resources.getResourceAsStream("select-config-o2o.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            DATA_SOURCES.put("o2o", sqlSessionFactory);

            inputStream.close();

            logger.info(DATA_SOURCES.keySet());
        } catch (Exception ex) {
            logger.error("initMybatis err", ex);
        }
    }

    public static SqlSession getSqlMapInstance(String dataSourceName) {
        return ((SqlSessionFactory) DATA_SOURCES.get(dataSourceName)).openSession();
    }

    public static SqlSession getO2OSqlMapInstance() {
        return ((SqlSessionFactory) DATA_SOURCES.get("o2o")).openSession();
    }
    
    public static SqlSession getSCMSqlMapInstance() {
        return ((SqlSessionFactory) DATA_SOURCES.get("scm")).openSession();
    }

    public static SqlSession getSHSqlMapInstance() {
        return ((SqlSessionFactory) DATA_SOURCES.get("sh")).openSession();
    }

    public static SqlSession getVIPSqlMapInstance() {
        return ((SqlSessionFactory) DATA_SOURCES.get("vip")).openSession();
    }
}
