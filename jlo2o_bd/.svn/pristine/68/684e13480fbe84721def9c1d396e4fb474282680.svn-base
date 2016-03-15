package com.jlsoft.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;

@Controller
@RequestMapping("/jlquery")
public class JLQuery {

    private SqlSession session = null;

	@RequestMapping("/selecto2o.action")
    public Map selectForO2O(String XmlData) throws Exception {
        session = JlAppSqlConfig.getO2OSqlMapInstance();
        return select(XmlData);
    }
    
    @RequestMapping("/selectSCM.do")
    public Map selectForSCM(String XmlData) throws Exception {
        session = JlAppSqlConfig.getSCMSqlMapInstance();
        return select(XmlData);
    }

    @RequestMapping("/selectVIP.do")
    public Map selectForVIP(String XmlData) throws Exception {
        session = JlAppSqlConfig.getVIPSqlMapInstance();
        return select(XmlData);
    }

    @RequestMapping("/selectSH.do")
    public Map selectForSH(String XmlData) throws Exception {
        session = JlAppSqlConfig.getSHSqlMapInstance();
        return select(XmlData);
    }

    public Map select(String XmlData) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
		List list =  (List)mapper.readValue(XmlData, ArrayList.class); 
		Map map = (Map)list.get(0);
		map.put("dataType", "Json");
		map.put("session", session);
		return map;
    }
    
    //查询树型控件
	public List selectTree(Map map) throws IOException{
		List list = null;
		try{
		session = JlAppSqlConfig.getSqlMapInstance(map.get("DataBaseType").toString());
		list = session.selectList((String) map.get("sqlid"), map);
		}catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	session.close();
	    }
		System.out.println(list);
		return list;
	}
   
	//java内部调用XML
	public static List queryForListByXML(String dataBaseType,String sqlId,Object obj) throws Exception{
    	List list = null;
    	SqlSession session = null;
    	try{
    		 session = JlAppSqlConfig.getSqlMapInstance(dataBaseType);
    		 list = session.selectList(sqlId, obj);
    	}catch(Exception ex){
    		throw ex;
    	}finally{
    		session.close();
    	}
    	return list;
    }
	
}
