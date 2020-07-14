package com.sd.teaching;

import com.sd.teaching.common.util.IdWorker;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @Package: com.sd.teaching.TeachApplication
 * @Description: 
 * @author sudan
 * @date 2020/7/10 10:42
 */
 
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.sd.teaching.mapper"})
public class TeachApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TeachApplication.class)
                .web(true).run(args);
    }

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(0,0);
    }
}