package com.sd.teaching.common.aop;

import com.sd.teaching.common.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

/**
 * @Package: com.sd.teaching.common.aop.MybatisAop
 * @Description: 
 * @author sudan
 * @date 2020/7/14 14:35
 */

@Component
@Aspect
@Order(1)
@Slf4j
public class MybatisAop {

    @Autowired
    private IdWorker idWorker;

    @Around("execution(* tk.mybatis.mapper.common.base.insert..*.*(..)) ||" +
            "execution(* tk.mybatis.mapper.common.special..*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Object[] args = proceedingJoinPoint.getArgs();
        if(args != null && args.length >0){
            for (Object object : args){
                if(object instanceof Collection){
                    for (Object entity : (Collection)object){
                        initInsertEntity(entity);
                    }
                }else {
                    initInsertEntity(object);
                }
            }
        }
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        }catch (Exception e){
            log.info("插入数据库处理异常");
            if (e instanceof UncategorizedSQLException) {
                log.info("捕获插入数据库处理异常");
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }

    @Around("execution(* tk.mybatis.mapper.common.base.update..*.*(..)) ||" +
            "execution(* tk.mybatis.mapper.common.example..Update*.*(..))")
    public Object doUpdateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object[] args = proceedingJoinPoint.getArgs();
        if(args != null && args.length >0){
            Object entity = args[0];
            initUpdateEntity(entity);
        }
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        }catch (Exception e){
            log.info("更新数据库处理异常");
            if (e instanceof UncategorizedSQLException) {
                log.info("捕获插入数据库处理异常");
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }

    private void initUpdateEntity(Object entity) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(entity);
        beanWrapper.setPropertyValue("updateTime",new Date());
        beanWrapper.setPropertyValue("updatorId","admin");
    }

    private void initInsertEntity(Object object) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
        beanWrapper.setPropertyValue("id",idWorker.nextId() );
        beanWrapper.setPropertyValue("createTime",new Date());
        beanWrapper.setPropertyValue("creatorId","admin");
        beanWrapper.setPropertyValue("updateTime",new Date());
        beanWrapper.setPropertyValue("updatorId","admin");
        beanWrapper.setPropertyValue("isDeleted","0");
    }

}