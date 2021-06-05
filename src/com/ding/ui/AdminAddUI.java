package com.ding.ui;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.ding.dao.impl.AdminDaoImpl;
import com.ding.entity.AdminEntity;
import com.ding.utils.RegExpHelp;

public class AdminAddUI extends JFrame {

    /**
     * 串行版本标识
     */
    private static final long serialVersionUID = -240441957949593746L;

    private JPanel contentPane;
    private JTextField userNameText;
    private JTextField userPasswordText;

    private AdminDaoImpl adminDaoImpl = new AdminDaoImpl();
    private RegExpHelp regExpHelp = new RegExpHelp();

    public AdminAddUI() {
        //frame
        setTitle("新增界面");
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.jpg"));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//关闭新增窗口时，只是隐藏该窗口，并不结束程序
        setSize(450, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel userNameLabel = new JLabel("用户名：");
        userNameLabel.setFont(new Font("宋体", Font.BOLD, 25));
        userNameLabel.setBounds(40, 35, 106, 52);
        contentPane.add(userNameLabel);

        JLabel userPasswordLabel = new JLabel("密  码：");
        userPasswordLabel.setFont(new Font("宋体", Font.BOLD, 25));
        userPasswordLabel.setBounds(40, 95, 106, 52);
        contentPane.add(userPasswordLabel);

        userNameText = new JTextField();
        userNameText.setFont(new Font("宋体", Font.PLAIN, 20));
        userNameText.setBounds(139, 42, 225, 36);
        contentPane.add(userNameText);
        userNameText.setColumns(10);

        userPasswordText = new JTextField();
        userPasswordText.setFont(new Font("宋体", Font.PLAIN, 20));
        userPasswordText.setColumns(10);
        userPasswordText.setBounds(139, 102, 225, 36);
        contentPane.add(userPasswordText);

        //button
        JButton addButton = new JButton("新 增");
        addButton.setFont(new Font("宋体", Font.BOLD, 20));
        addButton.setBounds(69, 168, 106, 36);
        contentPane.add(addButton);

        JButton resetButton = new JButton("重 置");
        resetButton.setFont(new Font("宋体", Font.BOLD, 20));
        resetButton.setBounds(250, 168, 106, 36);
        contentPane.add(resetButton);

        //重置按钮监听
        resetButton.addActionListener(e -> {
            if (e.getSource() == resetButton) {
                userNameText.setText("");
                userPasswordText.setText("");
            }
        });

        //新增按钮监听
        addButton.addActionListener(e -> {
            if (e.getSource() == addButton) {
                boolean inputJudge = true;
                boolean regexpUserName = true;        //正则表达式检验用户名
                boolean regexpUserPassword = true;    //正则表达式检验密码

                AdminEntity addAdminEntity = new AdminEntity();//对该学生进行新增的信息
                String userName = userNameText.getText();
                String userPassword = userPasswordText.getText();

                if ("".equals(userName) || userName == null) {
                    inputJudge = false;//若没有输入信息 ，则不执行新增操作
                } else {
                    regexpUserName = regExpHelp.checkUserNameAndUserPassword(userName);
                    if (regexpUserName) {
                        addAdminEntity.setUserName(userName);//若有输入新增信息，且满足正则，则把输入的新增信息传入
                    }
                }

                if ("".equals(userPassword) || userPassword == null) {
                    inputJudge = false;//若没有输入信息 ，则不执行新增操作
                } else {
                    regexpUserPassword = regExpHelp.checkUserNameAndUserPassword(userPassword);
                    if (regexpUserPassword) {
                        addAdminEntity.setUserPassword(userPassword);//若有输入新增信息，且满足正则，则把输入的新增信息传入
                    }
                }

                if (inputJudge && regexpUserName && regexpUserPassword) {
                    int n = JOptionPane.showConfirmDialog(null, "您确定要新增吗？", "确认新增",
                            JOptionPane.YES_NO_OPTION);
                    if (n == 0) {
                        boolean judge = adminDaoImpl.addAdmin(addAdminEntity);
                        if (judge) {
                            JOptionPane.showMessageDialog(null, "新增成功！");
                            setVisible(false);//关闭（隐藏）修改页面；

                        } else {
                            JOptionPane.showMessageDialog(null, "新增失败！", "失败",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (!inputJudge) {
                    JOptionPane.showMessageDialog(null, "您还有未输入的管理员信息！", "错误",
                            JOptionPane.ERROR_MESSAGE);

                } else if (!regexpUserName) {
                    JOptionPane.showMessageDialog(null, "用户名格式错误，请输入以字母开头，允许字母数字下划线的5-16个字节！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    userNameText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "密码格式错误，请输入以字母开头，允许字母数字下划线的5-16个字节！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    userPasswordText.setText("");
                }
            }
        });
    }
}
