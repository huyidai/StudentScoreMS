package com.jsu.hyd.Dao;

/** ʹ��JTable��ʾ���ݿ�����ݣ�Ҫ�����£�
* ���һ�����ݱ��ֶ���Ӧ�����������Ա������ֶ����⣬Ҫ��ʹ��Java�����������1000����¼������������ɣ����2�����ֻ�3�����ֵ����������Ա�������ɣ��л�Ů����
* ʹ��JTabel��ҳ��ʾ�������ݱ��е����ݣ���������ÿҳ��ʾ��������
* @author ������
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionSql{
	//����SQLServer���ݿ���������
	private static final String DBRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//����SQLServer���ݿ����ӵ�ַ��testdb�ɸĳ��Լ������ݿ�����
	private static final String DBURL="jdbc:sqlserver://localhost:1433;DatabaseName=scorems";
	private static final String DBUSER="sa"; //SQLServer���ݿ������û���
	private static final String PASSWORD="629yidai"; //SQLServer���ݿ���������
	private Connection conn=null; //�������Ӷ���
	public DatabaseConnectionSql(){//���췽���������ݿ�
		try {
			Class.forName(DBRIVER);
			this.conn=DriverManager.getConnection(DBURL,DBUSER,PASSWORD);
		} catch (Exception e) {e.printStackTrace();}
	}
	public Connection getConnection() {//�������ݿ����Ӷ���
		return this.conn;
	}
	
	
	
	public void close() {//�ر���������
		if(this.conn!=null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}