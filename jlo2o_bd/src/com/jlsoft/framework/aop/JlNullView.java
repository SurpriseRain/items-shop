package com.jlsoft.framework.aop;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class JlNullView extends AbstractUrlBasedView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
    }
}
