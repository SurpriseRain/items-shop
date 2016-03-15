/**
 * InspectReport.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class InspectReport  implements java.io.Serializable {
    private java.lang.String reportName;

    private java.lang.String[] imageURL;

    public InspectReport() {
    }

    public InspectReport(
           java.lang.String reportName,
           java.lang.String[] imageURL) {
           this.reportName = reportName;
           this.imageURL = imageURL;
    }


    /**
     * Gets the reportName value for this InspectReport.
     * 
     * @return reportName
     */
    public java.lang.String getReportName() {
        return reportName;
    }


    /**
     * Sets the reportName value for this InspectReport.
     * 
     * @param reportName
     */
    public void setReportName(java.lang.String reportName) {
        this.reportName = reportName;
    }


    /**
     * Gets the imageURL value for this InspectReport.
     * 
     * @return imageURL
     */
    public java.lang.String[] getImageURL() {
        return imageURL;
    }


    /**
     * Sets the imageURL value for this InspectReport.
     * 
     * @param imageURL
     */
    public void setImageURL(java.lang.String[] imageURL) {
        this.imageURL = imageURL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InspectReport)) return false;
        InspectReport other = (InspectReport) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportName==null && other.getReportName()==null) || 
             (this.reportName!=null &&
              this.reportName.equals(other.getReportName()))) &&
            ((this.imageURL==null && other.getImageURL()==null) || 
             (this.imageURL!=null &&
              java.util.Arrays.equals(this.imageURL, other.getImageURL())));
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
        if (getReportName() != null) {
            _hashCode += getReportName().hashCode();
        }
        if (getImageURL() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImageURL());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImageURL(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InspectReport.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "InspectReport"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "ReportName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "ImageURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.gs1cn.org", "string"));
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
