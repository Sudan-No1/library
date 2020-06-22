package com.sd.aop;

import com.sd.annotation.RepeatParam;
import com.sd.annotation.RepeatSubmit;
import com.sd.exception.BusinessException;
import com.sd.service.redisson.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Package: com.sd.aop.RepeatSubmitAop
 * @Description: 
 * @author sudan
 * @date 2020/6/17 16:42
 */
 
@Component
@Aspect
@Order(-3)
@Slf4j
public class RepeatSubmitAop {

    @Autowired
    private RedissonService redissonService;


    @Around("execution(* com.sd.controller..*.*(..))")
    public Object around(ProceedingJoinPoint pjo) throws Throwable{
        MethodSignature methodSignature = (MethodSignature) pjo.getSignature();
        Method method = methodSignature.getMethod();
        RepeatSubmit repeatSubmit = method.getAnnotation(RepeatSubmit.class);
        boolean flag = false;
        String key = null;
        if(repeatSubmit != null){
            Object[] args = pjo.getArgs();
            if(args == null || args.length == 0){
                log.error("方法{}没有参数，无法防重复提交");
            }else{
                for (Object object : args){
                    Field[] fields = object.getClass().getDeclaredFields();
                    for (Field field : fields){
                        RepeatParam repeatParam = field.getAnnotation(RepeatParam.class);
                        if(repeatParam != null){
                            flag = true;
                            field.setAccessible(true);
                            String prefix = repeatSubmit.prefix();
                            key = prefix +field.get(object);
                            break;
                        }
                    }
                }
            }
        }
        if(!flag) {
            return pjo.proceed();
        }else {
            Boolean success = false;
            try {
                success = redissonService.lock(key, 0, repeatSubmit.time());
                if(success){
                    return pjo.proceed();
                }else {
                    log.error("{},key:{}",repeatSubmit.message(),key);
                    throw new BusinessException(repeatSubmit.message());
                }
            } finally {
                if(success) {
                    redissonService.releaseLock(key);
                }
            }
        }
    }

    private String generateRepeatSubmitKey(String param){
        return ""+param;
    }
}