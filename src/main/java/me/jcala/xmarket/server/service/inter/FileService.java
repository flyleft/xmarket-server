package me.jcala.xmarket.server.service.inter;

import org.springframework.http.ResponseEntity;

public interface FileService {
     ResponseEntity<byte[]> gainPic(String dir, String picName);
}
