package com.jlsoft.init;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JLQuery;
import com.jlsoft.o2o.interfacepackage.V9.V9FH;
import com.jlsoft.o2o.interfacepackage.V9.V9RKD;
import com.jlsoft.o2o.interfacepackage.V9.V9THD;
import com.jlsoft.o2o.interfacepackage.jlinterface.JlInterfaces;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.JlAppResources;
import com.jlsoft.utils.PubFun;
import com.jlsoft.utils.PubTransaction;

@Controller
@RequestMapping("/Test")
public class Test extends JLBill{
	@Autowired
    private DataSourceTransactionManager txManager_o2o;
	@Autowired
	private V9RKD v9RKD;
	@Autowired
	private PubService pubService;
	@Autowired
	private V9THD v9THD;
	@Autowired
	private V9FH v9FH;
	
	@RequestMapping("/insertTest2")
	public Map insertTest2() throws Exception{
		Map map = new HashMap();
		/**for(int i=0;i<100;i++){
			String sql = "INSERT INTO TEST(BARCODE) VALUES('"+i+"')";
			execSQL(o2o, sql, map);
		}*/
		String cm = "";
		File f = new File("c:\\aa.txt"); 
		f.createNewFile();
		BufferedWriter output = new BufferedWriter(new FileWriter(f));
		StringBuffer str = new StringBuffer();
		for(int i=1;i<10000;i++){
			cm = JLTools.getID_X(i,5);
			str.append("<TraceCode curCode=\"(01)06950809201006(10)20151026001(21)"+cm+"(9999)00\" packLevel=\"0\" parentCode=\"(01)02211331122123(10)20151202001(21)00000(9999)01\" />");
			str.append("</br>");
			System.out.println("<TraceCode curCode=\"(01)06950809201006(10)20151026001(21)"+cm+"(9999)00\" packLevel=\"0\" parentCode=\"(01)02211331122123(10)20151202001(21)00000(9999)01\" />");
		}
		output.write(str.toString());
		output.close();
		return map;
	}
	
	@RequestMapping("/queryMapTest")
	public Map queryMapTest(){
		String sql = "SELECT OrderInvoiceId,ZTID FROM OrderInvoice";
		List list = queryForList(o2o,sql);
		Map map = new HashMap();
		map.put("list", list);
		return map;
		/**String sql = "SELECT XSDD01,XSDD05,XSDD07,XSDD23 FROM W_XSDD WHERE XSDD01='CG1426700239'";
		Map map = queryForMap(o2o,sql);
		System.out.println(map.get("XSDD05")+" +XSDD05+ "+(map.get("XSDD05")==null));
		System.out.println(map.get("XSDD07")+" +XSDD07+ "+(map.get("XSDD07")==null)+"  ####  "+(map.get("XSDD07").equals("")));
		String b = (map.get("XSDD07").equals("")?"ccccccccccc":"aa");
		System.out.println(b);
		return map;*/
	}
	
	//退货客户建档
	@RequestMapping("/v9THKHJD")
	public Map v9THKHJD(String THDH) throws Exception{
		Map map = new HashMap();
		String sql = "SELECT A.THDH,A.LXR,A.LXDH,A.XXDZ,B.ZCXX60 UserName,B.ZCXX61 PassWord,B.ZCXX59 URL," +
							"'30' PFD01 " +
							"FROM W_THD A,W_ZCXX B WHERE A.ZTID=B.ZCXX01 AND A.THDH='"+THDH+"'";
		Map erpMap = queryForMap(o2o,sql);
		String str = v9THD.createTHKHJD(erpMap);
		System.out.println(str+"   @@@@@@@@@@");
		return map;
	}
	
	//入库单对接
	@RequestMapping("/v9RKD")
	public Map v9RKD(String CKDH) throws Exception{
		Map map = new HashMap();
		String sql = "";
		Map erpMap = pubService.getECSURL("0005");
		if(erpMap.get("DJLX") != null){
			if(erpMap.get("DJLX").equals("V9")){
				String str = v9RKD.createRKD(erpMap,CKDH,"");
			}
		}
		//System.out.println(str);
		return map;
	}
	
