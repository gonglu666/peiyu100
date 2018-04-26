package com.peiyu100.service;

import com.peiyu100.dao.StudentDao;
import com.peiyu100.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Transactional(readOnly = false)
    public int insert(Student student){
        return studentDao.insert(student);
    }

    public List<Student> getList(String teacherLoginName){
        return studentDao.getList(teacherLoginName);
    }

    public List<Student> getListFromSchool(String scholId){
        return studentDao.getListFromSchool(scholId);
    }
}
