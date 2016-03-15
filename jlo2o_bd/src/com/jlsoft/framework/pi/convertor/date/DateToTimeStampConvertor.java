package com.jlsoft.framework.pi.convertor.date;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.util.Date;

/*
 * 普通时间 → Unix时间戳(Unix timestamp)
 */
public class DateToTimeStampConvertor extends AbstractConvertor {

    @Override
    protected Object convert(Object record) throws Exception {
        Date date;
        if (record instanceof Date) {
            date = (Date) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return date.getTime() / 1000;
    }
}
