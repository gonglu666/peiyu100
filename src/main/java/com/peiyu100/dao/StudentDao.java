package com.peiyu100.dao;

import com.peiyu100.model.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentDao {

    @Insert("insert into student (schoolId,teacherLoginName,name,sex,phone,classes) values (#{schoolId},#{teacherLoginName},#{name},#{sex},#{phone},#{classes})")
    int insert(Student student);


    @Select("select *  from student  where teacherLoginName=#{teacherLoginName}")
    List<Student> getList(@Param(value = "teacherLoginName") String teacherLoginName);


    @Select("select *  from student  where schoolId=#{schoolId}")
    List<Student> getListFromSchool(@Param(value = "schoolId") String schoolId);

}
