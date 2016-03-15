
package com.jlsoft.framework.ruleengine.engine;

import com.jlsoft.framework.ruleengine.IEngine;
import com.jlsoft.framework.ruleengine.Result;
import com.jlsoft.framework.ruleengine.domain.Group;
import com.ql.util.express.IExpressContext;
import java.util.List;

public class GroupEngine implements IEngine<Group, List<IExpressContext>, Result> {

    public Result execute(Group express, List<IExpressContext> context) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
