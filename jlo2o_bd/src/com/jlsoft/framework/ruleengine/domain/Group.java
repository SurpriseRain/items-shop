package com.jlsoft.framework.ruleengine.domain;

import com.jlsoft.framework.ruleengine.StrategyModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group<T> implements Serializable {

    List<T> values;
    StrategyModel strategy = StrategyModel.and;

    public Group() {
    }

    public Group(List<T> values) {
        this.values = values;
    }

    public StrategyModel getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyModel strategy) {
        this.strategy = strategy;
    }

    public void addValue(T value) {
        if (values == null) {
            values = new ArrayList<T>();
        }
        values.add(value);
    }

    public void cleanValue() {
        if (values != null) {
            values.clear();
        }
    }

    public List<T> getValues() {
        if (values == null) {
            values = new ArrayList<T>();
        }
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}