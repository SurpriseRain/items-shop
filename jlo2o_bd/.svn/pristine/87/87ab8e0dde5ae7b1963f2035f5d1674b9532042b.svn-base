package com.jlsoft.framework.pi.convertor.list;

import com.jlsoft.framework.pi.convertor.map.MapMappingConvertor;
import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListMappingConvertor extends AbstractConvertor {

    private String configKey = null;

    public ListMappingConvertor() {
    }

    public ListMappingConvertor(String configKey) {
        this.configKey = configKey;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        List rows;

        if (configKey == null) {
            throw new Exception("Null configKey not permitted.");
        }
        if (record instanceof List) {
            rows = (List) record;
        } else if (record instanceof Map) {
            rows = new ArrayList();
            rows.add(record);
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }

        List result = new ArrayList();
        MapMappingConvertor mapConvertor = new MapMappingConvertor(configKey);
        for (Object data : rows) {
            Map row = (Map) mapConvertor.process(data);
            result.add(row);
        }
        return result;
    }
}
