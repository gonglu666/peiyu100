package com.peiyu100.service;

import com.alibaba.fastjson.JSON;
import com.peiyu100.dao.SchoolDao;
import com.peiyu100.model.School;
import com.peiyu100.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SchoolService {

    @Autowired
    private SchoolDao schoolDao;


    private Map<String,String> schoolMap = null;


    public List<School> getList(){
        return schoolDao.getList();
    }

    public List<School> getList2(int status){
        return schoolDao.getList2(status);
    }

    public School get(String id){
        return schoolDao.get(id);
    }


    @Transactional(readOnly = false)
    public int insert(School school){
        return schoolDao.insert(school);
    }

    @Transactional(readOnly = false)
    public int updateStatus(String id,  Integer status){
        return schoolDao.updateStatus(id,status);
    }


    public List<School> getSchoolListFromFile() {
        String projectPath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        String filePath = projectPath + fileSeparator + "src/main/resources/school_list.json";
        try{
            String jsonStr = FileUtil.readTxtFile(new File(filePath));
            List<School> list = JSON.parseArray(jsonStr,School.class);
            for(School school:list){
                school.setStatus(0);
            }
            return list;
        }catch (Exception e){
            return null;
        }
    }

    @Transactional(readOnly = false)
    public void initSchoolDataBase(){
        List<School> list = getSchoolListFromFile();
        for(School school:list){
            insert(school);
        }
    }

    public String getSchoolName(String schoolId){
        if(schoolMap == null){
            List<School> list = getList();
            schoolMap = new HashMap<>();
            for(School school:list){
                String id = Integer.toString(school.getId());
                String name = school.getName().replace(id+"-","").trim();
                schoolMap.put(id,name);
            }
        }
        //System.out.println(JSON.toJSON(schoolMap));
        return schoolMap.get(schoolId);
    }
}
