package com.jlsoft.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBColumns {

    static String url = "jdbc:oracle:thin:@119.79.224.115:9010:WEB";
    static String user = "V9CSSCM";
    static String password = "AAAA";
    static String sSQL = "SELECT A.FPDC01, A.SKI01, A.FPDCI02, A.FPDCI03, A.FPDCI04, A.FPDCI05,	A.FPDCI06, A.FPDCI07, A.FPDCI08, A.FPDCI09, A.FPDCI10, A.FPDCI11,	A.SPXX04, A.JLDW01, A.GGB01	FROM FPDCITEM A	WHERE 1 = 1";

    private static String readerFile() throws IOException {
        StringBuilder sBuf = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("c:/SQL.txt"));
        String data = br.readLine();//一次读入一行，直到读入null为文件结束  
        while (data != null) {
            sBuf.append(data).append(" ");
            data = br.readLine(); //接着读下一行  
        }
        return sBuf.toString();
    }

    public static void main(String[] args) throws IOException {
        getFields();
    }

    public static void getFields() throws IOException {
        if (sSQL.length() == 0) {
            sSQL = readerFile();
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sSQL + " and 1 <> 1");

            ResultSetMetaData rsmd = rs.getMetaData();
            StringBuilder sBuf = new StringBuilder();

            //得到数据集列数
            int i;
            String sFieldType = "";
            int iFieldCount = rsmd.getColumnCount();
            // 从纪录第一列循环到最后一列
            for (i = 1; i <= iFieldCount; i++) {
                // 填写列名
                sBuf.append("<FIELD attrname=").append(JLTools.getQuot(rsmd.getColumnLabel(i))).append(" ");
                // 得到列数据类型
                sFieldType = rsmd.getColumnTypeName(i).toLowerCase();
                if (sFieldType.equals("varchar2")) {
                    sBuf.append(" fieldtype=\"string\" ");
                    sBuf.append(" width=").append(JLTools.getQuot(Integer.toString(rsmd.getPrecision(i))));
                } // 此列是NUMBER类型
                else if (sFieldType.equals("number")) {
                    //sBuf.append(" fieldtype=\"r8\" ");
                    if ((rsmd.getPrecision(i) > 0 && rsmd.getPrecision(i) <= 8
                            && rsmd.getScale(i) == 0)) {
                        sBuf.append(" fieldtype=\"i4\" ");
                    } else if ((rsmd.getPrecision(i) == 0 && rsmd.getScale(i) == 0)) {
                        sBuf.append(" fieldtype=\"fixed\" ");
                        sBuf.append(" decimals=\"4\" ");
                        sBuf.append(" width=\"20\" ");
                    } else if (rsmd.getScale(i) < 0) {
                        sBuf.append(" fieldtype=\"r8\" ");
                    } else {
                        sBuf.append(" fieldtype=\"fixed\" ");
                        if (rsmd.getScale(i) != 0) {
                            sBuf.append(" decimals=\"").append(rsmd.getScale(i)).append("\" ");
                        }
                        sBuf.append(" width=\"").append(rsmd.getPrecision(i)).append("\" ");
                    }
                } // 此列是DATETIME类型
                else if (sFieldType.equals("date")) {
                    sBuf.append(" fieldtype=\"dateTime\" ");
                } // 此列是FLOAT类型
                else if (sFieldType.equals("float")) {
                    sBuf.append(" fieldtype=\"r8\" ");
                } // 此列是CHAR类型
                else if (sFieldType.equals("char")) {
                    sBuf.append(" fieldtype=\"string\" ");
                    sBuf.append(" width=").append(JLTools.getQuot(Integer.toString(rsmd.getPrecision(i))));
                } // 此列是CLOB类型
                else if (sFieldType.equals("clob")) {
                    sBuf.append(" fieldtype=\"string\" ");
                    sBuf.append(" width=").append(JLTools.getQuot(JLTools.intToStr(4000)));
                } // 此列是INT类型
                else if (sFieldType.equals("int")) {
                    sBuf.append(" fieldtype=\"i4\" ");
                } // 此列是NUMERIC类型
                else if (sFieldType.equals("numeric")) {
                    if ((rsmd.getPrecision(i) > 0 && rsmd.getPrecision(i) <= 8
                            && rsmd.getScale(i) == 0)) {
                        sBuf.append(" fieldtype=\"i4\" ");
                    } else if ((rsmd.getPrecision(i) == 0 && rsmd.getScale(i) == 0)) {
                        sBuf.append(" fieldtype=\"fixed\" ");
                        sBuf.append(" decimals=\"2\" ");
                        sBuf.append(" width=\"8\" ");
                    } else if (rsmd.getScale(i) < 0) {
                        sBuf.append(" fieldtype=\"r8\" ");
                    } else {
                        sBuf.append(" fieldtype=\"fixed\" ");
                        if (rsmd.getScale(i) != 0) {
                            sBuf.append(" decimals=\"").append(rsmd.getScale(i)).append("\" ");
                        }
                        sBuf.append(" width=\"").append(rsmd.getPrecision(i)).append("\" ");
                    }
                }
                // 此列是varchar类型
                sFieldType = rsmd.getColumnTypeName(i).toLowerCase();
                if (sFieldType.equals("varchar")) {
                    sBuf.append(" fieldtype=\"string\" ");
                    sBuf.append(" width=").append(JLTools.getQuot(Integer.toString(rsmd.getPrecision(i))));
                }

                //sBuf.append(" required=\"false\"/>");
                sBuf.append("/>").append("\r");
            }

            System.out.println(sBuf.toString());
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
