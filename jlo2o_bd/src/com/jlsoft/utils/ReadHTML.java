package com.jlsoft.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;

@Controller
@RequestMapping("/ReadHTML")
public class ReadHTML extends JLBill {

	@RequestMapping("/getHTMLBody.action")
	public Map getHTMLBody(String filePath) throws Exception {
		Map resultMap = new HashMap();
		try {
			if(!"".equals(filePath)){
				String body;
				Document doc = Jsoup.connect(filePath).get();
				body = doc.getElementById("content").html();
				if (body.equals("")) {
					resultMap.put("STATE", "0");
				}else{
					resultMap.put("STATE", "1");
					resultMap.put("SPJS", body);
				}
			}
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			e.printStackTrace();
			throw e;
		}
		return resultMap;
	}
	//商品店铺读取
	@RequestMapping("/getHTMLSpdpBody.action")
	public Map getHTMLSpdpBody(String ZCXX01) throws Exception {
		Map resultMap = new HashMap();
		String body;
		try {
			System.out.println("ZCXX01====" + ZCXX01);
			File fileName = new File(JlAppResources.getProperty("path_dptp") +ZCXX01 + File.separator+ ZCXX01 + "_dpjs.html");
			if(fileName.exists()){
				Document doc = Jsoup.parse(fileName, "UTF-8");
				body = doc.getElementById("content").html();
				resultMap.put("STATE", "1");
				resultMap.put("SPJS", body);
			}
		} catch (Exception e) {
			resultMap.put("STATE", "0");
			e.printStackTrace();
			throw e;
		}
		return resultMap;
	}
	private String getProperty(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) throws Exception {
		ReadHTML rh = new ReadHTML();
		Map htmlBody = rh.getHTMLBody("781");
		System.out.println(htmlBody);
	}

}
