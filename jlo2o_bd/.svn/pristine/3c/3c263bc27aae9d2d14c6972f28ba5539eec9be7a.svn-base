package com.jlsoft.o2o.interfacepackage.loop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.manageLogs.service.ManageLogs;
import com.jlsoft.o2o.interfacepackage.V7.V7LSD;
import com.jlsoft.o2o.interfacepackage.V7.V7SPXX;
import com.jlsoft.o2o.interfacepackage.V9.V9XSDD;

@Controller
@RequestMapping("/ErpSPKCXX")
public class ErpSPKCXX extends JLBill{
	@Autowired
	private V7SPXX v7spxx;
	@Autowired
	private ManageLogs manageLogs;
	
	/**
	 * @todo 轮询同步V7商品信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleTreatLSD")
	public Map handleTreatLSD() throws Exception {
		Map resultMap = new HashMap();
		String sql = "SELECT A.ZCXX59 URL,A.ZCXX60 UserName,A.ZCXX61 PassWord,ZCXX58 DJLX,(select IFNULL(max(erptime01),0) From w_goods) time01 " +
				"FROM W_ZCXX A WHERE A.ZCXX01='0000'";
		Map map = queryForMap(o2o, sql);
		if(map.get("DJLX").equals("SCMV7")){
			v7spxx.updateV7SPXX(map);

		}
		return resultMap;
	}
	
	/**
	 * @todo 付款成功调用ERP
	 * @param XSDD01
	 * @return
	 * @throws Exception 
	 */
	public boolean paySucessV7LSD(String XSDD01) throws Exception{
		boolean flag = false;
		String sql = "SELECT A.XSDD01,A.XSDDG02,C.ZCXX58 DJLX,C.ZCXX59 URL,C.ZCXX60 UserName,C.ZCXX61 PassWord," +
						   "PFD01 LSDH,PFSFK01 FXSKD,XSDD19 SHR,XSDD20 SHDZ,XSDD21 SHRDH " +
				           "FROM W_XSDDGROUP A,W_XSDD B,W_ZCXX C,W_DJZX D,W_GSLX E WHERE A.XSDD01=B.XSDD01 " +
				           "AND B.ZTID=C.ZCXX01 AND B.XSDD01=D.W_DJZX01 AND C.ZCXX01 = E.GSID AND E.LX=24 AND (A.XSDDG02 = 0 OR A.XSDDG02 = 5) AND D.W_DJZX02=14 AND B.XSDD01='"+XSDD01+"'";
		Map map = queryForMap(o2o,sql);

		return flag;
	}
	
	
	/**
	 * @todo 公共写日志
	 * @param xsdd01
	 * @param error
	 * @throws Exception 
	 */
	public void writeLog(String xsdd01,String error) throws Exception{
		Map errorMap=new HashMap();
		errorMap.put("DJLX", "2");//单据类型（默认为0）
		errorMap.put("YWDH", xsdd01);//业务单号
		errorMap.put("DFHM", "");//对方单号（默认为空）
		errorMap.put("CZR", "");//操作人
		errorMap.put("RZZT", "0");//日志状态（0失败;1成功）
		errorMap.put("ERROR",error);//错误信息
		manageLogs.writeLogs(errorMap);
	}
}
