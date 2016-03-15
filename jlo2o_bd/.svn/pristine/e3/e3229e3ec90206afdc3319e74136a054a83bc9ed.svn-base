package com.jlsoft.o2o.hdgl.service; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/WDHD")
public class Oper_WDHD extends JLBill{
	//查询活动类型
	@RequestMapping("/selectActivityType.action")
	public Map selectActivityType(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select ActivityTypeId,ActivityTypeName from activitytype where Mark = 1";
			List hdlxList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("hdlxList", hdlxList);
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//查询产品状态
	@RequestMapping("/selectActivityProduct.action")
	public Map selectActivityProduct(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select case when State='0' then '申请状态' when State='1' then '审核'  when State = '2' then '终止' " +
						" when State='3' then '退出' else '' end  temp from activityproduct";
			List hdztList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("spList", hdztList);
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
		
	//查询产品种类
	@RequestMapping("/selectSPFL.action")
	public Map selectSPFL(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select spfl01,spfl02 from spfl where yxbj = '1'";
			List hdztList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("spList", hdztList);
			}
		} catch (Exception ex) {
			resultMap.put("STATE", "2");
			throw ex;
		}
		System.out.println("===="+resultMap);
		return resultMap;
	}
	//报名中心
	@RequestMapping("/select_BMZX.action")
	public Map select_BMZX(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select A.ActivityId,A.ActivityTypeId,B.ActivityTypeName,A.ActivityName,A.SPFL01,DATE_FORMAT(A.EnrollStartTime, '%Y-%m-%d %H:%i:%S') EnrollStartTime,DATE_FORMAT(A.ActivityStartTime, '%Y-%m-%d %H:%i:%S') ActivityStartTime,DATE_FORMAT(A.ActivityEndTime, '%Y-%m-%d %H:%i:%S') ActivityEndTime from activity A left join ActivityType B on B.ActivityTypeId = A.ActivityTypeId" +
					" where  now()<= A.ActivityStartTime or (now()>= A.ActivityStartTime and now()<=A.ActivityEndTime) and A.State = '1'order  by A.ActivityTypeId";
			List bmzxList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("bmzxList", bmzxList);
			}
		} catch (Exception e) {
			resultMap.put("STATE", "2");
			throw e;
		}
		return resultMap;
	}
	//所有商品分类
	@RequestMapping("/select_SPFL.action")
	public Map select_SPFL(String jsonData) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select spfl01,spfl02 from spfl";
			List spflList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("spflList", spflList);
			}
		} catch (Exception e) {
			resultMap.put("STATE", "2");
			throw e;
		}
		return resultMap;
	}
	//所有活动详情
	@RequestMapping("/select_ActivityDetail.action")
	public Map select_ActivityDetail(String ActivityId) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select ActivityId,ActivityName,SPFL01,DATE_FORMAT(EnrollStartTime, '%Y-%m-%d %H:%i:%S') EnrollStartTime,DATE_FORMAT(EnrollEndTime, '%Y-%m-%d %H:%i:%S') EnrollEndTime," +
						 "DATE_FORMAT(ActivityStartTime, '%Y-%m-%d %H:%i:%S') ActivityStartTime, DATE_FORMAT(ActivityEndTime, '%Y-%m-%d %H:%i:%S') ActivityEndTime from Activity where ActivityId='"+ActivityId+"'";
			List detailList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("detailList", detailList);
			}
		} catch (Exception e) {
			resultMap.put("STATE", "2");
			throw e;
		}
		return resultMap;
	}
	//满减规则
	@RequestMapping("/select_activityfulloffrule.action")
	public Map select_activityfulloffrule(String ActivityId) throws Exception {
		Map resultMap = new HashMap();
		try {
			String sql = "select * from activityfulloffrule where ActivityId='"+ActivityId+"'";
			List fullList = queryForList(o2o,sql);
			if(resultMap != null){
				resultMap.put("STATE", "1");
				resultMap.put("fullList", fullList);
			}
		} catch (Exception e) {
			resultMap.put("STATE", "2");
			throw e;
		}
		return resultMap;
	}
}
 