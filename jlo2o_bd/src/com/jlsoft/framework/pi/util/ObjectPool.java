package com.jlsoft.framework.pi.util;

import com.jlsoft.framework.pi.api.IDataProcessor;
import java.util.HashMap;
import java.util.Map;

public class ObjectPool {

    protected static Map objects = new HashMap();

    public synchronized Object checkOut(String className) throws Exception {
        Object o = objects.get(className);
        if (o != null) {
            return o;
        }
        o = create(className);
        return o;
    }

    public Object create(String className) throws Exception {
        IDataProcessor processor = null;
        try {
            processor = (IDataProcessor) Class.forName(className).newInstance();
            objects.put(className, processor);
        } catch (InstantiationException e) {
            throw new Exception("Must have an empty constructor", e);
        } catch (IllegalAccessException e) {
            throw new Exception("Must have a public cosntructor", e);
        } catch (ClassNotFoundException e) {
            throw new Exception("The class " + className + " is not found", e);
        }
        return processor;
    }
}
