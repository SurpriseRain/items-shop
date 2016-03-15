package com.jlsoft.framework.pi.connector.jdbc;

import com.jlsoft.framework.pi.api.ITransaction;
import com.jlsoft.framework.pi.connector.jdbc.writer.ISQLWriter;
import com.jlsoft.framework.pi.connector.jdbc.writer.JdbcSQLWriter;
import com.jlsoft.framework.pi.connector.jdbc.writer.OperatType;
import com.jlsoft.framework.pi.transaction.jdbc.JdbcTransaction;

public class JdbcWriterConnector extends AbstractJdbcConnector {

    private ISQLWriter sqlWriter;
    private ITransaction transaction = null;
    private boolean isTransaction = true;
    private String tableName = null;
    private OperatType operatType;
    private String source;

    public JdbcWriterConnector(String jndiName) {
        this.jndiName = jndiName;
    }

    public JdbcWriterConnector(String jndiName, String source) {
        this(jndiName);
        this.source = source;
    }

    public JdbcWriterConnector(String jndiName, String tableName, OperatType operatType) {
        this(jndiName);
        this.tableName = tableName;
        this.operatType = operatType;
    }

    public void setSqlWriter(ISQLWriter sqlWriter) {
        this.sqlWriter = sqlWriter;
    }

    @Override
    protected Object doProcess(Object data) throws Exception {
        if (sqlWriter == null) {
            if (tableName != null) {
                sqlWriter = new JdbcSQLWriter(conn, tableName, operatType);
            } else {
                sqlWriter = new JdbcSQLWriter(conn, source);
            }
            //throw new Exception("Null sqlWriter not permitted.");
        }

        Object result;
        try {
            begin();
            result = sqlWriter.write(data);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
        return result;
    }

    private void begin() throws Exception {
        if (isTransaction) {
            transaction = new JdbcTransaction(conn);
        }
        if (transaction != null) {
            transaction.begin();
        }
    }

    private void commit() throws Exception {
        if (transaction != null) {
            transaction.commit();
        }
    }

    private void rollback() throws Exception {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
