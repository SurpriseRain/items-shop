package com.jlsoft.pi.jia.sync;

import com.jlsoft.framework.pi.convertor.list.ListMappingConvertor;
import com.jlsoft.framework.pi.convertor.list.ListToDataSetConvertor;
import com.jlsoft.framework.pi.convertor.map.MapToJsonConvertor;
import com.jlsoft.framework.pi.convertor.map.MapMappingConvertor;
import com.jlsoft.framework.pi.convertor.json.JsonToMapConvertor;
import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.framework.pi.connector.jdbc.writer.OperatType;
import com.jlsoft.framework.pi.convertor.charset.CharsetConvertor;
import com.jlsoft.framework.pi.convertor.date.DateToStringConvertor;
import com.jlsoft.framework.pi.convertor.list.ListToJsonArraryConvertor;
import com.jlsoft.framework.pi.processor.log.LogProcessor;
import com.jlsoft.framework.pi.transport.JdbcReaderTransportor;
import com.jlsoft.framework.pi.transport.JdbcWriterTransportor;
import com.jlsoft.framework.pi.util.DBUtils;
import com.jlsoft.framework.pi.util.JndiConst;
import com.jlsoft.framework.pi.util.Untils;
import com.jlsoft.utils.PropertiesReader;
import java.util.*;
import net.sf.json.JSONNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/JiaJK")
public class JiaJK extends JLBill {

    private final String JIA_HYJN_URL = "JIA.HYJN.URL";  //会员卡校验接口地址
    private final String JIA_TG_URL = "JIA.TG.URL";  //查询团购信息接口地址
    private final String JIA_ORDER_URL = "JIA.ORDER.URL";  //下订单接口地址（钱包）
    private final String JIA_OldTgOrder_URL = "JIA.OldTgOrder.URL";  //下订单接口地址（非钱包）
    private final String JIA_UpdateORDER_URL = "JIA.UpdateORDER.URL";  //修改尾款(订金转销售)
    private final String JIA_OrderStatus_URL = "JIA.OrderStatus.URL";  //订单状态查询接口地址
    private final String JIA_OrderDeliver01_URL = "JIA.OrderDeliver01.URL";  //发货接口（非钱包）
    private final String JIA_OrderDeliver02_URL = "JIA.OrderDeliver02.URL";  //发货接口（钱包）

    @RequestMapping("/checkHYXX.do")
    public Map checkHYXX(String XmlData) throws Exception {
        cds = new DataSet(XmlData);
        Map message = getRow("(CARDNUM?,TYPE?)", null, 0);
        Map<String, Object> response = (Map<String, Object>) new MapMappingConvertor("JIA.HYXX_Request").setProcessor(new MapToJsonConvertor()).setProcessor(new JaiTransportor(JIA_HYJN_URL)).setProcessor(new JsonToMapConvertor()).process(message);
        if (!"200".equals(String.valueOf(response.get("statusCode")))) {
            throw new RuntimeException(response.toString());
        }
        String xml = (String) new MapMappingConvertor("JIA.HYXX_Response").setProcessor(new ListToDataSetConvertor("JIA.V9.HYXX")).process(response.get("result"));
        return result(xml);
    }

    @RequestMapping("/getTGXX.do")
    public Map getTGXX(String XmlData) throws Exception {
        cds = new DataSet(XmlData);
        Map message = getRow("(AREAFLAG?)", null, 0);
        message.put("TIMES", new Date());
        Map<String, Object> response = (Map<String, Object>) new MapMappingConvertor("JIA.TGXX_Request").setProcessor(new LogProcessor()).setProcessor(new JaiTransportor(JIA_TG_URL)).setProcessor(new LogProcessor()).setProcessor(new JsonToMapConvertor()).process(message);
        List rows = new ArrayList();
        if (("0".equals(String.valueOf(response.get("status")))) && (!(response.get("result") instanceof JSONNull))) {
            rows = (List) new ListMappingConvertor("JIA.TGXX_Response").process(response.get("result"));
        }
        String xml = (String) new ListToDataSetConvertor("JIA.V9.TGXX").process(rows);
        return result(xml);
    }

