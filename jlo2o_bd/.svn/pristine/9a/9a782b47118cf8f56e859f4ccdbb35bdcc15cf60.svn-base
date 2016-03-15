package com.jlsoft.framework.pi.transport;

import com.jlsoft.framework.pi.connector.jdbc.JdbcReaderConnector;

public class JdbcReaderTransportor extends SyncTransportor {

    private JdbcReaderConnector readerConnector;

    public JdbcReaderTransportor(String jndiName) {
        readerConnector = new JdbcReaderConnector(jndiName);
        setConnector(readerConnector);
    }

    public void setSql(String sql) {
        readerConnector.setSql(sql);
    }
}
