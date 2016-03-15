/**
 * CorrectData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class CorrectData  implements java.io.Serializable {
    private java.lang.String source;

    private java.lang.String barcode;

    private java.lang.String itemName;

    private java.lang.String brand;

    private java.lang.String specification;

    private java.lang.String location;

    private java.lang.String comment;

    public CorrectData() {
    }

    public CorrectData(
           java.lang.String source,
           java.lang.String barcode,
           java.lang.String itemName,
           java.lang.String brand,
           java.lang.String specification,
           java.lang.String location,
           java.lang.String comment) {
           this.source = source;
           this.barcode = barcode;
           this.itemName = itemName;
           this.brand = brand;
           this.specification = specification;
           this.location = location;
           this.comment = comment;
    }


    /**
     * Gets the source value for this CorrectData.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this CorrectData.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the barcode value for this CorrectData.
     * 
     * @return barcode
     */
    public java.lang.String getBarcode() {
        return barcode;
    }


    /**
     * Sets the barcode value for this CorrectData.
     * 
     * @param barcode
     */
    public void setBarcode(java.lang.String barcode) {
        this.barcode = barcode;
    }


    /**
     * Gets the itemName value for this CorrectData.
     * 
     * @return itemName
     */
    public java.lang.String getItemName() {
        return itemName;
    }


    /**
     * Sets the itemName value for this CorrectData.
     * 
     * @param itemName
     */
    public void setItemName(java.lang.String itemName) {
        this.itemName = itemName;
    }


    /**
     * Gets the brand value for this CorrectData.
     * 
     * @return brand
     */
    public java.lang.String getBrand() {
        return brand;
    }


    /**
     * Sets the brand value for this CorrectData.
     * 
     * @param brand
     */
    public void setBrand(java.lang.String brand) {
        this.brand = brand;
    }


    /**
     * Gets the specification value for this CorrectData.
     * 
     * @return specification
     */
    public java.lang.String getSpecification() {
        return specification;
    }


    /**
     * Sets the specification value for this CorrectData.
     * 
     * @param specification
     */
    public void setSpecification(java.lang.String specification) {
        this.specification = specification;
    }


    /**
     * Gets the location value for this CorrectData.
     * 
     * @return location
     */
    public java.lang.String getLocation() {
        return location;
    }


    /**
     * Sets the location value for this CorrectData.
     * 
     * @param location
     */
    public void setLocation(java.lang.String location) {
        this.location = location;
    }


    /**
     * Gets the comment value for this CorrectData.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this CorrectData.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CorrectData)) return false;
        CorrectData other = (CorrectData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.barcode==null && other.getBarcode()==null) || 
             (this.barcode!=null &&
              this.barcode.equals(other.getBarcode()))) &&
            ((this.itemName==null && other.getItemName()==null) || 
             (this.itemName!=null &&
              this.itemName.equals(other.getItemName()))) &&
            ((this.brand==null && other.getBrand()==null) || 
             (this.brand!=null &&
              this.brand.equals(other.getBrand()))) &&
            ((this.specification==null && other.getSpecification()==null) || 
             (this.specification!=null &&
              this.specification.equals(other.getSpecification()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment())));
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
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getBarcode() != null) {
            _hashCode += getBarcode().hashCode();
        }
        if (getItemName() != null) {
            _hashCode += getItemName().hashCode();
        }
        if (getBrand() != null) {
            _hashCode += getBrand().hashCode();
        }
        if (getSpecification() != null) {
            _hashCode += getSpecification().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CorrectData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", ">CorrectData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "barcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "itemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "brand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "specification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "comment"));
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
