package com.jlsoft.framework.dataset;

import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.config.Config;
import com.jlsoft.utils.config.Field;
import com.jlsoft.utils.config.JKConfig;
import java.util.*;

public class XmlDataSetWrapper implements IDataSetWrapper {

    private List rows;
    private String configKey = null;
    private boolean smallXml = false;
    private final String Type = "XML";

    public XmlDataSetWrapper(List rows, String configKey) {
        this(rows, configKey, false);
    }

    public XmlDataSetWrapper(List rows, String configKey, boolean smallXml) {
        this.rows = rows;
        this.configKey = configKey;
        this.smallXml = smallXml;
    }

    @Override
    public String convert() {
        if (!smallXml) {
            return toXml();
        } else {
            return toSmallXml();
        }
    }

    private Object toCdsDateTimeStr(Object obj) {
        if (obj != null) {
            if ("java.sql.Timestamp".equals(obj.getClass().getName())
                    || "java.sql.Date".equals(obj.getClass().getName())) {
                String sFieldData = String.valueOf(obj);
                sFieldData = sFieldData.trim().replace(' ', 'T');
                obj = sFieldData;
            }
        }
        return obj;
    }

    private String toXml() {
        StringBuilder sb = new StringBuilder();
        sb.append(xmlHeader());
        for (int i = 0; i < rows.size(); i++) {
            sb.append("<ROW");
            Map map = (Map) rows.get(i);
            Set<Map.Entry> params = map.entrySet();
            for (Map.Entry entry : params) {
                sb.append(" ");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append("\"");
                sb.append(JLTools.getXmlString(String.valueOf(toCdsDateTimeStr(entry.getValue()))));
                sb.append("\"");
            }
            sb.append("/>");
        }
        sb.append(xmlFooter());
        return sb.toString();
    }

    private String toSmallXml() {
        StringBuilder sb = new StringBuilder();
        sb.append(xmlHeader());
        for (int i = 0; i < rows.size(); i++) {
            sb.append("<ROW");
            Map map = (Map) rows.get(i);
            Set<Map.Entry> params = map.entrySet();
            for (Map.Entry entry : params) {
                if (null == entry || null == entry.getValue()) {
                    continue;
                }
                if ("java.math.BigDecimal".equals(entry.getValue().getClass().getName())) {
                    if ("0".equals(String.valueOf(entry.getValue()))) {
                        continue;
                    }
                }
                if ("java.lang.Integer".equals(entry.getValue().getClass().getName())) {
                    if ("0".equals(String.valueOf(entry.getValue()))) {
                        continue;
                    }
                }
                if ("java.lang.Float".equals(entry.getValue().getClass().getName())) {
                    if ("0.0".equals(String.valueOf(entry.getValue()))) {
                        continue;
                    }
                }
                if ("java.lang.Double".equals(entry.getValue().getClass().getName())) {
                    if ("0.0".equals(String.valueOf(entry.getValue()))) {
                        continue;
                    }
                }
                if ("java.lang.String".equals(entry.getValue().getClass().getName())) {
                    if ("".equals(String.valueOf(entry.getValue()))) {
                        continue;
                    }
                }
                sb.append(" ");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append("\"");
                sb.append(JLTools.getXmlString(String.valueOf(toCdsDateTimeStr(entry.getValue()))));
                sb.append("\"");
            }
            sb.append("/>");
        }
        sb.append(xmlFooter());
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

    private static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    private String getColumns() {
        if (configKey == null) {
            try {
                throw new Exception("Null configKey not permitted.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        StringBuilder result = new StringBuilder();
        Config config = JKConfig.getConfig(configKey);
        List fields = config.getFields();
        Field field;

        for (Iterator<Field> it = fields.iterator(); it.hasNext();) {
            field = it.next();
            result.append("<FIELD attrname=").append("\"").append(JLTools.getXmlString(field.getAttrname())).append("\"").append(" fieldtype=").append("\"").append(JLTools.getXmlString(field.getFieldtype())).append("\"");
            if (!isEmpty(field.getWidth())) {
                result.append(" width=").append("\"").append(JLTools.getXmlString(field.getWidth())).append("\"");
            }
            if (!isEmpty(field.getDecimals())) {
                result.append(" decimals=").append("\"").append(JLTools.getXmlString(field.getDecimals())).append("\"");
            }
            result.append("/>");
        }
        result.append("<FIELD attrname=").append("\"").append("JLID").append("\"").append(" fieldtype=").append("\"").append("i4").append("\"").append("/>");
        return result.toString();
    }

    @Override
    public String getType() {
        return Type;
    }
}
