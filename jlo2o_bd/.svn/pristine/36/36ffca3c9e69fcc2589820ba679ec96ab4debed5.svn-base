package com.jlsoft.framework.pi.convertor.date;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.util.Date;


/*
 * Unix时间戳(Unix timestamp) → 普通时间
 */
public class TimeStampToDateConvertor extends AbstractConvertor {

    public TimeStampToDateConvertor() {
    }

    @Override
    protected Object convert(Object record) throws Exception {
        Long timeStamp;
        if (record instanceof Long) {
            timeStamp = (Long) record;
        } else if (record instanceof Integer) {
            timeStamp = Long.parseLong(record.toString());
        } else if (record instanceof String) {
            timeStamp = Long.parseLong((String) record);
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        timeStamp = timeStamp * 1000;
        return new Date(timeStamp);
    }
}
