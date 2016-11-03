package me.jcala.xmarket.server.conf;

import me.jcala.xmarket.server.annotation.SwaggerIgnore;
import me.jcala.xmarket.server.entity.configuration.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
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
@Configuration
@EnableSwagger2//使swagger配置生效
public class RestConfig {
    @Value("${pic.home}")
    private String picHome;

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
   public ApplicationInfo applicationInfo(){
       return ApplicationInfo.builder()
                             .picHome(picHome)
                             .build();
   }
}
