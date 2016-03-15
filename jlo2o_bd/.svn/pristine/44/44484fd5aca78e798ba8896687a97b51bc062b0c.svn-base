package com.jlsoft.framework.sqlwriter;

import com.jlsoft.framework.FinalValue;
import com.jlsoft.framework.pi.convertor.file.FileToStringConvertor;
import java.util.Iterator;
import java.util.Map;

public class SQLConvertor {

    private String sSQL;
    private String[] colNames;
    private String separator = "?";

    public static void main(String[] args) throws Exception {
        String sql = (String) new FileToStringConvertor().process("c:/SQL.txt");
        SQLConvertor convertor = new SQLConvertor(sql);
        System.out.println(convertor.getsSQL());
        for (String name : convertor.getColNames()) {
            System.out.println(name);
        }
    }
    
    public SQLConvertor() {
    }

    public SQLConvertor(String source) throws Exception {
        this(source, "?");
    }

    public SQLConvertor(String source, String separator) throws Exception {
        this.separator = separator;
        convert(source);
    }

    public void convert(String source) throws Exception {
        int pos = -1;
        source = source + "@@@";
        int m = source.split("\\" + separator).length - 1;
        colNames = new String[m];
        StringBuilder sb = new StringBuilder();
        StringBuilder newSQL = new StringBuilder(source);
        StringBuilder oldSQL = new StringBuilder(source);
        for (int i = 0; i < m; i++) {
            if (sb.length() > 0) {
                sb.delete(0, sb.length());
            }

            pos = oldSQL.indexOf(separator, pos + 1);

            //取?号列名
            int index = pos - 1;
            while (oldSQL.charAt(index) != ',' && oldSQL.charAt(index) != '('
                    && oldSQL.charAt(index) != '=' && oldSQL.charAt(index) != '*'
                    && oldSQL.charAt(index) != ' ') {
                sb.insert(0, oldSQL.charAt(index));
                index--;
            }

            if (sb.length() == 0) {
                throw new RuntimeException("SQL statement syntax error: " + oldSQL.substring(0, pos + 1));
            }

            //替换
            colNames[i] = sb.toString().trim();
            int j = newSQL.indexOf(sb.toString() + separator);
            newSQL.delete(j, j + sb.length() + separator.length());
            newSQL.insert(j, "?");
        }
        sSQL = newSQL.toString().replace("@@@", "");
    }

    public String format(String sSQL, Map values) throws Exception {
        if (values != null) {
            for (Iterator it = values.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                Object value = values.get(key);
                if (value != null) {
                    if ("java.lang.String".equals(value.getClass().getName())) {
                        String strval = ((String) value).toUpperCase();
                        if (FinalValue.TIME01.equals(strval)
                                || FinalValue.DateTime.equals(strval)
                                || FinalValue.Date.equals(strval)
                                || -1 != strval.indexOf("TO_DATE(TO_CHAR(SYSDATE")) {
                            String oldValue = key + separator;
                            sSQL = sSQL.replace(oldValue, (String) value);
                        }
                    }
                }
            }
        }
        return sSQL;
    }

    public String[] getColNames() {
        return colNames;
    }

    public String getsSQL() {
        return sSQL;
    }
}
