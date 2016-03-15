package com.jlsoft.utils.config;

import java.util.ArrayList;
import java.util.List;

public class Config implements java.io.Serializable {

    private String id = null;
    private String target = null;
    private List fields = new ArrayList();
    private Config parent = null;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
        

    public List getFields() {
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public Config getParent() {
        return parent;
    }

    public void setParent(Config parent) {
        this.parent = parent;
    }
}
