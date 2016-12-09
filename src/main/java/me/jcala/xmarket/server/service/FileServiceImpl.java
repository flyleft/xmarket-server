package me.jcala.xmarket.server.service;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.service.inter.FileService;
import me.jcala.xmarket.server.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private ApplicationInfo info;

    @Autowired
    public FileServiceImpl(ApplicationInfo info) {
        this.info = info;
    }

    @Override
    public ResponseEntity<byte[]> gainPic(String dir, String picName) throws RuntimeException {
        File file=new File(info.getPicHome()+File.separatorChar+dir+File.separatorChar+picName);
        byte[] bytes;
        try {
            bytes= FileTools.readFileToByteArray(file);
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", picName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
