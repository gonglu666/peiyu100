package com.peiyu100.task;

import com.peiyu100.model.Student;
import com.peiyu100.model.UpdateStatus;
import com.peiyu100.service.StudentService;
import com.peiyu100.service.TeacherService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("studentTask")
@Scope("prototype")
@Transactional(readOnly = true)
public class StudentTask extends Thread {


    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherJob teacherJob;



    @Override
    public void run() {
        while (true){
            try{
                List<Student> list = teacherJob.studentListQueue.take();
                if(list != null){
                    updatDb(list);
                }

            }catch (Exception e){
                System.out.println(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Transactional(readOnly = false)
    protected void updatDb(List<Student> list)  throws Exception{
        String teacherLoginName = null;
        for(int i=0;i<list.size();i++){
            Student student = list.get(i);
            if(i==0){
                teacherLoginName = student.getTeacherLoginName();
            }
            studentService.insert(student);
        }
        teacherService.updateStatus(teacherLoginName, UpdateStatus.SUCCESS.getValue());
    }
}
