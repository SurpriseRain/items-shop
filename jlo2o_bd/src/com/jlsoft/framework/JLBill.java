package com.jlsoft.framework;

import com.jlsoft.framework.sqlwriter.SQLConvertor;
import com.jlsoft.framework.sqlwriter.SpringSQLWriter;
import com.jlsoft.framework.dataset.IDataSet;
import com.jlsoft.framework.dataset.RowSetter;
import com.jlsoft.framework.sqlwriter.ISQLWriter;
import com.jlsoft.framework.sqlwriter.JdbcSQLWriter;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PropertiesReader;
import com.jlsoft.utils.PubFun;
import java.sql.Connection;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JLBill extends JLConn {

    protected IDataSet cds = null;
    protected final String CONST_TIME01 = FinalValue.TIME01;
    protected final String CONST_DATETIME = FinalValue.DateTime;
    protected final String CONST_DATE = FinalValue.Date;
    protected final PropertiesReader config = PropertiesReader.getInstance();
    
    public void setDataSet(IDataSet cds) {
        this.cds = cds;
    }    

    //取第一级数据
    public Map getRow(final String sSQL, int i) throws Exception {
        Map otherValue = new HashMap();
        return getRows(sSQL, otherValue)[i];
    }

    //取第一级数据
    public Map getRow(final String sSQL, final Map otherValue, int i) throws Exception {
        return getRows(sSQL, otherValue)[i];
    }

    //取第一级数据
    public Map[] getRows(final String[] columns) throws Exception {
        return getRows(columns, null);
    }

    //取第一级数据
    public Map[] getRows(final String sSQL, final Map otherValue) throws Exception {
        String[] columns = new SQLConvertor(sSQL).getColNames();
        return getRows(columns, otherValue);
    }

    //取第一级数据
    public Map[] getRows(final String[] columns, final Map otherValue) throws Exception {
        return getRows(new RowSetter() {

            public String getNodeName() {
                return "";
            }

            public String[] getColumns() {
                return columns;
            }

            public void setValue(Map row) {
                //设置数据值（动态）、行数据有效性检查

                /*
                 * 自定义的直接传入数据
                 */
                if (otherValue != null) {
                    for (Object key : otherValue.keySet()) {
                        if (row.containsKey(key)) {
                            row.remove(key);
                        }
                        row.put(key, otherValue.get(key));
                    }
                }
            }
        });
    }

    //取第一级数据
    public Map[] getRows(RowSetter rowSetter) throws Exception {
        if (cds == null) {
            throw new Exception("DataSet没有初始化.");
        }
        return cds.getRows(rowSetter);
    }

    //取第二级数据
    public Map[] getRows(final String NodeName, final String sSQL, int i) throws Exception {
        Map otherValue = new HashMap();
        return getRows(NodeName, sSQL, otherValue, i);
    }

    //取第二级数据
    public Map[] getRows(final String NodeName, final String sSQL, final Map otherValue, int i) throws Exception {
        String[] columns = new SQLConvertor(sSQL).getColNames();
        return getRows(NodeName, columns, otherValue, i);
    }

    //取第二级数据
    public Map[] getRows(final String NodeName, final String[] columns, int i) throws Exception {
        return getRows(NodeName, columns, null, i);
    }

    //取第二级数据
    protected Map[] getRows(final String NodeName, final String[] columns, final Map otherValue, int i) throws Exception {
        return getRows(new RowSetter() {

            public String getNodeName() {
                return NodeName;
            }

            public String[] getColumns() {
                return columns;
            }

            public void setValue(Map row) {
                //设置数据值（动态）、行数据有效性检查

                /*
                 * 自定义的直接传入数据
                 */
                if (otherValue != null) {
                    for (Object key : otherValue.keySet()) {
                        if (row.containsKey(key)) {
                            row.remove(key);
                        }
                        row.put(key, otherValue.get(key));
                    }
                }
            }
        }, i);
    }

    //取第二级数据
    public Map[] getRows(RowSetter rowSetter, int i) throws Exception {
        if (cds == null) {
            throw new Exception("DataSet没有初始化.");
        }
        return cds.getRows(rowSetter, i);
    }

    //取第三级数据
    public Map[] getRows(final String NodeName, final String sSQL, int i, int j) throws Exception {
        Map otherValue = new HashMap();
        return getRows(NodeName, sSQL, otherValue, i, j);
    }

    //取第三级数据
    public Map[] getRows(final String NodeName, final String sSQL, final Map otherValue, int i, int j) throws Exception {
        String[] columns = new SQLConvertor(sSQL).getColNames();
        return getRows(NodeName, columns, otherValue, i, j);
    }

    //取第三级数据
    public Map[] getRows(final String NodeName, final String[] columns, int i, int j) throws Exception {
        return getRows(NodeName, columns, null, i, j);
    }

    //取第三级数据
    public Map[] getRows(final String NodeName, final String[] columns, final Map otherValue, int i, int j) throws Exception {
        return getRows(new RowSetter() {

            public String getNodeName() {
                return NodeName;
            }

            public String[] getColumns() {
                return columns;
            }

            public void setValue(Map row) {
                //设置数据值（动态）、行数据有效性检查

                /*
                 * 自定义的直接传入数据
                 */
                if (otherValue != null) {
                    for (Object key : otherValue.keySet()) {
                        if (row.containsKey(key)) {
                            row.remove(key);
                        }
                        row.put(key, otherValue.get(key));
                    }
                }
            }
        }, i, j);
    }

    //取第三级数据
    public Map[] getRows(RowSetter rowSetter, int i, int j) throws Exception {
        if (cds == null) {
            throw new Exception("DataSet没有初始化.");
        }
        return cds.getRows(rowSetter, i, j);
    }
    
    //取多级（三级以上）数据
    public Map[] getRows(final String NodeName, final String sSQL, final Map otherValue, int[] indexes) throws Exception {
        String[] columns = new SQLConvertor(sSQL).getColNames();
        return getRows(NodeName, columns, otherValue, indexes);
    }    
    
    //取多级（三级以上）数据
    public Map[] getRows(final String NodeName, final String[] columns, final Map otherValue, int[] indexes) throws Exception {
        return getRows(new RowSetter() {

            public String getNodeName() {
                return NodeName;
            }

            public String[] getColumns() {
                return columns;
            }

            public void setValue(Map row) {
                //设置数据值（动态）、行数据有效性检查

                /*
                 * 自定义的直接传入数据
                 */
                if (otherValue != null) {
                    for (Object key : otherValue.keySet()) {
                        if (row.containsKey(key)) {
                            row.remove(key);
                        }
                        row.put(key, otherValue.get(key));
                    }
                }
            }
        }, indexes);
    }

    //取取多级（三级以上）数据
    public Map[] getRows(RowSetter rowSetter, int[] indexes) throws Exception {
        if (cds == null) {
            throw new Exception("DataSet没有初始化.");
        }
        return cds.getRows(rowSetter, indexes);
    }    

    //单条执行
    public int execSQL(JdbcTemplate conn, String sSQL, Map row) throws Exception {
        sSQL = new SQLConvertor().format(sSQL, row);
        ISQLWriter sqlWriter = new SpringSQLWriter(conn, sSQL);
        return sqlWriter.write(row);
    }

    //多条执行
    public int[] execSQL(JdbcTemplate conn, String sSQL, Map[] rows) throws Exception {
        if (rows.length > 0) {
            sSQL = new SQLConvertor().format(sSQL, rows[0]);
        }
        ISQLWriter sqlWriter = new SpringSQLWriter(conn, sSQL);
        return sqlWriter.writeBatch(rows);
    }

    //执行第一级数据（i = 0）
    protected int execSQL(JdbcTemplate conn, String sSQL, int i) throws Exception {
        Map[] data = getRows(new SQLConvertor(sSQL).getColNames());
        return execSQL(conn, sSQL, data[i]);
    }

    //执行第一级数据（i = 0）
    protected int execSQL(JdbcTemplate conn, String sSQL, int i, final Map otherValue) throws Exception {
        Map[] data = getRows(new SQLConvertor(sSQL).getColNames(), otherValue);
        sSQL = new SQLConvertor().format(sSQL, otherValue);
        return execSQL(conn, sSQL, data[i]);
    }
    
//    //单条执行
//    public int execSQL(Connection conn, String sSQL, Map row) throws Exception {
//        sSQL = new SQLConvertor().format(sSQL, row);
//        ISQLWriter sqlWriter = new JdbcSQLWriter(conn, sSQL);
//        return sqlWriter.write(row);
//    }
//
//    //多条执行
//    public int[] execSQL(Connection conn, String sSQL, Map[] rows) throws Exception {
//        if (rows.length > 0) {
//            sSQL = new SQLConvertor().format(sSQL, rows[0]);
//        }
//        ISQLWriter sqlWriter = new JdbcSQLWriter(conn, sSQL);
//        return sqlWriter.writeBatch(rows);
//    }

    public LinkedList callProc(JdbcTemplate conn, String sSQL, final LinkedList inputParameter, final LinkedList outParameter) {
        return PubFun.callProc(sSQL, inputParameter, outParameter, conn);
    }
    
    protected void setDateValue(Map row, String key, String value) {
			if (JLTools.isEmpty(value)) {
				row.put(key, String.valueOf(""));
			} else {
				if (value.length() < 11) {
					row.put(key, JLTools.parseDate(value));
				} else {
					row.put(key, JLTools.parseTimestamp(value));
				}
			}
		}    

    protected void setKMS(JdbcTemplate conn, Map row) {
        LinkedList inValue = new LinkedList();
        inValue.add(row.get("BM01"));   //部门
        inValue.add(row.get("CK01"));   //仓库
        inValue.add(row.get("WLDW01")); //供应商
        inValue.add(row.get("SPXX01"));  //商品内码
        inValue.add(row.get("TSBJ"));  //商品属性
        inValue.add(row.get("SIZE"));  //包装含量
        inValue.add(row.get("FSSL"));  //发生数量 
        callProc(conn, "{call SETSP_KMSL(?,?,?,?,?,?,?)}", inValue, null);
    }

    protected void setWDZ(JdbcTemplate conn, Map row) {
        LinkedList inValue = new LinkedList();
        inValue.add(row.get("BM01"));   //部门
        inValue.add(row.get("CK01"));   //仓库
        inValue.add(row.get("DJLB"));   //单据类型
        inValue.add(row.get("DJHM"));   //单据号码
        inValue.add(row.get("KMS"));    //可卖数
        inValue.add(row.get("BGZ"));    //保管帐
        inValue.add(row.get("SJZ"));    //三级帐
        callProc(conn, "{call SET_WDZ(?,?,?,?,?,?,?)}", inValue, null);
    }

    protected long updateDJBH(JdbcTemplate conn, String sGSID, String sTblName) {
        LinkedList inParameter = new LinkedList();
        LinkedList outParameter = new LinkedList();
        inParameter.add(sGSID);
        inParameter.add(sTblName);
        outParameter.add(0L);
        outParameter = callProc(conn, "{call Update_DJBHZT(?,?,?)}", inParameter, outParameter);
        return ((Long) (outParameter.get(0))).longValue();
    }

    protected long updateVIPBHZT(JdbcTemplate conn, String sTblName) {
        LinkedList inParameter = new LinkedList();
        LinkedList outParameter = new LinkedList();
        inParameter.add(sTblName);
        outParameter.add(0L);
        outParameter = callProc(conn, "{call UPDATE_VIPBHZT(?,?)}", inParameter, outParameter);
        return ((Long) (outParameter.get(0))).longValue();
    }

    /**
     * 返回基础信息的最大编号
     *
     * @param sTblName String 表名
     * @param conn Connection
     * @return long 最大编号
     * @throws Exception
     */
    public long updateBHZTJC(JdbcTemplate conn, String sTblName) {
        LinkedList inParameter = new LinkedList();
        LinkedList outParameter = new LinkedList();
        inParameter.add(sTblName);
        outParameter.add(0L);
        outParameter = callProc(conn, "{call Update_JCBHZT(?,?)}", inParameter, outParameter);
        return ((Long) (outParameter.get(0))).longValue();
    }

    protected long updateCSBHZT(JdbcTemplate conn, String sGSID, String sTblName) {
        LinkedList inParameter = new LinkedList();
        LinkedList outParameter = new LinkedList();
        inParameter.add(sGSID);
        inParameter.add(sTblName);
        outParameter.add(0L);
        outParameter = callProc(conn, "{call UPDATE_CSBHZT(?,?,?)}", inParameter, outParameter);
        return ((Long) (outParameter.get(0))).longValue();
    }
    
    protected long updateBHZTPZ(JdbcTemplate conn,String sZTID, int iYear, int iMonth, String sPZLX) throws
    Exception {
    	LinkedList inParameter = new LinkedList();
        LinkedList outParameter = new LinkedList();
        inParameter.add(sZTID);
        inParameter.add(sPZLX);
        inParameter.add(iYear);
        inParameter.add(iMonth); 
        outParameter.add(0L);
        outParameter = callProc(conn, "{call UPDATE_PZBHZT(?,?,?,?,?)}", inParameter, outParameter);
    	return ((Long) (outParameter.get(0))).longValue();
    }
    
	protected void setKQKMS(JdbcTemplate conn, Map row) {
		LinkedList inValue = new LinkedList();
		inValue.add(row.get("P_CK")); // 仓库
		inValue.add(row.get("P_KQ")); // 仓库库区
		inValue.add(row.get("P_BM")); // 部门
		inValue.add(row.get("P_DW")); // 供应商
		inValue.add(row.get("P_TJBJ")); // 商品属性
		inValue.add(row.get("P_SP")); // 商品内码
		inValue.add(row.get("P_BZHL")); // 包装含量
		inValue.add(row.get("P_DJSL")); // 发生数量
		inValue.add(row.get("P_PH")); // 批号
		inValue.add(row.get("P_ZT")); // 状态
		callProc(conn, "{call SETSP_KQKMS(?,?,?,?,?,?,?,?,?,?)}", inValue, null);
	}

    protected Map result(final String sJLBH) {
        Map rst = new HashMap();
        rst.put("DJBH", sJLBH);
        return rst;
    }

    /**
     * @todo 查询返回整数
     *
     * @param sql
     * @return
     */
    public int queryForInt(JdbcTemplate jdbcTemplate, String sql) {
        int result = 0;
        result = jdbcTemplate.queryForInt(sql);
        return result;
    }

    /**
     * @todo 查询结果返回Map
     *
     * @param sql
     * @return
     */
    public Map queryForMap(JdbcTemplate jdbcTemplate, String sql) {
        Map tmp = null;
        try {
            tmp = (Map) jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException ex) {
            // Spring 对空结果集进行了严格检查，我们忽略这个异常。
        }
        return tmp;
    }

    /**
     * @todo 查询结果返回Map
     *
     * @param sql
     * @return
     */
    public Map queryForMap(JdbcTemplate jdbcTemplate, String sql, Map map) {
        NamedParameterJdbcTemplate npjt = null;
        npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map tmp = null;
        try {
            tmp = (Map) npjt.queryForMap(sql, map);
        } catch (EmptyResultDataAccessException ex) {
            // Spring 对空结果集进行了严格检查，我们忽略这个异常。
        }
        return tmp;
    }

    /**
     * @todo 查询结果返回list
     *
     * @param sql
     * @return
     */
    public List queryForList(JdbcTemplate jdbcTemplate, String sql) {
        List list = (List) jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * @todo 命名参数绑定查询，结果返回List<Map>
     *
     * @param sql
     * @return
     */
    public List queryForList(JdbcTemplate jdbcTemplate, String sql, Map map) {
        NamedParameterJdbcTemplate npjt = null;
        npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Map<String, Object>> list = npjt.queryForList(sql, map);
        return list;
    }

    //通过mybatis获取LIST值方法
    public List queryForListByXML(String dataBaseType, String sqlId, Map map) {
        SqlSession session = JlAppSqlConfig.getSqlMapInstance(dataBaseType);
        List list = session.selectList(sqlId, map);
        return list;
    }
}
