package com.jlsoft.framework.dataset;

import java.util.Map;

public class DataSet implements IDataSet {

    private IDataSet ds = null;

    public DataSet(String data) throws Exception {
        try {
            if (data.startsWith("<")) {
                ds = new XmlDataSet(data);
            } else {
                ds = new JSONDataSet(data);
            }
        } catch (Exception e) {
            throw new Exception("请输入有效的DataSet格式文件.");
        }

//        try {
//            ds = new XmlDataSet(data);
//        } catch (Exception e) {
//            try {
//                ds = new JSONDataSet(data);
//            } catch (Exception ex) {
//                throw new Exception("请输入有效的DataSet格式文件.");
//            }
//        }
    }

    public String getField(String felname, int i) throws Exception {
        return ds.getField(felname, i);
    }

    public String getField(String felname, int i, int j) throws Exception {
        return ds.getField(felname, i, j);
    }

    public String getField(String felname, int i, int j, int k) throws Exception {
        return ds.getField(felname, i, j, k);
    }

    public Map[] getRows(RowSetter rowSetter) throws Exception {
        return ds.getRows(rowSetter);
    }

    public Map[] getRows(RowSetter rowSetter, int i) throws Exception {
        return ds.getRows(rowSetter, i);
    }

    public Map[] getRows(RowSetter rowSetter, int i, int j) throws Exception {
        return ds.getRows(rowSetter, i, j);
    }
    
    public Map[] getRows(RowSetter rowSetter, int[] indexes) throws Exception {
        return ds.getRows(rowSetter, indexes);
    }    

    public int getTableRows(String tblname) throws Exception {
        return ds.getTableRows(tblname);
    }

    public int getTableRows(String tblname, int i) throws Exception {
        return ds.getTableRows(tblname, i);
    }

    public int getTableRows(String tblname, int i, int j) throws Exception {
        return ds.getTableRows(tblname, i, j);
    }
}
