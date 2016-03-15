package com.jlsoft.framework.pi.util.orderedmap;

/**
 * Utility methods for Ordered Maps
 *
 * @author Eddy Higgins
 */
public class OrderedMapUtils {

  // private static Log log = LogFactory.getLog(OrderedMapUtils.class);

  private OrderedMapUtils() {
  } // No instantiation allowed.

  /**
   * This method simply converts a record into an Ordered Map, probably just by casting it. If it cannot, then a
   * RecordException will be thrown.
   *
   * @param record
   *          A candidate object which might be an ordered map
   * @return the object as an ordered Map (probably just cast).
   * @throws NullRecordException
   *           if the incoming record is <tt>null</tt>
   * @throws RecordFormatException
   *           if the incoming record is not an <code>IOrderedMap</code>
   */
  public static IOrderedMap extractOrderedMap(Object record) throws Exception {
    IOrderedMap result = null;
    if (record instanceof IOrderedMap) {
      result = (IOrderedMap) record;
    } else {
      if (record == null) {
        throw new Exception("Expected IOrderedMap. Null record not permitted.");
      } else {
        throw new Exception("Expected IOrderedMap . Got [" + record.getClass().getName() + "]");
      }
    }
    return result;
  }
}
