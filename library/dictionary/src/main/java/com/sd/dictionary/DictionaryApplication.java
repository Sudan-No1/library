package com.sd.dictionary;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Package: com.sd.dictionary.DictionaryApplication
 * @Description: 
 * @author sudan
 * @date 2020/7/10 10:21
 */
 
@SpringBootApplication
@EnableEurekaClient
public class DictionaryApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DictionaryApplication.class)
                .web(true).run(args);
    }

}