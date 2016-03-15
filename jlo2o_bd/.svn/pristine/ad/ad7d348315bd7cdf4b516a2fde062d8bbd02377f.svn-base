package com.jlsoft.framework.pi.util;

import com.jlsoft.framework.pi.transport.JdbcReaderTransportor;
import java.sql.*;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;

public class DBUtils {

    public static List getTableColumnNames(String tableName, Connection connection) throws SQLException {
        List columnNames = (List) MemoryCache.getInstance().get(tableName + "_ColumnNames");
        if (columnNames == null) {
            String sql = "SELECT * FROM " + tableName + " WHERE 1=2";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            columnNames = new ArrayList(rsmd.getColumnCount());
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            MemoryCache.getInstance().put(tableName + "_ColumnNames", columnNames);
        }
        return columnNames;
    }

    public static List getTablePrimaryKeys(String tableName, Connection connection) throws SQLException {
        List primaryKeys = (List) MemoryCache.getInstance().get(tableName + "_PrimaryKey");
        if (primaryKeys == null) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getPrimaryKeys("", databaseMetaData.getUserName(), tableName);
            primaryKeys = new ArrayList(rs.getRow());
            while (rs.next()) {
                primaryKeys.add(rs.getString(4).toString());
            }
            MemoryCache.getInstance().put(tableName + "_PrimaryKey", primaryKeys);
        }
        return primaryKeys;
    }

    public static String generateInsertStatement(String tableName, String[] columnNames) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(");
        StringBuffer params = new StringBuffer();
        for (int i = 0; i < columnNames.length; i++) {
            sql.append(columnNames[i]).append(",");
            params.append("?,");
        }
        sql.setCharAt(sql.length() - 1, ')');
        params.setCharAt(params.length() - 1, ')');
        sql.append(" VALUES (").append(params);
        return sql.toString();
    }

    public static String generateUpdateStatement(String tableName, String[] columnNames, List primaryKeys) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        for (int i = 0; i < columnNames.length; i++) {
            if (!primaryKeys.contains(columnNames[i])) {
                sql.append(columnNames[i]).append(" = ?,");
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        for (int i = 0; i < primaryKeys.size(); i++) {
            if (i == 0) {
                sql.append(" WHERE ").append((String) primaryKeys.get(i)).append(" = ? ");
            } else {
                sql.append(" AND ").append((String) primaryKeys.get(i)).append(" = ? ");
            }
        }
        return sql.toString();
    }

    public static String generateDeleteStatement(String tableName, String[] primaryKeys) throws SQLException {
        StringBuilder sql = new StringBuilder("DELETE " + tableName + " WHERE ");
        for (int i = 0; i < primaryKeys.length; i++) {
            if (i == 0) {
                sql.append(primaryKeys[i]).append(" = ? ");
            } else {
                sql.append(" AND ").append(primaryKeys[i]).append(" = ? ");
            }
        }
        return sql.toString();
    }

    public static void eliminate(List tableColumnNames, Object[] inputColumns) {
        Set inputColumnSet = new HashSet();
        CollectionUtils.addAll(inputColumnSet, inputColumns);
        Iterator iterator = tableColumnNames.iterator();
        while (iterator.hasNext()) {
            if (inputColumnSet.contains(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public static void intersection(List tableColumnNames, Object[] inputColumns) {
        Set inputColumnSet = new HashSet();
        CollectionUtils.addAll(inputColumnSet, inputColumns);
        Iterator iterator = tableColumnNames.iterator();
        while (iterator.hasNext()) {
            if (!inputColumnSet.contains(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public static String[] getOutputColumns(final List columns) {
        String[] outputColumns;
        if (columns == null || columns.isEmpty()) {
            outputColumns = null;
        } else {
            outputColumns = (String[]) columns.toArray(new String[columns.size()]);
        }
        return outputColumns;
    }

    public static Object getTIME01() {
        JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
        transportor.setSql("select to_char(systimestamp,'YYYYMMDDHH24MISSFF2') TIME01 from dual");
        try {
            return ((Map) (((Object[]) transportor.process(null))[0])).get("TIME01");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Object getDate() {
        JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
        transportor.setSql("select TO_DATE(TO_CHAR(SYSDATE,'YYYY-MM-DD'),'YYYY-MM-DD') DATE01 from dual");
        try {
            return ((Map) (((Object[]) transportor.process(null))[0])).get("DATE01");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Object getDateTime() {
        JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
        transportor.setSql("select SYSDATE DateTime from dual");
        try {
            return ((Map) (((Object[]) transportor.process(null))[0])).get("DateTime");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
