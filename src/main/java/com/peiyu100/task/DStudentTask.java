package com.peiyu100.task;

import com.peiyu100.model.DStudent;
import com.peiyu100.model.Student;
import com.peiyu100.model.UpdateStatus;
import com.peiyu100.service.DStudentService;
import com.peiyu100.service.SchoolService;
import com.peiyu100.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("dStudentTask")
@Scope("prototype")
public class DStudentTask  extends Thread {


    @Autowired
    private DStudentService dStudentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DStudentJob2 dStudentJob2;



    @Override
    public void run() {
        while (true){
            try{
                List<Student> list = dStudentJob2.studentListQueue.take();
                if(list != null){
                    updatDb(list);
                    dStudentJob2.handleSchoolCount++;
                }
            }catch (Exception e){
                System.out.println(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Transactional(readOnly = false)
    protected void updatDb(List<Student> list)  throws Exception{
        String id = null;
        Map<String,DStudent> map = new HashMap<>();
        for(int i=0;i<list.size();i++){
            Student student = list.get(i);
            if(i==0){
                id = student.getSchoolId();
            }
            String schoolId = student.getSchoolId();
            String schoolName = schoolService.getSchoolName(schoolId);
            String classes = student.getClasses();
            String name = student.getName();
            String fatherPhone = "";
            String motherPhone = "";
            String parentPhone = "";
            String dataType;
            if(name.indexOf("爸")>=0){
                name = name.replace("爸","");
                fatherPhone = student.getPhone();
                dataType = "爸";
            }else if(name.indexOf("妈")>=0){
                name = name.replace("妈","");
                motherPhone = student.getPhone();
                dataType = "妈";
            }else {
                name = name.replace("家长","");
                parentPhone = student.getPhone();
                dataType = "家长";
            }

            String key = schoolId +"_" + classes +"_" +name;


            DStudent oldDStudent  = map.get(key);
            DStudent dStudent = new DStudent();
            dStudent.setSchoolId(schoolId);
            dStudent.setSchoolName(schoolName);
            dStudent.setClasses(classes);
            dStudent.setName(name);
            if(oldDStudent != null){
                dStudent.setFatherPhone(oldDStudent.getFatherPhone());
                dStudent.setMotherPhone(oldDStudent.getMotherPhone());
                dStudent.setParentPhone(oldDStudent.getParentPhone());
            }
            if("爸".equals(dataType)&&!StringUtils.isEmpty(fatherPhone)){
                dStudent.setFatherPhone(fatherPhone);
            }else  if("妈".equals(dataType)&&!StringUtils.isEmpty(motherPhone)){
                dStudent.setMotherPhone(motherPhone);
            }else if(!StringUtils.isEmpty(parentPhone)){
                dStudent.setParentPhone(parentPhone);
            }
            map.put(key,dStudent);
        }
        dStudentService.delete(id);
        for(DStudent dStudent:map.values()){
            dStudentService.insert(dStudent);

        }
        dStudentJob2.handleStudentCount += map.values().size();
        schoolService.updateStatus(id, UpdateStatus.SUCCESS.getValue());
    }
}
