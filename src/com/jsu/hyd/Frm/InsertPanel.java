package com.jsu.hyd.Frm;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertPanel extends JPanel {
	private Font headFont = new Font("黑体", Font.PLAIN, 18);//标题字体
	/**
	 * Create the panel.
	 */
	public InsertPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setFocusable(true);
		/* 标题文本 */
		JLabel title = new JLabel("录入学生成绩");
		title.setFont(headFont);
		panel.add(title);
		title.setBounds(175, 25, 120, 30);
		/* 显示表单文本及文本框 */
		final String[] label = { "学号", "姓名", "汇编程序设计", "Java程序设计", "计算机网络", "操作系统" };
		final JTextField[] textField = new JTextField[label.length];
		for (int i = 0; i < label.length; i++) {
			JLabel titleLabel = new JLabel(label[i] + "： ");
			titleLabel.setBounds(100, 80 + 40 * i, 120, 30);
			panel.add(titleLabel);
			textField[i] = new JTextField(20);
			textField[i].setBounds(220, 80 + 40 * i, 150, 24);
			panel.add(textField[i]);
		}
		/* 录入按钮 */
		JButton addBtn = new JButton("录入信息");
		panel.add(addBtn);
		addBtn.setBounds(140, 340, 105, 30);
		JButton clearBtn = new JButton("清空");
		panel.add(clearBtn);
		clearBtn.setBounds(280, 340, 60, 30);
		panel.setVisible(true);
	}

}
