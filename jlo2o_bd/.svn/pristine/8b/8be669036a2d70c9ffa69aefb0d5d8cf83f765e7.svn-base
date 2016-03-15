package com.jlsoft.o2o.product.service;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JLQuery;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.order.service.PublicZSXT;
import com.jlsoft.o2o.order.service.RequestOauthUtil;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;

@Controller
@RequestMapping("/XmlUploadProduct")
public class XmlUploadProduct extends JLBill{
	private SqlSession session=null;
	JLTools tool = new JLTools();
	PublicZSXT zs =new PublicZSXT();
	String path = JlAppResources.getProperty("BarcodeMG");
	/**
	 * @todo 基础商品导入
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertXmlUpload")
	public  Map<String,Object> insertXmlUpload(HttpServletRequest request, String XmlData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String filepath = "";
		Map hm = new HashMap();
		try{
			cds = new DataSet(XmlData);
			String gsid = cds.getField("gsid", 0);
			String xmlType = cds.getField("xmlType", 0);
			hm.put("gsid", gsid);
			JSONArray jsonList = JSONArray.fromObject(XmlData);
			List<HashMap<String, Object>> listMap = (List<HashMap<String, Object>>) jsonList;
			hm =(Map<String, Object>)listMap.get(0);
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			List<MultipartFile> listFile= mrRequest.getFiles("files");
			//上传导入文件
			hm = JLTools.uploadXml(request, hm,listFile);
			filepath = hm.get("filepath").toString();
			filepath = filepath.replace("\\", "/");
			//解析上传文件
			if(xmlType.equals("1")){
				//商品基础上传
				resultMap = uploadForSPTM(filepath);
			}else if(xmlType.equals("2")){
				//上传商品串码
				resultMap = uploadForSPCM(filepath);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("state", 2);
			throw ex;
		}finally{
			//删除上传文件
			JLTools.deleteXml(filepath);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @param request
	 * @param XmlData
	 * @return  state 0不匹配  1 不存在   2已包装   3成功 
	 * @throws Exception
	 */
	@RequestMapping("/query")
	public  Map<String,Object> query(HttpServletRequest request, String XmlData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		cds = new DataSet(XmlData);
		String zcxx01 = cds.getField("zcxx01", 0);
		String cpmc = cds.getField("cpmc", 0);
		String prevCode = cds.getField("prevcode", 0); 
		String curCode = cds.getField("code", 0);
		resultMap.put("cpmc", cpmc);
		
		try{
			String barcode = curCode.substring(5,18);
			
			if(prevCode!=null && !"".equals(prevCode)){
				if(!prevCode.substring(0,32).equals(curCode.substring(0,32))){
					resultMap.put("state", 0);
					return resultMap;
				}
			}
			
			String mongoRet = getMongoRet(curCode);  //0表示存在，1表示不存在
			if("0".equals(mongoRet)){
				//是否已包装
				String query = "select count(1) from w_spcmbz where parentCode is not null and packLevel=0 "
						+ "and zcxx01 ='"+zcxx01+"' and curCode='"+curCode+"'";
				int a = queryForInt(o2o, query);
				
				if(a>0){ //已包装
					resultMap.put("state", 2);
				}else{
					resultMap.put("state", 3);
				}
			}else{
				resultMap.put("state", 1);
			}
			
			//获取名称
			if("3".equals(resultMap.get("state").toString()) && "".equals(cpmc)){
				String spxxsql = "select a.spxx04 from w_goods b,w_spxx a where b.spxx01=a.spxx01 and b.barcode='"+barcode+"' and b.zcxx01='"+zcxx01+"'";
				Map spxxmap = queryForMap(o2o, spxxsql);
				if(spxxmap != null){
					resultMap.put("cpmc", spxxmap.get("spxx04"));
					resultMap.put("drsj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}else{
					resultMap.put("state", 4);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("state", 5);
		}
		
		return resultMap;
	}
	
	/**
	 * 串码是否存在
	 * @param code
	 * @return
	 */
	private String getMongoRet(String code){
		String address = path + "/GetgoodsCode.json";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("goodsCode", code);
		Map zsmap = new HashMap();
		zsmap.put("content", jsonObject.toString());
		String reponseString = RequestOauthUtil.postData(address, null,
				zsmap, "POST");
		JSONObject jsonObj = JSONObject.fromObject(reponseString);
		String resultCode = (String) jsonObj.get("resultCode");
		System.out.println("resultCode:　" + resultCode);
		return resultCode;
	}
	
	@RequestMapping("/insertMakeCode")
	public  Map<String,Object> insertMakeCode(HttpServletRequest request, String XmlData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		cds = new DataSet(XmlData);
		
		String zcxx01 = cds.getField("zcxx01", 0);
		String codes = cds.getField("codes", 0);
		String drsj = cds.getField("drsj", 0);
		
		if(codes!=null && codes.split(",").length>0){
			try {
				String[] codesArr = codes.split(",");
				String firstCode = codesArr[0].trim();
				
				String query = "select count(1) from w_spcmbz where parentCode is not null and packLevel=0 "
						+ "and zcxx01 ='"+zcxx01+"' and curCode='"+firstCode+"'";
				int a = queryForInt(o2o, query);
				if(a>0){ //已包装
					resultMap.put("state", 3);
					return resultMap;
				}
				
				int size = codesArr.length;
				//获取位置码、行业管理码等
				String sql = "select zcgs.hyglm,zcgs.wzm,zcgssbm.sbm from w_zcgs zcgs left join w_zcgssbm zcgssbm"
						+ " on(zcgs.zcxx01=zcgssbm.zcxx01) where zcgs.zcxx01='"+zcxx01+"'";
				Map zcxxMap = queryForMap(o2o, sql);
				
				String hyglm = "";
				String positionCode = "";
				String companyCode = "";
				if(zcxxMap != null){
					hyglm = zcxxMap.get("hyglm") == null?"":zcxxMap.get("hyglm").toString();
					positionCode = zcxxMap.get("wzm")== null?"":zcxxMap.get("wzm").toString();
					companyCode = zcxxMap.get("sbm")== null?"":zcxxMap.get("sbm").toString();
				}
				
				String drlx = "1";
				int spcmdr01 = PubFun.updateWBHZT(o2o,"W_SPCMDR",1);
				
				String sql1 = "insert into W_SPCMDR(ZCXX01,SPCMDR01,SBM,HYGLM,WZM,DRSL,DRLX,DRRQ) " +
				         "values ('"+zcxx01+"','"+spcmdr01+"','"+companyCode+"','"+hyglm+"','"+positionCode+"','"+size+"','"+drlx+"','"+drsj+"')";
				Map row=getRow(sql1, null, 0);
				execSQL(o2o, sql1, row);
				
				String djlx = "1";
				String barcode = firstCode.substring(5,18);
				
				sql = "select spxx01 from w_goods where barcode='"+barcode+"' and zcxx01='"+zcxx01+"'";
				Map spxxmap = queryForMap(o2o, sql);
				
				String spxx01 = "";
				if(spxxmap != null) {
					spxx01 = spxxmap.get("spxx01").toString();
				}
				int packLevel = 0;
				int firstPackLevel = 1;
				String parentCode = getParentCode(firstCode);
				for (int i = 0; i < size; i++) {
					String curCode = codesArr[i].trim();
					sql = "insert into W_SPCMBZ(ZCXX01,DJBH,DJLX,spxx01,barcode,curCode,parentCode,packLevel) " +
							" values ('"+zcxx01+"','"+spcmdr01+"','"+djlx+"','"+spxx01+"','"+barcode+"','"+curCode+"','"+parentCode+"','"+packLevel+"')";
				    execSQL(o2o, sql, new HashMap());
				}
				sql = "insert into W_SPCMBZ(ZCXX01,DJBH,DJLX,spxx01,barcode,curCode,packLevel) " +
						" values ('"+zcxx01+"','"+spcmdr01+"','"+djlx+"','"+spxx01+"','"+barcode+"','"+parentCode+"','"+firstPackLevel+"')";
			    execSQL(o2o, sql, new HashMap());
			    resultMap.put("pCode", parentCode); 
			    resultMap.put("state", 1);  //success
			} catch (Exception e) {
				resultMap.put("state", 2);
			}
		}
		
		return resultMap;
	}
	
	private String getParentCode(String code){
		int ranNumber = new Random().nextInt(99999);
		int index = code.lastIndexOf("(21)");
		String result = code.substring(0,index+4) + ranNumber + "(9999)01"; //01写死
		return result;
	}
	
	public static void main(String[] args) {
		int ranNumber = new Random().nextInt(99999);
		String str = "(01)06937478810104(10)(21)40001(9999)00";
		int index = str.lastIndexOf("(21)");
//		System.out.println(index);
//		System.out.println(str.substring(0,index+4));
//		String ss =  getParentCode(str);
//		System.out.println(ss);
	}
	
	/**
	 * @todo 导入商品基本信息
	 * @param request
	 * @param XmlData
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object> uploadForSPTM(String filepath) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			File file = new File(filepath);
        	SAXBuilder reader = new SAXBuilder();   
        	Document doc=(Document) reader.build(file);
            //取的根元素
            Element root = doc.getRootElement();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String DRSJ = df.format(new Date()).toString();
			
			//获取head 
            List head = root.getChildren("Head");
            Element e = (Element) head.get(0);

            String CompanyCode = e.getChild("CompanyCode").getValue();
			String hyglm = e.getChild("GuildCode").getValue();
			String PositionCode = e.getChild("PositionCode").getValue();
			
			String sql = "select zcxx01 from w_zcgs where HYGLM='"+hyglm+"'";
			Map zcxxMap = queryForMap(o2o, sql);
			if(zcxxMap == null) {
				resultMap.put("state", 0);
				return resultMap;
			}else{
				String zcxx01 = zcxxMap.get("zcxx01").toString();
				int SPDR01=PubFun.updateWBHZT(o2o,"W_SPDR",1);
				
				String W_SPDRsql = "insert into W_SPDR(ZCXX01,SPDR01,SBM,HYGLM,WZM,DRRQ) " +
												"values ('"+zcxx01+"','"+SPDR01+"','"+CompanyCode+"','"+hyglm+"','"+PositionCode+"','"+DRSJ+"')";
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
    					Integer SPXX01=PubFun.updateWBHZT(o2o,"W_SPXX",1);
	    				
	    				String sql1 = "insert into w_goods (barcode, spxx04, spxx01, zcxx01, sbm, jldw, Modal) values ('"+Projectcode+"','"+MateriaName+"','"+SPXX01+"','"+zcxx01+"','"+CompanyCode+"','"+UnitName+"','"+Modal+"')";
	    				Map row1=getRow(sql1, null, 0);
	    				execSQL(o2o, sql1, row1);
	    				
	    				String W_SPDRITEMsql = "insert into W_SPDRITEM(ZCXX01,SPDR01,barcode,spxx04,SPECIFICATION,jldw) " +
	    														"values ('"+zcxx01+"','"+SPDR01+"','"+Projectcode+"','"+MateriaName+"','"+Modal+"','"+UnitName+"')";
	    				Map W_SPDRITEMrow=getRow(W_SPDRITEMsql, null, 0);
	    				execSQL(o2o, W_SPDRITEMsql, W_SPDRITEMrow);
    				}
           	 	}
    			//获取返回参数
    			resultMap.put("ZCXX01", zcxx01);
    			resultMap.put("SPDR01", new Integer(SPDR01));
    			resultMap.put("state", 1);
			}
		}catch(Exception ex){
			resultMap.put("state", 2);
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 上传商品串码
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object> uploadForSPCM(String filepath) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			boolean flag=false;
			File file = new File(filepath);
        	SAXBuilder reader = new SAXBuilder();   
        	Document doc=(Document) reader.build(file);
            //取的根元素
            Element root = doc.getRootElement();
            List head = root.getChildren("Head");
            Element e = (Element) head.get(0);
            String CompanyCode = e.getChild("CompanyCode").getValue();  //厂商识别码   特别备注：系统存放表w_zcgssbm 现暂未处理
			String hyglm = e.getChild("GuildCode").getValue();                      //行业管理码
			String PositionCode = e.getChild("PositionCode").getValue();       //位置码
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String DRSJ = df.format(new Date()).toString();
			
			String sql = "select zcxx01 from w_zcgs where HYGLM='"+hyglm+"'";
			Map zcxxMap = queryForMap(o2o, sql);
			if(zcxxMap == null){
				resultMap.put("state", 0);
				return resultMap;
			}else{
				String zcxx01 = zcxxMap.get("zcxx01").toString();
				int SPCMDR01=PubFun.updateWBHZT(o2o,"W_SPCMDR",1);
				//导入文件类型（0：表示上传追溯信息 1：表示上传包装关系）
				String FileType = e.getChild("FileType").getValue();
				String TraceNum = e.getChild("TraceNum").getValue(); //上传数量
				//导入主表
				String sql1 = "insert into W_SPCMDR(ZCXX01,SPCMDR01,SBM,HYGLM,WZM,DRSL,DRLX,DRRQ) " +
						             "values ('"+zcxx01+"','"+SPCMDR01+"','"+CompanyCode+"','"+hyglm+"','"+PositionCode+"','"+TraceNum+"','"+FileType+"','"+DRSJ+"')";
				Map row=getRow(sql1, null, 0);
				execSQL(o2o, sql1, row);
				//导入串码流水表
				if(FileType.equals("0")){
					insertW_SPCMJLB(root,FileType,zcxx01,SPCMDR01);
				}
				//导入包装关系
				if(FileType.equals("1")){
					flag=insertW_SPCMBZ(root,FileType,zcxx01,SPCMDR01);
				}
				if(flag)
				{
					resultMap.put("DJBH", new Integer(SPCMDR01));
					resultMap.put("state", 1);
				}else
				{
					resultMap.put("DJBH", new Integer(SPCMDR01));
					resultMap.put("state", 3);
				}
			}
		}catch(Exception ex){
			resultMap.put("state", 2);
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入串码流水记录表
	 * @param root
	 * @param FileType （0：表示上传追溯信息 1：表示上传包装关系）
	 * @param zcxx01
	 * @param SPCMDR01
	 * @throws Exception 
	 */
	public void insertW_SPCMJLB(Element root,String FileType,String zcxx01,int SPCMDR01) throws Exception{
		String sql = "";
		try{
			Element Body = root.getChild("Body");
			Element PartsList = Body.getChild("PartsList");
			List Part = PartsList.getChildren("Part");
			for(int i=0;i<Part.size();i++){
				Element e_body = (Element) Part.get(i); 
				String barcode = e_body.getChild("Projectcode").getValue(); //商品条码
				String spxxsql = "select spxx01 from w_goods where barcode='"+barcode+"' and zcxx01='"+zcxx01+"'";
				Map spxxmap = queryForMap(o2o, spxxsql);
				String spxx01 = spxxmap.get("spxx01").toString();
				int DJBH = SPCMDR01;
				String DJLX = "1";
				String spcm01 = e_body.getChild("TraceCode").getValue(); //商品串码
				String cmzt = e_body.getChild("TraceState").getValue();//串码状态
				String PackageLevel = e_body.getChild("PackageLevel").getValue();//包装级别
				String cmclsj = e_body.getChild("ActualServiceTime").getValue();//实际业务处理时间
				String cmclry  = e_body.getChild("ActualServicePerson").getValue();//实际业务操作人员
				String ckqydm = e_body.getChild("DeliveryCompanyCode").getValue();//出库到对方企业代码
				String ckqymc = e_body.getChild("DeliveryCompanyName").getValue();//出库到对方企业名称
				sql = "insert into W_SPCMJLB(ZCXX01,DJBH,DJLX,spxx01,barcode,spcm01,cmclsj,cmclry,ckqydm,ckqymc,cmzt) " +
						"values ('"+zcxx01+"','"+DJBH+"','"+DJLX+"','"+spxx01+"','"+barcode+"','"+spcm01+"','"+cmclsj+"','"+cmclry+"','"+ckqydm+"','"+ckqymc+"','"+cmzt+"')";
				Map row1=getRow(sql, null, 0);
				execSQL(o2o, sql, row1);
				//判断是否是生成企业导入，如果是则需要插入W_SPCM表
				if(FileType.equals("0") && cmzt.equals("1010")){
					sql = "SELECT COUNT(0) FROM W_SPCM WHERE ZCXX01='"+zcxx01+"' AND SPXX01="+spxx01+" AND SPCM01='"+spcm01+"'";
					int cmNm = queryForInt(o2o,sql);
					if(cmNm == 0){
						String pc=spcm01.substring(JLTools.getStrSplitNum(spcm01,"(",2)+1, JLTools.getStrSplitNum(spcm01,")",2));
						String xlh=spcm01.substring(JLTools.getStrSplitNum(spcm01,"(",3)+1, JLTools.getStrSplitNum(spcm01,")",3));
						sql = "INSERT INTO W_SPCM(ZCXX01,SPXX01,SPCM01,BARCODE,SPCM02,SPCM03,SPCM04) " +
								 "VALUES('"+zcxx01+"',"+spxx01+",'"+spcm01+"','"+barcode+"',now(),'"+pc+"','"+xlh+"')";
						execSQL(o2o, sql, row1);
					}
				}
			}
			
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入包装关系
	 * @param root
	 * @throws Exception
	 */
	public boolean insertW_SPCMBZ(Element root,String FileType,String zcxx01,int SPCMDR01) throws Exception{
		String sql = "";
		String sqlSPCM="";
		boolean flag=true;
		try{
			Element Body = root.getChild("Body");
			Element TraceCodeRelationList = Body.getChild("TraceCodeRelationList");
			Element TraceCodeRelation = TraceCodeRelationList.getChild("TraceCodeRelation");
			List Relation = TraceCodeRelation.getChildren("Relation");  //包装信息，有：包装比例，包装零件名称等
			for(int i=0;i<Relation.size();i++){
				//读取Relation节点的元素
				Element e_body = (Element) Relation.get(i); 
				//获取并循环 TraceCode节点
				String barcode = e_body.getAttribute("projectCode").getValue();
				sql = "select spxx01 from w_goods where barcode='"+barcode+"' and zcxx01='"+zcxx01+"'";
				Map spxxmap = queryForMap(o2o, sql);
				if(spxxmap != null) {
					String spxx01 = spxxmap.get("spxx01").toString();
					//获取包装关系明细
					for(int j=0;j<e_body.getChildren("TraceCode").size();j++){
						//获取并循环 TraceCode节点元素
    					Element e_bodys = (Element)e_body.getChildren("TraceCode").get(j);
    					int DJBH = SPCMDR01;
        				String DJLX = "1";
        				String curCode = null;                   //当前串码
        				String packLevel = null;                 //包装级别
        				String parentCode = null;              //父节点串码

        				//获取并循环 TraceCode节点元素
           			    for(int k=0;k<e_bodys.getAttributes().size();k++) {
           			    	Attribute traceCodeAttr = (Attribute) e_bodys.getAttributes().get(k);
           			    	if(traceCodeAttr.getName().equals("curCode")) {
           			    		curCode = traceCodeAttr.getValue();
           			    	} else if(traceCodeAttr.getName().equals("packLevel")) {
           			    		packLevel = traceCodeAttr.getValue();
           			    	} else if(traceCodeAttr.getName().equals("parentCode")) {
           			    		parentCode = traceCodeAttr.getValue();
           			    	}
           			    }
           			    //判断当前包装行中：串码和父节点包装串码是否存在
           			    String query="select count(1) from W_SPCMBZ where curCode='"+curCode+"'";
           			    int count = queryForInt(o2o, query);
           			    if(count==0){
            				sql = "insert into W_SPCMBZ(ZCXX01,DJBH,DJLX,spxx01,barcode,curCode,parentCode,packLevel) " +
            						" values ('"+zcxx01+"','"+DJBH+"','"+DJLX+"','"+spxx01+"','"+barcode+"','"+curCode+"','"+parentCode+"','"+packLevel+"')";
               			    execSQL(o2o, sql, new HashMap());
           			    }else{
           			    	flag=false;
           			    	break;
           			    }
					}
					//追溯系统对接 -- 包装导入不对接
					/**
					if(tool.getCurConf(4)==1){
						//追溯地址
						String address=path+"/AddProduceCollection.json";
						sqlSPCM=sqlSPCM.substring(0, sqlSPCM.length()-1);
						String query="SELECT SPCM01, SPXX04, SPCM03, SPCM02,ZCXX02,ZCXX08,ZCXX55,barcode,(SELECT SPTP02 FROM w_sptp WHERE SPXX01 = w_spxx.SPXX01 AND sptp01 = '1') SPTP FROM w_spcm LEFT JOIN w_spxx ON w_spxx.spxx01 = w_spcm.spxx01 LEFT JOIN w_zcxx ON w_zcxx.zcxx01 = w_spcm.zcxx01 where spcm01 in ("+sqlSPCM+")";
						List list = queryForList(o2o, query);
						zs.rk(list,address,"0");
					}
					*/
				}	
			}	
			return flag;
		}catch(Exception ex){
			throw ex;
		}
	}
	/**
	 * @todo 出库单
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertXmlCKD")
	public  Map<String,Object> insertXmlCKD(HttpServletRequest request, String XmlData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String filepath = "";
		Map hm = new HashMap();
		try{
			cds = new DataSet(XmlData);
			String gsid = cds.getField("gsid", 0);
			String xmlType = cds.getField("xmlType", 0);
			hm.put("gsid", gsid);
			JSONArray jsonList = JSONArray.fromObject(XmlData);
			List<HashMap<String, Object>> listMap = (List<HashMap<String, Object>>) jsonList;
			hm =(Map<String, Object>)listMap.get(0);
			MultipartHttpServletRequest mrRequest=(MultipartHttpServletRequest)request;
			List<MultipartFile> listFile= mrRequest.getFiles("files");
			//上传导入文件
			hm = JLTools.uploadXml(request, hm,listFile);
			System.out.println(hm);
			Object filepathobj=hm.get("filepath");
			if(filepathobj==null||filepathobj==""){
				resultMap.put("state", 2);
				return resultMap;
			}
			filepath = hm.get("filepath").toString();
			filepath = filepath.replace("\\", "/");
			//解析上传文件
			if(xmlType.equals("1")){
				//商品基础上传
				resultMap = uploadForSPTM(filepath);
			}else if(xmlType.equals("2")){
				//上传商品串码
				resultMap = uploadForCKD(filepath);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resultMap.put("state", 2);
			throw ex;
		}finally{
			//删除上传文件
			JLTools.deleteXml(filepath);
		}
		return resultMap;
	}

	/**
	 * @todo 出库单数据
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  Map<String,Object> uploadForCKD(String filepath) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			File file = new File(filepath);
        	SAXBuilder reader = new SAXBuilder();   
        	Document doc=(Document) reader.build(file);
            //取的根元素
            Element root = doc.getRootElement();
            List head = root.getChildren("Head");
            Element e = (Element) head.get(0);
            String CompanyCode = e.getChild("CompanyCode").getValue();  //厂商识别码   特别备注：系统存放表w_zcgssbm 现暂未处理
			String hyglm = e.getChild("GuildCode").getValue();                      //行业管理码
			String PositionCode = e.getChild("PositionCode").getValue();       //位置码
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String DRSJ = df.format(new Date()).toString();
			String sql = "select zcxx01 from w_zcgs where HYGLM='"+hyglm+"'";
			Map zcxxMap = queryForMap(o2o, sql);
			if(zcxxMap == null){
				resultMap.put("state", 0);//行业管理码不符
				return resultMap;
			}else{
				String zcxx01 = zcxxMap.get("zcxx01").toString();
				//主数据
				resultMap.put("CompanyCode", CompanyCode);
				resultMap.put("GuildCode", hyglm);
				resultMap.put("PositionCode", PositionCode);
				resultMap.put("ZCXX01", zcxx01);
				resultMap.put("DRSJ", DRSJ);
				//int CKDH=PubFun.updateWBHZT(o2o,"W_CKD",1);
				//导入文件类型（0：表示上传追溯信息 1：表示上传包装关系）
				String FileType = e.getChild("FileType").getValue();
				String TraceNum = e.getChild("TraceNum").getValue(); //上传数量
				resultMap.put("FileType", FileType);
				resultMap.put("TraceNum", TraceNum);
				//导入串码流水表
				if(FileType.equals("0")){
					List list=insertW_CKDITEM(root,FileType,zcxx01);
					Map returnMaps=new HashMap();
					returnMaps=(HashMap)list.get(0);
					if(returnMaps.get("state")=="3"){
						resultMap.put("barcode",returnMaps.get("barcode"));
						resultMap.put("state", 3);//商品条码在平台中不存在，或者导入的XML数据不准确
						return resultMap;
					}else if(returnMaps.get("state")=="4"){
						resultMap.put("isExitList", returnMaps.get("isExitList"));
						resultMap.put("state", 4);//商品串码已导入
						return returnMaps;
					}else if(returnMaps.get("state")=="5"){
						resultMap.put("spcm01",returnMaps.get("spcm01"));
						resultMap.put("state", 5);//商品串码有误
						return resultMap;
					}
					resultMap.put("CKDITEM", list);
				}
				//导入包装关系
				if(FileType.equals("1")){
					int CKDH=0;
					boolean flag = insertW_SPCMBZ(root,FileType,zcxx01,CKDH);
					if(flag==false){
						throw new Exception("导入串码重复!");
					}
				}
				resultMap.put("state", 1);
			}
		}catch(Exception ex){
			resultMap.put("state", 2);
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入串码流水记录表
	 * @param root
	 * @param FileType （0：表示上传追溯信息 1：表示上传包装关系）
	 * @param zcxx01
	 * @param SPCMDR01
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List insertW_CKDITEM(Element root,String FileType,String zcxx01) throws Exception{
		List list=new ArrayList();
		List lists=new ArrayList();
		ArrayList barcodeList = new ArrayList();
		ArrayList cmList = new ArrayList();
		try{
			Element Body = root.getChild("Body");
			Element PartsList = Body.getChild("PartsList");
			List Part = PartsList.getChildren("Part");
			
			Element e_body1=null;
			HashMap maps=null;
			boolean find = false;
			Map map=new HashMap();
			for(int i=0;i<Part.size();i++){
				//基础数据
				Element e_body = (Element) Part.get(i); 
				String barcode = e_body.getChild("Projectcode").getValue(); //商品条码
				String spxxsql = "select spxx01,spxx04 from w_goods where barcode='"+barcode+"' and zcxx01='"+zcxx01+"'";
				Map spxxmap = queryForMap(o2o, spxxsql);
				if(spxxmap == null){
					Map returnMap=new HashMap();
					returnMap.put("barcode",barcode);
					returnMap.put("state","3");
					list.add(returnMap);
					return list;
				}
				String spxx01 = spxxmap.get("spxx01").toString();
				String spxx04 = spxxmap.get("spxx04").toString();
				String spxx02sql = "select spxx02 from w_spxx where spxx01='"+spxx01+"'";
				Map spxx02map = queryForMap(o2o, spxx02sql);
				String spxx02 = spxx02map.get("spxx02").toString();
				String spcm01 = e_body.getChild("TraceCode").getValue(); //商品串码
				String cmzt = e_body.getChild("TraceState").getValue();//串码状态
				String PackageLevel = e_body.getChild("PackageLevel").getValue();//包装级别
				String cmclsj = e_body.getChild("ActualServiceTime").getValue();//实际业务处理时间
				String cmclry  = e_body.getChild("ActualServicePerson").getValue();//实际业务操作人员
				String ckqydm = e_body.getChild("DeliveryCompanyCode").getValue();//出库到对方企业代码
				String ckqymc = e_body.getChild("DeliveryCompanyName").getValue();//出库到对方企业名称
				String xlh = "";
				String pc = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",2)+1, JLTools.getStrSplitNum(spcm01,"(",3));
				if(JLTools.getStringRepeatShowNum(spcm01,"(") == 4){
					xlh = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",3)+1, JLTools.getStrSplitNum(spcm01,"(",4));
				}else{
					Map returnMap=new HashMap();
					returnMap.put("spcm01",spcm01);
					returnMap.put("state","5");
					list.add(returnMap);
					return list;
				}
				//仓库数量处理，数据处理
				int num=1;
				e_body1 = (Element) Part.get(i);
				for(int j=0;j<lists.size();j++){
					//String barcodes=e_body1.getChild("Projectcode").getValue(); //商品条码
					maps=(HashMap) lists.get(j);
					if(maps.get("barcode").toString().equals(barcode)){
						//判断如果是包装则查询包装表的数量
						if(spcm01.substring(spcm01.length()-2, spcm01.length()).equals("00"))
						{
							num=Integer.parseInt(maps.get("num").toString())+1;
						}else
						{
							String query = "select count(1) from w_spcmbz where parentCode ='"+spcm01+"'";
							int a =queryForInt(o2o, query);
							num=Integer.parseInt(maps.get("num").toString())+a;
						}
						String spcm=maps.get("spcm01").toString()+","+spcm01;
						maps.put("spcm01",spcm);
						maps.put("num",num);
						find = true;
						break;
					}
				}
				if(!find){
					Map tempMap=new HashMap();
					tempMap.put("barcode", barcode);
					//判断如果是包装则查询包装表的数量
					if(spcm01.substring(spcm01.length()-2, spcm01.length()).equals("00"))
					{
						num=1;
					}else
					{
						String query = "select count(1) from w_spcmbz where parentCode ='"+spcm01+"'";
						int a =queryForInt(o2o, query);
						num=a;
					}
					tempMap.put("num",num);
					tempMap.put("SPXX01",spxx01);
					tempMap.put("SPXX02",spxx02);
					tempMap.put("SPXX04",spxx04);
					tempMap.put("spcm01",spcm01);
					tempMap.put("cmclsj",cmclsj);
					tempMap.put("cmclry",cmclry);
					tempMap.put("ckqydm",ckqydm);
					tempMap.put("ckqymc",ckqymc);
					tempMap.put("pc", pc);
					tempMap.put("xlh", xlh);
					tempMap.put("ckzt",new Integer(0));
					lists.add(tempMap);
				}
				find=false;
				map.put("spcmList", lists);
				//插入W_CKDCM需要记录每一条数据
				Map tempMap=new HashMap();
				tempMap.put("barcode", barcode);
				//判断如果是包装则查询包装表的数量
				if(spcm01.substring(spcm01.length()-2, spcm01.length()).equals("00"))
				{
					num=1;
					cmList.add(spcm01);//当节点是最终串码时直接存入
				}else{
					String query = "select count(1) from w_spcmbz where parentCode ='"+spcm01+"'";
					int a =queryForInt(o2o, query);
					num=a;
					//查询串码在包装中具体明细，cmList用于查询哪些已经入库
					query = "SELECT CURCODE FROM W_SPCMBZ WHERE parentCode='"+spcm01+"'";
					List bzcmList = queryForList(o2o,query);
					Map bzcmMap;
					for(int k=0;k<bzcmList.size();k++){
						bzcmMap = (Map)bzcmList.get(k);
						cmList.add(bzcmMap.get("CURCODE"));
					}
				}
				tempMap.put("num",num);
				tempMap.put("SPXX01",spxx01);
				tempMap.put("SPXX02",spxx02);
				tempMap.put("SPXX04",spxx04);
				tempMap.put("spcm01",spcm01);
				tempMap.put("cmclsj",cmclsj);
				tempMap.put("cmclry",cmclry);
				tempMap.put("ckqydm",ckqydm);
				tempMap.put("ckqymc",ckqymc);
				tempMap.put("cmzt",cmzt);
				tempMap.put("pc", pc);
				tempMap.put("xlh", xlh);
				tempMap.put("ckzt",new Integer(0));
				barcodeList.add(tempMap);
				map.put("barcodeList", barcodeList);
			}
			//查询导入串码哪些是已经出库的
			List isExitCMList=selectW_CKDCM(cmList);
			if(isExitCMList.size()>0){
				Map newMap=new HashMap();
				newMap.put("isExitList",isExitCMList);
				newMap.put("state","4");
				list.add(newMap);
				return list;
			}
			list.add(map);
		}catch(Exception ex){
			throw ex;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List selectW_CKDCM(List cmList) throws Exception {	
		Map map =new HashMap();
		map.put("cmList",cmList);
		String sqlid="com.jlsoft.o2o.product.sql.XmlUploadProduct.selectW_CKDCM";
		List cmlist=(List) JLQuery.queryForListByXML("o2o",sqlid,map);
		return cmlist;
	}
	
	
	/**
	 * @tod 包装导入JSON对接取值，用于跟燕总对接
	 * @author asus    http://127.0.0.1:8088/XmlUploadProduct/insertW_SPCMDR.action?json=
	 * [{"CompanyCode":"69319397",
		    		"GuildCode":"91100515",
		    		"PositionCode":"000",
		    		"TraceNum":"10",
		    		"FileType":"1",
		   	 		"DRSJ":"2015-04-30 13:46:06",
		   	 		"BZDRITEM":[
		   	 			{"projectCode":"1234567890123",
		   	 			 "cascade":"1:2",
		   	 			 "packageSpec":"盖总成",
		   	 			 "comment":"",
		   	 				"TraceCode":[
		   	 					{
		   	 					 "curCode":"(01)01234567890123(10)150523(21)A2 1001(9999)01",
		   	 					 "packLevel":"1"
		   	 					},
		   	 					{
		   	 					 "curCode":"(01)01234567890123(10)150523(21)A2 0001(9999)00",
		   	 					 "packLevel":"0",
		   	 					 "parentCode":"(01)01234567890123(10)150523(21)A2 1001(9999)01"
		   	 					},
		   	 					{
		   	 					 "curCode":"(01)01234567890123(10)150523(21)A2 0002(9999)00",
		   	 					 "packLevel":"0",
		   	 					 "parentCode":"(01)01234567890123(10)150523(21)A2 1001(9999)01"
			   	 				}
		   	 				]
		   	 		    }
		   	 	  ]
			}]
	 */
	@RequestMapping("/insertW_SPCMDR.action")
	public Map insertW_SPCMDR(String json) throws Exception {
		cds = new DataSet(json);
		Map returnMap = new HashMap();
		try {
			// 查询企业编码ZCXX01
			String gsSql = "SELECT ZCXX01 FROM W_ZCGS WHERE HYGLM ='"
					+ cds.getField("GuildCode", 0) + "'";
			Map zcxxMap = queryForMap(o2o, gsSql);
			if (zcxxMap == null) {
				returnMap.put("STATE", 0);
				return returnMap;
			} else {
				String zcxx01 = zcxxMap.get("zcxx01").toString();
				int SPCMDR01 = PubFun.updateWBHZT(o2o, "W_SPCMDR", 1);
				// 导入文件类型（0：表示上传追溯信息 1：表示上传包装关系）
				String FileType = cds.getField("FileType", 0); // 文件类型
				String TraceNum = cds.getField("TraceNum", 0); // 上传数量
				String CompanyCode = cds.getField("CompanyCode", 0);// 公司编码
				String hyglm = cds.getField("GuildCode", 0); // 行业管理码
				String PositionCode = cds.getField("PositionCode", 0);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String DRSJ = df.format(new Date()).toString();
				// 导入主表
				String spcmSql = "insert into W_SPCMDR(ZCXX01,SPCMDR01,SBM,HYGLM,WZM,DRSL,DRLX,DRRQ) "
						+ "values ('" + zcxx01 + "','" + SPCMDR01 + "','"
						+ CompanyCode + "','" + hyglm + "','" + PositionCode + "','"
						+ TraceNum + "','" + FileType + "','" + DRSJ + "')";
				Map row = getRow(spcmSql, null, 0);
				execSQL(o2o, spcmSql, row);
				boolean flag = false;
				// 导入包装关系
				if (FileType.equals("1")) {
					String sql = "";
					String BZDRITEM = cds.getField("BZDRITEM", 0);
					JSONArray lists = JSONArray.fromObject(BZDRITEM);
					for (int i = 0; i < lists.size(); i++) {
						Map bzMap = (Map) lists.get(i);
						String barcode = bzMap.get("projectCode").toString(); // 商品条码
						String spxxsql = "select spxx01 from w_goods where barcode='" 
										 + barcode + "' and zcxx01='" + zcxx01 + "'";
						Map spxxmap = queryForMap(o2o, spxxsql);
						// 如果存在商品信息
						if (spxxmap != null) {
							String spxx01 = spxxmap.get("spxx01").toString();
							List traceLists = (List) bzMap.get("TraceCode");
							for (int j = 0; j < traceLists.size(); j++) {
								Map trancMap = (Map) traceLists.get(j);
								int DJBH = SPCMDR01;
								String DJLX = "1";
								String curCode = null; // 当前串码
								String packLevel = null; // 包装级别
								String parentCode = null; // 父节点串码
								// 获取属性信息
								if (trancMap.get("curCode") != null) {
									curCode = trancMap.get("curCode").toString();
								} 
								if (trancMap.get("packLevel") != null) {
									packLevel = trancMap.get("packLevel").toString();
								} 
								if (trancMap.get("parentCode") != null) {
									parentCode = trancMap.get("parentCode").toString();
								}
								sql = "insert into W_SPCMBZ values ('" + zcxx01
										+ "','" + DJBH + "','" + DJLX + "','"
										+ spxx01 + "','" + barcode + "','"
										+ curCode + "','" + parentCode + "','"
										+ packLevel + "')";
								Map row1 = getRow(sql, null, 0);
		        			    execSQL(o2o, sql, row1);;
							}
							flag = true;
						} else {
							flag = false;
						}
					}
					// 如果商品存在，并且导入成功 返回状态 1 否则返回 3
					if (flag) {
						returnMap.put("STATE", 1);
					} else {
						returnMap.put("STATE", 3);
					}
				}
			}
		} catch (Exception ex) {
			returnMap.put("STATE", "0");
			throw ex;
		}
		return returnMap;
	}
	
	//生成并插入mongodb串码
	@RequestMapping("/insertW_SPCM.action")
	public Map insertW_SPCM(HttpServletRequest request, String XmlData) throws Exception{
		Map map = new HashMap();
		try{
			String address=path+"/AddgoodsCode.json";
			cds = new DataSet(XmlData);
			//商品条码
			String Barcode = cds.getField("Barcode", 0);
			//商品名称
			String GoodsName = cds.getField("GoodsName",0);
			//批次号
			String BatchNumber = cds.getField("BatchNumber",0);
			//公司编码
			String ZCXX01 = cds.getField("ZCXX01", 0);
			//顺序号
			String SerialNumber =  cds.getField("SerialNumber", 0);
			//配件数量
			String GoodsNumber = cds.getField("GoodsNumber", 0);
			//操作人
			String CZR = cds.getField("CZR", 0);
			List list  = new ArrayList();
			String GoodsCode = "";
			String start = "(01)0"+Barcode+"(10)"+BatchNumber+"(21)"+SerialNumber;
			String end = "(9999)00";
			if(!("").equals(GoodsNumber)){
				int gNumber = Integer.valueOf(GoodsNumber);
				int count = 1;
				for(int i=0;i<gNumber;i++){
					if(String.valueOf(count).length()==1){
						list.add(start+"000"+count+""+end);
					}else if(String.valueOf(count).length()==2){
						list.add(start+"00"+count+""+end);
					}else if(String.valueOf(count).length()==3){
						list.add(start+"0"+count+""+end);
					}else if(String.valueOf(count).length()==4){
						list.add(start+""+count+end);
					}else{
						list.add(start+""+count+""+end);
					}
					count++;
				}
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String DRSJ = df.format(new Date()).toString(); 
			//批次号，商品条码
			//---------------------外部------------------------------
			JSONArray jsonList=new JSONArray(); 
			JSONObject jsonObject_ = new JSONObject();
			jsonObject_.put("barcode",Barcode); //外边
			jsonObject_.put("goodsName",GoodsName); //外边
			jsonObject_.put("batchNumber",BatchNumber); //外边
			jsonObject_.put("zcxx01",ZCXX01); //外边
			//顺序号
			//---------------------内部------------------------------
			JSONArray eveList=new JSONArray();
			JSONObject eventjsonObject_ = new JSONObject();
			eventjsonObject_.put("serialNumber", SerialNumber);//内边
			eventjsonObject_.put("goodsNumber", GoodsNumber);//内边
			eventjsonObject_.put("publishPerson",CZR);//内边
			eventjsonObject_.put("publishTime", DRSJ);//内边
			//-------------------------最里面----------------------------------------
			JSONArray eveLists=new JSONArray();
			for(int i=0;i<list.size();i++){
				JSONObject eventjsonObject_s = new JSONObject();
				eventjsonObject_s.put("goodsCode", list.get(i));//内边
				eveLists.add(eventjsonObject_s);
			}
			eventjsonObject_.put("goodsCodeList", eveLists);
			eveList.add(eventjsonObject_);
			jsonObject_.put("operateList", eveList);
			jsonList.add(jsonObject_);
			Map zsmap=new HashMap();
			zsmap.put("content", jsonList.toString());
			String reponseString=RequestOauthUtil.postData(address, null,zsmap,"POST");
			JSONObject jsonObject = JSONObject.fromObject(reponseString);
			String resultCode=(String) jsonObject.get("resultCode");
			if("1".equals(resultCode)){
				map.put("STATE", "1");
			}else{
				map.put("STATE", "0");
			}
			map.put("list",list);
			map.put("STATE", "1");
		}catch (Exception ex) {
			map.put("STATE", "0");
			throw ex;
		}
		return map;	
	}
	//判断顺序号是否存在
	@RequestMapping("/get_SXH.action")
	public Map getSXH(HttpServletRequest request, String XmlData) throws Exception{
		Map map = new HashMap();
		try{
			String address=path+"/GetBarcodeBatchNumber.json";
			cds = new DataSet(XmlData);
			//商品条码
			String Barcode = cds.getField("Barcode", 0);
			//批次号
			String BatchNumber = cds.getField("BatchNumber",0);
			//顺序号
			String SerialNumber =  cds.getField("SerialNumber", 0);
			List list  = new ArrayList();
			if(!("").equals(SerialNumber)){
				JSONObject jsonObject_ = new JSONObject();
				jsonObject_.put("barcode", Barcode);
				jsonObject_.put("batchNumber", BatchNumber);
				jsonObject_.put("serialNumber", SerialNumber);
				Map zsmap = new HashMap();
				zsmap.put("content", writeObject(jsonObject_));
				String reponseString = RequestOauthUtil.postData(address, null,zsmap, "POST");
				JSONObject jsonObject = JSONObject.fromObject(reponseString);
				String resultCode = (String) jsonObject.get("resultCode");
				if ("0".equals(resultCode)) {
					//已经存在
					map.put("STATE", "0");
					try {
						JSONObject result = (JSONObject) jsonObject.get("result");
						map.put("result", result);
					} catch (Exception e) {
						JSONObject result = (JSONObject) jsonObject.get("result");
						map.put("result", "");
					}
				} else {
					//顺序号不存在可以使用
					map.put("STATE", "1");
				}
			}
		}catch (Exception ex) {
			map.put("STATE", "1");
			throw ex;
		}
		return map;	
	}
	public static String writeObject(Object obj) {
		ObjectMapper mapper = new ObjectMapper();

		StringWriter writer = new StringWriter();
		String re = null;
		try {
			JsonGenerator json = new JsonFactory().createGenerator(writer);
			mapper.writeValue(json, obj);
			re = writer.toString();
			json.close();
			writer.close();
		} catch (Exception e) {
		}
		return re;
	}
	//扫描查询串码
	@RequestMapping("/getSPCM.action")
	public Map getSPCM(HttpServletRequest request,String XmlData) throws Exception{
		Map map = new HashMap();
		Map map1 = new HashMap();
		try{
			cds = new DataSet(XmlData);
			String code = cds.getField("code", 0);
			String zcxx01 = cds.getField("zcxx01", 0);
			String ckqymc =  cds.getField("ckqymc", 0);
			String ckqydm = cds.getField("ckqydm", 0);
			String xtczy01 = cds.getField("xtczy01", 0);
			String sql = "select date_format(drrq, '%Y-%m-%d') drrq,w_spcmbz.barcode,w_spxx.spxx02,w_spxx.spxx01,w_spxx.spxx04,curCode,packLevel ,w_spxx.spxx01,w_spxx.spxx04,w_spcmbz.curCode,w_spcmdr.DRRQ,w_spcmdr.SBM,w_spcmdr.HYGLM,w_spcmdr.WZM,w_spcmdr.DRSL,w_spcmbz.DJLX" +
						 " from w_spcmbz " +
						 " left join w_spcmdr on w_spcmdr.SPCMDR01 = w_spcmbz.DJBH" +
						 " left join w_spxx on w_spxx.spxx01 = w_spcmbz.spxx01" +
						 " where w_spcmbz.zcxx01='"+zcxx01+"' and parentCode = '"+code+"';" ;
			List list = queryForList(o2o,sql);
			if(list.size()==0){
				String sql1 = "select date_format(drrq, '%Y-%m-%d') drrq,w_spcmbz.barcode,w_spxx.spxx02,w_spxx.spxx01,w_spxx.spxx04,curCode,packLevel ,w_spxx.spxx01,w_spxx.spxx04,w_spcmbz.curCode,w_spcmdr.DRRQ,w_spcmdr.SBM,w_spcmdr.HYGLM,w_spcmdr.WZM,w_spcmdr.DRSL,w_spcmbz.DJLX" +
						 " from w_spcmbz " +
						 " left join w_spcmdr on w_spcmdr.SPCMDR01 = w_spcmbz.DJBH" +
						 " left join w_spxx on w_spxx.spxx01 = w_spcmbz.spxx01" +
						 " where  w_spcmbz.zcxx01='"+zcxx01+"' and curCode = '"+code+"';";
				list = queryForList(o2o,sql1);
			}
			if(list!=null&&list.size()!=0){
				map.put("cmlist", list);
			}
			//封装erp系统数据
			List  erplist = new ArrayList();
			String sql2 = "select count(w_spxx.spxx01) number, w_spcmbz.barcode,zcxx02,w_spxx.spxx02,w_spxx.spxx01,w_spxx.spxx04,w_spcmbz.curCode,w_spcmdr.DRRQ,w_spcmdr.SBM,w_spcmdr.HYGLM,w_spcmdr.WZM,w_spcmdr.DRSL,w_spcmbz.DJLX" +
					 " from w_spcmbz " +
					 " left join w_spxx on w_spxx.spxx01 = w_spcmbz.spxx01"+
					 " left join w_zcgs on w_zcgs.zcxx01 = w_spcmbz.ZCXX01" +
					 " left join w_spcmdr on  w_spcmdr.SPCMDR01 = w_spcmbz.djbh"+
					 " where  w_spcmbz.zcxx01='"+zcxx01+"' and parentCode = '"+code+"'"+
					 " group by w_spcmbz.barcode ;";
			erplist = queryForList(o2o,sql2);
			if(erplist.size()==0){
				String sql3 = "select count(w_spxx.spxx01) number,w_spcmbz.barcode,zcxx02,w_spxx.spxx02,w_spxx.spxx01,w_spxx.spxx04,w_spcmbz.curCode,w_spcmdr.DRRQ,w_spcmdr.SBM,w_spcmdr.HYGLM,w_spcmdr.WZM,w_spcmdr.DRSL,w_spcmbz.DJLX" +
						 " from w_spcmbz " +
						 " left join w_spxx on w_spxx.spxx01 = w_spcmbz.spxx01"+
						 " left join w_zcgs on w_zcgs.zcxx01 = w_spcmbz.ZCXX01"+
						 " left join w_spcmdr on  w_spcmdr.SPCMDR01 = w_spcmbz.djbh"+
						 " where  w_spcmbz.zcxx01='"+zcxx01+"' and curCode = '"+code+"'"+
						" group by w_spcmbz.barcode ;";
				erplist = queryForList(o2o,sql3);
			}
			List nlist = new ArrayList();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(new Date());;
			//---外层
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String DRSJ = df.format(new Date()).toString(); 
			//添加spcmList
			List spcmList = new ArrayList();
			if(erplist!=null&&erplist.size()!=0){
				for(int i=0;i<erplist.size();i++){
					Map mapList = (Map) erplist.get(i);
					map.put("CompanyCode",mapList.get("SBM"));
					map.put("GuildCode",mapList.get("HYGLM"));
					map.put("PositionCode", mapList.get("WZM"));
					map.put("ZCXX01", zcxx01);
					map.put("DRSJ", DRSJ);
					map.put("FileType", mapList.get("DJLX"));
					map.put("TraceNum", mapList.get("DRSL"));
					Map tempMap=new HashMap();
					StringBuffer strbf  = new StringBuffer();
					for(int j=0;j<list.size();j++){
						Map cmlist = (Map) list.get(j);
						if(cmlist.get("barcode").equals(mapList.get("barcode"))){
							strbf.append(cmlist.get("curCode")+",");
						}
					}
					tempMap.put("barcode", mapList.get("barcode"));
					tempMap.put("num",mapList.get("number"));
					tempMap.put("SPXX01",mapList.get("spxx01"));
					tempMap.put("SPXX02",mapList.get("spxx02"));
					tempMap.put("SPXX04",mapList.get("spxx04"));
					tempMap.put("spcm01",strbf.toString().substring(0, strbf.length()-1));
					tempMap.put("cmclsj",mapList.get("DRRQ"));
					tempMap.put("cmclry",xtczy01);
					tempMap.put("ckqydm",ckqydm);
					tempMap.put("ckqymc",ckqymc);
					String spcm01 = strbf.toString().substring(0, strbf.length()-1);
					String pc = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",2)+1, JLTools.getStrSplitNum(spcm01,"(",3));
					String xlh = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",3)+1, JLTools.getStrSplitNum(spcm01,"(",4));
					if(!("").equals(pc)){
						tempMap.put("pc",pc);
					}
					if(!("").equals(xlh)){
						tempMap.put("xlh",xlh);
					}
					
					tempMap.put("ckzt",new Integer(0));
					spcmList.add(tempMap);
				}
				if(spcmList!=null&&spcmList.size()!=0){
					map1.put("spcmList", spcmList);
				}
			}
			
			//添加barcodeList
			List barcodeList = new ArrayList();
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					Map mapList = (Map) list.get(i);
					Map tempMap=new HashMap();
					tempMap.put("barcode", mapList.get("barcode"));
					tempMap.put("num","1");
					tempMap.put("SPXX01",mapList.get("spxx01"));
					tempMap.put("SPXX02",mapList.get("spxx02"));
					tempMap.put("SPXX04",mapList.get("spxx04"));
					tempMap.put("spcm01",mapList.get("curCode"));
					tempMap.put("cmclsj",mapList.get("DRRQ"));
					tempMap.put("cmclry",xtczy01);
					tempMap.put("ckqydm",ckqydm);
					tempMap.put("ckqymc",ckqymc);
					tempMap.put("cmzt","0");
					String spcm01 = String.valueOf(mapList.get("curCode"));
					String pc = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",2)+1, JLTools.getStrSplitNum(spcm01,"(",3));
					String xlh = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",3)+1, JLTools.getStrSplitNum(spcm01,"(",4));
					if(!("").equals(pc)){
						tempMap.put("pc",pc);
					}
					if(!("").equals(xlh)){
						tempMap.put("xlh",xlh);
					}
					tempMap.put("ckzt",new Integer(0));
					barcodeList.add(tempMap);
				}
				if(barcodeList!=null&&barcodeList.size()!=0){
					map1.put("barcodeList", barcodeList);
				}
			}
			/*List cmList = new ArrayList();
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					Map barmap  = (Map) list.get(i);
					cmList.add(barmap.get("curCode"));
				}
			}
			List isExitCMList = selectW_CKDCM(cmList);
			if(isExitCMList.size()>0){
				Map newMap = new HashMap();
				newMap.put("isExitList",isExitCMList);
				newMap.put("state","4");
				nlist.add(newMap);
			}*/
			nlist.add(map1);
			if(nlist!=null&&nlist.size()!=0){
				map.put("CKDITEM", nlist);
			}
			map.put("STATE", "1");
		}catch (Exception e) {
			map.put("STATE", "0");
			throw e;
		}
		return map;
	}
	//查询所有包装码
	@RequestMapping("/deleteW_SPCM.action")
	public Map searchW_SPCM(HttpServletRequest request,String XmlData) throws Exception{
		Map map = new HashMap();
		Map map1 = new HashMap();
		try{
			cds = new DataSet(XmlData);
			String zcxx01 = cds.getField("zcxx01", 0);
			String spcm01 = cds.getField("spcm01", 0);
			String flag = cds.getField("flag", 0);
			StringBuffer str = new StringBuffer();
			String query = "SELECT curCode,spxx04 from w_spcmbz " +
						   " left join w_spxx on w_spxx.spxx01 = w_spcmbz.spxx01" +
						   " where parentCode = '"+spcm01+"' and w_spcmbz.zcxx01='"+zcxx01+"'";
			List  cmlist = queryForList(o2o,query);
			if(flag!=null && flag.equals("item")){
				map.put("spcmlist", cmlist);
		    	map.put("STATE", "0");
			}
			StringBuffer strb = new StringBuffer();
			if(cmlist!=null&&cmlist.size()!=0){
				for(int a=0;a<cmlist.size();a++)
				{
					Map cmMap =(Map) cmlist.get(a);
					strb = strb.append("'"+cmMap.get("curCode")+"',");	
				}
				String SPCM01 = strb.toString().substring(0, strb.length()-1);
				String returnsql = "SELECT SPCM01 FROM W_CKDCM WHERE SPCM01 IN ("+SPCM01+");";
				List returnlist = queryForList(o2o,returnsql);
				if(returnlist != null && returnlist.size()>0){
					for(int l=0;l<returnlist.size();l++){
						Map ckcmMap=(Map) returnlist.get(l);
						str = str.append(ckcmMap.get("SPCM01")+",");
					}
			  	}
				if(!("").equals(str.toString())){
			    	map.put("cmlist", str.substring(0,str.length()-1));
			    	map.put("STATE", "0");
			    }else{
			    	//删除w_spcm
			    	String spcm_sql = "delete from w_spcm where spcm01 in" +
		    						  " (select a.curCode from " +
		    						  "      (select curCode from w_spcmbz a where zcxx01='0237' and ( parentCode = '"+spcm01+"' or curCode='"+spcm01+"') " +
		    						  "      ) a" +
		    						  " )";
			    	Map cm_row = getRow(spcm_sql, null, 0);
			    	execSQL(o2o, spcm_sql, cm_row);
			    	//删除串码dr表
			    	String spcmdr_sql = "delete from w_spcmdr where  spcmdr01 in" +
    						" (select DISTINCT(a.djbh) from " +
    						"      (select djbh from w_spcmbz a where zcxx01='0237' and ( parentCode = '"+spcm01+"' or curCode='"+spcm01+"') " +
    						"      ) a" +
    						" )";
			    	Map spcmdr_row = getRow(spcmdr_sql, null, 0);
			    	execSQL(o2o, spcmdr_sql, spcmdr_row);
			    	//删除w_spcmbz
			    	String spcmbz_sql = "delete from w_spcmbz where curCode in" +
			    						" (select a.curCode from " +
			    						"      (select curCode from w_spcmbz a where zcxx01='0237' and ( parentCode = '"+spcm01+"' or curCode='"+spcm01+"') " +
			    						"      ) a" +
			    						" )";
			    	Map bz_row = getRow(spcmbz_sql, null, 0);
			    	int i = execSQL(o2o, spcmbz_sql, bz_row);
			    	if (i > 0){
						map.put("STATE", "1");
					}else{
						map.put("STATE", "0");
					}
			    }
			 }else{
				 map.put("STATE", "0");
			 }
		}catch (Exception e) {
			map.put("STATE", "0");
			throw e;
		}
		return map;
	}
}