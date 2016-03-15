package com.jlsoft.QFYCODE;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Validator;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.axis.message.MessageElement;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static boolean validate(String schemaLocaltion, String xmlStr)throws SAXException, IOException {
	    //获取Schema工厂类，
	    //这里的XMLConstants.W3C_XML_SCHEMA_NS_URI的值就是：
	    //http://www.w3.org/2001/XMLSchema
	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    // Schema实例
	    Schema schema = null;
	    //获取xsd文件，以流的方式读取到Source中xsd文件的位置相对于类文件位置
//	    Source schemaSource = new StreamSource(product.class.getResourceAsStream(schemaLocaltion));
	    //实例化Schema对象
//	    schema = factory.newSchema(schemaSource);
	    //这里是将一个DOM树对象转换成流对象，以便对DOM树对象验证
	    ByteArrayInputStream bais = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
	    // 获取验证器，验证器的XML Schema源就是之前创建的Schema
	    Validator validator = (Validator) schema.newValidator();
	    Source source = new StreamSource(bais);
	    // 执行验证
	    try {
			validator.validate(source);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//根据商品编码获取数据接口
//		ANCCService service=new ANCCServiceLocator();
//		ANCCServiceSoapStub aa = null;
//		try {
//			aa = (ANCCServiceSoapStub)service.getANCCServiceSoap();
//		} catch (ServiceException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult bb=aa.getProductDataByBarCode("1FAC74B0-4733-43EE-B4D6-9082A1D7BD5E", "6921168509256");//06901234569117
//			System.out.println(bb.getItemData().getBrandName());
//			System.out.println(bb.getItemData().getShortDescription());
//			System.out.println(bb.getItemData().getClassCode());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try{
//			throw new Exception("12311111111");
//		}catch(Exception e){
//			System.out.println("1111111111   ");
//			e.printStackTrace();
//			System.out.println("22222222");
//		}finally{
//			System.out.println("33333");
//		}
//		System.out.println("444444");
//	//上传数据接口	
//		Service_TMT service=new Service_TMTLocator();
//		Service_TMTSoapStub aa=null;
//		try {
//			aa=(Service_TMTSoapStub)service.getService_TMTSoap();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			product pro=new product();	
//			 pro.setGtin("92344321356432");    //商品条码
//			    pro.setCnName("三星手机");  //产品名称
//			    pro.setEnName("Samsung");   //英文名称
//			    pro.setBrand("000543");  //商标
//			    pro.setUNSPSC("10101501");  //分类代码
//			    pro.setTM("004");  //目标市场		
//			    pro.setHeight(new BigDecimal(0.84));  //高
//			    pro.setHigthUnit("CM");  //高度单位	
//			    pro.setWidth(new BigDecimal(0.84));  //宽
//			    pro.setWidthUnit("CM");   //宽度单位		
//			    pro.setDepth(new BigDecimal(0.84));  //深度		
//			    pro.setDepthUnit("CM");  //深度单位		
//			    pro.setSpecification("");  //规格
//			    pro.setPackagingMaterialCode("");  //包装材料代码
//			    pro.setPackagingTypeCode("");   //包装类型代码
//			    pro.setOrigin("");  // 原产地
//			    pro.setShelfLife(12); // 保质期
//			    pro.setRetailPriceOnTradeItem(new BigDecimal(12)); // 建议零售价
//			    pro.setSellingUnitOfMeasure("B8"); // 销售单元
//			    pro.setConsumerAvailabilityDateTime(convertToXMLGregorianCalendar(new Date()));//  上市日期
//			    pro.setDiscontinuedDate(convertToXMLGregorianCalendar(new Date())); // 下市日期
//			    pro.setDescriptionShort("");  //短描述
//			    pro.setKeyword(""); // 关键字
//			    pro.setPlaceOfOrigin(""); // 原产地
//			    pro.setPlaceOfOriginCode("");  //原产地代码
//			    pro.setSupplierIdentification("");  //供应商供应商标识
//			    pro.setIsTradeItemAConsumerUnit("");  //是否销售单元
//			    pro.setIsPrivate(true); // 是否保密
//			    pro.setSource("");  //数据源
			
//			XmlValidator xmlval=new XmlValidator();
//			String xml=xmlval.toXML(pro);
//			System.out.println(xml);
//			try {
////				String xml=
////					"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
////					"<Answer>\n" + 
////					"    <ShortDesc>385</ShortDesc>\n" + 
////					"    <AnswerValue>1</AnswerValue>\n" + 
////					"</Answer>";
//				 InputStream XmlStream = Test.class.getResourceAsStream("bb.xml");
//			     Reader XmlReader = new InputStreamReader(XmlStream);        
//			     URL schema =Test.class.getResource("uploadFile_TMT.xsd");
//			     XmlValidator xmlvalid = new XmlValidator();
//			     System.out.println("1111:"+xmlvalid.ValidXmlDoc(XmlReader, schema));
//			     System.out.print("123123:"+xmlvalid.getXmlErr());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			 xml=
//				 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//				 "<product>\n" + 
//				 "  <gtin>06901234569117</gtin>\n" + 
//				 "  <cn_name>三星</cn_name>\n" + 
//				 "  <en_name>Samsung</en_name>\n" + 
//				 "  <brand>000543</brand>\n" + 
//				 "  <UNSPSC>10101501</UNSPSC>\n" + 
//				 "  <TM>004</TM>\n" + 
//				 "  <height>0.84</height>\n" + 
//				 "  <HigthUnit>CM</HigthUnit>\n" + 
//				 "  <width>0.55</width>\n" + 
//				 "  <WidthUnit>CM</WidthUnit>\n" + 
//				 "  <depth>0.55</depth>\n" + 
//				 "  <DepthUnit>CM</DepthUnit>\n" + 
//				 "  <specification>3</specification>\n" + 
//				 "  <packagingMaterialCode>5</packagingMaterialCode>\n" + 
//				 "  <packagingTypeCode>08</packagingTypeCode>\n" + 
//				 "  <origin>132</origin>\n" + 
//				 "  <shelfLife>12</shelfLife>\n" + 
//				 "  <consumerAvailabilityDateTime>2014-02-12</consumerAvailabilityDateTime>\n" + 
//				 "  <descriptionShort>松秦莞尔收到罚单</descriptionShort>\n" + 
//				 "  <keyword>手机</keyword>\n" + 
//				 "  <placeOfOrigin>阿什顿</placeOfOrigin>\n" + 
//				 "  <placeOfOriginCode>110000</placeOfOriginCode>\n" + 
//				 "  <supplierIdentification>44</supplierIdentification>\n" + 
//				 "  <isPrivate>true</isPrivate>\n" + 
//				 "</product>";
//			System.out.println("222222222222:"+xml);
//			Object result=aa.uploadXMLByTMT(xml, "1");
//			System.out.println(result);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		getListByCard
//		Service_TMT service=new Service_TMTLocator();
//		Service_TMTSoapStub aa=null;
//		try {
//			aa=(Service_TMTSoapStub)service.getService_TMTSoap();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			GetListByCardResponseGetListByCardResult result=new GetListByCardResponseGetListByCardResult();
//			Calendar c = Calendar.getInstance();
//			IntHolder resultCode = new IntHolder(0);
//			 result=aa.getListByCard("6921168509256", c.getInstance(),  c.getInstance(), 1, 2, resultCode);
//			System.out.println(result);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//firmInfoByCard
//		Service_TMT service=new Service_TMTLocator();
//		Service_TMTSoapStub aa=null;
//		try {
//			aa=(Service_TMTSoapStub)service.getService_TMTSoap();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("12312312");
//			FirmInfoByCardResponseFirmInfoByCardResult result=aa.firmInfoByCard();
//			System.out.println(result);
//			
//			 MessageElement[] msg = result.get_any();  
//	            List elementHead = msg[0].getChildren();//消息头,DataSet对象  
//	            List elementBody = msg[1].getChildren();//消息体信息,DataSet对象  
//	            if(elementBody.size()<=0){  
//	               System.out.println("nulll");
//	            }  
//	            String str = elementBody.get(0).toString();//消息体的字符串形式  
////	            System.out.println("消息体信息:"+str);
//	            new String(str.getBytes("ISO-8859-1"),"UTF-8");
////	            System.out.println("消息体信息2:"+new String(str.getBytes("ISO-8859-1"),"UTF-8"));
//
//	            
//	            Pattern compile = Pattern.compile("&#.*?;");
//	            Matcher matcher = compile.matcher(str);
//	    		// 循环搜索 并转换 替换
//	    		while (matcher.find()) {
//	    			String group = matcher.group();
//	    			// 获得16进制的码
//	    			String hexcode = "0" + group.replaceAll("(&#|;)", "");
//	    			// 字符串形式的16进制码转成int并转成char 并替换到源串中
//	    			str = str.replaceAll(group, (char) Integer.decode(hexcode).intValue() + "");
//	    		}
//	    		System.out.println(str);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//aa.firmForTMT
		Service_TMT service=new Service_TMTLocator();
		Service_TMTSoapStub aa=null;
		try {
			aa=(Service_TMTSoapStub)service.getService_TMTSoap();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println("12312312");

			FirmForTMTResponseFirmForTMTResult result=aa.firmForTMT("6900000");//1165004");//69547674");
			//FirmForTMTResponseFirmForTMTResult result=aa.firmForTMT("1165004");//1165004");//69547674");
			System.out.println("111:"+result);
			System.out.println(result);
			MessageElement[] msg = result.get_any();  
            List elementHead = msg[0].getChildren();//消息头,DataSet对象  
            List elementBody = msg[1].getChildren();//消息体信息,DataSet对象  
            if(elementBody.size()<=0){  
               System.out.println("nulll");
            }  
            String str = elementBody.get(0).toString();//消息体的字符串形式  
//	            System.out.println("消息体信息:"+str);
            new String(str.getBytes("ISO-8859-1"),"UTF-8");
//	            System.out.println("消息体信息2:"+new String(str.getBytes("ISO-8859-1"),"UTF-8"));

            
            Pattern compile = Pattern.compile("&#.*?;");
            Matcher matcher = compile.matcher(str);
    		// 循环搜索 并转换 替换
    		while (matcher.find()) {
    			String group = matcher.group();
    			// 获得16进制的码
    			String hexcode = "0" + group.replaceAll("(&#|;)", "");
    			// 字符串形式的16进制码转成int并转成char 并替换到源串中
    			str = str.replaceAll(group, (char) Integer.decode(hexcode).intValue() + "");
    		}
    		/*
    		str = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"+
			        "<Result xmlns=\"http://www.fiorano.com/fesb/activity/DBQueryOnInput2/Out\">"+
			           "<row resultcount=\"1\">"+
			              "<users_id>1001     </users_id>"+
			              "<users_name>wangwei   </users_name>"+
			              "<users_group>80        </users_group>"+
			              "<users_address>1001号   </users_address>"+
			           "</row>"+
			           "<row resultcount=\"1\">"+
			              "<users_id>1002     </users_id>"+
			              "<users_name>wangwei   </users_name>"+
			              "<users_group>80        </users_group>"+
			              "<users_address>1002号   </users_address>"+
			           "</row>"+
			        "</Result>";
    		*/
    		System.out.println(str);
    		xmlElements(str);
//			System.out.println("123:"+result.get_any());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

             e.printStackTrace();
        }
        return gc;
    }
	public static void saveXMLString(String xmlString,String filename) throws IOException{  
        File file = new File(filename);  
        if(file.exists()){  
            file.delete();  
        }         
        file.createNewFile();  
        if(file.canWrite()){  
            FileWriter out = new FileWriter(file);  
            out.write(xmlString);  
            out.close();  
        }  
         
	}
	
	public static List xmlElements(String xmlDoc) {
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            Element et = null;
            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(i);//循环依次得到子元素
               
                System.out.println(et.getChild("firm_name",ns).getText());
                System.out.println(et.getChild("code",ns).getText());
            }
           
            et = (Element) jiedian.get(0);
            List zjiedian = et.getChildren();
            for(int j=0;j<zjiedian.size();j++){
                Element xet = (Element) zjiedian.get(j);
                System.out.println(xet.getName());
            }
        } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return null;
    }
	
