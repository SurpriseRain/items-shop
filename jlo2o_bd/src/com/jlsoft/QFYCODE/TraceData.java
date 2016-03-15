/**
 * TraceData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class TraceData  implements java.io.Serializable {
    private java.lang.String GTIN;

    private java.lang.String dataSource;

    private java.lang.String[] traceList;

    private com.jlsoft.QFYCODE.SupplyInfo supply;

    private com.jlsoft.QFYCODE.LogisticsInfo logistics;

    private com.jlsoft.QFYCODE.RetailInfo retail;

    private com.jlsoft.QFYCODE.InspectionInfo inspection;

    private java.math.BigDecimal version;  // attribute

    public TraceData() {
    }

    public TraceData(
           java.lang.String GTIN,
           java.lang.String dataSource,
           java.lang.String[] traceList,
           com.jlsoft.QFYCODE.SupplyInfo supply,
           com.jlsoft.QFYCODE.LogisticsInfo logistics,
           com.jlsoft.QFYCODE.RetailInfo retail,
           com.jlsoft.QFYCODE.InspectionInfo inspection,
           java.math.BigDecimal version) {
           this.GTIN = GTIN;
           this.dataSource = dataSource;
           this.traceList = traceList;
           this.supply = supply;
           this.logistics = logistics;
           this.retail = retail;
           this.inspection = inspection;
           this.version = version;
    }


    /**
     * Gets the GTIN value for this TraceData.
     * 
     * @return GTIN
     */
    public java.lang.String getGTIN() {
        return GTIN;
    }


    /**
     * Sets the GTIN value for this TraceData.
     * 
     * @param GTIN
     */
    public void setGTIN(java.lang.String GTIN) {
        this.GTIN = GTIN;
    }


    /**
     * Gets the dataSource value for this TraceData.
     * 
     * @return dataSource
     */
    public java.lang.String getDataSource() {
        return dataSource;
    }


    /**
     * Sets the dataSource value for this TraceData.
     * 
     * @param dataSource
     */
    public void setDataSource(java.lang.String dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Gets the traceList value for this TraceData.
     * 
     * @return traceList
     */
    public java.lang.String[] getTraceList() {
        return traceList;
    }


    /**
     * Sets the traceList value for this TraceData.
     * 
     * @param traceList
     */
    public void setTraceList(java.lang.String[] traceList) {
        this.traceList = traceList;
    }


    /**
     * Gets the supply value for this TraceData.
     * 
     * @return supply
     */
    public com.jlsoft.QFYCODE.SupplyInfo getSupply() {
        return supply;
    }


    /**
     * Sets the supply value for this TraceData.
     * 
     * @param supply
     */
    public void setSupply(com.jlsoft.QFYCODE.SupplyInfo supply) {
        this.supply = supply;
    }


    /**
     * Gets the logistics value for this TraceData.
     * 
     * @return logistics
     */
    public com.jlsoft.QFYCODE.LogisticsInfo getLogistics() {
        return logistics;
    }


    /**
     * Sets the logistics value for this TraceData.
     * 
     * @param logistics
     */
    public void setLogistics(com.jlsoft.QFYCODE.LogisticsInfo logistics) {
        this.logistics = logistics;
    }


    /**
     * Gets the retail value for this TraceData.
     * 
     * @return retail
     */
    public com.jlsoft.QFYCODE.RetailInfo getRetail() {
        return retail;
    }


    /**
     * Sets the retail value for this TraceData.
     * 
     * @param retail
     */
    public void setRetail(com.jlsoft.QFYCODE.RetailInfo retail) {
        this.retail = retail;
    }


    /**
     * Gets the inspection value for this TraceData.
     * 
     * @return inspection
     */
    public com.jlsoft.QFYCODE.InspectionInfo getInspection() {
        return inspection;
    }


    /**
     * Sets the inspection value for this TraceData.
     * 
     * @param inspection
     */
    public void setInspection(com.jlsoft.QFYCODE.InspectionInfo inspection) {
        this.inspection = inspection;
    }


    /**
     * Gets the version value for this TraceData.
     * 
     * @return version
     */
    public java.math.BigDecimal getVersion() {
        return version;
    }


    /**
     * Sets the version value for this TraceData.
     * 
     * @param version
     */
    public void setVersion(java.math.BigDecimal version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TraceData)) return false;
        TraceData other = (TraceData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.GTIN==null && other.getGTIN()==null) || 
             (this.GTIN!=null &&
              this.GTIN.equals(other.getGTIN()))) &&
            ((this.dataSource==null && other.getDataSource()==null) || 
             (this.dataSource!=null &&
              this.dataSource.equals(other.getDataSource()))) &&
            ((this.traceList==null && other.getTraceList()==null) || 
             (this.traceList!=null &&
              java.util.Arrays.equals(this.traceList, other.getTraceList()))) &&
            ((this.supply==null && other.getSupply()==null) || 
             (this.supply!=null &&
              this.supply.equals(other.getSupply()))) &&
            ((this.logistics==null && other.getLogistics()==null) || 
             (this.logistics!=null &&
              this.logistics.equals(other.getLogistics()))) &&
            ((this.retail==null && other.getRetail()==null) || 
             (this.retail!=null &&
              this.retail.equals(other.getRetail()))) &&
            ((this.inspection==null && other.getInspection()==null) || 
             (this.inspection!=null &&
              this.inspection.equals(other.getInspection()))) &&
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
        if (getGTIN() != null) {
            _hashCode += getGTIN().hashCode();
        }
        if (getDataSource() != null) {
            _hashCode += getDataSource().hashCode();
        }
        if (getTraceList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTraceList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTraceList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSupply() != null) {
            _hashCode += getSupply().hashCode();
        }
        if (getLogistics() != null) {
            _hashCode += getLogistics().hashCode();
        }
        if (getRetail() != null) {
            _hashCode += getRetail().hashCode();
        }
        if (getInspection() != null) {
            _hashCode += getInspection().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TraceData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "TraceData"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Version"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GTIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "GTIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataSource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "DataSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("traceList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "TraceList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.gs1cn.org", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supply");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Supply"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "SupplyInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logistics");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Logistics"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "LogisticsInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Retail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "RetailInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inspection");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Inspection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "InspectionInfo"));
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
