package com.jlsoft.framework.dataset;

import com.jlsoft.framework.pi.convertor.file.FileToStringConvertor;
import com.jlsoft.utils.JLDefsys;
import com.jlsoft.utils.JLTools;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.StringReader;
import java.util.Map.Entry;
import java.util.*;

public class XmlDataSet extends DefaultHandler implements IDataSet {

    public static void main(String[] args) throws Exception {
        XmlDataSet ds = new XmlDataSet((String) new FileToStringConvertor().process("C:/Users/Administrator/Desktop/JLSCMV9WLList.xml"));
//        String[] keys = {"CXGZ", "CXGZTJ", "CXGZMX"};
//        int[] indexes = {0, 0, 1};
//        System.out.println(ds.getTableRows(keys, indexes));
//        ds.print();

        int[] s = {0, 0, 1};
        int[] d = {0, 0, 1};
        ds.compare(s, d);
    }

    public void print() throws Exception {
        System.out.println("fieldValues : " + fieldValues);
        System.out.println("table_size : " + table_size);
        System.out.println("row : " + row);
        System.out.println("index_size: " + index_size);
    }
    private String tablename = null;
    private String parentTblname = null;
    private int table_sl = 0;
    private Map fieldTypes = null;
    private Map fieldValues = null;
    private Map table_size;
    private StringBuffer row = null;
    private Map index_size = null;
    private StringBuffer valuekey = new StringBuffer();
    private StringBuffer field = new StringBuffer();
    private StringBuffer sb = new StringBuffer();
    private final Map mapType = new HashMap() {

        {
            put("string", new Integer(0));
            put("dateTime", new Integer(1));
            put("i4", new Integer(2));
            put("r8", new Integer(3));
        }
    };

    public XmlDataSet() {
        fieldTypes = new HashMap();
        fieldValues = new HashMap();
        table_size = new HashMap();
        row = new StringBuffer();
        index_size = new HashMap();
    }

    public XmlDataSet(String XmlData) throws Exception {
        this();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();

        if (JLDefsys.CHECK_HALF_WORD.equals("1")) {
            StringBuffer sb_temp = new StringBuffer(XmlData);
            while (sb_temp.indexOf("?/") != -1) {
                int i = sb_temp.indexOf("?/");
                sb_temp.insert(sb_temp.indexOf("?/") + 1, "\"");
                sb_temp.replace(i, i + 1, " ");
            }
            while (sb_temp.indexOf("? ") != -1) {
                int i = sb_temp.indexOf("? ");
                sb_temp.insert(sb_temp.indexOf("? ") + 1, "\"");
                sb_temp.replace(i, i + 1, " ");
            }
            XmlData = sb_temp.toString();
            sb_temp.delete(0, sb_temp.length());
            sb_temp = null;
        }
        parser.parse(new InputSource(new StringReader(XmlData)), this);
    }

