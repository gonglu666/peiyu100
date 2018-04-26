package com.peiyu100.task;

import com.peiyu100.crawler.SchoolCrawler;
import com.peiyu100.model.School;
import com.peiyu100.model.Teacher;
import com.peiyu100.model.UpdateStatus;
import com.peiyu100.service.SchoolService;
import com.peiyu100.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SchoolJob {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolCrawler schoolCrawler;

    @Autowired
    private TeacherService teacherService;

    @Transactional(readOnly = false)
    public void doJob()  throws Exception{
        System.out.println("=====抓取学校  start ======");
        List<School> list = schoolService.getList();
        for(int i=0;i<list.size();i++){
            School school = list.get(i);
            System.out.println("学校总数："+list.size()+",当前更新第"+(i+1)+"个学校");
            doOneJob(school);
        }
        System.out.println("=====抓取学校  end ======");
    }


    @Transactional(readOnly = false)
    protected void doOneJob(School school)  throws Exception{
        if(school == null || school.getStatus() != 0){
            return;
        }
        String schoolId = Integer.toString(school.getId());
        //学校登录
        String phpsessid = schoolCrawler.schoolLogin(schoolId);
        if(StringUtils.isEmpty(phpsessid)){
            System.out.println(school.getName()+"登录失败");
            schoolService.updateStatus(schoolId, UpdateStatus.UNABLE_TO_LAND.getValue());
            return;
        }
        //抓取教师列表
        List<Teacher> list = schoolCrawler.schooTeacherList(phpsessid,schoolId);
        if(list == null || list.size() == 0){
            System.out.println(school.getName()+"更新教师数为0");
            schoolService.updateStatus(schoolId, UpdateStatus.SUCCESS.getValue());
            return;
        }
        //删除数据库该学校的所有老师
        teacherService.delete(schoolId);
        //插入新的老师列表
        for( Teacher teacher: list){
            Teacher t = teacherService.get(teacher.getLoginName());
            if(t == null){
                teacherService.insert(teacher);
            }
        }
        System.out.println(school.getName()+"更新教师数为"+list.size());
        schoolService.updateStatus(schoolId, UpdateStatus.SUCCESS.getValue());
    }
}
