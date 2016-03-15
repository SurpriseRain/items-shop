package com.jlsoft.framework.pi.convertor;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/*
 * 深拷贝:序列化|反序列化方法
 */
public class ObjectCloner extends AbstractConvertor {

    @Override
    protected Object convert(Object record) throws Exception {
        return clone(record);
    }

    //深拷贝:序列化|反序列化方法
    private Object clone(Object src) throws Exception, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        Object dest = in.readObject();
        return dest;
    }
}
