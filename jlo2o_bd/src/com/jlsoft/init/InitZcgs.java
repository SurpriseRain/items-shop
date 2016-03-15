package com.jlsoft.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.user.service.Register;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;

@Controller
@RequestMapping("/InitZcgs")
public class InitZcgs extends JLBill{
	private static Logger logger = Logger.getLogger(InitZcgs.class);
	
	/**
	 * @todo 更新公司类型
	 * @param zcxx01
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateGSLX")
	public Map updateGSLX(String zcxx01) throws Exception{
		int gslx = 43;
		Map resultMap = new HashMap();
		try{
			String sql = "SELECT COUNT(0) FROM W_ZCGS WHERE ZCXX01='"+zcxx01+"'";
			if(queryForInt(o2o,sql) == 1){
				sql = "UPDATE W_ZCGS SET ZCGS03="+gslx+" WHERE ZCXX01='"+zcxx01+"'";
				execSQL(o2o,sql,resultMap);
				sql = "UPDATE W_GSLX SET LX="+gslx+" WHERE GSID='"+zcxx01+"'";
				execSQL(o2o,sql,resultMap);
			}
		}catch(Exception ex){
			throw ex;
		}
		return resultMap;
	}
	
	/**
	 * @todo 更新账号权限
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateZCGSQX")
	public Map updateZCGSQX() throws Exception{
		Map ResultMap = new HashMap();
		//String sql = "SELECT A.XTCZY01 FROM W_ZCGS A,W_GSLX B,W_ZCXX C WHERE A.ZCXX01=C.ZCXX01 AND A.ZCXX01=B.GSID AND A.zcgs01=4 AND B.LX=43 AND A.ZCXX01<>'JL' AND C.ZCXX65=1";
		//String sql = "SELECT A.XTCZY01 FROM W_ZCGS A,W_GSLX B,W_ZCXX C WHERE A.ZCXX01=C.ZCXX01 AND A.ZCXX01=B.GSID AND A.zcgs01=4 AND B.LX=43 AND A.ZCXX01<>'JL' AND C.ZCXX65=0";
		String sql = "SELECT A.XTCZY01 FROM W_ZCGS A,W_GSLX B,W_ZCXX C WHERE A.ZCXX01=C.ZCXX01 AND A.ZCXX01=B.GSID AND A.zcgs01=4 AND B.LX=44 AND A.ZCXX01<>'JL'";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			updateCZYQX(map.get("XTCZY01").toString());
		}
		return ResultMap;
	}
	
	public void updateCZYQX(String xtczy01) throws Exception{
		Map map = new HashMap();
		//删除原有操作权限
		String sql = "DELETE FROM W_XTCZYCZ WHERE PERSON_ID='"+xtczy01+"'";
		execSQL(o2o,sql,map);
		//插入新的操作权限
		sql = "INSERT INTO W_XTCZYCZ\n"
			+ "  (PERSON_ID, CD)\n"
			+ "  SELECT '"
			+ xtczy01
			+ "', W.CD FROM W_XTCZYCZ W WHERE PERSON_ID = 'JL_MRWXC44'";
		execSQL(o2o,sql,map);
	}
	
	/**
	 * @todo 删除待完善信息的账号
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteZCGS")
	public Map deleteZCGS() throws Exception{
		Map ResultMap = new HashMap();
		//String sql="SELECT XTCZY01,ZCXX01 FROM W_ZCGS WHERE ZCGS01=2 AND length(ZCXX01)>0 AND length(XTCZY01)>0 ORDER BY ZCXX14";
		String sql="SELECT XTCZY01,ZCXX01 FROM W_ZCGS WHERE ZCGS01=2 AND XTCZY01='1173717909'";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			deleteZCGSSinge(map);
		}
		return ResultMap;
	}
	
	public void deleteZCGSSinge(Map map) throws Exception{
		try{
			String sql="";
			Map row = new HashMap();
			String xtczy01=map.get("XTCZY01").toString();
			String zcxx01=map.get("ZCXX01").toString();
			//删除W_GSLX
			sql="DELETE FROM W_GSLX WHERE GSID='"+zcxx01+"'";
			execSQL(o2o, sql, row);
			//删除W_XTCZY
			sql="DELETE FROM W_XTCZY WHERE PERSON_ID='"+xtczy01+"'";
			execSQL(o2o, sql, row);
			//删除W_ZCXX
			sql="DELETE FROM W_ZCXX WHERE ZCXX01='"+zcxx01+"'";
			execSQL(o2o, sql, row);
			//删除W_ZCGS
			sql="DELETE FROM W_ZCGS WHERE ZCXX01='"+zcxx01+"'";
			execSQL(o2o, sql, row);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 初始导入维修厂
	 * @return
	 */
	@RequestMapping("/startMethod")
	public Map startMethod(){
		Map ResultMap = new HashMap();
		String sql="SELECT QYMC,YWJX,DZMC,FRXM,XXDZ,YYZZFB,FRSFZ1,FRSFZ2 FROM TEMP_ZCGS WHERE FLAG=0";
		List list = queryForList(vip,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			//判断改用户是否存在
			sql="SELECT COUNT(0) FROM W_XTCZY WHERE PERSON_ID='"+map.get("YWJX").toString()+"'";
			if(queryForInt(o2o,sql) == 0){
				insertGS(map);
			}
		}
		return ResultMap;
	}
	
