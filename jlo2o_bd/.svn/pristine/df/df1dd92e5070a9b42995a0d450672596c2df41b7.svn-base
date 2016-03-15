<%@ page import="com.jlsoft.framework.JLQuery,com.jlsoft.utils.JLTools,java.util.*,com.fasterxml.jackson.databind.ObjectMapper,com.jlsoft.utils.*" %>
<%
String pid = request.getParameter("pid");
String sqlID = request.getParameter("sqlID");
String backStr = request.getParameter("backStr");
String treeCxjb = request.getParameter("treeCxjb");
String DataBaseType = JLTools.getDataBaseType(request.getParameter("DataBaseType"));

JLQuery dao = new JLQuery();
Map map = new HashMap();
map.put("pid",pid);
map.put("sqlid",sqlID);
map.put("backStr",backStr);
map.put("treeCxjb",new Integer(treeCxjb));
map.put("DataBaseType",DataBaseType);
List list = (List)dao.selectTree(map);
response.setContentType("text/html;charset=UTF-8");
out.println(new ObjectMapper().writeValueAsString(list));
%>