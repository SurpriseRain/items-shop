package com.jlsoft.framework.pi.connector.jdbc.writer.sqlHelper;

public class NormalSQLHelper extends AbstractSQLHelper {

    private String separator = "?";

    public NormalSQLHelper(String source, String separator) throws Exception {
        this.separator = separator;
        analysis(source);
    }

    public NormalSQLHelper(String source) throws Exception {
        this(source, "?");
    }

    private void analysis(String source) {
        int pos = -1;
        source = source + "@";
        int m = source.split("\\" + separator).length - 1;
        outputColumns = new String[m];
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
            outputColumns[i] = sb.toString().trim();
            int j = newSQL.indexOf(sb.toString() + separator);
            newSQL.delete(j, j + sb.length() + separator.length());
            newSQL.insert(j, "?");
        }
        sql = newSQL.toString().replace("@", "");
    }
}
