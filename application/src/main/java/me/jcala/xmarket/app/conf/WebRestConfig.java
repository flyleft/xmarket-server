package me.jcala.xmarket.app.conf;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebRestConfig {
  /* @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:80");
        config.addAllowedHeader("Origin, X-Requested-With, Content-Type, Accept");
        config.addAllowedMethod("POST, GET, DELETE, PUT");
        source.registerCorsConfiguration("*//**", config);
        final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }*/
}
