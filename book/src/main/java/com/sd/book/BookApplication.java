package com.sd.book;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Package: com.sd.book.BookApplication
 * @Description: 启动类
 * @author sudan
 * @date 2020/7/10 10:26
 */
 
@EnableEurekaClient
@SpringBootApplication
public class BookApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BookApplication.class)
                .web(true).run(args);
    }

}