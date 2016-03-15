/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jlsoft.framework.aop;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Validator - 数据校验器类
 *
 * 利用 W3C Schema 校验 XML/JSON 数据
 *
 * @author root
 */
public class Validator {

    public static void validateExternalSchema(HttpServletRequest request) throws IOException, SAXException, ParserConfigurationException {

        if (JlInterceptor.isQueryURI(request)) {
            return;
        }

        // 获取XmlData字符串和XSD文件路径
        String uri = request.getRequestURI();
        String root = request.getContextPath();
        uri = uri.replace(root, "");
        String filename = uri.substring(uri.indexOf("/") + 1);
        filename = filename.substring(0, filename.indexOf("/")) + ".xsd";
        String xml = request.getParameter("XmlData");
        if (null == xml || "".equals(xml)) {
            return; // null值直接退出
        }
        if (xml.startsWith("<?xml")) {
            filename = request.getSession().getServletContext().getResource("/") + "com/jlsoft/schema/xml/" + filename;
        } else {
            filename = request.getSession().getServletContext().getResource("/") + "com/jlsoft/schema/json/" + filename;
        }
        File xsdfile = new File(filename);
        if (!xsdfile.exists()) {
            return; // xsd文件不存在则立即停止执行，避免无谓消耗资源做json->xml的解析和转换
        }

        // 如果XmlData是JSON字符串，将其转换为XML字符串
        JSON json = null;
        if (!xml.startsWith("<?xml")) {
            if (xml.startsWith("[")) {
                json = JSONArray.fromObject(xml);
            } else {
                if (xml.startsWith("{")) {
                    json = JSONObject.fromObject(xml);
                } else {
                    return;
                }
            }
        }
        XMLSerializer serializer = new XMLSerializer();
        serializer.setTypeHintsEnabled(false);
        xml = serializer.write(json, "UTF-8");

        // 用XSD文件校验XML字符串
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(filename)}));
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        StringReader xmlreader = new StringReader(xml);
        reader.parse(new InputSource(xmlreader));
    }
}
