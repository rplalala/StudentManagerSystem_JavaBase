package com.ding.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ding.dao.StudentDao;
import com.ding.entity.StudentEntity;
import com.ding.utils.DBUtils;


//学生接口实现类，实现功能
public class StudentDaoImpl implements StudentDao {

    private int pageSize = -1;//一页显示的数据量
    private DBUtils dbUtils = new DBUtils();

    public StudentDaoImpl() {

    }


    public StudentDaoImpl(int pageSize) {
        super();
        this.pageSize = pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * 根据输入的查询信息stu 和 当前页码PageNum 查询数据库中的学生信息
     * 输入的查询信息 储存在StudentEntity对象中
     */
    @Override
    public List<Map<String, String>> queryStudent(StudentEntity stu, int pageNum) {
        String sql = "select id,name,sex,age,class,address from students where 1=1";//where 1=1 是多条件查询的常用手法
        List<Object> list = new ArrayList<>();
        //输入的信息储存在学生类里，若不为null且不为""，则代表输入过信息，把信息拼接到sql中
        if (stu.getId() != null) {
            sql += " and id = ?";
            list.add(stu.getId());
        }
        if (stu.getName() != null && !("".equals(stu.getName()))) {
            sql += " and name like ?";
            list.add("%" + stu.getName() + "%");
        }
        if (stu.getSex() != null && !("".equals(stu.getSex()))) {
            sql += " and sex like ?";
            list.add("%" + stu.getSex() + "%");
        }
        if (stu.getAge() != null) {
            sql += " and age = ?";
            list.add(stu.getAge());
        }
        if (stu.getClasses() != null && !("".equals(stu.getClasses()))) {
            sql += " and class like ?";
            list.add("%" + stu.getClasses() + "%");
        }
        if (stu.getAddress() != null && !("".equals(stu.getAddress()))) {
            sql += " and address like ?";
            list.add("%" + stu.getAddress() + "%");
        }
        sql += " limit ?,?;";
        list.add((pageNum - 1) * pageSize);
        list.add(pageSize);
        Object[] obj = list.toArray();
        return dbUtils.query(sql, obj);
    }


    /**
     * 根据输入的新增信息stu 向数据表里新增学生
     * 返回更新的行数
     */
    @Override
    public int addStudent(StudentEntity stu) {
        String sql = "insert into students(id,name,sex,age,class,address) values(null,?,?,?,?,?);";
        Object[] obj = {stu.getName(), stu.getSex(), stu.getAge(), stu.getClasses(), stu.getAddress()};
        return dbUtils.update(sql, obj);
    }


    /**
     * 根据Id删除数据表里的学生
     */
    @Override
    public int deleteStudent(int id) {
        String sql = "delete from students where id = ?;";
        Object[] obj = {id};
        return dbUtils.update(sql, obj);
    }


    /**
     * 解决删除学生数据后，自增id断层的问题
     * <p>
     * 解决办法：先删除id这个字段，再把id这个字段按建表时的要求添加到首位
     * https://blog.csdn.net/RP123123123/article/details/117517540
     */
    @Override
    public void solveDeleteProblem() {
        String sql1 = "alter table students drop id;";//删除id字段
        String sql2 = "alter table students add id int(11) primary key auto_increment first;";//添加id字段，并重新设置id自增
        Object[] obj = {};
        dbUtils.update(sql1, obj);
        dbUtils.update(sql2, obj);
    }


    /**
     * 根据输入的学生信息stu 修改该学生的信息
     * <p>
     * 有输入的信息进行更改，没有输入的信息保持不变
     */
    @Override
    public int updateStudent(StudentEntity stu) {
        String sql = "update students set id = ?";
        List<Object> list = new ArrayList<>();
        list.add(stu.getId());
        if (!("".equals(stu.getName())) && !(stu.getName() == null)) {
            sql += ",name = ?";
            list.add(stu.getName());

        }
        if (!("".equals(stu.getSex())) && !(stu.getSex() == null)) {
            sql += ",sex = ?";
            list.add(stu.getSex());
        }
        if (!(stu.getAge() == null)) {
            sql += ",age = ?";
            list.add(stu.getAge());
        }
        if (!("".equals(stu.getClasses())) && !(stu.getClasses() == null)) {
            sql += ",class = ?";
            list.add(stu.getClasses());
        }
        if (!("".equals(stu.getAddress())) && !(stu.getAddress() == null)) {
            sql += ",address = ?";
            list.add(stu.getAddress());
        }
        sql += "where id = ?;";
        list.add(stu.getId());
        Object[] obj = list.toArray();
        return dbUtils.update(sql, obj);
    }


    /**
     * 根据输入的查询信息stu 得到学生总人数
     * 返回学生总人数
     */
    @Override
    public int countData(StudentEntity stu) {
        String sql = "select count(id) countStu from students where 1=1";//where 1=1 是多条件查询的常用手法
        List<Object> list = new ArrayList<>();
        //输入的信息储存在学生类里，若不为null且不为""，则代表输入过信息，把信息拼接到sql中
        if (stu.getId() != null) {
            sql += " and id = ?";
            list.add(stu.getId());
        }
        if (stu.getName() != null && !("".equals(stu.getName()))) {
            sql += " and name like ?";
            list.add("%" + stu.getName() + "%");
        }
        if (stu.getSex() != null && !("".equals(stu.getSex()))) {
            sql += " and sex like ?";
            list.add("%" + stu.getSex() + "%");
        }
        if (stu.getAge() != null) {
            sql += " and age = ?";
            list.add(stu.getAge());
        }
        if (stu.getClasses() != null && !("".equals(stu.getClasses()))) {
            sql += " and class like ?";
            list.add("%" + stu.getClasses() + "%");
        }
        if (stu.getAddress() != null && !("".equals(stu.getAddress()))) {
            sql += " and address like ?";
            list.add("%" + stu.getAddress() + "%");
        }
        Object[] obj = list.toArray();
        List<Map<String, String>> query = dbUtils.query(sql, obj);
        return Integer.parseInt(query.get(0).get("countStu"));
    }


    /**
     * 返回下拉列表中楼号的选项，根据数据库动态变换
     * <p>
     * 得到数据库 address字段结果的第一个字符，即楼号。再根据楼号分组得到共有几种楼号
     */
    @Override
    public String[] getFloors() {
        String sql = "select left(address,1) floor from students group by floor";
        Object[] obj = {};
        Vector<String> v = new Vector<>();
        List<Map<String, String>> query = dbUtils.query(sql, obj);
        for (Map<String, String> map : query) {
            v.add(map.get("floor") + "号楼");
        }
        String[] s = new String[v.size()];
        return v.toArray(s);
    }


    /**
     * 返回下拉列表中班级的选项，根据数据库动态变换
     * <p>
     * 得到数据库class字段的数据，再根据class分组得到共有几种班级
     */
    @Override
    public String[] getClasses() {
        String sql = "select class from students group by class";
        Object[] obj = {};
        Vector<String> v = new Vector<>();
        List<Map<String, String>> query = dbUtils.query(sql, obj);
        for (Map<String, String> map : query) {
            v.add(map.get("class"));
        }
        String[] s = new String[v.size()];
        return v.toArray(s);
    }
}
