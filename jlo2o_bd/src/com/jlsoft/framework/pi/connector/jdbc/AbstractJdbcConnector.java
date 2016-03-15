package com.jlsoft.framework.pi.connector.jdbc;

import com.jlsoft.framework.pi.api.IConnector;
import com.jlsoft.framework.pi.util.JndiConst;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public abstract class AbstractJdbcConnector implements IConnector {

    protected String jndiName = null;
    protected Connection conn;

    public void connect() throws Exception {
        try {
            if ((conn == null) && (jndiName != null)) {
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup(new JndiConst().getJndiName(jndiName));
                conn = ds.getConnection();
            }

//            String url = "jdbc:oracle:thin:@119.79.224.115:9010:WEB";
//            String user = "V9SCM";
//            String password = "v9user";
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disconnect() throws Exception {
        if (conn != null) {
            conn.close();
        }
        conn = null;
    }

    public Object process(Object data) throws Exception {
        return doProcess(data);
    }

    protected abstract Object doProcess(Object data) throws Exception;
}
