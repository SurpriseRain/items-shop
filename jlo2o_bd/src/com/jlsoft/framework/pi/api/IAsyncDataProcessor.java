package com.jlsoft.framework.pi.api;

import java.util.List;

public interface IAsyncDataProcessor extends IDataProcessor {

    List beforeProcess() throws Exception;

    void afterProcess(Object result) throws Exception;
}
