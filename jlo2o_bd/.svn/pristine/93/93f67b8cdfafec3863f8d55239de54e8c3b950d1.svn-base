/**
 * GetTraceDataByTraceCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class GetTraceDataByTraceCode  implements java.io.Serializable {
    private java.lang.String clientID;

    private java.lang.String tracecode;

    public GetTraceDataByTraceCode() {
    }

    public GetTraceDataByTraceCode(
           java.lang.String clientID,
           java.lang.String tracecode) {
           this.clientID = clientID;
           this.tracecode = tracecode;
    }


    /**
     * Gets the clientID value for this GetTraceDataByTraceCode.
     * 
     * @return clientID
     */
    public java.lang.String getClientID() {
        return clientID;
    }


    /**
     * Sets the clientID value for this GetTraceDataByTraceCode.
     * 
     * @param clientID
     */
    public void setClientID(java.lang.String clientID) {
        this.clientID = clientID;
    }


    /**
     * Gets the tracecode value for this GetTraceDataByTraceCode.
     * 
     * @return tracecode
     */
    public java.lang.String getTracecode() {
        return tracecode;
    }


    /**
     * Sets the tracecode value for this GetTraceDataByTraceCode.
     * 
     * @param tracecode
     */
    public void setTracecode(java.lang.String tracecode) {
        this.tracecode = tracecode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetTraceDataByTraceCode)) return false;
        GetTraceDataByTraceCode other = (GetTraceDataByTraceCode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientID==null && other.getClientID()==null) || 
             (this.clientID!=null &&
              this.clientID.equals(other.getClientID()))) &&
            ((this.tracecode==null && other.getTracecode()==null) || 
             (this.tracecode!=null &&
              this.tracecode.equals(other.getTracecode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getClientID() != null) {
            _hashCode += getClientID().hashCode();
        }
        if (getTracecode() != null) {
            _hashCode += getTracecode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetTraceDataByTraceCode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">getTraceDataByTraceCode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "clientID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tracecode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "tracecode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
