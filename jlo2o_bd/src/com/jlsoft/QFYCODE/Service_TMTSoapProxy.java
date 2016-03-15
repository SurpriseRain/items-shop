package com.jlsoft.QFYCODE;

public class Service_TMTSoapProxy implements com.jlsoft.QFYCODE.Service_TMTSoap {
  private String _endpoint = null;
  private com.jlsoft.QFYCODE.Service_TMTSoap service_TMTSoap = null;
  
  public Service_TMTSoapProxy() {
    _initService_TMTSoapProxy();
  }
  
  public Service_TMTSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initService_TMTSoapProxy();
  }
  
  private void _initService_TMTSoapProxy() {
    try {
      service_TMTSoap = (new com.jlsoft.QFYCODE.Service_TMTLocator()).getService_TMTSoap();
      if (service_TMTSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service_TMTSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service_TMTSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service_TMTSoap != null)
      ((javax.xml.rpc.Stub)service_TMTSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.jlsoft.QFYCODE.Service_TMTSoap getService_TMTSoap() {
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap;
  }
  
  public java.lang.String uploadXMLByTMT(java.lang.String xml, java.lang.String act) throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.uploadXMLByTMT(xml, act);
  }
  
  public void ping() throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    service_TMTSoap.ping();
  }
  
  public java.lang.String uploadProductImage(java.lang.String gtin, java.lang.String BINData, java.lang.String fileName, java.lang.String fileDescription) throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.uploadProductImage(gtin, BINData, fileName, fileDescription);
  }
  
  public java.lang.String getItemTemplate(java.lang.String templateflag) throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.getItemTemplate(templateflag);
  }
  
  public com.jlsoft.QFYCODE.FirmForTMTResponseFirmForTMTResult firmForTMT(java.lang.String code) throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.firmForTMT(code);
  }
  
  public com.jlsoft.QFYCODE.FirmInfoByCardResponseFirmInfoByCardResult firmInfoByCard() throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.firmInfoByCard();
  }
  
  public com.jlsoft.QFYCODE.GetListByCardResponseGetListByCardResult getListByCard(java.lang.String gtin, java.util.Calendar create_time, java.util.Calendar last_time, int pageindex, int pagesize, javax.xml.rpc.holders.IntHolder listcount) throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.getListByCard(gtin, create_time, last_time, pageindex, pagesize, listcount);
  }
  
  public java.lang.String getVersion() throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.getVersion();
  }
  
  public com.jlsoft.QFYCODE.GetNewsListResponseGetNewsListResult getNewsList() throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    return service_TMTSoap.getNewsList();
  }
  
  public void testConnection() throws java.rmi.RemoteException{
    if (service_TMTSoap == null)
      _initService_TMTSoapProxy();
    service_TMTSoap.testConnection();
  }
  
  
}