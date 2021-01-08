package com.jsu.hyd.Dao;

/** 使用JTable显示数据库的数据，要求如下：
* 设计一张数据表，字段中应包含姓名、性别，其它字段任意，要求使用Java代码随机生成1000条记录，姓名随机生成（随机2个汉字或3个汉字的姓名），性别随机生成（男或女）。
* 使用JTabel分页显示上述数据表中的数据，并能设置每页显示数据条数
* @author 胡仪黛
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionSql{
	//定义SQLServer数据库驱动程序
	private static final String DBRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//定义SQLServer数据库连接地址，testdb可改成自己的数据库名称
	private static final String DBURL="jdbc:sqlserver://localhost:1433;DatabaseName=scorems";
	private static final String DBUSER="sa"; //SQLServer数据库连接用户名
	private static final String PASSWORD="629yidai"; //SQLServer数据库连接密码
	private Connection conn=null; //保存连接对象
	public DatabaseConnectionSql(){//构造方法连接数据库
		try {
			Class.forName(DBRIVER);
			this.conn=DriverManager.getConnection(DBURL,DBUSER,PASSWORD);
		} catch (Exception e) {e.printStackTrace();}
	}
	public Connection getConnection() {//返回数据库连接对象
		return this.conn;
	}
	
	
	
	public void close() {//关闭数据连接
		if(this.conn!=null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}