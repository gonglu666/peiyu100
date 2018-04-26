package com.peiyu100.dao;

import com.peiyu100.model.StatClasses;
import com.peiyu100.model.StatSchool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface  StatDao {

    @Select("select t.schoolId as schoolId,t.schoolName as schoolName,count(1) as num from d_student t group  by t.schoolId ,t.schoolName")
    List<StatSchool> getStatSchoolList();

    @Select("select t.schoolId as schoolId,t.schoolName as schoolName,t.classes as classes,count(1) as num from d_student t group  by t.schoolId ,t.schoolName,t.classes")
    List<StatClasses> getStatClassesList();
}
