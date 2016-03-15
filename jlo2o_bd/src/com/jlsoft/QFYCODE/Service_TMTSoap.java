/**
 * Service_TMTSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jlsoft.QFYCODE;

public interface Service_TMTSoap extends java.rmi.Remote {

    /**
     * 进行产品数据的相关操作，xml(上传的具体内容uploadFile.xsd)，act=1：覆盖，act=0表示删除
     */
    public java.lang.String uploadXMLByTMT(java.lang.String xml, java.lang.String act) throws java.rmi.RemoteException;

    /**
     * 测试服务器是否正常！
     */
    public void ping() throws java.rmi.RemoteException;

    /**
     * 进行产品图片的追加维护，binData是图片的base64编码模式
     */
    public java.lang.String uploadProductImage(java.lang.String gtin, java.lang.String BINData, java.lang.String fileName, java.lang.String fileDescription) throws java.rmi.RemoteException;

    /**
     * 根据模板标签获取特定模板的所有信息
     */
    public java.lang.String getItemTemplate(java.lang.String templateflag) throws java.rmi.RemoteException;

    /**
     * 根据厂商识别代码获取企业相关信息（firm_name,address,code,postcode, Classname,
     * ClassCode,login_date,valid_date,contactman,tele,email,branch_code,leader,
     * leader_tele,leader_handset,certificate_code,logout_flag）logout_flag:表示是否被注销；valid_date：是否过期
     */
    public com.jlsoft.QFYCODE.FirmForTMTResponseFirmForTMTResult firmForTMT(java.lang.String code) throws java.rmi.RemoteException;

    /**
     * 根据厂商识别代码获取企业相关信息（firm_name,address,code,postcode, Classname,
     * ClassCode,login_date,valid_date,contactman,tele,email,branch_code,leader,
     * leader_tele,leader_handset,certificate_code,logout_flag）logout_flag:表示是否被注销；valid_date：是否过期
     */
    public com.jlsoft.QFYCODE.FirmInfoByCardResponseFirmInfoByCardResult firmInfoByCard() throws java.rmi.RemoteException;

    /**
     * 根据条码卡获取所有产品信息,包括的字段如下 base_gtin,base_card,b.tm,description,class_code,brand,origin,
     * height,heightUnit,width,WidthUnit,depth,DepthUnit,placeOfOrigin,placeOfOriginCode,Description_EN,
     * keyword,consumerAvailabilityDateTime,discontinuedDate,descriptionShort,packagingMaterialCode,
     * packagingTypeCode,specification,shelfLife,sellingUnitOfMeasure,retailPriceOnTradeItem,supplierIdentification,
     * is_private,is_consumer
     */
    public com.jlsoft.QFYCODE.GetListByCardResponseGetListByCardResult getListByCard(java.lang.String gtin, java.util.Calendar create_time, java.util.Calendar last_time, int pageindex, int pagesize, javax.xml.rpc.holders.IntHolder listcount) throws java.rmi.RemoteException;
    public java.lang.String getVersion() throws java.rmi.RemoteException;

    /**
     * 获取编码中心最新的新闻信息
     */
    public com.jlsoft.QFYCODE.GetNewsListResponseGetNewsListResult getNewsList() throws java.rmi.RemoteException;

    /**
     * 测试用户是否正确
     */
    public void testConnection() throws java.rmi.RemoteException;
}
