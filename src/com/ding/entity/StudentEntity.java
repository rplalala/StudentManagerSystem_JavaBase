package com.ding.entity;


//用来存放 输入框中输入的学生信息
public class StudentEntity {
    private Integer id = null;
    private String name = null;
    private String sex = null;
    private Integer age = null;
    private String classes = null;
    private String address = null;


    public StudentEntity() {

    }

    public StudentEntity(Integer id, String name, String sex, Integer age, String classes, String address) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.classes = classes;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
