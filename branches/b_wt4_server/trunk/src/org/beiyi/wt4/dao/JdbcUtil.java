package org.beiyi.wt4.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.beiyi.SysInfo;

public final class JdbcUtil {
	private static String driver=SysInfo.getParmeter("driver");
	private static String url=SysInfo.getParmeter("url");;
	private static String username=SysInfo.getParmeter("username");;
	private static String password=SysInfo.getParmeter("password");;
	
	private JdbcUtil(){};
	
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError();
		}
		
	}
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,username,password);
	}
	public static void releaseConnection(ResultSet rs,Statement st,Connection conn){
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(st!=null){
					st.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					if(conn!=null){
						conn.close();
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
}