	@RequestMapping("/insertGS")
	public void insertGS(Map map){
		try{
			String gslx = "44";
			String zcxx01 = '9'+JLTools.getID_X(PubFun.updateWBHZT(o2o,"WLDW_W_ZCXX",1), 5);
			String phone = "";
			String email = "";
			String xtczy01 = map.get("YWJX").toString();
			String password = "123123";
			String sjly = "2";
		  	String qymc = map.get("QYMC").toString();
			//获取区域代码
		  	String zcxx07="";
		  	String sql = "SELECT DQBZM01 FROM DQBZM WHERE DQBZM02='"+map.get("DZMC").toString()+"'";
			Map mapDZ=queryForMap(o2o,sql);
			if(mapDZ != null){
				zcxx07=mapDZ.get("DQBZM01").toString();
			}
			String zcxx08=map.get("XXDZ").toString();
			String frxm=map.get("FRXM").toString(); //法人姓名
			//获取行业管理码
			int hyglm = PubFun.updateWBHZT(o2o,"TEMP_HYGLM",1);
			//插入O2O库
			addZCGS(gslx,zcxx01,phone,email,xtczy01,password,sjly,qymc,zcxx07,zcxx08,frxm,hyglm);
			insertZCXX(zcxx01,xtczy01);
			insertXtczy(zcxx01,xtczy01,password);
			insertGslx(zcxx01,gslx);
			insertCzMenu(xtczy01,gslx);
			//按照最新规则生成图片
			if(map.get("FRSFZ1") != null && !map.get("FRSFZ1").equals("")){
				String outTpName = map.get("FRSFZ1").toString()+".jpg";
				//String inTpName = "fssfzzm_1"+outTpName.split("\\.")[1];
				String inTpName = "fssfzzm_1.jpg";
				saveDPTP(zcxx01,qymc,outTpName,inTpName,"1");
			}
			if(map.get("FRSFZ2") != null && !map.get("FRSFZ2").equals("")){
				String outTpName = map.get("FRSFZ2").toString()+".jpg";
				//String inTpName = "fssfzfm_2"+outTpName.split("\\.")[1];
				String inTpName = "fssfzfm_2.jpg";
				saveDPTP(zcxx01,qymc,outTpName,inTpName,"2");
			}
			if(map.get("YYZZFB") != null && !map.get("YYZZFB").equals("")){
				String outTpName = map.get("YYZZFB").toString()+".jpg";
				//String inTpName = "yyzzfb_3"+outTpName.split("\\.")[1];
				String inTpName = "yyzzfb_3.jpg";
				saveDPTP(zcxx01,qymc,outTpName,inTpName,"3");
			}
			//插入WORKFLOW库
			addOperator(xtczy01,password,frxm);
			//跟新导入状态标记
			sql = "UPDATE TEMP_ZCGS SET FLAG=1 WHERE YWJX='"+xtczy01+"'";
			execSQL(vip, sql, map);
			//导入成功
			String sqlSuccess = "UPDATE TEMP_ZCGS SET ERROR='' WHERE YWJX='"+xtczy01+"'";
			execSQL(vip, sqlSuccess, map);
		}catch(Exception ex){
			ex.printStackTrace();
			try{
				String sqlError = "UPDATE TEMP_ZCGS SET ERROR='"+ex+"' WHERE YWJX='"+map.get("YWJX").toString()+"'";
				execSQL(vip, sqlError, map);
			}catch(Exception e){
			}
		}
	}
	
	//插入W_ZCGS表
	public void addZCGS(String gslx,String zcxx01,String phone,String email,String xtczy01,String password,String sjly,
										String qymc,String zcxx07,String zcxx08,String frxm,int hyglm) throws Exception {
		try {
			String insertSQL = "INSERT INTO W_ZCGS (ZCGS03,ZCXX01,ZCXX26,ZCXX09,XTCZY01,XTCZY02,ZCXX56,ZCXX14," +
										  "ZCXX02,ZCXX07,ZCXX08,ZCXX03,ZCGS01,HYGLM) " +
					           "VALUES("+gslx+",'"+zcxx01+"','"+phone+"','"+email+"','"+xtczy01+"','"+password+"',"+sjly+",now()," +
					           			  "'"+qymc+"','"+zcxx07+"','"+zcxx08+"','"+frxm+"',4,"+hyglm+")";
			Map row = new HashMap();
			execSQL(o2o, insertSQL, row);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("插入注册公司***"+qymc+"***表出现异常！");
		}
	}
	
