package com.peiyu100.dao;


import com.peiyu100.model.School;
import com.peiyu100.model.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherDao {


    @Select("select *  from teacher where status ='0'")
    List<Teacher> getList();

    @Insert("insert into teacher (schoolId,name,loginName,sex,phone,isHeadmaster,duties,status) values (#{schoolId},#{name},#{loginName},#{sex},#{phone},#{isHeadmaster},#{duties},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Delete("delete  from teacher where schoolId = #{schoolId} ")
    int delete(@Param(value = "schoolId") String schoolId);

    @Select("select * from teacher  where loginName= #{loginName}")
    Teacher get(@Param(value = "loginName") String loginName);

    @Update("update  teacher set  status=#{status} where  loginName=#{loginName}")
    int updateStatus(@Param(value = "loginName") String loginName,@Param(value = "status") Integer status);
}
