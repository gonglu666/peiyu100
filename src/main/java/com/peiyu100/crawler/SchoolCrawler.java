package com.peiyu100.crawler;

import com.peiyu100.model.Pair;
import com.peiyu100.model.Teacher;
import com.peiyu100.util.HttpUitl;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolCrawler {

    /**
     * 学校登录
     * @param id
     * @return
     * @throws Exception
     */
    public String schoolLogin(String id) throws Exception {
        String url = "http://cms.peiyu100.com/login/index/admin";
        Map<String,String> map = new HashMap<>();
        map.put("username","admin" + id);
        map.put("password",id);
        Connection.Response response = HttpUitl.httpPostLogin(url,map);
        Map<String, String> cookies = response.cookies();
        if(cookies.containsKey("PHPSESSID")){
            return cookies.get("PHPSESSID");
        }
        return null;
    }


    public List<Teacher> schooTeacherList(String phpsessid,String schoolId)throws Exception{
        String cookie = "PHPSESSID="+phpsessid;
        String url = "http://cms.peiyu100.com/index.php/data/school/teachers";
        Document doc = HttpUitl.httpGet(url,cookie);
        Elements trs =  null;
        try{
            trs = doc.getElementById("teachers-table").select("tbody").select("tr");
        }catch (Exception e){
            return null;
        }

        List<Teacher> list = new ArrayList<>();
        for(int i = 0;i<trs.size();i++){
            Elements tds = trs.get(i).select("td");
            String name = tds.get(0).text();
            String loginName= tds.get(1).text();
            String sex= tds.get(2).text();
            String phone= tds.get(3).text();
            String isHeadmaster= tds.get(4).text();
            String duties= tds.get(5).text();

            Teacher teacher = new Teacher();
            teacher.setSchoolId(schoolId);
            teacher.setName(name);
            teacher.setLoginName(loginName);
            teacher.setSex(sex);
            teacher.setPhone(phone);
            teacher.setIsHeadmaster(isHeadmaster);
            teacher.setDuties(duties);
            teacher.setStatus(0);
            list.add(teacher);
        }
        return list;
    }
}