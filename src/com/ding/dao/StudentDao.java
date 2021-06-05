package com.ding.dao;

import java.util.List;
import java.util.Map;

import com.ding.entity.StudentEntity;

//学生接口，定义功能
public interface StudentDao {
    List<Map<String, String>> queryStudent(StudentEntity stu, int pageNum);

    int addStudent(StudentEntity stu);

    int deleteStudent(int id);

    int updateStudent(StudentEntity stu);

    void solveDeleteProblem();

    int countData(StudentEntity stu);

    String[] getFloors();

    String[] getClasses();
}
