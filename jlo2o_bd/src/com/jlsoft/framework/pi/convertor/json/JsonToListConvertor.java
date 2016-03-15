package com.jlsoft.framework.pi.convertor.json;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import com.jlsoft.framework.pi.api.IDataProcessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonToListConvertor extends AbstractConvertor {

    private IDataProcessor convertor = new JsonToMapConvertor();

    public JsonToListConvertor() {
    }

    public JsonToListConvertor(IDataProcessor convertor) {
        this.convertor = convertor;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        JSONArray jsonArr;
        if (record instanceof String) {
            jsonArr = ToJsonArray(record);
        } else if (record instanceof JSONObject) {
            jsonArr = JSONArray.fromObject(record);
        } else if (record instanceof JSONArray) {
            jsonArr = (JSONArray) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return JsonArraryToList(jsonArr);
    }

    private List<Map<String, Object>> JsonArraryToList(JSONArray jsonArr) throws Exception {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> row;
        Iterator<JSONObject> it = jsonArr.iterator();
        while (it.hasNext()) {
            JSONObject jo = it.next();
            row = (Map<String, Object>) convertor.process(jo);
            rows.add(row);
        }
        return rows;
    }

    private JSONArray ToJsonArray(Object record) {
        String JSONData = (String) record;
        JSONArray jsonArr = null;
        if (JSONData.startsWith("[")) {
            jsonArr = JSONArray.fromObject(JSONData);
        } else { //JSONData.startsWith("{")
            JSONObject jsonObject = JSONObject.fromObject(JSONData);
            jsonArr = JSONArray.fromObject(jsonObject);
        }
        return jsonArr;
    }
}
