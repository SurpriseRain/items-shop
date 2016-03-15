package com.jlsoft.framework.pi.processor.http;

import com.jlsoft.framework.pi.api.IConnector;
import com.jlsoft.framework.pi.api.IDataProcessor;
import com.jlsoft.framework.pi.connector.http.HttpWriteConnector;

public class HttpSyncWriteProcessor implements IDataProcessor {

    private IConnector connector;

    public HttpSyncWriteProcessor(IConnector connector) {
        this.connector = connector;
    }

    public void setConnector(HttpWriteConnector connector) {
        this.connector = connector;
    }

    public Object process(Object data) throws Exception {
        if (connector == null) {
            throw new Exception("Null connector not permitted.");
        }
        try {
            connector.connect();
            return connector.process(data);
        } catch (Exception e) {
            throw e;
        } finally {
            connector.disconnect();
        }
    }
}
