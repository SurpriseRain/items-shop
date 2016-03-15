package com.jlsoft.framework.pi.processor.log;

import com.jlsoft.framework.pi.api.AbstractDataProcessor;

public class LogProcessor extends AbstractDataProcessor {

    @Override
    protected Object doProcess(Object record) throws Exception {
        System.out.println(record.toString());
        return record;
    }
}
