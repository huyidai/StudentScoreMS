package com.jsu.hyd.Frm;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DisplayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DisplayPanel() {
		JPanel panel = new JPanel();
		panel.setVisible(true);
		/* ѧ���ı����ı��� */
		panel.add(new JLabel("ѧ�ţ� "));
		final JTextField textField = new JTextField(10);
		panel.add(textField);
		panel.add(new JLabel(""));
		/* ��ѯ��ť */
		JButton searchBtn = new JButton("��ѯ");
		panel.add(searchBtn);
		/* ɾ����ť */
		JButton deleteBtn = new JButton("ɾ��");
		panel.add(deleteBtn);
		/* ��ʾ������Ϣ��ť */
		JButton allBtn = new JButton("�鿴������Ϣ");
		panel.add(allBtn);
	}

}
