package com.ding.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DBUtils {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    static Properties properties = null;//存放mysql连接信息的配置文件

    //加载驱动
    static {
        try {
            //实例化配置文件信息
            properties = new Properties();
            properties.load(new FileReader("src/JDBC.properties"));
            //利用配置文件加载数据库
            Class.forName(properties.getProperty("mysqlDriver"));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     */
    public void getConnection() {
        try {
            String url = properties.getProperty("mysqlUrl");
            String userName = properties.getProperty("mysqlUser");
            String userPassword = properties.getProperty("mysqlPwd");
            connection = DriverManager.getConnection(url, userName, userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭各连接
     */
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (resultSet != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新操作
     *
     * @param sql 查询sql语句
     * @param obj sql语句中各占位符对应的值
     * @return 更新的行数(若为DDL语句 ， 因没有操作行 ， 则默认返回0)
     */
    public int update(String sql, Object[] obj) {
        int row = -1;
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                preparedStatement.setObject(i + 1, obj[i]);
            }
            row = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return row;
    }

    /**
     * 查询操作
     *
     * @param sql 查询sql语句
     * @param obj sql语句中各占位符对应的值
     * @return List<Map < String, String>>(储存查询到的学生信息)
     */
    public List<Map<String, String>> query(String sql, Object[] obj) {
        List<Map<String, String>> list = new ArrayList<>();
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                preparedStatement.setObject(i + 1, obj[i]);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    String columnName = resultSetMetaData.getColumnName(i);//按顺序获取表的列名
                    map.put(columnName, resultSet.getString(columnName));
                }
                list.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
}
