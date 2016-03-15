package com.jlsoft.framework.pi.util;

import java.util.HashMap;
import java.util.Map;

public class MemoryCache {

    private volatile static MemoryCache instance = null;
    private static Map cache = new HashMap();

    private MemoryCache() {
    }

    public static MemoryCache getInstance() {
        if (instance == null) {
            synchronized (MemoryCache.class) {
                if (instance == null) {
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public void remove(String key) {
        cache.remove(key);
    }
}
