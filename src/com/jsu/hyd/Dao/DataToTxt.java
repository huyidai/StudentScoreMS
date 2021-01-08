package com.jsu.hyd.Dao;


import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jsu.hyd.Dao.DatabaseConnectionSql;
import com.jsu.hyd.User.Student;


public class DataToTxt {
	static Connection conn = new DatabaseConnectionSql().getConnection();
	public static List<Student> getAllDAta() {
		String sql = "select * from score";
		List<Student> list = null;
		try(PreparedStatement pstmt=conn.prepareStatement(sql);) {
			list = new ArrayList<Student>();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Student student = new Student();
				student.setNo(rs.getString(1));
				student.setName(rs.getString(2));
				student.setAsmScore(rs.getInt(3));
				student.setJavaScore(rs.getInt(4));
				student.setNetScore(rs.getInt(5));
				student.setOsScore(rs.getInt(6));
				list.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static void main(String[] args) {
		try(FileWriter fw = new FileWriter("data.txt");) {
			List<Student> list = getAllDAta();
			for(Student s:list) {
				fw.write(s.toString()+"\r\n");
			}
			JOptionPane.showMessageDialog(null, "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
