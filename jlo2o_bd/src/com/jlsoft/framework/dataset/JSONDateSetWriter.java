package com.jlsoft.framework.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONDateSetWriter implements IDataWriter {

    protected List rows = null; // 存放所有传递数据

    public JSONDateSetWriter(Object data) {
        if (data instanceof String) {
            String JSONData = (String) data;
            JSONArray jsonList = null;
            if (JSONData.startsWith("[")) {
                jsonList = JSONArray.fromObject(JSONData);
            } else { //JSONData.startsWith("{")
                JSONObject jsonObject = JSONObject.fromObject(JSONData);
                jsonList = JSONArray.fromObject(jsonObject);
            }
            this.rows = jsonList;
        } else if (data instanceof List) {
            this.rows = (List) data;
        } else if (data instanceof Map) {
            this.rows = new ArrayList();
            this.rows.add(data);
        } else {
            throw new RuntimeException("record element Expected. Got - " + data.getClass());
        }
    }

    public List getData() {
        return rows;
    }

    public void setData(List rows) {
        this.rows = rows;
    }

    //设置第一级数据
    public void setRow(final Map otherValue, int i) throws Exception {
        String[] nodes = {};
        int indexes[] = {i};
        setRow(rows, otherValue, nodes, indexes);
    }

    //设置第二级数据
    public void setRow(final Map otherValue, String keys, int i, int j) throws Exception {
        String[] nodes = keys.split("\\.");
        int indexes[] = {i, j};
        setRow(rows, otherValue, nodes, indexes);
    }

    //设置第三级数据
    public void setRow(final Map otherValue, String keys, int i, int j, int k) throws Exception {
        String[] nodes = keys.split("\\.");
        int indexes[] = {i, j, k};
        setRow(rows, otherValue, nodes, indexes);
    }

    public void setRow(List rows, final Map otherValue, String[] keys, int[] indexes) throws Exception {
        Map row = getRow(rows, keys, indexes);
        if (otherValue != null) {
            for (Object key : otherValue.keySet()) {
                row.put(key, otherValue.get(key));
            }
        }
    }

    public Map[] getRows(List data, String[] keys, int[] indexes) throws Exception {
        Object[] a = getList(data, keys, indexes).toArray();
        Map[] rows = new Map[a.length];
        for (int j = 0; j < a.length; j++) {
            Map row = (Map) a[j];
            rows[j] = row;
        }
        return rows;
    }

    public Map getRow(List rows, String[] keys, int[] indexes) throws Exception {
        if (keys.length != (indexes.length - 1)) {
            throw new Exception("节点名称输入错误.");
        }
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

    public List getList(List rows, String[] keys, int[] indexes) throws Exception {
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
