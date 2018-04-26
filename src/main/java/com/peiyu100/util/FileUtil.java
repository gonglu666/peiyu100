package com.peiyu100.util;

import java.io.*;


public class FileUtil {

    private static long G_SIZE = 1024*1024*1024;
    private static long M_SIZE = 1024*1024;
    private static long KB_SIZE = 1024;


    /**创建文件*/
    public static boolean createFile(File fileName)throws Exception{
        boolean flag=false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag=true;
            }else{
                fileName.delete();
                fileName.createNewFile();
                flag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /** 读TXT文件内容*/
    public static String readTxtFile(File fileName)throws Exception{
        String result="";
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            fileReader=new FileReader(fileName);
            bufferedReader=new BufferedReader(fileReader);
            try{
                String read="";
                while((read=bufferedReader.readLine())!=null){
                    result=result+read+"\r\n";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        }
        if("".equals(result)){
            return null;
        }
        return result;
    }

    public static boolean writeTxtFile(String content,File  fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("UTF-8"));
            o.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }

    /***
     * 根据文件长度生成长度字符串
     * @param fileSize
     * @return
     */
    public static String getFlieSizeStr(long fileSize){
        if(fileSize>G_SIZE){
            long gSize = fileSize/G_SIZE;
            if(gSize*G_SIZE == fileSize){
                return gSize+"g";
            }else {
                long surplusMSize = fileSize - gSize*G_SIZE;
                if(surplusMSize > M_SIZE){
                    long mSize =   surplusMSize/M_SIZE;
                    if(mSize*M_SIZE == fileSize){
                        return gSize+"g"+mSize+"m";
                    }else {
                        long surplusKbSize = surplusMSize - mSize*M_SIZE;
                        long kSize = surplusKbSize/KB_SIZE;
                        return gSize+"g"+mSize+"m" + kSize +"kb";
                    }
                }else{
                    long kSize = surplusMSize/KB_SIZE;
                    return gSize+"g"+ kSize +"kb";
                }
            }
        }else {
            if(fileSize > M_SIZE){
                long mSize =   fileSize/M_SIZE;
                if(mSize*M_SIZE == fileSize){
                    return mSize+"m";
                }else {
                    long surplusKbSize = fileSize - mSize*M_SIZE;
                    long kSize = surplusKbSize/KB_SIZE;
                    return mSize+"m" + kSize +"kb";
                }
            }else{
                long kSize = fileSize/KB_SIZE;
                return kSize +"kb";
            }
        }
    }
}
