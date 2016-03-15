package com.jlsoft.utils;

import com.jlsoft.framework.pi.util.MemoryCache;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DBUtils {

    public static Map getTableColumnNames(DataSource ds, String tableName) throws SQLException {
        Connection con = DataSourceUtils.getConnection(ds);        
        try {
            String catalog = con.getCatalog();
            String key = catalog + "_" + tableName + "_ColumnType";
            Map columnNames = (Map) MemoryCache.getInstance().get(key);
            if (columnNames == null) {
                String sql = "SELECT * FROM " + tableName + " WHERE 1=2";
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                columnNames = new HashMap();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columnNames.put(rsmd.getColumnName(i + 1), rsmd.getColumnType(i + 1));
                }
                MemoryCache.getInstance().put(key, columnNames);
            }
            return columnNames;
        } finally {
            DataSourceUtils.releaseConnection(con, ds);
        }
    }
    
    public static int getColumnType(DataSource ds, String tableName, String columnName) throws SQLException {
        Map columnNames = getTableColumnNames(ds, tableName);
        int result = -1;
        Object value = columnNames.get(columnName);
        if (value != null) {
            result = ((Number) value).intValue();
        }
        return result;
    }    

    public static String getTableName(String sql) {
        String regex = "(INSERT INTO)(.+?)(\\()";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql.toUpperCase());
        if (matcher.find()) {
            return matcher.group(2).trim();
        }

        regex = "(UPDATE)(.+?)(SET)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql.toUpperCase());
        if (matcher.find()) {
            return matcher.group(2).trim();
        }

        regex = "(DELETE FROM)(.+?)(WHERE)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql.toUpperCase());
        if (matcher.find()) {
            return matcher.group(2).trim();
        }

        regex = "(DELETE)(.+?)(WHERE)";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(sql.toUpperCase());
        if (matcher.find()) {
            return matcher.group(2).trim();
        }

        return "";
    }
}
