package com.peiyu100.dao;

import com.peiyu100.model.DStudent;
import com.peiyu100.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DStudentDao {

    @Insert("insert into d_student (schoolId,schoolName,classes,name,fatherPhone,motherPhone,parentPhone) values (#{schoolId},#{schoolName},#{classes},#{name},#{fatherPhone},#{motherPhone},#{parentPhone})")
    int insert(DStudent dStudent);

    @Update("update  d_student set  fatherPhone=#{fatherPhone},motherPhone=#{motherPhone},parentPhone=#{parentPhone} where  schoolId=#{schoolId}  and classes=#{classes}  and  name=#{name} ")
    int update(DStudent dStudent);

    @Select("select * from d_student where  schoolId=#{schoolId}  and classes=#{classes}  and  name=#{name}")
    DStudent get(@Param(value = "schoolId") String schoolId,
                 @Param(value = "classes") String classes,
                 @Param(value = "name") String name);


    @Delete("delete  from d_student where schoolId = #{schoolId} ")
    int delete(@Param(value = "schoolId") String schoolId);


    @Select("select *  from d_student  where schoolId=#{schoolId} order by schoolId asc,classes asc,name asc")
    List<DStudent> getListFromSchool(@Param(value = "schoolId") String schoolId);


    @Select("select *  from d_student  order by schoolId asc,classes asc,name asc")
    List<DStudent> getList();
}