	//退货单制单
	@RequestMapping("/v9THD")
	public Map v9THD(String THDH) throws Exception{
		Map map = new HashMap();
		String sql = "SELECT A.ZTID,A.XSDD01,(SELECT ZCXX25 FROM W_ZCXX WHERE ZCXX01=A.ZTID) ZCXX25,B.PFD01 FROM W_THD A,W_XSDDGROUP B WHERE A.XSDD01=B.XSDD01 AND A.THDH='"+THDH+"'";
		map = queryForMap(o2o,sql);
		Map erpMap = pubService.getECSURL(map.get("ZTID").toString());
		erpMap.put("PFD01", map.get("PFD01").toString());
		erpMap.put("THDH", THDH);
		erpMap.put("XSDD01", map.get("XSDD01"));
		erpMap.put("ZCXX25", map.get("ZCXX25"));
		erpMap.put("ERPDZ", "0001010101");  //要进行查询
		String str = v9THD.createTHD(erpMap);
		System.out.println(str+ "  ###################");
		return map;
	}
	
	//退货入库
	@RequestMapping("/v9THRK")
	public Map v9THRK(String THDH) throws Exception{
		Map map = new HashMap();
		String sql = "SELECT ZTID FROM W_THD WHERE THDH='"+THDH+"'";
		map = queryForMap(o2o,sql);
		Map erpMap = pubService.getECSURL(map.get("ZTID").toString());
		String str = v9THD.createTHD(erpMap);
		System.out.println(str+"  @@@@@@@@@@@@@@@");
		return map;
	}
	
	/***
	 * 分销单发货接口
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createFH.action")
	public Map createFH() throws Exception{
		String sql = "SELECT ifnull(ZCXX58,'') DJLX,ifnull(ZCXX59,'') URL,ZCXX60 UserName,ZCXX61 PassWord FROM W_ZCXX WHERE ZCXX01='JL'";
		Map erpMap = queryForMap(o2o,sql);
		List<String> response = v9FH.createFH(erpMap, "XS0000000000001");
		Map map = new HashMap();
		map.put("list", response);
		return map;
	}
	
	@RequestMapping("/getSPXXList")
	public Map getSPXXList(){
		Map map = new HashMap();
		String sql = "SELECT SPXX02,SPXX04 FROM W_SPXX";
		List list = queryForList(o2o,sql);
		map.put("spxx", list);
		return map;
	}
	
	@RequestMapping("/startTest.action")
	public Map startTest(){
		Map map = new HashMap();
		try{
			insertTest("0001");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/insertTest1.action")
	public Map insertTest1() throws Exception{
		Map map = new HashMap();
		try{
			String sql = "INSERT INTO TEST(TEST01,TEST02) VALUES('AAA','CCCC')";
			execSQL(o2o, sql, map);
			//String aa = null;
			//Integer.parseInt(aa);
			sql = "UPDATE TEST AND BUG SET TEST01='BBBB'";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}finally{
				return map;
		}
	}
	
	@RequestMapping("/insertTest.action")
	public Map insertTest(String test01) throws Exception{
		Map map = new HashMap();
		 TransactionStatus status = PubTransaction.getTransactionStatus_o2o(txManager_o2o);
		try{
			String sql = "INSERT INTO TEST(TEST01,TEST02) VALUES('AAA','CCCC')";
			execSQL(o2o, sql, map);
			String aa = null;
			Integer.parseInt(aa);
			sql = "UPDATE TEST SET TEST01='BBBB'";
			execSQL(o2o, sql, map);
			txManager_o2o.commit(status);
		}catch(Exception ex){
			txManager_o2o.rollback(status);
			throw ex;
		}
		return map;
	}
	
	@RequestMapping("/updateTest")
	public Map updateTest() throws Exception{
		String sql = "INSERT INTO TEST(TEST01,TEST02) VALUES('TTTTT','XXXXX')";
		Map map = new HashMap();
		execSQL(o2o, sql, map);
		return map;
	}
	
	@RequestMapping("/sendSMS")
	public Map sendSMS() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SMS_pszMobis", "18611755240");
		map.put("SMS_Messages", "短信下发测试");
		map.put("DJLX", 0);
		map.put("YWDH", "1234");
		boolean response = false;
		try {
			//response = JlInterfaces.sendSMS(map);
			//System.out.println("response: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", response);
		return resultMap;
	}
	
	@RequestMapping("/ylcs")
	public Map ylcs() throws Exception{
		Map map = new HashMap();
		String urlvalue="http://www.motorsc.com/UnionpayOnline/auditUnionpayReceiveBack.action?RespCode=111&YLLS=2222";
		URL url = new URL(urlvalue);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine = in.readLine().toString();
        System.out.println(inputLine+"  @@@@@@@@@@@");
        return map;
	}
	
	public static void main(String[] args) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	 
		String orderId = df.format(new Date())+(int)(Math.random()*1000);
    	//int a = Integer.parseInt(orderId);
		long a =Long.parseLong(orderId);
		System.out.println(a);
		
		
		/**
		Random rand = new Random();
		System.out.println(rand.nextInt(900)+100);
		
		System.out.println((int)(Math.random()*1000)+"   @@@@"+Math.random());
		*/
		
