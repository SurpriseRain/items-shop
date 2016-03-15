package com.jlsoft.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jlsoft.utils.JlAppResources;

public class GridPageDataServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	int pages = Integer.valueOf(request.getParameter("pages").toLowerCase()).intValue();
    	String fileName = request.getParameter("fileName");
    	//String urlPath = JlAppResources.getProperty("REMOTE_READ_PHP")+fileName+"-"+pages+".xml";
    	String urlPath = PropertiesReader.getInstance().getProperty("REMOTE_READ_PHP")+fileName+"-"+pages+".xml";
    	
    	URL url = new  URL(urlPath); 
    	HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
    	connection.connect(); 
    	InputStream urlStream = connection.getInputStream(); 
    	BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream,"UTF-8")); 
    	
    	String sCurrentLine = "";
    	StringBuffer temp = new StringBuffer();
    	while((sCurrentLine = reader.readLine()) != null){
    		temp.append(sCurrentLine);
    	}
    	
        PrintWriter pw = response.getWriter();
    	pw.print(temp.toString());
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

}