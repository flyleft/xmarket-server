package me.jcala.xmarket.server.service;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.service.inter.StaticService;
import me.jcala.xmarket.server.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

@Slf4j
public class StaticServiceImpl implements StaticService{

    private ApplicationInfo info;

    @Autowired
    public StaticServiceImpl(ApplicationInfo info) {
        this.info = info;
    }

    @Override
    public ResponseEntity<byte[]> gainPic(String dir, String picName) throws RuntimeException {
        File file=new File(info.getPicHome()+File.separatorChar+dir+File.separatorChar+picName);
        byte[] bytes=new byte[]{};
        try {
            bytes= FileTools.readFileToByteArray(file);
        } catch (IOException e) {
            log.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", picName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }
}
