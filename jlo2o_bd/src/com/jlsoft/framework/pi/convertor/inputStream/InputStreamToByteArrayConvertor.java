package com.jlsoft.framework.pi.convertor.inputStream;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class InputStreamToByteArrayConvertor extends AbstractConvertor {

    private int BUFFER_SIZE = 4096;

    public InputStreamToByteArrayConvertor() {
    }

    public InputStreamToByteArrayConvertor(int BUFFER_SIZE) {
        this.BUFFER_SIZE = BUFFER_SIZE;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        InputStream input;
        if (record instanceof InputStream) {
            input = (InputStream) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return doConvert(input);
    }

    private byte[] doConvert(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            int count = -1;
            byte[] data = new byte[BUFFER_SIZE];
            while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            data = null;
            return outStream.toByteArray();
        } finally {
            if (outStream != null) {
                outStream.close();
                outStream = null;
            }
        }
    }
}