    public Map saveOrder(Map data) throws Exception {
        data.put("APPID", PropertiesReader.getInstance().getProperty("JIA.APPID"));
        data.put("AUTOCANCELTIME", new DateToStringConvertor("yyyy-MM-dd").process(new Date()) + " 23:59:59");
        Map<String, Object> response = null;
        if ("1".equals(String.valueOf(data.get("ZFBJ")))) {
            response = saveOfflineOrder(data);
        } else {
            response = new HashMap();
            Map result = new HashMap();
            result.put("ordergroupId", "");
            result.put("orderId", "");
            response.put("statusCode", 200);
            response.put("result", result);
        }

        if (!"200".equals(String.valueOf(response.get("statusCode")))) {
            throw new RuntimeException(response.toString());
        }
        return (Map) new MapMappingConvertor("JIA.Order_Response").process(response.get("result"));
    }

    private Map saveOfflineOrder(Map data) throws Exception {
        Map<String, Object> response = (Map<String, Object>) new MapMappingConvertor("JIA.Order_Request").setProcessor(new MapToJsonConvertor()).setProcessor(new CharsetConvertor()).setProcessor(new LogProcessor()).setProcessor(new JaiTransportor(JIA_ORDER_URL)).setProcessor(new LogProcessor()).setProcessor(new JsonToMapConvertor()).process(data);
        return response;
    }

    public Map updateOrder(Map data) throws Exception {
        data.put("APPID", PropertiesReader.getInstance().getProperty("JIA.APPID"));
        Map<String, Object> response = (Map<String, Object>) new MapMappingConvertor("JIA.UpdateOrder_Request").setProcessor(new MapToJsonConvertor()).setProcessor(new CharsetConvertor()).setProcessor(new LogProcessor()).setProcessor(new JaiTransportor(JIA_UpdateORDER_URL)).setProcessor(new LogProcessor()).setProcessor(new JsonToMapConvertor()).process(data);
        if (!"200".equals(String.valueOf(response.get("statusCode")))) {
            throw new RuntimeException(response.toString());
        }
        Map result = new HashMap();
        result.put("ORDERGROUPID", data.get("DINGJINDH"));
        result.put("ORDERID", data.get("ORDERID"));
        return result;
    }

    @RequestMapping("/qryOrderStatus.do")
    public Map qryOrderStatus(String XmlData) throws Exception {
        Map<String, Object> response = getOrderStatus(XmlData);
        if (!"200".equals(String.valueOf(response.get("statusCode")))) {
            throw new RuntimeException(response.toString());
        }
        String xml = (String) new MapMappingConvertor("JIA.OrderStatus_Response").setProcessor(new ListToDataSetConvertor("JIA.V9.OrderStatus")).process(response.get("result"));
        return result(xml);
    }

    @RequestMapping("/qryPreOrderStatus.do")
    public Map qryPreOrderStatus(String XmlData) throws Exception { //订金单状态查询
        Map<String, Object> response = getOrderStatus(XmlData);
        if (!"200".equals(String.valueOf(response.get("statusCode")))) {
            throw new RuntimeException(response.toString());
        }
        String xml = (String) new MapMappingConvertor("JIA.PreOrderStatus_Response").setProcessor(new ListToDataSetConvertor("JIA.V9.PreOrderStatus")).process(response.get("result"));
        return result(xml);
    }

    private Map getOrderStatus(String XmlData) throws Exception {
        cds = new DataSet(XmlData);
        Map data = getRow("(ORDERID?)", null, 0); //ORDERGROUPID?,
        Map<String, Object> response = (Map<String, Object>) new MapMappingConvertor("JIA.OrderStatus_Request").setProcessor(new MapToJsonConvertor()).setProcessor(new LogProcessor()).setProcessor(new JaiTransportor(JIA_OrderStatus_URL)).setProcessor(new LogProcessor()).setProcessor(new JsonToMapConvertor()).process(data);
        return response;
    }

