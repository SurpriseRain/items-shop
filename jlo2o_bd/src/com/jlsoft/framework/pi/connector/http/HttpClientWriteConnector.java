package com.jlsoft.framework.pi.connector.http;

import com.jlsoft.framework.pi.api.IConnector;
import com.jlsoft.framework.pi.convertor.inputStream.InputStreamToStringConvertor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientWriteConnector implements IConnector {

    protected String url = null;
    private String enc = "UTF-8";
    private Integer timeout = 60 * 1000;
    protected HttpClient client;
    private PostMethod postMethod;

    public HttpClientWriteConnector(String url) {
        this.url = url;
    }

    public void connect() throws Exception {
        client = new HttpClient();
        client.setTimeout(timeout);
        postMethod = new UTF8PostMethod(url);
    }

    public void disconnect() throws Exception {
        postMethod.releaseConnection();
    }

    public Object process(Object data) throws Exception {
        if (data == null) {
            throw new RuntimeException("Null data provided");
        }
        if (data instanceof Map) {
            NameValuePair[] nameValuePairs = MapToNameValuePair((Map) data);
            postMethod.setRequestBody(nameValuePairs);
        } else if (data instanceof String) {
            postMethod.setRequestBody((String) data);
        } else {
            throw new RuntimeException("Batch element Expected. Got - " + data.getClass());
        }

        Object result = null;
        try {
            /*
             * Execute the method and check the status
             */
            int statusCode = client.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("Method failed: " + postMethod.getStatusLine());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while write from HTTP POST! " + e.getMessage());
        }
        try {
            /*
             * Handle response
             */
            InputStream input = postMethod.getResponseBodyAsStream();
            result = new InputStreamToStringConvertor(enc).process(input);

        } catch (IOException e) {
            throw new RuntimeException("Error while reading response from HTTP POST! " + e.getMessage());
        } finally {
        }
        return result;
    }

    private NameValuePair[] MapToNameValuePair(Map data) throws Exception {
        NameValuePair[] result = new NameValuePair[data.size()];
        List<NameValuePair> values = new ArrayList();
        for (Object key : data.keySet()) {
            NameValuePair value = new NameValuePair();
            value.setName(String.valueOf(key));
            value.setValue(String.valueOf(data.get(key)));
            values.add(value);
        }
        return values.toArray(result);
    }

    //Inner class for UTF-8 support
    class UTF8PostMethod extends PostMethod {

        public UTF8PostMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            //return super.getRequestCharSet();
            return enc;
        }
    }
}
