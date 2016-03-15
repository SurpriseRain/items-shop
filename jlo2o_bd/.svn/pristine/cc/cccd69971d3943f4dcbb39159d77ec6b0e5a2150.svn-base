package com.jlsoft.framework.pi.connector.jdbc.reader;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abstract base class which converts a SQL ResultSet into a format for use
 * within an adaptor. Subclasses defined the output type.
 *
 * @author higginse
 * @author browe (updated based on Brian's patch suggestions)
 */
public abstract class AbstractResultSetConverter implements IResultSetConverter {

    protected boolean useColumnLabels = false; //False for backward compatibility with 3.3

    /**
     * If true, use column labels if available in returned ResultSets. <br> The
     * default is
     * <code>false</code> for backwards compatibility with openadaptor 3.3
     *
     * @param useColumnLabels boolean to indicate labels may be used.
     */
    public void setUseColumnLabels(boolean useColumnLabels) {
        this.useColumnLabels = useColumnLabels;
    }

    /**
     * Flag to indicate if column aliases may be used in returned ResultSets.
     *
     * @return boolean containing true if aliases are to be used.
     */
    public boolean getUseColumnLabels() {
        return useColumnLabels;
    }

    /**
     * Convert the next row in the result set into an Object.
     *
     * @param rs Contains the current ResultSet
     * @return Object representing the next row from the ResultSet, or null if
     * no rows remain. Actual type is determined by implementing class
     * @throws SQLException
     */
    public Object convertNext(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        if (rs.next()) {
            //JDBCUtil.logCurrentResultSetRow(log, "converting ResultSet", rs);
            return convertNext(rs, rsmd);
        } else {
            return null;
        }
    }

    /**
     * Convert all the rows in a ResultSet into an Object[].
     *
     * @param rs Contains the ResultSet to be converted
     * @return Object[] with entries for each row returned.
     * @throws SQLException
     */
    public Object[] convertAll(ResultSet rs) throws SQLException {
        ArrayList rows = new ArrayList();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            //JDBCUtil.logCurrentResultSetRow(log, "converting ResultSet", rs);
            rows.add(convertNext(rs, rsmd));
        }
        return rows.toArray(new Object[rows.size()]);
    }

    /**
     * If
     * <code>maxBatchSize</code> is a positive number convert not more than the
     * <code>maxBatchSize</code> of rows in the result set. If
     * <code>maxBatchSize</code> equal to zero or less - convert all rows in the
     * result set.
     *
     * @param rs Contains the current ResultSet
     * @return Object[] array with entries for each row returned
     */
    @Override
    public Object[] convert(ResultSet rs, int maxBatchSize) throws SQLException {
        ArrayList rows = new ArrayList();
        ResultSetMetaData rsmd = rs.getMetaData();
        int batchSize = 0;
        while ((maxBatchSize <= 0 || batchSize < maxBatchSize) && rs.next()) {
            //JDBCUtil.logCurrentResultSetRow(log, "converting ResultSet", rs);
            rows.add(convertNext(rs, rsmd));
            batchSize++;
        }
        return rows.toArray(new Object[rows.size()]);
    }

    /**
     * Convert the next row in a ResultSet (given its metadata).
     *
     * @param rs Contains the ResultSet being converted
     * @param rsmd ResultSetMetaData for the ResultSet. Note: Behaviour is
     * undefined if the supplied ResultSetMetaData doesn't correspond to the
     * supplied ResultSet.
     * @return Object representing the next row from the ResultSet, or null if
     * no rows remain. Actual type is determined by implementing class
     * @throws SQLException
     */
    protected abstract Object convertNext(ResultSet rs, ResultSetMetaData rsmd) throws SQLException;

    protected String getColumnNameOrAlias(ResultSetMetaData rsmd, int colIndex) throws SQLException {
        return useColumnLabels ? rsmd.getColumnLabel(colIndex) : rsmd.getColumnName(colIndex);
    }
}
