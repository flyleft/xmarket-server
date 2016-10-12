package xmarket.rest;

import xmarket.server.entity.transfer.UserProtos;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class ProtobufTest {
    static RestTemplate restTemplate=new RestTemplate();
    @Test
    public void testRead() {
        HttpHeaders headers = new HttpHeaders();
        RequestEntity<UserProtos.User> requestEntity =
                new RequestEntity<UserProtos.User>(headers, HttpMethod.POST, URI.create("http://localhost:80/proto/read"));

        ResponseEntity<UserProtos.User> responseEntity =
                restTemplate.exchange(requestEntity, UserProtos.User.class);

        System.out.println(responseEntity.getBody());
    }

    @Test
    public void testWrite() {
        UserProtos.User user = UserProtos.User.newBuilder().setId(1).setName("zhangsan").build();
        /*HttpHeaders headers = new HttpHeaders();
        RequestEntity<UserProtos.User> requestEntity =
                new RequestEntity<>(user, headers, HttpMethod.POST, URI.create("http://localhost:80/proto/write"));

        ResponseEntity<UserProtos.User> responseEntity =
                restTemplate.exchange(requestEntity, UserProtos.User.class);
        System.out.println(responseEntity.getBody());*/
        System.out.println(user);
    }
}
