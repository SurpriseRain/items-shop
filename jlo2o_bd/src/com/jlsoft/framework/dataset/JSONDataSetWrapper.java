package com.jlsoft.framework.dataset;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONDataSetWrapper implements IDataSetWrapper {

    private List rows;
    private final String Type = "JSON";

    public JSONDataSetWrapper(List rows) {
        this.rows = rows;
    }

    public String convert() {
        return toJson();
    }

    private String toJson() {
        return JSONArray.fromObject(rows).toString();
    }
    
    public String getType() {
        return Type;
    }    
}