		/**Map map = new HashMap();
		map.put("a", 1);
		map.put("b", "1");
		
		System.out.println(map.get("b").equals("1"));
		System.out.println(((Integer)map.get("a")).intValue()==1);
		*/
		//String aa = URLDecoder.decode(sign, "utf-8");
		
		/**String sign = "【汽服云】";
		String aa = java.net.URLEncoder.encode(sign, "utf-8");
		System.out.println(aa);*/
		
		/**if(aa.equals("%E7%BE%8E%E4%BB%98%E5%AE%9D%E7%94%B5%E5%AD%90%E6%94%AF%E4%BB%98%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8")){
			
			System.out.println("success");
		}*/
		/**SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = df.format(new Date());*/
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//java.util.Date time=sdf.parse(sdf.format(new Date()));
		
		//System.out.println(sdf.format(new Date())+"@@"+time+"+++"+DateUtils.addDays(time, 365));
		//String aa = DateFormatUtils.format(DateUtils.addDays(time, 365),"yyyy-MM-dd");
		//System.out.println(aa);
		
		
		/**
		StringBuffer strB = new StringBuffer();
		strB.append("i love china!").append("\r\n");
		strB.append("i love my family");
		String str=strB.toString();
        File txt=new File("C://Users//Administrator//Desktop//nihao.txt");
        if(!txt.exists()){
        	txt.createNewFile();
        }
        byte bytes[]=new byte[1024];
        bytes=str.getBytes();   //新加的
        int b=str.length();   //改
        FileOutputStream fos=new FileOutputStream(txt);
        fos.write(bytes,0,b);
        fos.close();
		
		/**SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
		String msg =df.format(new Date());
		System.out.println(msg+"   +++");*/
		
		/**String spcm = "(01)06920128200028(10)150211(21)B4(9999)02";
		int num=0;
		for(int i=0;i<spcm.length();i++){
			if(String.valueOf(spcm.charAt(i)).equals("(")){
				num=num+1;
			}
		}
		System.out.println(num);
		*/
		
		//String spcm = "(01)06920128200028(10)150211(21)B4(9999)02";
		//System.out.println(JLTools.getStrSplitNum(spcm,")",2) + "  +++  " +);
		//String pch = spcm.substring(JLTools.getStrSplitNum(spcm,")",2)+1, JLTools.getStrSplitNum(spcm,"(",3));
		//String xlh = spcm.substring(JLTools.getStrSplitNum(spcm,")",3)+1, JLTools.getStrSplitNum(spcm,"(",4));
		//System.out.println(pch + "   +++++"+xlh);
		
		
		/**
		File file = new File("C:/oldTP/2");
		if(file.isDirectory()){
			String[] fileList = file.list();
			for(int i=0;i<fileList.length;i++){
				System.out.println(fileList[i]+"   @@@@@@@@@@2");
			}
		}*/
		
		//Long aa = System.currentTimeMillis();
		//System.out.println(aa+"++++"+System.currentTimeMillis());
		
