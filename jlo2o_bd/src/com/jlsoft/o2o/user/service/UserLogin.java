package com.jlsoft.o2o.user.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.utils.ClientDataSet;

@Controller
@RequestMapping("/UserLogin")
public class UserLogin extends JLBill{
		private static final Logger logger = Logger.getLogger(UserLogin.class); 

		/**
		 * 登陆验证
		 * @param	String XmlData
		 * 	- int	XTCZY01	用户名/绑定手机号
		 * 	- int	XTCZY02	密码
		 * @return	Map
		 * 	- checkLogin	success:成功,failure:失败 
		 * @throws Exception 
		 * 
		 * @note 验证登陆,储存登陆信息
		 */
		@RequestMapping("/login.action")
		public Map login(String XmlData, HttpServletRequest request, HttpServletResponse response) throws Exception {
			cds = new DataSet(XmlData);
			Map login = new HashMap();
			Map check = checkUserName(XmlData);
			if(check.get("NUM").toString().equals("0")){
				login.put("state",1);//错误用户名
				return login;
			}
			Map userInfo = checkoutUserInfo(XmlData);
			if(userInfo.isEmpty()){
				login.put("state", 2);//错误密码
				return login;
			}
			login.put("userInfo",userInfo);
			login.put("state", 3);//登陆成功
			return login;
		}
		
		public Map checkUserName(String XmlData) throws Exception{
			cds = new DataSet(XmlData);
			String sql= 
				"SELECT COUNT(1) NUM\n" +
				"  FROM W_ZCXX A, W_XTCZY B\n" + 
				" WHERE B.GSID = A.ZCXX01\n" + 
				"   AND (B.PERSON_ID = '"+cds.getField("XTCZY01", 0)+
				"'   OR A.ZCXX09 = '"+cds.getField("XTCZY01", 0)+ 
				"'   OR (A.ZCXX06 = '"+cds.getField("XTCZY01", 0)+ 
				"'  AND A.ZCXX10 = 1))";
			Map map = queryForMap(o2o,sql);
			return map;
		}
		
		public Map checkoutUserInfo(String XmlData) throws Exception {
			cds = new DataSet(XmlData);
			String sql = "SELECT A.PERSON_ID XTCZY01, A.PASSWD XTCZY02,A.Role,B.ZCXX40 OPENID,B.ZCXX20, B.ZCXX01,B.ZCXX02,B.ZCXX06," +
					" CASE B.ZCXX16 WHEN 0 THEN '普通用户' WHEN 1 THEN '正式会员' END ZCXX16,B.ZCXX56," +
					" B.ERP01,B.ZCXX07,C.LX,Z.ZCGS01 \n" +
							"  FROM W_XTCZY A, W_ZCXX B,W_GSLX C,W_ZCGS Z \n" + 
							" WHERE A.GSID = B.ZCXX01 AND B.ZCXX01=C.GSID AND B.ZCXX01=Z.ZCXX01 AND (A.PERSON_ID = '" +cds.getField("XTCZY01", 0)+
							"'    OR B.ZCXX09 = '" + cds.getField("XTCZY01", 0)+
							"'    OR (B.ZCXX06 = '" + cds.getField("XTCZY01", 0)+
							"'    AND B.ZCXX10=1)) AND A.PASSWD='" +cds.getField("XTCZY02", 0)+
							"'";
			Map<String, String> userInfo = new HashMap<String, String>();
			List<Map<String, String>> userList = queryForList(o2o, sql);
			if(!userList.isEmpty() && userList.size()>1){
				for(int i=0;i<userList.size();i++){
					if(userList.get(i).get("LX").equals("42")){
						userInfo=userList.get(i);
					}
				}
			}else if(!userList.isEmpty() && userList.size()==1){
				userInfo=userList.get(0);
			}
			
			//logger.info(map1.get("XTCZY01"));
			
			return userInfo;
		}
		
