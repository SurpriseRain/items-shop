package com.jlsoft.framework.pi.convertor.map;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import com.jlsoft.framework.pi.util.Untils;
import com.jlsoft.utils.config.Config;
import com.jlsoft.utils.config.Field;
import com.jlsoft.utils.config.JKConfig;
import java.util.*;

public class MapMappingConvertor extends AbstractConvertor {

    private String configKey = null;

    public MapMappingConvertor(String configKey) {
        this.configKey = configKey;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        if (configKey == null) {
            throw new Exception("Null configKey not permitted.");
        }

        if (!(record instanceof Map)) {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }

        Config config = JKConfig.getConfig(configKey);
        return mapping((Map) record, config);
    }

    private Object mapping(Map record, Config config) throws Exception {
        Object o;
        Field field;
        Map row = new HashMap();
        List fields = config.getFields();
        for (Iterator it = fields.iterator(); it.hasNext();) {
            o = it.next();
            if (o instanceof Config) {
                Object value = record.get(((Config) o).getId());
                if (value instanceof Map) {
                    row.put(getKey(o), mapping((Map) value, (Config) o));
                } else if (value instanceof List) {
                    List result = new ArrayList();
                    for (Object data : (List) value) {
                        result.add(mapping((Map) data, (Config) o));
                    }
                    row.put(getKey(o), result);
                }
            } else if (o instanceof Field) {
                field = (Field) o;
                Object value = record.get(field.getAttrname());
                if (value != null) {
                    if (field.getConvertor() != null) {
                        value = field.getConvertor().process(value);
                    }
                    row.put(getKey(field), value);
                }
            }
        }
        return row;
    }

    private Object getKey(Object o) {
        if (o instanceof Field) {
            return isNull(((Field) o).getTarget(), ((Field) o).getAttrname());
        } else if (o instanceof Config) {
            return isNull(((Config) o).getTarget(), ((Config) o).getId());
        }
        return null;
    }

    private Object isNull(Object value, Object defaultValue) {
        if (value instanceof String) {
            return !Untils.isEmpty(value.toString()) ? value : defaultValue;
        } else {
            return (value != null) ? value : defaultValue;
        }
    }
    
    
//    private Object getField(List fields, String key) {
//        Object o;
//        for (Iterator it = fields.iterator(); it.hasNext();) {
//            o = it.next();
//            if (o instanceof Config) {
//                if (((Config) o).getId().equals(key)) {
//                    return o;
//                }
//            } else if (o instanceof Field) {
//                if (((Field) o).getAttrname().equals(key)) {
//                    return o;
//                }
//            }
//        }
//        return null;
//    }
//
//    private Object mapping(Map record, Config config) throws Exception {
//        Object o;
//        Field field;
//        Map row = new HashMap();
//        List fields = config.getFields();
//
//        for (Object key : record.keySet()) {
//            o = getField(fields, (String) key);
//            if (o instanceof Config) {
//                Object value = record.get(((Config) o).getId());
//                if (value instanceof Map) {
//                    row.put(getKey(o), mapping((Map) value, (Config) o));
//                } else if (value instanceof List) {
//                    List result = new ArrayList();
//                    for (Object data : (List) value) {
//                        result.add(mapping((Map) data, (Config) o));
//                    }
//                    row.put(getKey(o), result);
//                }
//            } else if (o instanceof Field) {
//                field = (Field) o;
//                Object value = record.get(field.getAttrname());
//                if (value != null) {
//                    if (field.getConvertor() != null) {
//                        value = field.getConvertor().process(value);
//                    }
//                    row.put(getKey(field), value);
//                }
//            } else {
//               row.put(key, record.get(key)); 
//            }
//        }
//        return row;
//    }    
}
