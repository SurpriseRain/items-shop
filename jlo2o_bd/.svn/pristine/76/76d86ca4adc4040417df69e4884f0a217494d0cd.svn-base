package com.jlsoft.utils;

import com.jlsoft.framework.JLBill;
import com.jlsoft.framework.dataset.DataSet;
import com.jlsoft.framework.pi.convertor.file.FileToStringConvertor;
import java.util.Map;

public class JsonTest extends JLBill {

    public static void main(String[] args) throws Exception {
       new JsonTest().test();
    }
    
    public void test() throws Exception{
      cds = new DataSet((String) new FileToStringConvertor().process("src/sample/mappingSample.txt"));
      Map[] rows = getRows("SPLIST", "", null, 0);
      System.out.println(cds.getField("SPLIST.WBDHDI01", 0,0));
      System.out.println(rows.length);
    }
}
