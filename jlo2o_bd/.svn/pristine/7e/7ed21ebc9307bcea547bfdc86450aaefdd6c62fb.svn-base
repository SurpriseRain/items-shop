package com.jlsoft.framework.dataset;

import com.jlsoft.utils.JLTools;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONDataSet implements IDataSet {

    private List cdsDate = null; // 存放所有传递数据

    // 将传入String数据转换成list
    public JSONDataSet(String JSONData) throws Exception {
        JSONArray jsonList = null;
        if (JSONData.startsWith("[")) {
            jsonList = JSONArray.fromObject(JSONData);
        } else { //JSONData.startsWith("{")
            JSONObject jsonObject = JSONObject.fromObject(JSONData);
            jsonList = JSONArray.fromObject(jsonObject);
        }
        this.cdsDate = jsonList;
    }

    private JSONDataSet() {
    }

    //获取一级表字段值
    public String getField(String felname, int i) throws Exception {
        String[] nodes = felname.split("\\.");
        int indexes[] = {i};
        return getField(cdsDate, nodes, indexes);
    }

    //获取二级表字段值
    public String getField(String felname, int i, int j) throws Exception {
        String[] nodes = felname.split("\\.");
        int indexes[] = {i, j};
        return getField(cdsDate, nodes, indexes);
    }

    //获取三级表字段值
    public String getField(String felname, int i, int j, int k) throws Exception {
        String[] nodes = felname.split("\\.");
        int indexes[] = {i, j, k};
        return getField(cdsDate, nodes, indexes);
    }

    private Map clone(Map row) {
        Map values = new HashMap();
        for (Object key : row.keySet()) {
            values.put(key, row.get(key));
        }
        return values;
    }

    //取第一级数据
    public Map[] getRows(RowSetter rowSetter) throws Exception {
        Map[] rows = null;
        if (cdsDate != null && cdsDate.size() > 0) {
            rows = new Map[cdsDate.size()];
            for (int i = 0; i < cdsDate.size(); i++) {
                Map row = clone((Map) cdsDate.get(i));
                setRow(row);
                rowSetter.setValue(row);
                rows[i] = row;
            }
        }
        return rows;
    }

    //取第二级数据
    public Map[] getRows(RowSetter rowSetter, int i) throws Exception {
        int indexes[] = {i};
        return getRows(rowSetter, indexes);
    }

    //取第三级数据
    public Map[] getRows(RowSetter rowSetter, int i, int j) throws Exception {
        int indexes[] = {i, j};
        return getRows(rowSetter, indexes);
    }

    private void setRow(Map row) throws Exception {
        for (Object key : row.keySet()) {
            Object value = row.get(key);
            if (!(value instanceof Map) || !(value instanceof List)) {
                switch (checkType(key, value)) {
                    case 0:
                        row.put(key, ((String) value).toString());
                        break;
                    case 1:
                        if (((String) value).length() == 0) {
                            row.put(key, String.valueOf(""));
                        } else {
                            if (((String) value).length() < 11) {
                                row.put(key, JLTools.parseDate(((String) value).toString()));
                            } else {
                                row.put(key, JLTools.parseTimestamp(((String) value).toString()));
                            }
                        }
                        break;
                    case 2:
                        if (String.valueOf(value).length() == 0) {
                            row.put(key, 0);
                        } else {
                            row.put(key, Long.parseLong(String.valueOf(value)));
                        }
                        break;
                    case 3:
                        if (String.valueOf(value).length() == 0) {
                            row.put(key, 0);
                        } else {
                            row.put(key, Double.parseDouble(String.valueOf(value)));
                        }
                        break;
                    case 4:
                        String sValue = ((String) value).substring("dateTime:".length());
                        if (sValue.length() == 0) {
                            row.put(key, String.valueOf(""));
                        } else {
                            if (sValue.length() < 11) {
                                row.put(key, JLTools.parseDate(sValue.toString()));
                            } else {
                                row.put(key, JLTools.parseTimestamp(sValue.toString()));
                            }
                        }
                        break;
                    case 5:
                        row.put(key, String.valueOf(""));
                        break;
                }
            }
        }
    }

    // 获取字段类型：0字串；1日期；2正型；3小数
    private int checkType(Object key, Object value) {
        int flag = -1;
        String fieldType = value.getClass().toString();
        // 日期类型
        if (((String) key).endsWith("RQ")) {
            flag = 1;
        } else if (fieldType.equals("class java.lang.String")) {
            if (((String) value).startsWith("dateTime:")) {
                flag = 4;
            } else {
                flag = 0;
            }
        } else if (fieldType.equals("class java.lang.Integer")) {
            flag = 2;
        } else if (fieldType.equals("class java.lang.Double")) {
            flag = 3;
        } else if (fieldType.equals("class net.sf.json.JSONNull")) {
            flag = 5;
        }
        return flag;
    }

    //获取第一级明细表有多少条数据
    public int getTableRows(String tblname) throws Exception {
        return cdsDate.size();
    }

    //获取第二级明细表有多少条数据
    public int getTableRows(String tblname, int i) throws Exception {
        String[] nodes = tblname.split("\\.");
        int indexes[] = {i};
        return getList(cdsDate, nodes, indexes).size();
    }

    //获取第三级明细表有多少条数据
    public int getTableRows(String tblname, int i, int j) throws Exception {
        String[] nodes = tblname.split("\\.");
        int indexes[] = {i, j};
        return getList(cdsDate, nodes, indexes).size();
    }

    private String getField(List rows, String[] keys, int[] indexes) throws Exception {
        if (keys.length != (indexes.length)) {
            throw new Exception("节点名称输入错误.");
        }
        Object field = getMap(rows, keys, indexes).get(keys[keys.length - 1]);
        return (field == null) ? "" : field.toString();
    }

    private Map getRow(List rows, String[] keys, int[] indexes) throws Exception {
        if (keys.length != (indexes.length - 1)) {
            throw new Exception("节点名称输入错误.");
        }
        return getMap(rows, keys, indexes);
    }

    public Map[] getRows(RowSetter rowSetter, int[] indexes) throws Exception {
        String tblname = rowSetter.getNodeName();
        String[] nodes = (JLTools.isEmpty(tblname) ? "" : tblname).split("\\.");
        Object[] data = getList(cdsDate, nodes, indexes).toArray();
        Map[] rows = new Map[data.length];
        for (int i = 0; i < data.length; i++) {
            Map row = clone((Map) data[i]);
            setRow(row);
            rowSetter.setValue(row);
            rows[i] = row;
        }
        return rows;
    }

    private Map getMap(List rows, String[] keys, int[] indexes) throws Exception {
        Object o = rows;
        for (int i = 0; i < indexes.length; i++) {
            if (i < indexes.length - 1) {
                if (o instanceof List) {
                    o = ((List) o).get(indexes[i]);
                }
                if (o instanceof Map) {
                    o = ((Map) o).get((String) keys[i]);
                }
            } else if (i == indexes.length - 1) {
                if (o instanceof List) {
                    o = ((List) o).get(indexes[i]);
                }
            }
        }
        
        if (o == null) {
            o = new HashMap();
        }
        
        if (!(o instanceof Map)) {
            throw new Exception("输入数据格式错误.");
        }
        return (Map) o;
    }

    private List getList(List rows, String[] keys, int[] indexes) throws Exception {
        if (keys.length != (indexes.length)) {
            throw new Exception("节点名称输入错误.");
        }
        Object o = rows;
        for (int i = 0; i < indexes.length; i++) {
            if (o instanceof List) {
                o = ((List) o).get(indexes[i]);
            }
            if (o instanceof Map) {
                o = ((Map) o).get((String) keys[i]);
            }
        }
        
        if (o == null) {
            o = new ArrayList();
        }
        
        if (!(o instanceof List)) {
            throw new Exception("输入数据格式错误.");
        }
        return (List) o;
    }
}
