package com.jlsoft.framework.sqlreader;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringSQLReader implements ISQLReader {

    private JdbcTemplate conn;

    public SpringSQLReader(JdbcTemplate conn) {
        this.conn = conn;
    }

    public List<Map<String, Object>> query(String sql) throws Exception {
        return conn.queryForList(sql);
    }

    public List<Map<String, Object>> query(String sql, Object[] args) throws Exception {
        return conn.queryForList(sql, args);
    }
}
