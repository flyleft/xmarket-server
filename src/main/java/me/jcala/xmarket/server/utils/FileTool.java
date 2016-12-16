package me.jcala.xmarket.server.utils;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.conf.RestConfig;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.util.StreamUtils.copy;

/**
 * 提供静态资源比如图片的一些公用操作
 */
@Slf4j
public class FileTool {

    public static String uploadFile(String picHome,HttpServletRequest request)
            throws Exception {
        MultipartHttpServletRequest multipartRequest =
                (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        if (!fileNames.hasNext()){
            return null;
        }
        MultipartFile multipartFile = multipartRequest.getFile(fileNames.next());

        //设置图片名称为currentTimeMillis+文件后缀
        String fileName = String.valueOf(System.currentTimeMillis()) + "." +
                getSuffix(multipartFile.getOriginalFilename());
        //获取当前年月
        String yearMonth = TimeTools.getYearMonthOfNow();

        //图片存储路径为根路径/年月。比如user/jcala/xmarket/201608
        File path = new File(picHome+File.separatorChar+ yearMonth);
        if (!path.exists()) {
            path.mkdirs();
        }

        //合成图片在服务器上的物理绝对路径
        File targetFile = new File(picHome+File.separatorChar + yearMonth + File.separatorChar + fileName);
        //保存图片
        multipartFile.transferTo(targetFile);
        return RestConfig.picUrlPath + yearMonth + "/" + fileName;
    }

    public static List<String> uploadMultiFiles(String picHome,HttpServletRequest request) throws IOException{
        List<String> imgUrls=new ArrayList<>();
        MultipartHttpServletRequest multipartRequest =
                (MultipartHttpServletRequest) request;

        List<MultipartFile> files=multipartRequest.getFiles("file");

        String yearMonth = TimeTools.getYearMonthOfNow();

        String picDir=picHome+File.separatorChar+ yearMonth;

        File path = new File(picDir);

        String imgUrl=RestConfig.picUrlPath + yearMonth + "/";

        if (!path.exists()) {
            path.mkdirs();
        }

        for (MultipartFile file:files){
            String fileName = String.valueOf(System.currentTimeMillis()) + "." +
                    getSuffix(file.getOriginalFilename());
            File targetFile = new File(picDir + File.separatorChar + fileName);
            file.transferTo(targetFile);
            imgUrls.add(imgUrl+fileName);
        }
        return imgUrls;
    }


    /**
     * 获取web服务器访问url根路径
     */
    /*private static String getServerRoot(HttpServletRequest request){
        String serverRoot = "";
        try {
            serverRoot=new URL(request.getScheme(), request.getServerName(), request.getServerPort(),
                    request.getContextPath()).toString();
        } catch (MalformedURLException e) {
            log.warn(e.getLocalizedMessage());
        }
        return serverRoot;
    }*/
    /**
     * 获取文件后缀
     */
    public   static String getSuffix(String fileName){
        String[] token = fileName.split("\\.");
        if (token.length>0){
            return token[token.length-1];
        }
        else {
            return "";
        }
    }

    /**
     * 读取文件为字节数组
     */
    public static byte[] readFileToByteArray(final File file) throws IOException {
        InputStream in = openInputStream(file);
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(in, output);
        return output.toByteArray();
    }
    private static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new RuntimeException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }
}
