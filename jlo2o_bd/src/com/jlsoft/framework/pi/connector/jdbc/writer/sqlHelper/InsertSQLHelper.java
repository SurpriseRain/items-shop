package com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper;

import com.jlsoft.framework.pi.util.DBUtils;
import java.sql.Connection;
import java.util.List;

public class InsertSQLHelper extends AbstractSQLHelper {

    public InsertSQLHelper(Connection conn, String tableName, Object[] inputColumns) throws Exception {
        List tableColumnNames = DBUtils.getTableColumnNames(tableName, conn);
        DBUtils.intersection(tableColumnNames, inputColumns); //交集
        outputColumns = DBUtils.getOutputColumns(tableColumnNames);
        sql = DBUtils.generateInsertStatement(tableName, outputColumns);
    }
}
