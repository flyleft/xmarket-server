package me.jcala.xmarket.api.conf;

import me.jcala.xmarket.api.annotation.SwaggerIgnore;
import me.jcala.xmarket.mongo.config.MongoConfig;
import me.jcala.xmarket.ser.conf.ServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@Configuration
@EnableSwagger2
@ComponentScan("me.jcala.xmarket.api.ctrl")
@Import(ServiceConfig.class)
public class SwaggerConfig {
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "me.jcala.xmarket.api.ctrl";
    @Bean
    public Docket merchantStoreApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user-api")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex(".*")))
                .apis(not(withClassAnnotation(SwaggerIgnore.class))) //SwaggerIngore的注解的controller将会被忽略
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .build()
                .apiInfo(testApiInfo());
    }


    private ApiInfo testApiInfo() {
        Contact contact=new Contact("jcala","https://github.com/jcalaz","jcalaz@163.com");
        ApiInfo apiInfo = new ApiInfo("xmarket-server",
                "基于reasteasy的api",
                "0.1",//版本
                "NO terms of service",
                contact,
                "The Apache License, Version 2.0",
                "http://115.28.18.158/"//网站链接
        );
        return apiInfo;
    }


}