		/**
		 * 登陆界面图片的查询
		 * FILE03	图片地址
		 * FILE04	图片类型（查询时用这个字段区分）
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/selectLoginIMG.action")
		public List<Map<String, Object>> selectLoginIMG(String XmlData) throws Exception {
			ClientDataSet cds = new ClientDataSet(XmlData);
			String sql=
				"SELECT B.FILE01 ID,\n" +
				"       B.FILE02 FILENAME,\n" + 
				"       B.FILE03 FILEPATH,\n" + 
				"       B.FILE04 FILETYPE,\n" + 
				"       B.FILE05 URL\n" + 
				"  FROM W_FILE B\n" + 
				" WHERE B.FILE04 LIKE '5%' order by FILE04";
			List picList=cds.queryForList(o2o, sql);
			System.out.println(picList);
			return picList;
		}
		
		/**
		 * 修改用户密码
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/updatePwd.action")
		public Map updatePwd(String XmlData) throws Exception {
			cds = new DataSet(XmlData);
			Map flag = new HashMap();
			String sql=
				"Update W_XTCZY A \n" +
				"       SET A.PASSWD='"+cds.getField("XTCZY02", 0)+"'"+
				" WHERE A.PERSON_ID='"+cds.getField("XTCZY01", 0)+"'";
			Map row = (Map)getRow(sql,null, 0);
			int i = execSQL(o2o, sql, row);
			flag.put("state",i);
			return flag;
		}
		/**
		 * 修改车主用户密码
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/updateCarPwd.action")
		public Map updateCarPwd(String XmlData) throws Exception {
			cds = new DataSet(XmlData);
			Map flag = new HashMap();
			String sql=
				"Update carowner A \n" +
				"       SET A.Password='"+cds.getField("XTCZY02", 0)+"'"+
				" WHERE A.AccountName='"+cds.getField("XTCZY01", 0)+"'";
			Map row = (Map)getRow(sql,null, 0);
			int i = execSQL(o2o, sql, row);
			flag.put("state",i);
			return flag;
		}
		/**
		 * 生成4位数字的随机短信验证码
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/selectCaryzm.action")
		public Map selectCaryzm() throws Exception {
			String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
					  "8", "9" };  
					     List list = Arrays.asList(beforeShuffle);  
					     Collections.shuffle(list);  
					     StringBuilder sb = new StringBuilder();  
					     for (int i = 0; i < list.size(); i++) {  
					         sb.append(list.get(i));  
					     }  
					     String afterShuffle = sb.toString(); 
					     System.out.println(afterShuffle);
					     String result = afterShuffle.substring(3, 7);  
					    System.out .print(result) ;
			Map flag = new HashMap();
			flag.put("YZM",result);
			return flag;
		}
		/**
		 * 根据手机号修改用户密码
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/updatePwdByPhone.action")
		public Map updatePwdByPhone(String XmlData) throws Exception {
			Map flag = new HashMap();
			try {
				cds = new DataSet(XmlData);
				String sql="update W_XTCZY set passwd='"+cds.getField("XTCZY02", 0)+"' where person_id =(select xtczy01 from w_zcxx where zcxx06='"+cds.getField("TEL", 0)+"')";
				Map row = (Map)getRow(sql,null, 0);
				int i = execSQL(o2o, sql, row);
				String query="select zcxx02 from w_zcxx where zcxx06='"+cds.getField("TEL", 0)+"'";
				Map userMap = queryForMap(o2o, query);
				flag.put("state",1);
				flag.put("userName",userMap.get("zcxx02"));
			} catch (Exception e) {
				flag.put("state",0);
				// TODO: handle exception
			}
			return flag;
		}
		/**
		 * 查询验证码
		 * @param XmlData
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/searchCaptcha.action")
		public Map searchCaptchaText(HttpServletRequest request, HttpServletResponse response) throws Exception {		
			Map flag = new HashMap();
			String captchaText=(String) request.getSession().getAttribute("captchaText");
			flag.put("captchaText",captchaText);
			return flag;
		}
}
