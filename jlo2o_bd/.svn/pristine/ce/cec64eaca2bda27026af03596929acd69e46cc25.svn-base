package com.jlsoft.QFYCODE;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;

import org.apache.axis.message.MessageElement;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.product.service.Oper_floor;
import com.jlsoft.o2o.user.service.Register;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

@Controller
@RequestMapping("/QFYCODE")
public class QFYCODE extends JLBill {

	@Autowired
	private Register register;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectQFYCODE.action")
	public Map selectQFYCODE(String XmlData) throws Exception {
		Map hm = new HashMap();
		
		try {
			cds = new DataSet(XmlData);
			String tm = cds.getField("tm", 0);
			//tm = "6900000000007";
			//System.out.println("12312312");
			//JSONArray json = JSONArray.fromObject(result.get_any()); 
			List list = identificationcode(tm);
			hm.put("list", list);
			//hm = (Map) json.get(0);
			
			String sql = "SELECT COUNT(1) FROM w_zcxx WHERE zcxx41='"+tm+"'";
			int count = queryForInt(o2o, sql);
			if(count == 0) {
				String sql1 = "SELECT COUNT(1) FROM w_zcxx WHERE zcxx42='"+tm+"'";
				int count1 = queryForInt(o2o, sql1);
				if(count1 == 0) {
					hm.put("state", 0);
				} else {
					hm.put("state", 1);
				}
			} else {
				hm.put("state", 1);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return hm;
	}
	

	
	public List identificationcode(String tm) {
		List list = new ArrayList();
		Service_TMT service=new Service_TMTLocator();
		Service_TMTSoapStub aa=null;
		try {
			aa=(Service_TMTSoapStub)service.getService_TMTSoap();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		try {
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
    		System.out.println(str);
    		list = xmlcssbmElements(str);
//			System.out.println("123:"+result.get_any());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/uploadXML.action")
	public Map uploadXML(HttpServletRequest request, String XmlData) throws Exception {
		Map hm = new HashMap();
		cds = new DataSet(XmlData);
		String gsid = cds.getField("gsid", 0);
		hm.put("gsid", gsid);
		JSONArray jsonList = JSONArray.fromObject(XmlData);
		List<HashMap<String, Object>> listMap = (List<HashMap<String, Object>>) jsonList;
		hm =(Map<String, Object>)listMap.get(0);
		MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
		List<MultipartFile> listFile= mrRequest.getFiles("files");
		hm = register.uploadFile(request, hm,listFile);
		String filepath = hm.get("filepath").toString();
		try {
			filepath = filepath.replace("\\", "\\\\");

			hm = xmlElements(filepath);
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("state", 2);
		}
		
		return hm;
	}
	
	//直接解析xml文件（产品追朔xml文件上传解析）
	public Map xmlElements(String filepath) throws Exception {
		Map hm = new HashMap();
        try {
    		File file = new File(filepath);
        	SAXBuilder reader = new SAXBuilder();   
        	Document doc=(Document) reader.build(file);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			System.out.println(df.format(new Date()));
			String DRSJ = df.format(new Date()).toString();
            
			//获取head 
            List head = root.getChildren("Head");
            Element e = (Element) head.get(0);

            String CompanyCode = e.getChild("CompanyCode").getValue();
			String GuildCode = e.getChild("GuildCode").getValue();
			String PositionCode = e.getChild("PositionCode").getValue();
			
			String sql = "select zcxx01 from w_zcgs where HYGLM='"+GuildCode+"'";
			Map zcxxMap = queryForMap(o2o, sql);
			if(zcxxMap == null) {
				hm.put("state", 0);
				return hm;
			} else {
				String zcxx01 = zcxxMap.get("zcxx01").toString();
				//商品基础信息上传
	            if(e.getChildren().size() == 3) {
					Integer SPDR01=PubFun.updateWBHZT(o2o,"W_SPDR",1);

    				String W_SPDRsql = "insert into W_SPDR values ('"+zcxx01+"','"+SPDR01+"','"+CompanyCode+"','"+GuildCode+"','"+PositionCode+"','"+DRSJ+"')";
    				Map W_SPDRrow=getRow(W_SPDRsql, null, 0);
    				execSQL(o2o, W_SPDRsql, W_SPDRrow);
	            	//获取Body 
	    			Element Body = root.getChild("Body");
	            	Element PartsList = Body.getChild("PartsList");
	    			List Part = PartsList.getChildren("Part");
	    			for(int i=0;i<Part.size();i++){
	    				//读取Relation节点的元素
	    				Element e_body = (Element) Part.get(i); 
	    				String Projectcode = e_body.getChild("Projectcode").getValue();
	    				String MateriaName = e_body.getChild("MateriaName").getValue();
	    				String Modal = e_body.getChild("Modal").getValue();
	    				String UnitName = e_body.getChild("UnitName").getValue();
	    				
	    				String spisnull = "select count(1) from w_goods where barcode='"+Projectcode+"' and zcxx01='"+zcxx01+"'";
	    				int spsum = queryForInt(o2o, spisnull);
	    				if(spsum == 0) {
	    					Integer SPXX01=PubFun.updateWBHZT(o2o,"w_goods",1);
		    				
		    				String sql1 = "insert into w_goods (barcode, spxx04, spxx01, zcxx01, sbm, jldw, Modal) values ('"+Projectcode+"','"+MateriaName+"','"+SPXX01+"','"+zcxx01+"','"+CompanyCode+"','"+UnitName+"','"+Modal+"')";
		    				Map row1=getRow(sql1, null, 0);
		    				execSQL(o2o, sql1, row1);
		    				
		    				String W_SPDRITEMsql = "insert into W_SPDRITEM values ('"+zcxx01+"','"+SPDR01+"','"+Projectcode+"','"+MateriaName+"','"+Modal+"','"+UnitName+"')";
		    				Map W_SPDRITEMrow=getRow(W_SPDRITEMsql, null, 0);
		    				execSQL(o2o, W_SPDRITEMsql, W_SPDRITEMrow);
	    				}
	           	 	}
    				hm.put("state", 1);
    				return hm;
	            }
				            
				LinkedList inParameter = new LinkedList();
				inParameter.add("W_SPCMDR");
				inParameter.add(1);
				LinkedList outParameter = new LinkedList();
				outParameter.add(java.sql.Types.CHAR);
				String sqlq="{call Update_WBHZT(?,?,?)}";
				List SPPJ01 = callProc(o2o, sqlq, inParameter, outParameter);
				String SPCMDR01 = SPPJ01.get(0).toString();
				
				String TraceNum = e.getChild("TraceNum").getValue();
				String FileType = e.getChild("FileType").getValue();
				
				String sql1 = "insert into W_SPCMDR values ('"+zcxx01+"','"+SPCMDR01+"','"+CompanyCode+"','"+GuildCode+"','"+PositionCode+"','"+TraceNum+"','"+FileType+"','"+DRSJ+"')";
				Map row=getRow(sql1, null, 0);
				execSQL(o2o, sql1, row);
				
				//一次读取所以head结点
				//System.out.println(e.getNodeName()+":"+e.getTextContent());
				//分别按标签读取一个结点
				System.out.println( e.getChild("CompanyCode").getName()+":"+e.getChild("CompanyCode").getText());
				System.out.println( e.getChild("GuildCode").getName()+":"+e.getChild("GuildCode").getText());
				System.out.println( e.getChild("PositionCode").getName()+":"+e.getChild("PositionCode").getText());
				System.out.println( e.getChild("TraceNum").getName()+":"+e.getChild("TraceNum").getText());
				System.out.println( e.getChild("FileType").getName()+":"+e.getChild("FileType").getText());
				
				//获取Body 
				Element Body = root.getChild("Body");
	            if(e.getChild("FileType").getText().equals("0")) {
	            	Element PartsList = Body.getChild("PartsList");
	    			List Part = PartsList.getChildren("Part");
	    			for(int i=0;i<Part.size();i++){
	    				//读取Relation节点的元素
	    				Element e_body = (Element) Part.get(i); 
	    				String Projectcode = e_body.getChild("Projectcode").getValue();
	    				
	    				String spxxsql = "select spxx01 from w_goods where barcode='"+Projectcode+"' and zcxx01='"+zcxx01+"'";
	    				Map spxxmap = queryForMap(o2o, spxxsql);
	    				String spxx01 = spxxmap.get("spxx01").toString();
	    				
	    				String DJBH = SPCMDR01;
	    				String DJLX = "1";
	    				String TraceCode = e_body.getChild("TraceCode").getValue();
	    				String TraceState = e_body.getChild("TraceState").getValue();
	    				String PackageLevel = e_body.getChild("PackageLevel").getValue();
	    				String ActualServiceTime = e_body.getChild("ActualServiceTime").getValue();
	    				String ActualServicePerson  = e_body.getChild("ActualServicePerson").getValue();
	    				String DeliveryCompanyCode = e_body.getChild("DeliveryCompanyCode").getValue();
	    				//String DeliveryCompanyName = e_body.getChild("DeliveryCompanyName").getValue();
	    				String DeliveryCompanyName = null;
	    				String sql2 = "insert into W_SPCMJLB values ('"+zcxx01+"','"+DJBH+"','"+DJLX+"','"+spxx01+"','"+Projectcode+"','"+TraceCode+"','"+ActualServiceTime+"','"+ActualServicePerson+"','"+DeliveryCompanyCode+"','"+DeliveryCompanyName+"')";
	    				Map row1=getRow(sql2, null, 0);
	    				execSQL(o2o, sql2, row1);
	           	 	}
    				hm.put("state", 1);
	            } else if (e.getChild("FileType").getText().equals("1")) {
	            	Element TraceCodeRelationList = Body.getChild("TraceCodeRelationList");
	    			Element TraceCodeRelation = TraceCodeRelationList.getChild("TraceCodeRelation");
	    			List Relation = TraceCodeRelation.getChildren("Relation");

	                //List Relation = root.getChild("Body").getChild("TraceCodeRelationList").getChild("TraceCodeRelation").getChildren("Relation");
	                for(int i=0;i<Relation.size();i++){
	    				//读取Relation节点的元素
	    				Element e_body = (Element) Relation.get(i); 
	    				System.out.println("cascade:"+e_body.getAttribute("cascade").getValue());
	    				System.out.println("projectCode:"+e_body.getAttribute("projectCode").getValue());
	    				System.out.println("packageSpec:"+e_body.getAttribute("packageSpec").getValue());
	    				System.out.println("comment:"+e_body.getAttribute("comment").getValue());
	    				//获取并循环 TraceCode节点
	    				String projectCode = e_body.getAttribute("projectCode").getValue();
	    				
	    				String spxxsql = "select spxx01 from w_goods where barcode='"+projectCode+"' and zcxx01='"+zcxx01+"'";
	    				Map spxxmap = queryForMap(o2o, spxxsql);
	    				if(spxxmap != null) {
	    					String spxx01 = spxxmap.get("spxx01").toString();
		    				
		    				for(int j=0;j<e_body.getChildren("TraceCode").size();j++)
		    				{
		    					//获取并循环 TraceCode节点元素
		    					Element e_bodys = (Element)e_body.getChildren("TraceCode").get(j);
		    					String DJBH = SPCMDR01;
		        				String DJLX = "1";
		        				String curCode = null;
		        				String packLevel = null;
		        				String parentCode = null;
		    					//获取并循环 TraceCode节点元素
		           			    for(int k=0;k<e_bodys.getAttributes().size();k++) {
		           			    	Attribute traceCodeAttr = (Attribute) e_bodys.getAttributes().get(k);
		           			    	System.out.println(traceCodeAttr.getName()+":"+traceCodeAttr.getValue());
		           			    	if(traceCodeAttr.getName().equals("curCode")) {
		           			    		curCode = traceCodeAttr.getValue();
		           			    	} else if(traceCodeAttr.getName().equals("packLevel")) {
		           			    		packLevel = traceCodeAttr.getValue();
		           			    	} else if(traceCodeAttr.getName().equals("parentCode")) {
		           			    		parentCode = traceCodeAttr.getValue();
		           			    	}
		           			    }		
		           			    String sql2 = "insert into W_SPCMBZ values ('"+zcxx01+"','"+DJBH+"','"+DJLX+"','"+spxx01+"','"+projectCode+"','"+curCode+"','"+parentCode+"','"+packLevel+"')";
		        				Map row1=getRow(sql2, null, 0);
		        				execSQL(o2o, sql2, row1);
		    				}
	    				}
	    				
        				hm.put("state", 1);

	           	 	}           
	            }
			}
			 
        } catch (Exception e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } 
        return hm;
    }
	
	//解析xml格式字符串（厂商识别码xml解析）
	public List xmlcssbmElements(String str) {
		List list = new ArrayList();
		
        try {
            //创建一个新的字符串
            StringReader read = new StringReader(str);
            //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            //创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
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
            	Map map = new HashMap(); 
                et = (Element) jiedian.get(i);//循环依次得到子元素
                map.put("firm_name", et.getChild("firm_name",ns).getText());
                map.put("code", et.getChild("code",ns).getText());
                System.out.println(et.getChild("firm_name",ns).getText());
                System.out.println(et.getChild("code",ns).getText());
                list.add(map);
            }
            //查询字段名称
            /*
            et = (Element) jiedian.get(0);
            List zjiedian = et.getChildren();
            for(int j=0;j<zjiedian.size();j++){
                Element xet = (Element) zjiedian.get(j);
                System.out.println(xet.getName());
            }
            */
        } catch (Exception e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } 
        return list;
    }
}