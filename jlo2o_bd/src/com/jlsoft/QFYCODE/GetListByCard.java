/**
 * GetListByCard.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class GetListByCard  implements java.io.Serializable {
    private java.lang.String gtin;

    private java.util.Calendar create_time;

    private java.util.Calendar last_time;

    private int pageindex;

    private int pagesize;

    private int listcount;

    public GetListByCard() {
    }

    public GetListByCard(
           java.lang.String gtin,
           java.util.Calendar create_time,
           java.util.Calendar last_time,
           int pageindex,
           int pagesize,
           int listcount) {
           this.gtin = gtin;
           this.create_time = create_time;
           this.last_time = last_time;
           this.pageindex = pageindex;
           this.pagesize = pagesize;
           this.listcount = listcount;
    }


    /**
     * Gets the gtin value for this GetListByCard.
     * 
     * @return gtin
     */
    public java.lang.String getGtin() {
        return gtin;
    }


    /**
     * Sets the gtin value for this GetListByCard.
     * 
     * @param gtin
     */
    public void setGtin(java.lang.String gtin) {
        this.gtin = gtin;
    }


    /**
     * Gets the create_time value for this GetListByCard.
     * 
     * @return create_time
     */
    public java.util.Calendar getCreate_time() {
        return create_time;
    }


    /**
     * Sets the create_time value for this GetListByCard.
     * 
     * @param create_time
     */
    public void setCreate_time(java.util.Calendar create_time) {
        this.create_time = create_time;
    }


    /**
     * Gets the last_time value for this GetListByCard.
     * 
     * @return last_time
     */
    public java.util.Calendar getLast_time() {
        return last_time;
    }


    /**
     * Sets the last_time value for this GetListByCard.
     * 
     * @param last_time
     */
    public void setLast_time(java.util.Calendar last_time) {
        this.last_time = last_time;
    }


    /**
     * Gets the pageindex value for this GetListByCard.
     * 
     * @return pageindex
     */
    public int getPageindex() {
        return pageindex;
    }


    /**
     * Sets the pageindex value for this GetListByCard.
     * 
     * @param pageindex
     */
    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }


    /**
     * Gets the pagesize value for this GetListByCard.
     * 
     * @return pagesize
     */
    public int getPagesize() {
        return pagesize;
    }


    /**
     * Sets the pagesize value for this GetListByCard.
     * 
     * @param pagesize
     */
    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }


    /**
     * Gets the listcount value for this GetListByCard.
     * 
     * @return listcount
     */
    public int getListcount() {
        return listcount;
    }


    /**
     * Sets the listcount value for this GetListByCard.
     * 
     * @param listcount
     */
    public void setListcount(int listcount) {
        this.listcount = listcount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetListByCard)) return false;
        GetListByCard other = (GetListByCard) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.gtin==null && other.getGtin()==null) || 
             (this.gtin!=null &&
              this.gtin.equals(other.getGtin()))) &&
            ((this.create_time==null && other.getCreate_time()==null) || 
             (this.create_time!=null &&
              this.create_time.equals(other.getCreate_time()))) &&
            ((this.last_time==null && other.getLast_time()==null) || 
             (this.last_time!=null &&
              this.last_time.equals(other.getLast_time()))) &&
            this.pageindex == other.getPageindex() &&
            this.pagesize == other.getPagesize() &&
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
        if (getGtin() != null) {
            _hashCode += getGtin().hashCode();
        }
        if (getCreate_time() != null) {
            _hashCode += getCreate_time().hashCode();
        }
        if (getLast_time() != null) {
            _hashCode += getLast_time().hashCode();
        }
        _hashCode += getPageindex();
        _hashCode += getPagesize();
        _hashCode += getListcount();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetListByCard.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", ">GetListByCard"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gtin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "gtin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("create_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "create_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("last_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "last_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageindex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "pageindex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagesize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "pagesize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
