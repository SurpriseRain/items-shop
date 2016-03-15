package com.jlsoft.utils;

import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import java.io.Writer;

/**
 * ������Ҫ����ʵ��XML�Ľ����Ĺ���.
 */
public class XmlUtils {
  private static class XmlPrinter {
    private static boolean lastIsAString = false;
    /**
     * �������ո����ĸ��ո�Ϊ��λ
     *
     * @param space
     * @return
     * ����ʱ�䣺2004-10-16 16:09:21
     */
    private static String getSpace(int space) {
      char ca[] = new char[space * 4];
      for (int i = 0; i < ca.length; i += 4) {
        ca[i] = ' ';
        ca[i + 1] = ' ';
        ca[i + 2] = ' ';
        ca[i + 3] = ' ';
      }
      return new String(ca);
    }

    /**
     * ��xml�ڵ㰴��xml���ļ���ʽ����ķ���
     *
     * @param pw �����
     * @param node  xml�ڵ�
     * @param deepSet �ڵ㼶�Σ�һ��Ϊ0��
     * ����ʱ�䣺2004-10-16 16:10:07
     */
    public static void printDOMTree(Writer pw, Node node, int deepSet) throws  IOException{
//		int deep = deepSet + 1;
      int type = node.getNodeType();
      switch (type) {
        // print the document element
        case Node.DOCUMENT_NODE: {
          pw.write("<?xml version=\"1.0\" encoding='UTF-8'?>");
          printDOMTree(pw, ( (Document) node).getDocumentElement(), deepSet);
          //pw.println(); 
		  pw.write(System.getProperty("line.separator"));
          break;
        }
        // print element with attributes
        case Node.ELEMENT_NODE: {
          //pw.println();
		  pw.write(System.getProperty("line.separator"));
          pw.write(getSpace(deepSet) + "<");
          pw.write(node.getNodeName());
          NamedNodeMap attrs = node.getAttributes();
          for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            pw.write(" " + attr.getNodeName() + "=\"" +
                     getXMLString(attr.getNodeValue()) + "\"");
            if (attr.getNodeValue().equals("null")) {
              lastIsAString = true;
            }
          }
          pw.write(">");
          NodeList children = node.getChildNodes();
          if (children != null) {
            int len = children.getLength();
            for (int i = 0; i < len; i++) {
              printDOMTree(pw, children.item(i), deepSet + 1);
            }
          }
          break;
        }
        // handle entity reference nodes
        case Node.ENTITY_REFERENCE_NODE: {
          pw.write("&");
          pw.write(node.getNodeName());
          pw.write(";");
          break;
        }
        // print cdata sections
        case Node.CDATA_SECTION_NODE: {
          pw.write(getSpace(deepSet) + "<![CDATA[");
          pw.write(node.getNodeValue());
          pw.write("]]>");
          break;
        }
        // print text
        case Node.TEXT_NODE: { //
          String value = node.getNodeValue();
          //.trim();
          if (value != null) {
            value = value.trim();
            value = getXMLString(value);
            pw.write(value);
          }
          lastIsAString = !"".equals(value);
          //

//					pw.print(node.getNodeValue());
//					lastIsAString = true;
          break;
        }
        // print processing instruction
        case Node.PROCESSING_INSTRUCTION_NODE: {
          pw.write(getSpace(deepSet) + "<?");
          pw.write(node.getNodeName());
          String data = node.getNodeValue();
          {
            pw.write("");
            pw.write(data);
          }
          pw.write("?>");
          break;
        }
        case Node.COMMENT_NODE: {
          //pw.println();  
          pw.write(System.getProperty("line.separator"));
          pw.write(getSpace(deepSet) + "<!--");
          pw.write(node.getNodeValue() + "-->");
          break;
        }
      }
      if (type == Node.ELEMENT_NODE) {
        if (!lastIsAString) {
          //pw.println();  
		  pw.write(System.getProperty("line.separator"));
          pw.write(getSpace(deepSet) + "</");
        }
        else {
          pw.write("</");
        }
        pw.write(node.getNodeName());
        pw.write('>');
        lastIsAString = false;
      }
    }

