/*
 * 玖号置业：会员接口
 */
package com.jlsoft.pi.jhzy;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.framework.pi.convertor.json.JsonToListConvertor;
import com.jlsoft.framework.pi.convertor.list.ListMappingConvertor;
import com.jlsoft.framework.pi.convertor.list.ListToJsonArraryConvertor;
import com.jlsoft.pi.jhzy.convertor.StringToDateConvertor;
import com.jlsoft.utils.JLTools;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/HYJK")
public class HYJK extends JLBill {

    public static Map<String, String> msgs = new HashMap<String, String>() {

        {
            put("001", "身份类型不允许为空！");
            put("002", "身份类型输入错误！");
            put("003", "密码不允许为空！");
            put("004", "手机号码不允许为空！");
            put("005", "登记人不允许为空！");
            put("006", "帐号不允许为空！");
            put("007", "帐号不存在！");
            put("008", "密码输入错误！");
            put("009", "新密码不允许为空！");
            put("010", "第三方用户不允许修改密码！");
            put("011", "帐号已经存在！");
            put("200", "成功!");
        }
    };


    /*
     * 会员接口：登陆
     */
    @RequestMapping("/Login.do")
    public Map login(String XmlData) throws Exception {
        Map result = new HashMap();
        cds = new DataSet(mapping("JHZY.Login_Request", XmlData));
        String sql = "(SF01?,SF02?,SFLX01?)";
        Map row = getRow(sql, null, 0);

        result = doLongin(row);
        if (result != null) {
            return result;
        }

        if (result == null) {
            result = new HashMap();
        }
        result.put("status", "200");
        result.put("message", msgs.get("200"));
        result.put("vip_id", row.get("VIP01"));
        return result;
    }

    private Map doLongin(Map row) throws Exception {
        Map result = new HashMap();
        if (isEmpty(row.get("SFLX01"))) {
            result.put("status", "001");
            result.put("message", msgs.get("001"));
            return result;
        }
        if (isEmpty(row.get("SF01"))) {
            result.put("status", "006");
            result.put("message", msgs.get("006"));
            return result;
        }
        Map rowSFLX = queryForMap(vip, "select SFLX01, SFLX02, SFLX03 from SFLX where SFLX01 = :SFLX01", row);
        if ((rowSFLX == null) || (isEmpty(rowSFLX.get("SFLX01")))) {
            result.put("status", "002");
            result.put("message", msgs.get("002"));
            return result;
        }
        if ((((Number) rowSFLX.get("SFLX03")).longValue() == 0) && (isEmpty(row.get("SF02")))) {
            result.put("status", "003");
            result.put("message", msgs.get("003"));
            return result;
        }
        String sql = "select VIP01, SF01, SF02, SFLX01"
                + "  from VIPSF"
                + " where SFLX01 = :SFLX01"
                + "   and SF01 = :SF01";
        Map rowVIPSF = queryForMap(vip, sql, row);
        if ((rowVIPSF == null) || (isEmpty(rowVIPSF.get("VIP01")))) {
            result.put("status", "007");
            result.put("message", msgs.get("007"));
            return result;
        }
        if ((((Number) rowSFLX.get("SFLX03")).longValue() == 0) && (!rowVIPSF.get("SF02").toString().equals(JLTools.MD5(row.get("SF02").toString())))) {
            result.put("status", "008");
            result.put("message", msgs.get("008"));
            return result;
        }
        row.put("VIP01", rowVIPSF.get("VIP01"));
        row.put("SFLX03", rowSFLX.get("SFLX03"));

        return null;
    }

    /*
     * 会员接口：注册
     */
    @RequestMapping("/Register.do")
    public Map insert(String XmlData) throws Exception {
        Map result = new HashMap();
        cds = new DataSet(mapping("JHZY.Register_Request", XmlData));
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("VIPLX01", getVIPLX());
        value.put("VIP15", CONST_DATE);
        value.put("VIP19", CONST_DATE);
        value.put("GSXX01", "0000");
        String sql = "(SF01?,SF02?,SFLX01?,VIP04?,VIP05?,VIP18?,VIP28?,VIPI01?,VIPI03?,VIPI31?)";
        Map row = getRow(sql, value, 0);
        result = checkInput(row);
        if (result != null) {
            return result;
        }
        row.put("SF02", JLTools.MD5(row.get("SF02").toString()));
        if (!isHY(row)) { //检查会员是否存在
            row.put("VIP01", updateVIPBHZT(vip, "VIPXX"));
            row.put("VIP02", "ONLINE_" + JLTools.intToStr(updateVIPBHZT(vip, "VIPZH")));
            if (!isEmpty(row.get("VIPI31"))) {
                row.put("VIPI31", new StringToDateConvertor().process(row.get("VIPI31")));
            }
            saveVIPXX(row);  //新建会员
            saveVIPITEM(row);
        }
        saveVIPSF(row);  //新建身份

        if (result == null) {
            result = new HashMap();
        }
        result.put("status", "200");
        result.put("message", msgs.get("200"));
        result.put("vip_id", row.get("VIP01"));
        return result;
    }

    /*
     * 会员接口：修改
     */
    @RequestMapping("/Update.do")
    public Map update(String XmlData) throws Exception {
        Map result = new HashMap();
        cds = new DataSet(mapping("JHZY.Register_Request", XmlData));
        return result("");
    }

