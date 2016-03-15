package com.jlsoft.framework.ruleengine.engine;

import com.jlsoft.framework.ruleengine.IEngine;
import com.jlsoft.framework.ruleengine.Result;
import com.jlsoft.framework.ruleengine.StrategyModel;
import com.jlsoft.framework.ruleengine.domain.Expression;
import com.jlsoft.framework.ruleengine.domain.Rule;
import com.ql.util.express.IExpressContext;
import java.util.Map;

public class RuleEngine implements IEngine<Rule, IExpressContext, Result> {

    private IEngine runner;

    public RuleEngine() {
        this.runner = new ExpressEngine();
    }

    public RuleEngine(IEngine runner) {
        this.runner = runner;
    }

    public void setRunner(IEngine runner) {
        this.runner = runner;
    }

    public Result execute(Rule rule, IExpressContext context) throws Exception {
        Result result = new Result();
        if (rule.getStrategy() == StrategyModel.and) {
            result = and(rule, context);
        } else if (rule.getStrategy() == StrategyModel.or) {
            result = or(rule, context);
        }

        System.out.println("规则执行结果：" + result.getSuccess().toString());
        printrule(rule, rule.getStrategy(), context);

        return result;
    }

    private Result and(Rule rule, IExpressContext context) throws Exception {
        Boolean issuccess = false;
        Integer lcm = -1;
        for (Expression expression : rule.getValues()) {
            Object result = runner.execute(expression.getExpres(), context);
            if (result.equals(false)) {
                issuccess = false;
                break;
            } else if (result.equals(true)) {
                issuccess = true;
                
                Map<String, Object> metadata = expression.getMetadata();
                if ((Integer.valueOf(metadata.get("ZSBJ").toString())) == 1) { 
                    Integer lcm01 = Float.valueOf(String.valueOf(context.get(metadata.get("ZDMC"))).toString()).intValue() / Float.valueOf(String.valueOf(metadata.get("VALUE"))).intValue();
                    if ((lcm > lcm01) || (lcm == -1)) {
                        lcm = lcm01;
                    }
                }
                
            }
        }
        return new Result(issuccess, lcm);
    }

    private Result or(Rule rule, IExpressContext context) throws Exception {
        Boolean issuccess = false;
        Integer lcm = -1;
        for (Expression expression : rule.getValues()) {
            Object result = runner.execute(expression.getExpres(), context);
            if (result.equals(true)) {
                issuccess = true;
                
                Map<String, Object> metadata = expression.getMetadata();
                if ((Integer.valueOf(metadata.get("ZSBJ").toString())) == 1) {
                    Integer lcm01 = Float.valueOf(String.valueOf(context.get(metadata.get("ZDMC"))).toString()).intValue() / Float.valueOf(String.valueOf(metadata.get("VALUE"))).intValue();
                    if ((lcm > lcm01) || (lcm == -1)) {
                        lcm = lcm01;
                    }
                }
                
                break;
            }
        }
        return new Result(issuccess, lcm);
    }

    private void printrule(Rule rule, StrategyModel strategy, IExpressContext context) {
        StringBuilder sb = new StringBuilder();
        String model = null;
        if (strategy == StrategyModel.and) {
            model = "并且";
        } else if (strategy == StrategyModel.or) {
            model = "或者";
        }

        for (Expression expression : rule.getValues()) {
            if (sb.length() == 0) {
                sb.append("规则：").append("(").append(expression.getExpres()).append(")");
            } else {
                sb.append(" ").append(model).append(" ").append("(").append(expression.getExpres()).append(")");
            }
        }
        System.out.println(sb.toString());
        System.out.println("上下文环境：" + context + "\n");
    }
}
