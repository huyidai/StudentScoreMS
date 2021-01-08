package com.jsu.hyd.Util;

import java.awt.FontFormatException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.jsu.hyd.Dao.DataOperate;
import com.jsu.hyd.User.Student;

/**
 * ���ݱ��ģ��
 */
public class DataTable extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	String[] label = { "ѧ��", "����", "���", "Java", "����", "ϵͳ" };
	ArrayList<Student> dataList = new ArrayList<Student>();
	/**
	 * ����ѧ����Ϣ��������
	 * 
	 * @param dataList ѧ����Ϣ��������
	 */
	public void update(ArrayList<Student> dataList) {
		this.dataList = dataList;
	}

	/**
	 * ����ѧ����Ϣ����
	 * 
	 * @param student ѧ����Ϣ����
	 */
	public void update(Student student) {
		dataList.clear();
		dataList.add(student);
	}
	/**
	 * ���ģ�ͳ���
	 */
	public int getColumnCount() {
		return label.length;
	}
	/**
	 * ��ö������鳤��
	 */
	public int getRowCount() {
		return dataList.size();
	}
	/**
	 * ��ÿһ������浽ģ����
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