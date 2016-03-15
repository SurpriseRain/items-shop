package com.jlsoft.framework.pi.processor.http;

import com.jlsoft.framework.pi.api.IAsyncDataProcessor;
import com.jlsoft.framework.pi.api.IConnector;
import com.jlsoft.framework.pi.api.IDataProcessor;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractHttpAsyncWriteProcessor implements IAsyncDataProcessor {

    private IConnector connector;

    public AbstractHttpAsyncWriteProcessor() {
    }

    public AbstractHttpAsyncWriteProcessor(IConnector connector) {
        this.connector = connector;
    }

    public void setConnector(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public Object process(Object data) throws Exception {
        if (connector == null) {
            throw new Exception("Null connector not permitted.");
        }
        try {
            connector.connect();
            List rows = beforeProcess();
            for (Iterator<Object> it = rows.iterator(); it.hasNext();) {
                Object row = it.next();
                Object result = connector.process(row);
                afterProcess(result);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            connector.disconnect();
        }
        return null;
    }
}
