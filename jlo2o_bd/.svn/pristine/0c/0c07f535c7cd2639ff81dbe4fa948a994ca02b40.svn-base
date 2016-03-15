package com.jlsoft.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ClientDataSet {
	private List cdsDate = null; // 存放所有传递数据
	private StringBuffer temp = new StringBuffer(); // 存放处理SQL语句
	private StringBuffer sb = new StringBuffer(); // 存放列字段
	private StringBuffer field = new StringBuffer(); // 存放列值
	
	// 将传入String数据转换成list
	public ClientDataSet(String XmlData) {
		JSONArray jsonList = JSONArray.fromObject(XmlData);
		this.cdsDate = jsonList;
	}
	
	//默认空的构造方法
	public ClientDataSet(){
		
	}

	/**
	 *@todo 第一级主表调用方法
	 * @param tblname
	 * @param source
	 * @param i
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public int executeUpdate(JdbcTemplate jdbcTemplate,String tblname, String source, int i)
			throws Exception {
		int result = 0;
		Map map = null;
		if (cdsDate != null && cdsDate.size() > 0) {
			map = (Map) cdsDate.get(i);
			result = jdbcTemplate.update(formatSQL(map, source));
		}
		return result;
	}

	/**
	 * @todo 第二级明细表调用方法
	 * @param tblname
	 * @param source
	 * @param i
	 * @param j
	 * @return
	 * @throws Exception
	 * @throws DataAccessException
	 */
	public int executeUpdate(JdbcTemplate jdbcTemplate,String tblname, String source, int i, int j)
			throws Exception {
		int result = 0;
		Map map = null;
		List list = null;
		if (cdsDate != null && cdsDate.size() > 0) {
			map = (Map) cdsDate.get(i);
			list = (List) map.get(tblname);
			result = jdbcTemplate.update(formatSQL((Map) list.get(j), source));
		}
		return result;
	}

	/**
	 * @todo 查询返回整数
	 * @param source
	 * @return
	 */
	public int queryForInt(JdbcTemplate jdbcTemplate,String source) {
		int result = 0;
		result = jdbcTemplate.queryForInt(source);
		return result;
	}

	/**
	 * @todo 查询结果返回Map
	 * @param source
	 * @return
	 */
	public Map queryForMap(JdbcTemplate jdbcTemplate,String source) {
		try{
			Map map = jdbcTemplate.queryForMap(source);
			return map;
		}catch (EmptyResultDataAccessException e){
			 return null; 
		}
	}

	
	/**
	 * @todo 查询结果返回list
	 * @param source
	 * @return
	 */
	public List queryForList(JdbcTemplate jdbcTemplate,String source) {
		List list = jdbcTemplate.queryForList(source);
		return list;
	}

	
	/**
	 * @todo 获取第二级明细表有多少条数据
	 * @param tblname
	 * @param i
	 * @return
	 */
	public int getTableRows(String tblname, int i) {
		int result = 0;
		Map map = null;
		if (cdsDate != null && cdsDate.size() > 0) {
			map = (Map) cdsDate.get(i);
			result = ((List) map.get(tblname)).size();
		}
		return result;
	}

	/**
	 * @todo 获取第三级明细表有多少条数据
	 * @param tblname
	 * @param i
	 * @param j
	 * @return
	 */
	public int getTableRows(String tblname, int i, int j) {
		int result = 0;
		return result;
	}

	/**
	 * @todo 获取一级表字段值
	 * @param felname
	 * @param i
	 * @return
	 */
	public String getField(String felname, int i) {
		Map map = null;
		if (cdsDate != null && cdsDate.size() > 0) {
			map = (Map) cdsDate.get(i);
		}
		return (map == null) ? null : map.get(felname).toString();
	}

	/**
	 * @todo 根据第三级表名获取第二级表名
	 * @param tblname
	 * @return
	 */
	public String getParentTblname(String tblname) {
		String parentTabName = "";
		Map map = (Map) cdsDate.get(0);
		Iterator it = map.entrySet().iterator();

		// 存明细数据变量
		List itemList = null;
		Map itemMap = null;

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			if ((map.get(e.getKey()).getClass().toString())
					.equals("class java.util.ArrayList")) {
				itemList = (List) e.getValue();
				// 获取明细表中数据进行进行循环开始
				if (itemList.size() > 0 && itemList.get(0) != null) {
					itemMap = (Map) itemList.get(0);
					Iterator itMx = itemMap.entrySet().iterator();
					while (itMx.hasNext()) {
						Map.Entry eMX = (Map.Entry) itMx.next();
						if ((eMX.getKey().toString()).equals(tblname)) {
							parentTabName = e.getKey().toString();
							break;
						}
					}
				}
				// 获取明细表中数据进行进行循环结束
			}
		}
		return parentTabName;
	}

	/**
	 * @todo 公共组装SQL方法
	 * @param map
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public String formatSQL(Map map, String source) throws Exception {
		clearTemp();
		temp.append(source);
		int pos = -1;
		while (temp.indexOf("?") != -1) {
			clear();
			pos = temp.indexOf("?");
			pos--;
			while (temp.charAt(pos) != ',' && temp.charAt(pos) != '('
					&& temp.charAt(pos) != '=' && temp.charAt(pos) != '*') {
				sb.insert(0, temp.charAt(pos));
				pos--;
			}
			System.out.println(sb.toString());
			// 获取字段值
			field.append(map.get(sb.toString()).toString());
			String fieldKey = sb.toString();
			switch (checkType(map, fieldKey)) {
			// 当是字符串时
			case 0:
				if (field.length() == 0) {
					field.append("NULL");
				} else {
					field.insert(0, "'");
					field.append("'");
				}
				break;
			// 当是日期类型时
			case 1:
				if (field.length() == 0) {
					field.append("NULL");
				} else {
					field = new StringBuffer(JLTools.dateStrToDateTimeStr(map.get(
							fieldKey).toString()));
				}
				break;
			// 当是数字类型时
			case 2:
				if (field.length() == 0) {
					field.append("0");
				}
				break;
			// 当是小数时
			case 3:
				if (field.length() == 0) {
					field.append("0.0");
				}
				break;
			}

			temp.delete(pos + 1, pos + sb.length() + 2);
			temp.insert(pos + 1, field.toString());
		}
		return temp.toString();
	}

	// 获取字段类型：0字串；1日期；2正型；3小数
	public int checkType(Map map, String field) {
		int flag = 0;
		String fieldType = (map.get(field).getClass()).toString();
		// 日期类型
		if ((field.substring(field.length() - 2, field.length())).equals("RQ")) {
			flag = 1;
		} else if (fieldType.equals("class java.lang.String")) {
			flag = 0;
		} else if (fieldType.equals("class java.lang.Integer")) {
			flag = 2;
		} else if (fieldType.equals("class java.lang.Double")) {
			flag = 3;
		}
		return flag;
	}

	// 清除拼接SQL语句
	public void clearTemp() {
		if (temp.length() > 0) {
			temp.delete(0, temp.length());
		}
	}

	// 清除临时存放的字段和字段值
	public void clear() {
		if (sb.length() > 0) {
			sb.delete(0, sb.length());
		}
		if (field.length() > 0) {
			field.delete(0, field.length());
		}
	}

}