    /** add by zlg at 2005-07-21
     * ��xml�ڵ㰴��xml���ļ���ʽ����ķ���,���Ϊ�ַ�
     * @param node  xml�ڵ�
     * @param deepSet �ڵ㼶�Σ�һ��Ϊ0��
     * ����ʱ�䣺2005-07-22 16:10:07
     */
    public static String getXmlStr(Node node, int deepSet) {
//		int deep = deepSet + 1;
      int type = node.getNodeType();
      StringBuffer sbf = new StringBuffer();
      switch (type) {
        // print the document element
        case Node.DOCUMENT_NODE: {
          sbf.append(getXMLString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
          sbf.append(getXmlStr( ( (Document) node).getDocumentElement(),
                               deepSet));
          sbf.append("\n");
          break;
        }
        // print element with attributes
        case Node.ELEMENT_NODE: {
          sbf.append("\n");
          sbf.append(getSpace(deepSet) + getXMLString("<"));
          sbf.append(node.getNodeName());
          NamedNodeMap attrs = node.getAttributes();
          for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            sbf.append(" " + attr.getNodeName() + "=\"" +
                       getXMLString(attr.getNodeValue()) + "\"");
            if (attr.getNodeValue().equals("null")) {
              lastIsAString = true;
            }
          }
          sbf.append(getXMLString(">"));
          NodeList children = node.getChildNodes();
          if (children != null) {
            int len = children.getLength();
            for (int i = 0; i < len; i++) {
              sbf.append(getXmlStr(children.item(i), deepSet + 1));
            }
          }
          break;
        }
        // handle entity reference nodes
        case Node.ENTITY_REFERENCE_NODE: {
          sbf.append(getXMLString("&"));
          sbf.append(node.getNodeName());
          sbf.append(";");
          break;
        }
        // print cdata sections
        case Node.CDATA_SECTION_NODE: {
          sbf.append(getSpace(deepSet) + getXMLString("<![CDATA["));
          sbf.append(node.getNodeValue());
          sbf.append(getXMLString("]]>"));
          break;
        }
        // print text
        case Node.TEXT_NODE: { //
          String value = node.getNodeValue();
          //.trim();
          if (value != null) {
            value = value.trim();
            value = getXMLString(value);
            sbf.append(value);
          }
          lastIsAString = !"".equals(value);
          //

//					pw.print(node.getNodeValue());
//					lastIsAString = true;
          break;
        }
        // print processing instruction
        case Node.PROCESSING_INSTRUCTION_NODE: {
          sbf.append(getSpace(deepSet) + getXMLString("<?"));
          sbf.append(node.getNodeName());
          String data = node.getNodeValue();
          {
            sbf.append("");
            sbf.append(data);
          }
          sbf.append(getXMLString("?>"));
          break;
        }
        case Node.COMMENT_NODE: {
          sbf.append("\n");
          sbf.append(getSpace(deepSet) + getXMLString("<!--"));
          sbf.append(node.getNodeValue() + getXMLString("-->"));
          break;
        }
      }
      if (type == Node.ELEMENT_NODE) {
        if (!lastIsAString) {
          sbf.append("\n");
          sbf.append(getSpace(deepSet) + getXMLString("</"));
        }
        else {
          sbf.append(getXMLString("</"));
        }
        sbf.append(node.getNodeName());
        sbf.append(getXMLString(">"));
        lastIsAString = false;
      }
      return sbf.toString();
    }

