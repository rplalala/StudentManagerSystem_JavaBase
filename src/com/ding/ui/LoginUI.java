package com.ding.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import com.ding.dao.impl.AdminDaoImpl;
import com.ding.entity.AdminEntity;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.SwingConstants;

public class LoginUI extends JFrame {
	/**
	 * 串行版本标识
	 */
	private static final long serialVersionUID = 7866480656166930724L;
	private JPanel contentPane;
	private JTextField userNameText;
	private JPasswordField userPasswordText;
	
	private AdminDaoImpl adminDaoImpl = new AdminDaoImpl();

	/**
	 * 登录界面
	 * */
	public LoginUI() {
		
		
		//panel
		contentPane = new JPanel(){


			/**
			 * 串行版本标识
			 */
			private static final long serialVersionUID = -1768559110741843875L;

			//设置面板背景图片
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon ii = new ImageIcon("images/loginBackground.jpg");
                g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
            }
        };
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane); //将frame的面板设置为contentPane
		contentPane.setLayout(null);
		
		//label
		JLabel userNameLabel = new JLabel("用户名：");
		userNameLabel.setFont(new Font("宋体", Font.BOLD, 30));
		userNameLabel.setBounds(886, 286, 129, 51);
		contentPane.add(userNameLabel);
		
		JLabel userPasswordLabel = new JLabel("密  码：");
		userPasswordLabel.setFont(new Font("宋体", Font.BOLD, 30));
		userPasswordLabel.setBounds(886, 360, 135, 51);
		contentPane.add(userPasswordLabel);
		
		JLabel markedWordsLabel = new JLabel("管理员登录");
		markedWordsLabel.setHorizontalAlignment(SwingConstants.CENTER);//根据Bounds设置标签居中
		markedWordsLabel.setForeground(Color.RED);
		markedWordsLabel.setFont(new Font("宋体", Font.BOLD, 33));
		markedWordsLabel.setBounds(886, 209, 331, 67);
		contentPane.add(markedWordsLabel);
		
		//text
		userNameText = new JTextField();
		userNameText.setFont(new Font("宋体", Font.PLAIN, 20));
		userNameText.setBounds(1002, 290, 215, 39);
		contentPane.add(userNameText);
		userNameText.setColumns(10);
		
		//password
		userPasswordText = new JPasswordField();
		userPasswordText.setFont(new Font("宋体", Font.PLAIN, 20));
		userPasswordText.setColumns(10);
		userPasswordText.setBounds(1002, 364, 215, 39);
		contentPane.add(userPasswordText);
		
		//button
		JButton loginButton = new JButton("登录");
		loginButton.setFont(new Font("宋体", Font.BOLD, 22));
		loginButton.setBounds(931, 434, 84, 39);
		contentPane.add(loginButton);
		loginButton.addActionListener(e -> {
			if(e.getSource() == loginButton) {
				String userName = userNameText.getText();//输入的用户名
				String userPassword = String.valueOf(userPasswordText.getPassword());//输入的密码
				AdminEntity adminEntity = new AdminEntity(userName,userPassword);
				if(adminDaoImpl.isLogin(adminEntity)) {
					JOptionPane.showMessageDialog(null, "登录成功！");
					setVisible(false);
					//如果登录的是主管理员账号admin，则进入操作选择界面
					if("admin".equals(userName)) {
						new ChoiceUI();
					} else {
						new StudentMainUI().setIsAdmin(false);//不是主管理员进入，isAdmin为false
					}
				}else {
					JOptionPane.showMessageDialog(null, "用户名或密码错误！", "请重新登录",
			                JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton resetButton = new JButton("重置");
		resetButton.setFont(new Font("宋体", Font.BOLD, 22));
		resetButton.setBounds(1103, 434, 84, 39);
		contentPane.add(resetButton);
		resetButton.addActionListener(e -> {
			if(e.getSource() == resetButton) {
				userNameText.setText("");
				userPasswordText.setText("");
			}
		});
		SwingUtilities.updateComponentTreeUI(this);//刷新函数，解决按钮/文本框刷新不出来的问题。貌似写在代码的最后一行会报空指针异常？
		
		//frame
		setTitle("学生管理系统 by Ding");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.jpg"));
		setSize(1300,800);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//不执行任何操作，只在WindowListener中的windowClosing方法中处理关闭操作
		//设置窗口关闭监听
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                int judge = JOptionPane.showConfirmDialog(null, "您确定要退出吗？", "退出",JOptionPane.YES_NO_OPTION);
                if(judge == 0) {
                	System.exit(1);
                }
            }
        });
		
	}
}
