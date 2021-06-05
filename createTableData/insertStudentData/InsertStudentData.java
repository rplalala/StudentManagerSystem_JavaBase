package insertStudentData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 此类用来随机生成2000条学生数据并插入MySQL数据库，利用批处理
public class InsertStudentData {
    public static void main(String[] args) {
        RandInfo rand = new RandInfo();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + "studentmanager?useSSL=false&characterEncoding=utf8", "root", "dzh20001220");
            connection.setAutoCommit(false);//设置手动提交事务
            long start = System.currentTimeMillis();
            String sql = "insert into students(id,name,sex,age,class,address) "
                    + "values(null,?,?,?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 2000; i++) {
                String[] nameAndSex = rand.getNameAndSex(rand.getSex()); //得到名字和性别的数组
                String familyName = rand.getFamilyName(); //得到姓氏
                String name = nameAndSex[0]; //根据数组得到名字和性别
                String sex = nameAndSex[1];
                String classes = rand.getClasses(); //得到班级
                String address = rand.getAddress(); //得到住址
                int age = rand.getAge(); //得到年龄
                prepareStatement.setObject(1, (familyName + name));
                prepareStatement.setObject(2, sex);
                prepareStatement.setObject(3, age);
                prepareStatement.setObject(4, classes);
                prepareStatement.setObject(5, address);
                prepareStatement.addBatch();//依次将2000个SQL语句提交到命令列表
            }
            prepareStatement.executeBatch();//一次性执行命令列表中的所有SQL语句
            connection.commit();//提交事务，使之前所有的sql命令都生效
            long end = System.currentTimeMillis();
            System.out.println("插入了2000条学生数据，共执行了" + (end - start) + "ms");
            prepareStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
