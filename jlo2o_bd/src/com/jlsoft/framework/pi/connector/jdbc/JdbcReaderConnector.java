package com.jlsoft.framework.pi.connector.jdbc;

import com.jlsoft.framework.pi.connector.jdbc.reader.AbstractResultSetConverter;
import com.jlsoft.framework.pi.connector.jdbc.reader.IResultSetConverter;
import com.jlsoft.framework.pi.connector.jdbc.reader.ResultSetToOrderedMapConverter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class JdbcReaderConnector extends AbstractJdbcConnector {

    private static AbstractResultSetConverter DEFAULT_CONVERTER = new ResultSetToOrderedMapConverter();
    private static final String DEFAULT_PARAMETER_PLACEHOLDER = "?";
    private IResultSetConverter resultSetConverter = DEFAULT_CONVERTER;
    protected String sql = null;
    protected int batchSize = Integer.MAX_VALUE;

    public JdbcReaderConnector(String jndiName) {
        this.jndiName = jndiName;
    }

    @Override
    protected Object doProcess(Object data) throws Exception {
        if (sql == null) {
            throw new Exception("Sql Statement is null!");
        }
        if (data != null) {
            if (!(data instanceof Map)) {
                throw new Exception("Record is not an Map. Record: " + data);
            }
        }
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            setValues(statement, (Map) data);
            rs = statement.executeQuery();
            return resultSetConverter.convert(rs, batchSize);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
        }
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    private void setValues(PreparedStatement ps, Map parameters) throws Exception {
        if (parameters == null || sql.indexOf(DEFAULT_PARAMETER_PLACEHOLDER) == -1) {
            return;
        }
        for (int j = 1; j <= parameters.size(); j++) {
            Object value = parameters.get(j);
            ps.setObject(j, value);
        }
    }
}
