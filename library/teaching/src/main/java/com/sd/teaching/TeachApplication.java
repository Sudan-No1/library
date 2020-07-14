package com.sd.teaching;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @Package: com.sd.teaching.TeachApplication
 * @Description: 
 * @author sudan
 * @date 2020/7/10 10:42
 */
 
@SpringBootApplication
@EnableEurekaClient
public class TeachApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TeachApplication.class)
                .web(true).run(args);
    }

}