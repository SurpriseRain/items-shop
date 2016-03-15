package com.jlsoft.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JLConn {
	@Autowired
    public JdbcTemplate o2o;
    @Autowired
    public JdbcTemplate scm;
    @Autowired
    public JdbcTemplate sh;
    @Autowired
    public JdbcTemplate vip;
}
