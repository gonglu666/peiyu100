package com.peiyu100.util;

public class DateUtil {

    public static String getPrintingTimeStr(long duration){
        String msg = "";
        long day = duration/(24*60*60);
        if(day > 0){
            msg += (day + "天");
        }
        long duration2 = duration - day*24*60*60;
        long hour = duration2/(60*60);
        if(hour > 0){
            msg += (hour + "小时");
        }
        long duration3 = duration2 - hour*60*60;
        long min = duration3/60;
        if(min > 0){
            msg += (min + "分钟");
        }
        long second = duration3 - min*60;
        msg += second + "秒";
        return msg;
    }
}
