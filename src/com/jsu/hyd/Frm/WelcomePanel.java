package com.jsu.hyd.Frm;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel {
	private Font headFont = new Font("����", Font.PLAIN, 18);//��������
	/**
	 * Create the panel.
	 */
	public WelcomePanel() {
		JPanel panel = new JPanel();
		/* �����ı� */
		JLabel title = new JLabel("ѧ���ɼ�����ϵͳ v1.0");
		title.setFont(headFont);
		panel.add(title);
		panel.setVisible(true);
	}

}
