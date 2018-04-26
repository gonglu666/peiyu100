package com.peiyu100.task;


import com.peiyu100.model.School;
import com.peiyu100.model.Student;
import com.peiyu100.model.Teacher;
import com.peiyu100.model.UpdateStatus;
import com.peiyu100.service.SchoolService;
import com.peiyu100.service.StudentService;
import com.peiyu100.service.TeacherService;
import com.peiyu100.util.DateUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class DStudentJob2 {

    public BlockingQueue<School> schoolQueue = new LinkedBlockingQueue<School>(50000);
    public BlockingQueue<List<Student>> studentListQueue = new LinkedBlockingQueue<List<Student>>(4);

    public int schoolCount;
    public volatile int handleSchoolCount = 0;
    public volatile int studentCount = 0;
    public volatile int handleStudentCount = 0;
    public long  startTimeStamp;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentService;


    public void doJob()  throws Exception{
        List<School> list = schoolService.getList2(0);
        schoolCount  = list.size();
        startTimeStamp = System.currentTimeMillis()/1000;
        System.out.println("学校总数："+schoolCount);
        Thread statThread = new Thread() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag){
                    try {
                        Thread.sleep(10*1000);
                    }catch (Exception e){
                        System.out.println(ExceptionUtils.getStackTrace(e));
                    }
                    long duration = System.currentTimeMillis()/1000 - startTimeStamp;
                    System.out.println("学校总数:【" + schoolCount +"】,已经处理学校数：【" + handleSchoolCount + "】，学生已经读取人数：【"+studentCount+"】,去重后学生人数：【"+handleStudentCount+"】,耗时：【"+ DateUtil.getPrintingTimeStr(duration) + "】");
                    if(schoolCount > handleSchoolCount){
                        int needhandleCount = schoolCount - handleSchoolCount;
                        long needDuration = Math.round(duration * ((double)needhandleCount/(double)handleSchoolCount));
                        System.out.println("剩余学校总数:【" + needhandleCount +"】,预估需要耗时：【"+ DateUtil.getPrintingTimeStr(needDuration) + "】");

                    }else {
                        flag = false;
                    }
                }
            }
        };
        statThread.start();
        for(int i=0;i<list.size();i++){
            School school = list.get(i);
            schoolQueue.put(school);

        }
        for(int j=0;j<3;j++){
            Thread task = new Thread() {
                @Override
                public void run() {
                    boolean flag = true;
                    while (flag){
                        try{
                            School school = schoolQueue.take();
                            if(school.getStatus()!=0){
                                handleSchoolCount++;
                                continue;
                            }
                            List<Student> studentList =  studentService.getListFromSchool(Integer.toString(school.getId()));
                            if(studentList != null &&  studentList.size() > 0 ){
                                studentListQueue.put(studentList);
                                studentCount+=studentList.size();
                            }else {
                                handleSchoolCount++;
                                schoolService.updateStatus(Integer.toString(school.getId()), UpdateStatus.UNABLE_TO_LAND.getValue());
                            }
                        }catch (Exception e){
                            System.out.println(ExceptionUtils.getStackTrace(e));
                        }
                    }
                }
            };
            task.start();
        }

    }
}
