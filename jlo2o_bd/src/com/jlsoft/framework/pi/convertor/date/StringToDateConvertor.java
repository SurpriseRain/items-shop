package com.jlsoft.framework.pi.convertor.date;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.text.SimpleDateFormat;

public class StringToDateConvertor extends AbstractConvertor {

    private String pattern = "yyyy-MM-dd HH:mm:ss";

    public StringToDateConvertor() {
    }

    public StringToDateConvertor(String pattern) {
        this.pattern = pattern;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        String s;
        if (record instanceof String) {
            s = (String) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return new SimpleDateFormat(pattern).parse(s);
    }
}
