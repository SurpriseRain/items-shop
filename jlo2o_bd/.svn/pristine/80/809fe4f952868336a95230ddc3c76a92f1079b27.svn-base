package com.jlsoft.o2o.interfacepackage.http;






import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static String writeObject(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		
		StringWriter writer = new StringWriter();
		String re = null;
		try {
			JsonGenerator json = new JsonFactory().createGenerator(writer);
			mapper.writeValue(json, obj);
			re = writer.toString();
			json.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	  /** 
     * @param <T>   泛型声明 
     * @param json  JSON字符串 
     * @param clzz  要转换对象的类型 
     * @return 
     */  
    public static <T> T fromJson(String json,Class<T> clzz){          
        ObjectMapper mapper = new ObjectMapper();   
        T t = null;  
        try {          
            t =  mapper.readValue(json, clzz); 
        } catch (Exception e) {  
        	e.printStackTrace();
        }   
        return t;  
    }  
    public static String writeObjectNotnull(Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	StringBuffer ssString=new StringBuffer();
    	Class classPojo = obj.getClass();
		Field[] classFields = classPojo.getDeclaredFields();
		for (int i = 0; i < classFields.length; i++) {
			Method method = null;

			String nameString = classFields[i].getName();
			String upperName = nameString.substring(0, 1).toUpperCase()
					+ nameString.substring(1);
			method = classPojo.getMethod("get" + upperName);
			Object value = method.invoke(obj);
			if(null!=value){
				if(ssString.length()==0){
					ssString.append("{\""+nameString+"\":"+"\""+value+"\"");
				}else{
					ssString.append(",\""+nameString+"\":"+"\""+value+"\"");
				}
			}
		}
		ssString.append("}");

		return ssString.toString();

    } 
}
