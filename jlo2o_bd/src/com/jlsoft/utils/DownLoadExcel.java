package com.jlsoft.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class DownLoadExcel extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		// TODO Auto-generated method stub
		int pageTotal = Integer.parseInt(req.getParameter("pageTotal"));
		String fileName = req.getParameter("filename");
		String columnsArr = req.getParameter("columnsArr");

		if (pageTotal <= 0) {
			throw new ServletException("pageTotal not null");
		}
		if (null == fileName) {
			throw new ServletException("fileName not null");
		}
		if (null == columnsArr) {
			throw new ServletException("columnsArr not null");
		}

		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		resp.setHeader("Content-disposition",
				"attachment; filename=" + df.format(new java.util.Date())
						+ ".xls");// 设定输出文件头
		resp.setContentType("application/msexcel");
		List<String> colNameList = new ArrayList<String>();
		List<String> dataBaseCoList = new ArrayList<String>();
		String[] ss = columnsArr.split(";");
		for (int i = 0; i < ss.length; i++) {
			String colName = ss[i].split(":")[1];
			if (!"操作".equals(colName)) {
				dataBaseCoList.add(ss[i].split(":")[0]);
				colNameList.add(colName);
			}
		}

		HashMap<String, Integer> currentPageTotalMap = new HashMap<String, Integer>();
		WritableSheet sheet = null;

		int excelCurrPage = 1;
		String currentPageName = "page" + excelCurrPage;

		int maxSheetRow = Integer.parseInt(JlAppResources
				.getProperty("EXCEL_SHEET_ROW"));
		WritableWorkbook wb = Workbook.createWorkbook(resp.getOutputStream());
		HttpURLConnection connection = null;
		try {
			if (pageTotal == 1) {
				String data = req.getParameter("data");
				if ("".equals(data) || null == data) {
					throw new ServletException("data not null");
				}

				JSONArray jsonList = JSONArray.fromObject(data);
				for (int k = 0; k < jsonList.size(); k++) {
					JSONObject valueJSONObject = JSONObject.fromObject(jsonList
							.get(k));

					int count = 0;

					if (k == 0) {

						currentPageTotalMap.put(currentPageName, -1);
						sheet = wb.createSheet(currentPageName, excelCurrPage);
						count = sheet.getRows();
						for (int j = 0; j < colNameList.size(); j++) {

							Label label = new Label(j, count,
									colNameList.get(j));
							sheet.addCell(label);
						}
					}

					Integer currentPageNum = currentPageTotalMap
							.get(currentPageName);
					if (null == currentPageNum || "0".equals(currentPageName)) {
						currentPageNum = 0;
					}
					int newPageNum = currentPageNum + 1;

					if (newPageNum >= maxSheetRow) {
						excelCurrPage = excelCurrPage + 1;
						currentPageName = "page" + excelCurrPage;
						sheet = wb.createSheet(currentPageName, excelCurrPage);
					} else {
						currentPageTotalMap.put(currentPageName, newPageNum);
					}
					count = sheet.getRows();
					for (int j = 0; j < dataBaseCoList.size(); j++) {
						String value = valueJSONObject.get(
								dataBaseCoList.get(j)).toString();
						Label label = new Label(j, count, value);
						sheet.addCell(label);
					}
				}
			} else {
				for (int i = 0; i < pageTotal; i++) {
					String urlPath = PropertiesReader.getInstance()
							.getProperty("REMOTE_READ_PHP")
							+ fileName
							+ "-"
							+ (i + 1) + ".xml";

					URL url = new URL(urlPath);
					connection = (HttpURLConnection) url.openConnection();
					connection.connect();
					InputStream urlStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(urlStream, "UTF-8"));

					String sCurrentLine = "";
					StringBuffer temp = new StringBuffer();
					while ((sCurrentLine = reader.readLine()) != null) {
						temp.append(sCurrentLine);
					}

					List list = returnJsonHandle(temp.toString(),
							dataBaseCoList);

					int count = 0;

					if (i == 0) {

						currentPageTotalMap.put(currentPageName, -1);
						sheet = wb.createSheet(currentPageName, excelCurrPage);
						count = sheet.getRows();
						for (int j = 0; j < colNameList.size(); j++) {

							Label label = new Label(j, count,
									colNameList.get(j));
							sheet.addCell(label);
						}
					}
					for (int j = 0; j < list.size(); j++) {
						Integer currentPageNum = currentPageTotalMap
								.get(currentPageName);
						if (null == currentPageNum
								|| "0".equals(currentPageName)) {
							currentPageNum = 0;
						}
						int newPageNum = currentPageNum + 1;

						if (newPageNum >= maxSheetRow) {
							excelCurrPage = excelCurrPage + 1;
							currentPageName = "page" + excelCurrPage;
							sheet = wb.createSheet(currentPageName,
									excelCurrPage);
						} else {
							currentPageTotalMap
									.put(currentPageName, newPageNum);
						}

						count = sheet.getRows();
						Map resultListMap = (Map) list.get(j);
						for (int k = 0; k < dataBaseCoList.size(); k++) {
							String value = resultListMap.get(
									dataBaseCoList.get(k)).toString();
							Label label = new Label(k, count, value);
							sheet.addCell(label);
						}
					}

				}
			}

			wb.write();
			wb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("export parse exception");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public List returnJsonHandle(String returnJson, List dbCollist)
			throws Exception {
		// 取的根元素
		List<Map<String, String>> datalist = new ArrayList();
		JSONArray jsonList = JSONArray.fromObject(returnJson);
		for (int i = 0; i < jsonList.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonList.get(i));
			Map<String, String> dataMap = new HashMap<String, String>();
			for (int j = 0; j < dbCollist.size(); j++) {
				dataMap.put(dbCollist.get(j).toString(),
						jsonObject.getString(dbCollist.get(j).toString()));
			}
			datalist.add(dataMap);
		}
		return datalist;

	}

}
