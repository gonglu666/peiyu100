package com.peiyu100.service;

import com.peiyu100.dao.TeacherDao;
import com.peiyu100.model.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    public Teacher get(String loginName){
        return teacherDao.get(loginName);
    }

    public List<Teacher> getList(){
        return teacherDao.getList();
    }


    @Transactional(readOnly = false)
    public int insert(Teacher teacher){
        return teacherDao.insert(teacher);
    }

    @Transactional(readOnly = false)
    public int delete(String schoolId){
        return teacherDao.delete(schoolId);
    }

    @Transactional(readOnly = false)
    public int updateStatus(String loginName,  Integer status){
        return teacherDao.updateStatus(loginName,status);
    }


}
