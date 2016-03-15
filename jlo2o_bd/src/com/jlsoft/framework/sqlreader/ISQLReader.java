package com.jlsoft.framework.sqlreader;

import java.util.List;
import java.util.Map;

public interface ISQLReader {

    public List<Map<String, Object>> query(String sql) throws Exception;
    
    public List<Map<String, Object>> query(String sql, Object[] args) throws Exception;
     
}
