package com.jlsoft.init;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.interfacepackage.jlinterface.GopInterface;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping("/InitSPXX")
public class InitSPXX extends JLBill{
	@Autowired
	private GopInterface gopInterface;
	
	//出库单与安迅对接
	@RequestMapping("/sendGopCKD")
	public Map sendGopCKD(String CKDH) throws Exception{
		String sql = "";
		Map resultMap = new HashMap();
		//获取出库单主记录
		sql = "SELECT A.CKDH,B.CKDZ99 SHCK,A.BZ FROM W_CKD A,CK B WHERE A.SHCK=B.CK01 AND A.CKDH='"+CKDH+"'";
		resultMap = (Map)queryForMap(o2o,sql);
		//获取订单明细记录
		sql = "SELECT (SELECT BARCODE FROM W_GOODS WHERE SPXX01=A.SPXX01) barcode,CKSL num FROM W_CKDITEM A WHERE CKDH='"+CKDH+"'";
		List list = (List)queryForList(o2o,sql);
		resultMap.put("spcmList", list);
		//调用出库单
		gopInterface.transCKD2(resultMap);
		return resultMap;
	}
	
	/**
	 * @todo 更新商品串码批次号和序列号
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/startUpdateSPCM")
	public Map startUpdateSPCM() throws Exception{
		Map resultMap = new HashMap();
		String sql = "SELECT SPCM01 FROM W_SPCM";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			updateSPCM(map);
		}
		return resultMap;
	}
	
	@RequestMapping("/updateSPCM")
	public void updateSPCM(Map map) throws Exception{
		try{
			String spcm = map.get("SPCM01").toString();
			String xlh = "";
			String pch = spcm.substring(JLTools.getStrSplitNum(spcm,")",2)+1, JLTools.getStrSplitNum(spcm,"(",3));
			if(JLTools.getStringRepeatShowNum(spcm,"(") == 4){
				xlh = spcm.substring(JLTools.getStrSplitNum(spcm,")",3)+1, JLTools.getStrSplitNum(spcm,"(",4));
			}
			String sql = "UPDATE W_SPCM SET SPCM03='"+pch+"' ,SPCM04='"+xlh+"' WHERE SPCM01='"+spcm+"'";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//更新出库单串码表
	@RequestMapping("/updateCKDCM")
	public Map updateCKDCM() throws Exception{
		Map resultMap = new HashMap();
		String sql = "SELECT SPCM01 FROM W_CKDCM";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			updateCKDCMMethod(map);
		}
		return resultMap;
	}
	
	public void updateCKDCMMethod(Map map) throws Exception{
		try{
			String spcm = map.get("SPCM01").toString();
			String xlh = "";
			String pch = spcm.substring(JLTools.getStrSplitNum(spcm,")",2)+1, JLTools.getStrSplitNum(spcm,"(",3));
			if(JLTools.getStringRepeatShowNum(spcm,"(") == 4){
				xlh = spcm.substring(JLTools.getStrSplitNum(spcm,")",3)+1, JLTools.getStrSplitNum(spcm,"(",4));
			}
			String sql = "UPDATE W_CKDCM SET PCH='"+pch+"',XLH='"+xlh+"' WHERE SPCM01='"+spcm+"'";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * @todo 更新商品描述
	 * @return
	 */
	@RequestMapping("/startUpdateSPMS")
	public Map startUpdateSPMS(){
		Map resultMap = new HashMap();
		String sql = "SELECT BARCODE,SPMS FROM TEMP_SPMS WHERE FLAG=0";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			sql = "SELECT A.SPXX01,A.ZCXX01 FROM W_GOODS A,W_SPXX B WHERE A.SPXX01=B.SPXX01 AND A.BARCODE='"+map.get("BARCODE").toString()+"'";
			Map spmsMap = queryForMap(o2o,sql);
			if(checkMapValue(spmsMap,"SPXX01") && checkMapValue(spmsMap,"ZCXX01")){
				map.put("SPXX01", spmsMap.get("SPXX01").toString());
				map.put("ZCXX01", spmsMap.get("ZCXX01").toString());
				updateSPMS(map);
			}
		}
		return resultMap;
	}
	
	public void updateSPMS(Map map){
		try{
			String sql = "UPDATE W_SPGL SET BZ='"+map.get("SPMS")+"' WHERE ZCXX01='"+map.get("ZCXX01").toString()+"' AND SPXX01="+map.get("SPXX01").toString()+"";
			execSQL(o2o, sql, map);
			//更新成功
			sql = "UPDATE TEMP_SPMS SET FLAG=1,ERROR='' WHERE BARCODE='"+map.get("BARCODE").toString()+"'";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			ex.printStackTrace();
			//更新错误信息
			try{
				String sqlError = "UPDATE TEMP_SPMS SET FLAG=0,ERROR='"+ex+"' WHERE BARCODE='"+map.get("BARCODE")+"'";
				execSQL(o2o, sqlError, map);
			}catch(Exception e){	
			}
		}
	}
	
	/**
	 * @todo 修改商品规格
	 * @return
	 */
	@RequestMapping("/startUpdateGGXH")
	public Map startUpdateGGXH(){
		Map resultMap = new HashMap();
		String sql = "SELECT BARCODE,GGXH FROM TEMP_SPGGXH WHERE FLAG=0";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			sql = "SELECT A.SPXX01 FROM W_GOODS A,W_SPXX B WHERE A.SPXX01=B.SPXX01 AND A.BARCODE='"+map.get("BARCODE").toString()+"'";
			Map ggxhMap = queryForMap(o2o,sql);
			if(checkMapValue(ggxhMap,"SPXX01")){
				map.put("SPXX01", ggxhMap.get("SPXX01").toString());
				updateGGXH(map);
			}
		}
		return resultMap;
	}
	
	public void updateGGXH(Map map){
		try{
			String sql = "UPDATE W_SPXX SET GGXH='"+map.get("GGXH").toString()+"' WHERE SPXX01="+map.get("SPXX01").toString()+"";
			execSQL(o2o, sql, map);
			//更新成功
			sql = "UPDATE TEMP_SPGGXH SET FLAG=1,ERROR='' WHERE BARCODE='"+map.get("BARCODE").toString()+"'";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			ex.printStackTrace();
			//更新错误信息
			try{
				String sqlError = "UPDATE TEMP_SPGGXH SET FLAG=0,ERROR='"+ex+"' WHERE BARCODE='"+map.get("BARCODE")+"'";
				execSQL(o2o, sqlError, map);
			}catch(Exception e){	
			}
		}
	}
	
	/**
	 * @todo 开始导入商品信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/startMethod")
	public Map startMethod(){
		Map resultMap = new HashMap();
		String sql = "SELECT ZCXX02,SPXX04,SPFL02,PPB02,BARCODE,SPGL24,ZJCLJH,ORIGIN_CPLACE,SPXX09,SPXX10,SPXX11,SPXX12," +
				           "SPGL04,GGXH,JLDW02,SPTP1,SPTP2,SPTP3,SPTP4,SPTP5,SPGL04 LSJ FROM TEMP_SPXX WHERE FLAG=0";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			//查询ZCXX01判断该生产厂家是否存在
			sql = "SELECT ZCXX01 FROM W_ZCGS WHERE ZCXX02='"+map.get("ZCXX02").toString()+"'";
			Map gsMap = queryForMap(o2o,sql);
			if(checkMapValue(gsMap,"ZCXX01")){
				map.put("ZCXX01", gsMap.get("ZCXX01").toString());
				insertSP(map);
			}
		}
		return resultMap;
	}
	
	/**
	 * @todo 插入商品
	 * @param map
	 */
	public void insertSP(Map map){
		try{
			String sql = "";
			//判断厂家自编码是否已存在
			if(checkMapValue(map,"SPGL24")){
				sql = "SELECT COUNT(0) FROM W_SPGL WHERE SPGL24='"+map.get("SPGL24").toString()+"'";
				if(queryForInt(o2o,sql)>0){
					throw new Exception("厂家自编码已存在");
				}
			}
			//判断主机厂零件号是否已存在
			if(checkMapValue(map,"ZJCLJH")){
				sql = "SELECT COUNT(0) FROM W_SPGLLJH WHERE SPGLLJH01='"+map.get("ZJCLJH").toString()+"'";
				if(queryForInt(o2o,sql)>0){
					throw new Exception("主机厂零件号已存在");
				}
			}
			//判断条码是否已存在
			sql="SELECT COUNT(0) FROM W_GOODS WHERE BARCODE='"+map.get("BARCODE").toString()+"'";
			if(queryForInt(o2o,sql) == 0){
				//获取商品ID
				int spxx01=PubFun.updateWBHZT(o2o,"W_SPXX",1);
				map.put("SPXX01", new Integer(spxx01));
				String spxx02 = JLTools.getID_X(spxx01, 6);
				map.put("SPXX02", spxx02);
				//获取PPB01
				sql = "SELECT PPB01 FROM PPB WHERE ZCXX01='"+map.get("ZCXX01")+"' AND PPB02='"+map.get("PPB02")+"'";
				Map ppMap = queryForMap(o2o,sql);
				if(checkMapValue(ppMap,"PPB01")){
					map.put("PPB01", ppMap.get("PPB01"));
				}else{
					map.put("PPB01", "");
				}
				//获取JLDW01
				sql = "SELECT JLDW01 FROM W_JLDW WHERE JLDW02='"+map.get("JLDW02")+"'";
				Map jldwMap = queryForMap(o2o,sql);
				if(checkMapValue(jldwMap,"JLDW01")){
					map.put("JLDW01", jldwMap.get("JLDW01"));
				}else{
					map.put("JLDW01", "");
				}
				//获取SPFL01
				map.put("SPFL01", "");
				//插入W_GOODS表
				insertW_GOODS(map);
				//插入W_SPXXDZ
				insertW_SPXXDZ(map);
				//插入W_SPXX
				insertW_SPXX(map);
				//插入W_SPGL
				insertW_SPGL(map);
				//插入W_KCXX
				insertW_KCXX(map);
				//插入W_SPJGB
				insertW_SPJGB(map);
				//插入W_SPGLLJH
				if(checkMapValue(map,"ZJCLJH")){
					insertW_SPGLLJH(map);
				}
				//图片处理
				//createSPTPFile(map);
				//车系 分类 未处理
				
				//插入成功
				sql="UPDATE TEMP_SPXX SET FLAG=1,ERROR='' WHERE BARCODE='"+map.get("BARCODE")+"'";
				execSQL(o2o, sql, map);
			}else{
				throw new Exception("条码已存在");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			//更新错误信息
			try{
				String sqlError = "UPDATE TEMP_SPXX SET FLAG=0,ERROR='"+ex+"' WHERE BARCODE='"+map.get("BARCODE")+"'";
				execSQL(o2o, sqlError, map);
			}catch(Exception e){	
			}
		}
	}
	
	/**
	 * @todo 插入W_GOODS表
	 * @param map
	 * @throws Exception
	 */
	public void insertW_GOODS(Map map) throws Exception{
		try{
			String sql = "INSERT INTO W_GOODS(BARCODE,SPXX04,ORIGIN_CPLACE,GOODS_STATUS,LISTDATE,SPXX01,ZCXX01,SJLY) " +
					           "VALUES(BARCODE?,SPXX04?,ORIGIN_CPLACE?,1,now(),SPXX01?,ZCXX01?,2)";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入W_SPXXDZ
	 * @param map
	 * @throws Exception 
	 */
	public void insertW_SPXXDZ(Map map) throws Exception{
		try{
			String sql = "INSERT INTO W_SPXXDZ(SPXX01,ERP_SPXX01,ERP01,CKSP12) " +
							   "VALUES("+map.get("SPXX01")+","+map.get("SPXX01")+",'"+map.get("ZCXX01")+"',0)";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入W_SPXX
	 * @param map
	 * @throws Exception
	 */
	public void insertW_SPXX(Map map) throws Exception{
		try{
			String sql="INSERT INTO W_SPXX(SPXX01,SPXX04,PPB01,PPB02,SPXX02,JLDW01,SPXX09,SPXX10,SPXX11,GGXH,SPXX12) "+
							 "VALUES(SPXX01?,SPXX04?,PPB01?,PPB02?,SPXX02?,JLDW01?,SPXX09?,SPXX10?,SPXX11?,GGXH?,SPXX12?)";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入W_SPGL
	 * @param map
	 * @throws Exception
	 */
	public void insertW_SPGL(Map map) throws Exception{
		try{
			String sql="INSERT INTO W_SPGL(SPXX01,ZCXX01,SPGL02,SPGL03,SPGL04,SPGL12,SPGL13,SPGL14,SPGL15,SPGL16,SPGL18,CKSP12,SPGL24,TIME01) " +
					         "VALUES(SPXX01?,ZCXX01?,2,sysdate(),0,1,0,1,999,0,sysdate(),0,SPGL24?,nowts())";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入W_KCXX
	 * @param map
	 * @throws Exception
	 */
	public void insertW_KCXX(Map map) throws Exception{
		try{
			String sql="INSERT INTO W_KCXX(ZCXX01,SPXX01,CKSP04,CK01) " +
					         "VALUES(ZCXX01?,SPXX01?,9999,ZCXX01?)";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入W_SPJGB
	 * @param map
	 * @throws Exception
	 */
	public void insertW_SPJGB(Map map) throws Exception{
		try{
			String sql="INSERT INTO W_SPJGB(ZCXX01,SPXX01,SPJGB01,SPJGB05) VALUES(ZCXX01?,SPXX01?,LSJ?,LSJ?)";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * @todo 插入W_SPGLLJH
	 * @param map
	 * @throws Exception
	 */
	public void insertW_SPGLLJH(Map map) throws Exception{
		try{
			String sql="INSERT INTO W_SPGLLJH(ZCXX01,SPXX01,SPGLLJH01) VALUES(ZCXX01?,SPXX01?,ZJCLJH?)";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	//对图片进行处理
	public void createSPTPFile(Map map) throws Exception{
		//图片1 处理
		if(checkMapValue(map,"SPTP1")){
			sptpCL(map,"SPTP1",1);
		}
		//图片2 处理
		if(checkMapValue(map,"SPTP2")){
			sptpCL(map,"SPTP2",2);
		}
		//图片3 处理
		if(checkMapValue(map,"SPTP3")){
			sptpCL(map,"SPTP3",3);
		}
		//图片4处理
		if(checkMapValue(map,"SPTP4")){
			sptpCL(map,"SPTP4",4);
		}
		//图片5处理
		if(checkMapValue(map,"SPTP5")){
			sptpCL(map,"SPTP5",5);
		}
	}
	
	//单个商品图片处理
	public void sptpCL(Map map,String yImgName,int sxh) throws Exception{
		try{
			String outFileUrl = "c:/oldTP/"+map.get("WJMC").toString()+"/"+yImgName;
			String fileUrl = "c:/sptp/"+map.get("ZCXX01").toString()+"/"+map.get("SPXX02").toString()+"/images";
			//生成图片原图
			String imgName = map.get("SPXX02").toString()+"_"+sxh+"_big."+yImgName.split("\\.")[1];
			uploadImg(outFileUrl,fileUrl,imgName);
			//生成二分之一图片
			imgName = map.get("SPXX02").toString()+"_"+sxh+"_mid."+yImgName.split("\\.")[1];
			uploadScaleImg(outFileUrl,fileUrl,imgName,50);
			//生成四分之一图片
			imgName = map.get("SPXX02").toString()+"_"+sxh+"_small."+yImgName.split("\\.")[1];
			uploadScaleImg(outFileUrl,fileUrl,imgName,25);
			//写入数据库
			String fileName = map.get("SPXX02").toString()+"_"+sxh+"."+yImgName.split("\\.")[1];
			String sql = "DELETE FROM W_SPTP WHERE ZCXX01='"+map.get("ZCXX01")+"' AND SPXX01="+map.get("SPXX01")+" AND SPTP01="+sxh+"";
			execSQL(o2o, sql, map);
			sql = "INSERT INTO W_SPTP(ZCXX01,SPXX01,SPTP01,SPTP02) " +
							   "VALUES('"+map.get("ZCXX01").toString()+"','"+map.get("SPXX01").toString()+"','"+sxh+"','"+fileName+"')";
			execSQL(o2o, sql, map);
		}catch(Exception ex){
			throw ex;
		}
	}
	
	//对单个商品多图片处理
	@RequestMapping("/manageMoreSPTP")
	public Map manageMoreSPTP() throws NumberFormatException, Exception{
		Map resultMap = new HashMap();
		String sql = "SELECT BARCODE,WJMC FROM TEMP_SPTP WHERE FLAG=0";
		List list = queryForList(o2o,sql);
		Map map;
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			sql = "SELECT A.ZCXX01,A.SPXX01,B.SPXX02 FROM W_GOODS A,W_SPXX B WHERE A.SPXX01=B.SPXX01 AND A.BARCODE='"+map.get("BARCODE").toString()+"'";
			Map spxxMap = queryForMap(o2o,sql);
			if(checkMapValue(spxxMap,"SPXX01") && checkMapValue(spxxMap,"ZCXX01")){
				map.put("ZCXX01", spxxMap.get("ZCXX01").toString());
				map.put("SPXX01", spxxMap.get("SPXX01"));
				map.put("SPXX02", spxxMap.get("SPXX02").toString());
				updateSPTP(map);
			}
		}
		return resultMap;
	}
	
	public void updateSPTP(Map map){
		try{
			//根据文件找图片路劲
			String path = "C:/oldTP/"+map.get("WJMC").toString();
			File file = new File(path);
			if(file.isDirectory()){
				String[] fileList = file.list();
				for(int j=0;j<fileList.length;j++){
					sptpCL(map,fileList[j],Integer.parseInt(fileList[j].split("\\.")[0]));
				}
				//更新成功
				String sql = "UPDATE TEMP_SPTP SET FLAG=1,ERROR='' WHERE BARCODE='"+map.get("BARCODE").toString()+"'";
				execSQL(o2o, sql, map);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			//更新错误信息
			try{
				String sqlError = "UPDATE TEMP_SPTP SET FLAG=0,ERROR='"+ex+"' WHERE BARCODE='"+map.get("BARCODE")+"'";
				execSQL(o2o, sqlError, map);
			}catch(Exception e){
			}
		}
	}
	
	//读写图片
	public void uploadImg(String outFileUrl,String fileUrl,String imgName) throws Exception{
		//获取源文件流
		File file = new File(outFileUrl);
		InputStream in = new FileInputStream(file);
		//判断文件路径是否存在
		File filePath = new File(fileUrl);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		//上传文件
		String path = fileUrl + "/" + imgName;
		FileOutputStream out = new FileOutputStream(path);
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
	
	//写压缩图片
	public void uploadScaleImg(String outFileUrl,String fileUrl,String imgName,int scale) throws Exception{
		try {
	    	  //获取路径生成文件
			  String path =fileUrl;
	          path = path.replace("\\", "/");
	          File file3 = new File(path);
	          if (!file3.exists()) {
	        	  file3.mkdirs();
	  		  }
	          //开始写流
	          File file = new File(outFileUrl);
	  		  InputStream in = new FileInputStream(file);
		      Image src = javax.imageio.ImageIO.read(in);
		      int width = (int) (src.getWidth(null) * scale / 100.0);
		      int height = (int) (src.getHeight(null) * scale / 100.0);
		      BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		      bufferedImage.getGraphics().drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
		      String fileName=imgName;
		      OutputStream os = new FileOutputStream(path+"/"+fileName);
		      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		      encoder.encode(bufferedImage);
		      os.flush();
		      os.close();
		} catch (Exception ex) {
			throw ex;
		} 
	}
	
	/**
	 * @todo 判断map值是否有效
	 * @param map
	 * @param key
	 * @return
	 */
	public boolean checkMapValue(Map map,String key){
		boolean flag = false;
		if(map != null && map.get(key) != null && !map.get(key).toString().equals("")){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @todo 删除单商品
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteSPXX")
	public Map deleteSPXX(String barcode) throws Exception{
		Map resultMap = new HashMap();
		String sql = "SELECT SPXX01,ZCXX01 FROM W_GOODS WHERE BARCODE='"+barcode+"'";
		Map map = queryForMap(o2o,sql);
		if(map != null){
			String spxx01=map.get("SPXX01").toString();
			String zcxx01=map.get("ZCXX01").toString();
			if(spxx01 != null && spxx01.length()>0){
				//删除W_SPXX
				sql="DELETE FROM W_SPXX WHERE SPXX01="+spxx01+"";
				execSQL(o2o, sql, resultMap);
				//删除W_SPGL
				sql="DELETE FROM W_SPGL WHERE SPXX01="+spxx01+" AND ZCXX01='"+zcxx01+"'";
				execSQL(o2o, sql, resultMap);
				//删除W_KCXX
				sql="DELETE FROM W_KCXX WHERE SPXX01="+spxx01+" AND ZCXX01='"+zcxx01+"'";
				execSQL(o2o, sql, resultMap);
				//删除W_SPJGB
				sql="DELETE FROM W_SPJGB WHERE SPXX01="+spxx01+" AND ZCXX01='"+zcxx01+"'";
				execSQL(o2o, sql, resultMap);
				//删除W_SPGLLJH
				sql="DELETE FROM W_SPGLLJH WHERE SPXX01="+spxx01+" AND ZCXX01='"+zcxx01+"'";
				execSQL(o2o, sql, resultMap);
				//删除W_SPTP
				sql="DELETE FROM W_SPTP WHERE SPXX01="+spxx01+" AND ZCXX01='"+zcxx01+"'";
				execSQL(o2o, sql, resultMap);
				//删除W_SPXXDZ
				sql="DELETE FROM W_SPXXDZ WHERE SPXX01="+spxx01+"";
				execSQL(o2o, sql, resultMap);
				//删除W_GOODS
				sql="DELETE FROM W_GOODS WHERE BARCODE='"+barcode+"' AND SPXX01="+spxx01+"";
				execSQL(o2o, sql, resultMap);
			}
		}
		return resultMap;
	}
	
}