package com.jlsoft.framework.pi.transaction.jdbc;

import com.jlsoft.framework.pi.api.ITransaction;
import java.sql.Connection;

public class JdbcTransaction implements ITransaction {

    private Connection conn;

    public JdbcTransaction(Connection conn) {
        this.conn = conn;
    }

    public void begin() throws Exception {
        conn.setAutoCommit(false);
    }

    public void commit() throws Exception {
        conn.commit();
    }

    public void rollback() throws Exception {
        conn.rollback();
    }
}