    public void init(String xml, DefaultHandler handler) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();
        parser.parse(new InputSource(new StringReader(xml)), handler);
    }

    @Override
    public void startElement(String namespaceUri, String ename, String qname, Attributes attributes) throws SAXException {
        if (ename.equals("FIELD")) {
            String filedtype = attributes.getValue("fieldtype");
            if (filedtype.equals("nested")) {
                tablename = attributes.getValue("attrname");
                table_sl++;
                if (table_sl == 1) {
                    parentTblname = tablename;
                }
                return;
            }
            String key = JLTools.isEmpty(tablename) ? attributes.getValue("attrname") : (tablename + "." + attributes.getValue("attrname"));
            if ("fixed".equals(filedtype)) {
                fieldTypes.put(key, new Integer(((JLTools.isEmpty(attributes.getValue("DECIMALS")) && JLTools.isEmpty(attributes.getValue("decimals"))) ? 2 : 3)));
            } else {
                fieldTypes.put(key, mapType.get(filedtype));
            }
        } else if (ename.startsWith("ROW") && !ename.equals("ROWDATA")) {
            if (table_size.containsKey(ename)) {
                Integer size = (Integer) table_size.get(ename);
                table_size.put(ename, new Integer(size.intValue() + 1));
            } else {
                table_size.put(ename, new Integer(1));
            }
            for (int i = 0; i < attributes.getLength(); i++) {
                fieldValues.put(ename + "." + table_size.get(ename) + "." + attributes.getLocalName(i), attributes.getValue(i));
            }
            row.append("<").append(ename).append(">");
        }
    }

    @Override
    public void endElement(String namespaceUri, String ename, String qname) throws SAXException {
        if (ename.startsWith("ROW") && !ename.equals("ROWDATA")) {
            row.append("</").append(ename).append(">");
        }
    }

    @Override
    public int getTableRows(String tblname) throws Exception {
        return table_size.containsKey("ROW") ? ((Integer) table_size.get("ROW")).intValue() : 0;
    }

    @Override
    public int getTableRows(String tblname, int i) throws Exception {
        int size = 0;
        if (index_size.containsKey(tblname + "." + i)) {
            size = ((Integer) index_size.get(tblname + "." + i)).intValue();
        } else {
            String arrrow[] = row.toString().split("</ROW>");

            if (arrrow.length > i) {
                size = getSize(arrrow[i], tblname);
            }
            index_size.put(tblname + "." + i, new Integer(size));
        }
        return size;
    }

    /*
     * private static String getParentTblname(String tblname,String arrrow){ int
     * size = arrrow.lastIndexOf("</ROW"+tblname+">")+
     * ("</ROW"+tblname+"></ROW").length(); return
     * arrrow.substring(size,arrrow.indexOf(">",size)); }
     */
    @Override
    public int getTableRows(String tblname, int i, int j) throws Exception {
//        String[] felnames = tblname.split("\\.");
//        if (felnames.length != 2) {
//            throw new Exception("节点名称输入错误.");
//        }      
        String[] tables = tblname.split("\\.");
        tblname = tables[tables.length - 1];
        int size = 0;
        if (index_size.containsKey(tblname + "." + i + "." + j)) {
            size = ((Integer) index_size.get(tblname + "." + i + "." + j)).intValue();
        } else {
            String arrrow = row.toString().split("</ROW>")[i];
            if (arrrow.indexOf("</ROW" + tblname + ">") >= 0) {
                String parTbl = tables[0]; //取第二级表名
                String jarr[] = arrrow.split("</ROW" + parTbl + ">");
                if (jarr.length > j) {
                    size = getSize(jarr[j], tblname);
                }
            }
            index_size.put(tblname + "." + i + "." + j, new Integer(size));
        }
        return size;
    }

    private int multiple(int digit) {
        int result = 1;
        for (int i = 0; i < digit - 1; i++) {
            result *= 10;
        }
        return result;
    }

    private Boolean compare(int[] src, int[] dest) {
        int lsrc = 0;
        int ldest = 0;
        for (int i = 0; i < src.length; i++) {
            lsrc += src[i] * multiple(src.length - i);
        }
        for (int i = 0; i < dest.length; i++) {
            ldest += dest[i] * multiple(dest.length - i);
        }
        return lsrc >= ldest;
    }

    private int getSize(String arr, String tblname) {
        if (arr.indexOf("</ROW" + tblname + ">") >= 0) {
            String tmparr[] = arr.split("</ROW" + tblname + ">");
            if (tmparr[tmparr.length - 1].indexOf("<ROW" + tblname + ">") >= 0) {
                return tmparr.length;
            } else {
                return (tmparr.length - 1);
            }
        } else {
            return 0;
        }
    }

    private void clear() {
        if (this.sb.length() > 0) {
            sb.delete(0, sb.length());
        }
        if (this.field.length() > 0) {
            field.delete(0, field.length());
        }
        if (this.valuekey.length() > 0) {
            valuekey.delete(0, valuekey.length());
        }
    }

    private int checkType(Map all_types, String field, String tblname) {
        /*
         * 如果没有METADATA节点，默认为String
         */
        if (all_types.isEmpty()) {
            return 0;
        }
        /*
         * 按fieldtype节点类型返回相应flag标志
         */
        if (!JLTools.isEmpty(tblname)) {
            tblname = tblname + ".";
        }
        Integer i = (Integer) all_types.get(tblname + field);
        return (i == null) ? 0 : i.intValue();
    }

    @Override
    public String getField(String felname, int i) throws Exception {
        String[] felnames = felname.split("\\.");
        if (felnames.length != 1) {
            throw new Exception("节点名称输入错误.");
        }
        clear();
        valuekey.append("ROW");
        valuekey.append(".").append((i + 1)).append(".").append(felname.substring(felname.indexOf(46) + 1, felname.length()));
        if (fieldValues.containsKey(valuekey.toString())) {
            field.append(fieldValues.get(valuekey.toString()));
        }
        return (JLTools.isNull(field.toString()) ? "null" : field.toString());
    }

    private int getFieldSize(String tblname, int i, int j) throws Exception {
        String[] tables = tblname.split("\\.");
        tblname = tables[tables.length - 1];

        int size = 0;
        for (int x = 0; x < i; x++) {
            if (index_size.containsKey(tblname + "." + x)) {
                Integer tsize = (Integer) index_size.get(tblname + "." + x);
                size += tsize.intValue();
            } else {
                int tmpsize = getTableRows(tblname, x);
                index_size.put(tblname + "." + x, new Integer(tmpsize));
                size += tmpsize;
            }
        }
        size += j;
        return size;
    }

    @Override
    public String getField(String felname, int i, int j) throws Exception {
//        if (felname.indexOf(".") > 0) {

        String arr[] = felname.split("\\.");
        if (arr.length != 2) {
            throw new Exception("节点名称输入错误.");
        }
        clear();
        valuekey.append("ROW");
        valuekey.append(arr[0]);
        int size = getFieldSize(arr[0], i, j);
        valuekey.append(".").append((size + 1)).append(".").append(arr[1]);
        if (fieldValues.containsKey(valuekey.toString())) {
            field.append(fieldValues.get(valuekey.toString()));
        }
//        } else {
//            return "null";
//        }

        return (JLTools.isNull(field.toString()) ? "null" : field.toString());
    }

    private int getFieldSize(String tblname, int i, int j, int k) {
        String[] tables = tblname.split("\\.");
        tblname = tables[tables.length - 1];

        int size = 0;
        Map.Entry entry = null;
        String tmpkey = null;
        for (Iterator it = index_size.entrySet().iterator(); it.hasNext();) {
            entry = (Entry) it.next();
            tmpkey = entry.getKey().toString();
            if (!JLTools.isEmpty(tmpkey) && tmpkey.startsWith(tblname)) {
                String key[] = tmpkey.split("\\.");
                if (key.length == 3 && key[0].equals(tblname)) {
                    if (Integer.parseInt(key[1]) < i) {
                        size += Integer.parseInt(entry.getValue().toString());
                    } else if (Integer.parseInt(key[1]) == i && Integer.parseInt(key[2]) < j) {
                        size += Integer.parseInt(entry.getValue().toString());
                    }
                }
            }
        }
        size += k;
        return size;
    }

    @Override
    public String getField(String felname, int i, int j, int k) throws Exception {
        //if (felname.indexOf(".") > 0) {
        String[] arr = felname.split("\\.");
        if (arr.length != 3) {
            throw new Exception("节点名称输入错误.");
        }
        clear();
        valuekey.append("ROW");
        String tblname = arr[1];
        valuekey.append(tblname);
        int size = getFieldSize(tblname, i, j, k);
        valuekey.append(".").append((size + 1)).append(".").append(arr[2]);
        if (fieldValues.containsKey(valuekey.toString())) {
            field.append(fieldValues.get(valuekey.toString()));
        }
//        } else {
//            return "null";
//        }
        return (JLTools.isNull(field.toString()) ? "null" : field.toString());
    }

    @Override
    public Map[] getRows(RowSetter rowProcessor) throws Exception { //取cds第一级数据 
        String tblname = rowProcessor.getNodeName();
        tblname = JLTools.isEmpty(tblname) ? "" : tblname;
        int size = getTableRows(tblname);
        int index;
        Map[] rows = new Map[size];
        for (int i = 0; i < size; i++) {
            index = i;
            rows[i] = getRow(rowProcessor, index);
        }
        return rows;
    }

    @Override
    public Map[] getRows(RowSetter rowSetter, int i) throws Exception { //取cds第二级数据       
        String tblname = rowSetter.getNodeName();
        tblname = JLTools.isEmpty(tblname) ? "" : tblname;
        int size = getTableRows(tblname, i);
        int index;
        Map[] rows = new Map[size];
        for (int j = 0; j < size; j++) {
            index = getFieldSize(tblname, i, j);
            rows[j] = getRow(rowSetter, index);
        }
        return rows;
    }

    @Override
    public Map[] getRows(RowSetter rowSetter, int i, int j) throws Exception { //取cds第三级数据 
        String tblname = rowSetter.getNodeName();
        tblname = JLTools.isEmpty(tblname) ? "" : tblname;

        String[] tables = tblname.split("\\.");
        tblname = tables[tables.length - 1];

        int size = getTableRows(rowSetter.getNodeName(), i, j);
        int index;
        Map[] rows = new Map[size];
        for (int m = 0; m < size; m++) {
            index = getFieldSize(rowSetter.getNodeName(), i, j, m);
            rows[m] = getRow(rowSetter, index);
        }
        return rows;
    }
    
    
    private int getIndex(String[] keys, int[] indexes) {
        String tblname = keys[keys.length - 1];
        int size = 0;
        Map.Entry entry = null;
        String tmpkey = null;
        for (Iterator it = index_size.entrySet().iterator(); it.hasNext();) {
            entry = (Entry) it.next();
            tmpkey = entry.getKey().toString();
            if (!JLTools.isEmpty(tmpkey) && tmpkey.startsWith(tblname)) {
                String key[] = tmpkey.split("\\.");
                if ((key.length == (indexes.length + 1)) && (key[0].equals(tblname))) {
                    int[] b = new int[key.length - 1];
                    for (int i = 1; i < key.length; i++) {
                        b[i - 1] = Integer.parseInt(key[i]);
                    }
                    if (!compare(b, indexes)) {
                        size += Integer.parseInt(entry.getValue().toString());
                    }
                }
            }
        }
        return size;
    }

    private int getTableRows(String[] keys, int[] indexes) throws Exception {
        if (keys.length != (indexes.length)) {
            throw new Exception("节点名称输入错误.");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            if (i == 0) {
                sb.append(keys[keys.length - 1]);
            }
            sb.append(".").append(indexes[i]);
        }
        String key = sb.toString();

        int size = 0;
        if (index_size.containsKey(key)) {
            size = ((Integer) index_size.get(key)).intValue();
        } else {
            String regex;
            String arrrow = null;
            for (int i = 0; i < indexes.length; i++) {
                if (i == 0) {
                    regex = "</ROW>";
                    arrrow = row.toString().split(regex)[indexes[i]];
                }

                if (i < indexes.length - 1) {
                    regex = "</ROW" + keys[i] + ">";
                    if (arrrow.indexOf(regex) >= 0) {
                        String[] arr = arrrow.split(regex);
                        if (arr.length > indexes[i + 1]) {
                            arrrow = arr[indexes[i + 1]];
                        } else {
                            return size;
                        }
                    }
                }

                if (i == indexes.length - 1) {
                    size = getSize(arrrow, keys[i]);
                }
            }
            index_size.put(key, new Integer(size));
        }
        return size;
    }    

    @Override
    public Map[] getRows(RowSetter rowSetter, int[] indexes) throws Exception { //取cds多级（三级以上）数据 
        String tblname = rowSetter.getNodeName();
        tblname = JLTools.isEmpty(tblname) ? "" : tblname;

        String[] tables = tblname.split("\\.");
        tblname = tables[tables.length - 1];

        int size = getTableRows(tables, indexes);
        int index = getIndex(tables, indexes);
        Map[] rows = new Map[size];
        for (int m = 0; m < size; m++) {
            index += m;
            rows[m] = getRow(rowSetter, index);
        }
        return rows;
    }

    private Map getRow(RowSetter rowSetter, int index) throws Exception {
        String tblname = rowSetter.getNodeName();
        tblname = JLTools.isEmpty(tblname) ? "" : tblname;
        String[] tables = tblname.split("\\.");
        tblname = tables[tables.length - 1];

        String[] columns = rowSetter.getColumns();
        Map row = getRow(tblname, columns, index);
        rowSetter.setValue(row);
        return row;
    }

    private Map getRow(String tblname, String[] columns, int index) throws Exception {
        String column;
        Map row = new HashMap();
        for (int i = 0; i < columns.length; i++) {
            clear();

            /*
             * 取列名
             */
            column = columns[i].trim();

            /*
             * 根据列名取节点的属性值
             */
            valuekey.append("ROW");
            valuekey.append(tblname).append(".").append((index + 1)).append(".").append(column);

            if (fieldValues.containsKey(valuekey.toString())) {
                field.append(fieldValues.get(valuekey.toString()));
            }

            /*
             * 根据不同类型赋值
             */
            switch (checkType(fieldTypes, column, tblname)) {
                case 0:
                    row.put(column, field.toString());
                    break;
                case 1:
                    if (field.length() == 0) {
                        row.put(column, String.valueOf(""));
                    } else {
                        if (field.length() < 11) {
                            row.put(column, JLTools.parseDate(field.toString()));
                        } else {
                            row.put(column, JLTools.parseTimestamp(field.toString()));
                        }
                    }
                    break;

                case 2:
                    if (field.length() == 0) {
                        row.put(column, 0);
                    } else {
                        row.put(column, Long.parseLong(field.toString()));
                    }
                    break;
                case 3:
                    if (field.length() == 0) {
                        row.put(column, 0);
                    } else {
                        row.put(column, Double.parseDouble(field.toString()));
                    }
                    break;
            }
        }
        return row;
    }
}
