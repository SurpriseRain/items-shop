package com.jlsoft.framework.pi.connector.jdbc.reader;

import com.jlsoft.framework.pi.util.orderedmap.IOrderedMap;
import com.jlsoft.framework.pi.util.orderedmap.OrderedHashMap;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Convert ResultSets into OrderedMaps.
 *
 * @author higginse
 * @author perryj
 */
public class ResultSetToOrderedMapConverter extends AbstractResultSetConverter {

    /**
     * This convert the current row of a ResultSet into an IOrderedMap. Note
     * that the supplied ResultSetMetaData must correspond to the supplied
     * ResultSet, or the behaviour is undefined.
     */
    @Override
    protected Object convertNext(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        int columnCount = rsmd.getColumnCount();
        IOrderedMap map = new OrderedHashMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            //This could be much more efficient (less methods calls). Candidate for improvement.
            map.put(getColumnNameOrAlias(rsmd, i), rs.getObject(i));
        }
        return map;
    }
}