    @RequestMapping("/orderDeliver.do")
    public void orderDeliver() throws Exception {
        synchronized (Untils.getSyncObject("JiaJK.OrderDeliver")) {
            JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
            transportor.setSql("select GSXX01, LSD01, APPID, SHOPID, ORDERID, ORDERGROUPID from V_JIA_FHJK");
            Object[] rows = (Object[]) transportor.process(null);
            Map<String, Object> response;
            for (Object row : rows) {
                response = getOrderStatus((String) (new MapToJsonConvertor()).process(row));
                if (!"200".equals(String.valueOf(response.get("statusCode")))) {
                    continue;
                }

                response = (Map<String, Object>) new MapMappingConvertor("JIA.OrderStatus_Response").process(response.get("result"));
                String type = String.valueOf(response.get("TYPE"));
                if (!(("2".equals(type)) || ("3".equals(type)) || ("4".equals(type)))) {
                    continue;
                }
                String JIA_OrderDeliver_URL = null;
                if (("2".equals(type)) || ("3".equals(type))) {  //调发货接口（钱包）
                    ((Map) row).put("SENDTYPE", 1);
                    JIA_OrderDeliver_URL = JIA_OrderDeliver02_URL;
                } else if ("4".equals(type)) {  //调发货接口（非钱包）
                    JIA_OrderDeliver_URL = JIA_OrderDeliver01_URL;
                }

                response = (Map<String, Object>) new MapMappingConvertor("JIA.OrderDeliver_Request")
                        .setProcessor(new MapToJsonConvertor())
                        .setProcessor(new LogProcessor())
                        .setProcessor(new JaiTransportor(JIA_OrderDeliver_URL))
                        .setProcessor(new LogProcessor()).setProcessor(new JsonToMapConvertor()).process(row);

                if ("200".equals(String.valueOf(response.get("statusCode")))) {
                    ((Map) row).put("TIME01", DBUtils.getTIME01());
                    new JdbcWriterTransportor(JndiConst.SCM, "JIA_FHJK", OperatType.Insert).process(row);
                }
            }
        }
    }

    private Map saveOldTgOrder(Map row) throws Exception {
        Map<String, Object> response = (Map<String, Object>) new MapMappingConvertor("JIA.OldTgOrder_Request")
                .setProcessor(new MapToJsonConvertor())
                .setProcessor(new CharsetConvertor())
                .setProcessor(new LogProcessor())
                .setProcessor(new JaiTransportor(JIA_OldTgOrder_URL))
                .setProcessor(new LogProcessor())
                .setProcessor(new JsonToMapConvertor())
                .process(row);
        return response;
    }

    private String getQJDYXX(Map parameters) throws Exception {
        JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
        transportor.setSql("select B.SPXX02, B.SPXX04, A.LSDI05, A.LSDI02, A.LSDI06"
                + "  from LSDITEM A, SPXX B"
                + " where A.SPXX01 = B.SPXX01"
                + "   and A.GSXX01 = ?"
                + "   and A.LSD01 = ?");
        Object[] rows = (Object[]) transportor.process(parameters);
        return (String) new ListMappingConvertor("JIA.LSDITEM_Print").setProcessor(new ListToJsonArraryConvertor()).process(Arrays.asList(rows));
    }

