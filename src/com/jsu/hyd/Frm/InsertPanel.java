package com.jsu.hyd.Frm;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertPanel extends JPanel {
	private Font headFont = new Font("����", Font.PLAIN, 18);//��������
	/**
	 * Create the panel.
	 */
	public InsertPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setFocusable(true);
		/* �����ı� */
		JLabel title = new JLabel("¼��ѧ���ɼ�");
		title.setFont(headFont);
		panel.add(title);
		title.setBounds(175, 25, 120, 30);
		/* ��ʾ���ı����ı��� */
		final String[] label = { "ѧ��", "����", "���������", "Java�������", "���������", "����ϵͳ" };
		final JTextField[] textField = new JTextField[label.length];
		for (int i = 0; i < label.length; i++) {
			JLabel titleLabel = new JLabel(label[i] + "�� ");
			titleLabel.setBounds(100, 80 + 40 * i, 120, 30);
			panel.add(titleLabel);
			textField[i] = new JTextField(20);
			textField[i].setBounds(220, 80 + 40 * i, 150, 24);
			panel.add(textField[i]);
		}
		/* ¼�밴ť */
		JButton addBtn = new JButton("¼����Ϣ");
		panel.add(addBtn);
		addBtn.setBounds(140, 340, 105, 30);
		JButton clearBtn = new JButton("���");
		panel.add(clearBtn);
		clearBtn.setBounds(280, 340, 60, 30);
		panel.setVisible(true);
	}

}
