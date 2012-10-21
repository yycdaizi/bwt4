package org.beiyi.wt4.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

public class SqlExcuteHelper {
	private Connection conn=null;
	
	public SqlExcuteHelper() throws SQLException{
		conn = JdbcUtil.getConnection();
	}
	public void startTransaction() throws SQLException{
		conn.setAutoCommit(false);
	}
	
	/**
	 * 插入sql语句
	 * @param sql
	 * @return 主键
	 * @throws Exception
	 */
	public long insertSingle(String sql,Object[] paras) throws SQLException{
		long id=0;
		PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		for(int i=0;i<paras.length;i++){
			ps.setObject(i+1, paras[i]);
		}
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()){
			id=(Long)rs.getObject(1);
		}
		return id;
	}
	
	public Object queryFisrt(String sql,Object[] paras) throws SQLException{
		PreparedStatement ps=conn.prepareStatement(sql);
		for(int i=0;i<paras.length;i++){
			ps.setObject(i+1, paras[i]);
		}
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return rs.getObject(1);
		}
		return null;
	}
	
	public void commit()throws SQLException{
		conn.commit();
	}
	
	public void releaseQuitely(){
		JdbcUtil.releaseConnection(null, null, conn);
	}
}
