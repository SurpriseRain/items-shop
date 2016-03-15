package com.jlsoft.o2o.interfacepackage.lucene;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
@RequestMapping("/IKAnalyzer")
public class IKAnalyzer extends JLBill {
	public IKAnalyzer() {
	}
	public IKAnalyzer(JdbcTemplate o2o){
		this.o2o = o2o;
	}
	/**
	 * @todo 分词
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/splitWord.action")
	public Map splitWord(String XmlData) throws Exception {
		Map resultMap = new HashMap();

		cds = new DataSet(XmlData);
		String wordString = cds.getField("word", 0);
		StringReader reader = new StringReader(wordString);
		IKSegmenter ik = new IKSegmenter(reader, true);// 当为true时，分词器进行最大词长切分
		Lexeme lexeme = null;
		
		ArrayList<String> al=new ArrayList<String>();
		while ((lexeme = ik.next()) != null)
		{
			al.add(lexeme.getLexemeText());
		}
		
		List pjflList=new ArrayList();
		
		List pjpplist=new ArrayList();
		//查询车系数据
		String queryCX="select count(1) from w_automobile where 1=1";
		//拼接车型车系
		queryCX=queryCX+" AND (";
				for(int i=0;i<al.size();i++)
				{
					if(i==0)
					{
						queryCX=queryCX+"mobile_modle like '%"+al.get(i)+"%'";
					}else
					{
						queryCX=queryCX+" or mobile_modle like '%"+al.get(i)+"%'";
					}
				}
				queryCX=queryCX+")";
		int cx = queryForInt(o2o, queryCX);
		//查询分类数据
				String queryFL="select count(1) from spfl where 1=1";
				//拼接车型车系
				queryFL=queryFL+" AND (";
						for(int i=0;i<al.size();i++)
						{
							if(i==0)
							{
								queryFL=queryFL+"spfl02 like '%"+al.get(i)+"%'";
							}else
							{
								queryFL=queryFL+" or spfl02 like '%"+al.get(i)+"%'";
							}
						}
						queryFL=queryFL+")";
				int fl = queryForInt(o2o, queryFL);
				//查询品牌数据
				String queryPP="select count(1) from ppb where 1=1";
				//拼接车型车系
				queryPP=queryPP+" AND (";
						for(int i=0;i<al.size();i++)
						{
							if(i==0)
							{
								queryPP=queryPP+"ppb02 like '%"+al.get(i)+"%'";
							}else
							{
								queryPP=queryPP+" or ppb02 like '%"+al.get(i)+"%'";
							}
						}
						queryPP=queryPP+")";
				int pp = queryForInt(o2o, queryPP);
				//查询商品名称
				String querySpxx="select count(1) from w_spxx where 1=1";
				//拼接车型车系
				querySpxx=querySpxx+" AND (";
						for(int i=0;i<al.size();i++)
						{
							if(i==0)
							{
								querySpxx=querySpxx+"spxx04 like '%"+al.get(i)+"%'";
							}else
							{
								querySpxx=querySpxx+" or spxx04 like '%"+al.get(i)+"%'";
							}
						}
						querySpxx=querySpxx+")";
				int sp = queryForInt(o2o, querySpxx);
				if(cx==0&&fl==0&&pp==0&&sp==0)
				{
					
				}else
				{
					//通过searchString，查询sql
					String  querySql="SELECT DISTINCT SUBSTR(spfl.spfl01, 1, 2) AS spfl01, w_spxx.ppb01 FROM w_spxx LEFT JOIN w_spcx ON w_spcx.spxx01 = w_spxx.spxx01 INNER JOIN w_automobile ON w_automobile.mobile_modle_id = w_spcx.mobile_modle_id INNER JOIN spfl ON spfl.spfl01 = w_spxx.spfl01 INNER JOIN ppb ON ppb.ppb01 = w_spxx.ppb01 WHERE 1 = 1";
					//拼接车型车系
					if(cx>0)
					{
						querySql=querySql+" AND (";
						for(int i=0;i<al.size();i++)
						{
							if(i==0)
							{
								querySql=querySql+"mobile_modle like '%"+al.get(i)+"%'";
							}else
							{
								querySql=querySql+" or mobile_modle like '%"+al.get(i)+"%'";
							}
						}
						querySql=querySql+")";
					}
					if(fl>0)
					{
						//拼接商品分类
						querySql=querySql+" AND (";
						for(int i=0;i<al.size();i++)
						{
							if(i==0)
							{
								querySql=querySql+"spfl.spfl02 like '%"+al.get(i)+"%'";
							}else
							{
								querySql=querySql+" or spfl.spfl02 like '%"+al.get(i)+"%'";
							}
						}
						querySql=querySql+")";
					}
					if(pp>0)
					{
						//拼接品牌
						querySql=querySql+" AND (";
						for(int i=0;i<al.size();i++)
						{
							if(i==0)
							{
								querySql=querySql+"w_spxx.ppb02 like '%"+al.get(i)+"%'";
							}else
							{
								querySql=querySql+" or w_spxx.ppb02 like '%"+al.get(i)+"%'";
							}
						}
						querySql=querySql+")";
					}
					List arrList=queryForList(o2o, querySql);
					for(int i=0;i<arrList.size();i++){
						Map map=(Map)arrList.get(0);
						pjflList.add(map.get("spfl01"));
						pjpplist.add(map.get("ppb01"));
					}
					resultMap.put("pjflList", pjflList);
					resultMap.put("pjpplist", pjpplist);
					resultMap.put("splitword", al);
					resultMap.put("cx", cx);
					resultMap.put("fl", fl);
					resultMap.put("pp", pp);
					resultMap.put("sp", sp);
				}
		return resultMap;
	}
/*	*//**
	 * @todo 查询配件分类和配件品牌
	 * @return
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchByFilter.action")
	public Map searchByFilter(String XmlData) throws Exception {
		Map resultMap = new HashMap();

		cds = new DataSet(XmlData);
		String wordString = cds.getField("word", 0);
		StringReader reader = new StringReader(wordString);
		IKSegmenter ik = new IKSegmenter(reader, true);// 当为true时，分词器进行最大词长切分
		Lexeme lexeme = null;
		ArrayList<String> al=new ArrayList<String>();
		StringBuffer searchString=new StringBuffer();
		while ((lexeme = ik.next()) != null) {
			if(searchString.length()==0){
				searchString.append("('"+lexeme.getLexemeText()+"'");
			}else{
				searchString.append(",'"+lexeme.getLexemeText()+"'");
			}
		}
		if(searchString.length()>0){
			searchString.append(")");
		}
		//通过searchString，查询sql
		String  querySql="";
		
		List arrList=queryForList(o2o, querySql);
		List pjflList=new ArrayList();
		List pjpplist=new ArrayList();
		for(int i=0;i<arrList.size();i++){
			Map map=(Map)arrList.get(0);
			pjflList.add(map.get("field1"));
			pjpplist.add(map.get("field2"));
		}
		resultMap.put("pjflList", pjflList);
		resultMap.put("pjpplist", pjpplist);
		return resultMap;
	}*/
}