//	//车次查询读取xml文件  
//    public static ArrayList loadXML(String filepath) throws IOException{  
//        ArrayList<Train> list = null;  
//        FileInputStream fis = null;  
//        try{  
//              
//            fis = new FileInputStream(filepath);  
//            list = new ArrayList<Train>();  
//            SAXBuilder builder = new SAXBuilder();  
//            org.dom4j.Document doc = builder.build(fis);  
//            Element root = doc.getRootElement();  
//            List doclist = root.getChildren();  
//            Element info = null;  
//            Train train = null;  
//              
//            for(int i=0; i<doclist.size(); i++){  
//                train = new Train();  
//                info = (Element) doclist.get(i);  
//                train.setArriveTime(info.getChild("ArriveTime").getText().equals("http://www.webxml.com.cn")?"没有数据":info.getChild("ArriveTime").getText());  
//                train.setKm(info.getChild("KM").getText().equals("")?"没有数据":info.getChild("KM").getText());  
//                train.setStartTime(info.getChild("StartTime").getText().equals("")?"没有数据":info.getChild("StartTime").getText());  
//                train.setTrainStation(info.getChild("TrainStation").getText().equals("免费用户24小时内访问超过规定数量")?"没有数据":info.getChild("TrainStation").getText());  
//                  
//                list.add(train);  
//            }         
//        }catch(Exception e){  
//            e.printStackTrace();  
//        }  
//          
//        return list;  
//    }  
}
