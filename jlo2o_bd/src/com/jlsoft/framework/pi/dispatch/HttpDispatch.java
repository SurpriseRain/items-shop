package com.jlsoft.framework.pi.dispatch;

import com.jlsoft.framework.pi.util.Constant;
import com.jlsoft.framework.pi.api.IDataProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Dispatch")
public class HttpDispatch {

    @RequestMapping("/process.do")
    public void process(String processor) throws Exception {
        try {
            IDataProcessor DataProcessor = createProcessor(processor);
            if (DataProcessor != null) {
                synchronized (DataProcessor) {
                    DataProcessor.process(null);
                }
            } else {
                throw new Exception("接口未定义！");
            }
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    private IDataProcessor createProcessor(String processor) throws Exception {
        if (processor == null) {
            throw new Exception("Null processor not permitted.");
        }
        String className = processor;
        return (IDataProcessor) Constant.objectPool.checkOut(className);
    }
}
