package com.peiyu100.crawler;

import com.alibaba.fastjson.JSON;
import com.peiyu100.model.Pair;
import com.peiyu100.model.Student;
import com.peiyu100.model.Teacher;
import com.peiyu100.util.HttpUitl;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Service
public class TeacherCrawler {

    public String teacherLogin(String loginName) throws Exception {
        String url = "http://cms.peiyu100.com/login/index/teacher";
        Map<String,String> map = new HashMap<>();
        map.put("username",loginName);
        map.put("password","123456");
        Connection.Response response = HttpUitl.httpPostLogin(url,map);
        Map<String, String> cookies = response.cookies();
        if(cookies.containsKey("PHPSESSID")){
            return cookies.get("PHPSESSID");
        }
        return null;
    }

    public List<Student> teacherStudentList(String phpsessid, String schoolId,String teacherLoginName)throws Exception{
        String cookie = "PHPSESSID="+phpsessid;
        String url = "http://cms.peiyu100.com/index.php/contact";
        Document doc = HttpUitl.httpGet(url,cookie);
        Elements ul =  null;
        try{
            ul = doc.getElementsByClass("parents").first().getElementsByTag("li");
        }catch (Exception e){
            //System.out.println(ExceptionUtils.getStackTrace(e));
            return null;
        }
        List<Pair<String, String>> userIdList = new ArrayList<Pair<String, String>>();
        for(int i = 0;i<ul.size();i++) {
            Element li = ul.get(i);
            String userId = li.attr("data-user-id");
            String cId = li.attr("data-cid");
            if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(cId)){
                continue;
            }
            userIdList.add(new Pair<String, String>(userId,cId));
        }
        if(userIdList == null || userIdList.size() == 0){
            return null;
        }
        List<Student> list = new ArrayList<>();
        for( Pair<String, String> pair : userIdList){
            try{
                String userId = pair.getFirst();
                String cId = pair.getSecond();
                Student student = getStudentInfo(userId,cId,phpsessid,schoolId,teacherLoginName);
                if(student != null){
                    list.add(student);
                }
            }catch (Exception e){
                System.out.println(ExceptionUtils.getStackTrace(e));
            }
        }
        return list;
    }

    public Student getStudentInfo(String userId,String cId,String phpsessid, String schoolId,String teacherLoginName)throws Exception{
        String cookie = "PHPSESSID="+phpsessid;
        String url = "http://cms.peiyu100.com/index.php/contact/detail/"+userId+"/"+cId;
        Document doc = HttpUitl.httpGet(url,cookie);
        Elements ps =  null;
        try{
            ps = doc.getElementsByClass("wen").first().getElementsByTag("p");
        }catch (Exception e){
            System.out.println(ExceptionUtils.getStackTrace(e));
            return null;
        }
        if(ps.size()<5){
            return null;
        }
        String name = ps.get(0).text().replace("姓名","").replaceAll("\u00A0","");
        String sex = ps.get(2).text().trim().replace("性别","").replaceAll("\u00A0","");
        String classes  = ps.get(3).text().trim().replace("班级","").replaceAll("\u00A0","");
        String phone = ps.get(4).text().trim().replace("手机号","").replaceAll("\u00A0","");

        Student student = new Student();
        student.setSchoolId(schoolId);
        student.setTeacherLoginName(teacherLoginName);
        student.setName(name);
        student.setSex(sex);
        student.setPhone(phone);
        student.setClasses(classes);
        return student;
    }
}
