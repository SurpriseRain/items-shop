package com.jlsoft.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jlsoft.framework.JLBill;

import java.io.PrintWriter;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JlInterceptor extends JLBill implements HandlerInterceptor {

	@Autowired
	private JlPIManage jlPIManage;
	@Autowired
	private JLAuthorize jlAuthorize;

	private boolean useWorkflow = Boolean.valueOf(config
			.getProperty("PCRM_USEWORKFLOW"));
	private Map<String, Object> jlAuthorize_result;
	private Map<String, Object> jlAuthorize_Session;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		// 增加对非查询类XML数据的Schema校验(XSD)
		Validator.validateExternalSchema(request);

		// 请求处理之前被调用
		// System.out.println("Pre-handle");
		// System.out.println(jdbcTemplate.queryForList("select * from t1"));

		// DataSource dataSource = jdbcTemplate.getDataSource();
		// TransactionFactory transactionFactory = new JdbcTransactionFactory();
		// Environment environment = new Environment("development",
		// transactionFactory, dataSource);
		// Configuration configuration = new Configuration(environment);
		// configuration.addLoadedResource("a.xml");
		// XMLMapperBuilder xmlMapperBuilder = new

		// SELECT count(1) FROM W_WORKFLOW_CONTROL WHERE SENDER=#value# AND
		// YXBJ=1

		// 调用检测Session合法性接口函数
//		jlAuthorize_Session = jlAuthorize.checkSession(request);
//		if (jlAuthorize_Session.get("Flag").toString().equals("1")) {
//			System.out.println("Session 检测通过!");
//		} else {
//			System.out.println("Session 检测未通过!");
//			return false;
//		}

		// 调用公用方法测试
		/**if (useWorkflow) {
			return jlPIManage.checkWorkflow(request, response);
		} else {
			return true;
		}*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 当前请求进行处理之后，也就是Controller 方法调用之后，DispatcherServlet 进行视图返回渲染之前被调用
		Map map = modelAndView.getModel();

		// 穿透业务系统
		/**if (useWorkflow) {
			jlPIManage.sendPIResult(request, response, map);
		}*/

		String uri = request.getRequestURI();
		if ((uri.indexOf("/jlquery/") != -1)
				|| (uri.indexOf("/getJson/") != -1)
				|| (uri.indexOf("/fileserver/") != -1)) {
			if (uri.indexOf("/jlquery/") != -1) {
				query(response, map);
			} else if (uri.indexOf("/getJson/") != -1) {
				// 注意map对象封装的内容不要太多，否则容易导致JVM内存溢出错误
				printMapJsonString(response, map);
			} else if (uri.indexOf("/fileserver/") != -1) {
				if (map.containsKey("linkedCaseInsensitiveMapList")) {
					List list = (List) map.get("linkedCaseInsensitiveMapList");
					printListMapJsonString(response, list);
				} else {
					printMapJsonString(response, map);
				}
			}
			modelAndView.setViewName("JlNullView");
		} else {
			String viewName = modelAndView.getViewName();
			if (viewName.indexOf("\"forword\":") == -1) {
				modelAndView.setViewName("JlView");
			} else {
				modelAndView.setViewName("a");// get forword value from json
			}
			// modelAndView.addObject("result", map);
		}
		// System.out.println("Post-handle " + request.getParameter("client"));
	}

	private void printMapJsonString(HttpServletResponse response, Map map)
			throws Exception {
		PrintWriter pw = response.getWriter();
		try {
			pw.print(JSONObject.fromObject(map));
			pw.close();
		} catch (Exception ex) {
			pw.print("Exception: " + ex);
			pw.close();
		}
	}

	private void printListMapJsonString(HttpServletResponse response, List list)
			throws Exception {
		PrintWriter pw = response.getWriter();
		try {
			pw.print(JSONArray.fromObject(list));
			pw.close();
		} catch (Exception ex) {
			pw.print("Exception: " + ex);
			pw.close();
		}
	}

	private void query(HttpServletResponse response, Map map) throws Exception {
		PrintWriter pw = response.getWriter();
		try {
			JlResultHandler resultHandler = null;
			boolean isJson = false;
			if (("Json").equals((String) map.get("dataType"))) {
				isJson = true;
			}
			if (("Report").equals((String) map.get("QryType"))) {
				resultHandler = new ReportResultHandler(pw,
						(String) map.get("sqlid"), isJson);
			} else {
				resultHandler = new BillResultHandler(pw,
						(String) map.get("sqlid"), isJson);
			}
			SqlSession session = null;
			try {
				session = (SqlSession) map.get("session");
				session.select((String) map.get("sqlid"), map, resultHandler);
				resultHandler.Post();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		} catch (Exception ex) {
			pw.print("Exception: " + ex);
			pw.close();
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 在DispatcherServlet 渲染了对应的视图之后执行
		// System.out.println("After completion handle");
		PrintWriter pw = response.getWriter();
		pw.print("Exception: " + ex);
		pw.close();
	}

	public static boolean isQueryURI(HttpServletRequest request) {

		boolean result = false;
		String uri = request.getRequestURI();
		if (-1 != uri.indexOf("/jlquery/") || -1 != uri.indexOf("/getJson/")
				|| -1 != uri.indexOf("/fileserver/")) {
			result = true;
		}
		return result;
	}
}
