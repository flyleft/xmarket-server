package me.jcala.xmarket.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;

@Slf4j
public class UploadPicTool {
    private MultipartFile getMultipartFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest =
                (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        return multipartRequest.getFile(fileNames.next());
    }
    public String updateAvatar(String picHome,HttpServletRequest request)
            throws RuntimeException {
        log.error(request.getRequestURI());
        final String imgDir = picHome;
        final String picUrlPath = "";
        MultipartFile multipartFile = getMultipartFile(request);
        //设置图片名称
        String fileName = String.valueOf(System.currentTimeMillis()) + "." +
                FileTools.getSuffix(multipartFile.getOriginalFilename());
        String yearMonth = TimeTools.getYearMonthOfNow();
        File path = new File(imgDir+ yearMonth);
        File targetFile = new File(imgDir + yearMonth + File.separatorChar + fileName);
        String url="";
        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            multipartFile.transferTo(targetFile);
            url=picUrlPath + yearMonth + "/" + fileName;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return url;
    }
}
