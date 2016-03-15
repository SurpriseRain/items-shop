
package com.jlsoft.pi.jhzy.convertor;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import com.jlsoft.utils.JLTools;

public class StringToDateConvertor extends AbstractConvertor {

    public StringToDateConvertor() {
    }

    @Override
    protected Object convert(Object record) throws Exception {
        String s;
        if (record instanceof String) {
            s = (String) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return JLTools.parseDate(s);
    }
}