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

public class AdminUpdateUI extends JFrame {

    /**
     * 串行版本标识
     */
    private static final long serialVersionUID = 8561832120991287956L;

    private JPanel contentPane;
    private JTextField userNameText;
    private JTextField userPasswordText;

    private AdminDaoImpl adminDaoImpl = new AdminDaoImpl();
    private RegExpHelp regExpHelp = new RegExpHelp();

    public AdminUpdateUI() {
    }

    //admin 为需要进行修改的 管理员的信息
    public AdminUpdateUI(AdminEntity admin) {
        //frame
        setTitle("修改界面");
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
        JButton updateButton = new JButton("修 改");
        updateButton.setFont(new Font("宋体", Font.BOLD, 20));
        updateButton.setBounds(69, 168, 106, 36);
        contentPane.add(updateButton);

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

        //修改按钮监听
        updateButton.addActionListener(e -> {
            if (e.getSource() == updateButton) {
                boolean regexpUserName = true;        //正则表达式检验用户名
                boolean regexpUserPassword = true;    //正则表达式检验密码

                AdminEntity updateAdminEntity = new AdminEntity();//对该学生进行修改的信息
                String userName = userNameText.getText();
                String userPassword = userPasswordText.getText();
                if ("".equals(userName) || userName == null) {
                    updateAdminEntity.setUserName(admin.getUserName());//若没有输入修改信息，则默认和修改前的信息一致
                } else {
                    regexpUserName = regExpHelp.checkUserNameAndUserPassword(userName);
                    if (regexpUserName) {
                        updateAdminEntity.setUserName(userName);//若有输入修改信息，且满足正则，则把输入的修改信息传入
                    }
                }

                if ("".equals(userPassword) || userPassword == null) {
                    updateAdminEntity.setUserPassword(admin.getUserPassword());//若没有输入修改信息，则默认和修改前的信息一致
                } else {
                    regexpUserPassword = regExpHelp.checkUserNameAndUserPassword(userPassword);
                    if (regexpUserPassword) {
                        updateAdminEntity.setUserPassword(userPassword);//若有输入修改信息，且满足正则，则把输入的修改信息传入
                    }
                }

                if (regexpUserName && regexpUserPassword) {
                    int n = JOptionPane.showConfirmDialog(null, "您确定要修改吗？", "确认修改",
                            JOptionPane.YES_NO_OPTION);
                    if (n == 0) {
                        boolean judge = adminDaoImpl.updateAdmin(updateAdminEntity, admin.getUserName());
                        if (judge) {
                            JOptionPane.showMessageDialog(null, "修改成功！");
                            setVisible(false);//关闭（隐藏）修改页面；

                        } else {
                            JOptionPane.showMessageDialog(null, "修改失败！", "失败",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
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
