package com.jlsoft.o2o.interfacepackage.jlinterface.TGYInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/TGYInterface")
public class TGYInterface extends JLBill {
	private static final Logger logger = Logger.getLogger(HttpClient.class);

	/*
	 * 3.1.1【QXY010001】汽修云用户注册通知
	 */
	@RequestMapping("/UserReg.action")
	public Map<String, Object> UserReg(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String CUS_ID = cds.getField("CUS_ID", 0);
			String sql = " SELECT 1 FROM W_ZCXX WHERE ZCXX01 = '" + CUS_ID
					+ "'";
			int num = queryForInt(o2o, sql);
			if (num != 0) {
				sql = " SELECT ZCXX01, ZCXX02, ZCXX03, ZCXX06, ZCXX08, ZCXX55, CURDATE() reqDate, NOW() reqTime,"
						+ " IFNULL((SELECT DQBZM02 FROM DQBZM WHERE DQBZM01 = W_ZCGS.ZCXX07),'000') CUS_AREA,"
						+ " (CASE ZCGS03 WHEN 43 THEN '01' ELSE '02' END ) CUS_TYPE, UNIX_TIMESTAMP() serialNo "
						+ " FROM W_ZCGS  WHERE ZCXX01 = '" + CUS_ID + "'";
				resultMap = (Map) queryForMap(o2o, sql);
				// 拼装XML
				xml.append("<ReqParam>");
				xml.append("<CUS_ID>" + resultMap.get("ZCXX01") + "</CUS_ID>");
				xml.append("<CUS_NAME>" + resultMap.get("ZCXX02")
						+ "</CUS_NAME>");
				xml.append("<CUS_AREA>" + resultMap.get("CUS_AREA")
						+ "</CUS_AREA>");
				xml.append("<CUS_ADDRESS>" + resultMap.get("ZCXX08")
						+ "</CUS_ADDRESS>");
				xml.append("<CONT_NAME>" + resultMap.get("ZCXX03")
						+ "</CONT_NAME>");
				xml.append("<CONT_TEL>" + resultMap.get("ZCXX06")
						+ "</CONT_TEL>");
				xml.append("<LANDLINE>" + resultMap.get("ZCXX55")
						+ "</LANDLINE>");
				xml.append("<OA_ZIP></OA_ZIP>");
				xml.append("<FAX_NO>" + resultMap.get("ZCXX55") + "</FAX_NO>");
				xml.append("<CUS_TYPE>" + resultMap.get("CUS_TYPE")
						+ "</CUS_TYPE>");
				xml.append("</ReqParam>");
				sendXml = "QXY010001|" + resultMap.get("ZCXX01") + "|010001|"
						+ resultMap.get("reqDate") + "|"
						+ resultMap.get("reqTime") + "|"
						+ resultMap.get("serialNo") + "|002|#";
				sendXml = sendXml + sendBody(xml.toString());
				System.out.println(sendXml);
				// 加密
				sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
						sendXml);
				resultMap.clear();
				String retXml = JLTools.sendToSync(sendXml, endpoint);
				// 解密
				retXml = DESUtil.decrypt(retXml, null);
				System.out.println(retXml);

				if (retXml.contains("000000")) {
					resultMap.put("result", "succeed");
					resultMap.put("state", "1");					
				} else {
					resultMap.put("result", "failure");
					resultMap.put("state", "0");
				}

			}
			
		} catch (Exception ex) {
			logger.info("Error to UserReg." + ex);
			resultMap.put("result", "failure");
			resultMap.put("state", "0");
		}
		return resultMap;
	}

	/*
	 * 3.1.2【QXY010002】支付账户注册
	 */
	@RequestMapping("/AccountReg.action")
	public Map<String, Object> AccountReg(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String CUS_ID = cds.getField("CUS_ID", 0);
			String sql = " SELECT 1 FROM W_ZCXX A, W_GSYHK B WHERE A.ZCXX01 = B.ZCXX01 AND A.ZCXX01 = '" + CUS_ID
					+ "'";
			int num = queryForInt(o2o, sql);
			if (num != 0) {
			    sql = " SELECT A.ZCXX01, A.ZCXX02, A.ZCXX32 CONT_NAME, A.ZCXX33 CONT_TEL, A.ZCXX09 EMAIL,"
					+ " (SELECT DPTP05 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 3) BUSI_LIC_NO, "
					+ " (SELECT DPTP05 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 4) INS_CODE, "
					+ " (SELECT DPTP05 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 5) TAX_REG_CODE, "
					+ " (SELECT DPTP05 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 6) BANK_ACC_PRM, "
					+ " B.GSYHK02 ACC_NO, B.GSYHK03 ACC_BANK, B.GSYHK04 ACC_PRO, B.GSYHK05 ACC_CTY,  B.GSYHK09 ACC_BCH,"
					+ " (SELECT DPTP02 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 3) IMG_BUSI_LIC, "
					+ " (SELECT DPTP02 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 1) IMG_LG_CERT_FRONT,"
					+ " (SELECT DPTP02 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 2) IMG_LG_CERT_BACK, "
					+ " (SELECT DPTP02 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 4) IMG_INS_CODE, "
					+ " (SELECT DPTP02 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 5) IMG_TAX_REG,"
					+ " (SELECT DPTP02 FROM W_DPTP WHERE ZCXX01 = A.ZCXX01 AND DPTP01 = 6) IMG_BANK_ACC_PRM, "
					+ " CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo "
					+ "  FROM W_ZCXX A, W_GSYHK B  WHERE A.ZCXX01 = B.ZCXX01 AND A.ZCXX01 = '"
					+ CUS_ID + "'";
			    resultMap = (Map) queryForMap(o2o, sql);
				// 拼装XML
				xml.append("<ReqParam>");
				xml.append("<CUS_ID>" + resultMap.get("ZCXX01") + "</CUS_ID>");
				xml.append("<CUS_NAME>" + resultMap.get("ZCXX02") + "</CUS_NAME>");
				xml.append("<BUSI_LIC_NO>" + resultMap.get("BUSI_LIC_NO") // 营业执照注册号
						+ "</BUSI_LIC_NO>");
				xml.append("<INS_CODE>" + resultMap.get("INS_CODE") // 组织机构代码
						+ "</INS_CODE>");
				xml.append("<TAX_REG_CODE>" + resultMap.get("TAX_REG_CODE") // 税务登记证
						+ "</TAX_REG_CODE>");
				xml.append("<BANK_ACC_PRM>" + resultMap.get("BANK_ACC_PRM") // 银行开户许可证
						+ "</BANK_ACC_PRM>");
				xml.append("<CONT_NAME>" + resultMap.get("CONT_NAME") // 联系人
						+ "</CONT_NAME>");
				xml.append("<CONT_TEL>" + resultMap.get("CONT_TEL") + "</CONT_TEL>"); // 联系电话
				xml.append("<EMAIL>" + resultMap.get("EMAIL") // 电子邮箱
						+ "</EMAIL>");
				xml.append("<ACC_BANK>" + resultMap.get("ACC_BANK") // 开户行
						+ "</ACC_BANK>");
				xml.append("<ACC_PRO>" + resultMap.get("ACC_PRO") // 开户省
						+ "</ACC_PRO>");
				xml.append("<ACC_CTY>" + resultMap.get("ACC_CTY") // 开户省
						+ "</ACC_CTY>");
				xml.append("<ACC_BCH>" + resultMap.get("ACC_BCH") // 开户分行支行名称
						+ "</ACC_BCH>");
				xml.append("<ACC_NO>" + resultMap.get("ACC_NO") // 账号
						+ "</ACC_NO>");
				xml.append("<IMG_BUSI_LIC>" + resultMap.get("IMG_BUSI_LIC") // 营业执照
						+ "</IMG_BUSI_LIC>");
				xml.append("<IMG_LG_CERT_FRONT>"
						+ resultMap.get("IMG_LG_CERT_FRONT") // 法人身份证正面
						+ "</IMG_LG_CERT_FRONT>");
				xml.append("<IMG_LG_CERT_BACK>" + resultMap.get("IMG_LG_CERT_BACK") // 法人身份证反面
						+ "</IMG_LG_CERT_BACK>");
				xml.append("<IMG_INS_CODE>" + resultMap.get("IMG_INS_CODE") // 组织机构代码
						+ "</IMG_INS_CODE>");
				xml.append("<IMG_TAX_REG>" + resultMap.get("IMG_TAX_REG") // 税务登记证
						+ "</IMG_TAX_REG>");
				xml.append("<IMG_BANK_ACC_PRM>" + resultMap.get("IMG_BANK_ACC_PRM") // 银行开户许可证
						+ "</IMG_BANK_ACC_PRM>");
				xml.append("</ReqParam>");
	
				sendXml = "QXY010002|" + resultMap.get("ZCXX01") + "|010002|"
						+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
						+ "|" + resultMap.get("serialNo") + "|002|#";
				sendXml = sendXml + sendBody(xml.toString());
				System.out.println(sendXml);
				// 加密
				sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
						sendXml);
				resultMap.clear();
				String retXml = JLTools.sendToSync(sendXml, endpoint);
				// 解密
				retXml = DESUtil.decrypt(retXml, null);
				System.out.println(retXml);
	
				if (retXml.contains("000000")) {
					resultMap.put("result", "succeed");
					resultMap.put("state", "1");					
				} else {
					resultMap.put("result", "failure");
					resultMap.put("state", "0");
				}
			} 
			
		} catch (Exception ex) {
			logger.info("Error to AccountReg." + ex);
			resultMap.put("result", "failure");
			resultMap.put("state", "0");
		}
		return resultMap;
	}

	/*
	 * 3.1.3【QXY010003】支付密码设置/修改
	 */
	@RequestMapping("/AccountOp.action")
	public Map<String, Object> AccountOp(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		try {
			cds = new DataSet(json);

			String sql = " SELECT CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo FROM DUAL ";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<CUS_ID>" + cds.getField("CUS_ID", 0) + "</CUS_ID>");
			xml.append("<CUS_NAME>" + cds.getField("CUS_NAME", 0)
					+ "</CUS_NAME>");
			xml.append("<TRANS_TYPE>" + cds.getField("TRANS_TYPE", 0)
					+ "</TRANS_TYPE>"); // 00：密码设置/修改; 01：提现; 03：订单支付
			xml.append("<PAY_NO>" + cds.getField("PAY_NO", 0) // 交易类型不为00时必输
					+ "</PAY_NO>");
			xml.append("</ReqParam>");

			sendXml = "QXY010003|" + cds.getField("CUS_ID", 0) + "|010003|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());

			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);

			System.out.println(sendXml);

			resultMap.clear();

			resultMap.put("result", sendXml);

		} catch (Exception ex) {
			logger.info("Error to AccountOp." + ex);
			resultMap.put("result", "failure");
		}
		return resultMap;
	}

	/*
	 * 3.1.4【QXY010004】银行账户绑定/解绑
	 */
	@RequestMapping("/BankBound.action")
	public Map<String, Object> BankBound(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String sql = " SELECT CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo FROM DUAL ";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<CUS_ID>" + cds.getField("CUS_ID", 0) + "</CUS_ID>");
			xml.append("<CUS_NAME>" + cds.getField("CUS_NAME", 0)
					+ "</CUS_NAME>");
			xml.append("<ACC_BANK>" + cds.getField("ACC_BANK", 0) // 开户行
					+ "</ACC_BANK>");
			xml.append("<ACC_PRO>" + cds.getField("ACC_PRO", 0) // 开户省
					+ "</ACC_PRO>");
			xml.append("<ACC_CTY>" + cds.getField("ACC_CTY", 0) // 开户市
					+ "</ACC_CTY>");
			xml.append("<ACC_BCH>" + cds.getField("ACC_BCH", 0) // 开户分行支行名称
					+ "</ACC_BCH>");
			xml.append("<ACC_BRANCH_NUM>" + cds.getField("ACC_BRANCH_NUM", 0) // 联行号
					+ "</ACC_BRANCH_NUM>");
			xml.append("<ACC_NO>" + cds.getField("ACC_NO", 0) // 账号GSYHK02
					+ "</ACC_NO>");
			xml.append("<OP_TYPE>" + cds.getField("OP_TYPE", 0) // 操作类型
					+ "</OP_TYPE>");
			xml.append("</ReqParam>");
			sendXml = "QXY010004|" + cds.getField("CUS_ID", 0) + "|010004|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());
			System.out.println(sendXml);
			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			String retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);

			resultMap.put("result", "succeed");
			resultMap.put("state", "1");

		} catch (Exception ex) {
			logger.info("Error to BankBound." + ex);
			resultMap.put("result", "failure");
			resultMap.put("state", "0");
		}
		return resultMap;
	}

	/*
	 * 3.1.5 【QXY010006】账户充值
	 */
	@RequestMapping("/AccountDeposit.action")
	public Map<String, Object> AccountDeposit(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		String sParam[] = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String CUS_ID = cds.getField("CUS_ID", 0);

			String sql = " SELECT CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo FROM DUAL ";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<PAY_NO>" + cds.getField("ORDER_NO", 0) + "</PAY_NO>");
			xml.append("<CUS_ID>" + cds.getField("CUS_ID", 0) + "</CUS_ID>");
			xml.append("<CUS_NAME>" + cds.getField("CUS_NAME", 0)
					+ "</CUS_NAME>");
			xml.append("<RCG_AMT>" + cds.getField("RCG_AMT", 0) + "</RCG_AMT>");
			xml.append("<PAY_CHANNEL>" + cds.getField("PAY_CHANNEL", 0) + "</PAY_CHANNEL>");
			// 通道费计算
			Map<String, Object> FeeMap = new HashMap<String, Object>();
			FeeMap.put("PAY_AMT", cds.getField("RCG_AMT", 0));
			FeeMap.put("PAY_CHANNEL", cds.getField("PAY_CHANNEL", 0));
			FeeMap.put("TRA_TYPE", "01");
			String FeeStr = JSONObject.fromObject(FeeMap).toString();
			String FEE_CHANNEL = FeeCalculate(FeeStr);
			sParam = FEE_CHANNEL.split("#");
			String FEE = sParam[1];
			FEE = getXmlNodeValue(FEE, "B_FEE_CHANNEL");
			xml.append("<FEE_CHANNEL>" + FEE + "</FEE_CHANNEL>");
			xml.append("<RT_PAGE_URL></RT_PAGE_URL>");		
			xml.append("</ReqParam>");

			sendXml = "QXY010006|" + CUS_ID + "|010006|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());
			System.out.println(sendXml);
			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			
			String retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);			

			if (retXml.contains("000000")) {
				resultMap.put("result", "succeed");
				resultMap.put("state", "1");					
			} else {
				resultMap.put("result", "failure");
				resultMap.put("state", "0");
			}

		} catch (Exception ex) {
			logger.info("Error to AccountDeposit." + ex);
			resultMap.put("result", "failure");
			resultMap.put("state", "0");
		}
		return resultMap;
	}

	/*
	 * 3.1.6【QXY010007】账户提现
	 */
	@RequestMapping("/AccountWithdrawal.action")
	public Map<String, Object> AccountWithdrawal(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		String sParam[] = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String CUS_ID = cds.getField("CUS_ID", 0);

			String sql = " SELECT CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo FROM DUAL ";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<PAY_NO>" + cds.getField("ORDER_NO", 0) + "</PAY_NO>");
			xml.append("<CUS_ID>" + cds.getField("CUS_ID", 0) + "</CUS_ID>");
			xml.append("<CUS_NAME>" + cds.getField("CUS_NAME", 0)
					+ "</CUS_NAME>");
			xml.append("<ACC_NO>" + cds.getField("ACC_NO", 0) + "</ACC_NO>");
			xml.append("<WDS_AMT>" + cds.getField("WDS_AMT", 0) + "</WDS_AMT>");
			// 通道费计算
			Map<String, Object> FeeMap = new HashMap<String, Object>();
			FeeMap.put("PAY_AMT", cds.getField("WDS_AMT", 0));
			FeeMap.put("PAY_CHANNEL", "YZF_B2B");
			FeeMap.put("TRA_TYPE", "02");
			String FeeStr = JSONObject.fromObject(FeeMap).toString();
			String FEE_CHANNEL = FeeCalculate(FeeStr);
			sParam = FEE_CHANNEL.split("#");
			String FEE = sParam[1];
			FEE = getXmlNodeValue(FEE, "B_FEE_CHANNEL");
			xml.append("<FEE_CHANNEL>" + FEE + "</FEE_CHANNEL>");
			xml.append("<RT_PAGE_URL></RT_PAGE_URL>");	
			xml.append("</ReqParam>");

			sendXml = "QXY010007|" + CUS_ID + "|010007|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());
			System.out.println(sendXml);	
			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			
			String retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);			

			if (retXml.contains("000000")) {
				resultMap.put("result", "succeed");
				resultMap.put("state", "1");					
			} else {
				resultMap.put("result", "failure");
				resultMap.put("state", "0");
			}
			
		} catch (Exception ex) {
			logger.info("Error to AccountWithdrawal." + ex);
			resultMap.put("result", "failure");
		}
		return resultMap;
	}

	/*
	 * 3.1.7【QXY010008】支付账户查询
	 */
	@RequestMapping("/AccountQry.action")
	public Map<String, Object> AccountQry(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		String sParam[] = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);

			String sql = " SELECT ZCXX02 CUS_NAME, CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo "
					+ " FROM W_ZCXX WHERE ZCXX01 = '" + cds.getField("CUS_ID", 0) + "'";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<CUS_ID>" + cds.getField("CUS_ID", 0) + "</CUS_ID>");
			xml.append("<CUS_NAME>" + resultMap.get("CUS_NAME") + "</CUS_NAME>");
			xml.append("</ReqParam>");

			sendXml = "QXY010008|" + cds.getField("CUS_ID", 0) + "|010008|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());

			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			String retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);
			
			if (retXml.contains("000000")) {
				sParam = retXml.split("#");
				retXml = sParam[1];
				String ACC_BLC = getXmlNodeValue(retXml, "ACC_BLC");
				resultMap.put("result", "succeed");
				resultMap.put("state", "1");	
				resultMap.put("ACC_BLC", ACC_BLC);
			} else {
				resultMap.put("result", "failure");
				resultMap.put("state", "0");
				resultMap.put("ACC_BLC", "");
			}

		} catch (Exception ex) {
			logger.info("Error to AccountQry." + ex);
			resultMap.put("result", "failure");
		}
		return resultMap;
	}

	/*
	 * 3.1.8【QXY010015】订单支付交易
	 */
	@RequestMapping("/TradeDate.action")
	public Map<String, Object> TradeDate(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMap1 = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		String sParam[] = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String ORDER_NO = cds.getField("ORDER_NO", 0);
			String sql = " SELECT 1 FROM W_XSDD A, W_XSDDSKFS B, W_ZCXX C"
					+ " WHERE A.XSDD01 = B.XSDD01 AND A.ZTID = C.ZCXX01"
					+ "   AND A.XSDD01 = '" + ORDER_NO + "'";
			int num = queryForInt(o2o, sql);
			if (num != 0) {
				sql = " SELECT A.XSDD01 PAY_NO, A.ZTID CUS_ID, C.ZCXX02 CUS_NAME, CURDATE() reqDate,"
						+ " IFNULL(ROUND(B.ZFJE,2),0) PAY_AMT, UNIX_TIMESTAMP() serialNo, NOW() reqTime "
						+ "  FROM W_XSDD A, W_XSDDSKFS B, W_ZCXX C"
						+ " WHERE A.XSDD01 = B.XSDD01 AND A.ZTID = C.ZCXX01"
						+ "   AND A.XSDD01 = '" + ORDER_NO + "'";
				resultMap = (Map) queryForMap(o2o, sql);
				xml.append("<ReqParam>");
				xml.append("<PAY_NO>" + resultMap.get("PAY_NO") + "</PAY_NO>");
				xml.append("<APPL_NO></APPL_NO>");
				xml.append("<CUS_ID>" + resultMap.get("CUS_ID") + "</CUS_ID>");
				xml.append("<CUS_NAME>" + resultMap.get("CUS_NAME")
						+ "</CUS_NAME>");
				xml.append("<PAY_AMT>" + resultMap.get("PAY_AMT")
						+ "</PAY_AMT>");
				xml.append("<PAY_CHANNEL>YZF</PAY_CHANNEL>");
				// 通道费计算
				Map<String, Object> FeeMap = new HashMap<String, Object>();
				FeeMap.put("PAY_AMT", resultMap.get("PAY_AMT"));
				FeeMap.put("PAY_CHANNEL", "YZF");
				FeeMap.put("TRA_TYPE", "03");
				String FeeStr = JSONObject.fromObject(FeeMap).toString();
				String FEE_CHANNEL = FeeCalculate(FeeStr);
				sParam = FEE_CHANNEL.split("#");
				String FEE = sParam[1];
				FEE = getXmlNodeValue(FEE, "B_FEE_CHANNEL");
				xml.append("<FEE_CHANNEL>" + FEE + "</FEE_CHANNEL>");
				xml.append("<RT_PAGE_URL></RT_PAGE_URL>");

				xml.append("<OrderList>");
				sql = " SELECT A.XSDD01 ORDER_NO, A.ZTID CUS_ID, C.ZCXX02 CUS_NAME, "
						+ " DATE_FORMAT(A.XSDD04,'%Y-%m-%d') PUR_DT, DATE_FORMAT(A.XSDD04,'%Y-%m-%d %T') ORDER_TM, "
						+ " ROUND((B.XSDDI02*B.XSDDI05),2) ORDER_AMT, IFNULL(ROUND(B.XSDDI03,2),0) DISC_AMT, A.XSDD20 DELV_ADDR, "
						+ " IFNULL(ROUND((B.XSDDI02*B.XSDDI05 - B.XSDDI03),2),0) PABLE_AMT, '01' ORDER_TYP, "
						+ " B.SPXX01 GOODS_ID, (SELECT SPXX04 FROM W_SPXX WHERE SPXX01 = B.SPXX01) GOODS_NAME, "
						+ " (CASE (SELECT SPECIFICATION FROM W_GOODS WHERE SPXX01 = B.SPXX01) WHEN '' THEN '无' END) SPEC_MODEL,"
						+ " B.XSDDI02 UNIT_PRICE, B.XSDDI05 GOODS_NUM"
						+ " FROM W_XSDD A, W_XSDDITEM B, W_ZCXX C "
						+ " WHERE A.XSDD01 = B.XSDD01 AND A.ZTID = C.ZCXX01"
						+ "  AND A.XSDD01 = '" + ORDER_NO + "'";
				List OrderList = queryForList(o2o, sql);
				if (OrderList != null) {
					for (int i = 0; i < OrderList.size(); i++) {
						resultMap1 = (Map) OrderList.get(i);
						xml.append("<OrderRow>");
						xml.append("<ORDER_NO>" + resultMap1.get("ORDER_NO")
								+ "</ORDER_NO>");
						xml.append("<CUS_ID>" + resultMap1.get("CUS_ID")
								+ "</CUS_ID>");
						xml.append("<CUS_NAME>" + resultMap1.get("CUS_NAME")
								+ "</CUS_NAME>");
						xml.append("<PUR_DT>" + resultMap1.get("PUR_DT")
								+ "</PUR_DT>");
						xml.append("<ORDER_TM>" + resultMap1.get("ORDER_TM")
								+ "</ORDER_TM>");
						xml.append("<ORDER_AMT>" + resultMap1.get("ORDER_AMT")
								+ "</ORDER_AMT>");
						xml.append("<DISC_AMT>" + resultMap1.get("DISC_AMT")
								+ "</DISC_AMT>");
						xml.append("<PABLE_AMT>" + resultMap1.get("PABLE_AMT")
								+ "</PABLE_AMT>");
						xml.append("<PAID_AMT>" + resultMap1.get("PABLE_AMT")
								+ "</PAID_AMT>");
						xml.append("<ORDER_TYP>" + resultMap1.get("ORDER_TYP")
								+ "</ORDER_TYP>");
						xml.append("<GOODS_ID>" + resultMap1.get("GOODS_ID")
								+ "</GOODS_ID>");
						xml.append("<GOODS_NAME>"
								+ resultMap1.get("GOODS_NAME")
								+ "</GOODS_NAME>");
						xml.append("<SPEC_MODEL>"
								+ resultMap1.get("SPEC_MODEL")
								+ "</SPEC_MODEL>");
						xml.append("<UNIT_PRICE>"
								+ resultMap1.get("UNIT_PRICE")
								+ "</UNIT_PRICE>");
						xml.append("<GOODS_NUM>" + resultMap1.get("GOODS_NUM")
								+ "</GOODS_NUM>");
						xml.append("<DELV_ADDR>" + resultMap1.get("DELV_ADDR")
								+ "</DELV_ADDR>");
						xml.append("</OrderRow>");
					}
					xml.append("</OrderList>");
					xml.append("</ReqParam>");
				}
				sendXml = "QXY010015|" + resultMap.get("CUS_ID") + "|010015|"
						+ resultMap.get("reqDate") + "|"
						+ resultMap.get("reqTime") + "|"
						+ resultMap.get("serialNo") + "|002|#";
				sendXml = sendXml + sendBody(xml.toString());
				System.out.println(sendXml);
				// 加密
				sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
						sendXml);
				resultMap.clear();
				String retXml = JLTools.sendToSync(sendXml, endpoint);
				// 解密
				retXml = DESUtil.decrypt(retXml, null);
				System.out.println(retXml);

				resultMap.put("result", retXml);
			} else {
				resultMap.put("result", "failure");
			}

		} catch (Exception ex) {
			logger.info("Error to TradeDate." + ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}

	/*
	 * 3.1.9【QXY010028】交易状态查询
	 */
	@RequestMapping("/TradeQry.action")
	public Map<String, Object> TradeQry(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		String sParam[] = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String ORDER_NO = cds.getField("ORDER_NO", 0);
			String sql = " SELECT A.ZTID CUS_ID, B.ZCXX02 CUS_NAME, A.XSDD01 PAY_NO, "
					+ " CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo "
					+ " FROM W_XSDD A, W_ZCXX B "
					+ " WHERE A.ZTID = B.ZCXX01 AND A.XSDD01 = '"
					+ ORDER_NO
					+ "'";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<CUS_ID>" + resultMap.get("CUS_ID") + "</CUS_ID>");
			xml.append("<CUS_NAME>" + resultMap.get("CUS_NAME") + "</CUS_NAME>");
			xml.append("<PAY_NO>" + resultMap.get("PAY_NO") + "</PAY_NO>");
			xml.append("</ReqParam>");

			sendXml = "QXY010028|" + resultMap.get("CUS_ID") + "|010028|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());
			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			String retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);

			resultMap.put("result", retXml);

		} catch (Exception ex) {
			logger.info("Error to TradeQry." + ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}

	/*
	 * 3.1.10【QXY010028】用户银行账户查询
	 */
	@RequestMapping("/BankQry.action")
	public Map<String, Object> BankQry(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String ORDER_NO = cds.getField("ORDER_NO", 0);
			String sql = " SELECT A.ZTID CUS_ID, B.ZCXX02 CUS_NAME, A.XSDD01 PAY_NO, "
					+ " CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo "
					+ " FROM W_XSDD A, W_ZCXX B "
					+ " WHERE A.ZTID = B.ZCXX01 AND A.XSDD01 = '"
					+ ORDER_NO
					+ "'";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<CUS_ID>" + resultMap.get("CUS_ID") + "</CUS_ID>");
			xml.append("<CUS_NAME>" + resultMap.get("CUS_NAME") + "</CUS_NAME>");
			xml.append("<PAY_NO>" + resultMap.get("PAY_NO") + "</PAY_NO>");
			xml.append("</ReqParam>");

			sendXml = "QXY010028|" + resultMap.get("CUS_ID") + "|010028|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());
			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			String retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);

			resultMap.put("result", retXml);

		} catch (Exception ex) {
			logger.info("Error to BankQry." + ex);
			resultMap.put("STATE", "failure");
		}
		return resultMap;
	}

	/*
	 * 3.1.12【QXY010035】通道费测算
	 */
	@RequestMapping("/FeeCalculate.action")
	public String FeeCalculate(String json) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		String sendXml = null;
		String retXml = null;
		try {
			String endpoint = "http://222.128.108.208:9081/cmis_tgy/httpAccess";
			cds = new DataSet(json);
			String PAY_AMT = cds.getField("PAY_AMT", 0);
			String PAY_CHANNEL = cds.getField("PAY_CHANNEL", 0);
			String TRA_TYPE = cds.getField("TRA_TYPE", 0);
			String sql = " SELECT CURDATE() reqDate, NOW() reqTime, UNIX_TIMESTAMP() serialNo FROM DUAL ";
			resultMap = (Map) queryForMap(o2o, sql);
			xml.append("<ReqParam>");
			xml.append("<PAY_AMT>" + PAY_AMT + "</PAY_AMT>");
			xml.append("<PAY_CHANNEL>" + PAY_CHANNEL + "</PAY_CHANNEL>");
			xml.append("<TRA_TYPE>" + TRA_TYPE + "</TRA_TYPE>");
			xml.append("</ReqParam>");
			// 加密
			sendXml = "QXY010035|" + "000" + "|010035|"
					+ resultMap.get("reqDate") + "|" + resultMap.get("reqTime")
					+ "|" + resultMap.get("serialNo") + "|002|#";
			sendXml = sendXml + sendBody(xml.toString());
			System.out.println(sendXml);
			// 加密
			sendXml = DESUtil.encrypt(DESUtil.TAG_QXY, DESUtil.TAG_END_QXY,
					sendXml);
			resultMap.clear();
			retXml = JLTools.sendToSync(sendXml, endpoint);
			// 解密
			retXml = DESUtil.decrypt(retXml, null);
			System.out.println(retXml);

			resultMap.put("result", retXml);
		} catch (Exception ex) {
			logger.info("Error to FeeCalculate." + ex);
			resultMap.put("STATE", "failure");
		}
		return retXml;
	}

	public String sendBody(String xmlBody) {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		result.append("<ReqData>");
		result.append(xmlBody);
		result.append("</ReqData>");
		return result.toString();
	}

	/**
	 * @todo 获取XML节点值
	 * @param xml
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	public String getXmlNodeValue(String xml, String nodeName) throws Exception {
		String nodeValue = "";
		Document document = DocumentHelper.parseText(xml);
		nodeValue = ((Element) document.selectSingleNode("//" + nodeName + ""))
				.getText();
		return nodeValue;
	}

}
