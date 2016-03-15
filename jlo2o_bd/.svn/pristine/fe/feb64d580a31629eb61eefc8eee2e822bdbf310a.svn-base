/**
 * ANCCServiceSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public class ANCCServiceSoapStub extends org.apache.axis.client.Stub implements
		com.jlsoft.QFYCODE.ANCCServiceSoap {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[3];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getProductDataByBarCode");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://www.gs1cn.org/",
						"clientID"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "string"),
				java.lang.String.class, false, false);
//		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://www.gs1cn.org/",
						"barcode"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "string"),
				java.lang.String.class, false, false);
//		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.gs1cn.org",
				">>getProductDataByBarCodeResponse>getProductDataByBarCodeResult"));
		oper.setReturnClass(com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult.class);
		oper.setReturnQName(new javax.xml.namespace.QName(
				"http://www.gs1cn.org", "getProductDataByBarCodeResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("CorrectDataByBarCode");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://www.gs1cn.org/",
						"clientID"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "string"),
				java.lang.String.class, false, false);
//		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://www.gs1cn.org",
						"CorrectData"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://www.gs1cn.org",
						">CorrectData"), com.jlsoft.QFYCODE.CorrectData.class,
				false, false);
//		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		oper.setReturnClass(java.lang.String.class);
		oper.setReturnQName(new javax.xml.namespace.QName(
				"http://www.gs1cn.org", "CorrectDataByBarCodeResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("getTraceDataByTraceCode");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://www.gs1cn.org/",
						"clientID"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "string"),
				java.lang.String.class, false, false);
//		param.setOmittable(true);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://www.gs1cn.org/",
						"tracecode"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "string"),
				java.lang.String.class, false, false);
//		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(
				"http://www.gs1cn.org", "TraceData"));
		oper.setReturnClass(com.jlsoft.QFYCODE.TraceData.class);
		oper.setReturnQName(new javax.xml.namespace.QName(
				"http://www.gs1cn.org", "getTraceDataByTraceCodeResult"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[2] = oper;

	}

	public ANCCServiceSoapStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public ANCCServiceSoapStub(java.net.URL endpointURL,
			javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public ANCCServiceSoapStub(javax.xml.rpc.Service service)
			throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service)
				.setTypeMappingVersion("1.2");
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		javax.xml.namespace.QName qName2;
		java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName = new javax.xml.namespace.QName("http://www.gs1cn.org/",
				">CorrectDataByBarCode");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.CorrectDataByBarCode.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org/",
				">getTraceDataByTraceCode");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.GetTraceDataByTraceCode.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org/",
				"OutHeader");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.OutHeader.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				">>getProductDataByBarCodeResponse>getProductDataByBarCodeResult");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				">CorrectData");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.CorrectData.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				">CorrectDataByBarCodeResponse");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.CorrectDataByBarCodeResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				">getTraceDataByTraceCodeResponse");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.GetTraceDataByTraceCodeResponse.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				">productData");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.ProductData.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"ANCCDocumentExtensionType");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.ANCCDocumentExtensionType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"ArrayOfInspectReport");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.InspectReport[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"InspectReport");
		qName2 = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"InspectReport");
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"ArrayOfMateriel");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.Materiel[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"materiel");
		qName2 = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"materiel");
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"ArrayOfPerson");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.Person[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://www.gs1cn.org", "Person");
		qName2 = new javax.xml.namespace.QName("http://www.gs1cn.org", "Person");
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"ArrayOfString");
		cachedSerQNames.add(qName);
		cls = java.lang.String[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string");
		qName2 = new javax.xml.namespace.QName("http://www.gs1cn.org", "string");
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"ArrayOfTraceEvent");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.TraceEvent[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"TraceEvent");
		qName2 = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"TraceEvent");
		cachedSerFactories
				.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(
						qName, qName2));
		cachedDeserFactories
				.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"firmDataType");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.FirmDataType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"InspectionInfo");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.InspectionInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"InspectReport");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.InspectReport.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"itemDataType");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.ItemDataType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"LogisticsInfo");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.LogisticsInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"materiel");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.Materiel.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"MeasurementValueType");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.MeasurementValueType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org", "Person");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.Person.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"RetailInfo");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.RetailInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"SupplyInfo");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.SupplyInfo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"TraceData");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.TraceData.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://www.gs1cn.org",
				"TraceEvent");
		cachedSerQNames.add(qName);
		cls = com.jlsoft.QFYCODE.TraceEvent.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

	}

	protected org.apache.axis.client.Call createCall()
			throws java.rmi.RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses
								.get(i);
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames
								.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories
									.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories
									.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
									.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
									.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault(
					"Failure trying to get the Call object", _t);
		}
	}

	public com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult getProductDataByBarCode(
			java.lang.String clientID, java.lang.String barcode)
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setTargetEndpointAddress("http://ws2.gs1cn.org/anccservice.asmx?WSDL");
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("http://www.gs1cn.org/getProductDataByBarCode");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
				Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
				Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://www.gs1cn.org/", "getProductDataByBarCode"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					clientID, barcode });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult) _resp;
				} catch (java.lang.Exception _exception) {
					return (com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult) org.apache.axis.utils.JavaUtils
							.convert(
									_resp,
									com.jlsoft.QFYCODE.GetProductDataByBarCodeResponseGetProductDataByBarCodeResult.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public java.lang.String correctDataByBarCode(java.lang.String clientID,
			com.jlsoft.QFYCODE.CorrectData correctData)
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("http://www.gs1cn.org/CorrectDataByBarCode");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
				Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
				Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://www.gs1cn.org/", "CorrectDataByBarCode"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					clientID, correctData });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (java.lang.String) _resp;
				} catch (java.lang.Exception _exception) {
					return (java.lang.String) org.apache.axis.utils.JavaUtils
							.convert(_resp, java.lang.String.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public com.jlsoft.QFYCODE.TraceData getTraceDataByTraceCode(
			java.lang.String clientID, java.lang.String tracecode)
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("http://www.gs1cn.org/getTraceDataByTraceCode");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
				Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
				Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://www.gs1cn.org/", "getTraceDataByTraceCode"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					clientID, tracecode });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (com.jlsoft.QFYCODE.TraceData) _resp;
				} catch (java.lang.Exception _exception) {
					return (com.jlsoft.QFYCODE.TraceData) org.apache.axis.utils.JavaUtils
							.convert(_resp, com.jlsoft.QFYCODE.TraceData.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


}
