package com.jlsoft.framework.sqlwriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class JdbcSQLWriter extends AbstractSQLWriter {

    private Connection conn;

    public JdbcSQLWriter(Connection conn, String sSQL) throws Exception {
        SQLConvertor sqlFormat = new SQLConvertor(sSQL);
        this.sSQL = sqlFormat.getsSQL();
        this.colNames = sqlFormat.getColNames();
        this.conn = conn;
    }

    @Override
    protected int update(Map row) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sSQL);
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

    @Override
    protected int[] batchUpdate(Map[] rows) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sSQL);
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
        System.out.println(row);
        for (int j = 1; j <= colNames.length; j++) {
            Object value = row.get(colNames[j - 1]);
            ps.setObject(j, value);
        }
    }
}
