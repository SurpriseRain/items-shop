package com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper;

import java.sql.Connection;
import org.apache.commons.lang.ArrayUtils;

public class InsertAndUpdateSQLHelper extends AbstractSQLHelper {

    public InsertAndUpdateSQLHelper(Connection conn, String tableName, Object[] inputColumns) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();

        ISQLHelper sqlHelper = new UpdateSQLHelper(conn, tableName, inputColumns);
        String[] updateOutputColumns = sqlHelper.getOutputColumns();
        sqlBuilder.append("BEGIN ");
        sqlBuilder.append(sqlHelper.getSql()).append(";");
        sqlBuilder.append(" IF sql%rowcount = 0 THEN ");

        sqlHelper = new InsertSQLHelper(conn, tableName, inputColumns);
        sqlBuilder.append(sqlHelper.getSql()).append(";");
        String[] insertOutputColumns = sqlHelper.getOutputColumns();

        sqlBuilder.append(" END IF;");
        sqlBuilder.append(" END;");
        outputColumns = (String[]) ArrayUtils.addAll(updateOutputColumns, insertOutputColumns);
        sql = sqlBuilder.toString();
    }
}
