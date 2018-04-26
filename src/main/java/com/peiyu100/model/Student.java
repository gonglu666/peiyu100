package com.peiyu100.model;

public class Student {
    private String schoolId;           //学校id
    private String teacherLoginName;          //教师登录id
    private String name;
    private String sex;                //性别
    private String phone;              //手机号
    private String classes;            //班级

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getTeacherLoginName() {
        return teacherLoginName;
    }

    public void setTeacherLoginName(String teacherLoginName) {
        this.teacherLoginName = teacherLoginName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
