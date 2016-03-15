package com.jlsoft.QFYCODE;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Controller
@RequestMapping("/XmlAnalysis")
public class XmlAnalysis {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/xmlAnalysis.action")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         try
         {
        	 //xml解析
        	 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();	
        	 DocumentBuilder db = dbf.newDocumentBuilder();	
        	 File file = new File("D:\\test.xml");
        	 //File file = new File(url);
        	 InputStream is = new FileInputStream(file);
        	 Document doc = db.parse(is);
        	 //得到根节点   
        	 Element root = doc.getDocumentElement(); 
        	 //获取head 
        	 NodeList nl = root.getElementsByTagName("Head"); 
    		 Element e = (Element) nl.item(0);
    		 //一次读取所以head结点
        	 //System.out.println(e.getNodeName()+":"+e.getTextContent());
        	 //分别按标签读取一个结点
        	 System.out.println( e.getElementsByTagName("UserName").item(0).getNodeName()+":"+e.getElementsByTagName("UserName").item(0).getTextContent());
        	 System.out.println(e.getElementsByTagName("Password").item(0).getNodeName()+":"+e.getElementsByTagName("Password").item(0).getTextContent());
        	 System.out.println(e.getElementsByTagName("CompanyCode").item(0).getNodeName()+":"+e.getElementsByTagName("CompanyCode").item(0).getTextContent());
        	 System.out.println(e.getElementsByTagName("TraceNum").item(0).getNodeName()+":"+e.getElementsByTagName("TraceNum").item(0).getTextContent());
        	 System.out.println(e.getElementsByTagName("FileType").item(0).getNodeName()+":"+e.getElementsByTagName("FileType").item(0).getTextContent());
        	 //获取Relation节点
        	 NodeList nl_body = root.getElementsByTagName("Relation"); 
        	//遍历Relation节点
        	 for(int i=0;i<nl_body.getLength();i++)
        	 {
        		 //读取Relation节点的元素
        		 Element e_body = (Element) nl_body.item(i); 
        		 System.out.println("cascade:"+e_body.getAttribute("cascade"));
        		 System.out.println("packageSpec:"+e_body.getAttribute("packageSpec"));
        		 System.out.println("comment:"+e_body.getAttribute("comment"));
        		 //获取并循环 TraceCode节点
        		 for(int j=0;j<e_body.getElementsByTagName("TraceCode").getLength();j++)
        		 {
        			 //获取并循环 TraceCode节点元素
        			 for(int k=0;k<e_body.getElementsByTagName("TraceCode").item(j).getAttributes().getLength();k++)
        			 System.out.println(e_body.getElementsByTagName("TraceCode").item(j).getAttributes().item(k).getNodeName()+":"+e_body.getElementsByTagName("TraceCode").item(j).getAttributes().item(k).getNodeValue());        		
        		 }
        	 }
        	         	 
         }catch(Exception e)
         {
        	 e.printStackTrace();
         }
	}

}
