package com.sd.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @Description: mongo 配置
 * @Package: com.yixin.mongo.MongoConfig
 * @Author Yao.Jie
 * @date 2019/9/25 9:28
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.sd"})
public class MongoConfig {
}
