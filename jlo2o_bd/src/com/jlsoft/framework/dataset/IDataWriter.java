package com.jlsoft.framework.dataset;

import java.util.List;
import java.util.Map;

public interface IDataWriter {

    void setRow(final Map otherValue, int i) throws Exception;

    void setRow(final Map otherValue, String keys, int i, int j) throws Exception;

    void setRow(final Map otherValue, String keys, int i, int j, int k) throws Exception;

    void setRow(List rows, final Map otherValue, String[] keys, int[] indexes) throws Exception;

    List getData();

    Map getRow(List rows, String[] keys, int[] indexes) throws Exception;

    Map[] getRows(List data, String[] keys, int[] indexes) throws Exception;
}