    /** add by zlg at 2005-07-21
     * ��xml�ڵ㰴��xml���ļ���ʽ����ķ���,���Ϊ�ַ�
     * @param node  xml�ڵ�
     * @param deepSet �ڵ㼶�Σ�һ��Ϊ0��
     * ����ʱ�䣺2005-07-22 16:10:07
     */
    public static String getXmlStrNoConvert(Node node, int deepSet) {
//		int deep = deepSet + 1;
      int type = node.getNodeType();
      StringBuffer sbf = new StringBuffer();
      switch (type) {
        // print the document element
        case Node.DOCUMENT_NODE: {
          sbf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
          sbf.append(getXmlStr( ( (Document) node).getDocumentElement(),
                               deepSet));
          sbf.append("\n");
          break;
        }
        // print element with attributes
        case Node.ELEMENT_NODE: {
          sbf.append("\n");
          sbf.append(getSpace(deepSet) + getXMLString("<"));
          sbf.append(node.getNodeName());
          NamedNodeMap attrs = node.getAttributes();
          for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            sbf.append(" " + attr.getNodeName() + "=\"" +
                       getXMLString(attr.getNodeValue()) + "\"");
            if (attr.getNodeValue().equals("null")) {
              lastIsAString = true;
            }
          }
          sbf.append(">");
          NodeList children = node.getChildNodes();
          if (children != null) {
            int len = children.getLength();
            for (int i = 0; i < len; i++) {
              sbf.append(getXmlStr(children.item(i), deepSet + 1));
            }
          }
          break;
        }
        // handle entity reference nodes
        case Node.ENTITY_REFERENCE_NODE: {
          sbf.append("&");
          sbf.append(node.getNodeName());
          sbf.append(";");
          break;
        }
        // print cdata sections
        case Node.CDATA_SECTION_NODE: {
          sbf.append(getSpace(deepSet) + "<![CDATA[");
          sbf.append(node.getNodeValue());
          sbf.append("]]>");
          break;
        }
        // print text
        case Node.TEXT_NODE: { //
          String value = node.getNodeValue();
          //.trim();
          if (value != null) {
            value = value.trim();
            value = getXMLString(value);
            sbf.append(value);
          }
          lastIsAString = !"".equals(value);
          //

//					pw.print(node.getNodeValue());
//					lastIsAString = true;
          break;
        }
        // print processing instruction
        case Node.PROCESSING_INSTRUCTION_NODE: {
          sbf.append(getSpace(deepSet) + getXMLString("<?"));
          sbf.append(node.getNodeName());
          String data = node.getNodeValue();
          {
            sbf.append("");
            sbf.append(data);
          }
          sbf.append(getXMLString("?>"));
          break;
        }
        case Node.COMMENT_NODE: {
          sbf.append("\n");
          sbf.append(getSpace(deepSet) + "<!--");
          sbf.append(node.getNodeValue() + "-->");
          break;
        }
      }
      if (type == Node.ELEMENT_NODE) {
        if (!lastIsAString) {
          sbf.append("\n");
          sbf.append(getSpace(deepSet) + "</");
        }
        else {
          sbf.append("</");
        }
        sbf.append(node.getNodeName());
        sbf.append(">");
        lastIsAString = false;
      }
      return sbf.toString();
    }

    /**
     *
     * @param value
     * @return
     */
    private static String getXMLString(String value) {
      if (value != null) {
        value = value.toString().trim();
        // ��ΪXML������ֵ���ܰ��ַ����̶ֹ���ʵ�����õ��ַ�

        value = replaceAllString(value, "&", "&amp;");
        value = replaceAllString(value, "<", "&lt;");
        value = replaceAllString(value, "<", "&lt;");
        value = replaceAllString(value, ">", "&gt;");
        value = replaceAllString(value, "\"", "&quot;");
        value = replaceAllString(value, "\'", "&apos;");
      }
      return value;
    }
  }

  public static String replaceAllString(String source, String strBeReplace,
                                        String strReplaced) {
    if (strReplaced == null) {
      strReplaced = "null";
    }
    int loc = 0;
    for (int indexLoc = source.indexOf(strBeReplace); indexLoc >= 0;
         indexLoc = source.indexOf(strBeReplace, loc)) {
      source = source.substring(0, indexLoc) + strReplaced +
          source.substring(indexLoc + strBeReplace.length());
      loc = indexLoc + strReplaced.length();
    }
    return source;
  }

  private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  static {
    dbf.setValidating(false);
    dbf.setNamespaceAware(true);
  }

  static ThreadLocal dbcache = new ThreadLocal() {
    protected Object initialValue() {
      try {
        return dbf.newDocumentBuilder();
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return null;
      }
    }
  };

  private XmlUtils() {

  }

  public static void addChildElement(Element element, String child_ele_name,
                                     String text) {
    Document doc = element.getOwnerDocument();
    Element sub_element = createLeafElement(doc, child_ele_name, text);
    element.appendChild(sub_element);
  }

  /**
   * Copies the source tree into the specified place in a destination
   * tree. The source node and its children are appended as children
   * of the destination node.
   * <p>
   * <em>Note:</em> This is an iterative implementation.
   */
  public static void copyInto(Node src, Node dest) throws DOMException {

    // get node factory
    Document factory = dest.getOwnerDocument();
//        boolean domimpl = factory instanceof DocumentImpl;

    Node start = src;
    Node parent = src;
    Node place = src;

    // traverse source tree
    while (place != null) {

      // copy this node
      Node node = null;
      int type = place.getNodeType();
      switch (type) {
        case Node.CDATA_SECTION_NODE: {
          node = factory.createCDATASection(place.getNodeValue());
          break;
        }
        case Node.COMMENT_NODE: {
          node = factory.createComment(place.getNodeValue());
          break;
        }
        case Node.ELEMENT_NODE: {
          Element element = factory.createElement(place.getNodeName());
          node = element;
          NamedNodeMap attrs = place.getAttributes();
          int attrCount = attrs.getLength();
          for (int i = 0; i < attrCount; i++) {
            Attr attr = (Attr) attrs.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            element.setAttribute(attrName, attrValue);
            /*
                                if (domimpl && !attr.getSpecified()) {
             ((Attr) element.getAttributeNode(attrName)).setSpecified(false);
                                }
             */
          }
          break;
        }
        case Node.ENTITY_REFERENCE_NODE: {
          node = factory.createEntityReference(place.getNodeName());
          break;
        }
        case Node.PROCESSING_INSTRUCTION_NODE: {
          node = factory.createProcessingInstruction(place.getNodeName(),
              place.getNodeValue());
          break;
        }
        case Node.TEXT_NODE: {
          node = factory.createTextNode(place.getNodeValue());
          break;
        }
        default: {
          throw new IllegalArgumentException("can't copy node type, " +
                                             type + " (" +
                                             node.getNodeName() + ')');
        }
      }
      dest.appendChild(node);

      // iterate over children
      if (place.hasChildNodes()) {
        parent = place;
        place = place.getFirstChild();
        dest = node;
      }

      // advance
      else {
        place = place.getNextSibling();
        while (place == null && parent != start) {
          place = parent.getNextSibling();
          parent = parent.getParentNode();
          dest = dest.getParentNode();
        }
      }

    }

  } // copyInto(Node,Node)

  public static Element createLeafElement(Document doc, String eleName,
                                          String text) {
    Element ele = doc.createElement(eleName);
    if (text != null) {
      ele.appendChild(doc.createTextNode(text));
    }
    return ele;
  }

  public static final String getAtrributeValueOf(Node node, String attribute) {
    Node _node = node.getAttributes().getNamedItem(attribute);
    return getValueOf(_node);
  }

  /**
   * �õ���node����node
   *
   * @param node ���node
   * @param tagName Ҫȡ�õ���node���
   * @return ��node
   */
  public static Node getChildNodeOf(Node node, String tagName) {
    for (Node temp = node.getFirstChild(); temp != null;
         temp = temp.getNextSibling()) {
      if (temp.getNodeType() == Node.ELEMENT_NODE
          && tagName.equals(temp.getNodeName())) {
        return temp;
      }
    }
    return null;
  }

  public static String getChildNodeValueOf(Node node, String tagName) {
    if (tagName.equals(node.getNodeName())) {
      return getValueOf(node);
    }
    for (Node temp = node.getFirstChild(); temp != null;
         temp = temp.getNextSibling()) {
      if (temp.getNodeType() == Node.ELEMENT_NODE &&
          tagName.equals(temp.getNodeName())) {
        return getValueOf(temp);
      }
    }
    return null;
  }

  /**
   * Returns the concatenated child text of the specified node.
   * This method only looks at the immediate children of type
   * <code>Node.TEXT_NODE</code> or the children of any child
   * node that is of type <code>Node.CDATA_SECTION_NODE</code>
   * for the concatenation.
   *
   * @param node The node to look at.
   */
  public static String getChildText(Node node) {
    if (node == null) {    // is there anything to do?
      return null;
    }
    StringBuffer str = new StringBuffer();    // concatenate children text
    Node child = node.getFirstChild();
    while (child != null) {
      short type = child.getNodeType();
      if (type == Node.TEXT_NODE) {
        str.append(child.getNodeValue());
      }
      else if (type == Node.CDATA_SECTION_NODE) {
        str.append(getChildText(child));
      }
      child = child.getNextSibling();
    }
    return str.toString();    // return text value
  } // getChildText(Node):String

  public static Document getDocument(File file) throws Exception {
    InputStream is = null;
    try {
      return getDocumentBuilder().parse(file);
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException e) {
        }
      }
    }
  }

  public static Document getDocument(String file) throws Exception {
    InputStream is = null;
    try {
      return getDocumentBuilder().parse(file);
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException e) {
        }
      }
    }
  }

  /**
   * get the xml root document of the xml descriptor
   * @param url the xml descriptor url
   * @return
   */
  public static Document getDocument(URL url) throws Exception {
    InputStream is = null;
    try {
      is = new BufferedInputStream(url.openStream());
      return getDocumentBuilder().parse(is);
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException e) {
        }
      }
    }
  }

  public static DocumentBuilder getDocumentBuilder() {
    return (DocumentBuilder) dbcache.get();
  }

  public static Iterator getElementsByTagName(Element element, String tag) {
    ArrayList children = new ArrayList();
    if (element != null && tag != null) {
      NodeList nodes = element.getElementsByTagName(tag);
      for (int i = 0; i < nodes.getLength(); i++) {
        Node child = nodes.item(i);
//      System.out.println("Name: " + child.getNodeName() + ", Value: " + child.getFirstChild().getNodeValue());
        children.add( (Element) child);
      }
    }
    return children.iterator();
  }

  public static Iterator getElementsByTagNames(Element element, String[] tags) {
    List children = new ArrayList();
    if (element != null && tags != null) {
      List tagList = Arrays.asList(tags);
      NodeList nodes = element.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        Node child = nodes.item(i);
        if (child.getNodeType() == Node.ELEMENT_NODE
            && tagList.contains( ( (Element) child).getTagName())) {
          children.add( (Element) child);
        //System.out.println("Name: " + child.getNodeName() + ", Value: " + child.getFirstChild().getNodeValue());
        }
      }
    }
    return children.iterator();
  }

  /**
   * ��ȡ��Ԫ��Element���ı�ֵ,���û���ı��ڵ�,����null
   *(ע���getChildText���������)
   * @param ele The node to look at.
   */
  public static String getElementText(Element ele) {
    if (ele == null) {    // is there anything to do?
      return null;
    }
    Node child = ele.getFirstChild();    // get children text
    if (child != null) {
      short type = child.getNodeType();
      if (type == Node.TEXT_NODE) {
        return child.getNodeValue();
      }
    }
    return null;    // return text value
  } // getChildText(Node):String

  /** Finds and returns the first child element node. */
  public static Element getFirstChildElement(Node parent) {
    Node child = parent.getFirstChild();   // search for node
    while (child != null) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        return (Element) child;
      }
      child = child.getNextSibling();
    }
    return null;  // not found
  } // getFirstChildElement(Node):Element

  /** Finds and returns the first child node with the given name. */
  public static Element getFirstChildElement(Node parent, String elemName) {
    Node child = parent.getFirstChild();  // search for node
    while (child != null) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        if (child.getNodeName().equals(elemName)) {
          return (Element) child;
        }
      }
      child = child.getNextSibling();
    }
    return null; // not found
  } // getFirstChildElement(Node,String):Element

  public static String getFirstChildElementText(Node node) {
    return getElementText(getFirstChildElement(node));
  }

  public static String getFirstChildElementText(Node node, String elemName) {
    return getElementText(getFirstChildElement(node, elemName));
  }

  /** Finds and returns the last child element node. */
  public static Element getLastChildElement(Node parent) {

    // search for node
    Node child = parent.getLastChild();
    while (child != null) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        return (Element) child;
      }
      child = child.getPreviousSibling();
    }
    // not found
    return null;

  } // getLastChildElement(Node):Element

  /** Finds and returns the last child node with the given name. */
  public static Element getLastChildElement(Node parent, String elemName) {

    // search for node
    Node child = parent.getLastChild();
    while (child != null) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        if (child.getNodeName().equals(elemName)) {
          return (Element) child;
        }
      }
      child = child.getPreviousSibling();
    }

    // not found
    return null;

  } // getLastChildElement(Node,String):Element

  public static String getLastChildElementText(Node node) {
    return getElementText(getLastChildElement(node));
  }

  public static String getLastChildElementText(Node node, String elemName) {
    return getElementText(getLastChildElement(node, elemName));
  }

  public static Document getNewDocument() {
    return getDocumentBuilder().newDocument();
  }

  /** Finds and returns the next sibling element node. */
  public static Element getNextSiblingElement(Node node) {

    // search for node
    Node sibling = node.getNextSibling();
    while (sibling != null) {
      if (sibling.getNodeType() == Node.ELEMENT_NODE) {
        return (Element) sibling;
      }
      sibling = sibling.getNextSibling();
    }

    // not found
    return null;

  } // getNextSiblingElement(Node):Element

  /** Finds and returns the next sibling node with the given name. */
  public static Element getNextSiblingElement(Node node, String elemName) {

    // search for node
    Node sibling = node.getNextSibling();
    while (sibling != null) {
      if (sibling.getNodeType() == Node.ELEMENT_NODE) {
        if (sibling.getNodeName().equals(elemName)) {
          return (Element) sibling;
        }
      }
      sibling = sibling.getNextSibling();
    }
    // not found
    return null;

  } // getNextSiblingdElement(Node,String):Element

  public static String getNextSiblingElementText(Node node) {
    return getElementText(getNextSiblingElement(node));
  }

  public static String getNextSiblingElementText(Node node, String elemName) {
    return getElementText(getNextSiblingElement(node, elemName));
  }

  public static final String getValueOf(Node node) {
    if (node == null) {
      return null;
    }
    else if (node instanceof Text) {
      return node.getNodeValue().trim();
    }
    else if (node instanceof Element) {
      ( (Element) node).normalize();
      Node temp = node.getFirstChild();
      if (temp != null && (temp instanceof Text)) {
        return temp.getNodeValue().trim();
      }
      else {
        return "";
      }
    }
    else {
      return node.getNodeValue().trim();
    }
  }

  /**
   * ��XML��ʽ���DOM��
   * @param pw �����
   * @param node ��� node
   * @param deepSet ������ȣ�0 ������
   */
  public static void printDOMTree(Writer pw, Node node, int deepSet) throws  IOException {
    XmlPrinter.printDOMTree(pw, node, deepSet);
  }

  /**
   * ��XML��ʽ���DOM��
   * @param pw �����
   * @param node ��� node
   * @param deepSet ������ȣ�0 ������
   */
  public static String getXmlStr(Node node, int deepSet) {
    return XmlPrinter.getXmlStr(node, deepSet);
  }
}
