package com.jlsoft.o2o.interfacepackage.loop.workflow;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlsoft.framework.JLBill;
import com.jlsoft.utils.JLTools;

@Controller
@RequestMapping("/InitCZY")
public class InitCZY extends JLBill{
	
	@RequestMapping("/handleCZY.action")
	public Map handleCZY() throws Exception{
		String sql = "SELECT A.ZCXX01,C.ZCXX25 ERPDZ,B.PERSON_ID,B.PASSWD,A.ZCXX02 PERSON_NAME FROM W_ZCGS A,W_XTCZY B,W_ZCXX C "+
					 "WHERE A.ZCXX01=B.GSID AND A.ZCXX01=C.ZCXX01 AND A.ZCGS03=42 AND A.ZCGS01=4";
		List list = queryForList(o2o,sql);
		Map map = null;
		for(int i=0;i<list.size();i++){
			map = (Map)list.get(i);
			try{
				if(map.get("ERPDZ")!=null){
					tbCZY(map);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return new HashMap();
	}
	
	/**
	 * @todo 同步操作员
	 * @param map
	 * @throws Exception
	 */
	public void tbCZY(Map map) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		try{
			conn = getWorkflowConnection();
			conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String GSXX01 = "0002";
            String BM01 = "00020203";
            String GW01="13";
            
            //删除现有W_CZY,W_CZYGW数据
            //pstmt=conn.prepareStatement("delete from w_czy where czy02=?");
            //pstmt.setString(1, map.get("PERSON_ID").toString());
            //pstmt.executeUpdate();
            
            //获取操作员编号
            cstmt = conn.prepareCall("{call UPDATE_BHZT(?,?)}");
            cstmt.setString(1, "W_CZY");
            cstmt.registerOutParameter(2,java.sql.Types.INTEGER);
            cstmt.executeUpdate();
            int CZY01 = cstmt.getInt(2);
            
            //写入W_CZY表
            pstmt=conn.prepareStatement("INSERT INTO W_CZY(CZY01,CZY02,CZY03,CZY04,GSXX01,BM01) VALUES(?,?,?,?,?,?)");
            pstmt.setInt(1, CZY01);
            //pstmt.setString(2, map.get("PERSON_ID").toString());
            pstmt.setString(2, map.get("ERPDZ").toString());
            pstmt.setString(3, map.get("PERSON_NAME").toString());
            pstmt.setString(4, JLTools.MD5(map.get("PASSWD").toString()));
            pstmt.setString(5, GSXX01);
            pstmt.setString(6, BM01);
            pstmt.executeUpdate();
            
            //写入岗位表
            //insert into w_czygw values(125003,13,0,0002)
            pstmt=conn.prepareStatement("insert into w_czygw(CZY01,GW01,YXBJ,GSXX01) values(?,?,?,?)");
            pstmt.setInt(1, CZY01);
            pstmt.setString(2,GW01);
            pstmt.setInt(3, 0);
            pstmt.setString(4, GSXX01);
            pstmt.executeUpdate();
            
            conn.commit();
		}catch(Exception ex){
			if(conn != null){
				conn.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}finally{
			if(rs != null){
				rs.close();
				rs=null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt=null;
			}
			if(conn != null){
				conn.close();
				conn=null;
			}
		}
	}
	
	/**
	 * @todo 获取工作数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getWorkflowConnection()throws Exception{
		String url = "jdbc:oracle:thin:@119.79.224.118:6789:web";
		String user = "qy_workflow";
		String password = "aaaa";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
}