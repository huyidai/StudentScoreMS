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
	private Container currentContainer = this;//当前窗体
	private String title = "学生成绩管理系统";//窗体标题
	private Font headFont = new Font("黑体", Font.PLAIN, 18);//标题字体
	private File data = new File("data.txt");//数据文件
	private DataTable dataModel = new DataTable();//数据模型
	private JTable dataTable = new JTable(dataModel);//数据表格
	private JPanel welcomePanel = createWelcomePanel();//欢迎面板
	private JPanel insertPanel = createInsertPanel();//添加信息面板
	private JPanel displayPanel = createDisplayPanel();//显示信息面板
	private JPanel modifyPanel = createModifyPanel();//修改信息面板
	
	/**
	 * 构造器： 初始化窗体
	 */
	public ScoreMSFrm() {
		/* 设置窗体基本信息 */
		this.setLocation(300, 300);
		this.setSize(475, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle(title);
		this.createMenu();
		this.add(welcomePanel);
		/* 如果数据文件不存在则默认创建一个 */
		if (!data.exists()) {
			try {
				new FileWriter(data);
			} catch (IOException e) {
			}
		}
		
		/* 显示窗体 */
		this.setVisible(true);
	}

	/**
	 * 创建菜单栏
	 */
	private void createMenu() {
		JMenuBar menubar = new JMenuBar();
		/* 建立菜单并添加到菜单栏中 */
		JMenu manageMenu = new JMenu("管理");
		menubar.add(manageMenu);
		JMenu helpMenu = new JMenu("帮助");
		menubar.add(helpMenu);
		/* 建立菜单项并添加到菜单中 */
		JMenuItem m_add = new JMenuItem("添加");
		JMenuItem m_manage = new JMenuItem("查看");
		JMenuItem m_modify = new JMenuItem("修改");
		JMenuItem m_exit = new JMenuItem("退出");
		manageMenu.add(m_add);
		manageMenu.add(m_modify);
		manageMenu.add(m_manage);
		manageMenu.addSeparator();
		manageMenu.add(m_exit);
		JMenuItem m_help = new JMenuItem("说明");
		JMenuItem m_copy = new JMenuItem("作者");
		helpMenu.add(m_help);
		helpMenu.addSeparator();
		helpMenu.add(m_copy);
		/* 为添加菜单项注册监听器， 使点击该菜单项时添加学生成绩数据 */
		m_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel(insertPanel);
			}
		});
		/* 为修改菜单项注册监听器， 使点击该菜单项时修改学生成绩数据 */
		m_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel(modifyPanel);
			}
		});
		/* 为管理菜单项注册监听器， 使点击该菜单项时显示学生成绩列表 */
		m_manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetPanel(displayPanel);
			}
		});
		/* 为退出菜单项注册监听器， 使点击该菜单项时退出程序 */
		m_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		/* 为说明菜单项注册监听器， 使点击该菜单项时弹出程序说明窗口 */
		m_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(currentContainer, "学生成绩管理系统的对象是在校学生， 成绩管理系统为学生提供了成绩查询等\r\n" + 
						"服务； 为学校及老师提供了添加、 修改、 删除学生成绩等服务。……", "帮助信息", JOptionPane.PLAIN_MESSAGE);
			}
		});
		/* 为版权菜单项注册监听器， 使点击该菜单项时弹出版权信息窗口 */
		m_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(currentContainer, "作者： \n" + "2019401353 huyidai", "作者信息",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		/* 设置菜单栏 */
		this.setJMenuBar(menubar);
	}

	/**
	 * 重设面板
	 */
	private void resetPanel(JPanel panel) {
		this.setVisible(false);
		this.getContentPane().removeAll();
		/* 如果是要切换到显示信息面板， 则同时加载信息 */
		if (panel == displayPanel) {
			loadData();
		}
		this.getContentPane().add(panel);
		this.setVisible(true);
	}

	/**
	 * 创建欢迎面板
	 */
	private JPanel createWelcomePanel() {
		JPanel panel = new JPanel();
		/* 标题文本 */
		JLabel title = new JLabel("学生成绩管理系统 v1.0");
		title.setFont(headFont);
		panel.add(title);
		panel.setVisible(true);
		return panel;
	}

	/**
	 * 创建添加信息面板
	 * 
	 * @return 添加信息面板
	 */
	private JPanel createInsertPanel() {
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
		/*
		 * 为学号按钮注册监听器， 使移开焦点时自动检测键入的学号是否存在
		 */
		textField[0].addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			/**
			 * 当焦点缺失时
			 */
			public void focusLost(FocusEvent e) {
				boolean flag = true;
				String s = "";
				/* 检测学号是否输入 */
				if (!checkString(textField[0].getText())) {
					flag = false;
					s = "学号不能为空！ \n";
				}
				if (flag) {
					/* 检查学号是否已存在 */
					if (!checkStudentNo(textField[0].getText().trim())) {
						flag = false;
						s = "系统已存在该学号的信息！ \n";
					}
				}
				if (!flag) {
					/*
					 * 输入有误或系统中不存在指定学号则弹 出提示
					 */
					JOptionPane.showMessageDialog(currentContainer, s, "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/* 录入按钮 */
		JButton addBtn = new JButton("录入信息");
		panel.add(addBtn);
		addBtn.setBounds(140, 340, 105, 30);
		/* 为录入按钮注册监听器， 使点击完成录入操作 */
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(currentContainer, "确认要录入信息吗？ ", "录入确认",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.NO_OPTION)
					return;
				boolean flag = true;
				String s = "录入过程中发生了错误！ \n"; // 预定义的错误信息
				/* 检查学号和姓名是否未填入 */
				for (int i = 0; i < 2; i++) {
					if (!checkString(textField[i].getText())) {
						flag = false;
						s = s + label[i] + "未填写！ \n";
					}
				}
				/*
				 * 检查各个成绩是否未填写或输入不是整数
				 */
				for (int i = 2; i < label.length; i++) {
					if (!checkInteger(textField[i].getText())) {
						flag = false;
						s = s + label[i] + "成绩未填写或输入不是整数！ \n";
					}
				}
				if (flag) {
					Student student = new Student();
					student.setNo(textField[0].getText().trim());
					/* 检查学号是否已存在 */
					if (!checkStudentNo(student.getNo())) {
						flag = false;
						s = s + "系统中已存在相同学号的信息！\n";
					} else {
						/*
						 * 封装文本框中的内容到用来传参的对象中
						 */
						student.setName(textField[1].getText().trim());
						student.setAsmScore(Integer.parseInt(textField[2].getText().trim()));
						student.setJavaScore(Integer.parseInt(textField[3].getText().trim()));
						student.setNetScore(Integer.parseInt(textField[4].getText().trim()));
						student.setOsScore(Integer.parseInt(textField[5].getText().trim()));
						/* 录入信息 */
						flag = insertData(student);
					}
				}
				if (flag) {
					JOptionPane.showMessageDialog(currentContainer, "录入成功！ ", "录入成功", JOptionPane.PLAIN_MESSAGE);
					clearForm(textField); // 录入成功则清空所有文本框内容
				} else {
					JOptionPane.showMessageDialog(currentContainer, s, "录入失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/* 清空按钮 */
		JButton clearBtn = new JButton("清空");
		panel.add(clearBtn);
		clearBtn.setBounds(280, 340, 60, 30);
		/* 为清空按钮注册监听器， 使点击调用清空所有文本框方法 */
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm(textField);
			}
		});
		panel.setVisible(true);
		return panel;
	}
	/**
	 * 创建显示信息面板
	 * 
	 * @return 显示信息面板
	 */
	private JPanel createDisplayPanel() {
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
		/*
		 * 为查询按钮注册监听器， 点击则查询该学号学生的信息并更新到表格中
		 */
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String no = textField.getText();
				/* 检查学号是否输入 */
				if (!checkString(no)) {
					JOptionPane.showMessageDialog(currentContainer, "未输入学号！ ", "查询失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* 检查是否有该学号的记录 */
				if (checkStudentNo(no)) {
					JOptionPane.showMessageDialog(currentContainer, "不存在该学号的学生信息！ ", "查询失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* 更新指定学号的学生信息到表格 */
				loadStudentInfo(no);
			}
		});
		/* 删除按钮 */
		JButton deleteBtn = new JButton("删除");
		panel.add(deleteBtn);
		/* 为删除按钮注册监听器， 点击则删除该学号学生的信息 */
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String no = textField.getText();
				/* 检查学号是否输入 */
				if (!checkString(no)) {
					JOptionPane.showMessageDialog(currentContainer, "未输入学号！ ", "删除失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* 检查是否有该学号的记录 */
				if (checkStudentNo(no)) {
					JOptionPane.showMessageDialog(currentContainer, "不存在该学号的学生信息！ ", "删除失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/* 弹出对话框确认是否要删除 */

				int choose = JOptionPane.showConfirmDialog(currentContainer, "确定要删除学号为" + no + "的学生的信息吗？", "删除确认",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.NO_OPTION)
					return;
				/* 执行删除方法 */
				boolean flag = deleteData(no);
				if (!flag) {
					JOptionPane.showMessageDialog(currentContainer, "删除过程中发生了错误！ ", "删除失败", JOptionPane.ERROR_MESSAGE);
					return;
				}
				/*
				 * 删除成功则跳转到显示面板， 并清空学号文本框
				 */
				resetPanel(displayPanel);
				JOptionPane.showMessageDialog(currentContainer, "已经删除了该学号学生的信息！ ", "删除成功", JOptionPane.PLAIN_MESSAGE);
				textField.setText("");
			}
		});
		/* 显示所有信息按钮 */
		JButton allBtn = new JButton("查看所有信息");
		panel.add(allBtn);
		/*
		 * 为显示所有信息按钮注册监听器， 使点击更新表格数据为全部学生的 信息
		 */
		allBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(""); // 清空学号文本框
				loadData(); // 加载表格信息
			}
		});
		
		/**
		 * 将文件中的数据添加到表格
		 */
		//每一列表头
		Vector<String> vector = new Vector<String>();
		vector.addElement("学号");
		vector.addElement("姓名");
		vector.addElement("汇编程序设计");
		vector.addElement("Java程序设计");
		vector.addElement("计算机网络");
		vector.addElement("操作系统");
		//声明所有行
		Vector<Vector> rowData = new Vector<Vector>();
		//开始读文本文件
		FileReader fr;
			try {
				fr = new FileReader("data.txt");
				BufferedReader br = new BufferedReader(fr);
				//定义每一行
				String ch = null;
				try {
					while ((ch=br.readLine())!=null) {//读文件至末尾
						//split(String):根据给定正则表达式的匹配拆分此字符串。
						String[] temp = ch.split(",");//每一行里的空格
						Vector<String> row = new Vector<String>();
						for(int i=0;i<temp.length;i++) {//遍历每一行
							row.add(temp[i]);//把每一行都加入row
						}
						rowData.add(row);//再把每一个row的数据给rowData
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
	
		/* 数据表格初始化参数 */
		dataTable.setAutoscrolls(true); // 设置表格自动滚动
		dataTable.setPreferredScrollableViewportSize(new Dimension(420, 300)); // 设置表格大小
		/* 添加数据表格 */
		panel.add(new JScrollPane(dataTable));
		return panel;
	}

	/**
	 * 创建修改信息面板
	 * 
	 * @return 修改信息面板
	 */
	private JPanel createModifyPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setFocusable(true);
		/* 标题文本 */
		JLabel title = new JLabel("修改学生成绩");
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
		/*
		 * 为学号文本框增加监听器:当焦点移开时读取该文本框中的学号信息
		 */
		textField[0].addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			/**
			 * 当焦点缺失时
			 */
			public void focusLost(FocusEvent e) {
				boolean flag = true;
				String s = "";
				/* 检测学号是否输入 */
				if (!checkString(textField[0].getText())) {
					flag = false;
					s = "学号不能为空！ \n";
				}
				if (flag) {
					/*
					 * 检测系统中是否有该学号的学生信息
					 */
					Student student = selectStudent(textField[0].getText().trim());
					if (student == null) {
						flag = false;
						s = "系统中不存在该学号的信息！ \n";
					} else {
						/* 更新到文本框 */
						textField[1].setText(student.getName());
						textField[2].setText(String.valueOf(student.getAsmScore()));
						textField[3].setText(String.valueOf(student.getJavaScore()));
						textField[4].setText(String.valueOf(student.getNetScore()));
						textField[5].setText(String.valueOf(student.getOsScore()));
					}
				}
				if (!flag) {
					/*
					 * 输入有误或系统中不存在指定学号则弹 出提示， 并清空所有文本框
					 */
					JOptionPane.showMessageDialog(currentContainer, s, "错误", JOptionPane.ERROR_MESSAGE);
					clearForm(textField);
				}
			}
		});
		/* 修改信息按钮 */
		JButton modifyBtn = new JButton("修改信息");
		panel.add(modifyBtn);
		modifyBtn.setBounds(140, 340, 105, 30);
		/* 为修改信息按钮注册监听器， 使点击完成修改操作 */
		modifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(currentContainer, "确认要修改信息吗？ ", "修改确认",
						JOptionPane.YES_NO_OPTION);
				if (choose == JOptionPane.NO_OPTION)
					return;
				boolean flag = true;
				String s = "";
				/* 检测学号是否为空 */
				if (!checkString(textField[0].getText())) {
					flag = false;
					s = "学号不能为空！ \n";
				}
				if (flag) {
					/*
					 * 查询系统中是否有指定学号的学生信息
					 */
					Student student = selectStudent(textField[0].getText().trim());
					if (student == null) {
						flag = false;
						s = "系统中不存在该学号的信息！ \n";
					} else {
						/*
						 * 将文本框中的内容封装到传参 用的对象中
						 */
						student.setName(textField[1].getText().trim());
						student.setAsmScore(Integer.parseInt(textField[2].getText().trim()));
						student.setJavaScore(Integer.parseInt(textField[3].getText().trim()));
						student.setNetScore(Integer.parseInt(textField[4].getText().trim()));
						student.setOsScore(Integer.parseInt(textField[5].getText().trim()));
						/* 修改信息 */
						flag = modifyData(student);
					}
				}
				if (flag) {
					/* 成功修改则清空文本框 */
					JOptionPane.showMessageDialog(currentContainer, "修改成功！ ", "修改成功", JOptionPane.PLAIN_MESSAGE);
					clearForm(textField);
				} else {
					JOptionPane.showMessageDialog(currentContainer, s, "修改失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/* 清空按钮 */
		JButton clearBtn = new JButton("清空");
		panel.add(clearBtn);
		clearBtn.setBounds(280, 340, 60, 30);
		/* 为清空按钮注册监听器， 使点击完成清空操作 */
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm(textField);
			}
		});
		panel.setVisible(true);
		return panel;
	}
	
	/**
	 * 加载信息
	 */
	private void loadData() {
		/* 读取所有学生的数据 */
		ArrayList<Student> dataList = displayDataList();
		/* 更新到表格 */
		dataModel.update(dataList);
		/* 通知表格数据改变 */
		dataModel.fireTableDataChanged();
		/* 更新表格显示 */
		dataTable.updateUI();
	}

	/**
	* 只显示查询的学号的学生信息
	*/
	private void loadStudentInfo(String no){
		
	/* 读取指定学号的学生数据更新到表格 */
	dataModel.update(selectStudent(no));
	/* 通知表格数据改变 */
	dataModel.fireTableDataChanged();
	/* 更新表格显示 */
	dataTable.updateUI();
	}
	
	/**
	 * 插入数据
	 * 
	 * @param student 要插入的学生信息
	 * @return 是否插入成功
	 */
	private boolean insertData(Student student) {
		try {
			/* 连接要插入的字符串 */
			String result = student.getNo() + "," + student.getName() + "," + student.getAsmScore() + ","
					+ student.getJavaScore() + "," + student.getNetScore() + "," + student.getOsScore() + "\r\n";
			/* 创建写文件对象， 指定写入方式为追加到文件末尾 */
			FileWriter fw = new FileWriter(data, true);
			/* 写入数据 */
			fw.write(result);
			/* 关闭对象 */
			fw.close();
			return true;
		} catch (Exception e) {
			/* 遇到异常即操作失败， 返回 false */
			return false;
		}
	}

	/**
	 * 修改数据
	 * 
	 * @param student 要修改的学生信息
	 * @return 是否修改成功
	 */
	private boolean modifyData(Student student) {
		try {
			/* 创建缓冲输入流对象， 打开定义的文件 */
			BufferedReader br = new BufferedReader(new FileReader(data));
			String s, result = "";
			/* 读到文件结束位置， 边读入每行边写入 */
			while ((s = br.readLine()) != null) {
				/* 分隔为各个数据具体的值 */
				String[] temp = s.split(",");
				/*
				 * 找到指定的学号时， 修改要插入的数据 为输入 的内容
				 */
				if (student.getNo().equals(temp[0])) {
					result = result + student.getNo() + "," + student.getName() + "," + student.getAsmScore() + ","
							+ student.getJavaScore() + "," + student.getNetScore() + "," + student.getOsScore()
							+ "\r\n";
				} else {
					/* 否则按原来的内容输入 */
					result = result + s + "\r\n";
				}
			}
			/* 关闭输入流 */
			br.close();
			/* 创建输出流对象 */
			FileWriter fw = new FileWriter(data);
			/* 输出结果数据 */
			fw.write(result);
			/* 关闭输出流 */
			fw.close();
			return true;
		} catch (Exception e) {
			/* 遇到异常即操作失败， 返回 false */
			return false;
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param no 学号
	 * @return 是否修改成功
	 */
	private boolean deleteData(String no) {
		try {
			/* 创建缓冲输入流对象， 打开定义的文件 */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String s, result = "";
			/* 读到文件结束位置， 边读入每行边写入 */
			while ((s = reader.readLine()) != null) {
				/* 分隔为各个数据具体的值 */
				String[] temp = s.split(",");
				/*
				 * 找到指定的学号时， 不插入数据， 直接跳到下一行
				 */
				if (no.equals(temp[0])) {
					continue;
				}
				/* 并非指定的学号时则照原样插入 */
				result = result + s + "\r\n";
			}
			/* 关闭输入流 */
			reader.close();
			/* 创建文件输出流对象 */
			FileWriter fw = new FileWriter(data);
			/* 输出结果数据 */
			fw.write(result);
			/* 关闭输出流 */
			fw.close();
			return true;
		} catch (Exception e) {
			/* 遇到异常即操作失败， 返回 false */
			return false;
		}
	}

	/**
	 * 查询学号是否已经被占用
	 * 
	 * @param no 学号
	 * @return true - 学号未被占用; false - 学号已被占用
	 */
	private boolean checkStudentNo(String no) {
		try {
			boolean flag = true;
			/* 创建缓冲输入流对象， 打开定义的文件 */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String s;
			/* 读到文件结束位置， 边读入每行边写入 */
			while ((s = reader.readLine()) != null) {
				/* 找到指定的学号时标记， 并退出循环 */
				if (no.equals(s.split(",")[0])) {
					flag = false;
					break;
				}
			}
			/* 关闭输入流 */
			reader.close();
			return flag;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查输入的文本信息是否为空 * @param text 输入的信息内容
	 * 
	 * @return 输入的文本信息是否为空
	 */
	private boolean checkString(String text) {
		/* 当输入的文本去掉前后空格长度为 0 时返回 false */
		if (text.trim().length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 检查输入的数字信息是否是整数
	 * 
	 * @param text 输入的信息内容
	 * @return 输入的数字信息是否是整数
	 */
	private boolean checkInteger(String text) {
		/* 当输入的文本去掉前后空格长度为 0 时返回 false */
		if (text.trim().length() == 0) {
			return false;
		}
		try {
			Integer.parseInt(text);
		} catch (Exception e) {
			/* 无法转换为整数时返回 false */
			return false;
		}
		return true;
	}

	/**
	 * 清空表单信息
	 * 
	 * @param textField 表单数组
	 */
	private void clearForm(JTextField[] textField) {
		/* 清空传来的文本框数组的内容 */
		for (int i = 0; i < textField.length; i++) {
			textField[i].setText("");
		}
	}

	/**
	 * 获取指定学号的学生信息
	 * 
	 * @param no 学生学号
	 * @return 学生成绩信息
	 */
	private Student selectStudent(String no) {
		try {
			/* 创建缓冲输入流对象， 打开定义的文件 */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			Student student = null;
			String s;
			/* 读到文件结束位置， 边读入每行边写入 */
			while ((s = reader.readLine()) != null) {
				/* 分隔为各个数据具体的值 */
				String temp[] = s.split(",");
				/*
				 * 找到指定的学号时将学生信息封装到指定对象中， 并退出循环
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
			/* 关闭输入流 */
			reader.close();
			return student;
		} catch (Exception e) {
			/* 遇到异常即操作失败， 返回 null */
			return null;
		}
	}

	/**
	 * 获取所有学生数据
	 * 
	 * @return 学生数据数组
	 */
	private ArrayList<Student> displayDataList() {
		ArrayList<Student> dataList = new ArrayList<Student>();
		try {
			/* 创建缓冲输入流对象， 打开定义的文件 */
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String s;
			/* 读到文件结束位置， 边读入每行边写入 */
			while ((s = reader.readLine()) != null) {
				try {
					/* 分隔为各个数据具体的值 */
					String[] temp = s.split(",");
					/* 封装数据 */
					Student student = new Student();
					student.setNo(temp[0]);
					student.setName(temp[1]);
					student.setAsmScore(Integer.parseInt(temp[2]));
					student.setJavaScore(Integer.parseInt(temp[3]));
					student.setNetScore(Integer.parseInt(temp[4]));
					student.setOsScore(Integer.parseInt(temp[5]));
					/* 将对象加入到对象数组中 */
					dataList.add(student);
				} catch (Exception e) {
					/*
					 * 中间遇到异常则继续录入下一行数据的 内容
					 */
					continue;
				}
			}
		} catch (Exception e) {
		}
		return dataList;
	}

	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		new ScoreMSFrm();
	}
}
