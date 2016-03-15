/**
 * ProductData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class ProductData  implements java.io.Serializable {
    private com.jlsoft.QFYCODE.FirmDataType firmData;

    private com.jlsoft.QFYCODE.ItemDataType itemData;

    private com.jlsoft.QFYCODE.ANCCDocumentExtensionType extension;

    private java.math.BigDecimal version;  // attribute

    public ProductData() {
    }

    public ProductData(
           com.jlsoft.QFYCODE.FirmDataType firmData,
           com.jlsoft.QFYCODE.ItemDataType itemData,
           com.jlsoft.QFYCODE.ANCCDocumentExtensionType extension,
           java.math.BigDecimal version) {
           this.firmData = firmData;
           this.itemData = itemData;
           this.extension = extension;
           this.version = version;
    }


    /**
     * Gets the firmData value for this ProductData.
     * 
     * @return firmData
     */
    public com.jlsoft.QFYCODE.FirmDataType getFirmData() {
        return firmData;
    }


    /**
     * Sets the firmData value for this ProductData.
     * 
     * @param firmData
     */
    public void setFirmData(com.jlsoft.QFYCODE.FirmDataType firmData) {
        this.firmData = firmData;
    }


    /**
     * Gets the itemData value for this ProductData.
     * 
     * @return itemData
     */
    public com.jlsoft.QFYCODE.ItemDataType getItemData() {
        return itemData;
    }


    /**
     * Sets the itemData value for this ProductData.
     * 
     * @param itemData
     */
    public void setItemData(com.jlsoft.QFYCODE.ItemDataType itemData) {
        this.itemData = itemData;
    }


    /**
     * Gets the extension value for this ProductData.
     * 
     * @return extension
     */
    public com.jlsoft.QFYCODE.ANCCDocumentExtensionType getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this ProductData.
     * 
     * @param extension
     */
    public void setExtension(com.jlsoft.QFYCODE.ANCCDocumentExtensionType extension) {
        this.extension = extension;
    }


    /**
     * Gets the version value for this ProductData.
     * 
     * @return version
     */
    public java.math.BigDecimal getVersion() {
        return version;
    }


    /**
     * Sets the version value for this ProductData.
     * 
     * @param version
     */
    public void setVersion(java.math.BigDecimal version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductData)) return false;
        ProductData other = (ProductData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firmData==null && other.getFirmData()==null) || 
             (this.firmData!=null &&
              this.firmData.equals(other.getFirmData()))) &&
            ((this.itemData==null && other.getItemData()==null) || 
             (this.itemData!=null &&
              this.itemData.equals(other.getItemData()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              this.extension.equals(other.getExtension()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getFirmData() != null) {
            _hashCode += getFirmData().hashCode();
        }
        if (getItemData() != null) {
            _hashCode += getItemData().hashCode();
        }
        if (getExtension() != null) {
            _hashCode += getExtension().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", ">productData"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "version"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firmData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "firmData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "firmDataType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "itemData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "itemDataType"));
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