	//插入W_ZCXX表
	public void insertZCXX(String ZCXX01, String XTCZY01) throws Exception {
		try {
			String insertSQL = "INSERT INTO W_ZCXX(ZCXX01, ZCXX02, ZCXX03, ZCXX04, ZCXX05, ZCXX06, ZCXX07, ZCXX08, ZCXX09, ZCXX10, ZCXX14,XTCZY01,ZCXX56)\n"
					+ "SELECT ZCXX01,ZCXX02,ZCXX03,ZCXX04,ZCXX05,ZCXX06,ZCXX07,ZCXX08,ZCXX09,ZCXX10,ZCXX14,'"
					+ XTCZY01
					+ "',ZCXX56 FROM W_ZCGS B  WHERE B.ZCXX01 = '"
					+ ZCXX01
					+ "';";
			Map row = new HashMap();
			execSQL(o2o, insertSQL, row);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("插入注册信息表出现异常！");
		}
	}
	
	//插入W_XTCZY表
	public void insertXtczy(String ZCXX01, String XTCZY01, String PASSWORD)throws Exception {
		try {
			String insertSQL = "INSERT INTO W_XTCZY(PERSON_ID, PASSWD, TYBJ,GSID) VALUES('"
					+ XTCZY01 + "','" + PASSWORD + "',0,'" + ZCXX01 + "')";
			Map row = new HashMap();
			execSQL(o2o, insertSQL, row);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("插入系统操作员出现异常！");
		}
	}
	
