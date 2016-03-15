package com.jlsoft.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class ExceptionHandler extends AbstractHandlerExceptionResolver {

	private static Log logger = LogFactory.getLog(ExceptionHandler.class);

	public ExceptionHandler() {
		this.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String requestedMethod =request.getHeader("x-requested-with");
		ModelAndView mv = new ModelAndView("error/dataAccessFailure");

		if("XMLHttpRequest".equals(requestedMethod)){
			return null;
		}else{
			logger.error(ex);
			mv.setViewName("error");
		}
		return mv;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		return null;
	}

}
