package com.peiyu100;

import com.peiyu100.common.ApplicationContextProvider;
import com.peiyu100.task.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages="com.peiyu100")
@Lazy(false)
public class Application {


    @Autowired
    private StatJob statJob;


    public static void main(String[] args) {
        System.out.print("开始");
        SpringApplication.run(Application.class, args);
        //爬取学生
        for(int i = 1;i<=4;i++){
            TeacherTask teacherTask =  ApplicationContextProvider.getBean("teacherTask", TeacherTask.class);
            teacherTask.setTaskName("task"+i);
            teacherTask.start();
        }
        StudentTask studentTask =  ApplicationContextProvider.getBean("studentTask", StudentTask.class);
        studentTask.start();
       // 学生去重
        DStudentTask dStudentTask =  ApplicationContextProvider.getBean("dStudentTask", DStudentTask.class);
        dStudentTask.start();

        DStudentTask dStudentTask2 =  ApplicationContextProvider.getBean("dStudentTask", DStudentTask.class);
        dStudentTask2.start();

        DStudentTask dStudentTask3 =  ApplicationContextProvider.getBean("dStudentTask", DStudentTask.class);
        dStudentTask3.start();
    }


    @PostConstruct
    private void  init() throws Exception {
        statJob.doJob();
    }

}