    @RequestMapping("/saveOldTgOrder.do")
    public void saveOldTgOrder() throws Exception {
        synchronized (Untils.getSyncObject("JiaJK.saveOldTgOrder")) {
            JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
            transportor.setSql("select A.GSXX01, A.LSD01, 1 AMOUNTTYPE, C.LSKH01 USERNAME, C.LSKH02 USERADDRESS, A.LSD16 CZY, A.LSD13 NOTE,"
                    + " B.APPID, B.ACTID, B.ACTNAME, B.AREA,B.CHANNELID, B.CHANNELNAME,B.DISCOUNTONTHOUSAND, "
                    + " B.JIFENRATE, B.SHOPID, B.SHOPNAME, B.TELEPHONE, B.INPUTAMOUNT,B.CARDNUM, B.MOBILE "
                    + " from LSD A, LSDQJTG B, LSKHXX C "
                    + " where A.GSXX01 = B.GSXX01 "
                    + "  and A.LSD01 = B.LSD01"
                    + "  and A.GSXX01 = C.GSXX01 "
                    + "  and A.LSD01 = C.LSD01"
                    + "  and A.LSD07 >= get_date - 10"
                    + "  and B.ORDERID is null"
                    + "  and B.ZFBJ = 0");
            Object[] rows = (Object[]) transportor.process(null);
            Map<String, Object> response;
            for (Object row : rows) {
                Map parameters = new HashMap();
                parameters.put(1, ((Map) row).get("GSXX01"));
                parameters.put(2, ((Map) row).get("LSD01"));
                ((Map) row).put("DYFP", getQJDYXX(parameters));
                response = saveOldTgOrder((Map) row);
                if ("200".equals(String.valueOf(response.get("statusCode")))) {
                    response = (Map<String, Object>) new MapMappingConvertor("JIA.Order_Response").process(response.get("result"));
                    response.put("GSXX01", ((Map) row).get("GSXX01"));
                    response.put("LSD01", ((Map) row).get("LSD01"));
                    ((Map) row).put("TIME01", DBUtils.getTIME01());
                    new JdbcWriterTransportor(JndiConst.SCM, "LSDQJTG", OperatType.Update).process(response);
                } else {
                    System.out.println(response);
                }
            }
        }
    }
//    @RequestMapping("/saveOfflineOrder.do")
//    public void saveOfflineOrder() throws Exception {
//        synchronized (Untils.getSyncObject("JiaJK.saveOfflineOrder")) {
//            JdbcReaderTransportor transportor = new JdbcReaderTransportor(JndiConst.SCM);
//            transportor.setSql("select A.GSXX01, A.LSD01, 1 AMOUNTTYPE, C.LSKH01 USERNAME, C.LSKH02 USERADDRESS, A.LSD16 CZY, A.LSD13 NOTE,"
//                    + " B.APPID, B.ACTID, B.ACTNAME, B.AREA,B.CHANNELID, B.CHANNELNAME,B.DISCOUNTONTHOUSAND, "
//                    + " B.JIFENRATE, B.SHOPID, B.SHOPNAME, B.TELEPHONE, B.INPUTAMOUNT,B.CARDNUM, B.MOBILE "
//                    + " from LSD A, LSDQJTG B, LSKHXX C "
//                    + " where A.GSXX01 = B.GSXX01 "
//                    + " and A.LSD01 = B.LSD01"
//                    + "  and A.GSXX01 = C.GSXX01 "
//                    + "  and A.LSD01 = C.LSD01"
//                    + "  and B.ORDERID is null"
//                    + "  and B.ZFBJ = 1");
//            Object[] rows = (Object[]) transportor.process(null);
//            Map<String, Object> response;
//            for (Object row : rows) {
//                response = saveOrder((Map) row);
//                if ("200".equals(String.valueOf(response.get("statusCode")))) {
//                    response = (Map<String, Object>) new MapMappingConvertor("JIA.Order_Response").process(response.get("result"));
//                    response.put("GSXX01", ((Map) row).get("GSXX01"));
//                    response.put("LSD01", ((Map) row).get("LSD01"));
//                    ((Map) row).put("TIME01", DBUtils.getTIME01());
//                    new JdbcWriterTransportor(JndiConst.SCM, "LSDQJTG", OperatType.Update).process(response);
//                } else{
//                    System.out.println(response);         
//                }
//            }
//        }
//    }    
}
