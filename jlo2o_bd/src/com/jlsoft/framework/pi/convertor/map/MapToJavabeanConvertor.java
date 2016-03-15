package com.jlsoft.framework.pi.convertor.map;

import com.jlsoft.framework.pi.convertor.map.MapMappingConvertor;
import com.jlsoft.framework.pi.api.AbstractConvertor;
import com.jlsoft.framework.pi.api.IDataProcessor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

public class MapToJavabeanConvertor extends AbstractConvertor {

    final static String PREFFIX = "set";
    private Object bean = null;
    private IDataProcessor mappingConvertor = null;

    public MapToJavabeanConvertor(Object bean) {
        this(bean, null);
    }

    public MapToJavabeanConvertor(Object bean, String configKey) {
        this.bean = bean;
        if ((configKey != null) && (!"".equals(configKey))) {
            mappingConvertor = new MapMappingConvertor(configKey);
        }
    }

    @Override
    protected Object convert(Object record) throws Exception {
        if (record == null) {
            throw new Exception("Null Map not permitted.");
        }
        if (!(record instanceof Map)) {
            throw new Exception("Record is not a Map. Record: " + record);
        }
        if (bean == null) {
            throw new Exception("Null bean object not permitted.");
        }

        if (mappingConvertor != null) {
            record = mappingConvertor.process(record);
        }

        //BeanUtils.populate(bean, (Map) record);
        MapToJavabean((Map) record, bean);

        return bean;
    }

    /**
     * 转化javaBean
     *
     * @param valueMap request获取的键值
     * @param bean javaBean对象
     * @param unSetProperties 不复制的对象
     * @param proertiesMap 不一致属性匹配
     * @return
     * @throws Exception
     */
    public static Object MapToJavabean(Map<String, String> valueMap, Object bean) throws Exception {
        if (valueMap == null) {
            return null;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        for (int i = 0; i < fields.length; i++) {
            String methodValue = null;
            Field field = fields[i];
            String key = field.getName();
            Object value = valueMap.get(key);
            if (value != null) {
                methodValue = ToString(value);
            }
            if (methodValue != null) {
                setValue(bean, key, methodValue);
            }
        }
        return bean;
    }

    public static String ToString(Object value) {
        if (value == null) {
            return "";
        }
        String type = value.getClass().getName();
        if ("java.lang.Integer".equals(type)) {
            return String.valueOf(value);
        } else if ("java.lang.String".equals(type)) {
            return (String) value;
        } else if ("java.lang.Double".equals(type)) {
            return String.valueOf(value);
        } else if ("java.lang.Float".equals(type)) {
            return String.valueOf(value);
        } else if ("java.lang.Character".equals(type)) {
            return String.valueOf(value);
        } else if ("java.util.Date".equals(type)) {
            return String.valueOf(value);
        } else if ("java.sql.Date".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(value);
        } else if ("java.sql.Timestamp".equals(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(value);
        } else if ("java.math.BigDecimal".equals(type)) {
            return value.toString();
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * 给javaBean中的属性字段赋值
     *
     * @param obj javaBean对象
     * @param name bean属性
     * @param value 值
     * @throws Exception
     */
    protected static void setValue(Object obj, String name, String value)
            throws Exception {
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(name);
        } catch (Exception e) {
            return;
        }
        if (field == null) {
            return;
        }
        Method method = obj.getClass().getDeclaredMethod(getSetMethodName(name), field.getType());
        String type = field.getGenericType().toString();
        if (type.equals("int") || type.equals("class java.lang.Integer")) {
            if (value != null && !value.equals("")) {
                method.invoke(obj, Integer.parseInt(value));
            }
        } else if (type.equals("double") || type.equals("class java.lang.Double")) {
            if (value != null && !value.equals("")) {
                method.invoke(obj, Double.valueOf(value));
            }
        } else if (type.equals("float") || type.equals("class java.lang.Float")) {
            if (value != null && !value.equals("")) {
                method.invoke(obj, Float.valueOf(value));
            }
        } else if (type.equals("char") || type.equals("class java.lang.Character")) {
            if (value != null && !value.equals("")) {
                method.invoke(obj, Character.valueOf(value.charAt(0)));
            }
        } else if (type.equals("class java.util.Date")) {
            if (value != null && !value.equals("")) {
                String format = value.length() == 10 ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                method.invoke(obj, sdf.parse(value));
            }
        } else if (type.equals("class java.sql.Timestamp")) {
            if (value != null && !value.equals("")) {
                String format = value.length() == 10 ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                method.invoke(obj, new Timestamp(sdf.parse(value).getTime()));
            }
        } else if (type.equals("class java.math.BigDecimal")) {
            if (value != null && !value.equals("")) {
                method.invoke(obj, new java.math.BigDecimal(value));
            }
        } else {
            method.invoke(obj, value);
        }
    }

    /**
     * bean属性转化为set方法
     *
     * @param methodName
     * @return
     */
    protected static String getSetMethodName(String methodName) {
        String first = methodName.substring(0, 1);
        String suffex = methodName.substring(1);
        return (PREFFIX + first.toUpperCase() + suffex).toString();
    }
}
