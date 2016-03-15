/**
 * Service_TMTLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class Service_TMTLocator extends org.apache.axis.client.Service implements com.jlsoft.QFYCODE.Service_TMT {

    public Service_TMTLocator() {
    }


    public Service_TMTLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Service_TMTLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Service_TMTSoap
    private java.lang.String Service_TMTSoap_address = "http://ws2.gds.org.cn/gdsservice/service_tmt.asmx";

    public java.lang.String getService_TMTSoapAddress() {
        return Service_TMTSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Service_TMTSoapWSDDServiceName = "Service_TMTSoap";

    public java.lang.String getService_TMTSoapWSDDServiceName() {
        return Service_TMTSoapWSDDServiceName;
    }

    public void setService_TMTSoapWSDDServiceName(java.lang.String name) {
        Service_TMTSoapWSDDServiceName = name;
    }

    public com.jlsoft.QFYCODE.Service_TMTSoap getService_TMTSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Service_TMTSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getService_TMTSoap(endpoint);
    }

    public com.jlsoft.QFYCODE.Service_TMTSoap getService_TMTSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.jlsoft.QFYCODE.Service_TMTSoapStub _stub = new com.jlsoft.QFYCODE.Service_TMTSoapStub(portAddress, this);
            _stub.setPortName(getService_TMTSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setService_TMTSoapEndpointAddress(java.lang.String address) {
        Service_TMTSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.jlsoft.QFYCODE.Service_TMTSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.jlsoft.QFYCODE.Service_TMTSoapStub _stub = new com.jlsoft.QFYCODE.Service_TMTSoapStub(new java.net.URL(Service_TMTSoap_address), this);
                _stub.setPortName(getService_TMTSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Service_TMTSoap".equals(inputPortName)) {
            return getService_TMTSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.gs1cn.org/", "Service_TMT");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.gs1cn.org/", "Service_TMTSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Service_TMTSoap".equals(portName)) {
            setService_TMTSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
