package com.peiyu100.model;

public class Teacher {

    private String schoolId;           //学校id
    private String name;               //教师姓名
    private String loginName;          //登录名
    private String sex;                //性别
    private String phone;              //手机号
    private String isHeadmaster;       //是否班主任
    private String duties;             //职务
    private int status;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getIsHeadmaster() {
        return isHeadmaster;
    }

    public void setIsHeadmaster(String isHeadmaster) {
        this.isHeadmaster = isHeadmaster;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
