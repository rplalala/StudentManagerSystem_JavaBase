package com.ding.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ChoiceUI extends JFrame {

    /**
     * 串行版本标识
     */
    private static final long serialVersionUID = -5608502845293350469L;
    private JPanel contentPane;

    public ChoiceUI() {
        //frame
        setTitle("学生管理系统 by Ding");
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.jpg"));
        setSize(430, 654);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//不执行任何操作，只在WindowListener中的windowClosing方法中处理该操作
        //设置窗口关闭监听
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int judge = JOptionPane.showConfirmDialog(null, "您确定要退出吗？", "退出", JOptionPane.YES_NO_OPTION);
                if (judge == 0) {
                    System.exit(1);
                }
            }
        });

        //panel
        contentPane = new JPanel() {
            /**
             * 串行版本标识
             */
            private static final long serialVersionUID = 1537109021718465086L;

            //设置面板背景图片
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon ii = new ImageIcon("images/choiceBackground.png");
                g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //label
        JLabel markedWordsLabel = new JLabel();
        markedWordsLabel.setText("欢迎您，主管理员admin");
        markedWordsLabel.setFont(new Font("方正粗黑宋简体", Font.BOLD, 32));
        markedWordsLabel.setForeground(Color.RED);
        markedWordsLabel.setBounds(10, 59, 396, 108);
        markedWordsLabel.setHorizontalAlignment(SwingConstants.CENTER);//根据Bounds设置标签居中(将label拉到和窗口同宽，在设置CENTER即可居中)
        contentPane.add(markedWordsLabel);

        //button
        JButton studentManagerButton = new JButton("学生操作");
        studentManagerButton.setFont(new Font("宋体", Font.BOLD, 20));
        studentManagerButton.setBounds(139, 232, 137, 33);
        contentPane.add(studentManagerButton);
        //学生操作按钮监听
        studentManagerButton.addActionListener(e -> {
            setVisible(false);
            new StudentMainUI().setIsAdmin(true);////是主管理员进入，isAdmin为true
        });

        //AdminButton
        JButton adminManagerButton = new JButton("管理操作");
        adminManagerButton.setFont(new Font("宋体", Font.BOLD, 20));
        adminManagerButton.setBounds(139, 344, 137, 33);
        contentPane.add(adminManagerButton);
        //管理员按钮监听
        adminManagerButton.addActionListener(e -> {
            setVisible(false);
            new AdminMainUI();
        });

    }
}
