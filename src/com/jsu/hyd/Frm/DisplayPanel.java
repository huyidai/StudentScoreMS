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
		/* 学号文本及文本框 */
		panel.add(new JLabel("学号： "));
		final JTextField textField = new JTextField(10);
		panel.add(textField);
		panel.add(new JLabel(""));
		/* 查询按钮 */
		JButton searchBtn = new JButton("查询");
		panel.add(searchBtn);
		/* 删除按钮 */
		JButton deleteBtn = new JButton("删除");
		panel.add(deleteBtn);
		/* 显示所有信息按钮 */
		JButton allBtn = new JButton("查看所有信息");
		panel.add(allBtn);
	}

}
