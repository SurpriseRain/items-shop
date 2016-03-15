package com.jlsoft.framework.pi.convertor.common;

import com.jlsoft.framework.pi.api.AbstractConvertor;

public class NumberToStringConvertor extends AbstractConvertor {

    public NumberToStringConvertor() {
    }

    @Override
    protected Object convert(Object record) throws Exception {
        Number value;
        if (record instanceof Float) {
            value = (Float) record;
        } else if (record instanceof Double) {
            value = (Double) record;
        } else if (record instanceof Integer) {
            value = (Integer) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return String.valueOf(value.intValue());
    }
}
