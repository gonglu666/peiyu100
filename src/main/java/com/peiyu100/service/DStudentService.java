package com.peiyu100.service;


import com.peiyu100.dao.DStudentDao;
import com.peiyu100.model.DStudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DStudentService {

    @Autowired
    private DStudentDao dStudentDao;

    @Transactional(readOnly = false)
    public int insert(DStudent dStudent){
        return dStudentDao.insert(dStudent);
    }

    @Transactional(readOnly = false)
    public int update(DStudent dStudent){
        return dStudentDao.update(dStudent);
    }

    public  DStudent get(String schoolId,String classes,String name){
        return dStudentDao.get(schoolId,classes,name);
    }

    @Transactional(readOnly = false)
    public int delete(String schoolId){
        return dStudentDao.delete(schoolId);
    }



    public List<DStudent> getListFromSchool(String schoolId){
        return dStudentDao.getListFromSchool(schoolId);
    }

    public List<DStudent> getList(){
        return dStudentDao.getList();
    }
}
