package com.peiyu100.dao;

import com.peiyu100.model.School;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolDao {


    @Select("select *  from school order by id asc")
    List<School> getList();

    @Select("select *  from school  where status= #{status} order by id asc")
    List<School> getList2(@Param(value = "status") Integer status);

    @Insert("insert into school (id,name,status) values (#{id},#{name},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(School school);

    @Select("select * from school  where id= #{id}")
    School get(@Param(value = "id") String id);

    @Update("update  school set  status=#{status} where  id=#{id}")
    int updateStatus(@Param(value = "id") String id,@Param(value = "status") Integer status);
}
