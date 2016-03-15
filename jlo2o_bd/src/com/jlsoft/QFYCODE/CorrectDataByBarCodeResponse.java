/**
 * CorrectDataByBarCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class CorrectDataByBarCodeResponse  implements java.io.Serializable {
    private java.lang.String correctDataByBarCodeResult;

    public CorrectDataByBarCodeResponse() {
    }

    public CorrectDataByBarCodeResponse(
           java.lang.String correctDataByBarCodeResult) {
           this.correctDataByBarCodeResult = correctDataByBarCodeResult;
    }


    /**
     * Gets the correctDataByBarCodeResult value for this CorrectDataByBarCodeResponse.
     * 
     * @return correctDataByBarCodeResult
     */
    public java.lang.String getCorrectDataByBarCodeResult() {
        return correctDataByBarCodeResult;
    }


    /**
     * Sets the correctDataByBarCodeResult value for this CorrectDataByBarCodeResponse.
     * 
     * @param correctDataByBarCodeResult
     */
    public void setCorrectDataByBarCodeResult(java.lang.String correctDataByBarCodeResult) {
        this.correctDataByBarCodeResult = correctDataByBarCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CorrectDataByBarCodeResponse)) return false;
        CorrectDataByBarCodeResponse other = (CorrectDataByBarCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.correctDataByBarCodeResult==null && other.getCorrectDataByBarCodeResult()==null) || 
             (this.correctDataByBarCodeResult!=null &&
              this.correctDataByBarCodeResult.equals(other.getCorrectDataByBarCodeResult())));
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
        if (getCorrectDataByBarCodeResult() != null) {
            _hashCode += getCorrectDataByBarCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CorrectDataByBarCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", ">CorrectDataByBarCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correctDataByBarCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "CorrectDataByBarCodeResult"));
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
