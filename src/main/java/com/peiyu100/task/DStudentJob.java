package com.peiyu100.task;


import com.peiyu100.model.Student;
import com.peiyu100.model.Teacher;
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
public class DStudentJob {

    public BlockingQueue<Teacher> teacherQueue = new LinkedBlockingQueue<Teacher>(50000);
    public BlockingQueue<List<Student>> studentListQueue = new LinkedBlockingQueue<List<Student>>(10);

    public int teacherCount;
    public volatile int handleTeacherCount = 0;
    public volatile int studentCount = 0;
    public volatile int handleStudentCount = 0;
    public long  startTimeStamp;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;


    public void doJob()  throws Exception{
        List<Teacher> list = teacherService.getList();
        teacherCount  = list.size();
        startTimeStamp = System.currentTimeMillis()/1000;
        System.out.println("教师总数："+teacherCount);
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
                    System.out.println("教师总数:【" + teacherCount +"】,已经处理教师数：【" + handleTeacherCount + "】，学生已经读取人数：【"+studentCount+"】,去重后学生人数：【"+handleStudentCount+"】,耗时：【"+ DateUtil.getPrintingTimeStr(duration) + "】");
                    if(teacherCount > handleTeacherCount){
                        int needhandleCount = teacherCount - handleTeacherCount;
                        long needDuration = Math.round(duration * ((double)needhandleCount/(double)handleTeacherCount));
                        System.out.println("剩余教师总数:【" + needhandleCount +"】,预估需要耗时：【"+ DateUtil.getPrintingTimeStr(needDuration) + "】");

                    }else {
                        flag = false;
                    }
                }
            }
        };
        statThread.start();
        for(int i=0;i<list.size();i++){
            Teacher teacher = list.get(i);
            teacherQueue.put(teacher);

        }
        for(int j=0;j<3;j++){
            Thread task = new Thread() {
                @Override
                public void run() {
                    boolean flag = true;
                    while (flag){
                        try{
                            Teacher teacher = teacherQueue.take();
                            List<Student> studentList =  studentService.getList(teacher.getLoginName());
                            if(studentList != null &&  studentList.size() > 0 ){
                                studentListQueue.put(studentList);
                                studentCount+=studentList.size();
                            }
                            handleTeacherCount++;
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
