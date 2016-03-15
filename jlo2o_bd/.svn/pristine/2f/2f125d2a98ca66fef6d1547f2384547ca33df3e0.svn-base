package com.jlsoft.framework.pi.connector.jdbc.writer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AbstractSQLWriter implements ISQLWriter {

    public int[] write(Object rows) throws Exception {
        try {
            Map[] data;
            if (rows instanceof Map) {
                data = new Map[1];
                data[0] = (Map) rows;
            } else if (rows instanceof List) {
                data = new Map[((List) rows).size()];
                for (int i = 0; i < ((List) rows).size(); i++) {
                    Object row = ((List) rows).get(i);
                    if (!(row instanceof Map)) {
                        throw new RuntimeException("Batch element [" + i + "]. Expected Map. Got - " + row.getClass());
                    }
                    if (row == null) {
                        throw new SQLException("Cannot create Statement from null data");
                    }
                    data[i] = (Map) row;
                }
            } else if (rows instanceof Map[]) {
                data = (Map[]) rows;
            } else {
                throw new RuntimeException("record element Expected. Got - " + rows.getClass());
            }
            return doWrite(data);
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract int[] doWrite(Map[] data) throws Exception;
}
