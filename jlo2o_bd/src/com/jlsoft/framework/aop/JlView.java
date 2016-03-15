package com.jlsoft.framework.aop;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JlView extends AbstractUrlBasedView {
	private String responseContent;

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		response.setContentType(getContentType());
//		response.getWriter().write("{\"dataType\":\"xml\",\"data\":");
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
//		new ObjectMapper().writeValue(baos,model);//.get("result"));
//		response.getWriter().write(baos.toString());//this.responseContent);
//		response.getWriter().write("}");
//		response.getWriter().close();
		
		PrintWriter pw = response.getWriter();
		pw.print("{\"dataType\":\"xml\",\"data\":");
		pw.print(new ObjectMapper().writeValueAsString(model));
		pw.print("}");
		pw.flush();
		pw.close();
	}

}
