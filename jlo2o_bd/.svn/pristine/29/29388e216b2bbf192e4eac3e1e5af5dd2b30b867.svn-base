package com.jlsoft.o2o.print;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JasperRunManager;


public class PubPrtDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final Logger log = Logger.getLogger(PubPrtDocument.class);
	protected String path = null;
	
	public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			print(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * @todo 通用打印入口
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void print(HttpServletRequest request, HttpServletResponse response) throws
            Exception {
    	path = getServletContext().getRealPath("WEB-INF/classes/com/jlsoft/o2o/print/") + File.separator;
    	String prtType = request.getParameter("prtType");
        Connection conn = getConnection();
        byte[] bytes = null;
        HashMap para = new HashMap();
        try {
        	//出库单打印
        	if(prtType.equals("ckd")){
        		para.put("ckdh", request.getParameter("ckdh").toString());
        		path = path+"/order/rptCKD.jasper";
        	}
        	//销售单打印
        	if(prtType.equals("xsd")){
        		para.put("xsdd", request.getParameter("xsdd").toString());
        		path = path+"/order/rptXSD.jasper";
        	}
        	//入库单打印
        	if(prtType.equals("rkd")){
        		para.put("RKDH", request.getParameter("rkdh").toString());
        		path = path+"/order/rptRKD.jasper";
        	}
            //PDF打印
        	bytes = JasperRunManager.runReportToPdf(path, para, conn);
            toPdf(bytes, response);
        } catch (Exception ex) {
            log.error("Error to print PubPrtDocument.", ex);
        } finally {
           conn.close();
           conn = null;
        }
    }
    
	protected void toPdf(byte[] bytes, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);
		response.setHeader("Content-Disposition", "inline; filename=\"A4.pdf\"");
		ServletOutputStream out = response.getOutputStream();
		out.write(bytes, 0, bytes.length);
		out.flush();
		out.close();
	}
	
	public Connection getConnection()throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/o2o");
		return (Connection) ds.getConnection();
	}
	
}