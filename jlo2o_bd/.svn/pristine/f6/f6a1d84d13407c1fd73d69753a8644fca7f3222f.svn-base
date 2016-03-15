package com.jlsoft.framework.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.framework.dataset.IDataSet;
import com.jlsoft.utils.JLTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JlPIManage extends JLBill {
	private static Logger logger = Logger.getLogger(JlPIManage.class);
    private ArrayList al = new ArrayList(); //存放所有result值
    private String tabName = "";
    
    //检查工作流是否可流转
    @SuppressWarnings("unchecked")
    public boolean checkWorkflow(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = "";
        String uri = (String) request.getRequestURI();
        String rootPath = (String)request.getContextPath();
        String data = "";
        String param = "";

        if(uri.substring(1, 2).equals("/")){
        	uri=uri.substring(1);
        }
        uri = uri.replace(rootPath, "");

        if(uri.indexOf("/jlquery/") != -1 || uri.indexOf("/getJson/") != -1)return true;
        //当uri路径地址中存在form时表示表单穿透
        int bdbj=0;
        if(uri.indexOf("form/saveRecord")!=-1){
        	bdbj=1;
        	uri=getBDSender((String) request.getParameter("json"), (String) request.getParameter("initField"));
        	if(uri.equals(""))return true;
        }

		String rnoPara = request.getParameter("RNO")==null||request.getParameter("RNO").toString().length()==0?"":("?RNO="+request.getParameter("RNO").toString());
        uri = uri + rnoPara;
        
        //判断穿透是否有配置

        sql = "SELECT count(1) FROM W_WORKFLOW_CONTROL WHERE SENDER='" + uri + "' AND YXBJ=1";
        if (queryForInt(scm, sql) == 0) {
            return true;
        } else {
        	String tblName = "";
        	if(bdbj==0){
        		uri = uri.substring(uri.indexOf("/"), uri.length());// /dhd/insert
                tblName = uri.substring(0, uri.lastIndexOf("/"));// /dhd
                data= (String) request.getParameter("XmlData");
                param = (String) request.getParameter("RNO"); // 取客户端传递给服务端的RNO参数
        	}else{
        		JSONObject uriObj = JSONObject.fromObject(uri);
        		tblName=uriObj.getString("name").toString();
        		data= (String) request.getParameter("json");
        	}
        	request.setAttribute("SENDER", uri);
        	request.setAttribute("tblName", tblName);
            sql = "SELECT A.RESULT,B.RESULTITEM,B.KEYFIELD,B.DATASOURCE,B.FIELDNAME FROM W_PIRESULT A, W_PIRESULTITEM B WHERE A.SENDER=B.SENDER AND A.SENDER='" + uri + "' AND B.CSLX=0";
            List l = queryForList(scm, sql);
            al.clear();
            HashMap hm = null;
            HashMap result = null;  //map存放两中类型值result=DHD,resultitem=值集合 
            ArrayList resultItem = null;
            boolean find = false;
            //循坏开始
            for (int i = 0; i < l.size(); i++) {
                hm = (HashMap) l.get(i);
                for (int j = 0; j < al.size(); j++) {
                    result = (HashMap) al.get(j);
                    if (result.get("RESULT").equals(hm.get("RESULT"))) {
                        resultItem = (ArrayList) result.get("RESULTITEM");
                        resultItem.add(hm);
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    result = new HashMap();
                    result.put("RESULT", hm.get("RESULT"));
                    resultItem = new ArrayList();
                    resultItem.add(hm);
                    result.put("RESULTITEM", resultItem);
                    al.add(result);
                }
                find = false;
            }
            //循环结束
            request.setAttribute("uri", uri);
            request.setAttribute("list_result", al);
            //对前端传入值进行处理
            cds = new DataSet(data);
        	request.setAttribute("XmlData", data);
            request.setAttribute("cds", cds);
            
            //判断message_id是否存在开始
            String MESSAGE_ID = null;
            if (!JLTools.isNull(request.getParameter("MESSAGE_ID"))) {
                MESSAGE_ID = request.getParameter("MESSAGE_ID");
            } else {
                //通过al获取需要的主键名，拼接查询条件SQL开始
                if (al.size() > 0) {
                	List resultList = (List) ((HashMap) al.get(0)).get("RESULTITEM");
                    Map resultMap;
                    String backStr = "";
                    String djbh = "";
                    for (int i = 0; i < resultList.size(); i++) {
                        resultMap = (Map) resultList.get(i);
                        if (resultMap.get("KEYFIELD") != null && !resultMap.get("KEYFIELD").equals("")) {
                            if (resultMap.get("KEYFIELD").equals("GSXX")) {
                                backStr  = backStr +  " AND GSXX='" + cds.getField(resultMap.get("RESULTITEM").toString(), 0) + "'";
                            }
                            if (resultMap.get("KEYFIELD").equals("JLBH")) {
                                djbh = cds.getField(resultMap.get("RESULTITEM").toString(), 0);
                                backStr = backStr + " AND JLBH=" + djbh + "";
                            }
                        }
                    }

                    if (!backStr.equals("null") && !backStr.equals("") && !djbh.equals("null") && !djbh.equals("")) {
                    	String msgSQL = "SELECT MESSAGE_ID FROM W_PISCENE WHERE TBLNAME='" + tblName + "'" + backStr;
                    	try{
                    		MESSAGE_ID = (String) scm.queryForObject(msgSQL, String.class);
                    	}catch(Exception ex){
                    		MESSAGE_ID = null;
                    	}
                    } 
                }
            }
            //判断message_id结束
            //W_PISCENE中没记录，检查行为是不是流程的第一步，如果不是流程第一步，表明之前没有穿透，那么后续也不需要穿透
        	if (JLTools.isNull(MESSAGE_ID)) {
        		sql = "SELECT count(1) FROM W_WORKFLOW_CONTROL WHERE SENDER='" + uri + "' AND BZBJ=1";
        		if(queryForInt(scm, sql) == 0){
        			return true;
        		}
            }
            //封装从页面传入值传到PCRM进行行为过滤判断
            IDataSet cds = (IDataSet) request.getAttribute("cds");
            tabName = ((HashMap) al.get(0)).get("RESULT").toString();
            List resultList = (List) ((HashMap) al.get(0)).get("RESULTITEM");
            Map resultMap;
            HashMap XML_DATA = new HashMap();
            HashMap parameterMap = new HashMap();
            for (int j = 0; j < resultList.size(); j++) {
                resultMap = (Map) resultList.get(j);
                if (Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 1) {
                    parameterMap.put(resultMap.get("RESULTITEM").toString(), cds.getField(resultMap.get("RESULTITEM").toString(), 0));
                }
            }
            XML_DATA.put(tabName, parameterMap);
            //封装值结束
            
            //远程调用开始
            String sendurl = null;
            String pcrmurl = config.getProperty("PCRM_URL");
            if (!pcrmurl.endsWith("/")) {
                pcrmurl += "/";
            }
            if (JLTools.isNull(MESSAGE_ID)) {
                sendurl = pcrmurl + "streamDocument/checkWorkflowStart.do";
                request.setAttribute("RECEIVER", pcrmurl + "streamDocument/interfaceCreateDocument.do");
            } else {
                sendurl = pcrmurl + "streamDocument/checkSceneExist.do";
                request.setAttribute("RECEIVER", pcrmurl + "streamDocument/interfaceTsransiteDocument.do");
            }
            HashMap strData = new HashMap();
            strData.put("PI_USERNAME", (request.getRemoteUser()==null||request.getRemoteUser().length()==0)?request.getParameter("PI_USERNAME"):request.getRemoteUser());
            strData.put("MESSAGE_ID", MESSAGE_ID);
            strData.put("SENDER", uri);
            strData.put("XML_DATA", XML_DATA);
            //封装获取权限值
            Map qxMap = getQXValue(cds,request);
            strData.put("QX_DATA", qxMap);
            ObjectMapper om = new ObjectMapper();
            //String ss = JLTools.sendToSync(om.writeValueAsString(strData), sendurl);
            if (null != param && !"".equals(param)) {
                sendurl += "?RNO=" + param; // 添加从客户端获得的RNO
            }
            String ss = getSendResult(om.writeValueAsString(strData),sendurl);
            //根据不同的返回值，来判断是否走向：为空时表示连接报错，业务系统照样运行；
            if(ss.equals("")){
            	request.setAttribute("RECEIVER","");
            	return true;
            }
            
            Map checkResult = om.readValue(ss, Map.class);
            Map dataMap = (Map) checkResult.get("data");
            if (!JLTools.isNull((String) dataMap.get("MESSAGE_ID"))) {
                request.setAttribute("MESSAGE_ID", dataMap.get("MESSAGE_ID"));
                return true;
            } else {
            	throw new Exception("流程检查失败!");
                //return false;//返回为false时，表示请求结束，后续的Interceptor和Controller都不会再执行 
            }
            //远程调用结束 
        }
        
    }
    
    //穿透调用工作流
    public void sendPIResult(HttpServletRequest request, HttpServletResponse response, Map map) throws Exception {
        String sendurl = (String) request.getAttribute("RECEIVER");
        if (!JLTools.isNull(sendurl)) {
            //改成根据list_result找，对会话场景进行处理
            IDataSet cds = (IDataSet) request.getAttribute("cds");
            tabName = ((HashMap) al.get(0)).get("RESULT").toString();
            List resultList = new ArrayList();
            Map resultMap = null;
            if (al.size() > 0) {
            	resultList = (List) ((HashMap) al.get(0)).get("RESULTITEM");
                String djbh = "";
                String gsxx = "";
                for (int i = 0; i < resultList.size(); i++) {
                    resultMap = (Map) resultList.get(i);
                    if (resultMap.get("KEYFIELD") != null && !resultMap.get("KEYFIELD").equals("")) {
                        //获取gsxx值
                        if (resultMap.get("KEYFIELD").equals("GSXX") && Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 1) {
                            gsxx = cds.getField(resultMap.get("RESULTITEM").toString(), 0);
                        }
                        if (resultMap.get("KEYFIELD").equals("GSXX") && Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 2) {
                            gsxx = (String) map.get(resultMap.get("RESULTITEM").toString());
                        }
                        //获取记录编号值
                        if (resultMap.get("KEYFIELD").equals("JLBH") && Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 1) {
                            djbh = cds.getField(resultMap.get("RESULTITEM").toString(), 0);
                        }
                        if (resultMap.get("KEYFIELD").equals("JLBH") && Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 2) {
                            djbh = (String) map.get(resultMap.get("RESULTITEM").toString());
                        }
                    }
                }

                Map values = new HashMap();
                values.put("MESSAGE_ID", request.getAttribute("MESSAGE_ID"));
                values.put("TBLNAME", request.getAttribute("tblName"));
                values.put("GSXX", gsxx);
                values.put("JLBH", djbh);
                String sql = "SELECT count(1) FROM W_WORKFLOW_CONTROL WHERE SENDER='" + request.getAttribute("uri")+"' AND YXBJ=1 AND DYBJ=1 ";
                int dybj = queryForInt(scm, sql);
                int num = execSQL(scm, "UPDATE W_PISCENE SET MESSAGE_ID=MESSAGE_ID? WHERE MESSAGE_ID = MESSAGE_ID? AND TBLNAME = TBLNAME? "+(JLTools.isEmpty(gsxx)?" AND GSXX IS NULL ":" AND GSXX=GSXX? ")+" AND JLBH= JLBH?", values);
                if (num == 0 && dybj == 0) {//改成从modelAndView获得
                    execSQL(scm, "INSERT INTO W_PISCENE(MESSAGE_ID,TBLNAME,GSXX,JLBH) VALUES(MESSAGE_ID?,TBLNAME?,GSXX?,JLBH?)", values);
                }
            }

            //穿透各个业务系统
            HashMap strData = new HashMap();
            strData.put("PI_USERNAME", (request.getRemoteUser()==null||request.getRemoteUser().length()==0)?request.getParameter("PI_USERNAME"):request.getRemoteUser());
            strData.put("MESSAGE_ID", (String) request.getAttribute("MESSAGE_ID"));
            strData.put("SENDER", (String) request.getAttribute("SENDER"));
            
            HashMap XML_DATA = new HashMap();
            HashMap parameterMap = new HashMap();   //键值数据
            HashMap zyMap = new HashMap();               //摘要数据
            String qzVal = "";
            //将存放的al中的字段值获取值
            for (int j = 0; j < resultList.size(); j++) {
                resultMap = (Map) resultList.get(j);
                qzVal = "";
                if (Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 1) {
                	qzVal = (JLTools.isEmpty(cds.getField(resultMap.get("RESULTITEM").toString(), 0))?"":cds.getField(resultMap.get("RESULTITEM").toString(), 0));
                    parameterMap.put(resultMap.get("RESULTITEM").toString(), qzVal);
                    zyMap.put(resultMap.get("FIELDNAME").toString(), qzVal);
                }
                if (Integer.parseInt(resultMap.get("DATASOURCE").toString()) == 2) {
                	qzVal = (JLTools.isEmpty((String)map.get(resultMap.get("RESULTITEM").toString()))?"":(String)map.get(resultMap.get("RESULTITEM").toString()));
                    parameterMap.put(resultMap.get("RESULTITEM").toString(), qzVal);
                    zyMap.put(resultMap.get("FIELDNAME").toString(), qzVal);
                }
            }
            
            //存放键值对数据
            XML_DATA.put(tabName, parameterMap);
            strData.put("XML_DATA", XML_DATA);
            //存放摘要数据
            strData.put("ZY_DATA", zyMap);
            //封装获取权限值
            Map qxMap = getQXValue(cds,request);
            strData.put("QX_DATA", qxMap);
            //获取GRID明细数据
            strData.put("MX_DATA", getMXDZValue(cds,request));
            //存放页面传递XML值
            strData.put("CDS_DATA", request.getAttribute("XmlData"));
            //strData.put("CDS_DATA",""); 
            //存放原始数据值
            strData.put("YSSJ_DATA", (map.get("PCRM_INIT_DATA")==null)?"":map.get("PCRM_INIT_DATA"));
            
            //存放返回值
            map.remove("PCRM_INIT_DATA");
            strData.put("RESULT_DATA",map);
            
            ObjectMapper om = new ObjectMapper();
            //Map checkResult = om.readValue(JLTools.sendToSync(om.writeValueAsString(strData), sendurl), Map.class);
            Map checkResult = om.readValue(getSendResult(om.writeValueAsString(strData),sendurl), Map.class);
        }
    }
    
    //判断对象值是什么类型
    public String checkObjectType(Object obj){
    	return null;
    }
    
    //获取穿透后的结果
    public String getSendResult(String data,String sendUrl) throws Exception{
    	String strResult = "";
    	try{
    		ObjectMapper om = new ObjectMapper();
    		strResult = JLTools.sendToSync(data, sendUrl);
    	}catch(Exception ex){
    		logger.error("Error to getSendResult "+ex);
    		//throw ex; 
    	}
    	return strResult;
    }
    
    //保持回话场景
    public void setPIscene() {
    }
    
    //封装获取权限值
    public Map getQXValue(IDataSet cds,HttpServletRequest request) throws Exception{
    	Map qx = new HashMap();
    	String uri = request.getAttribute("uri").toString();
    	String sql = "SELECT A.RESULT,B.RESULTITEM,B.DATASOURCE,B.FIELDNAME FROM W_PIRESULT A,W_PIRESULTITEM B WHERE A.SENDER=B.SENDER AND A.SENDER='" + uri + "' AND B.CSLX=1";
    	List qxList = queryForList(scm, sql);
    	Map resultMap;
    	if(qxList != null){
	    	for(int i=0;i<qxList.size();i++){
	    		resultMap = (Map)qxList.get(i);
	    		qx.put(resultMap.get("RESULTITEM").toString(), cds.getField(resultMap.get("RESULTITEM").toString(), 0));
	    	}
    	}
    	return qx;
    }
    
    //获取明细对照值
    public Map getMXDZValue(IDataSet cds,HttpServletRequest request) throws Exception{
    	Map mxdz = new HashMap(); //存放最终组装结果集
    	String uri = request.getAttribute("uri").toString();
    	String sql="SELECT A.RESULT,B.OBDBM,B.NBDBM,B.FIELDS FROM W_PIRESULT A,W_PIMXDZ B WHERE A.SENDER=B.SENDER AND A.SENDER='" + uri + "'";
    	List mxdzList = queryForList(scm, sql);
    	Map resultMap;
    	Map gridMap;  //存放结果集中每条GRID值
    	List<String> list = new ArrayList<String>();

    	for(int i=0;i<mxdzList.size();i++){
    		resultMap = (Map)mxdzList.get(i);
    		//获取单个GRID数据
    		String gridValueStr=cds.getField(resultMap.get("OBDBM").toString(), 0);

    		//对GRID值进行循环
    		List gridList = new ArrayList();

    		if(!JLTools.isEmpty(gridValueStr)){
    			JSONArray gridValueList= JSONArray.fromObject(gridValueStr);
        		//获取要组装的字段值
        		//String fieldStr = resultMap.get("FIELDS").toString();
        		//String[] fieldArr = fieldStr.split(",");
        		JSONObject j_field = JSONObject.fromObject(resultMap.get("FIELDS").toString());

        		for(int k=0;k<gridValueList.size();k++){
        			gridMap = (Map)gridValueList.get(k);
        			//对字段进行循环
        			/*List fieldList = new ArrayList();
        			for(int j=0;j<fieldArr.length;j++){
        				if(fieldArr[j].equals("''")){
        					fieldList.add("");
        				}else{
        					fieldList.add(gridMap.get(fieldArr[j]).toString());
        				}
        			}

        			if(fieldList.size()>0){
        				gridList.add(fieldList);
        			}*/

        			HashMap<String,String> m_field = new HashMap();
        			Iterator it = j_field.keys();
        			while(it.hasNext()) {
        				String key = (String) it.next();
        				String value = j_field.getString(key);
        				if(value.equals("''"))
        					m_field.put(key, "");
        				else
        					m_field.put(key, (String)gridMap.get(value));

        			}

        			if(m_field.size()>0)
        				gridList.add(m_field);

        		}
        		//存放单个GRID值
        		if(gridList.size()>0){
        			mxdz.put(resultMap.get("NBDBM").toString(), gridList);
        		}
    		}else{
    			JSONObject j_field = JSONObject.fromObject(resultMap.get("FIELDS").toString());
    			Iterator it = j_field.keys();
    			//columns = new String [it.];
    			while(it.hasNext()) {
    				String key = (String) it.next();
    				String value = j_field.getString(key);
    				if(!value.equals("''")){
    					list.add(value);
    				}
    			}
    			String[] column = new String[list.size()];
    			column = list.toArray(column);
    			Map[] rowMap = getRows(resultMap.get("OBDBM").toString(), column, 0);
    			if(rowMap != null && rowMap.length > 0){
    				for(int p=0;p<rowMap.length;p++){
    					gridMap = rowMap[p];

    					HashMap<String,String> m_field = new HashMap();
                        it = j_field.keys();
    					while(it.hasNext()) {
            				String key = (String) it.next();
            				String value = j_field.getString(key);
            				if(value.equals("''")){
            					m_field.put(key,"");
            				}else{
            					m_field.put(key, (String)gridMap.get(value));
            				}
            			}
    					if(m_field.size()>0)
            				gridList.add(m_field);
    				}

    				//存放单个GRID值
            		if(gridList.size()>0){
            			mxdz.put(resultMap.get("NBDBM").toString(), gridList);
            		}
    			}
    		}
    	}
    	//{"z1":[["abc","123"],["ccc","567"]],"z2":}
    	return mxdz;
    }
    
    //当是表单穿透时，拼装最终穿透URI地址
    public String getBDSender(String json, String initField){
    	String sender="";
    	JSONObject jsonObject = JSONObject.fromObject(json);
    	Object[] l_initField = JSONArray.fromObject(initField).toArray();
    	Arrays.sort(l_initField);
    	String bdbh = jsonObject.get("bdbh").toString();
    	//Map dataMap = (Map)jsonObject.get("data");
    	//通过表单编号查询该表单的所有操作
    	String sql = "SELECT FIELD FROM W_PIBDSTEP WHERE BDBH='"+bdbh+"'";
    	List stepFieldList = queryForList(scm, sql);
    	String stepField="";
    	for(int i=0;i<stepFieldList.size();i++){
    		stepField=((Map)stepFieldList.get(i)).get("FIELD").toString();
    		Object[] fieldList= JSONArray.fromObject(stepField).toArray();
    		Arrays.sort(fieldList);
    		if(Arrays.equals(l_initField,fieldList)) {
    			sender="\"field\":"+stepField;
    			break;
    		}
    	}
    	
    	//{"name":"form1","field":["field1","field2"]}
    	if(!sender.equals(""))sender="{\"name\":\""+bdbh+"\","+sender+"}";
    	return sender;
    }
    
}