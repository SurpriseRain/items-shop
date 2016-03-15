package com.jlsoft.framework.pi.convertor.list;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;

public class ListToJsonArraryConvertor extends AbstractConvertor {

    @Override
    protected Object convert(Object record) throws Exception {
        List rows;
        if (record instanceof Map) {
            rows = new ArrayList();
            rows.add(record);
        } else if (record instanceof List) {
            rows = (List) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return JSONArray.fromObject(rows).toString();
    }
}
