package me.jcala.xmarket.server.other;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Pic2String {
    public static void main(String args[]) throws Exception{
        String ss=readFromPic("G:\\tag.jpg");
        System.out.println(Md5.getMD5(readFromPic(ss)));
    }


   public static String readFromPic(String fileUrl){

       File file=new File(fileUrl);
       if (!file.exists()){
           System.out.println("文件不存在");
           try {
               file.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return null;
       }
       StringBuilder builder=new StringBuilder();
       BufferedInputStream inputStream=null;
       try {
           inputStream=new BufferedInputStream(new FileInputStream(file));
           int bytesRead = 0;
           byte[] buffer = new byte[1024];
           while ((bytesRead = inputStream.read(buffer)) != -1) {
               builder.append(new String(buffer, 0, bytesRead));
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (inputStream!=null){
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           return builder.toString();
       }
   }
}
