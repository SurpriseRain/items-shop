package com.jlsoft.framework.pi.convertor.map;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.util.Map;
import net.sf.json.JSONObject;

public class MapToJsonConvertor extends AbstractConvertor {

    @Override
    protected Object convert(Object record) throws Exception {
        Map row;
        if (record instanceof Map) {
            row = (Map) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return JSONObject.fromObject(row).toString();
    }
}
