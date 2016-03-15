package com.jlsoft.utils.config;

import com.jlsoft.framework.pi.api.IConvertor;

public class Field {

    private String attrname = null;
    private String fieldtype = null;
    private String width = null;
    private String decimals = null;
    private String target = null;
    private IConvertor convertor = null;



    public String getAttrname() {
        return attrname;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public String getWidth() {
        return width;
    }

    public String getDecimals() {
        return decimals;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public IConvertor getConvertor() {
        return convertor;
    }

    public void setConvertor(IConvertor convertor) {
        this.convertor = convertor;
    }
   
}