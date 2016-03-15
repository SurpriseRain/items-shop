package com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper;

import com.jlsoft.framework.pi.util.DBUtils;
import java.sql.Connection;
import java.util.List;

public class UpdateSQLHelper extends AbstractSQLHelper {

    public UpdateSQLHelper(Connection conn, String tableName, Object[] inputColumns) throws Exception {
        List tableColumnNames = DBUtils.getTableColumnNames(tableName, conn);
        List primaryKeys = DBUtils.getTablePrimaryKeys(tableName, conn);
        if (primaryKeys.isEmpty()) {
            throw new Exception("table " + tableName + " not primary key.");
        }
        DBUtils.intersection(tableColumnNames, inputColumns);  //交集
        DBUtils.eliminate(tableColumnNames, primaryKeys.toArray()); //去重
        tableColumnNames.addAll(primaryKeys);
        outputColumns = DBUtils.getOutputColumns(tableColumnNames);
        sql = DBUtils.generateUpdateStatement(tableName, outputColumns, primaryKeys);
    }
}
