package com.jlsoft.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaDoc {

    private static String url = "jdbc:oracle:thin:@119.79.224.115:9010:WEB";
    private static String user = "V9SCM";
    private static String password = "v9user";
    private static String TABLE_NAME = "RKDITEM";

    public static void main(String[] args) throws Exception {
        String s = " ";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(url, user, password);
        Statement stmt = con.createStatement();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT A.COLUMN_NAME, DATA_TYPE,DATA_LENGTH, A.DATA_PRECISION, A.DATA_SCALE,A.NULLABLE,A.DATA_DEFAULT,B.comments");
        sb.append("  FROM all_tab_columns A,DBA_COL_COMMENTS B");
        sb.append(" WHERE A.owner=B.owner");
        sb.append("   AND A.table_name=B.table_name");
        sb.append("   AND A.COLUMN_NAME=B.COLUMN_NAME");
        sb.append("   AND A.table_name = '").append(TABLE_NAME).append("'");   //  AND A.owner='inmscm'  owner是建立表的用户名
        sb.append(" ORDER BY A.TABLE_NAME");
        ResultSet rs = stmt.executeQuery(sb.toString());

        int i = 0;
        sb = new StringBuilder();
        sb.append("<pre>    ").append("字段名称      字段类型    字段长度  允许为空  默认值                                          字段描述                                         ").append("</pre>").append("\r\n");
        while (rs.next()) {
            sb.append("<pre>    ");
            sb.append(padding(rs.getString("COLUMN_NAME"), s, 15));  //字段名称
            sb.append(padding(rs.getString("DATA_TYPE"), s, 12));  //字段类型

            String type = rs.getString("DATA_TYPE");
            if (!type.equals("NUMBER") && !type.equals("DATE")) {  //字段长度
                sb.append(padding(rs.getString("DATA_LENGTH"), s, 12));
            } else if (type.equals("DATE")) {
                sb.append(padding("", s, 12));
            } else {
                String scale = rs.getString("DATA_SCALE");
                if ("null".equals(scale) || "0".equals(scale)) {
                    scale = "";
                } else {
                    scale = "," + scale;
                }
                sb.append(padding(rs.getString("DATA_PRECISION") + scale, s, 12));
            }
            sb.append(padding(rs.getString("NULLABLE"), s, 10));  //允许为空

            String value = rs.getString("DATA_DEFAULT");
            if (JLTools.isEmpty(value)) {
                value = "";
            }
            sb.append(padding(value, s, 45));  //默认值

            String commenis = rs.getString("COMMENTS");
            if (JLTools.isEmpty(commenis)) {
                commenis = "";
            }
            sb.append(commenis);  //字段描述
//            if (type.equals("DATE")) {
//                sb.append("（日期格式：‘YYYYMMDDHH’ 或 ‘YYYYMMDDHH24MISS’）");
//            }
            sb.append("   </pre>").append("\r\n");
            i++;
        }

        System.out.println(sb.toString());
        System.out.println(i);
    }

    /**
     *
     * @param str 要左填充的字符串
     * @param charc 要填充的字符
     * @param len 填充后字符串的长度(总长度)
     * @return 填充后的字符串
     */
    public static String padding(String str, String charc, int len) {
        if (str.length() >= len) {
            return str;
        } else {
            len = len - str.length();
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < len; i++) {
            sb.append(charc);
        }
        return sb.toString();
    }
}
