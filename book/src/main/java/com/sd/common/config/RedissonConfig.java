package com.sd.common.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.*;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.yixin.yxcode.common.configuration.redission.RedissonConfig
 * @Description: 配置类
 * @author Administrator
 * @date 2018/11/17 10:57
 */
@Configuration
@ConditionalOnProperty(value = "spring.redis.cluster.nodes")
public class RedissonConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.cluster.nodes}")
    private String[] redissonnodes;

    public Config getClusterConfig() {
        Config config = new Config();
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for (int i = 0; i < redissonnodes.length; i++) {
            redissonnodes[i] = "redis://"+redissonnodes[i].trim();
        }
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        if (StringUtils.isNotBlank(password)) {
            clusterServersConfig.setPassword(password);
        }
        clusterServersConfig
                .setScanInterval(2000)// 集群状态扫描间隔时间，单位是毫秒
                .setMasterConnectionPoolSize(500)//设置对于master节点的连接池中连接数最大为500
                .setSlaveConnectionPoolSize(500)//设置对于slave节点的连接池中连接数最大为500
                .setIdleConnectionTimeout(10000)//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关
                .setConnectTimeout(30000)//同任何节点建立连接时的等待超时。时间单位是毫秒。
                .setTimeout(10000)//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
                .setPingTimeout(30000)
                .setRetryAttempts(5)
                .setRetryInterval(3000)
                .setFailedSlaveCheckInterval(3000)
                .setFailedSlaveReconnectionInterval(60000)
                .setPingConnectionInterval(60000)
                .setSubscriptionsPerConnection(5)
                .setLoadBalancer(new RoundRobinLoadBalancer())
                .setSubscriptionConnectionMinimumIdleSize(50)
                .setSlaveConnectionMinimumIdleSize(32)
                .setReadMode(ReadMode.SLAVE)
                .setSubscriptionMode(SubscriptionMode.SLAVE)
                .setScanInterval(1000)
                .addNodeAddress(redissonnodes);
        config.setCodec(new JsonJacksonCodec());
        /**监控锁的看门狗超时 **/
        config.setLockWatchdogTimeout(6000);
        //当前处理核数量 * 2
        config.setThreads(8);
        config.setNettyThreads(8);
        config.setTransportMode(TransportMode.NIO);
        return config;
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = getClusterConfig();
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }


}