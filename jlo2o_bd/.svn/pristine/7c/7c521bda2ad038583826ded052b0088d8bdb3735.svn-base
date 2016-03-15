package com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper;

import com.jlsoft.framework.pi.util.DBUtils;
import java.sql.Connection;
import java.util.List;

public class DeleteSQLHelper extends AbstractSQLHelper {

    public DeleteSQLHelper(Connection conn, String tableName) throws Exception {
        List primaryKeys = DBUtils.getTablePrimaryKeys(tableName, conn);
        if (primaryKeys.isEmpty()) {
            throw new Exception("table " + tableName + " not primary key.");
        }
        outputColumns = DBUtils.getOutputColumns(primaryKeys);
        sql = DBUtils.generateDeleteStatement(tableName, outputColumns);
    }
}
