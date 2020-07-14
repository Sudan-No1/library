package com.sd.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Package: com.sd.eureka.EurekaApplication
 * @Description: 
 * @author sudan
 * @date 2020/7/10 10:11
 */
 
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaApplication.class)
                .web(true).run(args);
    }
}