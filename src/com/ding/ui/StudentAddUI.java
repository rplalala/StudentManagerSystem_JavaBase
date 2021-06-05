package com.ding.ui;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ding.dao.impl.StudentDaoImpl;
import com.ding.entity.StudentEntity;
import com.ding.utils.RegExpHelp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class StudentAddUI extends JFrame {

    /**
     * 串行版本标识
     */
    private static final long serialVersionUID = 2371912530571456029L;

    private JPanel contentPane;
    private JTextField idText;
    private JTextField nameText;
    private JTextField ageText;
    private JTextField roomText;

    private StudentDaoImpl studentDaoImpl = new StudentDaoImpl();//新增学生操作用不到pageSize，不用传pageSize
    private StudentEntity stu = new StudentEntity();
    private RegExpHelp regExpHelp = new RegExpHelp();

    public StudentAddUI() {

        //frame
        setTitle("新增界面");
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.jpg"));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//关闭新增窗口时，只是隐藏该窗口，并不结束程序
        setSize(500, 700);
        setLocationRelativeTo(null);
        setVisible(true);


        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //label
        JLabel idLabel = new JLabel("编号：");
        idLabel.setFont(new Font("宋体", Font.BOLD, 25));
        idLabel.setBounds(90, 40, 106, 52);
        contentPane.add(idLabel);

        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setFont(new Font("宋体", Font.BOLD, 25));
        nameLabel.setBounds(90, 110, 106, 52);
        contentPane.add(nameLabel);

        JLabel sexLabel = new JLabel("性别：");
        sexLabel.setFont(new Font("宋体", Font.BOLD, 25));
        sexLabel.setBounds(90, 180, 106, 52);
        contentPane.add(sexLabel);

        JLabel ageLabel = new JLabel("年龄：");
        ageLabel.setFont(new Font("宋体", Font.BOLD, 25));
        ageLabel.setBounds(90, 250, 106, 52);
        contentPane.add(ageLabel);

        JLabel classLabel = new JLabel("班级：");
        classLabel.setFont(new Font("宋体", Font.BOLD, 25));
        classLabel.setBounds(90, 320, 106, 52);
        contentPane.add(classLabel);

        JLabel floorLabel = new JLabel("楼号：");
        floorLabel.setFont(new Font("宋体", Font.BOLD, 25));
        floorLabel.setBounds(90, 390, 106, 52);
        contentPane.add(floorLabel);

        JLabel roomLabel = new JLabel("房号：");
        roomLabel.setFont(new Font("宋体", Font.BOLD, 25));
        roomLabel.setBounds(90, 460, 106, 52);
        contentPane.add(roomLabel);

        //button
        JButton addButton = new JButton("新 增");
        addButton.setFont(new Font("宋体", Font.BOLD, 20));
        addButton.setBounds(90, 540, 106, 36);
        contentPane.add(addButton);

        JButton resetButton = new JButton("重 置");
        resetButton.setFont(new Font("宋体", Font.BOLD, 20));
        resetButton.setBounds(289, 540, 106, 36);
        contentPane.add(resetButton);

        //text
        idText = new JTextField();
        idText.setFont(new Font("宋体", Font.PLAIN, 20));
        idText.setBounds(170, 45, 225, 36);
        contentPane.add(idText);
        idText.setColumns(10);

        nameText = new JTextField();
        nameText.setFont(new Font("宋体", Font.PLAIN, 20));
        nameText.setColumns(10);
        nameText.setBounds(170, 115, 225, 36);
        contentPane.add(nameText);

        ageText = new JTextField();
        ageText.setFont(new Font("宋体", Font.PLAIN, 20));
        ageText.setColumns(10);
        ageText.setBounds(170, 255, 225, 36);
        contentPane.add(ageText);

        roomText = new JTextField();
        roomText.setFont(new Font("宋体", Font.PLAIN, 20));
        roomText.setColumns(10);
        roomText.setBounds(170, 465, 225, 36);
        contentPane.add(roomText);

        //单选按钮
        JRadioButton sexBoyButton = new JRadioButton("男");
        sexBoyButton.setFont(new Font("宋体", Font.BOLD, 16));
        sexBoyButton.setBounds(170, 185, 58, 36);
        contentPane.add(sexBoyButton);

        JRadioButton sexGirlButton = new JRadioButton("女");
        sexGirlButton.setFont(new Font("宋体", Font.BOLD, 16));
        sexGirlButton.setBounds(230, 185, 58, 36);
        contentPane.add(sexGirlButton);

        //设置单选按钮组，实现单选
        ButtonGroup group = new ButtonGroup();
        group.add(sexBoyButton);
        group.add(sexGirlButton);
        sexBoyButton.setSelected(true);//设置默认选中 男

        //下拉列表
        String[] classes = studentDaoImpl.getClasses();
        JComboBox<String> classComboBox = new JComboBox<>(classes);
        classComboBox.setSelectedIndex(0);//默认选中第一个
        classComboBox.setFont(new Font("宋体", Font.BOLD, 15));
        classComboBox.setBounds(170, 325, 225, 36);
        contentPane.add(classComboBox);

        String[] floors = studentDaoImpl.getFloors();
        JComboBox<String> floorComboBox = new JComboBox<>(floors);
        floorComboBox.setSelectedIndex(0);//默认选中第一个
        floorComboBox.setFont(new Font("宋体", Font.BOLD, 15));
        floorComboBox.setBounds(170, 395, 225, 36);
        contentPane.add(floorComboBox);

        //将编号设为默认不可编辑，且显示此次新增学生的id
        int count = studentDaoImpl.countData(stu);//得到总数据量，即当前id的最高值。未新增成功时，stu为Null，默认查询所有数据
        idText.setText(String.valueOf(count + 1));//新增学生的id 为 当前id的最高值 + 1
        idText.setEnabled(false);//将id文本框设置为不可编辑


        //重置按钮监听
        resetButton.addActionListener(e -> {
            if (e.getSource() == resetButton) {
                nameText.setText("");
                sexBoyButton.setSelected(true);
                ageText.setText("");
                classComboBox.setSelectedIndex(0);
                floorComboBox.setSelectedIndex(0);
                roomText.setText("");

            }
        });
        //新增按钮监听
        addButton.addActionListener(e -> {
            if (e.getSource() == addButton) {
                boolean inputJudge = true;    //判断能否执行新增工作。学生信息是否全部填写？
                boolean regexpAge = true;    //判断能否执行新增工作。年龄是否符合正则(非0正整数)？
                boolean regexpRoom = true;    //判断能否执行新增工作。房间号是否符合正则(非0正整数)？
                boolean regexpName = true;    //判断能否执行新增工作。姓名是否符合正则(2-4位汉字)？

                String name = nameText.getText();
                if ("".equals(name) || name == null) {
                    inputJudge = false;//若姓名没填写，则在之后不执行新增操作
                } else {
                    regexpName = regExpHelp.formatChinese(name);//检验是否为2-4位汉字
                }

                String sex = null;
                if (sexBoyButton.isSelected()) {
                    sex = "男";
                } else {
                    sex = "女";
                }

                Integer age = null;
                if ("".equals(ageText.getText()) || ageText.getText() == null) {
                    inputJudge = false;//若年龄没填写，则在之后不执行新增操作
                } else {
                    regexpAge = regExpHelp.notZeroAndPositiveAndLimitDigit(ageText.getText(), 1, 2);//检验是否为1-2位非0正整数（1-99）
                    if (regexpAge) {
                        age = Integer.parseInt(ageText.getText());//先正则再赋值，防止Integer类型空指针或溢出
                    }
                }

                String classNum = (String) classComboBox.getSelectedItem();//得到班级

                String floor = (String) floorComboBox.getSelectedItem();//得到楼层（8号楼）
                String floorNum = floor.substring(0, 1);//得到楼层数（8）

                String roomNum = roomText.getText();
                if ("".equals(roomNum) || roomNum == null) {
                    inputJudge = false;//若房号没填写，则在之后不执行新增操作
                } else {
                    regexpRoom = regExpHelp.formatRoomNum(roomNum);//检验房间号格式：[1-4][0-3][1-9]的3位数
                }

                //依次判断是否正则成功，防止一次性弹出多个错误框
                if (inputJudge && regexpName && regexpAge && regexpRoom) {
                    //开始连接数据库新增
                    int n = JOptionPane.showConfirmDialog(null, "您确定要新增吗？", "确认新增",
                            JOptionPane.YES_NO_OPTION);
                    if (n == 0) {
                        String address = floorNum + "#" + roomNum;
                        stu = new StudentEntity(null, name, sex, age, classNum, address);
                        int i = studentDaoImpl.addStudent(stu);
                        if (i > 0) {
                            JOptionPane.showMessageDialog(null, "新增成功！");
                            setVisible(false);//关闭（隐藏）新增页面；

                        } else {
                            JOptionPane.showMessageDialog(null, "新增失败！", "失败",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (!inputJudge) {
                    JOptionPane.showMessageDialog(null, "您还有尚未填写的学生信息！", "错误",
                            JOptionPane.ERROR_MESSAGE);

                } else if (!regexpName) {
                    JOptionPane.showMessageDialog(null, "请输入2-4位的中文姓名！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    nameText.setText("");
                } else if (!regexpAge) {
                    JOptionPane.showMessageDialog(null, "年龄不满足范围（1-99）！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    ageText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "请输入正确的房间号：[1-4][0-3][1-9]！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    roomText.setText("");
                }
            }
        });
    }
}
