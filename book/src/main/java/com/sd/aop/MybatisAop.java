package com.sd.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @Package: com.sd.aop.MybatisAop
 * @Description: 
 * @author sudan
 * @date 2020/5/27 20:42
 */

@Component
@Aspect
@Slf4j
@Order(-1)
public class MybatisAop {

    @Around("execution(* tk.mybatis.mapper.common.base.insert..*.*(..)) || " +
            "execution(* tk.mybatis.mapper.common.special..*.*(..))")
    public Object doAroundInsert(ProceedingJoinPoint pjp)throws Throwable{
        Object[] args = pjp.getArgs();
        if(args != null && args.length>0){
            for (Object entity : args){
                if(entity instanceof Collection){
                    for (Object obj : (Collection) entity){
                        initInsertEntity(obj);
                    }
                }else {
                    initInsertEntity(entity);
                }
            }
        }
        Object result = null;
        try {
            result = pjp.proceed();
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

    /**
     * 拦截TK插件中调用的Update方法
     */
    @Around("execution(* tk.mybatis.mapper.common.base.update..*.*(..)) || " +
            "execution(* tk.mybatis.mapper.common.example..Update*.*(..))")
    public Object doAroundUpdate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取目标方法的参数信息
        Object[] arguments = proceedingJoinPoint.getArgs();
        if (arguments != null && arguments.length > 0) {
            Object entity = arguments[0];
            initUpdateEntity(entity);
        }
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            log.info("更新数据库处理异常");
            if (e instanceof UncategorizedSQLException) {
                log.info("捕获更新数据库处理异常");
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }

    private void initInsertEntity(Object object) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
        beanWrapper.setPropertyValue("id", UUID.randomUUID().toString().replace("-",""));
        beanWrapper.setPropertyValue("creatorId", "system");
        beanWrapper.setPropertyValue("updatorId", "system");
        beanWrapper.setPropertyValue("createTime", new Date());
        beanWrapper.setPropertyValue("updateTime", new Date());
    }
    private void initUpdateEntity(Object object) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
        beanWrapper.setPropertyValue("updatorId", "system");
        beanWrapper.setPropertyValue("updateTime", new Date());
    }
}