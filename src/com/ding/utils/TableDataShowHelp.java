package com.ding.utils;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class TableDataShowHelp {
    /**
     * 得到存有 列名 和 数据 数据模型(学生表)
     */
    public TableModel getStudentTableModel(List<Map<String, String>> list) {
        Vector<String> columnNames = new Vector<>();//定义列名称，并放到 Vector<String>中
        columnNames.add("编号");
        columnNames.add("姓名");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("班级");
        columnNames.add("住址");
        Vector<Vector<String>> data = new Vector<>();//将数据库中的数据存到到 Vector<Vector<String>>中
        for (Map<String, String> map : list) {
            Vector<String> v = new Vector<>();
            v.add(map.get("id"));
            v.add(map.get("name"));
            v.add(map.get("sex"));
            v.add(map.get("age"));
            v.add(map.get("class"));
            v.add(map.get("address"));
            data.add(v);
        }
        return new DefaultTableModel(data, columnNames);
    }

    /**
     * 得到存有 列名 和 数据 数据模型(管理员表)
     */
    public TableModel getAdminTableModel(List<Map<String, String>> list) {
        Vector<String> columnNames = new Vector<>();//定义列名称，并放到 Vector<String>中
        columnNames.add("用户名");
        columnNames.add("密  码");
        Vector<Vector<String>> data = new Vector<>();//将数据库中的数据存到到 Vector<Vector<String>>中
        for (Map<String, String> map : list) {
            Vector<String> v = new Vector<>();
            v.add(map.get("userName"));
            v.add(map.get("userPassword"));
            data.add(v);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
