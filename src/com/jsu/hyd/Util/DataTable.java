package com.jsu.hyd.Util;

import java.awt.FontFormatException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.jsu.hyd.Dao.DataOperate;
import com.jsu.hyd.User.Student;

/**
 * 数据表格模型
 */
public class DataTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	String[] label = { "学号", "姓名", "汇编", "Java", "网络", "系统" };
	ArrayList<Student> dataList = new ArrayList<Student>();
	/**
	 * 更新学生信息对象数组
	 * 
	 * @param dataList 学生信息对象数组
	 */
	public void update(ArrayList<Student> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 更新学生信息对象
	 * 
	 * @param student 学生信息对象
	 */
	public void update(Student student) {
		dataList.clear();
		dataList.add(student);
	}
	/**
	 * 获得模型长度
	 */
	public int getColumnCount() {
		return label.length;
	}
	/**
	 * 获得对象数组长度
	 */
	public int getRowCount() {
		return dataList.size();
	}
	/**
	 * 将每一个对象存到模型中
	 */
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:
			return dataList.get(row).getNo();
		case 1:
			return dataList.get(row).getName();
		case 2:
			return dataList.get(row).getAsmScore();
		case 3:
			return dataList.get(row).getJavaScore();
		case 4:
			return dataList.get(row).getNetScore();
		case 5:
			return dataList.get(row).getOsScore();
		}
		return null;
	}
	@Override
	public String getColumnName(int column) {
		return label[column];
	}
}