package com.jlsoft.utils.config;

import com.jlsoft.framework.pi.api.IConvertor;
import com.jlsoft.framework.pi.convertor.map.MapMappingConvertor;
import com.jlsoft.framework.pi.convertor.map.MapToJsonConvertor;
import com.jlsoft.framework.pi.processor.log.LogProcessor;
import com.jlsoft.utils.JlAppResources;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.beanutils.PropertyUtils;

public class JKConfig extends DefaultHandler {

    private static Map configs = new HashMap();
    private List fields = null;
    private IConvertor convertor = null;
    private Config nested = null;

    public static void main(String[] args) throws Exception {
        JKConfig jkConfig = new JKConfig();
        //jkConfig.testConvertor(); 
        //jkConfig.testChild();
        jkConfig.testJa();
    }

    private void testChild() throws Exception {
        readConfigFile("cdsSample.xml");
        Config config = JKConfig.getConfig("DHD");

        Map row = new HashMap();

        List fields = config.getFields();

//        for (Iterator<Field> it = fields.iterator(); it.hasNext();) {
//        }
        System.out.println(row);
    }

    private void testJa() throws Exception {
        init();

        Map row = new HashMap();

        row.put("AREAFLAG", "shanghai");
        row.put("TIMES", new Date());

        new MapMappingConvertor("JIA.TGXX_Request").setProcessor(new MapToJsonConvertor()).setProcessor(new LogProcessor()).process(row);
    }

    public void readConfigFile(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(Resources.getResourceAsStream(fileName), this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void init() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            refreshFile(parser, this.getClass().getResource("/").getPath());
            refreshFile(parser, JlAppResources.getProperty("mapper.path"));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void refreshFile(SAXParser parser, String strPath) throws Exception {
        strPath = URLDecoder.decode(strPath, "utf-8");//uuly 20140605解决路径有空格问题
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        String fileName = null;
        InputStream is = null;
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                refreshFile(parser, files[i].getAbsolutePath());
            } else {
                fileName = "";
                is = null;
                String[] paths = files[i].getAbsolutePath().split("classes");
                if (paths.length == 1) {
                    fileName = paths[0];
                    if (fileName.endsWith("_columns.xml")) {
                        is = new FileInputStream(fileName);
                    }
                } else if (paths.length == 2) {
                    fileName = paths[1];
                    if (fileName.endsWith("_columns.xml")) {
                        is = Resources.getResourceAsStream(fileName);
                    }
                }
                if (is != null) {
                    parser.parse(is, this);
                }
            }
        }
    }

    @Override
    public void startElement(String namespaceUri, String ename, String qname, Attributes attributes) throws SAXException {
        if (ename.equals("FIELD")) {
            convertor = null;
            Field field = new Field();
            for (int i = 0; i < attributes.getLength(); i++) {
                try {
                    PropertyUtils.setSimpleProperty(field, attributes.getLocalName(i), attributes.getValue(i));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            fields.add(field);
        } else if (ename.endsWith("Nested")) {
            Config config = new Config();
            fields.add(config);

            fields = new ArrayList();
            config.setFields(fields);
            for (int i = 0; i < attributes.getLength(); i++) {
                try {
                    PropertyUtils.setSimpleProperty(config, attributes.getLocalName(i), attributes.getValue(i));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            config.setParent(nested);
            nested = config;
        } else if (ename.endsWith("Config")) {
            Config config = new Config();
            fields = new ArrayList();
            config.setFields(fields);
            for (int i = 0; i < attributes.getLength(); i++) {
                try {
                    PropertyUtils.setSimpleProperty(config, attributes.getLocalName(i), attributes.getValue(i));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            configs.put(attributes.getValue("id"), config);
            nested = config;
        } else if (ename.equals("Convertor")) {
            try {
                createConvertor(attributes.getValue("className"));
            } catch (Exception ex) {
                throw new RuntimeException("record element Expected. Got - " + ex.getMessage());
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("FIELD")) {
            Field field = (Field) fields.get(fields.size() - 1);
            if (convertor != null) {
                field.setConvertor(convertor);
            }
        }

        if (qName.endsWith("Nested")) {
            if (nested != null) {
                nested = nested.getParent();
                fields = nested.getFields();
            }
        }
    }

    private void createConvertor(String className) throws Exception {
        IConvertor convertor = null;
        try {
            convertor = (IConvertor) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            throw new Exception("Must have an empty constructor: " + className, e);
        } catch (IllegalAccessException e) {
            throw new Exception("Must have a public cosntructor: " + className, e);
        } catch (ClassNotFoundException e) {
            throw new Exception("The class " + className + " is not found", e);
        }
        if (this.convertor == null) {
            this.convertor = convertor;
        } else {
            this.convertor.setProcessor(convertor);
        }
    }

    public static Config getConfig(String key) {
        Config config = (Config) configs.get(key);
        if (config == null) {
            throw new NullPointerException("there is not a Config named " + key + " in Config.xml");
        }
        return config;
    }

    private void testConvertor() throws Exception {
        readConfigFile("test.xml");
        Config config = JKConfig.getConfig("DHD");

        Map row = new HashMap();

        Map record = new HashMap();
        record.put("DHDH", new Date());
        record.put("CWGS", "4001");
        record.put("NOTE", new Date());

        List fields = config.getFields();

        for (Iterator<Field> it = fields.iterator(); it.hasNext();) {
            Field field = it.next();
            Object value = record.get(field.getAttrname());
            if (value != null) {
                if (field.getConvertor() != null) {
                    value = field.getConvertor().process(value);
                }
                row.put(field.getTarget(), value);
            }
        }
        System.out.println(row);
    }
}
