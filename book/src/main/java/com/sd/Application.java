package com.sd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Package: com.sd.Application
 * @Description: 
 * @author sudan
 * @date 2020/5/27 16:55
 */
 
@SpringBootApplication
@MapperScan(basePackages = {"com.sd.mapper"})
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}