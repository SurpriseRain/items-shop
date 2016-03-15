package com.jlsoft.framework.pi.convertor.file;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class FileToStringConvertor extends AbstractConvertor {

    @Override
    protected Object convert(Object record) throws Exception {
        Reader reader = null;
        if (record instanceof String) {
            reader = new FileReader((String) record);
        } else if (record instanceof Reader) {
            reader = (Reader) record;
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }

        StringBuilder sBuf = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);
        String data = br.readLine();//一次读入一行，直到读入null为文件结束  
        while (data != null) {
            sBuf.append(data); //.append(" ")
            data = br.readLine(); //接着读下一行  
        }
        return sBuf.toString();
    }
}
