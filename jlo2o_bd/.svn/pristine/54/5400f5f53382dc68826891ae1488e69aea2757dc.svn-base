/**
 * ANCCServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public interface ANCCServiceSoap extends java.rmi.Remote {

    /**
     * 支持8位、12位、13位条码类型查询;根据条码查询基础属性数据和厂商信息,如果没有商品信息并且厂商识别代码没有过期时，表示这个商品没有在ＧＤＳＮ中注册，则只返回厂商信息，如果商品信息和厂商信息都为空，那么此条码是无效条码！
     */
    public com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult getProductDataByBarCode(java.lang.String clientID, java.lang.String barcode) throws java.rmi.RemoteException;

    /**
     * 对错误的条码数据或者查找不到的条码数据进行反馈
     */
    public java.lang.String correctDataByBarCode(java.lang.String clientID, com.jlsoft.QFYCODE.CorrectData correctData) throws java.rmi.RemoteException;

    /**
     * 根据追溯码查询食品追溯信息
     */
    public com.jlsoft.QFYCODE.TraceData getTraceDataByTraceCode(java.lang.String clientID, java.lang.String tracecode) throws java.rmi.RemoteException;
}
