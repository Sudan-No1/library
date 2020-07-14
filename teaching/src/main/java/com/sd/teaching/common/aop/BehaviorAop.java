package com.sd.teaching.common.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.sd.teaching.common.annotions.BehaviorLog;
import com.sd.teaching.model.mongo.BehaviorRecord;
import com.sd.teaching.service.mongo.MongodbService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.sd.teaching.common.aop.BehaviorAop
 * @Description: 
 * @author sudan
 * @date 2020/7/14 15:08
 */
 
@Slf4j
@Aspect
@Component
@Order(2)
public class BehaviorAop {

    @Autowired
    private MongodbService mongodbService;

    @Around("execution(* com.sd.teaching.controller..*.*(..)) &&" +
            "@annotation(com.sd.teaching.common.annotions.BehaviorLog)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        BehaviorLog annotation = method.getAnnotation(BehaviorLog.class);
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String description = annotation.value();
        Object[] param = proceedingJoinPoint.getArgs();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object result = proceedingJoinPoint.proceed();
        String elapsed = stopwatch.elapsed(TimeUnit.MICROSECONDS)/1000000+"s";//请求时间
        BehaviorRecord behaviorRecord = BehaviorRecord.builder()
                .className(className)
                .consumedTime(elapsed)
                .description(description)
                .methodName(methodName)
                .param(JSON.toJSONString(param))
                .result(result)
                .build();
        mongodbService.save(behaviorRecord);
        return result;
    }

}