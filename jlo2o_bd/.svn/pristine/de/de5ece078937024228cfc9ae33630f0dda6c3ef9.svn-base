/**
 * GetListByCardResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class GetListByCardResponse  implements java.io.Serializable {
    private com.jlsoft.QFYCODE.GetListByCardResponseGetListByCardResult getListByCardResult;

    private int listcount;

    public GetListByCardResponse() {
    }

    public GetListByCardResponse(
           com.jlsoft.QFYCODE.GetListByCardResponseGetListByCardResult getListByCardResult,
           int listcount) {
           this.getListByCardResult = getListByCardResult;
           this.listcount = listcount;
    }


    /**
     * Gets the getListByCardResult value for this GetListByCardResponse.
     * 
     * @return getListByCardResult
     */
    public com.jlsoft.QFYCODE.GetListByCardResponseGetListByCardResult getGetListByCardResult() {
        return getListByCardResult;
    }


    /**
     * Sets the getListByCardResult value for this GetListByCardResponse.
     * 
     * @param getListByCardResult
     */
    public void setGetListByCardResult(com.jlsoft.QFYCODE.GetListByCardResponseGetListByCardResult getListByCardResult) {
        this.getListByCardResult = getListByCardResult;
    }


    /**
     * Gets the listcount value for this GetListByCardResponse.
     * 
     * @return listcount
     */
    public int getListcount() {
        return listcount;
    }


    /**
     * Sets the listcount value for this GetListByCardResponse.
     * 
     * @param listcount
     */
    public void setListcount(int listcount) {
        this.listcount = listcount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetListByCardResponse)) return false;
        GetListByCardResponse other = (GetListByCardResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getListByCardResult==null && other.getGetListByCardResult()==null) || 
             (this.getListByCardResult!=null &&
              this.getListByCardResult.equals(other.getGetListByCardResult()))) &&
            this.listcount == other.getListcount();
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
        if (getGetListByCardResult() != null) {
            _hashCode += getGetListByCardResult().hashCode();
        }
        _hashCode += getListcount();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetListByCardResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">GetListByCardResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getListByCardResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "GetListByCardResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">>GetListByCardResponse>GetListByCardResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listcount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "listcount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
