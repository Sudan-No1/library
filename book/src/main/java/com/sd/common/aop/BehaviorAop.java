package com.sd.common.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.sd.common.annotation.BehaviorLog;
import com.sd.dto.mongo.BehaviorRecord;
import com.sd.service.MongodbService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.sd.common.aop.BehaviorAop
 * @Description: 
 * @author sudan
 * @date 2020/5/29 17:58
 */
 
@Slf4j
@Aspect
@Component
@Order(-2)
public class BehaviorAop {

    @Autowired
    private MongodbService mongodbService;

    @Around("execution(* com.sd.controller..*.*(..)) && " +
            "@annotation(com.sd.common.annotation.BehaviorLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getName();//类名
        String methodName = method.getName();//方法名
        BehaviorLog annotation = method.getAnnotation(BehaviorLog.class);
        String description = annotation.value();//方法描述
        Object[] args = proceedingJoinPoint.getArgs();//方法入参
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object result = proceedingJoinPoint.proceed();//方法出参
        int elapsed = (int) stopwatch.elapsed(TimeUnit.MICROSECONDS);//请求时间
        BehaviorRecord behaviorRecord = BehaviorRecord.builder()
                .className(className)
                .methodName(methodName)
                .description(description)
                .param(JSON.toJSONString(Arrays.toString(args)))
                .result(result)
                .consumedTime(elapsed)
                .build();
        mongodbService.save(behaviorRecord);
        return result;
    }

}