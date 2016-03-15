/**
 * ANCCServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class ANCCServiceLocator extends org.apache.axis.client.Service implements com.jlsoft.QFYCODE.ANCCService {

    public ANCCServiceLocator() {
    }


    public ANCCServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ANCCServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ANCCServiceSoap
    private java.lang.String ANCCServiceSoap_address = "http://ws2.gs1cn.org/anccservice.asmx";

    public java.lang.String getANCCServiceSoapAddress() {
        return ANCCServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ANCCServiceSoapWSDDServiceName = "ANCCServiceSoap";

    public java.lang.String getANCCServiceSoapWSDDServiceName() {
        return ANCCServiceSoapWSDDServiceName;
    }

    public void setANCCServiceSoapWSDDServiceName(java.lang.String name) {
        ANCCServiceSoapWSDDServiceName = name;
    }

    public com.jlsoft.QFYCODE.ANCCServiceSoap getANCCServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ANCCServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getANCCServiceSoap(endpoint);
    }

    public com.jlsoft.QFYCODE.ANCCServiceSoap getANCCServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.jlsoft.QFYCODE.ANCCServiceSoapStub _stub = new com.jlsoft.QFYCODE.ANCCServiceSoapStub(portAddress, this);
            _stub.setPortName(getANCCServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setANCCServiceSoapEndpointAddress(java.lang.String address) {
        ANCCServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.jlsoft.QFYCODE.ANCCServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.jlsoft.QFYCODE.ANCCServiceSoapStub _stub = new com.jlsoft.QFYCODE.ANCCServiceSoapStub(new java.net.URL(ANCCServiceSoap_address), this);
                _stub.setPortName(getANCCServiceSoapWSDDServiceName());
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
        if ("ANCCServiceSoap".equals(inputPortName)) {
            return getANCCServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.gs1cn.org/", "ANCCService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.gs1cn.org/", "ANCCServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ANCCServiceSoap".equals(portName)) {
            setANCCServiceSoapEndpointAddress(address);
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
