/**
 * MeasurementValueType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class MeasurementValueType  implements java.io.Serializable {
    private java.math.BigDecimal value;

    private java.lang.String unitOfMeasure;  // attribute

    public MeasurementValueType() {
    }

    public MeasurementValueType(
           java.math.BigDecimal value,
           java.lang.String unitOfMeasure) {
           this.value = value;
           this.unitOfMeasure = unitOfMeasure;
    }


    /**
     * Gets the value value for this MeasurementValueType.
     * 
     * @return value
     */
    public java.math.BigDecimal getValue() {
        return value;
    }


    /**
     * Sets the value value for this MeasurementValueType.
     * 
     * @param value
     */
    public void setValue(java.math.BigDecimal value) {
        this.value = value;
    }


    /**
     * Gets the unitOfMeasure value for this MeasurementValueType.
     * 
     * @return unitOfMeasure
     */
    public java.lang.String getUnitOfMeasure() {
        return unitOfMeasure;
    }


    /**
     * Sets the unitOfMeasure value for this MeasurementValueType.
     * 
     * @param unitOfMeasure
     */
    public void setUnitOfMeasure(java.lang.String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MeasurementValueType)) return false;
        MeasurementValueType other = (MeasurementValueType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue()))) &&
            ((this.unitOfMeasure==null && other.getUnitOfMeasure()==null) || 
             (this.unitOfMeasure!=null &&
              this.unitOfMeasure.equals(other.getUnitOfMeasure())));
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
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        if (getUnitOfMeasure() != null) {
            _hashCode += getUnitOfMeasure().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MeasurementValueType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "MeasurementValueType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("unitOfMeasure");
        attrField.setXmlName(new javax.xml.namespace.QName("", "unitOfMeasure"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
