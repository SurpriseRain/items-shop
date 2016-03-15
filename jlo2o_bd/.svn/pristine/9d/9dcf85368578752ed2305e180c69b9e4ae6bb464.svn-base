package com.jlsoft.framework.pi.connector.jdbc.writer;

import com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper.*;
import java.sql.*;
import java.util.Map;

public class JdbcSQLWriter extends AbstractSQLWriter {

    private Connection conn;
    protected String sql;
    private String[] outputColumns;
    private OperatType operatType;
    private String tableName = null;
    private String source = null;

    public JdbcSQLWriter(Connection conn, String source) {
        if (source == null) {
            try {
                throw new Exception("Null source not permitted.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.conn = conn;
        this.source = source;
    }

    public JdbcSQLWriter(Connection conn, String tableName, OperatType operatType) {
        if (tableName == null) {
            try {
                throw new Exception("Null tableName not permitted.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.conn = conn;
        this.tableName = tableName;
        this.operatType = operatType;
    }

    @Override
    protected int[] doWrite(Map[] data) throws Exception {
        analysis(data[0]);
        if (data.length == 1) {
            int[] result = new int[1];
            result[0] = update(data[0]);
            return result;
        } else {
            return batchUpdate(data);
        }
    }

    private int update(Map row) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            setValues(ps, row);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
    }

    private int[] batchUpdate(Map[] rows) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (Map row : rows) {
                setValues(ps, row);
                ps.addBatch();
            }
            return ps.executeBatch();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
    }

    private void setValues(PreparedStatement ps, Map row) throws SQLException {
        for (int i = 1; i <= outputColumns.length; i++) {
            Object value = row.get(outputColumns[i - 1]);
            ps.setObject(i, value);
        }
    }

    private ISQLHelper createSqlHelper(String tableName, OperatType operatType, Object[] inputColumns) throws Exception {
        ISQLHelper sqlHelper = null;
        if (operatType == OperatType.Insert) {
            sqlHelper = new InsertSQLHelper(conn, tableName, inputColumns);
        } else if (operatType == OperatType.Update) {
            sqlHelper = new UpdateSQLHelper(conn, tableName, inputColumns);
        } else if (operatType == OperatType.UpdateAndInsert) {
            sqlHelper = new InsertAndUpdateSQLHelper(conn, tableName, inputColumns);
        } else if (operatType == OperatType.Delete) {
            sqlHelper = new DeleteSQLHelper(conn, tableName);
        }
        return sqlHelper;
    }

    private void analysis(Map row) throws Exception {
        ISQLHelper sqlHelper;
        if (tableName != null) {
            Object[] inputColumns = (Object[]) row.keySet().toArray();
            sqlHelper = createSqlHelper(tableName, operatType, inputColumns);
        } else {
            sqlHelper = new NormalSQLHelper(source);
        }
        outputColumns = sqlHelper.getOutputColumns();
        sql = sqlHelper.getSql();
System.out.println(sql);        
    }
}
