package com.jlsoft.framework.pi.api;

public interface IConnector extends IDataProcessor {

    void connect()  throws Exception;

    void disconnect()  throws Exception;
}
