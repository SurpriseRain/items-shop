package com.jlsoft.framework.pi.connector.jdbc.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for convertion of an SQL ResultSet into Object record(s). The
 * resultant record types is ultimately determined by the implementation
 * classes.
 *
 * @author higginse
 * @since Post 3.2.1
 */
public interface IResultSetConverter {

    /**
     * Flag to indicate converter shoudl convert a single row at a time.
     */
    public static final int CONVERT_ONE = 1;
    /**
     * This is the default batch size for converting a ResultSet.
     *
     */
    public static final int DEFAULT_BATCH_SIZE = CONVERT_ONE;
    /**
     * This indicates that all rows from a ResultSet should be converted.
     */
    public static final int CONVERT_ALL = 0;

    /**
     * Convert rows from ResultSet into corresponding record Objects.
     *
     * @param rs
     * @param maxBatchSize sets upper limit on number of records which may be
     * converted from the ResultSet.
     * @return Object[] with at most maxBatchSize records, unless maxBatchSize
     * is CONVERT_ALL where all records will be converted.
     * @throws SQLException
     */
    public Object[] convert(ResultSet rs, int maxBatchSize) throws SQLException;
}
