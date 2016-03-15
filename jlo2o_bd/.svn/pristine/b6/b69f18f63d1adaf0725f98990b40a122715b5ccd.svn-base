package com.jlsoft.framework.pi.convertor.inputStream;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.io.InputStream;

public class InputStreamToStringConvertor extends AbstractConvertor {

    private String enc = "UTF-8";

    public InputStreamToStringConvertor() {
    }

    public InputStreamToStringConvertor(String enc) {
        this.enc = enc;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        InputStream input;
        if (record instanceof InputStream) {
            input = (InputStream) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        byte[] data = (byte[]) new InputStreamToByteArrayConvertor().process(input);
        return new String(data, enc);
    }
}
