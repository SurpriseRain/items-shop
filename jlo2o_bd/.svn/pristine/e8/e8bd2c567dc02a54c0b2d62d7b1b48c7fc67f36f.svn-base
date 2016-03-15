package com.jlsoft.framework.aop;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class JlViewResolver extends AbstractCachingViewResolver implements
        Ordered {

    private int order = Integer.MIN_VALUE;
    // View
//    private String viewName;

//    public void setViewName(String viewName) {
//        this.viewName = viewName;
//    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        View view = null;
        if (viewName.equals("JlView")) {
            view = this.getApplicationContext().getBean(viewName, JlView.class);
        } else if (viewName.equals("JlNullView")) {
            view = this.getApplicationContext().getBean(viewName, JlNullView.class);
        }
        //view.setResponseContent(viewName);
        //view.setUrl("xxx");
        return view;
    }
}
