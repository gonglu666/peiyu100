package com.peiyu100.task;

import com.peiyu100.excel.StudentExcel;
import com.peiyu100.model.DStudent;
import com.peiyu100.model.School;
import com.peiyu100.service.DStudentService;
import com.peiyu100.service.SchoolService;
import com.peiyu100.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportJob {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DStudentService dStudentService;

    public void doJob()  throws Exception{
        long startTimeStamp = System.currentTimeMillis()/1000;
        List<School> list = schoolService.getList();
        for(int i=0;i<list.size();i++){
            School school = list.get(i);
            List<DStudent> slist = dStudentService.getListFromSchool(Integer.toString(school.getId()));
            if(slist != null && slist.size() > 0){
                String fileName = schoolService.getSchoolName(Integer.toString(school.getId()));
                StudentExcel.report(fileName, slist);
            }
            long duration = System.currentTimeMillis()/1000 - startTimeStamp;
            System.out.println("学校总数：【"+list.size()+"】,当前处理第【"+(i+1)+"】个学校,耗时：【"+ DateUtil.getPrintingTimeStr(duration) + "】");
        }
    }


    public void doAllJob()  throws Exception{
        List<DStudent> slist = dStudentService.getList();
        if(slist != null && slist.size() > 0){
            StudentExcel.report("全部学校", slist);
        }
    }

}
