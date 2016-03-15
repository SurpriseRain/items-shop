package com.jlsoft.framework.sqlwriter;

import com.jlsoft.utils.DBUtils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

public class SpringSQLWriter extends AbstractSQLWriter {

    private JdbcTemplate conn;
    private String tableName = "";

    public SpringSQLWriter(JdbcTemplate conn, String sSQL) throws Exception {
        SQLConvertor sqlFormat = new SQLConvertor(sSQL);
        this.sSQL = sqlFormat.getsSQL();
        this.colNames = sqlFormat.getColNames();
        this.conn = conn;
        this.tableName = DBUtils.getTableName(this.sSQL);
    }

    public SpringSQLWriter(JdbcTemplate conn, String sSQL, String[] colNames) {
        this.sSQL = sSQL;
        this.colNames = colNames;
        this.conn = conn;
        this.tableName = DBUtils.getTableName(this.sSQL);
    }

    @Override
    protected int update(final Map row) throws Exception {
        //printSQL(row);
        return conn.update(sSQL, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {
            	System.out.println(colNames);
                for (int j = 1; j <= colNames.length; j++) {
                    Object value = row.get(colNames[j - 1]);
                    if (value == null) {
                        if (!"".equals(tableName)) {
                            int sqlType = DBUtils.getColumnType(conn.getDataSource(), tableName, colNames[j - 1]);
                            if (sqlType != -1) {
                                ps.setNull(j, sqlType);
                                continue;
                            }
                        }
                    }
                    ps.setObject(j, value);
                }
            }
        });
    }

    @Override
    protected int[] batchUpdate(final Map[] rows) throws Exception {
        return conn.batchUpdate(sSQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map row = (Map) rows[i];
                for (int j = 1; j <= colNames.length; j++) {
                    Object value = row.get(colNames[j - 1]);
                    if (value == null) {
                        if (!"".equals(tableName)) {
                            int sqlType = DBUtils.getColumnType(conn.getDataSource(), tableName, colNames[j - 1]);
                            if (sqlType != -1) {
                                ps.setNull(j, sqlType);
                                continue;
                            }
                        }
                    }
                    ps.setObject(j, value);
                }
                //printSQL(row);
            }

            @Override
            public int getBatchSize() {
                return rows.length;
            }
        });
    }

    private void printSQL(Map row) {
        String separator = "?";
        StringBuilder newSQL = new StringBuilder(sSQL);
        for (int i = 1; i <= colNames.length; i++) {
            Object value = row.get(colNames[i - 1]);
            int j = newSQL.indexOf(separator);
            newSQL.delete(j, j + separator.length());
            newSQL.insert(j, ToString(value));
        }
        System.out.println(newSQL.toString());
    }

    private static String ToString(Object value) {
        if (value == null) {
            return "";
        }
        StringBuilder newValue = new StringBuilder();
        String type = value.getClass().getName();
        if ("java.lang.Integer".equals(type)) {
            newValue.append(String.valueOf(value));
        } else if ("java.lang.String".equals(type)) {
            newValue.append("'");
            newValue.append(String.valueOf(value));
            newValue.append("'");
            return newValue.toString();
        } else if ("java.lang.Double".equals(type)) {
            newValue.append(String.valueOf(value));
        } else if ("java.lang.Float".equals(type)) {
            newValue.append(String.valueOf(value));
        } else if ("java.sql.Date".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            newValue.append("TO_DATE(").append("'").append(sdf.format(value)).append("'").append(",'YYYY-MM-DD')");
        } else if ("java.sql.Timestamp".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newValue.append("TO_DATE(").append("'").append(sdf.format(value)).append("'").append(",'YYYY-MM-DD HH24:MI:SS')");
        } else if ("java.math.BigDecimal".equals(type)) {
            newValue.append(value.toString());
        } else {
            newValue.append(String.valueOf(value));
        }
        return newValue.toString();
    }
}
