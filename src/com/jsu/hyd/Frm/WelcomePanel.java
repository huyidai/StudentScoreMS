package com.jsu.hyd.Frm;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel {
	private Font headFont = new Font("黑体", Font.PLAIN, 18);//标题字体
	/**
	 * Create the panel.
	 */
	public WelcomePanel() {
		JPanel panel = new JPanel();
		/* 标题文本 */
		JLabel title = new JLabel("学生成绩管理系统 v1.0");
		title.setFont(headFont);
		panel.add(title);
		panel.setVisible(true);
	}

}
