/**
 * FirmInfoByCardResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class FirmInfoByCardResponse  implements java.io.Serializable {
    private com.jlsoft.QFYCODE.FirmInfoByCardResponseFirmInfoByCardResult firmInfoByCardResult;

    public FirmInfoByCardResponse() {
    }

    public FirmInfoByCardResponse(
           com.jlsoft.QFYCODE.FirmInfoByCardResponseFirmInfoByCardResult firmInfoByCardResult) {
           this.firmInfoByCardResult = firmInfoByCardResult;
    }


    /**
     * Gets the firmInfoByCardResult value for this FirmInfoByCardResponse.
     * 
     * @return firmInfoByCardResult
     */
    public com.jlsoft.QFYCODE.FirmInfoByCardResponseFirmInfoByCardResult getFirmInfoByCardResult() {
        return firmInfoByCardResult;
    }


    /**
     * Sets the firmInfoByCardResult value for this FirmInfoByCardResponse.
     * 
     * @param firmInfoByCardResult
     */
    public void setFirmInfoByCardResult(com.jlsoft.QFYCODE.FirmInfoByCardResponseFirmInfoByCardResult firmInfoByCardResult) {
        this.firmInfoByCardResult = firmInfoByCardResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FirmInfoByCardResponse)) return false;
        FirmInfoByCardResponse other = (FirmInfoByCardResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firmInfoByCardResult==null && other.getFirmInfoByCardResult()==null) || 
             (this.firmInfoByCardResult!=null &&
              this.firmInfoByCardResult.equals(other.getFirmInfoByCardResult())));
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
        if (getFirmInfoByCardResult() != null) {
            _hashCode += getFirmInfoByCardResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FirmInfoByCardResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">FirmInfoByCardResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmInfoByCardResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "FirmInfoByCardResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">>FirmInfoByCardResponse>FirmInfoByCardResult"));
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
