package com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper;

public abstract class AbstractSQLHelper implements ISQLHelper {

    protected String[] outputColumns;
    protected String sql;

    @Override
    public String getSql() {
        return sql;
    }

    @Override
    public String[] getOutputColumns() {
        return outputColumns;
    }
}
