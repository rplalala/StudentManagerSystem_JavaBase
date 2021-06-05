package com.ding.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ding.dao.impl.StudentDaoImpl;
import com.ding.entity.StudentEntity;
import com.ding.utils.RegExpHelp;
import com.ding.utils.TableDataShowHelp;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentMainUI extends JFrame {
    /**
     * 串行版本标识
     */
    private static final long serialVersionUID = -1663697923957422798L;

    private JPanel contentPane;
    private JTextField idText;
    private JTextField nameText;
    private JTextField sexText;
    private JTextField ageText;
    private JTextField classText;
    private JTextField addressText;
    private JTextField jumpPageText;
    private TableModel tableModel = new DefaultTableModel();
    private JTable table = new JTable(tableModel);//告诉JTable以什么格式解析数据
    private JPopupMenu rightMouseMenu;//鼠标右键菜单

    private int count = 0;//总数据
    private int pageSize = 20;//一页显示20个数据
    private int pageNum = 1;//当前页数
    private int pageCount = 1;//总页数

    private TableDataShowHelp tableDataShow = new TableDataShowHelp();
    private RegExpHelp regExpHelp = new RegExpHelp();
    private StudentDaoImpl studentDaoImpl = new StudentDaoImpl(pageSize);
    private List<Map<String, String>> list = new ArrayList<>();
    private StudentEntity stu = new StudentEntity();

    private boolean isAdmin = true;//用来判断是否是主管理员admin

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 主界面(查询、新增、修改、删除)
     */
    public StudentMainUI() {
        //frame
        setTitle("学生管理系统 by Ding");
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.jpg"));
        setSize(1400, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//不执行任何操作，只在WindowListener中的windowClosing方法中处理该操作
        //设置窗口关闭监听
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //如果是主管理员admin，可以选择是否要返回选择界面；如果不是，则直接退出
                if (isAdmin) {
                    Object[] obj = {"是", "否", "返回选择界面"};

                    int judge = JOptionPane.showOptionDialog(null, "您确定要退出吗？", "退出",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, obj, "是");
                    if (judge == 0) {
                        System.exit(1);
                    } else if (judge == 2) {
                        setVisible(false);
                        new ChoiceUI();
                    }
                } else {
                    int judge = JOptionPane.showConfirmDialog(null, "您确定要退出吗？", "退出", JOptionPane.YES_NO_OPTION);
                    if (judge == 0) {
                        System.exit(1);
                    }
                }
            }
        });


        //查询栏
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel idLabel = new JLabel("编号");
        idLabel.setFont(new Font("宋体", Font.BOLD, 18));
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(idLabel);

        idText = new JTextField();
        idText.setColumns(10);
        northPanel.add(idText);

        JLabel nameLabel = new JLabel("姓名");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("宋体", Font.BOLD, 18));
        northPanel.add(nameLabel);

        nameText = new JTextField();
        nameText.setColumns(10);
        northPanel.add(nameText);

        JLabel sexLabel = new JLabel("性别");
        sexLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sexLabel.setFont(new Font("宋体", Font.BOLD, 18));
        northPanel.add(sexLabel);

        sexText = new JTextField();
        sexText.setColumns(10);
        northPanel.add(sexText);

        JLabel ageLabel = new JLabel("年龄");
        ageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ageLabel.setFont(new Font("宋体", Font.BOLD, 18));
        northPanel.add(ageLabel);

        ageText = new JTextField();
        ageText.setColumns(10);
        northPanel.add(ageText);

        JLabel classLabel = new JLabel("班级");
        classLabel.setHorizontalAlignment(SwingConstants.CENTER);
        classLabel.setFont(new Font("宋体", Font.BOLD, 18));
        northPanel.add(classLabel);

        classText = new JTextField();
        classText.setColumns(10);
        northPanel.add(classText);

        JLabel addressLabel = new JLabel("住址");
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addressLabel.setFont(new Font("宋体", Font.BOLD, 18));
        northPanel.add(addressLabel);

        addressText = new JTextField();
        addressText.setColumns(10);
        northPanel.add(addressText);

        JLabel spaceLabel1 = new JLabel("   ");//用来增加文本框与查询之间的间距
        northPanel.add(spaceLabel1);

        JButton queryButton = new JButton("   查   询   ");
        queryButton.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 12));
        northPanel.add(queryButton);

        JLabel spaceLabel2 = new JLabel("   ");
        northPanel.add(spaceLabel2);

        JButton resetButton = new JButton("   重   置   ");
        resetButton.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 12));
        northPanel.add(resetButton);

        JLabel spaceLabel2_1 = new JLabel("   ");
        northPanel.add(spaceLabel2_1);

        JButton addButton = new JButton("   新   增   ");
        addButton.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 12));
        northPanel.add(addButton);

        //底部标示栏
        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);

        JLabel markedLabel1 = new JLabel("共有");
        markedLabel1.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(markedLabel1);

        JLabel countLabel = new JLabel("xxx");
        countLabel.setFont(new Font("宋体", Font.BOLD, 12));
        southPanel.add(countLabel);

        JLabel markedLabel2 = new JLabel("条数据");
        markedLabel2.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(markedLabel2);

        JLabel spaceLabel3 = new JLabel("                               ");
        southPanel.add(spaceLabel3);

        JLabel markedLabel3 = new JLabel("当前页 / 总页数：");
        markedLabel3.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(markedLabel3);

        JLabel pageNumLabel = new JLabel("xx");
        pageNumLabel.setFont(new Font("宋体", Font.BOLD, 12));
        southPanel.add(pageNumLabel);

        JLabel markedLabel4 = new JLabel("/");
        markedLabel4.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(markedLabel4);

        JLabel pageCountLabel = new JLabel("xx");
        pageCountLabel.setFont(new Font("宋体", Font.BOLD, 12));
        southPanel.add(pageCountLabel);

        JLabel spaceLabel4 = new JLabel("                               ");
        southPanel.add(spaceLabel4);

        JButton beginPageLabel = new JButton("首  页");
        beginPageLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(beginPageLabel);

        JButton upPageLabel = new JButton("上一页");
        upPageLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(upPageLabel);

        JButton downPageLabel = new JButton("下一页");
        downPageLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(downPageLabel);

        JButton endPageLabel = new JButton("尾  页");
        endPageLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(endPageLabel);

        JLabel spaceLabel5 = new JLabel("                               ");
        southPanel.add(spaceLabel5);

        jumpPageText = new JTextField();
        jumpPageText.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(jumpPageText);
        jumpPageText.setColumns(15);

        JButton jumpPageButton = new JButton("跳  页");
        jumpPageButton.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(jumpPageButton);

        JLabel spaceLabel6 = new JLabel("                               ");
        southPanel.add(spaceLabel6);

        JButton refreshButton = new JButton("刷  新");
        refreshButton.setFont(new Font("宋体", Font.PLAIN, 12));
        southPanel.add(refreshButton);


        //数据显示栏
        table.setRowHeight(52);//设置table表格行高
        //设置表格数据居中
        DefaultTableCellRenderer tableCenter = new DefaultTableCellRenderer();
        tableCenter.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, tableCenter);

        //将从数据库中读取到的数据根据pageNum展现在表格中（进入页面后最初显示的数据）
        pageNum = 1;//默认先展现第一页的数据
        list = studentDaoImpl.queryStudent(stu, pageNum);//还未点击查询按钮时，stu对象为Null，默认查询全部数据
        tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
        table.setModel(tableModel);

        count = studentDaoImpl.countData(stu);//返回查询到的总数据
        //再根据总数据量count，算出查询总页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        //设置底部栏标签动态变换
        countLabel.setText(Integer.valueOf(count).toString());
        pageCountLabel.setText(Integer.valueOf(pageCount).toString());
        pageNumLabel.setText(Integer.valueOf(pageNum).toString());

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //给鼠标设置右键菜单
        rightMouseMenu = new JPopupMenu();//右键菜单

        JMenuItem updateMenItem = new JMenuItem();//右键修改 菜单选项
        updateMenItem.setText("  修改  ");

        JMenuItem delMenItem = new JMenuItem();//右键删除 菜单选项
        delMenItem.setText("  删除  ");

        rightMouseMenu.add(updateMenItem);
        rightMouseMenu.add(delMenItem);

        //表格鼠标右键点击 事件监听。鼠标右键弹出菜单
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //通过点击位置找到点击为表格中的行
                    int focusedRowIndex = table.rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    //将表格所选项设为当前右键点击的行
                    table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    //弹出菜单
                    rightMouseMenu.show(table, e.getX(), e.getY());
                }
            }
        });

        //鼠标右键菜单 修改菜单监听
        updateMenItem.addActionListener(e -> {
            int index = table.getSelectedRow();//得到点击修改的位置 在表格中的索引
            Integer id = Integer.parseInt((String) table.getValueAt(index, 0));//得到该索引(第index行)的第0列(id)的属性值
            String name = (String) table.getValueAt(index, 1);
            String sex = (String) table.getValueAt(index, 2);
            Integer age = Integer.parseInt((String) table.getValueAt(index, 3));
            String classes = (String) table.getValueAt(index, 4);
            String address = (String) table.getValueAt(index, 5);
            new StudentUpdateUI(new StudentEntity(id, name, sex, age, classes, address));//把需要修改的学生 当前的所有信息传给修改UI
        });

        //鼠标右键菜单 删除菜单监听
        delMenItem.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(null, "您确定要删除该学生吗？", "删除",
                    JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                int index = table.getSelectedRow();//得到点击删除的位置 在表格中的索引
                String id = (String) table.getValueAt(index, 0);//得到该索引(第index行)的第0列(id)的属性值
                int row = studentDaoImpl.deleteStudent(Integer.parseInt(id));//删除当前id对应的学生信息
                studentDaoImpl.solveDeleteProblem();//解决删除id后自增断层的问题
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "删除成功！");
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败！");
                }
            }
        });

        //查询按钮监听
        queryButton.addActionListener(e -> {
            if (e.getSource() == queryButton) {
                boolean regexpId = true;    //判断id的正则表达式
                boolean regexpAge = true;    //判断年龄的正则表达式

                Integer id = null;
                if (!("".equals(idText.getText())) && idText.getText() != null) {
                    regexpId = regExpHelp.notZeroAndPositiveAndLimitDigit(idText.getText(), 1, 7);//检验是否为1-7位非0正整数（1-9999999）
                    if (regexpId) {
                        id = Integer.parseInt(idText.getText());//先正则再赋值，防止Integer类型空指针或溢出
                    }
                }
                String name = nameText.getText();
                String sex = sexText.getText();
                Integer age = null;
                if (!("".equals(ageText.getText())) && ageText.getText() != null) {
                    regexpAge = regExpHelp.notZeroAndPositiveAndLimitDigit(ageText.getText(), 1, 2);//检验是否为1-2位非0正整数（1-99）
                    if (regexpAge) {
                        age = Integer.parseInt(ageText.getText());//先正则再赋值，防止Integer类型空指针或溢出
                    }
                }
                String classes = classText.getText();
                String address = addressText.getText();
                //依次判断是否正则成功，防止一次性弹出多个错误框
                if (regexpId && regexpAge) {
                    //开始连接数据库查询
                    stu = new StudentEntity(id, name, sex, age, classes, address);//每次点击查询按钮时，成员变量stu的值都会发生改变
                    pageNum = 1;//默认先展现第一页的数据
                    list = studentDaoImpl.queryStudent(stu, pageNum);
                    tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                    table.setModel(tableModel);

                    count = studentDaoImpl.countData(stu);//返回查询到的总数据
                    //再根据总数据量count，算出查询总页数
                    if (count % pageSize == 0) {
                        pageCount = count / pageSize;
                    } else {
                        pageCount = count / pageSize + 1;
                    }
                    //设置底部栏标签动态变换
                    countLabel.setText(Integer.valueOf(count).toString());
                    pageCountLabel.setText(Integer.valueOf(pageCount).toString());
                    pageNumLabel.setText(Integer.valueOf(pageNum).toString());
                } else if (!regexpId) {
                    JOptionPane.showMessageDialog(null, "编号不满足范围（1-9999999）！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    idText.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "年龄不满足范围（1-99）！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    ageText.setText("");
                }
            }
        });
        //重置按钮监听
        resetButton.addActionListener(e -> {
            if (e.getSource() == resetButton) {
                idText.setText("");
                nameText.setText("");
                sexText.setText("");
                ageText.setText("");
                classText.setText("");
                addressText.setText("");
            }
        });
        //新增按钮监听
        addButton.addActionListener(e -> new StudentAddUI());
        //首页按钮监听
        beginPageLabel.addActionListener(e -> {
            if (e.getSource() == beginPageLabel) {
                pageNum = 1;
                //将从数据库中读取到的数据根据pageNum展现在表格中
                list = studentDaoImpl.queryStudent(stu, pageNum);
                tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                table.setModel(tableModel);
                pageNumLabel.setText(Integer.valueOf(pageNum).toString());
            }
        });
        //上一页按钮监听
        upPageLabel.addActionListener(e -> {
            if (e.getSource() == upPageLabel) {
                if (pageNum > 1) {
                    pageNum--;
                } else {
                    pageNum = pageCount;
                }

                //将从数据库中读取到的数据根据pageNum展现在表格中
                list = studentDaoImpl.queryStudent(stu, pageNum);
                tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                table.setModel(tableModel);
                pageNumLabel.setText(Integer.valueOf(pageNum).toString());
            }
        });
        //下一页监听
        downPageLabel.addActionListener(e -> {
            if (e.getSource() == downPageLabel) {
                if (pageNum < pageCount) {
                    pageNum++;
                } else {
                    pageNum = 1;
                }

                //将从数据库中读取到的数据根据pageNum展现在表格中
                list = studentDaoImpl.queryStudent(stu, pageNum);
                tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                table.setModel(tableModel);
                pageNumLabel.setText(Integer.valueOf(pageNum).toString());
            }
        });
        //尾页监听
        endPageLabel.addActionListener(e -> {
            if (e.getSource() == endPageLabel) {
                pageNum = pageCount;
                //将从数据库中读取到的数据根据pageNum展现在表格中
                list = studentDaoImpl.queryStudent(stu, pageNum);
                tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                table.setModel(tableModel);
                pageNumLabel.setText(Integer.valueOf(pageNum).toString());
            }
        });
        //跳页按钮事件监听
        jumpPageButton.addActionListener(e -> {
            if (e.getSource() == jumpPageButton) {
                //进行正则表达式检验：只能输入非零的正整数
                boolean judge = regExpHelp.notZeroAndPositiveAndLimitDigit(jumpPageText.getText(), 1, 5);//1-5位非0正整数（1-99999）

                if (!judge) {
                    JOptionPane.showMessageDialog(null, "输入范围1-" + pageCount + ",请在页数范围内跳转!", "错误",
                            JOptionPane.ERROR_MESSAGE);

                } else {
                    int jumpPage = Integer.parseInt(jumpPageText.getText());
                    if (jumpPage > pageCount) {
                        JOptionPane.showMessageDialog(null, "输入范围1-" + pageCount + ",请在页数范围内跳转!", "错误",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        pageNum = jumpPage;
                        //将从数据库中读取到的数据根据pageNum展现在表格中
                        list = studentDaoImpl.queryStudent(stu, pageNum);
                        tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                        table.setModel(tableModel);
                        pageNumLabel.setText(Integer.valueOf(pageNum).toString());
                    }
                }
                jumpPageText.setText("");
            }
        });
        //刷新按钮监听（刷新当前页数据，并更新标签值）
        refreshButton.addActionListener(e -> {
            if (e.getSource() == refreshButton) {
                stu = new StudentEntity();//刷新stu的数据，防止查询时stu的数据影响。
                list = studentDaoImpl.queryStudent(stu, pageNum);
                tableModel = tableDataShow.getStudentTableModel(list);//将列名和数据放到表格中
                table.setModel(tableModel);

                count = studentDaoImpl.countData(stu);//返回查询到的总数据
                //再根据总数据量count，算出查询总页数
                if (count % pageSize == 0) {
                    pageCount = count / pageSize;
                } else {
                    pageCount = count / pageSize + 1;
                }
                //设置底部栏标签动态变换
                countLabel.setText(Integer.valueOf(count).toString());
                pageCountLabel.setText(Integer.valueOf(pageCount).toString());
                pageNumLabel.setText(Integer.valueOf(pageNum).toString());
            }
        });
    }
}
