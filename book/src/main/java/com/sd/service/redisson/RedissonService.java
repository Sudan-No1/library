package com.sd.service.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author wangshuqing
 * @Description: 分布式锁
 * @Package: com.yixin.yxcode.open.service.redisson.RedissonService
 * @date 2019/5/22 18:03
 */
@Component
@Slf4j
public class RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * @Description:  分布式锁  默认等待1秒 超时1秒
     * @param key : 锁key值
     * @return java.lang.Boolean
     * @throws 
     * @author wangshuqing
     * @date 2019/5/22 18:10
     */
    public Boolean lock(String key,int waitTime, int timeOut){
        log.info("加锁："+key);
        return lock(key,TimeUnit.SECONDS,waitTime,timeOut,true);
    }

    public void releaseLock(String key) {
        RLock lock = redissonClient.getLock(key);
        if(!lock.isHeldByCurrentThread()){
            log.info("超时自动放锁:"+key);
        }else{
            log.info("释放锁:"+key);
            lock.unlock();
        }
    }

    public Boolean lock(String key, TimeUnit unit, int waitTime, int timeOut, boolean isAsync){
        if (waitTime <= 0) {
            waitTime = 0;
        }
        if (timeOut <= 0) {
            timeOut = 0;
        }
        if (unit == null){
            unit = TimeUnit.SECONDS;
        }
        RLock lock = redissonClient.getLock(key);
        boolean result = false;
        try {
            //尝试加锁，最多等待waitTime ，上锁以后timeOut 自动解锁 (单位：unit)
            if (isAsync) {
                Future<Boolean> future = lock.tryLockAsync(waitTime, timeOut, unit);
                result = future.get();
            }else{
                result = lock.tryLock(waitTime, timeOut, unit);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
        }
        return result;
    }


}
