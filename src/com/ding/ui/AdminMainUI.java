package com.ding.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ding.dao.impl.AdminDaoImpl;
import com.ding.entity.AdminEntity;
import com.ding.utils.TableDataShowHelp;

public class AdminMainUI extends JFrame{

	/**
	 * 串行版本标识
	 */
	private static final long serialVersionUID = -7432697750359302845L;
	
	private JPanel contentPane;
	private final JPanel southPanel = new JPanel();
	
	private TableModel tableModel = new DefaultTableModel();
	private JTable table = new JTable(tableModel);//告诉JTable以什么格式解析数据
	private JPopupMenu rightMouseMenu;//鼠标右键菜单
	
	private int count = 0;//总数据

	
	private TableDataShowHelp tableDataShow = new TableDataShowHelp();
	private List<Map<String, String>> list = new ArrayList<>();
	private AdminDaoImpl adminDaoImpl = new AdminDaoImpl();
	

	public AdminMainUI() {
		
		//frame
		setTitle("学生管理系统 by Ding");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.jpg"));
		setSize(500,700);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//不执行任何操作，只在WindowListener中的windowClosing方法中处理该操作
		//设置窗口关闭监听
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
            	Object[] obj = { "是", "否" ,"返回选择界面"};
            	
            	int judge = JOptionPane.showOptionDialog(null, "您确定要退出吗？", "退出",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, obj,"是");
                if(judge == 0) {
                	System.exit(1);
                }else if(judge == 2) {
                	setVisible(false);
                	new ChoiceUI();
                }
            }
        });
        
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//底部标签栏
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
		
		JLabel spaceLabel1 = new JLabel("                            ");
		southPanel.add(spaceLabel1);
		
		JButton addButton = new JButton("新  增");
		addButton.setFont(new Font("宋体", Font.PLAIN, 12));
	    southPanel.add(addButton);
	    
	    JLabel spaceLabel2 = new JLabel("                                    ");
		southPanel.add(spaceLabel2);
	    
		JButton refreshButton = new JButton("刷  新");
	    refreshButton.setFont(new Font("宋体", Font.PLAIN, 12));
	    southPanel.add(refreshButton);
	    
	    //设置底部总数据量的值
	    count = adminDaoImpl.countAdmin();
	    countLabel.setText(String.valueOf(count));
		
		//数据显示栏
		table.setRowHeight(52);//设置table表格行高
	    //设置表格数据居中
	    DefaultTableCellRenderer tableCenter = new DefaultTableCellRenderer();   
	    tableCenter.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tableCenter);
		
		list = adminDaoImpl.queryAdmin();//得到所有管理员信息
		tableModel = tableDataShow.getAdminTableModel(list);//将列名和数据放到表格中
	    table.setModel(tableModel);
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
	                rightMouseMenu.show( table, e.getX(), e.getY());
	            }
            }
	    });
		
		//鼠标右键菜单 修改菜单监听
        updateMenItem.addActionListener(e ->{
        	int index = table.getSelectedRow();//得到点击修改的位置 在表格中的索引
        	String userName = (String)table.getValueAt(index,0);//得到该索引(第index行)的第0列(用户名)的属性值
        	String userPassword = (String)table.getValueAt(index,1);
        	new AdminUpdateUI(new AdminEntity(userName,userPassword));//将需要进行修改的 管理员的信息传到update界面
        });
        
        //鼠标右键菜单 删除菜单监听
        delMenItem.addActionListener(e -> {
        	int n = JOptionPane.showConfirmDialog(null, "您确定要删除该管理员吗？", "删除",
                    JOptionPane.YES_NO_OPTION);
        	if(n == 0) {
            	int index = table.getSelectedRow();//得到点击删除的位置 在表格中的索引
            	String userName = (String)table.getValueAt(index,0);//得到该索引(第index行)的第0列(用户名)的属性值
            	boolean judge = adminDaoImpl.deleteAdmin(userName);//删除当前用户名对应的管理员信息
            	if(judge) {
            		JOptionPane.showMessageDialog(null, "删除成功！");
            	}else {
            		JOptionPane.showMessageDialog(null, "删除失败！");
            	}
        	}
        });
        
		//新增按钮监听
        addButton.addActionListener(e ->{
			new AdminAddUI();
		});
        
	    //刷新按钮监听（刷新数据，并更新标签值）
	    refreshButton.addActionListener(e -> {
	    	if(e.getSource() == refreshButton) {
	    		list = adminDaoImpl.queryAdmin();//得到所有管理员信息
	    		tableModel = tableDataShow.getAdminTableModel(list);//将列名和数据放到表格中
	    	    table.setModel(tableModel);
	    	    
	    	    count = adminDaoImpl.countAdmin();
	    	    countLabel.setText(String.valueOf(count));
	    	}
	    });
        

	}
}
