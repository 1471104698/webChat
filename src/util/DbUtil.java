package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class DbUtil {

	//数据库地址
	private static String dbUrl="jdbc:mysql://localhost:3306/web";
	
	//用户名
	private static String dbUserName="root";
	
	//密码
	private	static String dbPassword="123456";
	
	//驱动名称
	private static String jdbcName="com.mysql.jdbc.Driver";
	/*
	 * 获取数据库连接
	 * */
	public Connection getCon() throws Exception{		
			//加载驱动
		Class.forName(jdbcName);	
			//获取连接对象
		Connection con= (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		return con;	
	}
	/*
	 * 关闭连接
	 * */
	public void close(PreparedStatement psta,Connection con)throws SQLException{
		if(psta!=null) {	//con就像先进大门，psta就像后进小门，出来都是先关小门再关大门
		psta.close();
			if(con!=null)
				con.close();
		}
		}
}
