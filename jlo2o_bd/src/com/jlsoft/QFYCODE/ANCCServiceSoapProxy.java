package com.jlsoft.QFYCODE;

public class ANCCServiceSoapProxy implements com.jlsoft.QFYCODE.ANCCServiceSoap {
  private String _endpoint = null;
  private com.jlsoft.QFYCODE.ANCCServiceSoap aNCCServiceSoap = null;
  
  public ANCCServiceSoapProxy() {
    _initANCCServiceSoapProxy();
  }
  
  public ANCCServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initANCCServiceSoapProxy();
  }
  
  private void _initANCCServiceSoapProxy() {
    try {
      aNCCServiceSoap = (new com.jlsoft.QFYCODE.ANCCServiceLocator()).getANCCServiceSoap();
      if (aNCCServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)aNCCServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)aNCCServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (aNCCServiceSoap != null)
      ((javax.xml.rpc.Stub)aNCCServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.jlsoft.QFYCODE.ANCCServiceSoap getANCCServiceSoap() {
    if (aNCCServiceSoap == null)
      _initANCCServiceSoapProxy();
    return aNCCServiceSoap;
  }
  
  public com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult getProductDataByBarCode(java.lang.String clientID, java.lang.String barcode) throws java.rmi.RemoteException{
    if (aNCCServiceSoap == null)
      _initANCCServiceSoapProxy();
    return aNCCServiceSoap.getProductDataByBarCode(clientID, barcode);
  }
  
  public java.lang.String correctDataByBarCode(java.lang.String clientID, com.jlsoft.QFYCODE.CorrectData correctData) throws java.rmi.RemoteException{
    if (aNCCServiceSoap == null)
      _initANCCServiceSoapProxy();
    return aNCCServiceSoap.correctDataByBarCode(clientID, correctData);
  }
  
  public com.jlsoft.QFYCODE.TraceData getTraceDataByTraceCode(java.lang.String clientID, java.lang.String tracecode) throws java.rmi.RemoteException{
    if (aNCCServiceSoap == null)
      _initANCCServiceSoapProxy();
    return aNCCServiceSoap.getTraceDataByTraceCode(clientID, tracecode);
  }
  
  
}