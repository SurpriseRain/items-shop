/**
 * GetNewsListResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class GetNewsListResponse  implements java.io.Serializable {
    private com.jlsoft.QFYCODE.GetNewsListResponseGetNewsListResult getNewsListResult;

    public GetNewsListResponse() {
    }

    public GetNewsListResponse(
           com.jlsoft.QFYCODE.GetNewsListResponseGetNewsListResult getNewsListResult) {
           this.getNewsListResult = getNewsListResult;
    }


    /**
     * Gets the getNewsListResult value for this GetNewsListResponse.
     * 
     * @return getNewsListResult
     */
    public com.jlsoft.QFYCODE.GetNewsListResponseGetNewsListResult getGetNewsListResult() {
        return getNewsListResult;
    }


    /**
     * Sets the getNewsListResult value for this GetNewsListResponse.
     * 
     * @param getNewsListResult
     */
    public void setGetNewsListResult(com.jlsoft.QFYCODE.GetNewsListResponseGetNewsListResult getNewsListResult) {
        this.getNewsListResult = getNewsListResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetNewsListResponse)) return false;
        GetNewsListResponse other = (GetNewsListResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getNewsListResult==null && other.getGetNewsListResult()==null) || 
             (this.getNewsListResult!=null &&
              this.getNewsListResult.equals(other.getGetNewsListResult())));
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
        if (getGetNewsListResult() != null) {
            _hashCode += getGetNewsListResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetNewsListResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">GetNewsListResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getNewsListResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "GetNewsListResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">>GetNewsListResponse>GetNewsListResult"));
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
