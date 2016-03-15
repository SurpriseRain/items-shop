package com.jlsoft.framework.ruleengine.engine;

import com.jlsoft.framework.ruleengine.IEngine;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

public class ExpressEngine implements IEngine<String, IExpressContext, Object> {

    private Boolean isCache = true;
    private ExpressRunner runner;

    public ExpressEngine() {
        this.runner = new ExpressRunner();
    }

    public ExpressEngine(ExpressRunner runner) {
        this.runner = runner;
    }

    public ExpressRunner getRunner() {
        return runner;
    }

    public void setRunner(ExpressRunner runner) {
        this.runner = runner;
    }

    public Object execute(String expres, IExpressContext context) throws Exception {
        return runner.execute(expres, context, null, isCache, false);
    }
}
