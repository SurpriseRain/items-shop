package com.jlsoft.init;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.o2o.interfacepackage.V9.V9RKD;
import com.jlsoft.o2o.pub.service.KmsService;
import com.jlsoft.o2o.pub.service.PubService;
import com.jlsoft.utils.JLTools;
import com.jlsoft.utils.PubFun;

@Controller
@RequestMapping("/InitOutXml")
public class InitOutXml extends JLBill {
	@Autowired
	private PubService pubService;
	@Autowired
	private V9RKD v9RKD;
	@Autowired
	private KmsService kmsService;
	
	/**
	 * @todo 初始修改出库单串码数据
	 * @param fileName
	 * @param CKDH
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	@RequestMapping("/updateW_CKDCM")
	public Map updateW_CKDCM(String fileName,String CKDH) throws Exception{
		String sql = "";
		File file = new File("c:/"+fileName);
		SAXBuilder reader = new SAXBuilder();   
    	Document doc=(Document) reader.build(file);
    	//取的根元素
    	Element root = doc.getRootElement();
    	List head = root.getChildren("Head");
        Element e = (Element) head.get(0);
        String CompanyCode = e.getChild("CompanyCode").getValue();  //厂商识别码   特别备注：系统存放表w_zcgssbm 现暂未处理
		String hyglm = e.getChild("GuildCode").getValue();                      //行业管理码
		String PositionCode = e.getChild("PositionCode").getValue();       //位置码
		//获取明细数据
		Element Body = root.getChild("Body");
		Element TraceCodeRelationList = Body.getChild("TraceCodeRelationList");
		Element TraceCodeRelation = TraceCodeRelationList.getChild("TraceCodeRelation");
		List Relation = TraceCodeRelation.getChildren("Relation");  //包装信息，有：包装比例，包装零件名称等
		//删除W_CKDCM表数据
		sql="DELETE FROM W_CKDCM WHERE CKDH='"+CKDH+"'";
		execSQL(o2o, sql, new HashMap()); 
		for(int i=0;i<Relation.size();i++){
			//读取Relation节点的元素
			Element e_body = (Element) Relation.get(i); 
			//获取并循环 TraceCode节点
			String barcode = e_body.getAttribute("projectCode").getValue();
			//查询商品内码
			sql = "SELECT SPXX01 FROM W_GOODS WHERE BARCODE='"+barcode+"'";
			Map map = queryForMap(o2o,sql);
			String spxx01=map.get("SPXX01").toString();
			if(map != null){
				//获取包装关系明细
				for(int j=0;j<e_body.getChildren("TraceCode").size();j++){
					Element e_bodys = (Element)e_body.getChildren("TraceCode").get(j);
					String packLevel = e_bodys.getAttribute("packLevel").getValue();//包装级别
					String spcm01 = e_bodys.getAttribute("curCode").getValue();//当前串码
					sql = "SELECT COUNT(0) FROM W_CKDCM WHERE SPCM01='"+spcm01+"'";
					if(queryForInt(o2o,sql) == 0){
						if(packLevel.equals("0")){
							//批次号，序列号
							String xlh = "";
							String pch = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",2)+1, JLTools.getStrSplitNum(spcm01,"(",3));
							if(JLTools.getStringRepeatShowNum(spcm01,"(") == 4){
								xlh = spcm01.substring(JLTools.getStrSplitNum(spcm01,")",3)+1, JLTools.getStrSplitNum(spcm01,"(",4));
							}
							//插入W_CKDCM表
							sql="INSERT INTO W_CKDCM(CKDH,SPXX01,SPCM01,BARCODE,PCH,XLH) VALUES('"+CKDH+"',"+spxx01+",'"+spcm01+"','"+barcode+"','"+pch+"','"+xlh+"')";
							execSQL(o2o, sql, new HashMap()); 
						}
					}else{
						throw new Exception(barcode+" 该串码已出库");
					}
				}
			}else{
				throw new Exception(barcode+" 该串码不存在");
			}
		}
		Map map = new HashMap();
		return map;
	}
	
	/**
	 * @todo 生成出库单，并导入ERP
	 * @param CKDH
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/insertW_RKD")
	public Map insertW_RKD(String CKDH) throws Exception{
		String sql = "";
		//获取入库单号
		String RKDH = "RK"+JLTools.getID_X(PubFun.updateWBHZT(o2o,"W_RKD",1),10);
		//插入W_RKD
		sql = "INSERT INTO W_RKD(RKDH,ZCXX01,CKDH,SHCK,RKSJ,BZ) " +
				 "SELECT '"+RKDH+"',ZCXX01,CKDH,'01',NOW(),'系统导入' FROM W_CKD WHERE CKDH='"+CKDH+"'";
		execSQL(o2o, sql, new HashMap()); 
		//插入W_RKDITEM
		sql = "INSERT INTO W_RKDITEM(RKDH,SPXX01,RKSL) " +
				 "SELECT '"+RKDH+"',SPXX01,CKSL FROM W_CKDITEM WHERE CKDH='"+CKDH+"'";
		execSQL(o2o, sql, new HashMap()); 
		//更新W_CKDITEM入库数量
		sql = "UPDATE W_CKDITEM SET RKSL=CKSL WHERE CKDH='"+CKDH+"'";
		execSQL(o2o, sql, new HashMap());
		//插入W_RKDCM
		sql = "INSERT INTO W_RKDCM(RKDH,SPXX01,SPCM01,BARCODE,PCH,XLH) " +
				 "SELECT '"+RKDH+"',SPXX01,SPCM01,BARCODE,PCH,XLH FROM W_CKDCM WHERE CKDH='"+CKDH+"'";
		execSQL(o2o, sql, new HashMap()); 
		//与ERP交互
		sql = "SELECT ZCXX01 FROM W_RKD WHERE RKDH='"+RKDH+"'";
		Map map = queryForMap(o2o,sql);
		String ZCXX01=map.get("ZCXX01").toString();
		Map erpMap = pubService.getECSURL(ZCXX01);
		String returnStr = v9RKD.createRKD(erpMap,CKDH,RKDH);
		JSONObject jsonObject = JSONObject.fromObject(returnStr);
		Map returnData =(Map) jsonObject.get("data");
		if(returnData.get("JL_State").equals("1")){
			//写入库存信息
			String queryRKD="SELECT ZCXX01, SPXX01, SHCK, RKSL FROM w_rkd LEFT JOIN w_rkditem ON w_rkditem.RKDH = w_rkd.RKDH where w_rkd.RKDH='"+RKDH+"'";
			List ckList = queryForList(o2o, queryRKD);
			for(int i =0;i<ckList.size();i++){
				Map ckMap = (Map) ckList.get(i);
				kmsService.insertGwcSpxx(ckMap.get("ZCXX01").toString(), Double.valueOf(ckMap.get("SPXX01").toString()),ckMap.get("RKSL").toString(), 0, 0, 0, ckMap.get("SHCK").toString(), "0", 1, RKDH, Integer.parseInt(ckMap.get("RKSL").toString()), 0);
			}
		}else{
			throw new Exception("与ERP交互失败"+returnData.get("JL_ERR").toString());
		}
		return map;
	}
	
}
