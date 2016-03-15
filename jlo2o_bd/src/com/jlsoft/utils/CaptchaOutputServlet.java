package com.jlsoft.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smx.captcha.Producer;
import org.smx.captcha.impl.FactoryRandomImpl;

@SuppressWarnings("serial")
public class CaptchaOutputServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ByteArrayOutputStream b = new ByteArrayOutputStream();

		Properties props = new Properties();
		props.put("format", "png");
		props.put("font", request.getParameter("font"));
		props.put("fontsize", request.getParameter("fontsize"));
		props.put("min-width", request.getParameter("min-width"));
		props.put("padding-x", request.getParameter("padding-x"));
		props.put("padding-y", request.getParameter("padding-y"));
		props.put("curve", "false");
		FactoryRandomImpl inst = null;
		try {
			inst = (FactoryRandomImpl) Producer
					.forName("org.smx.captcha.impl.FactoryRandomImpl");
			inst.setSize(6);
			inst.setUseDigits(true);
			Producer.render(b, inst, props);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		String text = inst.getLastWord();
		
		
		request.getSession().setAttribute("captchaText", text);
        
		byte image[] = b.toByteArray();
		response.setBufferSize(image.length);
		response.setContentType("image/png");
		response.setContentLength(image.length);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		OutputStream out = response.getOutputStream();
		out.write(image);
	}

}
