package me.jcala.xmarket.server.conf;

import lombok.extern.slf4j.Slf4j;
import me.jcala.xmarket.server.annotation.SwaggerIgnore;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import me.jcala.xmarket.server.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

/**
 *数据非空校验放到客户端,而服务器端不进行数据校验
 * 与rest相关的config
 */
@Slf4j
@Configuration
@EnableSwagger2//使swagger配置生效
public class RestConfig extends WebMvcConfigurerAdapter {
    public static final String picUrlPath="/api/v1/file/img/";
    @Value("${xmarket.pic_home}")
    private String picHome;

    @Value("${xmarket.address")
    private String address;

    @Value("${xmarket.jwt.key}")
    private String jwtKey;

    @Value("${xmarket.jwt.life}")
    private String jwtLife;

    @SuppressWarnings("unchecked")
    @Bean
    public Docket ordinaryApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ordinary-api")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(regex(".*")))
                .apis(not(withClassAnnotation(SwaggerIgnore.class)))
                .apis(RequestHandlerSelectors.basePackage("me.jcala.xmarket.server.ctrl"))
                .build()
                .apiInfo(infoBuilder().description("普通用户的api后端").build());
    }

    @SuppressWarnings("unchecked")
    @Bean
   public Docket adminApi(){
       return new Docket(DocumentationType.SWAGGER_2)
               .groupName("admin-api")
               .genericModelSubstitutes(DeferredResult.class)
               .useDefaultResponseMessages(false)
               .forCodeGeneration(true)
               .pathMapping("/")
               .select()
               .paths(or(regex(".*")))
               .apis(not(withClassAnnotation(SwaggerIgnore.class)))
               .apis(RequestHandlerSelectors.basePackage("me.jcala.xmarket.server.admin.ctrl"))
               .build()
               .apiInfo(infoBuilder().description("管理员的api后端").build());
   }

    private ApiInfoBuilder infoBuilder(){
        return new ApiInfoBuilder().title("xmarket-server")
                .version("0.1.0")
                .license("MIT")
                .termsOfServiceUrl("http://115.28.18.158/")
                .contact(new Contact("jcala","https://github.com/jcalaz","jcalaz@163.com"));
    }
    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }
    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST","DELETE"));
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    /**
     *Validator验证器
     */
   /* @Bean
    public LocalValidatorFactoryBean validator(){
        LocalValidatorFactoryBean validatorFactoryBean=new LocalValidatorFactoryBean();
        validatorFactoryBean.setProviderClass(HibernateValidator.class);
        return validatorFactoryBean;
    }*/
    @Bean
    public boolean applicationInfoInit(){
        log.info("初始化访问地址，jwt，图片位置等系统变量");
        long life= 3600000;
        try {
            life = Long.parseLong(jwtLife);
        } catch (Exception e) {
            log.error("jwt life必须为数字!系统将采用默认值3600000毫秒");
        }
        ApplicationInfo.setAddress(address);
        ApplicationInfo.setPicHome(picHome);
        ApplicationInfo.setJwtKey(jwtKey);
        ApplicationInfo.setJwtLife(life);
        return true;
    }

}
