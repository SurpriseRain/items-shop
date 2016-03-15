package com.jlsoft.framework.sqlwriter;


public interface ISQLWriter {

    //单行执行
    int write(Object row) throws Exception;

    //批量执行
    int[] writeBatch(Object[] rows) throws Exception;
    
}
