package com.peiyu100.task;

import com.peiyu100.dao.StatDao;
import com.peiyu100.excel.StatClassesExcel;
import com.peiyu100.excel.StatSchoolExcel;
import com.peiyu100.model.StatClasses;
import com.peiyu100.model.StatSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatJob {

    @Autowired
    private StatDao statDao;

    public void doJob()  throws Exception{
        List<StatSchool> statSchoolList  = statDao.getStatSchoolList();
        StatSchoolExcel.report(statSchoolList);

        List<StatClasses> statClassesList  = statDao.getStatClassesList();
        StatClassesExcel.report(statClassesList);
    }
}
