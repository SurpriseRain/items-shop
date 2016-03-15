package com.jlsoft.framework.pi.convertor.dataset;

import com.jlsoft.framework.pi.api.AbstractConvertor;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.config.Config;
import com.jlsoft.utils.config.Field;
import com.jlsoft.utils.config.JKConfig;
import java.util.*;

public class XmlDataSetConvertor extends AbstractConvertor {

    private String configKey = null;

    public XmlDataSetConvertor(String configKey) {
        this.configKey = configKey;
    }

    @Override
    protected Object convert(Object record) throws Exception {
        List rows;
        if (configKey == null) {
            throw new Exception("Null configKey not permitted.");
        }
        if (record instanceof List) {
            rows = (List) record;
        } else if (record instanceof Map) {
            rows = new ArrayList();
            rows.add(record);
        } else {
            throw new RuntimeException("record element Expected. Got - " + record.getClass());
        }
        return doConvert(rows);
    }

    private String doConvert(List rows) {
        StringBuilder sb = new StringBuilder();
        sb.append(xmlHeader());
        sb.append(xmlBody(rows));
        sb.append(xmlFooter());
        return sb.toString();
    }

    private String xmlBody(List rows) {
        return xmlBody(rows, "");
    }

    private String xmlBody(List rows, String suffix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows.size(); i++) {
            sb.append("<ROW").append(suffix);
            Map row = (Map) rows.get(i);
            Set<Map.Entry> params = row.entrySet();
            for (Map.Entry entry : params) {
                if (!(entry.getValue() instanceof List)) {
                    sb.append(" ").append(entry.getKey()).append("=").append("\"").append(JLTools.getXmlString(String.valueOf(toCdsDateTimeStr(entry.getValue())))).append("\"");
                }
            }
            sb.append(">");

            for (Map.Entry entry : params) {
                if (entry.getValue() instanceof List) {
                    sb.append("<").append(entry.getKey()).append(">");
                    List values = (List) entry.getValue();
                    sb.append(xmlBody(values, (String) entry.getKey()));
                    sb.append("</").append(entry.getKey()).append(">");
                }
            }
            sb.append("</ROW").append(suffix).append(">");
        }
        return sb.toString();
    }

    private String xmlHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><DATAPACKET><METADATA><FIELDS>").append(getColumns()).append("</FIELDS></METADATA><ROWDATA>");
        return sb.toString();
    }

    private String xmlFooter() {
        StringBuilder sb = new StringBuilder();
        sb.append("</ROWDATA></DATAPACKET>");
        return sb.toString();
    }

    private String getColumns() {
        if (configKey == null) {
            try {
                throw new Exception("Null configKey not permitted.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Config config = JKConfig.getConfig(configKey);
        return getColumns(config);
    }

    private String getColumns(Config config) {
        StringBuilder result = new StringBuilder();
        List fields = config.getFields();
        Field field;
        Object o;
        for (Iterator it = fields.iterator(); it.hasNext();) {
            o = it.next();
            if (o instanceof Config) {
                result.append("<FIELD attrname=").append("\"").append(((Config) o).getId()).append("\"").append(" fieldtype=").append("\"").append("nested").append("\"").append(">");
                result.append("<FIELDS>");
                result.append(getColumns((Config) o));
                result.append("</FIELDS>");
                result.append("</FIELD>");
            } else if (o instanceof Field) {
                field = (Field) o;
                result.append("<FIELD attrname=").append("\"").append(JLTools.getXmlString(field.getAttrname())).append("\"").append(" fieldtype=").append("\"").append(JLTools.getXmlString(field.getFieldtype())).append("\"");
                if (!isEmpty(field.getWidth())) {
                    result.append(" width=").append("\"").append(JLTools.getXmlString(field.getWidth())).append("\"");
                }
                if (!isEmpty(field.getDecimals())) {
                    result.append(" decimals=").append("\"").append(JLTools.getXmlString(field.getDecimals())).append("\"");
                }
                result.append("/>");
            }
        }
        return result.toString();
    }

    private Object toCdsDateTimeStr(Object obj) {
        if (obj != null) {
            if ("java.sql.Timestamp".equals(obj.getClass().getName()) || "java.sql.Date".equals(obj.getClass().getName())) {
                String sFieldData = String.valueOf(obj);
                sFieldData = sFieldData.trim().replace(' ', 'T');
                obj = sFieldData;
            }
        }
        return obj;
    }

    private static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }
}