    /*
     * 会员接口：密码修改
     */
    @RequestMapping("/UpdatePassword.do")
    public Map updatePassword(String XmlData) throws Exception {
        Map result = new HashMap();
        cds = new DataSet(mapping("JHZY.UpdatePassword_Request", XmlData));
        String sql = "(SF01?,SF02?,NMM?,SFLX01?)";
        Map row = getRow(sql, null, 0);

        result = doLongin(row);
        if (result != null) {
            return result;
        } else {
            result = new HashMap();
        }

        if ((((Number) row.get("SFLX03")).longValue() == 0)) {
            if (isEmpty(row.get("NMM"))) {
                result.put("status", "009");
                result.put("message", msgs.get("009"));
                return result;
            }
        } else {
            result.put("status", "010");
            result.put("message", msgs.get("010"));
            return result;
        }

        row.put("SF02", JLTools.MD5(row.get("NMM").toString()));

        updateVIPSF(row);

        result.put("status", "200");
        result.put("message", msgs.get("200"));
        result.put("vip_id", row.get("VIP01"));
        return result;
    }

    private String mapping(String key, String data) throws Exception {
        return (String) new JsonToListConvertor().setProcessor(new ListMappingConvertor(key)).
                setProcessor(new ListToJsonArraryConvertor()).process(data);
    }

    private long getVIPLX() throws Exception {
        Map row = queryForMap(vip, "select VIPLX01 from VIPLX where VIPLX42 = 1", null);
        if (row == null) {
            row = new HashMap();
            row.put("VIPLX01", updateVIPBHZT(vip, "VIPLX"));
            row.put("VIPLX02", "线上会员");
            row.put("VIPLX42", 1);
            execSQL(vip, "INSERT INTO VIPLX (VIPLX01,VIPLX02, VIPLX42) VALUES (VIPLX01?,VIPLX02?,VIPLX42?)", row);
        }
        return ((Number) row.get("VIPLX01")).longValue();
    }

    public static boolean isEmpty(Object value) {
        return value == null || value.toString().equals("");
    }

    private Map checkInput(Map row) throws Exception {
        Map result = new HashMap();
        if (isEmpty(row.get("SFLX01"))) {
            result.put("status", "001");
            result.put("message", msgs.get("001"));
            return result;
        }
        Map rowSFLX = queryForMap(vip, "select SFLX01, SFLX02, SFLX03 from SFLX where SFLX01 = :SFLX01", row);
        if ((rowSFLX == null) || (isEmpty(rowSFLX.get("SFLX01")))) {
            result.put("status", "002");
            result.put("message", msgs.get("002"));
            return result;
        }
        if ((((Number) rowSFLX.get("SFLX03")).longValue() == 0) && (isEmpty(row.get("SF02")))) {
            result.put("status", "003");
            result.put("message", msgs.get("003"));
            return result;
        }
        if (isEmpty(row.get("VIP04"))) {
            result.put("status", "004");
            result.put("message", msgs.get("004"));
            return result;
        }
        if (isEmpty(row.get("VIP18"))) {
            result.put("status", "005");
            result.put("message", msgs.get("005"));
            return result;
        }
        if (isEmpty(row.get("SF01"))) {
            result.put("status", "006");
            result.put("message", msgs.get("006"));
            return result;
        }

        String sql = "select VIP01, SF01, SF02, SFLX01"
                + "  from VIPSF"
                + " where SFLX01 = :SFLX01"
                + "   and SF01 = :SF01";
        Map rowVIPSF = queryForMap(vip, sql, row);
        if ((rowVIPSF != null) && (!isEmpty(rowVIPSF.get("VIP01")))) {
            result.put("status", "011");
            result.put("message", msgs.get("011"));
            return result;
        }

        return null;
    }

    private boolean isHY(Map row) throws Exception {
        boolean result = false;
        Map map = queryForMap(vip, "select VIP01,VIP02 from VIPXX where VIP04 = :VIP04", row);
        if ((map != null) && (((Number) map.get("VIP01")).longValue() > 0)) {
            row.put("VIP01", map.get("VIP01"));
            row.put("VIP02", map.get("VIP02"));
            result = true;
        }
        return result;
    }

    private void saveVIPXX(Map row) throws Exception {
        String sql = "insert into VIPXX(VIP01, VIP02, VIP04, VIP05, VIP12, VIPLX01, VIP15, VIP18, VIP19, GSXX01)"  //, VIP28
                + "  values(VIP01?, VIP02?, VIP04?, VIP05?, VIP12?, VIPLX01?, VIP15?, VIP18?, VIP19?, GSXX01?)";   //, VIP28?
        execSQL(vip, sql, row);
    }

    private void saveVIPITEM(Map row) throws Exception {
        String sql = "insert into VIPITEM(VIP01, VIPI01, VIPI03, VIPI31, GSXX01)"
                + "  values(VIP01?, VIPI01?, VIPI03?, VIPI31?, GSXX01?)";
        execSQL(vip, sql, row);
    }

    private void saveVIPSF(Map row) throws Exception {
        String sql = "insert into VIPSF(VIP01, SF01, SF02, SFLX01)"
                + "  values(VIP01?, SF01?, SF02?, SFLX01?)";
        execSQL(vip, sql, row);
    }

    private void updateVIPSF(Map row) throws Exception {
        String sql = "update VIPSF"
                + "    set  SF02 = SF02? "
                + "  where SFLX01 = SFLX01?"
                + "    and SF01 = SF01?";
        execSQL(vip, sql, row);
    }
}
