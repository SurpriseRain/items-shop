package com.jlsoft.o2o.qxgl.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.JlAppSqlConfig;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.o2o.interfacepackage.V9.V9BasicData;
import com.jlsoft.o2o.pub.service.PubService;
@SuppressWarnings("unused")
@Controller
@RequestMapping("/Oper_CDQX")
public class Oper_CDQX extends JLBill {
	@Autowired
	private PubService pubService;
	@Autowired
	private V9BasicData v9BasicData;
	public Oper_CDQX() {
	}
	public Oper_CDQX(JdbcTemplate o2o){
		this.o2o = o2o;
	}
    private SqlSession session = null; 
/**
* @Title: selectPersonMenu 
* @Description: 查询当前用户的权限
* @param @param XmlData
* @return Map 返回类型
* @author NineDragon
*/
@RequestMapping("/selectPersonMenu.action")
public Map selectPersonMenu(String XmlData) throws Exception {
		cds=new DataSet(XmlData);
		String s= cds.getField("person_id", 0);
		String sql="SELECT CODE FLCODE,NAME FLNAME,(CASE WHEN LENGTH(CODE)=2 THEN '0' ELSE SUBSTR(CODE,1,2)END)  FATHERCODE, NULL FATHERNAME,W_XTCZYCZ.cd FROM W_XTCZYMENU LEFT JOIN (SELECT * FROM W_XTCZYCZ WHERE person_id = '"+s+"')W_XTCZYCZ ON W_XTCZYCZ.cd = W_XTCZYMENU.code";
		List spfllist = queryForList(o2o,sql);
    	Map map = new HashMap();
    	map.put("spfllist", spfllist);
		return map;
    }	
/**
 * @Title: saveUserQXFP 
 * @Description: 授权跟新，选中账号的权限菜单 
 * @param @param XmlData
 * @return Map 返回类型
 * @author NineDragon
 */
@RequestMapping("/saveUserQXFP.action")
public Map saveUserQXFP(String XmlData) throws Exception {
		cds=new DataSet(XmlData);
		//System.out.println(XmlData);
		String check_val =cds.getField("check_val", 0);
		//check_val=check_val.replaceAll("\"", "'");
		String check_vals []=check_val.split(",");
		String del="delete from W_XTCZYCZ where person_id ='"+cds.getField("person_id",0)+"'";
		Map	row = getRow(del, null, 0);
		execSQL(o2o, del, row);
		for(int i=0;i<check_vals.length;i++)
		{
			String insert="INSERT INTO W_XTCZYCZ (cd,person_id)VALUES('"+check_vals[i]+"','"+cds.getField("person_id",0)+"')";
			Map	row_ = getRow(insert, null, 0);
			execSQL(o2o, insert, row_);
		}
    	Map map = new HashMap();
		return map;
    }	
}