package com.jlsoft.framework.dataset;

import java.util.Map;

public interface IDataSet {

    String getField(String felname, int i) throws Exception;

    String getField(String felname, int i, int j) throws Exception;

    String getField(String felname, int i, int j, int k) throws Exception;

    Map[] getRows(RowSetter rowSetter) throws Exception;

    Map[] getRows(RowSetter rowSetter, int i) throws Exception;

    Map[] getRows(RowSetter rowSetter, int i, int j) throws Exception;
    
    Map[] getRows(RowSetter rowSetter, int[] indexes) throws Exception;

    int getTableRows(String tblname)  throws Exception ;

    int getTableRows(String tblname, int i)  throws Exception ;

    int getTableRows(String tblname, int i, int j)  throws Exception ;
    
}
