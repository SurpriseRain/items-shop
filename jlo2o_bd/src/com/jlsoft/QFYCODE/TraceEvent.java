/**
 * TraceEvent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class TraceEvent  implements java.io.Serializable {
    private java.lang.String step;

    private java.lang.String location;

    private java.util.Calendar eventDate;

    private com.jlsoft.QFYCODE.Person[] persons;

    private com.jlsoft.QFYCODE.Materiel[] materials;

    public TraceEvent() {
    }

    public TraceEvent(
           java.lang.String step,
           java.lang.String location,
           java.util.Calendar eventDate,
           com.jlsoft.QFYCODE.Person[] persons,
           com.jlsoft.QFYCODE.Materiel[] materials) {
           this.step = step;
           this.location = location;
           this.eventDate = eventDate;
           this.persons = persons;
           this.materials = materials;
    }


    /**
     * Gets the step value for this TraceEvent.
     * 
     * @return step
     */
    public java.lang.String getStep() {
        return step;
    }


    /**
     * Sets the step value for this TraceEvent.
     * 
     * @param step
     */
    public void setStep(java.lang.String step) {
        this.step = step;
    }


    /**
     * Gets the location value for this TraceEvent.
     * 
     * @return location
     */
    public java.lang.String getLocation() {
        return location;
    }


    /**
     * Sets the location value for this TraceEvent.
     * 
     * @param location
     */
    public void setLocation(java.lang.String location) {
        this.location = location;
    }


    /**
     * Gets the eventDate value for this TraceEvent.
     * 
     * @return eventDate
     */
    public java.util.Calendar getEventDate() {
        return eventDate;
    }


    /**
     * Sets the eventDate value for this TraceEvent.
     * 
     * @param eventDate
     */
    public void setEventDate(java.util.Calendar eventDate) {
        this.eventDate = eventDate;
    }


    /**
     * Gets the persons value for this TraceEvent.
     * 
     * @return persons
     */
    public com.jlsoft.QFYCODE.Person[] getPersons() {
        return persons;
    }


    /**
     * Sets the persons value for this TraceEvent.
     * 
     * @param persons
     */
    public void setPersons(com.jlsoft.QFYCODE.Person[] persons) {
        this.persons = persons;
    }


    /**
     * Gets the materials value for this TraceEvent.
     * 
     * @return materials
     */
    public com.jlsoft.QFYCODE.Materiel[] getMaterials() {
        return materials;
    }


    /**
     * Sets the materials value for this TraceEvent.
     * 
     * @param materials
     */
    public void setMaterials(com.jlsoft.QFYCODE.Materiel[] materials) {
        this.materials = materials;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TraceEvent)) return false;
        TraceEvent other = (TraceEvent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.step==null && other.getStep()==null) || 
             (this.step!=null &&
              this.step.equals(other.getStep()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.eventDate==null && other.getEventDate()==null) || 
             (this.eventDate!=null &&
              this.eventDate.equals(other.getEventDate()))) &&
            ((this.persons==null && other.getPersons()==null) || 
             (this.persons!=null &&
              java.util.Arrays.equals(this.persons, other.getPersons()))) &&
            ((this.materials==null && other.getMaterials()==null) || 
             (this.materials!=null &&
              java.util.Arrays.equals(this.materials, other.getMaterials())));
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
        if (getStep() != null) {
            _hashCode += getStep().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getEventDate() != null) {
            _hashCode += getEventDate().hashCode();
        }
        if (getPersons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPersons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPersons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMaterials() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMaterials());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMaterials(), i);
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
        new org.apache.axis.description.TypeDesc(TraceEvent.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "TraceEvent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("step");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Step"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "EventDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("persons");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Persons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "Person"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Person"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materials");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gs1cn.org", "Materials"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.gs1cn.org", "materiel"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.gs1cn.org", "materiel"));
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
