package com.jlsoft.framework.pi.transport;

import com.jlsoft.framework.pi.api.IConnector;
import com.jlsoft.framework.pi.connector.jdbc.JdbcWriterConnector;
import com.jlsoft.framework.pi.connector.jdbc.writer.OperatType;


public class JdbcWriterTransportor extends SyncTransportor {

    public JdbcWriterTransportor(String jndiName, String source) {
        IConnector writerConnector = new JdbcWriterConnector(jndiName, source);
        setConnector(writerConnector);
    }

    public JdbcWriterTransportor(String jndiName, String tableName, OperatType operatType) {
        IConnector writerConnector = new JdbcWriterConnector(jndiName, tableName, operatType);
        setConnector(writerConnector);
    }
}
