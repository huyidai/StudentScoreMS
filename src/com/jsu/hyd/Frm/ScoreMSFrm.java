package com.jsu.hyd.Frm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jsu.hyd.User.Student;
import com.jsu.hyd.Util.DataTable;


public class ScoreMSFrm extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container currentContainer = this;//��ǰ����
	private String title = "ѧ���ɼ�����ϵͳ";//�������
	private Font headFont = new Font("����", Font.PLAIN, 18);//��������
	private File data = new File("data.txt");//�����ļ�
	private DataTable dataModel = new DataTable();//����ģ��
	private JTable dataTable = new JTable(dataModel);//���ݱ��
	private JPanel welcomePanel = createWelcomePanel();//��ӭ���
	private JPanel insertPanel = createInsertPanel();//�����Ϣ���
	private JPanel displayPanel = createDisplayPanel();//��ʾ��Ϣ���
	private JPanel modifyPanel = createModifyPanel();//�޸���Ϣ���
	
	/**
	 * �������� ��ʼ������
	 */
	public ScoreMSFrm() {
		/* ���ô��������Ϣ */
		this.setLocation(300, 300);
		this.setSize(475, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle(title);
		this.createMenu();
		this.add(welcomePanel);
		/* ��������ļ���������Ĭ�ϴ���һ�� */
		if (!data.exists()) {
			try {
				new FileWriter(data);
			} catch (IOException e) {
			}
		}
		
		/* ��ʾ���� */
		this.setVisible(true);
	}

	/**
	 * �����˵���
	 */
	private void createMenu() {
		JMenuBar menubar = new JMenuBar();
		/* �����˵�����ӵ��˵����� */
		JMenu manageMenu = new JMenu("����");
		menubar.add(manageMenu);
		JMenu helpMenu = new JMenu("����");
		menubar.add(helpMenu);
		/* �����˵����ӵ��˵��� */
		JMenuItem m_add = new JMenuItem("���");
		JMenuItem m_manage = new JMenuItem("�鿴");
		JMenuItem m_modify = new JMenuItem("�޸�");
		JMenuItem m_exit = new JMenuItem("�˳�");
		manageMenu.add(m_add);
		manageMenu.add(m_modify);
		manageMenu.add(m_manage);
		manageMenu.addSeparator();
		manageMenu.add(m_exit);
		JMenuItem m_help = new JMenuItem("˵��");
		JMenuItem m_copy = new JMenuItem("����");
		helpMenu.add(m_help);
		helpMenu.addSeparator();
		helpMenu.add(m_copy);
		/* Ϊ��Ӳ˵���ע��������� ʹ����ò˵���ʱ���ѧ���ɼ����� */
		m_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel(insertPanel);
			}
		});
		/* Ϊ�޸Ĳ˵���ע��������� ʹ����ò˵���ʱ�޸�ѧ���ɼ����� */
		m_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel(modifyPanel);
			}
		});
		/* Ϊ����˵���ע��������� ʹ����ò˵���ʱ��ʾѧ���ɼ��б� */
		m_manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel(displayPanel);
			}
		});
		/* Ϊ�˳��˵���ע��������� ʹ����ò˵���ʱ�˳����� */
		m_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		/* Ϊ˵���˵���ע��������� ʹ����ò˵���ʱ��������˵������ */
		m_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(currentContainer, "ѧ���ɼ�����ϵͳ�Ķ�������Уѧ���� �ɼ�����ϵͳΪѧ���ṩ�˳ɼ���ѯ��\r\n" + 
						"���� ΪѧУ����ʦ�ṩ����ӡ� �޸ġ� ɾ��ѧ���ɼ��ȷ��񡣡���", "������Ϣ", JOptionPane.PLAIN_MESSAGE);
			}
		});
		/* Ϊ��Ȩ�˵���ע��������� ʹ����ò˵���ʱ������Ȩ��Ϣ���� */
		m_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(currentContainer, "���ߣ� \n" + "2019401353 huyidai", "������Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		/* ���ò˵��� */
		this.setJMenuBar(menubar);
	}

	/**
	 * �������
	 */
	private void resetPanel(JPanel panel) {
		this.setVisible(false);
		this.getContentPane().removeAll();
		/* �����Ҫ�л�����ʾ��Ϣ��壬 ��ͬʱ������Ϣ */
		if (panel == displayPanel) {
			loadData();
		}
		this.getContentPane().add(panel);
		this.setVisible(true);
	}

	/**
	 * ������ӭ���
	 */
	private JPanel createWelcomePanel() {
		JPanel panel = new JPanel();
		/* �����ı� */
		JLabel title = new JLabel("ѧ���ɼ�����ϵͳ v1.0");
		title.setFont(headFont);
		panel.add(title);
		panel.setVisible(true);
		return panel;
	}

	/**
	 * ���������Ϣ���
	 * 
	 * @return �����Ϣ���
	 */
	private JPanel createInsertPanel() {
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
		/*
		 * Ϊѧ�Ű�ťע��������� ʹ�ƿ�����ʱ�Զ��������ѧ���Ƿ����
		 */
		textField[0].addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			/**
			 * ������ȱʧʱ
			 */
			public void focusLost(FocusEvent e) {
				boolean flag = true;
				String s = "";
				/* ���ѧ���Ƿ����� */
				if (!checkString(textField[0].getText())) {
					flag = false;
					s = "ѧ�Ų���Ϊ�գ� \n";
				}
				if (flag) {
					/* ���ѧ���Ƿ��Ѵ��� */
					if (!checkStudentNo(textField[0].getText().trim())) {
						flag = false;
						s = "ϵͳ�Ѵ��ڸ�ѧ�ŵ���Ϣ�� \n";
					}
				}
				if (!flag) {
					/*
					 * ���������ϵͳ�в�����ָ��ѧ���� ����ʾ
					 */
					JOptionPane.showMessageDialog(currentContainer, s, "����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/* ¼�밴ť */
		JButton addBtn = new JButton("¼����Ϣ");
		panel.add(addBtn);
		addBtn.setBounds(140, 340, 105, 30);
		/* Ϊ¼�밴ťע��������� ʹ������¼����� */
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(currentContainer, "ȷ��Ҫ¼����Ϣ�� ", "¼��ȷ��",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.NO_OPTION)
					return;
				boolean flag = true;
				String s = "¼������з����˴��� \n"; // Ԥ����Ĵ�����Ϣ
				/* ���ѧ�ź������Ƿ�δ���� */
				for (int i = 0; i < 2; i++) {
					if (!checkString(textField[i].getText())) {
						flag = false;
						s = s + label[i] + "δ��д�� \n";
					}
				}
				/*
				 * �������ɼ��Ƿ�δ��д�����벻������
				 */
				for (int i = 2; i < label.length; i++) {
					if (!checkInteger(textField[i].getText())) {
						flag = false;
						s = s + label[i] + "�ɼ�δ��д�����벻�������� \n";
					}
				}
				if (flag) {
					Student student = new Student();
					student.setNo(textField[0].getText().trim());
					/* ���ѧ���Ƿ��Ѵ��� */
					if (!checkStudentNo(student.getNo())) {
						flag = false;
						s = s + "ϵͳ���Ѵ�����ͬѧ�ŵ���Ϣ��\n";
					} else {
						/*
						 * ��װ�ı����е����ݵ��������εĶ�����
						 */
						student.setName(textField[1].getText().trim());
						student.setAsmScore(Integer.parseInt(textField[2].getText().trim()));
						student.setJavaScore(Integer.parseInt(textField[3].getText().trim()));
						student.setNetScore(Integer.parseInt(textField[4].getText().trim()));
						student.setOsScore(Integer.parseInt(textField[5].getText().trim()));
						/* ¼����Ϣ */
						flag = insertData(student);
					}
				}
				if (flag) {
					JOptionPane.showMessageDialog(currentContainer, "¼��ɹ��� ", "¼��ɹ�", JOptionPane.PLAIN_MESSAGE);
					clearForm(textField); // ¼��ɹ�����������ı�������
				} else {
					JOptionPane.showMessageDialog(currentContainer, s, "¼��ʧ��", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/* ��հ�ť */
		JButton clearBtn = new JButton("���");
		panel.add(clearBtn);
		clearBtn.setBounds(280, 340, 60, 30);
		/* Ϊ��հ�ťע��������� ʹ���������������ı��򷽷� */
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm(textField);
			}
		});
		panel.setVisible(true);
		return panel;
	}
	/**
	 * ������ʾ��Ϣ���
	 * 
	 * @return ��ʾ��Ϣ���
	 */
	private JPanel createDisplayPanel() {
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
		/*
		 * Ϊ��ѯ��ťע��������� ������ѯ��ѧ��ѧ������Ϣ�����µ������
		 */
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String no = textField.getText();
				/* ���ѧ���Ƿ����� */
				if (!checkString(no)) {
					JOptionPane.showMessageDialog(currentContainer, "δ����ѧ�ţ� ", "��ѯʧ��", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* ����Ƿ��и�ѧ�ŵļ�¼ */
				if (checkStudentNo(no)) {
					JOptionPane.showMessageDialog(currentContainer, "�����ڸ�ѧ�ŵ�ѧ����Ϣ�� ", "��ѯʧ��", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* ����ָ��ѧ�ŵ�ѧ����Ϣ����� */
				loadStudentInfo(no);
			}
		});
		/* ɾ����ť */
		JButton deleteBtn = new JButton("ɾ��");
		panel.add(deleteBtn);
		/* Ϊɾ����ťע��������� �����ɾ����ѧ��ѧ������Ϣ */
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String no = textField.getText();
				/* ���ѧ���Ƿ����� */
				if (!checkString(no)) {
					JOptionPane.showMessageDialog(currentContainer, "δ����ѧ�ţ� ", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* ����Ƿ��и�ѧ�ŵļ�¼ */
				if (checkStudentNo(no)) {
					JOptionPane.showMessageDialog(currentContainer, "�����ڸ�ѧ�ŵ�ѧ����Ϣ�� ", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* �����Ի���ȷ���Ƿ�Ҫɾ�� */

				int choose = JOptionPane.showConfirmDialog(currentContainer, "ȷ��Ҫɾ��ѧ��Ϊ" + no + "��ѧ������Ϣ��", "ɾ��ȷ��",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.NO_OPTION)
					return;
				/* ִ��ɾ������ */
				boolean flag = deleteData(no);
				if (!flag) {
					JOptionPane.showMessageDialog(currentContainer, "ɾ�������з����˴��� ", "ɾ��ʧ��", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/*
				 * ɾ���ɹ�����ת����ʾ��壬 �����ѧ���ı���
				 */
				resetPanel(displayPanel);
				JOptionPane.showMessageDialog(currentContainer, "�Ѿ�ɾ���˸�ѧ��ѧ������Ϣ�� ", "ɾ���ɹ�", JOptionPane.PLAIN_MESSAGE);
				textField.setText("");
			}
		});
		/* ��ʾ������Ϣ��ť */
		JButton allBtn = new JButton("�鿴������Ϣ");
		panel.add(allBtn);
		/*
		 * Ϊ��ʾ������Ϣ��ťע��������� ʹ������±������Ϊȫ��ѧ���� ��Ϣ
		 */
		allBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(""); // ���ѧ���ı���
				loadData(); // ���ر����Ϣ
			}
		});
		
		/**
		 * ���ļ��е�������ӵ����
		 */
		//ÿһ�б�ͷ
		Vector<String> vector = new Vector<String>();
		vector.addElement("ѧ��");
		vector.addElement("����");
		vector.addElement("���������");
		vector.addElement("Java�������");
		vector.addElement("���������");
		vector.addElement("����ϵͳ");
		//����������
		Vector<Vector> rowData = new Vector<Vector>();
		//��ʼ���ı��ļ�
		FileReader fr;
			try {
				fr = new FileReader("data.txt");
				BufferedReader br = new BufferedReader(fr);
				//����ÿһ��
				String ch = null;
				try {
					while ((ch=br.readLine())!=null) {//���ļ���ĩβ
						//split(String):���ݸ���������ʽ��ƥ���ִ��ַ�����
						String[] temp = ch.split(",");//ÿһ����Ŀո�
						Vector<String> row = new Vector<String>();
						for(int i=0;i<temp.length;i++) {//����ÿһ��
							row.add(temp[i]);//��ÿһ�ж�����row
						}
						rowData.add(row);//�ٰ�ÿһ��row�����ݸ�rowData
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			JTable dataTable = new JTable(rowData, vector);
	
		/* ���ݱ���ʼ������ */
		dataTable.setAutoscrolls(true); // ���ñ���Զ�����
		dataTable.setPreferredScrollableViewportSize(new Dimension(420, 300)); // ���ñ���С
		/* ������ݱ�� */
		panel.add(new JScrollPane(dataTable));
		return panel;
	}

	/**
	 * �����޸���Ϣ���
	 * 
	 * @return �޸���Ϣ���
	 */
	private JPanel createModifyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setFocusable(true);
		/* �����ı� */
		JLabel title = new JLabel("�޸�ѧ���ɼ�");
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
		/*
		 * Ϊѧ���ı������Ӽ�����:�������ƿ�ʱ��ȡ���ı����е�ѧ����Ϣ
		 */
		textField[0].addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			/**
			 * ������ȱʧʱ
			 */
			public void focusLost(FocusEvent e) {
				boolean flag = true;
				String s = "";
				/* ���ѧ���Ƿ����� */
				if (!checkString(textField[0].getText())) {
					flag = false;
					s = "ѧ�Ų���Ϊ�գ� \n";
				}
				if (flag) {
					/*
					 * ���ϵͳ���Ƿ��и�ѧ�ŵ�ѧ����Ϣ
					 */
					Student student = selectStudent(textField[0].getText().trim());
					if (student == null) {
						flag = false;
						s = "ϵͳ�в����ڸ�ѧ�ŵ���Ϣ�� \n";
					} else {
						/* ���µ��ı��� */
						textField[1].setText(student.getName());
						textField[2].setText(String.valueOf(student.getAsmScore()));
						textField[3].setText(String.valueOf(student.getJavaScore()));
						textField[4].setText(String.valueOf(student.getNetScore()));
						textField[5].setText(String.valueOf(student.getOsScore()));
					}
				}
				if (!flag) {
					/*
					 * ���������ϵͳ�в�����ָ��ѧ���� ����ʾ�� ����������ı���
					 */
					JOptionPane.showMessageDialog(currentContainer, s, "����", JOptionPane.ERROR_MESSAGE);
					clearForm(textField);
				}
			}
		});
		/* �޸���Ϣ��ť */
		JButton modifyBtn = new JButton("�޸���Ϣ");
		panel.add(modifyBtn);
		modifyBtn.setBounds(140, 340, 105, 30);
		/* Ϊ�޸���Ϣ��ťע��������� ʹ�������޸Ĳ��� */
		modifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(currentContainer, "ȷ��Ҫ�޸���Ϣ�� ", "�޸�ȷ��",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.NO_OPTION)
					return;
				boolean flag = true;
				String s = "";
				/* ���ѧ���Ƿ�Ϊ�� */
				if (!checkString(textField[0].getText())) {
					flag = false;
					s = "ѧ�Ų���Ϊ�գ� \n";
				}
				if (flag) {
					/*
					 * ��ѯϵͳ���Ƿ���ָ��ѧ�ŵ�ѧ����Ϣ
					 */
					Student student = selectStudent(textField[0].getText().trim());
					if (student == null) {
						flag = false;
						s = "ϵͳ�в����ڸ�ѧ�ŵ���Ϣ�� \n";
					} else {
						/*
						 * ���ı����е����ݷ�װ������ �õĶ�����
						 */
						student.setName(textField[1].getText().trim());
						student.setAsmScore(Integer.parseInt(textField[2].getText().trim()));
						student.setJavaScore(Integer.parseInt(textField[3].getText().trim()));
						student.setNetScore(Integer.parseInt(textField[4].getText().trim()));
						student.setOsScore(Integer.parseInt(textField[5].getText().trim()));
						/* �޸���Ϣ */
						flag = modifyData(student);
					}
				}
				if (flag) {
					/* �ɹ��޸�������ı��� */
					JOptionPane.showMessageDialog(currentContainer, "�޸ĳɹ��� ", "�޸ĳɹ�", JOptionPane.PLAIN_MESSAGE);
					clearForm(textField);
				} else {
					JOptionPane.showMessageDialog(currentContainer, s, "�޸�ʧ��", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/* ��հ�ť */
		JButton clearBtn = new JButton("���");
		panel.add(clearBtn);
		clearBtn.setBounds(280, 340, 60, 30);
		/* Ϊ��հ�ťע��������� ʹ��������ղ��� */
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm(textField);
			}
		});
		panel.setVisible(true);
		return panel;
	}
	
	/**
	 * ������Ϣ
	 */
	private void loadData() {
		/* ��ȡ����ѧ�������� */
		ArrayList<Student> dataList = displayDataList();
		/* ���µ���� */
		dataModel.update(dataList);
		/* ֪ͨ������ݸı� */
		dataModel.fireTableDataChanged();
		/* ���±����ʾ */
		dataTable.updateUI();
	}

	/**
	* ֻ��ʾ��ѯ��ѧ�ŵ�ѧ����Ϣ
	*/
	private void loadStudentInfo(String no){
		
	/* ��ȡָ��ѧ�ŵ�ѧ�����ݸ��µ���� */
	dataModel.update(selectStudent(no));
	/* ֪ͨ������ݸı� */
	dataModel.fireTableDataChanged();
	/* ���±����ʾ */
	dataTable.updateUI();
	}
	
	/**
	 * ��������
	 * 
	 * @param student Ҫ�����ѧ����Ϣ
	 * @return �Ƿ����ɹ�
	 */
	private boolean insertData(Student student) {
		try {
			/* ����Ҫ������ַ��� */
			String result = student.getNo() + "," + student.getName() + "," + student.getAsmScore() + ","
					+ student.getJavaScore() + "," + student.getNetScore() + "," + student.getOsScore() + "\r\n";
			/* ����д�ļ����� ָ��д�뷽ʽΪ׷�ӵ��ļ�ĩβ */
			FileWriter fw = new FileWriter(data, true);
			/* д������ */
			fw.write(result);
			/* �رն��� */
			fw.close();
			return true;
		} catch (Exception e) {
			/* �����쳣������ʧ�ܣ� ���� false */
			return false;
		}
	}

	/**
	 * �޸�����
	 * 
	 * @param student Ҫ�޸ĵ�ѧ����Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	private boolean modifyData(Student student) {
		try {
			/* ������������������ �򿪶�����ļ� */
			BufferedReader br = new BufferedReader(new FileReader(data));
			String s, result = "";
			/* �����ļ�����λ�ã� �߶���ÿ�б�д�� */
			while ((s = br.readLine()) != null) {
				/* �ָ�Ϊ�������ݾ����ֵ */
				String[] temp = s.split(",");
				/*
				 * �ҵ�ָ����ѧ��ʱ�� �޸�Ҫ��������� Ϊ���� ������
				 */
				if (student.getNo().equals(temp[0])) {
					result = result + student.getNo() + "," + student.getName() + "," + student.getAsmScore() + ","
							+ student.getJavaScore() + "," + student.getNetScore() + "," + student.getOsScore()
							+ "\r\n";
				} else {
					/* ����ԭ������������ */
					result = result + s + "\r\n";
				}
			}
			/* �ر������� */
			br.close();
			/* ������������� */
			FileWriter fw = new FileWriter(data);
			/* ���������� */
			fw.write(result);
			/* �ر������ */
			fw.close();
			return true;
		} catch (Exception e) {
			/* �����쳣������ʧ�ܣ� ���� false */
			return false;
		}
	}

	/**
	 * ɾ������
	 * 
	 * @param no ѧ��
	 * @return �Ƿ��޸ĳɹ�
	 */
	private boolean deleteData(String no) {
		try {
			/* ������������������ �򿪶�����ļ� */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String s, result = "";
			/* �����ļ�����λ�ã� �߶���ÿ�б�д�� */
			while ((s = reader.readLine()) != null) {
				/* �ָ�Ϊ�������ݾ����ֵ */
				String[] temp = s.split(",");
				/*
				 * �ҵ�ָ����ѧ��ʱ�� ���������ݣ� ֱ��������һ��
				 */
				if (no.equals(temp[0])) {
					continue;
				}
				/* ����ָ����ѧ��ʱ����ԭ������ */
				result = result + s + "\r\n";
			}
			/* �ر������� */
			reader.close();
			/* �����ļ���������� */
			FileWriter fw = new FileWriter(data);
			/* ���������� */
			fw.write(result);
			/* �ر������ */
			fw.close();
			return true;
		} catch (Exception e) {
			/* �����쳣������ʧ�ܣ� ���� false */
			return false;
		}
	}

	/**
	 * ��ѯѧ���Ƿ��Ѿ���ռ��
	 * 
	 * @param no ѧ��
	 * @return true - ѧ��δ��ռ��; false - ѧ���ѱ�ռ��
	 */
	private boolean checkStudentNo(String no) {
		try {
			boolean flag = true;
			/* ������������������ �򿪶�����ļ� */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String s;
			/* �����ļ�����λ�ã� �߶���ÿ�б�д�� */
			while ((s = reader.readLine()) != null) {
				/* �ҵ�ָ����ѧ��ʱ��ǣ� ���˳�ѭ�� */
				if (no.equals(s.split(",")[0])) {
					flag = false;
					break;
				}
			}
			/* �ر������� */
			reader.close();
			return flag;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ���������ı���Ϣ�Ƿ�Ϊ�� * @param text �������Ϣ����
	 * 
	 * @return ������ı���Ϣ�Ƿ�Ϊ��
	 */
	private boolean checkString(String text) {
		/* ��������ı�ȥ��ǰ��ո񳤶�Ϊ 0 ʱ���� false */
		if (text.trim().length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * ��������������Ϣ�Ƿ�������
	 * 
	 * @param text �������Ϣ����
	 * @return �����������Ϣ�Ƿ�������
	 */
	private boolean checkInteger(String text) {
		/* ��������ı�ȥ��ǰ��ո񳤶�Ϊ 0 ʱ���� false */
		if (text.trim().length() == 0) {
			return false;
		}
		try {
			Integer.parseInt(text);
		} catch (Exception e) {
			/* �޷�ת��Ϊ����ʱ���� false */
			return false;
		}
		return true;
	}

	/**
	 * ��ձ���Ϣ
	 * 
	 * @param textField ������
	 */
	private void clearForm(JTextField[] textField) {
		/* ��մ������ı������������ */
		for (int i = 0; i < textField.length; i++) {
			textField[i].setText("");
		}
	}

	/**
	 * ��ȡָ��ѧ�ŵ�ѧ����Ϣ
	 * 
	 * @param no ѧ��ѧ��
	 * @return ѧ���ɼ���Ϣ
	 */
	private Student selectStudent(String no) {
		try {
			/* ������������������ �򿪶�����ļ� */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			Student student = null;
			String s;
			/* �����ļ�����λ�ã� �߶���ÿ�б�д�� */
			while ((s = reader.readLine()) != null) {
				/* �ָ�Ϊ�������ݾ����ֵ */
				String temp[] = s.split(",");
				/*
				 * �ҵ�ָ����ѧ��ʱ��ѧ����Ϣ��װ��ָ�������У� ���˳�ѭ��
				 */
				if (no.equals(temp[0])) {
					student = new Student();
					student.setNo(temp[0]);
					student.setName(temp[1]);
					student.setAsmScore(Integer.parseInt(temp[2]));
					student.setJavaScore(Integer.parseInt(temp[3]));
					student.setNetScore(Integer.parseInt(temp[4]));
					student.setOsScore(Integer.parseInt(temp[5]));
					break;
				}
			}
			/* �ر������� */
			reader.close();
			return student;
		} catch (Exception e) {
			/* �����쳣������ʧ�ܣ� ���� null */
			return null;
		}
	}

	/**
	 * ��ȡ����ѧ������
	 * 
	 * @return ѧ����������
	 */
	private ArrayList<Student> displayDataList() {
		ArrayList<Student> dataList = new ArrayList<Student>();
		try {
			/* ������������������ �򿪶�����ļ� */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String s;
			/* �����ļ�����λ�ã� �߶���ÿ�б�д�� */
			while ((s = reader.readLine()) != null) {
				try {
					/* �ָ�Ϊ�������ݾ����ֵ */
					String[] temp = s.split(",");
					/* ��װ���� */
					Student student = new Student();
					student.setNo(temp[0]);
					student.setName(temp[1]);
					student.setAsmScore(Integer.parseInt(temp[2]));
					student.setJavaScore(Integer.parseInt(temp[3]));
					student.setNetScore(Integer.parseInt(temp[4]));
					student.setOsScore(Integer.parseInt(temp[5]));
					/* ��������뵽���������� */
					dataList.add(student);
				} catch (Exception e) {
					/*
					 * �м������쳣�����¼����һ�����ݵ� ����
					 */
					continue;
				}
			}
		} catch (Exception e) {
		}
		return dataList;
	}

	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		new ScoreMSFrm();
	}
}
