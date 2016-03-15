package com.jlsoft.utils.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.utils.PropertiesReader;

public class initServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            new JKConfig().init();
            JlAppSqlConfig.initMybatis();
            PropertiesReader.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
