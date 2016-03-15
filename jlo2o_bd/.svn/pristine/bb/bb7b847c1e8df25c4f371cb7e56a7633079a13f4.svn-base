package com.jlsoft.framework.pi.connector.http;

import com.jlsoft.framework.pi.api.IConnector;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpWriteConnector implements IConnector {

    private String path;
    private HttpURLConnection connection;
    final static int BUFFER_SIZE = 4096;
    private int timeout = 5 * 1000;
    private String enc = "UTF-8";
    private String method = "POST";

    public HttpWriteConnector(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setEnc(String enc) {
        this.enc = enc;
    }
        
    public void connect() throws Exception {
        URL url = new URL(path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
        //至少要设置的两个请求头
        //Content-Type: application/x-www-form-urlencoded  //内容类型
        //Content-Length: 38  //实体数据的长度
        //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //长度是实体的二进制长度
        //conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
    }

    public void disconnect() throws Exception {
        if (connection != null) {
            connection.disconnect();
        }
    }

    public Object process(Object data) throws Exception {
        Object result = null;
        try {
            write(data);
            result = read();
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    private void write(Object data) throws Exception {
        if (data == null) {
            throw new RuntimeException("Null data provided");
        }
        byte[] entitydata;
        if (data instanceof Map) {
            entitydata = wrap((Map) data);
        } else if (data instanceof String) {
            entitydata = URLEncoder.encode(((String) data), enc).getBytes();
        } else {
            throw new RuntimeException("Batch element Expected. Got - " + data.getClass());
        }

        connection.setRequestProperty("Content-Length", String.valueOf(entitydata.length)); //实体数据的长度
        OutputStream outStream = connection.getOutputStream();
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();
        if (connection.getResponseCode() != 200) {
            throw new Exception("连接异常！" + connection.getResponseMessage());
        }
    }

    private byte[] wrap(Map<String, String> data) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                sb.append(entry.getKey()).append('=').append(URLEncoder.encode(String.valueOf(entry.getValue()), enc)).append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString().getBytes();//得到实体的二进制数据
    }

    private Object read() throws Exception {
        DataInputStream in = null;
        try {
            in = new DataInputStream(connection.getInputStream());
            return new String(convert(in), enc);
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
        }
    }

    private byte[] convert(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            byte[] data = new byte[BUFFER_SIZE];
            int count = -1;
            while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
            data = null;
            return outStream.toByteArray();
        } finally {
            if (outStream != null) {
                outStream.close();
                outStream = null;
            }
        }
    }
}
