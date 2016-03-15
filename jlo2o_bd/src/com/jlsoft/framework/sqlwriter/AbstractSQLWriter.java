package com.jlsoft.framework.sqlwriter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSQLWriter implements ISQLWriter {

    protected String sSQL;
    protected String[] colNames;

    //单行执行
    @Override
    public int write(Object row) throws Exception {
        try {
            if (row == null) {
                if (colNames.length == 0) {
                    if (row == null) {
                        row = new HashMap();
                    }
                } else {
                    throw new RuntimeException("Null data provided");
                }
            }
            if (!(row instanceof Map)) {
                throw new RuntimeException("Batch element Expected Map. Got - " + row.getClass());
            }
            return update((Map) row);
        } catch (Exception e) {
            throw e;
        }
    }

    //批量执行
    @Override
    public int[] writeBatch(Object[] rows) throws Exception {
        try {
            if (rows == null) {
                throw new RuntimeException("Null data provided");
            }
            Map[] maps = new Map[rows.length];
            for (int i = 0; i < maps.length; i++) {
                Object row = rows[i];
                if (!(row instanceof Map)) {
                    throw new RuntimeException("Batch element [" + i + "]. Expected Map. Got - " + rows.getClass());
                }
                if (row == null) {
                    throw new SQLException("Cannot create Statement from null data");
                }
                maps[i] = (Map) row;
            }
            return batchUpdate(maps);
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract int update(Map row) throws Exception;

    protected abstract int[] batchUpdate(Map[] rows) throws Exception;
}
