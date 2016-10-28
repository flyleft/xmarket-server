package me.jcala.xmarket.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * 提供静态资源比如图片的一些公用操作
 */
@Slf4j
public class StaticTool {

    public static String updateAvatar(String restUrl,String picHome,HttpServletRequest request)
            throws Exception {

        //图片在服务器上存储的根路径
        final String imgDir = picHome;

        MultipartFile multipartFile = getMultipartFile(request);

        //设置图片名称为currentTimeMillis+文件后缀
        String fileName = String.valueOf(System.currentTimeMillis()) + "." +
                FileTools.getSuffix(multipartFile.getOriginalFilename());
        //获取当前年月
        String yearMonth = TimeTools.getYearMonthOfNow();

        //图片存储路径为根路径/年月。比如user/jcala/xmarket/201608
        File path = new File(imgDir+ yearMonth);

        //合成图片在服务器上的绝对路径
        File targetFile = new File(imgDir + yearMonth + File.separatorChar + fileName);
        if (!path.exists()) {
            path.mkdirs();
        }
        //保存图片
        multipartFile.transferTo(targetFile);
        return getServerRoot(request) + restUrl + yearMonth + "/" + fileName;//"/api/user/avatar/"
    }

    /**
     * 从HttpServletRequest中获取MultipartFile
     */
    private static MultipartFile getMultipartFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest =
                (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        return multipartRequest.getFile(fileNames.next());
    }

    /**
     * 获取web服务器访问url根路径
     */
    private static String getServerRoot(HttpServletRequest request){
        String serverRoot = "";
        try {
            serverRoot=new URL(request.getScheme(), request.getServerName(), request.getServerPort(),
                    request.getContextPath()).toString();
        } catch (MalformedURLException e) {
            log.warn(e.getLocalizedMessage());
        }
        return serverRoot;
    }
}
