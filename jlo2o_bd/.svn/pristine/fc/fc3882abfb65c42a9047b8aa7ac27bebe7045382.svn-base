/**
 * InspectionInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class InspectionInfo  implements java.io.Serializable {
    private com.jlsoft.QFYCODE.TraceEvent[] traceEvent;

    private com.jlsoft.QFYCODE.InspectReport[] inspectReport;

    public InspectionInfo() {
    }

    public InspectionInfo(
           com.jlsoft.QFYCODE.TraceEvent[] traceEvent,
           com.jlsoft.QFYCODE.InspectReport[] inspectReport) {
           this.traceEvent = traceEvent;
           this.inspectReport = inspectReport;
    }


    /**
     * Gets the traceEvent value for this InspectionInfo.
     * 
     * @return traceEvent
     */
    public com.jlsoft.QFYCODE.TraceEvent[] getTraceEvent() {
        return traceEvent;
    }


    /**
     * Sets the traceEvent value for this InspectionInfo.
     * 
     * @param traceEvent
     */
    public void setTraceEvent(com.jlsoft.QFYCODE.TraceEvent[] traceEvent) {
        this.traceEvent = traceEvent;
    }


    /**
     * Gets the inspectReport value for this InspectionInfo.
     * 
     * @return inspectReport
     */
    public com.jlsoft.QFYCODE.InspectReport[] getInspectReport() {
        return inspectReport;
    }


    /**
     * Sets the inspectReport value for this InspectionInfo.
     * 
     * @param inspectReport
     */
    public void setInspectReport(com.jlsoft.QFYCODE.InspectReport[] inspectReport) {
        this.inspectReport = inspectReport;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InspectionInfo)) return false;
        InspectionInfo other = (InspectionInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.traceEvent==null && other.getTraceEvent()==null) || 
             (this.traceEvent!=null &&
              java.util.Arrays.equals(this.traceEvent, other.getTraceEvent()))) &&
            ((this.inspectReport==null && other.getInspectReport()==null) || 
             (this.inspectReport!=null &&
              java.util.Arrays.equals(this.inspectReport, other.getInspectReport())));
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
        if (getTraceEvent() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTraceEvent());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTraceEvent(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInspectReport() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInspectReport());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInspectReport(), i);
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
        new org.apache.axis.description.TypeDesc(InspectionInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "InspectionInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("traceEvent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "TraceEvent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "TraceEvent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.gs1cn.org", "TraceEvent"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inspectReport");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "InspectReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "InspectReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.gs1cn.org", "InspectReport"));
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
