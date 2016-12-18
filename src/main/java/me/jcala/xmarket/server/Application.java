package me.jcala.xmarket.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScans({
        @ComponentScan("me.jcala.xmarket.server.interceptor"),
        @ComponentScan("me.jcala.xmarket.server.service"),
        @ComponentScan("me.jcala.xmarket.server.conf"),
        @ComponentScan("me.jcala.xmarket.server.repository"),
        @ComponentScan("me.jcala.xmarket.server.ctrl"),
        @ComponentScan("me.jcala.xmarket.server.admin")

})
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
