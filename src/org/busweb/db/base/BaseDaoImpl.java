package org.busweb.db.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaseDaoImpl implements IBaseDao {

	private static MyDbPools pools;
	
	public List<Object[]> queryListByPara(String sql, Object... paras) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = pools.getConnection();
		
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			if(paras!=null && paras.length>0){
				for (int i = 0; i < paras.length; i++) {
					ptmt.setObject(i+1, paras[i]);
				}
			}
			
			ResultSet rs = ptmt.executeQuery();
			List<Object[]> list = new ArrayList<Object[]>();
			while (rs.next()) {
				String tempSql = sql.toUpperCase();
				String selectSql = sql.substring(tempSql.indexOf(" SELECT ")+" SELECT ".length(), tempSql.indexOf(" FROM "));
				int colNum = selectSql.split(",").length;
				Object[] rowArr = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					rowArr[i] = rs.getObject(i+1);
				}
				list.add(rowArr);
			}
			return list;
		} finally {
			pools.retrieveConnection(conn);
		}
		
	}

	public int modifyByPara(String sql, Object... paras) throws Exception {
		
		Connection conn = pools.getConnection();
		try {
			conn.setAutoCommit(false);
			PreparedStatement ptmt = conn.prepareStatement(sql);
			if(paras!=null && paras.length>0){
				for (int i = 0; i < paras.length; i++) {
					ptmt.setObject(i+1, paras[i]);
				}
			}
			
			int num= ptmt.executeUpdate();
			conn.commit();
			
			return num;
		} catch(Exception e){
			conn.rollback();
			throw e;
		}finally{
			conn.setAutoCommit(true);
			pools.retrieveConnection(conn);
		}
		
	}

}
