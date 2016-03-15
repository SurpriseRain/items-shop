/**
 * FirmDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class FirmDataType  implements java.io.Serializable {
    private java.lang.String firmName;

    private java.lang.String address;

    private com.jlsoft.QFYCODE.ANCCDocumentExtensionType extension;

    public FirmDataType() {
    }

    public FirmDataType(
           java.lang.String firmName,
           java.lang.String address,
           com.jlsoft.QFYCODE.ANCCDocumentExtensionType extension) {
           this.firmName = firmName;
           this.address = address;
           this.extension = extension;
    }


    /**
     * Gets the firmName value for this FirmDataType.
     * 
     * @return firmName
     */
    public java.lang.String getFirmName() {
        return firmName;
    }


    /**
     * Sets the firmName value for this FirmDataType.
     * 
     * @param firmName
     */
    public void setFirmName(java.lang.String firmName) {
        this.firmName = firmName;
    }


    /**
     * Gets the address value for this FirmDataType.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this FirmDataType.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the extension value for this FirmDataType.
     * 
     * @return extension
     */
    public com.jlsoft.QFYCODE.ANCCDocumentExtensionType getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this FirmDataType.
     * 
     * @param extension
     */
    public void setExtension(com.jlsoft.QFYCODE.ANCCDocumentExtensionType extension) {
        this.extension = extension;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FirmDataType)) return false;
        FirmDataType other = (FirmDataType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firmName==null && other.getFirmName()==null) || 
             (this.firmName!=null &&
              this.firmName.equals(other.getFirmName()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension())));
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
        if (getFirmName() != null) {
            _hashCode += getFirmName().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FirmDataType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "firmDataType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "firmName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "ANCCDocumentExtensionType"));
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