	//插入W_GSLX表
	public void insertGslx(String ZCXX01, String GSLX) throws Exception {
		try {
			String querySQL = "SELECT COUNT(0) FROM W_GSLX WHERE GSID = '"
					+ ZCXX01 + "' AND LX = '" + GSLX + "'";
			int count = queryForInt(o2o, querySQL);
			if (count == 0) {
				String insertSQL = "INSERT INTO W_GSLX(GSID,LX,TYBJ) VALUES('"
						+ ZCXX01 + "','" + GSLX + "',0)";
				Map row = new HashMap();
				execSQL(o2o, insertSQL, row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("插入公司类型出现异常，检查系统中是否存在该公司类型或其他问题！");
		}
	}
	
	//插入W_XTCZYCZ表
	public void insertCzMenu(String XTCZY01,String GSLX) throws Exception {
		try {
			String mrczy = "mz123456";
			if(GSLX.equals("43")){
				mrczy = "JL_MRSCCJ43";
			}else if(GSLX.equals("44")){
				mrczy = "JL_MRWXC44";
			}
			String insertXtczycz = "INSERT INTO W_XTCZYCZ\n"
				+ "  (PERSON_ID, CD)\n"
				+ "  SELECT '"
				+ XTCZY01
				+ "', W.CD FROM W_XTCZYCZ W WHERE PERSON_ID = '"+mrczy+"'";
		Map row = new HashMap();
		execSQL(o2o, insertXtczycz, row);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("插入操作菜单异常！");
		}
	}
	
	//XTCZY01 账号; XTCZY02 密码; frxm 法人姓名
	public void addOperator(String XTCZY01,String XTCZY02,String frxm) throws Exception{
		Map map = new HashMap();
		int RYBH = PubFun.callProcedureForJLBH(vip, "W_XTCZY", 1);
		String MWRYMM = md5Encrypt32(XTCZY02);
		map.put("RYBH", new Integer(RYBH));
		map.put("RYDM", XTCZY01);
		map.put("RYMC", frxm);
		map.put("RYMM", XTCZY02);
		map.put("MWRYMM", MWRYMM);
		map.put("DZYJ", " ");
		map.put("LXDH", " ");
		map.put("GWFZBH", new Integer(-1));
		map.put("BMDM", "0001");
		addOperatorBase(map);
		addCZYRecord(map);
		addOperatorPermission(map);
	}
	
	/**
	 * @todo 增加本地操作员账号
	 * @param map
	 * @throws Exception 
	 */
	public void addOperatorBase(Map map) throws Exception{
		try{
			String sql = "insert into W_XTCZY(RYBH,RYDM,RYMC,RYMM,DZYJ,LXDH,YXBJ,TS,MWRYMM) "+
            "values(RYBH?,RYDM?,RYMC?,RYMM?,DZYJ?,LXDH?,0,systimestamp,MWRYMM?)";
			execSQL(vip, sql, map);
		}catch(Exception ex){
			logger.error("Error to addOperatorBase.",ex);
			throw ex;
		}
	}
	
	/**
	 * @todo 操作员密码修改记录
	 * @param map
	 * @throws Exception
	 */
	public void addCZYRecord(Map map) throws Exception{
		try{
			String sql = "INSERT INTO W_XTCZYRECORD(RYBH,RYDM,RYMM,MWRYMM,LASTXGR,LASTDATE) "+
							   "values(RYBH?,RYDM?,RYMM?,MWRYMM?,-1,SYSDATE)";
			execSQL(vip, sql, map);
		}catch(Exception ex){
			logger.error("Error to addCZYRecord.",ex);
			throw ex;
		}
	}
	
	/**
	 * @todo 增加操作员
	 * @param map
	 * @throws Exception
	 */
	public void addOperatorPermission(Map map) throws Exception{
		try{
			String sql = "INSERT INTO W_XTCZYPERMISSIONS(RYBH,GWFZBH,JSFZBH,BMDM,RYDM,DQBH,QYBH) "+
							   "values(RYBH?,GWFZBH?,2,BMDM?,RYDM?,0,1)";
			execSQL(vip, sql, map);
		}catch(Exception ex){
			logger.error("Error to addOperatorPermission.",ex);
			throw ex;
		}
	}
	
	//md5加密
	public String md5Encrypt32(String pass) throws Exception {
        String md5Str = null;
        StringBuffer buf = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        byte b[] = md.digest();
        int i;

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        md5Str = buf.toString();
        return md5Str;
    }
	
	//保存图片
	public void saveDPTP(String zcxx01,String qymc,String tpName,String inImgName,String tplx) throws Exception{
		try{
			String outFileUrl = "c:/ini_zcgs/"+qymc+"/"+tpName;
			String inFileUrl = "c:/ini_zcgs/new/"+zcxx01;
			uploadImg(outFileUrl,inFileUrl,inImgName);
			//写入W_DPTP
			Map map=new HashMap();
			map.put("ZCXX01", zcxx01);
			map.put("TPLX", tplx);
			map.put("TPNAME", inImgName);
			insertW_DPTP(map);
		}catch(Exception ex){
			logger.error("Error to saveDPTP.",ex);
		}
	}
	
	/**
	 * @todo 插入店铺图片
	 * @param hm
	 * @throws Exception 
	 */
	public void insertW_DPTP(Map<String, Object> hm) throws Exception{
		try{
			String ZCXX01=hm.get("ZCXX01").toString();
			String TPLX=hm.get("TPLX").toString();
			String TPNAME=hm.get("TPNAME").toString();
			//删除原有图片
			String sql = "DELETE FROM W_DPTP WHERE ZCXX01='"+ZCXX01+"' AND DPTP01="+TPLX+"";
			execSQL(o2o, sql, hm);
			//插入新图片
			sql = "INSERT INTO W_DPTP(ZCXX01,DPTP01,DPTP02,DPTP04) " +
					 "VALUES('"+ZCXX01+"',"+TPLX+",'"+TPNAME+"',now())";
			execSQL(o2o, sql, hm);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	//读写图片
	public void uploadImg(String outFileUrl,String inFileUrl,String inImgName) throws Exception{
		//获取源文件流
		File file = new File(outFileUrl);
		InputStream in = new FileInputStream(file);
		//判断文件路径是否存在
		File filePath = new File(inFileUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		//上传文件
		String path = inFileUrl + "/" + inImgName;
		FileOutputStream out = new FileOutputStream(path);
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
	
	//地址 经纬度
	@RequestMapping("/selectLatLon.action")
	public Map<String, Object> selectlatlon(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sql = "";
				sql = "select zcxx01,zcxx08 from w_zcxx where (Latitude is null or Longitude is NULL) and zcxx08 <> '';";
			List servicelist = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("servicelist", servicelist);
			}else{
				resultMap.put("STATE", "0");
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//插入经纬度
	@RequestMapping("/insertLatLon.action")
	public Map<String, Object> insertLatLon(HttpServletRequest request,
			HttpServletResponse response, String XmlData) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			XmlData= JLTools.unescape(XmlData);
			cds = new DataSet(XmlData);
			//注册码
			String zcxx01 = cds.getField("zcxx01", 0);
			//经度
			String lng = cds.getField("lng", 0);
			//纬度
			String lat = cds.getField("lat", 0);
			String sql = "update w_zcxx set Latitude ='"+lat+"', Longitude='"+lng+"' where zcxx01 ='"+zcxx01+"'";
			resultMap.put("STATE", "1");
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		return resultMap;
	}
	
}