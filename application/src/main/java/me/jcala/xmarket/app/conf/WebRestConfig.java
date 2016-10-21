package me.jcala.xmarket.app.conf;

import me.jcala.xmarket.app.filter.SimpleCORSFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebRestConfig {
    @Bean
    public SimpleCORSFilter SimpleCORSFilter(){
        return new SimpleCORSFilter();
    }
}
