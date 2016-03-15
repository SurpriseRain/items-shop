/**
 * InHeader.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class InHeader  implements java.io.Serializable {
    private java.lang.String userName;

    private java.lang.String password;

    private java.lang.String ver;

    public InHeader() {
    }

    public InHeader(
           java.lang.String userName,
           java.lang.String password,
           java.lang.String ver) {
           this.userName = userName;
           this.password = password;
           this.ver = ver;
    }


    /**
     * Gets the userName value for this InHeader.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this InHeader.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the password value for this InHeader.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this InHeader.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the ver value for this InHeader.
     * 
     * @return ver
     */
    public java.lang.String getVer() {
        return ver;
    }


    /**
     * Sets the ver value for this InHeader.
     * 
     * @param ver
     */
    public void setVer(java.lang.String ver) {
        this.ver = ver;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InHeader)) return false;
        InHeader other = (InHeader) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.ver==null && other.getVer()==null) || 
             (this.ver!=null &&
              this.ver.equals(other.getVer())));
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
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getVer() != null) {
            _hashCode += getVer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InHeader.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org/", "InHeader"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "UserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ver");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org/", "Ver"));
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
