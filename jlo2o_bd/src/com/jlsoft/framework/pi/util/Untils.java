package com.jlsoft.framework.pi.util;

public class Untils {

    public static Object getSyncObject(String key) {
        Object syncObject = MemoryCache.getInstance().get(key);
        if (syncObject == null) {
            syncObject = new Object();
            MemoryCache.getInstance().put(key, syncObject);
        }
        return syncObject;
    }
    
    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }    
}
