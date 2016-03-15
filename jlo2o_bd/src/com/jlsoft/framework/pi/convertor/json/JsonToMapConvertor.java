package com.jlsoft.framework.pi.convertor.json;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonToMapConvertor extends AbstractConvertor {

    public JsonToMapConvertor() {
    }

    @Override
    protected Object convert(Object record) throws Exception {
        JSONObject jo;
        if (record instanceof String) {
            jo = JSONObject.fromObject(record);
        } else if (record instanceof JSONObject) {
            jo = (JSONObject) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return JsonObjectToMap(jo);
    }

    private Map<String, Object> JsonObjectToMap(JSONObject jo) throws Exception {
        Map<String, Object> row = new HashMap();
        //最外层解析
        for (Object key : jo.keySet()) {
            Object value = jo.get(key);
            //如果内层还是数组，继续解析
            if (value instanceof JSONArray) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) new JsonToListConvertor().process(value);
                row.put(key.toString(), list);
            } else {
                row.put(key.toString(), value);
            }
        }
        return row;
    }
}
