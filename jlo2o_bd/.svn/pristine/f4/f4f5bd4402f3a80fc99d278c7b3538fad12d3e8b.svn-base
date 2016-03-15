package com.jlsoft.pi.jia.sync;

import com.jlsoft.framework.pi.connector.http.HttpClientWriteConnector;
import com.jlsoft.framework.pi.transport.SyncTransportor;
import com.jlsoft.utils.PropertiesReader;

public class JaiTransportor extends SyncTransportor {

    public JaiTransportor(String key) throws Exception {
        String url = PropertiesReader.getInstance().getProperty(key);
        if (url == null) {
            throw new Exception("Null connector Property not permitted.");
        }
        setConnector(new HttpClientWriteConnector(url));
    }
}
