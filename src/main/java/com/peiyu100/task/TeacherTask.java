package com.peiyu100.task;

import com.peiyu100.crawler.TeacherCrawler;
import com.peiyu100.model.Student;
import com.peiyu100.model.Teacher;
import com.peiyu100.model.UpdateStatus;
import com.peiyu100.service.TeacherService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("teacherTask")
@Scope("prototype")
public class TeacherTask extends Thread {

    private String taskName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Autowired
    private TeacherCrawler teacherCrawler;

    @Autowired
    private TeacherJob teacherJob;

    @Autowired
    private TeacherService teacherService;

    @Override
    public void run() {
        System.out.println(getTaskName()+"开始运行");
        while (teacherJob.studentCount == 0 || teacherJob.teacherQueue.size() >0 ){
            try{
                Teacher teacher = teacherJob.teacherQueue.take();
                if(teacher.getStatus() ==  UpdateStatus.UNTREATED.getValue()){
                    doOneJob(teacher);
                }else {
                    teacherJob.handleCount++;
                }
            }catch (Exception e){
                System.out.println(ExceptionUtils.getStackTrace(e));
            }
        }
        System.out.println(getTaskName()+"停止运行");
    }

    protected void doOneJob(Teacher teacher)  throws Exception{
        String loginName = teacher.getLoginName();
        String phpsessid = teacherCrawler.teacherLogin(loginName);
        List<Student> list =  teacherCrawler.teacherStudentList(phpsessid, teacher.getSchoolId(),loginName);
        if(list!=null ){
            teacherJob.studentCount += list.size();
            teacherJob.studentListQueue.put(list);
        }else{
            teacherService.updateStatus(loginName, UpdateStatus.UNABLE_TO_LAND.getValue());
        }
        teacherJob.handleCount++;
    }
}
