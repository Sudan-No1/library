package com.sd.common.aop;

import com.sd.common.annotation.Dict;
import com.sd.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @Package: com.sd.common.aop.DictAop
 * @Description: 
 * @author sudan
 * @date 2020/8/27 10:41
 */
 

@Component
@Aspect
public class DictAop {

    @Autowired
    private DictionaryService dictionaryService;

    @Around("execution(* com.sd.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object reslut = proceedingJoinPoint.proceed();
        if(reslut == null){
            return null;
        }
        if(reslut instanceof Collection){
            for(Object object : (Collection)reslut){
                dealResult(object);
            }
        }else{
            dealResult(reslut);
        }
        return reslut;
    }

    private void dealResult(Object object) throws Exception {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(Dict.class)){
                Dict annotation = declaredField.getAnnotation(Dict.class);
                String name = declaredField.getName().replace("Name", "");
                Field field = object.getClass().getDeclaredField(name);
                field.setAccessible(true);
                String type = annotation.type();
                String dictName = dictionaryService.queryName(type, (String)field.get(object));
                if(StringUtils.isNotBlank(dictName)){
                    declaredField.setAccessible(true);
                    declaredField.set(object,dictName);
                }
            }
        }
    }

}