package me.jcala.xmarket.rest;


import org.springframework.stereotype.Component;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Component
@ApplicationPath("/rest/")
public class RestApplication extends Application {

}