		/**
		File file = new File("C:/ini_zcgs/大有汽车修理（天津）有限公司/TJWQDY0.jpg");
		InputStream in = new FileInputStream(file);
		FileOutputStream out = new FileOutputStream("c:/test.jpg");
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		*/
	}
	
	/**
	 * @todo 更新数据库备注
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateDBCommet.action")
	public Map updateDBCommet() throws Exception{
		StringBuffer strB = new StringBuffer();
		Map resultMap = new HashMap();
		String sql = "select table_name from information_schema.tables where table_schema='devpdb' and table_type='base table'";
		List list = queryForList(o2o,sql);
		Map map;
		Map mapBZ;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			String tblName = map.get("table_name").toString();
			sql = "SELECT column_name,column_comment,column_type FROM Information_schema.columns WHERE table_Name='"+tblName+"'";
			List listBZ = queryForList(o2o,sql);
			for(int j=0;j<listBZ.size();j++){
				mapBZ=(Map)listBZ.get(j);
				//ALTER TABLE w_zcgs MODIFY COLUMN zcxx06 varchar(10) COMMENT '手机号码11';
				strB.append("ALTER TABLE "+tblName+" MODIFY COLUMN "+mapBZ.get("column_name")+" ");
				strB.append(""+mapBZ.get("column_type")+" COMMENT '"+mapBZ.get("column_comment")+"';");
				strB.append("\r\n");
			}
		}
		//生成文件
		String str=strB.toString();
        File txt=new File("C://Users//Administrator//Desktop//nihao.txt");
        if(!txt.exists()){
        	txt.createNewFile();
        }
        byte bytes[]=new byte[1024];
        bytes=str.getBytes();
        int b=str.length();
        FileOutputStream fos=new FileOutputStream(txt);
        fos.write(bytes,0,b);
        fos.close();
		return resultMap;
	}
	
	@RequestMapping("/selectXSDD.action")
	public Map selectXSDD(String json) throws Exception{
		Map resultMap = new HashMap();
		ObjectMapper mapper = new ObjectMapper();
		List list =  (List)mapper.readValue(json, ArrayList.class); 
		Map map = (Map)list.get(0);
		JLQuery a = new JLQuery();
		List list2 = (List)a.queryForListByXML("o2o","com.jlsoft.o2o.sql.order.selectW_XSDD",map);
		resultMap.put("aaaa", list2);
		return resultMap;
	}
	
	/**
	public static void main(String[] args) throws Exception{
		String xmlStr = "<?xml version='1.0' encoding='UTF-8'?><response><status>4009</status><message>订单状态参数缺失</message><uuid>619bfae2744846eebf1c9d64137f3e83</uuid><data></data></response>";
		Document document = DocumentHelper.parseText(xmlStr);
		//获取根元素
		Element root = document.getRootElement();
	   System.out.println("Root: " + root.getName());
	   //获取子元素
	   String head = ((Element)document.selectSingleNode("//status")).getText();
	   System.out.println(head);
	}
	*/
	
	/**
	@RequestMapping("/uploadImg")
	public Map uploadImg(String json) throws Exception{
		InputStream ins = new ByteArrayInputStream(json.getBytes("ISO-8859-1"));
		OutputStream os = new FileOutputStream("c:/aa.jpg");
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
		Map map = new HashMap();
		return map;
	}
	*/
	
	
	
	/**
	public static void main(String[] args){
		String path = "E:/tomcat/apache-tomcat-6.0.36/webapps/jlo2o_bd/gui_o2o/qfy/images/file/0151/Material.xml";
		File file = new File(path);
		System.out.println(file.exists());
		if(file.exists()){
			boolean d =file.delete();
			System.out.println(d);
		}
	}
	*/
	
	/**public static void main(String[] args){
		List cxmx = new ArrayList();
		Map map1 = new HashMap();
		map1.put("cx", "bm");
		map1.put("mc", "1");
		
		Map map2 = new HashMap();
		map2.put("cx", "bm");
		map2.put("mc", "2");
		
		Map map3 = new HashMap();
		map3.put("cx", "aodi");
		map3.put("mc", "11");
		
		cxmx.add(map1);
		cxmx.add(map2);
		cxmx.add(map3);
		
		//处理数据
		ArrayList list = new ArrayList();
		HashMap resultMap = null;
		ArrayList resultList = null;
		HashMap cxmxMap = null;
		boolean find = false;
		for(int i=0;i<cxmx.size();i++){
			cxmxMap = (HashMap)cxmx.get(i);
			for(int j=0;j<list.size();j++){
				resultMap = (HashMap)list.get(j);
				if(resultMap.get("cx").equals(cxmxMap.get("cx"))){
					resultList = (ArrayList)resultMap.get("list");
					resultList.add(cxmxMap);
					find = true;
					break;
				}
			}
			if (!find) {
				HashMap map = new HashMap();
				map.put("cx", cxmxMap.get("cx"));
				resultList =new ArrayList();
				resultList.add(cxmxMap);
				map.put("list", resultList);
				list.add(map);
			}
			find = false;
		}
		System.out.println(list);
	} */
	
}
