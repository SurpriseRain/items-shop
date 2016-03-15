package com.jlsoft.c2b.service; 

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

/**
 * 维修厂发布需求信息
 * @author 齐俊宇
 * @version 1.0
 * @date 2015年9月11日
 *
 */
@Controller
@RequestMapping("/RepairDemand")
public class RepairDemand extends JLBill{
	/**
	 * 商品需求发布
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert_Produce.action")
	public Map<String, Object> insert_Produce(String jsonData) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds = new DataSet(jsonData);
		try {
			// 设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 当前系统时间 —> 发布时间
			String PublishTime = df.format(new Date()).toString();
			// 获取页面的有效时间 -> 无效时间 = (发布时间 + 页面的有效时间);
			String InvalidTime = cds.getField("InvalidTime", 0);
			String sql = "";
			// 插入发布需求信息
			sql = "INSERT INTO producedemand(BuyerId, PublishTime, InvalidTime, ProduceName, BuyCount, QuotedPrice, DemandDescription, DemandStatus) "
					+ "VALUES(BuyerId?, '" + PublishTime + "', "
					+ "DATE_ADD('" + PublishTime + "',INTERVAL '" + InvalidTime + "' HOUR), "
					+ "ProduceName?, BuyCount?, QuotedPrice?, DemandDescription?, 0)";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			returnMap.put("STATE", "1");
		} catch (Exception ex) {
			returnMap.put("STATE", "0");
			throw ex;
		}
		return returnMap;
	}
	
	/**
	 * 维修厂外协需求
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert_OutSourcing.action")
	public Map<String, Object> insert_OutSourcing(String jsonData) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds = new DataSet(jsonData);
		try {
			String sql = "";
			sql = "INSERT INTO OutSourcing(OutSourcingContent, PublisherId, Contact, ContactPhone, ContactAddress) "
					+ "VALUES(OutSourcingContent?, PublisherId?, Contact?, ContactPhone?, ContactAddress?)";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			returnMap.put("STATE", "1");
		} catch (Exception ex) {
			returnMap.put("STATE", "0");
			throw ex;
		}
		return returnMap;
	}
	
	/**
	 * 维修厂招聘需求
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert_Recruit.action")
	public Map<String, Object> insert_Recruit(String jsonData) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		cds = new DataSet(jsonData);
		try {
			String sql = "";
			sql = "INSERT INTO Recruit(RecruitInfo, PublisherId, PublishTime) "
					+ "VALUES(RecruitInfo?, PublisherId?, NOW())";
			Map	row = getRow(sql, null, 0);
			execSQL(o2o, sql, row);
			
			returnMap.put("STATE", "1");
		} catch (Exception ex) {
			returnMap.put("STATE", "0");
			throw ex;
		}
		return returnMap;
	}
	
	/**
	 * 查询商品需求信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select_Produce.action")
	public Map<String, Object> select_Produce() throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 查询维修厂名称, 商品名称, 商品价格 显示最新十条信息
			String sql = "";
			sql = "SELECT A.ZCXX02 NAME, '1' AS STATE "
				+ "FROM W_ZCGS A "
				+ "LEFT JOIN PRODUCEDEMAND B "
				+ "ON A.ZCXX01 = B.BUYERID "
				+ "WHERE B.DEMANDSTATUS = '0' "
				+ "AND B.INVALIDTIME > NOW() "
				+ "ORDER BY B.ID DESC LIMIT 1";
			Map pjMap = queryForMap(o2o, sql, null);
			// 查询发布外协信息
			sql = "SELECT A.ZCXX02 NAME, '2' AS STATE "
				+ "FROM W_ZCGS A, OUTSOURCING B "
				+ "WHERE A.ZCXX01 = B.PUBLISHERID "
				+ "ORDER BY B.ID DESC LIMIT 1";
			Map wxMap = queryForMap(o2o, sql, null);
			// 查询发布员工状态信息
			sql = "SELECT A.ZCXX02 NAME, '3' AS STATE "
				+ "FROM W_ZCGS A, RECRUIT B "
				+ "WHERE A.ZCXX01 = B.PUBLISHERID "
				+ "ORDER BY B.ID DESC LIMIT 1";
			Map ygMap = queryForMap(o2o, sql, null);
			// 放到list中
			List list = new ArrayList();
			if(pjMap != null){
				list.add(pjMap);
			}
			if(wxMap != null){
				list.add(wxMap);
			}
			if(ygMap != null){
				list.add(ygMap);
			}
			returnMap.put("produceList", list);
			returnMap.put("STATE", "1");
			
		} catch (Exception ex) {
			returnMap.put("STATE", "0");
			throw ex;
		}
		return returnMap;
	}
	
	/**
	 * 更新发布商品服务状态
	 */
	@RequestMapping("/update_ProduceStatus.action")
	public Map<String, Object> update_ProduceStatus() throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			String sql = "";
			sql = "UPDATE PRODUCEDEMAND "
				+ "SET DEMANDSTATUS = 2 "
				+ "WHERE INVALIDTIME < NOW()";
			Map	row = new HashMap();
			execSQL(o2o, sql, row);
			returnMap.put("STATE", "1");
		} catch (Exception e) {
			returnMap.put("STATE", "0");
			throw e;
		}
		
		return returnMap;
	}
}
 